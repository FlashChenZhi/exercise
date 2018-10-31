// $Id: AreaHistoryAlterKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.AreaHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * AREAHISTORY用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class AreaHistoryAlterKey
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
    public static final StoreMetaData $storeMetaData = AreaHistory.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AreaHistoryAlterKey()
    {
        super(AreaHistory.STORE_NAME) ;
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
        setKey(AreaHistory.LOG_DATE, value) ;
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
        setKey(AreaHistory.LOG_DATE, values, true) ;
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
        setKey(AreaHistory.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(AreaHistory.LOG_DATE, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(<code>LOG_DATE</code>)の更新値をセットします。
     * @param value 出力日時(<code>LOG_DATE</code>)更新値
     */
    public void updateLogDate(Date value)
    {
        setAdhocUpdateValue(AreaHistory.LOG_DATE, value) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値を出力日時(GMT)(<code>LOG_DATE_GMT</code>)にセットします。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(AreaHistory.LOG_DATE_GMT, value) ;
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
        setKey(AreaHistory.LOG_DATE_GMT, values, true) ;
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
        setKey(AreaHistory.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(AreaHistory.LOG_DATE_GMT, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の更新値をセットします。
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)更新値
     */
    public void updateLogDateGmt(Date value)
    {
        setAdhocUpdateValue(AreaHistory.LOG_DATE_GMT, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(AreaHistory.USER_ID, value) ;
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
        setKey(AreaHistory.USER_ID, values, true) ;
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
        setKey(AreaHistory.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(AreaHistory.USER_ID, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(AreaHistory.USER_ID, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名称(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(AreaHistory.USER_NAME, value) ;
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
        setKey(AreaHistory.USER_NAME, values, true) ;
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
        setKey(AreaHistory.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(AreaHistory.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名称(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名称(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(AreaHistory.USER_NAME, value) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No、RFTNo(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(AreaHistory.TERMINAL_NO, value) ;
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
        setKey(AreaHistory.TERMINAL_NO, values, true) ;
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
        setKey(AreaHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(AreaHistory.TERMINAL_NO, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(AreaHistory.TERMINAL_NO, value) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名称(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値を端末名称(<code>TERMINAL_NAME</code>)にセットします。
     */
    public void setTerminalName(String value)
    {
        setKey(AreaHistory.TERMINAL_NAME, value) ;
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
        setKey(AreaHistory.TERMINAL_NAME, values, true) ;
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
        setKey(AreaHistory.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(AreaHistory.TERMINAL_NAME, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の更新値をセットします。
     * @param value 端末名称(<code>TERMINAL_NAME</code>)更新値
     */
    public void updateTerminalName(String value)
    {
        setAdhocUpdateValue(AreaHistory.TERMINAL_NAME, value) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IP_ADDRESS</code>)<br>
     * 文字列の検索値をIPアドレス(<code>IP_ADDRESS</code>)にセットします。
     */
    public void setIpAddress(String value)
    {
        setKey(AreaHistory.IP_ADDRESS, value) ;
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
        setKey(AreaHistory.IP_ADDRESS, values, true) ;
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
        setKey(AreaHistory.IP_ADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setIpAddress(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.IP_ADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setIpAddress(String value, String compcode)
    {
        setKey(AreaHistory.IP_ADDRESS, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の更新値をセットします。
     * @param value IPアドレス(<code>IP_ADDRESS</code>)更新値
     */
    public void updateIpAddress(String value)
    {
        setAdhocUpdateValue(AreaHistory.IP_ADDRESS, value) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をDS番号(<code>DS_NO</code>)にセットします。
     */
    public void setDsNo(String value)
    {
        setKey(AreaHistory.DS_NO, value) ;
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
        setKey(AreaHistory.DS_NO, values, true) ;
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
        setKey(AreaHistory.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(AreaHistory.DS_NO, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * DS番号(<code>DS_NO</code>)の更新値をセットします。
     * @param value DS番号(<code>DS_NO</code>)更新値
     */
    public void updateDsNo(String value)
    {
        setAdhocUpdateValue(AreaHistory.DS_NO, value) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値を画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)にセットします。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(AreaHistory.PAGENAMERESOURCEKEY, value) ;
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
        setKey(AreaHistory.PAGENAMERESOURCEKEY, values, true) ;
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
        setKey(AreaHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(AreaHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の更新値をセットします。
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)更新値
     */
    public void updatePagenameResourcekey(String value)
    {
        setAdhocUpdateValue(AreaHistory.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 文字列の検索値を更新区分(<code>UPDATE_KIND</code>)にセットします。
     */
    public void setUpdateKind(String value)
    {
        setKey(AreaHistory.UPDATE_KIND, value) ;
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
        setKey(AreaHistory.UPDATE_KIND, values, true) ;
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
        setKey(AreaHistory.UPDATE_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateKind(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_KIND, values, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateKind(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_KIND, value, compcode, "", "", true) ;
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
        setKey(AreaHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AreaHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新区分(<code>UPDATE_KIND</code>)の更新値をセットします。
     * @param value 更新区分(<code>UPDATE_KIND</code>)更新値
     */
    public void updateUpdateKind(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_KIND, value) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)<br>
     * 文字列の検索値をシステム管理区分(<code>MANAGEMENT_TYPE</code>)にセットします。
     */
    public void setManagementType(String value)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, value) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementType(String[] values)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, values, true) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setManagementType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setManagementType(String value, String compcode)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
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
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の更新値をセットします。
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)更新値
     */
    public void updateManagementType(String value)
    {
        setAdhocUpdateValue(AreaHistory.MANAGEMENT_TYPE, value) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をエリアNo.(<code>AREA_NO</code>)にセットします。
     */
    public void setAreaNo(String value)
    {
        setKey(AreaHistory.AREA_NO, value) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNo(String[] values)
    {
        setKey(AreaHistory.AREA_NO, values, true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNo(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(AreaHistory.AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
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
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリアNo.(<code>AREA_NO</code>)の更新値をセットします。
     * @param value エリアNo.(<code>AREA_NO</code>)更新値
     */
    public void updateAreaNo(String value)
    {
        setAdhocUpdateValue(AreaHistory.AREA_NO, value) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value エリア名称(<code>AREA_NAME</code>)<br>
     * 文字列の検索値をエリア名称(<code>AREA_NAME</code>)にセットします。
     */
    public void setAreaName(String value)
    {
        setKey(AreaHistory.AREA_NAME, value) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaName(String[] values)
    {
        setKey(AreaHistory.AREA_NAME, values, true) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaName(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaName(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_NAME, values, and_or_toNext) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaName(String value, String compcode)
    {
        setKey(AreaHistory.AREA_NAME, value, compcode, "", "", true) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
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
    public void setAreaName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAreaName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリア名称(<code>AREA_NAME</code>)の更新値をセットします。
     * @param value エリア名称(<code>AREA_NAME</code>)更新値
     */
    public void updateAreaName(String value)
    {
        setAdhocUpdateValue(AreaHistory.AREA_NAME, value) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value エリア種別(<code>AREA_TYPE</code>)<br>
     * 文字列の検索値をエリア種別(<code>AREA_TYPE</code>)にセットします。
     */
    public void setAreaType(String value)
    {
        setKey(AreaHistory.AREA_TYPE, value) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaType(String[] values)
    {
        setKey(AreaHistory.AREA_TYPE, values, true) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_TYPE, values, and_or_toNext) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaType(String value, String compcode)
    {
        setKey(AreaHistory.AREA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
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
    public void setAreaType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.AREA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAreaType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.AREA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリア種別(<code>AREA_TYPE</code>)の更新値をセットします。
     * @param value エリア種別(<code>AREA_TYPE</code>)更新値
     */
    public void updateAreaType(String value)
    {
        setAdhocUpdateValue(AreaHistory.AREA_TYPE, value) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 棚管理方式(<code>LOCATION_TYPE</code>)<br>
     * 文字列の検索値を棚管理方式(<code>LOCATION_TYPE</code>)にセットします。
     */
    public void setLocationType(String value)
    {
        setKey(AreaHistory.LOCATION_TYPE, value) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationType(String[] values)
    {
        setKey(AreaHistory.LOCATION_TYPE, values, true) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_TYPE, values, and_or_toNext) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationType(String value, String compcode)
    {
        setKey(AreaHistory.LOCATION_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
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
    public void setLocationType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>)の更新値をセットします。
     * @param value 棚管理方式(<code>LOCATION_TYPE</code>)更新値
     */
    public void updateLocationType(String value)
    {
        setAdhocUpdateValue(AreaHistory.LOCATION_TYPE, value) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 棚表示形式(<code>LOCATION_STYLE</code>)<br>
     * 文字列の検索値を棚表示形式(<code>LOCATION_STYLE</code>)にセットします。
     */
    public void setLocationStyle(String value)
    {
        setKey(AreaHistory.LOCATION_STYLE, value) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationStyle(String[] values)
    {
        setKey(AreaHistory.LOCATION_STYLE, values, true) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationStyle(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_STYLE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationStyle(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_STYLE, values, and_or_toNext) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationStyle(String value, String compcode)
    {
        setKey(AreaHistory.LOCATION_STYLE, value, compcode, "", "", true) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
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
    public void setLocationStyle(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationStyle(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の更新値をセットします。
     * @param value 棚表示形式(<code>LOCATION_STYLE</code>)更新値
     */
    public void updateLocationStyle(String value)
    {
        setAdhocUpdateValue(AreaHistory.LOCATION_STYLE, value) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)<br>
     * 文字列の検索値を仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)にセットします。
     */
    public void setTemporaryAreaType(String value)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, value) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryAreaType(String[] values)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, values, true) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryAreaType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTemporaryAreaType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTemporaryAreaType(String value, String compcode)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
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
    public void setTemporaryAreaType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTemporaryAreaType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)の更新値をセットします。
     * @param value 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)更新値
     */
    public void updateTemporaryAreaType(String value)
    {
        setAdhocUpdateValue(AreaHistory.TEMPORARY_AREA_TYPE, value) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
     * 
     * @param value 移動先仮置エリア(<code>TEMPORARY_AREA</code>)<br>
     * 文字列の検索値を移動先仮置エリア(<code>TEMPORARY_AREA</code>)にセットします。
     */
    public void setTemporaryArea(String value)
    {
        setKey(AreaHistory.TEMPORARY_AREA, value) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryArea(String[] values)
    {
        setKey(AreaHistory.TEMPORARY_AREA, values, true) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryArea(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTemporaryArea(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA, values, and_or_toNext) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTemporaryArea(String value, String compcode)
    {
        setKey(AreaHistory.TEMPORARY_AREA, value, compcode, "", "", true) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
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
    public void setTemporaryArea(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTemporaryArea(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.TEMPORARY_AREA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>)の更新値をセットします。
     * @param value 移動先仮置エリア(<code>TEMPORARY_AREA</code>)更新値
     */
    public void updateTemporaryArea(String value)
    {
        setAdhocUpdateValue(AreaHistory.TEMPORARY_AREA, value) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)<br>
     * 文字列の検索値を空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)にセットします。
     */
    public void setVacantSearchType(String value)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, value) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantSearchType(String[] values)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, values, true) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantSearchType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setVacantSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setVacantSearchType(String value, String compcode)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setVacantSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setVacantSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.VACANT_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)の更新値をセットします。
     * @param value 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)更新値
     */
    public void updateVacantSearchType(String value)
    {
        setAdhocUpdateValue(AreaHistory.VACANT_SEARCH_TYPE, value) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo(<code>WHSTATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo(<code>WHSTATION_NO</code>)にセットします。
     */
    public void setWhstationNo(String value)
    {
        setKey(AreaHistory.WHSTATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhstationNo(String[] values)
    {
        setKey(AreaHistory.WHSTATION_NO, values, true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhstationNo(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.WHSTATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhstationNo(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.WHSTATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhstationNo(String value, String compcode)
    {
        setKey(AreaHistory.WHSTATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
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
    public void setWhstationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.WHSTATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWhstationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.WHSTATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo(<code>WHSTATION_NO</code>)更新値
     */
    public void updateWhstationNo(String value)
    {
        setAdhocUpdateValue(AreaHistory.WHSTATION_NO, value) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 入荷エリア(<code>RECEIVING_AREA</code>)<br>
     * 文字列の検索値を入荷エリア(<code>RECEIVING_AREA</code>)にセットします。
     */
    public void setReceivingArea(String value)
    {
        setKey(AreaHistory.RECEIVING_AREA, value) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingArea(String[] values)
    {
        setKey(AreaHistory.RECEIVING_AREA, values, true) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingArea(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.RECEIVING_AREA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReceivingArea(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.RECEIVING_AREA, values, and_or_toNext) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReceivingArea(String value, String compcode)
    {
        setKey(AreaHistory.RECEIVING_AREA, value, compcode, "", "", true) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
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
    public void setReceivingArea(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.RECEIVING_AREA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReceivingArea(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.RECEIVING_AREA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>)の更新値をセットします。
     * @param value 入荷エリア(<code>RECEIVING_AREA</code>)更新値
     */
    public void updateReceivingArea(String value)
    {
        setAdhocUpdateValue(AreaHistory.RECEIVING_AREA, value) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)<br>
     * 文字列の検索値を修正後エリア名称(<code>UPDATE_AREA_NAME</code>)にセットします。
     */
    public void setUpdateAreaName(String value)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, value) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAreaName(String[] values)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, values, true) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAreaName(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateAreaName(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, values, and_or_toNext) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateAreaName(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
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
    public void setUpdateAreaName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateAreaName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_AREA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)の更新値をセットします。
     * @param value 修正後エリア名称(<code>UPDATE_AREA_NAME</code>)更新値
     */
    public void updateUpdateAreaName(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_AREA_NAME, value) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)<br>
     * 文字列の検索値を修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)にセットします。
     */
    public void setUpdateTempAreaType(String value)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, value) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTempAreaType(String[] values)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, values, true) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTempAreaType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateTempAreaType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateTempAreaType(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
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
    public void setUpdateTempAreaType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateTempAreaType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)の更新値をセットします。
     * @param value 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)更新値
     */
    public void updateUpdateTempAreaType(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_TEMP_AREA_TYPE, value) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
     * 
     * @param value 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)<br>
     * 文字列の検索値を修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)にセットします。
     */
    public void setUpdateTempArea(String value)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, value) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTempArea(String[] values)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, values, true) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTempArea(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateTempArea(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, values, and_or_toNext) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateTempArea(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, value, compcode, "", "", true) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
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
    public void setUpdateTempArea(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateTempArea(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_TEMP_AREA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)の更新値をセットします。
     * @param value 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)更新値
     */
    public void updateUpdateTempArea(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_TEMP_AREA, value) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)<br>
     * 文字列の検索値を修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)にセットします。
     */
    public void setUpdateVacantSearchType(String value)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, value) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateVacantSearchType(String[] values)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, values, true) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateVacantSearchType(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateVacantSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateVacantSearchType(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setUpdateVacantSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateVacantSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)の更新値をセットします。
     * @param value 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)更新値
     */
    public void updateUpdateVacantSearchType(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_VACANT_SEARCH_TYPE, value) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)<br>
     * 文字列の検索値を修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)にセットします。
     */
    public void setUpdateReceivingArea(String value)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, value) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReceivingArea(String[] values)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, values, true) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReceivingArea(String[] values, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateReceivingArea(String[] values, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, values, and_or_toNext) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateReceivingArea(String value, String compcode)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, value, compcode, "", "", true) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
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
    public void setUpdateReceivingArea(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateReceivingArea(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AreaHistory.UPDATE_RECEIVING_AREA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)の更新値をセットします。
     * @param value 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)更新値
     */
    public void updateUpdateReceivingArea(String value)
    {
        setAdhocUpdateValue(AreaHistory.UPDATE_RECEIVING_AREA, value) ;
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
        return "$Id: AreaHistoryAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
