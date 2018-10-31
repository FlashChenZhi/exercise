// $Id: ItemHistorySearchKey.java 7591 2010-03-15 13:51:03Z kishimoto $
// $LastChangedRevision: 7591 $
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
import jp.co.daifuku.wms.base.entity.ItemHistory;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ITEMHISTORY用の検索キークラスです。
 *
 * @version $Revision: 7591 $, $Date: 2010-03-15 22:51:03 +0900 (月, 15 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class ItemHistorySearchKey
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
    public ItemHistorySearchKey()
    {
        super(ItemHistory.STORE_NAME) ;
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
        setKey(ItemHistory.LOG_DATE, value) ;
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
        setKey(ItemHistory.LOG_DATE, values, true) ;
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
        setKey(ItemHistory.LOG_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.LOG_DATE, values, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDate(Date value, String compcode)
    {
        setKey(ItemHistory.LOG_DATE, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.LOG_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.LOG_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateOrder(boolean ascorder)
    {
        setOrder(ItemHistory.LOG_DATE, ascorder) ;
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
        setOrder(ItemHistory.LOG_DATE, ascorder) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)のグループ順をセットします。
     */
    public void setLogDateGroup()
    {
        setGroup(ItemHistory.LOG_DATE) ;
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
        setGroup(ItemHistory.LOG_DATE) ;
    }

    /**
     * 出力日時(<code>LOG_DATE</code>)の情報取得を設定します。
     */
    public void setLogDateCollect()
    {
        setCollect(ItemHistory.LOG_DATE) ;
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
        setCollect(ItemHistory.LOG_DATE, sqlfunc, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 出力日時(GMT)(<code>LOG_DATE_GMT</code>)<br>
     * 日付の検索値をセット出力日時(GMT)(<code>LOG_DATE_GMT</code>)します。
     */
    public void setLogDateGmt(Date value)
    {
        setKey(ItemHistory.LOG_DATE_GMT, value) ;
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
        setKey(ItemHistory.LOG_DATE_GMT, values, true) ;
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
        setKey(ItemHistory.LOG_DATE_GMT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLogDateGmt(Date[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.LOG_DATE_GMT, values, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLogDateGmt(Date value, String compcode)
    {
        setKey(ItemHistory.LOG_DATE_GMT, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.LOG_DATE_GMT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLogDateGmtOrder(boolean ascorder)
    {
        setOrder(ItemHistory.LOG_DATE_GMT, ascorder) ;
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
        setOrder(ItemHistory.LOG_DATE_GMT, ascorder) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)のグループ順をセットします。
     */
    public void setLogDateGmtGroup()
    {
        setGroup(ItemHistory.LOG_DATE_GMT) ;
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
        setGroup(ItemHistory.LOG_DATE_GMT) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>)の情報取得を設定します。
     */
    public void setLogDateGmtCollect()
    {
        setCollect(ItemHistory.LOG_DATE_GMT) ;
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
        setCollect(ItemHistory.LOG_DATE_GMT, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
     */
    public void setUserId(String value)
    {
        setKey(ItemHistory.USER_ID, value) ;
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
        setKey(ItemHistory.USER_ID, values, true) ;
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
        setKey(ItemHistory.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(ItemHistory.USER_ID, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(ItemHistory.USER_ID, ascorder) ;
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
        setOrder(ItemHistory.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(ItemHistory.USER_ID) ;
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
        setGroup(ItemHistory.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(ItemHistory.USER_ID) ;
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
        setCollect(ItemHistory.USER_ID, sqlfunc, null) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をセットユーザ名称(<code>USER_NAME</code>)します。
     */
    public void setUserName(String value)
    {
        setKey(ItemHistory.USER_NAME, value) ;
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
        setKey(ItemHistory.USER_NAME, values, true) ;
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
        setKey(ItemHistory.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(ItemHistory.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserNameOrder(boolean ascorder)
    {
        setOrder(ItemHistory.USER_NAME, ascorder) ;
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
        setOrder(ItemHistory.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のグループ順をセットします。
     */
    public void setUserNameGroup()
    {
        setGroup(ItemHistory.USER_NAME) ;
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
        setGroup(ItemHistory.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の情報取得を設定します。
     */
    public void setUserNameCollect()
    {
        setCollect(ItemHistory.USER_NAME) ;
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
        setCollect(ItemHistory.USER_NAME, sqlfunc, null) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット端末No、RFTNo(<code>TERMINAL_NO</code>)します。
     */
    public void setTerminalNo(String value)
    {
        setKey(ItemHistory.TERMINAL_NO, value) ;
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
        setKey(ItemHistory.TERMINAL_NO, values, true) ;
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
        setKey(ItemHistory.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(ItemHistory.TERMINAL_NO, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(ItemHistory.TERMINAL_NO, ascorder) ;
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
        setOrder(ItemHistory.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(ItemHistory.TERMINAL_NO) ;
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
        setGroup(ItemHistory.TERMINAL_NO) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(ItemHistory.TERMINAL_NO) ;
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
        setCollect(ItemHistory.TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 端末名称(<code>TERMINAL_NAME</code>)<br>
     * 文字列の検索値をセット端末名称(<code>TERMINAL_NAME</code>)します。
     */
    public void setTerminalName(String value)
    {
        setKey(ItemHistory.TERMINAL_NAME, value) ;
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
        setKey(ItemHistory.TERMINAL_NAME, values, true) ;
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
        setKey(ItemHistory.TERMINAL_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalName(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.TERMINAL_NAME, values, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalName(String value, String compcode)
    {
        setKey(ItemHistory.TERMINAL_NAME, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.TERMINAL_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNameOrder(boolean ascorder)
    {
        setOrder(ItemHistory.TERMINAL_NAME, ascorder) ;
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
        setOrder(ItemHistory.TERMINAL_NAME, ascorder) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)のグループ順をセットします。
     */
    public void setTerminalNameGroup()
    {
        setGroup(ItemHistory.TERMINAL_NAME) ;
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
        setGroup(ItemHistory.TERMINAL_NAME) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>)の情報取得を設定します。
     */
    public void setTerminalNameCollect()
    {
        setCollect(ItemHistory.TERMINAL_NAME) ;
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
        setCollect(ItemHistory.TERMINAL_NAME, sqlfunc, null) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IP_ADDRESS</code>)<br>
     * 文字列の検索値をセットIPアドレス(<code>IP_ADDRESS</code>)します。
     */
    public void setIpAddress(String value)
    {
        setKey(ItemHistory.IP_ADDRESS, value) ;
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
        setKey(ItemHistory.IP_ADDRESS, values, true) ;
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
        setKey(ItemHistory.IP_ADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpAddress(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.IP_ADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIpAddress(String value, String compcode)
    {
        setKey(ItemHistory.IP_ADDRESS, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.IP_ADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIpAddressOrder(boolean ascorder)
    {
        setOrder(ItemHistory.IP_ADDRESS, ascorder) ;
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
        setOrder(ItemHistory.IP_ADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)のグループ順をセットします。
     */
    public void setIpAddressGroup()
    {
        setGroup(ItemHistory.IP_ADDRESS) ;
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
        setGroup(ItemHistory.IP_ADDRESS) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>)の情報取得を設定します。
     */
    public void setIpAddressCollect()
    {
        setCollect(ItemHistory.IP_ADDRESS) ;
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
        setCollect(ItemHistory.IP_ADDRESS, sqlfunc, null) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value DS番号(<code>DS_NO</code>)<br>
     * 文字列の検索値をセットDS番号(<code>DS_NO</code>)します。
     */
    public void setDsNo(String value)
    {
        setKey(ItemHistory.DS_NO, value) ;
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
        setKey(ItemHistory.DS_NO, values, true) ;
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
        setKey(ItemHistory.DS_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDsNo(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.DS_NO, values, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDsNo(String value, String compcode)
    {
        setKey(ItemHistory.DS_NO, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.DS_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.DS_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDsNoOrder(boolean ascorder)
    {
        setOrder(ItemHistory.DS_NO, ascorder) ;
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
        setOrder(ItemHistory.DS_NO, ascorder) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)のグループ順をセットします。
     */
    public void setDsNoGroup()
    {
        setGroup(ItemHistory.DS_NO) ;
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
        setGroup(ItemHistory.DS_NO) ;
    }

    /**
     * DS番号(<code>DS_NO</code>)の情報取得を設定します。
     */
    public void setDsNoCollect()
    {
        setCollect(ItemHistory.DS_NO) ;
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
        setCollect(ItemHistory.DS_NO, sqlfunc, null) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)<br>
     * 文字列の検索値をセット画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)します。
     */
    public void setPagenameResourcekey(String value)
    {
        setKey(ItemHistory.PAGENAMERESOURCEKEY, value) ;
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
        setKey(ItemHistory.PAGENAMERESOURCEKEY, values, true) ;
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
        setKey(ItemHistory.PAGENAMERESOURCEKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPagenameResourcekey(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.PAGENAMERESOURCEKEY, values, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPagenameResourcekey(String value, String compcode)
    {
        setKey(ItemHistory.PAGENAMERESOURCEKEY, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.PAGENAMERESOURCEKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPagenameResourcekeyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.PAGENAMERESOURCEKEY, ascorder) ;
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
        setOrder(ItemHistory.PAGENAMERESOURCEKEY, ascorder) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)のグループ順をセットします。
     */
    public void setPagenameResourcekeyGroup()
    {
        setGroup(ItemHistory.PAGENAMERESOURCEKEY) ;
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
        setGroup(ItemHistory.PAGENAMERESOURCEKEY) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)の情報取得を設定します。
     */
    public void setPagenameResourcekeyCollect()
    {
        setCollect(ItemHistory.PAGENAMERESOURCEKEY) ;
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
        setCollect(ItemHistory.PAGENAMERESOURCEKEY, sqlfunc, null) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 文字列の検索値をセット更新区分(<code>UPDATE_KIND</code>)します。
     */
    public void setUpdateKind(String value)
    {
        setKey(ItemHistory.UPDATE_KIND, value) ;
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
        setKey(ItemHistory.UPDATE_KIND, values, true) ;
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
        setKey(ItemHistory.UPDATE_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_KIND, values, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateKind(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_KIND, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.UPDATE_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateKindOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_KIND, ascorder) ;
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
        setOrder(ItemHistory.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     */
    public void setUpdateKindGroup()
    {
        setGroup(ItemHistory.UPDATE_KIND) ;
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
        setGroup(ItemHistory.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     */
    public void setUpdateKindCollect()
    {
        setCollect(ItemHistory.UPDATE_KIND) ;
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
        setCollect(ItemHistory.UPDATE_KIND, sqlfunc, null) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)<br>
     * 文字列の検索値をセットシステム管理区分(<code>MANAGEMENT_TYPE</code>)します。
     */
    public void setManagementType(String value)
    {
        setKey(ItemHistory.MANAGEMENT_TYPE, value) ;
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
        setKey(ItemHistory.MANAGEMENT_TYPE, values, true) ;
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
        setKey(ItemHistory.MANAGEMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setManagementType(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.MANAGEMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setManagementType(String value, String compcode)
    {
        setKey(ItemHistory.MANAGEMENT_TYPE, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setManagementTypeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.MANAGEMENT_TYPE, ascorder) ;
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
        setOrder(ItemHistory.MANAGEMENT_TYPE, ascorder) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)のグループ順をセットします。
     */
    public void setManagementTypeGroup()
    {
        setGroup(ItemHistory.MANAGEMENT_TYPE) ;
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
        setGroup(ItemHistory.MANAGEMENT_TYPE) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の情報取得を設定します。
     */
    public void setManagementTypeCollect()
    {
        setCollect(ItemHistory.MANAGEMENT_TYPE) ;
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
        setCollect(ItemHistory.MANAGEMENT_TYPE, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(ItemHistory.CONSIGNOR_CODE, value) ;
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
        setKey(ItemHistory.CONSIGNOR_CODE, values, true) ;
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
        setKey(ItemHistory.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(ItemHistory.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(ItemHistory.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(ItemHistory.CONSIGNOR_CODE) ;
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
        setGroup(ItemHistory.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(ItemHistory.CONSIGNOR_CODE) ;
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
        setCollect(ItemHistory.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(ItemHistory.ITEM_CODE, value) ;
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
        setKey(ItemHistory.ITEM_CODE, values, true) ;
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
        setKey(ItemHistory.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(ItemHistory.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(ItemHistory.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ItemHistory.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.ITEM_CODE, ascorder) ;
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
        setOrder(ItemHistory.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(ItemHistory.ITEM_CODE) ;
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
        setGroup(ItemHistory.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(ItemHistory.ITEM_CODE) ;
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
        setCollect(ItemHistory.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 商品名称(<code>ITEM_NAME</code>)<br>
     * 文字列の検索値をセット商品名称(<code>ITEM_NAME</code>)します。
     */
    public void setItemName(String value)
    {
        setKey(ItemHistory.ITEM_NAME, value) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemName(String[] values)
    {
        setKey(ItemHistory.ITEM_NAME, values, true) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemName(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemName(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemName(String value, String compcode)
    {
        setKey(ItemHistory.ITEM_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
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
    public void setItemName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemNameOrder(boolean ascorder)
    {
        setOrder(ItemHistory.ITEM_NAME, ascorder) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemNameOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.ITEM_NAME, ascorder) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のグループ順をセットします。
     */
    public void setItemNameGroup()
    {
        setGroup(ItemHistory.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemNameGroup(int prio)
    {
        setGroup(ItemHistory.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の情報取得を設定します。
     */
    public void setItemNameCollect()
    {
        setCollect(ItemHistory.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItemNameCollect(String sqlfunc)
    {
        setCollect(ItemHistory.ITEM_NAME, sqlfunc, null) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をセットソフトゾーン(<code>SOFT_ZONE_ID</code>)します。
     */
    public void setSoftZoneId(String value)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, values, true) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftZoneIdOrder(boolean ascorder)
    {
        setOrder(ItemHistory.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setSoftZoneIdGroup()
    {
        setGroup(ItemHistory.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdGroup(int prio)
    {
        setGroup(ItemHistory.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setSoftZoneIdCollect()
    {
        setCollect(ItemHistory.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftZoneIdCollect(String sqlfunc)
    {
        setCollect(ItemHistory.SOFT_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value JANコード(<code>JAN</code>)<br>
     * 文字列の検索値をセットJANコード(<code>JAN</code>)します。
     */
    public void setJan(String value)
    {
        setKey(ItemHistory.JAN, value) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJan(String[] values)
    {
        setKey(ItemHistory.JAN, values, true) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJan(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJan(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.JAN, values, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJan(String value, String compcode)
    {
        setKey(ItemHistory.JAN, value, compcode, "", "", true) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
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
    public void setJan(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJan(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJanOrder(boolean ascorder)
    {
        setOrder(ItemHistory.JAN, ascorder) ;
    }

    /**
     * JANコード(<code>JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJanOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.JAN, ascorder) ;
    }

    /**
     * JANコード(<code>JAN</code>)のグループ順をセットします。
     */
    public void setJanGroup()
    {
        setGroup(ItemHistory.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJanGroup(int prio)
    {
        setGroup(ItemHistory.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)の情報取得を設定します。
     */
    public void setJanCollect()
    {
        setCollect(ItemHistory.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJanCollect(String sqlfunc)
    {
        setCollect(ItemHistory.JAN, sqlfunc, null) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value ケースITF(<code>ITF</code>)<br>
     * 文字列の検索値をセットケースITF(<code>ITF</code>)します。
     */
    public void setItf(String value)
    {
        setKey(ItemHistory.ITF, value) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItf(String[] values)
    {
        setKey(ItemHistory.ITF, values, true) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItf(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItf(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.ITF, values, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItf(String value, String compcode)
    {
        setKey(ItemHistory.ITF, value, compcode, "", "", true) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
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
    public void setItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItfOrder(boolean ascorder)
    {
        setOrder(ItemHistory.ITF, ascorder) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItfOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.ITF, ascorder) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のグループ順をセットします。
     */
    public void setItfGroup()
    {
        setGroup(ItemHistory.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItfGroup(int prio)
    {
        setGroup(ItemHistory.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の情報取得を設定します。
     */
    public void setItfCollect()
    {
        setCollect(ItemHistory.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItfCollect(String sqlfunc)
    {
        setCollect(ItemHistory.ITF, sqlfunc, null) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value ボールITF(<code>BUNDLE_ITF</code>)<br>
     * 文字列の検索値をセットボールITF(<code>BUNDLE_ITF</code>)します。
     */
    public void setBundleItf(String value)
    {
        setKey(ItemHistory.BUNDLE_ITF, value) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItf(String[] values)
    {
        setKey(ItemHistory.BUNDLE_ITF, values, true) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItf(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleItf(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ITF, values, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBundleItf(String value, String compcode)
    {
        setKey(ItemHistory.BUNDLE_ITF, value, compcode, "", "", true) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
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
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBundleItfOrder(boolean ascorder)
    {
        setOrder(ItemHistory.BUNDLE_ITF, ascorder) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItfOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.BUNDLE_ITF, ascorder) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のグループ順をセットします。
     */
    public void setBundleItfGroup()
    {
        setGroup(ItemHistory.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItfGroup(int prio)
    {
        setGroup(ItemHistory.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の情報取得を設定します。
     */
    public void setBundleItfCollect()
    {
        setCollect(ItemHistory.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBundleItfCollect(String sqlfunc)
    {
        setCollect(ItemHistory.BUNDLE_ITF, sqlfunc, null) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ケース入数(<code>ENTERING_QTY</code>)<br>
     * 数値の検索値をセットケース入数(<code>ENTERING_QTY</code>)します。
     */
    public void setEnteringQty(int value)
    {
        setKey(ItemHistory.ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty(int[] values)
    {
        setKey(ItemHistory.ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEnteringQty(int value, String compcode)
    {
        setKey(ItemHistory.ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
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
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEnteringQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.ENTERING_QTY, ascorder) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.ENTERING_QTY, ascorder) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のグループ順をセットします。
     */
    public void setEnteringQtyGroup()
    {
        setGroup(ItemHistory.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQtyGroup(int prio)
    {
        setGroup(ItemHistory.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の情報取得を設定します。
     */
    public void setEnteringQtyCollect()
    {
        setCollect(ItemHistory.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEnteringQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.ENTERING_QTY, sqlfunc, null) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)<br>
     * 数値の検索値をセットボール入数(<code>BUNDLE_ENTERING_QTY</code>)します。
     */
    public void setBundleEnteringQty(int value)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQty(int[] values)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBundleEnteringQty(int value, String compcode)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
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
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBundleEnteringQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.BUNDLE_ENTERING_QTY, ascorder) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.BUNDLE_ENTERING_QTY, ascorder) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のグループ順をセットします。
     */
    public void setBundleEnteringQtyGroup()
    {
        setGroup(ItemHistory.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQtyGroup(int prio)
    {
        setGroup(ItemHistory.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の情報取得を設定します。
     */
    public void setBundleEnteringQtyCollect()
    {
        setCollect(ItemHistory.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBundleEnteringQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.BUNDLE_ENTERING_QTY, sqlfunc, null) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 上限在庫数(<code>UPPER_QTY</code>)<br>
     * 数値の検索値をセット上限在庫数(<code>UPPER_QTY</code>)します。
     */
    public void setUpperQty(int value)
    {
        setKey(ItemHistory.UPPER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQty(int[] values)
    {
        setKey(ItemHistory.UPPER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPPER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpperQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPPER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpperQty(int value, String compcode)
    {
        setKey(ItemHistory.UPPER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
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
    public void setUpperQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpperQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpperQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPPER_QTY, ascorder) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPPER_QTY, ascorder) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)のグループ順をセットします。
     */
    public void setUpperQtyGroup()
    {
        setGroup(ItemHistory.UPPER_QTY) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQtyGroup(int prio)
    {
        setGroup(ItemHistory.UPPER_QTY) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の情報取得を設定します。
     */
    public void setUpperQtyCollect()
    {
        setCollect(ItemHistory.UPPER_QTY) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpperQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPPER_QTY, sqlfunc, null) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 下限在庫数(<code>LOWER_QTY</code>)<br>
     * 数値の検索値をセット下限在庫数(<code>LOWER_QTY</code>)します。
     */
    public void setLowerQty(int value)
    {
        setKey(ItemHistory.LOWER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQty(int[] values)
    {
        setKey(ItemHistory.LOWER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.LOWER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLowerQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.LOWER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLowerQty(int value, String compcode)
    {
        setKey(ItemHistory.LOWER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
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
    public void setLowerQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLowerQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLowerQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.LOWER_QTY, ascorder) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.LOWER_QTY, ascorder) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)のグループ順をセットします。
     */
    public void setLowerQtyGroup()
    {
        setGroup(ItemHistory.LOWER_QTY) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQtyGroup(int prio)
    {
        setGroup(ItemHistory.LOWER_QTY) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の情報取得を設定します。
     */
    public void setLowerQtyCollect()
    {
        setCollect(ItemHistory.LOWER_QTY) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLowerQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.LOWER_QTY, sqlfunc, null) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 把持区分(<code>HOLD_TYPE</code>)<br>
     * 文字列の検索値をセット把持区分(<code>HOLD_TYPE</code>)します。
     */
    public void setHoldType(String value)
    {
        setKey(ItemHistory.HOLD_TYPE, value) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHoldType(String[] values)
    {
        setKey(ItemHistory.HOLD_TYPE, values, true) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHoldType(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.HOLD_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHoldType(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.HOLD_TYPE, values, and_or_toNext) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHoldType(String value, String compcode)
    {
        setKey(ItemHistory.HOLD_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
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
    public void setHoldType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.HOLD_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHoldType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.HOLD_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHoldTypeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.HOLD_TYPE, ascorder) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHoldTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.HOLD_TYPE, ascorder) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)のグループ順をセットします。
     */
    public void setHoldTypeGroup()
    {
        setGroup(ItemHistory.HOLD_TYPE) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHoldTypeGroup(int prio)
    {
        setGroup(ItemHistory.HOLD_TYPE) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の情報取得を設定します。
     */
    public void setHoldTypeCollect()
    {
        setCollect(ItemHistory.HOLD_TYPE) ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHoldTypeCollect(String sqlfunc)
    {
        setCollect(ItemHistory.HOLD_TYPE, sqlfunc, null) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 一時商品区分(<code>TEMPORARY_TYPE</code>)<br>
     * 文字列の検索値をセット一時商品区分(<code>TEMPORARY_TYPE</code>)します。
     */
    public void setTemporaryType(String value)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, value) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryType(String[] values)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, values, true) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryType(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTemporaryType(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, values, and_or_toNext) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTemporaryType(String value, String compcode)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
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
    public void setTemporaryType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTemporaryType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.TEMPORARY_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTemporaryTypeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.TEMPORARY_TYPE, ascorder) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.TEMPORARY_TYPE, ascorder) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)のグループ順をセットします。
     */
    public void setTemporaryTypeGroup()
    {
        setGroup(ItemHistory.TEMPORARY_TYPE) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTemporaryTypeGroup(int prio)
    {
        setGroup(ItemHistory.TEMPORARY_TYPE) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の情報取得を設定します。
     */
    public void setTemporaryTypeCollect()
    {
        setCollect(ItemHistory.TEMPORARY_TYPE) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTemporaryTypeCollect(String sqlfunc)
    {
        setCollect(ItemHistory.TEMPORARY_TYPE, sqlfunc, null) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)<br>
     * 文字列の検索値をセット修正後商品名称(<code>UPDATE_ITEM_NAME</code>)します。
     */
    public void setUpdateItemName(String value)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, value) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItemName(String[] values)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, values, true) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItemName(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateItemName(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateItemName(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
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
    public void setUpdateItemName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateItemName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateItemNameOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ITEM_NAME, ascorder) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItemNameOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ITEM_NAME, ascorder) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)のグループ順をセットします。
     */
    public void setUpdateItemNameGroup()
    {
        setGroup(ItemHistory.UPDATE_ITEM_NAME) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItemNameGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_ITEM_NAME) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の情報取得を設定します。
     */
    public void setUpdateItemNameCollect()
    {
        setCollect(ItemHistory.UPDATE_ITEM_NAME) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateItemNameCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_ITEM_NAME, sqlfunc, null) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をセット修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)します。
     */
    public void setUpdateSoftZoneId(String value)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, value) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSoftZoneId(String[] values)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, values, true) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSoftZoneId(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateSoftZoneId(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
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
    public void setUpdateSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateSoftZoneIdOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSoftZoneIdOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setUpdateSoftZoneIdGroup()
    {
        setGroup(ItemHistory.UPDATE_SOFT_ZONE_ID) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateSoftZoneIdGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_SOFT_ZONE_ID) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setUpdateSoftZoneIdCollect()
    {
        setCollect(ItemHistory.UPDATE_SOFT_ZONE_ID) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateSoftZoneIdCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_SOFT_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
     * 
     * @param value 修正後JANコード(<code>UPDATE_JAN</code>)<br>
     * 文字列の検索値をセット修正後JANコード(<code>UPDATE_JAN</code>)します。
     */
    public void setUpdateJan(String value)
    {
        setKey(ItemHistory.UPDATE_JAN, value) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateJan(String[] values)
    {
        setKey(ItemHistory.UPDATE_JAN, values, true) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateJan(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateJan(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_JAN, values, and_or_toNext) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateJan(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_JAN, value, compcode, "", "", true) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
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
    public void setUpdateJan(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateJan(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateJanOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_JAN, ascorder) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateJanOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_JAN, ascorder) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)のグループ順をセットします。
     */
    public void setUpdateJanGroup()
    {
        setGroup(ItemHistory.UPDATE_JAN) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateJanGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_JAN) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の情報取得を設定します。
     */
    public void setUpdateJanCollect()
    {
        setCollect(ItemHistory.UPDATE_JAN) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateJanCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_JAN, sqlfunc, null) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
     * 
     * @param value 修正後ケースITF(<code>UPDATE_ITF</code>)<br>
     * 文字列の検索値をセット修正後ケースITF(<code>UPDATE_ITF</code>)します。
     */
    public void setUpdateItf(String value)
    {
        setKey(ItemHistory.UPDATE_ITF, value) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItf(String[] values)
    {
        setKey(ItemHistory.UPDATE_ITF, values, true) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItf(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateItf(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITF, values, and_or_toNext) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateItf(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_ITF, value, compcode, "", "", true) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
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
    public void setUpdateItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateItfOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ITF, ascorder) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItfOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ITF, ascorder) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)のグループ順をセットします。
     */
    public void setUpdateItfGroup()
    {
        setGroup(ItemHistory.UPDATE_ITF) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateItfGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_ITF) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の情報取得を設定します。
     */
    public void setUpdateItfCollect()
    {
        setCollect(ItemHistory.UPDATE_ITF) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateItfCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_ITF, sqlfunc, null) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)<br>
     * 数値の検索値をセット修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)します。
     */
    public void setUpdateEnteringQty(int value)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateEnteringQty(int[] values)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateEnteringQty(int value, String compcode)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
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
    public void setUpdateEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateEnteringQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ENTERING_QTY, ascorder) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateEnteringQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_ENTERING_QTY, ascorder) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)のグループ順をセットします。
     */
    public void setUpdateEnteringQtyGroup()
    {
        setGroup(ItemHistory.UPDATE_ENTERING_QTY) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateEnteringQtyGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_ENTERING_QTY) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の情報取得を設定します。
     */
    public void setUpdateEnteringQtyCollect()
    {
        setCollect(ItemHistory.UPDATE_ENTERING_QTY) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateEnteringQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_ENTERING_QTY, sqlfunc, null) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)<br>
     * 数値の検索値をセット修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)します。
     */
    public void setUpdateUpperQty(int value)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUpperQty(int[] values)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUpperQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateUpperQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateUpperQty(int value, String compcode)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
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
    public void setUpdateUpperQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateUpperQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateUpperQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_UPPER_QTY, ascorder) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUpperQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_UPPER_QTY, ascorder) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)のグループ順をセットします。
     */
    public void setUpdateUpperQtyGroup()
    {
        setGroup(ItemHistory.UPDATE_UPPER_QTY) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUpperQtyGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_UPPER_QTY) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の情報取得を設定します。
     */
    public void setUpdateUpperQtyCollect()
    {
        setCollect(ItemHistory.UPDATE_UPPER_QTY) ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateUpperQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_UPPER_QTY, sqlfunc, null) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)<br>
     * 数値の検索値をセット修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)します。
     */
    public void setUpdateLowerQty(int value)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLowerQty(int[] values)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLowerQty(int[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateLowerQty(int[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateLowerQty(int value, String compcode)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
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
    public void setUpdateLowerQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateLowerQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateLowerQtyOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_LOWER_QTY, ascorder) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLowerQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_LOWER_QTY, ascorder) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)のグループ順をセットします。
     */
    public void setUpdateLowerQtyGroup()
    {
        setGroup(ItemHistory.UPDATE_LOWER_QTY) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateLowerQtyGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_LOWER_QTY) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の情報取得を設定します。
     */
    public void setUpdateLowerQtyCollect()
    {
        setCollect(ItemHistory.UPDATE_LOWER_QTY) ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateLowerQtyCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_LOWER_QTY, sqlfunc, null) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)<br>
     * 文字列の検索値をセット修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)します。
     */
    public void setUpdateHoldType(String value)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, value) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateHoldType(String[] values)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, values, true) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateHoldType(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateHoldType(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, values, and_or_toNext) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateHoldType(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
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
    public void setUpdateHoldType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateHoldType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_HOLD_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateHoldTypeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_HOLD_TYPE, ascorder) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateHoldTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_HOLD_TYPE, ascorder) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)のグループ順をセットします。
     */
    public void setUpdateHoldTypeGroup()
    {
        setGroup(ItemHistory.UPDATE_HOLD_TYPE) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateHoldTypeGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_HOLD_TYPE) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の情報取得を設定します。
     */
    public void setUpdateHoldTypeCollect()
    {
        setCollect(ItemHistory.UPDATE_HOLD_TYPE) ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateHoldTypeCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_HOLD_TYPE, sqlfunc, null) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)<br>
     * 文字列の検索値をセット修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)します。
     */
    public void setUpdateTemporaryType(String value)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, value) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTemporaryType(String[] values)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, values, true) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTemporaryType(String[] values, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateTemporaryType(String[] values, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, values, and_or_toNext) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateTemporaryType(String value, String compcode)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
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
    public void setUpdateTemporaryType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateTemporaryType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ItemHistory.UPDATE_TEMPORARY_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateTemporaryTypeOrder(boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_TEMPORARY_TYPE, ascorder) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTemporaryTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ItemHistory.UPDATE_TEMPORARY_TYPE, ascorder) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)のグループ順をセットします。
     */
    public void setUpdateTemporaryTypeGroup()
    {
        setGroup(ItemHistory.UPDATE_TEMPORARY_TYPE) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTemporaryTypeGroup(int prio)
    {
        setGroup(ItemHistory.UPDATE_TEMPORARY_TYPE) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の情報取得を設定します。
     */
    public void setUpdateTemporaryTypeCollect()
    {
        setCollect(ItemHistory.UPDATE_TEMPORARY_TYPE) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateTemporaryTypeCollect(String sqlfunc)
    {
        setCollect(ItemHistory.UPDATE_TEMPORARY_TYPE, sqlfunc, null) ;
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
        return "$Id: ItemHistorySearchKey.java 7591 2010-03-15 13:51:03Z kishimoto $" ;
    }
}
