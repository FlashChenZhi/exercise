// $Id: PCTHostItem.java 4129 2009-04-10 12:55:22Z okamura $
// $LastChangedRevision: 4129 $
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
 * PCTHOSTITEMのエンティティクラスです。
 *
 * @version $Revision: 4129 $, $Date: 2009-04-10 21:55:22 +0900 (金, 10 4 2009) $
 * @author  shimizu
 * @author  Last commit: $Author: okamura $
 */

public class PCTHostItem
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "PCTHOSTITEM";

    /*
     * ファイル名: PCTHOSTITEM
     * 取込区分 :                      LOAD_FLAG           N(1)
     * 荷主コード :                    CONSIGNOR_CODE      X(16)
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(50)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * ボールITF :                     BUNDLE_ITF          X(16)
     * ロット入数 :                    LOT_ENTERING_QTY    9(5)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * ボール入数 :                    BUNDLE_ENTERING_QTY 9(5)
     * 単位 :                          UNIT                9(6)
     * 単重量 :                        SINGLE_WEIGHT       9(6,1)
     * 誤差率 :                        WEIGHT_DISTINCT     9(2)
     * ロケーションNo.1 :              LOCATION_NO_1       X(8)
     * ロケーション入数1 :             ENTERING_QTY_1      9(5)
     * ロケーションNo.2 :              LOCATION_NO_2       X(8)
     * ロケーション入数2 :             ENTERING_QTY_2      9(5)
     * ロケーションNo.3 :              LOCATION_NO_3       X(8)
     * ロケーション入数3 :             ENTERING_QTY_3      9(5)
     * ロケーションNo.4 :              LOCATION_NO_4       X(8)
     * ロケーション入数4 :             ENTERING_QTY_4      9(5)
     * メッセージ :                    INFORMATION         A(50)
     * 賞味期間 :                      USE_BY_PERIOD       X(8)
     * 入荷限度日 :                    INSTOCK_LIMIT_DATE  N(8)
     * 出荷限度日 :                    SHIPPING_LIMIT_DATE N(8)
     * 最新賞味期限 :                  LATEST_USE_BY_DATE  N(8)
     * 最新製造日 :                    LATEST_MANUFACUTURE_DATEN(8)
     * 最新出庫日 :                    LATEST_RETRIEVAL_DATEN(8)
     * 最新在庫 :                      LATEST_STOCK        N(8)
     * 最古在庫 :                      OLDEST_STOCK        N(8)
     * 管理フラグ :                    MANAGEMENT_FLAG     X(1)
     * 上限在庫数 :                    UPPER_QTY           9(7)
     * 下限在庫数 :                    LOWER_QTY           9(7)
     */

    /** フィールド定義 (取込区分(<code>LOAD_FLAG</code>)) */
    public static final FieldName LOAD_FLAG = new FieldName(STORE_NAME, "LOAD_FLAG");

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 (ボールITF(<code>BUNDLE_ITF</code>)) */
    public static final FieldName BUNDLE_ITF = new FieldName(STORE_NAME, "BUNDLE_ITF");

    /** フィールド定義 (ロット入数(<code>LOT_ENTERING_QTY</code>)) */
    public static final FieldName LOT_ENTERING_QTY = new FieldName(STORE_NAME, "LOT_ENTERING_QTY");

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 (ボール入数(<code>BUNDLE_ENTERING_QTY</code>)) */
    public static final FieldName BUNDLE_ENTERING_QTY = new FieldName(STORE_NAME, "BUNDLE_ENTERING_QTY");

    /** フィールド定義 (単位(<code>UNIT</code>)) */
    public static final FieldName UNIT = new FieldName(STORE_NAME, "UNIT");

    /** フィールド定義 (単重量(<code>SINGLE_WEIGHT</code>)) */
    public static final FieldName SINGLE_WEIGHT = new FieldName(STORE_NAME, "SINGLE_WEIGHT");

    /** フィールド定義 (誤差率(<code>WEIGHT_DISTINCT</code>)) */
    public static final FieldName WEIGHT_DISTINCT = new FieldName(STORE_NAME, "WEIGHT_DISTINCT");

    /** フィールド定義 (ロケーションNo.1(<code>LOCATION_NO_1</code>)) */
    public static final FieldName LOCATION_NO_1 = new FieldName(STORE_NAME, "LOCATION_NO_1");

    /** フィールド定義 (ロケーション入数1(<code>ENTERING_QTY_1</code>)) */
    public static final FieldName ENTERING_QTY_1 = new FieldName(STORE_NAME, "ENTERING_QTY_1");

    /** フィールド定義 (ロケーションNo.2(<code>LOCATION_NO_2</code>)) */
    public static final FieldName LOCATION_NO_2 = new FieldName(STORE_NAME, "LOCATION_NO_2");

    /** フィールド定義 (ロケーション入数2(<code>ENTERING_QTY_2</code>)) */
    public static final FieldName ENTERING_QTY_2 = new FieldName(STORE_NAME, "ENTERING_QTY_2");

    /** フィールド定義 (ロケーションNo.3(<code>LOCATION_NO_3</code>)) */
    public static final FieldName LOCATION_NO_3 = new FieldName(STORE_NAME, "LOCATION_NO_3");

    /** フィールド定義 (ロケーション入数3(<code>ENTERING_QTY_3</code>)) */
    public static final FieldName ENTERING_QTY_3 = new FieldName(STORE_NAME, "ENTERING_QTY_3");

    /** フィールド定義 (ロケーションNo.4(<code>LOCATION_NO_4</code>)) */
    public static final FieldName LOCATION_NO_4 = new FieldName(STORE_NAME, "LOCATION_NO_4");

    /** フィールド定義 (ロケーション入数4(<code>ENTERING_QTY_4</code>)) */
    public static final FieldName ENTERING_QTY_4 = new FieldName(STORE_NAME, "ENTERING_QTY_4");

    /** フィールド定義 (メッセージ(<code>INFORMATION</code>)) */
    public static final FieldName INFORMATION = new FieldName(STORE_NAME, "INFORMATION");

    /** フィールド定義 (賞味期間(<code>USE_BY_PERIOD</code>)) */
    public static final FieldName USE_BY_PERIOD = new FieldName(STORE_NAME, "USE_BY_PERIOD");

    /** フィールド定義 (入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)) */
    public static final FieldName INSTOCK_LIMIT_DATE = new FieldName(STORE_NAME, "INSTOCK_LIMIT_DATE");

    /** フィールド定義 (出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)) */
    public static final FieldName SHIPPING_LIMIT_DATE = new FieldName(STORE_NAME, "SHIPPING_LIMIT_DATE");

    /** フィールド定義 (最新賞味期限(<code>LATEST_USE_BY_DATE</code>)) */
    public static final FieldName LATEST_USE_BY_DATE = new FieldName(STORE_NAME, "LATEST_USE_BY_DATE");

    /** フィールド定義 (最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)) */
    public static final FieldName LATEST_MANUFACUTURE_DATE = new FieldName(STORE_NAME, "LATEST_MANUFACUTURE_DATE");

    /** フィールド定義 (最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)) */
    public static final FieldName LATEST_RETRIEVAL_DATE = new FieldName(STORE_NAME, "LATEST_RETRIEVAL_DATE");

    /** フィールド定義 (最新在庫(<code>LATEST_STOCK</code>)) */
    public static final FieldName LATEST_STOCK = new FieldName(STORE_NAME, "LATEST_STOCK");

    /** フィールド定義 (最古在庫(<code>OLDEST_STOCK</code>)) */
    public static final FieldName OLDEST_STOCK = new FieldName(STORE_NAME, "OLDEST_STOCK");

    /** フィールド定義 (管理フラグ(<code>MANAGEMENT_FLAG</code>)) */
    public static final FieldName MANAGEMENT_FLAG = new FieldName(STORE_NAME, "MANAGEMENT_FLAG");

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
    public PCTHostItem()
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
     * 荷主コード(<code>CONSIGNOR_CODE</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_CODE
     */
    public void setConsignorCode(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_CODE, value);
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>) から値を取得します。
     * @return    荷主コード
     */
    public String getConsignorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, ""));
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
     * ボールITF(<code>BUNDLE_ITF</code>) に値をセットします。
     * @param value セットする値BUNDLE_ITF
     */
    public void setBundleItf(String value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ITF, value);
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>) から値を取得します。
     * @return    ボールITF
     */
    public String getBundleItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BUNDLE_ITF, ""));
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値LOT_ENTERING_QTY
     */
    public void setLotEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(LOT_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>) から値を取得します。
     * @return    ロット入数
     */
    public int getLotEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOT_ENTERING_QTY, BigDecimal.ZERO).intValue();
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
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値BUNDLE_ENTERING_QTY
     */
    public void setBundleEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) から値を取得します。
     * @return    ボール入数
     */
    public int getBundleEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(BUNDLE_ENTERING_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 単位(<code>UNIT</code>) に値をセットします。
     * @param value セットする値UNIT
     */
    public void setUnit(int value)  // @@GEN_V3@@
    {
        setValue(UNIT, HandlerUtil.toObject(value));
    }

    /**
     * 単位(<code>UNIT</code>) から値を取得します。
     * @return    単位
     */
    public int getUnit()  // @@GEN_V3@@
    {
        return getBigDecimal(UNIT, BigDecimal.ZERO).intValue();
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>) に値をセットします。
     * @param value セットする値SINGLE_WEIGHT
     */
    public void setSingleWeight(double value)  // @@GEN_V3@@
    {
        setValue(SINGLE_WEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>) から値を取得します。
     * @return    単重量
     */
    public double getSingleWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(SINGLE_WEIGHT, BigDecimal.ZERO).doubleValue();
    }

    /**
     * 誤差率(<code>WEIGHT_DISTINCT</code>) に値をセットします。
     * @param value セットする値WEIGHT_DISTINCT
     */
    public void setWeightDistinct(int value)  // @@GEN_V3@@
    {
        setValue(WEIGHT_DISTINCT, HandlerUtil.toObject(value));
    }

    /**
     * 誤差率(<code>WEIGHT_DISTINCT</code>) から値を取得します。
     * @return    誤差率
     */
    public int getWeightDistinct()  // @@GEN_V3@@
    {
        return getBigDecimal(WEIGHT_DISTINCT, BigDecimal.ZERO).intValue();
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>) に値をセットします。
     * @param value セットする値LOCATION_NO_1
     */
    public void setLocationNo1(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO_1, value);
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>) から値を取得します。
     * @return    ロケーションNo.1
     */
    public String getLocationNo1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_1, ""));
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY_1
     */
    public void setEnteringQty1(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY_1, HandlerUtil.toObject(value));
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>) から値を取得します。
     * @return    ロケーション入数1
     */
    public int getEnteringQty1()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_1, BigDecimal.ZERO).intValue();
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>) に値をセットします。
     * @param value セットする値LOCATION_NO_2
     */
    public void setLocationNo2(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO_2, value);
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>) から値を取得します。
     * @return    ロケーションNo.2
     */
    public String getLocationNo2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_2, ""));
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY_2
     */
    public void setEnteringQty2(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY_2, HandlerUtil.toObject(value));
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>) から値を取得します。
     * @return    ロケーション入数2
     */
    public int getEnteringQty2()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_2, BigDecimal.ZERO).intValue();
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>) に値をセットします。
     * @param value セットする値LOCATION_NO_3
     */
    public void setLocationNo3(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO_3, value);
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>) から値を取得します。
     * @return    ロケーションNo.3
     */
    public String getLocationNo3()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_3, ""));
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY_3
     */
    public void setEnteringQty3(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY_3, HandlerUtil.toObject(value));
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>) から値を取得します。
     * @return    ロケーション入数3
     */
    public int getEnteringQty3()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_3, BigDecimal.ZERO).intValue();
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>) に値をセットします。
     * @param value セットする値LOCATION_NO_4
     */
    public void setLocationNo4(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO_4, value);
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>) から値を取得します。
     * @return    ロケーションNo.4
     */
    public String getLocationNo4()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_4, ""));
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY_4
     */
    public void setEnteringQty4(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY_4, HandlerUtil.toObject(value));
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>) から値を取得します。
     * @return    ロケーション入数4
     */
    public int getEnteringQty4()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_4, BigDecimal.ZERO).intValue();
    }

    /**
     * メッセージ(<code>INFORMATION</code>) に値をセットします。
     * @param value セットする値INFORMATION
     */
    public void setInformation(String value)  // @@GEN_V3@@
    {
        setValue(INFORMATION, value);
    }

    /**
     * メッセージ(<code>INFORMATION</code>) から値を取得します。
     * @return    メッセージ
     */
    public String getInformation()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INFORMATION, ""));
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>) に値をセットします。
     * @param value セットする値USE_BY_PERIOD
     */
    public void setUseByPeriod(String value)  // @@GEN_V3@@
    {
        setValue(USE_BY_PERIOD, value);
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>) から値を取得します。
     * @return    賞味期間
     */
    public String getUseByPeriod()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USE_BY_PERIOD, ""));
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>) に値をセットします。
     * @param value セットする値INSTOCK_LIMIT_DATE
     */
    public void setInstockLimitDate(String value)  // @@GEN_V3@@
    {
        setValue(INSTOCK_LIMIT_DATE, value);
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>) から値を取得します。
     * @return    入荷限度日
     */
    public String getInstockLimitDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INSTOCK_LIMIT_DATE, ""));
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>) に値をセットします。
     * @param value セットする値SHIPPING_LIMIT_DATE
     */
    public void setShippingLimitDate(String value)  // @@GEN_V3@@
    {
        setValue(SHIPPING_LIMIT_DATE, value);
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>) から値を取得します。
     * @return    出荷限度日
     */
    public String getShippingLimitDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIPPING_LIMIT_DATE, ""));
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>) に値をセットします。
     * @param value セットする値LATEST_USE_BY_DATE
     */
    public void setLatestUseByDate(String value)  // @@GEN_V3@@
    {
        setValue(LATEST_USE_BY_DATE, value);
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>) から値を取得します。
     * @return    最新賞味期限
     */
    public String getLatestUseByDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_USE_BY_DATE, ""));
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>) に値をセットします。
     * @param value セットする値LATEST_MANUFACUTURE_DATE
     */
    public void setLatestManufacutureDate(String value)  // @@GEN_V3@@
    {
        setValue(LATEST_MANUFACUTURE_DATE, value);
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>) から値を取得します。
     * @return    最新製造日
     */
    public String getLatestManufacutureDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_MANUFACUTURE_DATE, ""));
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>) に値をセットします。
     * @param value セットする値LATEST_RETRIEVAL_DATE
     */
    public void setLatestRetrievalDate(String value)  // @@GEN_V3@@
    {
        setValue(LATEST_RETRIEVAL_DATE, value);
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>) から値を取得します。
     * @return    最新出庫日
     */
    public String getLatestRetrievalDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_RETRIEVAL_DATE, ""));
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>) に値をセットします。
     * @param value セットする値LATEST_STOCK
     */
    public void setLatestStock(String value)  // @@GEN_V3@@
    {
        setValue(LATEST_STOCK, value);
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>) から値を取得します。
     * @return    最新在庫
     */
    public String getLatestStock()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_STOCK, ""));
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>) に値をセットします。
     * @param value セットする値OLDEST_STOCK
     */
    public void setOldestStock(String value)  // @@GEN_V3@@
    {
        setValue(OLDEST_STOCK, value);
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>) から値を取得します。
     * @return    最古在庫
     */
    public String getOldestStock()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(OLDEST_STOCK, ""));
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>) に値をセットします。
     * @param value セットする値MANAGEMENT_FLAG
     */
    public void setManagementFlag(String value)  // @@GEN_V3@@
    {
        setValue(MANAGEMENT_FLAG, value);
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>) から値を取得します。
     * @return    管理フラグ
     */
    public String getManagementFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MANAGEMENT_FLAG, ""));
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
        return "$Id: PCTHostItem.java 4129 2009-04-10 12:55:22Z okamura $";
    }
}
