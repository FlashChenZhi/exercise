// $Id: PrintHistoryAlterKey.java 5192 2009-10-20 08:52:45Z okamura $
// $LastChangedRevision: 5192 $
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
import jp.co.daifuku.wms.base.entity.PrintHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PRINTHISTORY用の更新キークラスです。
 *
 * @version $Revision: 5192 $, $Date: 2009-10-20 17:52:45 +0900 (火, 20 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class PrintHistoryAlterKey
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
    public static final StoreMetaData $storeMetaData = PrintHistory.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PrintHistoryAlterKey()
    {
        super(PrintHistory.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value ファイル名(<code>FILE_NAME</code>)<br>
     * 文字列の検索値をファイル名(<code>FILE_NAME</code>)にセットします。
     */
    public void setFileName(String value)
    {
        setKey(PrintHistory.FILE_NAME, value) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileName(String[] values)
    {
        setKey(PrintHistory.FILE_NAME, values, true) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
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
        setKey(PrintHistory.FILE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFileName(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFileName(String value, String compcode)
    {
        setKey(PrintHistory.FILE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
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
        setKey(PrintHistory.FILE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFileName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ファイル名(<code>FILE_NAME</code>)の更新値をセットします。
     * @param value ファイル名(<code>FILE_NAME</code>)更新値
     */
    public void updateFileName(String value)
    {
        setAdhocUpdateValue(PrintHistory.FILE_NAME, value) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)<br>
     * 文字列の検索値をエクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)にセットします。
     */
    public void setXmlFileName(String value)
    {
        setKey(PrintHistory.XML_FILE_NAME, value) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setXmlFileName(String[] values)
    {
        setKey(PrintHistory.XML_FILE_NAME, values, true) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setXmlFileName(String[] values, String and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setXmlFileName(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setXmlFileName(String value, String compcode)
    {
        setKey(PrintHistory.XML_FILE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
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
    public void setXmlFileName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setXmlFileName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の更新値をセットします。
     * @param value エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)更新値
     */
    public void updateXmlFileName(String value)
    {
        setAdhocUpdateValue(PrintHistory.XML_FILE_NAME, value) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)<br>
     * 文字列の検索値を帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)にセットします。
     */
    public void setListResourcekey(String value)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setListResourcekey(String[] values)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, values, true) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setListResourcekey(String[] values, String and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setListResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setListResourcekey(String value, String compcode)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
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
    public void setListResourcekey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setListResourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の更新値をセットします。
     * @param value 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)更新値
     */
    public void updateListResourcekey(String value)
    {
        setAdhocUpdateValue(PrintHistory.LIST_RESOURCEKEY, value) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 発行日時(<code>PRINT_DATE</code>)<br>
     * 日付の検索値を発行日時(<code>PRINT_DATE</code>)にセットします。
     */
    public void setPrintDate(Date value)
    {
        setKey(PrintHistory.PRINT_DATE, value) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintDate(Date[] values)
    {
        setKey(PrintHistory.PRINT_DATE, values, true) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintDate(Date[] values, String and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPrintDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, values, and_or_toNext) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPrintDate(Date value, String compcode)
    {
        setKey(PrintHistory.PRINT_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
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
    public void setPrintDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPrintDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 発行日時(<code>PRINT_DATE</code>)の更新値をセットします。
     * @param value 発行日時(<code>PRINT_DATE</code>)更新値
     */
    public void updatePrintDate(Date value)
    {
        setAdhocUpdateValue(PrintHistory.PRINT_DATE, value) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値を画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)にセットします。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value) ;
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
        setKey(PrintHistory.PAGENAMERESOURCEKEY, values, true) ;
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
        setKey(PrintHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
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
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の更新値をセットします。
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)更新値
     */
    public void updatePagenameResourcekey(String value)
    {
        setAdhocUpdateValue(PrintHistory.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No.(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(PrintHistory.TERMINAL_NO, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values)
    {
        setKey(PrintHistory.TERMINAL_NO, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(PrintHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(PrintHistory.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(PrintHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No.(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No.(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(PrintHistory.TERMINAL_NO, value) ;
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
        return "$Id: PrintHistoryAlterKey.java 5192 2009-10-20 08:52:45Z okamura $" ;
    }
}
