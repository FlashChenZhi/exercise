// $Id: WorkerResultSearchKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WORKERRESULT用の検索キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class WorkerResultSearchKey
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
    public WorkerResultSearchKey()
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
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
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
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(WorkerResult.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayGroup(int prio)
    {
        setGroup(WorkerResult.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(WorkerResult.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkDayCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業開始日時(<code>WORK_START_DATE</code>)<br>
     * 日付の検索値をセット作業開始日時(<code>WORK_START_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkStartDate(Date value, String compcode)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
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
    public void setWorkStartDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkStartDateOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_START_DATE, ascorder) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStartDateOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_START_DATE, ascorder) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)のグループ順をセットします。
     */
    public void setWorkStartDateGroup()
    {
        setGroup(WorkerResult.WORK_START_DATE) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStartDateGroup(int prio)
    {
        setGroup(WorkerResult.WORK_START_DATE) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の情報取得を設定します。
     */
    public void setWorkStartDateCollect()
    {
        setCollect(WorkerResult.WORK_START_DATE) ;
    }

    /**
     * 作業開始日時(<code>WORK_START_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkStartDateCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_START_DATE, sqlfunc, null) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業終了日時(<code>WORK_END_DATE</code>)<br>
     * 日付の検索値をセット作業終了日時(<code>WORK_END_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkEndDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkEndDate(Date value, String compcode)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
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
    public void setWorkEndDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkEndDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_END_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkEndDateOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_END_DATE, ascorder) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkEndDateOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_END_DATE, ascorder) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)のグループ順をセットします。
     */
    public void setWorkEndDateGroup()
    {
        setGroup(WorkerResult.WORK_END_DATE) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkEndDateGroup(int prio)
    {
        setGroup(WorkerResult.WORK_END_DATE) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の情報取得を設定します。
     */
    public void setWorkEndDateCollect()
    {
        setCollect(WorkerResult.WORK_END_DATE) ;
    }

    /**
     * 作業終了日時(<code>WORK_END_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkEndDateCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_END_DATE, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(WorkerResult.USER_ID, value, compcode, "", "", true) ;
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
        setKey(WorkerResult.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(WorkerResult.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(WorkerResult.USER_ID, ascorder) ;
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
        setOrder(WorkerResult.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(WorkerResult.USER_ID) ;
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
        setGroup(WorkerResult.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(WorkerResult.USER_ID) ;
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
        setCollect(WorkerResult.USER_ID, sqlfunc, null) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をセットユーザ名称(<code>USER_NAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(WorkerResult.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(WorkerResult.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(WorkerResult.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserNameOrder(boolean ascorder)
    {
        setOrder(WorkerResult.USER_NAME, ascorder) ;
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
        setOrder(WorkerResult.USER_NAME, ascorder) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)のグループ順をセットします。
     */
    public void setUserNameGroup()
    {
        setGroup(WorkerResult.USER_NAME) ;
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
        setGroup(WorkerResult.USER_NAME) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の情報取得を設定します。
     */
    public void setUserNameCollect()
    {
        setCollect(WorkerResult.USER_NAME) ;
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
        setCollect(WorkerResult.USER_NAME, sqlfunc, null) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value RFTNo.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセットRFTNo.(<code>TERMINAL_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(WorkerResult.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(WorkerResult.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(WorkerResult.TERMINAL_NO, ascorder) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)のソート順をセットします。
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
        setOrder(WorkerResult.TERMINAL_NO, ascorder) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(WorkerResult.TERMINAL_NO) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoGroup(int prio)
    {
        setGroup(WorkerResult.TERMINAL_NO) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(WorkerResult.TERMINAL_NO) ;
    }

    /**
     * RFTNo.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNoCollect(String sqlfunc)
    {
        setCollect(WorkerResult.TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値をセット作業区分(<code>JOB_TYPE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
    public void setJobType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobTypeOrder(boolean ascorder)
    {
        setOrder(WorkerResult.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     */
    public void setJobTypeGroup()
    {
        setGroup(WorkerResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeGroup(int prio)
    {
        setGroup(WorkerResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     */
    public void setJobTypeCollect()
    {
        setCollect(WorkerResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJobTypeCollect(String sqlfunc)
    {
        setCollect(WorkerResult.JOB_TYPE, sqlfunc, null) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 作業数量(<code>WORK_QTY</code>)<br>
     * 数値の検索値をセット作業数量(<code>WORK_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkQty(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkQty(int value, String compcode)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
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
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkQtyOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_QTY, ascorder) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkQtyOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_QTY, ascorder) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)のグループ順をセットします。
     */
    public void setWorkQtyGroup()
    {
        setGroup(WorkerResult.WORK_QTY) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkQtyGroup(int prio)
    {
        setGroup(WorkerResult.WORK_QTY) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の情報取得を設定します。
     */
    public void setWorkQtyCollect()
    {
        setCollect(WorkerResult.WORK_QTY) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkQtyCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_QTY, sqlfunc, null) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数（明細数）(<code>WORK_CNT</code>)<br>
     * 数値の検索値をセット作業回数（明細数）(<code>WORK_CNT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkCnt(int value, String compcode)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
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
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkCntOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_CNT, ascorder) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkCntOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_CNT, ascorder) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)のグループ順をセットします。
     */
    public void setWorkCntGroup()
    {
        setGroup(WorkerResult.WORK_CNT) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkCntGroup(int prio)
    {
        setGroup(WorkerResult.WORK_CNT) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の情報取得を設定します。
     */
    public void setWorkCntCollect()
    {
        setCollect(WorkerResult.WORK_CNT) ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkCntCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_CNT, sqlfunc, null) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)<br>
     * 数値の検索値をセット作業回数（作業オーダ数）(<code>ORDER_CNT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOrderCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setOrderCnt(int value, String compcode)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
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
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setOrderCntOrder(boolean ascorder)
    {
        setOrder(WorkerResult.ORDER_CNT, ascorder) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOrderCntOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.ORDER_CNT, ascorder) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)のグループ順をセットします。
     */
    public void setOrderCntGroup()
    {
        setGroup(WorkerResult.ORDER_CNT) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOrderCntGroup(int prio)
    {
        setGroup(WorkerResult.ORDER_CNT) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の情報取得を設定します。
     */
    public void setOrderCntCollect()
    {
        setCollect(WorkerResult.ORDER_CNT) ;
    }

    /**
     * 作業回数（作業オーダ数）(<code>ORDER_CNT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setOrderCntCollect(String sqlfunc)
    {
        setCollect(WorkerResult.ORDER_CNT, sqlfunc, null) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 作業時間（秒）(<code>WORK_TIME</code>)<br>
     * 数値の検索値をセット作業時間（秒）(<code>WORK_TIME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkTime(int value, String compcode)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
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
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkTimeOrder(boolean ascorder)
    {
        setOrder(WorkerResult.WORK_TIME, ascorder) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTimeOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.WORK_TIME, ascorder) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)のグループ順をセットします。
     */
    public void setWorkTimeGroup()
    {
        setGroup(WorkerResult.WORK_TIME) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTimeGroup(int prio)
    {
        setGroup(WorkerResult.WORK_TIME) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の情報取得を設定します。
     */
    public void setWorkTimeCollect()
    {
        setCollect(WorkerResult.WORK_TIME) ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkTimeCollect(String sqlfunc)
    {
        setCollect(WorkerResult.WORK_TIME, sqlfunc, null) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 休憩時間（秒）(<code>REST_TIME</code>)<br>
     * 数値の検索値をセット休憩時間（秒）(<code>REST_TIME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRestTime(int value, String compcode)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
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
    public void setRestTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.REST_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRestTimeOrder(boolean ascorder)
    {
        setOrder(WorkerResult.REST_TIME, ascorder) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestTimeOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.REST_TIME, ascorder) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)のグループ順をセットします。
     */
    public void setRestTimeGroup()
    {
        setGroup(WorkerResult.REST_TIME) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestTimeGroup(int prio)
    {
        setGroup(WorkerResult.REST_TIME) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の情報取得を設定します。
     */
    public void setRestTimeCollect()
    {
        setCollect(WorkerResult.REST_TIME) ;
    }

    /**
     * 休憩時間（秒）(<code>REST_TIME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRestTimeCollect(String sqlfunc)
    {
        setCollect(WorkerResult.REST_TIME, sqlfunc, null) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 実作業時間（秒）(<code>REAL_WORK_TIME</code>)<br>
     * 数値の検索値をセット実作業時間（秒）(<code>REAL_WORK_TIME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRealWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRealWorkTime(int value, String compcode)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
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
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRealWorkTimeOrder(boolean ascorder)
    {
        setOrder(WorkerResult.REAL_WORK_TIME, ascorder) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRealWorkTimeOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.REAL_WORK_TIME, ascorder) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)のグループ順をセットします。
     */
    public void setRealWorkTimeGroup()
    {
        setGroup(WorkerResult.REAL_WORK_TIME) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRealWorkTimeGroup(int prio)
    {
        setGroup(WorkerResult.REAL_WORK_TIME) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の情報取得を設定します。
     */
    public void setRealWorkTimeCollect()
    {
        setCollect(WorkerResult.REAL_WORK_TIME) ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRealWorkTimeCollect(String sqlfunc)
    {
        setCollect(WorkerResult.REAL_WORK_TIME, sqlfunc, null) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value ミススキャン数(<code>MISS_SCAN_CNT</code>)<br>
     * 数値の検索値をセットミススキャン数(<code>MISS_SCAN_CNT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMissScanCnt(int[] values, boolean and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMissScanCnt(int value, String compcode)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
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
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WorkerResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMissScanCntOrder(boolean ascorder)
    {
        setOrder(WorkerResult.MISS_SCAN_CNT, ascorder) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMissScanCntOrder(int prio, boolean ascorder)
    {
        setOrder(WorkerResult.MISS_SCAN_CNT, ascorder) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)のグループ順をセットします。
     */
    public void setMissScanCntGroup()
    {
        setGroup(WorkerResult.MISS_SCAN_CNT) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMissScanCntGroup(int prio)
    {
        setGroup(WorkerResult.MISS_SCAN_CNT) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の情報取得を設定します。
     */
    public void setMissScanCntCollect()
    {
        setCollect(WorkerResult.MISS_SCAN_CNT) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMissScanCntCollect(String sqlfunc)
    {
        setCollect(WorkerResult.MISS_SCAN_CNT, sqlfunc, null) ;
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
        return "$Id: WorkerResultSearchKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
