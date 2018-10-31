// $Id: PCTUserResult.java 3213 2009-03-02 06:59:20Z arai $
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
 * PCTUSERRESULTのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTUserResult
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTUSERRESULT" ;

    /*
     * テーブル名: DNPCTUSERRESULT
     * 作業日 :                        WORK_DATE           varchar2(8)
     * 曜日 :                          DAY_OF_WEEK         number
     * 作業開始日時 :                  WORK_STARTTIME      date
     * 作業終了日時 :                  WORK_ENDTIME        date
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(3)
     * 実績区分 :                      RESULT_TYPE         varchar2(1)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 完了区分 :                      COMPLETE_KIND       varchar2(1)
     * 作業時間(秒) :                  WORK_TIME           number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (作業日(<code>WORK_DATE</code>)) */
    public static final FieldName WORK_DATE = new FieldName(STORE_NAME, "WORK_DATE") ;

    /** フィールド定義 (曜日(<code>DAY_OF_WEEK</code>)) */
    public static final FieldName DAY_OF_WEEK = new FieldName(STORE_NAME, "DAY_OF_WEEK") ;

    /** フィールド定義 (作業開始日時(<code>WORK_STARTTIME</code>)) */
    public static final FieldName WORK_STARTTIME = new FieldName(STORE_NAME, "WORK_STARTTIME") ;

    /** フィールド定義 (作業終了日時(<code>WORK_ENDTIME</code>)) */
    public static final FieldName WORK_ENDTIME = new FieldName(STORE_NAME, "WORK_ENDTIME") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (実績区分(<code>RESULT_TYPE</code>)) */
    public static final FieldName RESULT_TYPE = new FieldName(STORE_NAME, "RESULT_TYPE") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (完了区分(<code>COMPLETE_KIND</code>)) */
    public static final FieldName COMPLETE_KIND = new FieldName(STORE_NAME, "COMPLETE_KIND") ;

    /** フィールド定義 (作業時間(秒)(<code>WORK_TIME</code>)) */
    public static final FieldName WORK_TIME = new FieldName(STORE_NAME, "WORK_TIME") ;

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
    public PCTUserResult()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 作業日(<code>WORK_DATE</code>) に値をセットします。
     * @param value セットする値WORK_DATE
     */
    public void setWorkDate(String value)  // @@GEN_V3@@
    {
        setValue(WORK_DATE, value);
    }

    /**
     * 作業日(<code>WORK_DATE</code>) から値を取得します。
     * @return WORK_DATE
     */
    public String getWorkDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_DATE, "")) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>) に値をセットします。
     * @param value セットする値DAY_OF_WEEK
     */
    public void setDayOfWeek(int value)  // @@GEN_V3@@
    {
        setValue(DAY_OF_WEEK, HandlerUtil.toObject(value));
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>) から値を取得します。
     * @return DAY_OF_WEEK
     */
    public int getDayOfWeek()  // @@GEN_V3@@
    {
        return getBigDecimal(DAY_OF_WEEK, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業開始日時(<code>WORK_STARTTIME</code>) に値をセットします。
     * @param value セットする値WORK_STARTTIME
     */
    public void setWorkStarttime(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_STARTTIME, value);
    }

    /**
     * 作業開始日時(<code>WORK_STARTTIME</code>) から値を取得します。
     * @return WORK_STARTTIME
     */
    public Date getWorkStarttime()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_STARTTIME, null) ;
    }

    /**
     * 作業終了日時(<code>WORK_ENDTIME</code>) に値をセットします。
     * @param value セットする値WORK_ENDTIME
     */
    public void setWorkEndtime(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_ENDTIME, value);
    }

    /**
     * 作業終了日時(<code>WORK_ENDTIME</code>) から値を取得します。
     * @return WORK_ENDTIME
     */
    public Date getWorkEndtime()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_ENDTIME, null) ;
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
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 実績区分(<code>RESULT_TYPE</code>) に値をセットします。
     * @param value セットする値RESULT_TYPE
     */
    public void setResultType(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_TYPE, value);
    }

    /**
     * 実績区分(<code>RESULT_TYPE</code>) から値を取得します。
     * @return RESULT_TYPE
     */
    public String getResultType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_TYPE, "")) ;
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
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>) に値をセットします。
     * @param value セットする値SETTING_UNIT_KEY
     */
    public void setSettingUnitKey(String value)  // @@GEN_V3@@
    {
        setValue(SETTING_UNIT_KEY, value);
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>) から値を取得します。
     * @return SETTING_UNIT_KEY
     */
    public String getSettingUnitKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SETTING_UNIT_KEY, "")) ;
    }

    /**
     * 完了区分(<code>COMPLETE_KIND</code>) に値をセットします。
     * @param value セットする値COMPLETE_KIND
     */
    public void setCompleteKind(String value)  // @@GEN_V3@@
    {
        setValue(COMPLETE_KIND, value);
    }

    /**
     * 完了区分(<code>COMPLETE_KIND</code>) から値を取得します。
     * @return COMPLETE_KIND
     */
    public String getCompleteKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(COMPLETE_KIND, "")) ;
    }

    /**
     * 作業時間(秒)(<code>WORK_TIME</code>) に値をセットします。
     * @param value セットする値WORK_TIME
     */
    public void setWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 作業時間(秒)(<code>WORK_TIME</code>) から値を取得します。
     * @return WORK_TIME
     */
    public int getWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_TIME, BigDecimal.ZERO).intValue() ;
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
        return "$Id: PCTUserResult.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
