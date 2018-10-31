// $Id: ShippingHist.java 5127 2009-10-13 12:20:06Z ota $
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
 * SHIPPINGHISTのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class ShippingHist
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNSHIPPINGHIST" ;

    /*
     * テーブル名: DNSHIPPINGHIST
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 集計日 :                        SUMMARY_DATE        varchar2(8)
     * 当日出荷数 :                    SHIPPING_QTY        number
     * 伝票行数 :                      WORKING_QTY         number
     */
    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (集計日(<code>SUMMARY_DATE</code>)) */
    public static final FieldName SUMMARY_DATE = new FieldName(STORE_NAME, "SUMMARY_DATE") ;

    /** フィールド定義 (当日出荷数(<code>SHIPPING_QTY</code>)) */
    public static final FieldName SHIPPING_QTY = new FieldName(STORE_NAME, "SHIPPING_QTY") ;

    /** フィールド定義 (伝票行数(<code>WORKING_QTY</code>)) */
    public static final FieldName WORKING_QTY = new FieldName(STORE_NAME, "WORKING_QTY") ;


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
    public ShippingHist()
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
     * 出荷先コード(<code>CUSTOMER_CODE</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CODE
     */
    public void setCustomerCode(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CODE, value);
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>) から値を取得します。
     * @return CUSTOMER_CODE
     */
    public String getCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CODE, "")) ;
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
     * 当日出荷数(<code>SHIPPING_QTY</code>) に値をセットします。
     * @param value セットする値SHIPPING_QTY
     */
    public void setShippingQty(int value)  // @@GEN_V3@@
    {
        setValue(SHIPPING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 当日出荷数(<code>SHIPPING_QTY</code>) から値を取得します。
     * @return SHIPPING_QTY
     */
    public int getShippingQty()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIPPING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 伝票行数(<code>WORKING_QTY</code>) に値をセットします。
     * @param value セットする値WORKING_QTY
     */
    public void setWorkingQty(int value)  // @@GEN_V3@@
    {
        setValue(WORKING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 伝票行数(<code>WORKING_QTY</code>) から値を取得します。
     * @return WORKING_QTY
     */
    public int getWorkingQty()  // @@GEN_V3@@
    {
        return getBigDecimal(WORKING_QTY, BigDecimal.ZERO).intValue() ;
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
        return "$Id: ShippingHist.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
