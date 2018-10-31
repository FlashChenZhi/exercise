// $Id: Shipping.java 87 2008-10-04 03:07:38Z admin $
// Handler v3.8
package jp.co.daifuku.wms.base.fileentity;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.handler.util.HandlerUtil;


/**
 * SHIPPINGのエンティティクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: admin $
 */

public class Shipping
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "SHIPPING";

    /*
     * ファイル名: SHIPPING
     * CANCEL_FLAG :                   取消区分                                N(1)
     * PLAN_DAY :                      出荷予定日                              N(8)
     * CUSTOMER_CODE :                 出荷先コード                            X(16)
     * CUSTOMER_NAME :                 出荷先名称                              A(40)
     * SHIP_TICKET_NO :                出荷伝票No                              X(16)
     * SHIP_LINE_NO :                  出荷伝票行No                            9(3)
     * ITEM_CODE :                     商品コード                              X(16)
     * ITEM_NAME :                     商品名称                                A(40)
     * JAN :                           JANコード                               X(13)
     * ITF :                           ケースITF                               X(16)
     * ENTERING_QTY :                  ケース入数                              9(5)
     * LOT_NO :                        ロットNo.                               X(16)
     * PLAN_QTY :                      出荷予定数                              9(6)
     */

    /** フィールド定義 取消区分(<code>CANCEL_FLAG</code>) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG");

    /** フィールド定義 出荷予定日(<code>PLAN_DAY</code>) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY");

    /** フィールド定義 出荷先コード(<code>CUSTOMER_CODE</code>) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE");

    /** フィールド定義 出荷先名称(<code>CUSTOMER_NAME</code>) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME");

    /** フィールド定義 出荷伝票No(<code>SHIP_TICKET_NO</code>) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO");

    /** フィールド定義 出荷伝票行No(<code>SHIP_LINE_NO</code>) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO");

    /** フィールド定義 商品コード(<code>ITEM_CODE</code>) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 商品名称(<code>ITEM_NAME</code>) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 JANコード(<code>JAN</code>) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 ケースITF(<code>ITF</code>) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 ケース入数(<code>ENTERING_QTY</code>) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 ロットNo.(<code>LOT_NO</code>) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO");

    /** フィールド定義 出荷予定数(<code>PLAN_QTY</code>) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY");


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(
            STORE_NAME,
            new File(HandlerSysDefines.DEFINE_DIR, "filestores.xml"),    //    %%StoreMetadata
            new File(HandlerSysDefines.DEFINE_DIR, "filefields.xml")    //    %%FieldMetadata
            );

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     *  フィールド名リストを準備してインスタンスを生成します。
     */
    public Shipping()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取消区分(<code>CANCEL_FLAG</code>)に値を設定します。
     * @param value 取消区分
     */
    public void setCancelFlag(String value)
    {
        setValue(CANCEL_FLAG, value);
    }

    /**
     * 取消区分(<code>CANCEL_FLAG</code>)を取得します。
     * @return    取消区分
     */
    public String getCancelFlag()
    {
        return String.valueOf(getValue(CANCEL_FLAG, ""));
    }

    /**
     * 出荷予定日(<code>PLAN_DAY</code>)に値を設定します。
     * @param value 出荷予定日
     */
    public void setPlanDay(String value)
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 出荷予定日(<code>PLAN_DAY</code>)を取得します。
     * @return    出荷予定日
     */
    public String getPlanDay()
    {
        return String.valueOf(getValue(PLAN_DAY, ""));
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)に値を設定します。
     * @param value 出荷先コード
     */
    public void setCustomerCode(String value)
    {
        setValue(CUSTOMER_CODE, value);
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)を取得します。
     * @return    出荷先コード
     */
    public String getCustomerCode()
    {
        return String.valueOf(getValue(CUSTOMER_CODE, ""));
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)に値を設定します。
     * @param value 出荷先名称
     */
    public void setCustomerName(String value)
    {
        setValue(CUSTOMER_NAME, value);
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)を取得します。
     * @return    出荷先名称
     */
    public String getCustomerName()
    {
        return String.valueOf(getValue(CUSTOMER_NAME, ""));
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)に値を設定します。
     * @param value 出荷伝票No
     */
    public void setShipTicketNo(String value)
    {
        setValue(SHIP_TICKET_NO, value);
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)を取得します。
     * @return    出荷伝票No
     */
    public String getShipTicketNo()
    {
        return String.valueOf(getValue(SHIP_TICKET_NO, ""));
    }

    /**
     * 出荷伝票行No(<code>SHIP_LINE_NO</code>)に値を設定します。
     * @param value 出荷伝票行No
     */
    public void setShipLineNo(int value)
    {
        setValue(SHIP_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出荷伝票行No(<code>SHIP_LINE_NO</code>)を取得します。
     * @return    出荷伝票行No
     */
    public int getShipLineNo()
    {
        return getBigDecimal(SHIP_LINE_NO, new BigDecimal(0)).intValue();
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)に値を設定します。
     * @param value 商品コード
     */
    public void setItemCode(String value)
    {
        setValue(ITEM_CODE, value);
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)を取得します。
     * @return    商品コード
     */
    public String getItemCode()
    {
        return String.valueOf(getValue(ITEM_CODE, ""));
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)に値を設定します。
     * @param value 商品名称
     */
    public void setItemName(String value)
    {
        setValue(ITEM_NAME, value);
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)を取得します。
     * @return    商品名称
     */
    public String getItemName()
    {
        return String.valueOf(getValue(ITEM_NAME, ""));
    }

    /**
     * JANコード(<code>JAN</code>)に値を設定します。
     * @param value JANコード
     */
    public void setJan(String value)
    {
        setValue(JAN, value);
    }

    /**
     * JANコード(<code>JAN</code>)を取得します。
     * @return    JANコード
     */
    public String getJan()
    {
        return String.valueOf(getValue(JAN, ""));
    }

    /**
     * ケースITF(<code>ITF</code>)に値を設定します。
     * @param value ケースITF
     */
    public void setItf(String value)
    {
        setValue(ITF, value);
    }

    /**
     * ケースITF(<code>ITF</code>)を取得します。
     * @return    ケースITF
     */
    public String getItf()
    {
        return String.valueOf(getValue(ITF, ""));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)に値を設定します。
     * @param value ケース入数
     */
    public void setEnteringQty(int value)
    {
        setValue(ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)を取得します。
     * @return    ケース入数
     */
    public int getEnteringQty()
    {
        return getBigDecimal(ENTERING_QTY, new BigDecimal(0)).intValue();
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)に値を設定します。
     * @param value ロットNo.
     */
    public void setLotNo(String value)
    {
        setValue(LOT_NO, value);
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)を取得します。
     * @return    ロットNo.
     */
    public String getLotNo()
    {
        return String.valueOf(getValue(LOT_NO, ""));
    }

    /**
     * 出荷予定数(<code>PLAN_QTY</code>)に値を設定します。
     * @param value 出荷予定数
     */
    public void setPlanQty(int value)
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 出荷予定数(<code>PLAN_QTY</code>)を取得します。
     * @return    出荷予定数
     */
    public int getPlanQty()
    {
        return getBigDecimal(PLAN_QTY, new BigDecimal(0)).intValue();
    }


    /**
     *  ストアメタデータを返します。
     *  @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
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
     *  このクラスのリビジョンを返します。
     *  @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: Shipping.java 87 2008-10-04 03:07:38Z admin $";
    }
}
