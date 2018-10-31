// $Id: PrintHistorySearchKey.java 5192 2009-10-20 08:52:45Z okamura $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PRINTHISTORY用の検索キークラスです。
 *
 * @version $Revision: 5192 $, $Date: 2009-10-20 17:52:45 +0900 (火, 20 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */


public class PrintHistorySearchKey
        extends DefaultSQLSearchKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //  public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のテーブル名とカラムのリストを準備して、インスタンスを
     * 生成します。
     */
    public PrintHistorySearchKey()
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
     * 文字列の検索値をセットファイル名(<code>FILE_NAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFileName(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFileName(String value, String compcode)
    {
        setKey(PrintHistory.FILE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFileName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFileNameOrder(boolean ascorder)
    {
        setOrder(PrintHistory.FILE_NAME, ascorder) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFileNameOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.FILE_NAME, ascorder) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)のグループ順をセットします。
     */
    public void setFileNameGroup()
    {
        setGroup(PrintHistory.FILE_NAME) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFileNameGroup(int prio)
    {
        setGroup(PrintHistory.FILE_NAME) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の情報取得を設定します。
     */
    public void setFileNameCollect()
    {
        setCollect(PrintHistory.FILE_NAME) ;
    }

    /**
     * ファイル名(<code>FILE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFileNameCollect(String sqlfunc)
    {
        setCollect(PrintHistory.FILE_NAME, sqlfunc, null) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)<br>
     * 文字列の検索値をセットエクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setXmlFileName(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, values, and_or_toNext) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setXmlFileName(String value, String compcode)
    {
        setKey(PrintHistory.XML_FILE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setXmlFileName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.XML_FILE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setXmlFileNameOrder(boolean ascorder)
    {
        setOrder(PrintHistory.XML_FILE_NAME, ascorder) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setXmlFileNameOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.XML_FILE_NAME, ascorder) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)のグループ順をセットします。
     */
    public void setXmlFileNameGroup()
    {
        setGroup(PrintHistory.XML_FILE_NAME) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setXmlFileNameGroup(int prio)
    {
        setGroup(PrintHistory.XML_FILE_NAME) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の情報取得を設定します。
     */
    public void setXmlFileNameCollect()
    {
        setCollect(PrintHistory.XML_FILE_NAME) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setXmlFileNameCollect(String sqlfunc)
    {
        setCollect(PrintHistory.XML_FILE_NAME, sqlfunc, null) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)<br>
     * 文字列の検索値をセット帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setListResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setListResourcekey(String value, String compcode)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setListResourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.LIST_RESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setListResourcekeyOrder(boolean ascorder)
    {
        setOrder(PrintHistory.LIST_RESOURCEKEY, ascorder) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setListResourcekeyOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.LIST_RESOURCEKEY, ascorder) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setListResourcekeyGroup()
    {
        setGroup(PrintHistory.LIST_RESOURCEKEY) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setListResourcekeyGroup(int prio)
    {
        setGroup(PrintHistory.LIST_RESOURCEKEY) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setListResourcekeyCollect()
    {
        setCollect(PrintHistory.LIST_RESOURCEKEY) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setListResourcekeyCollect(String sqlfunc)
    {
        setCollect(PrintHistory.LIST_RESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 発行日時(<code>PRINT_DATE</code>)<br>
     * 日付の検索値をセット発行日時(<code>PRINT_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrintDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, values, and_or_toNext) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPrintDate(Date value, String compcode)
    {
        setKey(PrintHistory.PRINT_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrintDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.PRINT_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPrintDateOrder(boolean ascorder)
    {
        setOrder(PrintHistory.PRINT_DATE, ascorder) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrintDateOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.PRINT_DATE, ascorder) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)のグループ順をセットします。
     */
    public void setPrintDateGroup()
    {
        setGroup(PrintHistory.PRINT_DATE) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrintDateGroup(int prio)
    {
        setGroup(PrintHistory.PRINT_DATE) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の情報取得を設定します。
     */
    public void setPrintDateCollect()
    {
        setCollect(PrintHistory.PRINT_DATE) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPrintDateCollect(String sqlfunc)
    {
        setCollect(PrintHistory.PRINT_DATE, sqlfunc, null) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をセット画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameResourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPagenameResourcekeyOrder(boolean ascorder)
    {
        setOrder(PrintHistory.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameResourcekeyOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setPagenameResourcekeyGroup()
    {
        setGroup(PrintHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameResourcekeyGroup(int prio)
    {
        setGroup(PrintHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setPagenameResourcekeyCollect()
    {
        setCollect(PrintHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPagenameResourcekeyCollect(String sqlfunc)
    {
        setCollect(PrintHistory.PAGENAMERESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット端末No.(<code>TERMINAL_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(PrintHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(PrintHistory.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PrintHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(PrintHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(PrintHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(PrintHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoGroup(int prio)
    {
        setGroup(PrintHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(PrintHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNoCollect(String sqlfunc)
    {
        setCollect(PrintHistory.TERMINAL_NO, sqlfunc, null) ;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PrintHistorySearchKey.java 5192 2009-10-20 08:52:45Z okamura $" ;
    }
}
