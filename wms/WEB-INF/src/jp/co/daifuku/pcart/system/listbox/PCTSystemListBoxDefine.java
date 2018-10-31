package jp.co.daifuku.pcart.system.listbox;

import jp.co.daifuku.wms.base.common.ListBoxDefine;


/**
 * Designer :  M.Matsumoto<BR>
 * Maker :     M.Matsumoto<BR>
 * PCARTで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  M.Matsumoto
 * @author  Last commit: $Author: arai $
 */
public final class PCTSystemListBoxDefine
        extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // リストボックキー定義
    /**
     * データ環境設定キー
     */
    public static final String[] TYPE_KEY = {
            "PCTINSTOCK_SUPPORT",
            "PCTRETRIEVAL_SUPPORT",
            "PCTITEM_SUPPORT",
            "PCTINVENT_SUPPORT"
    };

    /**
     * データ環境設定キー PCT入荷
     */
    public static final int DATATYPE_PCTINSTOCK = 0;

    /**
     * データ環境設定キー PCT出庫
     */
    public static final int DATATYPE_PCTRETRIEVAL = 1;

    /**
     * データ環境設定キー PCT商品マスタ
     */
    public static final int DATATYPE_PCTITEM = 2;

    /**
     * データ環境設定キー PCT棚卸
     */
    public static final int DATATYPE_PCTINVENT = 3;

    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

    /**
     * 荷主名称の受け渡しに使用するキーです
     */
    public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";

    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

    /**
     * 商品名称の受け渡しに使用するキーです
     */
    public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

    /**
     * エリアの受け渡しに使用するキーです
     */
    public static final String AREA_KEY = "AREA_KEY";

    /**
     * エリア名称の受け渡しに使用するキーです
     */
    public static final String AREANAME_KEY = "AREANAME_KEY";

    /**
     * 棚の受け渡しに使用するキーです
     */
    public static final String LOCATION_KEY = "LOCATION_KEY";

    /**
     * 開始棚の受け渡しに使用するキーです
     */
    public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

    /**
     * 終了棚の受け渡しに使用するキーです
     */
    public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";

    /**
     * 作業日の受け渡しに使用するキーです
     */
    public static final String WORKDAY_KEY = "WORKDAY_KEY";

    /**
     * 作業日（To）の受け渡しに使用するキーです。
     */
    public static final String TO_WORKDAY_KEY = "TO_WORKDAY_KEY";

    /**
     * ゾーンNo.の受け渡しに使用するキーです
     */
    public static final String ZONENO_KEY = "ZONENO_KEY";

    /**
     * ゾーン名称の受け渡しに使用するキーです
     */
    public static final String ZONENAME_KEY = "ZONENAME_KEY";

    /**
     * 棚No.の範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_LOCATENO_KEY = "RANGE_LOCATENO_KEY";

    /**
     * 作業日の範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_WORKDAY_KEY = "RANGE_WORKDAY_KEY";

    /**
     * ボタン区分の受け渡しに使用するキーです
     */
    public static final String BUTTONKUBN_KEY = "BUTTONKUBN_KEY";

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
     * 「参照」ボタンフラグの受け渡しに使用するキーです
     */
    public static final String BTNFLUG_KEY = "BTNFLUG_KEY";

    /**
     * ユーザIDの受け渡しに使用するキーです
     */
    public static final String USERID_KEY = "USERID_KEY";

    /**
     * ユーザID(TO)の受け渡しに使用するキーです
     */
    public static final String USERIDTO_KEY = "USERIDTO_KEY";

    /**
     * 範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_KEY = "RANGE_KEY";
    
    /**
     * 検索対象テーブルの受け渡しに使用するキーです
     */
    public static final String SEARCHTABLE_KEY = "SEARCHTABLE_KEY";
        
    // データタイプ
    /**
     * 呼び出しフラグ(データ取込:PCT入荷)
     */
    public static final String LOAD_PCTINSTOCK = "LOAD_PCTINSTOCK";

    /**
     * 呼び出しフラグ(データ取込:PCT出庫)
     */
    public static final String LOAD_PCTRETRIEVAL = "LOAD_PCTRETRIEVAL";

    /**
     * 呼び出しフラグ(データ取込:PCT商品マスタ)
     */
    public static final String LOAD_PCTITEM = "LOAD_PCTITEM";

    /**
     * 呼び出しフラグ(データ報告:PCT入荷)
     */
    public static final String REPORT_PCTINSTOCK = "REPORT_PCTINSTOCK";

    /**
     * 呼び出しフラグ(データ報告:PCT出庫)
     */
    public static final String REPORT_PCTRETRIEVAL = "REPORT_PCTRETRIEVAL";

    /**
     * 呼び出しフラグ(データ報告:PCT棚卸し)
     */
    public static final String REPORT_PCTINVENTORY = "REPORT_PCTINVENTORY";

    /**
     * データ取り込みフォルダのセクション名
     */
    public static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

    /**
     * データ報告フォルダのセクション名
     */
    public static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

    
    //  リストボックスパス定義
    /**
     * 商品検索リストボックスのパスです
     */
    public static final String LST_ASRS_ITEM = "/asrs/listbox/item/LstAsItem.do";

    /**
     * 荷主検索リストボックスのパスです
     */
    public static final String LST_SRCH_CONSIGNOR = "/pcart/system/listbox/consignor/LstConsignor.do";

    /**
     * 商品検索リストボックスのパスです
     */
    public static final String LST_SRCH_ITEM = "/pcart/system/listbox/item/LstItem.do";

    /**
     * 棚検索リストボックスのパスです
     */
    public static final String LST_SRCH_LOCATION = "/pcart/system/listbox/locate/LstLocate.do";

    /**
     * 格納フォルダ検索リストボックスのパスです
     */
    public static final String LST_FOLDER_SELECT = "/pcart/system/listbox/folderselect/LstFolderSelect.do";

    /**
     * 作業日検索リストボックスのパスです
     */
    public static final String LST_SRCH_WORKDAY = "/pcart/system/listbox/workday/LstWorkDay.do";

    /**
     * エリア検索リストボックスのパスです
     */
    public static final String LST_SRCH_AREA = "/pcart/system/listbox/area/LstArea.do";

    /**
     * ゾーン検索リストボックスのパスです
     */
    public static final String LST_SRCH_ZONE = "/pcart/system/listbox/zone/LstZone.do";
    
    /**
     * ユーザID検索リストボックスのパスです
     */
    public static final String LST_SRCH_USER = "/pcart/system/listbox/userid/LstUserId.do";


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
    private PCTSystemListBoxDefine()
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
        return "$Id:";
    }
}
