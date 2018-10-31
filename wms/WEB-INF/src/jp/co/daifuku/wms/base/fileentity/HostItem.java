// $Id: HostItem.java 7218 2010-02-24 03:46:24Z kishimoto $
// $LastChangedRevision: 7218 $
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
 * HOSTITEMのエンティティクラスです。
 *
 * @version $Revision: 7218 $, $Date: 2010-02-24 12:46:24 +0900 (水, 24 2 2010) $
 * @author  shimizu
 * @author  Last commit: $Author: kishimoto $
 */

public class HostItem
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "HOSTITEM";

    /*
     * ファイル名: HOSTITEM
     * 取込区分 :                      LOAD_FLAG           N(1)
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(40)
     * ソフトゾーンID :                SOFT_ZONE_ID        X(3)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * 上限在庫数 :                    UPPER_QTY           9(6)
     * 下限在庫数 :                    LOWER_QTY           9(6)
     */

    /** フィールド定義 (取込区分(<code>LOAD_FLAG</code>)) */
    public static final FieldName LOAD_FLAG = new FieldName(STORE_NAME, "LOAD_FLAG");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 (ソフトゾーンID(<code>SOFT_ZONE_ID</code>)) */
    public static final FieldName SOFT_ZONE_ID = new FieldName(STORE_NAME, "SOFT_ZONE_ID");

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 (上限在庫数(<code>UPPER_QTY</code>)) */
    public static final FieldName UPPER_QTY = new FieldName(STORE_NAME, "UPPER_QTY");

    /** フィールド定義 (下限在庫数(<code>LOWER_QTY</code>)) */
    public static final FieldName LOWER_QTY = new FieldName(STORE_NAME, "LOWER_QTY");


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
    public HostItem()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取込区分(<code>LOAD_FLAG</code>) に値をセットします。
     * @param value セットする値LOAD_FLAG
     */
    public void setLoadFlag(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_FLAG, value);
    }

    /**
     * 取込区分(<code>LOAD_FLAG</code>) から値を取得します。
     * @return    取込区分
     */
    public String getLoadFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_FLAG, ""));
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
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>) に値をセットします。
     * @param value セットする値SOFT_ZONE_ID
     */
    public void setSoftZoneId(String value)  // @@GEN_V3@@
    {
        setValue(SOFT_ZONE_ID, value);
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>) から値を取得します。
     * @return    ソフトゾーンID
     */
    public String getSoftZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SOFT_ZONE_ID, ""));
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
     * 上限在庫数(<code>UPPER_QTY</code>) に値をセットします。
     * @param value セットする値UPPER_QTY
     */
    public void setUpperQty(int value)  // @@GEN_V3@@
    {
        setValue(UPPER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>) から値を取得します。
     * @return    上限在庫数
     */
    public int getUpperQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPPER_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>) に値をセットします。
     * @param value セットする値LOWER_QTY
     */
    public void setLowerQty(int value)  // @@GEN_V3@@
    {
        setValue(LOWER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>) から値を取得します。
     * @return    下限在庫数
     */
    public int getLowerQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOWER_QTY, BigDecimal.ZERO).intValue();
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
        return "$Id: HostItem.java 7218 2010-02-24 03:46:24Z kishimoto $";
    }
}
