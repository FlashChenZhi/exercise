// $Id: Sorting.java 87 2008-10-04 03:07:38Z admin $
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
 * SORTINGのエンティティクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: admin $
 */

public class Sorting
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "SORTING";

    /*
     * ファイル名: SORTING
     * CANCEL_FLAG :                   取消区分                                N(1)
     * PLAN_DAY :                      仕分予定日                              N(8)
     * ITEM_CODE :                     商品コード                              X(16)
     * ITEM_NAME :                     商品名称                                A(40)
     * JAN :                           JANコード                               X(13)
     * ITF :                           ケースITF                               X(16)
     * ENTERING_QTY :                  ケース入数                              9(5)
     * SORTING_PLACE :                 仕分場所                                X(16)
     * CUSTOMER_CODE :                 出荷先コード                            X(16)
     * CUSTOMER_NAME :                 出荷先名称                              A(40)
     * SHIP_TICKET_NO :                出荷伝票No                              X(16)
     * SHIP_LINE_NO :                  出荷伝票行No                            9(3)
     * PLAN_QTY :                      仕分予定数                              9(6)
     * RECEIVE_TICKET_NO :             入荷伝票No                              X(16)
     * RECEIVE_LINE_NO :               入荷伝票行No                            9(3)
     */

    /** フィールド定義 取消区分(<code>CANCEL_FLAG</code>) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG");

    /** フィールド定義 仕分予定日(<code>PLAN_DAY</code>) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY");

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

    /** フィールド定義 仕分場所(<code>SORTING_PLACE</code>) */
    public static final FieldName SORTING_PLACE = new FieldName(STORE_NAME, "SORTING_PLACE");

    /** フィールド定義 出荷先コード(<code>CUSTOMER_CODE</code>) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE");

    /** フィールド定義 出荷先名称(<code>CUSTOMER_NAME</code>) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME");

    /** フィールド定義 出荷伝票No(<code>SHIP_TICKET_NO</code>) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO");

    /** フィールド定義 出荷伝票行No(<code>SHIP_LINE_NO</code>) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO");

    /** フィールド定義 仕分予定数(<code>PLAN_QTY</code>) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY");

    /** フィールド定義 入荷伝票No(<code>RECEIVE_TICKET_NO</code>) */
    public static final FieldName RECEIVE_TICKET_NO = new FieldName(STORE_NAME, "RECEIVE_TICKET_NO");

    /** フィールド定義 入荷伝票行No(<code>RECEIVE_LINE_NO</code>) */
    public static final FieldName RECEIVE_LINE_NO = new FieldName(STORE_NAME, "RECEIVE_LINE_NO");


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
    public Sorting()
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
     * 仕分予定日(<code>PLAN_DAY</code>)に値を設定します。
     * @param value 仕分予定日
     */
    public void setPlanDay(String value)
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 仕分予定日(<code>PLAN_DAY</code>)を取得します。
     * @return    仕分予定日
     */
    public String getPlanDay()
    {
        return String.valueOf(getValue(PLAN_DAY, ""));
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
     * 仕分場所(<code>SORTING_PLACE</code>)に値を設定します。
     * @param value 仕分場所
     */
    public void setSortingPlace(String value)
    {
        setValue(SORTING_PLACE, value);
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)を取得します。
     * @return    仕分場所
     */
    public String getSortingPlace()
    {
        return String.valueOf(getValue(SORTING_PLACE, ""));
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
     * 仕分予定数(<code>PLAN_QTY</code>)に値を設定します。
     * @param value 仕分予定数
     */
    public void setPlanQty(int value)
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 仕分予定数(<code>PLAN_QTY</code>)を取得します。
     * @return    仕分予定数
     */
    public int getPlanQty()
    {
        return getBigDecimal(PLAN_QTY, new BigDecimal(0)).intValue();
    }

    /**
     * 入荷伝票No(<code>RECEIVE_TICKET_NO</code>)に値を設定します。
     * @param value 入荷伝票No
     */
    public void setReceiveTicketNo(String value)
    {
        setValue(RECEIVE_TICKET_NO, value);
    }

    /**
     * 入荷伝票No(<code>RECEIVE_TICKET_NO</code>)を取得します。
     * @return    入荷伝票No
     */
    public String getReceiveTicketNo()
    {
        return String.valueOf(getValue(RECEIVE_TICKET_NO, ""));
    }

    /**
     * 入荷伝票行No(<code>RECEIVE_LINE_NO</code>)に値を設定します。
     * @param value 入荷伝票行No
     */
    public void setReceiveLineNo(int value)
    {
        setValue(RECEIVE_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 入荷伝票行No(<code>RECEIVE_LINE_NO</code>)を取得します。
     * @return    入荷伝票行No
     */
    public int getReceiveLineNo()
    {
        return getBigDecimal(RECEIVE_LINE_NO, new BigDecimal(0)).intValue();
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
        return "$Id: Sorting.java 87 2008-10-04 03:07:38Z admin $";
    }
}
