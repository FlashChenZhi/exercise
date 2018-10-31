// $Id: Com_terminalAlterKey.java 87 2008-10-04 03:07:38Z admin $
// Handler v3.8
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * COM_TERMINAL用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class Com_terminalAlterKey
        extends DefaultSQLAlterKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this alter key */
    public static final StoreMetaData $storeMetaData = Com_terminal.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public Com_terminalAlterKey()
    {
        super(Com_terminal.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINALNUMBER</code>)<br>
     * 文字列の検索値を端末No.(<code>TERMINALNUMBER</code>)にセットします。
     */
    public void setTerminalnumber(String value)
    {
        setKey(Com_terminal.TERMINALNUMBER, value) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumber(String[] values)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumber(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalnumber(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalnumber(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumber(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalnumber(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の更新値をセットします。
     * @param value 端末No.(<code>TERMINALNUMBER</code>)更新値
     */
    public void updateTerminalnumber(String value)
    {
        setAdhocUpdateValue(Com_terminal.TERMINALNUMBER, value) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 端末名(<code>TERMINALNAME</code>)<br>
     * 文字列の検索値を端末名(<code>TERMINALNAME</code>)にセットします。
     */
    public void setTerminalname(String value)
    {
        setKey(Com_terminal.TERMINALNAME, value) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalname(String[] values)
    {
        setKey(Com_terminal.TERMINALNAME, values, true) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalname(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalname(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, values, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalname(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末名(<code>TERMINALNAME</code>)の更新値をセットします。
     * @param value 端末名(<code>TERMINALNAME</code>)更新値
     */
    public void updateTerminalname(String value)
    {
        setAdhocUpdateValue(Com_terminal.TERMINALNAME, value) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)<br>
     * 文字列の検索値を端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)にセットします。
     */
    public void setTerminaladdress(String value)
    {
        setKey(Com_terminal.TERMINALADDRESS, value) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdress(String[] values)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, true) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdress(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminaladdress(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, and_or_toNext) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminaladdress(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminaladdress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の更新値をセットします。
     * @param value 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)更新値
     */
    public void updateTerminaladdress(String value)
    {
        setAdhocUpdateValue(Com_terminal.TERMINALADDRESS, value) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value ロールID(<code>ROLEID</code>)<br>
     * 文字列の検索値をロールID(<code>ROLEID</code>)にセットします。
     */
    public void setRoleid(String value)
    {
        setKey(Com_terminal.ROLEID, value) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String[] values)
    {
        setKey(Com_terminal.ROLEID, values, true) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRoleid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, values, and_or_toNext) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRoleid(String value, String compcode)
    {
        setKey(Com_terminal.ROLEID, value, compcode, "", "", true) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロールID(<code>ROLEID</code>)の更新値をセットします。
     * @param value ロールID(<code>ROLEID</code>)更新値
     */
    public void updateRoleid(String value)
    {
        setAdhocUpdateValue(Com_terminal.ROLEID, value) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value プリンタ名(<code>PRINTERNAME</code>)<br>
     * 文字列の検索値をプリンタ名(<code>PRINTERNAME</code>)にセットします。
     */
    public void setPrintername(String value)
    {
        setKey(Com_terminal.PRINTERNAME, value) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintername(String[] values)
    {
        setKey(Com_terminal.PRINTERNAME, values, true) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintername(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPrintername(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, values, and_or_toNext) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPrintername(String value, String compcode)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, "", "", true) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintername(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPrintername(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * プリンタ名(<code>PRINTERNAME</code>)の更新値をセットします。
     * @param value プリンタ名(<code>PRINTERNAME</code>)更新値
     */
    public void updatePrintername(String value)
    {
        setAdhocUpdateValue(Com_terminal.PRINTERNAME, value) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)<br>
     * 数値の検索値を自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)にセットします。
     */
    public void setAutologinFlag(int value)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlag(int[] values)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlag(int[] values, String and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAutologinFlag(int[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAutologinFlag(int value, String compcode)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlag(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAutologinFlag(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の更新値をセットします。
     * @param value 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)更新値
     */
    public void updateAutologinFlag(int value)
    {
        setAdhocUpdateValue(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 更新日時(<code>UPDATE_DATE</code>)<br>
     * 日付の検索値を更新日時(<code>UPDATE_DATE</code>)にセットします。
     */
    public void setUpdateDate(Date value)
    {
        setKey(Com_terminal.UPDATE_DATE, value) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDate(Date[] values)
    {
        setKey(Com_terminal.UPDATE_DATE, values, true) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateDate(Date value, String compcode)
    {
        setKey(Com_terminal.UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新日時(<code>UPDATE_DATE</code>)の更新値をセットします。
     * @param value 更新日時(<code>UPDATE_DATE</code>)更新値
     */
    public void updateUpdateDate(Date value)
    {
        setAdhocUpdateValue(Com_terminal.UPDATE_DATE, value) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)<br>
     * 文字列の検索値を更新ユーザ(<code>UPDATE_USER</code>)にセットします。
     */
    public void setUpdateUser(String value)
    {
        setKey(Com_terminal.UPDATE_USER, value) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUser(String[] values)
    {
        setKey(Com_terminal.UPDATE_USER, values, true) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUser(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_USER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateUser(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_USER, values, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateUser(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_USER, value, compcode, "", "", true) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUser(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_USER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateUser(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_USER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の更新値をセットします。
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)更新値
     */
    public void updateUpdateUser(String value)
    {
        setAdhocUpdateValue(Com_terminal.UPDATE_USER, value) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)<br>
     * 文字列の検索値を更新端末IP(<code>UPDATE_TERMINAL</code>)にセットします。
     */
    public void setUpdateTerminal(String value)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminal(String[] values)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, values, true) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminal(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateTerminal(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, "", "", true) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminal(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateTerminal(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の更新値をセットします。
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)更新値
     */
    public void updateUpdateTerminal(String value)
    {
        setAdhocUpdateValue(Com_terminal.UPDATE_TERMINAL, value) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 数値の検索値を更新区分(<code>UPDATE_KIND</code>)にセットします。
     */
    public void setUpdateKind(int value)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(int[] values)
    {
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(int[] values, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateKind(int[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateKind(int value, String compcode)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateKind(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新区分(<code>UPDATE_KIND</code>)の更新値をセットします。
     * @param value 更新区分(<code>UPDATE_KIND</code>)更新値
     */
    public void updateUpdateKind(int value)
    {
        setAdhocUpdateValue(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)<br>
     * 文字列の検索値を更新処理名(<code>UPDATE_PROCESS</code>)にセットします。
     */
    public void setUpdateProcess(String value)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcess(String[] values)
    {
        setKey(Com_terminal.UPDATE_PROCESS, values, true) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcess(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_PROCESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateProcess(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_PROCESS, values, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateProcess(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, "", "", true) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcess(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateProcess(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の更新値をセットします。
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)更新値
     */
    public void updateUpdateProcess(String value)
    {
        setAdhocUpdateValue(Com_terminal.UPDATE_PROCESS, value) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateAutologinFlagWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_terminal.AUTOLOGIN_FLAG, source, addvalue);
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateUpdateKindWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_terminal.UPDATE_KIND, source, addvalue);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ストアメタデータを返します。
     * @return ストアメタデータ
     */
    @Override
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: Com_terminalAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
