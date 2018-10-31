// $Id: TcReceivingOperator.java 4406 2009-06-08 03:50:27Z ota $
package jp.co.daifuku.wms.receiving.operator;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.ReceivingWorkInfoController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.crossdock.operator.AbstractTcOperator;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;
import jp.co.daifuku.wms.receiving.schedule.TcReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.TcReceivingOutParameter;

/**
 * 入荷作業を行うためのオペレータクラス。
 *
 * @version $Revision: 4406 $, $Date: 2009-06-08 12:50:27 +0900 (月, 08 6 2009) $
 * @author  Softecs
 * @author  Last commit: $Author: ota $
 */

public class TcReceivingOperator
        extends AbstractTcOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws ScheduleException スケジュール処理でエラーが発生した場合にスローされます。
     */
    public TcReceivingOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入荷一括確定開始<BR>
     * パラメータの項目に該当するデータに対して入荷の確定処理を行います。<BR>
     * 
     * @param param 入力パラメータクラス
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)
     * @throws ScheduleException DMWarenaviSystemに整合性がない
     * @throws DataExistsException 作業情報が登録済み
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     * @throws OperatorException オペレータ処理でエラー発生
     * @throws CommonException 共通例外が発生
     * @return OutParameter 出力パラメータクラス
     */
    public OutParameter webComplete(TcReceivingInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException
    {
        // 返却用のTcReceivingOutParameter
        TcReceivingOutParameter outParam = new TcReceivingOutParameter();

        ReceivingWorkInfoFinder finder = null;

        try
        {
            // ::: 対象の入荷作業情報、TC予定情報データのロック

            // 入荷作業情報
            finder = new ReceivingWorkInfoFinder(getConnection());
            ReceivingWorkInfoSearchKey winfoSkey = new ReceivingWorkInfoSearchKey();

            finder.open(true);

            // 検索条件
            winfoSkey.setPlanDay(param.getPlanDay());
            if (!StringUtil.isBlank(param.getSupplierCode()))
            {
                winfoSkey.setSupplierCode(param.getSupplierCode());
            }
            winfoSkey.setConsignorCode(param.getConsignorCode());

            winfoSkey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            winfoSkey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);

            winfoSkey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);

            // ロック
            int count = finder.searchForUpdate(winfoSkey, ReceivingWorkInfoFinder.WAIT_SEC_DEFAULT);

            // ロック件数0件のとき
            if (count == 0)
            {
                return null;
            }

            // 作業日の取得
            WarenaviSystemController wscon = new WarenaviSystemController(getConnection(), getCaller());
            String workDay = wscon.getWorkDay();

            // 設定単位キーの取得
            String settingUkey = this.getSeqHandler().nextReceivingSetUkey();

            // ロックした行データを元に更新処理を行う
            while (finder.hasNext())
            {
                ReceivingWorkInfo[] workInfos = (ReceivingWorkInfo[])finder.getEntities(100);
                for (ReceivingWorkInfo workInfo : workInfos)
                {
                    // 完了情報の作成
                    ReceivingWorkInfo resultWorkInfo = new ReceivingWorkInfo();

                    resultWorkInfo.setSettingUnitKey(settingUkey);
                    resultWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
                    resultWorkInfo.setPlanQty(workInfo.getPlanQty());
                    resultWorkInfo.setResultQty(workInfo.getPlanQty());
                    resultWorkInfo.setShortageQty(0);
                    resultWorkInfo.setResultLotNo(workInfo.getPlanLotNo());
                    resultWorkInfo.setWorkDay(workDay);
                    resultWorkInfo.setWorkSecond(0);
                    resultWorkInfo.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);

                    // 更新処理
                    this.completeReceiving(workInfo, resultWorkInfo, param.getWmsUserInfo());
                }
            }

            return outParam;
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 欠品確定<BR>
     * RFTで定義された欠品情報を確定します。
     * 
     * @param inParams 入力パラメータクラス配列
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)
     * @throws ScheduleException DMWarenaviSystemに整合性がない
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     * @throws OperatorException オペレータ処理でエラー発生
     * @throws DataExistsException 作業情報が登録済み
     * @throws CommonException 共通例外が発生
     */
    public void shortageComplete(TcReceivingInParameter[] inParams)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                DataExistsException,
                CommonException
    {
        // Finder
        ReceivingWorkInfoFinder finder = new ReceivingWorkInfoFinder(getConnection());

        try
        {
            // ::: 対象の入荷作業情報、TC予定情報、出荷予定情報データのロック

            ReceivingWorkInfoSearchKey skey = new ReceivingWorkInfoSearchKey();
            finder.open(true);

            for (TcReceivingInParameter param : inParams)
            {
                // 対象リストセル行番号をセットしておく
                this.setRowNo(param.getRowNo());

                skey.clear();

                // 検索条件
                skey.setJobNo(param.getJobNo());
                skey.setLastUpdateDate(param.getLastUpdateDate());
                skey.setStatusFlag(SystemDefine.STATUS_FLAG_SHORTAGE_RESERVATION);

                skey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
                skey.setJoin(ReceivingWorkInfo.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);

                // ロック
                int count = finder.searchForUpdate(skey, ReceivingWorkInfoFinder.WAIT_SEC_DEFAULT);

                // ロック件数不正(取得データは1件のみのはず)
                if (count == 0)
                {
                    throw new NotFoundException();
                }
                else if (count >= 2)
                {
                    throw new NoPrimaryException();
                }

                // ロックしたEntityをInParameterにセット
                param.setLockEntity(finder.getEntities(1)[0]);
            }

            // 作業日の取得
            WarenaviSystemController wscon = new WarenaviSystemController(getConnection(), getCaller());
            String workDay = wscon.getWorkDay();

            // ::: 欠品完了処理(入荷作業情報とTC予定情報の更新)
            for (TcReceivingInParameter param : inParams)
            {
                // 対象リストセル行番号をセットしておく
                this.setRowNo(param.getRowNo());

                // ロックされたEntityを取得
                ReceivingWorkInfo workInfoLock = (ReceivingWorkInfo)param.getLockEntity();

                // 完了情報の作成
                ReceivingWorkInfo resultWorkInfo = new ReceivingWorkInfo();

                resultWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
                resultWorkInfo.setPlanQty(workInfoLock.getPlanQty());
                resultWorkInfo.setResultQty(0);
                resultWorkInfo.setShortageQty(workInfoLock.getPlanQty());
                resultWorkInfo.setResultLotNo(workInfoLock.getResultLotNo());
                resultWorkInfo.setWorkDay(workDay);
                resultWorkInfo.setWorkSecond(0);
                resultWorkInfo.setStatusFlag(this.getCrossDockPlanStatus(param.getCrossDockUkey()));

                // 入荷完了処理
                this.completeReceiving(workInfoLock, resultWorkInfo, param.getWmsUserInfo());
            }
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 欠品キャンセル<BR>
     * RFTで定義された欠品情報をキャンセルします。
     * 
     * @param inParams 入力パラメータクラス配列
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)
     * @throws ScheduleException DMWarenaviSystemに整合性がない
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     * @throws OperatorException オペレータ処理でエラー発生
     * @throws CommonException 共通例外が発生
     */
    public void shortageCancel(TcReceivingInParameter[] inParams)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException
    {
        // Finder
        ReceivingWorkInfoFinder finder = new ReceivingWorkInfoFinder(getConnection());

        try
        {
            ReceivingWorkInfoSearchKey skey = new ReceivingWorkInfoSearchKey();
            finder.open(true);

            // ::: 対象の入荷作業情報、TC予定情報データのロック

            // ロック件数
            int count = 0;

            for (TcReceivingInParameter param : inParams)
            {
                // 対象リストセル行番号をセットしておく
                this.setRowNo(param.getRowNo());

                skey.clear();

                // 検索条件
                skey.setJobNo(param.getJobNo());
                skey.setLastUpdateDate(param.getLastUpdateDate());
                skey.setStatusFlag(SystemDefine.STATUS_FLAG_SHORTAGE_RESERVATION);

                skey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);

                // ロック
                count = finder.searchForUpdate(skey, ReceivingWorkInfoFinder.WAIT_SEC_DEFAULT);

                // ロック件数不正
                if (count == 0)
                {
                    throw new NotFoundException();
                }
            }

            // Handler
            CrossDockPlanHandler planHandler = new CrossDockPlanHandler(getConnection());

            ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

            // ::: 欠品キャンセル処理(入荷作業情報とTC予定情報の更新)
            for (TcReceivingInParameter param : inParams)
            {
                // 対象リストセル行番号をセットしておく
                this.setRowNo(param.getRowNo());

                // ::: 入荷作業情報の更新

                wic.cancelShortageWorkInfo(param.getJobNo());

                // ::: TC予定情報の更新
                CrossDockPlanAlterKey cpKey = new CrossDockPlanAlterKey();

                // 更新内容
                cpKey.updateStatusFlag(this.getCrossDockPlanStatus(param.getCrossDockUkey()));
                cpKey.updateLastUpdatePname(this.getCallerName());

                // 条件
                cpKey.setPlanUkey(param.getPlanukey());

                planHandler.modify(cpKey);
            }
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * RFT入荷作業開始(商品単位)。<br>
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * RFTでの入荷作業開始に必要なパラメータをセットして入荷開始ロック処理を行います。
     * 商品コード、JANコード、ケースITF、ボールITFの順にデータが取得できるまで検索を行います。
     * 重複不可項目が重複していた場合はOperatorExceptionをスローします。<br>
     * 入荷開始ロック処理で検索した入荷作業情報をパラメータとして入荷作業開始メソッドを呼び出します。
     * @param param 入荷入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws NotFoundException 該当するデータが見当たらない場合にスローされます。
     * @throws DuplicateOperatorException 重複するデータがあった場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public OutParameter rftStartByItem(TcReceivingInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {


        // 入荷開始対象データを検索します。
        List<String> scanItemCodeList = new ArrayList<String>();
        final String planDay = param.getPlanDay();
        final String consignorCode = param.getConsignorCode();
        final String supplierCode = param.getSupplierCode();
        final String scanItemCode = param.getItemCode();
        final String ticketNo = param.getRecievingTicketNo();
        final String lotNo = param.getLotNo();

        ItemController itemController = new ItemController(getConnection(), getCaller());
        ReceivingWorkInfoFinder finder = new ReceivingWorkInfoFinder(getConnection());
        ReceivingWorkInfo[] targetWorkInfos = null;
        ArrayList<ReceivingOutParameter> list = new ArrayList<ReceivingOutParameter>();
        try
        {
            for (int scanIdx = 0; scanIdx < ItemController.MAX_SCAN_INDEX; scanIdx++)
            {
                final String itemCode =
                        (0 == scanIdx) ? scanItemCode
                                      : itemController.getItemCode(consignorCode, scanItemCode, scanIdx);

                if (StringUtil.isBlank(itemCode))
                {
                    continue;
                }
                scanItemCodeList.add(itemCode);

                ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();
                searchKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_UNSTART);
                searchKey.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_TC);
                if (!StringUtil.isBlank(planDay))
                {
                    searchKey.setPlanDay(planDay);
                }
                searchKey.setConsignorCode(consignorCode);
                if (!StringUtil.isBlank(supplierCode))
                {
                    searchKey.setSupplierCode(supplierCode);
                }
                if (!StringUtil.isBlank(ticketNo))
                {
                    searchKey.setReceiveTicketNo(ticketNo);
                }
                searchKey.setItemCode(itemCode);

                if (RftConst.SELECT_FLAG_SELECT.equals(param.getLotSelectionFlag()))
                {
                    searchKey.setPlanLotNo(lotNo);
                }

                searchKey.setPlanDayOrder(true);
                searchKey.setSupplierCodeOrder(true);
                searchKey.setReceiveTicketNoOrder(true);
                searchKey.setPlanLotNoOrder(true);

                finder.open(true);
                finder.searchForUpdate(searchKey, 10);
                ReceivingOutParameter outParam = null;

                while (finder.hasNext())
                {
                    targetWorkInfos = (ReceivingWorkInfo[])finder.getEntities(100);


                    for (ReceivingWorkInfo targetWorkInfo : targetWorkInfos)
                    {
                        outParam = new ReceivingOutParameter();

                        outParam.setJobNo(targetWorkInfo.getJobNo());
                        outParam.setPlanUKey(targetWorkInfo.getPlanUkey());
                        outParam.setReceivingDay(targetWorkInfo.getPlanDay());
                        outParam.setSupplierCode(targetWorkInfo.getSupplierCode());
                        outParam.setTicketNo(targetWorkInfo.getReceiveTicketNo());
                        outParam.setPlanLotNo(targetWorkInfo.getPlanLotNo());
                        list.add(outParam);
                    }
                }
            } // end loop.

            ReceivingOutParameter[] outParams = new ReceivingOutParameter[list.size()];
            list.toArray(outParams);

            // 入荷開始不可能データチェックを行います。
            if (ArrayUtil.isEmpty(outParams))
            {
                ReceivingWorkInfo workInfo = new ReceivingWorkInfo();
                workInfo.setPlanDay(planDay);
                workInfo.setConsignorCode(consignorCode);
                workInfo.setSupplierCode(supplierCode);
                workInfo.setReceiveTicketNo(ticketNo);
                workInfo.setPlanLotNo(lotNo);
                // 入荷開始できない理由を例外で通知します。
                String[] items = scanItemCodeList.toArray(new String[scanItemCodeList.size()]);
                checkReceivingStart(workInfo, items);
            }
            ReceivingWorkInfo[] workInfos = new ReceivingWorkInfo[outParams.length];
            for (int i = 0; i < workInfos.length; i++)
            {
                workInfos[i] = new ReceivingWorkInfo();
                workInfos[i].setPlanDay(outParams[i].getReceivingDay());
                workInfos[i].setPlanLotNo(outParams[i].getPlanLotNo());
                workInfos[i].setReceiveTicketNo(outParams[i].getTicketNo());
                workInfos[i].setSupplierCode(outParams[i].getSupplierCode());
            }
            if (!ArrayUtil.isEmpty(workInfos))
            {
                // 重複不可項目が重複していた場合、例外が通知されます。
                checkDuplicate(workInfos);
            }

            // 入荷作業開始します。
            WmsUserInfo userInfo = param.getWmsUserInfo();
            userInfo.setHardwareType(ReceivingWorkInfo.HARDWARE_TYPE_RFT);
            final int maxCollect = WmsParam.MAX_RFT_JOBCOLLECT;

            OutParameter startedParam = start(maxCollect, outParams, userInfo);

            return startedParam;
        }
        finally
        {
            finder.close();
        }
    }

    /**
     * 入荷作業完了。<br>
     * パラメータの設定単位キーに該当するデータに対して入荷完了処理を行います。<br>
     * 完了フラグによって確定(完了 or 欠品完了)、キャンセル処理のいずれかを行います。<br>
     * 集約作業完了メソッドはパラメータの配列インデックス分ループして処理します。
     * @param params 入荷入力パラメータ(配列)
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字を含む など…)の場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     */
    public void complete(TcReceivingInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException();
        }

        // 最初の入力パラメータにより入荷作業情報、TC予定情報のロックを行います。
        final String settingUnitKey = params[0].getSettingUnitKey();
        ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();
        searchKey.setSettingUnitKey(settingUnitKey);
        searchKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
        searchKey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
        ReceivingWorkInfoFinder finder = new ReceivingWorkInfoFinder(getConnection());
        try
        {
            finder.open(true);

            finder.searchForUpdate(searchKey, ReceivingWorkInfoFinder.WAIT_SEC_NOWAIT);

            while (finder.hasNext())
            {
                ReceivingWorkInfo[] work = (ReceivingWorkInfo[])finder.getEntities(100);

                if (ArrayUtil.isEmpty(work))
                {
                    throw new NotFoundException();
                }
                else
                {
                    break;
                }

            }
            // 入力パラメータで指定されたデータの作業完了を行います。
            int lineNo = 1;
            try
            {
                for (TcReceivingInParameter param : params)
                {
                    completeCollectData(param);
                    lineNo++;
                }
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(lineNo);
                throw e;
            }
        }
        finally
        {
            finder.close();
        }
    }

    /**
     * 入荷作業キャンセル。<br>
     * パラメータの設定単位キーに該当するデータに対して一括して入荷キャンセル処理を行います。
     * @param param 入荷入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    public void cancel(TcReceivingInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                NoPrimaryException
    {

        ReceivingWorkInfoFinder finder = new ReceivingWorkInfoFinder(getConnection());
        try
        {
            final String settingUnitKey = param.getSettingUnitKey();
            ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
            ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());

            finder.open(true);

            finder.searchForUpdate(searchKey, ReceivingWorkInfoFinder.WAIT_SEC_NOWAIT);

            ReceivingWorkInfo tgtWorkInfo = null;

            while (finder.hasNext())
            {
                ReceivingWorkInfo[] workInfos = (ReceivingWorkInfo[])finder.getEntities(100);


                if (ArrayUtil.isEmpty(workInfos))
                {
                    throw new NotFoundException();
                }
                tgtWorkInfo = workInfos[0];
            }

            ReceivingWorkInfoAlterKey alterKey = createCancelKey();
            alterKey.setSettingUnitKey(settingUnitKey);
            alterKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
            handler.modify(alterKey);

            final String crossDockStatus = getCrossDockPlanStatus(tgtWorkInfo.getCrossDockUkey());
            boolean isUnstart = CrossDockPlan.STATUS_FLAG_UNSTART.equals(crossDockStatus);
            if (isUnstart)
            {
                CrossDockPlanAlterKey cdAlterKey = new CrossDockPlanAlterKey();
                cdAlterKey.updateStatusFlag(CrossDockPlan.STATUS_FLAG_UNSTART);
                cdAlterKey.updateLastUpdatePname(getCallerName());
                cdAlterKey.setPlanUkey(tgtWorkInfo.getPlanUkey());
                CrossDockPlanHandler cdHandler = new CrossDockPlanHandler(getConnection());
                cdHandler.modify(cdAlterKey);
            }
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        finally
        {
            finder.close();
        }
    }

    /**
     * 開始不可能データチェック。<br>
     * パラメータの項目にて入荷作業情報の検索を行い、入荷開始データの状態をチェックします。<br>
     * 削除データを除いて、未作業が存在する場合は他端末で更新されたエラーコード、
     * 作業中が存在する場合は他端末の作業中のエラーコード、全て完了の場合は作業完了済みの
     * エラーコードでOperatorExceptionをスローします。<br>
     * 該当データが存在しない場合、または全て削除データの場合はNotFoundExceptionをスローします。
     * @param tgt 対象データ
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void checkReceivingStart(ReceivingWorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();
        searchKey.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_TC);
        final String planDay = tgt.getPlanDay();
        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        searchKey.setConsignorCode(tgt.getConsignorCode());
        final String supplierCode = tgt.getSupplierCode();
        if (!StringUtil.isBlank(supplierCode))
        {
            searchKey.setSupplierCode(supplierCode);
        }
        final String receiveTicketNo = tgt.getReceiveTicketNo();
        if (!StringUtil.isBlank(receiveTicketNo))
        {
            searchKey.setReceiveTicketNo(receiveTicketNo);
        }
        searchKey.setItemCode(items, true);
        String planLotNo = tgt.getPlanLotNo();
        if (!StringUtil.isBlank(planLotNo))
        {
            searchKey.setPlanLotNo(planLotNo);
        }

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        searchKey.setCollect(ReceivingWorkInfo.STATUS_FLAG, "MIN", minStatusField);
        searchKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_DELETE, "!=");

        ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfo[] workInfos = (ReceivingWorkInfo[])handler.find(searchKey);
        if (ArrayUtil.isEmpty(workInfos))
        {
            throw new NotFoundException();
        }

        String errCode = OperatorException.ERR_ALREADY_UPDATED;
        Object minStatus = workInfos[0].getValue(minStatusField);
        if (null == minStatus)
        {
            throw new NotFoundException();
        }
        if (ReceivingWorkInfo.STATUS_FLAG_UNSTART.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_UPDATED;
        }
        else if (ReceivingWorkInfo.STATUS_FLAG_NOWWORKING.equals(minStatus))
        {
            errCode = OperatorException.ERR_WORKING_INPROGRESS;
        }
        else if (ReceivingWorkInfo.STATUS_FLAG_COMPLETION.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_COMPLETED;
        }
        else if (ReceivingWorkInfo.STATUS_FLAG_SHORTAGE_RESERVATION.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_COMPLETED;
        }
        throw new OperatorException(errCode);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * 入荷作業開始。<br>
     * 入荷作業化情報をパラメータとし、集約して開始処理を行います。<br>
     * 設定単位キーを採番して戻り値として返します。<br>
     * 入荷場所、出荷先コード、予定ロットNo単位で集約作業Noを採番して更新処理を行います。<br>
     * パラメータの配列インデックス分作業情報開始処理を行います。
     * @param maxCollect 集約作業No最大件数(件数無制限(-1)を渡された場合は無制限とします。)
     * @param works 入荷作業情報エンティティ(配列)
     * @param ui WMSユーザ情報
     * @return 出力パラメータ
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     */
    protected TcReceivingOutParameter start(int maxCollect, ReceivingOutParameter[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        try
        {
            TcReceivingOutParameter outParam = new TcReceivingOutParameter();
            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
            final String settingUnitKey = seqHandler.nextReceivingSetUkey();
            outParam.setSettingUnitKey(settingUnitKey);

            ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());
            CrossDockPlanHandler cdHandler = new CrossDockPlanHandler(getConnection());

            String collectJobNo = "";
            int collectJobNoCount = 0;
            for (ReceivingOutParameter workInfo : works)
            {
                // 集約作業Noを採番するか決定します。
                boolean ret = outParam.setKeys(workInfo);
                if (!ret)
                {
                    collectJobNo = seqHandler.nextReceivingCollectJobNo();
                    collectJobNoCount++;
                    if (WmsParam.MAX_RFT_JOBCOLLECT_UNLIMITED != maxCollect && collectJobNoCount > maxCollect)
                    {
                        break;
                    }
                }
                outParam.setCollectJobNo(collectJobNo);

                // 入荷作業情報の更新を行います。
                ReceivingWorkInfoAlterKey alterKey = new ReceivingWorkInfoAlterKey();
                alterKey.updateSettingUnitKey(settingUnitKey);
                alterKey.updateCollectJobNo(collectJobNo);
                alterKey.updateStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
                alterKey.updateHardwareType(ui.getHardwareType());
                alterKey.updateTerminalNo(ui.getTerminalNo());
                alterKey.updateUserId(ui.getUserId());
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setJobNo(workInfo.getJobNo());
                handler.modify(alterKey);

                // TC予定情報の更新を行います。
                CrossDockPlanAlterKey tcAlterKey = new CrossDockPlanAlterKey();
                tcAlterKey.updateStatusFlag(CrossDockPlan.STATUS_FLAG_NOWWORKING);
                tcAlterKey.updateLastUpdatePname(getCallerName());
                //                tcAlterKey.setPlanUkey(workInfo.getPlanUkey());
                tcAlterKey.setPlanUkey(workInfo.getPlanUKey());
                cdHandler.modify(tcAlterKey);
            } // end workInfo loop.

            return outParam;
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 集約作業完了。<br>
     * パラメータの集約作業Noに該当するデータに対して、入荷作業情報を検索し完了処理を行います。<br>
     * パラメータの実績数、作業秒数を分配し、TC予定情報、出荷予定情報、入荷作業情報を更新します。<br>
     * 完了作業情報検索で取得した件数分ループし、処理を行います。<br>
     * 予定一意キーが変わった場合のみ予定情報の更新を行います。
     * @param param 入荷入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeCollectData(TcReceivingInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // 入荷作業情報の検索を行います。
            ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();
            searchKey.setCollectJobNo(param.getCollectJobNo());
            searchKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(ReceivingWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
            searchKey.setOrder(CrossDockPlan.CUSTOMER_CODE, true);
            searchKey.setOrder(CrossDockPlan.SHIP_TICKET_NO, true);
            searchKey.setOrder(CrossDockPlan.SHIP_LINE_NO, true);
            searchKey.setOrder(ReceivingWorkInfo.JOB_NO, true);

            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
            ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());
            ReceivingWorkInfo[] workInfos = (ReceivingWorkInfo[])handler.find(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            int remResult = param.getResultQty();
            WarenaviSystemController wmsController = new WarenaviSystemController(getConnection(), getCaller());
            final String workDay = wmsController.getWorkDay();

            WmsUserInfo paramUserInfo = param.getWmsUserInfo();
            final String completionFlag = param.getCompletionFlag();
            boolean isDecisionComp = TcReceivingInParameter.COMPLETION_FLAG_DECISION.equals(completionFlag);

            int resultQty = 0;
            int shortageQty = 0;
            for (ReceivingWorkInfo workInfo : workInfos)
            {
                // 実績数、欠品数の分配を行います。
                int planQty = workInfo.getPlanQty();
                if (remResult < planQty)
                {
                    resultQty = remResult;
                    if (isDecisionComp)
                    {
                        shortageQty = planQty - resultQty;
                    }
                    planQty = (resultQty != 0) ? resultQty
                                              : planQty;

                }
                else
                {
                    resultQty = planQty;
                }
                remResult -= resultQty;

                // 作業秒数の分配を行います。
                int workSeconds = param.getWorkSeconds();
                if (0 < workSeconds)
                {
                    // (workSeconds / paramQty) * resultQty => (workSeconds * resultQty) / paramQty
                    double ws = (double)(workSeconds * resultQty) / (double)param.getResultQty();
                    workSeconds = (int)Math.round(ws);
                }

                // 完了処理を行います。
                ReceivingWorkInfo compWorkInfo = new ReceivingWorkInfo();
                compWorkInfo.setPlanQty(planQty);
                compWorkInfo.setResultQty(resultQty);
                compWorkInfo.setResultLotNo(param.getLotNo());
                compWorkInfo.setWorkDay(workDay);
                compWorkInfo.setWorkSecond(workSeconds);
                completeReceiving(workInfo, compWorkInfo, paramUserInfo);

                final int workInfoPlanQty = workInfo.getPlanQty();
                if (shortageQty == workInfoPlanQty)
                {
                    // 欠品予約更新処理を行います。
                    ReceivingWorkInfoAlterKey alterKey = new ReceivingWorkInfoAlterKey();
                    alterKey.setJobNo(workInfo.getJobNo());
                    alterKey.updateStatusFlag(ReceivingWorkInfo.STATUS_FLAG_SHORTAGE_RESERVATION);
                    alterKey.updateUserId("");
                    alterKey.updateTerminalNo("");
                    alterKey.updateLastUpdatePname(getCallerName());

                    handler.modify(alterKey);
                }
                else if (resultQty < workInfoPlanQty)
                {
                    // 分割処理を行います。
                    ReceivingWorkInfo cloneWorkInfo = (ReceivingWorkInfo)workInfo.clone();
                    cloneWorkInfo.setJobNo(seqHandler.nextReceivingJobNo());
                    cloneWorkInfo.setPlanQty(cloneWorkInfo.getPlanQty() - resultQty);
                    cloneWorkInfo.setResultQty(0);
                    cloneWorkInfo.setShortageQty(0);
                    final String programName = getCallerName();
                    cloneWorkInfo.setRegistPname(programName);
                    cloneWorkInfo.setRegistDate(new SysDate());
                    cloneWorkInfo.setLastUpdatePname(programName);
                    if (isDecisionComp)
                    {
                        cloneWorkInfo.setUserId(cloneWorkInfo.getUserId());
                        cloneWorkInfo.setTerminalNo(cloneWorkInfo.getTerminalNo());
                        cloneWorkInfo.setResultLotNo(param.getLotNo());
                        // 確定 (一部欠品)
                        cloneWorkInfo.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_SHORTAGE_RESERVATION);
                    }
                    else
                    {
                        cloneWorkInfo.setSettingUnitKey("");
                        cloneWorkInfo.setCollectJobNo("");
                        cloneWorkInfo.setHardwareType(ReceivingWorkInfo.HARDWARE_TYPE_UNSTART);
                        cloneWorkInfo.setUserId("");
                        cloneWorkInfo.setTerminalNo("");
                        // 分納
                        cloneWorkInfo.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_UNSTART);
                    }

                    handler.create(cloneWorkInfo);
                }
                else if (0 == (resultQty + shortageQty))
                {
                    // 作業情報のキャンセル処理を行います。
                    ReceivingWorkInfoAlterKey alterKey = createCancelKey();
                    alterKey.setJobNo(workInfo.getJobNo());

                    handler.modify(alterKey);
                }
            } // end workInfo loop.
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 重複チェック。<br>
     * パラメータの入荷作業情報の重複不可項目が」重複していないかチェックします。<br>
     * 重複している場合は、エラーコードと重複しているデータを通知します。
     * @param works 入荷作業情報エンティティ(配列)
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    protected void checkDuplicate(ReceivingWorkInfo[] works)
            throws OperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, ReceivingWorkInfo.PLAN_DAY);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, ReceivingWorkInfo.SUPPLIER_CODE);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_SUPPLIER_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, ReceivingWorkInfo.RECEIVE_TICKET_NO);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_TICKET_NO_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, ReceivingWorkInfo.PLAN_LOT_NO);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_LOT_NO_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * キャンセルデータ更新キー作成。<br>
     * キャンセル用の更新キーを生成して返します。
     * @return キャンセル用の更新キー
     */
    protected ReceivingWorkInfoAlterKey createCancelKey()
    {
        ReceivingWorkInfoAlterKey alterKey = new ReceivingWorkInfoAlterKey();
        alterKey.updateSettingUnitKey("");
        alterKey.updateCollectJobNo("");
        alterKey.updateStatusFlag(ReceivingWorkInfo.STATUS_FLAG_UNSTART);
        alterKey.updateHardwareType(ReceivingWorkInfo.HARDWARE_TYPE_UNSTART);
        alterKey.updateUserId("");
        alterKey.updateTerminalNo("");
        alterKey.updateLastUpdatePname(getCallerName());
        return alterKey;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: TcReceivingOperator.java 4406 2009-06-08 03:50:27Z ota $";
    }
}
