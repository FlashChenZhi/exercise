// $Id: WmsParam.java 8083 2015-02-16 07:38:16Z okamura $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ResourceUtil;

/**
 * WareNaviシステムのパラメータをリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>WMSParam</code>となっています。
 * また、データベース接続のための<code>Connection</code>の取得が可能です。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8083 $, $Date: 2015-02-16 16:38:16 +0900 (月, 16 2 2015) $
 * @author  $Author: okamura $
 */
public final class WmsParam
{
    // Class fields --------------------------------------------------
    /**
     * デフォルトのリソース
     */
    public static final String WMS_DEFAULT_RESOURCE = "WMSParam";

    /**
     * 欠品時引当解除範囲:明細
     */
    public static final int SHORTAGE_RECOVERY_RANGE_LINE = 1;

    /**
     * 欠品時引当解除範囲:１設定全て
     */
    public static final int SHORTAGE_RECOVERY_RANGE_ALL = 2;

    /**
     * 進捗バーのカラー
     */
    public enum ColorType
    {
        /** 赤色 */
        RED,
        /** 緑色 */
        GREEN,
        /** 青色 */
        BLUE,
        /** 黄色 */
        YELLOW,
        /** 白色 */
        WHITE,
        /** 黒色 */
        BLACK
    }

    //*** [COMMON INFORMATION] ***//
    /**
     * 環境情報ファイルのパス
     */
    public static final String ENVIRONMENT = getParam("ENVIRONMENT");

    /**
     * プリンタ一覧
     */
    public static final ArrayList<String> PRINTER_NAME = getListParam("PRINTER_NAME");

    /**
     * ターミナルサーバー名
     */
    public static final ArrayList<String> TERMINALSERVER_NAME = getListParam("TERMINALSERVER_NAME");

    /**
     * log4j-config.xmlのファイルパス
     */
    public static final String LOG4J_FILE_PATH = getParam("LOG4J_FILE_PATH");

    //*** [DATABASE CONNECTION INFORMATION] ***//
    /**
     * ユーザ定義情報(テキストファイル)をRead、Writeする時の区切り文字
     */
    public static final String USERINFO_FIELD_SEPARATOR = getParam("USERINFO_FIELD_SEPARATOR");

    /**
     * DB再接続処理を実行するかどうかのフラグ
     */
    public static final String WMS_DB_CONNECT_CHECK = getParam("WMS_DB_CONNECT_CHECK");

    /**
     * 統計情報用データベースユーザー名
     */
    public static final String WMS_DB_USER = getParam("WMS_DB_USER");

    //*** [REPORT INFORMATION] ***//
    /**
     * データ取込エラー情報最大出力件数
     */
    public static final int MAX_NUMBER_OF_DATALOAD_ERROR_LIST = getIntParam("MAX_NUMBER_OF_DATALOAD_ERROR_LIST");

    /**
     * 帳票イメージファイル保持日数
     */
    public static final String PRINTHISTORY_KEEP_DAYS = getParam("PRINTHISTORY_KEEP_DAYS");

    /**
     * 棚卸作業リスト1ページに印字できる行数を指定する。この行数単位で改ページされる。
     */
    public static final int INVENTORYCHKLIST_MAX_ROW = getIntParam("INVENTORYCHKLIST_MAX_ROW");

    /**
     * 作業リスト情報保持日数
     */
    public static final int DNWORKLIST_KEEP_DAYS = getIntParam("DNWORKLIST_KEEP_DAYS");

    //*** [LOG FILES INFORMATION] ***//
    /**
     * RFT通信トレースON/OFF
     */
    public static final boolean RFT_TRACE_ON = getBoolParam("RFT_TRACE_ON");

    /**
     * RFT通信トレースファイル名称
     */
    public static final String RFT_TRACE_NAME = getParam("RFT_TRACE_NAME");

    /**
     * トレースサイズ最大バイト数
     */
    public static final int RFT_TRACE_MAX_SIZE = getIntParam("RFT_TRACE_MAX_SIZE");

    /**
     * 2005/09/09 岡村
     * 現在未使用ですが、今後実装の可能性があるため消さないでください
     *
     * RFT通信トレースポインタファイル名称
     */
    public static final String RFT_TRACE_POINTER_NAME = getParam("RFT_TRACE_POINTER_NAME");

    /**
     * TomcatLogファイルのパス
     */
    public static final String TOMCAT_LOGS_PATH = getParam("TOMCAT_LOGS_PATH");

    /**
     * TomcatRFTLogファイルのパス
     */
    public static final ArrayList<String> TOMCAT_RFT_LOGS_PATH = getListParam("TOMCAT_RFT_LOGS_PATH");

    /**
     * TomcatLogファイル保持日数
     */
    public static final String TOMCATLOGS_KEEP_DAYS = getParam("TOMCATLOGS_KEEP_DAYS");

    /**
     * LogBackUpファイル保持日数
     */
    public static final String WMS_LOGFILE_KEEP_DAYS = getParam("WMS_LOGFILE_KEEP_DAYS");

    /**
     * IIS(FTP)ログファイルのパス
     */
    public static final String IIS_LOGS_PATH = getParam("IIS_LOGS_PATH");

    /**
     * IIS(FTP)ログファイル保持日数
     */
    public static final String IIS_LOGFILE_KEEP_DAYS = getParam("IIS_LOGFILE_KEEP_DAYS");

    /**
     * FTP作業ファイルの履歴保存パス
     */
    public static final String FTP_FILE_HISTORY_PATH = getParam("FTP_FILE_HISTORY_PATH");

