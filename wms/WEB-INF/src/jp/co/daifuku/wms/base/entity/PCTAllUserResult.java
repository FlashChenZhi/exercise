// $Id: PCTAllUserResult.java 3213 2009-03-02 06:59:20Z arai $
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
 * PCTALLUSERRESULTのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTAllUserResult
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTALLUSERRESULT" ;

    /*
     * テーブル名: DNPCTALLUSERRESULT
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * エリア :                        AREA_NO             varchar2(4)
     * ランク :                        RANK                varchar2(1)
     * ロット作業基準値 :              LOT_STANDARD_VALUE  Number
     * オーダー作業基準値 :            ORDER_STANDARD_VALUENumber
     * 行作業基準値 :                  LINE_STANDARD_VALUE Number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (エリア(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (ランク(<code>RANK</code>)) */
    public static final FieldName RANK = new FieldName(STORE_NAME, "RANK") ;

    /** フィールド定義 (ロット作業基準値(<code>LOT_STANDARD_VALUE</code>)) */
    public static final FieldName LOT_STANDARD_VALUE = new FieldName(STORE_NAME, "LOT_STANDARD_VALUE") ;

    /** フィールド定義 (オーダー作業基準値(<code>ORDER_STANDARD_VALUE</code>)) */
    public static final FieldName ORDER_STANDARD_VALUE = new FieldName(STORE_NAME, "ORDER_STANDARD_VALUE") ;

    /** フィールド定義 (行作業基準値(<code>LINE_STANDARD_VALUE</code>)) */
    public static final FieldName LINE_STANDARD_VALUE = new FieldName(STORE_NAME, "LINE_STANDARD_VALUE") ;

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
    public PCTAllUserResult()
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
     * エリア(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリア(<code>AREA_NO</code>) から値を取得します。
     * @return AREA_NO
     */
    public String getAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NO, "")) ;
    }

    /**
     * ランク(<code>RANK</code>) に値をセットします。
     * @param value セットする値RANK
     */
    public void setRank(String value)  // @@GEN_V3@@
    {
        setValue(RANK, value);
    }

    /**
     * ランク(<code>RANK</code>) から値を取得します。
     * @return RANK
     */
    public String getRank()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RANK, "")) ;
    }

    /**
     * ロット作業基準値(<code>LOT_STANDARD_VALUE</code>) に値をセットします。
     * @param value セットする値LOT_STANDARD_VALUE
     */
    public void setLotStandardValue(double value)  // @@GEN_V3@@
    {
        setValue(LOT_STANDARD_VALUE, HandlerUtil.toObject(value));
    }

    /**
     * ロット作業基準値(<code>LOT_STANDARD_VALUE</code>) から値を取得します。
     * @return LOT_STANDARD_VALUE
     */
    public double getLotStandardValue()  // @@GEN_V3@@
    {
        return getBigDecimal(LOT_STANDARD_VALUE, BigDecimal.ZERO).doubleValue() ;
    }

    /**
     * オーダー作業基準値(<code>ORDER_STANDARD_VALUE</code>) に値をセットします。
     * @param value セットする値ORDER_STANDARD_VALUE
     */
    public void setOrderStandardValue(double value)  // @@GEN_V3@@
    {
        setValue(ORDER_STANDARD_VALUE, HandlerUtil.toObject(value));
    }

    /**
     * オーダー作業基準値(<code>ORDER_STANDARD_VALUE</code>) から値を取得します。
     * @return ORDER_STANDARD_VALUE
     */
    public double getOrderStandardValue()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_STANDARD_VALUE, BigDecimal.ZERO).doubleValue() ;
    }

    /**
     * 行作業基準値(<code>LINE_STANDARD_VALUE</code>) に値をセットします。
     * @param value セットする値LINE_STANDARD_VALUE
     */
    public void setLineStandardValue(double value)  // @@GEN_V3@@
    {
        setValue(LINE_STANDARD_VALUE, HandlerUtil.toObject(value));
    }

    /**
     * 行作業基準値(<code>LINE_STANDARD_VALUE</code>) から値を取得します。
     * @return LINE_STANDARD_VALUE
     */
    public double getLineStandardValue()  // @@GEN_V3@@
    {
        return getBigDecimal(LINE_STANDARD_VALUE, BigDecimal.ZERO).doubleValue() ;
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
        return "$Id: PCTAllUserResult.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
