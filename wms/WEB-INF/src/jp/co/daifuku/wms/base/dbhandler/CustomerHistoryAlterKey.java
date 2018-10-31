// $Id: CustomerHistoryAlterKey.java 1841 2008-12-09 10:12:59Z okayama $
// $LastChangedRevision: 1841 $
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
import jp.co.daifuku.wms.base.entity.CustomerHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * CUSTOMERHISTORY用の更新キークラスです。
 *
 * @version $Revision: 1841 $, $Date: 2008-12-09 19:12:59 +0900 (火, 09 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class CustomerHistoryAlterKey
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
    public static final StoreMetaData $storeMetaData = CustomerHistory.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public CustomerHistoryAlterKey()
    {
        super(CustomerHistory.STORE_NAME) ;
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
        setKey(CustomerHistory.LOG_DATE, value) ;
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
        setKey(CustomerHistory.LOG_DATE, values, true) ;
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
        setKey(CustomerHistory.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(CustomerHistory.LOG_DATE, value, compcode, "", "", true) ;
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
        setKey(CustomerHistory.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CustomerHistory.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(<code>LOG_DATE</code>)の更新値をセットします。
     * @param value 出力日時(<code>LOG_DATE</code>)更新値
     */
    public void updateLogDate(Date value)
    {
        setAdhocUpdateValue(CustomerHistory.LOG_DATE, value) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値を出力日時(GMT)(<code>LOG_DATE_GMT</code>)にセットします。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(CustomerHistory.LOG_DATE_GMT, value) ;
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
        setKey(CustomerHistory.LOG_DATE_GMT, values, true) ;
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
        setKey(CustomerHistory.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(CustomerHistory.LOG_DATE_GMT, value, compcode, "", "", true) ;
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
        setKey(CustomerHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CustomerHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の更新値をセットします。
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)更新値
     */
    public void updateLogDateGmt(Date value)
    {
        setAdhocUpdateValue(CustomerHistory.LOG_DATE_GMT, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(CustomerHistory.USER_ID, value) ;
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
        setKey(CustomerHistory.USER_ID, values, true) ;
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
        setKey(CustomerHistory.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(CustomerHistory.USER_ID, value, compcode, "", "", true) ;
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
        setKey(CustomerHistory.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CustomerHistory.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(CustomerHistory.USER_ID, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名称(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(CustomerHistory.USER_NAME, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserName(String[] values)
    {
        setKey(CustomerHistory.USER_NAME, values, true) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
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
        setKey(CustomerHistory.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(CustomerHistory.USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
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
        setKey(CustomerHistory.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名称(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名称(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(CustomerHistory.USER_NAME, value) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No、RFTNo(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(CustomerHistory.TERMINAL_NO, value) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values)
    {
        setKey(CustomerHistory.TERMINAL_NO, values, true) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(CustomerHistory.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
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
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(CustomerHistory.TERMINAL_NO, value) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名称(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値を端末名称(<code>TERMINAL_NAME</code>)にセットします。
     */
    public void setTerminalName(String value)
    {
        setKey(CustomerHistory.TERMINAL_NAME, value) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalName(String[] values)
    {
        setKey(CustomerHistory.TERMINAL_NAME, values, true) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
        setKey(CustomerHistory.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(CustomerHistory.TERMINAL_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
        setKey(CustomerHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の更新値をセットします。
     * @param value 端末名称(<code>TERMINAL_NAME</code>)更新値
     */
    public void updateTerminalName(String value)
    {
        setAdhocUpdateValue(CustomerHistory.TERMINAL_NAME, value) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IP_ADDRESS</code>)<br>
     * 文字列の検索値をIPアドレス(<code>IP_ADDRESS</code>)にセットします。
     */
    public void setIpAddress(String value)
    {
        setKey(CustomerHistory.IP_ADDRESS, value) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpAddress(String[] values)
    {
        setKey(CustomerHistory.IP_ADDRESS, values, true) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpAddress(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.IP_ADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setIpAddress(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.IP_ADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setIpAddress(String value, String compcode)
    {
        setKey(CustomerHistory.IP_ADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
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
    public void setIpAddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setIpAddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の更新値をセットします。
     * @param value IPアドレス(<code>IP_ADDRESS</code>)更新値
     */
    public void updateIpAddress(String value)
    {
        setAdhocUpdateValue(CustomerHistory.IP_ADDRESS, value) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をDS番号(<code>DS_NO</code>)にセットします。
     */
    public void setDsNo(String value)
    {
        setKey(CustomerHistory.DS_NO, value) ;
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
        setKey(CustomerHistory.DS_NO, values, true) ;
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
        setKey(CustomerHistory.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(CustomerHistory.DS_NO, value, compcode, "", "", true) ;
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
        setKey(CustomerHistory.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CustomerHistory.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * DS番号(<code>DS_NO</code>)の更新値をセットします。
     * @param value DS番号(<code>DS_NO</code>)更新値
     */
    public void updateDsNo(String value)
    {
        setAdhocUpdateValue(CustomerHistory.DS_NO, value) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値を画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)にセットします。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameResourcekey(String[] values)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, values, true) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameResourcekey(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
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
    public void setPagenameResourcekey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPagenameResourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の更新値をセットします。
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)更新値
     */
    public void updatePagenameResourcekey(String value)
    {
        setAdhocUpdateValue(CustomerHistory.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 文字列の検索値を更新区分(<code>UPDATE_KIND</code>)にセットします。
     */
    public void setUpdateKind(String value)
    {
        setKey(CustomerHistory.UPDATE_KIND, value) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(String[] values)
    {
        setKey(CustomerHistory.UPDATE_KIND, values, true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateKind(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_KIND, values, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateKind(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_KIND, value, compcode, "", "", true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
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
    public void setUpdateKind(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateKind(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新区分(<code>UPDATE_KIND</code>)の更新値をセットします。
     * @param value 更新区分(<code>UPDATE_KIND</code>)更新値
     */
    public void updateUpdateKind(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_KIND, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, values, true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
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
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(CustomerHistory.CONSIGNOR_CODE, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)<br>
     * 文字列の検索値を出荷先コード(<code>CUSTOMER_CODE</code>)にセットします。
     */
    public void setCustomerCode(String value)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, values, true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCustomerCode(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, values, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCustomerCode(String value, String compcode)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
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
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の更新値をセットします。
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)更新値
     */
    public void updateCustomerCode(String value)
    {
        setAdhocUpdateValue(CustomerHistory.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 出荷先名称(<code>CUSTOMER_NAME</code>)<br>
     * 文字列の検索値を出荷先名称(<code>CUSTOMER_NAME</code>)にセットします。
     */
    public void setCustomerName(String value)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, value) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, values, true) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCustomerName(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, values, and_or_toNext) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCustomerName(String value, String compcode)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
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
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CUSTOMER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の更新値をセットします。
     * @param value 出荷先名称(<code>CUSTOMER_NAME</code>)更新値
     */
    public void updateCustomerName(String value)
    {
        setAdhocUpdateValue(CustomerHistory.CUSTOMER_NAME, value) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)<br>
     * 文字列の検索値を修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)にセットします。
     */
    public void setUpdateCustomerName(String value)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, value) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateCustomerName(String[] values)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, values, true) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateCustomerName(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateCustomerName(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, values, and_or_toNext) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateCustomerName(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
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
    public void setUpdateCustomerName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateCustomerName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CUSTOMER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)の更新値をセットします。
     * @param value 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)更新値
     */
    public void updateUpdateCustomerName(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_CUSTOMER_NAME, value) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value ルート(<code>ROUTE</code>)<br>
     * 文字列の検索値をルート(<code>ROUTE</code>)にセットします。
     */
    public void setRoute(String value)
    {
        setKey(CustomerHistory.ROUTE, value) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoute(String[] values)
    {
        setKey(CustomerHistory.ROUTE, values, true) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoute(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.ROUTE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRoute(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ROUTE, values, and_or_toNext) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRoute(String value, String compcode)
    {
        setKey(CustomerHistory.ROUTE, value, compcode, "", "", true) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
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
    public void setRoute(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.ROUTE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRoute(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ROUTE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ルート(<code>ROUTE</code>)の更新値をセットします。
     * @param value ルート(<code>ROUTE</code>)更新値
     */
    public void updateRoute(String value)
    {
        setAdhocUpdateValue(CustomerHistory.ROUTE, value) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
     * 
     * @param value 修正後ルート(<code>UPDATE_ROUTE</code>)<br>
     * 文字列の検索値を修正後ルート(<code>UPDATE_ROUTE</code>)にセットします。
     */
    public void setUpdateRoute(String value)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, value) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateRoute(String[] values)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, values, true) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateRoute(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateRoute(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, values, and_or_toNext) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateRoute(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
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
    public void setUpdateRoute(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateRoute(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ROUTE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>)の更新値をセットします。
     * @param value 修正後ルート(<code>UPDATE_ROUTE</code>)更新値
     */
    public void updateUpdateRoute(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_ROUTE, value) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 郵便番号(<code>POSTAL_CODE</code>)<br>
     * 文字列の検索値を郵便番号(<code>POSTAL_CODE</code>)にセットします。
     */
    public void setPostalCode(String value)
    {
        setKey(CustomerHistory.POSTAL_CODE, value) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCode(String[] values)
    {
        setKey(CustomerHistory.POSTAL_CODE, values, true) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCode(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.POSTAL_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPostalCode(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.POSTAL_CODE, values, and_or_toNext) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPostalCode(String value, String compcode)
    {
        setKey(CustomerHistory.POSTAL_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
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
    public void setPostalCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.POSTAL_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPostalCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.POSTAL_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の更新値をセットします。
     * @param value 郵便番号(<code>POSTAL_CODE</code>)更新値
     */
    public void updatePostalCode(String value)
    {
        setAdhocUpdateValue(CustomerHistory.POSTAL_CODE, value) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)<br>
     * 文字列の検索値を修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)にセットします。
     */
    public void setUpdatePostalCode(String value)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, value) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdatePostalCode(String[] values)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, values, true) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdatePostalCode(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdatePostalCode(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, values, and_or_toNext) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdatePostalCode(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
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
    public void setUpdatePostalCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdatePostalCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_POSTAL_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)の更新値をセットします。
     * @param value 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)更新値
     */
    public void updateUpdatePostalCode(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_POSTAL_CODE, value) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 都道府県名(<code>PREFECTURE</code>)<br>
     * 文字列の検索値を都道府県名(<code>PREFECTURE</code>)にセットします。
     */
    public void setPrefecture(String value)
    {
        setKey(CustomerHistory.PREFECTURE, value) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrefecture(String[] values)
    {
        setKey(CustomerHistory.PREFECTURE, values, true) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrefecture(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.PREFECTURE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPrefecture(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.PREFECTURE, values, and_or_toNext) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPrefecture(String value, String compcode)
    {
        setKey(CustomerHistory.PREFECTURE, value, compcode, "", "", true) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
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
    public void setPrefecture(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.PREFECTURE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPrefecture(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.PREFECTURE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 都道府県名(<code>PREFECTURE</code>)の更新値をセットします。
     * @param value 都道府県名(<code>PREFECTURE</code>)更新値
     */
    public void updatePrefecture(String value)
    {
        setAdhocUpdateValue(CustomerHistory.PREFECTURE, value) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)<br>
     * 文字列の検索値を修正後都道府県名(<code>UPDATE_PREFECTURE</code>)にセットします。
     */
    public void setUpdatePrefecture(String value)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, value) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdatePrefecture(String[] values)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, values, true) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdatePrefecture(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdatePrefecture(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, values, and_or_toNext) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdatePrefecture(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
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
    public void setUpdatePrefecture(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdatePrefecture(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_PREFECTURE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)の更新値をセットします。
     * @param value 修正後都道府県名(<code>UPDATE_PREFECTURE</code>)更新値
     */
    public void updateUpdatePrefecture(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_PREFECTURE, value) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 住所(<code>ADDRESS1</code>)<br>
     * 文字列の検索値を住所(<code>ADDRESS1</code>)にセットします。
     */
    public void setAddress1(String value)
    {
        setKey(CustomerHistory.ADDRESS1, value) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1(String[] values)
    {
        setKey(CustomerHistory.ADDRESS1, values, true) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAddress1(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS1, values, and_or_toNext) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAddress1(String value, String compcode)
    {
        setKey(CustomerHistory.ADDRESS1, value, compcode, "", "", true) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
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
    public void setAddress1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAddress1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 住所(<code>ADDRESS1</code>)の更新値をセットします。
     * @param value 住所(<code>ADDRESS1</code>)更新値
     */
    public void updateAddress1(String value)
    {
        setAdhocUpdateValue(CustomerHistory.ADDRESS1, value) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 修正後住所(<code>UPDATE_ADDRESS1</code>)<br>
     * 文字列の検索値を修正後住所(<code>UPDATE_ADDRESS1</code>)にセットします。
     */
    public void setUpdateAddress1(String value)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, value) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAddress1(String[] values)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, values, true) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAddress1(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateAddress1(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, values, and_or_toNext) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateAddress1(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, value, compcode, "", "", true) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
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
    public void setUpdateAddress1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateAddress1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>)の更新値をセットします。
     * @param value 修正後住所(<code>UPDATE_ADDRESS1</code>)更新値
     */
    public void updateUpdateAddress1(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_ADDRESS1, value) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value ビル名等(<code>ADDRESS2</code>)<br>
     * 文字列の検索値をビル名等(<code>ADDRESS2</code>)にセットします。
     */
    public void setAddress2(String value)
    {
        setKey(CustomerHistory.ADDRESS2, value) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2(String[] values)
    {
        setKey(CustomerHistory.ADDRESS2, values, true) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAddress2(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS2, values, and_or_toNext) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAddress2(String value, String compcode)
    {
        setKey(CustomerHistory.ADDRESS2, value, compcode, "", "", true) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
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
    public void setAddress2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAddress2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.ADDRESS2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ビル名等(<code>ADDRESS2</code>)の更新値をセットします。
     * @param value ビル名等(<code>ADDRESS2</code>)更新値
     */
    public void updateAddress2(String value)
    {
        setAdhocUpdateValue(CustomerHistory.ADDRESS2, value) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)<br>
     * 文字列の検索値を修正後ビル名等(<code>UPDATE_ADDRESS2</code>)にセットします。
     */
    public void setUpdateAddress2(String value)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, value) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAddress2(String[] values)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, values, true) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAddress2(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateAddress2(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, values, and_or_toNext) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateAddress2(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, value, compcode, "", "", true) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
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
    public void setUpdateAddress2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateAddress2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_ADDRESS2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)の更新値をセットします。
     * @param value 修正後ビル名等(<code>UPDATE_ADDRESS2</code>)更新値
     */
    public void updateUpdateAddress2(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_ADDRESS2, value) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value TEL(<code>TELEPHONE</code>)<br>
     * 文字列の検索値をTEL(<code>TELEPHONE</code>)にセットします。
     */
    public void setTelephone(String value)
    {
        setKey(CustomerHistory.TELEPHONE, value) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTelephone(String[] values)
    {
        setKey(CustomerHistory.TELEPHONE, values, true) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTelephone(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.TELEPHONE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTelephone(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TELEPHONE, values, and_or_toNext) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTelephone(String value, String compcode)
    {
        setKey(CustomerHistory.TELEPHONE, value, compcode, "", "", true) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
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
    public void setTelephone(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.TELEPHONE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTelephone(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.TELEPHONE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * TEL(<code>TELEPHONE</code>)の更新値をセットします。
     * @param value TEL(<code>TELEPHONE</code>)更新値
     */
    public void updateTelephone(String value)
    {
        setAdhocUpdateValue(CustomerHistory.TELEPHONE, value) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 修正後TEL(<code>UPDATE_TELEPHONE</code>)<br>
     * 文字列の検索値を修正後TEL(<code>UPDATE_TELEPHONE</code>)にセットします。
     */
    public void setUpdateTelephone(String value)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, value) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTelephone(String[] values)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, values, true) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTelephone(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateTelephone(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, values, and_or_toNext) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateTelephone(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
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
    public void setUpdateTelephone(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateTelephone(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_TELEPHONE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>)の更新値をセットします。
     * @param value 修正後TEL(<code>UPDATE_TELEPHONE</code>)更新値
     */
    public void updateUpdateTelephone(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_TELEPHONE, value) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 連絡先１(<code>CONTACT1</code>)<br>
     * 文字列の検索値を連絡先１(<code>CONTACT1</code>)にセットします。
     */
    public void setContact1(String value)
    {
        setKey(CustomerHistory.CONTACT1, value) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact1(String[] values)
    {
        setKey(CustomerHistory.CONTACT1, values, true) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact1(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setContact1(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT1, values, and_or_toNext) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setContact1(String value, String compcode)
    {
        setKey(CustomerHistory.CONTACT1, value, compcode, "", "", true) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
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
    public void setContact1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setContact1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 連絡先１(<code>CONTACT1</code>)の更新値をセットします。
     * @param value 連絡先１(<code>CONTACT1</code>)更新値
     */
    public void updateContact1(String value)
    {
        setAdhocUpdateValue(CustomerHistory.CONTACT1, value) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 修正後連絡先１(<code>UPDATE_CONTACT1</code>)<br>
     * 文字列の検索値を修正後連絡先１(<code>UPDATE_CONTACT1</code>)にセットします。
     */
    public void setUpdateContact1(String value)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, value) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateContact1(String[] values)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, values, true) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateContact1(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateContact1(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, values, and_or_toNext) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateContact1(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, value, compcode, "", "", true) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
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
    public void setUpdateContact1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateContact1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>)の更新値をセットします。
     * @param value 修正後連絡先１(<code>UPDATE_CONTACT1</code>)更新値
     */
    public void updateUpdateContact1(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_CONTACT1, value) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 連絡先２(<code>CONTACT2</code>)<br>
     * 文字列の検索値を連絡先２(<code>CONTACT2</code>)にセットします。
     */
    public void setContact2(String value)
    {
        setKey(CustomerHistory.CONTACT2, value) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact2(String[] values)
    {
        setKey(CustomerHistory.CONTACT2, values, true) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact2(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setContact2(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT2, values, and_or_toNext) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setContact2(String value, String compcode)
    {
        setKey(CustomerHistory.CONTACT2, value, compcode, "", "", true) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
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
    public void setContact2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setContact2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.CONTACT2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 連絡先２(<code>CONTACT2</code>)の更新値をセットします。
     * @param value 連絡先２(<code>CONTACT2</code>)更新値
     */
    public void updateContact2(String value)
    {
        setAdhocUpdateValue(CustomerHistory.CONTACT2, value) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 修正後連絡先２(<code>UPDATE_CONTACT2</code>)<br>
     * 文字列の検索値を修正後連絡先２(<code>UPDATE_CONTACT2</code>)にセットします。
     */
    public void setUpdateContact2(String value)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, value) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateContact2(String[] values)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, values, true) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateContact2(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateContact2(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, values, and_or_toNext) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateContact2(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, value, compcode, "", "", true) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
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
    public void setUpdateContact2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateContact2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_CONTACT2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>)の更新値をセットします。
     * @param value 修正後連絡先２(<code>UPDATE_CONTACT2</code>)更新値
     */
    public void updateUpdateContact2(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_CONTACT2, value) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 仕分場所(<code>SORTING_PLACE</code>)<br>
     * 文字列の検索値を仕分場所(<code>SORTING_PLACE</code>)にセットします。
     */
    public void setSortingPlace(String value)
    {
        setKey(CustomerHistory.SORTING_PLACE, value) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlace(String[] values)
    {
        setKey(CustomerHistory.SORTING_PLACE, values, true) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlace(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.SORTING_PLACE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSortingPlace(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.SORTING_PLACE, values, and_or_toNext) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSortingPlace(String value, String compcode)
    {
        setKey(CustomerHistory.SORTING_PLACE, value, compcode, "", "", true) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
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
    public void setSortingPlace(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.SORTING_PLACE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSortingPlace(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.SORTING_PLACE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の更新値をセットします。
     * @param value 仕分場所(<code>SORTING_PLACE</code>)更新値
     */
    public void updateSortingPlace(String value)
    {
        setAdhocUpdateValue(CustomerHistory.SORTING_PLACE, value) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)<br>
     * 文字列の検索値を修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)にセットします。
     */
    public void setUpdateSortingPlace(String value)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, value) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSortingPlace(String[] values)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, values, true) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSortingPlace(String[] values, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateSortingPlace(String[] values, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, values, and_or_toNext) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateSortingPlace(String value, String compcode)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
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
    public void setUpdateSortingPlace(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateSortingPlace(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CustomerHistory.UPDATE_SORTING_PLACE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)の更新値をセットします。
     * @param value 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)更新値
     */
    public void updateUpdateSortingPlace(String value)
    {
        setAdhocUpdateValue(CustomerHistory.UPDATE_SORTING_PLACE, value) ;
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
        return "$Id: CustomerHistoryAlterKey.java 1841 2008-12-09 10:12:59Z okayama $" ;
    }
}
