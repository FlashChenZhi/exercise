// $Id: SortOperator.java,v 1.1.1.1 2009/02/10 08:55:48 arai Exp $
package jp.co.daifuku.wms.sort.operator;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
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
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.SortHostSend;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.crossdock.operator.AbstractTcOperator;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.sort.schedule.SortInParameter;
import jp.co.daifuku.wms.sort.schedule.SortOutParameter;

/**
 * 仕分作業を行うためのオペレータクラス。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:48 $
 * @author  Softecs
 * @author  Last commit: $Author: arai $
 */

public class SortOperator
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
    public SortOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 仕分一括確定開始<BR>
     * パラメータの項目に該当するデータに対して仕分の確定処理を行います。<BR>
     * 
     * @param param 仕分パラメータオブジェクト
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
    public OutParameter webComplete(SortInParameter param)
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
        // 返却用のSortOutParameter
        SortOutParameter outParam = new SortOutParameter();

        // Finder
        SortWorkInfoFinder finder = null;

        try
        {
            // ::: 対象の仕分作業情報、TC予定情報、出荷予定情報データのロック

            finder = new SortWorkInfoFinder(getConnection());
            SortWorkInfoSearchKey winfoSkey = new SortWorkInfoSearchKey();

            finder.open(true);

            // 検索条件
            winfoSkey.setPlanDay(param.getPlanDay());
            if (!StringUtil.isBlank(param.getBatchNo()))
            {
                winfoSkey.setBatchNo(param.getBatchNo());
            }
            if (!StringUtil.isBlank(param.getItemCode()))
            {
                winfoSkey.setItemCode(param.getItemCode());
            }
            if (!StringUtil.isBlank(param.getCustomerCode()))
            {
                winfoSkey.setCustomerCode(param.getCustomerCode());
            }
            if (!StringUtil.isBlank(param.getConsignorCode()))
            {
                winfoSkey.setConsignorCode(param.getConsignorCode());
            }
            winfoSkey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

            winfoSkey.setJoin(SortWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
            winfoSkey.setJoin(SortWorkInfo.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);

            // ロック
            int count = finder.searchForUpdate(winfoSkey, SortWorkInfoFinder.WAIT_SEC_DEFAULT);

            // ロック件数0件のとき
            if (count == 0)
            {
                return null;
            }

            // 作業日の取得
            WarenaviSystemController wscon = new WarenaviSystemController(getConnection(), getCaller());
            String workDay = wscon.getWorkDay();

            // 設定単位キーの取得
            String settingUkey = this.getSeqHandler().nextSortSetUkey();

            // ロックした行データを元に更新処理を行う
            while (finder.hasNext())
            {
                SortWorkInfo[] workInfos = (SortWorkInfo[])finder.getEntities(100);

                for (SortWorkInfo workInfo : workInfos)
                {
                    // 完了情報の作成
                    SortWorkInfo resultWorkInfo = new SortWorkInfo();

                    // 設定単位キー
                    resultWorkInfo.setSettingUnitKey(settingUkey);
                    // ハードウェア区分 ： 1：リスト
                    resultWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
                    // 実績数 ： 2で取得した仕分作業情報の予定数
                    resultWorkInfo.setResultQty(workInfo.getPlanQty());
                    // 欠品数 ： 0
                    resultWorkInfo.setShortageQty(0);
                    // 実績ロットNo. ： 2で取得した仕分作業情報の予定ロットNo.
                    resultWorkInfo.setResultLotNo(workInfo.getPlanLotNo());
                    // 作業日 ： 3で取得した作業日
                    resultWorkInfo.setWorkDay(workDay);
                    // 作業秒数 ： 0
                    resultWorkInfo.setWorkSecond(0);

                    // 更新処理
                    this.completeSort(workInfo, resultWorkInfo, param.getWmsUserInfo(), false);
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
     * 修正登録処理<BR>
     * 画面に入力された値から実績数、予定数、欠品数を計算し、
     * 仕分実績送信情報、仕分作業情報、TC予定情報の該当データの更新処理を行います。
     * 
     * @param params 仕分パラメータオブジェクト
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)
     * @throws ScheduleException DMWarenaviSystemに整合性がない
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     * @throws OperatorException オペレータ処理でエラー発生
     * @throws CommonException 共通例外が発生
     */
    public void webMaintenance(SortInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException
    {
        // finder
        SortHostSendFinder hostSendFinder = null;

        try
        {
            hostSendFinder = new SortHostSendFinder(getConnection());
            SortHostSendSearchKey skey = new SortHostSendSearchKey();

            hostSendFinder.open(true);

            // ::: 仕分実績送信情報、仕分作業情報、TC予定情報のロック処理
            for (SortInParameter param : params)
            {
                // 行Noをセットしておく
                this.setRowNo(param.getRowNo());

                skey.clear();

                // 検索条件
                skey.setJobNo(param.getJobNo());
                skey.setLastUpdateDate(param.getLastUpdateDate());

                // join
                skey.setJoin(SortHostSend.JOB_NO, SortWorkInfo.JOB_NO);
                skey.setJoin(SortHostSend.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);

                // ロック
                int count = hostSendFinder.searchForUpdate(skey, SortHostSendFinder.WAIT_SEC_DEFAULT);

                if (count == 0)
                {
                    throw new NotFoundException();
                }
                else if (count >= 2)
                {
                    throw new NoPrimaryException();
                }

                // 取得した行(Entity)をInParameterにセット
                param.setLockEntity(hostSendFinder.getEntities(1)[0]);
            }

            // ::: 修正登録処理
            for (SortInParameter param : params)
            {
                // 行Noをセットしておく
                this.setRowNo(param.getRowNo());

                // 実績数の計算
                int enteringQty = param.getEnteringQty();
                int resultQty = param.getResultCaseQty() * enteringQty + param.getResultPieceQty();

                // 予定数の計算
                int planQty = param.getPlanCaseQty() * enteringQty + param.getPlanPieceQty();

                // 欠品数の計算
                int shortageQty = planQty - resultQty;

                // ::: 仕分実績送信情報を更新
                SortHostSendHandler hostSendHandler = new SortHostSendHandler(getConnection());
                SortHostSendAlterKey hostSendAlterKey = new SortHostSendAlterKey();

                // 更新内容
                hostSendAlterKey.updateResultQty(resultQty);
                hostSendAlterKey.updateShortageQty(shortageQty);
                hostSendAlterKey.updateResultLotNo(param.getLotNo());
                hostSendAlterKey.updateUserId(param.getUserId());
                hostSendAlterKey.updateUserName(param.getUserName());
                hostSendAlterKey.updateTerminalNo(param.getTerminalNo());
                hostSendAlterKey.updateLastUpdatePname(getCallerName());

                // 更新条件
                hostSendAlterKey.setJobNo(param.getJobNo());

                // 更新
                hostSendHandler.modify(hostSendAlterKey);

                // ::: 仕分作業情報を更新
                SortWorkInfoHandler workInfoHandler = new SortWorkInfoHandler(getConnection());
                SortWorkInfoAlterKey workInfoAlterKey = new SortWorkInfoAlterKey();

                // 更新内容
                workInfoAlterKey.updateResultQty(resultQty);
                workInfoAlterKey.updateShortageQty(shortageQty);
                workInfoAlterKey.updateResultLotNo(param.getLotNo());
                workInfoAlterKey.updateUserId(param.getUserId());
                workInfoAlterKey.updateTerminalNo(param.getTerminalNo());
                workInfoAlterKey.updateLastUpdatePname(getCallerName());

                // 更新条件
                workInfoAlterKey.setJobNo(param.getJobNo());

                // 更新
                workInfoHandler.modify(workInfoAlterKey);

                // ::: TC予定情報を更新
                CrossDockPlanHandler planHandler = new CrossDockPlanHandler(getConnection());
                CrossDockPlanAlterKey planAlterKey = new CrossDockPlanAlterKey();

                // 更新内容
                SortHostSend workInfo = (SortHostSend)param.getLockEntity();
                int mntQty = workInfo.getResultQty() - resultQty;

                planAlterKey.setUpdateWithColumn(CrossDockPlan.RESULT_QTY, CrossDockPlan.RESULT_QTY,
                        BigDecimal.valueOf(-mntQty));
                planAlterKey.setUpdateWithColumn(CrossDockPlan.SHORTAGE_QTY, CrossDockPlan.SHORTAGE_QTY,
                        BigDecimal.valueOf(mntQty));
                planAlterKey.updateLastUpdatePname(getCallerName());

                // 更新条件
                planAlterKey.setPlanUkey(param.getPlanUKey());

                // 更新
                planHandler.modify(planAlterKey);
            }
        }
        finally
        {
            closeFinder(hostSendFinder);
        }
    }

    /**
     * RFT仕分作業開始。<br>
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * RFTでの仕分作業開始に必要なパラメータをセットして仕分開始ロック処理を行います。
     * 商品コード、JANコード、ケースITF、ボールITFの順にデータが取得できるまで検索を行います。
     * 重複不可項目が重複していた場合はOperatorExceptionをスローします。<br>
     * 仕分開始ロック処理で検索した仕分作業情報パラメータとして仕分作業開始メソッドを呼び出します。
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws NotFoundException 該当するデータが見当たらない場合にスローされます。
     * @throws DuplicateOperatorException 重複するデータがあった場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public OutParameter rftStart(SortInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {
        // 仕分開始対象データを検索します。
        List<String> scanItemCodeList = new ArrayList<String>();
        final String scanItemCode = param.getScanItemCode();
        final String consignorCode = param.getConsignorCode();
        final String batchNo = param.getBatchNo();
        final String planDay = param.getPlanDate();
        final String lotNo = param.getLotNo();

        ItemController itemController = new ItemController(getConnection(), getCaller());
        SortWorkInfoFinder finder = new SortWorkInfoFinder(getConnection());
        ArrayList<SortOutParameter> list = new ArrayList<SortOutParameter>();
        SortWorkInfo[] targetWorkInfos = null;
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

                SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();
                searchKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_UNSTART);
                searchKey.setBatchNo(batchNo);
                if (!StringUtil.isBlank(planDay))
                {
                    searchKey.setPlanDay(planDay);
                }
                searchKey.setConsignorCode(consignorCode);
                searchKey.setItemCode(itemCode);

                if (RftConst.SELECT_FLAG_SELECT.equals(param.getLotSelectionFlag()))
                {
                    searchKey.setPlanLotNo(lotNo);
                }
                searchKey.setOrder(SortWorkInfo.PLAN_DAY, true);
                searchKey.setOrder(SortWorkInfo.SORTING_PLACE, true);
                searchKey.setOrder(SortWorkInfo.CUSTOMER_CODE, true);
                searchKey.setOrder(SortWorkInfo.PLAN_LOT_NO, true);

                finder.open(true);
                finder.searchForUpdate(searchKey, SortWorkInfoFinder.WAIT_SEC_DEFAULT);
                SortOutParameter outParam = null;
                while (finder.hasNext())
                {
                    targetWorkInfos = (SortWorkInfo[])finder.getEntities(100);
                    for (SortWorkInfo targetWorkInfo : targetWorkInfos)
                    {
                        outParam = new SortOutParameter();
                        outParam.setJobNo(targetWorkInfo.getJobNo());
                        outParam.setPlanDay(targetWorkInfo.getPlanDay());
                        outParam.setPlanLotNo(targetWorkInfo.getPlanLotNo());
                        outParam.setCustomerCode(targetWorkInfo.getCustomerCode());
                        outParam.setSortingPlace(targetWorkInfo.getSortingPlace());
                        list.add(outParam);
                    }
                }
                if (!ArrayUtil.isEmpty(targetWorkInfos))
                {
                    break;
                }
            } // end loop.

            SortOutParameter[] outParams = new SortOutParameter[list.size()];
            list.toArray(outParams);

            // 仕分開始不可能データチェックを行います。
            if (ArrayUtil.isEmpty(outParams))
            {
                SortWorkInfo workInfo = new SortWorkInfo();
                workInfo.setPlanDay(planDay);
                workInfo.setBatchNo(batchNo);
                workInfo.setConsignorCode(consignorCode);
                workInfo.setPlanLotNo(lotNo);
                // 仕分開始できない理由を例外で通知します。
                String[] items = scanItemCodeList.toArray(new String[0]);
                checkSortStart(workInfo, items);
            }
            SortWorkInfo[] workInfos = new SortWorkInfo[outParams.length];
            for (int i = 0; i < workInfos.length; i++)
            {
                workInfos[i] = new SortWorkInfo();
                workInfos[i].setJobNo(outParams[i].getJobNo());
                workInfos[i].setPlanDay(outParams[i].getPlanDay());
                workInfos[i].setPlanLotNo(outParams[i].getPlanLotNo());
            }

            // 重複不可項目が重複していた場合、例外が通知されます。
            checkDuplicate(workInfos);

            // 仕分作業開始します。
            WmsUserInfo userInfo = param.getWmsUserInfo();
            userInfo.setHardwareType(SortWorkInfo.HARDWARE_TYPE_RFT);
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
     * 仕分作業完了。<br>
     * パラメータの設定単位キーに該当するデータに対して仕分完了処理を行います。<br>
     * 完了フラグによって確定(完了 or 欠品完了)、キャンセル処理のいずれかを行います。<br>
     * 集約作業完了メソッドはパラメータの配列インデックス分行います。
     * @param params 入力パラメータ(配列)
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字を含む など…)の場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     */
    public void complete(SortInParameter[] params)
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

        // 最初の入力パラメータにより仕分作業情報、TC予定情報、出荷予定情報のロックを行います。
        final String settingUnitKey = params[0].getSettingUnitKey();
        SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();
        searchKey.setSettingUnitKey(settingUnitKey);
        searchKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);
        searchKey.setJoin(SortWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
        searchKey.setJoin(SortWorkInfo.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);
        //        SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());

        SortWorkInfoFinder finder = new SortWorkInfoFinder(getConnection());

        finder.open(true);

        while (finder.hasNext())
        {
            SortWorkInfo[] work = (SortWorkInfo[])finder.getEntities(100);

            if (ArrayUtil.isEmpty(work))
            {
                throw new NotFoundException();
            }
            else
            {
                break;
            }
        }

        // 入力パラメータで指定されたデータの集約作業完了/集約作業キャンセルを行います。
        int lineNo = 1;
        try
        {
            for (SortInParameter param : params)
            {
                final String completionFlag = param.getCompletionFlag();
                if (SortInParameter.COMPLETION_FLAG_DECISION.equals(completionFlag))
                {
                    completeCollectData(param);
                }
                else if (SortInParameter.COMPLETION_FLAG_UNSTART.equals(completionFlag))
                {
                    completeCancelData(param);
                }
                lineNo++;
            }
        }
        catch (OperatorException e)
        {
            e.setErrorLineNo(lineNo);
            throw e;
        }
    }

    /**
     * 仕分作業キャンセル。<br>
     * パラメータの設定単位キーに該当するデータに対して一括して仕分キャンセル処理を行います。
     * @param param 入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void cancel(SortInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException
    {

        SortWorkInfoFinder finder = new SortWorkInfoFinder(getConnection());
        try
        {
            final String settingUnitKey = param.getSettingUnitKey();
            SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(SortWorkInfo.PLAN_UKEY, CrossDockPlan.PLAN_UKEY);
            SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());

            finder.open(true);

            finder.searchForUpdate(searchKey, SortWorkInfoFinder.WAIT_SEC_DEFAULT);
            while (finder.hasNext())
            {
                SortWorkInfo[] workInfos = (SortWorkInfo[])finder.getEntities(100);

                if (ArrayUtil.isEmpty(workInfos))
                {
                    throw new NotFoundException();
                }
            }
            SortWorkInfoAlterKey alterKey = createCancelKey();
            alterKey.setSettingUnitKey(settingUnitKey);
            alterKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);
            handler.modify(alterKey);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 開始不可能データチェック。<br>
     * パラメータの項目にて仕分作業情報の検索を行い、仕分開始データの状態をチェックします。<br>
     * 削除データを除いて、未作業が存在する場合は他端末で更新されたエラーコード、
     * 作業中が存在する場合は他端末作業中のエラーコード、全て完了の場合は作業完了済みのエラーコードで
     * OperatorExceptionをスローします。<br>
     * 該当データが存在しない場合、または全て削除データの場合はNotFoundExceptionをスローします。
     * @param tgt 対象データ
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void checkSortStart(SortWorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();
        final String planDay = tgt.getPlanDay();
        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        searchKey.setBatchNo(tgt.getBatchNo());
        searchKey.setConsignorCode(tgt.getConsignorCode());
        searchKey.setItemCode(items, true);
        final String planLotNo = tgt.getPlanLotNo();
        if (!StringUtil.isBlank(planLotNo))
        {
            searchKey.setPlanLotNo(planLotNo);
        }

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        searchKey.setCollect(SortWorkInfo.STATUS_FLAG, "MIN", minStatusField);
        searchKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_DELETE, "!=");

        SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());
        SortWorkInfo[] workInfos = (SortWorkInfo[])handler.find(searchKey);
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
        if (SortWorkInfo.STATUS_FLAG_UNSTART.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_UPDATED;
        }
        else if (SortWorkInfo.STATUS_FLAG_NOWWORKING.equals(minStatus))
        {
            errCode = OperatorException.ERR_WORKING_INPROGRESS;
        }
        else if (SortWorkInfo.STATUS_FLAG_COMPLETION.equals(minStatus))
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
     * 仕分作業開始。<br>
     * 仕分作業情報を派rメータとし、集約して開始処理を行います。<br>
     * 設定単位キーを採番して更新し、戻り値として返します。<br>
     * 仕分場所、出荷先コード、予定ロットNo.単位で集約作業Noを採番して更新処理を行います。<br>
     * パラメータの配列インデックス分作業情報開始処理を行います。
     * @param maxCollect 集約作業No.最大件数(件数無制限(-1)を渡された場合は無制限とします。)
     * @param works 仕分作業情報エンティティ(配列)
     * @param ui WMSユーザ情報
     * @return 出力パラメータ
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     */
    protected SortOutParameter start(int maxCollect, SortOutParameter[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        try
        {
            SortOutParameter outParam = new SortOutParameter();
            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
            final String settingUnitKey = seqHandler.nextSortSetUkey();
            outParam.setSettingUnitKey(settingUnitKey);

            SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());

            String collectJobNo = "";
            int collectJobNoCount = 0;
            for (SortOutParameter workInfo : works)
            {
                // 集約作業Noを採番するか決定します。
                boolean ret = outParam.setKeys(workInfo);
                if (!ret)
                {
                    collectJobNo = seqHandler.nextSortCollectJobNo();
                    collectJobNoCount++;
                    if (WmsParam.MAX_RFT_JOBCOLLECT_UNLIMITED != maxCollect && collectJobNoCount > maxCollect)
                    {
                        break;
                    }
                    outParam.setCollectJobNo(collectJobNo);
                }
                // 仕分作業情報の更新を行います。
                SortWorkInfoAlterKey alterKey = new SortWorkInfoAlterKey();
                alterKey.updateSettingUnitKey(settingUnitKey);
                alterKey.updateCollectJobNo(collectJobNo);
                alterKey.updateStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);
                alterKey.updateUserId(ui.getUserId());
                alterKey.updateHardwareType(ui.getHardwareType());
                alterKey.updateTerminalNo(ui.getTerminalNo());
                alterKey.updateLastUpdatePname(getCallerName());

                alterKey.setJobNo(workInfo.getJobNo());

                handler.modify(alterKey);
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
     * パラメータの集約作業No.に該当するデータに対して、仕分作業情報を検索し完了処理を行います。<br>
     * パラメータの実績数、作業秒数を分配し、TC予定情報、出荷予定情報、仕分作業情報を更新します。<br>
     * 完了作業情報検索で取得した件数分ループして処理を行います。<br>
     * 予定一意キーが変わった場合のみ予定情報の更新を行います。
     * @param param 仕分入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeCollectData(SortInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // 仕分作業情報の検索を行います。
            SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();
            searchKey.setCollectJobNo(param.getCollectJobNo());
            searchKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setOrder(SortWorkInfo.CONSIGNOR_CODE, true);
            searchKey.setOrder(SortWorkInfo.SHIP_TICKET_NO, true);
            searchKey.setOrder(SortWorkInfo.SHIP_LINE_NO, true);
            searchKey.setOrder(SortWorkInfo.JOB_NO, true);
            SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());
            SortWorkInfo[] workInfos = (SortWorkInfo[])handler.find(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            int remResult = param.getResultQty();
            WarenaviSystemController wmsController = new WarenaviSystemController(getConnection(), getCaller());
            final String workDay = wmsController.getWorkDay();
            final WmsUserInfo paramUserInfo = param.getWmsUserInfo();

            int resultQty = 0;
            int shortageQty = 0;
            for (SortWorkInfo workInfo : workInfos)
            {
                // 実績数、欠品数の分配を行います。
                int planQty = workInfo.getPlanQty();
                if (remResult < planQty)
                {
                    resultQty = remResult;
                    shortageQty = planQty - resultQty;
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
                SortWorkInfo resultWorkInfo = new SortWorkInfo();
                resultWorkInfo.setResultQty(resultQty);
                resultWorkInfo.setShortageQty(shortageQty);
                resultWorkInfo.setResultLotNo(workInfo.getPlanLotNo()); // 2008-05-13 result -> plan
                resultWorkInfo.setWorkDay(workDay);
                resultWorkInfo.setWorkSecond(workSeconds);
                resultWorkInfo.setHardwareType(workInfo.getHardwareType());
                completeSort(workInfo, resultWorkInfo, paramUserInfo);
            } // end workInfo loop.
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 集約作業キャンセル。<br>
     * パラメータの集約作業No.に該当するデータに対して、仕分作業情報キャンセル処理を行います。
     * @param param 仕分入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    protected void completeCancelData(SortInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        try
        {
            SortWorkInfoAlterKey alterKey = createCancelKey();
            alterKey.setCollectJobNo(param.getCollectJobNo());
            alterKey.setStatusFlag(SortWorkInfo.STATUS_FLAG_NOWWORKING);

            SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());
            handler.modify(alterKey);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 重複チェック。<br>
     * パラメータの仕分作業情報の重複不可項目が重複していないかチェックします。<br>
     * 重複している場合は、エラーコードと重複しているデータを通知します。
     * @param works 仕分作業情報エンティティ(配列)
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    protected void checkDuplicate(SortWorkInfo[] works)
            throws OperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, SortWorkInfo.PLAN_DAY);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);

            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, SortWorkInfo.PLAN_LOT_NO);
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
    protected SortWorkInfoAlterKey createCancelKey()
    {
        SortWorkInfoAlterKey alterKey = new SortWorkInfoAlterKey();
        alterKey.updateSettingUnitKey("");
        alterKey.updateCollectJobNo("");
        alterKey.updateStatusFlag(SortWorkInfo.STATUS_FLAG_UNSTART);
        alterKey.updateHardwareType(SortWorkInfo.HARDWARE_TYPE_UNSTART);
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
        return "$Id: SortOperator.java,v 1.1.1.1 2009/02/10 08:55:48 arai Exp $";
    }
}
