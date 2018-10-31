// $Id: PulldownController.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.RoutePiece;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.Part11StockHistImpHandler;
import jp.co.daifuku.wms.base.dbhandler.Part11StockHistImpSearchKey;
import jp.co.daifuku.wms.base.dbhandler.Part11StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.Part11StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PrintHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.PrintHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReasonHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Part11StockHistImp;
import jp.co.daifuku.wms.base.entity.Part11StockHistory;
import jp.co.daifuku.wms.base.entity.PrintHistory;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 可変プルダウンメニューの表示データ取得を行います。<br>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2007/03/07</TD>
 * <TD>T.Kishimoto</TD>
 * <TD>新規作成</TD>
 * </TR>
 * <TR>
 * <TD>2007/07/05</TD>
 * <TD>Y.Okamura</TD>
 * <TD>Stationから作業場、エリアを逆算するよう修正</TD>
 * </TR>
 * </TABLE>
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author Last commit: $Author: kishimoto $
 */

public class PulldownController
        extends AbstractController
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * プルダウン用デリミタ
     */
    public static final String PDDELIM = ",";

    /**
     * エリアのプルダウンタイプ
     */
    public enum AreaType
    {
        /** ASRSと平置き */
        FLOOR_AND_ASRS,
        /** AS/RSエリア */
        ASRS,
        /** 平置エリア */
        FLOOR,
        /** 平置でフリー管理以外 */
        FLOOR_LOCATE,
        /** 平置で商品固定棚 */
        FLOOR_FIXEDLOCATE,
        /** 平置エリアと仮置エリアと入荷エリア */
        FLOOR_AND_TEMP_AND_RECEIVE,
        /** 仮置エリア */
        TEMP,
        /** 入荷エリア */
        RECEIVE,
        /** 移動中エリア以外 */
        NOT_MOVING,
        /** 移動中エリア以外かつAS/RS端末エリア情報を参照 */
        NOT_MOVING_TERM,
        /** 全エリア */
        ALL
    }

    /**
     * ステーションのプルダウンタイプ
     */
    public enum StationType
    {
        /** 入庫設定用 */
        STORAGE,
        /** 出庫設定用 */
        RETRIEVAL,
        /** 商品コード指定出庫設定用 */
        ITEM_RETRIEVAL,
        /** 積み増し入庫用 */
        ADD_STORAGE,
        /** 再入庫メンテナンス・問合せ用 */
        RESTORING_MNT,
        /** 直行(搬送元)用 */
        DIRECT_TRANSFER_FROM,
        /** 直行(搬送先)用 */
        DIRECT_TRANSFER_TO,
        /** 在庫確認用 */
        INVENTORY_CHECK,
        /** 作業表示用 */
        OPERATION_DISPLAY,
        /** 作業メンテナンス用 */
        WORK_MNT,
        /** 全ステーション */
        ALL,
        /** 入庫設定＋平置エリア */
        FLOORANDSTORAGE,
        /** 出庫設定＋平置エリア */
        FLOORANDRETRIEVAL,
    }

    /**
     * バンクのプルダウンタイプ
     */
    public enum BankType
    {
        /** 平置き、移動ラックエリア用 */
        FLOOR,
        /** AS/RSエリア用 */
        ASRS
    }

    /**
     * 引当パターンのプルダウンタイプ
     */
    public enum AllocateType
    {
        /** 通常（出庫）用 */
        NORMAL,
        /** 補充用 */
        REPLENISHMENT
    }

    /**
     * ステーションの先頭文字列
     */
    public enum Distribution
    {
        /** なし */
        UNUSE,
        /** 自動振り分け */
        AUTO,
        /** 全て */
        ALL
    }

    /**
     * RFTNo.のプルダウンタイプ
     */
    public enum TerminalType
    {
        /** HT */
        HT,
        /** Pカート */
        PCART
    }

    /**
     * ソフトゾーン検索条件
     */
    public enum SoftZoneType
    {
        /** マスターに登録されているソフトゾーンを検索 **/
        MASTER,
        /** 紐付くエリアからソフトゾーンを検索 */
        AREA
    }

    /**
     * 送受信検索条件
     */
    public enum DataType
    {
        /** 受信を検索 **/
        RECEIVE,
        /** 送信を検索 */
        SEND
    }


    /**
     * 初期表示フラグ（初期表示する項目を表す）
     */
    private static final String SELECT_ON = "1";

    /**
     * 初期表示フラグ（初期表示しない項目を表す）
     */
    private static final String SELECT_OFF = "0";

    /**
     * 出庫フリーステーションクラス名
     */
    private static final String FREE_RETRIEVAL_STATION_CLASSNAME = FreeRetrievalStationOperator.class.getName();

    /**
     * pulldownKey：エリア
     */
    private static final String PULL_AREA_KEY = "AreaPulldown";

    /**
     * pulldownKey：作業場
     */
    private static final String PULL_WORK_PLACE_KEY = "WorkspacePulldown";

    /**
     * pulldownKey：ステーション
     */
    private static final String PULL_STATION_KEY = "StationPulldown";

    /**
     * pulldownKey：ハードゾーン
     */
    private static final String PULL_HARDZONE_KEY = "HardZonePulldown";

    /**
     * pulldownKey：ハードゾーン
     */
    private static final String PULL_SOFTZONE_KEY = "SoftZonePulldown";

    /**
     * pulldownKey：アイル
     */
    private static final String PULL_AISLE_KEY = "AislePulldown";

    /**
     * pulldownKey：バンク
     */
    private static final String PULL_BANK = "BankPulldown";

    /**
     * pulldownKey：引当パターン
     */
    private static final String PULL_ALLOC = "AllocatePriorityPulldown";

    /**
     * pulldownKey：号機No.
     */
    private static final String PULL_RFT_KEY = "AllocatePriorityPulldown";

    /**
     * pulldownKey：データタイプ
     */
    private static final String PULL_DATA_TYPE_KEY = "DateTypePulldown";

    /**
     * pulldownKey：リスト名称
     */
    private static final String PULL_LISTNAME_KEY = "ListNamePulldown";

    /**
     * 作業場に属さないステーションの倉庫ステーションNo. <BR>
     * keyにはステーションNo.をセットする。
     */
    private ArrayList<String> _noParentStationWH = new ArrayList<String>();

    /**
     * ソフトゾーンプルダウンにフリーソフトゾーンを追加するか
     */
    private boolean _freeSoftZoneDisp = false;

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 端末No.
     */
    private String _terminalNo = null;

    /**
     * <CODE>Locale</CODE>オブジェクト
     */
    @SuppressWarnings("all")
    private Locale _locale = null;

    /**
     * 検索したプルダウンデータ
     */
    private Map<String, ArrayList<String>> _pulldownDataTable = null;

    /**
     * 端末に紐づくステーション
     */
    private Map<String, Station[]> _stationTable = null;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス (ロギング,更新プログラムの保存用に使用されます)<br>
     * また、端末No.とLocaleを保持とプルダウンデータの初期化を行います。<br>
     *
     * @param conn
     *            データベースコネクション
     * @param terminalNo
     *            端末No.
     * @param locale
     *            Localeオブジェクト
     * @param caller
     *            呼び出し元クラス
     */
    public PulldownController(Connection conn, String terminalNo, Locale locale, Class caller)
    {
        super(conn, caller);

        _terminalNo = terminalNo;
        _locale = locale;
        _pulldownDataTable = new Hashtable<String, ArrayList<String>>();
        _stationTable = new Hashtable<String, Station[]>();
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------

    /**
     * エリアプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データ、「全エリア」の表示有無フラグを与えます。 <BR>
     * ・全エリア（AreaType.ALLを指定）<BR>
     * ・AS/RSエリアのみ表示（AreaType.ASRSを指定）<BR>
     * ・平置、移動ラックエリアのみ表示（AreaType.FLOORを指定）<BR>
     * ・棚管理エリアのみ表示（AreaType.FLOOR_LOCATEを指定）<BR>
     * ・平置、仮置きエリア(棚卸)用のみ表示（AreaType.INVENTORYを指定）<BR>
     * ・引当パターン用エリアのみ表示（AreaType.ALLOCATEPRIORITYを指定）<BR>
     * ・在庫用エリアのみ表示（AreaType.SOTCKを指定）<BR>
     * <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type
     *            プルダウンの種別を指定します。
     * @param stType
     *            エリアがAS/RSの場合の検索タイプを指定します
     * @param selected
     *            初期表示を行うエリアNo。指定しない場合は""を入力してください。
     * @param isAll
     *            「全エリア」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException
     * @throws InvalidDefineException
     */
    public String[] getAreaPulldown(AreaType type, StationType stType, String selected, boolean isAll)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AREA_KEY + type + String.valueOf(isAll);
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        Area[] areas = getArea(type, stType);

        // 該当データがなければ、nullを返す。
        if (areas == null || areas.length == 0)
        {
            return new String[0];
        }

        // 「全エリア」をArrayListに追加する
        if (isAll)
        {
            // 「全エリア」はリソースより取得する。
            String allArea = WmsParam.ALL_AREA_NO + ":" + DisplayText.getText("CMB-W0023");
            pulldownData.add(0, WmsParam.ALL_AREA_NO + PDDELIM + allArea + PDDELIM + WmsParam.ALL_AREA_NO + PDDELIM
                    + SELECT_OFF);
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (Area area : areas)
        {
            // Value値のセット
            value = area.getAreaNo();
            // Text値のセット
            text = area.getAreaNo() + ":" + area.getAreaName();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * エリアプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データ、「全エリア」の表示有無フラグを与えます。 <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うエリアNo。指定しない場合は""を入力してください。
     * @param isAll
     *            「全エリア」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getPart11StockHistoryPulldown(String selected, boolean isAll)
            throws CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AREA_KEY + String.valueOf(isAll);
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        Part11StockHistory[] part11StockHistorys = getPart11StockHistory();

        // 該当データがなければ、nullを返す。
        if (part11StockHistorys == null || part11StockHistorys.length == 0)
        {
            return new String[0];
        }

        // 「全エリア」をArrayListに追加する
        if (isAll)
        {
            // 「全エリア」はリソースより取得する。
            String allArea = WmsParam.ALL_AREA_NO + ":" + DisplayText.getText("CMB-W0023");
            pulldownData.add(0, WmsParam.ALL_AREA_NO + PDDELIM + allArea + PDDELIM + WmsParam.ALL_AREA_NO + PDDELIM
                    + SELECT_OFF);
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (Part11StockHistory part11StockHistory : part11StockHistorys)
        {
            // Value値のセット
            value = part11StockHistory.getAreaNo();
            // Text値のセット
            text = part11StockHistory.getAreaNo() + ":" + part11StockHistory.getAreaName();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * エリアプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データ、「全エリア」の表示有無フラグを与えます。 <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うエリアNo。指定しない場合は""を入力してください。
     * @param isAll
     *            「全エリア」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getPart11StockHistImpPulldown(String selected, boolean isAll)
            throws CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AREA_KEY + String.valueOf(isAll);
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        Part11StockHistImp[] part11StockHistImps = getPart11StockHistImp();

        // 該当データがなければ、nullを返す。
        if (part11StockHistImps == null || part11StockHistImps.length == 0)
        {
            return new String[0];
        }

        // 「全エリア」をArrayListに追加する
        if (isAll)
        {
            // 「全エリア」はリソースより取得する。
            String allArea = WmsParam.ALL_AREA_NO + ":" + DisplayText.getText("CMB-W0023");
            pulldownData.add(0, WmsParam.ALL_AREA_NO + PDDELIM + allArea + PDDELIM + WmsParam.ALL_AREA_NO + PDDELIM
                    + SELECT_OFF);
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (Part11StockHistImp part11StockHistImp : part11StockHistImps)
        {
            // Value値のセット
            value = part11StockHistImp.getAreaNo();
            // Text値のセット
            text = part11StockHistImp.getAreaNo() + ":" + part11StockHistImp.getAreaName();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * 作業場プルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データを与えます。 <BR>
     * ・入庫用（StationType.STORAGEを指定）<BR>
     * ・出庫用（StationType.RETRIEVALを指定）<BR>
     * ・積増入庫用（StationType.ADD_STORAGEを指定）<BR>
     * ・在庫確認用（StationType.INVENTORY_CHECKを指定）<BR>
     * ・作業表示用（StationType.OPERATION_DISPLAYを指定）<BR>
     * ・作業メンテナンス用（StationType.WORK_MNTを指定）<BR>
     * ・全ステーション(StationType.ALLを指定)<BR>
     * <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type
     *            プルダウンの種別を指定します。
     * @param selected
     *            初期表示を行う作業場。指定しない場合は""を入力してください。
     * @param firstChar
     *            自動振り分けまたは全ステーション。UNUSEの場合は、作業場のみ表示します。
     * @param parentHasAll
     * 			  親エリアの表示に全てがある場合true。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getWorkplacePulldown(StationType type, String selected, Distribution firstChar, Boolean parentHasAll)
            throws CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        String key = PULL_WORK_PLACE_KEY + type;
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // 作業場を取得します
        Station[] workPlaces = getWorkSpace(type);

        // 該当データが無い場合。
        if (workPlaces == null || workPlaces.length == 0 && _noParentStationWH.isEmpty())
        {
        	// 平置を表示する場合
        	if (StationType.FLOORANDSTORAGE.equals(type) || StationType.FLOORANDRETRIEVAL.equals(type)
            		|| StationType.ITEM_RETRIEVAL.equals(type))
            {
                // 平置エリアのエリアプルダウン連携
                Area[] areas = getArea(AreaType.FLOOR_AND_TEMP_AND_RECEIVE, null);

                // 平置もない場合は終わり。
                if (areas == null || areas.length == 0)
                {
                	return new String[0];
                }

            	ArrayList<String> pulldownData = new ArrayList<String>();
                // 親のエリアプルダウンに全てがあるとき
                if (parentHasAll)
                {
                    String areano = WmsParam.ALL_AREA_NO;
                    // 「全体作業場」をArrayListに追加する
                    value = InParameter.SELECT_STATION_NONE + areano;
                    text = DisplayText.getText("CMB-W0069");;
                    forignKey = areano;
                    pulldownData.add(0, value + PDDELIM + text + PDDELIM + areano + PDDELIM + SELECT_OFF);
                }

                for (Area area : areas)
                {
                    value = InParameter.SELECT_STATION_NONE + area.getAreaNo();
                    text = DisplayText.getText("CMB-W0069");
                    forignKey = area.getAreaNo();

                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
                // プルダウンデータを保持する
                _pulldownDataTable.put(key, pulldownData);

                return changeToSelected(pulldownData, seldata, "");
            }
            return new String[0];
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        // 親のエリアプルダウンに全てがあるとき
        if (parentHasAll)
        {
            String areano = WmsParam.ALL_AREA_NO;
            // 「全体作業場」をArrayListに追加する
            value = WmsParam.ALL_STATION + areano;
            // CMB-W0040=全体作業場
            text = WmsParam.ALL_STATION + ":" + DisplayText.getText("CMB-W0040");
            forignKey = areano;
            pulldownData.add(0, value + PDDELIM + text + PDDELIM + areano + PDDELIM + SELECT_OFF);

            // DBから取得した作業場をArrayListに追加する。
            for (Station station : workPlaces)
            {
                value = station.getStationNo() + areano;
                text = station.getStationNo() + ":" + station.getStationName();
                forignKey = areano;
                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }

        // 自動振分、全体作業場をArrayListに追加する。
        if (firstChar.equals(Distribution.ALL) || firstChar.equals(Distribution.AUTO))
        {
            if (type.equals(StationType.WORK_MNT))
            {
                // 作業メンテはエリアの指定がないため、１度だけ追加する
                // 「全体作業場」をArrayListに追加する
                if (firstChar.equals(Distribution.ALL))
                {
                    value = WmsParam.ALL_STATION;
                    // CMB-W0040=全体作業場
                    text = WmsParam.ALL_STATION + ":" + DisplayText.getText("CMB-W0040");
                    forignKey = "";
                    pulldownData.add(0, value + PDDELIM + text + PDDELIM + value + PDDELIM + SELECT_OFF);
                }
            }
            else
            {
                // エリアごとに追加する
                List<String> areaList = new ArrayList<String>();
                for (Station wp : workPlaces)
                {
                    String areano = (String)wp.getValue(Area.AREA_NO, "");

                    if (areaList.contains(areano))
                    {
                        // 登録済みのエリアの場合はスキップ
                        continue;
                    }
                    else
                    {
                        // 「全体作業場」をArrayListに追加する
                        if (firstChar.equals(Distribution.ALL))
                        {
                            value = WmsParam.ALL_STATION + areano;
                            // CMB-W0040=全体作業場
                            text = WmsParam.ALL_STATION + ":" + DisplayText.getText("CMB-W0040");
                            forignKey = areano;
                            pulldownData.add(0, value + PDDELIM + text + PDDELIM + areano + PDDELIM + SELECT_OFF);
                        }
                        else if (firstChar.equals(Distribution.AUTO))
                        {
                            value = WmsParam.AUTO_SELECT_STATION + areano;
                            // CMB-W0024=自動振分け
                            text = DisplayText.getText("CMB-W0024");
                            forignKey = areano;
                            pulldownData.add(0, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                        }

                        // 登録済みエリアとして登録
                        areaList.add(areano);

                    }
                }
            }
        }

        // DBから取得した作業場をArrayListに追加する。
        for (Station station : workPlaces)
        {
            value = station.getStationNo();
            text = station.getStationNo() + ":" + station.getStationName();
            forignKey = (String)station.getValue(Area.AREA_NO);
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // 作業場に属さないステーションがあった場合 登録作業場なしを追加する。
        // また、作業メンテの場合はエリアがないので登録作業場なしは追加しない。
        if (!_noParentStationWH.isEmpty() && !StationType.WORK_MNT.equals(type))
        {
            AreaHandler ah = new AreaHandler(getConnection());
            AreaSearchKey akey = new AreaSearchKey();
            // set collect
            akey.setAreaNoCollect();
            akey.setWhstationNoCollect();
            // set where
            akey.setWhstationNo(_noParentStationWH.toArray(new String[_noParentStationWH.size()]), true);
            Area[] ar = (Area[])ah.find(akey);

            // CMB-W0051=登録作業場なし
            text = DisplayText.getText("CMB-W0051");
            for (Area a : ar)
            {
                value = WmsParam.NOPARENT_STATION_WPNO + a.getWhstationNo();
                forignKey = a.getAreaNo();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }

        if (StationType.FLOORANDSTORAGE.equals(type) || StationType.FLOORANDRETRIEVAL.equals(type)
        		|| StationType.ITEM_RETRIEVAL.equals(type))
        {
            // 平置エリアのエリアプルダウン連携
            Area[] areas = getArea(AreaType.FLOOR_AND_TEMP_AND_RECEIVE, null);

            for (Area area : areas)
            {
                value = InParameter.SELECT_STATION_NONE + area.getAreaNo();
                text = DisplayText.getText("CMB-W0069");
                forignKey = area.getAreaNo();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * ステーションプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データを与えます。 <BR>
     * ・入庫用（StationType.STORAGEを指定）<BR>
     * ・出庫用（StationType.RETRIEVALを指定）<BR>
     * ・積増入庫用（StationType.ADD_STORAGEを指定）<BR>
     * ・在庫確認用（StationType.INVENTORY_CHECKを指定）<BR>
     * ・作業表示用（SStationType.OPERATION_DISPLAYを指定）<BR>
     * ・作業メンテナンス用（StationType.WORK_MNTを指定）<BR>
     * ・全ステーション(StationType.ALLを指定)<BR>
     * <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type
     *            プルダウンの種別を指定します。
     * @param selected
     *            初期表示を行うステーションNo。指定しない場合は""を入力してください。
     * @param firstChar
     *            自動振り分けまたは全ステーション。UNUSEの場合は、ステーションのみ表示します。
     * @param parentChar
     *            親ステーションの表示に全てや自動振り分けを指定した場合に指定する。
     * @param workplace
     *            作業場を元にステーションを取得するときに指定する。(直行設定用)
     * @param parentAreahasAll
     * 			  親エリア
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getStationPulldown(StationType type, String selected, Distribution firstChar,
            Distribution parentChar, String workplace, Boolean parentAreahasAll)
            throws CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_STATION_KEY + type;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        // 対象となるステーションを取得する
        Station[] stations = null;

        if (workplace != null && StationType.DIRECT_TRANSFER_FROM.equals(type))
        {
            // 作業場を元に搬送元ステーションを取得する
            stations = getStationByWorkPlace(type, workplace);
        }
        else
        {
            stations = getStation(type);
        }

        // 該当データがない場合
        if (stations == null || stations.length == 0)
        {
        	// 平置を表示する場合
            if (StationType.FLOORANDSTORAGE.equals(type) || StationType.FLOORANDRETRIEVAL.equals(type)
            		|| StationType.ITEM_RETRIEVAL.equals(type))
            {
                // 平置エリアのエリアプルダウン連携
                Area[] areas = getArea(AreaType.FLOOR_AND_TEMP_AND_RECEIVE, null);

                // 平置エリアも無ければ終わり。
                if (areas == null || areas.length == 0)
                {
                	return new String[0];
                }

                // 対象ステーションを作業場ごとに登録する
                ArrayList<String> pulldownData = new ArrayList<String>();

                // Value値
                String value = "";
                // Text値「なし」
                String text = DisplayText.getText("CMB-W0069");
                // ForignKey値
                String forignKey = "";

                // 親エリアに全てがあるとき
                if (parentAreahasAll)
                {
                    String areaNo = WmsParam.ALL_AREA_NO;

                    value = InParameter.SELECT_STATION_NONE + areaNo;
                    forignKey = InParameter.SELECT_STATION_NONE + areaNo;
                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);

                }

                for (Area area : areas)
                {
                    value = InParameter.SELECT_STATION_NONE + area.getAreaNo();
                    forignKey = InParameter.SELECT_STATION_NONE + area.getAreaNo();

                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
                // プルダウンデータを保持する
                _pulldownDataTable.put(key, pulldownData);

                return changeToSelected(pulldownData, seldata, "");
            }

            return new String[0];
        }

        // 作業場検索が必要
        boolean needWorkPlace = true;
        if (StationType.OPERATION_DISPLAY.equals(type) || StationType.RESTORING_MNT.equals(type))
        {
            needWorkPlace = false;
        }

        // 対象ステーションを作業場ごとに登録する
        ArrayList<String> pulldownData = new ArrayList<String>();

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        ArrayList<String> parentArray = new ArrayList<String>();
        // DBから取得したステーションをArrayListに追加する。
        for (Station station : stations)
        {
            if (StationType.DIRECT_TRANSFER_FROM.equals(type))
            {
                // 直行搬送元の場合、親プルダウンなしのためforignKeyはセットしない。
                value = station.getStationNo();
                text = value + ":" + station.getStationName();

                pulldownData.add(value + PDDELIM + text + PDDELIM + SELECT_OFF);
            }
            else if (StationType.DIRECT_TRANSFER_TO.equals(type))
            {
                Station[] from_stations = getStation(StationType.DIRECT_TRANSFER_FROM);

                for (Station from_station : from_stations)
                {
                    // From-Toで直行できるなら追加
                    if (isDirectTransferRoute(from_station, station))
                    {
                        // 直行搬送先の場合、親キーに搬送元ステーションをセット。
                        value = station.getStationNo();
                        text = value + ":" + station.getStationName();
                        forignKey = from_station.getStationNo();

                        pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                    }
                }

            }
            else
            {
                String[] workplaces = getParentStationNo(station.getStationNo());

                // 親となる作業場が１件もない場合、または、親作業場を検索する必要がない場合は
                // 登録作業場なしのforignKeyを設定する
                if ((workplaces == null || workplaces.length == 0) || !needWorkPlace)
                {
                    value = station.getStationNo();
                    text = value + ":" + station.getStationName();
                    forignKey = WmsParam.NOPARENT_STATION_WPNO + station.getWhStationNo();

                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
                else
                {
                    for (String wp : workplaces)
                    {
                        // 全ステーションから作業場を一意に抽出する(順不同)
                        // 自動振り分けの場合に使用する
                        if (!parentArray.contains(wp))
                        {
                            parentArray.add(wp);
                        }

                        value = station.getStationNo();
                        text = value + ":" + station.getStationName();
                        forignKey = wp;

                        pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                    }
                }
            }
        }

        // 親エリアに全てがあるとき
        if (parentAreahasAll)
        {
            String areaNo = WmsParam.ALL_AREA_NO;

            value = WmsParam.ALL_STATION;
            forignKey = WmsParam.ALL_STATION + areaNo;
            // CMB-W0028=全ステーション
            text = WmsParam.ALL_STATION + ":" + DisplayText.getText("CMB-W0028");
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);

            for (Station station : stations)
            {
                value = station.getStationNo();
                text = value + ":" + station.getStationName();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }

            for (Station station : stations)
            {


                String[] workplaces = getParentStationNo(station.getStationNo());
                // 親となる作業場が１件もない場合、または、親作業場を検索する必要がない場合は
                // 登録作業場なしのforignKeyを設定する
                if ((workplaces == null || workplaces.length == 0) || !needWorkPlace)
                {
                    value = station.getStationNo();
                    text = value + ":" + station.getStationName();
                    forignKey = WmsParam.NOPARENT_STATION_WPNO + station.getWhStationNo();

                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
                else
                {
                    for (String wp : workplaces)
                    {
                        // 全ステーションから作業場を一意に抽出する(順不同)
                        // 自動振り分けの場合に使用する
                        if (!parentArray.contains(wp))
                        {
                            parentArray.add(wp);
                        }
                        value = station.getStationNo();
                        text = value + ":" + station.getStationName();
                        forignKey = wp + areaNo;

                        pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                    }
                }

            }
        }

        // 親の作業場に「全体作業場」「自動振分」が登録されていた場合、
        // 紐づくステーションをリストに追加する
        if (type.equals(StationType.WORK_MNT))
        {
            // 作業メンテはエリア表示がないため処理を別にする
            forignKey = WmsParam.ALL_STATION;
            value = WmsParam.ALL_STATION;
            // CMB-W0028=全ステーション
            text = value + ":" + DisplayText.getText("CMB-W0028");

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);

            for (Station station : stations)
            {
                value = station.getStationNo();
                text = value + ":" + station.getStationName();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }
        else if (parentChar.equals(Distribution.ALL) || parentChar.equals(Distribution.AUTO))
        {
            // エリアごとに「全体作業場」が登録されるので、エリア単位で処理を行う
            // 親（作業場）が、「全体作業場」の場合は、valueに9999をセットする
            Area[] areas = getArea(AreaType.ASRS, type);
            for (Area area : areas)
            {
                if (parentChar.equals(Distribution.ALL))
                {
                    value = WmsParam.ALL_STATION;
                    forignKey = WmsParam.ALL_STATION + area.getAreaNo();
                    // CMB-W0028=全ステーション
                    text = WmsParam.ALL_STATION + ":" + DisplayText.getText("CMB-W0028");
                }
                else
                {
                    value = WmsParam.AUTO_SELECT_STATION;
                    forignKey = WmsParam.AUTO_SELECT_STATION + area.getAreaNo();
                    // CMB-W0024=自動振分け
                    text = DisplayText.getText("CMB-W0024");
                }
                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);

                for (Station station : stations)
                {
                    if (!station.getWhStationNo().equals(area.getWhstationNo()))
                    {
                        continue;
                    }
                    value = station.getStationNo();
                    text = value + ":" + station.getStationName();

                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
            }
        }

        // 作業場ごとに、自動振り分け、または全ステーションの項目を追加する
        if (firstChar.equals(Distribution.AUTO) || firstChar.equals(Distribution.ALL))
        {
            if (StationType.RESTORING_MNT.equals(type))
            {
                text = DisplayText.getText("CMB-W0028");
                value = WmsParam.ALL_STATION;
                forignKey = "";

                // 必ず先頭に表示されるよう、0番目に登録する。
                pulldownData.add(0, (value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF));
            }
            else if (parentArray.size() > 0)
            {
                String txtname = "";
                if (firstChar.equals(Distribution.AUTO))
                {
                    // CMB-W0024=自動振分け
                    txtname = DisplayText.getText("CMB-W0024");
                }
                else
                {
                    // CMB-W0028=全ステーション
                    txtname = DisplayText.getText("CMB-W0028");
                }

                for (int i = 0; i < parentArray.size(); i++)
                {
                    if (StationType.WORK_MNT.equals(type))
                    {
                        value = WmsParam.ALL_STATION;
                    }
                    else
                    {
                        value = parentArray.get(i);
                    }
                    if (firstChar.equals(Distribution.ALL))
                    {
                        text = WmsParam.ALL_STATION + ":" + txtname;
                    }
                    else
                    {
                        text = txtname;
                    }
                    forignKey = parentArray.get(i);

                    // 必ず先頭に表示されるよう、0番目に登録する。
                    pulldownData.add(0, (value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF));

                    //親エリアに「全て」があるとき
                    if (parentAreahasAll)
                    {
                        pulldownData.add(0, (value + PDDELIM + text + PDDELIM + forignKey + WmsParam.ALL_AREA_NO
                                + PDDELIM + SELECT_OFF));
                    }
                }
            }
        }

        if (StationType.FLOORANDSTORAGE.equals(type) || StationType.FLOORANDRETRIEVAL.equals(type)
        		|| StationType.ITEM_RETRIEVAL.equals(type))
        {
            // 平置エリアのエリアプルダウン連携
            Area[] areas = getArea(AreaType.FLOOR_AND_TEMP_AND_RECEIVE, null);

            for (Area area : areas)
            {
                value = InParameter.SELECT_STATION_NONE + area.getAreaNo();
                text = DisplayText.getText("CMB-W0069");
                forignKey = InParameter.SELECT_STATION_NONE + area.getAreaNo();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * ハードゾーンプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うハードゾーン。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getHardZonePulldown(String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_HARDZONE_KEY;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        HardZone[] hardzones = getHardZone();

        // 該当データがなければ、nullを返す。
        if (hardzones == null || hardzones.length == 0)
        {
            return new String[0];
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // 作業場をArrayListに追加する。
        for (HardZone hardzone : hardzones)
        {
            value = hardzone.getHardZoneId();
            text = hardzone.getHardZoneId();
            forignKey = (String)hardzone.getValue(Area.AREA_NO);

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * ソフトゾーンプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     *
     * @param selected
     *            初期表示を行うハードゾーン。指定しない場合は""を入力してください。
     * @param softZoneType ソフトゾーン検索条件。<BR>
     * マスタに登録されているソフトゾーンもしくは、該当エリアのソフトゾーンプルダウンを取得します。
     * @param itemCode 商品コード。これを指定された場合、該当商品のソフトゾーンプルダウンを取得します。
     * @param freeZone FreeSoftZoneを表示するかどうか
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException
     * @throws InvalidDefineException
     */
    public String[] getSoftZonePulldown(String selected, SoftZoneType softZoneType, List<String> itemCode, boolean freeZone)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_SOFTZONE_KEY;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        String[] item = null;
        if (itemCode != null && !itemCode.isEmpty())
        {
            item = new String[itemCode.size()];
            itemCode.toArray(item);
        }
        SoftZone[] softzones = getSoftZone(softZoneType, item);

        // 該当データがなければ、nullを返す。
        if ((softzones == null || softzones.length == 0) && !_freeSoftZoneDisp)
        {
            return new String[0];
        }

        // プルダウン生成時にフリーソフトゾーンが不要と指定された場合はfalseを設定
        if (!freeZone)
        {
            _freeSoftZoneDisp = freeZone;
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // フリーソフトゾーンの追加
        if (_freeSoftZoneDisp)
        {
            // フリーソフトゾーン
            String freezone_name = getFreeSoftZoneName();

            if (freezone_name != null)
            {
                value = SystemDefine.SOFT_ZONE_ALL;
                text = freezone_name;

                if (SoftZoneType.MASTER.equals(softZoneType))
                {
                    pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                }
                else
                {
                    // エリア分をセット
                    Area[] areas = getArea(AreaType.ASRS, StationType.ALL);
                    for (Area area : areas)
                    {
                        forignKey = (String)area.getValue(Area.AREA_NO);

                        pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
                    }
                }
            }
        }

        // ArrayListに追加する。
        for (SoftZone softzone : softzones)
        {
            value = softzone.getSoftZoneId();
            text = softzone.getSoftZoneName();
            if (SoftZoneType.AREA.equals(softZoneType))
            {
                forignKey = (String)softzone.getValue(Area.AREA_NO);
            }

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * ハードゾーンプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うハードゾーン。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getReasonPulldown(String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AREA_KEY;
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        Reason[] reasons = getReason();

        // 該当データがなければ、nullを返す。
        if (reasons == null || reasons.length == 0)
        {
            return new String[0];
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (Reason reason : reasons)
        {
            // Value値のセット
            value = String.valueOf(reason.getReasonType());
            // Text値のセット
            text = String.valueOf(reason.getReasonType()) + ":" + reason.getReasonName();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * 作業理由プルダウンを表示するためのデータを<code>PullDownItem</code>の配列にて返します。<BR>
     *
     * @return <code>PullDownItem</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public PullDownItem[] getReasonPulldownItem()
            throws ReadWriteException
    {
        Reason[] reasons = getReason();

        // 該当データがなければ、nullを返す。
        if (reasons == null || reasons.length == 0)
        {
            return new PullDownItem[0];
        }

        // Value値
        String value = "";
        // Text値
        String text = "";

        ArrayList<PullDownItem> pulldownData = new ArrayList<PullDownItem>();

        // エリアをArrayListに追加する。
        for (Reason reason : reasons)
        {
            PullDownItem tmp = new PullDownItem();

            // Value値のセット
            value = String.valueOf(reason.getReasonType());
            // Text値のセット
            text = String.valueOf(reason.getReasonType()) + ":" + reason.getReasonName();

            tmp.setValue(value);
            tmp.setText(text);
            pulldownData.add(tmp);

        }

        return pulldownData.toArray(new PullDownItem[pulldownData.size()]);

    }

    /**
     * アイルプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データ、複数倉庫の場合の選択データ、「全RM」の表示有無フラグを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うエリアNo。指定しない場合は""を入力してください。
     * @param parent
     *            複数倉庫の場合に選択された倉庫ステーションNo.を指定します。倉庫が１つの場合は""をセットします。
     * @param isAll
     *            「全RM」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getAislePulldown(String selected, String parent, boolean isAll)
            throws ReadWriteException
    {
        String seldata = "";
        String parentdata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }
        // parentがnullでない場合は、parentdataにセットする。
        if (parent != null)
        {
            parentdata = parent;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AISLE_KEY + String.valueOf(isAll);
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, parentdata);
        }
        // プルダウンデータが検索済みかをチェックここまで

        ArrayList<String> pulldownData = new ArrayList<String>();

        Aisle[] aisleArray = getAisle();

        // 該当データがなければ、nullを返す。
        if (aisleArray == null || aisleArray.length == 0)
        {
            return new String[0];
        }

        // 全RM挿入位置検索用
        String areaNo = "";
        String allText = DisplayText.getText("CMB-W0025");

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";
        // CMB-W0026=RM-
        String txtname = DisplayText.getText("CMB-W0026");

        // ArrayListに追加する。
        for (Aisle aisle : aisleArray)
        {
            value = String.valueOf(aisle.getAisleNo());
            text = txtname + String.valueOf(aisle.getAisleNo());
            forignKey = (String)aisle.getValue(Area.AREA_NO);

            if (isAll)
            {
                // 全RMの挿入
                if (!areaNo.equals(forignKey))
                {
                    pulldownData.add(0, WmsParam.ALL_AISLE_NO + PDDELIM + allText + PDDELIM + forignKey + PDDELIM
                            + SELECT_OFF);

                    // 前回のエリアNo.と違う場合、そのindexを覚えておく
                    areaNo = forignKey;
                }
            }

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, selected, parentdata);
    }

    /**
     * バンクプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データを与えます。 <BR>
     * ・平置、移動ラックエリア（BankType.FLOORを指定）<BR>
     * ・AS/RSエリア（BankType.ASRSを指定）<BR>
     * <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type
     *            プルダウンの種別を指定します。
     * @param selected
     *            初期表示を行う作業場。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getBankPulldown(BankType type, String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        String key = "";
        // HashTableのキーを作成
        key = PULL_BANK + type;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        ArrayList<String> pulldownData = new ArrayList<String>();

        Entity[] ents = getBank(type);

        // 該当データがなければ、nullを返す。
        if (ents == null || ents.length == 0)
        {
            return new String[0];
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // CMB-W0027=BANK
        String txtname = DisplayText.getText("CMB-W0027");

        // 平置、移動ラックエリアの場合
        if (type.equals(BankType.FLOOR))
        {
            // バンクをArrayListに追加する。
            for (Locate locate : (Locate[])ents)
            {
                value = String.valueOf(locate.getBankNo());
                text = txtname + locate.getBankNo();
                forignKey = locate.getAreaNo();

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }
        // それ以外の場合
        else
        {
            // バンクをArrayListに追加する。
            for (Shelf shelf : (Shelf[])ents)
            {
                value = String.valueOf(shelf.getBankNo());
                text = txtname + shelf.getBankNo();
                forignKey = (String)shelf.getValue(Area.AREA_NO);

                pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, selected, "");
    }

    /**
     * 引当パターンプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データを与えます。 <BR>
     * ・通常(出庫)用（AllocateType.NORMALを指定）<BR>
     * ・補充用（AllocateType.REPLENISHMENTを指定）<BR>
     * <BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type
     *            プルダウンの種別を指定します。
     * @param selected
     *            初期表示を行う作業場。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getAllocatePriorityPulldown(AllocateType type, String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_ALLOC + type;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        ArrayList<String> pulldownData = new ArrayList<String>();

        AllocatePriority[] patterns = getAllocatePriority(type);

        // 該当データがなければ、nullを返す。
        if (patterns == null || patterns.length == 0)
        {
            return new String[0];
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // 引当パターンをArrayListに追加する。
        for (AllocatePriority pattern : patterns)
        {
            value = pattern.getAllocateNo();
            text = value + ":" + pattern.getAllocateName();
            forignKey = pattern.getAllocateNo();

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, selected, "");
    }

    /**
     * 作業内容プルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データ、「全て」の表示有無フラグを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行う作業内容。指定しない場合は""を入力してください。
     * @param isAll
     *            「全て」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getWorkContentsPulldown(String selected, boolean isAll)
            throws ReadWriteException,
                CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AISLE_KEY + String.valueOf(isAll);
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェック

        ArrayList<String> pulldownData = new ArrayList<String>();

        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";
        // 表示順序
        int _point = 0;

        // 全て表示要時
        if (isAll)
        {
            value = InParameter.SEARCH_ALL;
            text = DisplayResource.getJobType(InParameter.SEARCH_ALL);
            forignKey = InParameter.SEARCH_ALL;
            pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            _point++;
        }

        // ArrayListに追加する。
        if (sysController.hasReceivingPack() || sysController.hasCrossdockPack())
        {
            // 01:入荷
            value = SystemDefine.JOB_TYPE_RECEIVING;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_RECEIVING);
            forignKey = SystemDefine.JOB_TYPE_RECEIVING;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasStoragePack())
        {
            // 02:入庫
            value = SystemDefine.JOB_TYPE_STORAGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_STORAGE);
            forignKey = SystemDefine.JOB_TYPE_STORAGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasRetrievalPack())
        {
            // 03:出庫
            value = SystemDefine.JOB_TYPE_RETRIEVAL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
            forignKey = SystemDefine.JOB_TYPE_RETRIEVAL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasShippingPack() || sysController.hasCrossdockPack())
        {
            // 05:出荷
            value = SystemDefine.JOB_TYPE_SHIPPING;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_SHIPPING);
            forignKey = SystemDefine.JOB_TYPE_SHIPPING;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasStockPack())
        {
            // 12:移動入庫、13:移動出庫、22:予定外入庫、23:予定外出庫
            value = SystemDefine.JOB_TYPE_MOVEMENT_STORAGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE);
            forignKey = SystemDefine.JOB_TYPE_MOVEMENT_STORAGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL);
            forignKey = SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_NOPLAN_STORAGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_NOPLAN_STORAGE);
            forignKey = SystemDefine.JOB_TYPE_NOPLAN_STORAGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL);
            forignKey = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasStoragePack())
        {
            // 40:棚卸作業
            value = SystemDefine.JOB_TYPE_INVENTORY;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_INVENTORY);
            forignKey = SystemDefine.JOB_TYPE_INVENTORY;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, selected, "");
    }

    /**
     * 作業区分プルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データ、「全て」の表示有無フラグを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected 初期表示を行う作業内容。指定しない場合は""を入力してください。
     * @param isAll 「全て」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getWorkFlagPulldown(String selected, boolean isAll)
            throws ReadWriteException,
                CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_AISLE_KEY + String.valueOf(isAll);
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        ArrayList<String> pulldownData = new ArrayList<String>();

        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";
        // 表示順序
        int _point = 0;

        // 全て表示要時
        if (isAll)
        {
            value = InParameter.SEARCH_ALL;
            text = DisplayResource.getJobType(InParameter.SEARCH_ALL);
            forignKey = InParameter.SEARCH_ALL;
            pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            _point++;
        }

        // ArrayListに追加する。
        if (sysController.hasReceivingPack())
        {
            value = SystemDefine.JOB_TYPE_RECEIVING;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_RECEIVING);
            forignKey = SystemDefine.JOB_TYPE_RECEIVING;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasStoragePack())
        {
            value = SystemDefine.JOB_TYPE_STORAGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_STORAGE);
            forignKey = SystemDefine.JOB_TYPE_STORAGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasRetrievalPack())
        {
            value = SystemDefine.JOB_TYPE_RETRIEVAL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
            forignKey = SystemDefine.JOB_TYPE_RETRIEVAL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }
        if (sysController.hasStockPack())
        {
            value = SystemDefine.JOB_TYPE_MOVEMENT;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_MOVEMENT);
            forignKey = SystemDefine.JOB_TYPE_MOVEMENT;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_NOPLAN_STORAGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_NOPLAN_STORAGE);
            forignKey = SystemDefine.JOB_TYPE_NOPLAN_STORAGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL);
            forignKey = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_MAINTENANCE_PLUS;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS);
            forignKey = SystemDefine.JOB_TYPE_MAINTENANCE_PLUS;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_MAINTENANCE_MINUS;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS);
            forignKey = SystemDefine.JOB_TYPE_MAINTENANCE_MINUS;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

        }
        if (sysController.hasStoragePack())
        {
            value = SystemDefine.JOB_TYPE_INVENTORY_PLUS;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_INVENTORY_PLUS);
            forignKey = SystemDefine.JOB_TYPE_INVENTORY_PLUS;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_INVENTORY_MINUS;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_INVENTORY_MINUS);
            forignKey = SystemDefine.JOB_TYPE_INVENTORY_MINUS;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }

        if (sysController.hasAsrsPack())
        {
            value = SystemDefine.JOB_TYPE_DIRECT_TRAVEL;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_DIRECT_TRAVEL);
            forignKey = SystemDefine.JOB_TYPE_DIRECT_TRAVEL;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_ASRS_RACK_TO_RACK;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_ASRS_RACK_TO_RACK);
            forignKey = SystemDefine.JOB_TYPE_ASRS_RACK_TO_RACK;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_ASRS_REARRANGE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_ASRS_REARRANGE);
            forignKey = SystemDefine.JOB_TYPE_ASRS_REARRANGE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_ASRS_EXPENDITURE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_ASRS_EXPENDITURE);
            forignKey = SystemDefine.JOB_TYPE_ASRS_EXPENDITURE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;

            value = SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE;
            text = DisplayResource.getJobType(SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE);
            forignKey = SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE;
            if (_point == 0)
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);
            }
            else
            {
                pulldownData.add(_point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
            }
            _point++;
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, selected, "");
    }

    /**
     * RFT号機No.を表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 端末区分、「全HT」または「全Pカート」の表示有無フラグを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行うエリアNo。指定しない場合は""を入力してください。
     * @param terminalType
     *            表示する号機No.端末区分
     * @param isAll
     *            「全HT」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getRFTNo(String selected, TerminalType terminalType, boolean isAll)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_RFT_KEY + String.valueOf(isAll);
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        String allRft = null;
        String searchTerminalType = null;
        ArrayList<String> pulldownData = new ArrayList<String>();

        // 「全HT」
        if (terminalType.equals(TerminalType.HT))
        {
            if (isAll)
            {
                // 「全HT」はリソースより取得する。
                allRft = DisplayText.getText("CMB-W0053");
                pulldownData.add(0, WmsParam.ALL_RFT_NO + PDDELIM + allRft + PDDELIM + WmsParam.ALL_RFT_NO + PDDELIM
                        + SELECT_OFF);
            }
            searchTerminalType = SystemDefine.TERMINAL_TYPE_HT;
        }
        // 「全Pカート」
        else if (terminalType.equals(TerminalType.PCART))
        {
            if (isAll)
            {
                // 「全Pカート」はリソースより取得する。
                allRft = DisplayText.getText("CMB-W0054");
                pulldownData.add(0, WmsParam.ALL_RFT_NO + PDDELIM + allRft + PDDELIM + WmsParam.ALL_RFT_NO + PDDELIM
                        + SELECT_OFF);
            }
            searchTerminalType = SystemDefine.TERMINAL_TYPE_PCART;
        }

        RftHandler rftHndl = new RftHandler(getConnection());
        RftSearchKey rftSrky = new RftSearchKey();

        // 検索条件
        // 端末区分
        rftSrky.setTerminalType(searchTerminalType);

        // 取得項目
        // 号機No.
        rftSrky.setRftNoCollect();

        // 表示順序
        // 号機No. (昇順)
        rftSrky.setRftNoOrder(true);

        Rft[] rftEntitys = (Rft[])rftHndl.find(rftSrky);

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (Rft rftEntity : rftEntitys)
        {
            // Value値のセット
            value = rftEntity.getRftNo();
            // Text値のセット
            text = rftEntity.getRftNo();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");

    }

    /**
     * RFT号機No.を表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 端末区分、「全HT」または「全Pカート」の表示有無フラグを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected
     *            初期表示を行う端末区分。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getTerminalType(String selected)
            throws ReadWriteException,
                CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_RFT_KEY;
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();


        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";
        // 表示順序
        int point = 0;

        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // HT
        value = SystemDefine.TERMINAL_TYPE_HT;
        text = DisplayResource.getTerminalType(SystemDefine.TERMINAL_TYPE_HT);
        forignKey = SystemDefine.TERMINAL_TYPE_HT;

        pulldownData.add(point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);

        // Pカートパッケージ導入時のみ
        // TODO
        if (sysController.hasPCTMasterPack() || sysController.hasPCTReceivingPack()
                || sysController.hasPCTRetrievalPack() || sysController.hasPCTInventoryPack())
        {
            // Pカート
            value = SystemDefine.TERMINAL_TYPE_PCART;
            text = DisplayResource.getTerminalType(SystemDefine.TERMINAL_TYPE_PCART);
            forignKey = SystemDefine.TERMINAL_TYPE_PCART;
            pulldownData.add(point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);

            // 画像登録用HT
            value = SystemDefine.TERMINAL_TYPE_CAMERA_HT;
            text = DisplayResource.getTerminalType(SystemDefine.TERMINAL_TYPE_CAMERA_HT);
            forignKey = SystemDefine.TERMINAL_TYPE_CAMERA_HT;
            pulldownData.add(point, value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");

    }

    /**
     * 送受信のデータタイプを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param type 送受信検索条件
     * @param selected
     *            初期表示を行う。送信、受信のラジオボタン切り替えを行う。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getDataTypePulldown(DataType type, String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_DATA_TYPE_KEY;
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        ExchangeEnvironment[] environments = getExchangeEnvironment(type);

        // 該当データがなければ、nullを返す。
        if (environments == null || environments.length == 0)
        {
            return new String[0];
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        value = InParameter.SEARCH_ALL;
        text = DisplayResource.getDataType("", "");
        forignKey = InParameter.SEARCH_ALL;
        pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_ON);

        // データタイプをArrayListに追加する。
        for (ExchangeEnvironment environment : environments)
        {
            // Value値のセット
            value = String.valueOf(environment.getJobType());
            // Text値のセット
            text = String.valueOf(environment.getDataName());
            if (StringUtil.isBlank(text))
            {
                continue;
            }

            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * リスト名称プルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データを与えます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     *
     * @param selected 初期表示を行うハードゾーン。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getListNamePulldown(String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_LISTNAME_KEY;
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        PrintHistory[] printHistories = getPrintHistory();

        // 該当データがなければ、nullを返す。
        if (printHistories == null || printHistories.length == 0)
        {
            return new String[0];
        }

        // 「全Pカート」はリソースより取得する。
        String allListName = DisplayText.getText("CMB-W0072");
        pulldownData.add(0, WmsParam.ALL_AREA_NO + PDDELIM + allListName + PDDELIM + WmsParam.ALL_AREA_NO + PDDELIM
                + SELECT_ON);

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // エリアをArrayListに追加する。
        for (PrintHistory printHistory : printHistories)
        {
            // Value値のセット
            value = printHistory.getListResourcekey();
            // Text値のセット
            text = DisplayText.getText(printHistory.getListResourcekey());
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------
    /**
     * 端末に対応するステーションの検索を行います。<BR>
     *
     * @param type
     *            ステーション表示種別
     * @return 検索結果
     * @throws NotFoundException
     * @throws InvalidDefineException
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Station[] getStation(StationType type)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        // 検索済かチェック
        // key = 端末No. + type
        String key = _terminalNo + String.valueOf(type);
        if (_stationTable.containsKey(key))
        {
            return _stationTable.get(key);
        }

        StationHandler handler = new StationHandler(getConnection());
        StationSearchKey sKey = new StationSearchKey();

        // 副問い合わせ用TerminalAreaの検索条件のセットをする。
        TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
        tKey.setStationNoCollect();
        tKey.setTerminalNo(_terminalNo);

        switch (type)
        {
            case STORAGE:
            case FLOORANDSTORAGE:
            {
                // 入庫用ステーションの場合
                // select parentStNo, StNo, StName
                // from dmstation
                // where
                // station_no in ( select station_no from dmterminalarea where
                // terminal_no='...')
                // and (station_type = in or station_type = inout)
                // and workplace_type = floor(=station)
                // order by
                // parent_station_no, station_no

                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setWhStationNoCollect();
                sKey.setAisleStationNoCollect();

                // 副問合せ条件セット
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", false);
                sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setParentStationNoOrder(true);
                sKey.setStationNoOrder(true);
                break;
            }

            case RETRIEVAL:
            case FLOORANDRETRIEVAL:
            case DIRECT_TRANSFER_TO:
            {
                // 出庫用ステーションの場合
                // 直行(搬送先)用の場合も、出庫用ステーションを取得する。
                // select parentStNo, StNo, StName
                // from dmstation
                // where
                // station_no in ( select station_no from dmterminalarea where
                // terminal_no='...')
                // and (station_type = out or station_type = inout)
                // and workplace_type = floor(=station)
                // order by
                // parent_station_no, station_no

                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setWhStationNoCollect();
                sKey.setAisleStationNoCollect();

                // 副問い合わせ用Stationの検索条件をセットする。
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", false);
                sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setParentStationNoOrder(true);
                sKey.setStationNoOrder(true);
                break;
            }

            case ADD_STORAGE:
            case INVENTORY_CHECK:
            case ITEM_RETRIEVAL:
            {
                // 積増入庫または在庫確認用または商品コード指定出庫用ステーションの場合
                // select parentStNo, StNo, StName
                // from dmstation
                // where
                // station_no in ( select station_no from dmterminalarea where
                // terminal_no='...')
                // and (station_type = out or class_name =
                // FreeRetrievalStationOperator)
                // and workplace_type = floor(=station)
                // order by
                // parent_station_no, station_no

                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setWhStationNoCollect();

                // 副問い合わせ用Stationの検索条件をセットする。
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setClassName(FREE_RETRIEVAL_STATION_CLASSNAME, "=", "(", "", false);
                sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setParentStationNoOrder(true);
                sKey.setStationNoOrder(true);
                break;
            }

            case OPERATION_DISPLAY:
            {
                // 作業表示用ステーションの場合
                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setWhStationNoCollect();

                // 副問い合わせ用Stationの検索条件をセットする。
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setOperationDisplay(Station.OPERATION_DISPLAY_DISP_ONLY, "=", "(", "", false);
                sKey.setOperationDisplay(Station.OPERATION_DISPLAY_INSTRUCTION, "=", "", ")", true);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setStationNoOrder(true);
                break;
            }

            case DIRECT_TRANSFER_FROM:
            {
                // 直行(搬送元)用ステーションの場合
                Station[] storage_sts = getStation(StationType.STORAGE);

                ArrayList<Station> temp_st = new ArrayList<Station>();
                for (int i = 0; i < storage_sts.length; i++)
                {
                    // 取得した入庫用ステーションが直行ルートを持っているかをチェック
                    if (hasToStation(storage_sts[i]))
                    {
                        // 直行可能な場合はステーションを保持
                        temp_st.add(storage_sts[i]);
                    }
                }

                // 返却用のStation配列
                Station[] from_sts = new Station[0];

                // 直行可能なステーションがある場合のみ結果をセットする。
                if (!temp_st.isEmpty())
                {
                    from_sts = new Station[temp_st.size()];
                    temp_st.toArray(from_sts);
                }

                // 取得結果を記憶
                _stationTable.put(key, from_sts);
                return from_sts;
            }

            case WORK_MNT:
            case ALL:
            {
                // 全てまたは作業メンテナンス用ステーションの場合
                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setSendableCollect();
                sKey.setWhStationNoCollect();

                // 副問い合わせ用Stationの検索条件をセットする。
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", false);
                sKey.setStationType(Station.STATION_TYPE_OUT, "=", "", "", false);
                sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setParentStationNoOrder(true);
                sKey.setStationNoOrder(true);
                break;
            }

            case RESTORING_MNT:
            {
                // 再入庫メンテナンス・問合せ用の場合
                // set collect
                sKey.setParentStationNoCollect();
                sKey.setStationNoCollect();
                sKey.setStationNameCollect();
                sKey.setWhStationNoCollect();

                // 副問い合わせ用Stationの検索条件をセットする。
                sKey.setKey(Station.STATION_NO, tKey);

                // set where
                sKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", false);
                sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                sKey.setRestoringOperation(Station.RESTORING_OPERATION_CREATE);
                sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
                sKey.setSendable(Station.SENDABLE_TRUE);

                // set order by
                sKey.setStationNoOrder(true);
                break;
            }

            default:
                return new Station[0];
        }

        // 対象となるステーションを取得する
        Station[] stations = (Station[])handler.find(sKey);

        _stationTable.put(key, stations);

        return stations;

    }

    /**
     * 引数で指定された作業場から、端末に対応するステーションの検索を行います。<BR>
     *
     * @param type ステーション表示種別
     * @param wp 作業場
     * @return 検索結果
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Station[] getStationByWorkPlace(StationType type, String wp)
            throws CommonException
    {
        // 検索済かチェック
        // key = 端末No. + type
        String key = _terminalNo + String.valueOf(type);
        if (_stationTable.containsKey(key))
        {
            return _stationTable.get(key);
        }

        StationHandler handler = new StationHandler(getConnection());
        StationSearchKey sKey = new StationSearchKey();

        boolean noparent_flg = false;
        String noparent_wp = WmsParam.NOPARENT_STATION_WPNO;
        if (noparent_wp.length() < wp.length() && noparent_wp.equals(wp.substring(0, noparent_wp.length())))
        {
            // 登録作業場なしの場合
            noparent_flg = true;
        }

        String[] workspaces = null;
        if (!noparent_flg)
        {
            sKey.setStationNoCollect();

            sKey.setStationNo(wp, "=", "(", "", false);
            sKey.setParentStationNo(wp, "=", "", ")", true);
            sKey.setSendable(Station.SENDABLE_FALSE);
            Station[] tmp_st = (Station[])handler.find(sKey);

            List<String> st_array = new ArrayList<String>();
            for (Station st : tmp_st)
            {
                st_array.add(st.getStationNo());
            }
            workspaces = new String[st_array.size()];
            st_array.toArray(workspaces);

            sKey.clear();
        }

        // 副問い合わせ用TerminalAreaの検索条件のセットをする。
        TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
        tKey.setStationNoCollect();
        tKey.setTerminalNo(_terminalNo);

        // set collect
        sKey.setParentStationNoCollect();
        sKey.setStationNoCollect();
        sKey.setStationNameCollect();
        sKey.setWhStationNoCollect();
        sKey.setAisleStationNoCollect();

        // 副問合せ条件セット
        sKey.setKey(Station.STATION_NO, tKey);

        // set where
        sKey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", false);
        sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
        sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
        sKey.setSendable(Station.SENDABLE_TRUE);
        if (!noparent_flg)
        {
            sKey.setParentStationNo(workspaces, false);
        }
        else
        {
            // 登録作業場なしの場合
            String whst = wp.substring(noparent_wp.length());
            sKey.setWhStationNo(whst);
            sKey.setParentStationNo((String)null);
        }

        // set order by
        sKey.setParentStationNoOrder(true);
        sKey.setStationNoOrder(true);

        // 対象となるステーションを取得する
        Station[] stations = (Station[])handler.find(sKey);

        _stationTable.put(key, stations);

        return stations;

    }

    /**
     * 作業場の検索を行います。<BR>
     * 端末No.から該当するステーションを取得し、そのステーションに紐づく作業場一覧を昇順で返します。<BR>
     * 作業場に属さないステーションだった場合は、返り値にはそのステーションのデータはセットせず、
     * <code>_noParentStationWH</code>に、 作業場に属さないステーションの倉庫ステーションNo.をセットします。<BR>
     *
     * @param type
     *            表示プルダウンタイプ
     * @return 作業場
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Station[] getWorkSpace(StationType type)
            throws CommonException
    {
        // typeに該当するステーションを取得する
        Station[] stations = getStation(type);

        // 取得したStationの親ステーションNo.から作業場を取得する。
        // 親ステーションNo.ごとに集約し、作業場一覧を取得します。
        Set<String> whnoSet = new TreeSet<String>();
        for (int i = 0; i < stations.length; i++)
        {
            // 作業場が空白だった場合(ステーションが作業場に属していない場合)は
            // _noParentStationWHに倉庫ステーションNo.を保持
            if (StringUtil.isBlank(stations[i].getParentStationNo()))
            {
                String wh = stations[i].getWhStationNo();
                if (!_noParentStationWH.contains(wh))
                {
                    _noParentStationWH.add(stations[i].getWhStationNo());
                }
            }

            if (!StringUtil.isBlank(stations[i].getParentStationNo()))
            {
                whnoSet.add(stations[i].getParentStationNo());
            }
        }
        if (whnoSet.size() == 0)
        {
            return new Station[0];
        }

        // 上記作業場から親作業場の必要なデータを取得する
        ArrayList<String> tempWp = new ArrayList<String>();
        for (int i = 0; i < whnoSet.size(); i++)
        {
            for (Iterator<String> it = whnoSet.iterator(); it.hasNext();)
            {
                for (String s : getParentStationNo(it.next()))
                {
                    tempWp.add(s);
                }
            }
        }
        // 取得した作業場をソートする
        String[] wpStations = new String[tempWp.size()];
        tempWp.toArray(wpStations);

        StationHandler sth = new StationHandler(getConnection());
        StationSearchKey stKey = new StationSearchKey();

        // set collect
        stKey.setStationNoCollect();
        stKey.setStationNameCollect();
        stKey.setParentStationNoCollect();
        stKey.setCollect(Area.AREA_NO);

        // set where
        stKey.setStationNo(wpStations, true);
        stKey.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);

        // set order by
        stKey.setStationNoOrder(true);

        return (Station[])sth.find(stKey);
    }

    /**
     * 指定されたステーションNo.を含めた上位の作業場のステーションNo.を全て返します。<BR>
     * 引数で指定されたステーションが末端のステーションで、 親作業場をもたないステーションだった場合は、length0のString配列を返します。<BR>
     * 親作業場をもつ場合は、親作業場のステーションNo.配列を返します。（自分は含みません）<BR>
     * 引数で指定されたステーションが作業場だった場合は、 引数で指定した作業場も含めたステーションNo.配列を返します。<BR>
     *
     * @param stno
     *            ステーションNo.
     * @return 作業場
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected String[] getParentStationNo(String stno)
            throws CommonException
    {
        if (StringUtil.isBlank(stno))
        {
            return new String[0];
        }

        Station st = StationFactory.makeStation(getConnection(), stno);

        ArrayList<String> tempSt = new ArrayList<String>();
        // 末端のステーションだった場合は、上位を検索し登録する
        if (st.getWorkplaceType().equals(Station.WORKPLACE_TYPE_FLOOR))
        {
            for (String s : getParentStationNo(st.getParentStationNo()))
            {
                tempSt.add(s);
            }
        }
        // 作業場で、かつ上位に作業場がある場合
        // 自インスタンスを登録し、上位を検索し登録する
        else
        {
            tempSt.add(st.getStationNo());
            for (String s : getParentStationNo(st.getParentStationNo()))
            {
                tempSt.add(s);
            }
        }

        return tempSt.toArray(new String[tempSt.size()]);
    }

    /**
     * エリアの検索を行います。<br>
     *
     * @param type
     *            エリア表示プルダウンタイプ
     * @param stType
     *            AS/RS表示タイプ
     * @return 検索結果
     * @throws NotFoundException
     * @throws InvalidDefineException
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Area[] getArea(AreaType type, StationType stType)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        AreaHandler handler = new AreaHandler(getConnection());
        AreaSearchKey sKey = new AreaSearchKey();

        switch (type)
        {
            case FLOOR_AND_ASRS:
            {
                // AS/RSと平置の場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();

                if (stType == null)
                {
                    // 検索条件
                    String[] str = {
                            SystemDefine.AREA_TYPE_ASRS,
                            SystemDefine.AREA_TYPE_FLOOR
                    };
                    sKey.setAreaType(str, true);
                }
                else if (stType != null)
                {
                    // 検索条件
                    sKey.setAreaType(SystemDefine.AREA_TYPE_ASRS);

                    // stTypeが指定されている場合はさらに条件追加
                    Station[] stations = getStation(stType);

                    Set<String> whnoSet = new TreeSet<String>();
                    // 取得したStationから倉庫ステーションNo.を集約して取得する
                    for (int i = 0; i < stations.length; i++)
                    {
                        whnoSet.add(stations[i].getWhStationNo());
                    }

                    sKey.setWhstationNo(whnoSet.toArray(new String[whnoSet.size()]), false);

                    sKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
                }

                // ソート順
                sKey.setAreaNoOrder(true);

                break;
            }

            case ASRS:
            {
                if (stType == null)
                {
                    return new Area[0];
                }

                // AS/RSエリアの場合
                // typeに該当するステーションを取得する
                Station[] stations = getStation(stType);

                Set<String> whnoSet = new TreeSet<String>();
                // 取得したStationから倉庫ステーションNo.を集約して取得する
                for (int i = 0; i < stations.length; i++)
                {
                    whnoSet.add(stations[i].getWhStationNo());
                }

                if (whnoSet == null || whnoSet.size() == 0)
                {
                	return new Area[0];
                }

                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                sKey.setWhstationNoCollect();

                // 検索条件
                sKey.setWhstationNo(whnoSet.toArray(new String[whnoSet.size()]), true);
                sKey.setAreaType(SystemDefine.AREA_TYPE_ASRS);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;

            }

            case FLOOR:
            {
                // 平置、移動ラックエリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case FLOOR_LOCATE:
            {
                // 棚管理エリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
                sKey.setLocationType(SystemDefine.LOCATION_TYPE_FREE, "!=");
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case FLOOR_FIXEDLOCATE:
            {
                // 商品固定棚管理エリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
                sKey.setLocationType(SystemDefine.LOCATION_TYPE_FIXED);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case FLOOR_AND_TEMP_AND_RECEIVE:
            {
                // 平置、仮置き、入荷エリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                String[] str = {
                        SystemDefine.AREA_TYPE_FLOOR,
                        SystemDefine.AREA_TYPE_TEMPORARY,
                        SystemDefine.AREA_TYPE_RECEIVING
                };
                sKey.setAreaType(str, false);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case TEMP:
            {
                // 仮置きエリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_TEMPORARY);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case RECEIVE:
            {
                // 仮置きエリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_RECEIVING);
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case NOT_MOVING:
            {
                // 引当パターンエリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_MOVING, "!=");
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case NOT_MOVING_TERM:
            {
                // 移動エリア以外でAS/RS端末エリア情報を参照する場合
                // typeに該当するステーションを取得する
                Station[] stations = getStation(stType);

                Set<String> whnoSet = new TreeSet<String>();
                // 取得したStationから倉庫ステーションNo.を集約して取得する
                for (Station st:stations)
                {
                    whnoSet.add(st.getWhStationNo());
                }

                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();

                // 検索条件
                sKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR, "=", "", "", false);
                sKey.setAreaType(SystemDefine.AREA_TYPE_RECEIVING, "=", "", "", false);
                sKey.setAreaType(SystemDefine.AREA_TYPE_TEMPORARY, "=", "", "", false);
                if (whnoSet != null && whnoSet.size() != 0)
                {
	                sKey.setAreaType(SystemDefine.AREA_TYPE_ASRS, "=", "", "", true);
	                sKey.setWhstationNo(whnoSet.toArray(new String[whnoSet.size()]), true);
                }

                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            case ALL:
            {
                // 在庫エリアの場合
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setAreaNameCollect();
                // ソート順
                sKey.setAreaNoOrder(true);
                break;
            }

            default:
                return null;
        }

        // 検索を行い、結果を返す。
        return (Area[])handler.find(sKey);
    }

    /**
     * Part11StockHistoryの検索を行います。<br>
     *
     * @return 検索結果
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Part11StockHistory[] getPart11StockHistory()
            throws CommonException
    {
        Part11StockHistoryHandler handler = new Part11StockHistoryHandler(getConnection());
        Part11StockHistorySearchKey sKey = new Part11StockHistorySearchKey();

        // 在庫エリアの場合
        // 取得条件
        sKey.setAreaNoCollect();
        sKey.setAreaNameCollect();
        // 集約条件
        sKey.setAreaNoGroup();
        sKey.setAreaNameGroup();
        // ソート順
        sKey.setAreaNoOrder(true);
        // 検索を行い、結果を返す。
        return (Part11StockHistory[])handler.find(sKey);
    }

    /**
     * Part11StockHistImpの検索を行います。<br>
     *
     * @return 検索結果
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Part11StockHistImp[] getPart11StockHistImp()
            throws CommonException
    {
        Part11StockHistImpHandler handler = new Part11StockHistImpHandler(getConnection());
        Part11StockHistImpSearchKey sKey = new Part11StockHistImpSearchKey();

        // 在庫エリアの場合
        // 取得条件
        sKey.setAreaNoCollect();
        sKey.setAreaNameCollect();
        // 集約条件
        sKey.setAreaNoGroup();
        sKey.setAreaNameGroup();
        // ソート順
        sKey.setAreaNoOrder(true);
        // 検索を行い、結果を返す。
        return (Part11StockHistImp[])handler.find(sKey);
    }

    /**
     * ハードゾーンの検索を行います。<br>
     *
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected HardZone[] getHardZone()
            throws ReadWriteException
    {
        HardZoneHandler handler = new HardZoneHandler(getConnection());
        HardZoneSearchKey sKey = new HardZoneSearchKey();

        // 副問い合わせ用TerminalAreaの検索条件のセットをする。
        TerminalAreaSearchKey sssKey = new TerminalAreaSearchKey();
        sssKey.setStationNoCollect();
        sssKey.setTerminalNo(_terminalNo);

        // 副問い合わせ用Stationの検索条件をセットする。
        StationSearchKey ssKey = new StationSearchKey();
        ssKey.setWhStationNoCollect("DISTINCT");
        ssKey.setKey(Station.STATION_NO, sssKey);

        // 取得条件
        sKey.setCollect(Area.AREA_NO);
        sKey.setHardZoneIdCollect();
        // 検索条件
        sKey.setKey(HardZone.WH_STATION_NO, ssKey);
        sKey.setJoin(HardZone.WH_STATION_NO, Area.WHSTATION_NO);
        // ソート順
        sKey.setOrder(Area.AREA_NO, true);
        sKey.setHardZoneIdOrder(true);

        // 検索を行い、結果を返す。
        return (HardZone[])handler.find(sKey);
    }

    /**
     * ハードゾーンの検索を行います。<br>
     *
     * @param type ソフトゾーン検索条件
     * @param itemCode 商品コード
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected SoftZone[] getSoftZone(SoftZoneType type, String[] itemCode)
            throws ReadWriteException
    {
        if (SoftZoneType.MASTER.equals(type))
        {
            // マスタから取得の場合はフリーソフトゾーンを表示
            _freeSoftZoneDisp = true;

            SoftZoneHandler handler = new SoftZoneHandler(getConnection());
            SoftZoneSearchKey sKey = new SoftZoneSearchKey();

            // 取得条件
            sKey.setSoftZoneIdCollect();
            sKey.setSoftZoneNameCollect("max");
            // フリーソフトゾーン以外
            sKey.setSoftZoneId(SystemDefine.SOFT_ZONE_ALL, "!=");
            // ソート順
            sKey.setSoftZoneIdOrder(true);
            // group by
            sKey.setSoftZoneIdGroup();

            return (SoftZone[])handler.find(sKey);
        }
        else
        {
            // 該当ステーションNo.を取得（副問い合わせ用）
            TerminalAreaSearchKey inKey = new TerminalAreaSearchKey();
            inKey.setStationNoCollect();
            if (!StringUtil.isBlank(_terminalNo))
            {
                inKey.setTerminalNo(_terminalNo);
            }

            // 倉庫StNoを取得（副問い合わせ用）
            StationSearchKey stKey = new StationSearchKey();
            stKey.setWhStationNoCollect("DISTINCT");
            stKey.setKey(Station.STATION_NO, inKey);

            StationHandler sth = new StationHandler(getConnection());
            Station[] stations = (Station[])sth.find(stKey);

            if (ArrayUtil.isEmpty(stations))
            {
                return null;
            }

            String[] whSts = new String[stations.length];
            for (int i = 0; i < stations.length; i++)
            {
                whSts[i] = stations[i].getWhStationNo();
            }

            if (!StringUtil.isBlank(itemCode) && isAllFreeSoftZoneItems(itemCode))
            {
                // 全てフリーソフトゾーン商品の場合はフラグ更新
                _freeSoftZoneDisp = true;
            }

            SoftZoneHandler handler = new SoftZoneHandler(getConnection());
            SoftZoneSearchKey sKey = new SoftZoneSearchKey();

            if (!StringUtil.isBlank(itemCode) && !_freeSoftZoneDisp)
            {
                // 入庫可能かチェックし、入庫ソフトゾーンを取得
                String[] softzone = getSoftZoneOfItems(itemCode);

                if (softzone == null || softzone.length == 0)
                {
                    // 入庫可能なソフトゾーンなしの場合
                    return null;
                }

                // collect
                sKey.setCollect(Area.AREA_NO);
                sKey.setSoftZoneIdCollect();
                sKey.setSoftZoneNameCollect();
                // where
                sKey.setSoftZoneId(softzone, true);
                sKey.setKey(SoftZonePriority.WH_STATION_NO, whSts, true);
                sKey.setJoin(SoftZone.SOFT_ZONE_ID, SoftZonePriority.PRIORITY_SOFT_ZONE);
                sKey.setJoin(SoftZonePriority.WH_STATION_NO, Area.WHSTATION_NO);
                // ソート順
                sKey.setOrder(Area.AREA_NO, true);
                sKey.setSoftZoneIdOrder(true);
                // group by
                sKey.setGroup(Area.AREA_NO);
                sKey.setSoftZoneIdGroup();
                sKey.setSoftZoneNameGroup();
            }

            if (StringUtil.isBlank(itemCode) || _freeSoftZoneDisp)
            {
                // 商品に対応するソフトゾーン表示でない場合またはフリーソフトゾーンを表示する場合
                _freeSoftZoneDisp = true;

                // 取得条件
                sKey.setCollect(Area.AREA_NO);
                sKey.setSoftZoneIdCollect();
                sKey.setSoftZoneNameCollect("max");
                // 検索条件
                sKey.setKey(SoftZonePriority.WH_STATION_NO, whSts, true);

                sKey.setJoin(SoftZone.SOFT_ZONE_ID, SoftZonePriority.SOFT_ZONE_ID);
                sKey.setJoin(SoftZonePriority.WH_STATION_NO, Area.WHSTATION_NO);
                // ソート順
                sKey.setOrder(Area.AREA_NO, true);
                sKey.setSoftZoneIdOrder(true);
                // group by
                sKey.setGroup(Area.AREA_NO);
                sKey.setSoftZoneIdGroup();
            }

            return (SoftZone[])handler.find(sKey);
        }

    }

    /**
     * アイルの検索を行います。<br>
     *
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Aisle[] getAisle()
            throws ReadWriteException
    {
        AisleHandler handler = new AisleHandler(getConnection());
        AisleSearchKey sKey = new AisleSearchKey();

        // 取得条件
        sKey.setCollect(Area.AREA_NO);
        sKey.setAisleNoCollect();
        // 検索条件
        sKey.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        // ソート順
        sKey.setOrder(Area.AREA_NO, true);
        sKey.setAisleNoOrder(true);

        // 検索を行い、結果を返す。
        return (Aisle[])handler.find(sKey);
    }

    /**
     * バンクの検索を行います。<br>
     *
     * @param type
     *            表示プルダウンタイプ
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Entity[] getBank(BankType type)
            throws ReadWriteException
    {
        Entity[] ents = null;

        switch (type)
        {
            case ASRS:
            {
                // AS/RSエリアの場合
                ShelfHandler handler = new ShelfHandler(getConnection());
                ShelfSearchKey sKey = new ShelfSearchKey();
                // 取得条件
                sKey.setCollect(Area.AREA_NO);
                sKey.setBankNoCollect();
                // 検索条件
                sKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);
                // ソート順
                sKey.setOrder(Area.AREA_NO, true);
                sKey.setBankNoOrder(true);
                // グループ条件
                sKey.setGroup(Area.AREA_NO);
                sKey.setBankNoGroup();

                ents = handler.find(sKey);
                break;
            }

            case FLOOR:
            {
                // 平置、移動ラックエリアの場合
                AreaSearchKey ssKey = new AreaSearchKey();

                // 副問い合わせ用Areaの検索条件のセットをする。
                ssKey.setAreaNoCollect();
                ssKey.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
                ssKey.setLocationType(SystemDefine.LOCATION_TYPE_FREE, "!=");

                LocateHandler handler = new LocateHandler(getConnection());
                LocateSearchKey sKey = new LocateSearchKey();
                // 取得条件
                sKey.setAreaNoCollect();
                sKey.setBankNoCollect();
                // 検索条件
                sKey.setKey(Locate.AREA_NO, ssKey);
                // ソート順
                sKey.setAreaNoOrder(true);
                sKey.setBankNoOrder(true);
                // グループ条件
                sKey.setAreaNoGroup();
                sKey.setBankNoGroup();

                ents = handler.find(sKey);
                break;
            }

            default:
                return null;
        }

        // 結果を返す。
        return ents;
    }

    /**
     * 引当パターンの検索を行います。<br>
     *
     * @param type
     *            表示プルダウンタイプ
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected AllocatePriority[] getAllocatePriority(AllocateType type)
            throws ReadWriteException
    {
        AllocatePriorityHandler handler = new AllocatePriorityHandler(getConnection());
        AllocatePrioritySearchKey sKey = new AllocatePrioritySearchKey();

        switch (type)
        {
            case NORMAL:
            {
                // 通常(出庫)用の場合
                // 取得条件
                sKey.setAllocateNoCollect();
                sKey.setAllocateNameCollect();
                // 検索条件
                sKey.setAllocateType(SystemDefine.ALLOCATE_TYPE_NORMAL);
                // ソート順
                sKey.setAllocateNoOrder(true);
                // 集約条件
                sKey.setAllocateNoGroup();
                sKey.setAllocateNameGroup();
                break;
            }

            case REPLENISHMENT:
            {
                // 補充用の場合
                // 取得条件
                sKey.setAllocateNoCollect();
                sKey.setAllocateNameCollect();
                // 検索条件
                sKey.setAllocateType(SystemDefine.ALLOCATE_TYPE_REPLENISHMENT);
                // ソート順
                sKey.setAllocateNoOrder(true);
                // 集約条件
                sKey.setAllocateNoGroup();
                sKey.setAllocateNameGroup();
                break;
            }

            default:
                return null;

        }

        // 検索を行い、結果を返す。
        return (AllocatePriority[])handler.find(sKey);
    }

    /**
     * 作業理由マスタの検索を行います。<br>
     *
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Reason[] getReason()
            throws ReadWriteException
    {
        ReasonHandler handler = new ReasonHandler(getConnection());
        ReasonSearchKey sKey = new ReasonSearchKey();

        // 在庫エリアの場合
        // 検索条件
        // 取得条件
        sKey.setReasonTypeCollect();
        sKey.setReasonNameCollect();
        // 集約条件
        sKey.setReasonTypeGroup();
        sKey.setReasonNameGroup();
        // ソート順
        sKey.setReasonTypeOrder(true);
        // 検索を行い、結果を返す。
        return (Reason[])handler.find(sKey);
    }

    /**
     * 引数で指定された項目の初期表示フラグを"1"に変更します。<BR>
     * ArrayList内の他の項目がすでに"1"の場合はそれを"0"に変更します。<BR>
     * ArrayList内にselectedで指定したvalueが存在しない場合は、全ての初期表示フラグが"0"となります。<BR>
     * 連動プルダウンの場合は、子プルダウンのVALUEだけでは表示すべき項目を識別できません。そこで、
     * parentに親項目のVALUEをセットします。連動プルダウンで無い場合は""をセットします。<BR>
     * 倉庫-ステーション連動プルダウンで子（ステーションプルダウン）にこのメソッドを適用する場合、
     * 倉庫9000のステーション1301を初期表示する場合はselected="1301"、parent="9000"と指定します。
     *
     * @param array
     *            変更を行うArrayList
     * @param selected
     *            初期表示したい項目（Valueを指定する）
     * @param parent
     *            初期表示する項目の親プルダウンのVALUE。
     * @return <code>String</code>の配列
     */
    protected String[] changeToSelected(ArrayList<String> array, String selected, String parent)
    {
        for (int i = 0; i < array.size(); i++)
        {
            StringTokenizer stk = new StringTokenizer((String)array.get(i), ",", false);
            String value = stk.nextToken();
            String name = stk.nextToken();
            String parentValue = stk.nextToken();
            if (value.equals(selected))
            {
                // 親項目が無効な場合
                if (parent.equals(""))
                {
                    array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_ON);
                }
                // 親項目が有効な場合
                else
                {
                    if (parentValue.equals(parent))
                    {
                        array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_ON);
                    }
                    else
                    {
                        array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_OFF);
                    }
                }
            }
            else
            {
                array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_OFF);
            }
        }

        String[] str = new String[array.size()];
        array.toArray(str);
        return str;
    }

    /**
     * ｽﾃｰｼｮﾝNoからエリアNoを取得
     *
     * @param station
     *            ステーションNo
     * @return String エリアNo
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected String getAreaNo(String station)
            throws ReadWriteException
    {

        AreaHandler handler = new AreaHandler(getConnection());
        AreaSearchKey sKey = new AreaSearchKey();

        sKey.setJoin(Area.WHSTATION_NO, Station.WH_STATION_NO);

        sKey.setKey(Station.STATION_NO, station);

        Area[] area = (Area[])handler.find(sKey);
        return area[0].getAreaNo();
    }

    /**
     * 引数の搬送元ステーションが直行可能な搬送先ステーションを持っているかを返します。
     * @param from_st 搬送元ステーション
     * @return 直行可能ステーションを持っている場合にtrueを返す。
     * @throws ReadWriteException ルート定義ファイルの読み込みに失敗した場合にスローされます。
     * @throws NotFoundException
     * @throws InvalidDefineException
     */
    protected boolean hasToStation(Station from_st)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        Station[] to_sts = getStation(StationType.DIRECT_TRANSFER_TO);

        for (Station to_st : to_sts)
        {
            if (isDirectTransferRoute(from_st, to_st))
            {
                // 直行ルートが見つかった時点でtrueを返す。
                return true;
            }
        }

        return false;
    }

    /**
     * ルート定義より引数で搬送元ステーションから搬送先ステーションへ
     * 搬送可能かチェックします。
     * @param from_st 搬送元ステーション
     * @param to_st 搬送先ステーション
     * @return 可能の場合はTrue。不可の場合はfalse。
     * @throws ReadWriteException ルート定義ファイルの読み込みに失敗した場合にスローされます。
     * @throws NotFoundException
     * @throws InvalidDefineException
     */
    protected boolean isDirectTransferRoute(Station from_st, Station to_st)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        // 同一ステーションはfalseを返す
        if (from_st.getStationNo().equals(to_st.getStationNo()))
        {
            return false;
        }

        // アイル独立型STの場合は同一アイルかチェックする
        if (!StringUtil.isBlank(from_st.getAisleStationNo()) && !StringUtil.isBlank(to_st.getAisleStationNo()))
        {
            if (!from_st.getAisleStationNo().equals(to_st.getAisleStationNo()))
            {
                return false;
            }
        }

        RoutePiece rp;
        try
        {
            rp = new RoutePiece(new LineNumberReader(new FileReader(WmsParam.ROUTE_FILE)), from_st.getStationNo() ,from_st.getStationNo());
            int[] status = new int[1];
            status[0] = 9;
            if (!rp.exists(to_st.getStationNo(), new ArrayList<String>(), new ArrayList<String>(), getConnection(),
                    status))
            {
                // 指定された搬送ルートが定義情報内に存在しない
                return false;
            }
        }
        catch (IOException e)
        {
            // ルート定義ファイルの読み込みに失敗した場合
            // 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }

        return true;
    }

    /**
     * ためうち入力の商品から入庫可能なゾーンをチェックし、返します。
     *
     * @param items ためうち商品
     * @return 入庫可能ゾーン
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected String[] getSoftZoneOfItems(String[] items)
            throws ReadWriteException
    {
        SoftZonePriorityHandler softhandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey softkey = new SoftZonePrioritySearchKey();
        ItemSearchKey itemkey = new ItemSearchKey();

        // 1件目商品の入庫可能ソフトゾーンを取得
        List<String> softzone = new ArrayList<String>();
        if (isFreeSoftZoneItem(items[0]))
        {
            // 商品がフリーソフトゾーン商品の場合
            // 全ソフトゾーンを取得
            SoftZoneHandler szhandler = new SoftZoneHandler(getConnection());
            SoftZoneSearchKey szkey = new SoftZoneSearchKey();

            // フリーソフトゾーン以外
            szkey.setSoftZoneId(SystemDefine.SOFT_ZONE_ALL, "!=");
            szkey.setSoftZoneIdCollect();
            szkey.setSoftZoneIdOrder(true);

            SoftZone[] soft = (SoftZone[])szhandler.find(szkey);

            for (SoftZone sz : soft)
            {
                softzone.add(sz.getSoftZoneId());
            }
        }
        else
        {
            // 商品がフリーソフトゾーン商品でない場合
            softkey.setPrioritySoftZoneCollect();

            itemkey.setItemCode(items[0]);
            itemkey.setSoftZoneIdCollect("DISTINCT");
            softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
            softkey.setPrioritySoftZoneOrder(true);

            SoftZonePriority[] softzone_priority = (SoftZonePriority[])softhandler.find(softkey);

            for (SoftZonePriority sz : softzone_priority)
            {
                softzone.add(sz.getPrioritySoftZone());
            }
        }

        boolean find_flg;
        for (int i = softzone.size() - 1; i >= 0; i--)
        {
            // 2件目以降の商品とソフトゾーンをチェック
            for (int j = 1; j < items.length; j++)
            {
                if (isFreeSoftZoneItem(items[j]))
                {
                    // フリーソフトゾーン商品の場合はチェックしない
                    continue;
                }

                softkey.clear();
                itemkey.clear();

                softkey.setPrioritySoftZoneCollect();

                itemkey.setItemCode(items[j]);
                itemkey.setSoftZoneIdCollect("DISTINCT");
                softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
                softkey.setPrioritySoftZoneOrder(true);

                SoftZonePriority[] chk_softzone_priority = (SoftZonePriority[])softhandler.find(softkey);

                find_flg = false;
                for (SoftZonePriority chk_sz : chk_softzone_priority)
                {
                    if (softzone.get(i).equals(chk_sz.getPrioritySoftZone()))
                    {
                        find_flg = true;
                        break;
                    }
                }

                if (!find_flg)
                {
                    // 見つからない場合、削除
                    softzone.remove(i);
                    break;
                }
            }
        }

        String[] ret = new String[softzone.size()];
        softzone.toArray(ret);
        return ret;
    }

    /**
     * 全ての入力商品がフリーソフトゾーンかどうかを返します。
     *
     * @param items ためうち商品
     * @return 全てフリーソフトゾーン商品の場合、true
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean isAllFreeSoftZoneItems(String[] items)
            throws ReadWriteException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();

        ikey.setItemCode(items, true);
        ikey.setSoftZoneIdCollect("DISTINCT");

        Item[] item = (Item[])ihandler.find(ikey);

        if (item.length == 1 && SystemDefine.SOFT_ZONE_ALL.equals(item[0].getSoftZoneId()))
        {
            return true;
        }
        return false;
    }

    /**
     * 入力商品がフリーソフトゾーン商品かどうかを返します。
     *
     * @param item ためうち商品
     * @return フリーソフトゾーン商品の場合、true
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean isFreeSoftZoneItem(String item)
            throws ReadWriteException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();

        ikey.setItemCode(item);
        ikey.setSoftZoneIdCollect();

        Item[] ent = (Item[])ihandler.find(ikey);

        if (ent != null && ent.length != 0)
        {
            if (SystemDefine.SOFT_ZONE_ALL.equals(ent[0].getSoftZoneId()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * フリーソフトゾーン名称を取得します。<br>
     * フリーソフトゾーンがない場合、nullを返します。
     *
     * @return フリーソフトゾーン名称
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected String getFreeSoftZoneName()
            throws ReadWriteException
    {
        SoftZoneHandler shandler = new SoftZoneHandler(getConnection());
        SoftZoneSearchKey skey = new SoftZoneSearchKey();

        skey.setSoftZoneId(SystemDefine.SOFT_ZONE_ALL);

        SoftZone[] ent = (SoftZone[])shandler.find(skey);

        if (ent != null && ent.length != 0)
        {
            return ent[0].getSoftZoneName();
        }

        return null;

    }

    /**
     * 帳票発行履歴の検索を行います。<br>
     *
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected PrintHistory[] getPrintHistory()
            throws ReadWriteException
    {
        PrintHistoryHandler handler = new PrintHistoryHandler(getConnection());
        PrintHistorySearchKey sKey = new PrintHistorySearchKey();

        // 在庫エリアの場合
        // 検索条件
        // 取得条件
        sKey.setListResourcekeyCollect("DISTINCT");
        // ソート順
        sKey.setListResourcekeyOrder(true);
        // 検索を行い、結果を返す。
        return (PrintHistory[])handler.find(sKey);
    }

    /**
     * 交換データ環境定義情報の検索を行います。<br>
     *
     * @return 検索結果
     * @throws ReadWriteException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected ExchangeEnvironment[] getExchangeEnvironment(DataType type)
            throws ReadWriteException
    {
        ExchangeEnvironmentHandler handler = new ExchangeEnvironmentHandler(getConnection());
        ExchangeEnvironmentSearchKey sKey = new ExchangeEnvironmentSearchKey();

        // 在庫エリアの場合
        // 検索条件
        switch (type)
        {
            case RECEIVE:
                sKey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
                break;
            case SEND:
                sKey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
                break;
        }
        // 取得条件
        sKey.setJobTypeCollect();
        sKey.setExchangeTypeCollect();
        sKey.setDataNameCollect();
        // ソート順
        sKey.setJobTypeOrder(true);
        sKey.setExchangeTypeOrder(true);

        // 検索を行い、結果を返す。
        return (ExchangeEnvironment[])handler.find(sKey);
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     *
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PulldownController.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }
}
