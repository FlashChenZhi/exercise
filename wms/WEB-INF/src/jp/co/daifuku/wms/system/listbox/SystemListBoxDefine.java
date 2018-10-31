// $Id: SystemListBoxDefine.java 3213 2009-03-02 06:59:20Z arai $
package jp.co.daifuku.wms.system.listbox;

import jp.co.daifuku.wms.base.common.ListBoxDefine;


/**
 * システムパッケージで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * 
 * Designer :  h.kouzaki<BR>
 * Maker :     h.kouzaki<BR>
 * 
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  Last commit: $Author: arai $
 */
public final class SystemListBoxDefine
        extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // リストボックキー定義
    /**
     * 作業内容の受け渡しに使用するキーです
     */
    public static final String JOBTYPE_KEY = "JOBTYPE_KEY";

    /**
     * 開始作業日の受け渡しに使用するキーです
     */
    public static final String STARTWORKDAY_KEY = "STARTWORKDAY_KEY";

    /**
     * 終了作業日の受け渡しに使用するキーです
     */
    public static final String ENDWORKDAY_KEY = "ENDWORKDAY_KEY";

    /**
     * 作業日の受け渡しに使用するキーです
     */
    public static final String WORKDAY_KEY = "WORKDAY_KEY";

    /**
     * 作業日(to)の受け渡しに使用するキーです
     */
    public static final String WORKDAYTO_KEY = "WORKDAYTO_KEY";

    /**
     * ユーザIDの受け渡しに使用するキーです
     */
    public static final String USERID_KEY = "USERID_KEY";

    /**
     * ユーザ名称の受け渡しに使用するキーです
     */
    public static final String USERNAME_KEY = "USERNAME_KEY";

    /**
     * 集約条件の受け渡しに使用するキーです
     */
    public static final String COLLECTCONDITION_KEY = "COLLECTCONDITION_KEY";

    /**
     * 検索対象テーブルの受け渡しに使用するキーです
     */
    public static final String SEARCHTABLE_KEY = "SEARCHTABLE_KEY";

    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNOR_KEY = "CONSIGNOR_KEY";

    /**
     * 表示用開始日の受け渡しに使用するキーです。
     */
    public static final String DISPFROMDAY_KEY = "DISPFROMDAY_KEY";

    /**
     * 表示用開始時間の受け渡しに使用するキーです。
     */
    public static final String DISPFROMTIME_KEY = "DISPFROMTIME_KEY";

    /**
     * 表示用終了日の受け渡しに使用するキーです。
     */
    public static final String DISPTODAY_KEY = "DISPTODAY_KEY";

    /**
     * 表示用終了時間の受け渡しに使用するキーです。
     */
    public static final String DISPTOTIME_KEY = "DISPTOTIME_KEY";

    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

    /**
     * エリアの受け渡しに使用するキーです
     */
    public static final String AREANO_KEY = "AREANO_KEY";

    /**
     * ロットNo.の受け渡しに使用するキーです
     */
    public static final String LOTNO_KEY = "LOTNO_KEY";

    /**
     * 作業区分の受け渡しに使用するキーです
     */
    public static final String WORKDIV_KEY = "WORKDIV_KEY";

    /**
     * 範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_KEY = "RANGE_KEY";

    /**
     * 範囲フラグ：開始
     */
    public static final String RANGE_START = "0";

    /**
     * 範囲フラグ：終了
     */
    public static final String RANGE_END = "1";

    /**
     * 格納フォルダの受け渡しに使用するキーです
     */
    public static final String FOLDER_KEY = "FOLDER_KEY";

    /**
     * パッケージの受け渡しに使用するキーです
     */
    public static final String PACKAGE_KEY = "PACKAGE_KEY";

    /**
     * 呼び出し元画面フラグに使用するキーです 取込画面:true 報告画面:false
     */
    public static final String BTNFLUG_KEY = "BTNFLUG_KEY";

    /**
     * 呼び出しフラグ(データ報告:在庫情報)
     */
    public static final String REPORT_STOCK = "REPORT_STOCK";

    /**
     * データ取り込みフォルダのセクション名
     */
    public static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

    /**
     * データ報告フォルダのセクション名
     */
    public static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

    /**
     * データ環境設定キー
     */
    public static final String[] TYPE_KEY = {
        "RECEIVE_SUPPORT",
        "STORAGE_SUPPORT",
        "RETRIEVAL_SUPPORT",
        "SHIP_INSPECTION",
        "PICKING_SUPPORT",
        "MOVING_SUPPORT",
        "STOCKTAKING_SUPPORT",
        "STOCK_SUPPORT",
        "NOPLANSTORAGE_SUPPORT",
        "NOPLANRETRIEVAL_SUPPORT",
        "MASTER_SUPPLIER",
        "MASTER_CUSTOMER",
        "MASTER_ITEM"
    };

    /**
     * データ環境設定キー 入荷
     */
    public static final int DATATYPE_RECEIVING = 0;

    /**
     * データ環境設定キー 入庫
     */
    public static final int DATATYPE_STORAGESUPPORT = 1;

    /**
     * データ環境設定キー 出庫
     */
    public static final int DATATYPE_RETRIEVALSUPPORT = 2;

    /**
     * データ環境設定キー 出荷
     */
    public static final int DATATYPE_SHIPPINGINSPECTION = 3;

    /**
     * データ環境設定キー 仕分
     */
    public static final int DATATYPE_SORTINGSUPPORT = 4;

    /**
     * データ環境設定キー 在庫移動
     */
    public static final int DATATYPE_MOVINGSUPPORT = 5;

    /**
     * データ環境設定キー 棚卸
     */
    public static final int DATATYPE_INVENTORYSUPPORT = 6;

    /**
     * データ環境設定キー 在庫
     */
    public static final int DATATYPE_STOCK = 7;

    /**
     * データ環境設定キー 予定外入庫
     */
    public static final int DATATYPE_NOPLANSTORAGE = 8;

    /**
     * データ環境設定キー 予定外出庫
     */
    public static final int DATATYPE_NOPLANRETRIEVAL = 9;

    /**
     * データ環境設定キー 仕入先マスタ
     */
    public static final int DATATYPE_SUPPLIER = 10;

    /**
     * データ環境設定キー 出荷先マスタ
     */
    public static final int DATATYPE_CUSTOMER = 11;

    /**
     * データ環境設定キー 商品マスタ
     */
    public static final int DATATYPE_ITEM = 12;

    // リストボックスパス定義

    /**
     * 作業日検索リストボックスのパスです
     */
    public static final String LST_SYSTEM_FOLDER = "/system/listbox/folderselect/LstFolderSelect.do";
    
    /**
     * 作業日検索リストボックスのパスです
     */
    public static final String LST_SYSTEM_WORKDAY = "/system/listbox/workday/LstSystemWorkday.do";

    /**
     * ロットNo.検索リストボックスのパスです。
     */
    public static final String LST_SYSTEM_LOTNO = "/system/listbox/lotno/LstSystemLotNo.do";

    /**
     * ユーザ名称検索リストボックスのパスです
     */
    public static final String LST_SYSTEM_USER = "/system/listbox/user/LstSystemUser.do";

    /**
     * ユーザ別実績照会リストボックスのパスです
     */
    public static final String LST_SYSTEM_WORKERRESULT = "/system/listbox/workerresult/LstWorkerResult.do";

    /**
     * 商品検索リストボックスのパスです
     */
    public static final String LST_SYSTEM_ITEM = "/system/listbox/item/LstSystemItem.do";

    /**
     * 入出庫実績リストボックスのパスです
     */
    public static final String LST_SYSTEM_STOCKHISTORYRESULT =
            "/system/listbox/stockhistoryresult/LstStockHistoryResult.do";

    /**
     * 格納フォルダ検索リストボックスのパスです
     */
    public static final String LST_FOLDER_SELECT = "/system/listbox/folderselect/LstFolderSelect.do";

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
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private SystemListBoxDefine()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: SystemListBoxDefine.java 3213 2009-03-02 06:59:20Z arai $";
    }
}
