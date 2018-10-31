// $Id: LocateHistorySearchKey.java 5653 2009-11-11 02:41:16Z okayama $
// $LastChangedRevision: 5653 $
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
import jp.co.daifuku.wms.base.entity.LocateHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * LOCATEHISTORY用の検索キークラスです。
 *
 * @version $Revision: 5653 $, $Date: 2009-11-11 11:41:16 +0900 (水, 11 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */


public class LocateHistorySearchKey
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
    public LocateHistorySearchKey()
    {
        super(LocateHistory.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 出力日時(<code>LOG_DATE</code>)<br>
     * 日付の検索値をセット出力日時(<code>LOG_DATE</code>)します。
     */
    public void setLogDate(Date value)
    {
        setKey(LocateHistory.LOG_DATE, value) ;
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
        setKey(LocateHistory.LOG_DATE, values, true) ;
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
        setKey(LocateHistory.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(LocateHistory.LOG_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
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
    public void setLogDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateOrder(boolean ascorder)
    {
        setOrder(LocateHistory.LOG_DATE, ascorder) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.LOG_DATE, ascorder) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のグループ順をセットします。
     */
    public void setLogDateGroup()
    {
        setGroup(LocateHistory.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateGroup(int prio)
    {
        setGroup(LocateHistory.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の情報取得を設定します。
     */
    public void setLogDateCollect()
    {
        setCollect(LocateHistory.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLogDateCollect(String sqlfunc)
    {
        setCollect(LocateHistory.LOG_DATE, sqlfunc, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値をセット出力日時(GMT)(<code>LOG_DATE_GMT</code>)します。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(LocateHistory.LOG_DATE_GMT, value) ;
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
        setKey(LocateHistory.LOG_DATE_GMT, values, true) ;
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
        setKey(LocateHistory.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(LocateHistory.LOG_DATE_GMT, value, compcode, "", "", true) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
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
    public void setLogDateGmt(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDateGmt(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateGmtOrder(boolean ascorder)
    {
        setOrder(LocateHistory.LOG_DATE_GMT, ascorder) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateGmtOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.LOG_DATE_GMT, ascorder) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のグループ順をセットします。
     */
    public void setLogDateGmtGroup()
    {
        setGroup(LocateHistory.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLogDateGmtGroup(int prio)
    {
        setGroup(LocateHistory.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の情報取得を設定します。
     */
    public void setLogDateGmtCollect()
    {
        setCollect(LocateHistory.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLogDateGmtCollect(String sqlfunc)
    {
        setCollect(LocateHistory.LOG_DATE_GMT, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
     */
    public void setUserId(String value)
    {
        setKey(LocateHistory.USER_ID, value) ;
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
        setKey(LocateHistory.USER_ID, values, true) ;
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
        setKey(LocateHistory.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(LocateHistory.USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
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
    public void setUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(LocateHistory.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(LocateHistory.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserIdGroup(int prio)
    {
        setGroup(LocateHistory.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(LocateHistory.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUserIdCollect(String sqlfunc)
    {
        setCollect(LocateHistory.USER_ID, sqlfunc, null) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をセットユーザ名称(<code>USER_NAME</code>)します。
     */
    public void setUserName(String value)
    {
        setKey(LocateHistory.USER_NAME, value) ;
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
        setKey(LocateHistory.USER_NAME, values, true) ;
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
        setKey(LocateHistory.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(LocateHistory.USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
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
    public void setUserName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserNameOrder(boolean ascorder)
    {
        setOrder(LocateHistory.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserNameOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のグループ順をセットします。
     */
    public void setUserNameGroup()
    {
        setGroup(LocateHistory.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserNameGroup(int prio)
    {
        setGroup(LocateHistory.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の情報取得を設定します。
     */
    public void setUserNameCollect()
    {
        setCollect(LocateHistory.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUserNameCollect(String sqlfunc)
    {
        setCollect(LocateHistory.USER_NAME, sqlfunc, null) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット端末No、RFTNo(<code>TERMINAL_NO</code>)します。
     */
    public void setTerminalNo(String value)
    {
        setKey(LocateHistory.TERMINAL_NO, value) ;
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
        setKey(LocateHistory.TERMINAL_NO, values, true) ;
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
        setKey(LocateHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(LocateHistory.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(LocateHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のソート順をセットします。
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
        setOrder(LocateHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(LocateHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoGroup(int prio)
    {
        setGroup(LocateHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(LocateHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名称(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値をセット端末名称(<code>TERMINAL_NAME</code>)します。
     */
    public void setTerminalName(String value)
    {
        setKey(LocateHistory.TERMINAL_NAME, value) ;
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
        setKey(LocateHistory.TERMINAL_NAME, values, true) ;
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
        setKey(LocateHistory.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(LocateHistory.TERMINAL_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNameOrder(boolean ascorder)
    {
        setOrder(LocateHistory.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNameOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     */
    public void setTerminalNameGroup()
    {
        setGroup(LocateHistory.TERMINAL_NAME) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNameGroup(int prio)
    {
        setGroup(LocateHistory.TERMINAL_NAME) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     */
    public void setTerminalNameCollect()
    {
        setCollect(LocateHistory.TERMINAL_NAME) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNameCollect(String sqlfunc)
    {
        setCollect(LocateHistory.TERMINAL_NAME, sqlfunc, null) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IP_ADDRESS</code>)<br>
     * 文字列の検索値をセットIPアドレス(<code>IP_ADDRESS</code>)します。
     */
    public void setIpAddress(String value)
    {
        setKey(LocateHistory.IP_ADDRESS, value) ;
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
        setKey(LocateHistory.IP_ADDRESS, values, true) ;
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
        setKey(LocateHistory.IP_ADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpAddress(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.IP_ADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIpAddress(String value, String compcode)
    {
        setKey(LocateHistory.IP_ADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
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
    public void setIpAddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpAddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIpAddressOrder(boolean ascorder)
    {
        setOrder(LocateHistory.IP_ADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpAddressOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.IP_ADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のグループ順をセットします。
     */
    public void setIpAddressGroup()
    {
        setGroup(LocateHistory.IP_ADDRESS) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpAddressGroup(int prio)
    {
        setGroup(LocateHistory.IP_ADDRESS) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の情報取得を設定します。
     */
    public void setIpAddressCollect()
    {
        setCollect(LocateHistory.IP_ADDRESS) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setIpAddressCollect(String sqlfunc)
    {
        setCollect(LocateHistory.IP_ADDRESS, sqlfunc, null) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をセットDS番号(<code>DS_NO</code>)します。
     */
    public void setDsNo(String value)
    {
        setKey(LocateHistory.DS_NO, value) ;
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
        setKey(LocateHistory.DS_NO, values, true) ;
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
        setKey(LocateHistory.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(LocateHistory.DS_NO, value, compcode, "", "", true) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
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
    public void setDsNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDsNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDsNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.DS_NO, ascorder) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDsNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.DS_NO, ascorder) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のグループ順をセットします。
     */
    public void setDsNoGroup()
    {
        setGroup(LocateHistory.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDsNoGroup(int prio)
    {
        setGroup(LocateHistory.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の情報取得を設定します。
     */
    public void setDsNoCollect()
    {
        setCollect(LocateHistory.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDsNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.DS_NO, sqlfunc, null) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をセット画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)します。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(LocateHistory.PAGENAMERESOURCEKEY, value) ;
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
        setKey(LocateHistory.PAGENAMERESOURCEKEY, values, true) ;
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
        setKey(LocateHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(LocateHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
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
        setKey(LocateHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(LocateHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPagenameResourcekeyOrder(boolean ascorder)
    {
        setOrder(LocateHistory.PAGENAMERESOURCEKEY, ascorder) ;
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
        setOrder(LocateHistory.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setPagenameResourcekeyGroup()
    {
        setGroup(LocateHistory.PAGENAMERESOURCEKEY) ;
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
        setGroup(LocateHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setPagenameResourcekeyCollect()
    {
        setCollect(LocateHistory.PAGENAMERESOURCEKEY) ;
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
        setCollect(LocateHistory.PAGENAMERESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 文字列の検索値をセット更新区分(<code>UPDATE_KIND</code>)します。
     */
    public void setUpdateKind(String value)
    {
        setKey(LocateHistory.UPDATE_KIND, value) ;
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
        setKey(LocateHistory.UPDATE_KIND, values, true) ;
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
        setKey(LocateHistory.UPDATE_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_KIND, values, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateKind(String value, String compcode)
    {
        setKey(LocateHistory.UPDATE_KIND, value, compcode, "", "", true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
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
    public void setUpdateKind(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateKindOrder(boolean ascorder)
    {
        setOrder(LocateHistory.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKindOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     */
    public void setUpdateKindGroup()
    {
        setGroup(LocateHistory.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKindGroup(int prio)
    {
        setGroup(LocateHistory.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     */
    public void setUpdateKindCollect()
    {
        setCollect(LocateHistory.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateKindCollect(String sqlfunc)
    {
        setCollect(LocateHistory.UPDATE_KIND, sqlfunc, null) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)<br>
     * 文字列の検索値をセットシステム管理区分(<code>MANAGEMENT_TYPE</code>)します。
     */
    public void setManagementType(String value)
    {
        setKey(LocateHistory.MANAGEMENT_TYPE, value) ;
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
        setKey(LocateHistory.MANAGEMENT_TYPE, values, true) ;
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
        setKey(LocateHistory.MANAGEMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setManagementType(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.MANAGEMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setManagementType(String value, String compcode)
    {
        setKey(LocateHistory.MANAGEMENT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
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
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setManagementTypeOrder(boolean ascorder)
    {
        setOrder(LocateHistory.MANAGEMENT_TYPE, ascorder) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setManagementTypeOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.MANAGEMENT_TYPE, ascorder) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のグループ順をセットします。
     */
    public void setManagementTypeGroup()
    {
        setGroup(LocateHistory.MANAGEMENT_TYPE) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setManagementTypeGroup(int prio)
    {
        setGroup(LocateHistory.MANAGEMENT_TYPE) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の情報取得を設定します。
     */
    public void setManagementTypeCollect()
    {
        setCollect(LocateHistory.MANAGEMENT_TYPE) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setManagementTypeCollect(String sqlfunc)
    {
        setCollect(LocateHistory.MANAGEMENT_TYPE, sqlfunc, null) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をセットエリアNo.(<code>AREA_NO</code>)します。
     */
    public void setAreaNo(String value)
    {
        setKey(LocateHistory.AREA_NO, value) ;
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
        setKey(LocateHistory.AREA_NO, values, true) ;
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
        setKey(LocateHistory.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(LocateHistory.AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
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
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAreaNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.AREA_NO, ascorder) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.AREA_NO, ascorder) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のグループ順をセットします。
     */
    public void setAreaNoGroup()
    {
        setGroup(LocateHistory.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNoGroup(int prio)
    {
        setGroup(LocateHistory.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の情報取得を設定します。
     */
    public void setAreaNoCollect()
    {
        setCollect(LocateHistory.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAreaNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.AREA_NO, sqlfunc, null) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 棚No.(<code>LOCATION_NO</code>)<br>
     * 文字列の検索値をセット棚No.(<code>LOCATION_NO</code>)します。
     */
    public void setLocationNo(String value)
    {
        setKey(LocateHistory.LOCATION_NO, value) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo(String[] values)
    {
        setKey(LocateHistory.LOCATION_NO, values, true) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo(String[] values, String and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationNo(String value, String compcode)
    {
        setKey(LocateHistory.LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
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
    public void setLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.LOCATION_NO, ascorder) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.LOCATION_NO, ascorder) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setLocationNoGroup()
    {
        setGroup(LocateHistory.LOCATION_NO) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNoGroup(int prio)
    {
        setGroup(LocateHistory.LOCATION_NO) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setLocationNoCollect()
    {
        setCollect(LocateHistory.LOCATION_NO) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocationNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value アイルNo.(<code>AISLE_NO</code>)<br>
     * 数値の検索値をセットアイルNo.(<code>AISLE_NO</code>)します。
     */
    public void setAisleNo(int value)
    {
        setKey(LocateHistory.AISLE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNo(int[] values)
    {
        setKey(LocateHistory.AISLE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNo(int[] values, String and_or_toNext)
    {
        setKey(LocateHistory.AISLE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleNo(int[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.AISLE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleNo(int value, String compcode)
    {
        setKey(LocateHistory.AISLE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.AISLE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.AISLE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.AISLE_NO, ascorder) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.AISLE_NO, ascorder) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のグループ順をセットします。
     */
    public void setAisleNoGroup()
    {
        setGroup(LocateHistory.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNoGroup(int prio)
    {
        setGroup(LocateHistory.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の情報取得を設定します。
     */
    public void setAisleNoCollect()
    {
        setCollect(LocateHistory.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAisleNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.AISLE_NO, sqlfunc, null) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value バンク(<code>BANK_NO</code>)<br>
     * 数値の検索値をセットバンク(<code>BANK_NO</code>)します。
     */
    public void setBankNo(int value)
    {
        setKey(LocateHistory.BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values)
    {
        setKey(LocateHistory.BANK_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values, String and_or_toNext)
    {
        setKey(LocateHistory.BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBankNo(int value, String compcode)
    {
        setKey(LocateHistory.BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBankNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.BANK_NO, ascorder) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBankNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.BANK_NO, ascorder) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のグループ順をセットします。
     */
    public void setBankNoGroup()
    {
        setGroup(LocateHistory.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBankNoGroup(int prio)
    {
        setGroup(LocateHistory.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の情報取得を設定します。
     */
    public void setBankNoCollect()
    {
        setCollect(LocateHistory.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBankNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.BANK_NO, sqlfunc, null) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value ベイ(<code>BAY_NO</code>)<br>
     * 数値の検索値をセットベイ(<code>BAY_NO</code>)します。
     */
    public void setBayNo(int value)
    {
        setKey(LocateHistory.BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values)
    {
        setKey(LocateHistory.BAY_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values, String and_or_toNext)
    {
        setKey(LocateHistory.BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBayNo(int value, String compcode)
    {
        setKey(LocateHistory.BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBayNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.BAY_NO, ascorder) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBayNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.BAY_NO, ascorder) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のグループ順をセットします。
     */
    public void setBayNoGroup()
    {
        setGroup(LocateHistory.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBayNoGroup(int prio)
    {
        setGroup(LocateHistory.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の情報取得を設定します。
     */
    public void setBayNoCollect()
    {
        setCollect(LocateHistory.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBayNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.BAY_NO, sqlfunc, null) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value レベル(<code>LEVEL_NO</code>)<br>
     * 数値の検索値をセットレベル(<code>LEVEL_NO</code>)します。
     */
    public void setLevelNo(int value)
    {
        setKey(LocateHistory.LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values)
    {
        setKey(LocateHistory.LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values, String and_or_toNext)
    {
        setKey(LocateHistory.LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLevelNo(int value, String compcode)
    {
        setKey(LocateHistory.LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLevelNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.LEVEL_NO, ascorder) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.LEVEL_NO, ascorder) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setLevelNoGroup()
    {
        setGroup(LocateHistory.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNoGroup(int prio)
    {
        setGroup(LocateHistory.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setLevelNoCollect()
    {
        setCollect(LocateHistory.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLevelNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.LEVEL_NO, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(LocateHistory.STATUS_FLAG, value) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(LocateHistory.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(LocateHistory.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(LocateHistory.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(LocateHistory.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(LocateHistory.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(LocateHistory.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(LocateHistory.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(LocateHistory.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)<br>
     * 数値の検索値をセット修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)します。
     */
    public void setUpdateAisleNo(int value)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAisleNo(int[] values)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAisleNo(int[] values, String and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateAisleNo(int[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateAisleNo(int value, String compcode)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAisleNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateAisleNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.UPDATE_AISLE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateAisleNoOrder(boolean ascorder)
    {
        setOrder(LocateHistory.UPDATE_AISLE_NO, ascorder) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAisleNoOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.UPDATE_AISLE_NO, ascorder) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)のグループ順をセットします。
     */
    public void setUpdateAisleNoGroup()
    {
        setGroup(LocateHistory.UPDATE_AISLE_NO) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateAisleNoGroup(int prio)
    {
        setGroup(LocateHistory.UPDATE_AISLE_NO) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の情報取得を設定します。
     */
    public void setUpdateAisleNoCollect()
    {
        setCollect(LocateHistory.UPDATE_AISLE_NO) ;
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateAisleNoCollect(String sqlfunc)
    {
        setCollect(LocateHistory.UPDATE_AISLE_NO, sqlfunc, null) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 棚表示形式(<code>LOCATION_STYLE</code>)<br>
     * 文字列の検索値をセット棚表示形式(<code>LOCATION_STYLE</code>)します。
     */
    public void setLocationStyle(String value)
    {
        setKey(LocateHistory.LOCATION_STYLE, value) ;
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
        setKey(LocateHistory.LOCATION_STYLE, values, true) ;
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
        setKey(LocateHistory.LOCATION_STYLE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationStyle(String[] values, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_STYLE, values, and_or_toNext) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationStyle(String value, String compcode)
    {
        setKey(LocateHistory.LOCATION_STYLE, value, compcode, "", "", true) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
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
    public void setLocationStyle(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationStyle(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LocateHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationStyleOrder(boolean ascorder)
    {
        setOrder(LocateHistory.LOCATION_STYLE, ascorder) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationStyleOrder(int prio, boolean ascorder)
    {
        setOrder(LocateHistory.LOCATION_STYLE, ascorder) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のグループ順をセットします。
     */
    public void setLocationStyleGroup()
    {
        setGroup(LocateHistory.LOCATION_STYLE) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationStyleGroup(int prio)
    {
        setGroup(LocateHistory.LOCATION_STYLE) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の情報取得を設定します。
     */
    public void setLocationStyleCollect()
    {
        setCollect(LocateHistory.LOCATION_STYLE) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocationStyleCollect(String sqlfunc)
    {
        setCollect(LocateHistory.LOCATION_STYLE, sqlfunc, null) ;
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
        return "$Id: LocateHistorySearchKey.java 5653 2009-11-11 02:41:16Z okayama $" ;
    }
}