    /**
     * FTP作業ファイルの履歴保持日数
     */
    public static final String FTP_FILE_HISTORY_KEEP_DAYS = getParam("FTP_FILE_HISTORY_KEEP_DAYS");

    /**
     * その他のログファイルのパス
     */
    public static final String ETC_LOGS_PATH = getParam("ETC_LOGS_PATH");

    /**
     * その他のログファイル保持日数
     */
    public static final String ETC_LOGFILE_KEEP_DAYS = getParam("ETC_LOGFILE_KEEP_DAYS");

    /**
     * AS21TraceLogファイルの保持日数
     */
    public static final String AS21_TRACE_HISTORY_KEEP_DAYS = getParam("AS21_TRACE_HISTORY_KEEP_DAYS");

    //*** [DISPLAY INFORMATION] ***//
    /**
     * デフォルト荷主コード　画面で荷主コードを指定しない場合に使用。このコードは荷主マスタ情報に登録する必要があります。
     */
    public static final String DEFAULT_CONSIGNOR_CODE = getParam("DEFAULT_CONSIGNOR_CODE");

    /**
     * リストセル（ためうちエリア）最大表示件数
     */
    public static final int MAX_NUMBER_OF_DISP = getIntParam("MAX_NUMBER_OF_DISP");

    /**
     * MESSAGE_LOG 表示上限値
     */
    public static final int MAX_NUMBER_OF_DISP_MESSAGE_LOG = getIntParam("MAX_NUMBER_OF_DISP_MESSAGE_LOG");

    /**
     * グラフ数値の最大値
     */
    public static final int MAX_NUMBER_OF_DISP_GRAPH = getIntParam("MAX_NUMBER_OF_DISP_GRAPH");

    /**
     * 全エリア
     */
    public static final String ALL_AREA_NO = getParam("ALL_AREA_NO");

    /**
     * 全RFT
     */
    public static final String ALL_RFT_NO = getParam("ALL_RFT_NO");

    /**
     * Auto Line No.
     */
    public static final boolean AUTO_LINE_NO = getBoolParam("AUTO_LINE_NO");


    //*** [SQL INFORMATION] ***//
    /**
     *  デフォルトのレコードロックリトライ回数
     */
    public static final int LOCK_RETRY_COUNT = getIntParam("LOCK_RETRY_COUNT");

    /**
     *  デフォルトのロックリトライ待ち時間
     */
    public static final int LOCK_RETRY_SLEEP_SEC = getIntParam("LOCK_RETRY_SLEEP_SEC");

    /**
     * デッドロック発生時のリトライ回数
     */
    public static final int DEADLOCK_RETRY_MAX_COUNT = getIntParam("DEADLOCK_RETRY_MAX_COUNT");

    /**
     * デッドロック発生時のリトライ待ち時間
     */
    public static final int DEADLOCK_RETRY_SLEEP_TIME = getIntParam("DEADLOCK_RETRY_SLEEP_TIME");

    //*** [HOST INFORMATION] ***//
    /**
     * 外部データ取込みを実行してできるバックアップファイルのパス
     */
    public static final String HISTORY_HOSTDATA_PATH = getParam("HISTORY_HOSTDATA_PATH");

    /**
     * 一時保存用ディレクトリパス
     */
    public static final String HOSTDATA_LOCAL_PATH = getParam("HOSTDATA_LOCAL_PATH");

    /**
     * 外部データバックアップファイル保持日数
     */
    public static final String HOSTDATA_KEEP_DAYS = getParam("HOSTDATA_KEEP_DAYS");

    /**
     * 外部データ取込最大レコード数(入荷)
     */
    public static final int MAX_RECORD_RECEIVE = getIntParam("MAX_RECORD_RECEIVE");

    /**
     * 外部データ取込最大レコード数(入庫)
     */
    public static final int MAX_RECORD_STORAGE = getIntParam("MAX_RECORD_STORAGE");

    /**
     * 外部データ取込最大レコード数(出庫)
     */
    public static final int MAX_RECORD_RETRIEVAL = getIntParam("MAX_RECORD_RETRIEVAL");

    /**
     * 外部データ取込最大レコード数(TC予定)
     */
    public static final int MAX_RECORD_CROSSDOCK = getIntParam("MAX_RECORD_CROSSDOCK");

    /**
     * 外部データ取込最大レコード数(荷主マスタ)
     */
    public static final int MAX_RECORD_CONSIGNOER = getIntParam("MAX_RECORD_CONSIGNOER");

    /**
     * 外部データ取込最大レコード数(仕入先マスタ)
     */
    public static final int MAX_RECORD_SUPPLIER = getIntParam("MAX_RECORD_SUPPLIER");

    /**
     * 外部データ取込最大レコード数(出荷先マスタ)
     */
    public static final int MAX_RECORD_CUSTOMER = getIntParam("MAX_RECORD_CUSTOMER");

    /**
     * 外部データ取込最大レコード数(商品マスタ)
     */
    public static final int MAX_RECORD_ITEM = getIntParam("MAX_RECORD_ITEM");

    /**
     * 外部データ取込最大レコード数(商品固定棚)
     */
    public static final int MAX_RECORD_FIXEDLOCATEINFO = getIntParam("MAX_RECORD_FIXEDLOCATEINFO");

    //*** [RFT INFORMATION] ***//
    /**
     * DAIDATAのパス(RFT使用 C:\\daifuku\\wms\\terminal\\)
     */
    public static final String DAIDATA = getParam("DAIDATA");

