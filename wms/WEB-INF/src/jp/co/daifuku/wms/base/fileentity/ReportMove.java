// $Id: ReportMove.java 1545 2008-11-25 09:33:35Z dmori $
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
 * REPORTMOVEのエンティティクラスです。
 *
 * @version $Revision: 1545 $, $Date: 2008-11-25 18:33:35 +0900 (火, 25 11 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: dmori $
 */

public class ReportMove
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "REPORTMOVE";

    /*
     * ファイル名: REPORTMOVE
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(40)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * ロットNo. :                     LOT_NO              X(16)
     * 移動元エリア :                  RETRIEVAL_AREA_NO   X(4)
     * 移動元棚 :                      RETRIEVAL_LOCATION_NON(8)
     * 移動先エリア :                  STORAGE_AREA_NO     X(4)
     * 移動先棚 :                      STORAGE_LOCATION_NO N(8)
     * 移動実績数 :                    STORAGE_RESULT_QTY  9(7)
     * 移動実績日時 :                  STORAGE_WORK_DATE   N(14)
     * ユーザID :                      USER_ID             X(20)
     * 端末No.、RFTNo. :               TERMINAL_NO         X(4)
     */

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

    /** フィールド定義 (移動元エリア(<code>RETRIEVAL_AREA_NO</code>)) */
    public static final FieldName RETRIEVAL_AREA_NO = new FieldName(STORE_NAME, "RETRIEVAL_AREA_NO");

    /** フィールド定義 (移動元棚(<code>RETRIEVAL_LOCATION_NO</code>)) */
    public static final FieldName RETRIEVAL_LOCATION_NO = new FieldName(STORE_NAME, "RETRIEVAL_LOCATION_NO");

    /** フィールド定義 (移動先エリア(<code>STORAGE_AREA_NO</code>)) */
    public static final FieldName STORAGE_AREA_NO = new FieldName(STORE_NAME, "STORAGE_AREA_NO");

    /** フィールド定義 (移動先棚(<code>STORAGE_LOCATION_NO</code>)) */
    public static final FieldName STORAGE_LOCATION_NO = new FieldName(STORE_NAME, "STORAGE_LOCATION_NO");

    /** フィールド定義 (移動実績数(<code>STORAGE_RESULT_QTY</code>)) */
    public static final FieldName STORAGE_RESULT_QTY = new FieldName(STORE_NAME, "STORAGE_RESULT_QTY");

    /** フィールド定義 (移動実績日時(<code>STORAGE_WORK_DATE</code>)) */
    public static final FieldName STORAGE_WORK_DATE = new FieldName(STORE_NAME, "STORAGE_WORK_DATE");

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID");

    /** フィールド定義 (端末No.、RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO");


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
    public ReportMove()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 移動元エリア(<code>RETRIEVAL_AREA_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_AREA_NO
     */
    public void setRetrievalAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_AREA_NO, value);
    }

    /**
     * 移動元エリア(<code>RETRIEVAL_AREA_NO</code>) から値を取得します。
     * @return    移動元エリア
     */
    public String getRetrievalAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_AREA_NO, ""));
    }

    /**
     * 移動元棚(<code>RETRIEVAL_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_LOCATION_NO
     */
    public void setRetrievalLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_LOCATION_NO, value);
    }

    /**
     * 移動元棚(<code>RETRIEVAL_LOCATION_NO</code>) から値を取得します。
     * @return    移動元棚
     */
    public String getRetrievalLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_LOCATION_NO, ""));
    }

    /**
     * 移動先エリア(<code>STORAGE_AREA_NO</code>) に値をセットします。
     * @param value セットする値STORAGE_AREA_NO
     */
    public void setStorageAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_AREA_NO, value);
    }

    /**
     * 移動先エリア(<code>STORAGE_AREA_NO</code>) から値を取得します。
     * @return    移動先エリア
     */
    public String getStorageAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_AREA_NO, ""));
    }

    /**
     * 移動先棚(<code>STORAGE_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値STORAGE_LOCATION_NO
     */
    public void setStorageLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_LOCATION_NO, value);
    }

    /**
     * 移動先棚(<code>STORAGE_LOCATION_NO</code>) から値を取得します。
     * @return    移動先棚
     */
    public String getStorageLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_LOCATION_NO, ""));
    }

    /**
     * 移動実績数(<code>STORAGE_RESULT_QTY</code>) に値をセットします。
     * @param value セットする値STORAGE_RESULT_QTY
     */
    public void setStorageResultQty(int value)  // @@GEN_V3@@
    {
        setValue(STORAGE_RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 移動実績数(<code>STORAGE_RESULT_QTY</code>) から値を取得します。
     * @return    移動実績数
     */
    public int getStorageResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STORAGE_RESULT_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 移動実績日時(<code>STORAGE_WORK_DATE</code>) に値をセットします。
     * @param value セットする値STORAGE_WORK_DATE
     */
    public void setStorageWorkDate(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_WORK_DATE, value);
    }

    /**
     * 移動実績日時(<code>STORAGE_WORK_DATE</code>) から値を取得します。
     * @return    移動実績日時
     */
    public String getStorageWorkDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_WORK_DATE, ""));
    }

    /**
     * ユーザID(<code>USER_ID</code>) に値をセットします。
     * @param value セットする値USER_ID
     */
    public void setUserId(String value)  // @@GEN_V3@@
    {
        setValue(USER_ID, value);
    }

    /**
     * ユーザID(<code>USER_ID</code>) から値を取得します。
     * @return    ユーザID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, ""));
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return    端末No.、RFTNo.
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, ""));
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
        return "$Id: ReportMove.java 1545 2008-11-25 09:33:35Z dmori $";
    }
}
