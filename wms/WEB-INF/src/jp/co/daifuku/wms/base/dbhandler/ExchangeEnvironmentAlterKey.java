// $Id: ExchangeEnvironmentAlterKey.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
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
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EXCHANGEENVIRONMENT用の更新キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class ExchangeEnvironmentAlterKey
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
    public static final StoreMetaData $storeMetaData = ExchangeEnvironment.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public ExchangeEnvironmentAlterKey()
    {
        super(ExchangeEnvironment.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込データ区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値を取込データ区分(<code>JOB_TYPE</code>)にセットします。
     */
    public void setJobType(String value)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value) ;
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
        setKey(ExchangeEnvironment.JOB_TYPE, values, true) ;
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
        setKey(ExchangeEnvironment.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, "", "", true) ;
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
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の更新値をセットします。
     * @param value 取込データ区分(<code>JOB_TYPE</code>)更新値
     */
    public void updateJobType(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.JOB_TYPE, value) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 送受信区分(<code>EXCHANGE_TYPE</code>)<br>
     * 文字列の検索値を送受信区分(<code>EXCHANGE_TYPE</code>)にセットします。
     */
    public void setExchangeType(String value)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value) ;
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
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, true) ;
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
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setExchangeType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setExchangeType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, "", "", true) ;
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
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の更新値をセットします。
     * @param value 送受信区分(<code>EXCHANGE_TYPE</code>)更新値
     */
    public void updateExchangeType(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.EXCHANGE_TYPE, value) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込種別(<code>DATA_TYPE</code>)<br>
     * 文字列の検索値を取込種別(<code>DATA_TYPE</code>)にセットします。
     */
    public void setDataType(String value)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value) ;
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
        setKey(ExchangeEnvironment.DATA_TYPE, values, true) ;
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
        setKey(ExchangeEnvironment.DATA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDataType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDataType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, "", "", true) ;
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
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込種別(<code>DATA_TYPE</code>)の更新値をセットします。
     * @param value 取込種別(<code>DATA_TYPE</code>)更新値
     */
    public void updateDataType(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.DATA_TYPE, value) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 取込ファイル名(<code>DATA_NAME</code>)<br>
     * 文字列の検索値を取込ファイル名(<code>DATA_NAME</code>)にセットします。
     */
    public void setDataName(String value)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value) ;
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
        setKey(ExchangeEnvironment.DATA_NAME, values, true) ;
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
        setKey(ExchangeEnvironment.DATA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDataName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_NAME, values, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDataName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, "", "", true) ;
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
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の更新値をセットします。
     * @param value 取込ファイル名(<code>DATA_NAME</code>)更新値
     */
    public void updateDataName(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.DATA_NAME, value) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 報告単位(<code>REPORT_TYPE</code>)<br>
     * 文字列の検索値を報告単位(<code>REPORT_TYPE</code>)にセットします。
     */
    public void setReportType(String value)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value) ;
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
        setKey(ExchangeEnvironment.REPORT_TYPE, values, true) ;
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
        setKey(ExchangeEnvironment.REPORT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReportType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReportType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, "", "", true) ;
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
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 報告単位(<code>REPORT_TYPE</code>)の更新値をセットします。
     * @param value 報告単位(<code>REPORT_TYPE</code>)更新値
     */
    public void updateReportType(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.REPORT_TYPE, value) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value ファイル交換フォルダパス(<code>FOLDER_NAME</code>)<br>
     * 文字列の検索値をファイル交換フォルダパス(<code>FOLDER_NAME</code>)にセットします。
     */
    public void setFolderName(String value)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFolderName(String[] values)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, true) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFolderName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFolderName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, and_or_toNext) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFolderName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
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
    public void setFolderName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFolderName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の更新値をセットします。
     * @param value ファイル交換フォルダパス(<code>FOLDER_NAME</code>)更新値
     */
    public void updateFolderName(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.FOLDER_NAME, value) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value ファイル名指定方法(<code>IS_PREFIX</code>)<br>
     * 文字列の検索値をファイル名指定方法(<code>IS_PREFIX</code>)にセットします。
     */
    public void setIsPrefix(String value)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefix(String[] values)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, true) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefix(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setIsPrefix(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, and_or_toNext) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setIsPrefix(String value, String compcode)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, "", "", true) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
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
    public void setIsPrefix(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setIsPrefix(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の更新値をセットします。
     * @param value ファイル名指定方法(<code>IS_PREFIX</code>)更新値
     */
    public void updateIsPrefix(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.IS_PREFIX, value) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value データID(<code>DATA_ID</code>)<br>
     * 文字列の検索値をデータID(<code>DATA_ID</code>)にセットします。
     */
    public void setDataId(String value)
    {
        setKey(ExchangeEnvironment.DATA_ID, value) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataId(String[] values)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, true) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataId(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDataId(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, and_or_toNext) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDataId(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, "", "", true) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
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
    public void setDataId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDataId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * データID(<code>DATA_ID</code>)の更新値をセットします。
     * @param value データID(<code>DATA_ID</code>)更新値
     */
    public void updateDataId(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.DATA_ID, value) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 拡張子(<code>EXTENTION</code>)<br>
     * 文字列の検索値を拡張子(<code>EXTENTION</code>)にセットします。
     */
    public void setExtention(String value)
    {
        setKey(ExchangeEnvironment.EXTENTION, value) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExtention(String[] values)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, true) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExtention(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setExtention(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, and_or_toNext) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setExtention(String value, String compcode)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, "", "", true) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
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
    public void setExtention(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setExtention(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 拡張子(<code>EXTENTION</code>)の更新値をセットします。
     * @param value 拡張子(<code>EXTENTION</code>)更新値
     */
    public void updateExtention(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.EXTENTION, value) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 取込可能行数(<code>MAX_RECORD</code>)<br>
     * 数値の検索値を取込可能行数(<code>MAX_RECORD</code>)にセットします。
     */
    public void setMaxRecord(int value)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecord(int[] values)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecord(int[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxRecord(int[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxRecord(int value, String compcode)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
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
    public void setMaxRecord(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxRecord(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の更新値をセットします。
     * @param value 取込可能行数(<code>MAX_RECORD</code>)更新値
     */
    public void updateMaxRecord(int value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value)) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)<br>
     * 文字列の検索値をTimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)にセットします。
     */
    public void setTimeKeeperPath(String value)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPath(String[] values)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, true) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPath(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTimeKeeperPath(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, and_or_toNext) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTimeKeeperPath(String value, String compcode)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, "", "", true) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
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
    public void setTimeKeeperPath(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTimeKeeperPath(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の更新値をセットします。
     * @param value TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)更新値
     */
    public void updateTimeKeeperPath(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.TIME_KEEPER_PATH, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value クラス名(<code>CLASS_NAME</code>)<br>
     * 文字列の検索値をクラス名(<code>CLASS_NAME</code>)にセットします。
     */
    public void setClassName(String value)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setClassName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setClassName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, "", "", true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
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
    public void setClassName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setClassName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * クラス名(<code>CLASS_NAME</code>)の更新値をセットします。
     * @param value クラス名(<code>CLASS_NAME</code>)更新値
     */
    public void updateClassName(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.CLASS_NAME, value) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)<br>
     * 文字列の検索値をエラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)にセットします。
     */
    public void setAutoPrintErrorList(String value)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorList(String[] values)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, true) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorList(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAutoPrintErrorList(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, and_or_toNext) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAutoPrintErrorList(String value, String compcode)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, "", "", true) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
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
    public void setAutoPrintErrorList(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAutoPrintErrorList(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の更新値をセットします。
     * @param value エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)更新値
     */
    public void updateAutoPrintErrorList(String value)
    {
        setAdhocUpdateValue(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxRecordWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ExchangeEnvironment.MAX_RECORD, source, addvalue);
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
        return "$Id: ExchangeEnvironmentAlterKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
