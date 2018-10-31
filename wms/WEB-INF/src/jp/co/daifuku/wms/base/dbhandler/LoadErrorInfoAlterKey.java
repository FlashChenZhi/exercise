// $Id: LoadErrorInfoAlterKey.java 5356 2009-11-02 00:47:10Z okamura $
// $LastChangedRevision: 5356 $
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
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * LOADERRORINFO用の更新キークラスです。
 *
 * @version $Revision: 5356 $, $Date: 2009-11-02 09:47:10 +0900 (月, 02 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class LoadErrorInfoAlterKey
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
    public static final StoreMetaData $storeMetaData = LoadErrorInfo.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public LoadErrorInfoAlterKey()
    {
        super(LoadErrorInfo.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 取込開始日時(<code>START_DATE</code>)<br>
     * 日付の検索値を取込開始日時(<code>START_DATE</code>)にセットします。
     */
    public void setStartDate(Date value)
    {
        setKey(LoadErrorInfo.START_DATE, value) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartDate(Date[] values)
    {
        setKey(LoadErrorInfo.START_DATE, values, true) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartDate(Date[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.START_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.START_DATE, values, and_or_toNext) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartDate(Date value, String compcode)
    {
        setKey(LoadErrorInfo.START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
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
    public void setStartDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込開始日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込開始日時(<code>START_DATE</code>)の更新値をセットします。
     * @param value 取込開始日時(<code>START_DATE</code>)更新値
     */
    public void updateStartDate(Date value)
    {
        setAdhocUpdateValue(LoadErrorInfo.START_DATE, value) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 取込ファイル名(<code>FILE_NAME</code>)<br>
     * 文字列の検索値を取込ファイル名(<code>FILE_NAME</code>)にセットします。
     */
    public void setFileName(String value)
    {
        setKey(LoadErrorInfo.FILE_NAME, value) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileName(String[] values)
    {
        setKey(LoadErrorInfo.FILE_NAME, values, true) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileName(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFileName(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFileName(String value, String compcode)
    {
        setKey(LoadErrorInfo.FILE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
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
    public void setFileName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFileName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の更新値をセットします。
     * @param value 取込ファイル名(<code>FILE_NAME</code>)更新値
     */
    public void updateFileName(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.FILE_NAME, value) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value ファイル行No.(<code>FILE_LINE_NO</code>)<br>
     * 数値の検索値をファイル行No.(<code>FILE_LINE_NO</code>)にセットします。
     */
    public void setFileLineNo(int value)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNo(int[] values)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNo(int[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFileLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFileLineNo(int value, String compcode)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
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
    public void setFileLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFileLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の更新値をセットします。
     * @param value ファイル行No.(<code>FILE_LINE_NO</code>)更新値
     */
    public void updateFileLineNo(int value)
    {
        setAdhocUpdateValue(LoadErrorInfo.FILE_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込データ区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値を取込データ区分(<code>JOB_TYPE</code>)にセットします。
     */
    public void setJobType(String value)
    {
        setKey(LoadErrorInfo.JOB_TYPE, value) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values)
    {
        setKey(LoadErrorInfo.JOB_TYPE, values, true) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
        setKey(LoadErrorInfo.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(LoadErrorInfo.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
        setKey(LoadErrorInfo.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の更新値をセットします。
     * @param value 取込データ区分(<code>JOB_TYPE</code>)更新値
     */
    public void updateJobType(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.JOB_TYPE, value) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
     * 
     * @param value エラーレベル(<code>ERROR_LEVEL</code>)<br>
     * 文字列の検索値をエラーレベル(<code>ERROR_LEVEL</code>)にセットします。
     */
    public void setErrorLevel(String value)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, value) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorLevel(String[] values)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, values, true) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorLevel(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setErrorLevel(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, values, and_or_toNext) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setErrorLevel(String value, String compcode)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, value, compcode, "", "", true) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
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
    public void setErrorLevel(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setErrorLevel(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_LEVEL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エラーレベル(<code>ERROR_LEVEL</code>)の更新値をセットします。
     * @param value エラーレベル(<code>ERROR_LEVEL</code>)更新値
     */
    public void updateErrorLevel(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.ERROR_LEVEL, value) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
     * 
     * @param value エラー区分(<code>ERROR_FLAG</code>)<br>
     * 文字列の検索値をエラー区分(<code>ERROR_FLAG</code>)にセットします。
     */
    public void setErrorFlag(String value)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, value) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorFlag(String[] values)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, values, true) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorFlag(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setErrorFlag(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, values, and_or_toNext) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setErrorFlag(String value, String compcode)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
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
    public void setErrorFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setErrorFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ERROR_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エラー区分(<code>ERROR_FLAG</code>)の更新値をセットします。
     * @param value エラー区分(<code>ERROR_FLAG</code>)更新値
     */
    public void updateErrorFlag(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.ERROR_FLAG, value) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
     * 
     * @param value 項目番号(<code>ITEM_NO</code>)<br>
     * 文字列の検索値を項目番号(<code>ITEM_NO</code>)にセットします。
     */
    public void setItemNo(String value)
    {
        setKey(LoadErrorInfo.ITEM_NO, value) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemNo(String[] values)
    {
        setKey(LoadErrorInfo.ITEM_NO, values, true) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemNo(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ITEM_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemNo(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ITEM_NO, values, and_or_toNext) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemNo(String value, String compcode)
    {
        setKey(LoadErrorInfo.ITEM_NO, value, compcode, "", "", true) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
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
    public void setItemNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.ITEM_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItemNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.ITEM_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 項目番号(<code>ITEM_NO</code>)の更新値をセットします。
     * @param value 項目番号(<code>ITEM_NO</code>)更新値
     */
    public void updateItemNo(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.ITEM_NO, value) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value データ(<code>DATA</code>)<br>
     * 文字列の検索値をデータ(<code>DATA</code>)にセットします。
     */
    public void setData(String value)
    {
        setKey(LoadErrorInfo.DATA, value) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setData(String[] values)
    {
        setKey(LoadErrorInfo.DATA, values, true) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setData(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.DATA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setData(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.DATA, values, and_or_toNext) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setData(String value, String compcode)
    {
        setKey(LoadErrorInfo.DATA, value, compcode, "", "", true) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
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
    public void setData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * データ(<code>DATA</code>)の更新値をセットします。
     * @param value データ(<code>DATA</code>)更新値
     */
    public void updateData(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.DATA, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(LoadErrorInfo.REGIST_DATE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values)
    {
        setKey(LoadErrorInfo.REGIST_DATE, values, true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(LoadErrorInfo.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
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
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(LoadErrorInfo.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, values, true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values, String and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
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
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadErrorInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(LoadErrorInfo.REGIST_PNAME, value) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateFileLineNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(LoadErrorInfo.FILE_LINE_NO, source, addvalue);
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
        return "$Id: LoadErrorInfoAlterKey.java 5356 2009-11-02 00:47:10Z okamura $" ;
    }
}
