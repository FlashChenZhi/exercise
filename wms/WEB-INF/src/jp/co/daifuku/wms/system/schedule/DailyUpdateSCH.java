// $Id: DailyUpdateSCH.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.DailyUpdateSCHParams.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.PrePostFileFilter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogController;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.pcart.system.schedule.PCTSystemInParameter;
import jp.co.daifuku.pcart.system.schedule.PCTSystemOutParameter;
import jp.co.daifuku.wms.analysis.operator.HistoryMaker;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DeleteStockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PrintHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReplenishShortageSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortResultHandler;
import jp.co.daifuku.wms.base.dbhandler.SortResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InventResult;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveResult;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.OperationDay;
import jp.co.daifuku.wms.base.entity.PCTAllUserResult;
import jp.co.daifuku.wms.base.entity.PCTCustomer;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.PCTUserRanking;
import jp.co.daifuku.wms.base.entity.PCTUserResult;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingResult;
import jp.co.daifuku.wms.base.entity.ReceivingResultView;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Result;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.ShipHostSend;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.ShipResult;
import jp.co.daifuku.wms.base.entity.ShipResultView;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SortHostSend;
import jp.co.daifuku.wms.base.entity.SortResult;
import jp.co.daifuku.wms.base.entity.SortResultView;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkingUnit;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.CompressToZipFile;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.FileCleaner;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DatabaseFinder;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.db.DirectDBHandler;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.system.dasch.FaAsReStoringDeleteDASCH;
import jp.co.daifuku.wms.system.dasch.FaAsReStoringDeleteDASCHParams;
import jp.co.daifuku.wms.system.exporter.ReStoringDeleteListParams;

