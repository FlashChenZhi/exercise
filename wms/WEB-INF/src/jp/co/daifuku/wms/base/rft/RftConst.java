// $Id: RftConst.java 4606 2009-07-03 07:28:29Z shibamoto $
package jp.co.daifuku.wms.base.rft;


/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * TODO This is default class comment.<br>
 *
 *
 * @version $Revision: 4606 $, $Date: 2009-07-03 16:28:29 +0900 (金, 03 7 2009) $
 * @author  971498
 * @author  Last commit: $Author: shibamoto $
 */
public class RftConst
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** 選択フラグ(選択) */
    public static final String SELECT_FLAG_SELECT = "1";

    /** 選択フラグ(未選択) */
    public static final String SELECT_FLAG_NOTSELECT = "0";

    /** ケースボタンカラー */
    public static final String CASEBTN_COLOR = "E0FFFF";

    /** ピースボタンカラー */
    public static final String PIECEBTN_COLOR = "FA5048";
    
    /** メニューボタンカラー */
    public static final String MENUBTN_COLOR = "FFA500";

    /** 検品モード(ケース) */
    public static final String CPMODE_CASE = "1";

    /** 検品モード(ピース) */
    public static final String CPMODE_PIECE = "2";

    /** 端末区分(DTX7) */
    public static final String TERMINAL_TYPE_DTX7 = "dtx7";

    /** 端末区分(BT910) */
    public static final String TERMINAL_TYPE_BT910 = "bt910";

    /** 端末区分(MC3000) */
    public static final String TERMINAL_TYPE_MC3000 = "mc3000";
    
    /** ビープ音 */
    public static final String BEEP = "Platform\\WavAlias\\EMPTY.WAV";

    /** <code>TC入荷商品単位ID番号</code> */
    public static final String TC_RECEIVING = "ID01";

    /** <code>DC入荷ID番号</code> */
    public static final String DC_RECEIVING = "ID02";

    /** <code>出庫ID番号</code> */
    public static final String RETRIEVAL_ORDER = "ID03";

    /** <code>出荷検品ID番号</code> */
    public static final String SHIPPING_CUSTOMER = "ID04";

    /** <code>出荷(商品単位)ID番号</code> */
    public static final String SHIPPING_ITEM = "ID05";

    /** <code>出荷積込ID番号</code> */
    public static final String SHIPPING_LOAD = "ID06";

    /** <code>仕分ID番号</code> */
    public static final String SORTING = "ID07";

    /** <code>入庫ID番号</code> */
    public static final String STORAGE = "ID08";

    /** <code>棚卸ID番号</code> */
    public static final String INVENTORY = "ID09";
    
    /** <code>入庫(入荷エリア)ID番号</code> */
    public static final String RECEIVING_STORAGE = "ID10";
    
    /** <code>移動入庫ID番号</code> */
    public static final String MOVE_STORAGE = "ID11";

    /** フィールド名(ユーザID) **/
    public static final String FIELD_NAME_USERID = "UserId";
    
    /** フィールド名(パスワード) **/
    public static final String FIELD_NAME_PASSWORD = "Password";
    
    /** フィールド名(ケース数) **/
    public static final String FIELD_NAME_CASEQTY = "CaseQty";
    
    /** フィールド名(ピース数) **/
    public static final String FIELD_NAME_PIECEQTY = "PieceQty";
    
    /** フィールド名(ロットNo.) **/
    public static final String FIELD_NAME_LOTNO = "LotNo";
    
    /** フィールド名(エリアNo.) **/
    public static final String FIELD_NAME_AREANO = "AreaNo";
    
    /** フィールド名(検索棚) **/
    public static final String FIELD_NAME_SEARCHLOC = "SearchLoc";
    
    /** フィールド名(オーダーNo.1) **/
    public static final String FIELD_NAME_ORDERNO1 = "OrderNo1";

    /** フィールド名(オーダーNo.2) **/
    public static final String FIELD_NAME_ORDERNO2 = "OrderNo2";

    /** フィールド名(オーダーNo.3) **/
    public static final String FIELD_NAME_ORDERNO3 = "OrderNo3";

    /** フィールド名(オーダーNo.4) **/
    public static final String FIELD_NAME_ORDERNO4 = "OrderNo4";

    /** フィールド名(棚) **/
    public static final String FIELD_NAME_LOCATION = "Location";

    /** フィールド名(商品コード) **/
    public static final String FIELD_NAME_ITEMCODE = "ItemCode";

    /** <code>エンコーディング文字列(文字列<->Byte配列 変換)</code> */
    public static final String DATA_ENCODING_STRING = "shift_jis";

    /** エリアNo.の長さ */
    public static final int AREANO_LENGTH = 4;

    /** 棚検索区分(空棚) */
    public static final String LOCATION_FLAG_EMP = "1";
    
    /** 棚検索区分(補充棚) */
    public static final String LOCATION_FLAG_SPLY = "2";
    
    /** 最大のスキップカウント件数 */
    public static final int MAX_SKIP_COUNT = 9999;

    /** 一意に決まらない伝票行番号 */
    public static final int DUPLICATE_LINE_NO = -1;

    /** 棚No（ロケーション）の長さ(byte) */
    public static final int LEN_LOCATION = 8;
    
    /** エリアの長さ(byte) */
    public static final int LEN_AREA = 4;

    /** オーダー通番(1) */
    public static final String ORDER_SERIAL_NO1 = "1";
    
    /** オーダー通番(2) */
    public static final String ORDER_SERIAL_NO2 = "2";
    
    /** オーダー通番(3) */
    public static final String ORDER_SERIAL_NO3 = "3";
    
    /** オーダー通番(4) */
    public static final String ORDER_SERIAL_NO4 = "4";
    
    /** メニュー(非表示) */
    public static final String MENU_OFF = "0";
    
    /** メニュー(表示) */
    public static final String MENU_ON = "1";

    /** 入力タイプ(数量) */
    public static final String INPUT_TYPE_QTY = "1";
    
    /** 入力タイプ(ロット) */
    public static final String INPUT_TYPE_LOT = "2";

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
     * TODO default constructor.
     */
    public RftConst()
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
        return "$Id: RftConst.java 4606 2009-07-03 07:28:29Z shibamoto $";
    }
}
