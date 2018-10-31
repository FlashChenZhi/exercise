// $Id: PCTSystem.java 3213 2009-03-02 06:59:20Z arai $
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
 * PCTSYSTEMのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTSystem
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTSYSTEM" ;

    /*
     * テーブル名: DNPCTSYSTEM
     * ランクA基準値 :                 A_RANK_STANDARD_VALUEnumber
     * ランクB基準値 :                 B_RANK_STANDARD_VALUEnumber
     * ランク設定フラグ :              RANK_SETTING_FLAG   varchar2(1)
     * センター名 :                    CENTER_NAME         varchar2(40)
     * SeqNo.桁数 :                    SEQ_DIGIT           number
     * チェックディジット有無フラグ :  CHECK_DIGIT_FLAG    varchar2(1)
     * PCT商品マスタ取込フラグ :       PCTMASTER_LOAD_FLAG varchar2(1)
     * 初期重量誤差率 :                DEFULT_DISTINCT_RATEnumber
     * オーダー最大重量 :              ORDER_MAX_WEIGHT    number
     * ゾーン優先順位 :                ZONE_PRIORITY       number
     * 棚優先順位1 :                   LOC_PRIORITY_1      number
     * 棚優先順位2 :                   LOC_PRIORITY_2      number
     * 棚優先順位3 :                   LOC_PRIORITY_3      number
     * 棚優先順位4 :                   LOC_PRIORITY_4      number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)) */
    public static final FieldName A_RANK_STANDARD_VALUE = new FieldName(STORE_NAME, "A_RANK_STANDARD_VALUE") ;

    /** フィールド定義 (ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)) */
    public static final FieldName B_RANK_STANDARD_VALUE = new FieldName(STORE_NAME, "B_RANK_STANDARD_VALUE") ;

    /** フィールド定義 (ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)) */
    public static final FieldName RANK_SETTING_FLAG = new FieldName(STORE_NAME, "RANK_SETTING_FLAG") ;

    /** フィールド定義 (センター名(<code>CENTER_NAME</code>)) */
    public static final FieldName CENTER_NAME = new FieldName(STORE_NAME, "CENTER_NAME") ;

    /** フィールド定義 (SeqNo.桁数(<code>SEQ_DIGIT</code>)) */
    public static final FieldName SEQ_DIGIT = new FieldName(STORE_NAME, "SEQ_DIGIT") ;

    /** フィールド定義 (チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)) */
    public static final FieldName CHECK_DIGIT_FLAG = new FieldName(STORE_NAME, "CHECK_DIGIT_FLAG") ;

    /** フィールド定義 (PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)) */
    public static final FieldName PCTMASTER_LOAD_FLAG = new FieldName(STORE_NAME, "PCTMASTER_LOAD_FLAG") ;

    /** フィールド定義 (初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)) */
    public static final FieldName DEFULT_DISTINCT_RATE = new FieldName(STORE_NAME, "DEFULT_DISTINCT_RATE") ;

    /** フィールド定義 (オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)) */
    public static final FieldName ORDER_MAX_WEIGHT = new FieldName(STORE_NAME, "ORDER_MAX_WEIGHT") ;

    /** フィールド定義 (ゾーン優先順位(<code>ZONE_PRIORITY</code>)) */
    public static final FieldName ZONE_PRIORITY = new FieldName(STORE_NAME, "ZONE_PRIORITY") ;

    /** フィールド定義 (棚優先順位1(<code>LOC_PRIORITY_1</code>)) */
    public static final FieldName LOC_PRIORITY_1 = new FieldName(STORE_NAME, "LOC_PRIORITY_1") ;

    /** フィールド定義 (棚優先順位2(<code>LOC_PRIORITY_2</code>)) */
    public static final FieldName LOC_PRIORITY_2 = new FieldName(STORE_NAME, "LOC_PRIORITY_2") ;

    /** フィールド定義 (棚優先順位3(<code>LOC_PRIORITY_3</code>)) */
    public static final FieldName LOC_PRIORITY_3 = new FieldName(STORE_NAME, "LOC_PRIORITY_3") ;

    /** フィールド定義 (棚優先順位4(<code>LOC_PRIORITY_4</code>)) */
    public static final FieldName LOC_PRIORITY_4 = new FieldName(STORE_NAME, "LOC_PRIORITY_4") ;

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
    public PCTSystem()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>) に値をセットします。
     * @param value セットする値A_RANK_STANDARD_VALUE
     */
    public void setARankStandardValue(int value)  // @@GEN_V3@@
    {
        setValue(A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value));
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>) から値を取得します。
     * @return A_RANK_STANDARD_VALUE
     */
    public int getARankStandardValue()  // @@GEN_V3@@
    {
        return getBigDecimal(A_RANK_STANDARD_VALUE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>) に値をセットします。
     * @param value セットする値B_RANK_STANDARD_VALUE
     */
    public void setBRankStandardValue(int value)  // @@GEN_V3@@
    {
        setValue(B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value));
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>) から値を取得します。
     * @return B_RANK_STANDARD_VALUE
     */
    public int getBRankStandardValue()  // @@GEN_V3@@
    {
        return getBigDecimal(B_RANK_STANDARD_VALUE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>) に値をセットします。
     * @param value セットする値RANK_SETTING_FLAG
     */
    public void setRankSettingFlag(String value)  // @@GEN_V3@@
    {
        setValue(RANK_SETTING_FLAG, value);
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>) から値を取得します。
     * @return RANK_SETTING_FLAG
     */
    public String getRankSettingFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RANK_SETTING_FLAG, "")) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>) に値をセットします。
     * @param value セットする値CENTER_NAME
     */
    public void setCenterName(String value)  // @@GEN_V3@@
    {
        setValue(CENTER_NAME, value);
    }

    /**
     * センター名(<code>CENTER_NAME</code>) から値を取得します。
     * @return CENTER_NAME
     */
    public String getCenterName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CENTER_NAME, "")) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>) に値をセットします。
     * @param value セットする値SEQ_DIGIT
     */
    public void setSeqDigit(int value)  // @@GEN_V3@@
    {
        setValue(SEQ_DIGIT, HandlerUtil.toObject(value));
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>) から値を取得します。
     * @return SEQ_DIGIT
     */
    public int getSeqDigit()  // @@GEN_V3@@
    {
        return getBigDecimal(SEQ_DIGIT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>) に値をセットします。
     * @param value セットする値CHECK_DIGIT_FLAG
     */
    public void setCheckDigitFlag(String value)  // @@GEN_V3@@
    {
        setValue(CHECK_DIGIT_FLAG, value);
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>) から値を取得します。
     * @return CHECK_DIGIT_FLAG
     */
    public String getCheckDigitFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CHECK_DIGIT_FLAG, "")) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>) に値をセットします。
     * @param value セットする値PCTMASTER_LOAD_FLAG
     */
    public void setPctmasterLoadFlag(String value)  // @@GEN_V3@@
    {
        setValue(PCTMASTER_LOAD_FLAG, value);
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>) から値を取得します。
     * @return PCTMASTER_LOAD_FLAG
     */
    public String getPctmasterLoadFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PCTMASTER_LOAD_FLAG, "")) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>) に値をセットします。
     * @param value セットする値DEFULT_DISTINCT_RATE
     */
    public void setDefultDistinctRate(int value)  // @@GEN_V3@@
    {
        setValue(DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value));
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>) から値を取得します。
     * @return DEFULT_DISTINCT_RATE
     */
    public int getDefultDistinctRate()  // @@GEN_V3@@
    {
        return getBigDecimal(DEFULT_DISTINCT_RATE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>) に値をセットします。
     * @param value セットする値ORDER_MAX_WEIGHT
     */
    public void setOrderMaxWeight(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_MAX_WEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>) から値を取得します。
     * @return ORDER_MAX_WEIGHT
     */
    public int getOrderMaxWeight()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_MAX_WEIGHT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>) に値をセットします。
     * @param value セットする値ZONE_PRIORITY
     */
    public void setZonePriority(int value)  // @@GEN_V3@@
    {
        setValue(ZONE_PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>) から値を取得します。
     * @return ZONE_PRIORITY
     */
    public int getZonePriority()  // @@GEN_V3@@
    {
        return getBigDecimal(ZONE_PRIORITY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>) に値をセットします。
     * @param value セットする値LOC_PRIORITY_1
     */
    public void setLocPriority1(int value)  // @@GEN_V3@@
    {
        setValue(LOC_PRIORITY_1, HandlerUtil.toObject(value));
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>) から値を取得します。
     * @return LOC_PRIORITY_1
     */
    public int getLocPriority1()  // @@GEN_V3@@
    {
        return getBigDecimal(LOC_PRIORITY_1, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>) に値をセットします。
     * @param value セットする値LOC_PRIORITY_2
     */
    public void setLocPriority2(int value)  // @@GEN_V3@@
    {
        setValue(LOC_PRIORITY_2, HandlerUtil.toObject(value));
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>) から値を取得します。
     * @return LOC_PRIORITY_2
     */
    public int getLocPriority2()  // @@GEN_V3@@
    {
        return getBigDecimal(LOC_PRIORITY_2, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>) に値をセットします。
     * @param value セットする値LOC_PRIORITY_3
     */
    public void setLocPriority3(int value)  // @@GEN_V3@@
    {
        setValue(LOC_PRIORITY_3, HandlerUtil.toObject(value));
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>) から値を取得します。
     * @return LOC_PRIORITY_3
     */
    public int getLocPriority3()  // @@GEN_V3@@
    {
        return getBigDecimal(LOC_PRIORITY_3, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>) に値をセットします。
     * @param value セットする値LOC_PRIORITY_4
     */
    public void setLocPriority4(int value)  // @@GEN_V3@@
    {
        setValue(LOC_PRIORITY_4, HandlerUtil.toObject(value));
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>) から値を取得します。
     * @return LOC_PRIORITY_4
     */
    public int getLocPriority4()  // @@GEN_V3@@
    {
        return getBigDecimal(LOC_PRIORITY_4, BigDecimal.ZERO).intValue() ;
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
        return "$Id: PCTSystem.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