    /**
     * RFT SENDデータのパス
     */
    public static final String RFTSEND = getParam("RFTSEND");

    /**
     * RFT RECVデータのパス
     */
    public static final String RFTRECV = getParam("RFTRECV");

    /**
     * RFTのユーザ認証設定(する:TRUE しない:FALSE)
     */
    public static final boolean RFT_AUTHENTICATION = getBoolParam("RFT_AUTHENTICATION");

    /**
     * RFTのユーザ認証で、作業可能なロール
     */
    public static final ArrayList<String> RFT_WORKABLE_ROLES = getListParam("RFT_WORKABLE_ROLES");

    /**
     * RFT rft-log4j-config.xmlファイルのパス
     */
    public static final String RFT_LO4J_FILE_PATH = getParam("RFT_LO4J_FILE_PATH");

    /**
     * RFT rft-datasource.xmlファイルのパス
     */
    public static final String RFT_DATASOURCE_FILE_PATH = getParam("RFT_DATASOURCE_FILE_PATH");

    /**
     * ソケット受信してスレッド生成後、次のソケット受信処理待ちまでのSLEEP時間。単位:ms
     */
    public static final int RFT_SLEEP_SEC = getIntParam("RFT_SLEEP_SEC");

    /**
     * RFTサーバーのポートNo.
     */
    public static final int RFT_SERVER_PORT = getIntParam("RFT_SERVER_PORT");

    /**
     * 生存確認(する:TRUE しない:FALSE)(しない場合、間隔は無視)
     */
    public static final boolean RFT_KEEP_ALIVE_POLLING_ENABLE = getBoolParam("RFT_KEEP_ALIVE_POLLING_ENABLE");

    /**
     *  生存確認間隔
     */
    public static final int RFT_KEEP_ALIVE_POLLING_TIME = getIntParam("RFT_KEEP_ALIVE_POLLING_TIME");

    /**
     * RTF最大集約数(FTPファイルの最大行数。多量にHitした時はこの件数でファイル作成される)
     */
    public static final int MAX_RFT_JOBCOLLECT = getIntParam("MAX_RFT_JOBCOLLECT");

    /**
     * RFT最大集約数(無制限)
     */
    public static final int MAX_RFT_JOBCOLLECT_UNLIMITED = -1;

    //*** [STOCK INFORMATION] ***//
    /**
     * 作業のバラ総数の最大値
     */
    public static final int MAX_TOTAL_QTY = getIntParam("MAX_TOTAL_QTY");

    /**
     * 作業者実績の作業数量の最大値
     */
    public static final int WORKER_MAX_TOTAL_QTY = getIntParam("WORKER_MAX_TOTAL_QTY");

    /**
     * 在庫数（バラ）の最大値
     */
    public static final int MAX_STOCK_QTY = getIntParam("MAX_STOCK_QTY");

    /**
     * レベルの上限(棚状態照会で使用)
     */
    public static final int LOCATION_STATUS_MAX_LEVEL = getIntParam("LOCATION_STATUS_MAX_LEVEL");

    /**
     * ベイの上限(棚状態照会で使用)
     */
    public static final int LOCATION_STATUS_MAX_BAY = getIntParam("LOCATION_STATUS_MAX_BAY");

    /**
     * 棚マスタ一括設定の上限(棚マスタ一括設定で使用)
     */
    public static final int LOCATION_MAX = getIntParam("LOCATION_MAX");

    /**
     * ベイの下限
     */
    public static final int LOCATION_STATUS_MIN_LEVEL = 1;

    /**
     * レベルの下限
     */
    public static final int LOCATION_STATUS_MIN_BAY = 1;

    //*** [AS21 Communication Information] ***//
    /**
     * AS21トレースファイル名
     */
    public static final String AS21_TRACE_NAME = getParam("AS21_TRACE_NAME");

    /**
     * AS21トレース Write on/off
     */
    public static final boolean AS21_TRACE_ON = getBoolParam("AS21_TRACE_ON");

    /**
     * トレースファイルのMaxサイズ(Byte)
     */
    public static final int AS21_TRACE_MAX_SIZE = getIntParam("AS21_TRACE_MAX_SIZE");

    /**
     * 各電文毎の対応(Id??)処理が異常となった場合の再試行までのSleep Time(ms)
     */
    public static final int CONTROL_SLEEP_SEC = getIntParam("CONTROL_SLEEP_SEC");

    /**
     * 搬送、出庫指示に於いて全てのStationにDataがない場合の再試行までのSleep Time
     */
    public static final int INSTRUCT_CONTROL_SLEEP_SEC = getIntParam("INSTRUCT_CONTROL_SLEEP_SEC");

    /**
     * 搬送、出庫指示に於いてデータはあるが送信可能な状態ではない場合の再試行までのSleep Time
     */
    public static final int INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST =
            getIntParam("INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST");

    /**
     * AS21通信処理で回線異常となった場合の再接続実施までのSleep Time
     */
    public static final int AS21_SLEEP_SEC = getIntParam("AS21_SLEEP_SEC");

    /**
     * AS21通信処理KeepAlive発生認識時間
     */
    public static final long AS21_KEEPALIVE_TIMER = Long.valueOf(getParam("AS21_KEEPALIVE_TIMER"));

    /**
     * AS21通信処理KeepAliveSACNタイマー値
     */
    public static final long AS21_KEEPALIVE_CHECK = Long.valueOf(getParam("AS21_KEEPALIVE_CHECK"));

    /**
     * AS21通信処理KeepAlive監視誤差値
     */
    public static final long AS21_KEEPALIVE_MARGIN = Long.valueOf(getParam("AS21_KEEPALIVE_MARGIN"));

