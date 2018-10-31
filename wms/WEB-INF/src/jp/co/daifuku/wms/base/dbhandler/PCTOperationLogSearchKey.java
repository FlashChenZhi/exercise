// $Id: PCTOperationLogSearchKey.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTOPERATIONLOG用の検索キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */


public class PCTOperationLogSearchKey
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
    public PCTOperationLogSearchKey()
    {
        super(PCTOperationLog.STORE_NAME) ;
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
        setKey(PCTOperationLog.LOG_DATE, value) ;
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
        setKey(PCTOperationLog.LOG_DATE, values, true) ;
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
        setKey(PCTOperationLog.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(PCTOperationLog.LOG_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTOperationLog.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTOperationLog.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.LOG_DATE, ascorder) ;
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
        setOrder(PCTOperationLog.LOG_DATE, ascorder) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のグループ順をセットします。
     */
    public void setLogDateGroup()
    {
        setGroup(PCTOperationLog.LOG_DATE) ;
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
        setGroup(PCTOperationLog.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の情報取得を設定します。
     */
    public void setLogDateCollect()
    {
        setCollect(PCTOperationLog.LOG_DATE) ;
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
        setCollect(PCTOperationLog.LOG_DATE, sqlfunc, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値をセット出力日時(GMT)(<code>LOG_DATE_GMT</code>)します。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value) ;
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
        setKey(PCTOperationLog.LOG_DATE_GMT, values, true) ;
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
        setKey(PCTOperationLog.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, "", "", true) ;
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
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTOperationLog.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateGmtOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.LOG_DATE_GMT, ascorder) ;
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
        setOrder(PCTOperationLog.LOG_DATE_GMT, ascorder) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のグループ順をセットします。
     */
    public void setLogDateGmtGroup()
    {
        setGroup(PCTOperationLog.LOG_DATE_GMT) ;
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
        setGroup(PCTOperationLog.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の情報取得を設定します。
     */
    public void setLogDateGmtCollect()
    {
        setCollect(PCTOperationLog.LOG_DATE_GMT) ;
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
        setCollect(PCTOperationLog.LOG_DATE_GMT, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
     */
    public void setUserId(String value)
    {
        setKey(PCTOperationLog.USER_ID, value) ;
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
        setKey(PCTOperationLog.USER_ID, values, true) ;
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
        setKey(PCTOperationLog.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(PCTOperationLog.USER_ID, value, compcode, "", "", true) ;
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
        setKey(PCTOperationLog.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTOperationLog.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.USER_ID, ascorder) ;
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
        setOrder(PCTOperationLog.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(PCTOperationLog.USER_ID) ;
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
        setGroup(PCTOperationLog.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(PCTOperationLog.USER_ID) ;
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
        setCollect(PCTOperationLog.USER_ID, sqlfunc, null) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名(<code>USER_NAME</code>)<br>
     * 文字列の検索値をセットユーザ名(<code>USER_NAME</code>)します。
     */
    public void setUserName(String value)
    {
        setKey(PCTOperationLog.USER_NAME, value) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserName(String[] values)
    {
        setKey(PCTOperationLog.USER_NAME, values, true) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
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
        setKey(PCTOperationLog.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(PCTOperationLog.USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
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
        setKey(PCTOperationLog.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserNameOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)のソート順をセットします。
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
        setOrder(PCTOperationLog.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)のグループ順をセットします。
     */
    public void setUserNameGroup()
    {
        setGroup(PCTOperationLog.USER_NAME) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserNameGroup(int prio)
    {
        setGroup(PCTOperationLog.USER_NAME) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の情報取得を設定します。
     */
    public void setUserNameCollect()
    {
        setCollect(PCTOperationLog.USER_NAME) ;
    }

    /**
     * ユーザ名(<code>USER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUserNameCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.USER_NAME, sqlfunc, null) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NUMBER</code>)<br>
     * 文字列の検索値をセット端末No.(<code>TERMINAL_NUMBER</code>)します。
     */
    public void setTerminalNumber(String value)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumber(String[] values)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumber(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNumber(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNumber(String value, String compcode)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
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
    public void setTerminalNumber(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNumber(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NUMBER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNumberOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.TERMINAL_NUMBER, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumberOrder(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.TERMINAL_NUMBER, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)のグループ順をセットします。
     */
    public void setTerminalNumberGroup()
    {
        setGroup(PCTOperationLog.TERMINAL_NUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNumberGroup(int prio)
    {
        setGroup(PCTOperationLog.TERMINAL_NUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の情報取得を設定します。
     */
    public void setTerminalNumberCollect()
    {
        setCollect(PCTOperationLog.TERMINAL_NUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNumberCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.TERMINAL_NUMBER, sqlfunc, null) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値をセット端末名(<code>TERMINAL_NAME</code>)します。
     */
    public void setTerminalName(String value)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalName(String[] values)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, values, true) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
        setKey(PCTOperationLog.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
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
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNameOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)のソート順をセットします。
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
        setOrder(PCTOperationLog.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     */
    public void setTerminalNameGroup()
    {
        setGroup(PCTOperationLog.TERMINAL_NAME) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNameGroup(int prio)
    {
        setGroup(PCTOperationLog.TERMINAL_NAME) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     */
    public void setTerminalNameCollect()
    {
        setCollect(PCTOperationLog.TERMINAL_NAME) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNameCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.TERMINAL_NAME, sqlfunc, null) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IPADDRESS</code>)<br>
     * 文字列の検索値をセットIPアドレス(<code>IPADDRESS</code>)します。
     */
    public void setIpaddress(String value)
    {
        setKey(PCTOperationLog.IPADDRESS, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values)
    {
        setKey(PCTOperationLog.IPADDRESS, values, true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpaddress(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIpaddress(String value, String compcode)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
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
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.IPADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIpaddressOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.IPADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddressOrder(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.IPADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のグループ順をセットします。
     */
    public void setIpaddressGroup()
    {
        setGroup(PCTOperationLog.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddressGroup(int prio)
    {
        setGroup(PCTOperationLog.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の情報取得を設定します。
     */
    public void setIpaddressCollect()
    {
        setCollect(PCTOperationLog.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setIpaddressCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.IPADDRESS, sqlfunc, null) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をセットDS番号(<code>DS_NO</code>)します。
     */
    public void setDsNo(String value)
    {
        setKey(PCTOperationLog.DS_NO, value) ;
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
        setKey(PCTOperationLog.DS_NO, values, true) ;
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
        setKey(PCTOperationLog.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(PCTOperationLog.DS_NO, value, compcode, "", "", true) ;
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
        setKey(PCTOperationLog.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTOperationLog.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDsNoOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.DS_NO, ascorder) ;
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
        setOrder(PCTOperationLog.DS_NO, ascorder) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のグループ順をセットします。
     */
    public void setDsNoGroup()
    {
        setGroup(PCTOperationLog.DS_NO) ;
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
        setGroup(PCTOperationLog.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の情報取得を設定します。
     */
    public void setDsNoCollect()
    {
        setCollect(PCTOperationLog.DS_NO) ;
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
        setCollect(PCTOperationLog.DS_NO, sqlfunc, null) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をセットページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)します。
     */
    public void setPagenameresourcekey(String value)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekey(String[] values)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, true) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekey(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameresourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPagenameresourcekey(String value, String compcode)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
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
    public void setPagenameresourcekey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameresourcekey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPagenameresourcekeyOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekeyOrder(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setPagenameresourcekeyGroup()
    {
        setGroup(PCTOperationLog.PAGENAMERESOURCEKEY) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPagenameresourcekeyGroup(int prio)
    {
        setGroup(PCTOperationLog.PAGENAMERESOURCEKEY) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setPagenameresourcekeyCollect()
    {
        setCollect(PCTOperationLog.PAGENAMERESOURCEKEY) ;
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPagenameresourcekeyCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.PAGENAMERESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)<br>
     * 数値の検索値をセット操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)します。
     */
    public void setOperationType(int value)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationType(int[] values)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationType(int[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOperationType(int[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setOperationType(int value, String compcode)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
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
    public void setOperationType(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOperationType(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.OPERATION_TYPE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setOperationTypeOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.OPERATION_TYPE, ascorder) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOperationTypeOrder(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.OPERATION_TYPE, ascorder) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)のグループ順をセットします。
     */
    public void setOperationTypeGroup()
    {
        setGroup(PCTOperationLog.OPERATION_TYPE) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOperationTypeGroup(int prio)
    {
        setGroup(PCTOperationLog.OPERATION_TYPE) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の情報取得を設定します。
     */
    public void setOperationTypeCollect()
    {
        setCollect(PCTOperationLog.OPERATION_TYPE) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setOperationTypeCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.OPERATION_TYPE, sqlfunc, null) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 詳細(<code>DETAIL</code>)<br>
     * 文字列の検索値をセット詳細(<code>DETAIL</code>)します。
     */
    public void setDetail(String value)
    {
        setKey(PCTOperationLog.DETAIL, value) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDetail(String[] values)
    {
        setKey(PCTOperationLog.DETAIL, values, true) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDetail(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDetail(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, values, and_or_toNext) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDetail(String value, String compcode)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, "", "", true) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
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
    public void setDetail(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDetail(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.DETAIL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDetailOrder(boolean ascorder)
    {
        setOrder(PCTOperationLog.DETAIL, ascorder) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDetailOrder(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.DETAIL, ascorder) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)のグループ順をセットします。
     */
    public void setDetailGroup()
    {
        setGroup(PCTOperationLog.DETAIL) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDetailGroup(int prio)
    {
        setGroup(PCTOperationLog.DETAIL) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の情報取得を設定します。
     */
    public void setDetailCollect()
    {
        setCollect(PCTOperationLog.DETAIL) ;
    }

    /**
     * 詳細(<code>DETAIL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDetailCollect(String sqlfunc)
    {
        setCollect(PCTOperationLog.DETAIL, sqlfunc, null) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 項目1(<code>ITEM_1</code>)<br>
     * 文字列の検索値をセット項目1(<code>ITEM_1</code>)します。
     */
    public void setItem1(String value)
    {
        setKey(PCTOperationLog.ITEM_1, value) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem1(String[] values)
    {
        setKey(PCTOperationLog.ITEM_1, values, true) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem1(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem1(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, values, and_or_toNext) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem1(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, "", "", true) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
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
    public void setItem1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem1Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_1, ascorder) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem1Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_1, ascorder) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)のグループ順をセットします。
     */
    public void setItem1Group()
    {
        setGroup(PCTOperationLog.ITEM_1) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem1Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_1) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の情報取得を設定します。
     */
    public void setItem1Collect()
    {
        setCollect(PCTOperationLog.ITEM_1) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem1Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_1, sqlfunc, null) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 項目2(<code>ITEM_2</code>)<br>
     * 文字列の検索値をセット項目2(<code>ITEM_2</code>)します。
     */
    public void setItem2(String value)
    {
        setKey(PCTOperationLog.ITEM_2, value) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem2(String[] values)
    {
        setKey(PCTOperationLog.ITEM_2, values, true) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem2(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem2(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, values, and_or_toNext) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem2(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, "", "", true) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
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
    public void setItem2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem2Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_2, ascorder) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem2Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_2, ascorder) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)のグループ順をセットします。
     */
    public void setItem2Group()
    {
        setGroup(PCTOperationLog.ITEM_2) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem2Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_2) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の情報取得を設定します。
     */
    public void setItem2Collect()
    {
        setCollect(PCTOperationLog.ITEM_2) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem2Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_2, sqlfunc, null) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 項目3(<code>ITEM_3</code>)<br>
     * 文字列の検索値をセット項目3(<code>ITEM_3</code>)します。
     */
    public void setItem3(String value)
    {
        setKey(PCTOperationLog.ITEM_3, value) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem3(String[] values)
    {
        setKey(PCTOperationLog.ITEM_3, values, true) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem3(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem3(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, values, and_or_toNext) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem3(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, "", "", true) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
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
    public void setItem3(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem3(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_3, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem3Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_3, ascorder) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem3Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_3, ascorder) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)のグループ順をセットします。
     */
    public void setItem3Group()
    {
        setGroup(PCTOperationLog.ITEM_3) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem3Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_3) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の情報取得を設定します。
     */
    public void setItem3Collect()
    {
        setCollect(PCTOperationLog.ITEM_3) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem3Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_3, sqlfunc, null) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 項目4(<code>ITEM_4</code>)<br>
     * 文字列の検索値をセット項目4(<code>ITEM_4</code>)します。
     */
    public void setItem4(String value)
    {
        setKey(PCTOperationLog.ITEM_4, value) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem4(String[] values)
    {
        setKey(PCTOperationLog.ITEM_4, values, true) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem4(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem4(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, values, and_or_toNext) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem4(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, "", "", true) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
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
    public void setItem4(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem4(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_4, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem4Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_4, ascorder) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem4Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_4, ascorder) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)のグループ順をセットします。
     */
    public void setItem4Group()
    {
        setGroup(PCTOperationLog.ITEM_4) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem4Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_4) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の情報取得を設定します。
     */
    public void setItem4Collect()
    {
        setCollect(PCTOperationLog.ITEM_4) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem4Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_4, sqlfunc, null) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 項目5(<code>ITEM_5</code>)<br>
     * 文字列の検索値をセット項目5(<code>ITEM_5</code>)します。
     */
    public void setItem5(String value)
    {
        setKey(PCTOperationLog.ITEM_5, value) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem5(String[] values)
    {
        setKey(PCTOperationLog.ITEM_5, values, true) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem5(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem5(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, values, and_or_toNext) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem5(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, "", "", true) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
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
    public void setItem5(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem5(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_5, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem5Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_5, ascorder) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem5Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_5, ascorder) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)のグループ順をセットします。
     */
    public void setItem5Group()
    {
        setGroup(PCTOperationLog.ITEM_5) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem5Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_5) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の情報取得を設定します。
     */
    public void setItem5Collect()
    {
        setCollect(PCTOperationLog.ITEM_5) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem5Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_5, sqlfunc, null) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 項目6(<code>ITEM_6</code>)<br>
     * 文字列の検索値をセット項目6(<code>ITEM_6</code>)します。
     */
    public void setItem6(String value)
    {
        setKey(PCTOperationLog.ITEM_6, value) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem6(String[] values)
    {
        setKey(PCTOperationLog.ITEM_6, values, true) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem6(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem6(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, values, and_or_toNext) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem6(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, "", "", true) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
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
    public void setItem6(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem6(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_6, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem6Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_6, ascorder) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem6Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_6, ascorder) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)のグループ順をセットします。
     */
    public void setItem6Group()
    {
        setGroup(PCTOperationLog.ITEM_6) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem6Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_6) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の情報取得を設定します。
     */
    public void setItem6Collect()
    {
        setCollect(PCTOperationLog.ITEM_6) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem6Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_6, sqlfunc, null) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 項目7(<code>ITEM_7</code>)<br>
     * 文字列の検索値をセット項目7(<code>ITEM_7</code>)します。
     */
    public void setItem7(String value)
    {
        setKey(PCTOperationLog.ITEM_7, value) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem7(String[] values)
    {
        setKey(PCTOperationLog.ITEM_7, values, true) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem7(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem7(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, values, and_or_toNext) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem7(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, "", "", true) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
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
    public void setItem7(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem7(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_7, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem7Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_7, ascorder) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem7Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_7, ascorder) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)のグループ順をセットします。
     */
    public void setItem7Group()
    {
        setGroup(PCTOperationLog.ITEM_7) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem7Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_7) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の情報取得を設定します。
     */
    public void setItem7Collect()
    {
        setCollect(PCTOperationLog.ITEM_7) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem7Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_7, sqlfunc, null) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 項目8(<code>ITEM_8</code>)<br>
     * 文字列の検索値をセット項目8(<code>ITEM_8</code>)します。
     */
    public void setItem8(String value)
    {
        setKey(PCTOperationLog.ITEM_8, value) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem8(String[] values)
    {
        setKey(PCTOperationLog.ITEM_8, values, true) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem8(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem8(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, values, and_or_toNext) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem8(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, "", "", true) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
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
    public void setItem8(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem8(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_8, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem8Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_8, ascorder) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem8Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_8, ascorder) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)のグループ順をセットします。
     */
    public void setItem8Group()
    {
        setGroup(PCTOperationLog.ITEM_8) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem8Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_8) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の情報取得を設定します。
     */
    public void setItem8Collect()
    {
        setCollect(PCTOperationLog.ITEM_8) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem8Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_8, sqlfunc, null) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 項目9(<code>ITEM_9</code>)<br>
     * 文字列の検索値をセット項目9(<code>ITEM_9</code>)します。
     */
    public void setItem9(String value)
    {
        setKey(PCTOperationLog.ITEM_9, value) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem9(String[] values)
    {
        setKey(PCTOperationLog.ITEM_9, values, true) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem9(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem9(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, values, and_or_toNext) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem9(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, "", "", true) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
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
    public void setItem9(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem9(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_9, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem9Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_9, ascorder) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem9Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_9, ascorder) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)のグループ順をセットします。
     */
    public void setItem9Group()
    {
        setGroup(PCTOperationLog.ITEM_9) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem9Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_9) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の情報取得を設定します。
     */
    public void setItem9Collect()
    {
        setCollect(PCTOperationLog.ITEM_9) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem9Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_9, sqlfunc, null) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 項目10(<code>ITEM_10</code>)<br>
     * 文字列の検索値をセット項目10(<code>ITEM_10</code>)します。
     */
    public void setItem10(String value)
    {
        setKey(PCTOperationLog.ITEM_10, value) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem10(String[] values)
    {
        setKey(PCTOperationLog.ITEM_10, values, true) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem10(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem10(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, values, and_or_toNext) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem10(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, "", "", true) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
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
    public void setItem10(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem10(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_10, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem10Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_10, ascorder) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem10Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_10, ascorder) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)のグループ順をセットします。
     */
    public void setItem10Group()
    {
        setGroup(PCTOperationLog.ITEM_10) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem10Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_10) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の情報取得を設定します。
     */
    public void setItem10Collect()
    {
        setCollect(PCTOperationLog.ITEM_10) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem10Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_10, sqlfunc, null) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 項目11(<code>ITEM_11</code>)<br>
     * 文字列の検索値をセット項目11(<code>ITEM_11</code>)します。
     */
    public void setItem11(String value)
    {
        setKey(PCTOperationLog.ITEM_11, value) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem11(String[] values)
    {
        setKey(PCTOperationLog.ITEM_11, values, true) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem11(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem11(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, values, and_or_toNext) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem11(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, "", "", true) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
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
    public void setItem11(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem11(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_11, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem11Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_11, ascorder) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem11Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_11, ascorder) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)のグループ順をセットします。
     */
    public void setItem11Group()
    {
        setGroup(PCTOperationLog.ITEM_11) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem11Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_11) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の情報取得を設定します。
     */
    public void setItem11Collect()
    {
        setCollect(PCTOperationLog.ITEM_11) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem11Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_11, sqlfunc, null) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 項目12(<code>ITEM_12</code>)<br>
     * 文字列の検索値をセット項目12(<code>ITEM_12</code>)します。
     */
    public void setItem12(String value)
    {
        setKey(PCTOperationLog.ITEM_12, value) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem12(String[] values)
    {
        setKey(PCTOperationLog.ITEM_12, values, true) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem12(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem12(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, values, and_or_toNext) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem12(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, "", "", true) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
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
    public void setItem12(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem12(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_12, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem12Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_12, ascorder) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem12Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_12, ascorder) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)のグループ順をセットします。
     */
    public void setItem12Group()
    {
        setGroup(PCTOperationLog.ITEM_12) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem12Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_12) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の情報取得を設定します。
     */
    public void setItem12Collect()
    {
        setCollect(PCTOperationLog.ITEM_12) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem12Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_12, sqlfunc, null) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 項目13(<code>ITEM_13</code>)<br>
     * 文字列の検索値をセット項目13(<code>ITEM_13</code>)します。
     */
    public void setItem13(String value)
    {
        setKey(PCTOperationLog.ITEM_13, value) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem13(String[] values)
    {
        setKey(PCTOperationLog.ITEM_13, values, true) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem13(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem13(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, values, and_or_toNext) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem13(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, "", "", true) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
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
    public void setItem13(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem13(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_13, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem13Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_13, ascorder) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem13Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_13, ascorder) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)のグループ順をセットします。
     */
    public void setItem13Group()
    {
        setGroup(PCTOperationLog.ITEM_13) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem13Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_13) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の情報取得を設定します。
     */
    public void setItem13Collect()
    {
        setCollect(PCTOperationLog.ITEM_13) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem13Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_13, sqlfunc, null) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 項目14(<code>ITEM_14</code>)<br>
     * 文字列の検索値をセット項目14(<code>ITEM_14</code>)します。
     */
    public void setItem14(String value)
    {
        setKey(PCTOperationLog.ITEM_14, value) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem14(String[] values)
    {
        setKey(PCTOperationLog.ITEM_14, values, true) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem14(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem14(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, values, and_or_toNext) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem14(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, "", "", true) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
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
    public void setItem14(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem14(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_14, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem14Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_14, ascorder) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem14Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_14, ascorder) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)のグループ順をセットします。
     */
    public void setItem14Group()
    {
        setGroup(PCTOperationLog.ITEM_14) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem14Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_14) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の情報取得を設定します。
     */
    public void setItem14Collect()
    {
        setCollect(PCTOperationLog.ITEM_14) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem14Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_14, sqlfunc, null) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 項目15(<code>ITEM_15</code>)<br>
     * 文字列の検索値をセット項目15(<code>ITEM_15</code>)します。
     */
    public void setItem15(String value)
    {
        setKey(PCTOperationLog.ITEM_15, value) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem15(String[] values)
    {
        setKey(PCTOperationLog.ITEM_15, values, true) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem15(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem15(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, values, and_or_toNext) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem15(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, "", "", true) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
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
    public void setItem15(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem15(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_15, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem15Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_15, ascorder) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem15Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_15, ascorder) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)のグループ順をセットします。
     */
    public void setItem15Group()
    {
        setGroup(PCTOperationLog.ITEM_15) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem15Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_15) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の情報取得を設定します。
     */
    public void setItem15Collect()
    {
        setCollect(PCTOperationLog.ITEM_15) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem15Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_15, sqlfunc, null) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 項目16(<code>ITEM_16</code>)<br>
     * 文字列の検索値をセット項目16(<code>ITEM_16</code>)します。
     */
    public void setItem16(String value)
    {
        setKey(PCTOperationLog.ITEM_16, value) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem16(String[] values)
    {
        setKey(PCTOperationLog.ITEM_16, values, true) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem16(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem16(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, values, and_or_toNext) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem16(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, "", "", true) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
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
    public void setItem16(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem16(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_16, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem16Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_16, ascorder) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem16Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_16, ascorder) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)のグループ順をセットします。
     */
    public void setItem16Group()
    {
        setGroup(PCTOperationLog.ITEM_16) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem16Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_16) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の情報取得を設定します。
     */
    public void setItem16Collect()
    {
        setCollect(PCTOperationLog.ITEM_16) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem16Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_16, sqlfunc, null) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 項目17(<code>ITEM_17</code>)<br>
     * 文字列の検索値をセット項目17(<code>ITEM_17</code>)します。
     */
    public void setItem17(String value)
    {
        setKey(PCTOperationLog.ITEM_17, value) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem17(String[] values)
    {
        setKey(PCTOperationLog.ITEM_17, values, true) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem17(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem17(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, values, and_or_toNext) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem17(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, "", "", true) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
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
    public void setItem17(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem17(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_17, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem17Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_17, ascorder) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem17Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_17, ascorder) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)のグループ順をセットします。
     */
    public void setItem17Group()
    {
        setGroup(PCTOperationLog.ITEM_17) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem17Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_17) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の情報取得を設定します。
     */
    public void setItem17Collect()
    {
        setCollect(PCTOperationLog.ITEM_17) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem17Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_17, sqlfunc, null) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 項目18(<code>ITEM_18</code>)<br>
     * 文字列の検索値をセット項目18(<code>ITEM_18</code>)します。
     */
    public void setItem18(String value)
    {
        setKey(PCTOperationLog.ITEM_18, value) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem18(String[] values)
    {
        setKey(PCTOperationLog.ITEM_18, values, true) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem18(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem18(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, values, and_or_toNext) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem18(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, "", "", true) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
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
    public void setItem18(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem18(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_18, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem18Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_18, ascorder) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem18Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_18, ascorder) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)のグループ順をセットします。
     */
    public void setItem18Group()
    {
        setGroup(PCTOperationLog.ITEM_18) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem18Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_18) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の情報取得を設定します。
     */
    public void setItem18Collect()
    {
        setCollect(PCTOperationLog.ITEM_18) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem18Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_18, sqlfunc, null) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 項目19(<code>ITEM_19</code>)<br>
     * 文字列の検索値をセット項目19(<code>ITEM_19</code>)します。
     */
    public void setItem19(String value)
    {
        setKey(PCTOperationLog.ITEM_19, value) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem19(String[] values)
    {
        setKey(PCTOperationLog.ITEM_19, values, true) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem19(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem19(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, values, and_or_toNext) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem19(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, "", "", true) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
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
    public void setItem19(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem19(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_19, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem19Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_19, ascorder) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem19Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_19, ascorder) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)のグループ順をセットします。
     */
    public void setItem19Group()
    {
        setGroup(PCTOperationLog.ITEM_19) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem19Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_19) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の情報取得を設定します。
     */
    public void setItem19Collect()
    {
        setCollect(PCTOperationLog.ITEM_19) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem19Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_19, sqlfunc, null) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 項目20(<code>ITEM_20</code>)<br>
     * 文字列の検索値をセット項目20(<code>ITEM_20</code>)します。
     */
    public void setItem20(String value)
    {
        setKey(PCTOperationLog.ITEM_20, value) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem20(String[] values)
    {
        setKey(PCTOperationLog.ITEM_20, values, true) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem20(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem20(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, values, and_or_toNext) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem20(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, "", "", true) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
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
    public void setItem20(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem20(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_20, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem20Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_20, ascorder) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem20Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_20, ascorder) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)のグループ順をセットします。
     */
    public void setItem20Group()
    {
        setGroup(PCTOperationLog.ITEM_20) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem20Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_20) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の情報取得を設定します。
     */
    public void setItem20Collect()
    {
        setCollect(PCTOperationLog.ITEM_20) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem20Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_20, sqlfunc, null) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 項目21(<code>ITEM_21</code>)<br>
     * 文字列の検索値をセット項目21(<code>ITEM_21</code>)します。
     */
    public void setItem21(String value)
    {
        setKey(PCTOperationLog.ITEM_21, value) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem21(String[] values)
    {
        setKey(PCTOperationLog.ITEM_21, values, true) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem21(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem21(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, values, and_or_toNext) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem21(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, "", "", true) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
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
    public void setItem21(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem21(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_21, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem21Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_21, ascorder) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem21Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_21, ascorder) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)のグループ順をセットします。
     */
    public void setItem21Group()
    {
        setGroup(PCTOperationLog.ITEM_21) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem21Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_21) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の情報取得を設定します。
     */
    public void setItem21Collect()
    {
        setCollect(PCTOperationLog.ITEM_21) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem21Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_21, sqlfunc, null) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 項目22(<code>ITEM_22</code>)<br>
     * 文字列の検索値をセット項目22(<code>ITEM_22</code>)します。
     */
    public void setItem22(String value)
    {
        setKey(PCTOperationLog.ITEM_22, value) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem22(String[] values)
    {
        setKey(PCTOperationLog.ITEM_22, values, true) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem22(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem22(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, values, and_or_toNext) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem22(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, "", "", true) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
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
    public void setItem22(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem22(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_22, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem22Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_22, ascorder) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem22Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_22, ascorder) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)のグループ順をセットします。
     */
    public void setItem22Group()
    {
        setGroup(PCTOperationLog.ITEM_22) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem22Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_22) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の情報取得を設定します。
     */
    public void setItem22Collect()
    {
        setCollect(PCTOperationLog.ITEM_22) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem22Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_22, sqlfunc, null) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 項目23(<code>ITEM_23</code>)<br>
     * 文字列の検索値をセット項目23(<code>ITEM_23</code>)します。
     */
    public void setItem23(String value)
    {
        setKey(PCTOperationLog.ITEM_23, value) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem23(String[] values)
    {
        setKey(PCTOperationLog.ITEM_23, values, true) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem23(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem23(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, values, and_or_toNext) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem23(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, "", "", true) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
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
    public void setItem23(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem23(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_23, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem23Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_23, ascorder) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem23Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_23, ascorder) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)のグループ順をセットします。
     */
    public void setItem23Group()
    {
        setGroup(PCTOperationLog.ITEM_23) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem23Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_23) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の情報取得を設定します。
     */
    public void setItem23Collect()
    {
        setCollect(PCTOperationLog.ITEM_23) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem23Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_23, sqlfunc, null) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 項目24(<code>ITEM_24</code>)<br>
     * 文字列の検索値をセット項目24(<code>ITEM_24</code>)します。
     */
    public void setItem24(String value)
    {
        setKey(PCTOperationLog.ITEM_24, value) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem24(String[] values)
    {
        setKey(PCTOperationLog.ITEM_24, values, true) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem24(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem24(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, values, and_or_toNext) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem24(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, "", "", true) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
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
    public void setItem24(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem24(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_24, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem24Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_24, ascorder) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem24Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_24, ascorder) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)のグループ順をセットします。
     */
    public void setItem24Group()
    {
        setGroup(PCTOperationLog.ITEM_24) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem24Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_24) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の情報取得を設定します。
     */
    public void setItem24Collect()
    {
        setCollect(PCTOperationLog.ITEM_24) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem24Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_24, sqlfunc, null) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 項目25(<code>ITEM_25</code>)<br>
     * 文字列の検索値をセット項目25(<code>ITEM_25</code>)します。
     */
    public void setItem25(String value)
    {
        setKey(PCTOperationLog.ITEM_25, value) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem25(String[] values)
    {
        setKey(PCTOperationLog.ITEM_25, values, true) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem25(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem25(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, values, and_or_toNext) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem25(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, "", "", true) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
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
    public void setItem25(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem25(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_25, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem25Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_25, ascorder) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem25Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_25, ascorder) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)のグループ順をセットします。
     */
    public void setItem25Group()
    {
        setGroup(PCTOperationLog.ITEM_25) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem25Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_25) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の情報取得を設定します。
     */
    public void setItem25Collect()
    {
        setCollect(PCTOperationLog.ITEM_25) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem25Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_25, sqlfunc, null) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 項目26(<code>ITEM_26</code>)<br>
     * 文字列の検索値をセット項目26(<code>ITEM_26</code>)します。
     */
    public void setItem26(String value)
    {
        setKey(PCTOperationLog.ITEM_26, value) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem26(String[] values)
    {
        setKey(PCTOperationLog.ITEM_26, values, true) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem26(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem26(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, values, and_or_toNext) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem26(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, "", "", true) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
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
    public void setItem26(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem26(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_26, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem26Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_26, ascorder) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem26Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_26, ascorder) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)のグループ順をセットします。
     */
    public void setItem26Group()
    {
        setGroup(PCTOperationLog.ITEM_26) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem26Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_26) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の情報取得を設定します。
     */
    public void setItem26Collect()
    {
        setCollect(PCTOperationLog.ITEM_26) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem26Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_26, sqlfunc, null) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 項目27(<code>ITEM_27</code>)<br>
     * 文字列の検索値をセット項目27(<code>ITEM_27</code>)します。
     */
    public void setItem27(String value)
    {
        setKey(PCTOperationLog.ITEM_27, value) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem27(String[] values)
    {
        setKey(PCTOperationLog.ITEM_27, values, true) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem27(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem27(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, values, and_or_toNext) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem27(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, "", "", true) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
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
    public void setItem27(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem27(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_27, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem27Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_27, ascorder) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem27Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_27, ascorder) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)のグループ順をセットします。
     */
    public void setItem27Group()
    {
        setGroup(PCTOperationLog.ITEM_27) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem27Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_27) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の情報取得を設定します。
     */
    public void setItem27Collect()
    {
        setCollect(PCTOperationLog.ITEM_27) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem27Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_27, sqlfunc, null) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 項目28(<code>ITEM_28</code>)<br>
     * 文字列の検索値をセット項目28(<code>ITEM_28</code>)します。
     */
    public void setItem28(String value)
    {
        setKey(PCTOperationLog.ITEM_28, value) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem28(String[] values)
    {
        setKey(PCTOperationLog.ITEM_28, values, true) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem28(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem28(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, values, and_or_toNext) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem28(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, "", "", true) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
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
    public void setItem28(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem28(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_28, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem28Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_28, ascorder) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem28Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_28, ascorder) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)のグループ順をセットします。
     */
    public void setItem28Group()
    {
        setGroup(PCTOperationLog.ITEM_28) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem28Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_28) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の情報取得を設定します。
     */
    public void setItem28Collect()
    {
        setCollect(PCTOperationLog.ITEM_28) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem28Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_28, sqlfunc, null) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 項目29(<code>ITEM_29</code>)<br>
     * 文字列の検索値をセット項目29(<code>ITEM_29</code>)します。
     */
    public void setItem29(String value)
    {
        setKey(PCTOperationLog.ITEM_29, value) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem29(String[] values)
    {
        setKey(PCTOperationLog.ITEM_29, values, true) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem29(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem29(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, values, and_or_toNext) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem29(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, "", "", true) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
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
    public void setItem29(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem29(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_29, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem29Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_29, ascorder) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem29Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_29, ascorder) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)のグループ順をセットします。
     */
    public void setItem29Group()
    {
        setGroup(PCTOperationLog.ITEM_29) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem29Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_29) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の情報取得を設定します。
     */
    public void setItem29Collect()
    {
        setCollect(PCTOperationLog.ITEM_29) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem29Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_29, sqlfunc, null) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 項目30(<code>ITEM_30</code>)<br>
     * 文字列の検索値をセット項目30(<code>ITEM_30</code>)します。
     */
    public void setItem30(String value)
    {
        setKey(PCTOperationLog.ITEM_30, value) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem30(String[] values)
    {
        setKey(PCTOperationLog.ITEM_30, values, true) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem30(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem30(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, values, and_or_toNext) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem30(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, "", "", true) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
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
    public void setItem30(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem30(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_30, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem30Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_30, ascorder) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem30Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_30, ascorder) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)のグループ順をセットします。
     */
    public void setItem30Group()
    {
        setGroup(PCTOperationLog.ITEM_30) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem30Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_30) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の情報取得を設定します。
     */
    public void setItem30Collect()
    {
        setCollect(PCTOperationLog.ITEM_30) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem30Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_30, sqlfunc, null) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 項目31(<code>ITEM_31</code>)<br>
     * 文字列の検索値をセット項目31(<code>ITEM_31</code>)します。
     */
    public void setItem31(String value)
    {
        setKey(PCTOperationLog.ITEM_31, value) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem31(String[] values)
    {
        setKey(PCTOperationLog.ITEM_31, values, true) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem31(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem31(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, values, and_or_toNext) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem31(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, "", "", true) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
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
    public void setItem31(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem31(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_31, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem31Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_31, ascorder) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem31Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_31, ascorder) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)のグループ順をセットします。
     */
    public void setItem31Group()
    {
        setGroup(PCTOperationLog.ITEM_31) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem31Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_31) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の情報取得を設定します。
     */
    public void setItem31Collect()
    {
        setCollect(PCTOperationLog.ITEM_31) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem31Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_31, sqlfunc, null) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 項目32(<code>ITEM_32</code>)<br>
     * 文字列の検索値をセット項目32(<code>ITEM_32</code>)します。
     */
    public void setItem32(String value)
    {
        setKey(PCTOperationLog.ITEM_32, value) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem32(String[] values)
    {
        setKey(PCTOperationLog.ITEM_32, values, true) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem32(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem32(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, values, and_or_toNext) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem32(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, "", "", true) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
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
    public void setItem32(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem32(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_32, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem32Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_32, ascorder) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem32Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_32, ascorder) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)のグループ順をセットします。
     */
    public void setItem32Group()
    {
        setGroup(PCTOperationLog.ITEM_32) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem32Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_32) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の情報取得を設定します。
     */
    public void setItem32Collect()
    {
        setCollect(PCTOperationLog.ITEM_32) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem32Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_32, sqlfunc, null) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 項目33(<code>ITEM_33</code>)<br>
     * 文字列の検索値をセット項目33(<code>ITEM_33</code>)します。
     */
    public void setItem33(String value)
    {
        setKey(PCTOperationLog.ITEM_33, value) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem33(String[] values)
    {
        setKey(PCTOperationLog.ITEM_33, values, true) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem33(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem33(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, values, and_or_toNext) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem33(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, "", "", true) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
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
    public void setItem33(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem33(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_33, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem33Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_33, ascorder) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem33Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_33, ascorder) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)のグループ順をセットします。
     */
    public void setItem33Group()
    {
        setGroup(PCTOperationLog.ITEM_33) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem33Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_33) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の情報取得を設定します。
     */
    public void setItem33Collect()
    {
        setCollect(PCTOperationLog.ITEM_33) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem33Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_33, sqlfunc, null) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 項目34(<code>ITEM_34</code>)<br>
     * 文字列の検索値をセット項目34(<code>ITEM_34</code>)します。
     */
    public void setItem34(String value)
    {
        setKey(PCTOperationLog.ITEM_34, value) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem34(String[] values)
    {
        setKey(PCTOperationLog.ITEM_34, values, true) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem34(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem34(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, values, and_or_toNext) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem34(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, "", "", true) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
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
    public void setItem34(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem34(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_34, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem34Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_34, ascorder) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem34Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_34, ascorder) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)のグループ順をセットします。
     */
    public void setItem34Group()
    {
        setGroup(PCTOperationLog.ITEM_34) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem34Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_34) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の情報取得を設定します。
     */
    public void setItem34Collect()
    {
        setCollect(PCTOperationLog.ITEM_34) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem34Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_34, sqlfunc, null) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 項目35(<code>ITEM_35</code>)<br>
     * 文字列の検索値をセット項目35(<code>ITEM_35</code>)します。
     */
    public void setItem35(String value)
    {
        setKey(PCTOperationLog.ITEM_35, value) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem35(String[] values)
    {
        setKey(PCTOperationLog.ITEM_35, values, true) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem35(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem35(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, values, and_or_toNext) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem35(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, "", "", true) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
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
    public void setItem35(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem35(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_35, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem35Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_35, ascorder) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem35Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_35, ascorder) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)のグループ順をセットします。
     */
    public void setItem35Group()
    {
        setGroup(PCTOperationLog.ITEM_35) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem35Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_35) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の情報取得を設定します。
     */
    public void setItem35Collect()
    {
        setCollect(PCTOperationLog.ITEM_35) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem35Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_35, sqlfunc, null) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 項目36(<code>ITEM_36</code>)<br>
     * 文字列の検索値をセット項目36(<code>ITEM_36</code>)します。
     */
    public void setItem36(String value)
    {
        setKey(PCTOperationLog.ITEM_36, value) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem36(String[] values)
    {
        setKey(PCTOperationLog.ITEM_36, values, true) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem36(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem36(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, values, and_or_toNext) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem36(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, "", "", true) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
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
    public void setItem36(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem36(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_36, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem36Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_36, ascorder) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem36Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_36, ascorder) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)のグループ順をセットします。
     */
    public void setItem36Group()
    {
        setGroup(PCTOperationLog.ITEM_36) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem36Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_36) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の情報取得を設定します。
     */
    public void setItem36Collect()
    {
        setCollect(PCTOperationLog.ITEM_36) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem36Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_36, sqlfunc, null) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 項目37(<code>ITEM_37</code>)<br>
     * 文字列の検索値をセット項目37(<code>ITEM_37</code>)します。
     */
    public void setItem37(String value)
    {
        setKey(PCTOperationLog.ITEM_37, value) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem37(String[] values)
    {
        setKey(PCTOperationLog.ITEM_37, values, true) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem37(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem37(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, values, and_or_toNext) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem37(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, "", "", true) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
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
    public void setItem37(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem37(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_37, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem37Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_37, ascorder) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem37Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_37, ascorder) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)のグループ順をセットします。
     */
    public void setItem37Group()
    {
        setGroup(PCTOperationLog.ITEM_37) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem37Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_37) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の情報取得を設定します。
     */
    public void setItem37Collect()
    {
        setCollect(PCTOperationLog.ITEM_37) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem37Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_37, sqlfunc, null) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 項目38(<code>ITEM_38</code>)<br>
     * 文字列の検索値をセット項目38(<code>ITEM_38</code>)します。
     */
    public void setItem38(String value)
    {
        setKey(PCTOperationLog.ITEM_38, value) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem38(String[] values)
    {
        setKey(PCTOperationLog.ITEM_38, values, true) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem38(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem38(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, values, and_or_toNext) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem38(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, "", "", true) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
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
    public void setItem38(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem38(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_38, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem38Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_38, ascorder) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem38Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_38, ascorder) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)のグループ順をセットします。
     */
    public void setItem38Group()
    {
        setGroup(PCTOperationLog.ITEM_38) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem38Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_38) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の情報取得を設定します。
     */
    public void setItem38Collect()
    {
        setCollect(PCTOperationLog.ITEM_38) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem38Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_38, sqlfunc, null) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 項目39(<code>ITEM_39</code>)<br>
     * 文字列の検索値をセット項目39(<code>ITEM_39</code>)します。
     */
    public void setItem39(String value)
    {
        setKey(PCTOperationLog.ITEM_39, value) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem39(String[] values)
    {
        setKey(PCTOperationLog.ITEM_39, values, true) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem39(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem39(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, values, and_or_toNext) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem39(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, "", "", true) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
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
    public void setItem39(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem39(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_39, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem39Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_39, ascorder) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem39Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_39, ascorder) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)のグループ順をセットします。
     */
    public void setItem39Group()
    {
        setGroup(PCTOperationLog.ITEM_39) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem39Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_39) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の情報取得を設定します。
     */
    public void setItem39Collect()
    {
        setCollect(PCTOperationLog.ITEM_39) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem39Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_39, sqlfunc, null) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 項目40(<code>ITEM_40</code>)<br>
     * 文字列の検索値をセット項目40(<code>ITEM_40</code>)します。
     */
    public void setItem40(String value)
    {
        setKey(PCTOperationLog.ITEM_40, value) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem40(String[] values)
    {
        setKey(PCTOperationLog.ITEM_40, values, true) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItem40(String[] values, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem40(String[] values, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, values, and_or_toNext) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItem40(String value, String compcode)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, "", "", true) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
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
    public void setItem40(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItem40(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTOperationLog.ITEM_40, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItem40Order(boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_40, ascorder) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem40Order(int prio, boolean ascorder)
    {
        setOrder(PCTOperationLog.ITEM_40, ascorder) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)のグループ順をセットします。
     */
    public void setItem40Group()
    {
        setGroup(PCTOperationLog.ITEM_40) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItem40Group(int prio)
    {
        setGroup(PCTOperationLog.ITEM_40) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の情報取得を設定します。
     */
    public void setItem40Collect()
    {
        setCollect(PCTOperationLog.ITEM_40) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItem40Collect(String sqlfunc)
    {
        setCollect(PCTOperationLog.ITEM_40, sqlfunc, null) ;
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
        return "$Id: PCTOperationLogSearchKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
