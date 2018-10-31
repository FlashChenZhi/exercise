// $Id: InventoryHist.java 5127 2009-10-13 12:20:06Z ota $
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
 * INVENTORYHISTのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class InventoryHist
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNINVENTORYHIST" ;

    /*
     * テーブル名: DNINVENTORYHIST
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * ロットNo :                      LOT_NO              varchar2(60)
     * 集計日 :                        SUMMARY_DATE        varchar2(8)
     * 当日在庫数 :                    STOCK_QTY           number
     * 当日入庫数 :                    STORAGE_QTY         number
     * 当日出庫数 :                    RETRIEVAL_QTY       number
     */
    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (ロットNo(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (集計日(<code>SUMMARY_DATE</code>)) */
    public static final FieldName SUMMARY_DATE = new FieldName(STORE_NAME, "SUMMARY_DATE") ;

    /** フィールド定義 (当日在庫数(<code>STOCK_QTY</code>)) */
    public static final FieldName STOCK_QTY = new FieldName(STORE_NAME, "STOCK_QTY") ;

    /** フィールド定義 (当日入庫数(<code>STORAGE_QTY</code>)) */
    public static final FieldName STORAGE_QTY = new FieldName(STORE_NAME, "STORAGE_QTY") ;

    /** フィールド定義 (当日出庫数(<code>RETRIEVAL_QTY</code>)) */
    public static final FieldName RETRIEVAL_QTY = new FieldName(STORE_NAME, "RETRIEVAL_QTY") ;


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
    public InventoryHist()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_CODE
     */
    public void setConsignorCode(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_CODE, value);
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>) から値を取得します。
     * @return CONSIGNOR_CODE
     */
    public String getConsignorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, "")) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>) に値をセットします。
     * @param value セットする値ITEM_CODE
     */
    public void setItemCode(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_CODE, value);
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>) から値を取得します。
     * @return ITEM_CODE
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, "")) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>) に値をセットします。
     * @param value セットする値LOT_NO
     */
    public void setLotNo(String value)  // @@GEN_V3@@
    {
        setValue(LOT_NO, value);
    }

    /**
     * ロットNo(<code>LOT_NO</code>) から値を取得します。
     * @return LOT_NO
     */
    public String getLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOT_NO, "")) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>) に値をセットします。
     * @param value セットする値SUMMARY_DATE
     */
    public void setSummaryDate(String value)  // @@GEN_V3@@
    {
        setValue(SUMMARY_DATE, value);
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>) から値を取得します。
     * @return SUMMARY_DATE
     */
    public String getSummaryDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUMMARY_DATE, "")) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>) に値をセットします。
     * @param value セットする値STOCK_QTY
     */
    public void setStockQty(int value)  // @@GEN_V3@@
    {
        setValue(STOCK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>) から値を取得します。
     * @return STOCK_QTY
     */
    public int getStockQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STOCK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>) に値をセットします。
     * @param value セットする値STORAGE_QTY
     */
    public void setStorageQty(int value)  // @@GEN_V3@@
    {
        setValue(STORAGE_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>) から値を取得します。
     * @return STORAGE_QTY
     */
    public int getStorageQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STORAGE_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_QTY
     */
    public void setRetrievalQty(int value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>) から値を取得します。
     * @return RETRIEVAL_QTY
     */
    public int getRetrievalQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RETRIEVAL_QTY, BigDecimal.ZERO).intValue() ;
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
        return "$Id: InventoryHist.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