    /**
     * AS21通信処理受信KeepAlive発生後、再接続の可否
     */
    public static final boolean AS21_KEEPALIVE_RECONNECT = getBoolParam("AS21_KEEPALIVE_RECONNECT");


    //*** [AS/RS INFORMATION] ***//
    /**
     * ASRSサーバ負荷分散フラグ
     */
    public static final boolean MULTI_ASRSSERVER = getBoolParam("MULTI_ASRSSERVER");

    /**
     * Routeファイル(route.txt)のパス
     */
    public static final String ROUTE_FILE = getParam("ROUTE_FILE");

    /**
     * RouteSpetialファイル(routeSpetial.txt)のパス
     */
    public static final String RESERVED_ROUTE_FILE = getParam("RESERVED_ROUTE_FILE");

    /**
     * ProhibitedRouteファイル(prohibitedRoute.txt)のパス
     */
    public static final String PROHIBITED_ROUTE_FILE = getParam("PROHIBITED_ROUTE_FILE");

    /**
     * 画面から全RMを選んだ場合
     */
    public static final String ALL_AISLE_NO = getParam("ALL_AISLE_NO");

    /**
     * 空パレット荷主コード
     */
    public static final String EMPTYPB_CONSIGNORCODE = getParam("EMPTYPB_CONSIGNORCODE");

    /**
     * 空パレット商品コード
     */
    public static final String EMPTYPB_ITEMCODE = getParam("EMPTYPB_ITEMCODE");

    /**
     * 異常棚荷主コード
     */
    public static final String IRREGULAR_CONSIGNORCODE = getParam("IRREGULAR_CONSIGNORCODE");

    /**
     * 異常棚商品コード
     */
    public static final String IRREGULAR_ITEMCODE = getParam("IRREGULAR_ITEMCODE");

    /**
     * 簡易直行荷主コード
     */
    public static final String SIMPLEDIRECTTRANSFER_CONSIGNORCODE = getParam("SIMPLEDIRECTTRANSFER_CONSIGNORCODE");

    /**
     * 簡易直行商品コード
     */
    public static final String SIMPLEDIRECTTRANSFER_ITEMCODE = getParam("SIMPLEDIRECTTRANSFER_ITEMCODE");

    /**
     * 画面から全ステーションを選んだ場合
     */
    public static final String ALL_STATION = getParam("ALL_STATION");

    /**
     * 画面から自動振分けを選んだ場合
     */
    public static final String AUTO_SELECT_STATION = getParam("AUTO_SELECT_STATION");

    /**
     * 登録作業場なし
     */
    public static final String NOPARENT_STATION_WPNO = getParam("NOPARENT_STATION_WPNO");

    /**
     * Dummy CarryKey
     */
    public static final String DUMMY_MCKEY = getParam("DUMMY_MCKEY");

    /**
     *  空出荷時の在庫削除運用 [ TRUE:削除する, FALSE:削除しない ]
     */
    public static final boolean EMPTY_RETRIEVAL_DELETE_STOCK = getBoolParam("EMPTY_RETRIEVAL_DELETE_STOCK");

    /**
     * AS/RS作業メンテナンス実施時にAS/RS作業メンテナンスリストの自動発行を制御 [ TRUE:発行する, FALSE:発行しない ]
     */
    public static final boolean CARRY_MNT_AUTO_REPORT = getBoolParam("CARRY_MNT_AUTO_REPORT");

    /**
     * 新規入庫時に、画面から優先区分の指定がなかった場合にセットする優先区分[ TRUE:通常 FALSE: 緊急]
     */
    public static final boolean STORAGE_PRIORITY_NORMAL = getBoolParam("STORAGE_PRIORITY_NORMAL");

    /**
     * 画面のソフトゾーンプルダウン表示について、商品コードをキーに表示させるか、該当ソフトゾーンを全て表示させるかの区分
     * [ TRUE:商品に対応するソフトゾーン FALSE: 全ソフトゾーン]
     */
    public static final boolean SOFTZONE_SELECT_ITEM = getBoolParam("SOFTZONE_SELECT_ITEM");

    /**
     * ソフトゾーン違いの商品を混載できるかどうかの区分
     * [ TRUE:混載できる FALSE:混載できない]
     */
    public static final boolean MIXED_SOFTZONE = getBoolParam("MIXED_SOFTZONE");

    /**
     * パレット種別ID
     * eWareNaviではPalletType表がないため、固定で0をセットします
     */
    public static final String PALLET_TYPE_ID = "0";

    /**
     * ステーショングループ一覧
     * [ ステーションのグループを設定]
     */
    public static final ArrayList<String> STATION_GROUP_LIST = getListParam("STATION_GROUP_LIST");

    /**
     * 在庫更新のタイミングを選択
     * [TRUE:作業完了時 FALSE:搬送指示応答時]
     */
    public static final boolean STOCK_MODIFY_TIMING = getBoolParam("STOCK_MODIFY_TIMING");

    /**
     * AS/RS作業開始設定にて日次更新チェックを行うかどうか
     * [TRUE:チェックする FALSE:チェックしない]
     */
    public static final boolean WORKSTART_DAILYCHECK = getBoolParam("WORKSTART_DAILYCHECK");

    /**
     * 到着情報保持日数
     * eWareNaviではPalletType表がないため、固定で0をセットします
     */
    public static final String ARRIVAL_KEEP_DAYS = getParam("ARRIVAL_KEEP_DAYS");

