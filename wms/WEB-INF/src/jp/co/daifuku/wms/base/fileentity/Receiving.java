// $Id: Receiving.java 1545 2008-11-25 09:33:35Z dmori $
// $LastChangedRevision: 1545 $
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
 * RECEIVINGのエンティティクラスです。
 *
 * @version $Revision: 1545 $, $Date: 2008-11-25 18:33:35 +0900 (火, 25 11 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: dmori $
 */

public class Receiving
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "RECEIVING";

    /*
     * ファイル名: RECEIVING
     * 取消区分 :                      CANCEL_FLAG         N(1)
     * 入荷予定日 :                    PLAN_DAY            N(8)
     * 仕入先コード :                  SUPPLIER_CODE       X(16)
     * 仕入先名称 :                    SUPPLIER_NAME       A(40)
     * 入荷伝票No. :                   RECEIVE_TICKET_NO   X(16)
     * 入荷伝票行No. :                 RECEIVE_LINE_NO     9(3)
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(40)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * ロットNo. :                     LOT_NO              X(16)
     * 入荷予定数 :                    PLAN_QTY            9(6)
     */

    /** フィールド定義 (取消区分(<code>CANCEL_FLAG</code>)) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG");

    /** フィールド定義 (入荷予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY");

    /** フィールド定義 (仕入先コード(<code>SUPPLIER_CODE</code>)) */
    public static final FieldName SUPPLIER_CODE = new FieldName(STORE_NAME, "SUPPLIER_CODE");

    /** フィールド定義 (仕入先名称(<code>SUPPLIER_NAME</code>)) */
    public static final FieldName SUPPLIER_NAME = new FieldName(STORE_NAME, "SUPPLIER_NAME");

    /** フィールド定義 (入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)) */
    public static final FieldName RECEIVE_TICKET_NO = new FieldName(STORE_NAME, "RECEIVE_TICKET_NO");

    /** フィールド定義 (入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)) */
    public static final FieldName RECEIVE_LINE_NO = new FieldName(STORE_NAME, "RECEIVE_LINE_NO");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 (ロットNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO");

    /** フィールド定義 (入荷予定数(<code>PLAN_QTY</code>)) */
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
    public Receiving()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) に値をセットします。
     * @param value セットする値CANCEL_FLAG
     */
    public void setCancelFlag(String value)  // @@GEN_V3@@
    {
        setValue(CANCEL_FLAG, value);
    }

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) から値を取得します。
     * @return    取消区分
     */
    public String getCancelFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CANCEL_FLAG, ""));
    }

    /**
     * 入荷予定日(<code>PLAN_DAY</code>) に値をセットします。
     * @param value セットする値PLAN_DAY
     */
    public void setPlanDay(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 入荷予定日(<code>PLAN_DAY</code>) から値を取得します。
     * @return    入荷予定日
     */
    public String getPlanDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_DAY, ""));
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>) に値をセットします。
     * @param value セットする値SUPPLIER_CODE
     */
    public void setSupplierCode(String value)  // @@GEN_V3@@
    {
        setValue(SUPPLIER_CODE, value);
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>) から値を取得します。
     * @return    仕入先コード
     */
    public String getSupplierCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUPPLIER_CODE, ""));
    }

    /**
     * 仕入先名称(<code>SUPPLIER_NAME</code>) に値をセットします。
     * @param value セットする値SUPPLIER_NAME
     */
    public void setSupplierName(String value)  // @@GEN_V3@@
    {
        setValue(SUPPLIER_NAME, value);
    }

    /**
     * 仕入先名称(<code>SUPPLIER_NAME</code>) から値を取得します。
     * @return    仕入先名称
     */
    public String getSupplierName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUPPLIER_NAME, ""));
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>) に値をセットします。
     * @param value セットする値RECEIVE_TICKET_NO
     */
    public void setReceiveTicketNo(String value)  // @@GEN_V3@@
    {
        setValue(RECEIVE_TICKET_NO, value);
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>) から値を取得します。
     * @return    入荷伝票No.
     */
    public String getReceiveTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RECEIVE_TICKET_NO, ""));
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>) に値をセットします。
     * @param value セットする値RECEIVE_LINE_NO
     */
    public void setReceiveLineNo(int value)  // @@GEN_V3@@
    {
        setValue(RECEIVE_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>) から値を取得します。
     * @return    入荷伝票行No.
     */
    public int getReceiveLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(RECEIVE_LINE_NO, BigDecimal.ZERO).intValue();
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
     * @return    商品コード
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, ""));
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>) に値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_NAME, value);
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>) から値を取得します。
     * @return    商品名称
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, ""));
    }

    /**
     * JANコード(<code>JAN</code>) に値をセットします。
     * @param value セットする値JAN
     */
    public void setJan(String value)  // @@GEN_V3@@
    {
        setValue(JAN, value);
    }

    /**
     * JANコード(<code>JAN</code>) から値を取得します。
     * @return    JANコード
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, ""));
    }

    /**
     * ケースITF(<code>ITF</code>) に値をセットします。
     * @param value セットする値ITF
     */
    public void setItf(String value)  // @@GEN_V3@@
    {
        setValue(ITF, value);
    }

    /**
     * ケースITF(<code>ITF</code>) から値を取得します。
     * @return    ケースITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, ""));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY
     */
    public void setEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) から値を取得します。
     * @return    ケース入数
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * ロットNo.(<code>LOT_NO</code>) に値をセットします。
     * @param value セットする値LOT_NO
     */
    public void setLotNo(String value)  // @@GEN_V3@@
    {
        setValue(LOT_NO, value);
    }

    /**
     * ロットNo.(<code>LOT_NO</code>) から値を取得します。
     * @return    ロットNo.
     */
    public String getLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOT_NO, ""));
    }

    /**
     * 入荷予定数(<code>PLAN_QTY</code>) に値をセットします。
     * @param value セットする値PLAN_QTY
     */
    public void setPlanQty(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 入荷予定数(<code>PLAN_QTY</code>) から値を取得します。
     * @return    入荷予定数
     */
    public int getPlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_QTY, BigDecimal.ZERO).intValue();
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
        return "$Id: Receiving.java 1545 2008-11-25 09:33:35Z dmori $";
    }
}
