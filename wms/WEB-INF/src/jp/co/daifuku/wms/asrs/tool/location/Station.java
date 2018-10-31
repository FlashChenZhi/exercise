// $Id: Station.java 5355 2009-11-02 00:44:35Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;

/**<jp>
 * 搬送の起点・終点などポイントとなる位置を表すためのクラスです。
 * 現時点で搬送物がいくつあるか、到着予定の数、利用可能かどうかなどの
 * 情報を持ちます。<BR>
 * ステーションは、固有の番号を持ちます。これは任意の英数字からなり
 * 文字列として扱われます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to indicate the starting point/ ending point of the carry.
 * It preserves information such as the current quantity of carrying load, scheduled quantity 
 * of arrival, whether/not they are available, and etc. <BR>
 * Station have unique numbers. These numbers consist of any alphanumeric and are handled as strings.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class Station
        extends ToolEntity
{
    // Class fields --------------------------------------------------

    /**<jp>
     * ステーション番号の長さ
     </jp>*/
    /**<en>
     * StationNo Length
     </en>*/
    public static final int STATIONNO_LEN = 4;

    /**<jp>
     * ステーション状態を表すフィールド(切り離し) 搬送ルートチェックで使用されます。
     </jp>*/
    /**<en>
     * Field of station status (off-line). This is used in carry route check.
     </en>*/
    public static final int STATION_NG = 0;

    /**<jp>
     * ステーション状態を表すフィールド(正常) 搬送ルートチェックで使用されます。
     </jp>*/
    /**<en>
     * Field of station status (normal). This is used in carry route check.
     </en>*/
    public static final int STATION_OK = 1;

    /**<jp>
     * ステーション状態を表すフィールド(異常) 搬送ルートチェックで使用されます。
     </jp>*/
    /**<en>
     * Field of station status (error). This is used in carry route check.
     </en>*/
    public static final int STATION_FAIL = 2;

    /**<jp>
     * 入庫設定タイプを表すフィールド (その他)
     * <code>getInSettingType,setInSettingType</code> で使用します。
     </jp>*/
    /**<en>
     * Field of storage setting type (other)
     * Used in <code>getInSettingType,setInSettingType</code>.
     </en>*/
    public static final int IN_SETTING_OTHER = 0;

    /**<jp>
     * 入庫設定タイプを表すフィールド (先行設定)
     * <code>getInSettingType,setInSettingType</code> で使用します。
     </jp>*/
    /**<en>
     * Field of storage setting type (advanced setting)
     * Used in <code>getInSettingType,setInSettingType</code>.
     </en>*/
    public static final int IN_SETTING_PRECEDE = 1;

    /**<jp>
     * 入庫設定タイプを表すフィールド (在荷確認設定)
     * <code>getInSettingType,setInSettingType</code> で使用します。
     </jp>*/
    /**<en>
     * Field of storage setting type (set to check the load presence)
     * Used in <code>getInSettingType,setInSettingType</code>.
     </en>*/
    public static final int IN_SETTING_CONFIRM = 2;

    /**<jp>
     * ステーションのタイプを表すフィールド(その他)
     </jp>*/
    /**<en>
     * Field of the station type (other)
     </en>*/
    public static final int STATION_TYPE_OTHER = 0;

    /**<jp>
     * ステーションのタイプを表すフィールド(入庫)
     </jp>*/
    /**<en>
     * Field of the station type (storage)
     </en>*/
    public static final int STATION_TYPE_IN = 1;

    /**<jp>
     * ステーションのタイプを表すフィールド(出庫)
     </jp>*/
    /**<en>
     * Field of the station type (retrieval)
     </en>*/
    public static final int STATION_TYPE_OUT = 2;

    /**<jp>
     * ステーションのタイプを表すフィールド(入出庫兼用)
     </jp>*/
    /**<en>
     * Field of the station type (storage/retrieval available)
     </en>*/
    public static final int STATION_TYPE_INOUT = 3;

    /**<jp>
     * 作業場の種別を表すフィールド(未指定)
     </jp>*/
    /**<en>
     * Field which represents the type of workplace (undesignated)
     </en>*/
    public static final int NOT_WORKPLACE = 0;

    /**<jp>
     * 作業場の種別を表すフィールド(アイル独立ステーションの集合)
     </jp>*/
    /**<en>
     * Field of workshop type (group of stand alone stations)
     </en>*/
    public static final int STAND_ALONE_STATIONS = 1;

    /**<jp>
     * 作業場の種別を表すフィールド(アイル結合ステーションの集合)
     </jp>*/
    /**<en>
     * Field of workshop type (group of aisle connected stations)
     </en>*/
    public static final int AISLE_CONMECT_STATIONS = 2;

    /**<jp>
     * 作業場の種別を表すフィールド(代表ステーションの集合)
     </jp>*/
    /**<en>
     * Field of workshop type (group of main stations)
     </en>*/
    public static final int MAIN_STATIONS = 3;

    /**<jp>
     * 作業場の種別を表すフィールド（全体作業場の場合）
     </jp>*/
    /**<en>
     * Field of workshop type (general workshop)
     </en>*/
    public static final int WPTYPE_ALL = 4;

    /**<jp>
     * 作業表示種別を表すフィールド(作業指示画面なし)
     </jp>*/
    /**<en>
     * Field of on-line indication type ( no on-line indication )
     </en>*/
    public static final int NOT_OPERATIONDISPLAY = 0;

    /**<jp>
     * 作業表示種別を表すフィールド(作業表示運用、完了ボタン無し)
     </jp>*/
    /**<en>
     * Field of on-line indication type (on-line indication operated, without completion button)
     </en>*/
    public static final int OPERATIONDISPLAY = 1;

    /**<jp>
     * 作業表示種別を表すフィールド(作業指示運用、完了ボタン有り)
     </jp>*/
    /**<en>
     * Field of on-line indication type (on-line indication operated, with completion button)
     </en>*/
    public static final int OPERATIONINSTRUCTION = 2;

    /**<jp>
     * 送信可能・不可能を表すフィールド (送信可能)。
     </jp>*/
    /**<en>
     * Field of sendable/not sendable (sendable)
     </en>*/
    public static final int SENDABLE = 1;

    /**<jp>
     * 送信可能・不可能を表すフィールド (送信不可能)。
     </jp>*/
    /**<en>
     * Field of sendable/not sendable (not sendable)
     </en>*/
    public static final int NOT_SENDABLE = 0;

    /**<jp>
     * ステーションの中断中を表すフィ－ルド（中断中）
     </jp>*/
    /**<en>
     * Field of station suspention (suspended)
     </en>*/
    public static final int SUSPEND = 1;

    /**<jp>
     * ステーションの中断中を表すフィ－ルド（使用可能）
     </jp>*/
    /**<en>
     * Field of station suspention (active)
     </en>*/
    public static final int NOT_SUSPEND = 0;

    /**<jp>
     * ステーションの到着チェック有無を表すフィ－ルド（到着チェック有）
     </jp>*/
    /**<en>
     * Field of arrival checks - whether/not the check is operated (checked) 
     </en>*/
    public static final int ARRIVALCHECK = 1;

    /**<jp>
     * ステーションの到着チェック有無を表すフィ－ルド（到着チェック無）
     </jp>*/
    /**<en>
     * Field of arrival checks - whether/not the check is operated (no check)
     </en>*/
    public static final int NOT_ARRIVALCHECK = 0;

    /**<jp>
     * ステーションの荷姿チェック有無を表すフィ－ルド（荷姿チェック有）
     </jp>*/
    /**<en>
     * Field of load size check - whether/not the check is operated (checked))
     </en>*/
    public static final int LOADSIZECHECK = 1;

    /**<jp>
     * ステーションの荷姿チェック有無を表すフィ－ルド（荷姿チェック無）
     </jp>*/
    /**<en>
     * Field of load size check - whether/not the check is operated (no check)
     </en>*/
    public static final int NOT_LOADSIZECHECK = 0;

    /**<jp>
     * 払い出し区分を表すフィ－ルド（払い出し可）
     </jp>*/
    /**<en>
     * Field of the availability for removal (available)
     </en>*/
    public static final int PAYOUT_OK = 0;

    /**<jp>
     * 払い出し区分を表すフィ－ルド（払い出し不可）
     </jp>*/
    /**<en>
     * Field of the availability for removal (not available)
     </en>*/
    public static final int PAYOUT_NG = 1;

    /**<jp>
     * ステーションの在庫確認中フラグを表すフィ－ルド（空棚確認中）
     </jp>*/
    /**<en>
     * Field of inventory checking flag (checking the empty locations)
     </en>*/
    public static final int EMPTY_LOCATION_CHECK = 2;

    /**<jp>
     * ステーションの在庫確認中フラグを表すフィ－ルド（在庫確認中）
     </jp>*/
    /**<en>
     * Field of inventory checking flag (checking the inventory checking)
     </en>*/
    public static final int INVENTORYCHECK = 1;

    /**<jp>
     * ステーションの在庫確認中フラグを表すフィ－ルド（在庫確認未作業）
     </jp>*/
    /**<en>
     * Field of inventory checking flag (inventory check unprocessed)
     </en>*/
    public static final int NOT_INVENTORYCHECK = 0;

    /**<jp>
     * 再入庫予定データの作成有無を表すフィールド（再入庫データ作成なし）
     </jp>*/
    /**<en>
     * Field of scheduled restorage data creation (not created)
     </en>*/
    public static final int NOT_CREATE_RESTORING = 0;

    /**<jp>
     * 再入庫予定データの作成有無を表すフィールド（再入庫データ作成）
     </jp>*/
    /**<en>
     *  Field of scheduled restorage data creation (created)
     </en>*/
    public static final int CREATE_RESTORING = 1;

    /**<jp>
     * ピッキング出庫における戻り入庫時の搬送指示有無を表すフィ－ルド（AGC側で自動的に戻り入庫。搬送指示不要）
     </jp>*/
    /**<en>
     * Field which indicates whether/not the carry instruction is send when storing the returned 
     * load from the pick retrieval. 
     * (AGC automatically stores the returned load. no instruction necessary)
     </en>*/
    public static final int AGC_STORAGE_SEND = 0;

    /**<jp>
     * ピッキング出庫における戻り入庫時の搬送指示有無を表すフィ－ルド（eWareNavi側で搬送指示必要）
     </jp>*/
    /**<en>
     * Field which indicates whether/not the carry instruction is send when storing the returned 
     * load from the pick retrieval.
     * (eWareNavi requies the carry instruction)
     </en>*/
    public static final int AWC_STORAGE_SEND = 1;

    /**<jp>
     * モード切替種別を表すフィールド（モード切替無し）
     </jp>*/
    /**<en>
     * Field of mode switch type (no mode switching)
     </en>*/
    public static final int NO_MODE_CHANGE = 0;

    /**<jp>
     * モード切替種別を表すフィールド（AWCモード切替）
     </jp>*/
    /**<en>
     * Field of mode switch type (switch to AWC mode)
     </en>*/
    public static final int AWC_MODE_CHANGE = 1;

    /**<jp>
     * モード切替種別を表すフィールド（AGCモード切替）
     </jp>*/
    /**<en>
     * Field of mode switch type (switch to AGC mode)
     </en>*/
    public static final int AGC_MODE_CHANGE = 2;

    /**<jp>
     * モード切替種別を表すフィールド（自動モード切替）
     </jp>*/
    /**<en>
     * Field of mode switch type (automatic mode switch)
     </en>*/
    public static final int AUTOMATIC_MODE_CHANGE = 3;

    /**<jp>
     *  作業モードを表すフィールド（ニュートラル）
     </jp>*/
    /**<en>
     *  Field of work mode (neutral)
     </en>*/
    public static final int NEUTRAL = 0;

    /**<jp>
     *  作業モードを表すフィールド（入庫モード）
     </jp>*/
    /**<en>
     *  Field of work mode (storage mode)
     </en>*/
    public static final int STORAGE_MODE = 1;

    /**<jp>
     *  作業モードを表すフィールド（出庫モード）
     </jp>*/
    /**<en>
     *  Field of work mode (retrieval mode)
     </en>*/
    public static final int RETRIEVAL_MODE = 2;

    /**<jp>
     * モード切替要求区分を表すフィールド（モード切替要求なし）
     </jp>*/
    /**<en>
     * Field of mode switch requests (no request for mode switch)
     </en>*/
    public static final int NO_REQUEST = 0;

    /**<jp>
     * モード切替要求区分を表すフィールド（入庫モード切替要求）
     </jp>*/
    /**<en>
     * Field of mode switch requests  (request for switching to storage mode)
     </en>*/
    public static final int STORAGE_REQUEST = 1;

    /**<jp>
     * モード切替要求区分を表すフィールド（出庫モード切替要求）
     </jp>*/
    /**<en>
     * Field of mode switch requests (request for switching to retrieval mode)
     </en>*/
    public static final int RETRIEVAL_REQUEST = 2;

    /**<jp>
     * 機器切離しの許容範囲。この台数を超えた機器の切離しが発生した場合、StatusをNGにします。
     </jp>*/
    /**<en>
     * Acceptable range of off-line machines. If the number of off-line machines exceeded this 
     * number, the Status will be altered to 'unacceptable'.
     </en>*/
    protected static final int STATION_NG_JUDGMENT = 0;

    /**<jp>
     * モード切替要求応答待ち時間（秒）。
     * モード切替要求時間と現在の時間の差がこの値以下であれば、モード切替要求を送信することは出来ません。
     </jp>*/
    /**<en>
     * Wait time for the response to the mode switch request (second)
     * If the difference between mode switch requested time and current time is less than this value,
     * the mode switch request cannot be sent.
     </en>*/
    protected static final int MODECHANGE_REQUEST_WAITTIME = 20;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーション番号を保持する
     </jp>*/
    /**<en>
     * Preserve the station no.
     </en>*/
    protected String wStationNo = "";

    /**<jp>
     * 対応するGroup Controllerを保持する
     </jp>*/
    /**<en>
     * Preserve the Group Controller to support.
     </en>*/
    protected GroupController wGC = null;

    /**<jp>
     * ステーション状態を保持する
     </jp>*/
    /**<en>
     * Preserve the status of the station.
     </en>*/
    protected int wStatus = STATION_NG;

    /**<jp>
     * 最大パレット保持数
     </jp>*/
    /**<en>
     * Max number of pallet preserved.
     </en>*/
    protected int wMaxPalletQty = 0;

    /**<jp>
     * 最大搬送指示可能数
     </jp>*/
    /**<en>
     * Max. number of carry instruction sendable
     </en>*/
    protected int wMaxInstruction = 0;

    /**<jp>
     * コマンド送信可・不可
     </jp>*/
    /**<en>
     * Command sendable/not sendable
     </en>*/
    protected boolean wSendable = false;

    /**<jp>
     * 入庫設定タイプ
     </jp>*/
    /**<en>
     * Storage setting type
     </en>*/
    protected int wSettingType = 0;

    /**<jp>
     * 入出庫運用種別
     </jp>*/
    /**<en>
     * Operation type (storage/retrieval)
     </en>*/
    protected int wStationType = 0;

    /**<jp>
     * 作業場種別
     </jp>*/
    /**<en>
     * Type of workshop
     </en>*/
    protected int wWorkPlaceType = 0;

    /**<jp>
     * 作業表示種別
     </jp>*/
    /**<en>
     * Type of on-line indication
     </en>*/
    protected int wOperataionDisplay = NOT_OPERATIONDISPLAY;

    /**<jp>
     * ステーション名称
     </jp>*/
    /**<en>
     * Name of the station
     </en>*/
    protected String wStationName = "";

    /**<jp>
     * 中断中フラグ
     </jp>*/
    /**<en>
     * Suspention flag
     </en>*/
    protected boolean wSuspend = false;

    /**<jp>
     * 到着報告チェック
     </jp>*/
    /**<en>
     * Arrival reporting check
     </en>*/
    protected boolean wArrivalCheck = false;

    /**<jp>
     * 荷姿チェック
     </jp>*/
    /**<en>
     * Load size check
     </en>*/
    protected boolean wLoadSize = false;

    /**<jp>
     * 払い出し区分を表す。
     * true = 払出し可能 false = 払出し不可
     </jp>*/
    /**<en>
     * Distinguish the availability of removal.
     * true = available, false = unavaileble
     </en>*/
    protected boolean wRemove = true;

    /**<jp>
     * 再入庫予定データの作成有無をあらわします。
     </jp>*/
    /**<en>
     * Indicate whether/not the scheduled restorage data is created.
     </en>*/
    protected boolean wReStoringOperation = false;

    /**<jp>
     * ピッキング出庫における戻り入庫時の搬送指示送信有無をあらわします。
     </jp>*/
    /**<en>
     * Indicate whether/not the carry instruction is sent when re-storing
     * the load from pick-retrieval.
     </en>*/
    protected int wReStoringInstruction = 0;

    /**<jp>
     * 在庫確認中フラグ
     </jp>*/
    /**<en>
     * Inventory checking flag
     </en>*/
    protected int wInventoryCheckFlag = NOT_INVENTORYCHECK;

    /**<jp>
     * ステーションの所属する倉庫
     </jp>*/
    /**<en>
     * Warehouse that the station belongs to 
     </en>*/
    protected Warehouse wWarehouse = null;

    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. that the station belongs to
     </en>*/
    protected String wWhStationNo = "";

    /**<jp>
     * 親ステーション
     </jp>*/
    /**<en>
     * Parent station
     </en>*/
    protected Station wParentStation = null;

    /**<jp>
     * 親ステーションNo
     </jp>*/
    /**<en>
     * Parent station no.
     </en>*/
    protected String wParentStationNo = "";

    /**<jp>
     * 入出庫可能アイルStation アイル結合Stationのみ有効。アイル結合Stationの場合はnullがセットされます。
     </jp>*/
    /**<en>
     * Aisle station available of storage/retrieval. Valid only with the aisle connected Stations.
     * Null will be set in case with aisle connected Station.
     </en>*/
    protected Station wAisleStation = null;

    /**<jp>
     * 入出庫可能アイルStationNo アイル結合Stationのみ有効。アイル結合Stationの場合はnullがセットされます。
     </jp>*/
    /**<en>
     * Aisle station no. available of storage/retrieval. Valid only with the aisle connected Stations.
     * Null will be set in case with aisle connected Station.
     </en>*/
    protected String wAisleStationNo = "";

    /**<jp>
     * 次搬送Station（中継Stationのみ有効)
     </jp>*/
    /**<en>
     * Next carry station (only valid for relay stations)
     </en>*/
    protected Station wNextStation = null;

    /**<jp>
     * 次搬送StationNo（中継Stationのみ有効)
     </jp>*/
    /**<en>
     * Next carry station no. (only valid for relay stations)
     </en>*/
    protected String wNextStationNo = "";

    /**<jp>
     * 最終使用Station（作業場のみ有効)
     </jp>*/
    /**<en>
     * End use station (valid only with workshops)
     </en>*/
    protected Station wLastUsedStation = null;

    /**<jp>
     * 最終使用StationNo（作業場のみ有効)
     </jp>*/
    /**<en>
     * Next carry station no.(only valid for relay stations)
     </en>*/
    protected String wLastUsedStationNo = "";

    /**<jp>
     * リジェクトステーション
     </jp>*/
    /**<en>
     * Reject station
     </en>*/
    protected Station wRejectStation = null;

    /**<jp>
     * リジェクトステーションNo
     </jp>*/
    /**<en>
     * Reject station no.
     </en>*/
    protected String wRejectStationNo = "";

    /**<jp>
     * モード切替種別
     </jp>*/
    /**<en>
     * Type of mode switch
     </en>*/
    protected int wModeType = NO_MODE_CHANGE;

    /**<jp>
     * 作業モード
     </jp>*/
    /**<en>
     * Work mode
     </en>*/
    protected int wCurrentMode = NEUTRAL;

    /**<jp>
     * モード切替要求区分
     </jp>*/
    /**<en>
     * Mode change request 
     </en>*/
    protected int wModeRequest = NO_REQUEST;

    /**<jp>
     *モード切替要求開始時刻
     </jp>*/
    /**<en>
     * Starting time of the mode switch request
     </en>*/
    protected java.util.Date wModeRequestDate = null;

    /**<jp>
     * クラス名
     </jp>*/
    /**<en>
     * name of the class
     </en>*/
    protected String wClassName = "";

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 5355 $,$Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>Station</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>Station</CODE>.
     </en>*/
    public Station()
    {
    }

    /**<jp>
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param snum  保持するステーション番号
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create the new instance of <code>Station</code>. Please use <code>StationFactory</code> 
     * class if the instance which already has the  defined station is required.
     * @param snum  : station no. preserved
     </en>*/
    public Station(String snum)
    {
        wStationNo = snum;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーション番号を取得します。
     * @return    ステーション番号
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return    :the station no.
     </en>*/
    public String getStationNo()
    {
        return (wStationNo);
    }

    /**<jp>
     * ステーション番号をセットします。
     * @param  arg   ステーション番号
     </jp>*/
    /**<en>
     * Set the station no.
     * @param  arg : the station no.
     </en>*/
    public void setStationNo(String arg)
    {
        wStationNo = arg;
    }

    /**<jp>
     * 最大搬送指示可能数を設定します。
     * @param nm 最大搬送指示可能数
     </jp>*/
    /**<en>
     * Set the max number of carry isntructions sendable.
     * @param nm :max number of carry instructions sendable
     </en>*/
    public void setMaxInstruction(int nm)
    {
        wMaxInstruction = nm;
    }

    /**<jp>
     * 最大搬送指示可能数を取得します。
     * @return    最大搬送指示可能数
     </jp>*/
    /**<en>
     * Retrieve the max number of carry instructions sendable.
     * @return    :max number of carry instructions sendable
     </en>*/
    public int getMaxInstruction()
    {
        return (wMaxInstruction);
    }

    /**<jp>
     * このステーションがユニット出庫運用専用かどうかをチェックします。
     * このメソッドはステーションの種別をもとに運用のチェックを行ないます。
     * @return   ステーションがユニット出庫運用専用であればtrueを、そうでなければfalseを返す。
     </jp>*/
    /**<en>
     * Check whether/not the station is dedicated for retrieval operation.
     * This method cheks the operation based on the station type.
     * @return   :true if the station is dedicated for unit retrieval, or false if not.
     </en>*/
    public boolean isUnitOnly()
    {
        if (wStationType == STATION_TYPE_OUT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * ステーション状態を設定します。
     * @param sts ステーション状態
     * @thrwos InvalidStatusException 正しくないステーション状態を設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the station status.
     * @param sts :station status
     * @thrwos InvalidStatusException :Notifies if incorrect station status has been set.
     </en>*/
    public void setStatus(int sts)
            throws InvalidStatusException
    {
        //<jp> ステーション状態のチェック</jp>
        //<en> Check the status of the station.</en>
        switch (sts)
        {
            //<jp> 正しいステーション状態の一覧</jp>
            //<en> List of the correct station status.</en>
            case STATION_OK:
            case STATION_NG:
            case STATION_FAIL:
                break;

            //<jp> 正しくないステーション状態を設定しようとした場合は例外を発生させ、ステーション状態の変更はしない</jp>
            //<en> If incorrect station status were to set, it lets the exception occur and will not modify the station status.</en>
            default:
                //<jp> 定義されていないステーション状態です。</jp>
                //<en> This station status is undefined.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "STATUS";
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> ステーション状態の変更</jp>
        //<en> Modify the station status.</en>
        wStatus = sts;
    }

    /**<jp>
     * ステーション状態を取得します。ステーション状態の一覧は、
     * このクラスのフィールドとして定義されています。
     * @return    ステーション状態
     </jp>*/
    /**<en>
     * Retrieve the station status. List of station status is defined as fields 
     * of this class.
     * @return    station status
     </en>*/
    public int getStatus()
    {
        return (wStatus);
    }

    /**<jp>
     * ステーション名称を設定します。
     * @param nm 設定するステーション名称
     </jp>*/
    /**<en>
     * Set the station name.
     * @param nm :the station name to set
     </en>*/
    public void setStationName(String nm)
    {
        wStationName = nm;
    }

    /**<jp>
     * ステーション名称を取得します。
     * @return    ステーション名称
     </jp>*/
    /**<en>
     * Retrieve the the station name.
     * @return    station name
     </en>*/
    public String getStationName()
    {
        return (wStationName);
    }

    /**<jp>
     * 中断中フラグを設定します。
     * @param sus 中断中フラグ。
     </jp>*/
    /**<en>
     * Set the suspention flag.
     * @param sus :suspention flag
     </en>*/
    public void setSuspend(boolean sus)
    {
        wSuspend = sus;
    }

    /**<jp>
     * このステーションが中断中かどうか返します。
     * @return    true  中断中
     * @return    false 使用可能
     </jp>*/
    /**<en>
     * Return whether/not this station is in suspention.
     * @return    true  :suspended
     *             false :available (active)
     </en>*/
    public boolean isSuspend()
    {
        return (wSuspend);
    }

    /**<jp>
     * このステーションの中断中フラグを返します。
     * @return    中断中フラグの値
     </jp>*/
    /**<en>
     * Return the suspention flag of this station.
     * @return    :value of the suspention flag
     </en>*/
    public int getSuspend()
    {
        if (wSuspend)
        {
            return SUSPEND;
        }

        return (NOT_SUSPEND);
    }

    /**<jp>
     * 到着報告チェック有無を設定します。
     * @param ari 到着報告チェック有無
     </jp>*/
    /**<en>
     * Set whether/not the arrival reporting check is operated.
     * @param ari :whether/not the arrival reporting check is operated
     </en>*/
    public void setArrivalCheck(boolean ari)
    {
        wArrivalCheck = ari;
    }

    /**<jp>
     * このステーションが到着チェックありどうか返します。
     * @return    true  到着チェックあり
     * @return    false 到着チェックなし
     </jp>*/
    /**<en>
     * Return whether/not the arrival reporting check is operated.
     * @return    true  :arrival check in operation
     *             false :no arrival check
     </en>*/
    public boolean isArrivalCheck()
    {
        return (wArrivalCheck);
    }

    /**<jp>
     * 到着報告チェック有無を返します。
     * @return    到着報告チェック有無の値
     </jp>*/
    /**<en>
     * Return whether/not the arrival reporting check is operated.
     * @return    :value of whether/not the arrival reporting check is operated
     </en>*/
    public int getArrivalCheck()
    {
        if (wArrivalCheck)
        {
            return ARRIVALCHECK;
        }

        return (NOT_ARRIVALCHECK);
    }

    /**<jp>
     * 荷姿チェック有無を設定します。
     * @param load 荷姿チェック有無
     </jp>*/
    /**<en>
     * Set whether/not the load size is checked.
     * @param load :whether/not the load size is checked
     </en>*/
    public void setLoadSize(boolean load)
    {
        wLoadSize = load;
    }

    /**<jp>
     * このステーションが払い出し可能かどうかを返します。
     * @return    true  払出し可能
     * @return    false 払出し不可
     </jp>*/
    /**<en>
     * Return whether/not this station is available of removal.
     * @return    true  :available of removal
     *             false unavailable of removal
     </en>*/
    public boolean isRemove()
    {
        return (wRemove);
    }

    /**<jp>
     * ステーションの払い出し有無を返します。
     * @param rem 払い出し有無
     </jp>*/
    /**<en>
     * Return whether/not this station is available of removal.
     * @param rem :hether/not this station is available of removal.
     </en>*/
    public void setRemove(boolean rem)
    {
        wRemove = rem;
    }

    /**<jp>
     * このステーションが荷姿チェックありどうか返します。
     * @return    true  荷姿チェックあり
     * @return    false 荷姿チェックなし
     </jp>*/
    /**<en>
     * Return whether/not the load size is checked.
     * @return    true  :load size checked
     *             false :no check
     </en>*/
    public boolean isLoadSizeCheck()
    {
        return (wLoadSize);
    }

    /**<jp>
     * このステーションが荷姿チェックありどうか設定します。
     * @return    到着報告チェック有無の値
     </jp>*/
    /**<en>
     * Set whether/not the load size is checked in this station.
     * @return    :value of whether/not the arrival reporting is checked.
     </en>*/
    public int getLoadSize()
    {
        if (wLoadSize)
        {
            return LOADSIZECHECK;
        }

        return (NOT_LOADSIZECHECK);
    }

    /**<jp>
     * 在庫確認中フラグを設定します。
     * @param flag 在庫確認中フラグ
     * @thrwos InvalidStatusException 正しくない在庫確認中フラグの値を設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the inventory checking flag.
     * @param flag :inventory checking flag
     * @thrwos InvalidStatusException :Notifies if incorrect value of inventory checking flag is set.
     </en>*/
    public void setInventoryCheckFlag(int flag)
            throws InvalidStatusException
    {
        //<jp> 在庫確認中フラグのチェック</jp>
        //<en> Check teh inventory checking flag.</en>
        switch (flag)
        {
            //<jp> 正しい在庫確認中フラグの一覧</jp>
            //<en> List of the correct inventory checking flag</en>
            case EMPTY_LOCATION_CHECK:
            case INVENTORYCHECK:
            case NOT_INVENTORYCHECK:
                break;

            //<jp> 正しくない在庫確認中フラグを設定しようとした場合は例外を発生させ、在庫確認中フラグの変更はしない</jp>
            //<en> If incorrect inventory checking flag were to set, it lets the exception occur and will not modify the inventory checking flag.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "INVENTORYCHECKFLAG";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 在庫確認中フラグの変更</jp>
        //<en> Modify the inventory checking flag.</en>
        wInventoryCheckFlag = flag;
    }

    /**<jp>
     * 在庫確認中フラグを取得します。
     * @return    在庫確認中フラグ
     </jp>*/
    /**<en>
     * Retrieve the inventory checking flag.
     * @return    :nventory checking flag
     </en>*/
    public int getInventoryCheckFlag()
    {
        return (wInventoryCheckFlag);
    }

    /**<jp>
     * 在庫確認中フラグの値を確認し、在庫確認中または空棚確認中であればtrueを、在庫確認未作業であればfalseを返します。
     * @return    在庫確認中または空棚確認中の場合true、在庫確認未作業であればfalse
     </jp>*/
    /**<en>
     * Check teh value of inventory checking flag, then return true if inventory check or 
     * empty location check is in process, or return false if inventory check is unprocessed.
     * @return    :true if inventory/empty location checking or false if these checks are unprocessed.
     </en>*/
    public boolean isInventoryCheck()
    {
        switch (wInventoryCheckFlag)
        {
            case EMPTY_LOCATION_CHECK:
            case INVENTORYCHECK:
                return true;

            case NOT_INVENTORYCHECK:
                return false;

            default:
                return false;
        }
    }

    /**<jp>
     * 在庫確認中フラグの値を確認し、空棚確認中であればtrueを、それ以外であればfalseを返します。
     * @return    空棚確認中の場合true、それ以外であればfalse
     </jp>*/
    /**<en>
     * Check the valud of inventory checking flag, then return true if empty location is being checked
     * or return false for other status.
     * @return    :true if empty lcoation is checked, or false for all other cases.
     </en>*/
    public boolean isEmptyLocationCheck()
    {
        switch (wInventoryCheckFlag)
        {
            case EMPTY_LOCATION_CHECK:
                return true;

            case NOT_INVENTORYCHECK:
            case INVENTORYCHECK:
                return false;

            default:
                return false;
        }
    }

    /**<jp>
     * 再入庫予定データの作成有無を設定します。
     * @param type 再入庫予定データの作成有無
     *   true  : 再入庫予定データ作成
     *   false : 再入庫予定データ作成なし
     </jp>*/
    /**<en>
     * Set whether/not the scheduled restorage data is created.
     * @param type :whether/not the scheduled restorage data is created
     *   true  : the scheduled restorage data is created
     *   false : the scheduled restorage data is not created
     </en>*/
    public void setReStoringOperation(boolean type)
    {
        wReStoringOperation = type;
    }

    /**<jp>
     * 再入庫予定データの作成有無を取得します。
     * @return 再入庫予定データの作成有無
     *   true  : 再入庫予定データ作成
     *   false : 再入庫予定データ作成なし
     </jp>*/
    /**<en>
     * Retrieve whether/not the scheduled restorage data is created.
     * @return :whether/not the scheduled restorage data is created
     *   true  : the scheduled restorage data is created
     *   false : the scheduled restorage data is not created
     </en>*/
    public boolean isReStoringOperation()
    {
        return (wReStoringOperation);
    }

    /**<jp>
     * 再入庫予定データの作成有無を取得します。
     * @return 再入庫予定データの作成有無
     </jp>*/
    /**<en>
     * Retrieve whether/not the scheduled restorage data is created.
     * @return :whether/not the scheduled restorage data is created
     </en>*/
    public int getReStoringOperation()
    {
        if (wReStoringOperation)
        {
            return CREATE_RESTORING;
        }

        return (NOT_CREATE_RESTORING);
    }

    /**<jp>
     * ピッキング出庫における戻り入庫時の搬送指示送信有無を設定します。
     * @param type 搬送指示送信有無
     * @thrwos InvalidStatusException 正しくない搬送指示送信有無を設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set whether/not the carry instruction should be sent when restoring the retunrd load from pick-retrieval.
     * @param type :whether/not the carry instruction shuold be sent
     * @thrwos InvalidStatusException :Notifies if it set the status of whether/not to send the carry 
     * instruction incorrectly.
     </en>*/
    public void setReStoringInstruction(int type)
            throws InvalidStatusException
    {
        //<jp> 搬送指示送信有無</jp>
        //<en>  whether/not the carry instruction should be sent </en>
        switch (type)
        {
            //<jp> 正しい搬送指示送信有無の一覧</jp>
            //<en> List of correct whether/not the carry instruction shuold be sent</en>
            case AGC_STORAGE_SEND:
            case AWC_STORAGE_SEND:
                break;

            //<jp> 正しくない搬送指示送信有無を設定しようとした場合は例外を発生させ、搬送指示送信有無の変更はしない</jp>
            //<en> If incorrect whether/not the carry instruction shuold be sent were to set, </en>
            //<en> it lets the exception occur and will not modify the whether/not the carry instruction shuold be sent.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "RESTORINGINSTRUCTION";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 搬送指示送信有無の変更</jp>
        //<en> Modify the whether/not the carry instruction shuold be sent.</en>
        wReStoringInstruction = type;
    }

    /**<jp>
     * 搬送指示有無を取得します。
     * @return    搬送指示有無
     </jp>*/
    /**<en>
     * Retrieve whether/not the carry instruction should be sent
     * @return    :whether/not the carry instruction should be sent
     </en>*/
    public int getReStoringInstruction()
    {
        return (wReStoringInstruction);
    }

    /**<jp>
     * このステーションが属している倉庫Noを設定します。
     * @param whnum ステーションの属している倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no. that this station belongs to.
     * @param whnum :warehouse no, that this station belongs to
     </en>*/
    public void setWhStationNo(String whnum)
    {
        wWhStationNo = whnum;
    }

    /**<jp>
     * このステーションが属している倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no. that this station belongs to.
     * @return :warehouse no.
     </en>*/
    public String getWarehouseStationNo()
    {
        return wWhStationNo;
    }

    /**<jp>
     * 親ステーションNoを設定します。
     * @param pnum 親ステーションNo
     </jp>*/
    /**<en>
     * Set the parent station no.
     * @param pnum :the parent station no.
     </en>*/
    public void setParentStationNo(String pnum)
    {
        wParentStationNo = pnum;
    }

    /**<jp>
     * 親ステーションNoを取得します。
     * @return 親ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the parent station no.
     * @return :the parent station no.
     </en>*/
    public String getParentStationNo()
    {
        return wParentStationNo;
    }

    /**<jp>
     * 親ステーションを設定します。
     * 実際には親ステーションNoを設定します。
     * @param pst 設定する親ステーション
     </jp>*/
    /**<en>
     * Set the parent station.
     * Specifically, the parent station no. should be set.
     * @param pst :the parent station to set
     </en>*/
    public void setParentStation(Station pst)
    {
        setParentStationNo(pst.getStationNo());
    }

    /**<jp>
     * 入出庫可能アイルステーションNoを設定します。
     * @param anum 入出庫可能アイルステーションNo
     </jp>*/
    /**<en>
     * Set the aisle station no. available for storage/retrieval.
     * @param anum :the aisle station no. available
     </en>*/
    public void setAisleStationNo(String anum)
    {
        wAisleStationNo = anum;
    }

    /**<jp>
     * 入出庫可能アイルステーションNoを取得します。
     * @return 入出庫可能アイルステーションNo
     </jp>*/
    /**<en>
     * Retrieve the aisle station no. available.
     * @return :the aisle station no. available
     </en>*/
    public String getAisleStationNo()
    {
        return wAisleStationNo;
    }

    /**<jp>
     * 入出庫可能アイルステーションを設定します。
     * 実際にはアイルステーションNoを設定します。
     * @param ast 入出庫可能アイルステーション。
     </jp>*/
    /**<en>
     * Set the aisle station no. available of storage/retrieval.
     * Specifically set the aisle station no.
     * @param ast :the aisle station no. available of storage/retrieval
     </en>*/
    public void setAisleStation(Station ast)
    {
        setAisleStationNo(ast.getStationNo());
    }

    /**<jp>
     * 次搬送ステーションNoを設定します。
     * @param nnum 次搬送ステーションNo
     </jp>*/
    /**<en>
     * Set the carry-to station no.
     * @param nnum :the carry-to station no.
     </en>*/
    public void setNextStationNo(String nnum)
    {
        wNextStationNo = nnum;
    }

    /**<jp>
     * 次搬送ステーションNoを取得します。
     * @return 次搬送ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the carry-to station no.
     * @return :the carry-to station no.
     </en>*/
    public String getNextStationNo()
    {
        return wNextStationNo;
    }

    /**<jp>
     * 最終使用ステーションNoを設定します。
     * @param lnum 最終使用ステーションNo
     </jp>*/
    /**<en>
     * Set the end-use station no.
     * @param lnum :end-use station no.
     </en>*/
    public void setLastUsedStationNo(String lnum)
    {
        wLastUsedStationNo = lnum;
    }

    /**<jp>
     * 最終使用ステーションNoを取得します。
     * @return 最終使用ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the end-use station no.
     * @return :end-use station no.
     </en>*/
    public String getLastUsedStationNo()
    {
        return wLastUsedStationNo;
    }

    /**<jp>
     * リジェクトステーションNoを設定します。
     * @param rnum リジェクトステーションNo
     </jp>*/
    /**<en>
     * Set the reject station no.
     * @param rnum :reject station no.
     </en>*/
    public void setRejectStationNo(String rnum)
    {
        wRejectStationNo = rnum;
    }

    /**<jp>
     * リジェクトステーションNoを取得します。
     * 実際にはリジェクトステーションNoを設定します。
     * @return リジェクトステーションNo
     </jp>*/
    /**<en>
     * Retrieve the reject station no.
     * Specifically set the reject station no.
     * @return :reject station no.
     </en>*/
    public String getRejectStationNo()
    {
        return wRejectStationNo;
    }

    /**<jp>
     * モード切替種別を設定します。
     * @param mt モード切替種別
     </jp>*/
    /**<en>
     * Set the mode switch type.
     * @param mt :the mode switch type
     </en>*/
    public void setModeType(int mt)
    {
        wModeType = mt;
    }

    /**<jp>
     * モード切替種別を取得します。
     * @return    モード切替種別
     </jp>*/
    /**<en>
     * Retrieve the mode switch type.
     * @return    :the mode switch type
     </en>*/
    public int getModeType()
    {
        return wModeType;
    }

    /**<jp>
     * 現在の作業モードを設定します。
     * @param mode   作業モード
     </jp>*/
    /**<en>
     * Set the current work mode.
     * @param mode   :work mode
     </en>*/
    public void setCurrentMode(int mode)
    {
        wCurrentMode = mode;
    }

    /**<jp>
     * 現在の作業モードを取得します。
     * @return   作業モード
     </jp>*/
    /**<en>
     * Retrieve the current work mode.
     * @return   :work mode
     </en>*/
    public int getCurrentMode()
    {
        return wCurrentMode;
    }

    /**<jp>
     * モード切替要求区分を設定します。
     * @param request   モード切替要求区分
     </jp>*/
    /**<en>
     * Set the segment of mode switch request.
     * @param request   :segment of mode switch request
     </en>*/
    public void setModeRequest(int request)
    {
        wModeRequest = request;
    }

    /**<jp>
     * モード切替要求区分を取得します。
     * @return   モード切替要求区分
     </jp>*/
    /**<en>
     * Retrieve the segment of mode switch request.
     * @return   :segment of mode switch request
     </en>*/
    public int getModeRequest()
    {
        return wModeRequest;
    }

    /**<jp>
     * モード切替要求開始時刻を設定します
     * @param time  モード切替要求開始時刻
     </jp>*/
    /**<en>
     * Set the start time of mode switch request.
     * @param time  :start time of mode switch request
     </en>*/
    public void setModeRequestDate(java.util.Date time)
    {
        wModeRequestDate = time;
    }

    /**<jp>
     * モード切替要求開始時刻を取得します
     * @return time  モード切替要求開始時刻
     </jp>*/
    /**<en>
     * Retrieve the start time of mode switch request.
     * @return time  :start time of mode switch request
     </en>*/
    public java.util.Date getModeRequestDate()
    {
        return wModeRequestDate;
    }

    /**<jp>
     * 対応するグループ・コントローラを設定します。
     * @param gctl 設定するグループ・コントローラ
     </jp>*/
    /**<en>
     * Set the supporting group controller.
     * @param gctl :group controller to set
     </en>*/
    public void setGroupController(GroupController gctl)
    {
        wGC = gctl;
    }

    /**<jp>
     * 対応するグループ・コントローラを取得します。
     * @return    対応するグループ・コントローラ
     </jp>*/
    /**<en>
     * Retrieve the su@porting group controller.
     * @return    :the supporting group controller
     </en>*/
    public GroupController getGroupController()
    {
        return (wGC);
    }

    /**<jp>
     * 最大パレット保持数を設定します。
     * @param pnum 設定する最大パレット保持数
     </jp>*/
    /**<en>
     * Set the max pallet quantity preserved.
     * @param pnum :max pallet quantity preserved to set
     </en>*/
    public void setMaxPalletQty(int pnum)
    {
        wMaxPalletQty = pnum;
    }

    /**<jp>
     * 最大パレット保持数を取得します。
     * @return    最大パレット保持数
     </jp>*/
    /**<en>
     * Retrieve the max pallet quantity preserved.
     * @return    :max pallet quantity preserved
     </en>*/
    public int getMaxPalletQty()
    {
        return (wMaxPalletQty);
    }

    /**<jp>
     * ステーションタイプを設定します。<BR>
     * ステーションタイプ(入出庫)タイプの一覧は、このクラスのフィールドとして定義されています。
     * @param type 設定するステーションタイプ
     * @thrwos InvalidStatusException 正しくないステーションタイプを設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the station type.<BR>
     * The list of station type (storage/retrieval) is defined in the field of this class.
     * @param type :station type to set
     * @thrwos InvalidStatusException :Notifies if incorrect station type has been set.
     </en>*/
    public void setStationType(int type)
            throws InvalidStatusException
    {
        //<jp> ステーションタイプのチェック</jp>
        //<en> Check the station type</en>
        switch (type)
        {
            //<jp> 正しいタイプの一覧</jp>
            //<en> List of corect types</en>
            case STATION_TYPE_OTHER:
            case STATION_TYPE_IN:
            case STATION_TYPE_OUT:
            case STATION_TYPE_INOUT:
                break;

            //<jp> 正しくないステーションタイプを設定しようとした場合は例外を発生させ、ステーションタイプの変更はしない</jp>
            //<en> If incorrect station type were to set, it lets the exception occur and will not modify the station type.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "STATIONTYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> ステーションタイプの変更</jp>
        //<en> Modify the station type.</en>
        wStationType = type;
    }

    /**<jp>
     * ステーションタイプを取得します。<BR>
     * ステーションタイプ(入出庫)タイプの一覧は、このクラスのフィールドとして定義されています。
     * @return    ステーションタイプ
     </jp>*/
    /**<en>
     * Retrieve the station type.<BR>
     * The list of station types (storage/retrieval) is defined in the field of this class.
     * @return    :station type
     </en>*/
    public int getStationType()
    {
        return (wStationType);
    }

    /**<jp>
     * 入庫設定タイプを取得します。
     * 入庫設定タイプの一覧は、このクラスのフィールドとして定義されています。
     * @return    入庫設定タイプ
     </jp>*/
    /**<en>
     * Retrieve the storage setting type.
     * The list of the storage setting type is defined in the field of this class.
     * @return    :the storage setting type
     </en>*/
    public int getSettingType()
    {
        return (wSettingType);
    }

    /**<jp>
     * 入庫設定タイプを設定します。
     * 入庫設定タイプの一覧は、このクラスのフィールドとして定義されています。
     * @param type 設定する入庫設定タイプ
     * @thrwos InvalidStatusException 正しくない入庫設定タイプを設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the the storage setting type.
     * The list of the storage setting type is defined in the field of this class.
     * @param type :the storage setting type to set
     * @thrwos InvalidStatusException :Notifies if incorrect storage setting type is set.
     </en>*/
    public void setSettingType(int type)
            throws InvalidStatusException
    {
        //<jp> 入庫設定タイプのチェック</jp>
        //<en> Check the storage setting type</en>
        switch (type)
        {
            //<jp> 正しい設定タイプの一覧</jp>
            //<en> List of correct setting type</en>
            case IN_SETTING_OTHER:
            case IN_SETTING_CONFIRM:
            case IN_SETTING_PRECEDE:
                break;

            //<jp> 正しくない入庫設定タイプを設定しようとした場合は例外を発生させ、入庫設定タイプの変更はしない</jp>
            //<en> If incorrect storage setting type were to set, it lets the exception occur and will not modify the storage setting type.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "SETTINGTYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 入庫設定タイプの変更</jp>
        //<en> Modify the storage setting type.</en>
        wSettingType = type;
    }

    /**<jp>
     * 作業場種別を取得します。
     * 作業場種別の一覧は、このクラスのフィールドとして定義されています。
     * @return    作業場種別
     </jp>*/
    /**<en>
     * Retrieve the type of workshop.
     * List of workshop types is defined in the field of this class.
     * @return    :type of workshop
     </en>*/
    public int getWorkPlaceType()
    {
        return (wWorkPlaceType);
    }

    /**<jp>
     * 作業場種別を設定します。<BR>
     * 作業場種別の一覧は、このクラスのフィールドとして定義されています。
     * @param type 設定する作業場種別
     * @thrwos InvalidStatusException 正しくない作業場種別を設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the type of workshop.<BR>
     * List of workshop types is defined in the field of this class.
     * @param type :type of workshop to set
     * @thrwos InvalidStatusException :Notifies if incorrect workshop type is set.
     </en>*/
    public void setWorkPlaceType(int type)
            throws InvalidStatusException
    {
        //<jp> 作業場種別のチェック</jp>
        //<en> Check the type of workshop.</en>
        switch (type)
        {
            //<jp> 正しい作業場種別の一覧</jp>
            //<en> List of correct type of workshop</en>
            case NOT_WORKPLACE:
            case STAND_ALONE_STATIONS:
            case AISLE_CONMECT_STATIONS:
            case MAIN_STATIONS:
            case WPTYPE_ALL:
                break;
            //<jp> 正しくない作業場種別を設定しようとした場合は例外を発生させ、作業場種別の変更はしない</jp>
            //<en> If incorrect type of workshop were to set, it lets the exception occur and will not modify the type of workshop.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "WorkPlaceType";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 作業場種別の変更</jp>
        //<en> Modify the type of workshop.</en>
        wWorkPlaceType = type;
    }

    /**<jp>
     * このステーションの作業表示画面の種別を設定します。
     * 作業表示画面の種別の一覧は、このクラスのフィールドとして定義されています。
     * @param disp 設定する作業表示画面の種別
     * @thrwos InvalidStatusException 正しくない作業表示種別を設定された場合に通知します。
     </jp>*/
    /**<en>
     * Set the type of on-line indication of this station.
     * List of on-line indication types is defined in the field of this class.
     * @param disp :type of on-line indication to set
     * @thrwos InvalidStatusException :Notifies if incorrect on-line indication type is set.
     </en>*/
    public void setOperationDisplay(int disp)
            throws InvalidStatusException
    {
        //<jp> 作業指示画面の種別のチェック</jp>
        //<en> Check the type of on-line indications.</en>
        switch (disp)
        {
            //<jp> 正しい作業指示画面の種別の一覧</jp>
            //<en> List of correct types of on-line indication</en>
            case NOT_OPERATIONDISPLAY:
            case OPERATIONDISPLAY:
            case OPERATIONINSTRUCTION:
                break;

            //<jp> 正しくない作業指示画面の種別を設定しようとした場合は例外を発生させ、作業指示画面の種別の変更はしない</jp>
            //<en> If incorrect on-line indication types were to set, it lets the exception occur and will not modify the type of on-line indication.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "OperataionDisplay";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 作業指示画面の種別の変更</jp>
        //<en> Modify the type of on-line indication.</en>
        wOperataionDisplay = disp;
    }

    /**<jp>
     * このステーションの作業指示画面の有無を返します。
     * @return    作業指示画面の有無
     </jp>*/
    /**<en>
     * Return whether/not the on-line indication is operated in this station.
     * @return    whether/not the on-line indication is operated
     </en>*/
    public int getOperationDisplay()
    {
        return wOperataionDisplay;
    }

    /**<jp>
     * このステーションが指示情報として送信可能かどうかを設定します。
     * @param    snd 送信可能ならば true
     </jp>*/
    /**<en>
     * Set whether/not the carry instruction should be sendable to this station.
     * @param    snd :true if setting the station sendable
     </en>*/
    public void setSendable(boolean snd)
    {
        wSendable = snd;
    }

    /**<jp>
     * このステーションが指示情報として送信可能かどうか返します。
     * @return    true 送信可能
     * @return    false 送信不可能
     </jp>*/
    /**<en>
     * Return whether/not the carry instruction should be sendable to this station.
     * @return    true  :sendable
     * @return    false :not sendable
     </en>*/
    public boolean isSendable()
    {
        return (wSendable);
    }

    /**<jp>
     * このステーションが指示情報として送信可能かどうかを返します。
     * @param    snd 送信可能ならば true
     </jp>*/
    /**<en>
     * Return hether/not the carry instruction should be sendable to this station.
     * @param    snd :true if sendable
     </en>*/
    public boolean getSendable()
    {
        return wSendable;
    }

    /**<jp>
     * このステーションが入庫ステーションかどうかを返します。
     * 入出庫ステーションは、Trueが返ります。
     * @return    true 入庫ステーション
     * @return    false 入庫ステーションではない
     </jp>*/
    /**<en>
     * Return whether/not this station is a storage station.
     * Return trud for storage/retrieval stations.
     * @return    true  :storage station
     * @return    false :this is not the storage station
     </en>*/
    public boolean isInStation()
    {
        boolean itype = true;

        switch (wStationType)
        {
            //<jp> タイプのチェック</jp>
            //<en> Check the type</en>
            case STATION_TYPE_IN:
            case STATION_TYPE_INOUT:
                itype = true;
                break;

            case STATION_TYPE_OUT:
                itype = false;
                break;
        }

        return (itype);
    }

    /**<jp>
     * このステーションが出庫ステーションかどうかを返します。
     * 入出庫ステーションは、Trueが返ります。
     * @return    true 出庫ステーション
     * @return    false 出庫ステーションではない
     </jp>*/
    /**<en>
     * Return whether/not this station is a retrieval station.
     * Return true for storage/retrieval stations.
     * @return    true  :retrieval station
     * @return    false :this is not a retrieval station.
     </en>*/
    public boolean isOutStation()
    {
        boolean itype = true;

        switch (wStationType)
        {
            //<jp> タイプのチェック</jp>
            //<en> Check the type</en>
            case STATION_TYPE_OUT:
            case STATION_TYPE_INOUT:
                itype = true;
                break;

            case STATION_TYPE_IN:
                itype = false;
                break;
        }
        return (itype);
    }

    /**<jp>
     * クラス名を設定します。
     * @param cls クラス名
     </jp>*/
    /**<en>
     * Set the name of the class.
     * @param cls :the name of the class
     </en>*/
    public void setClassName(String cls)
    {
        wClassName = cls;
    }

    /**<jp>
     * クラス名をを取得します。
     * @return    クラス名
     </jp>*/
    /**<en>
     * Retrieve the name of the class
     * @return    :the name of the class
     </en>*/
    public String getClassName()
    {
        return (wClassName);
    }

    /**<jp>
     * 文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100);
        try
        {
            buf.append("\nStation Number:" + wStationNo);
            buf.append("\nMax Pallet Quantity:" + String.valueOf(wMaxPalletQty));
            buf.append("\nSendable:" + wSendable);
            buf.append("\nStation Status:" + getStatus());
            if (wGC == null)
            {
                buf.append("\nGroup Controller: null");
            }
            else
            {
                buf.append("\n------Group Controller-------");
                buf.append("\nGroup Controller:" + wGC.toString());
                buf.append("\n------Group Controller-------");
            }
            buf.append("\nType:" + String.valueOf(wStationType));
            buf.append("\nInSettingType:" + String.valueOf(wSettingType));
            buf.append("\nWorkPlaceType:" + String.valueOf(wWorkPlaceType));
            buf.append("\nOperataionDisplay:" + wOperataionDisplay);
            buf.append("\nStationName:" + wStationName);
            buf.append("\nSuspend:" + wSuspend);
            buf.append("\nArrivalCheck:" + wArrivalCheck);
            buf.append("\nLoadSizeCheck:" + wLoadSize);
            buf.append("\nInventoryCheckFlag:" + wInventoryCheckFlag);
            buf.append("\nReStoringOperation:" + wReStoringOperation);
            buf.append("\nReStoringInstruction:" + wReStoringInstruction);
            buf.append("\nWarehouse:" + wWhStationNo);
            buf.append("\nParent Station:" + wParentStationNo);
            buf.append("\nAisle Station:" + wAisleStationNo);
            buf.append("\nNext Station:" + wNextStationNo);
            buf.append("\nLastUsed Station:" + wLastUsedStationNo);
            buf.append("\nReject Station:" + wRejectStationNo);
            buf.append("\nModeType:" + wModeType);
            buf.append("\nCurrentMode:" + wCurrentMode);
            buf.append("\nChangeModeRequest:" + wModeRequest);
            buf.append("\nChangeModeRequestTime:" + wModeRequestDate);
            buf.append("\nClass Name:" + wClassName);
        }
        catch (Exception e)
        {
        }

        return (buf.toString());
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

