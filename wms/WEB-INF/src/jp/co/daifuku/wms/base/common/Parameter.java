//$Id: Parameter.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.common;

import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;


/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * <CODE>Parameter</CODE>クラスは、BlueDog等の画面処理を行うクラスとスケジュール処理を行うクラス間での
 * パラメータの受渡しを行うために用意されたクラスです。<BR>
 * Paramererクラスには画面～スケジュール間で値の受渡しを行う項目をインスタンス変数に保持します。<BR>
 * 各パッケージ固有の項目を定義する場合はこのクラスを継承し、必要な項目を追加してください。
 * 
 * <PRE>
 * 使用例 : 
 * 
 *   Parameter param = new Parameter();
 *   param.setUserId("user1");
 *   param.setTerminalNumber("1");
 *   Scheduler sch = new SetStorageSCH();
 *   boolean bool = sch.checkParameter(param);
 * </PRE>
 * 
 * <BR>
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class Parameter
{

    // fields (upper case only)

    // Class variables -----------------------------------------------
    /**
     * WMSユーザ情報
     */
    private DfkUserInfo _dfkuserInfo = null;

    /**
     * WMSユーザ情報
     */
    private WmsUserInfo _wmsUserInfo = null;

    /**
     * ロケール情報
     */
    private Locale locale = null;

    /**
     * 完了フラグ : 未作業に戻す
     */
    public static final String COMPLETION_FLAG_UNSTART = "0";

    /**
     * 完了フラグ : 確定(全数完了or欠品完了)
     */
    public static final String COMPLETION_FLAG_DECISION = "1";

    /**
     * 完了フラグ : 分納or分割or保留
     */
    public static final String COMPLETION_FLAG_REMNANT = "2";

    /**
     * 完了フラグ(RFT) : 箱替
     */
    public static final String COMPLETION_FLAG_BOXCHANGE = "3";

    /**
     * 完了フラグ(RFT) : 完了処理済
     */
    public static final String COMPLETION_FLAG_COMPLETED = "5";

    /**
     * 完了フラグ(未使用) : 上書
     */
    public static final String COMPLETION_FLAG_OVERWRITE = "7";

    /**
     * 完了フラグ (未使用): 追加
     */
    public static final String COMPLETION_FLAG_ADD = "8";

    /**
     * 完了フラグ (RFT): キャンセル
     */
    public static final String COMPLETION_FLAG_CANCEL = "9";


    /**
     * 集約条件 : 予定単位
     */
    public static final String COLLECT_CONDITION_PLAN = "1";

    /**
     * 集約条件 : 詳細表示
     */
    public static final String COLLECT_CONDITION_DETAIL = "2";

    /**
     * 集約条件：全予定日
     */
    public static final String COLLECT_ALL_PLANDATE = "3";

    /**
     * 集約条件：予定日別
     */
    public static final String COLLECT_PLANDATE_UNIT = "4";

    /**
     * 集約条件：予定日＋バッチNo.別
     */
    public static final String COLLECT_BATCHNO_UNIT = "5";

    /**
     * 集約条件 : 商品単位
     */
    public static final String COLLECT_CONDITION_ITEM = "6";

    /**
     * 集約条件 : 期間内合計表示
     */
    public static final String COLLECT_CONDITION_TEAM = "7";

    /**
     * 集約条件 : 日別合計表示
     */
    public static final String COLLECT_CONDITION_DAILY = "8";

    /**
     * 集約条件 : 伝票・行No単位
     */
    public static final String COLLECT_CONDITION_TICKET = "9";

    /**
     * 集約条件 : 作業単位
     */
    public static final String COLLECT_CONDITION_WORK = "10";

    /**
     * 実績表示条件 : 全件
     */
    public static final String DISPLAY_RESULT_ALL = "1";

    /**
     * 実績表示条件 : 欠品分のみ
     */
    public static final String DISPLAY_RESULT_SHORTONLY = "2";

    /**
     * 実績表示条件 : バース実績
     */
    public static final String DISPLAY_RESULT_BERTH = "3";

    /**
     * 開始終了フラグ : 開始
     */
    public static final String FROM_TO_FLAG_FROM = "0";

    /**
     * 開始終了フラグ : 終了
     */
    public static final String FROM_TO_FLAG_TO = "1";

    /**
     * データ区分 : 入荷
     */
    public static final String DATA_TYPE_RECEIVE = "01";

    /**
     * データ区分 : 入庫
     */
    public static final String DATA_TYPE_STORAGE = "02";

    /**
     * データ区分 : 出庫
     */
    public static final String DATA_TYPE_RETRIEVAL = "03";

    /**
     * データ区分 : 仕分
     */
    public static final String DATA_TYPE_SORTING = "04";

    /**
     * データ区分 : 出荷
     */
    public static final String DATA_TYPE_SHIPPING = "05";
    
    /**
     * データ区分 : 荷主マスタ
     */
    public static final String DATA_TYPE_CONSIGNOR_MASTER = "06";

    /**
     * データ区分 : 仕入先マスタ
     */
    public static final String DATA_TYPE_SUPPLIER_MASTER = "07";

    /**
     * データ区分 : 出荷先マスタ
     */
    public static final String DATA_TYPE_CUSTOMER_MASTER = "08";

    /**
     * データ区分 : 商品マスタ
     */
    public static final String DATA_TYPE_ITEM_MASTER = "09";

    /**
     * データ区分 : 商品固定棚マスタ
     */
    public static final String DATA_TYPE_FIXED_LOCATION_MASTER = "10";

    /**
     * データ区分 : 在庫移動
     */
    public static final String DATA_TYPE_MOVEMENT = "11";

    /**
     * データ区分 : 棚卸
     */
    public static final String DATA_TYPE_INVENTORY = "12";

    /**
     * データ区分 : 予定外入庫
     */
    public static final String DATA_TYPE_NOPLAN_STORAGE = "13";

    /**
     * データ区分 : 予定外出庫
     */
    public static final String DATA_TYPE_NOPLAN_RETRIEVAL = "14";

    /**
     * データ区分 : エリアマスタ
     */
    public static final String DATA_TYPE_AREA_MASTER = "15";

    /**
     * データ区分 : 棚マスタ
     */
    public static final String DATA_TYPE_LOCATION_MASTER = "16";

    /**
     * データ区分 : 在庫
     */
    public static final String DATA_TYPE_STOCK = "21";
    
    /**
     * データ区分 : クロスドック
     */
    public static final String DATA_TYPE_CROSSDOCK = "00";
    
    /**
     * データ区分 ： PCT入庫予定
     */
    public static final String DATA_TYPE_PICKINGRECEIVE = "30";

    /**
     * データ区分 ： PCT出庫予定
     */
    public static final String DATA_TYPE_PICKINGRET = "31";

    /**
     * データ区分 ： PCT商品マスタ
     */
    public static final String DATA_TYPE_PCTITEM_MASTER = "32";

    /**
     * データ区分 ： PCT入荷実績
     */
    public static final String DATA_TYPE_PCTINSTOCK_RESULT = "33";

    /**
     * データ区分 ： PCT入荷実績：ファイル指定
     */
    public static final String DATA_TYPE_PCTINSTOCK_RESULT_FILE = "34";

    /**
     * データ区分 ： PCT出庫実績
     */
    public static final String DATA_TYPE_PCTRETRIEVAL_RESULT = "35";

    /**
     * データ区分 ： PCT出庫実績：ファイル指定
     */
    public static final String DATA_TYPE_PCTRETRIEVAL_RESULT_FILE = "36";

    /**
     * データ区分 ： PCT棚卸実績
     */
    public static final String DATA_TYPE_PCTINVENTORY_RESULT = "37";

    /**
     * データ区分 ： PCT出庫予定：ファイル指定
     */
    public static final String DATA_TYPE_PICKINGRET_FILE = "38";

    /**
     * 取込データの拡張子
     */
    public static final String EXTENSION = ".txt";

    /**
     * サーバーIPアドレス
     */
    public static final String SERVER_IP = "127.0.0.1";

    // 日次処理にて使用
    /**
     * NG理由:オンライン
     */
    public static final String REASON_ONLINE = "REASON_ONLINE";

    /**
     * NG理由:取込中
     */
    public static final String REASON_LOADING = "REASON_LOADING";

    /**
     * NG理由:報告中
     */
    public static final String REASON_REPORTING = "REASON_REPORTING";

    /**
     * NG理由:日次更新中
     */
    public static final String REASON_DAILYUPDATING = "REASON_DAILYUPDATING";

    /**
     * NG理由:出庫引当中
     */
    public static final String REASON_RETRIEVALALLOCATE = "REASON_RETRIEVALALLOCATE";
    
    /**
     * NG理由:搬送データクリア中
     */
    public static final String REASON_ALLOCATION_CLEAR = "REASON_ALLOCATION_CLEAR";

    /**
     * NG理由:終了処理中
     */
    public static final String REASON_END_PROCESSING = "REASON_END_PROCESSING";

    /**
     * NG理由:作業中
     */
    public static final String REASON_NOWWORKING = "REASON_NOWWORKING";

    /**
     * NG理由:起動中
     */
    public static final String REASON_NOWSTARTING = "REASON_NOWSTARTING";

    /**
     * NG理由：引当済
     */
    public static final String REASON_ALLOCATED = "REASON_ALLOCATED";

    /**
     * NG理由:報告ファイル残
     */
    public static final String REASON_REPORTFILESTAY = "REASON_REPORTFILESTAY";

    /**
     * NG理由:出庫済入庫待
     */
    public static final String REASON_WAITSTORAGE = "REASON_WAITSTORAGE";

    /**
     * NG理由:補充データあり
     */
    public static final String REASON_REPLENISHMENTDATA = "REASON_REPLENISHMENTDATA";

    /**
     * NG理由:未報告データあり
     */
    public static final String REASON_NOREPORT = "REASON_NOREPORT";

    /**
     * NG理由:未確定データあり
     */
    public static final String REASON_NOCOMPLETE = "REASON_NOCOMPLETE";

    /**
     * 発生ポイント:AS/RS
     */
    public static final String POINT_ASRS = "POINT_ASRS";

    /**
     * 発生ポイント:AS/RS在庫確認
     */
    public static final String POINT_ASRS_STOCK = "POINT_ASRS_STOCK";

    /**
     * 発生ポイント:NoReason
     */
    public static final String POINT_SPACE = "POINT_SPACE";

    /**
     * 発生ポイント:入荷
     */
    public static final String POINT_RECEIVE = "POINT_RECEIVE";

    /**
     * 発生ポイント:入庫
     */
    public static final String POINT_STORAGE = "POINT_STORAGE";

    /**
     * 発生ポイント:出庫
     */
    public static final String POINT_RETRIEVAL = "POINT_RETRIEVAL";

    /**
     * 発生ポイント:出荷
     */
    public static final String POINT_SHIPPING = "POINT_SHIPPING";

    /**
     * 発生ポイント:RFT
     */
    public static final String POINT_RFT = "POINT_RFT";

    /**
     * 発生ポイント:Pカート
     */
    public static final String POINT_PCART = "POINT_PCART";

    /**
     * 発生ポイント:移動
     */
    public static final String POINT_MOVE = "POINT_MOVE";

    /**
     * 発生ポイント:仕分
     */
    public static final String POINT_SORT = "POINT_SORT";

    /**
     * 発生ポイント:棚卸
     */
    public static final String POINT_INVENT = "POINT_INVENT";

    /**
     * 発生ポイント:補充
     */
    public static final String POINT_REPLENISHMENT = "POINT_REPLENISHMENT";

    /**
     * 発生ポイント:予定外入庫
     */
    public static final String POINT_NOPLANSTORAGE = "POINT_NOPLANSTORAGE";

    /**
     * 発生ポイント:予定外出庫
     */
    public static final String POINT_NOPLANRETRIEVAL = "POINT_NOPLANRETRIEVAL";

    /**
     * 発生ポイント:再入庫
     */
    public static final String POINT_RESTORING = "POINT_RESTORING";

    /**
     * 発生ポイント：PCT出庫
     */
    public static final String POINT_PCTRET = "POINT_PCTRET";

    // End

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * パラメータのインスタンスを生成します。
     */
    public Parameter()
    {
        _wmsUserInfo = new WmsUserInfo();
    }

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    /**
     * ユーザIDを返します。
     * @return ユーザIDを返します。
     */
    public String getUserId()
    {
        return _wmsUserInfo.getUserId();
    }

    /**
     * ユーザIDを設定します。
     * @param userId ユーザID
     */
    public void setUserId(String userId)
    {
        _wmsUserInfo.setUserId(userId);
    }

    /**
     * ユーザ名を返します。
     * @return ユーザ名を返します。
     */
    public String getUserName()
    {
        return _wmsUserInfo.getUserName();
    }

    /**
     * ユーザ名を設定します。
     * @param userName ユーザ名
     */
    public void setUserName(String userName)
    {
        _wmsUserInfo.setUserName(userName);
    }

    /**
     * 端末No.を返します。
     * @return 端末No.を返します。
     */
    public String getTerminalNo()
    {
        return _wmsUserInfo.getTerminalNo();
    }

    /**
     * 端末No.を設定します。
     * @param terminalNo 端末No.
     */
    public void setTerminalNo(String terminalNo)
    {
        _wmsUserInfo.setTerminalNo(terminalNo);
    }

    /**
     * 端末名を返します。
     * @return 端末名を返します。
     */
    public String getTerminalName()
    {
        return _wmsUserInfo.getTerminalName();
    }

    /**
     * 端末名を設定します。
     * @param terminalName 端末名
     */
    public void setTerminalName(String terminalName)
    {
        _wmsUserInfo.setTerminalName(terminalName);
    }

    /**
     * 端末アドレスを返します。
     * @return 端末アドレスを返します。
     */
    public String getTerminalAddress()
    {
        return _wmsUserInfo.getTerminalAddress();
    }

    /**
     * 端末アドレスを設定します。
     * @param terminalAddress 端末アドレス
     */
    public void setTerminalAddress(String terminalAddress)
    {
        _wmsUserInfo.setTerminalAddress(terminalAddress);
    }

    /**
     * WMSユーザ情報を返します。
     * @return WMSユーザ情報を返します。
     */
    public WmsUserInfo getWmsUserInfo()
    {
        return _wmsUserInfo;
    }

    /**
     * WMSユーザ情報を設定します。
     * @param userInfo WMSユーザ情報
     */
    public void setWmsUserInfo(WmsUserInfo userInfo)
    {
        _wmsUserInfo = userInfo;
    }

    /**
     * パラメータの<code>DfkUserInfo</code>の内容をWMSユーザ情報に設定します。
     * @param dfkUserInfo ダイフク用ユーザ情報
     */
    public void setUserInfo(DfkUserInfo dfkUserInfo)
    {
        setUserId(dfkUserInfo.getUserId());
        setUserName(dfkUserInfo.getUserName());
        setTerminalNo(dfkUserInfo.getTerminalNumber());
        setTerminalName(dfkUserInfo.getTerminalName());
        setTerminalAddress(dfkUserInfo.getTerminalAddress());

        setDfkUserInfo(dfkUserInfo);
    }
    
    /**
     * DFKユーザ情報を返します。
     * @return DFKユーザ情報を返します。
     */
    public DfkUserInfo getDfkUserInfo()
    {
        return _dfkuserInfo;
    }

    /**
     * DFKユーザ情報を設定します。<br>
     * Part11対応にて、DS番号、ページキーリソースキーなどを保持するためにセットします。<br>
     * @param dfkuserInfo DFKユーザ情報
     */
    public void setDfkUserInfo(DfkUserInfo dfkuserInfo)
    {
    	_wmsUserInfo.setDfkUserInfo(dfkuserInfo);
        _dfkuserInfo = dfkuserInfo;
    }

    /**
     * ハードウェア区分を返します。
     * @return ハードウェア区分
     */
    public String getHardwareType()
    {
        return _wmsUserInfo.getHardwareType();
    }

    /**
     * ハードウェア区分を設定します。
     * @param hardwareType ハードウェア区分<br>
     * <code>SystemDefine.HARDWARE_TYPE_XXXX</code> を指定してください。<br>
     */
    public void setHardwareType(String hardwareType)
    {
        _wmsUserInfo.setHardwareType(hardwareType);
    }

    /**
     * ロケール情報を返します。
     * @return ロケール情報を返します。
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * ロケール情報を設定します。
     * @param locale ロケール情報
     */
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