    /**
     * 再入庫データ保持日数
     */
    public static final String RESTORING_KEEP_DAYS = getParam("RESTORING_KEEP_DAYS");

    // 2009/09/26 Y.Osawa ADD ST
    /**
     * 代替棚検索時、開始ベイを空棚検索対象としない倉庫一覧（ツインフォークへの対応）
     * 二重格納や荷姿不一致など、代替棚検索時に開始ベイを検索しない倉庫の一覧
     */
    public static final ArrayList<String> WAREHOUSE_ALTER_LOCATION_EXCEPT_START_BAY =
            getListParam("WAREHOUSE_ALTER_LOCATION_EXCEPT_START_BAY");

    /**
     * 代替棚検索時、終了ベイを空棚検索対象としない倉庫一覧（ツインフォークへの対応）
     * 二重格納や荷姿不一致など、代替棚検索時に終了ベイを検索しない倉庫の一覧
     */
    public static final ArrayList<String> WAREHOUSE_ALTER_LOCATION_EXCEPT_END_BAY =
            getListParam("WAREHOUSE_ALTER_LOCATION_EXCEPT_END_BAY");
    // 2009/09/26 Y.Osawa ADD ED

    /**
     * 同一ステーションのダミー到着情報を上書きするか、バッファリングするかの区分
     * [TRUE:上書き FALSE:バッファリング]
     */
    public static final boolean DUMMY_ARRIVED_BUFFERING_FLAG = getBoolParam("DUMMY_ARRIVED_BUFFERING_FLAG");

    // 2015/02/16 Y.Okamura start TimeKeeper用のLOG4j定義追加
    //*** [TIMEKEEPER INFORMATION] ***//
    /**
     * TimeKeeperプロセスで使用するtk-log4j-config.xmlのファイルパス
     */
    public static final String TK_LOG4J_FILE_PATH = getParam("TK_LOG4J_FILE_PATH");
    // 2015/02/16 Y.Okamura end TimeKeeper用のLOG4j定義追加

    //*** [ALLOCATE INFORMATION] ***//
    /**
     * デフォルトの仮在庫ロケーション
     */
    public static final String DEFAULT_LOCATION_NO = getParam("DEFAULT_LOCATION_NO");

    /**
     *  引当時、在庫情報のロックに失敗した際の再試行回数
     */
    public static final int ALLOCATE_RETRY_COUNT = getIntParam("ALLOCATE_RETRY_COUNT");

    /**
     *  引当時、在庫情報のロックに失敗した際の待機時間（秒）
     */
    public static final int ALLOCATE_SLEEP_TIME = getIntParam("ALLOCATE_SLEEP_TIME");

    /**
     *  補充引当時の、１件の設定単位キーに含まれる作業数の最大件数
     */
    public static final int REPLENISH_MAX_PER_AREA = getIntParam("REPLENISH_MAX_PER_AREA");

    /**
     *  出庫引当順がロットNo.昇順かロットNo.無視かを判断
     */
    public static final int RETRIEVAL_ALLOCATE_PRIORITY = getIntParam("RETRIEVAL_ALLOCATE_PRIORITY");

    /**
     *  商品コード指定出庫の引当順を指定します。
     */
    public static final int ITEM_RETRIEVAL_ALLOCATE_PRIORITY = getIntParam("ITEM_RETRIEVAL_ALLOCATE_PRIORITY");

    /**
     *  マルチ引当フラグ
     */
    public static final boolean MULTI_ALLOCATE_FLAG = getBoolParam("MULTI_ALLOCATE_FLAG");


    //*** [ANALYSIS INFORMATION] ***//
    /**
     * 分析系設定ファイルパス
     */
    public static final String ANALYSIS_INI_PATH = getParam("ANALYSIS_INI_PATH");

    /**
     * 履歴情報保存期間(月)
     */
    public static final int KEEP_MONTHS = getIntParam("KEEP_MONTHS");

    /**
     * デフォルト作業単位数
     */
    public static final int WORK_UNIT_TYPE = getIntParam("WORK_UNIT_TYPE");

    /**
     * 休憩回数上限
     */
    public static final int BREAKTIME_MAX = getIntParam("BREAKTIME_MAX");

    //*** [SHORTAGE INFORMATION] ***//
    /**
     * 欠品情報の保持日数
     */
    public static final int SHORTAGE_INFO_KEEP_DAYS = getIntParam("SHORTAGE_INFO_KEEP_DAYS");

    //*** [SYSTEM INFORMATION] ***//
    /**
     * 営業日最大件数
     */
    public static final int OPERATION_DAY_MAX = getIntParam("OPERATION_DAY_MAX");

    //*** [MASTER INFORMATION] ***//
    /**
     * 未使用マスタデータ保持日数
     */
    public static final int MASTER_INFO_KEEP_DAYS = getIntParam("MASTER_INFO_KEEP_DAYS");

    /**
     * マスタ変更フラグ
     */
    public static final boolean MASTER_MODIFY_FLAG = getBoolParam("MASTER_MODIFY_FLAG");

    //*** [PART11 INFORMATION] ***//
    /**
     * システムユーザID
     */
    public static final String SYS_USER_ID = getParam("SYS_USER_ID");

    /**
     * システムユーザ名
     */
    public static final String SYS_USER_NAME = getParam("SYS_USER_NAME");

    /**
     * システム端末No
     */
    public static final String SYS_TERMINAL_NO = getParam("SYS_TERMINAL_NO");

    /**
     * システム端末名
     */
    public static final String SYS_TERMINAL_NAME = getParam("SYS_TERMINAL_NAME");

