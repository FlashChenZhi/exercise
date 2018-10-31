// $Id: PCTMasterListBoxDefine.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.listbox;

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
public final class PCTMasterListBoxDefine
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
     * 検索対象テーブルの受け渡しに使用するキーです
     */
    public static final String SEARCHTABLE_KEY = "SEARCHTABLE_KEY";

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
     * 呼び出しフラグ(データ報告:PCT商品マスタ)
     */
    public static final String REPORT_PCTITEM = "REPORT_PCTITEM";
    
    /**
     * データ取り込みフォルダのセクション名
     */
    public static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

    /**
     * データ報告フォルダのセクション名
     */
    public static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

    /**
     * パッケージの受け渡しに使用するキーです
     */
    public static final String PACKAGE_KEY = "PACKAGE_KEY";

    /**
     * 範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_KEY = "RANGE_KEY";
    
    /**
     * 範囲フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_FLAG_OFF_KEY = "false";

    /**
     * 開始エリアNoの受け渡しに使用するキーです
     */
    //public static final String STARTAREA_KEY = "STARTAREA_KEY";
    /**
     * 終了エリアNoの受け渡しに使用するキーです
     */
    //public static final String ENDAREA_KEY = "ENDAREA_KEY";
    /**
     * ロット入数の受け渡しに使用するキーです
     */
    public static final String LOTENTERINGKEY_KEY = "LOTENTERINGKEY_KEY";

    /**
     * マスタ単重量の受け渡しに使用するキーです
     */
    public static final String SINGLEWEIGHT_KEY = "SINGLEWEIGHT_KEY";

    /**
     * マスタ単重量の受け渡しに使用するキーです
     */
    public static final String SINGLEWEIGHT_TO_KEY = "SINGLEWEIGHT_TO_KEY";

    /**
     * 測定重量の受け渡しに使用するキーです
     */
    public static final String INSPECTWEIGHT_KEY = "INSPECTWEIGHT_KEY";

    /**
     * 訂正数量の受け渡しに使用するキーです
     */
    public static final String CORRECTQTY_KEY = "CORRECTQTY_KEY";

    /**
     * 訂正単重量の受け渡しに使用するキーです
     */
    public static final String CORRECTWEIGHT_KEY = "CORRECTWEIGHT_KEY";

    /**
     * 登録日時の受け渡しに使用するキーです
     */
    public static final String REGISTDATE_KEY = "REGISTDATE_KEY";

    /**
     * 端末No.の受け渡しに使用するキーです
     */
    public static final String TERMINALNO_KEY = "TERMINALNO_KEY";

    /**
     * 間口の受け渡しに使用するキーです
     */
    public static final String LOCATIONNO_KEY = "LOCATIONNO_KEY";

    /**
     * 発生日(From)の受け渡しに使用するキーです
     */
    public static final String SEARCHDAYFROM_KEY = "SEARCHDAYFROM_KEY";

    /**
     * 発生日(To)の受け渡しに使用するキーです
     */
    public static final String SEARCHDAYTO_KEY = "SEARCHDAYTO_KEY";

    /**
     * 発生日(From)の受け渡しに使用するキーです
     */
    public static final String SEARCHTIMEFROM_KEY = "SEARCHTIMEFROM_KEY";

    /**
     * 発生日(To)の受け渡しに使用するキーです
     */
    public static final String SEARCHTIMETO_KEY = "SEARCHTIMETO_KEY";

    /**
     * 発生日の受け渡しに使用するキーです
     */
    public static final String REGISTDAY_KEY = "REGISTDAY_KEY";

    /**
     * 発生時刻の受け渡しに使用するキーです
     */
    public static final String REGISTTIME_KEY = "REGISTTIME_KEY";

    /**
     * 範囲指定チェックボックス状態 : チェック無し
     */
    public static final String RANGE_CHECK_OFF = "0";

    /**
     * 範囲指定チェックボックス状態 : チェック有り
     */
    public static final String RANGE_CHECK_ON = "1";
    
    /**
     * 開始商品コードの受け渡しに使用するキーです
     */
    public static final String STARTITEMCODE_KEY = "STARTITEMCODE_KEY";

    /**
     * 終了商品コードの受け渡しに使用するキーです
     */
    public static final String ENDITEMCODE_KEY = "ENDITEMNAME_KEY";
    
    /**
     * 終了商品コードの受け渡しに使用するキーです
     */
    public static final String TOITEMCODE_KEY = "TOITEMCODE_KEY";
    
    /**
     * 商品コード範囲の受け渡しに使用するキーです
     */
    public static final String RANGE_ITEMCODE_KEY = "RANGE_ITEMCODE_KEY";
    
    /**
     * 出荷先コードの受け渡しに使用するキーです
     */
    public static final String CUSTOMERCODE_KEY = "RANGE_ITEMCODE_KEY";
    
    /**
     * 出荷先名称の受渡しに使用するキーです
     */
    public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";
    
    //  リストボックスパス定義
    /**
     * 商品検索リストボックスのパスです
     */
    public static final String LST_ASRS_ITEM = "/asrs/listbox/item/LstAsItem.do";

    /**
     * 荷主検索リストボックスのパスです
     */
    public static final String LST_SRCH_CONSIGNOR = "/pcart/master/listbox/consignor/LstConsignor.do";

    /**
     * 商品検索リストボックスのパスです
     */
    public static final String LST_SRCH_ITEM = "/pcart/master/listbox/item/LstItem.do";
    
   /**
     * 出荷先検索リストボックスのパスです
     */
    public static final String LST_SRCH_CUSTOMER = "/pcart/master/listbox/cust/LstCustomer.do";
    
    /**
     * 出荷先検索リストボックス(登録)のパスです
     */
    public static final String LST_SRCH_REGISTCUSTOMER = "/pcart/master/listbox/registcust/LstRegistCustomer.do";
    
    
    /**
     * 商品マスタポップアップアドレス
     */
    public static final String LST_ITEM_MASTER_LIST = "/pcart/master/listbox/pctitemlist/PCtLstItemList.do";

    /**
     * 棚検索リストボックスのパスです
     */
    public static final String LST_SRCH_LOCATION = "/pcart/master/listbox/locate/LstLocate.do";

    /**
     * 格納フォルダ検索リストボックスのパスです
     */
    public static final String LST_FOLDER_SELECT = "/pcart/master/listbox/folderselect/LstFolderSelect.do";

    /**
     * 発生日検索リストボックスのパスです
     */
    public static final String LST_SRCH_OCCURENCEDATE = "/pcart/master/listbox/date/LstDate.do";

    /**
     * エリア検索リストボックスのパスです
     */
    public static final String LST_SRCH_AREA = "/pcart/master/listbox/area/LstArea.do";

    /**
     * ゾーン検索リストボックスのパスです
     */
    public static final String LST_SRCH_ZONE = "/pcart/master/listbox/zone/LstZone.do";

    /**
     * 重量差異リスト一覧リストボックスのパスです
     */
    public static final String LST_WEIGHTDISTINCTION = "/pcart/master/listbox/pctweightdistinctionlist/LstPCTWeightDistinctionList.do";

    /**
     * 単重量一覧リストボックスのパスです
     */
    public static final String LST_SINGLEWEIGHT = "/pcart/master/listbox/unitweight/LstUnitWeight.do";

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
    private PCTMasterListBoxDefine()
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
        return "$Id: PCTMasterListBoxDefine.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
