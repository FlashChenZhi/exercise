// $Id: PCTOperationLogAlterKey.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTOPERATIONLOG用の更新キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTOperationLogAlterKey
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
    public static final StoreMetaData $storeMetaData = PCTOperationLog.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PCTOperationLogAlterKey()
    {
        super(PCTOperationLog.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 出力日時(<code>LOG_DATE</code>)<br>
     * 日付の検索値を出力日時(<code>LOG_DATE</code>)にセットします。
     */
    public void setLogDate(Date value)
    {
        setKey(PCTOperationLog.LOG_DATE, value) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLogDate(Date[] values)
    {
        setKey(PCTOperationLog.LOG_DATE, values, true) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLogDate(Date[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(PCTOperationLog.LOG_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
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
    public void setLogDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLogDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(<code>LOG_DATE</code>)の更新値をセットします。
     * @param value 出力日時(<code>LOG_DATE</code>)更新値
     */
    public void updateLogDate(Date value)
    {
        setAdhocUpdateValue(PCTOperationLog.LOG_DATE, value) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値を出力日時(GMT)(<code>LOG_DATE_GMT</code>)にセットします。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateGmt(Date[] values)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, values, true) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateGmt(Date[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, "", "", true) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
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
    public void setLogDateGmt(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLogDateGmt(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の更新値をセットします。
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)更新値
     */
    public void updateLogDateGmt(Date value)
    {
        setAdhocUpdateValue(PCTOperationLog.LOG_DATE_GMT, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(PCTOperationLog.USER_ID, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserId(String[] values)
    {
        setKey(PCTOperationLog.USER_ID, values, true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserId(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(PCTOperationLog.USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
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
    public void setUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.USER_ID, value) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(PCTOperationLog.USER_NAME, value) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserName(String[] values)
    {
        setKey(PCTOperationLog.USER_NAME, values, true) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserName(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(PCTOperationLog.USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
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
    public void setUserName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.USER_NAME, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NUMBER</code>)<br>
     * 文字列の検索値を端末No.(<code>TERMINAL_NUMBER</code>)にセットします。
     */
    public void setTerminalNumber(String value)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumber(String[] values)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumber(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNumber(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNumber(String value, String compcode)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
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
    public void setTerminalNumber(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNumber(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の更新値をセットします。
     * @param value 端末No.(<code>TERMINAL_NUMBER</code>)更新値
     */
    public void updateTerminalNumber(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.TERMINAL_NUMBER, value) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値を端末名(<code>TERMINAL_NAME</code>)にセットします。
     */
    public void setTerminalName(String value)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalName(String[] values)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, values, true) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalName(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末名(<code>TERMINAL_NAME</code>)の更新値をセットします。
     * @param value 端末名(<code>TERMINAL_NAME</code>)更新値
     */
    public void updateTerminalName(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.TERMINAL_NAME, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IPADDRESS</code>)<br>
     * 文字列の検索値をIPアドレス(<code>IPADDRESS</code>)にセットします。
     */
    public void setIpaddress(String value)
    {
        setKey(PCTOperationLog.IPADDRESS, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values)
    {
        setKey(PCTOperationLog.IPADDRESS, values, true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setIpaddress(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setIpaddress(String value, String compcode)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
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
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * IPアドレス(<code>IPADDRESS</code>)の更新値をセットします。
     * @param value IPアドレス(<code>IPADDRESS</code>)更新値
     */
    public void updateIpaddress(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.IPADDRESS, value) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をDS番号(<code>DS_NO</code>)にセットします。
     */
    public void setDsNo(String value)
    {
        setKey(PCTOperationLog.DS_NO, value) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDsNo(String[] values)
    {
        setKey(PCTOperationLog.DS_NO, values, true) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDsNo(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(PCTOperationLog.DS_NO, value, compcode, "", "", true) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
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
    public void setDsNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDsNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * DS番号(<code>DS_NO</code>)の更新値をセットします。
     * @param value DS番号(<code>DS_NO</code>)更新値
     */
    public void updateDsNo(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.DS_NO, value) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)にセットします。
     */
    public void setPagenameresourcekey(String value)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekey(String[] values)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, true) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekey(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPagenameresourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPagenameresourcekey(String value, String compcode)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
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
    public void setPagenameresourcekey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPagenameresourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の更新値をセットします。
     * @param value ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)更新値
     */
    public void updatePagenameresourcekey(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)<br>
     * 数値の検索値を操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)にセットします。
     */
    public void setOperationType(int value)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationType(int[] values)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationType(int[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOperationType(int[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOperationType(int value, String compcode)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
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
    public void setOperationType(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOperationType(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の更新値をセットします。
     * @param value 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)更新値
     */
    public void updateOperationType(int value)
    {
        setAdhocUpdateValue(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 詳細(<code>DETAIL</code>)<br>
     * 文字列の検索値を詳細(<code>DETAIL</code>)にセットします。
     */
    public void setDetail(String value)
    {
        setKey(PCTOperationLog.DETAIL, value) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDetail(String[] values)
    {
        setKey(PCTOperationLog.DETAIL, values, true) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDetail(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDetail(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, values, and_or_toNext) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDetail(String value, String compcode)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, "", "", true) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
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
    public void setDetail(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDetail(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 詳細(<code>DETAIL</code>)の更新値をセットします。
     * @param value 詳細(<code>DETAIL</code>)更新値
     */
    public void updateDetail(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.DETAIL, value) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 項目1(<code>ITEM_1</code>)<br>
     * 文字列の検索値を項目1(<code>ITEM_1</code>)にセットします。
     */
    public void setItem1(String value)
    {
        setKey(PCTOperationLog.ITEM_1, value) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem1(String[] values)
    {
        setKey(PCTOperationLog.ITEM_1, values, true) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem1(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem1(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, values, and_or_toNext) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem1(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, "", "", true) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
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
    public void setItem1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目1(<code>ITEM_1</code>)の更新値をセットします。
     * @param value 項目1(<code>ITEM_1</code>)更新値
     */
    public void updateItem1(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_1, value) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 項目2(<code>ITEM_2</code>)<br>
     * 文字列の検索値を項目2(<code>ITEM_2</code>)にセットします。
     */
    public void setItem2(String value)
    {
        setKey(PCTOperationLog.ITEM_2, value) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem2(String[] values)
    {
        setKey(PCTOperationLog.ITEM_2, values, true) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem2(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem2(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, values, and_or_toNext) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem2(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, "", "", true) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
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
    public void setItem2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目2(<code>ITEM_2</code>)の更新値をセットします。
     * @param value 項目2(<code>ITEM_2</code>)更新値
     */
    public void updateItem2(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_2, value) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 項目3(<code>ITEM_3</code>)<br>
     * 文字列の検索値を項目3(<code>ITEM_3</code>)にセットします。
     */
    public void setItem3(String value)
    {
        setKey(PCTOperationLog.ITEM_3, value) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem3(String[] values)
    {
        setKey(PCTOperationLog.ITEM_3, values, true) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem3(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem3(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, values, and_or_toNext) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem3(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, "", "", true) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
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
    public void setItem3(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem3(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目3(<code>ITEM_3</code>)の更新値をセットします。
     * @param value 項目3(<code>ITEM_3</code>)更新値
     */
    public void updateItem3(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_3, value) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 項目4(<code>ITEM_4</code>)<br>
     * 文字列の検索値を項目4(<code>ITEM_4</code>)にセットします。
     */
    public void setItem4(String value)
    {
        setKey(PCTOperationLog.ITEM_4, value) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem4(String[] values)
    {
        setKey(PCTOperationLog.ITEM_4, values, true) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem4(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem4(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, values, and_or_toNext) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem4(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, "", "", true) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
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
    public void setItem4(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem4(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目4(<code>ITEM_4</code>)の更新値をセットします。
     * @param value 項目4(<code>ITEM_4</code>)更新値
     */
    public void updateItem4(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_4, value) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 項目5(<code>ITEM_5</code>)<br>
     * 文字列の検索値を項目5(<code>ITEM_5</code>)にセットします。
     */
    public void setItem5(String value)
    {
        setKey(PCTOperationLog.ITEM_5, value) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem5(String[] values)
    {
        setKey(PCTOperationLog.ITEM_5, values, true) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem5(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem5(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, values, and_or_toNext) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem5(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, "", "", true) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
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
    public void setItem5(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem5(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目5(<code>ITEM_5</code>)の更新値をセットします。
     * @param value 項目5(<code>ITEM_5</code>)更新値
     */
    public void updateItem5(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_5, value) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 項目6(<code>ITEM_6</code>)<br>
     * 文字列の検索値を項目6(<code>ITEM_6</code>)にセットします。
     */
    public void setItem6(String value)
    {
        setKey(PCTOperationLog.ITEM_6, value) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem6(String[] values)
    {
        setKey(PCTOperationLog.ITEM_6, values, true) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem6(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem6(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, values, and_or_toNext) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem6(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, "", "", true) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
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
    public void setItem6(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem6(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目6(<code>ITEM_6</code>)の更新値をセットします。
     * @param value 項目6(<code>ITEM_6</code>)更新値
     */
    public void updateItem6(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_6, value) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 項目7(<code>ITEM_7</code>)<br>
     * 文字列の検索値を項目7(<code>ITEM_7</code>)にセットします。
     */
    public void setItem7(String value)
    {
        setKey(PCTOperationLog.ITEM_7, value) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem7(String[] values)
    {
        setKey(PCTOperationLog.ITEM_7, values, true) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem7(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem7(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, values, and_or_toNext) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem7(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, "", "", true) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
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
    public void setItem7(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem7(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目7(<code>ITEM_7</code>)の更新値をセットします。
     * @param value 項目7(<code>ITEM_7</code>)更新値
     */
    public void updateItem7(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_7, value) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 項目8(<code>ITEM_8</code>)<br>
     * 文字列の検索値を項目8(<code>ITEM_8</code>)にセットします。
     */
    public void setItem8(String value)
    {
        setKey(PCTOperationLog.ITEM_8, value) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem8(String[] values)
    {
        setKey(PCTOperationLog.ITEM_8, values, true) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem8(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem8(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, values, and_or_toNext) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem8(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, "", "", true) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
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
    public void setItem8(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem8(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目8(<code>ITEM_8</code>)の更新値をセットします。
     * @param value 項目8(<code>ITEM_8</code>)更新値
     */
    public void updateItem8(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_8, value) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 項目9(<code>ITEM_9</code>)<br>
     * 文字列の検索値を項目9(<code>ITEM_9</code>)にセットします。
     */
    public void setItem9(String value)
    {
        setKey(PCTOperationLog.ITEM_9, value) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem9(String[] values)
    {
        setKey(PCTOperationLog.ITEM_9, values, true) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem9(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem9(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, values, and_or_toNext) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem9(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, "", "", true) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
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
    public void setItem9(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem9(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目9(<code>ITEM_9</code>)の更新値をセットします。
     * @param value 項目9(<code>ITEM_9</code>)更新値
     */
    public void updateItem9(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_9, value) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 項目10(<code>ITEM_10</code>)<br>
     * 文字列の検索値を項目10(<code>ITEM_10</code>)にセットします。
     */
    public void setItem10(String value)
    {
        setKey(PCTOperationLog.ITEM_10, value) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem10(String[] values)
    {
        setKey(PCTOperationLog.ITEM_10, values, true) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem10(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem10(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, values, and_or_toNext) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem10(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, "", "", true) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
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
    public void setItem10(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem10(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目10(<code>ITEM_10</code>)の更新値をセットします。
     * @param value 項目10(<code>ITEM_10</code>)更新値
     */
    public void updateItem10(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_10, value) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 項目11(<code>ITEM_11</code>)<br>
     * 文字列の検索値を項目11(<code>ITEM_11</code>)にセットします。
     */
    public void setItem11(String value)
    {
        setKey(PCTOperationLog.ITEM_11, value) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem11(String[] values)
    {
        setKey(PCTOperationLog.ITEM_11, values, true) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem11(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem11(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, values, and_or_toNext) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem11(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, "", "", true) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
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
    public void setItem11(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem11(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目11(<code>ITEM_11</code>)の更新値をセットします。
     * @param value 項目11(<code>ITEM_11</code>)更新値
     */
    public void updateItem11(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_11, value) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 項目12(<code>ITEM_12</code>)<br>
     * 文字列の検索値を項目12(<code>ITEM_12</code>)にセットします。
     */
    public void setItem12(String value)
    {
        setKey(PCTOperationLog.ITEM_12, value) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem12(String[] values)
    {
        setKey(PCTOperationLog.ITEM_12, values, true) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem12(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem12(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, values, and_or_toNext) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem12(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, "", "", true) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
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
    public void setItem12(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem12(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目12(<code>ITEM_12</code>)の更新値をセットします。
     * @param value 項目12(<code>ITEM_12</code>)更新値
     */
    public void updateItem12(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_12, value) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 項目13(<code>ITEM_13</code>)<br>
     * 文字列の検索値を項目13(<code>ITEM_13</code>)にセットします。
     */
    public void setItem13(String value)
    {
        setKey(PCTOperationLog.ITEM_13, value) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem13(String[] values)
    {
        setKey(PCTOperationLog.ITEM_13, values, true) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem13(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem13(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, values, and_or_toNext) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem13(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, "", "", true) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
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
    public void setItem13(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem13(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目13(<code>ITEM_13</code>)の更新値をセットします。
     * @param value 項目13(<code>ITEM_13</code>)更新値
     */
    public void updateItem13(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_13, value) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 項目14(<code>ITEM_14</code>)<br>
     * 文字列の検索値を項目14(<code>ITEM_14</code>)にセットします。
     */
    public void setItem14(String value)
    {
        setKey(PCTOperationLog.ITEM_14, value) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem14(String[] values)
    {
        setKey(PCTOperationLog.ITEM_14, values, true) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem14(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem14(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, values, and_or_toNext) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem14(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, "", "", true) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
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
    public void setItem14(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem14(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目14(<code>ITEM_14</code>)の更新値をセットします。
     * @param value 項目14(<code>ITEM_14</code>)更新値
     */
    public void updateItem14(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_14, value) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 項目15(<code>ITEM_15</code>)<br>
     * 文字列の検索値を項目15(<code>ITEM_15</code>)にセットします。
     */
    public void setItem15(String value)
    {
        setKey(PCTOperationLog.ITEM_15, value) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem15(String[] values)
    {
        setKey(PCTOperationLog.ITEM_15, values, true) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem15(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem15(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, values, and_or_toNext) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem15(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, "", "", true) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
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
    public void setItem15(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem15(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目15(<code>ITEM_15</code>)の更新値をセットします。
     * @param value 項目15(<code>ITEM_15</code>)更新値
     */
    public void updateItem15(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_15, value) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 項目16(<code>ITEM_16</code>)<br>
     * 文字列の検索値を項目16(<code>ITEM_16</code>)にセットします。
     */
    public void setItem16(String value)
    {
        setKey(PCTOperationLog.ITEM_16, value) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem16(String[] values)
    {
        setKey(PCTOperationLog.ITEM_16, values, true) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem16(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem16(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, values, and_or_toNext) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem16(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, "", "", true) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
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
    public void setItem16(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem16(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目16(<code>ITEM_16</code>)の更新値をセットします。
     * @param value 項目16(<code>ITEM_16</code>)更新値
     */
    public void updateItem16(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_16, value) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 項目17(<code>ITEM_17</code>)<br>
     * 文字列の検索値を項目17(<code>ITEM_17</code>)にセットします。
     */
    public void setItem17(String value)
    {
        setKey(PCTOperationLog.ITEM_17, value) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem17(String[] values)
    {
        setKey(PCTOperationLog.ITEM_17, values, true) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem17(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem17(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, values, and_or_toNext) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem17(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, "", "", true) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
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
    public void setItem17(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem17(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目17(<code>ITEM_17</code>)の更新値をセットします。
     * @param value 項目17(<code>ITEM_17</code>)更新値
     */
    public void updateItem17(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_17, value) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 項目18(<code>ITEM_18</code>)<br>
     * 文字列の検索値を項目18(<code>ITEM_18</code>)にセットします。
     */
    public void setItem18(String value)
    {
        setKey(PCTOperationLog.ITEM_18, value) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem18(String[] values)
    {
        setKey(PCTOperationLog.ITEM_18, values, true) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem18(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem18(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, values, and_or_toNext) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem18(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, "", "", true) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
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
    public void setItem18(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem18(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目18(<code>ITEM_18</code>)の更新値をセットします。
     * @param value 項目18(<code>ITEM_18</code>)更新値
     */
    public void updateItem18(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_18, value) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 項目19(<code>ITEM_19</code>)<br>
     * 文字列の検索値を項目19(<code>ITEM_19</code>)にセットします。
     */
    public void setItem19(String value)
    {
        setKey(PCTOperationLog.ITEM_19, value) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem19(String[] values)
    {
        setKey(PCTOperationLog.ITEM_19, values, true) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem19(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem19(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, values, and_or_toNext) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem19(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, "", "", true) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
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
    public void setItem19(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem19(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目19(<code>ITEM_19</code>)の更新値をセットします。
     * @param value 項目19(<code>ITEM_19</code>)更新値
     */
    public void updateItem19(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_19, value) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 項目20(<code>ITEM_20</code>)<br>
     * 文字列の検索値を項目20(<code>ITEM_20</code>)にセットします。
     */
    public void setItem20(String value)
    {
        setKey(PCTOperationLog.ITEM_20, value) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem20(String[] values)
    {
        setKey(PCTOperationLog.ITEM_20, values, true) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem20(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem20(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, values, and_or_toNext) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem20(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, "", "", true) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
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
    public void setItem20(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem20(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目20(<code>ITEM_20</code>)の更新値をセットします。
     * @param value 項目20(<code>ITEM_20</code>)更新値
     */
    public void updateItem20(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_20, value) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 項目21(<code>ITEM_21</code>)<br>
     * 文字列の検索値を項目21(<code>ITEM_21</code>)にセットします。
     */
    public void setItem21(String value)
    {
        setKey(PCTOperationLog.ITEM_21, value) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem21(String[] values)
    {
        setKey(PCTOperationLog.ITEM_21, values, true) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem21(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem21(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, values, and_or_toNext) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem21(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, "", "", true) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
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
    public void setItem21(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem21(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目21(<code>ITEM_21</code>)の更新値をセットします。
     * @param value 項目21(<code>ITEM_21</code>)更新値
     */
    public void updateItem21(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_21, value) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 項目22(<code>ITEM_22</code>)<br>
     * 文字列の検索値を項目22(<code>ITEM_22</code>)にセットします。
     */
    public void setItem22(String value)
    {
        setKey(PCTOperationLog.ITEM_22, value) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem22(String[] values)
    {
        setKey(PCTOperationLog.ITEM_22, values, true) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem22(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem22(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, values, and_or_toNext) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem22(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, "", "", true) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
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
    public void setItem22(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem22(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目22(<code>ITEM_22</code>)の更新値をセットします。
     * @param value 項目22(<code>ITEM_22</code>)更新値
     */
    public void updateItem22(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_22, value) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 項目23(<code>ITEM_23</code>)<br>
     * 文字列の検索値を項目23(<code>ITEM_23</code>)にセットします。
     */
    public void setItem23(String value)
    {
        setKey(PCTOperationLog.ITEM_23, value) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem23(String[] values)
    {
        setKey(PCTOperationLog.ITEM_23, values, true) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem23(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem23(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, values, and_or_toNext) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem23(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, "", "", true) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
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
    public void setItem23(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem23(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目23(<code>ITEM_23</code>)の更新値をセットします。
     * @param value 項目23(<code>ITEM_23</code>)更新値
     */
    public void updateItem23(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_23, value) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 項目24(<code>ITEM_24</code>)<br>
     * 文字列の検索値を項目24(<code>ITEM_24</code>)にセットします。
     */
    public void setItem24(String value)
    {
        setKey(PCTOperationLog.ITEM_24, value) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem24(String[] values)
    {
        setKey(PCTOperationLog.ITEM_24, values, true) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem24(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem24(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, values, and_or_toNext) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem24(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, "", "", true) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
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
    public void setItem24(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem24(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目24(<code>ITEM_24</code>)の更新値をセットします。
     * @param value 項目24(<code>ITEM_24</code>)更新値
     */
    public void updateItem24(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_24, value) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 項目25(<code>ITEM_25</code>)<br>
     * 文字列の検索値を項目25(<code>ITEM_25</code>)にセットします。
     */
    public void setItem25(String value)
    {
        setKey(PCTOperationLog.ITEM_25, value) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem25(String[] values)
    {
        setKey(PCTOperationLog.ITEM_25, values, true) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem25(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem25(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, values, and_or_toNext) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem25(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, "", "", true) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
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
    public void setItem25(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem25(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目25(<code>ITEM_25</code>)の更新値をセットします。
     * @param value 項目25(<code>ITEM_25</code>)更新値
     */
    public void updateItem25(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_25, value) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 項目26(<code>ITEM_26</code>)<br>
     * 文字列の検索値を項目26(<code>ITEM_26</code>)にセットします。
     */
    public void setItem26(String value)
    {
        setKey(PCTOperationLog.ITEM_26, value) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem26(String[] values)
    {
        setKey(PCTOperationLog.ITEM_26, values, true) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem26(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem26(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, values, and_or_toNext) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem26(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, "", "", true) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
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
    public void setItem26(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem26(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目26(<code>ITEM_26</code>)の更新値をセットします。
     * @param value 項目26(<code>ITEM_26</code>)更新値
     */
    public void updateItem26(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_26, value) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 項目27(<code>ITEM_27</code>)<br>
     * 文字列の検索値を項目27(<code>ITEM_27</code>)にセットします。
     */
    public void setItem27(String value)
    {
        setKey(PCTOperationLog.ITEM_27, value) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem27(String[] values)
    {
        setKey(PCTOperationLog.ITEM_27, values, true) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem27(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem27(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, values, and_or_toNext) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem27(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, "", "", true) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
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
    public void setItem27(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem27(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目27(<code>ITEM_27</code>)の更新値をセットします。
     * @param value 項目27(<code>ITEM_27</code>)更新値
     */
    public void updateItem27(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_27, value) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 項目28(<code>ITEM_28</code>)<br>
     * 文字列の検索値を項目28(<code>ITEM_28</code>)にセットします。
     */
    public void setItem28(String value)
    {
        setKey(PCTOperationLog.ITEM_28, value) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem28(String[] values)
    {
        setKey(PCTOperationLog.ITEM_28, values, true) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem28(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem28(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, values, and_or_toNext) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem28(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, "", "", true) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
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
    public void setItem28(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem28(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目28(<code>ITEM_28</code>)の更新値をセットします。
     * @param value 項目28(<code>ITEM_28</code>)更新値
     */
    public void updateItem28(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_28, value) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 項目29(<code>ITEM_29</code>)<br>
     * 文字列の検索値を項目29(<code>ITEM_29</code>)にセットします。
     */
    public void setItem29(String value)
    {
        setKey(PCTOperationLog.ITEM_29, value) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem29(String[] values)
    {
        setKey(PCTOperationLog.ITEM_29, values, true) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem29(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem29(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, values, and_or_toNext) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem29(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, "", "", true) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
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
    public void setItem29(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem29(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目29(<code>ITEM_29</code>)の更新値をセットします。
     * @param value 項目29(<code>ITEM_29</code>)更新値
     */
    public void updateItem29(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_29, value) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 項目30(<code>ITEM_30</code>)<br>
     * 文字列の検索値を項目30(<code>ITEM_30</code>)にセットします。
     */
    public void setItem30(String value)
    {
        setKey(PCTOperationLog.ITEM_30, value) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem30(String[] values)
    {
        setKey(PCTOperationLog.ITEM_30, values, true) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem30(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem30(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, values, and_or_toNext) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem30(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, "", "", true) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
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
    public void setItem30(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem30(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目30(<code>ITEM_30</code>)の更新値をセットします。
     * @param value 項目30(<code>ITEM_30</code>)更新値
     */
    public void updateItem30(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_30, value) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 項目31(<code>ITEM_31</code>)<br>
     * 文字列の検索値を項目31(<code>ITEM_31</code>)にセットします。
     */
    public void setItem31(String value)
    {
        setKey(PCTOperationLog.ITEM_31, value) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem31(String[] values)
    {
        setKey(PCTOperationLog.ITEM_31, values, true) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem31(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem31(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, values, and_or_toNext) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem31(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, "", "", true) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
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
    public void setItem31(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem31(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目31(<code>ITEM_31</code>)の更新値をセットします。
     * @param value 項目31(<code>ITEM_31</code>)更新値
     */
    public void updateItem31(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_31, value) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 項目32(<code>ITEM_32</code>)<br>
     * 文字列の検索値を項目32(<code>ITEM_32</code>)にセットします。
     */
    public void setItem32(String value)
    {
        setKey(PCTOperationLog.ITEM_32, value) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem32(String[] values)
    {
        setKey(PCTOperationLog.ITEM_32, values, true) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem32(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem32(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, values, and_or_toNext) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem32(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, "", "", true) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
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
    public void setItem32(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem32(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目32(<code>ITEM_32</code>)の更新値をセットします。
     * @param value 項目32(<code>ITEM_32</code>)更新値
     */
    public void updateItem32(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_32, value) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 項目33(<code>ITEM_33</code>)<br>
     * 文字列の検索値を項目33(<code>ITEM_33</code>)にセットします。
     */
    public void setItem33(String value)
    {
        setKey(PCTOperationLog.ITEM_33, value) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem33(String[] values)
    {
        setKey(PCTOperationLog.ITEM_33, values, true) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem33(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem33(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, values, and_or_toNext) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem33(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, "", "", true) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
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
    public void setItem33(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem33(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目33(<code>ITEM_33</code>)の更新値をセットします。
     * @param value 項目33(<code>ITEM_33</code>)更新値
     */
    public void updateItem33(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_33, value) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 項目34(<code>ITEM_34</code>)<br>
     * 文字列の検索値を項目34(<code>ITEM_34</code>)にセットします。
     */
    public void setItem34(String value)
    {
        setKey(PCTOperationLog.ITEM_34, value) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem34(String[] values)
    {
        setKey(PCTOperationLog.ITEM_34, values, true) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem34(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem34(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, values, and_or_toNext) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem34(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, "", "", true) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
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
    public void setItem34(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem34(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目34(<code>ITEM_34</code>)の更新値をセットします。
     * @param value 項目34(<code>ITEM_34</code>)更新値
     */
    public void updateItem34(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_34, value) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 項目35(<code>ITEM_35</code>)<br>
     * 文字列の検索値を項目35(<code>ITEM_35</code>)にセットします。
     */
    public void setItem35(String value)
    {
        setKey(PCTOperationLog.ITEM_35, value) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem35(String[] values)
    {
        setKey(PCTOperationLog.ITEM_35, values, true) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem35(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem35(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, values, and_or_toNext) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem35(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, "", "", true) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
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
    public void setItem35(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem35(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目35(<code>ITEM_35</code>)の更新値をセットします。
     * @param value 項目35(<code>ITEM_35</code>)更新値
     */
    public void updateItem35(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_35, value) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 項目36(<code>ITEM_36</code>)<br>
     * 文字列の検索値を項目36(<code>ITEM_36</code>)にセットします。
     */
    public void setItem36(String value)
    {
        setKey(PCTOperationLog.ITEM_36, value) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem36(String[] values)
    {
        setKey(PCTOperationLog.ITEM_36, values, true) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem36(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem36(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, values, and_or_toNext) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem36(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, "", "", true) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
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
    public void setItem36(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem36(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目36(<code>ITEM_36</code>)の更新値をセットします。
     * @param value 項目36(<code>ITEM_36</code>)更新値
     */
    public void updateItem36(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_36, value) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 項目37(<code>ITEM_37</code>)<br>
     * 文字列の検索値を項目37(<code>ITEM_37</code>)にセットします。
     */
    public void setItem37(String value)
    {
        setKey(PCTOperationLog.ITEM_37, value) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem37(String[] values)
    {
        setKey(PCTOperationLog.ITEM_37, values, true) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem37(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem37(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, values, and_or_toNext) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem37(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, "", "", true) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
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
    public void setItem37(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem37(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目37(<code>ITEM_37</code>)の更新値をセットします。
     * @param value 項目37(<code>ITEM_37</code>)更新値
     */
    public void updateItem37(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_37, value) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 項目38(<code>ITEM_38</code>)<br>
     * 文字列の検索値を項目38(<code>ITEM_38</code>)にセットします。
     */
    public void setItem38(String value)
    {
        setKey(PCTOperationLog.ITEM_38, value) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem38(String[] values)
    {
        setKey(PCTOperationLog.ITEM_38, values, true) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem38(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem38(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, values, and_or_toNext) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem38(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, "", "", true) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
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
    public void setItem38(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem38(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目38(<code>ITEM_38</code>)の更新値をセットします。
     * @param value 項目38(<code>ITEM_38</code>)更新値
     */
    public void updateItem38(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_38, value) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 項目39(<code>ITEM_39</code>)<br>
     * 文字列の検索値を項目39(<code>ITEM_39</code>)にセットします。
     */
    public void setItem39(String value)
    {
        setKey(PCTOperationLog.ITEM_39, value) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem39(String[] values)
    {
        setKey(PCTOperationLog.ITEM_39, values, true) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem39(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem39(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, values, and_or_toNext) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem39(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, "", "", true) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
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
    public void setItem39(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem39(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目39(<code>ITEM_39</code>)の更新値をセットします。
     * @param value 項目39(<code>ITEM_39</code>)更新値
     */
    public void updateItem39(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_39, value) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 項目40(<code>ITEM_40</code>)<br>
     * 文字列の検索値を項目40(<code>ITEM_40</code>)にセットします。
     */
    public void setItem40(String value)
    {
        setKey(PCTOperationLog.ITEM_40, value) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem40(String[] values)
    {
        setKey(PCTOperationLog.ITEM_40, values, true) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem40(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItem40(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, values, and_or_toNext) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItem40(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, "", "", true) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
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
    public void setItem40(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItem40(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目40(<code>ITEM_40</code>)の更新値をセットします。
     * @param value 項目40(<code>ITEM_40</code>)更新値
     */
    public void updateItem40(String value)
    {
        setAdhocUpdateValue(PCTOperationLog.ITEM_40, value) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOperationTypeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTOperationLog.OPERATION_TYPE, source, addvalue);
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
        return "$Id: PCTOperationLogAlterKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
