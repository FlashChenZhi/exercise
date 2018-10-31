// $Id: Com_terminal.java 1544 2008-11-25 09:32:24Z dmori $
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
 * COM_TERMINALのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class Com_terminal
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "COM_TERMINAL" ;

    /*
     * テーブル名: COM_TERMINAL
     * 端末No. :                       TERMINALNUMBER      VARCHAR2(4)
     * 端末名 :                        TERMINALNAME        VARCHAR2(60)
     * 端末IPアドレスorマシン名 :      TERMINALADDRESS     VARCHAR2(64)
     * ロールID :                      ROLEID              VARCHAR2(10)
     * プリンタ名 :                    PRINTERNAME         VARCHAR2(60)
     * 自動ログインフラグ :            AUTOLOGIN_FLAG      NUMBER
     * 更新日時 :                      UPDATE_DATE         DATE
     * 更新ユーザ :                    UPDATE_USER         VARCHAR2(20)
     * 更新端末IP :                    UPDATE_TERMINAL     VARCHAR2(15)
     * 更新区分 :                      UPDATE_KIND         NUMBER
     * 更新処理名 :                    UPDATE_PROCESS      VARCHAR2(250)
     */

    /** フィールド定義 (端末No.(<code>TERMINALNUMBER</code>)) */
    public static final FieldName TERMINALNUMBER = new FieldName(STORE_NAME, "TERMINALNUMBER") ;

    /** フィールド定義 (端末名(<code>TERMINALNAME</code>)) */
    public static final FieldName TERMINALNAME = new FieldName(STORE_NAME, "TERMINALNAME") ;

    /** フィールド定義 (端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)) */
    public static final FieldName TERMINALADDRESS = new FieldName(STORE_NAME, "TERMINALADDRESS") ;

    /** フィールド定義 (ロールID(<code>ROLEID</code>)) */
    public static final FieldName ROLEID = new FieldName(STORE_NAME, "ROLEID") ;

    /** フィールド定義 (プリンタ名(<code>PRINTERNAME</code>)) */
    public static final FieldName PRINTERNAME = new FieldName(STORE_NAME, "PRINTERNAME") ;

    /** フィールド定義 (自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)) */
    public static final FieldName AUTOLOGIN_FLAG = new FieldName(STORE_NAME, "AUTOLOGIN_FLAG") ;

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
    public Com_terminal()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 端末No.(<code>TERMINALNUMBER</code>) に値をセットします。
     * @param value セットする値TERMINALNUMBER
     */
    public void setTerminalnumber(String value)  // @@GEN_V3@@
    {
        setValue(TERMINALNUMBER, value);
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>) から値を取得します。
     * @return TERMINALNUMBER
     */
    public String getTerminalnumber()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINALNUMBER, "")) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>) に値をセットします。
     * @param value セットする値TERMINALNAME
     */
    public void setTerminalname(String value)  // @@GEN_V3@@
    {
        setValue(TERMINALNAME, value);
    }

    /**
     * 端末名(<code>TERMINALNAME</code>) から値を取得します。
     * @return TERMINALNAME
     */
    public String getTerminalname()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINALNAME, "")) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>) に値をセットします。
     * @param value セットする値TERMINALADDRESS
     */
    public void setTerminaladdress(String value)  // @@GEN_V3@@
    {
        setValue(TERMINALADDRESS, value);
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>) から値を取得します。
     * @return TERMINALADDRESS
     */
    public String getTerminaladdress()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINALADDRESS, "")) ;
    }

    /**
     * ロールID(<code>ROLEID</code>) に値をセットします。
     * @param value セットする値ROLEID
     */
    public void setRoleid(String value)  // @@GEN_V3@@
    {
        setValue(ROLEID, value);
    }

    /**
     * ロールID(<code>ROLEID</code>) から値を取得します。
     * @return ROLEID
     */
    public String getRoleid()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ROLEID, "")) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>) に値をセットします。
     * @param value セットする値PRINTERNAME
     */
    public void setPrintername(String value)  // @@GEN_V3@@
    {
        setValue(PRINTERNAME, value);
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>) から値を取得します。
     * @return PRINTERNAME
     */
    public String getPrintername()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PRINTERNAME, "")) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>) に値をセットします。
     * @param value セットする値AUTOLOGIN_FLAG
     */
    public void setAutologinFlag(int value)  // @@GEN_V3@@
    {
        setValue(AUTOLOGIN_FLAG, HandlerUtil.toObject(value));
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>) から値を取得します。
     * @return AUTOLOGIN_FLAG
     */
    public int getAutologinFlag()  // @@GEN_V3@@
    {
        return getBigDecimal(AUTOLOGIN_FLAG, BigDecimal.ZERO).intValue() ;
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
        return "$Id: Com_terminal.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}
