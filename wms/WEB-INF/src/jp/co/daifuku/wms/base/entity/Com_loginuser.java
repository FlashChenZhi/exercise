// $Id: Com_loginuser.java 8061 2013-05-24 10:24:10Z kishimoto $
// $LastChangedRevision: 8061 $
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
 * COM_LOGINUSERのエンティティクラスです。
 *
 * @version $Revision: 8061 $, $Date: 2013-05-24 19:24:10 +0900 (金, 24 5 2013) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class Com_loginuser
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "COM_LOGINUSER" ;

    /*
     * テーブル名: COM_LOGINUSER
     * ユーザID :                      USERID              VARCHAR2(20)
     * パスワード :                    PASSWORD            VARCHAR2(20)
     * ユーザ名 :                      USERNAME            VARCHAR2(40)
     * ロール :                        ROLEID              VARCHAR2(10)
     * 仮パスワードフラグ :            DUMMYPASSWORD_FLAG  NUMBER
     * ユーザ状態 :                    USERSTATUS          NUMBER
     * パスワード有効期限 :            PWDEXPIRES          DATE
     * パスワード有効期間日数 :        PWDCHANGEINTERVAL   NUMBER
     * 最終ログイン日時 :              LASTACCESSDATE      DATE
     * 同一ユーザログイン最大数 :      SAMEUSERLOGINMAX    NUMBER
     * 認証ミス猶予回数 :              FAILEDLOGINATTEMPTS NUMBER
     * 認証ミス回数 :                  FAILEDCOUNT         NUMBER
     * 最終認証ミス日時 :              FAILEDSTARTDATE     DATE
     * 削除ステータス :                DELETESTATUS        NUMBER
     * 削除日付 :                      DELETE_DATE         DATE
     * 所属 :                          DEPARTMENT          VARCHAR2(100)
     * 備考 :                          REMARK              VARCHAR2(100)
     * 更新日時 :                      UPDATE_DATE         DATE
     * 更新ユーザ :                    UPDATE_USER         VARCHAR2(20)
     * 更新端末IP :                    UPDATE_TERMINAL     VARCHAR2(15)
     * 更新区分 :                      UPDATE_KIND         NUMBER
     * 更新処理名 :                    UPDATE_PROCESS      VARCHAR2(250)
     */
    /** フィールド定義 (ユーザID(<code>USERID</code>)) */
    public static final FieldName USERID = new FieldName(STORE_NAME, "USERID") ;

    /** フィールド定義 (パスワード(<code>PASSWORD</code>)) */
    public static final FieldName PASSWORD = new FieldName(STORE_NAME, "PASSWORD") ;

    /** フィールド定義 (ユーザ名(<code>USERNAME</code>)) */
    public static final FieldName USERNAME = new FieldName(STORE_NAME, "USERNAME") ;

    /** フィールド定義 (ロール(<code>ROLEID</code>)) */
    public static final FieldName ROLEID = new FieldName(STORE_NAME, "ROLEID") ;

    /** フィールド定義 (仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)) */
    public static final FieldName DUMMYPASSWORD_FLAG = new FieldName(STORE_NAME, "DUMMYPASSWORD_FLAG") ;

    /** フィールド定義 (ユーザ状態(<code>USERSTATUS</code>)) */
    public static final FieldName USERSTATUS = new FieldName(STORE_NAME, "USERSTATUS") ;

    /** フィールド定義 (パスワード有効期限(<code>PWDEXPIRES</code>)) */
    public static final FieldName PWDEXPIRES = new FieldName(STORE_NAME, "PWDEXPIRES") ;

    /** フィールド定義 (パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)) */
    public static final FieldName PWDCHANGEINTERVAL = new FieldName(STORE_NAME, "PWDCHANGEINTERVAL") ;

    /** フィールド定義 (最終ログイン日時(<code>LASTACCESSDATE</code>)) */
    public static final FieldName LASTACCESSDATE = new FieldName(STORE_NAME, "LASTACCESSDATE") ;

    /** フィールド定義 (同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)) */
    public static final FieldName SAMEUSERLOGINMAX = new FieldName(STORE_NAME, "SAMEUSERLOGINMAX") ;

    /** フィールド定義 (認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)) */
    public static final FieldName FAILEDLOGINATTEMPTS = new FieldName(STORE_NAME, "FAILEDLOGINATTEMPTS") ;

    /** フィールド定義 (認証ミス回数(<code>FAILEDCOUNT</code>)) */
    public static final FieldName FAILEDCOUNT = new FieldName(STORE_NAME, "FAILEDCOUNT") ;

    /** フィールド定義 (最終認証ミス日時(<code>FAILEDSTARTDATE</code>)) */
    public static final FieldName FAILEDSTARTDATE = new FieldName(STORE_NAME, "FAILEDSTARTDATE") ;

    /** フィールド定義 (削除ステータス(<code>DELETESTATUS</code>)) */
    public static final FieldName DELETESTATUS = new FieldName(STORE_NAME, "DELETESTATUS") ;

    /** フィールド定義 (削除日付(<code>DELETE_DATE</code>)) */
    public static final FieldName DELETE_DATE = new FieldName(STORE_NAME, "DELETE_DATE") ;

    /** フィールド定義 (所属(<code>DEPARTMENT</code>)) */
    public static final FieldName DEPARTMENT = new FieldName(STORE_NAME, "DEPARTMENT") ;

    /** フィールド定義 (備考(<code>REMARK</code>)) */
    public static final FieldName REMARK = new FieldName(STORE_NAME, "REMARK") ;

    /** フィールド定義 (更新日時(<code>UPDATE_DATE</code>)) */
    public static final FieldName UPDATE_DATE = new FieldName(STORE_NAME, "UPDATE_DATE") ;

    /** フィールド定義 (更新ユーザ(<code>UPDATE_USER</code>)) */
    public static final FieldName UPDATE_USER = new FieldName(STORE_NAME, "UPDATE_USER") ;

    /** フィールド定義 (更新端末IP(<code>UPDATE_TERMINAL</code>)) */
    public static final FieldName UPDATE_TERMINAL = new FieldName(STORE_NAME, "UPDATE_TERMINAL") ;

    /** フィールド定義 (更新区分(<code>UPDATE_KIND</code>)) */
    public static final FieldName UPDATE_KIND = new FieldName(STORE_NAME, "UPDATE_KIND") ;

    /** フィールド定義 (更新処理名(<code>UPDATE_PROCESS</code>)) */
    public static final FieldName UPDATE_PROCESS = new FieldName(STORE_NAME, "UPDATE_PROCESS") ;


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
    public Com_loginuser()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ユーザID(<code>USERID</code>) に値をセットします。
     * @param value セットする値USERID
     */
    public void setUserid(String value)  // @@GEN_V3@@
    {
        setValue(USERID, value);
    }

    /**
     * ユーザID(<code>USERID</code>) から値を取得します。
     * @return USERID
     */
    public String getUserid()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USERID, "")) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>) に値をセットします。
     * @param value セットする値PASSWORD
     */
    public void setPassword(String value)  // @@GEN_V3@@
    {
        setValue(PASSWORD, value);
    }

    /**
     * パスワード(<code>PASSWORD</code>) から値を取得します。
     * @return PASSWORD
     */
    public String getPassword()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PASSWORD, "")) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>) に値をセットします。
     * @param value セットする値USERNAME
     */
    public void setUsername(String value)  // @@GEN_V3@@
    {
        setValue(USERNAME, value);
    }

    /**
     * ユーザ名(<code>USERNAME</code>) から値を取得します。
     * @return USERNAME
     */
    public String getUsername()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USERNAME, "")) ;
    }

    /**
     * ロール(<code>ROLEID</code>) に値をセットします。
     * @param value セットする値ROLEID
     */
    public void setRoleid(String value)  // @@GEN_V3@@
    {
        setValue(ROLEID, value);
    }

    /**
     * ロール(<code>ROLEID</code>) から値を取得します。
     * @return ROLEID
     */
    public String getRoleid()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ROLEID, "")) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>) に値をセットします。
     * @param value セットする値DUMMYPASSWORD_FLAG
     */
    public void setDummypasswordFlag(int value)  // @@GEN_V3@@
    {
        setValue(DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value));
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>) から値を取得します。
     * @return DUMMYPASSWORD_FLAG
     */
    public int getDummypasswordFlag()  // @@GEN_V3@@
    {
        return getBigDecimal(DUMMYPASSWORD_FLAG, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>) に値をセットします。
     * @param value セットする値USERSTATUS
     */
    public void setUserstatus(int value)  // @@GEN_V3@@
    {
        setValue(USERSTATUS, HandlerUtil.toObject(value));
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>) から値を取得します。
     * @return USERSTATUS
     */
    public int getUserstatus()  // @@GEN_V3@@
    {
        return getBigDecimal(USERSTATUS, BigDecimal.ZERO).intValue() ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>) に値をセットします。
     * @param value セットする値PWDEXPIRES
     */
    public void setPwdexpires(Date value)  // @@GEN_V3@@
    {
        setValue(PWDEXPIRES, value);
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>) から値を取得します。
     * @return PWDEXPIRES
     */
    public Date getPwdexpires()  // @@GEN_V3@@
    {
        return (Date)getValue(PWDEXPIRES, null) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>) に値をセットします。
     * @param value セットする値PWDCHANGEINTERVAL
     */
    public void setPwdchangeinterval(int value)  // @@GEN_V3@@
    {
        setValue(PWDCHANGEINTERVAL, HandlerUtil.toObject(value));
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>) から値を取得します。
     * @return PWDCHANGEINTERVAL
     */
    public int getPwdchangeinterval()  // @@GEN_V3@@
    {
        return getBigDecimal(PWDCHANGEINTERVAL, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>) に値をセットします。
     * @param value セットする値LASTACCESSDATE
     */
    public void setLastaccessdate(Date value)  // @@GEN_V3@@
    {
        setValue(LASTACCESSDATE, value);
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>) から値を取得します。
     * @return LASTACCESSDATE
     */
    public Date getLastaccessdate()  // @@GEN_V3@@
    {
        return (Date)getValue(LASTACCESSDATE, null) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>) に値をセットします。
     * @param value セットする値SAMEUSERLOGINMAX
     */
    public void setSameuserloginmax(int value)  // @@GEN_V3@@
    {
        setValue(SAMEUSERLOGINMAX, HandlerUtil.toObject(value));
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>) から値を取得します。
     * @return SAMEUSERLOGINMAX
     */
    public int getSameuserloginmax()  // @@GEN_V3@@
    {
        return getBigDecimal(SAMEUSERLOGINMAX, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>) に値をセットします。
     * @param value セットする値FAILEDLOGINATTEMPTS
     */
    public void setFailedloginattempts(int value)  // @@GEN_V3@@
    {
        setValue(FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value));
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>) から値を取得します。
     * @return FAILEDLOGINATTEMPTS
     */
    public int getFailedloginattempts()  // @@GEN_V3@@
    {
        return getBigDecimal(FAILEDLOGINATTEMPTS, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>) に値をセットします。
     * @param value セットする値FAILEDCOUNT
     */
    public void setFailedcount(int value)  // @@GEN_V3@@
    {
        setValue(FAILEDCOUNT, HandlerUtil.toObject(value));
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>) から値を取得します。
     * @return FAILEDCOUNT
     */
    public int getFailedcount()  // @@GEN_V3@@
    {
        return getBigDecimal(FAILEDCOUNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>) に値をセットします。
     * @param value セットする値FAILEDSTARTDATE
     */
    public void setFailedstartdate(Date value)  // @@GEN_V3@@
    {
        setValue(FAILEDSTARTDATE, value);
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>) から値を取得します。
     * @return FAILEDSTARTDATE
     */
    public Date getFailedstartdate()  // @@GEN_V3@@
    {
        return (Date)getValue(FAILEDSTARTDATE, null) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>) に値をセットします。
     * @param value セットする値DELETESTATUS
     */
    public void setDeletestatus(int value)  // @@GEN_V3@@
    {
        setValue(DELETESTATUS, HandlerUtil.toObject(value));
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>) から値を取得します。
     * @return DELETESTATUS
     */
    public int getDeletestatus()  // @@GEN_V3@@
    {
        return getBigDecimal(DELETESTATUS, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>) に値をセットします。
     * @param value セットする値DELETE_DATE
     */
    public void setDeleteDate(Date value)  // @@GEN_V3@@
    {
        setValue(DELETE_DATE, value);
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>) から値を取得します。
     * @return DELETE_DATE
     */
    public Date getDeleteDate()  // @@GEN_V3@@
    {
        return (Date)getValue(DELETE_DATE, null) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>) に値をセットします。
     * @param value セットする値DEPARTMENT
     */
    public void setDepartment(String value)  // @@GEN_V3@@
    {
        setValue(DEPARTMENT, value);
    }

    /**
     * 所属(<code>DEPARTMENT</code>) から値を取得します。
     * @return DEPARTMENT
     */
    public String getDepartment()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DEPARTMENT, "")) ;
    }

    /**
     * 備考(<code>REMARK</code>) に値をセットします。
     * @param value セットする値REMARK
     */
    public void setRemark(String value)  // @@GEN_V3@@
    {
        setValue(REMARK, value);
    }

    /**
     * 備考(<code>REMARK</code>) から値を取得します。
     * @return REMARK
     */
    public String getRemark()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REMARK, "")) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>) に値をセットします。
     * @param value セットする値UPDATE_DATE
     */
    public void setUpdateDate(Date value)  // @@GEN_V3@@
    {
        setValue(UPDATE_DATE, value);
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>) から値を取得します。
     * @return UPDATE_DATE
     */
    public Date getUpdateDate()  // @@GEN_V3@@
    {
        return (Date)getValue(UPDATE_DATE, null) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>) に値をセットします。
     * @param value セットする値UPDATE_USER
     */
    public void setUpdateUser(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_USER, value);
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>) から値を取得します。
     * @return UPDATE_USER
     */
    public String getUpdateUser()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_USER, "")) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>) に値をセットします。
     * @param value セットする値UPDATE_TERMINAL
     */
    public void setUpdateTerminal(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_TERMINAL, value);
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>) から値を取得します。
     * @return UPDATE_TERMINAL
     */
    public String getUpdateTerminal()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_TERMINAL, "")) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>) に値をセットします。
     * @param value セットする値UPDATE_KIND
     */
    public void setUpdateKind(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_KIND, HandlerUtil.toObject(value));
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>) から値を取得します。
     * @return UPDATE_KIND
     */
    public int getUpdateKind()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_KIND, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>) に値をセットします。
     * @param value セットする値UPDATE_PROCESS
     */
    public void setUpdateProcess(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_PROCESS, value);
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>) から値を取得します。
     * @return UPDATE_PROCESS
     */
    public String getUpdateProcess()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_PROCESS, "")) ;
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
        return "$Id: Com_loginuser.java 8061 2013-05-24 10:24:10Z kishimoto $" ;
    }
}
