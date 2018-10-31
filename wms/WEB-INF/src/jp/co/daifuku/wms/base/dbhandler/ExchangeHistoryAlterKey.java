// $Id: ExchangeHistoryAlterKey.java 7239 2010-02-26 05:18:07Z okayama $
// $LastChangedRevision: 7239 $
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
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EXCHANGEHISTORY用の更新キークラスです。
 *
 * @version $Revision: 7239 $, $Date: 2010-02-26 14:18:07 +0900 (金, 26 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class ExchangeHistoryAlterKey
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
    public static final StoreMetaData $storeMetaData = ExchangeHistory.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public ExchangeHistoryAlterKey()
    {
        super(ExchangeHistory.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 送受信日時(<code>START_DATE</code>)<br>
     * 日付の検索値を送受信日時(<code>START_DATE</code>)にセットします。
     */
    public void setStartDate(Date value)
    {
        setKey(ExchangeHistory.START_DATE, value) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartDate(Date[] values)
    {
        setKey(ExchangeHistory.START_DATE, values, true) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
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
        setKey(ExchangeHistory.START_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.START_DATE, values, and_or_toNext) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartDate(Date value, String compcode)
    {
        setKey(ExchangeHistory.START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
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
        setKey(ExchangeHistory.START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信日時(<code>START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 送受信日時(<code>START_DATE</code>)の更新値をセットします。
     * @param value 送受信日時(<code>START_DATE</code>)更新値
     */
    public void updateStartDate(Date value)
    {
        setAdhocUpdateValue(ExchangeHistory.START_DATE, value) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込データ区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値を取込データ区分(<code>JOB_TYPE</code>)にセットします。
     */
    public void setJobType(String value)
    {
        setKey(ExchangeHistory.JOB_TYPE, value) ;
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
        setKey(ExchangeHistory.JOB_TYPE, values, true) ;
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
        setKey(ExchangeHistory.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(ExchangeHistory.JOB_TYPE, value, compcode, "", "", true) ;
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
        setKey(ExchangeHistory.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeHistory.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の更新値をセットします。
     * @param value 取込データ区分(<code>JOB_TYPE</code>)更新値
     */
    public void updateJobType(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.JOB_TYPE, value) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 送受信区分(<code>EXCHANGE_TYPE</code>)<br>
     * 文字列の検索値を送受信区分(<code>EXCHANGE_TYPE</code>)にセットします。
     */
    public void setExchangeType(String value)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, value) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeType(String[] values)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, values, true) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setExchangeType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setExchangeType(String value, String compcode)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
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
    public void setExchangeType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setExchangeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の更新値をセットします。
     * @param value 送受信区分(<code>EXCHANGE_TYPE</code>)更新値
     */
    public void updateExchangeType(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.EXCHANGE_TYPE, value) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込種別(<code>DATA_TYPE</code>)<br>
     * 文字列の検索値を取込種別(<code>DATA_TYPE</code>)にセットします。
     */
    public void setDataType(String value)
    {
        setKey(ExchangeHistory.DATA_TYPE, value) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataType(String[] values)
    {
        setKey(ExchangeHistory.DATA_TYPE, values, true) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDataType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDataType(String value, String compcode)
    {
        setKey(ExchangeHistory.DATA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
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
    public void setDataType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDataType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込種別(<code>DATA_TYPE</code>)の更新値をセットします。
     * @param value 取込種別(<code>DATA_TYPE</code>)更新値
     */
    public void updateDataType(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.DATA_TYPE, value) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 取込ファイル名(<code>DATA_NAME</code>)<br>
     * 文字列の検索値を取込ファイル名(<code>DATA_NAME</code>)にセットします。
     */
    public void setDataName(String value)
    {
        setKey(ExchangeHistory.DATA_NAME, value) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataName(String[] values)
    {
        setKey(ExchangeHistory.DATA_NAME, values, true) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDataName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_NAME, values, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDataName(String value, String compcode)
    {
        setKey(ExchangeHistory.DATA_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
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
    public void setDataName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDataName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.DATA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の更新値をセットします。
     * @param value 取込ファイル名(<code>DATA_NAME</code>)更新値
     */
    public void updateDataName(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.DATA_NAME, value) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 報告単位(<code>REPORT_TYPE</code>)<br>
     * 文字列の検索値を報告単位(<code>REPORT_TYPE</code>)にセットします。
     */
    public void setReportType(String value)
    {
        setKey(ExchangeHistory.REPORT_TYPE, value) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportType(String[] values)
    {
        setKey(ExchangeHistory.REPORT_TYPE, values, true) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.REPORT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReportType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.REPORT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReportType(String value, String compcode)
    {
        setKey(ExchangeHistory.REPORT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
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
    public void setReportType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.REPORT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReportType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.REPORT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 報告単位(<code>REPORT_TYPE</code>)の更新値をセットします。
     * @param value 報告単位(<code>REPORT_TYPE</code>)更新値
     */
    public void updateReportType(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.REPORT_TYPE, value) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 取込ファイル名(<code>FILE_NAME</code>)<br>
     * 文字列の検索値を取込ファイル名(<code>FILE_NAME</code>)にセットします。
     */
    public void setFileName(String value)
    {
        setKey(ExchangeHistory.FILE_NAME, value) ;
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
        setKey(ExchangeHistory.FILE_NAME, values, true) ;
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
        setKey(ExchangeHistory.FILE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFileName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFileName(String value, String compcode)
    {
        setKey(ExchangeHistory.FILE_NAME, value, compcode, "", "", true) ;
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
        setKey(ExchangeHistory.FILE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeHistory.FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込ファイル名(<code>FILE_NAME</code>)の更新値をセットします。
     * @param value 取込ファイル名(<code>FILE_NAME</code>)更新値
     */
    public void updateFileName(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.FILE_NAME, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS</code>)<br>
     * 文字列の検索値を状態(<code>STATUS</code>)にセットします。
     */
    public void setStatus(String value)
    {
        setKey(ExchangeHistory.STATUS, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values)
    {
        setKey(ExchangeHistory.STATUS, values, true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.STATUS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatus(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.STATUS, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatus(String value, String compcode)
    {
        setKey(ExchangeHistory.STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
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
    public void setStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS</code>)更新値
     */
    public void updateStatus(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.STATUS, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(ExchangeHistory.REGIST_DATE, value) ;
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
        setKey(ExchangeHistory.REGIST_DATE, values, true) ;
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
        setKey(ExchangeHistory.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ExchangeHistory.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(ExchangeHistory.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeHistory.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(ExchangeHistory.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(ExchangeHistory.REGIST_PNAME, value) ;
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
        setKey(ExchangeHistory.REGIST_PNAME, values, true) ;
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
        setKey(ExchangeHistory.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ExchangeHistory.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(ExchangeHistory.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeHistory.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, values, true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(ExchangeHistory.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, values, true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values, String and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
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
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeHistory.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(ExchangeHistory.LAST_UPDATE_PNAME, value) ;
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
        return "$Id: ExchangeHistoryAlterKey.java 7239 2010-02-26 05:18:07Z okayama $" ;
    }
}