    /**
     * システムIPアドレス
     */
    public static final String SYS_IP_ADDRESS = getParam("SYS_IP_ADDRESS");

    /**
     * システムDS番号
     */
    public static final String SYS_DS_NUMBER = getParam("SYS_DS_NUMBER");

    /**
     * システムページリソースキー
     */
    public static final String SYS_PAGE_RESOUCE_KEY = getParam("SYS_PAGE_RESOUCE_KEY");

    //*** [TC INFORMATION] ***//
    /**
     * 出荷先一意チェックの要不要(一意チェックする:TRUE しない:FALSE)
     */
    public static final boolean IS_UNIQUE_CHECK_CUSTOMER = getBoolParam("IS_UNIQUE_CHECK_CUSTOMER");

    /**
     * 仕入れ先一意チェックの要不要(一意チェックする:TRUE しない:FALSE)
     */
    public static final boolean IS_UNIQUE_CHECK_SUPPLIER = getBoolParam("IS_UNIQUE_CHECK_SUPPLIER");

    //*** [PCART INFORMATION] ***//
    //*** PCARTシステムで使用する項目定義 ***//

    /**
     * 外部データ取込最大レコード数(PCT出庫)
     */
    public static final int MAX_RECORD_PCTRET = getIntParam("MAX_RECORD_PCTRET");

    /**
     * 外部データ取込最大レコード数(PCT商品マスタ)
     */
    public static final int MAX_RECORD_PCTITEM = getIntParam("MAX_RECORD_PCTITEM");

    /**
     * ピッキング実績集計情報保存期間(日)
     */
    public static final int PCT_RESULT_KEEP_DAYS = getIntParam("PCT_RESULT_KEEP_DAYS");

    /**
     * 全ユーザ実績保持日数情報保存期間(日)
     */
    public static final int PCT_ALL_USER_RESULT_KEEP_DAYS = getIntParam("PCT_ALL_USER_RESULT_KEEP_DAYS");

    /**
     * PCT操作ログ保持日数
     */
    public static final int PCT_OPERATION_LOG_KEEP_DAYS = getIntParam("PCT_OPERATION_LOG_KEEP_DAYS");

    /**
     * デフォルト棚フォーマット（エリアマスタ情報自動登録用）
     */
    public static final String DEFAULT_LOCATE_STYLE = getParam("DEFAULT_LOCATE_STYLE");

    /**
     * 全バッチSeqNo
     */
    public static final String ALL_BATCH_SEQ_NO = getParam("ALL_BATCH_SEQ_NO");

    /**
     * LOADデータサーバーエラーディレクトリ
     */
    public static final String DATE_LOAD_ERR_PATH = getParam("DATE_LOAD_ERR_PATH");

    /**
     * SAVE/LOADデータディレクトリ
     */
    public static final String DMPDATA_FILE_PATH = getParam("DMPDATA_FILE_PATH");

    /**
     * SAVE/LOADデータファイル名
     */
    public static final String DMPDATA_FILE_NAME = getParam("DMPDATA_FILE_NAME");

    /**
     * SAVE/LOADデータファイルの世代
     */
    public static final int FILE_GENERATION = getIntParam("FILE_GENERATION");

    /**
     * 基準値の最大値
     */
    public static final double MAX_STANDARD_VALUE = getDoubleParam("MAX_STANDARD_VALUE");

    /**
     *  進捗
     */
    public static final Color PROGRESS_COLOR_TYPE = getColorParam("PROGRESS_COLOR_TYPE");

    //*** [EXTENDED PACKAGE INFORMATION] ***//
    /**
     * 製番開発用です。必要に応じて使用してください
     */
    public static final String WMS_EXTENDED_PACKAGE = getParam("WMS_EXTENDED_PACKAGE");

    /**
     * eWareNaviメッセージリソース
     */
    public static final String RESOURCE_WMS = getParam("RESOURCE_WMS");

    /**
     * WCSメッセージリソース
     */
    public static final String RESOURCE_WCS = getParam("RESOURCE_WCS");

    //*******************//
    //*** CommonParam ***//
    //*******************//
    //*** [LOG FILES INFORMATION] ***//
    /**
     * ログファイル フォルダ(CommonParam)
     */
    public static final String LOGS_PATH = CommonParam.getParam("LOGS_PATH");

    /**
     * メッセージログ、トレースログなどのLogファイルのディレクトリ(CommonParam)
     */
    public static final String WMS_LOGS_PATH = CommonParam.getParam("LOGS_PATH");

    /**
     * メッセージログ ファイル名称(CommonParam)
     */
    public static final String MESSAGELOG_FILE = CommonParam.getParam("MESSAGELOG_FILE");

    //*** [LOCATION INFORMATION] ***//
    /**
     * SHELFのStationNumberで保持する倉庫Noの長さ(CommonParam)
     */
    public static final int WAREHOUSE_LENGTH = Integer.parseInt(CommonParam.getParam("WAREHOUSE_LENGTH"));

    /**
     * SHELFのStationNumberで保持するバンクの長さ(CommonParam)
     */
    public static final int ASRS_BANK_LENGTH = Integer.parseInt(CommonParam.getParam("ASRS_BANK_LENGTH"));

    /**
     * SHELFのStationNumberで保持するベイの長さ(CommonParam)
     */
    public static final int ASRS_BAY_LENGTH = Integer.parseInt(CommonParam.getParam("ASRS_BAY_LENGTH"));

