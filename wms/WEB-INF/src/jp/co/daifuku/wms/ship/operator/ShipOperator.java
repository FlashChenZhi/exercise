// $Id: ShipOperator.java,v 1.1.1.1 2009/02/10 08:55:47 arai Exp $
package jp.co.daifuku.wms.ship.operator;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.ShipWorkInfoController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.crossdock.operator.AbstractTcOperator;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.ship.schedule.ShipInParameter;
import jp.co.daifuku.wms.ship.schedule.ShipOutParameter;

/**
 * 出荷作業を行うためのオペレータクラス。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:47 $
 * @author  Softecs
 * @author  Last commit: $Author: arai $
 */

public class ShipOperator
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
    public ShipOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * RFT出荷検品作業開始(出荷先単位)。<br>
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * RFTでの出荷作業開始に必要なパラメータをセットして出荷検品開始ロック処理を行います。
     * 重複不可項目が重複していた場合はOperatorExceptionをスローします。<br>
     * 出荷検品開始ロック処理で検索した出荷作業情報をパラメータとして出荷検品作業開始メソッドを呼び出します。
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws NotFoundException 該当するデータが見当たらない場合にスローされます。
     * @throws DuplicateOperatorException 重複するデータがあった場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public OutParameter rftStartByCustomer(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {
        // 出荷開始対象データを取得します。
        final String planDay = param.getPlanDate();
        final String batchNo = param.getBatchNo();
        final String consignorCode = param.getConsignorCode();
        final String customerCode = param.getCustomerCode();

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "!=");
        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        searchKey.setBatchNo(batchNo);
        searchKey.setConsignorCode(consignorCode);
        searchKey.setCustomerCode(customerCode);
        searchKey.setOrder(ShipWorkInfo.PLAN_DAY, true);
        searchKey.setOrder(ShipWorkInfo.ITEM_CODE, true);
        searchKey.setOrder(ShipWorkInfo.PLAN_LOT_NO, true);

        ShipWorkInfo[] targetWorkInfos = null;
        ArrayList<ShipOutParameter> list = new ArrayList<ShipOutParameter>();
        ShipOutParameter outParam = null;

        try
        {
            finder.open(true);

            finder.searchForUpdate(searchKey, ShipWorkInfoFinder.WAIT_SEC_NOWAIT);

            while (finder.hasNext())
            {

                targetWorkInfos = (ShipWorkInfo[])finder.getEntities(100);
                for (ShipWorkInfo targetWorkInfo : targetWorkInfos)
                {
                    outParam = new ShipOutParameter();
                    outParam.setPlanDay(targetWorkInfo.getPlanDay());
                    outParam.setPlanLotNo(targetWorkInfo.getPlanLotNo());
                    outParam.setItemCode(targetWorkInfo.getItemCode());
                    outParam.setJobNo(targetWorkInfo.getJobNo());
                    outParam.setPlanUkey(targetWorkInfo.getPlanUkey());
                    list.add(outParam);
                }
            }
            ShipOutParameter[] outParams = new ShipOutParameter[list.size()];
            list.toArray(outParams);

            if (ArrayUtil.isEmpty(outParams))
            {
                ShipWorkInfo workInfo = new ShipWorkInfo();
                workInfo.setPlanDay(planDay);
                workInfo.setBatchNo(batchNo);
                workInfo.setConsignorCode(consignorCode);
                workInfo.setCustomerCode(customerCode);
                // 出荷開始できない理由を例外で通知します。
                checkShipStart(workInfo);
            }

            ShipWorkInfo[] workInfos = new ShipWorkInfo[outParams.length];
            for (int i = 0; i < workInfos.length; i++)
            {
                workInfos[i] = new ShipWorkInfo();
                workInfos[i].setPlanDay(outParams[i].getPlanDay());
                workInfos[i].setPlanLotNo(outParams[i].getPlanLotNo());
                workInfos[i].setItemCode(outParams[i].getItemCode());
                workInfos[i].setJobNo(outParams[i].getJobNo());
                workInfos[i].setPlanUkey(outParams[i].getPlanUkey());
            }

            // 重複不可項目が重複していた場合、例外が通知されます。
            checkDuplicate(workInfos);

            // 出荷検品作業開始します。
            WmsUserInfo userInfo = param.getWmsUserInfo();
            userInfo.setHardwareType(ShipWorkInfo.HARDWARE_TYPE_RFT);
            final int maxCollect = WmsParam.MAX_RFT_JOBCOLLECT;
            return start(maxCollect, outParams, userInfo);

        }
        finally
        {
            finder.close();
        }
    }

    /**
     * RFT出荷検品作業開始(出荷先単位)。<br>
     * RFTでの出荷作業開始に必要なパラメータをセットして出荷検品開始ロック処理を行います。
     * 重複不可項目が重複していた場合はOperatorExceptionをスローします。<br>
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws NotFoundException 該当するデータが見当たらない場合にスローされます。
     * @throws DuplicateOperatorException 重複するデータがあった場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public OutParameter rftQueryByCustomer(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {
        // 出荷開始対象データを取得します。
        final String planDay = param.getPlanDate();
        final String batchNo = param.getBatchNo();
        final String consignorCode = param.getConsignorCode();
        final String customerCode = param.getCustomerCode();

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "!=");
        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        searchKey.setBatchNo(batchNo);
        searchKey.setConsignorCode(consignorCode);
        searchKey.setCustomerCode(customerCode);
        searchKey.setOrder(ShipWorkInfo.PLAN_DAY, true);
        searchKey.setOrder(ShipWorkInfo.ITEM_CODE, true);
        searchKey.setOrder(ShipWorkInfo.PLAN_LOT_NO, true);

        ShipWorkInfo[] targetWorkInfos = null;
        ArrayList<ShipOutParameter> list = new ArrayList<ShipOutParameter>();
        ShipOutParameter outParam = null;

        try
        {
            finder.open(true);

            finder.searchForUpdate(searchKey, ShipWorkInfoFinder.WAIT_SEC_NOWAIT);

            while (finder.hasNext())
            {

                targetWorkInfos = (ShipWorkInfo[])finder.getEntities(100);
                for (ShipWorkInfo targetWorkInfo : targetWorkInfos)
                {
                    outParam = new ShipOutParameter();
                    outParam.setPlanDay(targetWorkInfo.getPlanDay());
                    outParam.setPlanLotNo(targetWorkInfo.getPlanLotNo());
                    outParam.setItemCode(targetWorkInfo.getItemCode());
                    outParam.setJobNo(targetWorkInfo.getJobNo());
                    outParam.setPlanUkey(targetWorkInfo.getPlanUkey());
                    list.add(outParam);
                }
            }
            ShipOutParameter[] outParams = new ShipOutParameter[list.size()];
            list.toArray(outParams);

            if (ArrayUtil.isEmpty(outParams))
            {
                ShipWorkInfo workInfo = new ShipWorkInfo();
                workInfo.setPlanDay(planDay);
                workInfo.setBatchNo(batchNo);
                workInfo.setConsignorCode(consignorCode);
                workInfo.setCustomerCode(customerCode);
                // 出荷開始できない理由を例外で通知します。
                checkShipStart(workInfo);
            }

            ShipWorkInfo[] workInfos = new ShipWorkInfo[outParams.length];
            for (int i = 0; i < workInfos.length; i++)
            {
                workInfos[i] = new ShipWorkInfo();
                workInfos[i].setPlanDay(outParams[i].getPlanDay());
                workInfos[i].setPlanLotNo(outParams[i].getPlanLotNo());
                workInfos[i].setItemCode(outParams[i].getItemCode());
                workInfos[i].setJobNo(outParams[i].getJobNo());
                workInfos[i].setPlanUkey(outParams[i].getPlanUkey());
            }

            // 重複不可項目が重複していた場合、例外が通知されます。
            checkDuplicate(workInfos);
            return null;

        }
        finally
        {
            finder.close();
        }
    }

    /**
     * RFT出荷積込作業開始。<br>
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * RFTでの出荷作業開始に必要なパラメータをセットして出荷積込開始ロック処理を行います。
     * 重複不可項目が重複していた場合はOperatorExceptionをスローします。<br>
     * 出荷積込開始ロック処理で検索した出荷作業情報をパラメータとして出荷積込開始メソッドを呼び出します。
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws NotFoundException 該当するデータが見当たらない場合にスローされます。
     * @throws DuplicateOperatorException 重複するデータがあった場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public OutParameter rftStartLoad(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {
        // 出荷積込開始対象データを取得します。
        final String planDay = param.getPlanDate();
        final String batchNo = param.getBatchNo();
        final String consignorCode = param.getConsignorCode();
        final String customerCode = param.getCustomerCode();
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "!=");
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        searchKey.setBatchNo(batchNo);
        searchKey.setConsignorCode(consignorCode);
        searchKey.setCustomerCode(customerCode);

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());

        ShipWorkInfo[] targetWorkInfos = null;
        ArrayList<ShipOutParameter> list = new ArrayList<ShipOutParameter>();
        ShipOutParameter outParam = null;
        try
        {
            finder.open(true);
            finder.searchForUpdate(searchKey, ShipWorkInfoFinder.WAIT_SEC_NOWAIT);

            while (finder.hasNext())
            {

                targetWorkInfos = (ShipWorkInfo[])finder.getEntities(100);
                for (ShipWorkInfo targetWorkInfo : targetWorkInfos)
                {

                    outParam = new ShipOutParameter();
                    outParam.setPlanDay(targetWorkInfo.getPlanDay());
                    outParam.setPlanLotNo(targetWorkInfo.getPlanLotNo());
                    outParam.setItemCode(targetWorkInfo.getItemCode());
                    outParam.setJobNo(targetWorkInfo.getJobNo());
                    outParam.setPlanUkey(targetWorkInfo.getPlanUkey());

                    list.add(outParam);
                }
            }
            ShipOutParameter[] outParams = new ShipOutParameter[list.size()];
            list.toArray(outParams);

            if (ArrayUtil.isEmpty(outParams))
            {
                ShipWorkInfo workInfo = new ShipWorkInfo();
                workInfo.setPlanDay(planDay);
                workInfo.setBatchNo(batchNo);
                workInfo.setConsignorCode(consignorCode);
                workInfo.setCustomerCode(customerCode);
                // 出荷積込開始できない理由を例外で通知します。
                checkShipLoadStart(workInfo);
            }
            ShipWorkInfo[] workInfos = new ShipWorkInfo[outParams.length];
            for (int i = 0; i < workInfos.length; i++)
            {
                workInfos[i] = new ShipWorkInfo();
                workInfos[i].setPlanDay(outParams[i].getPlanDay());
                workInfos[i].setPlanLotNo(outParams[i].getPlanLotNo());
                workInfos[i].setItemCode(outParams[i].getItemCode());
                workInfos[i].setJobNo(outParams[i].getJobNo());
                workInfos[i].setPlanUkey(outParams[i].getPlanUkey());

            }
            // 重複不可項目が重複していた場合、例外が通知されます。
            checkDuplicateLoad(workInfos);

            // 出荷検品作業開始します。
            WmsUserInfo userInfo = param.getWmsUserInfo();
            userInfo.setHardwareType(ShipWorkInfo.HARDWARE_TYPE_RFT);
            return startLoad(outParams, userInfo);
        }
        finally
        {
            finder.close();
        }

    }

    /**
     * 出荷検品作業完了。<br>
     * パラメータの設定単位キーに該当するデータに対して出荷検品完了処理を行います。
     * 完了フラグによって確定(完了 or 欠品完了)、保留処理のいずれかを行います。<br>
     * 完了処理はパラメータの配列インデックス分行います。
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
    public void complete(ShipInParameter[] params)
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

        // 最初の入力パラメータにより出荷作業情報、出荷予定情報のロックを行います。
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();

        searchKey.setSettingUnitKey(params[0].getSettingUnitKey());

        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
        searchKey.setJoin(ShipWorkInfo.PLAN_UKEY, ShipPlan.PLAN_UKEY);

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());

        try
        {

            finder.open(true);

            while (finder.hasNext())
            {

                ShipWorkInfo[] workInfos = (ShipWorkInfo[])finder.getEntities(100);

                if (ArrayUtil.isEmpty(workInfos))
                {
                    throw new NotFoundException();
                }
                else
                {
                    break;
                }
            }
            // 入力パラメータで指定されたデータの出荷検品集約作業完了を行います。
            int lineNo = 1;
            try
            {
                for (ShipInParameter param : params)
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
     * 出荷積込作業完了。<br>
     * パラメータの設定単位キーに該当するデータに対して出荷積込完了処理を行います。
     * @param param 入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     */
    public void completeLoad(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        try
        {
            // 出荷作業情報、出荷予定情報のロックを行います。
            final String settingUnitKey = param.getSettingUnitKey();
            ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(ShipWorkInfo.PLAN_UKEY, ShipPlan.PLAN_UKEY);
            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            ShipWorkInfo[] workInfos = (ShipWorkInfo[])handler.findForUpdate(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            // 出荷積込作業完了対象となる出荷作業情報の検索を行います。
            searchKey.clear();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            workInfos = (ShipWorkInfo[])handler.find(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            ShipPlanHandler planHandler = new ShipPlanHandler(getConnection());
            ShipWorkInfoController workInfoController = new ShipWorkInfoController(getConnection(), getCaller());
            WarenaviSystemController wmsController = new WarenaviSystemController(getConnection(), getCaller());
            final String workDay = wmsController.getWorkDay();
            final WmsUserInfo userInfo = param.getWmsUserInfo();
            final int workSeconds = (int)Math.round((double)param.getWorkSeconds() / (double)workInfos.length);

            for (ShipWorkInfo workInfo : workInfos)
            {
                // 積込完了を行います。
                ShipWorkInfo compWorkInfo = new ShipWorkInfo();
                compWorkInfo.setResultBerth(param.getBerthNo());
                compWorkInfo.setWorkDay(workDay);
                compWorkInfo.setBerthWorkSecond(workSeconds);
                workInfoController.completeLoad(workInfo, compWorkInfo, userInfo);

                // 出荷予定情報を更新します。
                ShipPlanAlterKey planAlterKey = new ShipPlanAlterKey();
                String berthStatus = getBerthStatus(workInfo.getCrossDockUkey());
                planAlterKey.updateBerthStatusFlag(berthStatus);
                planAlterKey.updateLastUpdatePname(getCallerName());
                planAlterKey.updateWorkDay(workDay);
                planAlterKey.setPlanUkey(workInfo.getPlanUkey());
                planHandler.modify(planAlterKey);
            } // end workInfo loop.
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出荷積込作業完了。<BR>
     * パラメータの項目に該当するデータに対して出荷積込完了処理を行います。<BR>
     *
     * @param param 出荷パラメータオブジェクト
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
    public ShipOutParameter webCompleteShipLoad(ShipInParameter param)
            throws CommonException
    {
        // 返却用のSortOutParameter
        ShipOutParameter outParam = new ShipOutParameter();

        // Finder
        ShipWorkInfoFinder finder = null;

        try
        {

            // ::: 対象の仕分作業情報、TC予定情報、出荷予定情報データのロック

            finder = new ShipWorkInfoFinder(getConnection());
            ShipWorkInfoSearchKey winfoSkey = new ShipWorkInfoSearchKey();

            finder.open(true);

            // 検索条件
            // 荷主コード
            winfoSkey.setConsignorCode(param.getConsignorCode());
            // 出荷予定日
            if (!StringUtil.isBlank(param.getPlanDay()))
            {
                winfoSkey.setPlanDay(param.getPlanDay());
            }
            // 出荷先コード
            if (!StringUtil.isBlank(param.getCustomerCode()))
            {
                winfoSkey.setCustomerCode(param.getCustomerCode());
            }
            // 伝票No.
            if (!StringUtil.isBlank(param.getTicketNo()))
            {
                winfoSkey.setShipTicketNo(param.getTicketNo());
            }
            // バッチNo.
            if (!StringUtil.isBlank(param.getBatchNo()))
            {
                winfoSkey.setBatchNo(param.getBatchNo());
            }
            // 検品済のみ
            if (param.getInspectionFlag())
            {
                winfoSkey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
            }
            else
            {
                winfoSkey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "!=");
            }
            winfoSkey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

            // DFKLOOK 3.5 ADD START
            winfoSkey.setJoin(ShipWorkInfo.CONSIGNOR_CODE, Customer.CONSIGNOR_CODE);
            // DFKLOOK 3.5 ADD END

            winfoSkey.setJoin(ShipWorkInfo.CUSTOMER_CODE, Customer.CUSTOMER_CODE);

            // ロック
            int count = finder.searchForUpdate(winfoSkey, ShipWorkInfoFinder.WAIT_SEC_DEFAULT);

            // ロック件数0件のとき
            if (count == 0)
            {
                throw new NotFoundException();
            }

            // 作業日の取得
            WarenaviSystemController wscon = new WarenaviSystemController(getConnection(), getCaller());
            String workDay = wscon.getWorkDay();

            // 設定単位キーの取得
            String settingUkey = this.getSeqHandler().nextShipSetUkey();
            outParam.setSettingUnitKey(settingUkey);

            // ロックした行データを元に更新処理を行う
            while (finder.hasNext())
            {
                ShipWorkInfo[] workInfos = (ShipWorkInfo[])finder.getEntities(100);

                for (ShipWorkInfo workInfo : workInfos)
                {
                    // 完了情報の作成
                    ShipWorkInfo resultWorkInfo = new ShipWorkInfo();

                    // 設定単位キー
                    resultWorkInfo.setSettingUnitKey(settingUkey);
                    // ハードウェア区分 ： 1：リスト
                    resultWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
                    // 結果バース
                    resultWorkInfo.setResultBerth(param.getBerthNo());
                    // 作業日 ： 3で取得した作業日
                    resultWorkInfo.setWorkDay(workDay);
                    // 作業秒数 ： 0
                    resultWorkInfo.setBerthWorkSecond(0);

                    this.completeShipLoad(workInfo, resultWorkInfo, param.getWmsUserInfo());
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
     * 出荷検品作業キャンセル。<br>
     * パラメータの設定単位キーに該当するデータに対して一括して出荷検品キャンセル処理を行います。
     * @param param 入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void cancel(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException
    {

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());
        try
        {
            final String settingUnitKey = param.getSettingUnitKey();
            ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(ShipWorkInfo.PLAN_UKEY, ShipPlan.PLAN_UKEY);
            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());

            finder.open(true);
            finder.searchForUpdate(searchKey, ShipWorkInfoFinder.WAIT_SEC_NOWAIT);

            ArrayList<ShipOutParameter> list = new ArrayList<ShipOutParameter>();
            ShipOutParameter outParam = null;
            while (finder.hasNext())
            {
                ShipWorkInfo[] works = (ShipWorkInfo[])finder.getEntities(100);
                for (ShipWorkInfo work : works)
                {
                    outParam = new ShipOutParameter();
                    outParam.setPlanUkey(work.getPlanUkey());
                    outParam.setCrossDockUkey(work.getCrossDockUkey());
                    outParam.setBerthStatusFlag(work.getBerthStatusFlag());
                    list.add(outParam);
                }
            }
            ShipOutParameter[] outParams = new ShipOutParameter[list.size()];
            list.toArray(outParams);

            if (ArrayUtil.isEmpty(outParams))
            {
                throw new NotFoundException();
            }

            // 出荷作業情報を更新します。
            ShipWorkInfoAlterKey alterKey = createCancelKey();
            alterKey.setSettingUnitKey(settingUnitKey);
            alterKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(outParams[0].getBerthStatusFlag())
                    || ShipWorkInfo.STATUS_FLAG_DELETE.equals(outParams[0].getBerthStatusFlag()))
            {
                alterKey.updateHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
            }
            handler.modify(alterKey);
            try
            {
                alterKey.clear();
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setSettingUnitKey(settingUnitKey);
                alterKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
                handler.modify(alterKey);
            }
            catch (NotFoundException e)
            {
                // ignore no work info
            }

            // 出荷予定情報を更新します。
            Set<String> ukeySet = new HashSet<String>();
            for (ShipOutParameter tgtWorkInfo : outParams)
            {
                String planUkey = tgtWorkInfo.getPlanUkey();
                if (ukeySet.contains(planUkey))
                {
                    // 該当データ更新済みのためスキップ
                    continue;
                }
                ukeySet.add(planUkey);

                ShipPlanAlterKey planAlterKey = new ShipPlanAlterKey();
                String shipStatus = getShipInspectionStatus(tgtWorkInfo.getCrossDockUkey());
                planAlterKey.updateWorkStatusFlag(shipStatus);
                planAlterKey.updateLastUpdatePname(getCallerName());
                planAlterKey.setPlanUkey(planUkey);
                ShipPlanHandler planHandler = new ShipPlanHandler(getConnection());
                planHandler.modify(planAlterKey);
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
     * 出荷積込作業キャンセル。<br>
     * パラメータの設定単位キーに該当するデータに対して一括して出荷積込キャンセル処理を行います。
     * @param param 入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void cancelLoad(ShipInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException
    {

        ShipWorkInfoFinder finder = new ShipWorkInfoFinder(getConnection());
        try
        {
            final String settingUnitKey = param.getSettingUnitKey();
            ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
            searchKey.setSettingUnitKey(settingUnitKey);
            searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setJoin(ShipWorkInfo.PLAN_UKEY, ShipPlan.PLAN_UKEY);
            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            finder.open(true);
            finder.searchForUpdate(searchKey, ShipWorkInfoFinder.WAIT_SEC_NOWAIT);
            ShipWorkInfo[] workInfos = (ShipWorkInfo[])finder.getEntities(1);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            ShipWorkInfo tgtWorkInfo = workInfos[0];

            // 出荷作業情報を更新します。
            ShipWorkInfoAlterKey alterKey = createCancelLoadKey();
            alterKey.setSettingUnitKey(settingUnitKey);
            alterKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(tgtWorkInfo.getWorkStatusFlag())
                    || ShipWorkInfo.STATUS_FLAG_DELETE.equals(tgtWorkInfo.getWorkStatusFlag()))
            {
                alterKey.updateHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
            }
            handler.modify(alterKey);
            try
            {
                alterKey.clear();
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setSettingUnitKey(settingUnitKey);
                alterKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
                handler.modify(alterKey);
            }
            catch (NotFoundException e)
            {
                // ignore no work info
            }

            // 出荷予定情報を更新します。
            ShipPlanAlterKey planAlterKey = new ShipPlanAlterKey();
            String berthStatus = getBerthStatus(tgtWorkInfo.getCrossDockUkey());
            planAlterKey.updateBerthStatusFlag(berthStatus);
            planAlterKey.updateLastUpdatePname(getCallerName());
            planAlterKey.setPlanUkey(tgtWorkInfo.getPlanUkey());
            ShipPlanHandler planHandler = new ShipPlanHandler(getConnection());
            planHandler.modify(planAlterKey);
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
     * 出荷検品開始不可能データチェック。<br>
     * パラメータの項目にて出荷作業情報、出荷予定情報の検索を行い、出荷検品開始データの出荷検品状態をチェックします。<br>
     * 削除データを除いて、作業中が存在する場合は他端末作業中のエラーコード、全て完了の場合は作業完了済みのエラーコードで
     * OperatorExceptionをスローします。<br>
     * 該当データが存在しない場合、または全て削除データの場合はNotfoundexceptionをスローします。
     * @param tgt 対象データ
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void checkShipStart(ShipWorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
        String planDay = tgt.getPlanDay();
        String batNo = tgt.getBatchNo();
        String customerCode = tgt.getCustomerCode();

        if (!StringUtil.isBlank(planDay))
        {
            searchKey.setPlanDay(planDay);
        }
        if (!StringUtil.isBlank(batNo))
        {
            searchKey.setBatchNo(tgt.getBatchNo());
        }
        if (!StringUtil.isBlank(customerCode))
        {
            searchKey.setCustomerCode(tgt.getCustomerCode());
        }
        searchKey.setConsignorCode(tgt.getConsignorCode());

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        searchKey.setCollect(ShipWorkInfo.WORK_STATUS_FLAG, "MIN", minStatusField);
        searchKey.setCollect(ShipWorkInfo.BERTH_STATUS_FLAG);
        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");
        searchKey.setGroup(ShipWorkInfo.BERTH_STATUS_FLAG);

        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfo[] workInfos = (ShipWorkInfo[])handler.find(searchKey);
        if (ArrayUtil.isEmpty(workInfos))
        {
            throw new NotFoundException();
        }

        String errCode = OperatorException.ERR_ALREADY_UPDATED;
        Object minStatus = workInfos[0].getValue(minStatusField);
        if (ShipWorkInfo.STATUS_FLAG_NOWWORKING.equals(minStatus))
        {
            errCode = OperatorException.ERR_WORKING_INPROGRESS;
        }
        else if (ShipWorkInfo.STATUS_FLAG_COMPLETION.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_COMPLETED;
        }
        else if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(minStatus))
        {
            boolean isWorking = false;
            for (ShipWorkInfo workInfo : workInfos)
            {
                if (ShipWorkInfo.STATUS_FLAG_NOWWORKING.equals(workInfo.getBerthStatusFlag()))
                {
                    isWorking = true;
                    errCode = OperatorException.ERR_WORKING_INPROGRESS;
                    break;
                }
            } // end workInfo loop.
            if (!isWorking)
            {
                errCode = OperatorException.ERR_ALREADY_UPDATED;
            }
        }
        throw new OperatorException(errCode);
    }

    /**
     * 出荷積込開始不可能データチェック。<br>
     * パラメータの項目にて出荷作業情報、出荷予定情報の検索を行い、出荷積込開始データのバース登録状態をチェックします。<br>
     * 削除出たーを除いて、作業中が存在する場合は他端末作業中のエラーコード、全て完了の場合は作業完了済みのエラーコードで
     * OperatorExceptionをスローします。<br>
     * 該当データが存在しない場合、または全て削除データの場合はNotfoundexceptionをスローします。
     * @param tgt 対象データ
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 対象作業データがない(他端末で更新された…)場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    public void checkShipLoadStart(ShipWorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();

        if (!StringUtil.isBlank(tgt.getPlanDay()))
        {
            searchKey.setPlanDay(tgt.getPlanDay());
        }
        if (!StringUtil.isBlank(tgt.getBatchNo()))
        {
            searchKey.setBatchNo(tgt.getBatchNo());
        }
        if (!StringUtil.isBlank(tgt.getConsignorCode()))
        {
            searchKey.setConsignorCode(tgt.getConsignorCode());
        }
        if (!StringUtil.isBlank(tgt.getCustomerCode()))
        {
            searchKey.setCustomerCode(tgt.getCustomerCode());
        }
        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        searchKey.setCollect(ShipWorkInfo.BERTH_STATUS_FLAG, "MIN", minStatusField);
        searchKey.setCollect(ShipWorkInfo.WORK_STATUS_FLAG);
        searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");
        searchKey.setGroup(ShipWorkInfo.WORK_STATUS_FLAG);

        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfo[] workInfos = (ShipWorkInfo[])handler.find(searchKey);
        if (ArrayUtil.isEmpty(workInfos))
        {
            throw new NotFoundException();
        }

        String errCode = OperatorException.ERR_ALREADY_UPDATED;
        Object minStatus = workInfos[0].getValue(minStatusField);
        if (ShipWorkInfo.STATUS_FLAG_NOWWORKING.equals(minStatus))
        {
            errCode = OperatorException.ERR_WORKING_INPROGRESS;
        }
        else if (ShipWorkInfo.STATUS_FLAG_COMPLETION.equals(minStatus))
        {
            errCode = OperatorException.ERR_ALREADY_COMPLETED;
        }
        else if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(minStatus))
        {
            boolean isWorking = false;
            for (ShipWorkInfo workInfo : workInfos)
            {
                if (ShipWorkInfo.STATUS_FLAG_NOWWORKING.equals(workInfo.getWorkStatusFlag()))
                {
                    isWorking = true;
                    errCode = OperatorException.ERR_WORKING_INPROGRESS;
                    break;
                }
            } // end workInfo loop.
            if (!isWorking)
            {
                errCode = OperatorException.ERR_ALREADY_UPDATED;
            }
        }
        throw new OperatorException(errCode);
    }

    /**
     * 出荷検品集約作業実績更新。<br>
     * パラメータの集約作業No.に該当するデータに対して、出荷作業情報を検索して出荷検品完了処理を行います。<br>
     * パラメータの実績数、作業秒数を配分して出荷作業情報を更新します。<br>
     * 完了作業情報検索で取得した件数分ループして処理を行います。<br>
     * @param param 出荷入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws IdSchException
     */
    public void updateResult(ShipInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException,
                IdSchException
    {
        try
        {

            /* 禁止文字チェック */
            // ロットNo.
            if (DisplayText.isPatternMatching(param.getResultLotNo()))
            {
                throw new IdSchException(IdSchException.PATTERN_NG);
            }

            // 出荷作業情報コントローラ
            ShipWorkInfoController wkInfoCtrl = new ShipWorkInfoController(getConnection(), getCaller());

            // 出荷作業情報の検索を行います。
            ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
            final String collectJobNo = param.getCollectJobNo();

            searchKey.setCollectJobNo(collectJobNo);

            searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setOrder(ShipWorkInfo.CONSIGNOR_CODE, true);
            searchKey.setOrder(ShipWorkInfo.SHIP_TICKET_NO, true);
            searchKey.setOrder(ShipWorkInfo.SHIP_LINE_NO, true);
            searchKey.setOrder(ShipWorkInfo.JOB_NO, true);
            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            ShipWorkInfo[] workInfos = (ShipWorkInfo[])handler.find(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            int remResult = param.getResultQty();
            WarenaviSystemController wmsController = new WarenaviSystemController(getConnection(), getCaller());
            final String workDay = wmsController.getWorkDay();

            WmsUserInfo paramUserInfo = param.getWmsUserInfo();

            int resultQty = 0;
            for (ShipWorkInfo workInfo : workInfos)
            {
                // 実績数、欠品数の分配を行います。
                int planQty = workInfo.getPlanQty();
                if (remResult < planQty)
                {
                    resultQty = remResult;
                }
                else
                {
                    resultQty = planQty;
                }
                remResult -= resultQty;

                // 作業秒数の分配を行います。
                int workSeconds = param.getWorkSeconds() + workInfo.getShipWorkSecond();
                if (0 < workSeconds)
                {
                    // (workSeconds / paramQty) * resultQty => (workSeconds * resultQty) / paramQty
                    double ws = (double)(workSeconds * resultQty) / (double)param.getResultQty();
                    workSeconds = (int)Math.round(ws);
                }

                ShipWorkInfo compWorkInfo = new ShipWorkInfo();
                compWorkInfo.setPlanQty(planQty);
                compWorkInfo.setResultQty(resultQty);
                compWorkInfo.setResultLotNo(param.getResultLotNo());
                compWorkInfo.setWorkDay(workDay);
                compWorkInfo.setShipWorkSecond(workSeconds);
                // 作業情報更新
                wkInfoCtrl.updateWorkInfo(workInfo, compWorkInfo, paramUserInfo);

            } // end workInfo loop.
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
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
     * 出荷検品作業開始。<br>
     * 出荷作業情報をパラメータとし、集約して出荷検品開始処理を行います。<br>
     * 設定単位キーを採番して更新し、戻り値として返します。<br>
     * 商品コード、予定ロットNo.単位で集約作業Noを採番して更新処理を行います。<br>
     * パラメータの配列インデックス分出荷検品作業情報開始処理を行います。
     * @param maxCollect 集約作業No.最大件数(件数無制限(-1)を渡された場合は無制限とします。)
     * @param works 出荷作業情報エンティティ(配列)
     * @param ui WMSユーザ情報
     * @return 出力パラメータ
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     */
    protected ShipOutParameter start(int maxCollect, ShipOutParameter[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        try
        {
            ShipOutParameter outParam = new ShipOutParameter();
            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
            final String settingUnitKey = seqHandler.nextShipSetUkey();
            outParam.setSettingUnitKey(settingUnitKey);

            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            ShipPlanHandler planHandler = new ShipPlanHandler(getConnection());

            boolean withLimit = WmsParam.MAX_RFT_JOBCOLLECT_UNLIMITED != maxCollect;
            String collectJobNo = "";
            int collectJobNoCount = 0;
            for (ShipOutParameter workInfo : works)
            {
                // 集約作業Noを採番するか決定します。
                boolean ret = outParam.setKeys(workInfo);
                if (!ret)
                {
                    collectJobNo = seqHandler.nextShipCollectJobNo();
                    collectJobNoCount++;
                    boolean limitReached = collectJobNoCount > maxCollect;
                    if (withLimit && limitReached)
                    {
                        break;
                    }
                }

                // 出荷作業情報の更新を行います。
                ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();
                alterKey.updateSettingUnitKey(settingUnitKey);
                alterKey.updateCollectJobNo(collectJobNo);
                alterKey.updateWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
                alterKey.updateHardwareType(ui.getHardwareType());
                alterKey.updateShipUserId(ui.getUserId());
                alterKey.updateShipTerminalNo(ui.getTerminalNo());
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setJobNo(workInfo.getJobNo());
                handler.modify(alterKey);

                // 出荷予定情報の更新を行います。
                ShipPlanAlterKey planAlterKey = new ShipPlanAlterKey();
                planAlterKey.updateWorkStatusFlag(ShipPlan.STATUS_FLAG_NOWWORKING);
                planAlterKey.updateLastUpdatePname(getCallerName());
                planAlterKey.setPlanUkey(workInfo.getPlanUkey());
                planHandler.modify(planAlterKey);
            } // end workInfo loop.

            return outParam;
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出荷積込作業開始。<br>
     * 出荷作業情報をパラメータとし、出荷積込開始処理を行います。<br>
     * 設定単位キーを採番して更新し、戻り値として返します。<br>
     * パラメータの配列インデックス分出荷積込作業情報開始処理を行います。
     * @param works 出荷作業情報エンティティ(配列)
     * @param ui WMSユーザ情報
     * @return 出力パラメータ
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     */
    protected ShipOutParameter startLoad(ShipOutParameter[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        try
        {
            ShipOutParameter outParam = new ShipOutParameter();
            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
            final String collectJobNo = seqHandler.nextShipCollectJobNo();
            final String settingUnitKey = seqHandler.nextShipSetUkey();
            outParam.setSettingUnitKey(settingUnitKey);

            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            ShipPlanHandler planHandler = new ShipPlanHandler(getConnection());

            for (ShipOutParameter workInfo : works)
            {
                // 出荷作業情報の更新を行います。
                ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();
                alterKey.updateSettingUnitKey(settingUnitKey);
                alterKey.updateCollectJobNo(collectJobNo);
                alterKey.updateBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
                alterKey.updateHardwareType(ui.getHardwareType());
                alterKey.updateBerthUserId(ui.getUserId());
                alterKey.updateBerthTerminalNo(ui.getTerminalNo());
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setJobNo(workInfo.getJobNo());
                handler.modify(alterKey);

                // 出荷予定情報の更新を行います。
                ShipPlanAlterKey planAlterKey = new ShipPlanAlterKey();
                planAlterKey.updateBerthStatusFlag(ShipPlan.STATUS_FLAG_NOWWORKING);
                planAlterKey.updateLastUpdatePname(getCallerName());
                planAlterKey.setPlanUkey(workInfo.getPlanUkey());
                planHandler.modify(planAlterKey);
            } // end workInfo loop.

            return outParam;
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出荷検品集約作業完了。<br>
     * パラメータの集約作業No.に該当するデータに対して、出荷作業情報を検索して出荷検品完了処理を行います。<br>
     * パラメータの実績数、作業秒数を配分して出荷予定情報、出荷作業情報を更新します。<br>
     * 完了作業情報検索で取得した件数分ループして処理を行います。<br>
     * 予定一意キーが変わった場合のみ予定情報の更新を行います。
     * @param param 出荷入力パラメータ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeCollectData(ShipInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // 出荷作業情報の検索を行います。
            ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();
            final String collectJobNo = param.getCollectJobNo();

            searchKey.setCollectJobNo(collectJobNo);

            searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            searchKey.setOrder(ShipWorkInfo.CONSIGNOR_CODE, true);
            searchKey.setOrder(ShipWorkInfo.SHIP_TICKET_NO, true);
            searchKey.setOrder(ShipWorkInfo.SHIP_LINE_NO, true);
            searchKey.setOrder(ShipWorkInfo.JOB_NO, true);
            ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
            ShipWorkInfo[] workInfos = (ShipWorkInfo[])handler.find(searchKey);
            if (ArrayUtil.isEmpty(workInfos))
            {
                throw new NotFoundException();
            }

            int remResult = param.getResultQty();
            WarenaviSystemController wmsController = new WarenaviSystemController(getConnection(), getCaller());
            final String workDay = wmsController.getWorkDay();

            WmsUserInfo paramUserInfo = param.getWmsUserInfo();
            final String completionFlag = param.getCompletionFlag();
            boolean isDecisionComp = ShipInParameter.COMPLETION_FLAG_DECISION.equals(completionFlag);

            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());

            int resultQty = 0;
            int shortageQty = 0;
            for (ShipWorkInfo workInfo : workInfos)
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

                int updatedPlanQty = resultQty + shortageQty;
                // 完了処理を行います。
                if (0 < updatedPlanQty)
                {
                    // 実績数＜予定数 でかつ 完了フラグが「保留」の時、分割処理を行います。
                    final int tgtPlanQty = workInfo.getPlanQty();
                    if ((resultQty < tgtPlanQty) && ShipInParameter.COMPLETION_FLAG_REMNANT.equals(completionFlag))
                    {
                        ShipWorkInfo cloneWorkInfo = (ShipWorkInfo)workInfo.clone();
                        cloneWorkInfo.setJobNo(seqHandler.nextShipJobNo());
                        cloneWorkInfo.setSettingUnitKey("");
                        cloneWorkInfo.setCollectJobNo("");
                        cloneWorkInfo.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
                        // CHECK ハードウェア区分をここでリセットしてもいいか？
                        cloneWorkInfo.setHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
                        cloneWorkInfo.setPlanQty(tgtPlanQty - resultQty);
                        cloneWorkInfo.setResultQty(0);
                        cloneWorkInfo.setShortageQty(0);
                        cloneWorkInfo.setShipUserId("");
                        cloneWorkInfo.setShipTerminalNo("");
                        cloneWorkInfo.setRegistPname(getCallerName());
                        cloneWorkInfo.setRegistDate(new SysDate());
                        cloneWorkInfo.setLastUpdatePname(getCallerName());
                        handler.create(cloneWorkInfo);
                    }

                    ShipWorkInfo compWorkInfo = new ShipWorkInfo();
                    compWorkInfo.setPlanQty(updatedPlanQty);
                    compWorkInfo.setResultQty(resultQty);
                    compWorkInfo.setShortageQty(shortageQty);
                    compWorkInfo.setResultLotNo(param.getResultLotNo());
                    compWorkInfo.setWorkDay(workDay);
                    compWorkInfo.setShipWorkSecond(workSeconds);
                    completeShip(workInfo, compWorkInfo, paramUserInfo);

                }
                else
                {
                    // 作業情報の出荷検品キャンセルを行います。
                    ShipWorkInfoAlterKey alterKey = createCancelKey();
                    alterKey.setJobNo(workInfo.getJobNo());
                    alterKey.updateLastUpdatePname(getCallerName());
                    if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(workInfo.getBerthStatusFlag()))
                    {
                        alterKey.updateHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
                    }
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
     * 出荷検品重複チェック。<br>
     * パラメータの出荷作業情報の重複不可項目が重複していないかチェックします。<br>
     * 重複している場合は、エラーコードと重複しているデータを通知します。
     * @param works 出荷作業情報エンティティ(配列)
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    protected void checkDuplicate(ShipWorkInfo[] works)
            throws OperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, ShipWorkInfo.PLAN_DAY);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * 出荷積込重複チェック。<br>
     * パラメータの出荷作業情報の重複不可項目が重複していないかチェックします。<br>
     * 重複している場合は、エラーコードと重複しているデータを通知します。
     * @param works 出荷作業情報エンティティ(配列)
     * @throws OperatorException オペレータ処理でエラーが発生した場合にスローされます。
     */
    protected void checkDuplicateLoad(ShipWorkInfo[] works)
            throws OperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, ShipWorkInfo.PLAN_DAY);
        if (null != dup)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * 出荷検品キャンセル用の更新キーを生成して返します。
     * @return 出荷検品キャンセル用の更新キー
     */
    protected ShipWorkInfoAlterKey createCancelKey()
    {
        ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();
        alterKey.updateSettingUnitKey("");
        alterKey.updateCollectJobNo("");
        alterKey.updateWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
        alterKey.updateShipUserId("");
        alterKey.updateShipTerminalNo("");
        alterKey.updateLastUpdatePname(getCallerName());
        alterKey.updateResultQty(0);
        alterKey.updateResultLotNo("");
        alterKey.updateShipWorkSecond(0);
        alterKey.updateWorkDay("");
        return alterKey;
    }

    /**
     * 出荷積込キャンセル用の更新キーを生成して返します。
     * @return 出荷積込キャンセル用の更新キー
     */
    protected ShipWorkInfoAlterKey createCancelLoadKey()
    {
        ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();
        alterKey.updateSettingUnitKey("");
        alterKey.updateCollectJobNo("");
        alterKey.updateBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
        alterKey.updateBerthUserId("");
        alterKey.updateBerthTerminalNo("");
        alterKey.updateLastUpdatePname(getCallerName());
        return alterKey;
    }

    /**
     * 出荷検品の予定情報状態取得を行います。<br>
     *
     * @param ukey クロスドック連携キー
     * @return 出荷検品の予定情報状態
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象データなし
     * @see AbstractTcOperator#getShipPlanStatus(java.lang.String)
     */
    protected String getShipInspectionStatus(String ukey)
            throws ReadWriteException,
                NotFoundException
    {
        final String[] statusFlag = super.getShipPlanStatus(ukey);
        return statusFlag[0];
    }

    /**
     * バース登録状態取得を行います。<br>
     *
     * @param ukey クロスドック連携キー
     * @return バース登録状態
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象データなし
     * @see AbstractTcOperator#getShipPlanStatus(java.lang.String)
     */
    protected String getBerthStatus(String ukey)
            throws ReadWriteException,
                NotFoundException
    {
        final String[] statusFlag = super.getShipPlanStatus(ukey);
        return statusFlag[1];
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
        return "$Id: ShipOperator.java,v 1.1.1.1 2009/02/10 08:55:47 arai Exp $";
    }
}
