// $Id: WorkerResultAlterKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WORKERRESULT用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class WorkerResultAlterKey
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
    public static final StoreMetaData $storeMetaData = WorkerResult.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public WorkerResultAlterKey()
    {
        super(WorkerResult.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値を作業日(<code>WORK_DAY</code>)にセットします。
     */
    public void setWorkDay(String value)
    {
        setKey(WorkerResult.WORK_DAY, value) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values)
    {
        setKey(WorkerResult.WORK_DAY, values, true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
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
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業日(<code>WORK_DAY</code>)の更新値をセットします。
     * @param value 作業日(<code>WORK_DAY</code>)更新値
     */
    public void updateWorkDay(String value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_DAY, value) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業開始日時(<code>WORK_START_DATE</code>)<br>
     * 日付の検索値を作業開始日時(<code>WORK_START_DATE</code>)にセットします。
     */
    public void setWorkStartDate(Date value)
    {
        setKey(WorkerResult.WORK_START_DATE, value) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStartDate(Date[] values)
    {
        setKey(WorkerResult.WORK_START_DATE, values, true) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStartDate(Date[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkStartDate(Date value, String compcode)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
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
    public void setWorkStartDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の更新値をセットします。
     * @param value 作業開始日時(<code>WORK_START_DATE</code>)更新値
     */
    public void updateWorkStartDate(Date value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_START_DATE, value) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業終了日時(<code>WORK_END_DATE</code>)<br>
     * 日付の検索値を作業終了日時(<code>WORK_END_DATE</code>)にセットします。
     */
    public void setWorkEndDate(Date value)
    {
        setKey(WorkerResult.WORK_END_DATE, value) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkEndDate(Date[] values)
    {
        setKey(WorkerResult.WORK_END_DATE, values, true) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkEndDate(Date[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkEndDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkEndDate(Date value, String compcode)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
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
    public void setWorkEndDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkEndDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の更新値をセットします。
     * @param value 作業終了日時(<code>WORK_END_DATE</code>)更新値
     */
    public void updateWorkEndDate(Date value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_END_DATE, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(WorkerResult.USER_ID, value) ;
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
        setKey(WorkerResult.USER_ID, values, true) ;
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
        setKey(WorkerResult.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(WorkerResult.USER_ID, value, compcode, "", "", true) ;
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
        setKey(WorkerResult.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(WorkerResult.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(WorkerResult.USER_ID, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名称(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(WorkerResult.USER_NAME, value) ;
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
        setKey(WorkerResult.USER_NAME, values, true) ;
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
        setKey(WorkerResult.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(WorkerResult.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(WorkerResult.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(WorkerResult.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名称(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名称(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(WorkerResult.USER_NAME, value) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value RFTNo.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をRFTNo.(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(WorkerResult.TERMINAL_NO, value) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values)
    {
        setKey(WorkerResult.TERMINAL_NO, values, true) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(WorkerResult.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(WorkerResult.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(WorkerResult.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value RFTNo.(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(WorkerResult.TERMINAL_NO, value) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値を作業区分(<code>JOB_TYPE</code>)にセットします。
     */
    public void setJobType(String value)
    {
        setKey(WorkerResult.JOB_TYPE, value) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values)
    {
        setKey(WorkerResult.JOB_TYPE, values, true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values, String and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
    public void setJobType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業区分(<code>JOB_TYPE</code>)の更新値をセットします。
     * @param value 作業区分(<code>JOB_TYPE</code>)更新値
     */
    public void updateJobType(String value)
    {
        setAdhocUpdateValue(WorkerResult.JOB_TYPE, value) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 作業数量(<code>WORK_QTY</code>)<br>
     * 数値の検索値を作業数量(<code>WORK_QTY</code>)にセットします。
     */
    public void setWorkQty(int value)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkQty(int[] values)
    {
        setKey(WorkerResult.WORK_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkQty(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkQty(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkQty(int value, String compcode)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
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
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業数量(<code>WORK_QTY</code>)の更新値をセットします。
     * @param value 作業数量(<code>WORK_QTY</code>)更新値
     */
    public void updateWorkQty(int value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数（明細数）(<code>WORK_CNT</code>)<br>
     * 数値の検索値を作業回数（明細数）(<code>WORK_CNT</code>)にセットします。
     */
    public void setWorkCnt(int value)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkCnt(int[] values)
    {
        setKey(WorkerResult.WORK_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkCnt(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkCnt(int value, String compcode)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
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
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の更新値をセットします。
     * @param value 作業回数（明細数）(<code>WORK_CNT</code>)更新値
     */
    public void updateWorkCnt(int value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)<br>
     * 数値の検索値を作業回数（作業オーダ数）(<code>ORDER_CNT</code>)にセットします。
     */
    public void setOrderCnt(int value)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderCnt(int[] values)
    {
        setKey(WorkerResult.ORDER_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderCnt(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderCnt(int value, String compcode)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
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
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の更新値をセットします。
     * @param value 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)更新値
     */
    public void updateOrderCnt(int value)
    {
        setAdhocUpdateValue(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 作業時間（秒）(<code>WORK_TIME</code>)<br>
     * 数値の検索値を作業時間（秒）(<code>WORK_TIME</code>)にセットします。
     */
    public void setWorkTime(int value)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTime(int[] values)
    {
        setKey(WorkerResult.WORK_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTime(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkTime(int value, String compcode)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
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
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の更新値をセットします。
     * @param value 作業時間（秒）(<code>WORK_TIME</code>)更新値
     */
    public void updateWorkTime(int value)
    {
        setAdhocUpdateValue(WorkerResult.WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 休憩時間（秒）(<code>REST_TIME</code>)<br>
     * 数値の検索値を休憩時間（秒）(<code>REST_TIME</code>)にセットします。
     */
    public void setRestTime(int value)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestTime(int[] values)
    {
        setKey(WorkerResult.REST_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestTime(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRestTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRestTime(int value, String compcode)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
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
    public void setRestTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRestTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の更新値をセットします。
     * @param value 休憩時間（秒）(<code>REST_TIME</code>)更新値
     */
    public void updateRestTime(int value)
    {
        setAdhocUpdateValue(WorkerResult.REST_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 実作業時間（秒）(<code>REAL_WORK_TIME</code>)<br>
     * 数値の検索値を実作業時間（秒）(<code>REAL_WORK_TIME</code>)にセットします。
     */
    public void setRealWorkTime(int value)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRealWorkTime(int[] values)
    {
        setKey(WorkerResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRealWorkTime(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRealWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRealWorkTime(int value, String compcode)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
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
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の更新値をセットします。
     * @param value 実作業時間（秒）(<code>REAL_WORK_TIME</code>)更新値
     */
    public void updateRealWorkTime(int value)
    {
        setAdhocUpdateValue(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value ミススキャン数(<code>MISS_SCAN_CNT</code>)<br>
     * 数値の検索値をミススキャン数(<code>MISS_SCAN_CNT</code>)にセットします。
     */
    public void setMissScanCnt(int value)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMissScanCnt(int[] values)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMissScanCnt(int[] values, String and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMissScanCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMissScanCnt(int value, String compcode)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
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
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の更新値をセットします。
     * @param value ミススキャン数(<code>MISS_SCAN_CNT</code>)更新値
     */
    public void updateMissScanCnt(int value)
    {
        setAdhocUpdateValue(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.WORK_QTY, source, addvalue);
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.WORK_CNT, source, addvalue);
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.ORDER_CNT, source, addvalue);
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.WORK_TIME, source, addvalue);
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRestTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.REST_TIME, source, addvalue);
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRealWorkTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.REAL_WORK_TIME, source, addvalue);
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMissScanCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WorkerResult.MISS_SCAN_CNT, source, addvalue);
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
        return "$Id: WorkerResultAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