    /**
     * SHELFのStationNumberで保持するレベルの長さ(CommonParam)
     */
    public static final int ASRS_LEVEL_LENGTH = Integer.parseInt(CommonParam.getParam("ASRS_LEVEL_LENGTH"));

    /**
     * SHELFのStationNumberで保持するサブロケーションの長さ(CommonParam)
     */
    public static final int ASRS_SUBLOC_LENGTH = Integer.parseInt(CommonParam.getParam("ASRS_SUBLOC_LENGTH"));

    /**
     * DNSTOCKのLocationNoで保持するバンクの長さ(CommonParam)
     */
    public static final int BANK_LENGTH = Integer.parseInt(CommonParam.getParam("BANK_LENGTH"));

    /**
     * DNSTOCKのLocationNoで保持するベイの長さ(CommonParam)
     */
    public static final int BAY_LENGTH = Integer.parseInt(CommonParam.getParam("BAY_LENGTH"));

    /**
     * DNSTOCKのLocationNoで保持するレベルの長さ(CommonParam)
     */
    public static final int LEVEL_LENGTH = Integer.parseInt(CommonParam.getParam("LEVEL_LENGTH"));

    /**
     * DNSTOCKのLocationNoで保持するサブロケーションの長さ(CommonParam)
     */
    public static final int SUBLOC_LENGTH = Integer.parseInt(CommonParam.getParam("SUBLOC_LENGTH"));

    //*** [DISPLAY INFORMATION] ***//
    /**
     * 禁止文字(CommonParam)
     */
    public static final String NG_PARAMETER_TEXT = CommonParam.getParam("NG_PARAMETER_TEXT");

    /**
     * 検索用ワイルドカード文字(CommonParam)
     */
    public static final String PATTERNMATCHING = CommonParam.getParam("PATTERNMATCHING_CHAR");

    //*** [BUSITUNE INFORMATION] ***//
    /**
     * 印刷データの作成先ディレクトリ(CommonParam)
     */
    public static final String DATA_FILE_PATH = CommonParam.getParam("PR_FILE_DIR");

    /**
     * 印刷用のデータのバックアップ先ディレクトリ(CommonParam)
     */
    public static final String BACKUP_DATA_FILE_PATH = CommonParam.getParam("PR_BACKUP_FILE_DIR");

    /**
     * プレビュー用のデータの作成先ディレクトリ(CommonParam)
     */
    public static final String PREVIEW_DATA_FILE_PATH = CommonParam.getParam("PR_PREVIEW_FILE_DIR");

    /**
     * プレビューPDFファイルのURI(CommonParam)
     */
    public static final String PREVIEW_DATA_FILE_URI = CommonParam.getParam("PR_PREVIEW_FILE_URI");

    /**
     * TODO 必要か処理検討必要
     * CSV/XLSファイル最大出力件数(CommonParam)
     */
    public static final int MAX_NUMBER_OF_DISP_FILE = CommonParam.getIntParam("MAX_NUMBER_OF_XLS");

    /**
     * LISTBOX検索最大件数(CommonParam)
     */
    public static final int MAX_NUMBER_OF_DISP_LISTBOX = CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX");


