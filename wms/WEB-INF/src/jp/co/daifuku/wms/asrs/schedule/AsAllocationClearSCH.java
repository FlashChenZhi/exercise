// $Id: AsAllocationClearSCH.java 8006 2011-09-02 03:52:18Z ota $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.dasch.AsAllocationClearDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsAllocationClearDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.DeleteStockListParams;
import jp.co.daifuku.wms.asrs.exporter.FaDeleteStockListParams;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DeleteStockHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.DeleteStock;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;

/**
 * 搬送データクリアのスケジュール処理を行います。
 *
 * @version $Revision: 8006 $, $Date:: 2011-09-02 12:52:18 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsAllocationClearSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    // 印刷用
    private Date _startDate = null;

    // ログ出力用
    protected RandomAcsFileHandler _rTrHandler = null;

    // 出力ファイル名
    protected String _trName = "allocateClea.txt";

    // ログファイルの為のパラメータ
    protected static Object[] _trcParam = new Object[1];

    // 区切り文字
    protected static String _SEPARAT = ".";


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AsAllocationClearSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。<BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 更新フラグ
        boolean isAllocationFlag = false;

        try
        {
            // 開始チェック(オンライン中、日次更新中、･･･)
            if (!startCheck())
            {
                return false;
            }

            // 搬送データクリアフラグ更新
            if (!doAllocationClearStart())
            {
                return false;
            }
            doCommit(this.getClass());
            // この処理内でフラグを更新
            isAllocationFlag = true;

            // 日付を保持
            _startDate = DbDateUtil.getTimeStamp();

            // 搬送データ一覧
            CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
            CarryInfoSearchKey sKey = new CarryInfoSearchKey();

            // 搬送情報検索
            CarryInfo[] carryInfoList = (CarryInfo[])cHandler.find(createSearchKey(sKey));

            // 搬送データからデータクリアする関連データをクリア処理
            for (CarryInfo carryInfo : carryInfoList)
            {
                // 入庫
                if (SystemDefine.WORK_TYPE_STORAGE.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeStorage(carryInfo);
                }
                // 予定外入庫
                else if (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeNoPlanStorage(carryInfo);
                }
                // 再入庫
                else if (SystemDefine.WORK_TYPE_RESTORING.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeReStoring(carryInfo);
                }
                // 積み増し入庫
                else if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeNoPlanStorageAdd(carryInfo);
                }
                // 予定出庫
                else if (SystemDefine.WORK_TYPE_RETRIEVAL.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeRetrieval(carryInfo);
                }
                // 予定外出庫 在庫確認、補充
                else if (SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL.equals(carryInfo.getWorkType())
                        || SystemDefine.WORK_TYPE_INVENTORYCHECK.equals(carryInfo.getWorkType())
                        || SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT.equals(carryInfo.getWorkType())
                        || SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT.equals(carryInfo.getWorkType()))
                {
                    clearWorkTypeNoPlanRetrieval(carryInfo);
                }
                // 直行
                // 2010/05/11 Y.Osawa UPD ST
                //if (SystemDefine.WORK_TYPE_DIRECT_TRAVEL.equals(carryInfo.getWorkType()))
                else if (SystemDefine.WORK_TYPE_DIRECT_TRAVEL.equals(carryInfo.getWorkType()))
                // 2010/05/11 Y.Osawa UPD ED
                {
                    clearCarryFlagDirectTransfer(carryInfo);
                }
                // 棚間移動
                // 2010/05/11 Y.Osawa UPD ST
                //if (SystemDefine.WORK_TYPE_RACKMOVE_FROM.equals(carryInfo.getWorkType()))
                else if (SystemDefine.WORK_TYPE_RACKMOVE_FROM.equals(carryInfo.getWorkType()))
                // 2010/05/11 Y.Osawa UPD ED
                {
                    clearCarryFlagLocationTransfer(carryInfo);
                }
                // その他搬送データ
                else
                {
                    clearCarryFlagError(carryInfo);
                }
                // 作業表示情報削除
                dropOperationDisplay(carryInfo);
            }

            // 不整合データクリア
            clearMismatchDate();
            // コミット
            doCommit(getClass());


            // 対象データが無かった場合
            if (carryInfoList.length == 0)
            {
                setMessage("6003011");
            }
            // 対象データがあった場合
            else
            {
                //                // 不整合データクリア
                //                clearMismatchDate();
                //                // コミット
                //                doCommit(getClass());

                setMessage("6021030");
                // 帳票出力
                print();
            }

            return true;
        }
        finally
        {
            // この処理内でフラグが更新された場合
            if (isAllocationFlag)
            {
                // 搬送データクリア中フラグを更新
                doAllocationClearEnd();
                doCommit(this.getClass());
            }
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
     * 搬送データクリア処理を開始できるかチェックするメソッドです。
     * システムオンラインチェック
     * 日次更新チェック
     * 搬送データクリア中チェック
     * 予定取込チェック
     * 報告データ作成中チェック
     * 出庫引当中チェック
     * @return boolean true:開始できる false:開始できない
     */
    protected boolean startCheck()
            throws CommonException
    {
        // システムオンラインチェック
        if (!isOnlineCheck())
        {
            return false;
        }

        // 日次更新、搬送クリア中チェック
        if (!canStart())
        {
            return false;
        }

        // 予定データ取込中チェック
        if (isLoadData())
        {
            return false;
        }
        // 報告データ作成中チェック
        if (isReportData())
        {
            return false;
        }

        // 出庫引当中チェック
        if (isRetrievalAllocate())
        {
            return false;
        }
        return true;
    }


    /**
     * システムオンラインかどうかチェックするメソッドです。
     * @return boolean true:システムがオンライン中 false:システムがオフライン中
     * @throws ReadWriteException
     *
     */
    protected boolean isOnlineCheck()
            throws ReadWriteException
    {
        // システムオンラインチェック
        GroupControllerHandler gHandler = new GroupControllerHandler(getConnection());
        GroupControllerSearchKey sKey = new GroupControllerSearchKey();

        sKey.setStatusFlag(SystemDefine.GC_STATUS_ONLINE);

        if (gHandler.count(sKey) >= 1)
        {
            setMessage("6023100");
            return false;
        }
        return true;
    }

    /**
     * 検索条件のセットを行います。
     * @param sKey CarryInfoSearchKey 搬送データクリア条件key
     * @return CarryInfoSearchKey 搬送データクリア条件key
     */
    protected CarryInfoSearchKey createSearchKey(CarryInfoSearchKey sKey)
    {
        // 入庫条件
        sKey.setWorkType(SystemDefine.WORK_TYPE_STORAGE, "=", "", "", false);

        // 予定外入庫条件
        sKey.setWorkType(SystemDefine.WORK_TYPE_NOPLAN_STORAGE, "=", "", "", false);

        // 再入庫条件
        sKey.setWorkType(SystemDefine.WORK_TYPE_RESTORING, "=", "", "", false);

        // 出庫条件
        sKey.setWorkType(SystemDefine.WORK_TYPE_RETRIEVAL, "=", "(", "", true);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_WAIT_RESPONSE, "=", "(((", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_INSTRUCTION, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_PICKUP, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_COMP_RETRIEVAL, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_ARRIVAL, "=", "", ")", true);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_RETRIEVAL, "=", "", ")", false);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_STORAGE, "=", "", "))", false);

        // 予定外出庫条件
        sKey.setWorkType(SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL, "=", "(", "", true);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_WAIT_RESPONSE, "=", "(((", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_INSTRUCTION, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_PICKUP, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_COMP_RETRIEVAL, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_ARRIVAL, "=", "", ")", true);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_RETRIEVAL, "=", "", ")", false);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_STORAGE, "=", "", "))", false);

        // 棚間移動
        sKey.setWorkType(SystemDefine.WORK_TYPE_RACKMOVE_FROM, "=", "", "", false);

        // 直行
        sKey.setWorkType(SystemDefine.WORK_TYPE_DIRECT_TRAVEL, "=", "", "", false);
        // 積み増し入庫
        sKey.setWorkType(SystemDefine.WORK_TYPE_ADD_STORAGE, "=", "", "", false);

        // 在庫確認
        sKey.setWorkType(SystemDefine.WORK_TYPE_INVENTORYCHECK, "=", "(", "", true);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_WAIT_RESPONSE, "=", "(((", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_INSTRUCTION, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_PICKUP, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_COMP_RETRIEVAL, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_ARRIVAL, "=", "", ")", true);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_RETRIEVAL, "=", "", ")", false);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_STORAGE, "=", "", "))", false);

        // 補充
        sKey.setWorkType(SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT, "=", "((", "", false);
        sKey.setWorkType(SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT, "=", "", ")", true);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_WAIT_RESPONSE, "=", "(((", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_INSTRUCTION, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_PICKUP, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_COMP_RETRIEVAL, "=", "", "", false);
        sKey.setCmdStatus(SystemDefine.CMD_STATUS_ARRIVAL, "=", "", ")", true);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_RETRIEVAL, "=", "", ")", false);
        sKey.setCarryFlag(SystemDefine.CARRY_FLAG_STORAGE, "=", "", "))", false);

        // 強制払出し
        sKey.setWorkType(SystemDefine.WORK_TYPE_EXPENDITURE, "=", "", "", false);

        // 搬送データ削除
        sKey.setWorkType(SystemDefine.WORK_TYPE_CARRYINFODELETE, "=", "", "", false);

        // 空出荷
        sKey.setWorkType(SystemDefine.WORK_TYPE_EMPTYRETRIEVAL, "=", "", "", false);

        return sKey;
    }

    /**
     * 作業情報取得するメソッドです。
     * @param carryKey 搬送キー
     */
    protected WorkInfo getWorkInfo(String carryKey, String stockId)
    {
        // 搬送情報に紐付く作業情報取得
        WorkInfoHandler wHandler = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();

        sKey.setSystemConnKey(carryKey);
        if (!StringUtil.isBlank(stockId))
        {
            sKey.setStockId(stockId);
        }
        FieldName[] collects = {
            WorkInfo.WORK_DAY,
            WorkInfo.JOB_NO,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.JOB_TYPE,
            WorkInfo.STATUS_FLAG,
            WorkInfo.HARDWARE_TYPE,
            WorkInfo.PLAN_UKEY,
            WorkInfo.STOCK_ID,
            WorkInfo.SYSTEM_CONN_KEY,
            WorkInfo.PLAN_DAY,
            WorkInfo.CONSIGNOR_CODE,
            WorkInfo.SUPPLIER_CODE,
            WorkInfo.RECEIVE_TICKET_NO,
            WorkInfo.RECEIVE_LINE_NO,
            WorkInfo.RECEIVE_BRANCH_NO,
            WorkInfo.CUSTOMER_CODE,
            WorkInfo.SHIP_TICKET_NO,
            WorkInfo.SHIP_LINE_NO,
            WorkInfo.SHIP_BRANCH_NO,
            WorkInfo.BATCH_NO,
            WorkInfo.ORDER_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOCATION_NO,
            WorkInfo.ITEM_CODE,
            WorkInfo.PLAN_LOT_NO,
            WorkInfo.PLAN_QTY,
            WorkInfo.RESULT_QTY,
            WorkInfo.SHORTAGE_QTY,
            WorkInfo.RESULT_AREA_NO,
            WorkInfo.RESULT_LOCATION_NO,
            WorkInfo.RESULT_LOT_NO,
            WorkInfo.REASON_TYPE,
            WorkInfo.USER_ID,
            WorkInfo.TERMINAL_NO,
            WorkInfo.WORK_SECOND,
        };
        setCollectFields(sKey, collects);

        try
        {
            return (WorkInfo)wHandler.findPrimary(sKey);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 入庫予定情報取得するメソッドです。
     * @param planUkey 予定一意キー
     */
    protected StoragePlan getStoragePlan(String planUkey)
    {
        // 作業情報に紐付く予定情報取得
        StoragePlanHandler stHandler = new StoragePlanHandler(getConnection());
        StoragePlanSearchKey sKey = new StoragePlanSearchKey();

        sKey.setPlanUkey(planUkey);

        try
        {
            return (StoragePlan)stHandler.findPrimary(sKey);
        }
        catch (Exception e)
        {
            return null;
        }
    }


    /**
     * 出庫予定情報を取得するメソッドです。
     * @param planUkey 予定一意キー
     */
    protected RetrievalPlan getRetrievalPlan(String planUkey)
    {
        // 作業情報に紐付く予定情報取得
        RetrievalPlanHandler retHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();

        sKey.setPlanUkey(planUkey);

        try
        {
            return (RetrievalPlan)retHandler.findPrimary(sKey);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 再入庫予定情報取得するメソッドです。
     * @param planUkey 予定一意キー
     */
    protected ReStoringPlan getReStoringPlan(String planUkey)
    {
        // 作業情報に紐付く予定情報取得
        ReStoringPlanHandler rsHandler = new ReStoringPlanHandler(getConnection());
        ReStoringPlanSearchKey sKey = new ReStoringPlanSearchKey();

        sKey.setPlanUkey(planUkey);

        try
        {
            return (ReStoringPlan)rsHandler.findPrimary(sKey);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 作業種別 = 入庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeStorage(CarryInfo carryInfo)
    {
        // パレット情報の検索
        PalletHandler pHandler = new PalletHandler(getConnection());
        PalletSearchKey pKey = new PalletSearchKey();
        // 検索条件
        pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
        pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
        pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
        // 搬送状態 = 引当、開始
        if (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報に紐付く、予定情報を取得
                        StoragePlan stPlan = getStoragePlan(workInfo.getPlanUkey());
                        // 作業情報のキャンセル
                        cancelWorkInfo(workInfo, stPlan);
                        // 予定情報のキャンセル
                        cancelPlanStorage(stPlan, workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }
        }

        // 搬送状態 = 応答待ち
        if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報に紐付く、予定情報を取得
                        StoragePlan stPlan = getStoragePlan(workInfo.getPlanUkey());
                        // 作業情報のキャンセル
                        cancelWorkInfo(workInfo, stPlan);
                        // 予定情報のキャンセル
                        cancelPlanStorage(stPlan, workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }
        }
        // 搬送状態 = 指示済み
        else if (SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_PICKUP.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                carryCompOpe.drop(carryInfo, InOutResult.WORK_TYPE_CARRYINFODELETE, false,
                        CarryCompleteOperator.CARRY_COMPLETE.NORMAL);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }

            try
            {
                // 強制払出しされた在庫をリストに出力する
                HostSendHandler hostHand = new HostSendHandler(getConnection());
                HostSendSearchKey hostSkey = new HostSendSearchKey();

                hostSkey.setSystemConnKey(carryInfo.getCarryKey());
                hostSkey.setJobType(SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE);

                HostSend[] hostSends =  (HostSend[])hostHand.find(hostSkey);

                for (HostSend hostSend : hostSends)
                {
                    createDeleteStockInfo(copyHostSend(hostSend));
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    /**
     * 作業種別 = 予定外入庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeNoPlanStorage(CarryInfo carryInfo)
    {
        // パレット情報を検索
        PalletHandler pHandler = new PalletHandler(getConnection());
        PalletSearchKey pKey = new PalletSearchKey();
        // 更新条件
        pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
        pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
        pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
        // 搬送状態 = 引当　、開始
        if (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus()))
        {
            // パレット情報検索
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報の削除
                        deleteWorkInfo(workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
            }
        }
        // 搬送状態 = 応答待ち
        if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()))
        {

            // パレット情報検索
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報の削除
                        deleteWorkInfo(workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
            }
        }
        // 搬送状態が = 指示済み
        else if (SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_PICKUP.equals(carryInfo.getCmdStatus()))
        {
            // パレット情報検索
            try
            {
                CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                carryCompOpe.drop(carryInfo, InOutResult.WORK_TYPE_CARRYINFODELETE, false,
                        CarryCompleteOperator.CARRY_COMPLETE.NORMAL);
            }
            catch (Exception e)
            {

            }

            try
            {
                // 強制払出しされた在庫をリストに出力する
                HostSendHandler hostHand = new HostSendHandler(getConnection());
                HostSendSearchKey hostSkey = new HostSendSearchKey();

                hostSkey.setSystemConnKey(carryInfo.getCarryKey());
                hostSkey.setJobType(SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE);

                HostSend[] hostSends =  (HostSend[])hostHand.find(hostSkey);

                for (HostSend hostSend : hostSends)
                {
                    createDeleteStockInfo(copyHostSend(hostSend));
                }
            }
            catch (Exception e)
            {

            }




        }
    }

    /**
     * 作業種別 = 再入庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeReStoring(CarryInfo carryInfo)
    {
        // パレット情報の検索
        PalletHandler pHandler = new PalletHandler(getConnection());
        PalletSearchKey pKey = new PalletSearchKey();
        // 検索条件
        pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
        pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
        pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
        // 搬送状態 = 引当、開始
        if (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報に紐付く、予定情報を取得
                        ReStoringPlan rsPlan = getReStoringPlan(workInfo.getPlanUkey());
                        // 作業情報のキャンセル
                        cancelWorkInfo(workInfo, rsPlan);
                        // 予定情報のキャンセル
                        cancelPlanReStoring(rsPlan, workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }
        }

        // 搬送状態 = 応答待ち
        if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                Pallet[] pallets = (Pallet[])pHandler.find(pKey);

                for (Pallet pallet : pallets)
                {
                    // 在庫情報を検索
                    StockHandler sHandler = new StockHandler(getConnection());
                    StockSearchKey sKey = new StockSearchKey();
                    // 更新条件
                    sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                    sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
                    sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

                    sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
                    sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                    // 在庫情報検索
                    Stock[] stocks = (Stock[])sHandler.find(sKey);

                    for (Stock stock : stocks)
                    {
                        // 搬送情報に紐付く、作業情報を取得
                        WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                        // 作業情報に紐付く、予定情報を取得
                        ReStoringPlan rsPlan = getReStoringPlan(workInfo.getPlanUkey());
                        // 作業情報のキャンセル
                        cancelWorkInfo(workInfo, rsPlan);
                        // 予定情報のキャンセル
                        cancelPlanReStoring(rsPlan, workInfo);
                        // 予定在庫の削除
                        deleteStock(stock);
                    }
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }
        }
        // 搬送状態 = 指示済み
        else if (SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus())
                || SystemDefine.CMD_STATUS_PICKUP.equals(carryInfo.getCmdStatus()))
        {
            try
            {
                CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                carryCompOpe.drop(carryInfo, InOutResult.WORK_TYPE_CARRYINFODELETE, false,
                        CarryCompleteOperator.CARRY_COMPLETE.NORMAL);
            }
            catch (Exception e)
            {
                // 不整合データは最終処理で削除
            }

            try
            {
                // 強制払出しされた在庫をリストに出力する
                HostSendHandler hostHand = new HostSendHandler(getConnection());
                HostSendSearchKey hostSkey = new HostSendSearchKey();

                hostSkey.setSystemConnKey(carryInfo.getCarryKey());
                hostSkey.setJobType(SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE);

                HostSend[] hostSends =  (HostSend[])hostHand.find(hostSkey);

                for (HostSend hostSend : hostSends)
                {
                    createDeleteStockInfo(copyHostSend(hostSend));
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    /**
     * 作業種別 = 積み増し入庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeNoPlanStorageAdd(CarryInfo carryInfo)
    {
        try
        {
            // パレット情報を検索
            PalletHandler pHandler = new PalletHandler(getConnection());
            PalletSearchKey pKey = new PalletSearchKey();
            // 更新条件
            pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
            pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            Pallet pallet = new Pallet();
            // パレット情報検索
            pallet = (Pallet)pHandler.findPrimary(pKey);

            // 在庫情報を検索
            StockHandler sHandler = new StockHandler(getConnection());
            StockSearchKey sKey = new StockSearchKey();

            HostSendController hsc = new HostSendController(getConnection(), getClass());

            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

            if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus()) || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus())))
            {
                // 処理対象外
            }

            // 搬送状態 = 応答待ちの対象
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()))
            {
                // 未指示に戻す
                carryCompOpe.cancelRetrieval(carryInfo);
            }
            // 搬送状態 = 指示済みの対象
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus()))
            {
                // 未指示に戻す
                carryCompOpe.cancelRetrieval(carryInfo);
            }
            else if (SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(carryInfo.getCmdStatus()))
            {
                // 詰め合わせ分在庫
                sKey.clear();
                sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                sKey.setAllocationQty(0, ">", "", "", true);
                Stock[] stocks = (Stock[])sHandler.find(sKey);

                // 作業分在庫
                sKey.clear();
                sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                sKey.setPlanQty(0, ">", "", "", true);
                // 在庫情報検索
                Stock[] workStocks = (Stock[])sHandler.find(sKey);

                carryCompOpe.drop(carryInfo, InOutResult.WORK_TYPE_CARRYINFODELETE, false,
                        CarryCompleteOperator.CARRY_COMPLETE.SHORTAGE);

                for (Stock st : stocks)
                {

                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), st.getStockId());

                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }
                    // 削除作業情報を作成
                    createDeleteStockInfo(copyStockInfo(st));
                }

                // 搬送情報の削除
                deleteCarryInfo(carryInfo);

                for (Stock st : workStocks)
                {
                    // 実績送信情報
                    deleteHostSend(getWorkInfo(carryInfo.getCarryKey(), st.getStockId()));
                    // 作業情報を削除
                    deleteWorkInfo(getWorkInfo(carryInfo.getCarryKey(), st.getStockId()));
                }

            }
            else
            {

                // 詰め合わせ分在庫、作業分在庫
                sKey.clear();
                sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
                sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
                Stock[] stocks = (Stock[])sHandler.find(sKey);
                carryCompOpe.drop(carryInfo, InOutResult.WORK_TYPE_CARRYINFODELETE, false,
                        CarryCompleteOperator.CARRY_COMPLETE.NORMAL);

                for (Stock st : stocks)
                {
                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), st.getStockId());

                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }


                    // 削除作業情報を作成
                    createDeleteStockInfo(copyStockInfo(st));
                }

            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 作業種別 = 出庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeRetrieval(CarryInfo carryInfo)
    {
        try
        {
            // パレット情報を検索
            PalletHandler pHandler = new PalletHandler(getConnection());
            PalletSearchKey pKey = new PalletSearchKey();
            // 検索条件
            pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
            pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            // パレット情報検索
            Pallet pallet = new Pallet();;

            pallet = (Pallet)pHandler.findPrimary(pKey);

            // 在庫情報の検索
            StockHandler sHandler = new StockHandler(getConnection());
            StockSearchKey sKey = new StockSearchKey();
            // 検索条件
            sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
            sKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
            sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
            sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());

            HostSendController hsc = new HostSendController(getConnection(), getClass());

            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

            // 在庫情報更新
            // 搬送状態 = 引当、開始の場合
            if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus()) || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus())))
            {
                // 処理対象外
            }
            //  搬送状態 = 応答待ち、指示済みの場合
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()) || SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus())))
            {
                // 未指示に戻す
                carryCompOpe.cancelRetrieval(carryInfo);
            }
            // 搬送状態 = 出庫完了の場合
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(carryInfo.getCmdStatus())))
            {
                // 在庫情報検索
                Stock[] stocks = (Stock[])sHandler.find(sKey);

                Stock[] uMixStocks = null;
                if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(carryInfo.getRetrievalDetail()))
                {
                    // 詰合せ在庫の保持
                    sKey.clear();
                    sKey.setPalletId(pallet.getPalletId());
                    for (Stock stock : stocks)
                    {
                        sKey.setStockId(stock.getStockId(), "!=");
                    }
                    uMixStocks = (Stock[])sHandler.find(sKey);
                }


                try
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carryInfo);
                }
                catch (CommonException e)
                {
                }

                for (Stock stock : stocks)
                {
                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }

                    // 在庫削除ﾘｽﾄ追加
                    workInfo.setStockId(stock.getStockId());
                    createDeleteStockInfo(copyWorkInfo(workInfo, stock.getStorageDate()));

                    createDeleteStockInfo(copyStockInfo(stock));
                }


                // 搬送情報の削除
                deleteCarryInfo(carryInfo);

                // マルチ引当分完了処理
                CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey cKey = new CarryInfoSearchKey();

                cKey.setPalletId(pallet.getPalletId());
                cKey.setCarryKey(carryInfo.getCarryKey(), "!=");
                cKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
                cKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
                cKey.setCarryKeyCollect();
                cKey.setCollect(Stock.STORAGE_DATE);
                cKey.setCollect(Stock.STOCK_ID);

                CarryInfo[] carryInfoList = (CarryInfo[])cHandler.find(cKey);
                for (CarryInfo carry : carryInfoList)
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carry);
                    // 搬送情報に紐付く、作業情報を取得
                    createDeleteStockInfo(copyWorkInfo(getWorkInfo(carry.getCarryKey(),
                            String.valueOf(carry.getValue(Stock.STOCK_ID))), carry.getDate(Stock.STORAGE_DATE)));
                    // 搬送情報の削除
                    deleteCarryInfo(carry);
                }
                // 在庫引き落とし

                for (Stock stock : stocks)
                {
                    dropStock(stock);
                }

                if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(carryInfo.getRetrievalDetail()))
                {
                    // 詰合せ在庫の削除
                    for (Stock st : uMixStocks)
                    {
                        createDeleteStockInfo(copyStockInfo(st));
                        dropStock(st);
                    }
                }
                else
                {
                    // 詰合せ在庫の削除
                    sKey.clear();
                    sKey.setPalletId(stocks[0].getPalletId());
                    Stock[] mixStocks = (Stock[])sHandler.find(sKey);
                    for (Stock st : mixStocks)
                    {
                        createDeleteStockInfo(copyStockInfo(st));
                        dropStock(st);
                    }
                }

                // ステーションのクリア
                clearStation(carryInfo.getCarryKey());
                // 在庫確認チェック
                carryCompOpe.updateInventoryCheckInfo(carryInfo);
            }
            else if (SystemDefine.CARRY_FLAG_STORAGE.equals(carryInfo.getCarryFlag()))
            {
                Stock[] stocks = (Stock[])sHandler.find(sKey);
                for (Stock stock : stocks)
                {
                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());
                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }

                    // 在庫削除ﾘｽﾄ追加
                    createDeleteStockInfo(copyStockInfo(stock));
                }

                // マルチ引当分完了処理
                CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey cKey = new CarryInfoSearchKey();

                cKey.setPalletId(pallet.getPalletId());
                cKey.setCarryKey(carryInfo.getCarryKey(), "!=");
                cKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
                cKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
                cKey.setCarryKeyCollect();
                cKey.setCollect(Stock.STORAGE_DATE);
                cKey.setCollect(Stock.STOCK_ID);

                CarryInfo[] carryInfoList = (CarryInfo[])cHandler.find(cKey);
                for (CarryInfo carry : carryInfoList)
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carry);
                    // 搬送情報に紐付く、作業情報を取得
                    createDeleteStockInfo(copyWorkInfo(getWorkInfo(carry.getCarryKey(),
                            String.valueOf(carry.getValue(Stock.STOCK_ID))), carry.getDate(Stock.STORAGE_DATE)));
                    // 搬送情報の削除
                    deleteCarryInfo(carry);
                }


                for (Stock stock : stocks)
                {
                    // 在庫引き落とし
                    dropStock(stock);
                }


                // 詰合せ在庫の削除
                sKey.clear();
                sKey.setPalletId(stocks[0].getPalletId());
                Stock[] miXStocks = (Stock[])sHandler.find(sKey);
                for (Stock st : miXStocks)
                {
                    createDeleteStockInfo(copyStockInfo(st));
                    dropStock(st);
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);


                // ステーションのクリア
                clearStation(carryInfo.getCarryKey());
                // 在庫確認チェック
                carryCompOpe.updateInventoryCheckInfo(carryInfo);
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 作業種別 = 予定外出庫の処理を行うメソッド
     * @param carryInfo 搬送情報
     */
    protected void clearWorkTypeNoPlanRetrieval(CarryInfo carryInfo)
    {
        try
        {
            // パレット情報を検索
            PalletHandler pHandler = new PalletHandler(getConnection());
            PalletSearchKey pKey = new PalletSearchKey();
            // 検索条件
            pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
            pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            // パレット情報検索
            Pallet pallet = new Pallet();;

            pallet = (Pallet)pHandler.findPrimary(pKey);

            // 在庫情報の検索
            StockHandler sHandler = new StockHandler(getConnection());
            StockSearchKey sKey = new StockSearchKey();
            // 検索条件
            sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
            sKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
            sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
            sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());

            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

            HostSendController hsc = new HostSendController(getConnection(), getClass());

            // 在庫情報更新
            // 搬送状態 = 引当、開始の場合
            if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus()) || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus())))
            {
                // 処理対象外
            }
            //  搬送状態 = 応答待ち、指示済みの場合
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus()) || SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus())))
            {
                // 未指示に戻す
                carryCompOpe.cancelRetrieval(carryInfo);
            }
            // 搬送状態 = 出庫完了の場合
            else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag())
                    && (SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(carryInfo.getCmdStatus())))
            {

                // 在庫情報検索
                Stock[] stocks = (Stock[])sHandler.find(sKey);

                Stock[] uMixStocks = null;
                if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(carryInfo.getRetrievalDetail()))
                {
                    // 詰合せ在庫の保持
                    sKey.clear();
                    sKey.setPalletId(pallet.getPalletId());
                    for (Stock stock : stocks)
                    {
                        sKey.setStockId(stock.getStockId(), "!=");
                    }
                    uMixStocks = (Stock[])sHandler.find(sKey);
                }

                try
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carryInfo);
                }
                catch (CommonException e)
                {
                }

                for (Stock stock : stocks)
                {
                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }

                    // 在庫削除ﾘｽﾄ追加
                    workInfo.setStockId(stock.getStockId());

                    // 在庫(作業分)
                    createDeleteStockInfo(copyWorkInfo(workInfo, stock.getStorageDate()));

                    // 在庫(詰合分)
                    createDeleteStockInfo(copyStockInfo(stock));
                }

                // 搬送情報の削除
                deleteCarryInfo(carryInfo);

                // マルチ引当分完了処理
                CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey cKey = new CarryInfoSearchKey();

                cKey.setPalletId(pallet.getPalletId());
                cKey.setCarryKey(carryInfo.getCarryKey(), "!=");
                cKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
                cKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
                cKey.setCarryKeyCollect();
                cKey.setCollect(Stock.STORAGE_DATE);
                cKey.setCollect(Stock.STOCK_ID);

                CarryInfo[] carryInfoList = (CarryInfo[])cHandler.find(cKey);
                for (CarryInfo carry : carryInfoList)
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carry);
                    // 搬送情報に紐付く、作業情報を取得
                    createDeleteStockInfo(copyWorkInfo(getWorkInfo(carry.getCarryKey(),
                            String.valueOf(carry.getValue(Stock.STOCK_ID))), carry.getDate(Stock.STORAGE_DATE)));
                    // 搬送情報の削除
                    deleteCarryInfo(carry);
                }

                for (Stock stock : stocks)
                {
                    // 在庫引き落とし
                    dropStock(stock);
                }
                if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(carryInfo.getRetrievalDetail()))
                {
                    // 詰合せ在庫の削除
                    for (Stock st : uMixStocks)
                    {
                        createDeleteStockInfo(copyStockInfo(st));
                        dropStock(st);
                    }
                }
                else
                {
                    // 詰合せ在庫の削除
                    sKey.clear();
                    sKey.setPalletId(stocks[0].getPalletId());
                    Stock[] mixStocks = (Stock[])sHandler.find(sKey);
                    for (Stock st : mixStocks)
                    {
                        createDeleteStockInfo(copyStockInfo(st));
                        dropStock(st);
                    }
                }
                // ステーションのクリア
                clearStation(carryInfo.getCarryKey());
                // 在庫確認チェック
                carryCompOpe.updateInventoryCheckInfo(carryInfo);
            }
            else if (SystemDefine.CARRY_FLAG_STORAGE.equals(carryInfo.getCarryFlag()))
            {


                // 在庫情報検索
                Stock[] stocks = (Stock[])sHandler.find(sKey);
                for (Stock stock : stocks)
                {

                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                    try
                    {
                        workInfo.setJobType(SystemDefine.WORK_TYPE_CARRYINFODELETE);
                        hsc.insertByWorkInfo(workInfo, SystemDefine.WORK_TYPE_CARRYINFODELETE);
                    }
                    catch (Exception e)
                    {
                    }
                    // 在庫削除ﾘｽﾄ追加
                    createDeleteStockInfo(copyStockInfo(stock));
                }


                // マルチ引当分完了処理
                CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey cKey = new CarryInfoSearchKey();

                cKey.setPalletId(pallet.getPalletId());
                cKey.setCarryKey(carryInfo.getCarryKey(), "!=");
                cKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
                cKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
                cKey.setCarryKeyCollect();
                cKey.setCollect(Stock.STORAGE_DATE);
                cKey.setCollect(Stock.STOCK_ID);

                CarryInfo[] carryInfoList = (CarryInfo[])cHandler.find(cKey);
                for (CarryInfo carry : carryInfoList)
                {
                    // 完了処理
                    completeRetrieval(getConnection(), carry);
                    // 搬送情報に紐付く、作業情報を取得
                    createDeleteStockInfo(copyWorkInfo(getWorkInfo(carry.getCarryKey(),
                            String.valueOf(carry.getValue(Stock.STOCK_ID))), carry.getDate(Stock.STORAGE_DATE)));
                    // 搬送情報の削除
                    deleteCarryInfo(carry);
                }


                for (Stock stock : stocks)
                {
                    // 在庫引き落とし
                    dropStock(stock);
                }


                // 詰合せ在庫の削除
                sKey.clear();
                sKey.setPalletId(stocks[0].getPalletId());
                Stock[] mixStocks = (Stock[])sHandler.find(sKey);
                for (Stock st : mixStocks)
                {
                    createDeleteStockInfo(copyStockInfo(st));
                    dropStock(st);
                }
                // 搬送情報の削除
                deleteCarryInfo(carryInfo);

                // 在庫確認チェック
                carryCompOpe.updateInventoryCheckInfo(carryInfo);
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 搬送区分 = 直行の処理を行うメソッドです。
     * @param carryInfo 搬送情報
     */
    protected void clearCarryFlagDirectTransfer(CarryInfo carryInfo)
    {
        try
        {
            // パレット情報の検索
            PalletHandler pHandler = new PalletHandler(getConnection());
            PalletSearchKey pKey = new PalletSearchKey();
            // 検索条件
            pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
            pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            // パレット情報検索
            Pallet pallet = new Pallet();

            pallet = (Pallet)pHandler.findPrimary(pKey);

            // 在庫情報の検索
            StockHandler sHandler = new StockHandler(getConnection());
            StockSearchKey sKey = new StockSearchKey();
            // 検索条件
            sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
            sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
            sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
            // 在庫情報検索
            Stock[] stocks = (Stock[])sHandler.find(sKey);
            for (Stock stock : stocks)
            {
                // 搬送情報に紐付く、作業情報を取得
                WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());

                // 作業情報のキャンセル
                deleteWorkInfo(workInfo);
                // 予定在庫の削除
                deleteStock(stock);
            }

            // ステーションのクリア
            clearStation(carryInfo.getCarryKey());
            // 搬送情報の削除
            deleteCarryInfo(carryInfo);
        }
        catch (Exception e)
        {
            // データの不整合は最終処理で削除
        }
    }

    /**
     * 搬送区分 = 棚間移動の処理を行うメソッドです。
     * @param carryInfo 搬送情報
     */
    protected void clearCarryFlagLocationTransfer(CarryInfo carryInfo)
    {
        try
        {
            // パレット情報の検索
            PalletHandler pHandler = new PalletHandler(getConnection());
            PalletSearchKey pKey = new PalletSearchKey();
            // 検索条件
            pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            pKey.setKey(CarryInfo.PALLET_ID, carryInfo.getPalletId());
            pKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            // パレット情報検索
            Pallet pallet = new Pallet();

            pallet = (Pallet)pHandler.findPrimary(pKey);

            // 在庫情報の検索
            StockHandler sHandler = new StockHandler(getConnection());
            StockSearchKey sKey = new StockSearchKey();
            // 検索条件
            sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
            sKey.setJoin(Stock.STOCK_ID, WorkInfo.STOCK_ID);
            sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
            sKey.setKey(CarryInfo.CARRY_KEY, carryInfo.getCarryKey());
            sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());

            // 搬送状態 = 引当、開始、応答待ち、指示済みの場合
            if (SystemDefine.CMD_STATUS_ALLOCATION.equals(carryInfo.getCmdStatus())
                    || SystemDefine.CMD_STATUS_START.equals(carryInfo.getCmdStatus())
                    || SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(carryInfo.getCmdStatus())
                    || SystemDefine.CMD_STATUS_INSTRUCTION.equals(carryInfo.getCmdStatus()))
            {
                ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());
                releaseOpe.allocateClearOfCarryKey(carryInfo);

                // 搬送情報の削除
                deleteCarryInfo(carryInfo);
            }
            else
            {
                // 在庫情報検索
                Stock[] stocks = (Stock[])sHandler.find(sKey);
                for (Stock stock : stocks)
                {
                    // 搬送情報に紐付く、作業情報を取得
                    WorkInfo workInfo = getWorkInfo(carryInfo.getCarryKey(), stock.getStockId());
                    stock.setLocationNo(workInfo.getPlanLocationNo());
                    // 在庫削除ﾘｽﾄ追加
                    createDeleteStockInfo(copyStockInfo(stock));
                }

                // 削除処理
                CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                carryCompOpe.drop(carryInfo, true);
            }
        }
        catch (Exception e)
        {
            // データの不整合は最終処理で削除
        }
    }

    /**
     * その他異常搬送データの削除
     * 空出荷
     * 搬送データ削除
     * 強制払出し
     * @param carryInfo 搬送情報
     */
    protected void clearCarryFlagError(CarryInfo carryInfo)
    {

        try
        {
            // 削除処理
            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
            carryCompOpe.drop(carryInfo, true);
        }
        catch (Exception e)
        {
        }
    }


    /**
     * 出庫完了処理
     * @param conn コネクション
     * @param ci 搬送情報
     * @throws CommonException
     */
    protected void completeRetrieval(Connection conn, CarryInfo ci)
            throws CommonException
    {

    	ci = find(conn ,ci.getCarryKey());

        // 到着ステーションを作成
        Station st = StationFactory.makeStation(conn, ci.getDestStationNo());
        // ステーション別の到着処理を行う。
        StationOperator dop = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());

        // コの字出庫ステーションの場合は作業表示運用に関わらず到着処理を行なう
        if (dop instanceof FreeRetrievalStationOperator)
        {
            FreeRetrievalStationOperator frsop = new FreeRetrievalStationOperator(conn, st.getStationNo());
            frsop.completeByForce(ci, this.getClass());
        }
        else
        {
            dop.arrival(ci, new Pallet(), this.getClass());
        }

        // 到着処理後の搬送情報を再検索
        CarryInfo st_ci = find(getConnection(), ci.getCarryKey());

        if (st_ci != null)
        {
            // 今回の作業がユニット出庫以外で、搬送元が入庫可能ステーションとなった場合、
            // 以下の条件に一致する場合は入庫完了処理まで行なう。
            // 1.パレットの現在位置が入庫可能ステーション
            // 2.ピッキング出庫の戻り入庫作業がAGC側で自動的に行なわれる
            if (!CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(st_ci.getRetrievalDetail()))
            {
                Station frst = StationFactory.makeStation(conn, st_ci.getSourceStationNo());
                if ((Station.STATION_TYPE_IN.equals(frst.getStationType()))
                        || (Station.STATION_TYPE_INOUT.equals(frst.getStationType())))
                {
                    if (Station.RESTORING_INSTRUCTION_AGC_STORAGE_SEND.equals(frst.getRestoringInstruction()))
                    {
                        completeStorage(conn, st_ci);
                    }
                }
            }
        }
    }


    /**
     * 入庫完了処理を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param ci 強制完了対象作業データ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void completeStorage(Connection conn, CarryInfo ci)
            throws CommonException
    {
        // 変数の定義
        CarryInfoHandler carryHandler = new CarryInfoHandler(conn);
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // 処理の開始
        // 行き先ステーションのインスタンス取得
        ShelfHandler slfHandler = new ShelfHandler(conn);
        ShelfSearchKey slfKey = new ShelfSearchKey();
        slfKey.setStationNo(ci.getDestStationNo());
        Shelf toShelf = (Shelf)slfHandler.findPrimary(slfKey);

        // 実際の完了処理
        // 入庫棚の予約を解除し、在荷をONにする。
        ShelfOperator sop = new ShelfOperator(conn, toShelf.getStationNo());
        sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);

        // CarryInfoを削除
        carryKey.setCarryKey(ci.getCarryKey());
        carryHandler.drop(carryKey);

        // パレットの現在地を目的地に変更
        PalletHandler palHandler = new PalletHandler(conn);
        PalletSearchKey palkey = new PalletSearchKey();
        palkey.setPalletId(ci.getPalletId());
        Pallet pl = (Pallet)palHandler.findPrimary(palkey);
        alterPalletCurrentStationNo(conn, pl.getPalletId(), toShelf.getStationNo());
        // パレットに対する他の引当がないか確認
        carryKey.clear();
        carryKey.setPalletId(pl.getPalletId());
        if (carryHandler.count(carryKey) == 0)
        {
            // 商品コードが二重格納用商品コードであれば、異常棚に変更する。
            StockHandler stkHandler = new StockHandler(conn);
            StockSearchKey stkKey = new StockSearchKey();
            stkKey.setPalletId(pl.getPalletId());
            Stock[] stks = (Stock[])stkHandler.find(stkKey);
            if (WmsParam.IRREGULAR_ITEMCODE.equals(stks[0].getItemCode()))
            {
                // 状態を異常棚に変更
                alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_IRREGULAR);
            }
            else
            {
                // 状態を実棚に変更
                alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_REGULAR);
            }
            // 引当フラグを未引当に変更
            alterPalletAllocation(conn, pl.getPalletId(), Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
        }
        else
        {
            // 状態を出庫予約に変更
            alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
        }

        // 実績送信情報の報告フラグを未報告に更新
        HostSendController hsc = new HostSendController(conn, this.getClass());
        hsc.updateReportFlag(ci);
    }


    /**
     * 作業情報を削除するメソッドです
     * @param workInfo 作業情報
     */
    protected void deleteWorkInfo(WorkInfo workInfo)
    {
        WorkInfoHandler wHandler = new WorkInfoHandler(getConnection());
        try
        {
            WorkInfoSearchKey sKey = new WorkInfoSearchKey();
            sKey.setJobNo(workInfo.getJobNo());
            if (wHandler.count(sKey) > 0)
            {
                wHandler.drop(sKey);
            }
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 作業情報を削除するメソッドです
     * @param workInfo 作業情報
     */
    protected void deleteCarryResult(WorkInfo workInfo)
    {
        // 実績送信情報
        deleteHostSend(workInfo);
        // 稼動実績情報
        deleteInOutResult(workInfo);
        // 在庫更新履歴情報
        deleteStockHistory(workInfo);
    }


    /**
     * 実績送信情報を削除するメソッドです
     * @param workInfo 作業情報
     */
    protected void deleteHostSend(WorkInfo workInfo)
    {
        HostSendHandler wHandler = new HostSendHandler(getConnection());
        try
        {
            HostSendSearchKey sKey = new HostSendSearchKey();
            sKey.setSystemConnKey(workInfo.getSystemConnKey());
            if (wHandler.count(sKey) > 0)
            {
                wHandler.drop(sKey);
            }
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 稼動実績情報を削除するメソッドです
     * @param workInfo 作業情報
     */
    protected void deleteInOutResult(WorkInfo workInfo)
    {
        InOutResultHandler wHandler = new InOutResultHandler(getConnection());
        try
        {
            InOutResultSearchKey sKey = new InOutResultSearchKey();
            sKey.setCarryKey(workInfo.getSystemConnKey());
            if (wHandler.count(sKey) > 0)
            {
                wHandler.drop(sKey);
            }
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 在庫更新履歴を削除するメソッドです
     * @param workInfo 作業情報
     */
    protected void deleteStockHistory(WorkInfo workInfo)
    {
        StockHistoryHandler wHandler = new StockHistoryHandler(getConnection());
        try
        {
            StockHistorySearchKey sKey = new StockHistorySearchKey();
            sKey.setStockId(workInfo.getStockId());
            if (wHandler.count(sKey) > 0)
            {
                wHandler.drop(sKey);
            }
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 搬送情報を削除するメソッドです。
     * 搬送情報を削除するに伴い実績送信情報が作成されている場合は、搬送中から未報告更新
     * 搬送キーに紐付くステーションの更新
     * @param carryInfo 搬送情報
     */
    protected void deleteCarryInfo(CarryInfo carryInfo)
    {
        CarryInfoHandler cHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey sKey = new CarryInfoSearchKey();

        try
        {
            sKey.setCarryKey(carryInfo.getCarryKey());
            cHandler.drop(sKey);
        }
        catch (Exception e)
        {
        }
        try
        {
            // 実績送信情報、報告フラグ更新
            HostSendController hostCon = new HostSendController(getConnection(), getClass());
            hostCon.updateReportFlag(carryInfo);
        }
        catch (Exception e)
        {
        }

        // ステーションのクリア
        clearStation(carryInfo.getCarryKey());

    }

    /**
     * 在庫情報を削除するメソッドです。
     * @param stock 在庫情報
     */
    protected void deleteStock(Stock stock)
    {
        StockHandler sHandler = new StockHandler(getConnection());

        try
        {
            sHandler.drop(stock);
        }
        catch (Exception e)
        {
        }

    }

    /**
     * マルチ引当された作業情報を削除するメソッドです。
     * @param stock 在庫情報
     */
    protected void deleteMultiCarry(Pallet pl, String carryKey)
    {
        try
        {
            // 搬送データを削除する
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            CarryInfoHandler ciH = new CarryInfoHandler(getConnection());
            ciKey.clear();
            ciKey.setPalletId(pl.getPalletId());
            ciKey.setCarryKey(carryKey, "!=");

            ciKey.setCarryKeyOrder(true);
            CarryInfo[] cinfos = (CarryInfo[])ciH.find(ciKey);

            for (CarryInfo ci : cinfos)
            {
                // 引当解除処理
                ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());
                releaseOpe.allocateClearOfCarryKey(ci);
                //                WorkInfoHandler wiHnad = new WorkInfoHandler(getConnection());
                //                WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
                //                wiKey.setSystemConnKey(ci.getCarryKey());
                //                wiKey.setPlanUkeyOrder(true);
                //                WorkInfo[] works = (WorkInfo[])wiHnad.find(wiKey);
                //
                //                int numWorks = works.length;
                //                for (int i = 0; i < numWorks; i++)
                //                {
                //                    WorkInfo currWork = works[i];
                //                    // 欠品完了で作業、実績を作成する
                //                    completeWorkinfo(currWork,ci.getSourceStationNo());
                //                    // 紐付く予定情報を更新する
                //                    // 同一予定に紐付く作業情報の更新がすべて処理されてから予定情報の更新を行います。
                //                    if (numWorks > 1)
                //                    {
                //                        if (i == 0)
                //                        {
                //                            continue;
                //                        }
                //                        else
                //                        {
                //                            WorkInfo prevWork = works[i - 1];
                //                            if (!prevWork.getPlanUkey().equals(currWork.getPlanUkey()))
                //                            {
                //                                updateRetrievalPlan(prevWork.getPlanUkey());
                //                            }
                //                        }
                //                    }
                //                    if (i == (numWorks - 1))
                //                    {
                //                        updateRetrievalPlan(currWork.getPlanUkey());
                //                    }
                //
                //                }
                //                // 搬送情報削除
                //                deleteCarryInfo(ci);

            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 予定一意キーに紐付く作業情報から、実績数を取得します。<BR>
     * また、該当する作業情報がない場合、０を返します。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @return 予定一意キーで集約した実績数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     */
    protected int getResultQty(String planUkey)
            throws ReadWriteException
    {
        return getQty(planUkey, WorkInfo.RESULT_QTY);
    }

    /**
     * 予定一意キーに紐付く作業情報から、欠品数を取得します。<BR>
     * また、該当する作業情報がない場合、０を返します。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @return 予定一意キーで集約した欠品数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     */
    protected int getShortageQty(String planUkey)
            throws ReadWriteException
    {
        return getQty(planUkey, WorkInfo.SHORTAGE_QTY);
    }

    /**
     * 作業情報から予定一意キーごとの数量を合計して返します。
     *
     * @param planUkey 予定一意キー
     * @param sumField 数量フィールド(実績数, 欠品数)
     * @return 合計数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @since 2007-05-08
     */
    protected int getQty(String planUkey, FieldName sumField)
            throws ReadWriteException
    {
        WorkInfoHandler workInfoH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        try
        {
            wiKey.setPlanUkey(planUkey);
            wiKey.setCollect(sumField, "SUM", sumField);
            wiKey.setPlanUkeyGroup();

            WorkInfo winfo = (WorkInfo)workInfoH.findPrimary(wiKey);

            // 作業情報がない場合、数量は0を返します。
            if (null == winfo)
            {
                return 0;
            }
            return winfo.getBigDecimal(sumField, BigDecimal.ZERO).intValue();
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 出庫予定情報に紐付く作業情報から実績数を求め、出庫予定情報の更新を行います。<BR>
     * 実績数が予定数未満の場合は同一予定一意キーで作業情報を検索し各状態に、それ以外の場合は完了に更新します。<BR>
     * 予定数未満の場合は作業中＞開始済＞一部完了の優先順で更新を行います。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    protected void updateRetrievalPlan(String planUkey)
            throws ReadWriteException,
                InvalidDefineException
    {
        RetrievalPlanHandler retrievalPlanH = new RetrievalPlanHandler(getConnection());
        try
        {
            // 出庫予定情報を取得する
            RetrievalPlanSearchKey rpKey = new RetrievalPlanSearchKey();
            rpKey.setPlanUkey(planUkey);

            RetrievalPlan rp = (RetrievalPlan)retrievalPlanH.findPrimary(rpKey);
            if (null == rp)
            {
                throw new NotFoundException();
            }

            // 作業情報から作業結果数を取得する
            int resultQty = getResultQty(planUkey);
            int shortageQty = getShortageQty(planUkey);

            WarenaviSystemController warenaviSysCtlr = new WarenaviSystemController(getConnection(), getClass());
            String workDay = warenaviSysCtlr.getWorkDay();

            // 出庫予定情報を更新する
            RetrievalPlanAlterKey rpAKey = new RetrievalPlanAlterKey();
            rpAKey.setPlanUkey(planUkey);

            // 実績が予定より少ない場合は作業中に更新する
            if ((resultQty + shortageQty) < rp.getPlanQty())
            {
                rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
            }
            else
            {
                rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);

                if (WmsParam.EMPTYPB_CONSIGNORCODE.equals(rp.getConsignorCode())
                        && WmsParam.EMPTYPB_ITEMCODE.equals(rp.getItemCode()))
                {
                    // 空パレットの場合、報告フラグをONにする
                    rpAKey.updateReportFlag(RetrievalPlan.REPORT_FLAG_REPORT);
                }
            }
            rpAKey.updateResultQty(resultQty);
            rpAKey.updateShortageQty(shortageQty);
            rpAKey.updateWorkDay(workDay);
            rpAKey.updateLastUpdatePname(getClass().getSimpleName());

            retrievalPlanH.modify(rpAKey);
        }
        catch (ScheduleException e)
        {
        }
        catch (NoPrimaryException e)
        {
        }
        catch (NotFoundException e)
        {
        }
    }

    /**
     * 作業情報の完了処理を行います。 同時に、実績を作成します。
     *
     * @param winfo 完了する作業情報
     * @param asResultLocation 結果ロケーションNo.
     * @return 更新後の作業情報
     * @throws ReadWriteException
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    protected WorkInfo completeWorkinfo(WorkInfo winfo, String asResultLocation)
    {
        try
        {
            Connection conn = getConnection();
            AsWorkInfoController wic = new AsWorkInfoController(conn, getClass());
            AreaController areaC = new AreaController(getConnection(), getClass());
            // AS/RS型ロケーションをParamLocationに変換
            String resultLoc = areaC.toParamLocation(asResultLocation);
            WarenaviSystemController warenaviSysCtlr = new WarenaviSystemController(getConnection(), getClass());
            // 作業日の取得
            String workDay = warenaviSysCtlr.getWorkDay();

            // set result work info
            WorkInfo resultWork = new WorkInfo();
            int planQty = winfo.getPlanQty();

            // check aera type
            String planAreaNo = winfo.getPlanAreaNo();

            resultWork.setShortageQty(planQty);
            resultWork.setResultAreaNo(planAreaNo);
            resultWork.setResultLocationNo(resultLoc);
            resultWork.setResultLotNo(winfo.getPlanLotNo());
            resultWork.setReasonType(winfo.getReasonType());
            resultWork.setWorkDay(workDay);
            // build user info. for this work
            String userId = winfo.getUserId();
            String terminalNo = winfo.getTerminalNo();
            WmsUserInfo ui = WmsUserInfo.buildUserInfo(conn, userId, SystemDefine.HARDWARE_TYPE_ASRS, terminalNo);

            // complete work
            wic.completeWorkInfo(winfo, resultWork, WorkInfo.STATUS_FLAG_COMPLETION, ui);

            winfo.setShortageQty(winfo.getPlanQty());
            winfo.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);
            winfo.setResultAreaNo(planAreaNo);
            winfo.setResultLocationNo(resultLoc);
            winfo.setResultLotNo(winfo.getPlanLotNo());
            winfo.setWorkDay(workDay);
            winfo.setLastUpdatePname(getClass().getSimpleName());

            return winfo;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    /**
     * 在庫を引き落とすメソッドです。
     * 仮置き在庫作成の場合、仮置きエリアに在庫を作成する。
     * @param stock 在庫情報
     */
    protected void dropStock(Stock stock)
    {
        StockHandler sHandler = new StockHandler(getConnection());

        try
        {
            AreaController areaC = new AreaController(getConnection(), getClass());

            if (AreaController.TEMPORARY_AREA_TYPE.CREATE_TMP_ANY == areaC.getTemporaryAreaType(stock.getAreaNo()))
            {
                String tempAreaNo = areaC.getTemporaryArea(stock.getAreaNo());

                // do create if temporary area found.
                if (!StringUtil.isBlank(tempAreaNo))
                {
                    StockController sc = new StockController(getConnection(), getClass());

                    WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getClass());

                    stock.setAreaNo(tempAreaNo);
                    stock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);

                    Stock[] tgtStock = sc.lock(stock);
                    boolean noTarget = ArrayUtil.isEmpty(tgtStock);
                    stock.setStorageDay(wsc.getWorkDay());
                    stock.setStorageDate(new SysDate());
                    if (noTarget)
                    {
                        // create new stock
                        sc.initStorage(stock, SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE, getWmsUserInfo(), 0);
                    }
                    else
                    {
                        // add to exists stock
                        Stock addTarget = tgtStock[0];
                        stock.setStorageDay(null);
                        stock.setStorageDate(null);
                        sc.addStorage(addTarget, stock, SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE, false,
                                getWmsUserInfo(), 0);
                    }
                }
            }
            else
            {
                StockSearchKey sKey = new StockSearchKey();
                sKey.setStockId(stock.getStockId());
                sHandler.drop(sKey);
            }
        }
        catch (Exception e)
        {
        }
    }


    /**
     * 在庫引当をキャンセルするメソッドです。
     * @param stock 在庫情報
     * @param workInfo 作業情報
     */
    protected void cancelAllocate(Stock stock, WorkInfo workInfo)
    {
        StockHandler stockHandler = new StockHandler(getConnection());
        StockAlterKey aKey = new StockAlterKey();
        // 更新条件
        aKey.updateAllocationQty(stock.getAllocationQty() + workInfo.getPlanQty());
        aKey.updateLastUpdatePname(getClass().getSimpleName());
        // 検索条件
        aKey.setStockId(stock.getStockId());
        // 在庫更新
        try
        {
            stockHandler.modify(aKey);
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 予定在庫をキャンセルするメソッドです。
     * @param stock 在庫情報
     * @param workInfo 作業情報
     */
    protected void cancelPlanStock(Stock stock, WorkInfo workInfo)
    {
        StockHandler stockHandler = new StockHandler(getConnection());
        StockAlterKey aKey = new StockAlterKey();
        // 更新条件
        aKey.updatePlanQty(stock.getPlanQty() - workInfo.getPlanQty());
        aKey.updateLastUpdatePname(getClass().getSimpleName());
        // 検索条件
        aKey.setStockId(stock.getStockId());
        // 在庫更新
        try
        {
            stockHandler.modify(aKey);
        }
        catch (Exception e)
        {
        }


    }

    /**
     * 作業情報をキャンセルするメソッドです。
     * @param workInfo 作業情報
     * @param stPlan 入庫予定情報
     */
    protected void cancelWorkInfo(WorkInfo workInfo, StoragePlan stPlan)
    {
        try
        {
            WorkInfoHandler workhandler = new WorkInfoHandler(getConnection());
            WorkInfoAlterKey aKey = new WorkInfoAlterKey();
            WorkInfoSearchKey sKey = new WorkInfoSearchKey();

            sKey.setPlanUkey(stPlan.getPlanUkey());
            sKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            sKey.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);

            // 分割作業の場合の場合
            if (workhandler.count(sKey) == 0)
            {
                // 更新条件
                aKey.updateSettingUnitKey("");
                aKey.updateCollectJobNo("");
                aKey.updateSystemConnKey("");
                aKey.updateStockId("");
                aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateOrderSerialNo("");
                aKey.updateSkipCnt(0);
                aKey.updatePlanAreaNo(stPlan.getPlanAreaNo());
                aKey.updatePlanLocationNo(stPlan.getPlanLocationNo());
                aKey.updatePlanLotNo(stPlan.getPlanLotNo());
                aKey.updateResultAreaNo("");
                aKey.updateResultLocationNo("");
                aKey.updateResultLotNo("");
                aKey.updateResultQty(0);
                aKey.updateShortageQty(0);
                aKey.updateWorkSecond(0);
                aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
                aKey.updateWorkDay("");
                aKey.updateUserId("");
                aKey.updateTerminalNo("");
                aKey.updateLastUpdatePname(getClass().getSimpleName());
                // 検索条件
                aKey.setJobNo(workInfo.getJobNo());
                // 更新

                workhandler.modify(aKey);
            }
            // 入庫作業が分割された場合
            else
            {
                WorkInfo baseWI = (WorkInfo)workhandler.findPrimary(sKey);

                // 更新条件
                aKey.updateSettingUnitKey("");
                aKey.updateCollectJobNo("");
                aKey.updateSystemConnKey("");
                aKey.updateStockId("");
                aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateOrderSerialNo("");
                aKey.updateSkipCnt(0);
                aKey.updatePlanAreaNo(stPlan.getPlanAreaNo());
                aKey.updatePlanLocationNo(stPlan.getPlanLocationNo());
                aKey.updatePlanQty(baseWI.getPlanQty() + workInfo.getPlanQty());
                aKey.updateResultAreaNo("");
                aKey.updateResultLocationNo("");
                aKey.updateResultLotNo("");
                aKey.updateResultQty(0);
                aKey.updateShortageQty(0);
                aKey.updateWorkSecond(0);
                aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
                aKey.updateWorkDay("");
                aKey.updateUserId("");
                aKey.updateTerminalNo("");
                aKey.updateLastUpdatePname(getClass().getSimpleName());
                // 検索条件
                aKey.setJobNo(workInfo.getJobNo());
                // 更新
                workhandler.modify(aKey);

                // 分割作業を削除する
                deleteWorkInfo(baseWI);
            }


        }
        catch (Exception e1)
        {

        }

    }

    /**
     * 作業情報をキャンセルするメソッドです。
     * @param workInfo 作業情報
     * @param rsPlan 再入庫予定情報
     */
    protected void cancelWorkInfo(WorkInfo workInfo, ReStoringPlan rsPlan)
    {
        try
        {
            WorkInfoHandler workhandler = new WorkInfoHandler(getConnection());
            WorkInfoAlterKey aKey = new WorkInfoAlterKey();

            // 更新条件
            aKey.updateSettingUnitKey("");
            aKey.updateCollectJobNo("");
            aKey.updateSystemConnKey("");
            aKey.updateStockId("");
            aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateOrderSerialNo("");
            aKey.updateSkipCnt(0);
            aKey.updatePlanAreaNo(rsPlan.getPlanAreaNo());
            aKey.updatePlanLocationNo(rsPlan.getPlanLocationNo());
            aKey.updatePlanLotNo(rsPlan.getPlanLotNo());
            aKey.updateResultAreaNo("");
            aKey.updateResultLocationNo("");
            aKey.updateResultLotNo("");
            aKey.updateResultQty(0);
            aKey.updateShortageQty(0);
            aKey.updateWorkSecond(0);
            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
            aKey.updateWorkDay("");
            aKey.updateUserId("");
            aKey.updateTerminalNo("");
            aKey.updateLastUpdatePname(getClass().getSimpleName());

            // 検索条件
            aKey.setJobNo(workInfo.getJobNo());

            // 更新
            workhandler.modify(aKey);
        }
        catch (Exception e1)
        {

        }

    }

    /**
     * 入庫予定情報をキャンセルするメソッドです。
     * @pparam stPlan 入庫予定情報
     */
    protected void cancelPlanStorage(StoragePlan stPlan, WorkInfo workInfo)
    {
        try
        {
            StoragePlanHandler stHandler = new StoragePlanHandler(getConnection());
            StoragePlanAlterKey aKey = new StoragePlanAlterKey();

            WorkInfoHandler woHandler = new WorkInfoHandler(getConnection());
            WorkInfoSearchKey sKey = new WorkInfoSearchKey();
            sKey.setPlanUkey(stPlan.getPlanUkey());

            boolean splitFlag = (woHandler.count(sKey) > 1);

            // 更新条件
            aKey.updateStatusFlag(splitFlag ? SystemDefine.STATUS_FLAG_NOWWORKING
                                           : SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateResultQty(stPlan.getResultQty() - workInfo.getResultQty());
            aKey.updateLastUpdatePname(getClass().getSimpleName());
            if (!splitFlag)
            {
                // 分割作業が無くなる場合
                aKey.updateWorkDay("");
            }

            // 検索条件
            aKey.setPlanUkey(stPlan.getPlanUkey());
            // 入庫予定情報キャンセル

            stHandler.modify(aKey);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 出庫予定情報をキャンセルするメソッドです。
     * @param retPlan 出庫予定情報
     */
    protected void cancelPlanRetrievalPlan(RetrievalPlan retPlan)
    {
        RetrievalPlanHandler retHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanAlterKey aKey = new RetrievalPlanAlterKey();
        // 更新条件
        aKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
        aKey.updateSchFlag(RetrievalPlan.SCH_FLAG_SCHEDULE);
        aKey.updateResultQty(0);
        aKey.updateWorkDay("");
        aKey.updateLastUpdatePname(getClass().getSimpleName());
        // 検索条件
        aKey.setPlanUkey(retPlan.getPlanUkey());
        // 出庫予定情報キャンセル
        try
        {
            retHandler.modify(aKey);
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 再入庫予定情報をキャンセルするメソッドです。
     * @pparam stPlan 再入庫予定情報
     */
    protected void cancelPlanReStoring(ReStoringPlan rsPlan, WorkInfo workInfo)
    {
        try
        {
            ReStoringPlanHandler rsHandler = new ReStoringPlanHandler(getConnection());
            ReStoringPlanAlterKey aKey = new ReStoringPlanAlterKey();

            // 更新条件
            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateLastUpdatePname(getClass().getSimpleName());

            // 検索条件
            aKey.setPlanUkey(rsPlan.getPlanUkey());
            // 再入庫予定情報キャンセル

            rsHandler.modify(aKey);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * ステーション情報クリア
     */
    protected void clearStation(String carryKey)
    {
        // 到着情報クリア
        ArrivalHandler arrHand = new ArrivalHandler(getConnection());
        ArrivalSearchKey sKey = new ArrivalSearchKey();
        sKey.setCarryKey(carryKey);
        String statioNo = null;
        try
        {
            Arrival[] arrival = (Arrival[])arrHand.find(sKey);
            if (arrival.length > 0)
            {
                for (Arrival arr : arrival)
                {
                    statioNo = arr.getStationNo();
                    arrHand.drop(arr);
                }
            }
        }
        catch (Exception e)
        {
        }
        if (!StringUtil.isBlank(statioNo))
        {
            // ステーション情報クリア
            StationHandler staHandler = new StationHandler(getConnection());
            StationAlterKey aKey = new StationAlterKey();
            aKey.setStationNo(statioNo);
            aKey.updateInventoryCheckFlag(SystemDefine.INVENTORY_CHECK_FLAG_UNSTART);
            aKey.updateModeRequest(SystemDefine.MODE_REQUEST_NONE);
            aKey.updateModeRequestDate(null);
            try
            {
                staHandler.modify(aKey);
            }
            catch (Exception e)
            {
            }
        }
    }


    /**
     * 不整合データクリア
     */
    protected void clearMismatchDate()
            throws CommonException
    {
        WarenaviSystemController wSysCtlr = new WarenaviSystemController(getConnection(), getClass());
        // 作業情報、移動作業情報削除
        if (!wSysCtlr.isFaDaEnabled())
        {
            clearMoveWorkInfo();
        }
        // 作業情報と紐付かない搬送情報の削除
        clearWorkCarry();

        //データ不整合在庫情報、パレット情報削除
        clearMismatchStockPallet();

        // データ不整合棚情報の更新
        clearMismatchShelf();
    }

    /**
     * 移動作業情報のハードウェアタイプがAS/RSの物は削除するメソッドです。
     * ASRS移動作業情報は削除する。
     */
    protected void clearMoveWorkInfo()
    {

        // 移動作業情報
        MoveWorkInfoHandler movHandler = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfoSearchKey movKey = new MoveWorkInfoSearchKey();
        // 更新条件
        movKey.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        // AS/ASタイプは削除
        try
        {
            movHandler.drop(movKey);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 作業情報と紐付かない搬送情報を削除するメソッドです。
     * 紐付く作業情報が存在しない搬送情報は削除する。
     */
    @SuppressWarnings("static-access")
    protected void clearWorkCarry()
    {
        // 不整合在庫情報削除
        CarryInfoHandler caHandler = new CarryInfoHandler(getConnection());
        CarryInfoFinder caFinder = new CarryInfoFinder(getConnection());
        CarryInfoSearchKey caKey = new CarryInfoSearchKey();
        caFinder.open(true);
        // 検索条件
        caKey.setCarryKey(null, "!=", "", "", true);
        caKey.setJoin(CarryInfo.CARRY_KEY, "", WorkInfo.SYSTEM_CONN_KEY, "(+)");
        caKey.setKey(WorkInfo.SYSTEM_CONN_KEY, null);
        // 削除
        try
        {
            caFinder.searchForUpdate(caKey, caFinder.WAIT_SEC_NOWAIT);

            while (caFinder.hasNext())
            {
                CarryInfo[] cis = (CarryInfo[])caFinder.getEntities(100);
                for (CarryInfo ci : cis)
                {
                    try
                    {
                        caHandler.drop(ci);
                        logWrite(entity2String(ci), LogMessage.F_NOTICE);
                    }
                    catch (Exception e)
                    {
                    }
                }
                caFinder.searchForUpdate(caKey, caFinder.WAIT_SEC_NOWAIT);
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * データ不整合在庫情報、パレット情報を削除するメソッドです。
     * パレット情報が存在するが在庫情報が存在しない場合、パレット情報は削除する。
     * 在庫情報が存在するがパレット情報が存在しない場合、在庫情報を削除する。
     */
    protected void clearMismatchStockPallet()
    {


        // 不整合在庫情報削除
        StockHandler stoHandler = new StockHandler(getConnection());
        StockSearchKey stKey = new StockSearchKey();
        // 検索条件
        stKey.setPalletId(null, "!=", "", "", true);
        stKey.setJoin(Stock.PALLET_ID, "", Pallet.PALLET_ID, "(+)");
        stKey.setKey(Pallet.PALLET_ID, null);
        // 削除
        try
        {
            Stock[] stocks = (Stock[])stoHandler.find(stKey);

            for (Stock stock : stocks)
            {
                try
                {
                    stoHandler.drop(stock);
                    logWrite(entity2String(stock), LogMessage.F_NOTICE);
                }
                catch (Exception e)
                {
                }
            }
        }
        catch (Exception e)
        {
        }

        // 不整合パレット情報削除
        PalletHandler palHandler = new PalletHandler(getConnection());
        PalletSearchKey paKey = new PalletSearchKey();
        // 検索条件
        paKey.setPalletId(null, "!=", "", "", true);
        paKey.setJoin(Stock.PALLET_ID, "(+)", Pallet.PALLET_ID, "");
        paKey.setKey(Stock.PALLET_ID, null);
        // 削除
        try
        {
            Pallet[] pallets = (Pallet[])palHandler.find(paKey);

            for (Pallet pallet : pallets)
            {
                try
                {
                    palHandler.drop(pallet);
                    logWrite(entity2String(pallet), LogMessage.F_NOTICE);
                }
                catch (Exception e)
                {
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * データ不整合棚情報を更新するメソッドです。
     * 棚情報が実棚になっているがパレット情報が存在しない場合、棚情報を空棚に更新する。
     */
    protected void clearMismatchShelf()
    {
        // 棚情報
        ShelfHandler sheHandler = new ShelfHandler(getConnection());
        ShelfSearchKey sheKey = new ShelfSearchKey();
        ShelfAlterKey sheAKey = new ShelfAlterKey();

        sheKey.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_STORAGED, "=", "(", "", false);
        sheKey.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_RESERVATION, "=", "", ")", true);

        sheKey.setJoin(Shelf.STATION_NO, "", Pallet.CURRENT_STATION_NO, "(+)");
        sheKey.setKey(Pallet.PALLET_ID, null);

        // 棚情報が実棚になっているがパレット情報が存在しない場合
        try
        {
            if (sheHandler.count(sheKey) >= 0)
            {
                // 2010/05/11 Y.Osawa ADD ST
                // 搬送情報
                CarryInfoHandler carryHandler = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
                // 2010/05/11 Y.Osawa ADD ED

                Shelf[] shelfs = (Shelf[])sheHandler.find(sheKey);

                for (Shelf shelf : shelfs)
                {
                    // 2010/05/11 Y.Osawa ADD ST
                    // 出庫元棚が該当棚で在庫確認の搬送情報が存在すればクリア対象外
                    carryKey.clear();
                    carryKey.setRetrievalStationNo(shelf.getStationNo());
                    carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK);

                    if (carryHandler.count(carryKey) > 0)
                    {
                        continue;
                    }

                    sheAKey.clear();
                    // 2010/05/11 Y.Osawa ADD ED
                    sheAKey.setStationNo(shelf.getStationNo());
                    sheAKey.updateStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_EMPTY);
                    sheHandler.modify(sheAKey);
                    logWrite(entity2String(shelf), LogMessage.F_INFO);
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 搬送キーに紐付く作業表示データを削除します。
     * @param carryInfo 搬送情報
     */
    protected void dropOperationDisplay(CarryInfo carryInfo)
    {
        // 作業表示データの有無を確認する。作業表示データがあれば削除する
        OperationDisplayHandler odh = new OperationDisplayHandler(getConnection());
        OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
        odkey.setCarryKey(carryInfo.getCarryKey());
        try
        {
            if (odh.count(odkey) != 0)
            {
                odh.drop(odkey);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 作業情報を削除作業情報にコピーするメソッドです。
     * @param workInfo 作業情報
     * @param stotateDate 入庫日時
     * @return DeleteStock 削除在庫情報
     */
    protected DeleteStock copyWorkInfo(WorkInfo workInfo, Date storageDate)
    {
        DeleteStock deleteStock = new DeleteStock();
        try
        {
            deleteStock.setAreaNo(workInfo.getPlanAreaNo());
            deleteStock.setItemCode(workInfo.getItemCode());
            deleteStock.setLastUpdatePname(this.getClass().getSimpleName());
            deleteStock.setLocationNo(workInfo.getPlanLocationNo());
            deleteStock.setLotNo(workInfo.getPlanLotNo());
            deleteStock.setRegistDate(_startDate);
            deleteStock.setRegistPname(this.getClass().getSimpleName());
            deleteStock.setStockQty(workInfo.getPlanQty());
            deleteStock.setStorageDate(storageDate);
            deleteStock.setSystemConnKey(workInfo.getSystemConnKey());
        }
        catch (Exception e)
        {
        }
        return deleteStock;
    }

    /**
     * 在庫情報を作業情報にコピーするメソッドです。
     * @param stock 在庫情報
     * @return DeleteStock 削除在庫情報
     */
    protected DeleteStock copyStockInfo(Stock stock)
    {
        DeleteStock deleteStock = new DeleteStock();

        try
        {
            deleteStock.setAreaNo(stock.getAreaNo());
            deleteStock.setItemCode(stock.getItemCode());
            deleteStock.setLastUpdatePname(this.getClass().getSimpleName());
            deleteStock.setLocationNo(stock.getLocationNo());
            deleteStock.setLotNo(stock.getLotNo());
            deleteStock.setRegistDate(_startDate);
            deleteStock.setRegistPname(this.getClass().getSimpleName());
            deleteStock.setStockQty(stock.getAllocationQty());
            deleteStock.setStorageDate(stock.getStorageDate());
            deleteStock.setSystemConnKey("");
        }
        catch (Exception e)
        {
        }
        return deleteStock;
    }

    /**
     * 送信実績情報を削除作業情報にコピーするメソッドです。
     * @param hostSend 実績送信情報
     * @return DeleteStock 削除在庫情報
     */
    protected DeleteStock copyHostSend(HostSend hostSend)
    {
        DeleteStock deleteStock = new DeleteStock();
        try
        {
            deleteStock.setAreaNo(hostSend.getPlanAreaNo());
            deleteStock.setItemCode(hostSend.getItemCode());
            deleteStock.setLastUpdatePname(this.getClass().getSimpleName());
            deleteStock.setLocationNo(hostSend.getPlanLocationNo());
            deleteStock.setLotNo(hostSend.getPlanLotNo());
            deleteStock.setRegistDate(_startDate);
            deleteStock.setRegistPname(this.getClass().getSimpleName());
            deleteStock.setStockQty(hostSend.getResultQty());
//            deleteStock.setStorageDate(storageDate);
            deleteStock.setSystemConnKey(hostSend.getSystemConnKey());
        }
        catch (Exception e)
        {
        }
        return deleteStock;
    }


    /**
     * 在庫削除情報を作成するメソッドです。
     * @param delteStock 削除在庫情報
     */
    protected void createDeleteStockInfo(DeleteStock delteStock)
    {
        try
        {
            // insert to hostsend table
            DeleteStockHandler dsHandler = new DeleteStockHandler(getConnection());

            dsHandler.create(delteStock);
        }
        catch (Exception e)
        {
        }
    }


    /**
     * 取得フィールドを検索キーにセットします。
     *
     * @param key セット先の検索キー
     * @param collects 取得フィールド一覧
     */
    protected void setCollectFields(WorkInfoSearchKey key, FieldName[] collects)
    {
        for (FieldName fld : collects)
        {
            key.setCollect(fld);
        }
    }

    /**
     * CarryInfo表からwCarryKeyをキーに検索を行います。
     * @param conn データベースとのコネクションオブジェクト。
     * @param carrykey 搬送キー
     * @return CarryInfoの検索結果
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected CarryInfo find(Connection conn, String carrykey)
            throws CommonException
    {
        CarryInfo[] ci = null;
        CarryInfoHandler carryHandler = new CarryInfoHandler(conn);
        CarryInfoSearchKey skey = new CarryInfoSearchKey();

        skey.setCarryKey(carrykey);
        ci = (CarryInfo[])carryHandler.find(skey);

        // 対象データが見つからない
        if (ci == null || ci.length <= 0)
        {
            return null;
        }

        return (ci[0]);
    }

    /**
     * AS/RS搬送情報のキャンセル要求フラグを指定された要求区分に変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param carryKey 搬送Key
     * @param req 要求フラグ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterCarryInfoCancelRequest(Connection conn, String carryKey, String req)
            throws CommonException
    {
        CarryInfoHandler caHandler = new CarryInfoHandler(conn);
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        cakey.setCarryKey(carryKey);
        cakey.updateCancelRequest(req);
        cakey.updateCancelRequestDate(DbDateUtil.getTimeStamp());
        cakey.updateLastUpdatePname(getClass().getSimpleName());
        caHandler.modify(cakey);
    }

    /**
     * AS/RS搬送情報のメンテナンス端末Noを指定されたステーションに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param carryKey 搬送Key
     * @param no 出庫ステーション
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterCarryInfoMaintenanceTerminal(Connection conn, String carryKey, String no)
            throws CommonException
    {
        CarryInfoHandler caHandler = new CarryInfoHandler(conn);
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        cakey.setCarryKey(carryKey);
        cakey.updateMaintenanceTerminal(no);
        cakey.updateLastUpdatePname(getClass().getSimpleName());
        caHandler.modify(cakey);
    }

    /**
     * AS/RSパレット情報のカレントステーションNoを指定されたステーションNoに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param stno カレントステーションNo.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletCurrentStationNo(Connection conn, String pid, String stno)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.setPalletId(pid);
        palkey.updateCurrentStationNo(stno);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * AS/RSパレット情報のStatusを指定されたStatusに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param status 在庫状態
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletStatus(Connection conn, String pid, String status)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.updateStatusFlag(status);
        palkey.setPalletId(pid);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * AS/RSパレット情報の引当状態を指定された引当状態に変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param alloc 引当状態
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletAllocation(Connection conn, String pid, String alloc)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.updateAllocationFlag(alloc);
        palkey.setPalletId(pid);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * 削除在庫ﾘｽﾄを印刷するメソッドです。
     */
    protected void print()
    {
        try
        {
            // 削除在庫ﾘｽﾄ印刷
            AsAllocationClearDASCH dasch =
                    new AsAllocationClearDASCH(getConnection(), this.getClass(), this.getLocale(), this.getUserInfo());
            dasch.setForwardOnly(true);

            AsAllocationClearDASCHParams listParam = new AsAllocationClearDASCHParams();

            listParam.set(AsAllocationClearDASCHParams.START_DATE, _startDate);

            PrintExporter exporter = null;

            if (dasch.count(listParam) > 0)
            {
                dasch.search(listParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(this.getLocale(), this.getUserInfo());

                WarenaviSystemController wSysCtlr = new WarenaviSystemController(getConnection(), getClass());
                // FAのとき
                if (wSysCtlr.isFaDaEnabled())
                {
                    exporter = factory.newPrinterExporter("FaDeleteStockList", false);
                    exporter.open();

                    // export.
                    while (dasch.next())
                    {
                        Params outparam = dasch.get();
                        FaDeleteStockListParams expparam = new FaDeleteStockListParams();
                        expparam.set(FaDeleteStockListParams.DFK_DS_NO,
                                outparam.get(AsAllocationClearDASCHParams.DFK_DS_NO));
                        expparam.set(FaDeleteStockListParams.DFK_USER_ID,
                                outparam.get(AsAllocationClearDASCHParams.DFK_USER_ID));
                        expparam.set(FaDeleteStockListParams.DFK_USER_NAME,
                                outparam.get(AsAllocationClearDASCHParams.DFK_USER_NAME));
                        expparam.set(FaDeleteStockListParams.SYS_DAY,
                                outparam.get(AsAllocationClearDASCHParams.SYS_DAY));
                        expparam.set(FaDeleteStockListParams.SYS_TIME,
                                outparam.get(AsAllocationClearDASCHParams.SYS_TIME));
                        expparam.set(FaDeleteStockListParams.ALLOCATION_FLAG,
                                outparam.get(AsAllocationClearDASCHParams.ALLOCATION_FLAG));
                        expparam.set(FaDeleteStockListParams.ITEM_CODE,
                                outparam.get(AsAllocationClearDASCHParams.ITEM_CODE));
                        expparam.set(FaDeleteStockListParams.ITEM_NAME,
                                outparam.get(AsAllocationClearDASCHParams.ITEM_NAME));
                        expparam.set(FaDeleteStockListParams.LOT_NO, outparam.get(AsAllocationClearDASCHParams.LOT_NO));
                        expparam.set(FaDeleteStockListParams.AREA_NO,
                                outparam.get(AsAllocationClearDASCHParams.AREA_NO));
                        expparam.set(FaDeleteStockListParams.LOCATION_NO,
                                outparam.get(AsAllocationClearDASCHParams.LOCATION_NO));
                        expparam.set(FaDeleteStockListParams.STOCK_QTY,
                                outparam.get(AsAllocationClearDASCHParams.STOCK_PIECE_QTY));
                        expparam.set(FaDeleteStockListParams.STORAGE_DATE, WmsFormatter.toDate(
                                outparam.getDate(AsAllocationClearDASCHParams.STORAGE_DAY),
                                outparam.getDate(AsAllocationClearDASCHParams.STORAGE_TIME)));
                        if (!exporter.write(expparam))
                        {
                            break;
                        }
                    }
                }
                // DAのとき
                else
                {
                    exporter = factory.newPrinterExporter("DeleteStockList", false);
                    exporter.open();

                    // export.
                    while (dasch.next())
                    {
                        Params outparam = dasch.get();
                        DeleteStockListParams expparam = new DeleteStockListParams();
                        expparam.set(DeleteStockListParams.DFK_DS_NO,
                                outparam.get(AsAllocationClearDASCHParams.DFK_DS_NO));
                        expparam.set(DeleteStockListParams.DFK_USER_ID,
                                outparam.get(AsAllocationClearDASCHParams.DFK_USER_ID));
                        expparam.set(DeleteStockListParams.DFK_USER_NAME,
                                outparam.get(AsAllocationClearDASCHParams.DFK_USER_NAME));
                        expparam.set(DeleteStockListParams.SYS_DAY, outparam.get(AsAllocationClearDASCHParams.SYS_DAY));
                        expparam.set(DeleteStockListParams.SYS_TIME,
                                outparam.get(AsAllocationClearDASCHParams.SYS_TIME));
                        expparam.set(DeleteStockListParams.ALLOCATION_FLAG,
                                outparam.get(AsAllocationClearDASCHParams.ALLOCATION_FLAG));
                        expparam.set(DeleteStockListParams.ITEM_CODE,
                                outparam.get(AsAllocationClearDASCHParams.ITEM_CODE));
                        expparam.set(DeleteStockListParams.ITEM_NAME,
                                outparam.get(AsAllocationClearDASCHParams.ITEM_NAME));
                        expparam.set(DeleteStockListParams.LOT_NO, outparam.get(AsAllocationClearDASCHParams.LOT_NO));
                        expparam.set(DeleteStockListParams.AREA_NO, outparam.get(AsAllocationClearDASCHParams.AREA_NO));
                        expparam.set(DeleteStockListParams.LOCATION_NO,
                                outparam.get(AsAllocationClearDASCHParams.LOCATION_NO));
                        expparam.set(DeleteStockListParams.ENTERING_QTY,
                                outparam.get(AsAllocationClearDASCHParams.ENTERING_QTY));
                        expparam.set(DeleteStockListParams.STOCK_CASE_QTY,
                                outparam.get(AsAllocationClearDASCHParams.STOCK_CASE_QTY));
                        expparam.set(DeleteStockListParams.STOCK_PIECE_QTY,
                                outparam.get(AsAllocationClearDASCHParams.STOCK_PIECE_QTY));
                        expparam.set(DeleteStockListParams.JAN, outparam.get(AsAllocationClearDASCHParams.JAN));
                        expparam.set(DeleteStockListParams.ITF, outparam.get(AsAllocationClearDASCHParams.ITF));
                        expparam.set(DeleteStockListParams.STORAGE_DAY,
                                outparam.get(AsAllocationClearDASCHParams.STORAGE_DAY));
                        expparam.set(DeleteStockListParams.STORAGE_TIME,
                                outparam.get(AsAllocationClearDASCHParams.STORAGE_TIME));
                        if (!exporter.write(expparam))
                        {
                            break;
                        }
                    }
                }
                // execute print.
                try
                {
                    exporter.print();
                }
                catch (Exception ex)
                {
                    // 設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }
            }
        }
        catch (CommonException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 不整合削除データをログに出力します。
     * @throws IOException
     * @throws NumberFormatException
     */
    protected void logWrite(String trdata, String code)
    {
        if ((_rTrHandler == null))
        {
            _trName = WmsParam.ETC_LOGS_PATH + _trName;
            _rTrHandler = new RandomAcsFileHandler(_trName, 1000000);
        }
        if (!(_rTrHandler == null))
        {
            StringTokenizer strTok = new StringTokenizer(trdata);
            String className = null;
            if (strTok.hasMoreTokens())
            {
                className = strTok.nextToken(_SEPARAT);
            }
            if (strTok.hasMoreTokens())
            {
                _trcParam[0] = trdata;
            }
            try
            {
                _rTrHandler.write(6001005, code, className, _trcParam);
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * entityから取得するデータをStringに変換する
     * @return String
     */
    @SuppressWarnings("unchecked")
    protected String entity2String(Entity ent)
    {
        String line;

        line = ent.getStoreName() + _SEPARAT;

        Map valueMap = ent.getValueMap();
        FieldName[] flds = (FieldName[])valueMap.keySet().toArray(new FieldName[0]);

        for (int i = 0; i < flds.length; i++)
        {
            Object val = valueMap.get(flds[i]);
            if (val != null)
            {
                line = line + flds[i] + ":" + val.toString() + " ";
            }
        }
        return line;
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
