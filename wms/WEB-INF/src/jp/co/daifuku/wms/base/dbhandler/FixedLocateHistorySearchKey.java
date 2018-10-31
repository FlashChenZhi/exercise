// $Id: FixedLocateHistorySearchKey.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
import jp.co.daifuku.wms.base.entity.FixedLocateHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * FIXEDLOCATEHISTORY用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class FixedLocateHistorySearchKey
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
    public FixedLocateHistorySearchKey()
    {
        super(FixedLocateHistory.STORE_NAME) ;
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
        setKey(FixedLocateHistory.LOG_DATE, value) ;
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
        setKey(FixedLocateHistory.LOG_DATE, values, true) ;
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
        setKey(FixedLocateHistory.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(FixedLocateHistory.LOG_DATE, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.LOG_DATE, ascorder) ;
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
        setOrder(FixedLocateHistory.LOG_DATE, ascorder) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のグループ順をセットします。
     */
    public void setLogDateGroup()
    {
        setGroup(FixedLocateHistory.LOG_DATE) ;
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
        setGroup(FixedLocateHistory.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の情報取得を設定します。
     */
    public void setLogDateCollect()
    {
        setCollect(FixedLocateHistory.LOG_DATE) ;
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
        setCollect(FixedLocateHistory.LOG_DATE, sqlfunc, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値をセット出力日時(GMT)(<code>LOG_DATE_GMT</code>)します。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(FixedLocateHistory.LOG_DATE_GMT, value) ;
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
        setKey(FixedLocateHistory.LOG_DATE_GMT, values, true) ;
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
        setKey(FixedLocateHistory.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(FixedLocateHistory.LOG_DATE_GMT, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateGmtOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.LOG_DATE_GMT, ascorder) ;
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
        setOrder(FixedLocateHistory.LOG_DATE_GMT, ascorder) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のグループ順をセットします。
     */
    public void setLogDateGmtGroup()
    {
        setGroup(FixedLocateHistory.LOG_DATE_GMT) ;
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
        setGroup(FixedLocateHistory.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の情報取得を設定します。
     */
    public void setLogDateGmtCollect()
    {
        setCollect(FixedLocateHistory.LOG_DATE_GMT) ;
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
        setCollect(FixedLocateHistory.LOG_DATE_GMT, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
     */
    public void setUserId(String value)
    {
        setKey(FixedLocateHistory.USER_ID, value) ;
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
        setKey(FixedLocateHistory.USER_ID, values, true) ;
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
        setKey(FixedLocateHistory.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(FixedLocateHistory.USER_ID, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.USER_ID, ascorder) ;
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
        setOrder(FixedLocateHistory.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(FixedLocateHistory.USER_ID) ;
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
        setGroup(FixedLocateHistory.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(FixedLocateHistory.USER_ID) ;
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
        setCollect(FixedLocateHistory.USER_ID, sqlfunc, null) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をセットユーザ名称(<code>USER_NAME</code>)します。
     */
    public void setUserName(String value)
    {
        setKey(FixedLocateHistory.USER_NAME, value) ;
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
        setKey(FixedLocateHistory.USER_NAME, values, true) ;
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
        setKey(FixedLocateHistory.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(FixedLocateHistory.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserNameOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.USER_NAME, ascorder) ;
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
        setOrder(FixedLocateHistory.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のグループ順をセットします。
     */
    public void setUserNameGroup()
    {
        setGroup(FixedLocateHistory.USER_NAME) ;
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
        setGroup(FixedLocateHistory.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の情報取得を設定します。
     */
    public void setUserNameCollect()
    {
        setCollect(FixedLocateHistory.USER_NAME) ;
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
        setCollect(FixedLocateHistory.USER_NAME, sqlfunc, null) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット端末No、RFTNo(<code>TERMINAL_NO</code>)します。
     */
    public void setTerminalNo(String value)
    {
        setKey(FixedLocateHistory.TERMINAL_NO, value) ;
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
        setKey(FixedLocateHistory.TERMINAL_NO, values, true) ;
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
        setKey(FixedLocateHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(FixedLocateHistory.TERMINAL_NO, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.TERMINAL_NO, ascorder) ;
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
        setOrder(FixedLocateHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(FixedLocateHistory.TERMINAL_NO) ;
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
        setGroup(FixedLocateHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(FixedLocateHistory.TERMINAL_NO) ;
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
        setCollect(FixedLocateHistory.TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名称(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値をセット端末名称(<code>TERMINAL_NAME</code>)します。
     */
    public void setTerminalName(String value)
    {
        setKey(FixedLocateHistory.TERMINAL_NAME, value) ;
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
        setKey(FixedLocateHistory.TERMINAL_NAME, values, true) ;
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
        setKey(FixedLocateHistory.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(FixedLocateHistory.TERMINAL_NAME, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNameOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.TERMINAL_NAME, ascorder) ;
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
        setOrder(FixedLocateHistory.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     */
    public void setTerminalNameGroup()
    {
        setGroup(FixedLocateHistory.TERMINAL_NAME) ;
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
        setGroup(FixedLocateHistory.TERMINAL_NAME) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     */
    public void setTerminalNameCollect()
    {
        setCollect(FixedLocateHistory.TERMINAL_NAME) ;
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
        setCollect(FixedLocateHistory.TERMINAL_NAME, sqlfunc, null) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IP_ADDRESS</code>)<br>
     * 文字列の検索値をセットIPアドレス(<code>IP_ADDRESS</code>)します。
     */
    public void setIpAddress(String value)
    {
        setKey(FixedLocateHistory.IP_ADDRESS, value) ;
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
        setKey(FixedLocateHistory.IP_ADDRESS, values, true) ;
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
        setKey(FixedLocateHistory.IP_ADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpAddress(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.IP_ADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIpAddress(String value, String compcode)
    {
        setKey(FixedLocateHistory.IP_ADDRESS, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIpAddressOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.IP_ADDRESS, ascorder) ;
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
        setOrder(FixedLocateHistory.IP_ADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のグループ順をセットします。
     */
    public void setIpAddressGroup()
    {
        setGroup(FixedLocateHistory.IP_ADDRESS) ;
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
        setGroup(FixedLocateHistory.IP_ADDRESS) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の情報取得を設定します。
     */
    public void setIpAddressCollect()
    {
        setCollect(FixedLocateHistory.IP_ADDRESS) ;
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
        setCollect(FixedLocateHistory.IP_ADDRESS, sqlfunc, null) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をセットDS番号(<code>DS_NO</code>)します。
     */
    public void setDsNo(String value)
    {
        setKey(FixedLocateHistory.DS_NO, value) ;
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
        setKey(FixedLocateHistory.DS_NO, values, true) ;
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
        setKey(FixedLocateHistory.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(FixedLocateHistory.DS_NO, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDsNoOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.DS_NO, ascorder) ;
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
        setOrder(FixedLocateHistory.DS_NO, ascorder) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のグループ順をセットします。
     */
    public void setDsNoGroup()
    {
        setGroup(FixedLocateHistory.DS_NO) ;
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
        setGroup(FixedLocateHistory.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の情報取得を設定します。
     */
    public void setDsNoCollect()
    {
        setCollect(FixedLocateHistory.DS_NO) ;
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
        setCollect(FixedLocateHistory.DS_NO, sqlfunc, null) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をセット画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)します。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, value) ;
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
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, values, true) ;
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
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPagenameResourcekeyOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.PAGENAMERESOURCEKEY, ascorder) ;
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
        setOrder(FixedLocateHistory.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setPagenameResourcekeyGroup()
    {
        setGroup(FixedLocateHistory.PAGENAMERESOURCEKEY) ;
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
        setGroup(FixedLocateHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setPagenameResourcekeyCollect()
    {
        setCollect(FixedLocateHistory.PAGENAMERESOURCEKEY) ;
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
        setCollect(FixedLocateHistory.PAGENAMERESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 文字列の検索値をセット更新区分(<code>UPDATE_KIND</code>)します。
     */
    public void setUpdateKind(String value)
    {
        setKey(FixedLocateHistory.UPDATE_KIND, value) ;
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
        setKey(FixedLocateHistory.UPDATE_KIND, values, true) ;
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
        setKey(FixedLocateHistory.UPDATE_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_KIND, values, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateKind(String value, String compcode)
    {
        setKey(FixedLocateHistory.UPDATE_KIND, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateKindOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.UPDATE_KIND, ascorder) ;
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
        setOrder(FixedLocateHistory.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     */
    public void setUpdateKindGroup()
    {
        setGroup(FixedLocateHistory.UPDATE_KIND) ;
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
        setGroup(FixedLocateHistory.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     */
    public void setUpdateKindCollect()
    {
        setCollect(FixedLocateHistory.UPDATE_KIND) ;
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
        setCollect(FixedLocateHistory.UPDATE_KIND, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(FixedLocateHistory.CONSIGNOR_CODE, value) ;
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
        setKey(FixedLocateHistory.CONSIGNOR_CODE, values, true) ;
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
        setKey(FixedLocateHistory.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(FixedLocateHistory.CONSIGNOR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
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
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(FixedLocateHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeOrder(int prio, boolean ascorder)
    {
        setOrder(FixedLocateHistory.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(FixedLocateHistory.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeGroup(int prio)
    {
        setGroup(FixedLocateHistory.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(FixedLocateHistory.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setConsignorCodeCollect(String sqlfunc)
    {
        setCollect(FixedLocateHistory.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(FixedLocateHistory.ITEM_CODE, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values)
    {
        setKey(FixedLocateHistory.ITEM_CODE, values, true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values, String and_or_toNext)
    {
        setKey(FixedLocateHistory.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(FixedLocateHistory.ITEM_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
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
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(FixedLocateHistory.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeOrder(int prio, boolean ascorder)
    {
        setOrder(FixedLocateHistory.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(FixedLocateHistory.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeGroup(int prio)
    {
        setGroup(FixedLocateHistory.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(FixedLocateHistory.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItemCodeCollect(String sqlfunc)
    {
        setCollect(FixedLocateHistory.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をセットエリアNo.(<code>AREA_NO</code>)します。
     */
    public void setAreaNo(String value)
    {
        setKey(FixedLocateHistory.AREA_NO, value) ;
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
        setKey(FixedLocateHistory.AREA_NO, values, true) ;
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
        setKey(FixedLocateHistory.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(FixedLocateHistory.AREA_NO, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAreaNoOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.AREA_NO, ascorder) ;
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
        setOrder(FixedLocateHistory.AREA_NO, ascorder) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のグループ順をセットします。
     */
    public void setAreaNoGroup()
    {
        setGroup(FixedLocateHistory.AREA_NO) ;
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
        setGroup(FixedLocateHistory.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の情報取得を設定します。
     */
    public void setAreaNoCollect()
    {
        setCollect(FixedLocateHistory.AREA_NO) ;
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
        setCollect(FixedLocateHistory.AREA_NO, sqlfunc, null) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 棚No.(<code>LOCATION_NO</code>)<br>
     * 文字列の検索値をセット棚No.(<code>LOCATION_NO</code>)します。
     */
    public void setLocationNo(String value)
    {
        setKey(FixedLocateHistory.LOCATION_NO, value) ;
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
        setKey(FixedLocateHistory.LOCATION_NO, values, true) ;
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
        setKey(FixedLocateHistory.LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationNo(String value, String compcode)
    {
        setKey(FixedLocateHistory.LOCATION_NO, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationNoOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.LOCATION_NO, ascorder) ;
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
        setOrder(FixedLocateHistory.LOCATION_NO, ascorder) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setLocationNoGroup()
    {
        setGroup(FixedLocateHistory.LOCATION_NO) ;
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
        setGroup(FixedLocateHistory.LOCATION_NO) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setLocationNoCollect()
    {
        setCollect(FixedLocateHistory.LOCATION_NO) ;
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
        setCollect(FixedLocateHistory.LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 最大保管量(<code>REPLENISHMENT_QTY</code>)<br>
     * 数値の検索値をセット最大保管量(<code>REPLENISHMENT_QTY</code>)します。
     */
    public void setReplenishmentQty(int value)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentQty(int[] values)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentQty(int[] values, String and_or_toNext)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReplenishmentQty(int[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReplenishmentQty(int value, String compcode)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
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
    public void setReplenishmentQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReplenishmentQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReplenishmentQtyOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.REPLENISHMENT_QTY, ascorder) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentQtyOrder(int prio, boolean ascorder)
    {
        setOrder(FixedLocateHistory.REPLENISHMENT_QTY, ascorder) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)のグループ順をセットします。
     */
    public void setReplenishmentQtyGroup()
    {
        setGroup(FixedLocateHistory.REPLENISHMENT_QTY) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentQtyGroup(int prio)
    {
        setGroup(FixedLocateHistory.REPLENISHMENT_QTY) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の情報取得を設定します。
     */
    public void setReplenishmentQtyCollect()
    {
        setCollect(FixedLocateHistory.REPLENISHMENT_QTY) ;
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReplenishmentQtyCollect(String sqlfunc)
    {
        setCollect(FixedLocateHistory.REPLENISHMENT_QTY, sqlfunc, null) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット修正後棚No.(<code>UPDATE_LOCATION_NO</code>)します。
     */
    public void setUpdateLocationNo(String value)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, value) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLocationNo(String[] values)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, values, true) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLocationNo(String[] values, String and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateLocationNo(String value, String compcode)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
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
    public void setUpdateLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateLocationNoOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.UPDATE_LOCATION_NO, ascorder) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(FixedLocateHistory.UPDATE_LOCATION_NO, ascorder) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setUpdateLocationNoGroup()
    {
        setGroup(FixedLocateHistory.UPDATE_LOCATION_NO) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLocationNoGroup(int prio)
    {
        setGroup(FixedLocateHistory.UPDATE_LOCATION_NO) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setUpdateLocationNoCollect()
    {
        setCollect(FixedLocateHistory.UPDATE_LOCATION_NO) ;
    }

    /**
     * 修正後棚No.(<code>UPDATE_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateLocationNoCollect(String sqlfunc)
    {
        setCollect(FixedLocateHistory.UPDATE_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)<br>
     * 数値の検索値をセット修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)します。
     */
    public void setUpdateReplenishmentQty(int value)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReplenishmentQty(int[] values)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReplenishmentQty(int[] values, String and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateReplenishmentQty(int[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateReplenishmentQty(int value, String compcode)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
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
    public void setUpdateReplenishmentQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateReplenishmentQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateReplenishmentQtyOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, ascorder) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReplenishmentQtyOrder(int prio, boolean ascorder)
    {
        setOrder(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, ascorder) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)のグループ順をセットします。
     */
    public void setUpdateReplenishmentQtyGroup()
    {
        setGroup(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateReplenishmentQtyGroup(int prio)
    {
        setGroup(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の情報取得を設定します。
     */
    public void setUpdateReplenishmentQtyCollect()
    {
        setCollect(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY) ;
    }

    /**
     * 修正後最大保管量(<code>UPDATE_REPLENISHMENT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateReplenishmentQtyCollect(String sqlfunc)
    {
        setCollect(FixedLocateHistory.UPDATE_REPLENISHMENT_QTY, sqlfunc, null) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 棚表示形式(<code>LOCATION_STYLE</code>)<br>
     * 文字列の検索値をセット棚表示形式(<code>LOCATION_STYLE</code>)します。
     */
    public void setLocationStyle(String value)
    {
        setKey(FixedLocateHistory.LOCATION_STYLE, value) ;
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
        setKey(FixedLocateHistory.LOCATION_STYLE, values, true) ;
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
        setKey(FixedLocateHistory.LOCATION_STYLE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationStyle(String[] values, boolean and_or_toNext)
    {
        setKey(FixedLocateHistory.LOCATION_STYLE, values, and_or_toNext) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationStyle(String value, String compcode)
    {
        setKey(FixedLocateHistory.LOCATION_STYLE, value, compcode, "", "", true) ;
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
        setKey(FixedLocateHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(FixedLocateHistory.LOCATION_STYLE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationStyleOrder(boolean ascorder)
    {
        setOrder(FixedLocateHistory.LOCATION_STYLE, ascorder) ;
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
        setOrder(FixedLocateHistory.LOCATION_STYLE, ascorder) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)のグループ順をセットします。
     */
    public void setLocationStyleGroup()
    {
        setGroup(FixedLocateHistory.LOCATION_STYLE) ;
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
        setGroup(FixedLocateHistory.LOCATION_STYLE) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>)の情報取得を設定します。
     */
    public void setLocationStyleCollect()
    {
        setCollect(FixedLocateHistory.LOCATION_STYLE) ;
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
        setCollect(FixedLocateHistory.LOCATION_STYLE, sqlfunc, null) ;
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
        return "$Id: FixedLocateHistorySearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
