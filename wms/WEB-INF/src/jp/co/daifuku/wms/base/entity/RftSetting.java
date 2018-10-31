// $Id: RftSetting.java 2865 2009-01-21 04:54:08Z arai $
// $LastChangedRevision: 2865 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * RFTSETTINGのエンティティクラスです。
 *
 * @version $Revision: 2865 $, $Date: 2009-01-21 13:54:08 +0900 (水, 21 1 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class RftSetting
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMRFTSETTING" ;

    /*
     * テーブル名: DMRFTSETTING
     * 号機No. :                       RFT_NO              varchar2(3)
     * パラメータ名 :                  KEY                 varchar2(40)
     * 設定値 :                        VALUE               varchar2(40)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (号機No.(<code>RFT_NO</code>)) */
    public static final FieldName RFT_NO = new FieldName(STORE_NAME, "RFT_NO") ;

    /** フィールド定義 (パラメータ名(<code>KEY</code>)) */
    public static final FieldName KEY = new FieldName(STORE_NAME, "KEY") ;

    /** フィールド定義 (設定値(<code>VALUE</code>)) */
    public static final FieldName VALUE = new FieldName(STORE_NAME, "VALUE") ;

    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (登録処理名(<code>REGIST_PNAME</code>)) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;

    /** フィールド定義 (最終更新日時(<code>LAST_UPDATE_DATE</code>)) */
    public static final FieldName LAST_UPDATE_DATE = new FieldName(STORE_NAME, "LAST_UPDATE_DATE") ;

    /** フィールド定義 (最終更新処理名(<code>LAST_UPDATE_PNAME</code>)) */
    public static final FieldName LAST_UPDATE_PNAME = new FieldName(STORE_NAME, "LAST_UPDATE_PNAME") ;


    /** Key(端末区分) */
    public static final String KEY_TERMINAL_TYPE = "TerminalType";

    /** Key(ロケール) */
    public static final String KEY_LOCALE = "Locale";

    /** Key(パスワード入力) */
    public static final String KEY_PASSWORD_INPUT_MODE = "PassWordInputMode";

    /** Key(入荷荷主スキップ) */
    public static final String KEY_RECEIVING_CONSIGNOR = "ReceivingConsignor";

    /** Key(入荷ITFtoJan) */
    public static final String KEY_RECEIVING_ITFTOJAN = "ReceivingItfToJan";

    /** Key(入荷C/P初期モード) */
    public static final String KEY_RECEIVING_CP_MODE = "ReceivingCPMode";

    /** Key(入庫荷主スキップ) */
    public static final String KEY_STORAGE_CONSIGNOR = "StorageConsignor";

    /** Key(入庫ITFtoJan) */
    public static final String KEY_STORAGE_ITFTOJAN = "StorageItfToJan";

    /** Key(入庫C/P初期モード) */
    public static final String KEY_STORAGE_CP_MODE = "StorageCPMode";

    /** Key(入庫検品モード) */
    public static final String KEY_STORAGE_INSPECTION_MODE = "StorageInspectionMode";

    /** Key(出庫荷主スキップ) */
    public static final String KEY_RETRIEVAL_CONSIGNOR = "RetrievalConsignor";

    /** Key(出庫ITFtoJan) */
    public static final String KEY_RETRIEVAL_ITFTOJAN = "RetrievalItfToJan";

    /** Key(出庫C/P初期モード) */
    public static final String KEY_RETRIEVAL_CP_MODE = "RetrievalCPMode";

    /** Key(出庫検品モード) */
    public static final String KEY_RETRIEVAL_INSPECTION_MODE = "RetrievalInspectionMode";

    /** Key(仕分荷主スキップ) */
    public static final String KEY_SORTING_CONSIGNOR = "SortingConsignor";

    /** Key(仕分ITFtoJan) */
    public static final String KEY_SORTING_ITFTOJAN = "SortingItfToJan";

    /** Key(仕分C/P初期モード) */
    public static final String KEY_SORTING_CP_MODE = "SortingCPMode";

    /** Key(仕分検品モード) */
    public static final String KEY_SORTING_INSPECTION_MODE = "SortingInspectionMode";

    /** Key(出荷荷主スキップ) */
    public static final String KEY_SHIPPING_CONSIGNOR = "ShippingConsignor";

    /** Key(出荷ITFtoJan) */
    public static final String KEY_SHIPPING_ITFTOJAN = "ShippingItfToJan";

    /** Key(出荷C/P初期モード) */
    public static final String KEY_SHIPPING_CP_MODE = "ShippingCPMode";

    /** Key(移動出庫荷主スキップ) */
    public static final String KEY_MOVERETRIEVAL_CONSIGNOR = "MoveRetrievalConsignor";

    /** Key(移動出庫ITFtoJan) */
    public static final String KEY_MOVERETRIEVAL_ITFTOJAN = "MoveRetrievalItfToJan";

    /** Key(移動出庫C/P初期モード) */
    public static final String KEY_MOVERETRIEVAL_CP_MODE = "MoveRetrievalCPMode";

    /** Key(移動出庫検品モード) */
    public static final String KEY_MOVERETRIEVAL_INSPECTION_MODE = "MoveRetrievalInspectionMode";

    /** Key(移動入庫荷主スキップ) */
    public static final String KEY_MOVESTORAGE_CONSIGNOR = "MoveStorageConsignor";

    /** Key(移動入庫ITFtoJan) */
    public static final String KEY_MOVESTORAGE_ITFTOJAN = "MoveStorageItfToJan";

    /** Key(移動入庫C/P初期モード) */
    public static final String KEY_MOVESTORAGE_CP_MODE = "MoveStorageCPMode";

    /** Key(移動入庫検品モード) */
    public static final String KEY_MOVESTORAGE_INSPECTION_MODE = "MoveStorageInspectionMode";

    /** Key(棚卸荷主スキップ) */
    public static final String KEY_INVENTORY_CONSIGNOR = "InventoryConsignor";

    /** Key(棚卸ITFtoJan) */
    public static final String KEY_INVENTORY_ITFTOJAN = "InventoryItfToJan";

    /** Key(棚卸C/P初期モード) */
    public static final String KEY_INVENTORY_CP_MODE = "InventoryCPMode";

    /** Key(棚卸検品モード) */
    public static final String KEY_INVENTORY_INSPECTION_MODE = "InventoryInspectionMode";

    /** Key(予定外入庫荷主スキップ) */
    public static final String KEY_NOPLANSTORAGE_CONSIGNOR = "NoPlanStorageConsignor";

    /** Key(予定外入庫ITFtoJan) */
    public static final String KEY_NOPLANSTORAGE_ITFTOJAN = "NoPlanStorageItfToJan";

    /** Key(予定外入庫C/P初期モード) */
    public static final String KEY_NOPLANSTORAGE_CP_MODE = "NoPlanStorageCPMode";

    /** Key(予定外入庫検品モード) */
    public static final String KEY_NOPLANSTORAGE_INSPECTION_MODE = "NoPlanStorageInspectionMode";

    /** Key(予定外出庫荷主スキップ) */
    public static final String KEY_NOPLANRETRIEVAL_CONSIGNOR = "NoPlanRetrievalConsignor";

    /** Key(予定外出庫ITFtoJan) */
    public static final String KEY_NOPLANRETRIEVAL_ITFTOJAN = "NoPlanRetrievalItfToJan";

    /** Key(予定外出庫C/P初期モード) */
    public static final String KEY_NOPLANRETRIEVAL_CP_MODE = "NoPlanRetrievalCPMode";

    /** Key(予定外出庫検品モード) */
    public static final String KEY_NOPLANRETRIEVAL_INSPECTION_MODE = "NoPlanRetrievalInspectionMode";

    /** Key(DC入荷メニュー) */
    public static final String KEY_RECEIVING_MENU = "ReceivingMenu";
    
    /** Key(TC入荷メニュー) */
    public static final String KEY_TC_RECEIVING_MENU = "TcReceivingMenu";
    
    /** Key(DC入庫メニュー) */
    public static final String KEY_RECEIVING_STORAGE_MENU = "ReceivingStorageMenu";
    
    /** Key(入庫メニュー) */
    public static final String KEY_STORAGE_MENU = "StorageMenu";
    
    /** Key(出庫メニュー) */
    public static final String KEY_RETRIEVAL_MENU = "RetrievalMenu";
    
    /** Key(仕分メニュー) */
    public static final String KEY_SORTING_MENU = "SortingMenu";
    
    /** Key(出荷メニュー) */
    public static final String KEY_SHIPPING_MENU = "ShippingMenu";
    
    /** Key(出荷積込メニュー) */
    public static final String KEY_SHIPPING_LOAD_MENU = "ShippingLoadMenu";
    
    /** Key(予定外入庫メニュー) */
    public static final String KEY_NOPLAN_STORAGE_MENU = "NoPlanStorageMenu";
    
    /** Key(予定外出庫メニュー) */
    public static final String KEY_NOPLAN_RETRIEVAL_MENU = "NoPlanRetrievalMenu";
    
    /** Key(棚卸メニュー) */
    public static final String KEY_INVENTORY_MENU = "InventoryMenu";
    
    /** Key(移動出庫メニュー) */
    public static final String KEY_MOVE_RETRIEVAL_MENU = "MoveRetrievalMenu";
    
    /** Key(移動入庫メニュー) */
    public static final String KEY_MOVE_STORAGE_MENU = "MoveStorageMenu";
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(STORE_NAME) ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * カラム名リストを準備しインスタンスを生成します。
     */
    public RftSetting()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 号機No.(<code>RFT_NO</code>) に値をセットします。
     * @param value セットする値RFT_NO
     */
    public void setRftNo(String value)  // @@GEN_V3@@
    {
        setValue(RFT_NO, value);
    }

    /**
     * 号機No.(<code>RFT_NO</code>) から値を取得します。
     * @return RFT_NO
     */
    public String getRftNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RFT_NO, "")) ;
    }

    /**
     * パラメータ名(<code>KEY</code>) に値をセットします。
     * @param value セットする値KEY
     */
    public void setKey(String value)  // @@GEN_V3@@
    {
        setValue(KEY, value);
    }

    /**
     * パラメータ名(<code>KEY</code>) から値を取得します。
     * @return KEY
     */
    public String getKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(KEY, "")) ;
    }

    /**
     * 設定値(<code>VALUE</code>) に値をセットします。
     * @param value セットする値VALUE
     */
    public void setValue(String value)  // @@GEN_V3@@
    {
        setValue(VALUE, value);
    }

    /**
     * 設定値(<code>VALUE</code>) から値を取得します。
     * @return VALUE
     */
    public String getValue()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(VALUE, "")) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>) に値をセットします。
     * @param value セットする値REGIST_DATE
     */
    public void setRegistDate(Date value)  // @@GEN_V3@@
    {
        setValue(REGIST_DATE, value);
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>) から値を取得します。
     * @return REGIST_DATE
     */
    public Date getRegistDate()  // @@GEN_V3@@
    {
        return (Date)getValue(REGIST_DATE, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>) に値をセットします。
     * @param value セットする値REGIST_PNAME
     */
    public void setRegistPname(String value)  // @@GEN_V3@@
    {
        setValue(REGIST_PNAME, value);
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>) から値を取得します。
     * @return REGIST_PNAME
     */
    public String getRegistPname()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGIST_PNAME, "")) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>) に値をセットします。
     * @param value セットする値LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Date value)  // @@GEN_V3@@
    {
        setValue(LAST_UPDATE_DATE, value);
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>) から値を取得します。
     * @return LAST_UPDATE_DATE
     */
    public Date getLastUpdateDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LAST_UPDATE_DATE, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>) に値をセットします。
     * @param value セットする値LAST_UPDATE_PNAME
     */
    public void setLastUpdatePname(String value)  // @@GEN_V3@@
    {
        setValue(LAST_UPDATE_PNAME, value);
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>) から値を取得します。
     * @return LAST_UPDATE_PNAME
     */
    public String getLastUpdatePname()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LAST_UPDATE_PNAME, "")) ;
    }


    /**
     * ストアメタデータを返します。
     * @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData ;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
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
        return "$Id: RftSetting.java 2865 2009-01-21 04:54:08Z arai $" ;
    }
}
