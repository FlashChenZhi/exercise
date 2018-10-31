// $Id: WorkerResult.java 1544 2008-11-25 09:32:24Z dmori $
// $LastChangedRevision: 1544 $
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
 * WORKERRESULTのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class WorkerResult
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNWORKERRESULT" ;

    /*
     * テーブル名: DNWORKERRESULT
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 作業開始日時 :                  WORK_START_DATE     date
     * 作業終了日時 :                  WORK_END_DATE       date
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * RFTNo. :                        TERMINAL_NO         varchar2(3)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 作業数量 :                      WORK_QTY            number
     * 作業回数（明細数） :            WORK_CNT            number
     * 作業回数（作業オーダ数） :      ORDER_CNT           number
     * 作業時間（秒） :                WORK_TIME           number
     * 休憩時間（秒） :                REST_TIME           number
     * 実作業時間（秒） :              REAL_WORK_TIME      number
     * ミススキャン数 :                MISS_SCAN_CNT       number
     */

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (作業開始日時(<code>WORK_START_DATE</code>)) */
    public static final FieldName WORK_START_DATE = new FieldName(STORE_NAME, "WORK_START_DATE") ;

    /** フィールド定義 (作業終了日時(<code>WORK_END_DATE</code>)) */
    public static final FieldName WORK_END_DATE = new FieldName(STORE_NAME, "WORK_END_DATE") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (作業数量(<code>WORK_QTY</code>)) */
    public static final FieldName WORK_QTY = new FieldName(STORE_NAME, "WORK_QTY") ;

    /** フィールド定義 (作業回数（明細数）(<code>WORK_CNT</code>)) */
    public static final FieldName WORK_CNT = new FieldName(STORE_NAME, "WORK_CNT") ;

    /** フィールド定義 (作業回数（作業オーダ数）(<code>ORDER_CNT</code>)) */
    public static final FieldName ORDER_CNT = new FieldName(STORE_NAME, "ORDER_CNT") ;

    /** フィールド定義 (作業時間（秒）(<code>WORK_TIME</code>)) */
    public static final FieldName WORK_TIME = new FieldName(STORE_NAME, "WORK_TIME") ;

    /** フィールド定義 (休憩時間（秒）(<code>REST_TIME</code>)) */
    public static final FieldName REST_TIME = new FieldName(STORE_NAME, "REST_TIME") ;

    /** フィールド定義 (実作業時間（秒）(<code>REAL_WORK_TIME</code>)) */
    public static final FieldName REAL_WORK_TIME = new FieldName(STORE_NAME, "REAL_WORK_TIME") ;

    /** フィールド定義 (ミススキャン数(<code>MISS_SCAN_CNT</code>)) */
    public static final FieldName MISS_SCAN_CNT = new FieldName(STORE_NAME, "MISS_SCAN_CNT") ;


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
    public WorkerResult()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 作業日(<code>WORK_DAY</code>) に値をセットします。
     * @param value セットする値WORK_DAY
     */
    public void setWorkDay(String value)  // @@GEN_V3@@
    {
        setValue(WORK_DAY, value);
    }

    /**
     * 作業日(<code>WORK_DAY</code>) から値を取得します。
     * @return WORK_DAY
     */
    public String getWorkDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_DAY, "")) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>) に値をセットします。
     * @param value セットする値WORK_START_DATE
     */
    public void setWorkStartDate(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_START_DATE, value);
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>) から値を取得します。
     * @return WORK_START_DATE
     */
    public Date getWorkStartDate()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_START_DATE, null) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>) に値をセットします。
     * @param value セットする値WORK_END_DATE
     */
    public void setWorkEndDate(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_END_DATE, value);
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>) から値を取得します。
     * @return WORK_END_DATE
     */
    public Date getWorkEndDate()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_END_DATE, null) ;
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
     * RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>) に値をセットします。
     * @param value セットする値JOB_TYPE
     */
    public void setJobType(String value)  // @@GEN_V3@@
    {
        setValue(JOB_TYPE, value);
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>) から値を取得します。
     * @return JOB_TYPE
     */
    public String getJobType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_TYPE, "")) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>) に値をセットします。
     * @param value セットする値WORK_QTY
     */
    public void setWorkQty(int value)  // @@GEN_V3@@
    {
        setValue(WORK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 作業数量(<code>WORK_QTY</code>) から値を取得します。
     * @return WORK_QTY
     */
    public int getWorkQty()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>) に値をセットします。
     * @param value セットする値WORK_CNT
     */
    public void setWorkCnt(int value)  // @@GEN_V3@@
    {
        setValue(WORK_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>) から値を取得します。
     * @return WORK_CNT
     */
    public int getWorkCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>) に値をセットします。
     * @param value セットする値ORDER_CNT
     */
    public void setOrderCnt(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>) から値を取得します。
     * @return ORDER_CNT
     */
    public int getOrderCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>) に値をセットします。
     * @param value セットする値WORK_TIME
     */
    public void setWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>) から値を取得します。
     * @return WORK_TIME
     */
    public int getWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>) に値をセットします。
     * @param value セットする値REST_TIME
     */
    public void setRestTime(int value)  // @@GEN_V3@@
    {
        setValue(REST_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>) から値を取得します。
     * @return REST_TIME
     */
    public int getRestTime()  // @@GEN_V3@@
    {
        return getBigDecimal(REST_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>) に値をセットします。
     * @param value セットする値REAL_WORK_TIME
     */
    public void setRealWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(REAL_WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>) から値を取得します。
     * @return REAL_WORK_TIME
     */
    public int getRealWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(REAL_WORK_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>) に値をセットします。
     * @param value セットする値MISS_SCAN_CNT
     */
    public void setMissScanCnt(int value)  // @@GEN_V3@@
    {
        setValue(MISS_SCAN_CNT, HandlerUtil.toObject(value));
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>) から値を取得します。
     * @return MISS_SCAN_CNT
     */
    public int getMissScanCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(MISS_SCAN_CNT, BigDecimal.ZERO).intValue() ;
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
        return "$Id: WorkerResult.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}
