// $Id: WeightDistinct.java 3213 2009-03-02 06:59:20Z arai $
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
 * WEIGHTDISTINCTのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class WeightDistinct
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNWEIGHTDISTINCT" ;

    /*
     * テーブル名: DNWEIGHTDISTINCT
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 商品コード :                    ITEM_CODE           varchar2(16)
     * 商品名称 :                      ITEM_NAME           varchar2(50)
     * 端末No.、RFTNo. :               TERMINAL_NO         varchar2(8)
     * 間口No. :                       LOCATION_NO         number
     * 作業No. :                       JOB_NO              varchar2(8)
     * ロット入数 :                    LOT_ENTERING_QTY    number
     * マスタ単重量 :                  SINGLE_WEIGHT       number
     * 測定重量 :                      INSPECT_WEIGHT      number
     * 測定数量 :                      INSPECT_QTY         number
     * 訂正数量 :                      CORRECT_QTY         number
     * 訂正単重量 :                    CORRECT_WEIGHT      number
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME") ;

    /** フィールド定義 (端末No.、RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (間口No.(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (ロット入数(<code>LOT_ENTERING_QTY</code>)) */
    public static final FieldName LOT_ENTERING_QTY = new FieldName(STORE_NAME, "LOT_ENTERING_QTY") ;

    /** フィールド定義 (マスタ単重量(<code>SINGLE_WEIGHT</code>)) */
    public static final FieldName SINGLE_WEIGHT = new FieldName(STORE_NAME, "SINGLE_WEIGHT") ;

    /** フィールド定義 (測定重量(<code>INSPECT_WEIGHT</code>)) */
    public static final FieldName INSPECT_WEIGHT = new FieldName(STORE_NAME, "INSPECT_WEIGHT") ;

    /** フィールド定義 (測定数量(<code>INSPECT_QTY</code>)) */
    public static final FieldName INSPECT_QTY = new FieldName(STORE_NAME, "INSPECT_QTY") ;

    /** フィールド定義 (訂正数量(<code>CORRECT_QTY</code>)) */
    public static final FieldName CORRECT_QTY = new FieldName(STORE_NAME, "CORRECT_QTY") ;

    /** フィールド定義 (訂正単重量(<code>CORRECT_WEIGHT</code>)) */
    public static final FieldName CORRECT_WEIGHT = new FieldName(STORE_NAME, "CORRECT_WEIGHT") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

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
    public WeightDistinct()
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
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 間口No.(<code>LOCATION_NO</code>) に値をセットします。
     * @param value セットする値LOCATION_NO
     */
    public void setLocationNo(int value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO, HandlerUtil.toObject(value));
    }

    /**
     * 間口No.(<code>LOCATION_NO</code>) から値を取得します。
     * @return LOCATION_NO
     */
    public int getLocationNo()  // @@GEN_V3@@
    {
        return getBigDecimal(LOCATION_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>) に値をセットします。
     * @param value セットする値JOB_NO
     */
    public void setJobNo(String value)  // @@GEN_V3@@
    {
        setValue(JOB_NO, value);
    }

    /**
     * 作業No.(<code>JOB_NO</code>) から値を取得します。
     * @return JOB_NO
     */
    public String getJobNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_NO, "")) ;
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
     * マスタ単重量(<code>SINGLE_WEIGHT</code>) に値をセットします。
     * @param value セットする値SINGLE_WEIGHT
     */
    public void setSingleWeight(double value)  // @@GEN_V3@@
    {
        setValue(SINGLE_WEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * マスタ単重量(<code>SINGLE_WEIGHT</code>) から値を取得します。
     * @return SINGLE_WEIGHT
     */
    public double getSingleWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(SINGLE_WEIGHT, BigDecimal.ZERO).doubleValue() ;
    }

    /**
     * 測定重量(<code>INSPECT_WEIGHT</code>) に値をセットします。
     * @param value セットする値INSPECT_WEIGHT
     */
    public void setInspectWeight(int value)  // @@GEN_V3@@
    {
        setValue(INSPECT_WEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * 測定重量(<code>INSPECT_WEIGHT</code>) から値を取得します。
     * @return INSPECT_WEIGHT
     */
    public int getInspectWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(INSPECT_WEIGHT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 測定数量(<code>INSPECT_QTY</code>) に値をセットします。
     * @param value セットする値INSPECT_QTY
     */
    public void setInspectQty(int value)  // @@GEN_V3@@
    {
        setValue(INSPECT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 測定数量(<code>INSPECT_QTY</code>) から値を取得します。
     * @return INSPECT_QTY
     */
    public int getInspectQty()  // @@GEN_V3@@
    {
        return getBigDecimal(INSPECT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 訂正数量(<code>CORRECT_QTY</code>) に値をセットします。
     * @param value セットする値CORRECT_QTY
     */
    public void setCorrectQty(int value)  // @@GEN_V3@@
    {
        setValue(CORRECT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 訂正数量(<code>CORRECT_QTY</code>) から値を取得します。
     * @return CORRECT_QTY
     */
    public int getCorrectQty()  // @@GEN_V3@@
    {
        return getBigDecimal(CORRECT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 訂正単重量(<code>CORRECT_WEIGHT</code>) に値をセットします。
     * @param value セットする値CORRECT_WEIGHT
     */
    public void setCorrectWeight(double value)  // @@GEN_V3@@
    {
        setValue(CORRECT_WEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * 訂正単重量(<code>CORRECT_WEIGHT</code>) から値を取得します。
     * @return CORRECT_WEIGHT
     */
    public double getCorrectWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(CORRECT_WEIGHT, BigDecimal.ZERO).doubleValue() ;
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
     * @return USER_ID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, "")) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) に値をセットします。
     * @param value セットする値USER_NAME
     */
    public void setUserName(String value)  // @@GEN_V3@@
    {
        setValue(USER_NAME, value);
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) から値を取得します。
     * @return USER_NAME
     */
    public String getUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_NAME, "")) ;
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
        return "$Id: WeightDistinct.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