/**
 * 日次更新のスケジュール処理を行います。
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class DailyUpdateSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * <code>REPORTDATA_FILENAME</code>
     */
    private static final String REPORTDATA_FILENAME = "REPORTDATA_FILENAME";

    /**
     * <code>COMPARE Less Than Equal</code>
     */
    private static final String COMPARE_LT_EQ = "<=";

    /**
     * <code>COMPARE Less</code>
     */
    private static final String COMPARE_LT = "<";

    /**
     * クラス名(日次更新処理)
     */
    private static final String PROC_NAME = DailyUpdateSCH.class.getSimpleName();

    /**
     * <code>FILENAME_MESSAGE_WORK</code>
     */
    private static final String FILENAME_MESSAGE_WORK = "message_work.txt";

    //DFKLOOK 3.5 Start
    /** データ報告報告単位のセクション名 **/
    private static final String REPORT_TYPE = "REPORT_TYPE";

    /** データタイプ 入荷 **/
    private static final int REPORTTYPE_INSTOCKRECEIVE = 0;

    /** データタイプ 入庫 **/
    private static final int REPORTTYPE_STORAGE = 1;

    /** データタイプ 出庫 **/
    private static final int REPORTTYPE_RETRIEVAL = 2;

    /** データタイプ 仕分 **/
    private static final int REPORTTYPE_PICKING = 3;

    /** データタイプ 出荷 **/
    private static final int REPORTTYPE_SHIPPING = 4;

    /** データタイプ 在庫移動 **/
    private static final int REPORTTYPE_STOCKMOVING = 5;

    /** データタイプ 棚卸 **/
    private static final int REPORTTYPE_INVENTORY = 6;

    /** データタイプ 予定外入庫 **/
    private static final int REPORTTYPE_NOPLANSTORAGE = 7;

    /** データタイプ 予定外出庫 **/
    private static final int REPORTTYPE_NOPLANRETRIEVAL = 8;

    /** データタイプ クロスドック **/
    private static final int REPORTTYPE_CROSS_DOCK = 9;

    /** データタイプ PCT出庫 **/
    private static final int REPORTTYPE_PCTRETRIEVAL = 10;

    /** データタイプ　在庫情報 **/
    private static final int REPORTTYPE_DATA_TYPE_STOCK = 11;

    /** データタイプ　仕入先マスタ **/
    private static final int RECEIVETYPE_SUPPLIER_MASTER = 12;

    /** データタイプ　出荷先マスタ **/
    private static final int RECEIVETYPE_CUSTOMER_MASTER = 13;

    /** データタイプ　商品マスタ **/
    private static final int RECEIVETYPE_ITEM_MASTER = 14;

    /** データタイプ　商品固定棚マスタ **/
    private static final int RECEIVETYPE_FIXED_LOCATION_MASTER = 15;

    /** データタイプ　PCT商品マスタ **/
    private static final int RCEIVETYPE_PCTITEM_MASTER = 16;

    /** データ環境設定キーをString配列で定義 **/
    private static final String[] TYPE_KEY = {
            "RECEIVE_SUPPORT",
            "STORAGE_SUPPORT",
            "RETRIEVAL_SUPPORT",
            "PICKING_SUPPORT",
            "SHIP_INSPECTION",
            "MOVE_SUPPORT",
            "INVENT_SUPPORT",
            "NOPLANSTRAGE_SUPPORT",
            "NOPLANRETRIEVAL_SUPPORT",
            "CROSSDOCK_SUPPORT",
            "PCTRETRIEVAL_SUPPORT",
            "STOCK_SUPPORT",
            "MASTER_SUPPLIER",
            "MASTER_CUSTOMER",
            "MASTER_ITEM",
            "AREA_FIXEDLOCATE",
            "PCTITEM_SUPPORT"
    };

    //DFKLOOK 3.5 End

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * WareNaviシステム定義コントローラ
     */
    private WarenaviSystemController _wsysCtlr = null;

    /**
     * WareNaviSystem のマスタ保持日数
     */
    private Date _masterHoldLimitDay = null;

    /**
     * Shared Handler
     */
    private DatabaseHandler _sharedHandler;

    /**
     * Environment information operator
     */
    private IniFileOperator _iniOperator = null;

    /**
     * WareNaviSystem の予定保持日数を日付に変換し
     * 削除対象の期日となる境界日時
     */
    private String _delPlanDate = null;

    /**
     * WareNaviSystem の実績保持日数を日付に変換し
     * 削除対象の期日となる境界日時
     */
    private String _delResultDate = null;

    /**
     * 欠品情報の、削除対象の期日となる境界日時
     */
    private Date _delShortageDate = null;

    /**
     * 帳票発行履歴の、削除対象の期日となる境界日時
     */
    private Date _printHistoryHoldDate = null;

    /**
     * 到着情報の、削除対象の期日となる境界日時
     */
    private Date _arrivalHoldDate = null;

    /**
     * 再入庫予定情報の、削除対象の期日となる境界日時
     */
    private String _restoringHoldDate = null;

    /**
     * Flag for completed of shared process
     */
    private boolean _isShareProcessed = false;

    /**
     * 全ユーザ実績情報の、削除対象の期日となる境界日時
     */
    private Date _delAllUserDate = null;

    /**
     * PCT操作ログの、削除対象の期日となる境界日時
     */
    private Date _delOperationLog = null;

    /**
     * ピッキング実績情報の、削除対象の期日となる境界日時
     */
    private String _delPickingDate = null;

    /**
     * 重量差異情報の、削除対象の期日となる境界日時
     */
    private Date _delWeightDate = null;

    /**
     * 作業リスト情報の、削除対象の期日となる境界日時
     */
    private Date _delWorkListDate = null;

    /**
     * 再入庫の削除予定データ印刷の失敗フラグ
     */
    private boolean _isReStoringPrintFailure = false;

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
    public DailyUpdateSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);

        // eWareNaviシステム定義コントローラー生成
        _wsysCtlr = new WarenaviSystemController(conn, getClass());
        // eWareNaviシステム定義データベースハンドラ生成
        _sharedHandler = new WarenaviSystemHandler(conn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    @SuppressWarnings("unchecked")
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // 返却パラメータ生成
        List<Params> errList = new ErrorList<Params>();

        // 日次更新状態(日次更新不可)
        String statusNG = SystemOutParameter.DAILYUPDATE_STATUS_NG;
        // 日次更新状態(警告)
        String statusWarn = SystemOutParameter.DAILYUPDATE_STATUS_WARNING;

        // システムの状態から日次更新可能チェックを行う
        errList = (checkSystem(errList));
        // パッケージごとに日次更新可能チェックを行う
        errList = (checkPackageNG(errList));
        // ピッキングカートの日次更新可能チェックを行う
        errList = (checkRFTNG(errList));
        // パッケージごとに日次更新警告チェックを行う
        errList = (checkPackageWran(errList));
        // RFT端末の日次更新警告チェックを行う
        errList = (checkRFT(errList));

        // エラー情報が存在しなかった場合
        if (errList.isEmpty())
        {
            // (6021033)日次更新可能です。
            setMessage("6021033");
            return new ArrayList<Params>();
        }

        // メッセージ設定
        boolean hasNG = false;
        boolean hasWarning = false;
        for (Params param : errList)
        {
            hasNG |= statusNG.equals(param.get(STATUS));
            hasWarning |= statusWarn.equals(param.get(STATUS));
        }
        // 日次更新不可の場合
        if (hasNG)
        {
            // (6023130)日次更新を行えません。警告内容を確認してください
            setMessage("6023130");
        }
        // 警告の場合
        else if (hasWarning)
        {
            // (6023131)日次更新は行えますが警告が発生しています。警告内容を確認してください
            setMessage("6023131");
        }
        // 生成されたエラー情報を返却
        return errList;
    }

    /**
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
     * <BR>
     * @param startParams スケジュールパラメータの配列
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    public boolean startSCH(DailyUpdateSCHParams startParams)
            throws CommonException
    {
        // ログ出力
        RmiMsgLogClient.write(WmsMessageFormatter.format(6020034, DispResources.getText("LBL-W0286")),
                (String)this.getClass().getName());
        // コネクション生成
        Connection conn = getConnection();
        // システム定義コントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;
        // 日次更新フラグ
        boolean dailyUpdate = false;

        try
        {
            // パラメータ生成
            SystemInParameter[] param = new SystemInParameter[1];
            param[0] = new SystemInParameter(getWmsUserInfo());
            param[0].setWorkDay(startParams.getString(WORK_DATE));
            param[0].setDisplayCondition(startParams.getString(NO_WORK_INFOMATION));

            // WareNaviSystemの日次更新フラグをONに変更にします。
            // 以下の処理でエラーが発生した場合は日次更新フラグをOFFにして日次更新を終了させます。
            wsysCtlr.updateDailyUpdating(true);
            doCommit(getClass());

            // 日次更新フラグを成立
            dailyUpdate = true;

            // 日次更新処理で必要となる値の取得
            prepare();

            // 分析パッケージが導入されている場合
            if (wsysCtlr.hasAnalysisPack())
            {
                // 履歴データ作成
                HistoryMaker hm = new HistoryMaker();
                hm.make(conn);
            }

            // PCT出庫パッケージありの場合
            if (wsysCtlr.hasPCTRetrievalPack())
            {
                // 実績集計照会用テーブルを登録する
                registPickingResult(conn, wsysCtlr.getWorkDay());

                // PCT全ユーザ実績テーブルを登録する
                registAllUserResult(conn, wsysCtlr.getWorkDay());
            }

            // 不要データの削除
            deleteData(conn, param);

            // 営業日登録
            registBusinessDay();

            // 作業日 > システム日付の場合は作業日を更新しない
            // 作業日 + 1日 < システム日付の場合は作業日をシステム日付に更新する
            // 上記以外の場合は作業日を次の日へ更新する
            Date systemDate = WmsFormatter.toDate(DbDateUtil.getSystemDate());
            Date ewNWorkDate = WmsFormatter.toDate(wsysCtlr.getWorkDay());
            if (!StringUtil.isBlank(systemDate) && !StringUtil.isBlank(ewNWorkDate))
            {
                // 作業日 + 1日した日付を取得
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(ewNWorkDate);
                cal.add(GregorianCalendar.DATE, 1);

                // 作業日 > システム日付の場合
                if (ewNWorkDate.compareTo(systemDate) > 0)
                {
                    // 日次更新日時の更新を行う
                    wsysCtlr.updateDailyUpdateDate();

                    // (6021022)日次更新を終了しました。
                    setMessage(WmsMessageFormatter.format(6021022));
                }
                // 作業日 + 1日 < システム日付の場合
                else if (systemDate.compareTo(cal.getTime()) > 0)
                {
                    // 作業日と日次更新日時を更新する
                    wsysCtlr.updateWorkDay(DbDateUtil.getSystemDate(), param[0].getWmsUserInfo());
                    wsysCtlr.updateDailyUpdateDate();

                    // (6021038)日次更新を終了しました。作業日を更新しました。
                    setMessage(WmsMessageFormatter.format(6021038));
                }
                else
                {
                    // 作業日を翌日にする
                    changeWorkDate(param[0].getWmsUserInfo());

                    // (6021038)日次更新を終了しました。作業日を更新しました。
                    setMessage(WmsMessageFormatter.format(6021038));
                }
            }

            // 再入庫削除リストの印刷失敗
            if (_isReStoringPrintFailure)
            {
                // 6007046=再入庫削除リストの印刷に失敗したため、再入庫の未作業データの削除を行えませんでした。
                setMessage("6007046");
            }

            // Part11対応
            P11LogController p11log = new P11LogController(conn);
            p11log.exportLog();
            if (p11log.isFailedCopyToBackupMedia())
            {
                // (6007044)日次処理後、DVDへのコピーに失敗しました。ファイル名={0}
                setMessage(WmsMessageFormatter.format(6007044, p11log.getCopyFailedFileName()));
            }

            // ログ出力
            RmiMsgLogClient.write(WmsMessageFormatter.format(6020035, DispResources.getText("LBL-W0286")),
                    getClass().getName());

            // 正常の場合はtrueを返却
            return true;
        }
        // データ重複例外が発生した場合
        catch (DataExistsException e)
        {
            // rollback.
            doRollBack(getClass());

            // ログ出力
            RmiMsgLogClient.write(new TraceHandler(6006021, e), this.getClass().getName());

            // 異常の場合はfalseを返却
            return false;
        }
        // 予期しない例外が発生した場合
        catch (CommonException e)
        {
            // rollback.
            doRollBack(getClass());
            throw e;
        }
        // 実行時例外が発生した場合
        catch (RuntimeException e)
        {
            // rollback.
            doRollBack(getClass());
            throw e;
        }
        // SQL例外が発生した場合
        catch (SQLException e)
        {
            // rollback.
            doRollBack(getClass());

            // (6127005)データベースエラーが発生しました。
            setMessage(WmsMessageFormatter.format(6127005));

            // DB例外をスロー
            throw new ReadWriteException(e);
        }
        // 入出力例外が発生した場合
        catch (IOException e)
        {
            // rollback.
            doRollBack(getClass());

            // (6007001)スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007001));

            // DB例外をスロー
            throw new ReadWriteException(e);
        }
        finally
        {
            // 日次更新中の場合
            if (dailyUpdate)
            {
                // 日次更新中フラグをOFFにします。
                wsysCtlr.updateDailyUpdating(false);
                // commit.
                doCommit(getClass());
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 日次更新処理で必要となる値を取得します。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void prepare()
            throws CommonException
    {
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;
        // 作業日を取得
        String workDay = wsysCtlr.getWorkDay();
        // WareNaviSystemから取得した予定保持日数を日付に変換し、削除対象の期日となる境界日時を取得します。
        _delPlanDate = getKeepDay(workDay, wsysCtlr.getPlanHoldPeriod());
        // WareNaviSystemから取得した実績保持日数を日付に変換し、削除対象の期日となる境界日時を取得します。
        _delResultDate = getKeepDay(workDay, wsysCtlr.getResultHoldPeriod());

        // 欠品情報処理日数に0以下を指定された場合はNGとする。
        int siKeepDays = WmsParam.SHORTAGE_INFO_KEEP_DAYS;
        if (siKeepDays <= 0)
        {
            // ログを出力
            Object[] msgParam = {
                    "WmsParam.SHORTAGE_INFO_KEEP_DAYS",
                    String.valueOf((siKeepDays)),
            };
            // (6006016)定義エラーが発生しました。項目={0}に対し{1}がセットされました。
            RmiMsgLogClient.write(6006016, getClass().getName(), msgParam);

            // 定義外例外をスロー
            throw new InvalidDefineException();
        }
        // 欠品情報の、削除対象の期日となる境界日時を取得します。
        _delShortageDate = getKeepDate(siKeepDays);

        // マスタ保持日数の値を取得します。MASTER_INFO_KEEP_DAYS
        int masterHoldDay = WmsParam.MASTER_INFO_KEEP_DAYS;
        _masterHoldLimitDay = (masterHoldDay > 0) ? getKeepDate(masterHoldDay)
                                                 : null;

        // 帳票発行履歴保持日数の値を取得します。PRINTHISTORY_KEEP_DAYS
        int printHistoryHoldDate = Integer.parseInt(WmsParam.PRINTHISTORY_KEEP_DAYS);
        _printHistoryHoldDate = (printHistoryHoldDate > 0) ? getKeepDate(printHistoryHoldDate)
                                                          : null;

        // 到着情報保持日数の値を取得します。ARRIVAL_KEEP_DAYS
        int arrivalHoldDate = Integer.parseInt(WmsParam.ARRIVAL_KEEP_DAYS);
        _arrivalHoldDate = WmsFormatter.toDate(getKeepDay(workDay, arrivalHoldDate));

        // 再入庫予定情報保持日数の値を取得します。RESTORING_KEEP_DAYS
        int restoringHoldDate = Integer.parseInt(WmsParam.RESTORING_KEEP_DAYS);
        _restoringHoldDate = getKeepDay(workDay, restoringHoldDate);

        // 重量差異保持日数の値を取得します。PCT_ALL_USER_RESULT_KEEP_DAYS
        int delWeightDistinct = Integer.parseInt(WmsParam.ETC_LOGFILE_KEEP_DAYS);
        _delWeightDate = (delWeightDistinct > 0) ? getKeepDate(delWeightDistinct)
                                                : null;

        // 全ユーザ実績保持日数の値を取得します。PCT_ALL_USER_RESULT_KEEP_DAYS
        int delAllUserDay = WmsParam.PCT_ALL_USER_RESULT_KEEP_DAYS;
        _delAllUserDate = (delAllUserDay > 0) ? getKeepDate(delAllUserDay)
                                             : null;

        // ピッキング実績保持日数の値を取得します。PCT_RESULT_KEEP_DAYS
        int delPickingDay = WmsParam.PCT_RESULT_KEEP_DAYS;
        _delPickingDate = getKeepDay(workDay, delPickingDay);

        // PCT操作ログ保持日数の値を取得します。PCT_OPERATION_LOG_KEEP_DAYS
        int delOperationLogDay = WmsParam.PCT_OPERATION_LOG_KEEP_DAYS;
        _delOperationLog = (delOperationLogDay > 0) ? getKeepDate(delOperationLogDay)
                                                   : null;

        // 作業リスト保持日数の値を取得します。DNWORKLIST_KEEP_DAYS
        int delWorkList = WmsParam.DNWORKLIST_KEEP_DAYS;
        _delWorkListDate = WmsFormatter.toDate(getKeepDay(workDay, delWorkList));

        // 処理終了
        return;
    }

    /**
     * 営業日を取得する。<BR>
     * <BR>
     * @param workDay 作業日
     * @param keepDay 保持日数(予定or実績)
     * @return String 営業日
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private String getKeepDay(String workDay, int keepDay)
            throws ReadWriteException
    {
        // 保持日数が0の場合は作業日を返却
        if (keepDay == 0)
        {
            return workDay;
        }

        // 営業日情報データベースハンドラー
        OperationDayHandler keepDateHnd = new OperationDayHandler(getConnection());
        // 営業日情報検索キー
        OperationDaySearchKey sKey = new OperationDaySearchKey();

        // 営業日(作業日以前)
        sKey.setOperationDay(workDay, "<");
        // 保持日数の分の営業日がDBに存在しない場合
        if (keepDateHnd.count(sKey) < keepDay)
        {
            // 空文字を返却
            return "";
        }
        else
        {
            // 保持日数分の営業日がDBに存在した場合
            sKey.setCollect(OperationDay.OPERATION_DAY);
            sKey.setOperationDayOrder(false);

            // 営業日情報検索
            OperationDay[] keep = (OperationDay[])keepDateHnd.find(sKey);

            return keep[keepDay - 1].getOperationDay();
        }
    }

    /**
     * 保持日付を返します。<BR>
     * <BR>
     * @param keepDays 保持日数
     * @return 保持日付
     */
    private String getKeepDateString(int keepDays)
    {
        // 保持日数が存在しない場合
        if (keepDays <= 0)
        {
            // nullを返却
            return null;
        }
        // 算出した保持日付を返却
        return getKeepDateStringConvert(Calendar.getInstance(), keepDays);
    }

    /**
     * 保持日付を返します。<BR>
     * <BR>
     * @param cal 日付
     * @param keepDays 保持日数
     * @return 保持日付
     */
    private String getKeepDateStringConvert(Calendar cal, int keepDays)
    {
        // 日付 - (保持日数の前日)
        cal.add(Calendar.DATE, -(keepDays - 1));
        // 時刻
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        cal.set(Calendar.MINUTE, 0);
        // 秒 - (1秒)
        cal.set(Calendar.SECOND, -1);

        // パラメータをフォーマットし返却
        SimpleDateFormat f = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
        return f.format(cal.getTime());
    }

    /**
     * システム日付と保持日数を比較して、削除対象の期日となる日時を返します。<BR>
     * <BR>
     * @param keepDays 保持日数
     * @return 期日となる日時
     */
    private Date getKeepDate(int keepDays)
    {
        // カレンダー生成
        Calendar curdate = Calendar.getInstance();

        // 現在日付 - (保持日数の前日)
        curdate.add(Calendar.DATE, -(keepDays - 1));
        // 現在時刻
        curdate.set(Calendar.HOUR_OF_DAY, 00);
        // 現在分
        curdate.set(Calendar.MINUTE, 00);
        // 現在秒
        curdate.set(Calendar.SECOND, 00);

        // 生成した日付を返却
        return curdate.getTime();
    }

    /**
     * 不要なデータの削除を行います。<BR>
     * <ol>
     *   <li>予定情報および関連する作業情報
     *   <li>マスタデータ
     *   <li>ログファイル
     * </ol>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param param スケジュールパラメータの配列
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteData(Connection conn, SystemInParameter[] param)
            throws CommonException,
                IOException
    {
        // PCTマスタデータ削除を行います。
        deletePCTMaster(conn);

        // 予定、作業データの削除を行います。
        deleteWorkingData(conn, param);

        // PART11 Packageなし時のみ処理を行います。
        if (!P11Config.isPart11Log())
        {
            // マスタデータ削除を行います。
            deleteMaster(conn);
        }
        // 取込エラー情報削除
        deleteLoadError(conn);

        // 交換データ通信履歴削除
        deleteExchangeHistory(conn);

        // AS/RSパッケージ導入時
        if (_wsysCtlr.hasAsrsPack())
        {
            // 削除在庫情報削除
            deleteDeleteStock();

            // 到着情報削除
            deleteArrival(conn);
        }

        if (!_wsysCtlr.hasPCTRetrievalPack())
        {
            // 作業リスト情報削除
            deleteWorkList(conn);
        }

        // 帳票発行履歴削除
        deletePrintHistory(conn);

        if (!_wsysCtlr.hasPCTRetrievalPack())
        {
            // 一時商品データ削除を行います。
            deleteTempItem(conn);
        }
        // ログファイルの削除を行います。
        deleteLog();
    }

    /**
     * 取込エラー情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteLoadError(Connection conn)
            throws CommonException
    {
        // 取込エラー情報検索キー
        LoadErrorInfoSearchKey loadError = new LoadErrorInfoSearchKey();

        // データ削除
        dropRecords(loadError);
    }

    /**
     * 交換データ通信履歴の削除を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteExchangeHistory(Connection conn)
            throws CommonException
    {
        // 取込エラー情報検索キー
        ExchangeHistorySearchKey exchangeHis = new ExchangeHistorySearchKey();

        // データ削除
        dropRecords(exchangeHis);
    }

    /**
     * 削除在庫情報の削除を行います。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteDeleteStock()
            throws CommonException
    {
        // 削除在庫情報検索キー
        DeleteStockSearchKey deleteStock = new DeleteStockSearchKey();

        // データ削除
        dropRecords(deleteStock);
    }

    /**
     * 到着情報の削除を行います。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteArrival(Connection conn)
            throws CommonException
    {
        // 帳票発行履歴検索キー
        ArrivalSearchKey arrivalKey = new ArrivalSearchKey();
        // 到着日時(保持日数以上)
        arrivalKey.setArrivalDate(_arrivalHoldDate, COMPARE_LT);

        // 帳票発行履歴削除
        dropRecords(arrivalKey);
    }

    /**
     * 作業リスト情報の削除を行います。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteWorkList(Connection conn)
            throws CommonException
    {
        // 作業リスト情報検索キー
        WorkListSearchKey workListKey = new WorkListSearchKey();
        // 登録日時(保持日数以上)
        workListKey.setRegistDate(_delWorkListDate, COMPARE_LT);

        // 作業リスト情報削除
        dropRecords(workListKey);
    }

    /**
     * 帳票発行履歴の削除を行います。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deletePrintHistory(Connection conn)
            throws CommonException
    {
        // 帳票発行履歴検索キー
        PrintHistorySearchKey printHisKey = new PrintHistorySearchKey();
        // 発行日時(保持日数以上)
        printHisKey.setPrintDate(_printHistoryHoldDate, COMPARE_LT);

        // 帳票発行履歴削除
        dropRecords(printHisKey);
    }

    /**
     * 保持期間を過ぎたマスタのレコードを削除します。<BR>
     * マスタ保持期間が設定されていない場合はマスタ削除処理を行いません。<BR>
     * <ol>
     * 以下のマスタが対象です。
     *   <li>商品マスタ
     *   <li>仕入先マスタ
     *   <li>出荷先マスタ
     * </ol>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void deleteMaster(Connection conn)
            throws CommonException
    {
        // マスタ保持期限が設定されてる場合
        if (null != _masterHoldLimitDay)
        {
            //eWareNavi使用パッケージ導入時
            if (hasWmsPack())
            {
                // 商品マスタ削除
                delItemMaster(conn);

                // 仕入先マスタ削除
                delSupplierMaster(conn);

                // 出荷先マスタ削除
                delCustomerMaster(conn);
            }
        }
    }

    /**
     * 商品マスタ削除処理を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void delItemMaster(Connection conn)
            throws CommonException
    {
        // 商品マスタデータベースハンドラ
        ItemHandler itemH = new ItemHandler(conn);
        // 商品マスタ検索キー
        ItemSearchKey addKey = new ItemSearchKey();
        // WareNaviコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // マスタパッケージが導入されている場合
        if (wsysCtlr.hasMasterPack())
        {
            // 商品マスタ更新(商品固定棚該当)
            updateItemLastUse(FixedLocateInfo.STORE_NAME, itemH, null);
        }

        // 在庫パッケージが導入されている場合
        if (wsysCtlr.hasStockPack())
        {
            // 商品マスタ更新(在庫情報該当)
            updateItemLastUse(Stock.STORE_NAME, itemH, null);
        }

        // 入荷パッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack())
        {
            // 商品マスタ更新(入荷予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(ReceivingPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(ReceivingPlan.STORE_NAME, itemH, addKey);

            // 商品マスタ更新(入荷実績情報view該当)
            updateItemLastUse(ReceivingResultView.STORE_NAME, itemH, null);
        }

        // 入庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack())
        {
            // 商品マスタ更新(入庫予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(StoragePlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(StoragePlan.STORE_NAME, itemH, addKey);

            // 商品マスタ更新(棚卸作業情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(InventWorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(InventWorkInfo.STORE_NAME, itemH, addKey);

            // 商品マスタ更新(棚卸実績情報該当)
            updateItemLastUse(InventResult.STORE_NAME, itemH, null);
        }

        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            // 商品マスタ更新(出庫予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(RetrievalPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(RetrievalPlan.STORE_NAME, itemH, addKey);
        }

        // 入庫パッケージ、または出庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack() || wsysCtlr.hasRetrievalPack())
        {
            // 商品マスタ更新(実績情報view該当)
            updateItemLastUse(ResultView.STORE_NAME, itemH, null);
        }

        // AS/RSパッケージが導入されている場合
        if (wsysCtlr.hasAsrsPack())
        {
            // 商品マスタ更新(入出庫作業該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(WorkInfo.STORE_NAME, itemH, addKey);
        }

        // クロスドックパッケージが導入されている場合
        if (wsysCtlr.hasCrossdockPack())
        {
            // 商品マスタ更新(TC予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(CrossDockPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateItemLastUse(CrossDockPlan.STORE_NAME, itemH, addKey);

            // 商品マスタ更新(入荷実績情報view該当)
            updateItemLastUse(ReceivingResultView.STORE_NAME, itemH, null);

            // 商品マスタ更新(仕分実績情報view該当)
            updateItemLastUse(SortResultView.STORE_NAME, itemH, null);
        }

        // 出荷パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasShippingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 商品マスタ更新(出荷予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(ShipPlan.WORK_STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
            // バース状態フラグ(削除以外)
            addKey.setKey(ShipPlan.BERTH_STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
            updateItemLastUse(ShipPlan.STORE_NAME, itemH, addKey);

            // 商品マスタ更新(出荷実績情報view該当)
            updateItemLastUse(ShipResultView.STORE_NAME, itemH, null);
        }

        // 分析パッケージが導入されている場合
        if (wsysCtlr.hasAnalysisPack())
        {
            // 商品マスタ更新(作業単位数マスタ該当)
            updateItemLastUse(WorkingUnit.STORE_NAME, itemH, null);
        }

        // 商品マスタ検索キー
        ItemSearchKey itemDelKey = new ItemSearchKey();
        // システム管理区分(ユーザ)
        itemDelKey.setManagementType(Item.MANAGEMENT_TYPE_USER);
        // 最終使用日(保持日数以上)
        itemDelKey.setLastUsedDate(_masterHoldLimitDay, COMPARE_LT);

        // 商品マスタ削除
        dropRecords(itemDelKey);
    }

    /**
     * 商品マスタの最終使用日を更新します。<BR>
     * 結合対象テーブルに存在するデータは使用中とみなします。<BR>
     * <BR>
     * @param joinTable 結合対象テーブル
     * @param h データベースハンドラ
     * @param addSkey 副問い合わせキー追加条件
     * @throws ReadWriteException データベースアクセスエラー
     */
    private void updateItemLastUse(String joinTable, DatabaseHandler h, ItemSearchKey addSkey)
            throws CommonException
    {
        // 商品マスタ更新キー
        ItemAlterKey itemAkey = new ItemAlterKey();
        // 商品マスタ検索キー(荷主用)
        ItemSearchKey itemCoSkey = new ItemSearchKey();
        // 商品マスタ検索キー(商品用)
        ItemSearchKey itemItSkey = new ItemSearchKey();

        try
        {
            // パラメータの追加キーが存在した場合
            if (addSkey != null)
            {
                // 商品用検索キーに追加
                itemItSkey.setKey(addSkey);
                // 荷主用検索キーに追加
                itemCoSkey.setKey(addSkey);
            }

            // 商品マスタ(荷主副問合せ)
            // 重複データ非表示
            itemCoSkey.setConsignorCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            itemCoSkey.setJoin(Item.CONSIGNOR_CODE, new FieldName(joinTable, Item.CONSIGNOR_CODE.getName()));
            // 商品コード(結合対象テーブルの商品コード)
            itemCoSkey.setJoin(Item.ITEM_CODE, new FieldName(joinTable, Item.ITEM_CODE.getName()));

            // 商品マスタ(商品副問合せ)
            // 重複データ非表示
            itemItSkey.setItemCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            itemItSkey.setJoin(Item.CONSIGNOR_CODE, new FieldName(joinTable, Item.CONSIGNOR_CODE.getName()));
            // 商品コード(結合対象テーブルの商品コード)
            itemItSkey.setJoin(Item.ITEM_CODE, new FieldName(joinTable, Item.ITEM_CODE.getName()));

            // 商品マスタ(更新キー)
            // 荷主コード(副問合せで取得した荷主コード)
            itemAkey.setKey(Item.CONSIGNOR_CODE, itemCoSkey);
            // 商品コード(副問合せで取得した商品コード)
            itemAkey.setKey(Item.ITEM_CODE, itemItSkey);
            // 最終使用日(システム日付)
            itemAkey.updateLastUsedDate(new SysDate());
            // 最終更新処理名(クラス名)
            itemAkey.updateLastUpdatePname(PROC_NAME);

            // 商品マスタ更新
            h.modify(itemAkey);
        }
        // データが存在しない場合
        catch (NotFoundException e)
        {
            // データが存在しなくても問題ないため、処理続行
        }
    }

    /**
     * 仕入先マスタ削除処理を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void delSupplierMaster(Connection conn)
            throws CommonException
    {
        // 仕入先マスタデータベースハンドラ
        SupplierHandler supplierHand = new SupplierHandler(conn);
        // 仕入先マスタ検索キー
        SupplierSearchKey addKey = new SupplierSearchKey();
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // 入荷パッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack())
        {
            // 仕入先マスタ更新(入荷予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(ReceivingPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateSuppLastUse(ReceivingPlan.STORE_NAME, supplierHand, addKey);

            // 仕入先マスタ更新(入荷実績情報view該当)
            updateSuppLastUse(ReceivingResultView.STORE_NAME, supplierHand, null);
        }

        // 入庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack())
        {
            // 仕入先マスタ更新(入庫予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(StoragePlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateSuppLastUse(StoragePlan.STORE_NAME, supplierHand, addKey);

            // 仕入先マスタ更新(入出庫実績情報view該当)
            updateSuppLastUse(ResultView.STORE_NAME, supplierHand, null);
        }

        // AS/RSパッケージが導入されている場合
        if (wsysCtlr.hasAsrsPack())
        {
            // 仕入先マスタ更新(入出庫作業)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateSuppLastUse(WorkInfo.STORE_NAME, supplierHand, addKey);
        }

        // クロスドックパッケージが導入されている場合
        if (wsysCtlr.hasCrossdockPack())
        {
            // 仕入先マスタ更新(TC予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(CrossDockPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateSuppLastUse(CrossDockPlan.STORE_NAME, supplierHand, addKey);

            // 仕入先マスタ更新(入荷実績情報view該当)
            updateSuppLastUse(ReceivingResultView.STORE_NAME, supplierHand, null);
        }

        // 仕入先マスタ検索キー
        SupplierSearchKey suppDropKey = new SupplierSearchKey();
        // 最終使用日(保持日数以上)
        suppDropKey.setLastUsedDate(_masterHoldLimitDay, COMPARE_LT);

        // 仕入先マスタ(削除)
        dropRecords(suppDropKey);
    }

    /**
     * 仕入先マスタの最終使用日を更新します。<BR>
     * 結合対象テーブルに存在するデータは使用中とみなします。<BR>
     * <BR>
     * @param joinTable 結合対象テーブル
     * @param h データベースハンドラ
     * @param addSkey 副問い合わせキー追加条件
     * @throws ReadWriteException データベースアクセスエラー
     */
    private void updateSuppLastUse(String joinTable, DatabaseHandler h, SupplierSearchKey addSkey)
            throws CommonException
    {
        // 仕入先マスタ更新キー
        SupplierAlterKey suppAkey = new SupplierAlterKey();
        // 仕入先マスタ検索キー(荷主用)
        SupplierSearchKey suppCkey = new SupplierSearchKey();
        // 仕入先マスタ検索キー(仕入先用)
        SupplierSearchKey suppSkey = new SupplierSearchKey();

        try
        {
            // パラメータの追加キーが存在した場合
            if (addSkey != null)
            {
                // 仕入先用検索キーに追加
                suppSkey.setKey(addSkey);
                // 荷主用検索キーに追加
                suppCkey.setKey(addSkey);
            }

            // 仕入先マスタ(荷主副問合せ)
            // 重複データ非表示
            suppCkey.setConsignorCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            suppCkey.setJoin(Supplier.CONSIGNOR_CODE, new FieldName(joinTable, Supplier.CONSIGNOR_CODE.getName()));
            // 仕入先コード(結合対象テーブルの仕入先コード)
            suppCkey.setJoin(Supplier.SUPPLIER_CODE, new FieldName(joinTable, Supplier.SUPPLIER_CODE.getName()));

            // 仕入先マスタ(仕入先副問合せ)
            // 重複データ非表示
            suppSkey.setSupplierCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            suppSkey.setJoin(Supplier.CONSIGNOR_CODE, new FieldName(joinTable, Supplier.CONSIGNOR_CODE.getName()));
            // 仕入先コード(結合対象テーブルの仕入先コード)
            suppSkey.setJoin(Supplier.SUPPLIER_CODE, new FieldName(joinTable, Supplier.SUPPLIER_CODE.getName()));

            // 仕入先マスタ(更新キー)
            // 荷主コード(副問合せで取得した荷主コード)
            suppAkey.setKey(Supplier.CONSIGNOR_CODE, suppCkey);
            // 仕入先コード(副問合せで取得した仕入先コード)
            suppAkey.setKey(Supplier.SUPPLIER_CODE, suppSkey);
            // 最終使用日(システム日付)
            suppAkey.updateLastUsedDate(new SysDate());
            // 最終更新処理名(クラス名)
            suppAkey.updateLastUpdatePname(PROC_NAME);

            // 仕入先マスタ更新
            h.modify(suppAkey);
        }
        catch (NotFoundException ex)
        {
            // データが存在しなくても問題ないため、処理続行
        }
    }

    /**
     * 出荷先マスタ削除処理を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void delCustomerMaster(Connection conn)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        CustomerHandler customerHand = new CustomerHandler(conn);
        // 出荷先マスタ検索キー
        CustomerSearchKey addKey = new CustomerSearchKey();
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            // 出荷先マスタ更新(出庫予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(RetrievalPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateCustLastUse(RetrievalPlan.STORE_NAME, customerHand, addKey);

            // 出荷先マスタ更新(入出庫実績情報view該当)
            updateCustLastUse(ResultView.STORE_NAME, customerHand, null);
        }

        // クロスドックパッケージが導入されている場合
        if (wsysCtlr.hasCrossdockPack())
        {
            // 出荷先マスタ更新(TC予定情報該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(CrossDockPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateCustLastUse(CrossDockPlan.STORE_NAME, customerHand, addKey);

            // 出荷先マスタ更新(仕分実績情報view該当)
            updateCustLastUse(SortResultView.STORE_NAME, customerHand, null);
        }

        // 出荷パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasShippingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 出荷先マスタ更新(出荷予定情報該当)
            addKey.clear();
            // 出荷検品状態フラグ(削除以外)
            addKey.setKey(ShipPlan.WORK_STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
            // バース状態フラグ(削除以外)
            addKey.setKey(ShipPlan.BERTH_STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
            updateCustLastUse(ShipPlan.STORE_NAME, customerHand, addKey);

            // 出荷先マスタ更新(出荷実績情報view該当)
            updateCustLastUse(ShipResultView.STORE_NAME, customerHand, null);
        }

        // AS/RSパッケージが導入されている場合
        if (wsysCtlr.hasAsrsPack())
        {
            // 出荷先マスタ更新(入出庫作業該当)
            addKey.clear();
            // 状態フラグ(削除以外)
            addKey.setKey(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updateCustLastUse(WorkInfo.STORE_NAME, customerHand, addKey);
        }

        // 出荷先マスタ検索キー
        CustomerSearchKey custDelKey = new CustomerSearchKey();
        // 最終使用日(保持日数以上)
        custDelKey.setLastUsedDate(_masterHoldLimitDay, COMPARE_LT);

        // 出荷先マスタ削除
        dropRecords(custDelKey);
    }

    /**
     * 一時商品データ削除処理を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void deleteTempItem(Connection conn)
            throws CommonException
    {

        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        DirectDBHandler ddbhandler = null;
        StringBuffer sql = new StringBuffer();

        // 在庫データ及び各予定データに存在しない一時商品を検索
        sql.append(" SELECT DMItem.item_code FROM DMItem ").append(
                " WHERE DMItem.management_type = " + DBFormat.format(Item.MANAGEMENT_TYPE_USER)).append(
                " AND   DMItem.temporary_type = " + DBFormat.format(Item.TEMPORARY_TYPE_TEMPORARY)).append(
                " AND   DMItem.item_code IN ").append("  ( SELECT DMItem.item_code FROM DMItem ");
        // 在庫パッケージが導入されている場合
        if (wsysCtlr.hasStockPack())
        {
            sql.append("    MINUS ").append("    SELECT DNStock.item_code FROM DNStock ");
        }
        // 入荷パッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack())
        {
            sql.append("    MINUS ").append("    SELECT DNReceivingPlan.item_code FROM DNReceivingPlan ");
        }
        // 入庫パッケージが導入されている場合
        // かつ DA版の場合のみ
        if (wsysCtlr.hasStoragePack() && !wsysCtlr.isFaDaEnabled())
        {
            sql.append("    MINUS ").append("    SELECT DNStoragePlan.item_code FROM DNStoragePlan ");
        }
        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            sql.append("    MINUS ").append("    SELECT DNRetrievalPlan.item_code FROM DNRetrievalPlan ");
        }
        // クロスドックパッケージが導入されている場合
        if (wsysCtlr.hasCrossdockPack())
        {
            sql.append("    MINUS ").append("    SELECT DNCrossDockPlan.item_code FROM DNCrossDockPlan ");
        }
        // 出荷パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasShippingPack() || wsysCtlr.hasCrossdockPack())
        {
            sql.append("    MINUS ").append("    SELECT DNShipPlan.item_code FROM DNShipPlan ");
        }
        sql.append("  ) ");

        try
        {
            ddbhandler = new DefaultDDBHandler(getConnection());
            // 検索処理
            Item[] items = (Item[])ddbhandler.query(String.valueOf(sql), new Item());
            if (items == null || items.length == 0)
            {
                // 削除可能な一時商品が無い場合は抜ける
                return;
            }

            // 商品マスタ検索キー
            ItemSearchKey itemDelKey = new ItemSearchKey();

            ItemHandler itemhandler = new ItemHandler(getConnection());

            for (Item item : items)
            {
                itemDelKey.clear();
                itemDelKey.setItemCode(item.getItemCode());

                // 一時商品削除
                dropRecords(itemDelKey);

                if (itemhandler.count(itemDelKey) == 0)
                {
                    continue;
                }
            }
        }
        finally
        {
            if (ddbhandler != null)
            {
                ddbhandler.close();
            }
        }

    }

    /**
     * 検索キーに合致するレコードを削除します。<BR>
     * <BR>
     * @param key 削除対象データを絞り込むキー
     * @return 削除対象があったとき true
     * @throws ReadWriteException データベースアクセスエラー
     */
    private boolean dropRecords(SQLSearchKey key)
            throws ReadWriteException
    {
        // データベースハンドラ
        DatabaseHandler h = _sharedHandler;

        try
        {
            // 対象データ削除
            h.drop(key);

            // 正常の場合はtrueを返却
            return true;
        }
        // データが存在しない場合
        catch (NotFoundException e)
        {
            // 異常の場合はfalseを返却
            return false;
        }
    }

    /**
     * 出荷先マスタの最終使用日を更新します。<BR>
     * 結合対象テーブルに存在するデータは使用中とみなします。<BR>
     * <BR>
     * @param joinTable 結合対象テーブル
     * @param h データベースハンドラ
     * @param addSkey 副問い合わせキー追加条件
     * @throws ReadWriteException データベースアクセスエラー
     */
    private void updateCustLastUse(String joinTable, DatabaseHandler h, CustomerSearchKey addSkey)
            throws CommonException
    {
        // 出荷先マスタ更新キー
        CustomerAlterKey custAkey = new CustomerAlterKey();
        // 出荷先マスタ検索キー(荷主用)
        CustomerSearchKey custCkey = new CustomerSearchKey();
        // 出荷先マスタ検索キー(出荷先用)
        CustomerSearchKey custSkey = new CustomerSearchKey();

        try
        {
            // パラメータの追加キーが存在した場合
            if (addSkey != null)
            {
                // 荷主用検索キーに追加
                custCkey.setKey(addSkey);
                // 出荷先用検索キーに追加
                custSkey.setKey(addSkey);
            }

            // 出荷先マスタ(荷主副問合せ)
            // 重複データ非表示
            custCkey.setConsignorCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            custCkey.setJoin(Customer.CONSIGNOR_CODE, new FieldName(joinTable, Customer.CONSIGNOR_CODE.getName()));
            // 出荷先コード(結合対象テーブルの出荷先コード)
            custCkey.setJoin(Customer.CUSTOMER_CODE, new FieldName(joinTable, Customer.CUSTOMER_CODE.getName()));

            // 出荷先マスタ(出荷先副問合せ)
            // 重複データ非表示
            custSkey.setCustomerCodeCollect("DISTINCT");
            // 荷主コード(結合対象テーブルの荷主コード)
            custSkey.setJoin(Customer.CONSIGNOR_CODE, new FieldName(joinTable, Customer.CONSIGNOR_CODE.getName()));
            // 出荷先コード(結合対象テーブルの出荷先コード)
            custSkey.setJoin(Customer.CUSTOMER_CODE, new FieldName(joinTable, Customer.CUSTOMER_CODE.getName()));

            // 出荷先マスタ(更新キー)
            // 荷主コード(副問合せで取得した荷主コード)
            custAkey.setKey(Customer.CONSIGNOR_CODE, custCkey);
            // 出荷先コード(副問合せで取得した出荷先コード)
            custAkey.setKey(Customer.CUSTOMER_CODE, custSkey);
            // 最終使用日(システム日付)
            custAkey.updateLastUsedDate(new SysDate());
            // 最終更新処理名(クラス名)
            custAkey.updateLastUpdatePname(PROC_NAME);

            // 出荷先マスタ更新
            h.modify(custAkey);
        }
        // データが存在しない場合
        catch (NotFoundException e)
        {
            // データが存在しなくても問題ないため、処理続行
        }
    }

    /**
     * 不要な予定、作業データの削除を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param param スケジュールパラメータの配列
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteWorkingData(Connection conn, SystemInParameter[] param)
            throws CommonException
    {
        // 未処理の作業情報を残すか、削除するかのフラグを参照します。
        String displayCondition = param[0].getDisplayCondition();
        boolean isDeleteUnWork = SystemInParameter.UNSTART_DELETE.equals(displayCondition);
        // 削除日付
        String deleteDate = _delPlanDate;
        // 実績削除日付
        String resultDelDay = _delResultDate;
        // 欠品削除日付
        Date delShortDate = _delShortageDate;
        // WareNaviコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // AS/RSパッケージが導入されている場合
        if (wsysCtlr.hasAsrsPack())
        {
            // 再入庫予定データ印刷
            if (!printReStoringDeleteList())
            {
                _isReStoringPrintFailure = true;
            }
            // 再入庫予定データ削除
            deleteReStoringPlan(conn);

            // ASRS在庫確認情報データ(削除)
            deleteInventoryCheck(conn);

            // ASRS稼動実績情報データ(削除)
            deleteInOutResult(conn, resultDelDay);

            // Sequence初期化
            resetSequence(conn);
        }

        // 入庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack() && !wsysCtlr.isFaDaEnabled())
        {
            // 入庫予定情報(削除)
            deletePlans(StoragePlan.STORE_NAME, conn, isDeleteUnWork);

            // 保持期限超過情報(削除)
            deleteExpireStorage(conn, deleteDate, resultDelDay);

            // 棚卸実績情報(削除)
            deleteInventResult(conn, resultDelDay);

            // 棚卸設定情報(削除)
            deleteInventSetting(conn);
        }

        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            // 予定情報(削除)
            deletePlans(RetrievalPlan.STORE_NAME, conn, isDeleteUnWork);

            // 保持期限超過情報(削除)
            deleteExpireRetrieval(conn, deleteDate, resultDelDay);
        }

        // 在庫パッケージが導入されている場合
        if (wsysCtlr.hasStockPack())
        {
            // 予定外情報(削除)
            deleteNoPlan(conn);

            // 保持期間が過ぎた予定外の作業情報と実績送信情報を
            // 実績保存期間で処理していたので予定と同じ保持期間に変更
            deleteExprireNoPlan(deleteDate);

            // DA版の場合のみ
            if (!wsysCtlr.isFaDaEnabled())
            {
                // 移動作業データ(削除)
                deleteMovement(conn);

                // 移動実績データ(削除)
                deleteResult(conn, resultDelDay);
            }

            // 在庫パッケージ、かつ出庫パッケージが導入されている場合
            if (wsysCtlr.hasRetrievalPack())
            {
                // 欠品情報(削除)
                deleteShortageInformation(conn, delShortDate);

                // DA版の場合のみ
                if (!wsysCtlr.isFaDaEnabled())
                {
                    // 補充欠品情報(削除)
                    deleteReplenishShortageInformation(conn, delShortDate);
                }
            }

            // 在庫更新履歴データ(削除)
            deleteStockHistory(conn, resultDelDay);
        }

        // クロスドックパッケージが導入されている場合
        if (wsysCtlr.hasCrossdockPack())
        {
            // TC入荷・仕分予定データ(削除)
            deleteTCPlan(conn, isDeleteUnWork);

            // TC入荷・仕分保存期間超過(削除)
            deleteTCResult(conn, deleteDate, resultDelDay);

            // 出荷予定(削除)
            deleteShipPlan(conn, isDeleteUnWork);

            // 出荷保存期間超過(削除)
            deleteShipResult(conn, deleteDate, resultDelDay);
        }

        // 入荷パッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack())
        {
            // 入荷予定データ(削除)
            deleteReceivingPlan(conn, isDeleteUnWork);
            // 入荷実績情報(削除)
            deleteReceivingResult(conn, deleteDate, resultDelDay);
        }

        // PCT出庫パッケージありの場合
        if (wsysCtlr.hasPCTRetrievalPack())
        {
            // PCT出庫予定情報の削除を行ないます。
            deletePCTRetrieval(conn, isDeleteUnWork, deleteDate, resultDelDay);

            // オーダー情報の削除を行ないます。
            deleteOrderInfo(conn);

            // ユーザ実績情報の削除を行ないます。
            deletePCTUserResult(conn);

            // ピッキング実績集計情報の削除を行ないます。
            deletePCTPickingResult(conn, _delPickingDate);

            // 重量差異情報の削除を行ないます。
            deleteWeightDistinct(conn, _delWeightDate);

            // 全ユーザ実績情報の削除を行ないます。
            deletePCTAllUserResult(conn, _delAllUserDate);

            // PCT操作ログの削除を行ないます。
            deletePCTOperationLog(conn, _delOperationLog);
        }

        // eWareNavi使用パッケージ導入時
        if (hasWmsPack())
        {
            // 作業者実績情報の削除を行います。
            deleteWokerResult(conn, resultDelDay);
        }
    }

    /**
     * WareNaviSystemの作業日を一日進めます。<BR>
     * WareNaviSystemの日次更新日時をDB日付で更新します。<BR>
     * <BR>
     * @param userInfo WmsUserInfoオブジェクト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void changeWorkDate(WmsUserInfo userInfo)
            throws CommonException
    {
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;
        // 作業日を取得
        Date wday = WmsFormatter.getDate(wsysCtlr.getWorkDay(), WMSConstants.PARAM_DATE_FORMAT);

        // 作業用Calendarオブジェクト生成
        GregorianCalendar cal = new GregorianCalendar();
        // 作業日を設定
        cal.setTime(wday);
        // 作業日を一日進める
        cal.add(GregorianCalendar.DATE, 1);

        // 生成した作業日をフォーマット
        DateFormat fmt = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
        String nextDay = fmt.format(cal.getTime());

        // 作業日と日次更新日時を更新する
        wsysCtlr.updateWorkDay(nextDay, userInfo);
        wsysCtlr.updateDailyUpdateDate();
    }

    /**
     * 作業日を営業日情報に登録します。<BR>
     * <BR>
     * @throws DataExistsException 不正データが存在し場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException データの不整合が発生した場合に通知されます。
     */
    protected void registBusinessDay()
            throws ReadWriteException,
                DataExistsException,
                NotFoundException
    {
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;
        // 作業日を取得
        String wday = wsysCtlr.getWorkDay();

        // 営業日情報データベースハンドラー
        OperationDayHandler keepDateHnd = new OperationDayHandler(getConnection());
        // 営業日情報検索キー
        OperationDaySearchKey sKey = new OperationDaySearchKey();
        // 営業日(作業日)
        sKey.setOperationDay(wday);

        // 営業日情報に存在しない場合
        if (keepDateHnd.count(sKey) == 0)
        {
            // 営業日情報エンティティ
            OperationDay ent = new OperationDay();

            // 営業日(作業日)
            ent.setOperationDay(wday);
            // 営業日情報(新規登録)
            keepDateHnd.create(ent);
        }

        // 営業日情報検索キークリア
        sKey.clearKeys();
        // 営業日取得
        sKey.setOperationDayCollect();
        // 並び順(営業日降順)
        sKey.setOperationDayOrder(false);
        // 検索
        OperationDay[] keep = (OperationDay[])keepDateHnd.find(sKey);

        // 営業日テーブル最大件数以上の場合
        int maxData = WmsParam.OPERATION_DAY_MAX;
        if (keep.length > maxData)
        {
            // 営業日(最大件数以上は削除)
            sKey.setOperationDay(keep[maxData].getOperationDay(), "<=");

            // 営業日情報(削除)
            keepDateHnd.drop(sKey);
        }
    }

    /**
     * 保持期間が超過した予定入庫の情報の削除処理を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 作業情報削除境界日時
     * @param resultDelDay 実績情報削除境界日時
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void deleteExpireStorage(Connection conn, String deleteDate, String resultDelDay)
            throws CommonException
    {
        // 作業情報削除境界日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 入庫予定情報検索キー
            StoragePlanSearchKey planKey = new StoragePlanSearchKey();
            // 予定日(作業情報削除境界日時超過)
            planKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            // 予定一意キー(空白以外)
            planKey.setPlanUkey("", "!=");

            // 入庫予定情報(削除)
            dropRecords(planKey);
        }
        // 予定入庫作業・実績(保持期限超過)
        deleteExpireWork(deleteDate, resultDelDay, SystemDefine.JOB_TYPE_STORAGE);
    }

    /**
     * 保持期間が超過した予定出庫の情報の削除処理を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 作業情報削除境界日時
     * @param resultDelDay 実績情報削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteExpireRetrieval(Connection conn, String deleteDate, String resultDelDay)
            throws ReadWriteException
    {
        // 作業情報削除境界日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 出庫予定情報検索キー
            RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
            // 予定日(作業情報削除境界日時超過)
            planKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            // 予定一意キー(空白以外)
            planKey.setPlanUkey("", "!=");

            // 在庫パッケージが導入されている場合
            if (_wsysCtlr.hasStockPack())
            {
                // 状態フラグ(未作業)
                planKey.setKey(RetrievalPlan.STATUS_FLAG, RetrievalPlan.STATUS_FLAG_UNSTART, "=", "NOT(", "", true);
                // スケジュール処理フラグ(スケジュール済み)
                planKey.setKey(RetrievalPlan.SCH_FLAG, RetrievalPlan.SCH_FLAG_SCHEDULE, "=", "", ")", true);
            }
            // 出庫予定情報(削除)
            dropRecords(planKey);
        }
        // 予定出庫作業・実績(保持期限超過)
        deleteExpireWork(deleteDate, resultDelDay, SystemDefine.JOB_TYPE_RETRIEVAL);
    }

    /**
     * 保持期間が超過した予定外の作業情報・実績を削除します。<BR>
     * 作業情報の予定日がdeleteDate(同一日を含む)よりも古いものを削除します。<BR>
     * <BR>
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteExprireNoPlan(String deleteDate)
            throws ReadWriteException
    {
        // 削除境界日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 入出庫作業情報検索キー
            WorkInfoSearchKey workKey = new WorkInfoSearchKey();
            // 予定日(削除境界日時超過)
            workKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            // 予定一意キー(空白)
            workKey.setPlanUkey("");
            // 入出庫作業情報(削除)
            dropRecords(workKey);

            // 入出庫実績送信情報検索キー
            HostSendSearchKey hostKey = new HostSendSearchKey();
            // 予定日(削除境界日時超過)
            hostKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            // 予定一意キー(空白)
            hostKey.setPlanUkey("");
            // 入出庫実績送信情報(削除)
            dropRecords(hostKey);
        }
    }

    /**
     * 予定外作業情報削除処理を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void deleteNoPlan(Connection conn)
            throws CommonException
    {
        // 入出庫実績送信情報検索キー
        HostSendSearchKey hostKey = new HostSendSearchKey();
        // 予定一意キー(空白)
        hostKey.setPlanUkey("");
        // 状態フラグ(完了)
        hostKey.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
        // 実績報告区分(報告済)
        hostKey.setReportFlag(HostSend.REPORT_FLAG_REPORT);

        // 入出庫実績送信情報から入出庫実績情報(データ移動)
        moveHostSendToResultNoPlan(hostKey);
    }

    /**
     * ASRS在庫確認情報情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteInventoryCheck(Connection conn)
            throws ReadWriteException
    {
        // AS/RS在庫確認情報検索キー
        InventoryCheckSearchKey inventorySearckKey = new InventoryCheckSearchKey();
        // 状態フラグ(在庫確認未作業)
        inventorySearckKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NO_CONFIRM);

        // AS/RS在庫確認情報(削除)
        dropRecords(inventorySearckKey);
    }

    /**
     * 移動作業情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteMovement(Connection conn)
            throws CommonException
    {
        // 移動作業情報検索キー
        MoveWorkInfoSearchKey moveSearchKey = new MoveWorkInfoSearchKey();
        // 状態フラグ(完了・入庫キャンセル・削除)
        String[] status = {
                MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_CANCEL,
                MoveWorkInfo.STATUS_FLAG_COMPLETION,
                MoveWorkInfo.STATUS_FLAG_DELETE,
        };
        moveSearchKey.setStatusFlag(status, false);

        // 移動作業情報(削除)
        dropRecords(moveSearchKey);
    }

    /**
     * 移動実績情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param delPlanDate 実績データ削除日時
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteResult(Connection conn, String delPlanDate)
            throws CommonException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(delPlanDate))
        {
            // 移動実績情報検索キー
            MoveResultSearchKey moveSearchKey = new MoveResultSearchKey();
            // 移動入庫作業日(削除日時超過)
            moveSearchKey.setStorageWorkDay(delPlanDate, COMPARE_LT_EQ, "", "", true);

            // 移動実績情報(削除)
            dropRecords(moveSearchKey);
        }
    }

    /**
     * 保存期限を越えた入荷情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param delPlanDate 予定データ削除日時
     * @param resultDelDay 実績データ削除日時
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteReceivingResult(Connection conn, String delPlanDate, String resultDelDay)
            throws CommonException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(delPlanDate))
        {
            // 入荷予定情報検索キー
            ReceivingPlanSearchKey pKey = new ReceivingPlanSearchKey();
            pKey.clear();
            // 予定一意キー(NULL以外)
            pKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            pKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(DC)
            pKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);
            // 入荷予定情報(削除)
            dropRecords(pKey);

            // 入荷作業情報検索キー
            ReceivingWorkInfoSearchKey wKey = new ReceivingWorkInfoSearchKey();
            wKey.clear();
            // 予定一意キー(NULL以外)
            wKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            wKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(DC)
            wKey.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_DC);
            // 入荷作業情報(削除)
            dropRecords(wKey);

            // 入荷実績送信情報検索キー
            ReceivingHostSendSearchKey hKey = new ReceivingHostSendSearchKey();
            hKey.clear();
            // 予定一意キー(NULL以外)
            hKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            hKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(DC)
            hKey.setTcdcFlag(ReceivingHostSend.TCDC_FLAG_DC);
            // 入荷実績送信情報(削除)
            dropRecords(hKey);

            // 入荷実績情報検索キー
            ReceivingResultSearchKey rKey = new ReceivingResultSearchKey();
            rKey.clear();
            // 作業日(削除日時超過)
            rKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);
            // TC/DCフラグ(DC)
            rKey.setTcdcFlag(ReceivingHostSend.TCDC_FLAG_DC);
            // 入荷実績情報(削除)
            dropRecords(rKey);
        }
    }

    /**
     * 保存期限を越えた入荷、仕分情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param delPlanDate 予定データ削除日時
     * @param resultDelDay 実績データ削除日時
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteTCResult(Connection conn, String delPlanDate, String resultDelDay)
            throws CommonException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(delPlanDate))
        {
            // TC予定情報検索キー
            CrossDockPlanSearchKey pKey = new CrossDockPlanSearchKey();
            pKey.clear();
            // 予定日(削除日時超過)
            pKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // 予定一意キー(NULL以外)
            pKey.setPlanUkey("", "IS NOT NULL");
            // TC予定情報(削除)
            dropRecords(pKey);

            // 入荷作業情報検索キー
            ReceivingWorkInfoSearchKey rwKey = new ReceivingWorkInfoSearchKey();
            rwKey.clear();
            // 予定一意キー(NULL以外)
            rwKey.setPlanUkey("", "IS NOT NULL");
            // TC/DCフラグ(TC)
            rwKey.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_TC);
            // 予定日(削除日時超過)
            rwKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // 入荷作業情報(削除)
            dropRecords(rwKey);

            // 入荷実績送信情報検索キー
            ReceivingHostSendSearchKey rhKey = new ReceivingHostSendSearchKey();
            rhKey.clear();
            // 予定一意キー(NULL以外)
            rhKey.setPlanUkey("", "IS NOT NULL");
            // TC/DCフラグ(TC)
            rhKey.setTcdcFlag(ReceivingHostSend.TCDC_FLAG_TC);
            // 予定日(削除日時超過)
            rhKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // 入荷実績送信情報(削除)
            dropRecords(rhKey);

            // 入荷実績情報検索キー
            ReceivingResultSearchKey rrKey = new ReceivingResultSearchKey();
            rrKey.clear();
            // TC/DCフラグ(TC)
            rrKey.setTcdcFlag(ReceivingResult.TCDC_FLAG_TC);
            // 作業日(削除日時超過)
            rrKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);
            // 入荷実績情報(削除)
            dropRecords(rrKey);

            // 仕分作業情報検索キー
            SortWorkInfoSearchKey swKey = new SortWorkInfoSearchKey();
            swKey.clear();
            // 予定日(削除日時超過)
            swKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // 予定一意キー(NULL以外)
            swKey.setPlanUkey("", "IS NOT NULL");
            // 仕分作業情報(削除)
            dropRecords(swKey);

            // 仕分実績送信情報検索キー
            SortHostSendSearchKey shKey = new SortHostSendSearchKey();
            shKey.clear();
            // 予定日(削除日時超過)
            shKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // 予定一意キー(NULL以外)
            shKey.setPlanUkey("", "IS NOT NULL");
            // 仕分実績送信情報(削除)
            dropRecords(shKey);

            // 仕分実績情報検索キー
            SortResultSearchKey srKey = new SortResultSearchKey();
            srKey.clear();
            // 作業日(削除日時超過)
            srKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);
            // 仕分実績情報(削除)
            dropRecords(srKey);
        }
    }

    /**
     * 保存期限を越えた出荷情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param delPlanDate 予定データ削除日時
     * @param resultDelDay 実績データ削除日時
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteShipResult(Connection conn, String delPlanDate, String resultDelDay)
            throws CommonException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(delPlanDate))
        {
            // 出荷予定情報検索キー
            ShipPlanSearchKey pKey = new ShipPlanSearchKey();
            pKey.clear();
            // 予定一意キー(NULL以外)
            pKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            pKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(TC)
            pKey.setTcdcFlag(ShipPlan.TCDC_FLAG_TC);
            // 出荷予定情報(削除)
            dropRecords(pKey);

            // 出荷作業情報検索キー
            ShipWorkInfoSearchKey wKey = new ShipWorkInfoSearchKey();
            wKey.clear();
            // 予定一意キー(NULL以外)
            wKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            wKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(TC)
            wKey.setTcdcFlag(ShipWorkInfo.TCDC_FLAG_TC);
            // 出荷作業情報(削除)
            dropRecords(wKey);

            // 出荷実績送信情報検索キー
            ShipHostSendSearchKey hKey = new ShipHostSendSearchKey();
            hKey.clear();
            // 予定一意キー(NULL以外)
            hKey.setPlanUkey("", "IS NOT NULL");
            // 予定日(削除日時超過)
            hKey.setPlanDay(delPlanDate, COMPARE_LT_EQ);
            // TC/DCフラグ(TC)
            hKey.setTcdcFlag(ShipHostSend.TCDC_FLAG_TC);
            // 出荷実績送信情報(削除)
            dropRecords(hKey);

            // 出荷実績情報検索キー
            ShipResultSearchKey rKey = new ShipResultSearchKey();
            rKey.clear();
            // 作業日(削除日時超過)
            rKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);
            // TC/DCフラグ(TC)
            rKey.setTcdcFlag(ShipResult.TCDC_FLAG_TC);
            // 出荷実績情報(削除)
            dropRecords(rKey);
        }
    }

    /**
     * 出荷実績送信情報削除処理を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void deleteShipHostSend(Connection conn)
            throws CommonException
    {
        // 出荷実績送信情報検索キー
        ShipHostSendSearchKey hostKey = new ShipHostSendSearchKey();
        // 予定一意キー(空白)
        hostKey.setPlanUkey("");
        // 出荷検品状態フラグ(完了)
        hostKey.setWorkStatusFlag(ShipHostSend.STATUS_FLAG_COMPLETION);
        // バース状態フラグ(完了)
        hostKey.setBerthStatusFlag(ShipHostSend.BERTH_STATUS_FLAG_COMPLETION);
        // 実績報告区分(報告済み)
        hostKey.setReportFlag(ShipHostSend.REPORT_FLAG_REPORT);

        // 入出庫実績送信情報から入出庫実績情報(データ移動)
        moveHostSendToResultNoPlan(hostKey);
    }

    /**
     * 入出庫実績送信情報から、入出庫実績情報へのデータ移動を行います。<BR>
     * <BR>
     * @param hostKey 入出庫実績送信情報検索キー
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void moveHostSendToResultNoPlan(SQLSearchKey hostKey)
            throws CommonException
    {
        // 入出庫実績送信情報ファインダー
        HostSendFinder hostSendFinder = new HostSendFinder(getConnection());
        // 入出庫実績送信情報エンティティ
        HostSend[] hostSends = null;

        try
        {
            // 入出庫作業情報検索キー
            WorkInfoSearchKey sKey = new WorkInfoSearchKey();

            // 検索
            hostSendFinder.open(true);
            if (hostSendFinder.search(hostKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得した実績送信情報件数分、繰り返す
            while (hostSendFinder.hasNext())
            {
                // 100件毎取得
                Entity[] plans = hostSendFinder.getEntities(100);

                // 入出庫実績情報へデータコピー
                hostSends = (HostSend[])plans;
                Result[] results = entReplace(hostSends);

                // 入出庫実績情報データベースハンドラ
                ResultHandler resultHandler = new ResultHandler(getConnection());

                // 取得した実績情報件数分、繰り返す
                for (int i = 0; i < results.length; i++)
                {
                    // 入出庫実績情報エンティティ
                    Result result = results[i];
                    // 入出庫実績情報(新規登録)
                    resultHandler.create(result);

                    // 入出庫作業情報検索キークリア
                    sKey.clear();
                    // 作業No.(入出庫実績情報の作業No.)
                    sKey.setJobNo(result.getJobNo());
                    // 入出庫作業情報(削除)
                    dropRecords(sKey);
                }
            }
            // 入出庫作業情報検索キークリア
            sKey.clear();
            // 状態フラグ(削除)
            sKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE);
            // 入出庫作業情報(削除)
            dropRecords(sKey);

            // 入出庫実績送信情報(削除)
            dropRecords(hostKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(hostSendFinder);
        }
    }

    /**
     * HostSendエンティティからResultエンティティに入れ替えます。<BR>
     * <BR>
     * @param hostSends HostSendの配列情報
     * @return Result[] Resultの配列情報
     */
    protected Result[] entReplace(HostSend[] hostSends)
    {
        // 入出庫実績情報エンティティ配列
        Result[] results = new Result[hostSends.length];

        // 件数分、繰り返す
        for (int i = 0; i < hostSends.length; i++)
        {
            // 該当データを取り出し
            HostSend hs = hostSends[i];

            // 入出庫実績情報エンティティ
            Result res = new Result();
            // バッチNo.
            res.setBatchNo(hs.getBatchNo());
            // ボール入数
            res.setBundleEnteringQty(hs.getBundleEnteringQty());
            // ボールITF
            res.setBundleItf(hs.getBundleItf());
            // 集約作業No.
            res.setCollectJobNo(hs.getCollectJobNo());
            // 荷主コード
            res.setConsignorCode(hs.getConsignorCode());
            // 荷主名称
            res.setConsignorName(hs.getConsignorName());
            // 出荷先コード
            res.setCustomerCode(hs.getCustomerCode());
            // 出荷先名称
            res.setCustomerName(hs.getCustomerName());
            // 入数
            res.setEnteringQty(hs.getEnteringQty());
            // ハードウェア区分
            res.setHardwareType(hs.getHardwareType());
            // 商品コード
            res.setItemCode(hs.getItemCode());
            // 商品名称
            res.setItemName(hs.getItemName());
            // ITF
            res.setItf(hs.getItf());
            // JANコード
            res.setJan(hs.getJan());
            // 作業No.
            res.setJobNo(hs.getJobNo());
            // 作業区分
            res.setJobType(hs.getJobType());
            // オーダーNo.
            res.setOrderNo(hs.getOrderNo());
            // 予定エリアNo.
            res.setPlanAreaNo(hs.getPlanAreaNo());
            // 予定日
            res.setPlanDay(hs.getPlanDay());
            // 予定棚
            res.setPlanLocationNo(hs.getPlanLocationNo());
            // 予定数
            res.setPlanQty(hs.getPlanQty());
            // 予定意一意キー
            res.setPlanUkey(hs.getPlanUkey());
            // 入荷伝票作業枝番
            res.setReceiveBranchNo(hs.getReceiveBranchNo());
            // 入荷伝票行
            res.setReceiveLineNo(hs.getReceiveLineNo());
            // 入荷伝票No.
            res.setReceiveTicketNo(hs.getReceiveTicketNo());
            // 実績報告区分
            res.setReportFlag(hs.getReportFlag());
            // 実績エリアNo.
            res.setResultAreaNo(hs.getResultAreaNo());
            // 実績棚
            res.setResultLocationNo(hs.getResultLocationNo());
            // 実績数
            res.setResultQty(hs.getResultQty());
            // 設定単位キー
            res.setSettingUnitKey(hs.getSettingUnitKey());
            // 出荷伝票作業枝番
            res.setShipBranchNo(hs.getShipBranchNo());
            // 出荷伝票行
            res.setShipLineNo(hs.getShipLineNo());
            // 出荷伝票No.
            res.setShipTicketNo(hs.getShipTicketNo());
            // 欠品数
            res.setShortageQty(hs.getShortageQty());
            // 状態フラグ
            res.setStatusFlag(hs.getStatusFlag());
            // 在庫ID
            res.setStockId(hs.getStockId());
            // 仕入先コード
            res.setSupplierCode(hs.getSupplierCode());
            // 仕入先名称
            res.setSupplierName(hs.getSupplierName());
            // システム接続キー
            res.setSystemConnKey(hs.getSystemConnKey());
            // 端末No.、RFTNo.
            res.setTerminalNo(hs.getTerminalNo());
            // ユーザID
            res.setUserId(hs.getUserId());
            // ユーザ名称
            res.setUserName(hs.getUserName());
            // 作業日
            res.setWorkDay(hs.getWorkDay());
            // 作業時間
            res.setWorkSecond(hs.getWorkSecond());
            // 予定ロットNo.
            res.setPlanLotNo(hs.getPlanLotNo());
            // 実績ロットNo.
            res.setResultLotNo(hs.getResultLotNo());
            // 理由区分
            res.setReasonType(hs.getReasonType());
            // 理由名称
            res.setReasonName(hs.getReasonName());
            // 登録処理名
            res.setRegistPname(PROC_NAME);
            // 最終更新処理名
            res.setLastUpdatePname(PROC_NAME);

            // 生成したエンティティを配列に格納
            results[i] = res;
        }
        // 生成したエンティティ配列を格納
        return results;
    }

    /**
     * 作業者実績情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteWokerResult(Connection conn, String deleteDate)
            throws ReadWriteException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 作業者別RFT実績情報検索キー
            WorkerResultSearchKey workerResultSearchKey = new WorkerResultSearchKey();
            // 作業日(削除日時超過)
            workerResultSearchKey.setWorkDay(deleteDate, COMPARE_LT_EQ);

            // 作業者別RFT実績情報(削除)
            dropRecords(workerResultSearchKey);
        }
    }

    /**
     * 欠品情報の削除を行います。 登録日が引数で渡されたdeleteDate以前のレコードが削除対象です。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteShortageInformation(Connection conn, Date deleteDate)
            throws ReadWriteException
    {
        // 欠品情報検索キー
        ShortageInfoSearchKey shortageInfoSearchKey = new ShortageInfoSearchKey();
        // 登録日時(削除日時超過)
        shortageInfoSearchKey.setRegistDate(deleteDate, COMPARE_LT);

        // 欠品情報(削除)
        dropRecords(shortageInfoSearchKey);
    }

    /**
     * 補充欠品情報の削除を行います。 登録日が引数で渡されたdeleteDate以前のレコードが削除対象です。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteReplenishShortageInformation(Connection conn, Date deleteDate)
            throws ReadWriteException
    {
        // 補充欠品情報検索キー
        ReplenishShortageSearchKey sKey = new ReplenishShortageSearchKey();
        // 登録日時(削除日時超過)
        sKey.setRegistDate(deleteDate, COMPARE_LT);

        // 補充欠品情報(削除)
        dropRecords(sKey);
    }

    /**
     * ASRS稼動実績情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteInOutResult(Connection conn, String deleteDate)
            throws ReadWriteException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // AS/RS稼動実績情報検索キー
            InOutResultSearchKey inoutResultSearckKey = new InOutResultSearchKey();
            // 作業日(削除日時超過)
            inoutResultSearckKey.setWorkDay(deleteDate, COMPARE_LT_EQ);

            // AS/RS稼動実績情報(削除)
            dropRecords(inoutResultSearckKey);
        }
    }

    /**
     * 棚卸設定情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteInventSetting(Connection conn)
            throws ReadWriteException
    {
        // 棚卸設定情報検索キー
        InventSettingSearchKey sKey = new InventSettingSearchKey();
        // 状態フラグ(完了・削除)
        String[] delStats = {
                InventSetting.STATUS_FLAG_COMPLETION,
                InventSetting.STATUS_FLAG_DELETE
        };
        sKey.setStatusFlag(delStats, true);

        // 棚卸設定情報(削除)
        dropRecords(sKey);
    }

    /**
     * 棚卸実績情報の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteInventResult(Connection conn, String deleteDate)
            throws ReadWriteException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 棚卸実績情報検索キー
            InventResultSearchKey sKey = new InventResultSearchKey();
            // 作業日(削除日時超過)
            sKey.setWorkDay(deleteDate, COMPARE_LT_EQ);

            // 棚卸実績情報(削除)
            dropRecords(sKey);
        }
    }

    /**
     * 在庫更新履歴の削除を行います。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteStockHistory(Connection conn, String deleteDate)
            throws ReadWriteException
    {
        // 削除日時が存在する場合
        if (!StringUtil.isBlank(deleteDate))
        {
            // 在庫更新履歴情報検索キー
            StockHistorySearchKey stockHisKey = new StockHistorySearchKey();
            // 作業日(削除日時超過)
            stockHisKey.setWorkDay(deleteDate, COMPARE_LT_EQ, "", "", true);

            // 在庫更新履歴情報(削除)
            dropRecords(stockHisKey);
        }
    }

    /**
     * Sequenceの初期化を行います。<BR>
     * <BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void resetSequence(Connection conn)
            throws ReadWriteException
    {
        // シーケンスデータベースハンドラ
        WMSSequenceHandler sHandler = new WMSSequenceHandler(conn);

        // 作業No.(リセット)
        sHandler.resetWorkNo();

        // 入庫作業No.(リセット)
        sHandler.resetStorageWorkNo();

        // 出庫作業No.(リセット)
        sHandler.resetRetrievalWorkNo();
    }

    /**
     * 入庫または出庫の予定データを削除します。<BR>
     * 対象とする予定情報テーブルのテーブル名を受け取ることで、対象のテーブルを 内部で切り替えています。<BR>
     * <BR>
     * @param store 削除対象予定情報テーブル名
     * @param conn データベースコネクション
     * @param isDeleteUnWork 未作業のものを対称にするときはtrue
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deletePlans(String store, Connection conn, boolean isDeleteUnWork)
            throws CommonException
    {
        // SQL検索キー
        SQLSearchKey planKey = new RetrievalPlanSearchKey();
        // 対象テーブル名
        planKey.setStoreName(store);

        // 状態フラグ(フィールド生成)
        FieldName statusFlagFld = new FieldName(store, RetrievalPlan.STATUS_FLAG.getName());
        // 実績報告区分(フィールド生成)
        FieldName reportFlagFld = new FieldName(store, RetrievalPlan.REPORT_FLAG.getName());
        // 予定一意キー(フィールド生成)
        FieldName planUkeyFld = new FieldName(store, RetrievalPlan.PLAN_UKEY.getName());
        // 予定日(フィールド生成)
        FieldName planDayFld = new FieldName(store, RetrievalPlan.PLAN_DAY.getName());
        // スケジュール処理フラグ(フィールド生成)
        FieldName schFld = new FieldName(store, RetrievalPlan.SCH_FLAG.getName());

        // 未作業情報が対象の場合
        if (isDeleteUnWork)
        {
            // 状態フラグ(未作業)
            planKey.setKey(statusFlagFld, RetrievalPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);

            // 対象テーブルが『出庫予定情報』 かつ 在庫パッケージが導入されている場合
            if (RetrievalPlan.STORE_NAME.equals(store) && _wsysCtlr.hasStockPack())
            {
                // 予定日(作業日超過)
                planKey.setKey(planDayFld, _wsysCtlr.getWorkDay(), "<=", "", "", true);
                // スケジュール処理フラグ(未スケジュール)
                planKey.setKey(schFld, RetrievalPlan.SCH_FLAG_NOT_SCHEDULE, "=", "", ")", false);
            }
            else
            {
                // 予定日(作業日超過)
                planKey.setKey(planDayFld, _wsysCtlr.getWorkDay(), "<=", "", ")", false);
            }
        }

        // 状態フラグ(完了)
        planKey.setKey(statusFlagFld, RetrievalPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        // 実績報告区分(報告済)
        planKey.setKey(reportFlagFld, RetrievalPlan.REPORT_FLAG_REPORT, "=", "", ")", false);
        // 状態フラグ(削除)
        planKey.setKey(statusFlagFld, RetrievalPlan.STATUS_FLAG_DELETE, "=", "", "", true);

        // データベースファインダー
        DatabaseFinder finder = new RetrievalPlanFinder(conn);

        try
        {
            // 検索
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得した予定情報件数分、繰り返す
            while (finder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = finder.getEntities(100);

                // 入出庫作業情報検索キー
                WorkInfoSearchKey workKey = new WorkInfoSearchKey();
                // 入出庫実績送信情報
                HostSendSearchKey hostKey = new HostSendSearchKey();

                // 取得した100毎に繰り返す
                for (Entity plan : plans)
                {
                    // 予定一意キー取得
                    String planUkey = String.valueOf(plan.getValue(planUkeyFld, ""));

                    // 予定一意キー(上記で取得した予定一意キー)
                    workKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    hostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                // 入出庫作業情報(削除)
                dropRecords(workKey);

                // 入出庫実績送信情報から入出庫実績情報(データ移動)
                moveHostSendToResult(hostKey);
            }
            // 対象予定情報(削除)
            dropRecords(planKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(finder);
        }
    }

    /**
     * 入荷(DC)予定データを削除します。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param isDeleteUnWork 未作業のものを対称にするときはtrue
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deleteReceivingPlan(Connection conn, boolean isDeleteUnWork)
            throws CommonException
    {
        // 入荷予定情報テーブル名の取得
        String store = ReceivingPlan.STORE_NAME;
        // 入荷予定情報検索キー
        ReceivingPlanSearchKey planKey = new ReceivingPlanSearchKey();

        // 状態フラグ(フィールド生成)
        FieldName statusFlagFld = new FieldName(store, ReceivingPlan.STATUS_FLAG.getName());
        // 実績報告区分(フィールド生成)
        FieldName reportFlagFld = new FieldName(store, ReceivingPlan.REPORT_FLAG.getName());
        // 予定一意キー(フィールド生成)
        FieldName planUkeyFld = new FieldName(store, ReceivingPlan.PLAN_UKEY.getName());
        // 予定日(フィールド生成)
        FieldName planDayFld = new FieldName(store, ReceivingPlan.PLAN_DAY.getName());
        // TC/DCフラグ(フィールド生成)
        FieldName tcdcFlagFld = new FieldName(store, ReceivingPlan.TCDC_FLAG.getName());

        // 未作業情報が対象の場合
        if (isDeleteUnWork)
        {
            // 状態フラグ(未作業)
            planKey.setKey(statusFlagFld, ReceivingPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);
            // 予定日(作業超過)
            planKey.setKey(planDayFld, _wsysCtlr.getWorkDay(), "<=", "", ")", false);
        }

        // 状態フラグ(完了)
        planKey.setKey(statusFlagFld, ReceivingPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        // 実績報告区分(報告済)
        planKey.setKey(reportFlagFld, ReceivingPlan.REPORT_FLAG_REPORT, "=", "", ")", false);
        // 状態フラグ(削除)
        planKey.setKey(statusFlagFld, ReceivingPlan.BERTH_STATUS_FLAG_DELETE, "=", "", "", true);
        // TC/DCフラグ(DC)
        planKey.setKey(tcdcFlagFld, ReceivingPlan.TCDC_FLAG_DC, "=", "", "", true);

        // データベースファインダー
        DatabaseFinder finder = new ReceivingPlanFinder(conn);

        try
        {
            // 検索
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得件数分、繰り返す
            while (finder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = finder.getEntities(100);

                // 入荷作業情報検索キー
                ReceivingWorkInfoSearchKey rWorkKey = new ReceivingWorkInfoSearchKey();

                // 入荷実績送信情報検索キー
                ReceivingHostSendSearchKey rHostKey = new ReceivingHostSendSearchKey();

                // 取得した100件毎、繰り返す
                for (Entity plan : plans)
                {
                    // 予定一意キーを取得
                    String planUkey = String.valueOf(plan.getValue(planUkeyFld, ""));

                    // 予定一意キー(上記で取得した予定一意キー)
                    rWorkKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    rHostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                // 入荷作業情報(削除)
                dropRecords(rWorkKey);

                // 入荷実績送信情報から入荷実績情報(データ移動)
                moveReceivingHostSendToResult(rHostKey);
            }
            // 対象予定情報(削除)
            dropRecords(planKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(finder);
        }
    }

    /**
     * TC予定データを削除します。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param isDeleteUnWork 未作業のものを対称にするときはtrue
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deleteTCPlan(Connection conn, boolean isDeleteUnWork)
            throws CommonException
    {
        // TC予定情報テーブル名の取得
        String store = CrossDockPlan.STORE_NAME;
        // TC予定情報検索キー
        CrossDockPlanSearchKey planKey = new CrossDockPlanSearchKey();

        // 状態フラグ(フィールド生成)
        FieldName statusFlagFld = new FieldName(store, CrossDockPlan.STATUS_FLAG.getName());
        // 実績報告区分(フィールド生成)
        FieldName reportFlagFld = new FieldName(store, CrossDockPlan.REPORT_FLAG.getName());
        // 予定一意キー(フィールド生成)
        FieldName planUkeyFld = new FieldName(store, CrossDockPlan.PLAN_UKEY.getName());
        // 予定日(フィールド生成)
        FieldName planDayFld = new FieldName(store, CrossDockPlan.PLAN_DAY.getName());

        // 未作業情報が対象の場合
        if (isDeleteUnWork)
        {
            // 状態フラグ(未作業)
            planKey.setKey(statusFlagFld, CrossDockPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);
            // 予定日(作業日超過)
            planKey.setKey(planDayFld, _wsysCtlr.getWorkDay(), "<=", "", ")", false);
        }

        // 状態フラグ(完了)
        planKey.setKey(statusFlagFld, CrossDockPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        // 実績報告区分(報告済)
        planKey.setKey(reportFlagFld, CrossDockPlan.REPORT_FLAG_REPORT, "=", "", ")", false);
        // 状態フラグ(削除)
        planKey.setKey(statusFlagFld, CrossDockPlan.BERTH_STATUS_FLAG_DELETE, "=", "", "", true);

        // データベースファインダー
        DatabaseFinder finder = new CrossDockPlanFinder(conn);

        try
        {
            // 検索
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得した件数分、繰り返す
            while (finder.hasNext())
            {
                // 100毎に繰り返す
                Entity[] plans = finder.getEntities(100);

                // 入荷作業情報検索キー
                ReceivingWorkInfoSearchKey rWorkKey = new ReceivingWorkInfoSearchKey();
                // 入荷実績送信情報検索キー
                ReceivingHostSendSearchKey rHostKey = new ReceivingHostSendSearchKey();

                // 仕分作業情報検索キー
                SortWorkInfoSearchKey sWorkKey = new SortWorkInfoSearchKey();
                // 仕分実績送信情報検索キー
                SortHostSendSearchKey sHostKey = new SortHostSendSearchKey();

                // 取得した100件毎、繰り返す
                for (Entity plan : plans)
                {
                    // 予定一意キーを取得
                    String planUkey = String.valueOf(plan.getValue(planUkeyFld, ""));

                    // 入荷情報
                    // 予定一意キー(上記で取得した予定一意キー)
                    rWorkKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    rHostKey.setPlanUkey(planUkey, "=", "", "", false);

                    // 仕分情報
                    // 予定一意キー(上記で取得した予定一意キー)
                    sWorkKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    sHostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                // 入荷作業情報(削除)
                dropRecords(rWorkKey);
                // 仕分作業情報(削除)
                dropRecords(sWorkKey);

                // 入荷実績送信情報から入荷実績情報(データ移動)
                moveReceivingHostSendToResult(rHostKey);
                // 仕分実績送信情報から仕分実績情報(データ移動)
                moveSortHostSendToResult(sHostKey);
            }
            // 対象予定情報(削除)
            dropRecords(planKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(finder);
        }
    }

    /**
     * 出荷の予定データを削除します。<BR>
     * <BR>
     * @param conn データベースコネクション
     * @param isDeleteUnWork 未作業のものを対称にするときはtrue
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deleteShipPlan(Connection conn, boolean isDeleteUnWork)
            throws CommonException
    {
        // 出荷予定情報テーブル名の取得
        String store = ShipPlan.STORE_NAME;
        // 出荷予定情報検索キー
        ShipPlanSearchKey planKey = new ShipPlanSearchKey();

        // 出荷検品状態フラグ(フィールド生成)
        FieldName statusWorkFlagFld = new FieldName(store, ShipPlan.WORK_STATUS_FLAG.getName());
        // バース状態フラグ(フィールド生成)
        FieldName statusBerthFlagFld = new FieldName(store, ShipPlan.BERTH_STATUS_FLAG.getName());
        // 実績報告区分(フィールド生成)
        FieldName reportFlagFld = new FieldName(store, ShipPlan.REPORT_FLAG.getName());
        // 予定一意キー(フィールド生成)
        FieldName planUkeyFld = new FieldName(store, ShipPlan.PLAN_UKEY.getName());
        // 予定日(フィールド生成)
        FieldName planDayFld = new FieldName(store, ShipPlan.PLAN_DAY.getName());
        // TC/DCフラグ(フィールド生成)
        FieldName tcdcFlagFld = new FieldName(store, ShipPlan.TCDC_FLAG.getName());

        // 未作業情報が対象の場合
        if (isDeleteUnWork)
        {
            // 出荷検品状態フラグ(未作業)
            planKey.setKey(statusWorkFlagFld, ShipPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);
            // 実績報告区分(未作業)
            planKey.setKey(statusBerthFlagFld, ShipPlan.BERTH_STATUS_FLAG_UNSTART, "=", "", "", true);
            // 予定日(作業日超過)
            planKey.setKey(planDayFld, _wsysCtlr.getWorkDay(), "<=", "", ")", false);
        }

        // 出荷検品状態フラグ(完了)
        planKey.setKey(statusWorkFlagFld, ShipPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        // バース状態フラグ(完了)
        planKey.setKey(statusBerthFlagFld, ShipPlan.BERTH_STATUS_FLAG_COMPLETION, "=", "", "", true);
        // 実績報告区分(報告済)
        planKey.setKey(reportFlagFld, ShipPlan.REPORT_FLAG_REPORT, "=", "", ")", false);
        // 出荷検品状態フラグ(削除)
        planKey.setKey(statusWorkFlagFld, ShipPlan.STATUS_FLAG_DELETE, "=", "(", "", true);
        // バース状態フラグ(削除)
        planKey.setKey(statusBerthFlagFld, ShipPlan.BERTH_STATUS_FLAG_DELETE, "=", "", ")", true);
        // TC/DCフラグ(TC)
        planKey.setKey(tcdcFlagFld, ShipPlan.TCDC_FLAG_TC, "=", "", "", true);

        // データベースファインダー
        DatabaseFinder finder = new ShipPlanFinder(conn);

        try
        {
            // 検索
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得した件数分、繰り返す
            while (finder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = finder.getEntities(100);

                // 出荷作業情報検索キー
                ShipWorkInfoSearchKey workKey = new ShipWorkInfoSearchKey();
                // 出荷実績送信情報検索キー
                ShipHostSendSearchKey hostKey = new ShipHostSendSearchKey();

                // 取得した100件毎、繰り返す
                for (Entity plan : plans)
                {
                    // 予定一意キーの取得
                    String planUkey = String.valueOf(plan.getValue(planUkeyFld, ""));

                    // 予定一意キー(上記で取得した予定一意キー)
                    workKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    hostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                // 出荷作業情報(削除)
                dropRecords(workKey);

                // 出荷実績送信情報から出荷実績情報(データ移動)
                moveShipHostSendToResult(hostKey);
            }
            // 対象予定情報(削除)
            dropRecords(planKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(finder);
        }
    }

    /**
     * 再入庫予定データを削除します。<BR>
     * 未作業の削除対象データは再入庫削除リストの発行に失敗した場合、削除されません。
     * <BR>
     * @param conn データベースコネクション
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deleteReStoringPlan(Connection conn)
            throws CommonException
    {
        // 再入庫予定情報テーブル名の取得
        String store = ReStoringPlan.STORE_NAME;
        // 再入庫予定情報検索キー
        ReStoringPlanSearchKey planKey = new ReStoringPlanSearchKey();

        // 状態フラグ(フィールド生成)
        FieldName statusFlagFld = new FieldName(store, ReStoringPlan.STATUS_FLAG.getName());
        // 実績報告区分(フィールド生成)
        FieldName reportFlagFld = new FieldName(store, ReStoringPlan.REPORT_FLAG.getName());
        // 予定一意キー(フィールド生成)
        FieldName planUkeyFld = new FieldName(store, ReStoringPlan.PLAN_UKEY.getName());
        // 予定日(フィールド生成)
        FieldName planDayFld = new FieldName(store, ReStoringPlan.PLAN_DAY.getName());

        // 再入庫削除リストの発行に失敗した場合は削除しない
        if (!_isReStoringPrintFailure)
        {
            // 状態フラグ(未作業)
            planKey.setKey(statusFlagFld, ReStoringPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);
            // 予定日(保持期間超過)
            planKey.setKey(planDayFld, _restoringHoldDate, "<=", "", ")", false);
        }

        // 状態フラグ(完了)
        planKey.setKey(statusFlagFld, ReStoringPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        // 実績報告区分(報告済)
        planKey.setKey(reportFlagFld, ReStoringPlan.REPORT_FLAG_REPORT, "=", "", ")", false);

        // 状態フラグ(削除)
        planKey.setKey(statusFlagFld, ReStoringPlan.STATUS_FLAG_DELETE, "=", "", "", true);

        // データベースファインダー
        DatabaseFinder finder = new ReStoringPlanFinder(conn);

        try
        {
            // 検索
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                // データが存在しなければ処理抜け
                return;
            }

            // 取得件数分、繰り返す
            while (finder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = finder.getEntities(100);

                // 作業情報検索キー
                WorkInfoSearchKey rWorkKey = new WorkInfoSearchKey();

                // 実績送信情報検索キー
                HostSendSearchKey rHostKey = new HostSendSearchKey();

                // 取得した100件毎、繰り返す
                for (Entity plan : plans)
                {
                    // 予定一意キーを取得
                    String planUkey = String.valueOf(plan.getValue(planUkeyFld, ""));

                    // 予定一意キー(上記で取得した予定一意キー)
                    rWorkKey.setPlanUkey(planUkey, "=", "", "", false);
                    // 予定一意キー(上記で取得した予定一意キー)
                    rHostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                // 作業情報(削除)
                dropRecords(rWorkKey);

                // 実績送信情報から実績情報(データ移動)
                moveHostSendToResult(rHostKey);
            }
            // 対象予定情報(削除)
            dropRecords(planKey);
        }
        finally
        {
            // ファインダークローズ
            closeFinder(finder);
        }
    }

    /**
     * 入出庫実績送信情報から、入出庫実績情報へのデータ移動を行います。<BR>
     * <BR>
     * @param hostKey 入出庫実績送信情報検索キー
     * @return 削除対象となった入出庫実績送信情報
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected HostSend[] moveHostSendToResult(SQLSearchKey hostKey)
            throws CommonException
    {
        // 入出庫実績送信ファインダー
        HostSendFinder hostSendFinder = new HostSendFinder(getConnection());
        // 入出庫実績送信エンティティ配列
        HostSend[] hostSends = null;

        try
        {
            // 検索
            hostSendFinder.open(true);
            if (hostSendFinder.search(hostKey) == 0)
            {
                // データが存在しなければ空エンティティを返却
                return new HostSend[0];
            }

            // 取得した件数分、繰り返す
            while (hostSendFinder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = hostSendFinder.getEntities(100);

                // 入出庫実績情報へデータコピー
                hostSends = (HostSend[])plans;
                Result[] results = entReplace(hostSends);

                // 入出庫実績情報データベースハンドラ
                ResultHandler resultHandler = new ResultHandler(getConnection());

                // 取得した100件毎、繰り返す
                for (int i = 0; i < results.length; i++)
                {
                    // 入出庫実績情報エンティティ
                    Result result = results[i];
                    // 入出庫実績情報(新規作成)
                    resultHandler.create(result);
                }
            }
            // 入出庫実績送信情報(削除)
            dropRecords(hostKey);

            // 取得した入出庫実績情報
            return hostSends;
        }
        finally
        {
            // ファインダークローズ
            closeFinder(hostSendFinder);
        }
    }

    /**
     * 入荷実績送信情報から、入荷実績情報へのデータ移動を行います。<BR>
     * <BR>
     * @param hostKey 入荷実績送信情報検索キー
     * @return 削除対象となった入荷実績送信情報
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected ReceivingHostSend[] moveReceivingHostSendToResult(SQLSearchKey hostKey)
            throws CommonException
    {
        // 入荷実績送信情報ファインダー
        ReceivingHostSendFinder hostSendFinder = new ReceivingHostSendFinder(getConnection());
        // 入荷実績送信情報エンティティ配列
        ReceivingHostSend[] hostSends = null;

        try
        {
            // 検索
            hostSendFinder.open(true);
            if (hostSendFinder.search(hostKey) == 0)
            {
                // データが存在しなければ空エンティティを返却
                return new ReceivingHostSend[0];
            }

            // 取得した件数分、繰り返す
            while (hostSendFinder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = hostSendFinder.getEntities(100);

                // 実績情報へデータコピー
                hostSends = (ReceivingHostSend[])plans;
                ReceivingResult[] results = entReceivingReplace(hostSends);

                // 入荷実績情報データベースハンドラ
                ReceivingResultHandler resultHandler = new ReceivingResultHandler(getConnection());

                // 取得した100件毎、繰り返す
                for (int i = 0; i < results.length; i++)
                {
                    // 入荷実績情報エンティティ
                    ReceivingResult result = results[i];
                    // 入荷実績情報(新規作成)
                    resultHandler.create(result);
                }
            }
            // 入荷実績送信情報(削除)
            dropRecords(hostKey);

            // 入荷実績情報
            return hostSends;
        }
        finally
        {
            // ファインダークローズ
            closeFinder(hostSendFinder);
        }
    }

    /**
     * ReceivingHostSendエンティティからReceivingResultエンティティに入れ替えます。<BR>
     * <BR>
     * @param hostSends ReceivingHostSendの配列情報
     * @return Result[] ReceivingResultの配列情報
     */
    protected ReceivingResult[] entReceivingReplace(ReceivingHostSend[] hostSends)
    {
        // 入荷実績情報エンティティ配列
        ReceivingResult[] results = new ReceivingResult[hostSends.length];

        // 入荷実績送信情報件数分、繰り返す
        for (int i = 0; i < hostSends.length; i++)
        {
            // 該当データを取り出し
            ReceivingHostSend hs = hostSends[i];

            // 入荷実績情報エンティティ
            ReceivingResult res = new ReceivingResult();

            // 作業日
            res.setWorkDay(hs.getWorkDay());
            // 作業No.
            res.setJobNo(hs.getJobNo());
            // 設定単位キー
            res.setSettingUnitKey(hs.getSettingUnitKey());
            // 集約作業No.
            res.setCollectJobNo(hs.getCollectJobNo());
            // クロスドック連携キー
            res.setCrossDockUkey(hs.getCrossDockUkey());
            // 作業No.
            res.setJobType(hs.getJobType());
            // 状態フラグ
            res.setStatusFlag(hs.getStatusFlag());
            // ハードウェア区分
            res.setHardwareType(hs.getHardwareType());
            // 予定一意キー
            res.setPlanUkey(hs.getPlanUkey());
            // TC/DCフラグ
            res.setTcdcFlag(hs.getTcdcFlag());
            // 予定日
            res.setPlanDay(hs.getPlanDay());
            // 荷主コード
            res.setConsignorCode(hs.getConsignorCode());
            // 荷主名称
            res.setConsignorName(hs.getConsignorName());
            // 仕入先コード
            res.setSupplierCode(hs.getSupplierCode());
            // 仕入先名称
            res.setSupplierName(hs.getSupplierName());
            // 入荷伝票No.
            res.setReceiveTicketNo(hs.getReceiveTicketNo());
            // 入荷伝票行
            res.setReceiveLineNo(hs.getReceiveLineNo());
            // 商品コード
            res.setItemCode(hs.getItemCode());
            // 商品名称
            res.setItemName(hs.getItemName());
            // JANコード
            res.setJan(hs.getJan());
            // ITF
            res.setItf(hs.getItf());
            // ボールITF
            res.setBundleItf(hs.getBundleItf());
            // 入数
            res.setEnteringQty(hs.getEnteringQty());
            // ボール入数
            res.setBundleEnteringQty(hs.getBundleEnteringQty());
            // 予定ロットNo.
            res.setPlanLotNo(hs.getPlanLotNo());
            // 予定数
            res.setPlanQty(hs.getPlanQty());
            // 実績数
            res.setResultQty(hs.getResultQty());
            // 欠品数
            res.setShortageQty(hs.getShortageQty());
            // 実績ロットNo.
            res.setResultLotNo(hs.getResultLotNo());
            // 実績報告区分
            res.setReportFlag(hs.getReportFlag());
            // ユーザID
            res.setUserId(hs.getUserId());
            // ユーザ名称
            res.setUserName(hs.getUserName());
            // 端末No.、RFTNo.
            res.setTerminalNo(hs.getTerminalNo());
            // 作業秒数
            res.setWorkSecond(hs.getWorkSecond());
            // 登録処理名
            res.setRegistPname(PROC_NAME);
            // 最終更新処理名
            res.setLastUpdatePname(PROC_NAME);

            // 生成したエンティティを配列に格納
            results[i] = res;
        }
        // 生成したエンティティ配列を返却
        return results;
    }

    /**
     * 仕分実績送信情報から、仕分実績情報へのデータ移動を行います。<BR>
     * <BR>
     * @param hostKey 仕分実績送信情報検索キー
     * @return 削除対象となった仕分実績送信情報
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected SortHostSend[] moveSortHostSendToResult(SQLSearchKey hostKey)
            throws CommonException
    {
        // 仕分実績送信情報ファインダー
        SortHostSendFinder hostSendFinder = new SortHostSendFinder(getConnection());
        // 仕分実績送信情報エンティティ
        SortHostSend[] hostSends = null;

        try
        {
            // 検索
            hostSendFinder.open(true);
            if (hostSendFinder.search(hostKey) == 0)
            {
                // データが存在しなければ空エンティティを返却
                return new SortHostSend[0];
            }

            // 取得した件数分、繰り返す
            while (hostSendFinder.hasNext())
            {
                // 100件毎に取得
                Entity[] plans = hostSendFinder.getEntities(100);

                // 実績情報へデータコピー
                hostSends = (SortHostSend[])plans;
                SortResult[] results = entSortReplace(hostSends);

                // 仕分実績情報データベースハンドラ
                SortResultHandler resultHandler = new SortResultHandler(getConnection());

                // 取得した100件毎、繰り返す
                for (int i = 0; i < results.length; i++)
                {
                    // 仕分実績情報エンティティ
                    SortResult result = results[i];
                    // 仕分実績情報(新規登録)
                    resultHandler.create(result);
                }
            }
            // 仕分実績送信情報(削除)
            dropRecords(hostKey);

            // 仕分実績送信情報
            return hostSends;
        }
        finally
        {
            // ファインダークローズ
            closeFinder(hostSendFinder);
        }
    }

    /**
     * SortHostSendエンティティからSortResultエンティティに入れ替えます。<BR>
     * <BR>
     * @param hostSends SortHostSendの配列情報
     * @return Result[] SortResultの配列情報
     */
    protected SortResult[] entSortReplace(SortHostSend[] hostSends)
    {
        // 仕分実績情報エンティティ配列
        SortResult[] results = new SortResult[hostSends.length];

        // 仕分送信実績情報件数分、繰り返す
        for (int i = 0; i < hostSends.length; i++)
        {
            // 該当データを取得
            SortHostSend hs = hostSends[i];

            // 仕分実績情報エンティティ
            SortResult res = new SortResult();

            // 作業日
            res.setWorkDay(hs.getWorkDay());
            // 作業No.
            res.setJobNo(hs.getJobNo());
            // 設定単位キー
            res.setSettingUnitKey(hs.getSettingUnitKey());
            // 集約作業No.
            res.setCollectJobNo(hs.getCollectJobNo());
            // クロスドック連携キー
            res.setCrossDockUkey(hs.getCrossDockUkey());
            // 作業No.
            res.setJobType(hs.getJobType());
            // 状態フラグ
            res.setStatusFlag(hs.getStatusFlag());
            // ハードウェア区分
            res.setHardwareType(hs.getHardwareType());
            // 予定一意キー
            res.setPlanUkey(hs.getPlanUkey());
            // 予定日
            res.setPlanDay(hs.getPlanDay());
            // バッチNo.
            res.setBatchNo(hs.getBatchNo());
            // 荷主コード
            res.setConsignorCode(hs.getConsignorCode());
            // 荷主名称
            res.setConsignorName(hs.getConsignorName());
            // 出荷先コード
            res.setCustomerCode(hs.getCustomerCode());
            // 出荷先名称
            res.setCustomerName(hs.getCustomerName());
            // 出荷伝票No.
            res.setShipTicketNo(hs.getShipTicketNo());
            // 出荷伝票行
            res.setShipLineNo(hs.getShipLineNo());
            // 仕分場所
            res.setSortingPlace(hs.getSortingPlace());
            // 商品コード
            res.setItemCode(hs.getItemCode());
            // 商品名称
            res.setItemName(hs.getItemName());
            // JANコード
            res.setJan(hs.getJan());
            // ITF
            res.setItf(hs.getItf());
            // ボールITF
            res.setBundleItf(hs.getBundleItf());
            // 入数
            res.setEnteringQty(hs.getEnteringQty());
            // ボール入数
            res.setBundleEnteringQty(hs.getBundleEnteringQty());
            // 予定ロットNO.
            res.setPlanLotNo(hs.getPlanLotNo());
            // 予定数
            res.setPlanQty(hs.getPlanQty());
            // 実績数
            res.setResultQty(hs.getResultQty());
            // 欠品数
            res.setShortageQty(hs.getShortageQty());
            // 実績ロットNo.
            res.setResultLotNo(hs.getResultLotNo());
            // 実績報告区分
            res.setReportFlag(hs.getReportFlag());
            // ユーザID
            res.setUserId(hs.getUserId());
            // ユーザ名称
            res.setUserName(hs.getUserName());
            // 端末No.、RFTNo.
            res.setTerminalNo(hs.getTerminalNo());
            // 作業秒数
            res.setWorkSecond(hs.getWorkSecond());
            // 登録処理名
            res.setRegistPname(PROC_NAME);
            // 最終更新処理名
            res.setLastUpdatePname(PROC_NAME);

            // 生成したエンティティを配列に格納
            results[i] = res;
        }
        // 生成したエンティティ配列を返却
        return results;
    }

    /**
     * 出荷実績送信情報から、出荷実績情報へのデータ移動を行います。<BR>
     * <BR>
     * @param hostKey 出荷実績送信情報検索キー
     * @return 削除対象となった出荷実績送信情報
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected ShipHostSend[] moveShipHostSendToResult(SQLSearchKey hostKey)
            throws CommonException
    {
        // 出荷実績送信情報データベースハンドラ
        ShipHostSendFinder hostSendFinder = new ShipHostSendFinder(getConnection());
        // 出荷実績送信情報エンティティ
        ShipHostSend[] hostSends = null;

        try
        {
            // 検索
            hostSendFinder.open(true);
            if (hostSendFinder.search(hostKey) == 0)
            {
                // データが存在しなければ空エンティティを返却
                return new ShipHostSend[0];
            }

            // 取得した件数分、繰り返す
            while (hostSendFinder.hasNext())
            {
                // 100件毎に、取得する
                Entity[] plans = hostSendFinder.getEntities(100);

                // 実績情報へデータコピー
                hostSends = (ShipHostSend[])plans;
                ShipResult[] results = entShipReplace(hostSends);

                // 出荷実績情報データベースハンドラ
                ShipResultHandler resultHandler = new ShipResultHandler(getConnection());

                // 取得した100件毎、繰り返す
                for (int i = 0; i < results.length; i++)
                {
                    // 出荷実績情報エンティティ
                    ShipResult result = results[i];
                    // 出荷実績情報(新規登録)
                    resultHandler.create(result);
                }
            }
            // 出荷実績送信情報(削除)
            dropRecords(hostKey);

            // 出荷実績送信情報
            return hostSends;
        }
        finally
        {
            // ファインダークローズ
            closeFinder(hostSendFinder);
        }
    }

    /**
     * ShipHostSendエンティティからShipResultエンティティに入れ替えます。<BR>
     * <BR>
     * @param hostSends ShipHostSendの配列情報
     * @return Result[] ShipResultの配列情報
     */
    protected ShipResult[] entShipReplace(ShipHostSend[] hostSends)
    {
        // 出荷実績情報エンティティ配列
        ShipResult[] results = new ShipResult[hostSends.length];

        // 出荷実績送信情報件数分、繰り返す
        for (int i = 0; i < hostSends.length; i++)
        {
            // 該当データを取得
            ShipHostSend hs = hostSends[i];

            // 出荷実績情報エンティティ
            ShipResult res = new ShipResult();

            // 作業日
            res.setWorkDay(hs.getWorkDay());
            // 作業No.
            res.setJobNo(hs.getJobNo());
            // 設定単位キー
            res.setSettingUnitKey(hs.getSettingUnitKey());
            // 集約作業No.
            res.setCollectJobNo(hs.getCollectJobNo());
            // クロスドック連携キー
            res.setCrossDockUkey(hs.getCrossDockUkey());
            // 作業区分
            res.setJobType(hs.getJobType());
            // 出荷検品状態フラグ
            res.setWorkStatusFlag(hs.getWorkStatusFlag());
            // バース状態フラグ
            res.setBerthStatusFlag(hs.getBerthStatusFlag());
            // ハードウェア区分
            res.setHardwareType(hs.getHardwareType());
            // 予定一意キー
            res.setPlanUkey(hs.getPlanUkey());
            // TC/DCフラグ
            res.setTcdcFlag(hs.getTcdcFlag());
            // 予定日
            res.setPlanDay(hs.getPlanDay());
            // バッチNo.
            res.setBatchNo(hs.getBatchNo());
            // 荷主コード
            res.setConsignorCode(hs.getConsignorCode());
            // 荷主名称
            res.setConsignorName(hs.getConsignorName());
            // 出荷先コード
            res.setCustomerCode(hs.getCustomerCode());
            // 出荷先名称
            res.setCustomerName(hs.getCustomerName());
            // 出荷伝票No.
            res.setShipTicketNo(hs.getShipTicketNo());
            // 出荷伝票行
            res.setShipLineNo(hs.getShipLineNo());
            // 商品コード
            res.setItemCode(hs.getItemCode());
            // 商品名称
            res.setItemName(hs.getItemName());
            // JANコード
            res.setJan(hs.getJan());
            // ITF
            res.setItf(hs.getItf());
            // ボールITF
            res.setBundleItf(hs.getBundleItf());
            // 入数
            res.setEnteringQty(hs.getEnteringQty());
            // ボール入数
            res.setBundleEnteringQty(hs.getBundleEnteringQty());
            // 予定ロットNo.
            res.setPlanLotNo(hs.getPlanLotNo());
            // 予定数
            res.setPlanQty(hs.getPlanQty());
            // 実績数
            res.setResultQty(hs.getResultQty());
            // 欠品数
            res.setShortageQty(hs.getShortageQty());
            // 実績ロットNo.
            res.setResultLotNo(hs.getResultLotNo());
            // 結果バース
            res.setResultBerth(hs.getResultBerth());
            // 実績報告区分
            res.setReportFlag(hs.getReportFlag());
            // 出荷ユーザID
            res.setShipUserId(hs.getShipUserId());
            // 出荷ユーザ名称
            res.setShipUserName(hs.getShipUserName());
            // 出荷端末No.、出荷RFTNo.
            res.setShipTerminalNo(hs.getShipTerminalNo());
            // 出荷作業秒数
            res.setShipWorkSecond(hs.getShipWorkSecond());
            // バースユーザID
            res.setBerthUserId(hs.getBerthUserId());
            // バースユーザ名称
            res.setBerthUserName(hs.getBerthUserName());
            // バース端末No.、バースRFTNo.
            res.setBerthTerminalNo(hs.getBerthTerminalNo());
            // バース作業秒数
            res.setBerthWorkSecond(hs.getBerthWorkSecond());
            // 登録処理名
            res.setRegistPname(PROC_NAME);
            // 最終更新処理名
            res.setLastUpdatePname(PROC_NAME);

            // 生成したエンティティを配列に格納
            results[i] = res;
        }
        // 生成したエンティティ配列を返却
        return results;
    }

    /**
     * 作業情報・実績情報の整理を行います。<BR>
     * <BR>
     * @param deleteDate 削除予定日(これより古いものが削除されます)
     * @param resultDelDay 削除実績日(これより古いものが削除されます)
     * @param jobType 入出庫識別値
     * @throws ReadWriteException データベースエラー
     */
    protected void deleteExpireWork(String deleteDate, String resultDelDay, String jobType)
            throws ReadWriteException
    {
        // WareNaviシステムコントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // 入庫パッケージ、または出庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack() || wsysCtlr.hasRetrievalPack())
        {
            // 削除日時が存在する場合
            if (!StringUtil.isBlank(deleteDate))
            {
                // 入出庫作業情報検索キー
                WorkInfoSearchKey workKey = new WorkInfoSearchKey();
                // 状態フラグ(削除日時超過)
                workKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
                // 作業区分(入出庫識別値)
                workKey.setJobType(jobType);
                // 予定意一意キー(空白以外)
                workKey.setPlanUkey("", "!=");

                // 出庫作業データ削除時、作業状態「未作業」以外のデータを削除(状態フラグ「出庫」且つ在庫パッケージ導入時)
                if (jobType.equals(SystemDefine.JOB_TYPE_RETRIEVAL) && wsysCtlr.hasStockPack())
                {
                    // 状態フラグ(未作業以外)
                    workKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART, "!=");
                }

                // 入出庫作業情報(削除)
                dropRecords(workKey);

                // 入出庫実績送信情報検索キー
                HostSendSearchKey hostKey = new HostSendSearchKey();
                // 予定日(削除日時超過)
                hostKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
                // 作業区分(入出庫識別値)
                hostKey.setJobType(jobType);
                // 予定一意キー(空白以外)
                hostKey.setPlanUkey("", "!=");

                // 入出庫作業情報(削除)
                dropRecords(hostKey);
            }

            // 削除実績日が存在する場合
            if (!StringUtil.isBlank(resultDelDay))
            {
                // 初回の場合
                if (!_isShareProcessed)
                {
                    // 入出庫実績情報検索キー
                    ResultSearchKey resultKey = new ResultSearchKey();
                    // 作業日(削除実績日超過)
                    resultKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);

                    // 入出庫実績情報(削除)
                    dropRecords(resultKey);

                    // フラグをtrueに
                    _isShareProcessed = true;
                }
            }
        }
    }

    /**
     * 在庫情報の引当のキャンセル処理を行います。<BR>
     * <BR>
     * @param stockID 在庫ID
     * @param allocQty 引当数
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void allocateCancelStock(String stockID, int allocQty)
            throws CommonException
    {
        // 在庫情報データベースハンドラ
        StockHandler stockH = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey skey = new StockSearchKey();

        // 作業情報の在庫IDと一致。
        skey.setStockId(stockID);
        Stock stock = (Stock)stockH.findPrimaryForUpdate(skey);
        if (null == stock)
        {
            // データが存在しなければ処理終了
            return;
        }

        // 在庫情報を更新する。
        StockAlterKey akey = new StockAlterKey();
        // 在庫ID(在庫ID)
        akey.setStockId(stock.getStockId());
        // 引当可能数(作業情報の作業可能数分、加算を行う)
        akey.updateAllocationQty(stock.getAllocationQty() + allocQty);
        akey.updateLastUpdatePname(PROC_NAME);

        // 在庫情報(更新)
        stockH.modify(akey);
    }

    /**
     * 再入庫削除リストを発行します。<br>
     *
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printReStoringDeleteList()
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaAsReStoringDeleteDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new FaAsReStoringDeleteDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaAsReStoringDeleteDASCHParams inparam = new FaAsReStoringDeleteDASCHParams();
            inparam.set(FaAsReStoringDeleteDASCHParams.DELETE_DAY, _restoringHoldDate);

            // check count.
            if (dasch.count(inparam) == 0)
            {
                // 削除対象なしのため、正常で返す
                return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ReStoringDeleteList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReStoringDeleteListParams expparam = new ReStoringDeleteListParams();
                expparam.set(ReStoringDeleteListParams.DFK_DS_NO,
                        outparam.get(FaAsReStoringDeleteDASCHParams.DFK_DS_NO));
                expparam.set(ReStoringDeleteListParams.DFK_USER_ID,
                        outparam.get(FaAsReStoringDeleteDASCHParams.DFK_USER_ID));
                expparam.set(ReStoringDeleteListParams.DFK_USER_NAME,
                        outparam.get(FaAsReStoringDeleteDASCHParams.DFK_USER_NAME));
                expparam.set(ReStoringDeleteListParams.SYS_DAY, outparam.get(FaAsReStoringDeleteDASCHParams.SYS_DAY));
                expparam.set(ReStoringDeleteListParams.SYS_TIME, outparam.get(FaAsReStoringDeleteDASCHParams.SYS_TIME));
                expparam.set(ReStoringDeleteListParams.STATION_NO,
                        outparam.get(FaAsReStoringDeleteDASCHParams.STATION_NO));
                expparam.set(ReStoringDeleteListParams.STATION_NAME,
                        outparam.get(FaAsReStoringDeleteDASCHParams.STATION_NAME));
                expparam.set(ReStoringDeleteListParams.WORK_NO, outparam.get(FaAsReStoringDeleteDASCHParams.WORK_NO));
                expparam.set(ReStoringDeleteListParams.ITEM_CODE,
                        outparam.get(FaAsReStoringDeleteDASCHParams.ITEM_CODE));
                expparam.set(ReStoringDeleteListParams.ITEM_NAME,
                        outparam.get(FaAsReStoringDeleteDASCHParams.ITEM_NAME));
                expparam.set(ReStoringDeleteListParams.LOT_NO, outparam.get(FaAsReStoringDeleteDASCHParams.LOT_NO));
                expparam.set(ReStoringDeleteListParams.STORAGE_DATE,
                        outparam.get(FaAsReStoringDeleteDASCHParams.STORAGE_DATE));
                expparam.set(ReStoringDeleteListParams.REMOVE_DATE,
                        outparam.get(FaAsReStoringDeleteDASCHParams.REMOVE_DATE));
                expparam.set(ReStoringDeleteListParams.PLAN_QTY, outparam.get(FaAsReStoringDeleteDASCHParams.PLAN_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }

        return true;
    }

    /**
     * WMSパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    protected boolean hasWmsPack()
    {
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        // WMSパッケージが導入されている場合はtrueを返す
        if (wsysCtlr.hasReceivingPack() || wsysCtlr.hasStoragePack() || wsysCtlr.hasRetrievalPack()
                || wsysCtlr.hasSortingPack() || wsysCtlr.hasShippingPack() || wsysCtlr.hasStockPack()
                || wsysCtlr.hasCrossdockPack() || wsysCtlr.hasAnalysisPack() || wsysCtlr.hasAsrsPack()
                || wsysCtlr.hasMasterPack())
        {
            return true;
        }

        return false;
    }

    /**
     * 各種のログファイルの削除を行います。<BR>
     * <BR>
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void deleteLog()
            throws CommonException,
                IOException
    {
        // メッセージログの削除(保持期間超過)
        deleteMessageLog();

        // 外部履歴ファイルの削除(保持期間超過)
        FileCleaner.clearOutData();

        // 帳票イメージファイル削除(保持期間超過)
        FileCleaner.clearPrintHistory();

        // TOMCATログファイル削除(保持期間超過)
        FileCleaner.clearTomcatLog();

        // TOMCAT-RFTログファイル削除(保持期間超過)
        FileCleaner.clearTomcatRFTLog();

        // IIS(FTP)ログファイル削除(保持期間超過)
        FileCleaner.clearIISLog();

        // その他ログファイル削除(保持期間超過)
        FileCleaner.clearOtherLog();

        // FTP履歴ファイル圧縮
        CompressToZipFile.makeZipFile(WmsParam.FTP_FILE_HISTORY_PATH);

        // FTP履歴ファイル削除(保持期間超過)
        FileCleaner.clearFTPBackupFile();

        // プレビューtempファイル削除
        FileCleaner.clearPdfFile();

        // AS21TraceLogファイル削除
        FileCleaner.clearAs21TraceLog();
    }

    /**
     * 保持日数を過ぎたメッセージログを削除します。<BR>
     * <BR>
     * @throws CommonException 全ての例外を報告します。
     */
    private void deleteMessageLog()
            throws CommonException,
                IOException
    {
        // ログ日付のフォーマット形式
        String dateFormat = "yyyyMMdd";
        SimpleDateFormat logDtFmt = new SimpleDateFormat(dateFormat);

        // 削除日付を取得(ログバックアップ保持日数をWMSParamから取得)
        String deleteDate = getKeepDateString(Integer.parseInt(WmsParam.WMS_LOGFILE_KEEP_DAYS));

        // 2010/07/29 Y.Osawa UPD ST
        // ログのエンコード形式
        String logEncode = "UTF-8";

        String[] fileNmae = getLogFileName();
        for (String name : fileNmae)
        {
            // メッセージログファイルオブジェクト生成
            //            File messageLog = new File(WmsParam.LOGS_PATH, WmsParam.MESSAGELOG_FILE);
            File messageLog = new File(WmsParam.LOGS_PATH, name);
            // ワークファイルオブジェクト生成
            File workFile = new File(WmsParam.LOGS_PATH, FILENAME_MESSAGE_WORK);

            // メッセージファイル
            String filename = "";
            // リーダー
            BufferedReader reader = null;
            // ライター
            BufferedWriter writer = null;
            // 日付チェックフラグ
            boolean foundHoldLog = false;
            // 書き込みチェックフラグ
            boolean hasWriteDate = false;

            try
            {
                // ファイルコピー処理
                // メッセージログファイルをオープン
                filename = messageLog.getAbsolutePath();
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(messageLog), logEncode));

                // ワークファイルをオープン
                filename = workFile.getAbsolutePath();
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(workFile, false), logEncode));

                // ファイルを一行ずつ読み込んで処理
                while (reader.ready())
                {
                    // 一行分読み込み
                    String logRec = reader.readLine();
                    if (null == logRec)
                    {
                        // データが存在しなければ次行へ
                        continue;
                    }

                    // 日付チェック必須時
                    if (!foundHoldLog)
                    {
                        try
                        {
                            // ファイルに書き込まれている日付を取得
                            StringBuilder buf = new StringBuilder();
                            // 年
                            buf.append(logRec.substring(0, 4));
                            // 月
                            buf.append(logRec.substring(5, 7));
                            // 日
                            buf.append(logRec.substring(8, 10));

                            // ファイルに書き込まれている日付が削除日付よりも新しい場合
                            // ワークファイルに一時書き込み
                            String recordDate = String.valueOf(buf);


                            // 削除日付をフォーマット
                            Date delDate = logDtFmt.parse(deleteDate);
                            // ワーク日付をフォーマット
                            Date recDate = logDtFmt.parse(recordDate);

                            // 日付チェックフラグを変更
                            foundHoldLog = (delDate.compareTo(recDate) < 0);
                        }
                        catch (ParseException e)
                        {
                            continue;
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            continue;
                        }

                        // 成立していない場合
                        if (!foundHoldLog)
                        {
                            // 次行へ
                            continue;
                        }
                    }
                    // 一時ファイルに書き込み
                    writer.write(logRec);

                    // 新規行追加
                    writer.newLine();

                    // データを書き込んだのでフラグを更新
                    hasWriteDate = true;
                }
                // 全バッファ初期化
                writer.flush();

                // ファイルのクローズ
                close(reader);
                close(writer);

                // 元ファイルの削除
                messageLog.delete();

                if (hasWriteDate)
                {
                    // ワークファイルの名前をログファイルの名前に変更する
                    workFile.renameTo(messageLog);
                }
                else
                {
                    // 書き込みデータが無かった場合は削除します。
                    workFile.delete();
                }
            }
            // ファイル例外が発生した場合
            catch (FileNotFoundException e)
            {
                // (6003009)ファイルが見つかりませんでした。{0}
                setMessage(WmsMessageFormatter.format(6003009, filename));
                return;
            }
            // 入出力例外が発生した場合
            catch (IOException e)
            {
                // (6006020)ファイルの入出力エラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006021, e), getClass().getName());
                throw new ScheduleException();
            }
            //            // 解析例外が発生した場合
            //            catch (ParseException e)
            //            {
            //                // (6006001)予期しないエラーが発生しました。{0}
            //                RmiMsgLogClient.write(new TraceHandler(6006001, e), getClass().getName());
            //                throw new ScheduleException();
            //            }
            finally
            {
                // クローズ処理
                close(reader);
                close(writer);
            }

            // 削除日付より新しい日付が存在した場合は次のファイルは処理しない
            if (foundHoldLog)
            {
                break;
            }
        }
        // 2010/07/29 Y.Osawa UPD ED
    }

    // 2010/07/29 Y.Osawa ADD ST
    /**
     * メッセージログファイル名を古いファイル順に取得します。<BR>
     * <BR>
     * @throws CommonException 全ての例外を報告します。
     */
    private String[] getLogFileName()
            throws CommonException,
                IOException
    {
        //<jp> Fileのオブジェクトを生成</jp>
        //<en> Instantiation of FIle object</en>
        File passfile = new File(WmsParam.LOGS_PATH);
        //<jp> フォルダの中のファイルを読み込む</jp>
        //<en> Reading file in the folder</en>
        String[] pass = passfile.list();
        ArrayList<String> fileList = new ArrayList<String>();

        if (pass.length != 0)
        {
            //<jp> フォルダ内のファイルをソートします。</jp>
            //<en> Sorting the file in the folder</en>
            Arrays.sort(pass);

            //<jp> ファイル名の中でMESSAGELOG_FILEを含むファイルがあるかを調べます。
            // MESSAGELOG_FILEを含む場合</jp>
            //<en> Checking if there are any file including MESSAGELOG_FILE in the file.
            //If MESSAGELOG_FILE is included</en>
            String logFileName = WmsParam.MESSAGELOG_FILE;
            //<jp> 拡張子を取り除き、比較用の文字列を生成する。</jp>
            //<en>String array for comparison is generated by eliminating file extension. </en>
            String dlim = ".";
            StringTokenizer stk = new StringTokenizer(logFileName, dlim, false);
            String name = stk.nextToken();

            boolean existsLogFile = false;
            //<jp> passの中のファイル名の個数分まわします。</jp>
            //<en> Looping the times of file numbers in pass</en>
            for (String fileName : pass)
            {
                if (fileName.indexOf(name) != -1)
                {
                    // 日付のついていないメッセージログファイルは最新ファイルなので最後に回す
                    if (fileName.equals(logFileName))
                    {
                        existsLogFile = true;
                        continue;
                    }

                    //<jp>ファイル名を取得します。</jp>
                    //<en>Retrieving the file name.</en>
                    fileList.add(fileName);
                }
            }
            if (existsLogFile)
            {
                fileList.add(logFileName);
            }

            // ファイル名を返す
            return fileList.toArray(new String[fileList.size()]);
        }

        return null;
    }

    // 2010/07/29 Y.Osawa ADD ED

    /**
     * ストリームリーダーをクローズします。 エラーについては無視します。<BR>
     * <BR>
     * @param reader クローズするリーダー
     */
    private void close(Reader reader)
            throws IOException
    {
        // リーダがnullの場合
        if (null == reader)
        {
            // 処理抜け
            return;
        }
        // クローズ
        reader.close();
    }

    /**
     * ストリームライターをクローズします。 エラーについては無視します。<BR>
     * <BR>
     * @param writer クローズするライター
     */
    private void close(Writer writer)
            throws IOException
    {
        // ライターがnullの場合
        if (null == writer)
        {
            // 処理抜け
            return;
        }
        // クローズ
        writer.close();
    }

    /**
     * システム状態で日次更新可能かチェックを行う。<BR>
     * <BR>
     * @param errList エラーリスト
     * @return ErrorList エラーリスト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private List<Params> checkSystem(List<Params> errList)
            throws CommonException
    {
        // 日次更新状態(日次更新不可)
        String statusNG = SystemOutParameter.DAILYUPDATE_STATUS_NG;
        // 発生ポイント(NoReason)
        String checkPointSP = SystemOutParameter.POINT_SPACE;

        // 予定データ取込中チェック
        if (isLoadData())
        {
            // 日次更新不可・NG理由(取込中)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_LOADING, checkPointSP));
        }
        // 報告データ作成中チェック
        if (isReportData())
        {
            // 日次更新不可・NG理由(報告中)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_REPORTING, checkPointSP));
        }

        // 日次更新中チェック
        if (isDailyUpdate())
        {
            // 日次更新不可・NG理由(日次更新中)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_DAILYUPDATING, checkPointSP));
        }

        // 出庫引当中チェック
        if (isRetrievalAllocate())
        {
            // 日次更新不可・NG理由(出庫引当中)・発生ポイント(出庫)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_RETRIEVALALLOCATE,
                    SystemOutParameter.POINT_RETRIEVAL));
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            // 日次更新不可・NG理由(搬送クリア中)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_ALLOCATION_CLEAR, checkPointSP));
        }

        // 終了中チェック
        if (isEndProcessing())
        {
            // 日次更新不可・NG理由(終了処理中)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusNG, SystemOutParameter.REASON_END_PROCESSING, checkPointSP));
        }

        // 生成したエラー情報の返却
        return errList;
    }

    /**
     * パッケージごとに日次更新の実行可能かのチェックを行う。<BR>
     * <BR>
     * @param errList エラーリスト
     * @return ErrorList エラーリスト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    @SuppressWarnings("unchecked")
    private List<Params> checkPackageNG(List errList)
            throws CommonException
    {
        // 日次更新付加
        String statusNG = SystemOutParameter.DAILYUPDATE_STATUS_NG;
        // NG理由(作業中)
        String reasonWorking = SystemOutParameter.REASON_NOWWORKING;
        // システム定義コントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;
        // 発生ポイント
        String checkPoint = null;

        // AS/RSパッケージが導入されている場合
        if (wsysCtlr.hasAsrsPack())
        {
            // 発生ポイント(AS/RS)
            checkPoint = SystemOutParameter.POINT_ASRS;
            // AS/RSグループコントローラー情報検索キー
            GroupControllerSearchKey grKey = new GroupControllerSearchKey();
            // AS/RS搬送情報検索キー
            CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
            // 入出庫作業情報検索キー
            WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

            // AS/RSグループコントローラー情報の状態が『オンライン』の場合は設定できません。
            Error err = new Error(statusNG, SystemOutParameter.REASON_ONLINE, checkPoint);
            // 状態(オンライン)
            grKey.setStatusFlag(GroupController.GC_STATUS_ONLINE);
            // AS/RSグループコントローラーに情報があればリストに追加
            errList.add(checkRecordRemain(grKey, err));

            // AS/RS搬送情報の状態が『作業中』の場合は設定できません。
            err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_ASRS);
            // AS/RS搬送情報に情報があればリストに追加
            errList.add(checkRecordRemain(carryKey, err));

            // 入出庫作業情報の状態が『作業中』の場合は設定できません。
            err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_ASRS_STOCK);
            // 作業区分(在庫確認)
            wiKey.setJobType(WorkInfo.JOB_TYPE_ASRS_INVENTORYCHECK);
            // 状態(作業中)
            wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // 入出庫作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(wiKey, err));
        }

        // 入荷パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 発生ポイント(入荷)
            checkPoint = SystemOutParameter.POINT_RECEIVE;
            // 入荷作業情報検索キー
            ReceivingWorkInfoSearchKey receiveKey = new ReceivingWorkInfoSearchKey();

            // 入荷作業情報の状態が『作業中』の場合は設定できません。
            Error err = new Error(statusNG, reasonWorking, checkPoint);
            // 作業区分(入荷)
            receiveKey.setJobType(ReceivingWorkInfo.JOB_TYPE_RECEIVING);
            // 状態(作業中)
            receiveKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
            // 入荷作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(receiveKey, err));
        }

        // 入庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack())
        {
            // 入出庫作業情報検索キー
            WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
            // 棚卸作業情報検索キー
            InventWorkInfoSearchKey iwKey = new InventWorkInfoSearchKey();
            // 移動作業情報検索キー
            MoveWorkInfoSearchKey moveKey = new MoveWorkInfoSearchKey();

            // 入出庫作業情報の状態が『作業中』の場合は設定できません。
            Error err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_STORAGE);
            // 作業区分(入庫)
            wiKey.setJobType(WorkInfo.JOB_TYPE_STORAGE);
            // 状態(作業中)
            wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // 入出庫作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(wiKey, err));

            // DA版の場合のみ
            if (!wsysCtlr.isFaDaEnabled())
            {
                // 棚卸作業情報の状態が『作業中』の場合は設定できません。
                err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_INVENT);
                // 状態(作業中)
                iwKey.setStatusFlag(InventWorkInfo.STATUS_FLAG_NOWWORKING);
                // 棚卸作業情報に情報があればリストに追加
                errList.add(checkRecordRemain(iwKey, err));

                // 移動作業情報の状態が『作業中』の場合は設定できません。
                err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_MOVE);
                // 作業区分(移動)
                moveKey.setJobType(MoveWorkInfo.JOB_TYPE_MOVEMENT);
                // 状態(入庫作業中 or 出庫作業中)
                String[] workStats = {
                        MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WORKING,
                        MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING,
                };
                moveKey.setStatusFlag(workStats, true);
                // 移動作業情報に情報があればリストに追加
                errList.add(checkRecordRemain(moveKey, err));
            }
        }

        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            // 発生ポイント(出庫)
            checkPoint = SystemOutParameter.POINT_RETRIEVAL;
            // 入出庫作業情報検索キー
            WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

            // 入出庫作業情報の状態が『作業中』の場合は設定できません。
            Error err = new Error(statusNG, reasonWorking, checkPoint);
            // 作業区分(出庫)
            wiKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
            // 状態(作業中)
            wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // 入出庫作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(wiKey, err));
        }

        // 仕分パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasSortingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 仕分作業情報検索キー
            SortWorkInfoSearchKey sortKey = new SortWorkInfoSearchKey();

            // 仕分作業情報の状態が『作業中』の場合は設定できません。
            Error err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_SORT);
            // 作業区分(仕分)
            sortKey.setJobType(SortWorkInfo.JOB_TYPE_SORTING);
            // 状態(作業中)
            sortKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_NOWWORKING);
            // 仕分作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(sortKey, err));
        }

        // 出荷パッケージ、またはクロスドックパッケージが導入されている場合
        if (wsysCtlr.hasShippingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 出荷作業情報検索キー
            ShipWorkInfoSearchKey shipKey = new ShipWorkInfoSearchKey();

            // 出荷作業情報の状態が『作業中』の場合は設定できません。
            Error err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_SHIPPING);
            // 作業区分(出荷)
            shipKey.setJobType(ShipWorkInfo.JOB_TYPE_SHIPPING);
            // 出荷検品状態フラグ(作業中)
            shipKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "=", "(", "", false);
            // バース登録状態フラグ(作業中)
            shipKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "=", "", ")", true);
            // 出荷作業情報に情報があればリストに追加
            errList.add(checkRecordRemain(shipKey, err));
        }
        // PCT出庫パッケージ
        if (wsysCtlr.hasPCTRetrievalPack())
        {
            // 作業中の作業があれば日次更新は行えません。
            // 状態 日時更新不可, NG理由 作業中,発生箇所 PCT出庫
            Error err = new Error(statusNG, reasonWorking, SystemOutParameter.POINT_PCTRET);
            PCTRetWorkInfoSearchKey pctRetWorkKey = new PCTRetWorkInfoSearchKey();
            String[] workStats = {
                    PCTSystemInParameter.STATUS_FLAG_EXIST_WORKING,
                    PCTSystemInParameter.STATUS_FLAG_EXIST_WORKED,
            };
            pctRetWorkKey.setStatusFlag(workStats, true);
            errList.add(checkRecordRemain(pctRetWorkKey, err));
        }
        // 追加されたデータを返却
        return errList;
    }

    /**
     * 日次更新可能かRFT端末のチェックを行います。
     * @param errList エラーリスト
     * @return ErrorList エラーリスト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    @SuppressWarnings("unchecked")
    private List<Params> checkRFTNG(List errList)
            throws CommonException
    {
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        String statusNG = SystemOutParameter.DAILYUPDATE_STATUS_NG;

        // PCTパッケージ
        if (wsysCtlr.hasPCTMasterPack() || wsysCtlr.hasPCTReceivingPack() || wsysCtlr.hasPCTRetrievalPack()
                || wsysCtlr.hasPCTInventoryPack())
        {
            // ----------------------------------------------
            // 起動中のPカートが存在するかチェック
            // ----------------------------------------------
            // 状態 警告,NG理由 起動中,発生箇所 RFT
            Error err = new Error(statusNG, SystemOutParameter.REASON_NOWSTARTING, SystemOutParameter.POINT_PCART);
            RftSearchKey rftKey = new RftSearchKey();
            rftKey.clear();
            rftKey.setStatusFlag(Rft.STATUS_FLAG_UNSTART, "!=");
            rftKey.setTerminalType(Rft.TERMINAL_TYPE_PCART);
            // （RFT作業のダイアログが表示されていない場合）
            errList.add(checkRecordRemain(rftKey, err));
        }

        return errList;
    }

    /**
     * パッケージごとに日次更新の警告内容のチェックを行う<BR>
     * <BR>
     * @param errList エラーリスト
     * @return ErrorList エラーリスト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    @SuppressWarnings("unchecked")
    private List<Params> checkPackageWran(List errList)
            throws CommonException
    {
        // 日次更新状態(警告)
        String statusWarn = SystemOutParameter.DAILYUPDATE_STATUS_WARNING;
        // NG理由(出庫済入庫待)
        String reasonWaitStorage = SystemOutParameter.REASON_WAITSTORAGE;
        // NG理由(未報告データあり)
        String reasonNoReport = SystemOutParameter.REASON_NOREPORT;
        // NG理由(補充データあり)
        String reasonReplenishmentData = SystemOutParameter.REASON_REPLENISHMENTDATA;
        // NG理由(未確定データあり)
        String reasonNoComplete = SystemOutParameter.REASON_NOCOMPLETE;

        // 発生ポイント(NoReason)
        String checkPointSP = SystemOutParameter.POINT_SPACE;
        // システム定義コントローラー
        WarenaviSystemController wsysCtlr = _wsysCtlr;

        //DFKLOOK 3.5 Start
        // 環境設定ファイルから格納フォルダ、ファイル名を取得
        IniFileOperator enviO = new IniFileOperator(WmsParam.ENVIRONMENT);
        //DFKLOOK 3.5 End

        // 一時保存用ファイルが存在する場合
        if (existFilesCount() > 0)
        {
            // 警告・NG理由(報告ファイル残)・発生ポイント(NoReason)
            errList.add(Error.toParam(statusWarn, SystemOutParameter.REASON_REPORTFILESTAY, checkPointSP));
        }

        // 入荷パッケージが導入されている場合
        if (wsysCtlr.hasReceivingPack())
        {
            //DFKLOOK 3.5 Start
            // 警告・NG理由(未報告データあり)・発生ポイント(入荷)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_RECEIVE);

            // 報告単位にて処理分岐
            if (SystemDefine.REPORT_TYPE_PLAN_UNIT_SUM.equals(enviO.get(REPORT_TYPE,
                    TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE])))
            {
                // 報告単位＝予定単位（集約）の場合
                // 入荷予定情報（完了）かつ入荷実績情報（未報告）があればリストに追加
                errList.add(checkRecordRemainReceivingPlan(err));
            }
            else
            {
                // 報告単位＝予定単位（明細）、作業単位の場合
                // 入荷実績送信情報検索キー
                ReceivingHostSendSearchKey receiveHostSend = new ReceivingHostSendSearchKey();

                receiveHostSend.clear();
                // 実績報告区分(未報告)
                receiveHostSend.setReportFlag(ReceivingHostSend.REPORT_FLAG_NOT_REPORT);
                // 入荷実績送信情報に情報があればリストに追加
                errList.add(checkRecordRemain(receiveHostSend, err));
            }
            //DFKLOOK 3.5 End
        }

        // 入庫パッケージが導入されている場合
        if (wsysCtlr.hasStoragePack())
        {
            // 入出庫実績送信情報検索キー
            HostSendSearchKey hostSend = new HostSendSearchKey();
            // 移動実績情報検索キー
            MoveResultSearchKey moveResult = new MoveResultSearchKey();
            // 移動作業情報検索キー
            MoveWorkInfoSearchKey moveKey = new MoveWorkInfoSearchKey();
            // 棚卸実績情報検索キー
            InventResultSearchKey inventResult = new InventResultSearchKey();
            // 棚卸作業情報検索キー
            InventWorkInfoSearchKey inventWorkinfo = new InventWorkInfoSearchKey();

            //DFKLOOK 3.5 Start
            Error err = null;

            if (!SystemDefine.REPORT_TYPE_PLAN_UNIT_SUM.equals(enviO.get(REPORT_TYPE, TYPE_KEY[REPORTTYPE_STORAGE])))
            {
                // 報告単位＝予定単位（明細）、作業単位の場合

                // 警告・NG理由(未報告データあり)・発生ポイント(入庫)
                err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_STORAGE);
                hostSend.clear();
                // 作業区分(入庫)
                hostSend.setJobType(HostSend.JOB_TYPE_STORAGE);
                // 実績報告区分(未報告)
                hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
                // 入出庫実績送信情報に情報があればリストに追加
                errList.add(checkRecordRemain(hostSend, err));
            }
            //DFKLOOK 3.5 End

            // DA版の場合
            if (!wsysCtlr.isFaDaEnabled())
            {
                //DFKLOOK 3.5 Start
                if (SystemDefine.REPORT_TYPE_PLAN_UNIT_SUM.equals(enviO.get(REPORT_TYPE, TYPE_KEY[REPORTTYPE_STORAGE])))
                {
                    // 警告・NG理由(未報告データあり)・発生ポイント(入庫)
                    err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_STORAGE);
                    // 報告単位＝予定単位（集約）の場合
                    // 入庫予定情報（完了）かつ入出庫実績情報（未報告）があればリストに追加
                    errList.add(checkRecordRemainStoragePlan(err));
                }
                //DFKLOOK 3.5 End

                // 警告・NG理由(未報告データあり)・発生ポイント(移動)
                err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_MOVE);
                moveResult.clear();
                // 実績報告区分(未報告)
                moveResult.setReportFlag(MoveResult.REPORT_FLAG_NOT_REPORT);
                // 移動実績情報に情報があればリストに追加
                errList.add(checkRecordRemain(moveResult, err));

                // 警告・NG理由(出庫済入庫待)・発生ポイント(移動)
                err = new Error(statusWarn, reasonWaitStorage, SystemOutParameter.POINT_MOVE);
                moveKey.clear();
                // 作業区分(移動)
                moveKey.setJobType(MoveWorkInfo.JOB_TYPE_MOVEMENT);
                // 状態(出庫済入庫待)
                moveKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING);
                // 移動作業情報に情報があればリストに追加
                errList.add(checkRecordRemain(moveKey, err));

                // 在庫パッケージが導入されている場合
                if (_wsysCtlr.hasStockPack())
                {
                    // 警告・NG理由(補充データあり)・発生ポイント(補充)
                    err = new Error(statusWarn, reasonReplenishmentData, SystemOutParameter.POINT_REPLENISHMENT);
                    moveKey.clear();
                    // 作業区分(移動)
                    moveKey.setJobNo(MoveWorkInfo.JOB_TYPE_MOVEMENT, "!=");
                    // 状態(未作業)
                    moveKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_UNSTART);
                    // 移動作業情報に情報があればリストに追加
                    errList.add(checkRecordRemain(moveKey, err));
                }

                // 警告・NG理由(未報告データあり)・発生ポイント(棚卸)
                err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_INVENT);
                inventResult.clear();
                // 実績報告区分(未報告)
                inventResult.setReportFlag(InventResult.REPORT_FLAG_NOT_REPORT);
                // 棚卸実績情報に情報があればリストに追加
                errList.add(checkRecordRemain(inventResult, err));

                // 警告・NG理由(未確定データあり)・発生ポイント(棚卸)
                err = new Error(statusWarn, reasonNoComplete, SystemOutParameter.POINT_INVENT);
                inventWorkinfo.clear();
                // 状態(棚卸作業済み)
                inventWorkinfo.setStatusFlag(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
                // 棚卸作業情報に情報があればリストに追加
                errList.add(checkRecordRemain(inventWorkinfo, err));
            }
        }

        //DFKLOOK 3.5 Start
        // 出庫パッケージが導入されている場合
        if (wsysCtlr.hasRetrievalPack())
        {
            // 在庫パッケージが導入されている場合
            if (wsysCtlr.hasStockPack())
            {
                // 入出庫作業情報検索キー
                WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
                // 警告・NG理由(入出庫作業情報の状態が『引当済』あり）
                Error err =
                        new Error(statusWarn, SystemOutParameter.REASON_ALLOCATED, SystemOutParameter.POINT_RETRIEVAL);
                wiKey.clear();
                // 作業区分(出庫)
                wiKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
                // 状態(未作業)
                wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                // 入出庫作業情報に情報があればリストに追加
                errList.add(checkRecordRemain(wiKey, err));
            }
        }
        //DFKLOOK 3.5 Start

        // 在庫パッケージが導入されている場合
        if (wsysCtlr.hasStockPack())
        {
            // 入出庫実績送信情報検索キー
            HostSendSearchKey hostSend = new HostSendSearchKey();

            // 警告・NG理由(未報告データあり)・発生ポイント(予定外入庫)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_NOPLANSTORAGE);
            hostSend.clear();
            // 作業区分(予定外入庫)
            hostSend.setJobType(HostSend.JOB_TYPE_NOPLAN_STORAGE);
            // 実績報告区分(未報告)
            hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
            // 入出庫実績送信情報に情報があればリストに追加
            errList.add(checkRecordRemain(hostSend, err));

            // 警告・NG理由(未報告データあり)・発生ポイント(予定外出庫)
            err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_NOPLANRETRIEVAL);
            hostSend.clear();
            // 作業区分(予定外出庫)
            hostSend.setJobType(HostSend.JOB_TYPE_NOPLAN_RETRIEVAL);
            // 実績報告区分(未報告)
            hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
            // 入出庫実績送信情報に情報があればリストに追加
            errList.add(checkRecordRemain(hostSend, err));
        }

        // FA版の場合
        if (wsysCtlr.isFaDaEnabled())
        {
            // 入出庫実績送信情報検索キー
            HostSendSearchKey hostSend = new HostSendSearchKey();

            // 警告・NG理由(未報告データあり)・発生ポイント(再入庫)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_RESTORING);
            hostSend.clear();
            // 作業区分(再入庫)
            hostSend.setJobType(HostSend.JOB_TYPE_RESTORING);
            // 実績報告区分(未報告)
            hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
            // 入出庫実績送信情報に情報があればリストに追加
            errList.add(checkRecordRemain(hostSend, err));
        }

        // 出庫パッケージが導入されていた場合
        if (wsysCtlr.hasRetrievalPack())
        {
            //DFKLOOK 3.5 Start
            // 警告・NG理由(未報告データあり)・発生ポイント(出庫)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_RETRIEVAL);

            // 報告単位にて処理分岐
            if (SystemDefine.REPORT_TYPE_PLAN_UNIT_SUM.equals(enviO.get(REPORT_TYPE, TYPE_KEY[REPORTTYPE_RETRIEVAL])))
            {
                // 報告単位＝予定単位（集約）の場合
                // 出庫予定情報（完了）かつ入出庫実績情報（未報告）があればリストに追加
                errList.add(checkRecordRemainRetrievalPlan(err));
            }
            else
            {
                // 入出庫実績送信情報検索キー
                HostSendSearchKey hostSend = new HostSendSearchKey();

                hostSend.clear();
                // 作業区分(出庫)
                hostSend.setJobType(HostSend.JOB_TYPE_RETRIEVAL);
                // 実績報告区分(未報告)
                hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
                // 入出庫実績送信情報に情報があればリストに追加
                errList.add(checkRecordRemain(hostSend, err));
            }
            //DFKLOOK 3.5 End
        }

        // 仕分パッケージ、またはクロスドックパッケージが導入されていた場合
        if (wsysCtlr.hasSortingPack() || wsysCtlr.hasCrossdockPack())
        {
            // 仕分実績送信情報検索キー
            SortHostSendSearchKey sortHostSend = new SortHostSendSearchKey();

            // 警告・NG理由(未報告)・発生ポイント(仕分)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_SORT);
            sortHostSend.clear();
            // 実績報告区分(未報告)
            sortHostSend.setReportFlag(SortHostSend.REPORT_FLAG_NOT_REPORT);
            // 仕分実績送信情報に情報があればリストに追加
            errList.add(checkRecordRemain(sortHostSend, err));
        }

        // 出荷パッケージが導入されていた場合
        if (wsysCtlr.hasShippingPack())
        {
            // 出荷実績送信情報検索キー
            ShipHostSendSearchKey shipHostSend = new ShipHostSendSearchKey();

            // 警告・NG理由(未報告)・発生ポイント(出荷)
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_SHIPPING);
            shipHostSend.clear();
            // 実績報告区分(未報告)
            shipHostSend.setReportFlag(ShipHostSend.REPORT_FLAG_NOT_REPORT);
            // 出荷実績送信情報に情報があればリストに追加
            errList.add(checkRecordRemain(shipHostSend, err));
        }
        // PCT出庫パッケージ
        if (wsysCtlr.hasPCTRetrievalPack())
        {
            // PCT出庫実績送信の確認　未報告データあり
            // 状態 警告,NG理由 未報告データあり,発生箇所 PCT出庫
            Error err = new Error(statusWarn, reasonNoReport, SystemOutParameter.POINT_PCTRET);
            PCTRetHostSendSearchKey pctRetHostSend = new PCTRetHostSendSearchKey();
            pctRetHostSend.clear();
            pctRetHostSend.setReportFlag(PCTRetHostSend.REPORT_FLAG_NOT_REPORT);
            errList.add(checkRecordRemain(pctRetHostSend, err));

            // 作業データの確認 未作業
            // 状態 警告,NG理由 未作業データ有り,発生箇所 PCT出庫
            err = new Error(statusWarn, SystemOutParameter.REASON_ALLOCATED, SystemOutParameter.POINT_PCTRET);
            PCTRetWorkInfoSearchKey winfoKey = new PCTRetWorkInfoSearchKey();
            winfoKey.clear();
            winfoKey.setStatusFlag(PCTRetWorkInfo.STATUS_FLAG_UNSTART);
            errList.add(checkRecordRemain(winfoKey, err));
        }
        // 追加されたデータを返却
        return errList;
    }

    /**
     * 一時保存用ディレクトリのファイル数を数えます。<BR>
     * <BR>
     * @return int 検出したファイルの数を返します。
     * @throws ReadWriteException INIファイルの読み込みに失敗
     */
    private int existFilesCount()
            throws ReadWriteException
    {
        // Operatorがnull
        if (null == _iniOperator)
        {
            // 生成
            _iniOperator = new IniFileOperator(WmsParam.ENVIRONMENT);
        }

        // ファイル名の取得
        String[] repPfxKeys = _iniOperator.getKeys(REPORTDATA_FILENAME);
        // 存在しない場合
        if (ArrayUtil.isEmpty(repPfxKeys))
        {
            // 0を返却
            return 0;
        }

        // 該当ファイルの数を取得
        File backuppath = new File(WmsParam.HOSTDATA_LOCAL_PATH);
        int numFiles = 0;
        for (String pfxKey : repPfxKeys)
        {
            // 接頭辞を取得
            String prefix = _iniOperator.get(REPORTDATA_FILENAME, pfxKey);
            // 拡張子を取得
            String ext = Parameter.EXTENSION;

            // ファイルを検索
            FilenameFilter fnfilter = new PrePostFileFilter(prefix, ext);
            String[] fnames = backuppath.list(fnfilter);
            if (null == fnames)
            {
                // 存在しなければ次ファイルへ
                continue;
            }
            numFiles += fnames.length;
        }
        // 検索数を返却
        return numFiles;
    }

    /**
     * 検索キーからデータベースレコードをチェックし、レコードが残っている場合は、<BR>
     * ErrorからParams配列を生成して返します。<BR>
     * レコードが残っていない場合は nullが返されます。<BR>
     * <BR>
     * @param key 検索キー
     * @param err エラー情報
     * @return レコードが残っているときはエラー情報
     * @throws ReadWriteException データベースアクセスエラー
     */
    private Params checkRecordRemain(SQLSearchKey key, Error err)
            throws ReadWriteException
    {
        // エラー情報が存在しない場合
        if (null == err)
        {
            // nullを返却
            return null;
        }

        // データベースハンドラー
        DatabaseHandler h = _sharedHandler;
        // データが存在している場合true、それ以外false
        boolean recRemain = (0 < h.count(key));
        // エラー情報の取得
        Params param = (recRemain) ? err.toParam()
                                  : null;

        // 取得したエラー情報を返却
        return param;
    }

    /**
     * 日次更新可能かRFT端末のチェックを行います。<BR>
     * <BR>
     * @param errList エラーリスト
     * @return ErrorList エラーリスト
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    @SuppressWarnings("unchecked")
    private List<Params> checkRFT(List errList)
            throws CommonException
    {
        // 日次更新状態(警告)
        String statusWarn = SystemOutParameter.DAILYUPDATE_STATUS_WARNING;
        // RFT管理情報
        RftSearchKey rftKey = new RftSearchKey();

        // 警告・NG理由(起動中)・発生ポイント(RFT)
        Error err = new Error(statusWarn, SystemOutParameter.REASON_NOWSTARTING, SystemOutParameter.POINT_RFT);
        // 状態(未作業以外)
        rftKey.setStatusFlag(Rft.STATUS_FLAG_UNSTART, "!=");
        rftKey.setTerminalType(Rft.TERMINAL_TYPE_HT);
        // RFT管理情報に情報があればリストに追加
        errList.add(checkRecordRemain(rftKey, err));

        // 追加されたデータを返却
        return errList;
    }

    /**
     * 実績集計データをピッキング実績集計情報に登録します。
     * @param conn データベースとのコネクションオブジェクト
     * @param workDate 作業日
     * @throws CommonException 例外が発生した場合に通知されます。
     *
     */
    protected void registPickingResult(Connection conn, String workDate)
            throws CommonException
    {
        // PCTユーザー実績情報のハンドラ定義
        PCTUserResultSearchKey userResultKey = new PCTUserResultSearchKey();

        // PCTオーダー情報のハンドラ定義
        PCTOrderInfoFinder orderFinder = new PCTOrderInfoFinder(conn);
        PCTOrderInfoSearchKey ordershKey = new PCTOrderInfoSearchKey();
        PCTOrderInfoHandler tmpHandler = new PCTOrderInfoHandler(conn);
        PCTOrderInfoSearchKey tmpKey = new PCTOrderInfoSearchKey();

        // 実績区分が運用、作業区分がピッキング、完了区分がPカート、終了日時が登録されている
        Date dmyDate = null;
        userResultKey.setResultType(PCTRetrievalInParameter.RESULT_TYPE_OPERATION);
        userResultKey.setJobType(PCTRetrievalInParameter.DATA_TYPE_RETRIEVAL);
        userResultKey.setCompleteKind(PCTRetrievalInParameter.COMPLETE_KIND_PCART);
        userResultKey.setUserId("", "!=");
        userResultKey.setWorkEndtime(dmyDate, "!=");
        userResultKey.setUserIdOrder(true);
        userResultKey.setWorkDateOrder(true);
        userResultKey.setWorkStarttimeOrder(true);

        PCTUserResultFinder userResultFinder = new PCTUserResultFinder(conn);
        try
        {
            userResultFinder.open(true);
            if (userResultFinder.search(userResultKey) == 0)
            {
                return;
            }

            while (userResultFinder.hasNext())
            {
                PCTUserResult[] userResultEntity = (PCTUserResult[])userResultFinder.getEntities(100);

                // PCTオーダー情報のハンドラ定義
                PCTOrderInfo[] tmpEntity = null;

                // ピッキング実績集計情報のハンドラ定義
                PCTPickingResult pickingEntity = new PCTPickingResult();

                for (PCTUserResult userResult : userResultEntity)
                {
                    pickingEntity.clear();

                    // 作業用領域
                    PCTPickingResult wkEntity = new PCTPickingResult();

                    String oldSettingUnitKey = null; // 設定単位キー

                    ordershKey.clear();
                    // 読込んだユーザ情報データのユーザIDと作業開始日時、作業終了日時を
                    // PCTOrderInfoSearchKeyにセット
                    ordershKey.setWorkStarttime(userResult.getWorkStarttime(), ">=", "(", "", true);
                    ordershKey.setWorkStarttime(userResult.getWorkEndtime(), "<=", "", ")", true);
                    ordershKey.setUserId(userResult.getUserId());
                    ordershKey.setTerminalNo(userResult.getTerminalNo());
                    ordershKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);
                    ordershKey.setWorkStarttimeOrder(true);
                    ordershKey.setWorkEndtimeOrder(true);

                    orderFinder.open(true);
                    if (orderFinder.search(ordershKey) == 0)
                    {
                        continue;
                    }

                    boolean firstFlag = true;
                    while (orderFinder.hasNext())
                    {
                        PCTOrderInfo[] orderEntity = (PCTOrderInfo[])orderFinder.getEntities(100);

                        for (int j = 0; j < orderEntity.length; j++)
                        {
                            // 初期処理
                            if (firstFlag)
                            {
                                // INSERT用領域に初期データをセットする
                                wkEntity.setStartTime(userResult.getWorkStarttime());
                                setFirstPickingEntity(conn, pickingEntity, userResult, wkEntity, orderEntity[j]);

                                oldSettingUnitKey = orderEntity[j].getSettingUnitKey(); // 設定単位キー
                                firstFlag = false;
                                continue;
                            }

                            // 荷主コード、エリア、バッチNoのいずれかが違う場合
                            if ((!pickingEntity.getConsignorCode().equals(orderEntity[j].getConsignorCode()))
                                    || (!pickingEntity.getAreaNo().equals(orderEntity[j].getAreaNo()))
                                    || (!pickingEntity.getBatchNo().equals(orderEntity[j].getBatchNo())))
                            {
                                // ピッキング実績情報登録
                                createPickingResult(conn, pickingEntity, wkEntity);

                                // INSERT用領域に初期データをセットする
                                wkEntity.setStartTime(wkEntity.getEndTime());
                                setFirstPickingEntity(conn, pickingEntity, userResult, wkEntity, orderEntity[j]);

                                oldSettingUnitKey = orderEntity[j].getSettingUnitKey(); // 設定単位キー

                                continue;
                            }

                            // 記憶している終了日時より読込んだ終了日時の方が遅い場合
                            if (!StringUtil.isBlank(wkEntity.getEndTime()))
                            {
                                if ((!StringUtil.isBlank(orderEntity[j].getWorkEndtime()))
                                        && (wkEntity.getEndTime().compareTo(orderEntity[j].getWorkEndtime()) < 0))
                                {
                                    // 終了日時を更新
                                    wkEntity.setEndTime(orderEntity[j].getWorkEndtime());
                                }
                            }
                            else
                            {
                                // 終了日時を更新
                                wkEntity.setEndTime(orderEntity[j].getWorkEndtime());
                            }

                            // 設定単位キーが違う場合
                            if (!oldSettingUnitKey.equals(orderEntity[j].getSettingUnitKey()))
                            {
                                tmpKey.clear();
                                // 読込んだユーザ情報データのユーザIDと作業開始日時、作業終了日時を
                                // PCTOrderInfoSearchKeyにセット
                                tmpKey.setWorkStarttimeCollect("MIN");
                                tmpKey.setWorkEndtimeCollect("MAX");
                                tmpKey.setUserId(userResult.getUserId());
                                tmpKey.setTerminalNo(userResult.getTerminalNo());
                                tmpKey.setConsignorCode(pickingEntity.getConsignorCode());
                                tmpKey.setAreaNo(pickingEntity.getAreaNo());
                                tmpKey.setBatchNo(pickingEntity.getBatchNo());
                                tmpKey.setSettingUnitKey(orderEntity[j].getSettingUnitKey());
                                tmpKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);

                                tmpEntity = (PCTOrderInfo[])tmpHandler.find(tmpKey);
                                int tmpWorkTime =
                                        wkEntity.getWorkTime()
                                                + (int)(tmpEntity[0].getWorkEndtime().getTime() - tmpEntity[0].getWorkStarttime().getTime())
                                                / 1000; // 作業時間
                                wkEntity.setWorkTime(tmpWorkTime);

                                oldSettingUnitKey = orderEntity[j].getSettingUnitKey(); // 設定単位キー
                            }

                            // 荷主コード、エリア、バッチNoが同じ間は作業数量等を足し込んでいきます
                            // 作業用領域にデータセット
                            wkEntity.setWorkQty(wkEntity.getWorkQty() + orderEntity[j].getWorkQty()); // 作業数量
                            wkEntity.setPieceQty(wkEntity.getPieceQty() + orderEntity[j].getPieceQty()); // 作業数量（バラ）
                            wkEntity.setWorkCnt(wkEntity.getWorkCnt() + orderEntity[j].getWorkCnt()); // 作業回数（明細数）
                            wkEntity.setBoxCnt(wkEntity.getBoxCnt() + 1); // 集品箱数
                            wkEntity.setRealWorkTime(wkEntity.getRealWorkTime() + orderEntity[j].getRealWorkTime()); // 実作業時間
                            wkEntity.setMissScanCnt(wkEntity.getMissScanCnt() + orderEntity[j].getMissScanCnt()); // ミススキャン数
                        }
                    }

                    // オーダー情報が存在した場合
                    if (!firstFlag)
                    {
                        wkEntity.setEndTime(userResult.getWorkEndtime());
                        // ピッキング実績情報登録
                        createPickingResult(conn, pickingEntity, wkEntity);
                    }
                }
            }
        }
        finally
        {
            closeFinder(userResultFinder);
            closeFinder(orderFinder);
        }
    }

    /**
     * PCT出庫作業情報の削除処理
     * 予定情報の削除
     * @param conn データベースコネクション
     * @param unWorkDelete 未作業削除有無
     * @param deleteDate 削除境界日時
     * @param resultDelDay 実績削除境界日時
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deletePCTRetrieval(Connection conn, boolean unWorkDelete, String deleteDate, String resultDelDay)
            throws CommonException
    {
        // 予定情報の削除
        deletePCTRetrievalPlan(conn, unWorkDelete, deleteDate);

        // 保持期限超過情報の削除
        deleteExpirePCTRetrieval(conn, unWorkDelete, deleteDate, resultDelDay);
    }

    /**
     * PCT出庫予定情報の削除処理
     *
     * @param conn コネクション
     * @param unWorkDelete 未作業削除有無
     * @param deleteDate 予定情報削除境界日時
     * @throws CommonException 全ての例外が通知されます。
     */
    protected void deletePCTRetrievalPlan(Connection conn, boolean unWorkDelete, String deleteDate)
            throws CommonException
    {
        // PCT出庫予定情報のデータを削除
        PCTRetPlanSearchKey planKey = new PCTRetPlanSearchKey();

        // (状態が完了且つ報告フラグが報告済)、又は 状態が削除のデータを検索
        // PlanUKeyに紐付く作業情報、実績送信情報を削除
        planKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_COMPLETION, "=", "(", "", true);
        planKey.setReportFlag(PCTRetPlan.REPORT_FLAG_REPORT, "=", "", ")", false);
        planKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "=", "", "", false);

        PCTRetPlanFinder finder = new PCTRetPlanFinder(conn);
        try
        {
            finder.open(true);
            if (finder.search(planKey) == 0)
            {
                return;
            }

            while (finder.hasNext())
            {
                PCTRetPlan[] plans = (PCTRetPlan[])finder.getEntities(100);
                PCTRetWorkInfoSearchKey workKey = new PCTRetWorkInfoSearchKey();
                PCTRetHostSendSearchKey hostKey = new PCTRetHostSendSearchKey();
                // PlanUKeyに紐付く作業情報、実績送信情報を削除
                for (PCTRetPlan plan : plans)
                {
                    String planUkey = plan.getPlanUkey();
                    workKey.setPlanUkey(planUkey, "=", "", "", false);
                    hostKey.setPlanUkey(planUkey, "=", "", "", false);
                }
                dropRecords(workKey);
                // 実績送信情報の移動削除
                movePCTRetHostSendToResult(hostKey);
            }
            // 未作業情報「削除する」の場合
            if (unWorkDelete)
            {
                // 保持日数を超えたPCT出庫予定情報を削除します。
                // (状態フラグ = 未開始 and スケジュールフラグ = 未スケジュール and 予定日 <= 保持期日 )
                planKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART, "=", "(", "", true);
                planKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE, "=", "", "", true);
                planKey.setPlanDay(_wsysCtlr.getWorkDay(), COMPARE_LT_EQ, "", ")", true);
            }
            // 予定情報の削除
            dropRecords(planKey);
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * オーダー情報の削除処理
     * @param conn データベースコネクション
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deleteOrderInfo(Connection conn)
            throws ReadWriteException
    {
        // PCTオーダー情報（DNPCTOrderInfo）の削除
        PCTOrderInfoSearchKey pctOrderInfoKey = new PCTOrderInfoSearchKey();
        pctOrderInfoKey.setStatusFlag(PCTOrderInfo.STATUS_FLAG_COMPLETION);
        dropRecords(pctOrderInfoKey);
    }

    /**
     * PCTユーザ実績情報の削除処理
     * @param conn データベースコネクション
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deletePCTUserResult(Connection conn)
            throws ReadWriteException
    {
        // PCTユーザ実績情報（DNPCTUserResult）の削除
        PCTUserResultSearchKey pctUserResultKey = new PCTUserResultSearchKey();
        dropRecords(pctUserResultKey);
    }

    /**
     * ピッキング実績集計情報の削除処理
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deletePCTPickingResult(Connection conn, String deleteDate)
            throws ReadWriteException
    {
        if (!StringUtil.isBlank(deleteDate))
        {
            // ピッキング実績集計情報のデータを削除
            // 保持日数を超えたピッキング実績集計情報を削除します。
            PCTPickingResultSearchKey pickingKey = new PCTPickingResultSearchKey();
            pickingKey.setWorkDay(deleteDate, COMPARE_LT_EQ);
            dropRecords(pickingKey);
        }
    }

    /**
     * 全ユーザ実績情報の削除処理
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deletePCTAllUserResult(Connection conn, Date deleteDate)
            throws ReadWriteException
    {
        // 全ユーザ実績集計情報のデータを削除
        // 保持日数を超えた全ユーザ実績集計情報を削除します。
        PCTAllUserResultSearchKey allUserKey = new PCTAllUserResultSearchKey();
        allUserKey.setLastUpdateDate(deleteDate, COMPARE_LT);
        dropRecords(allUserKey);
    }

    /**
     * PCT操作ログ情報の削除処理
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deletePCTOperationLog(Connection conn, Date deleteDate)
            throws ReadWriteException
    {
        // PCT操作ログ情報のデータを削除
        // 保持日数を超えたPCT操作ログ情報を削除します。
        PCTOperationLogSearchKey delKey = new PCTOperationLogSearchKey();
        delKey.setLogDate(deleteDate, COMPARE_LT);
        dropRecords(delKey);
    }

    /**
     * 重量差異情報の削除処理
     * @param conn データベースコネクション
     * @param deleteDate 削除境界日時
     * @throws ReadWriteException スケジュールエラーを報告します。
     */
    protected void deleteWeightDistinct(Connection conn, Date deleteDate)
            throws ReadWriteException
    {
        // 重量差異情報のデータを削除
        // 保持日数を超えた重量差異情報を削除します。
        WeightDistinctSearchKey weightKey = new WeightDistinctSearchKey();
        weightKey.setRegistDate(deleteDate, COMPARE_LT);
        dropRecords(weightKey);
    }

    /**
     * 保持期間が超過したPCT出庫情報の削除処理
     *
     * @param conn データベースコネクション
     * @param unWorkDelete 未作業を持ち越すか
     * @param deleteDate 作業情報削除境界日時
     * @param resultDelDay 実績情報削除境界日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deleteExpirePCTRetrieval(Connection conn, boolean unWorkDelete, String deleteDate,
            String resultDelDay)
            throws ReadWriteException
    {
        if (!StringUtil.isBlank(deleteDate))
        {
            // 保持期限が超過した予定情報の削除
            PCTRetPlanSearchKey planKey = new PCTRetPlanSearchKey();
            planKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            planKey.setKey(PCTRetPlan.STATUS_FLAG, PCTRetPlan.STATUS_FLAG_UNSTART, "=", "NOT(", "", true);
            planKey.setKey(PCTRetPlan.SCH_FLAG, PCTRetPlan.SCH_FLAG_SCHEDULE, "=", "", ")", true);
            dropRecords(planKey);

            // 作業情報
            PCTRetWorkInfoSearchKey workKey = new PCTRetWorkInfoSearchKey();
            workKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);

            // 作業状態「未作業」以外のデータを削除
            workKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART, "!=");
            dropRecords(workKey);

            // 実績送信情報
            PCTRetHostSendSearchKey hostKey = new PCTRetHostSendSearchKey();
            hostKey.setPlanDay(deleteDate, COMPARE_LT_EQ, "", "", true);
            dropRecords(hostKey);
        }
        if (!StringUtil.isBlank(resultDelDay))
        {
            // 実績情報
            PCTRetResultSearchKey resultKey = new PCTRetResultSearchKey();
            // 実績保持日数超過データ削除
            // 作業日 ＜ 実績保持日数
            resultKey.setWorkDay(resultDelDay, COMPARE_LT_EQ, "", "", true);
            dropRecords(resultKey);
        }
    }

    /**
     * PCTRetHostSendエンティティからPCTRetResultエンティティに入れ替える処理
     *
     * @param hostSend PCTRetHostSendの配列情報
     * @return Result[] PCTRetResultの配列情報
     */
    protected PCTRetResult[] entReplacePCT(PCTRetHostSend[] hostSend)
    {
        PCTRetResult[] result = new PCTRetResult[hostSend.length];
        for (int i = 0; i < hostSend.length; i++)
        {
            result[i] = new PCTRetResult();

            result[i].setWorkDay(hostSend[i].getWorkDay());
            result[i].setJobNo(hostSend[i].getJobNo());
            result[i].setSettingUnitKey(hostSend[i].getSettingUnitKey());
            result[i].setCollectJobNo(hostSend[i].getCollectJobNo());
            result[i].setStatusFlag(hostSend[i].getStatusFlag());
            result[i].setHardwareType(hostSend[i].getHardwareType());
            result[i].setPlanUkey(hostSend[i].getPlanUkey());
            result[i].setStockId(hostSend[i].getStockId());
            result[i].setSystemConnKey(hostSend[i].getSystemConnKey());
            result[i].setPlanDay(hostSend[i].getPlanDay());
            result[i].setConsignorCode(hostSend[i].getConsignorCode());
            result[i].setConsignorName(hostSend[i].getConsignorName());
            result[i].setRegularCustomerCode(hostSend[i].getRegularCustomerCode());
            result[i].setRegularCustomerName(hostSend[i].getRegularCustomerName());
            result[i].setCustomerCode(hostSend[i].getCustomerCode());
            result[i].setCustomerName(hostSend[i].getCustomerName());
            result[i].setCustomerCategory(hostSend[i].getCustomerCategory());
            result[i].setShipTicketNo(hostSend[i].getShipTicketNo());
            result[i].setShipLineNo(hostSend[i].getShipLineNo());
            result[i].setShipBranchNo(hostSend[i].getShipBranchNo());
            result[i].setBatchNo(hostSend[i].getBatchNo());
            result[i].setBatchSeqNo(hostSend[i].getBatchSeqNo());
            result[i].setOrderNo(hostSend[i].getOrderNo());
            result[i].setOrderSeq(hostSend[i].getOrderSeq());
            result[i].setOrderInfo(hostSend[i].getOrderInfo());
            result[i].setPlanOrderNo(hostSend[i].getPlanOrderNo());
            result[i].setResultOrderNo(hostSend[i].getResultOrderNo());
            result[i].setThroughNo(hostSend[i].getThroughNo());
            result[i].setOrderItemQty(hostSend[i].getOrderItemQty());
            result[i].setOrderThroughNo(hostSend[i].getOrderThroughNo());
            result[i].setOrderThroughNoCnt(hostSend[i].getOrderThroughNoCnt());
            result[i].setGeneralFlag(hostSend[i].getGeneralFlag());
            result[i].setShootNo(hostSend[i].getShootNo());
            result[i].setPlanAreaNo(hostSend[i].getPlanAreaNo());
            result[i].setPlanZoneNo(hostSend[i].getPlanZoneNo());
            result[i].setWorkZoneNo(hostSend[i].getWorkZoneNo());
            result[i].setPlanLocationNo(hostSend[i].getPlanLocationNo());
            result[i].setItemCode(hostSend[i].getItemCode());
            result[i].setItemName(hostSend[i].getItemName());
            result[i].setEnteringQty(hostSend[i].getEnteringQty());
            result[i].setBundleEnteringQty(hostSend[i].getBundleEnteringQty());
            result[i].setLotEnteringQty(hostSend[i].getLotEnteringQty());
            result[i].setJan(hostSend[i].getJan());
            result[i].setItf(hostSend[i].getItf());
            result[i].setBundleItf(hostSend[i].getBundleItf());
            result[i].setUseByDate(hostSend[i].getUseByDate());
            result[i].setItemInfo(hostSend[i].getItemInfo());
            result[i].setPlanLotNo(hostSend[i].getPlanLotNo());
            result[i].setPlanQty(hostSend[i].getPlanQty());
            result[i].setResultQty(hostSend[i].getResultQty());
            result[i].setShortageQty(hostSend[i].getShortageQty());
            result[i].setReportFlag(hostSend[i].getReportFlag());
            result[i].setUserId(hostSend[i].getUserId());
            result[i].setUserName(hostSend[i].getUserName());
            result[i].setTerminalNo(hostSend[i].getTerminalNo());
            result[i].setWorkSecond(hostSend[i].getWorkSecond());
            result[i].setRegistPname(getClass().getSimpleName());
            result[i].setLastUpdatePname(getClass().getSimpleName());
        }
        return result;
    }

    /**
     * ピッキング実績集計データをベースにPCT全ユーザ実績情報を登録します。
     * @param conn データベースとのコネクションオブジェクト
     * @param workDate 作業日
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    protected void registAllUserResult(Connection conn, String workDate)
            throws CommonException
    {
        // PCTシステム情報を取得する
        PCTSystemOutParameter outPCTSystemData = getPCTSystemData();

        // ランク設定フラグが自動設定の場合のみ処理を行う
        if (PCTSystemInParameter.WORK_RANK_SET_TYPE_AUTO.equals(outPCTSystemData.getWorkRankSetType()))
        {
            // ピッキング実績集計情報のハンドラ定義
            PCTPickingResultFinder pickingFinder = new PCTPickingResultFinder(conn);
            PCTPickingResultSearchKey pickingKey = new PCTPickingResultSearchKey();

            // 荷主、エリア毎のデータを検索
            pickingKey.setConsignorCodeCollect();
            pickingKey.setAreaNoCollect();
            pickingKey.setOperateTimeCollect("SUM");
            pickingKey.setWorkQtyCollect("SUM");
            pickingKey.setWorkCntCollect("SUM");
            pickingKey.setOrderCntCollect("SUM");
            pickingKey.setBatteryChangeTimeCollect("SUM");
            pickingKey.setBreakTimeCollect("SUM");

            pickingKey.setConsignorCodeGroup();
            pickingKey.setAreaNoGroup();
            pickingKey.setConsignorCodeOrder(true);
            pickingKey.setAreaNoOrder(true);

            try
            {
                pickingFinder.open(true);
                if (pickingFinder.search(pickingKey) == 0)
                {
                    return;
                }

                while (pickingFinder.hasNext())
                {
                    PCTPickingResult[] pickingEntity = (PCTPickingResult[])pickingFinder.getEntities(100);

                    // PCT全ユーザ実績情報のハンドラ定義
                    PCTAllUserResultHandler allUserResultHandler = new PCTAllUserResultHandler(conn);
                    PCTAllUserResultSearchKey allUserResultSKey = new PCTAllUserResultSearchKey();
                    PCTAllUserResultAlterKey allUserResultAKey = new PCTAllUserResultAlterKey();

                    for (PCTPickingResult pickResult : pickingEntity)
                    {
                        // 総実稼働時間
                        double realOperateTime =
                                (double)pickResult.getOperateTime() - (double)pickResult.getBatteryChangeTime()
                                        - (double)pickResult.getBreakTime();
                        if (realOperateTime != 0)
                        {
                            realOperateTime = realOperateTime / 3600;
                        }

                        /*
                         * ランク毎の値をセット
                         */

                        /*
                         * ランクBの算出結果
                         */
                        // ﾛｯﾄ/H(ランクB)
                        double lotStdVal_B = getPerHour(pickResult.getWorkQty(), realOperateTime);
                        // ｵｰﾀﾞｰ/H(ランクB)
                        double orderStdVal_B = getPerHour(pickResult.getOrderCnt(), realOperateTime);
                        // 行/H(ランクB)
                        double lineStdVal_B = getPerHour(pickResult.getWorkCnt(), realOperateTime);

                        /*
                         * ランクAの算出結果
                         */
                        // ﾛｯﾄ/H(ランクA)
                        double lotStdVal_A = getRate(lotStdVal_B, outPCTSystemData.getRankA());
                        // ｵｰﾀﾞｰ/H(ランクA)
                        double orderStdVal_A = getRate(orderStdVal_B, outPCTSystemData.getRankA());
                        // 行/H(ランクA)
                        double lineStdVal_A = getRate(lineStdVal_B, outPCTSystemData.getRankA());

                        /*
                         * ランクCの算出結果
                         */
                        // ﾛｯﾄ/H(ランクC)
                        double lotStdVal_C = getRate(lotStdVal_B, outPCTSystemData.getRankB());
                        // ｵｰﾀﾞｰ/H(ランクC)
                        double orderStdVal_C = getRate(orderStdVal_B, outPCTSystemData.getRankB());
                        // 行/H(ランクC)
                        double lineStdVal_C = getRate(lineStdVal_B, outPCTSystemData.getRankB());


                        // PCT全ユーザ実績情報の検索キークリア
                        allUserResultSKey.clear();
                        // 荷主コード
                        allUserResultSKey.setConsignorCode(pickResult.getConsignorCode());
                        // エリア
                        allUserResultSKey.setAreaNo(pickResult.getAreaNo());

                        // 更新対象データが存在する場合は、更新処理を行なう。
                        if (allUserResultHandler.count(allUserResultSKey) > 0)
                        {
                            /*
                             * 更新条件(ランクB)
                             */
                            allUserResultAKey.clear();
                            // 荷主コード
                            allUserResultAKey.setConsignorCode(pickResult.getConsignorCode());
                            // エリア
                            allUserResultAKey.setAreaNo(pickResult.getAreaNo());
                            // ランクB
                            allUserResultAKey.setRank(SystemDefine.RANK_NO_B);

                            /*
                             * 更新値(ランクB)
                             */
                            // ﾛｯﾄ/H
                            allUserResultAKey.updateLotStandardValue(lotStdVal_B);
                            // ｵｰﾀﾞｰ/H
                            allUserResultAKey.updateOrderStandardValue(orderStdVal_B);
                            // 行/H
                            allUserResultAKey.updateLineStandardValue(lineStdVal_B);
                            // 最終更新処理名
                            allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                            // 更新処理
                            allUserResultHandler.modify(allUserResultAKey);

                            /*
                             * 更新条件(ランクA)
                             */
                            allUserResultAKey.clear();
                            // 荷主コード
                            allUserResultAKey.setConsignorCode(pickResult.getConsignorCode());
                            // エリア
                            allUserResultAKey.setAreaNo(pickResult.getAreaNo());
                            // ランクA
                            allUserResultAKey.setRank(SystemDefine.RANK_NO_A);

                            /*
                             * 更新値(ランクA)
                             */
                            // ﾛｯﾄ/H
                            allUserResultAKey.updateLotStandardValue(lotStdVal_A);
                            // ｵｰﾀﾞｰ/H
                            allUserResultAKey.updateOrderStandardValue(orderStdVal_A);
                            // 行/H
                            allUserResultAKey.updateLineStandardValue(lineStdVal_A);
                            // 最終更新処理名
                            allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                            // 更新処理
                            allUserResultHandler.modify(allUserResultAKey);

                            /*
                             * 更新条件(ランクC)
                             */
                            allUserResultAKey.clear();
                            // 荷主コード
                            allUserResultAKey.setConsignorCode(pickResult.getConsignorCode());
                            // エリア
                            allUserResultAKey.setAreaNo(pickResult.getAreaNo());
                            // ランクC
                            allUserResultAKey.setRank(SystemDefine.RANK_NO_C);

                            /*
                             * 更新値(ランクC)
                             */
                            // ﾛｯﾄ/H
                            allUserResultAKey.updateLotStandardValue(lotStdVal_C);
                            // ｵｰﾀﾞｰ/H
                            allUserResultAKey.updateOrderStandardValue(orderStdVal_C);
                            // 行/H
                            allUserResultAKey.updateLineStandardValue(lineStdVal_C);
                            // 最終更新処理名
                            allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                            // 更新処理
                            allUserResultHandler.modify(allUserResultAKey);
                        }
                        // 更新対象データが存在しない場合は、登録処理を行なう。
                        else
                        {
                            /*
                             * 共通項目セット
                             */
                            PCTAllUserResult allUserResult = new PCTAllUserResult();
                            // 荷主コード
                            allUserResult.setConsignorCode(pickResult.getConsignorCode());
                            // エリア
                            allUserResult.setAreaNo(pickResult.getAreaNo());
                            // 登録処理名
                            allUserResult.setRegistPname(this.getClass().getSimpleName());
                            // 最終更新処理名
                            allUserResult.setLastUpdatePname(this.getClass().getSimpleName());

                            /*
                             * 登録値(ランクB)
                             */
                            // ランクB
                            allUserResult.setRank(SystemDefine.RANK_NO_B);
                            // ﾛｯﾄ/H
                            allUserResult.setLotStandardValue(lotStdVal_B);
                            // ｵｰﾀﾞｰ/H
                            allUserResult.setOrderStandardValue(orderStdVal_B);
                            // 行/H
                            allUserResult.setLineStandardValue(lineStdVal_B);
                            // 登録処理
                            allUserResultHandler.create(allUserResult);

                            /*
                             * 登録値(ランクA)
                             */
                            // ランクA
                            allUserResult.setRank(SystemDefine.RANK_NO_A);
                            // ﾛｯﾄ/H
                            allUserResult.setLotStandardValue(lotStdVal_A);
                            // ｵｰﾀﾞｰ/H
                            allUserResult.setOrderStandardValue(orderStdVal_A);
                            // 行/H
                            allUserResult.setLineStandardValue(lineStdVal_A);
                            // 登録処理
                            allUserResultHandler.create(allUserResult);

                            /*
                             * 登録値(ランクC)
                             */
                            // ランクC
                            allUserResult.setRank(SystemDefine.RANK_NO_C);
                            // ﾛｯﾄ/H
                            allUserResult.setLotStandardValue(lotStdVal_C);
                            // ｵｰﾀﾞｰ/H
                            allUserResult.setOrderStandardValue(orderStdVal_C);
                            // 行/H
                            allUserResult.setLineStandardValue(lineStdVal_C);
                            // 登録処理
                            allUserResultHandler.create(allUserResult);
                        }
                    }
                }
            }
            finally
            {
                closeFinder(pickingFinder);
            }
        }
    }

    /**
     * 保持期間を過ぎたPCT用マスタのレコードを削除します。<br>
     * マスタ保持期間が設定されていない場合はマスタ削除処理を行いません。
     * <ol>
     * 以下のマスタが対象です。
     * <li>商品マスタ
     * <li>仕入先マスタ
     * <li>出荷先マスタ
     * </ol>
     *
     * @param conn データベースとのコネクションオブジェクト
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void deletePCTMaster(Connection conn)
            throws CommonException
    {
        // マスタ保持期限が設定されていない場合はマスタ削除処理を行わない。
        if (null != _masterHoldLimitDay)
        {
            WarenaviSystemController wsysCtlr = _wsysCtlr;

            if (wsysCtlr.hasPCTMasterPack())
            {
                // PCT商品マスタ削除処理
                delPCTItemMaster(conn);
                // PCT出荷先マスタ削除処理
                delPCTCustomerMaster(conn);
            }
        }
    }

    /**
     * PCT商品マスタ削除処理<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void delPCTItemMaster(Connection conn)
            throws CommonException
    {
        // PCT商品マスタハンドラ、ファインダ 更新キー
        PCTItemHandler itemH = new PCTItemHandler(conn);
        PCTItemSearchKey addKey = new PCTItemSearchKey();

        WarenaviSystemController wsysCtlr = _wsysCtlr;

        if (wsysCtlr.hasPCTRetrievalPack())
        {
            // PCT出庫予定情報に存在するPCT商品マスタ情報を更新
            addKey.clear();
            addKey.setKey(PCTRetPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updatePCTItemLastUsedate(PCTRetPlan.STORE_NAME, itemH, addKey);
        }

        // 商品マスタ削除
        PCTItemSearchKey itemDelKey = new PCTItemSearchKey();
        itemDelKey.setManagementType(PCTItem.MANAGEMENT_TYPE_USER);
        itemDelKey.setLastUsedDate(_masterHoldLimitDay, COMPARE_LT);

        dropRecords(itemDelKey);
    }

    /**
     * PCT出荷先マスタ削除処理
     *
     * @param conn データベースとのコネクションオブジェクト
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void delPCTCustomerMaster(Connection conn)
            throws CommonException
    {
        // 出荷先マスタ
        PCTCustomerHandler customerHand = new PCTCustomerHandler(conn);
        PCTCustomerSearchKey addKey = new PCTCustomerSearchKey();

        WarenaviSystemController wsysCtlr = _wsysCtlr;

        if (wsysCtlr.hasPCTRetrievalPack())
        {
            // PCT出庫予定情報に存在するPCT出荷先マスタ情報を更新
            addKey.clear();
            addKey.setKey(PCTRetPlan.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);
            updatePCTCustLastUse(PCTRetPlan.STORE_NAME, customerHand, addKey);
        }

        // 古い出荷先マスタを削除
        PCTCustomerSearchKey custDelKey = new PCTCustomerSearchKey();
        custDelKey.setLastUsedDate(_masterHoldLimitDay, COMPARE_LT);

        dropRecords(custDelKey);
    }

    /**
     * PCT商品マスタの最終使用日を更新します。<br>
     * 結合対象テーブルに存在するデータは使用中とみなします。
     *
     * @param joinTable 結合対象テーブル
     * @param h データベースハンドラ
     * @param addSkey 副問い合わせキー追加条件
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected void updatePCTItemLastUsedate(String joinTable, DatabaseHandler h, PCTItemSearchKey addSkey)
            throws CommonException
    {
        PCTItemAlterKey itemAkey = new PCTItemAlterKey();
        PCTItemSearchKey itemItSkey = new PCTItemSearchKey();
        PCTItemSearchKey itemCoSkey = new PCTItemSearchKey();
        PCTItemSearchKey itemLotkey = new PCTItemSearchKey();

        try
        {
            // パラメータの追加キーが存在した場合、更新キーを追加する
            if (addSkey != null)
            {
                itemItSkey.setKey(addSkey);
                itemCoSkey.setKey(addSkey);
                itemLotkey.setKey(addSkey);
            }

            // 荷主副問合せキー
            itemCoSkey.setConsignorCodeCollect("DISTINCT");
            itemCoSkey.setJoin(PCTItem.CONSIGNOR_CODE, new FieldName(joinTable, PCTItem.CONSIGNOR_CODE.getName()));
            itemCoSkey.setJoin(PCTItem.ITEM_CODE, new FieldName(joinTable, PCTItem.ITEM_CODE.getName()));
            itemCoSkey.setJoin(PCTItem.LOT_ENTERING_QTY, new FieldName(joinTable, PCTItem.LOT_ENTERING_QTY.getName()));

            // 商品副問合せキー
            itemItSkey.setItemCodeCollect("DISTINCT");
            itemItSkey.setJoin(PCTItem.CONSIGNOR_CODE, new FieldName(joinTable, PCTItem.CONSIGNOR_CODE.getName()));
            itemItSkey.setJoin(PCTItem.ITEM_CODE, new FieldName(joinTable, PCTItem.ITEM_CODE.getName()));
            itemItSkey.setJoin(PCTItem.LOT_ENTERING_QTY, new FieldName(joinTable, PCTItem.LOT_ENTERING_QTY.getName()));

            // ロット入数副問合せキー
            itemLotkey.setLotEnteringQtyCollect("DISTINCT");
            itemLotkey.setJoin(PCTItem.CONSIGNOR_CODE, new FieldName(joinTable, PCTItem.CONSIGNOR_CODE.getName()));
            itemLotkey.setJoin(PCTItem.ITEM_CODE, new FieldName(joinTable, PCTItem.ITEM_CODE.getName()));
            itemLotkey.setJoin(PCTItem.LOT_ENTERING_QTY, new FieldName(joinTable, PCTItem.LOT_ENTERING_QTY.getName()));

            itemAkey.setKey(PCTItem.CONSIGNOR_CODE, itemCoSkey);
            itemAkey.setKey(PCTItem.ITEM_CODE, itemItSkey);
            itemAkey.setKey(PCTItem.LOT_ENTERING_QTY, itemLotkey);
            itemAkey.updateLastUsedDate(new SysDate());
            itemAkey.updateLastUpdatePname(PROC_NAME);

            h.modify(itemAkey);
        }
        // データが存在しない場合
        catch (NotFoundException e)
        {
            // データが存在しなくても問題は無いため、処理続行
        }
    }

    /**
     * PCT出庫実績送信情報から、PCT出庫実績情報へのデータ移動を行います。
     *
     * @param hostKey PCT出庫実績送信情報検索キー
     * @return 削除対象となった出庫実績送信情報
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected PCTRetHostSend[] movePCTRetHostSendToResult(SQLSearchKey hostKey)
            throws CommonException
    {
        // PCT出庫実績送信情報を読み込み
        PCTRetHostSendFinder hostSendFinder = new PCTRetHostSendFinder(getConnection());
        PCTRetHostSend[] hostSends = null;

        try
        {
            hostSendFinder.open(true);

            if (hostSendFinder.search(hostKey) == 0)
            {
                return new PCTRetHostSend[0];
            }

            while (hostSendFinder.hasNext())
            {
                Entity[] plans = hostSendFinder.getEntities(100);

                // 入出庫実績情報へデータコピー
                hostSends = (PCTRetHostSend[])plans;
                PCTRetResult[] results = entReplacePCT(hostSends);
                PCTRetResultHandler resultHandler = new PCTRetResultHandler(getConnection());

                for (int i = 0; i < results.length; i++)
                {
                    try
                    {
                        PCTRetResult result = results[i];
                        resultHandler.create(result);
                    }
                    catch (DataExistsException e)
                    {
                        // (6027006)データの不整合が発生しました。ログを参照してください。TABLE={0}
                        setMessage(WmsMessageFormatter.format(6027006, PCTRetResult.STORE_NAME));

                        throw e;
                    }
                }
            }
            dropRecords(hostKey);
            return hostSends;
        }
        finally
        {
            closeFinder(hostSendFinder);
        }
    }

    /**
     * PCT出荷先マスタの最終使用日を更新します。<br>
     * 結合対象テーブルに存在するデータは使用中とみなします。
     *
     * @param joinTable 結合対象テーブル
     * @param h データベースハンドラ
     * @param addSkey 副問い合わせキー追加条件
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected void updatePCTCustLastUse(String joinTable, DatabaseHandler h, PCTCustomerSearchKey addSkey)
            throws CommonException
    {
        PCTCustomerAlterKey custAkey = new PCTCustomerAlterKey();
        PCTCustomerSearchKey custSkey = new PCTCustomerSearchKey();
        PCTCustomerSearchKey custCkey = new PCTCustomerSearchKey();

        try
        {
            // パラメータの追加キーが存在した場合、更新キーを追加する
            if (addSkey != null)
            {
                custSkey.setKey(addSkey);
                custCkey.setKey(addSkey);
            }

            // 荷主副問合せキー
            custCkey.setConsignorCodeCollect("DISTINCT");
            custCkey.setJoin(PCTCustomer.CONSIGNOR_CODE, new FieldName(joinTable, PCTCustomer.CONSIGNOR_CODE.getName()));
            custCkey.setJoin(PCTCustomer.CUSTOMER_CODE, new FieldName(joinTable, PCTCustomer.CUSTOMER_CODE.getName()));

            // 出荷先副問合せキー
            custSkey.setCustomerCodeCollect("DISTINCT");
            custSkey.setJoin(PCTCustomer.CONSIGNOR_CODE, new FieldName(joinTable, PCTCustomer.CONSIGNOR_CODE.getName()));
            custSkey.setJoin(PCTCustomer.CUSTOMER_CODE, new FieldName(joinTable, PCTCustomer.CUSTOMER_CODE.getName()));

            custAkey.setKey(PCTCustomer.CONSIGNOR_CODE, custCkey);
            custAkey.setKey(PCTCustomer.CUSTOMER_CODE, custSkey);
            custAkey.updateLastUsedDate(new SysDate());
            custAkey.updateLastUpdatePname(PROC_NAME);

            h.modify(custAkey);
        }
        // データが存在しない場合
        catch (NotFoundException e)
        {
            // データが存在しなくても問題ないため、処理続行
        }
    }

    /**
     * @param conn データベースとのコネクションオブジェクト
     * @param pickingEntity ピッキング実績集計情報
     * @param wkEntity ピッキング実績情報の作業用領域
     * @throws ReadWriteException データベースアクセスエラー
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    private void createPickingResult(Connection conn, PCTPickingResult pickingEntity, PCTPickingResult wkEntity)
            throws ReadWriteException,
                DataExistsException
    {
        PCTUserResultHandler userResultHandler = new PCTUserResultHandler(conn);
        PCTUserResultSearchKey userResultKey = new PCTUserResultSearchKey();
        PCTOrderInfo[] tmpEntity;

        PCTOrderInfoHandler tmpHandler = new PCTOrderInfoHandler(conn);
        PCTOrderInfoSearchKey tmpKey = new PCTOrderInfoSearchKey();

        PCTPickingResultHandler pickingHandler = new PCTPickingResultHandler(conn);

        // 先に記憶している実績集計データを登録
        pickingEntity.setEndTime(wkEntity.getEndTime()); // 作業終了日時
        pickingEntity.setOperateTime((int)(wkEntity.getEndTime().getTime() - wkEntity.getStartTime().getTime()) / 1000); // 稼働時間
        pickingEntity.setWorkQty(wkEntity.getWorkQty()); // 作業数量
        pickingEntity.setPieceQty(wkEntity.getPieceQty()); // 作業数量（バラ）
        pickingEntity.setWorkCnt(wkEntity.getWorkCnt()); // 作業回数（明細数）
        //        pickingEntity.setOrderCnt(wkEntity.getOrderCnt()); // 作業回数（作業オーダー数）
        pickingEntity.setBoxCnt(wkEntity.getBoxCnt()); // 集品箱数
        pickingEntity.setWorkTime(wkEntity.getWorkTime()); // 作業時間
        pickingEntity.setRealWorkTime(wkEntity.getRealWorkTime()); // 実作業時間
        pickingEntity.setMissScanCnt(wkEntity.getMissScanCnt()); // ミススキャン数
        // 読込んだユーザ情報データのユーザIDと作業開始日時、作業終了日時を
        // バッテリ交換時間取得の為PCTUserResultSearchKeyにセット
        userResultKey.clear();
        userResultKey.setResultType(PCTRetrievalInParameter.RESULT_TYPE_BATTERY);
        userResultKey.setWorkStarttime(wkEntity.getStartTime(), ">=", "(", "", true);
        userResultKey.setWorkStarttime(wkEntity.getEndTime(), "<=", "", ")", true);
        userResultKey.setUserId(pickingEntity.getUserId());
        userResultKey.setTerminalNo(pickingEntity.getTerminalNo());
        userResultKey.setWorkTimeCollect("SUM");
        PCTUserResult[] usrresultEntity_Battery = (PCTUserResult[])userResultHandler.find(userResultKey);

        if (usrresultEntity_Battery.length > 0)
        {
            pickingEntity.setBatteryChangeTime(usrresultEntity_Battery[0].getWorkTime()); // バッテリー交換時間
        }
        else
        {
            pickingEntity.setBatteryChangeTime(0); // バッテリー交換時間
        }
        // 読込んだユーザ情報データのユーザIDと作業開始日時、作業終了日時を
        // 休憩時間取得の為PCTUserResultSearchKeyにセット
        userResultKey.clear();
        userResultKey.setResultType(PCTRetrievalInParameter.RESULT_TYPE_BREAK);
        userResultKey.setWorkStarttime(wkEntity.getStartTime(), ">=", "(", "", true);
        userResultKey.setWorkStarttime(wkEntity.getEndTime(), "<=", "", ")", true);
        userResultKey.setUserId(pickingEntity.getUserId());
        userResultKey.setTerminalNo(pickingEntity.getTerminalNo());
        userResultKey.setWorkTimeCollect("SUM");
        PCTUserResult[] usrresultEntity_Break = (PCTUserResult[])userResultHandler.find(userResultKey);
        if (usrresultEntity_Break.length > 0)
        {
            pickingEntity.setBreakTime(usrresultEntity_Break[0].getWorkTime()); // 休憩時間
        }
        else
        {
            pickingEntity.setBreakTime(0); // 休憩時間
        }

        // オーダー情報より荷主名称、エリア名称を取得
        tmpKey.clear();
        tmpKey.setConsignorNameCollect("MAX");
        tmpKey.setAreaNameCollect("MAX");
        tmpKey.setWorkStarttime(wkEntity.getStartTime(), ">=", "(", "", true);
        tmpKey.setWorkStarttime(wkEntity.getEndTime(), "<=", "", ")", true);
        tmpKey.setUserId(pickingEntity.getUserId());
        tmpKey.setTerminalNo(pickingEntity.getTerminalNo());
        tmpKey.setConsignorCode(pickingEntity.getConsignorCode());
        tmpKey.setAreaNo(pickingEntity.getAreaNo());
        tmpKey.setBatchNo(pickingEntity.getBatchNo());
        tmpKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);

        tmpEntity = (PCTOrderInfo[])tmpHandler.find(tmpKey);

        pickingEntity.setConsignorName(tmpEntity[0].getConsignorName());
        pickingEntity.setAreaName(tmpEntity[0].getAreaName());

        // オーダー情報よりオーダー件数を取得（作業オーダーの種類数）
        tmpKey.clear();
        tmpKey.setCollect(PCTOrderInfo.RESULT_ORDER_NO, "COUNT(DISTINCT {0})", PCTOrderInfo.RESULT_ORDER_NO);
        tmpKey.setWorkStarttime(wkEntity.getStartTime(), ">=", "(", "", true);
        tmpKey.setWorkStarttime(wkEntity.getEndTime(), "<=", "", ")", true);
        tmpKey.setUserId(pickingEntity.getUserId());
        tmpKey.setTerminalNo(pickingEntity.getTerminalNo());
        tmpKey.setConsignorCode(pickingEntity.getConsignorCode());
        tmpKey.setAreaNo(pickingEntity.getAreaNo());
        tmpKey.setBatchNo(pickingEntity.getBatchNo());
        tmpKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);

        tmpEntity = (PCTOrderInfo[])tmpHandler.find(tmpKey);

        pickingEntity.setOrderCnt(tmpEntity[0].getBigDecimal(PCTOrderInfo.RESULT_ORDER_NO).intValue());

        // オーダー情報よりオーダー件数を取得（予定オーダーの種類数）
        tmpKey.clearCollect();
        tmpKey.setCollect(PCTOrderInfo.RESULT_ORDER_NO, "COUNT(DISTINCT {0})", PCTOrderInfo.RESULT_ORDER_NO);

        // 副問い合わせ追加
        PCTOrderInfoSearchKey subKey = new PCTOrderInfoSearchKey();
        subKey.setPlanOrderNoCollect("DISTINCT");
        subKey.setWorkStarttime(wkEntity.getStartTime(), ">=", "(", "", true);
        subKey.setWorkStarttime(wkEntity.getEndTime(), "<=", "", ")", true);
        subKey.setUserId(pickingEntity.getUserId());
        subKey.setTerminalNo(pickingEntity.getTerminalNo());
        subKey.setConsignorCode(pickingEntity.getConsignorCode());
        subKey.setAreaNo(pickingEntity.getAreaNo());
        subKey.setBatchNo(pickingEntity.getBatchNo());
        subKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);

        tmpKey.setKey(PCTOrderInfo.RESULT_ORDER_NO, subKey);

        tmpEntity = (PCTOrderInfo[])tmpHandler.find(tmpKey);

        pickingEntity.setPlanOrderCnt(tmpEntity[0].getBigDecimal(PCTOrderInfo.RESULT_ORDER_NO).intValue());

        pickingEntity.setRegistPname(getClass().getSimpleName()); // 登録処理名
        pickingEntity.setLastUpdatePname(getClass().getSimpleName()); // 最終更新処理名

        pickingHandler.create(pickingEntity); // Insert処理
    }

    /**
     * @param conn データベースとのコネクションオブジェクト
     * @param pickingEntity ピッキング実績集計情報
     * @param userResult ユーザ実績情報
     * @param wkEntity ピッキング実績情報の作業用領域
     * @param orderEntity オーダー情報
     * @throws ReadWriteException データベースアクセスエラー
     */
    private void setFirstPickingEntity(Connection conn, PCTPickingResult pickingEntity, PCTUserResult userResult,
            PCTPickingResult wkEntity, PCTOrderInfo orderEntity)
            throws ReadWriteException
    {
        PCTOrderInfoHandler tmpHandler = new PCTOrderInfoHandler(conn);
        PCTOrderInfoSearchKey tmpKey = new PCTOrderInfoSearchKey();
        PCTOrderInfo[] tmpEntity;

        // Insert用領域にデータをセット
        pickingEntity.clear();
        pickingEntity.setUserId(userResult.getUserId()); // ユーザID
        pickingEntity.setUserName(userResult.getUserName()); // ユーザ名称
        pickingEntity.setTerminalNo(userResult.getTerminalNo()); // 端末ID
        pickingEntity.setWorkDay(userResult.getWorkDate()); // 作業日
        pickingEntity.setDayOfWeek(userResult.getDayOfWeek()); // 曜日
        pickingEntity.setConsignorCode(orderEntity.getConsignorCode()); // 荷主コード
        pickingEntity.setAreaNo(orderEntity.getAreaNo()); // エリアNo
        pickingEntity.setBatchNo(orderEntity.getBatchNo()); // バッチNo
        pickingEntity.setStartTime(wkEntity.getStartTime()); // 作業開始日時
        pickingEntity.setEndTime(orderEntity.getWorkEndtime()); // 作業終了日時
        // 作業用領域にデータセット
        wkEntity.setWorkQty(orderEntity.getWorkQty()); // 作業数量
        wkEntity.setPieceQty(orderEntity.getPieceQty()); // 作業数量（バラ）
        wkEntity.setWorkCnt(orderEntity.getWorkCnt()); // 作業回数（明細数）
        wkEntity.setBoxCnt(1); // 集品箱数
        wkEntity.setRealWorkTime(orderEntity.getRealWorkTime()); // 実作業時間
        wkEntity.setMissScanCnt(orderEntity.getMissScanCnt()); // ミススキャン数
        wkEntity.setEndTime(orderEntity.getWorkEndtime());

        tmpKey.clear();
        // 読込んだユーザ情報データのユーザIDと作業開始日時、作業終了日時を
        // PCTOrderInfoSearchKeyにセット
        tmpKey.setWorkStarttimeCollect("MIN");
        tmpKey.setWorkEndtimeCollect("MAX");
        tmpKey.setUserId(userResult.getUserId());
        tmpKey.setTerminalNo(userResult.getTerminalNo());
        tmpKey.setConsignorCode(pickingEntity.getConsignorCode());
        tmpKey.setAreaNo(pickingEntity.getAreaNo());
        tmpKey.setBatchNo(pickingEntity.getBatchNo());
        tmpKey.setSettingUnitKey(orderEntity.getSettingUnitKey());
        tmpKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);

        tmpEntity = (PCTOrderInfo[])tmpHandler.find(tmpKey);

        int tmpWorkTime =
                (int)(tmpEntity[0].getWorkEndtime().getTime() - tmpEntity[0].getWorkStarttime().getTime()) / 1000; // 作業時間
        wkEntity.setWorkTime(tmpWorkTime);

        // PCTユーザランク情報ハンドラ生成
        PCTUserRankingHandler rankHandler = new PCTUserRankingHandler(conn);
        PCTUserRankingSearchKey rankKey = new PCTUserRankingSearchKey();

        // 条件セット
        rankKey.setUserId(userResult.getUserId());

        // ユーザランク取得
        PCTUserRanking[] userRank = (PCTUserRanking[])rankHandler.find(rankKey);

        // 取得内容セット
        if (!ArrayUtil.isEmpty(userRank))
        {
            pickingEntity.setRank(userRank[0].getRank());
        }
        else
        {
            pickingEntity.setRank(PCTUserRanking.RANK_NO_B);
        }
    }

    /**
     * PCTシステム情報を取得します。<BR>
     *
     * @return PCTシステム情報(ランクA基準値、ランクB基準値)
     * @throws CommonException
     *             全ての例外をスローします。
     */
    private PCTSystemOutParameter getPCTSystemData()
            throws CommonException
    {
        // PCTシステム情報より、データを取得する
        // PCTシステムの検索
        PCTSystemHandler sysHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey sysSkey = new PCTSystemSearchKey();
        PCTSystem pctSystemEnt = (PCTSystem)sysHandler.findPrimary(sysSkey);
        // 出力用パラメータを宣言
        PCTSystemOutParameter outParam = new PCTSystemOutParameter();

        if (pctSystemEnt != null)
        {
            // ランク設定フラグ
            outParam.setWorkRankSetType(pctSystemEnt.getRankSettingFlag());
            // ランクA基準値
            outParam.setRankA(pctSystemEnt.getARankStandardValue());
            // ランクB基準値
            outParam.setRankB(pctSystemEnt.getBRankStandardValue());

            return outParam;
        }

        throw new NotFoundException();
    }

    /**
     * パラメータを基に時間当りの数量を算出します。<BR>
     * 算出結果(ZZ,ZZ9.9) = 数量 / 時間<BR>
     * ※小数点第一位まで、第二位を四捨五入<BR>
     *
     * @param qty
     *            数量
     * @param time
     *            時間
     * @return 算出結果
     */
    private double getPerHour(double qty, double time)
    {
        if (qty == 0 || time == 0)
        {
            return 0;
        }
        else
        {
            BigDecimal bdQty = new BigDecimal(qty);
            BigDecimal bdRealTime = new BigDecimal(time);
            return bdQty.divide(bdRealTime, 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * パラメータを基に基準値からの比率を算出します。<BR>
     * 算出結果(ZZ,ZZ9.9) = 数量 * 基準値 / 100%<BR>
     * ※小数点第一位まで、第二位を四捨五入<BR>
     *
     * @param qty
     *            数量
     * @param baseRate
     *            基準値
     * @return 算出結果
     */
    private double getRate(double qty, double baseRate)
    {
        if (qty == 0 || baseRate == 0)
        {
            return 0;
        }
        else
        {
            BigDecimal bdQty = new BigDecimal(qty * baseRate);
            return bdQty.divide(new BigDecimal(100), 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 日次処理チェック時のエラー情報保持用のエンティティクラスです。<BR>
     */
    private static class Error
    {
        /**
         * エラーステータス
         */
        private String _errStatus;

        /**
         * 発生原因コード
         */
        private String _reason;

        /**
         * 発生位置
         */
        private String _point;

        /**
         * エラー内容を指定してインスタンスを生成します。
         *
         * @param errStatus エラーステータス
         * @param reason 発生原因コード
         * @param point 発生位置
         */
        Error(String errStatus, String reason, String point)
        {
            _errStatus = errStatus;
            _reason = reason;
            _point = point;
        }

        /**
         * errTypeを返します。
         *
         * @return errTypeを返します。
         */
        String getErrStatus()
        {
            return _errStatus;
        }

        /**
         * pointを返します。
         *
         * @return pointを返します。
         */
        String getPoint()
        {
            return _point;
        }

        /**
         * reasonを返します。
         *
         * @return reasonを返します。
         */
        String getReason()
        {
            return _reason;
        }

        /**
         * インスタンスが保持しているエラー内容をパラメータに変換します。
         *
         * @return エラー内容がセットされたパラメータ
         */
        Params toParam()
        {
            return toParam(getErrStatus(), getReason(), getPoint());
        }

        /**
         * エラーの内容をパラメータに変換します。
         *
         * @param errStatus エラーステータス
         * @param reason 発生原因コード
         * @param point 発生位置
         * @return エラー内容がセットされたパラメータ
         */
        static Params toParam(String errStatus, String reason, String point)
        {
            Params outParam = new Params();

            // 状態
            outParam.set(STATUS, errStatus);
            // NG理由 オンライン
            outParam.set(REASON, reason);
            // 発生箇所
            outParam.set(NG_HAPPENED_POINT, point);

            return outParam;
        }
    }

    /**
     * 日次処理チェック時のエラー情報保持用のリストです。<BR>
     * @param <E> 保持対象クラス
     */
    @SuppressWarnings({
            "unchecked",
            "serial"
    })
    private static class ErrorList<E>
            extends ArrayList
    {
        /**
         * @see java.util.ArrayList#add(int, java.lang.Object)
         * @param index int
         * @param element Object
         */
        public void add(int index, Object element)
        {
            if (null == element)
            {
                return;
            }
            super.add(index, element);
        }

        /**
         * @see java.util.ArrayList#add(java.lang.Object)
         * @param o Object
         * @return boolean
         */
        public boolean add(Object o)
        {
            if (null == o)
            {
                return false;
            }
            return super.add(o);
        }
    }

    //DFKLOOK 3.5 Start
    /**
     * 完了した入荷予定データに対する実績に未報告データが存在する時は<BR>
     * ErrorからParams配列を生成して返します。<BR>
     * レコードが残っていない場合は nullが返されます。<BR>
     * @param err エラー情報
     * @return レコードが残っているときはエラー情報
     * @throws ReadWriteException データベースアクセスエラー
     */
    private Params checkRecordRemainReceivingPlan(Error err)
            throws ReadWriteException
    {
        DefaultDDBHandler ddbHandler = null;
        StringBuffer countSql = null;

        // 予定データ検索SQL文作成
        countSql = new StringBuffer();
        countSql.append(" SELECT     COUNT(*) CNT ");
        countSql.append(" FROM       DNRECEIVINGPLAN ");
        countSql.append(" WHERE      dnreceivingplan.STATUS_FLAG = ");
        //状態フラグ（完了）
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_COMPLETION));
        countSql.append(" AND EXISTS ( SELECT * ");
        countSql.append("              FROM   DNRECEIVINGHOSTSEND ");
        countSql.append("              WHERE  dnreceivinghostsend.REPORT_FLAG = ");
        //報告区分(未報告）
        countSql.append(DBFormat.format(SystemDefine.REPORT_FLAG_NOT_REPORT));
        countSql.append("              AND    dnreceivinghostsend.PLAN_UKEY = dnreceivingplan.PLAN_UKEY ) ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(countSql));
            Entity[] countEntity = ddbHandler.getEntities(1, new ReceivingPlan());

            // 未報告予定数取得用のフィールド
            FieldName count = new FieldName(ReceivingPlan.STORE_NAME, "CNT");

            // データが存在している場合true、それ以外false
            boolean recRemain = (0 < countEntity[0].getBigDecimal(count).intValue());
            // エラー情報の取得
            Params param = (recRemain) ? err.toParam()
                                      : null;
            // 取得したエラー情報を返却
            return param;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * 完了した入庫予定データに対する実績に未報告データが存在する時は<BR>
     * ErrorからParams配列を生成して返します。<BR>
     * レコードが残っていない場合は nullが返されます。<BR>
     * @param err エラー情報
     * @return レコードが残っているときはエラー情報
     * @throws ReadWriteException データベースアクセスエラー
     */
    private Params checkRecordRemainStoragePlan(Error err)
            throws ReadWriteException
    {
        DefaultDDBHandler ddbHandler = null;
        StringBuffer countSql = null;

        // 予定データ検索SQL文作成
        countSql = new StringBuffer();
        countSql.append(" SELECT     COUNT(*) CNT ");
        countSql.append(" FROM       DNSTORAGEPLAN ");
        countSql.append(" WHERE      dnstorageplan.STATUS_FLAG = ");
        //状態フラグ（完了）
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_COMPLETION));
        countSql.append(" AND EXISTS ( SELECT * ");
        countSql.append("              FROM   DNHOSTSEND ");
        countSql.append("              WHERE  dnhostsend.REPORT_FLAG = ");
        //報告区分(未報告）
        countSql.append(DBFormat.format(SystemDefine.REPORT_FLAG_NOT_REPORT));
        countSql.append("              AND    dnhostsend.PLAN_UKEY = dnstorageplan.PLAN_UKEY ) ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(countSql));
            Entity[] countEntity = ddbHandler.getEntities(1, new StoragePlan());

            // 未報告予定数取得用のフィールド
            FieldName count = new FieldName(StoragePlan.STORE_NAME, "CNT");

            // データが存在している場合true、それ以外false
            boolean recRemain = (0 < countEntity[0].getBigDecimal(count).intValue());
            // エラー情報の取得
            Params param = (recRemain) ? err.toParam()
                                      : null;
            // 取得したエラー情報を返却
            return param;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * 完了した出庫予定データに対する実績に未報告データが存在する時は<BR>
     * ErrorからParams配列を生成して返します。<BR>
     * レコードが残っていない場合は nullが返されます。<BR>
     * @param err エラー情報
     * @return レコードが残っているときはエラー情報
     * @throws ReadWriteException データベースアクセスエラー
     */
    private Params checkRecordRemainRetrievalPlan(Error err)
            throws ReadWriteException
    {
        DefaultDDBHandler ddbHandler = null;
        StringBuffer countSql = null;

        // 予定データ検索SQL文作成
        countSql = new StringBuffer();
        countSql.append(" SELECT     COUNT(*) CNT ");
        countSql.append(" FROM       DNRETRIEVALPLAN ");
        countSql.append(" WHERE      dnretrievalplan.STATUS_FLAG = ");
        //状態フラグ（完了）
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_COMPLETION));
        countSql.append(" AND EXISTS ( SELECT * ");
        countSql.append("              FROM   DNHOSTSEND ");
        countSql.append("              WHERE  dnhostsend.REPORT_FLAG = ");
        //報告区分(未報告）
        countSql.append(DBFormat.format(SystemDefine.REPORT_FLAG_NOT_REPORT));
        countSql.append("              AND    dnhostsend.PLAN_UKEY = dnretrievalplan.PLAN_UKEY ) ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(countSql));
            Entity[] countEntity = ddbHandler.getEntities(1, new RetrievalPlan());

            // 未報告予定数取得用のフィールド
            FieldName count = new FieldName(RetrievalPlan.STORE_NAME, "CNT");

            // データが存在している場合true、それ以外false
            boolean recRemain = (0 < countEntity[0].getBigDecimal(count).intValue());
            // エラー情報の取得
            Params param = (recRemain) ? err.toParam()
                                      : null;
            // 取得したエラー情報を返却
            return param;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    //DFKLOOK 3.5 End

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