    // Class private fields ------------------------------------------
    /**
     * JDBC prefix
     */
    private static final String THIN_DRIVER = "jdbc:oracle:thin:@";

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 8083 $,$Date: 2015-02-16 16:38:16 +0900 (月, 16 2 2015) $");
    }

    // Constructors --------------------------------------------------
    /**
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private WmsParam()
    {
        // do not use instance of this class.
    }

    // Public methods ------------------------------------------------
    /**
     * Oracle DB接続のための<code>Connection</code>を取得します。
     * @param server    データベース・サーバのホスト名またはIPアドレス
     * @param port      データベース・サーバの接続先 Port
     * @param id        データベース・インスタンス名
     * @param user      データベース・ユーザー名
     * @param passwd    ユーザ・パスワード
     * @return   <code>Connection</code>
     * @throws SQLException データベースへのアクセスに失敗したときスローされます。
     */
    public static Connection getConnection(String server, String port, String id, String user, String passwd)
            throws SQLException
    {
        // JDBC driverのロード
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        // connect stringを作成
        String connString = THIN_DRIVER + server + ":" + port + ":" + id;

        // コネクションを取得
        Connection conn = DriverManager.getConnection(connString, user, passwd);
        // Auto Commitにfalseをセット
        conn.setAutoCommit(false);

        return (conn);
    }

    /**
     * Oracle DB接続のための<code>Connection</code>を取得します。
     * @param user      データベース・ユーザー名
     * @param passwd    ユーザ・パスワー・
     * @param host      接続先のホスト（オラクルの登録済みのサービス名）
     * @return   <code>Connection</code>
     * @throws SQLException データベースへのアクセスに失敗したときスローされます。
     */
    public static Connection getConnection(String user, String passwd, String host)
            throws SQLException
    {
        // getting parameter from resource
        String port = getParam("WMS_DB_PORT");
        String id = getParam("WMS_DB_SID");

        return getConnection(host, port, id, user, passwd);
    }

    /**
     * Oracle DB接続のための<code>Connection</code>を取得します。
     * @param user      データベース・ユーザー名
     * @param passwd    ユーザ・パスワー・
     * @return   <code>Connection</code>
     * @throws SQLException データベースへのアクセスに失敗したときスローされます。
     */
    public static Connection getConnection(String user, String passwd)
            throws SQLException
    {
        // getting parameter from resource
        String server = getParam("WMS_DB_HOST");
        String port = getParam("WMS_DB_PORT");
        String id = getParam("WMS_DB_SID");

        return (getConnection(server, port, id, user, passwd));
    }

    /**
     * DB接続のための<code>Connection</code>を取得します。
     * 接続先,ユーザ,パスワードはパラメータ・リソースから取得します。
     * <pre>
     * パラメータ・キー(リソース内に定義が必要)
     * WMS_DB_HOST : 接続先ホスト
     * WMS_DB_PORT : 接続先 Port
     * WMS_DB_SID  : SID
     * WMS_DB_USER : 接続ユーザ
     * WMS_DB_PW   : ユーザ・パスワード
     * </pre>
     * @return   <code>Connection</code>
     * @throws SQLException データベースへのアクセスに失敗したときスローされます。
     */
    public static Connection getConnection()
            throws SQLException
    {
        // getting parameter from resource
        String server = getParam("WMS_DB_HOST");
        String port = getParam("WMS_DB_PORT");
        String id = getParam("WMS_DB_SID");
        String user = getParam("WMS_DB_USER");
        String passwd = getParam("WMS_DB_PW");

        return (getConnection(server, port, id, user, passwd));
    }

    /**
     * Acquire a<code>Connection</code> to the customer's database server.
     * Acquire the server connection info,user, and password from the parameter resource file.
     * <pre>
     * Parameter key (defined in the resource file)
     * WMS_DB_CUSTOMER_HOST : Customer's DB server name
     * WMS_DB_CUSTOMER_PORT : Customer's DB server port
     * WMS_DB_CUSTOMER_SID  : SID
     * WMS_DB_CUSTOMER_USER : Customer's DB user name
     * WMS_DB_CUSTOMER_PW   : Customer's DB user password
     * </pre>
     * @return   <code>Connection</code>
     * @throws SQLException thrown when failure to access the customer's database
     */
    public static Connection getCustomerConnection()
            throws SQLException
    {
        // getting parameter from resource
        String server = getParam("WMS_DB_CUSTOMER_HOST");
        String port = getParam("WMS_DB_CUSTOMER_PORT");
        String id = getParam("WMS_DB_CUSTOMER_SID");
        String user = getParam("WMS_DB_CUSTOMER_USER");
        String passwd = getParam("WMS_DB_CUSTOMER_PW");

        return (getConnection(server, port, id, user, passwd));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Gets the contents of parameter on a key basis.
     * @param key  key of the retrieving parameter
     * @return   string representation of parameter
     </en>*/
    private static String getParam(String key)
    {
        try
        {
            ResourceBundle rb = getBundle(WMS_DEFAULT_RESOURCE, Locale.getDefault());
            if (rb != null)
            {
                return (rb.getString(key));
            }
        }
        catch (Exception e)
        {
            // do nothing.
        }
        return "";

    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    private static int getIntParam(String key)
    {
        try
        {
            return Integer.parseInt(getParam(key));
        }
        catch (Exception e)
        {
            // do nothing.
        }
        return -1;
    }

    /**
     * キーから、パラメータの内容を数値（double）表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    private static double getDoubleParam(String key)
    {
        try
        {
            return Double.parseDouble(getParam(key));
        }
        catch (Exception e)
        {
            // do nothing.
        }
        return -1;
    }

    /**
     * キーから、パラメータの内容をArrayListに保持して取得します。<BR>
     * Valueは「,」（カンマ）で区切ってください。<BR>
     * @param key  取得するパラメータのキー
     * @return   ArrayListに保持したパラメータ
     */
    private static ArrayList<String> getListParam(String key)
    {
        ArrayList<String> retList = new ArrayList<String>();
        try
        {
            // WmsParamより値を取得します。
            StringTokenizer st = new StringTokenizer(getParam(key), ",");
            while (st.hasMoreTokens())
            {
                retList.add(st.nextToken());
            }

        }
        catch (Exception e)
        {
            // do nothing.
        }
        return retList;
    }

    /**
     * キーから、パラメータの内容を真偽値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの真偽値表現
     */
    private static boolean getBoolParam(String key)
    {
        return ResourceUtil.getBoolParam(WMS_DEFAULT_RESOURCE, key);
    }

    /**
     * リソースバンドルを取得します。
     * @param res  リソース
     * @param locale Localeオブジェクト
     * @return リソースバンドル
     */
    private static ResourceBundle getBundle(String res, Locale locale)
    {
        return (ResourceBundle.getBundle(res, locale));
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Gets the contents of parameter on a key basis.
     * @param key  key of the retrieving parameter
     * @return   string representation of parameter
     </en>*/
    private static Color getColorParam(String key)
    {
        try
        {
            ResourceBundle rb = getBundle(WMS_DEFAULT_RESOURCE, Locale.getDefault());
            if (rb != null)
            {
                if (String.valueOf(ColorType.RED).equals((rb.getString(key))))
                {
                    return Color.RED;
                }
                else if (String.valueOf(ColorType.GREEN).equals((rb.getString(key))))
                {
                    return Color.GREEN;
                }
                else if (String.valueOf(ColorType.BLUE).equals((rb.getString(key))))
                {
                    return Color.BLUE;
                }
                else if (String.valueOf(ColorType.YELLOW).equals((rb.getString(key))))
                {
                    return Color.YELLOW;
                }
                else if (String.valueOf(ColorType.WHITE).equals((rb.getString(key))))
                {
                    return Color.WHITE;
                }
                else if (String.valueOf(ColorType.BLACK).equals((rb.getString(key))))
                {
                    return Color.BLACK;
                }
            }
        }
        catch (Exception e)
        {
            // do nothing.
        }
        return Color.BLUE;
    }
}
