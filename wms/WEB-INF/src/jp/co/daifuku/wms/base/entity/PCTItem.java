// $Id: PCTItem.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
 * PCTITEMのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTItem
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMPCTITEM" ;

    /*
     * テーブル名: DMPCTITEM
     * システム管理区分 :              MANAGEMENT_TYPE     varchar2(1)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 商品コード :                    ITEM_CODE           varchar2(16)
     * 商品名称 :                      ITEM_NAME           varchar2(50)
     * JANコード :                     JAN                 varchar2(13)
     * ケースITF :                     ITF                 varchar2(16)
     * ボールITF :                     BUNDLE_ITF          varchar2(16)
     * ロット入数 :                    LOT_ENTERING_QTY    number
     * ケース入数 :                    ENTERING_QTY        number
     * ボール入数 :                    BUNDLE_ENTERING_QTY number
     * 単位 :                          UNIT                number
     * 単重量 :                        SINGLE_WEIGHT       number
     * 重量誤差率 :                    WEIGHT_DISTINCT_RATEnumber
     * 最大検品単位数 :                MAX_INSPECTION_UNIT_QTYnumber
     * ロケーションNo.1 :              LOCATION_NO_1       varchar2(8)
     * ロケーション入数1 :             ENTERING_QTY_1      number
     * ロケーションNo.2 :              LOCATION_NO_2       varchar2(8)
     * ロケーション入数2 :             ENTERING_QTY_2      number
     * ロケーションNo.3 :              LOCATION_NO_3       varchar2(8)
     * ロケーション入数3 :             ENTERING_QTY_3      number
     * ロケーションNo.4 :              LOCATION_NO_4       varchar2(8)
     * ロケーション入数4 :             ENTERING_QTY_4      number
     * メッセージ :                    INFORMATION         varchar2(50)
     * 賞味期間 :                      USE_BY_PERIOD       varchar2(8)
     * 入荷限度日 :                    INSTOCK_LIMIT_DATE  varchar2(8)
     * 出荷限度日 :                    SHIPPING_LIMIT_DATE varchar2(8)
     * 最新賞味期限 :                  LATEST_USE_BY_DATE  varchar2(8)
     * 最新製造日 :                    LATEST_MANUFACUTURE_DATEvarchar2(8)
     * 最新出庫日 :                    LATEST_RETRIEVAL_DATEvarchar2(8)
     * 最新在庫 :                      LATEST_STOCK        varchar2(8)
     * 最古在庫 :                      OLDEST_STOCK        varchar2(8)
     * 管理フラグ :                    MANAGEMENT_FLAG     varchar2(1)
     * 上限在庫数 :                    UPPER_QTY           number
     * 下限在庫数 :                    LOWER_QTY           number
     * 最終使用日 :                    LAST_USED_DATE      date
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (システム管理区分(<code>MANAGEMENT_TYPE</code>)) */
    public static final FieldName MANAGEMENT_TYPE = new FieldName(STORE_NAME, "MANAGEMENT_TYPE") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME") ;

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN") ;

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF") ;

    /** フィールド定義 (ボールITF(<code>BUNDLE_ITF</code>)) */
    public static final FieldName BUNDLE_ITF = new FieldName(STORE_NAME, "BUNDLE_ITF") ;

    /** フィールド定義 (ロット入数(<code>LOT_ENTERING_QTY</code>)) */
    public static final FieldName LOT_ENTERING_QTY = new FieldName(STORE_NAME, "LOT_ENTERING_QTY") ;

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY") ;

    /** フィールド定義 (ボール入数(<code>BUNDLE_ENTERING_QTY</code>)) */
    public static final FieldName BUNDLE_ENTERING_QTY = new FieldName(STORE_NAME, "BUNDLE_ENTERING_QTY") ;

    /** フィールド定義 (単位(<code>UNIT</code>)) */
    public static final FieldName UNIT = new FieldName(STORE_NAME, "UNIT") ;

    /** フィールド定義 (単重量(<code>SINGLE_WEIGHT</code>)) */
    public static final FieldName SINGLE_WEIGHT = new FieldName(STORE_NAME, "SINGLE_WEIGHT") ;

    /** フィールド定義 (重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)) */
    public static final FieldName WEIGHT_DISTINCT_RATE = new FieldName(STORE_NAME, "WEIGHT_DISTINCT_RATE") ;

    /** フィールド定義 (最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)) */
    public static final FieldName MAX_INSPECTION_UNIT_QTY = new FieldName(STORE_NAME, "MAX_INSPECTION_UNIT_QTY") ;

    /** フィールド定義 (ロケーションNo.1(<code>LOCATION_NO_1</code>)) */
    public static final FieldName LOCATION_NO_1 = new FieldName(STORE_NAME, "LOCATION_NO_1") ;

    /** フィールド定義 (ロケーション入数1(<code>ENTERING_QTY_1</code>)) */
    public static final FieldName ENTERING_QTY_1 = new FieldName(STORE_NAME, "ENTERING_QTY_1") ;

    /** フィールド定義 (ロケーションNo.2(<code>LOCATION_NO_2</code>)) */
    public static final FieldName LOCATION_NO_2 = new FieldName(STORE_NAME, "LOCATION_NO_2") ;

    /** フィールド定義 (ロケーション入数2(<code>ENTERING_QTY_2</code>)) */
    public static final FieldName ENTERING_QTY_2 = new FieldName(STORE_NAME, "ENTERING_QTY_2") ;

    /** フィールド定義 (ロケーションNo.3(<code>LOCATION_NO_3</code>)) */
    public static final FieldName LOCATION_NO_3 = new FieldName(STORE_NAME, "LOCATION_NO_3") ;

    /** フィールド定義 (ロケーション入数3(<code>ENTERING_QTY_3</code>)) */
    public static final FieldName ENTERING_QTY_3 = new FieldName(STORE_NAME, "ENTERING_QTY_3") ;

    /** フィールド定義 (ロケーションNo.4(<code>LOCATION_NO_4</code>)) */
    public static final FieldName LOCATION_NO_4 = new FieldName(STORE_NAME, "LOCATION_NO_4") ;

    /** フィールド定義 (ロケーション入数4(<code>ENTERING_QTY_4</code>)) */
    public static final FieldName ENTERING_QTY_4 = new FieldName(STORE_NAME, "ENTERING_QTY_4") ;

    /** フィールド定義 (メッセージ(<code>INFORMATION</code>)) */
    public static final FieldName INFORMATION = new FieldName(STORE_NAME, "INFORMATION") ;

    /** フィールド定義 (賞味期間(<code>USE_BY_PERIOD</code>)) */
    public static final FieldName USE_BY_PERIOD = new FieldName(STORE_NAME, "USE_BY_PERIOD") ;

    /** フィールド定義 (入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)) */
    public static final FieldName INSTOCK_LIMIT_DATE = new FieldName(STORE_NAME, "INSTOCK_LIMIT_DATE") ;

    /** フィールド定義 (出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)) */
    public static final FieldName SHIPPING_LIMIT_DATE = new FieldName(STORE_NAME, "SHIPPING_LIMIT_DATE") ;

    /** フィールド定義 (最新賞味期限(<code>LATEST_USE_BY_DATE</code>)) */
    public static final FieldName LATEST_USE_BY_DATE = new FieldName(STORE_NAME, "LATEST_USE_BY_DATE") ;

    /** フィールド定義 (最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)) */
    public static final FieldName LATEST_MANUFACUTURE_DATE = new FieldName(STORE_NAME, "LATEST_MANUFACUTURE_DATE") ;

    /** フィールド定義 (最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)) */
    public static final FieldName LATEST_RETRIEVAL_DATE = new FieldName(STORE_NAME, "LATEST_RETRIEVAL_DATE") ;

    /** フィールド定義 (最新在庫(<code>LATEST_STOCK</code>)) */
    public static final FieldName LATEST_STOCK = new FieldName(STORE_NAME, "LATEST_STOCK") ;

    /** フィールド定義 (最古在庫(<code>OLDEST_STOCK</code>)) */
    public static final FieldName OLDEST_STOCK = new FieldName(STORE_NAME, "OLDEST_STOCK") ;

    /** フィールド定義 (管理フラグ(<code>MANAGEMENT_FLAG</code>)) */
    public static final FieldName MANAGEMENT_FLAG = new FieldName(STORE_NAME, "MANAGEMENT_FLAG") ;

    /** フィールド定義 (上限在庫数(<code>UPPER_QTY</code>)) */
    public static final FieldName UPPER_QTY = new FieldName(STORE_NAME, "UPPER_QTY") ;

    /** フィールド定義 (下限在庫数(<code>LOWER_QTY</code>)) */
    public static final FieldName LOWER_QTY = new FieldName(STORE_NAME, "LOWER_QTY") ;

    /** フィールド定義 (最終使用日(<code>LAST_USED_DATE</code>)) */
    public static final FieldName LAST_USED_DATE = new FieldName(STORE_NAME, "LAST_USED_DATE") ;

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
    public PCTItem()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) に値をセットします。
     * @param value セットする値MANAGEMENT_TYPE
     */
    public void setManagementType(String value)  // @@GEN_V3@@
    {
        setValue(MANAGEMENT_TYPE, value);
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) から値を取得します。
     * @return MANAGEMENT_TYPE
     */
    public String getManagementType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MANAGEMENT_TYPE, "")) ;
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
     * @return CONSIGNOR_CODE
     */
    public String getConsignorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, "")) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_NAME
     */
    public void setConsignorName(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_NAME, value);
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>) から値を取得します。
     * @return CONSIGNOR_NAME
     */
    public String getConsignorName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_NAME, "")) ;
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
     * 商品名称(<code>ITEM_NAME</code>) に値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_NAME, value);
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>) から値を取得します。
     * @return ITEM_NAME
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, "")) ;
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
     * @return JAN
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, "")) ;
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
     * @return ITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, "")) ;
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
     * @return BUNDLE_ITF
     */
    public String getBundleItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BUNDLE_ITF, "")) ;
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
     * @return LOT_ENTERING_QTY
     */
    public int getLotEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOT_ENTERING_QTY, BigDecimal.ZERO).intValue() ;
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
     * @return ENTERING_QTY
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue() ;
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
     * @return BUNDLE_ENTERING_QTY
     */
    public int getBundleEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(BUNDLE_ENTERING_QTY, BigDecimal.ZERO).intValue() ;
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
     * @return UNIT
     */
    public int getUnit()  // @@GEN_V3@@
    {
        return getBigDecimal(UNIT, BigDecimal.ZERO).intValue() ;
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
     * @return SINGLE_WEIGHT
     */
    public double getSingleWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(SINGLE_WEIGHT, BigDecimal.ZERO).doubleValue() ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>) に値をセットします。
     * @param value セットする値WEIGHT_DISTINCT_RATE
     */
    public void setWeightDistinctRate(int value)  // @@GEN_V3@@
    {
        setValue(WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value));
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>) から値を取得します。
     * @return WEIGHT_DISTINCT_RATE
     */
    public int getWeightDistinctRate()  // @@GEN_V3@@
    {
        return getBigDecimal(WEIGHT_DISTINCT_RATE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>) に値をセットします。
     * @param value セットする値MAX_INSPECTION_UNIT_QTY
     */
    public void setMaxInspectionUnitQty(int value)  // @@GEN_V3@@
    {
        setValue(MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>) から値を取得します。
     * @return MAX_INSPECTION_UNIT_QTY
     */
    public int getMaxInspectionUnitQty()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_INSPECTION_UNIT_QTY, BigDecimal.ZERO).intValue() ;
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
     * @return LOCATION_NO_1
     */
    public String getLocationNo1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_1, "")) ;
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
     * @return ENTERING_QTY_1
     */
    public int getEnteringQty1()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_1, BigDecimal.ZERO).intValue() ;
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
     * @return LOCATION_NO_2
     */
    public String getLocationNo2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_2, "")) ;
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
     * @return ENTERING_QTY_2
     */
    public int getEnteringQty2()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_2, BigDecimal.ZERO).intValue() ;
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
     * @return LOCATION_NO_3
     */
    public String getLocationNo3()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_3, "")) ;
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
     * @return ENTERING_QTY_3
     */
    public int getEnteringQty3()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_3, BigDecimal.ZERO).intValue() ;
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
     * @return LOCATION_NO_4
     */
    public String getLocationNo4()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO_4, "")) ;
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
     * @return ENTERING_QTY_4
     */
    public int getEnteringQty4()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY_4, BigDecimal.ZERO).intValue() ;
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
     * @return INFORMATION
     */
    public String getInformation()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INFORMATION, "")) ;
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
     * @return USE_BY_PERIOD
     */
    public String getUseByPeriod()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USE_BY_PERIOD, "")) ;
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
     * @return INSTOCK_LIMIT_DATE
     */
    public String getInstockLimitDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INSTOCK_LIMIT_DATE, "")) ;
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
     * @return SHIPPING_LIMIT_DATE
     */
    public String getShippingLimitDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIPPING_LIMIT_DATE, "")) ;
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
     * @return LATEST_USE_BY_DATE
     */
    public String getLatestUseByDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_USE_BY_DATE, "")) ;
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
     * @return LATEST_MANUFACUTURE_DATE
     */
    public String getLatestManufacutureDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_MANUFACUTURE_DATE, "")) ;
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
     * @return LATEST_RETRIEVAL_DATE
     */
    public String getLatestRetrievalDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_RETRIEVAL_DATE, "")) ;
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
     * @return LATEST_STOCK
     */
    public String getLatestStock()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LATEST_STOCK, "")) ;
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
     * @return OLDEST_STOCK
     */
    public String getOldestStock()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(OLDEST_STOCK, "")) ;
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
     * @return MANAGEMENT_FLAG
     */
    public String getManagementFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MANAGEMENT_FLAG, "")) ;
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
     * @return UPPER_QTY
     */
    public int getUpperQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPPER_QTY, BigDecimal.ZERO).intValue() ;
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
     * @return LOWER_QTY
     */
    public int getLowerQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOWER_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>) に値をセットします。
     * @param value セットする値LAST_USED_DATE
     */
    public void setLastUsedDate(Date value)  // @@GEN_V3@@
    {
        setValue(LAST_USED_DATE, value);
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>) から値を取得します。
     * @return LAST_USED_DATE
     */
    public Date getLastUsedDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LAST_USED_DATE, null) ;
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
        return "$Id: PCTItem.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
