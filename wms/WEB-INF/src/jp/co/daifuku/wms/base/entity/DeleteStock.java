// $Id: DeleteStock.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
 * DELETESTOCKのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class DeleteStock
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNDELETESTOCK" ;

    /*
     * テーブル名: DNDELETESTOCK
     * システム接続キー :              SYSTEM_CONN_KEY     varchar2(16)
     * 商品ｺｰﾄﾞ :                      ITEM_CODE           varchar2(40)
     * ﾛｯﾄNo. :                        LOT_NO              varchar2(60)
     * エリア :                        AREA_NO             varchar2(4)
     * 棚 :                            LOCATION_NO         varchar2(11)
     * 在庫数 :                        STOCK_QTY           number
     * 入庫日時 :                      STORAGE_DATE        date
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (システム接続キー(<code>SYSTEM_CONN_KEY</code>)) */
    public static final FieldName SYSTEM_CONN_KEY = new FieldName(STORE_NAME, "SYSTEM_CONN_KEY") ;

    /** フィールド定義 (商品ｺｰﾄﾞ(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (ﾛｯﾄNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (エリア(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (棚(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (在庫数(<code>STOCK_QTY</code>)) */
    public static final FieldName STOCK_QTY = new FieldName(STORE_NAME, "STOCK_QTY") ;

    /** フィールド定義 (入庫日時(<code>STORAGE_DATE</code>)) */
    public static final FieldName STORAGE_DATE = new FieldName(STORE_NAME, "STORAGE_DATE") ;

    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (登録処理名(<code>REGIST_PNAME</code>)) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;

    /** フィールド定義 (最終更新日時(<code>LAST_UPDATE_DATE</code>)) */
    public static final FieldName LAST_UPDATE_DATE = new FieldName(STORE_NAME, "LAST_UPDATE_DATE") ;

    /** フィールド定義 (最終更新処理名(<code>LAST_UPDATE_PNAME</code>)) */
    public static final FieldName LAST_UPDATE_PNAME = new FieldName(STORE_NAME, "LAST_UPDATE_PNAME") ;


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
    public DeleteStock()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>) に値をセットします。
     * @param value セットする値SYSTEM_CONN_KEY
     */
    public void setSystemConnKey(String value)  // @@GEN_V3@@
    {
        setValue(SYSTEM_CONN_KEY, value);
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>) から値を取得します。
     * @return SYSTEM_CONN_KEY
     */
    public String getSystemConnKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SYSTEM_CONN_KEY, "")) ;
    }

    /**
     * 商品ｺｰﾄﾞ(<code>ITEM_CODE</code>) に値をセットします。
     * @param value セットする値ITEM_CODE
     */
    public void setItemCode(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_CODE, value);
    }

    /**
     * 商品ｺｰﾄﾞ(<code>ITEM_CODE</code>) から値を取得します。
     * @return ITEM_CODE
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, "")) ;
    }

    /**
     * ﾛｯﾄNo.(<code>LOT_NO</code>) に値をセットします。
     * @param value セットする値LOT_NO
     */
    public void setLotNo(String value)  // @@GEN_V3@@
    {
        setValue(LOT_NO, value);
    }

    /**
     * ﾛｯﾄNo.(<code>LOT_NO</code>) から値を取得します。
     * @return LOT_NO
     */
    public String getLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOT_NO, "")) ;
    }

    /**
     * エリア(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリア(<code>AREA_NO</code>) から値を取得します。
     * @return AREA_NO
     */
    public String getAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NO, "")) ;
    }

    /**
     * 棚(<code>LOCATION_NO</code>) に値をセットします。
     * @param value セットする値LOCATION_NO
     */
    public void setLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO, value);
    }

    /**
     * 棚(<code>LOCATION_NO</code>) から値を取得します。
     * @return LOCATION_NO
     */
    public String getLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO, "")) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>) に値をセットします。
     * @param value セットする値STOCK_QTY
     */
    public void setStockQty(int value)  // @@GEN_V3@@
    {
        setValue(STOCK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>) から値を取得します。
     * @return STOCK_QTY
     */
    public int getStockQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STOCK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>) に値をセットします。
     * @param value セットする値STORAGE_DATE
     */
    public void setStorageDate(Date value)  // @@GEN_V3@@
    {
        setValue(STORAGE_DATE, value);
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>) から値を取得します。
     * @return STORAGE_DATE
     */
    public Date getStorageDate()  // @@GEN_V3@@
    {
        return (Date)getValue(STORAGE_DATE, null) ;
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
        return "$Id: DeleteStock.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
