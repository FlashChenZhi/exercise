// $Id: WarenaviSystemSearchKey.java 7996 2011-07-06 00:52:24Z kitamaki $
// $LastChangedRevision: 7996 $
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
import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WARENAVISYSTEM用の検索キークラスです。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */


public class WarenaviSystemSearchKey
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
    public WarenaviSystemSearchKey()
    {
        super(WarenaviSystem.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param value システムNo.(<code>SYSTEM_NO</code>)<br>
     * 文字列の検索値をセットシステムNo.(<code>SYSTEM_NO</code>)します。
     */
    public void setSystemNo(String value)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSystemNo(String[] values)
    {
        setKey(WarenaviSystem.SYSTEM_NO, values, true) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSystemNo(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSystemNo(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, values, and_or_toNext) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSystemNo(String value, String compcode)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, "", "", true) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
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
    public void setSystemNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSystemNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSystemNoOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.SYSTEM_NO, ascorder) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSystemNoOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.SYSTEM_NO, ascorder) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)のグループ順をセットします。
     */
    public void setSystemNoGroup()
    {
        setGroup(WarenaviSystem.SYSTEM_NO) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSystemNoGroup(int prio)
    {
        setGroup(WarenaviSystem.SYSTEM_NO) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の情報取得を設定します。
     */
    public void setSystemNoCollect()
    {
        setCollect(WarenaviSystem.SYSTEM_NO) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSystemNoCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.SYSTEM_NO, sqlfunc, null) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
     */
    public void setWorkDay(String value)
    {
        setKey(WarenaviSystem.WORK_DAY, value) ;
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
        setKey(WarenaviSystem.WORK_DAY, values, true) ;
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
        setKey(WarenaviSystem.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(WarenaviSystem.WORK_DAY, value, compcode, "", "", true) ;
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
        setKey(WarenaviSystem.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(WarenaviSystem.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.WORK_DAY, ascorder) ;
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
        setOrder(WarenaviSystem.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(WarenaviSystem.WORK_DAY) ;
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
        setGroup(WarenaviSystem.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(WarenaviSystem.WORK_DAY) ;
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
        setCollect(WarenaviSystem.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日次更新日時(<code>DAILY_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット日次更新日時(<code>DAILY_UPDATE_DATE</code>)します。
     */
    public void setDailyUpdateDate(Date value)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateDate(Date[] values)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, values, true) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDailyUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDailyUpdateDate(Date value, String compcode)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setDailyUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDailyUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDailyUpdateDateOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.DAILY_UPDATE_DATE, ascorder) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateDateOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.DAILY_UPDATE_DATE, ascorder) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setDailyUpdateDateGroup()
    {
        setGroup(WarenaviSystem.DAILY_UPDATE_DATE) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateDateGroup(int prio)
    {
        setGroup(WarenaviSystem.DAILY_UPDATE_DATE) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setDailyUpdateDateCollect()
    {
        setCollect(WarenaviSystem.DAILY_UPDATE_DATE) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDailyUpdateDateCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.DAILY_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)<br>
     * 数値の検索値をセット実績保持日数(<code>RESULT_HOLD_PERIOD</code>)します。
     */
    public void setResultHoldPeriod(int value)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultHoldPeriod(int[] values)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultHoldPeriod(int[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultHoldPeriod(int[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultHoldPeriod(int value, String compcode)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
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
    public void setResultHoldPeriod(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultHoldPeriod(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultHoldPeriodOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.RESULT_HOLD_PERIOD, ascorder) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultHoldPeriodOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.RESULT_HOLD_PERIOD, ascorder) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)のグループ順をセットします。
     */
    public void setResultHoldPeriodGroup()
    {
        setGroup(WarenaviSystem.RESULT_HOLD_PERIOD) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultHoldPeriodGroup(int prio)
    {
        setGroup(WarenaviSystem.RESULT_HOLD_PERIOD) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の情報取得を設定します。
     */
    public void setResultHoldPeriodCollect()
    {
        setCollect(WarenaviSystem.RESULT_HOLD_PERIOD) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setResultHoldPeriodCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.RESULT_HOLD_PERIOD, sqlfunc, null) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)<br>
     * 数値の検索値をセット予定保持日数(<code>PLAN_HOLD_PERIOD</code>)します。
     */
    public void setPlanHoldPeriod(int value)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanHoldPeriod(int[] values)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanHoldPeriod(int[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanHoldPeriod(int[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanHoldPeriod(int value, String compcode)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
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
    public void setPlanHoldPeriod(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanHoldPeriod(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanHoldPeriodOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.PLAN_HOLD_PERIOD, ascorder) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanHoldPeriodOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.PLAN_HOLD_PERIOD, ascorder) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)のグループ順をセットします。
     */
    public void setPlanHoldPeriodGroup()
    {
        setGroup(WarenaviSystem.PLAN_HOLD_PERIOD) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanHoldPeriodGroup(int prio)
    {
        setGroup(WarenaviSystem.PLAN_HOLD_PERIOD) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の情報取得を設定します。
     */
    public void setPlanHoldPeriodCollect()
    {
        setCollect(WarenaviSystem.PLAN_HOLD_PERIOD) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanHoldPeriodCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.PLAN_HOLD_PERIOD, sqlfunc, null) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 入荷パッケージ(<code>RECEIVING_PACK</code>)<br>
     * 文字列の検索値をセット入荷パッケージ(<code>RECEIVING_PACK</code>)します。
     */
    public void setReceivingPack(String value)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingPack(String[] values)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, values, true) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceivingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, values, and_or_toNext) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReceivingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
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
    public void setReceivingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceivingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReceivingPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.RECEIVING_PACK, ascorder) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.RECEIVING_PACK, ascorder) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)のグループ順をセットします。
     */
    public void setReceivingPackGroup()
    {
        setGroup(WarenaviSystem.RECEIVING_PACK) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingPackGroup(int prio)
    {
        setGroup(WarenaviSystem.RECEIVING_PACK) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の情報取得を設定します。
     */
    public void setReceivingPackCollect()
    {
        setCollect(WarenaviSystem.RECEIVING_PACK) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReceivingPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.RECEIVING_PACK, sqlfunc, null) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 入庫パッケージ(<code>STORAGE_PACK</code>)<br>
     * 文字列の検索値をセット入庫パッケージ(<code>STORAGE_PACK</code>)します。
     */
    public void setStoragePack(String value)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStoragePack(String[] values)
    {
        setKey(WarenaviSystem.STORAGE_PACK, values, true) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStoragePack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStoragePack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, values, and_or_toNext) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStoragePack(String value, String compcode)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
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
    public void setStoragePack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStoragePack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStoragePackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.STORAGE_PACK, ascorder) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStoragePackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.STORAGE_PACK, ascorder) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)のグループ順をセットします。
     */
    public void setStoragePackGroup()
    {
        setGroup(WarenaviSystem.STORAGE_PACK) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStoragePackGroup(int prio)
    {
        setGroup(WarenaviSystem.STORAGE_PACK) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の情報取得を設定します。
     */
    public void setStoragePackCollect()
    {
        setCollect(WarenaviSystem.STORAGE_PACK) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStoragePackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.STORAGE_PACK, sqlfunc, null) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 出庫パッケージ(<code>RETRIEVAL_PACK</code>)<br>
     * 文字列の検索値をセット出庫パッケージ(<code>RETRIEVAL_PACK</code>)します。
     */
    public void setRetrievalPack(String value)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalPack(String[] values)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, values, true) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, values, and_or_toNext) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalPack(String value, String compcode)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
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
    public void setRetrievalPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.RETRIEVAL_PACK, ascorder) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.RETRIEVAL_PACK, ascorder) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)のグループ順をセットします。
     */
    public void setRetrievalPackGroup()
    {
        setGroup(WarenaviSystem.RETRIEVAL_PACK) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalPackGroup(int prio)
    {
        setGroup(WarenaviSystem.RETRIEVAL_PACK) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の情報取得を設定します。
     */
    public void setRetrievalPackCollect()
    {
        setCollect(WarenaviSystem.RETRIEVAL_PACK) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.RETRIEVAL_PACK, sqlfunc, null) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 仕分パッケージ(<code>SORTING_PACK</code>)<br>
     * 文字列の検索値をセット仕分パッケージ(<code>SORTING_PACK</code>)します。
     */
    public void setSortingPack(String value)
    {
        setKey(WarenaviSystem.SORTING_PACK, value) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPack(String[] values)
    {
        setKey(WarenaviSystem.SORTING_PACK, values, true) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSortingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, values, and_or_toNext) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSortingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
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
    public void setSortingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSortingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSortingPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.SORTING_PACK, ascorder) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.SORTING_PACK, ascorder) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)のグループ順をセットします。
     */
    public void setSortingPackGroup()
    {
        setGroup(WarenaviSystem.SORTING_PACK) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPackGroup(int prio)
    {
        setGroup(WarenaviSystem.SORTING_PACK) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の情報取得を設定します。
     */
    public void setSortingPackCollect()
    {
        setCollect(WarenaviSystem.SORTING_PACK) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSortingPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.SORTING_PACK, sqlfunc, null) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 出荷パッケージ(<code>SHIPPING_PACK</code>)<br>
     * 文字列の検索値をセット出荷パッケージ(<code>SHIPPING_PACK</code>)します。
     */
    public void setShippingPack(String value)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShippingPack(String[] values)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, values, true) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShippingPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShippingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, values, and_or_toNext) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShippingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
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
    public void setShippingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShippingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShippingPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.SHIPPING_PACK, ascorder) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShippingPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.SHIPPING_PACK, ascorder) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)のグループ順をセットします。
     */
    public void setShippingPackGroup()
    {
        setGroup(WarenaviSystem.SHIPPING_PACK) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShippingPackGroup(int prio)
    {
        setGroup(WarenaviSystem.SHIPPING_PACK) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の情報取得を設定します。
     */
    public void setShippingPackCollect()
    {
        setCollect(WarenaviSystem.SHIPPING_PACK) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShippingPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.SHIPPING_PACK, sqlfunc, null) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 在庫パッケージ(<code>STOCK_PACK</code>)<br>
     * 文字列の検索値をセット在庫パッケージ(<code>STOCK_PACK</code>)します。
     */
    public void setStockPack(String value)
    {
        setKey(WarenaviSystem.STOCK_PACK, value) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockPack(String[] values)
    {
        setKey(WarenaviSystem.STOCK_PACK, values, true) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, values, and_or_toNext) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStockPack(String value, String compcode)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
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
    public void setStockPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStockPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.STOCK_PACK, ascorder) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.STOCK_PACK, ascorder) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)のグループ順をセットします。
     */
    public void setStockPackGroup()
    {
        setGroup(WarenaviSystem.STOCK_PACK) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockPackGroup(int prio)
    {
        setGroup(WarenaviSystem.STOCK_PACK) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の情報取得を設定します。
     */
    public void setStockPackCollect()
    {
        setCollect(WarenaviSystem.STOCK_PACK) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStockPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.STOCK_PACK, sqlfunc, null) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)<br>
     * 文字列の検索値をセットクロスドックパッケージ(<code>CROSSDOCK_PACK</code>)します。
     */
    public void setCrossdockPack(String value)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCrossdockPack(String[] values)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, values, true) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCrossdockPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCrossdockPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, values, and_or_toNext) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCrossdockPack(String value, String compcode)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, "", "", true) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
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
    public void setCrossdockPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCrossdockPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCrossdockPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.CROSSDOCK_PACK, ascorder) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCrossdockPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.CROSSDOCK_PACK, ascorder) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)のグループ順をセットします。
     */
    public void setCrossdockPackGroup()
    {
        setGroup(WarenaviSystem.CROSSDOCK_PACK) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCrossdockPackGroup(int prio)
    {
        setGroup(WarenaviSystem.CROSSDOCK_PACK) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の情報取得を設定します。
     */
    public void setCrossdockPackCollect()
    {
        setCollect(WarenaviSystem.CROSSDOCK_PACK) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCrossdockPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.CROSSDOCK_PACK, sqlfunc, null) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)<br>
     * 文字列の検索値をセットPCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)します。
     */
    public void setPctMasterPack(String value)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctMasterPack(String[] values)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, values, true) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctMasterPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctMasterPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, values, and_or_toNext) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPctMasterPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
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
    public void setPctMasterPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctMasterPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPctMasterPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_MASTER_PACK, ascorder) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctMasterPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_MASTER_PACK, ascorder) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)のグループ順をセットします。
     */
    public void setPctMasterPackGroup()
    {
        setGroup(WarenaviSystem.PCT_MASTER_PACK) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctMasterPackGroup(int prio)
    {
        setGroup(WarenaviSystem.PCT_MASTER_PACK) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の情報取得を設定します。
     */
    public void setPctMasterPackCollect()
    {
        setCollect(WarenaviSystem.PCT_MASTER_PACK) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPctMasterPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.PCT_MASTER_PACK, sqlfunc, null) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)<br>
     * 文字列の検索値をセットPCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)します。
     */
    public void setPctReceivingPack(String value)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctReceivingPack(String[] values)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, values, true) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctReceivingPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctReceivingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPctReceivingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
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
    public void setPctReceivingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctReceivingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPctReceivingPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_RECEIVING_PACK, ascorder) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctReceivingPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_RECEIVING_PACK, ascorder) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)のグループ順をセットします。
     */
    public void setPctReceivingPackGroup()
    {
        setGroup(WarenaviSystem.PCT_RECEIVING_PACK) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctReceivingPackGroup(int prio)
    {
        setGroup(WarenaviSystem.PCT_RECEIVING_PACK) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の情報取得を設定します。
     */
    public void setPctReceivingPackCollect()
    {
        setCollect(WarenaviSystem.PCT_RECEIVING_PACK) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPctReceivingPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.PCT_RECEIVING_PACK, sqlfunc, null) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)<br>
     * 文字列の検索値をセットPCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)します。
     */
    public void setPctRetrievalPack(String value)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctRetrievalPack(String[] values)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, values, true) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctRetrievalPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctRetrievalPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPctRetrievalPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
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
    public void setPctRetrievalPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctRetrievalPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPctRetrievalPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_RETRIEVAL_PACK, ascorder) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctRetrievalPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_RETRIEVAL_PACK, ascorder) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)のグループ順をセットします。
     */
    public void setPctRetrievalPackGroup()
    {
        setGroup(WarenaviSystem.PCT_RETRIEVAL_PACK) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctRetrievalPackGroup(int prio)
    {
        setGroup(WarenaviSystem.PCT_RETRIEVAL_PACK) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の情報取得を設定します。
     */
    public void setPctRetrievalPackCollect()
    {
        setCollect(WarenaviSystem.PCT_RETRIEVAL_PACK) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPctRetrievalPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.PCT_RETRIEVAL_PACK, sqlfunc, null) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)<br>
     * 文字列の検索値をセットPCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)します。
     */
    public void setPctInventoryPack(String value)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctInventoryPack(String[] values)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, values, true) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctInventoryPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctInventoryPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPctInventoryPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
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
    public void setPctInventoryPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctInventoryPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPctInventoryPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_INVENTORY_PACK, ascorder) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctInventoryPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.PCT_INVENTORY_PACK, ascorder) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)のグループ順をセットします。
     */
    public void setPctInventoryPackGroup()
    {
        setGroup(WarenaviSystem.PCT_INVENTORY_PACK) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctInventoryPackGroup(int prio)
    {
        setGroup(WarenaviSystem.PCT_INVENTORY_PACK) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の情報取得を設定します。
     */
    public void setPctInventoryPackCollect()
    {
        setCollect(WarenaviSystem.PCT_INVENTORY_PACK) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPctInventoryPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.PCT_INVENTORY_PACK, sqlfunc, null) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)<br>
     * 文字列の検索値をセット分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)します。
     */
    public void setAnalysisPack(String value)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAnalysisPack(String[] values)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, values, true) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAnalysisPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAnalysisPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, values, and_or_toNext) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAnalysisPack(String value, String compcode)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
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
    public void setAnalysisPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAnalysisPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAnalysisPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.ANALYSIS_PACK, ascorder) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAnalysisPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.ANALYSIS_PACK, ascorder) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)のグループ順をセットします。
     */
    public void setAnalysisPackGroup()
    {
        setGroup(WarenaviSystem.ANALYSIS_PACK) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAnalysisPackGroup(int prio)
    {
        setGroup(WarenaviSystem.ANALYSIS_PACK) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の情報取得を設定します。
     */
    public void setAnalysisPackCollect()
    {
        setCollect(WarenaviSystem.ANALYSIS_PACK) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAnalysisPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.ANALYSIS_PACK, sqlfunc, null) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value AS/RSパッケージ(<code>ASRS_PACK</code>)<br>
     * 文字列の検索値をセットAS/RSパッケージ(<code>ASRS_PACK</code>)します。
     */
    public void setAsrsPack(String value)
    {
        setKey(WarenaviSystem.ASRS_PACK, value) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAsrsPack(String[] values)
    {
        setKey(WarenaviSystem.ASRS_PACK, values, true) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAsrsPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAsrsPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, values, and_or_toNext) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAsrsPack(String value, String compcode)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, "", "", true) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
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
    public void setAsrsPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAsrsPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAsrsPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.ASRS_PACK, ascorder) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAsrsPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.ASRS_PACK, ascorder) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)のグループ順をセットします。
     */
    public void setAsrsPackGroup()
    {
        setGroup(WarenaviSystem.ASRS_PACK) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAsrsPackGroup(int prio)
    {
        setGroup(WarenaviSystem.ASRS_PACK) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の情報取得を設定します。
     */
    public void setAsrsPackCollect()
    {
        setCollect(WarenaviSystem.ASRS_PACK) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAsrsPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.ASRS_PACK, sqlfunc, null) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value マスタ管理パッケージ(<code>MASTER_PACK</code>)<br>
     * 文字列の検索値をセットマスタ管理パッケージ(<code>MASTER_PACK</code>)します。
     */
    public void setMasterPack(String value)
    {
        setKey(WarenaviSystem.MASTER_PACK, value) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMasterPack(String[] values)
    {
        setKey(WarenaviSystem.MASTER_PACK, values, true) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMasterPack(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMasterPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, values, and_or_toNext) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMasterPack(String value, String compcode)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, "", "", true) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
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
    public void setMasterPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMasterPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMasterPackOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.MASTER_PACK, ascorder) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMasterPackOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.MASTER_PACK, ascorder) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)のグループ順をセットします。
     */
    public void setMasterPackGroup()
    {
        setGroup(WarenaviSystem.MASTER_PACK) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMasterPackGroup(int prio)
    {
        setGroup(WarenaviSystem.MASTER_PACK) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の情報取得を設定します。
     */
    public void setMasterPackCollect()
    {
        setCollect(WarenaviSystem.MASTER_PACK) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMasterPackCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.MASTER_PACK, sqlfunc, null) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value FA/DA区分(<code>FADA_FLAG</code>)<br>
     * 文字列の検索値をセットFA/DA区分(<code>FADA_FLAG</code>)します。
     */
    public void setFadaFlag(String value)
    {
        setKey(WarenaviSystem.FADA_FLAG, value) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFadaFlag(String[] values)
    {
        setKey(WarenaviSystem.FADA_FLAG, values, true) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFadaFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFadaFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, values, and_or_toNext) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFadaFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
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
    public void setFadaFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFadaFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFadaFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.FADA_FLAG, ascorder) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFadaFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.FADA_FLAG, ascorder) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)のグループ順をセットします。
     */
    public void setFadaFlagGroup()
    {
        setGroup(WarenaviSystem.FADA_FLAG) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFadaFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.FADA_FLAG) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の情報取得を設定します。
     */
    public void setFadaFlagCollect()
    {
        setCollect(WarenaviSystem.FADA_FLAG) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFadaFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.FADA_FLAG, sqlfunc, null) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)<br>
     * 文字列の検索値をセット操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)します。
     */
    public void setControlLogFlag(String value)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlLogFlag(String[] values)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, values, true) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlLogFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControlLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControlLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
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
    public void setControlLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControlLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControlLogFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.CONTROL_LOG_FLAG, ascorder) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControlLogFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.CONTROL_LOG_FLAG, ascorder) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)のグループ順をセットします。
     */
    public void setControlLogFlagGroup()
    {
        setGroup(WarenaviSystem.CONTROL_LOG_FLAG) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControlLogFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.CONTROL_LOG_FLAG) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の情報取得を設定します。
     */
    public void setControlLogFlagCollect()
    {
        setCollect(WarenaviSystem.CONTROL_LOG_FLAG) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setControlLogFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.CONTROL_LOG_FLAG, sqlfunc, null) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)<br>
     * 文字列の検索値をセットマスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)します。
     */
    public void setMasterUpdateLogFlag(String value)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMasterUpdateLogFlag(String[] values)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, values, true) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMasterUpdateLogFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMasterUpdateLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMasterUpdateLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
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
    public void setMasterUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMasterUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMasterUpdateLogFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, ascorder) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMasterUpdateLogFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, ascorder) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)のグループ順をセットします。
     */
    public void setMasterUpdateLogFlagGroup()
    {
        setGroup(WarenaviSystem.MASTER_UPDATE_LOG_FLAG) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMasterUpdateLogFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.MASTER_UPDATE_LOG_FLAG) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の情報取得を設定します。
     */
    public void setMasterUpdateLogFlagCollect()
    {
        setCollect(WarenaviSystem.MASTER_UPDATE_LOG_FLAG) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMasterUpdateLogFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, sqlfunc, null) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)<br>
     * 文字列の検索値をセット在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)します。
     */
    public void setStockUpdateLogFlag(String value)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockUpdateLogFlag(String[] values)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, values, true) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockUpdateLogFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockUpdateLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStockUpdateLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
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
    public void setStockUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStockUpdateLogFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, ascorder) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockUpdateLogFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, ascorder) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)のグループ順をセットします。
     */
    public void setStockUpdateLogFlagGroup()
    {
        setGroup(WarenaviSystem.STOCK_UPDATE_LOG_FLAG) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockUpdateLogFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.STOCK_UPDATE_LOG_FLAG) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の情報取得を設定します。
     */
    public void setStockUpdateLogFlagCollect()
    {
        setCollect(WarenaviSystem.STOCK_UPDATE_LOG_FLAG) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStockUpdateLogFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, sqlfunc, null) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value ホスト通信状態(<code>COMM_STATUS_FLAG</code>)<br>
     * 文字列の検索値をセットホスト通信状態(<code>COMM_STATUS_FLAG</code>)します。
     */
    public void setCommStatusFlag(String value)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCommStatusFlag(String[] values)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, values, true) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCommStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCommStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCommStatusFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
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
    public void setCommStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCommStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCommStatusFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.COMM_STATUS_FLAG, ascorder) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCommStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.COMM_STATUS_FLAG, ascorder) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setCommStatusFlagGroup()
    {
        setGroup(WarenaviSystem.COMM_STATUS_FLAG) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCommStatusFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.COMM_STATUS_FLAG) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setCommStatusFlagCollect()
    {
        setCollect(WarenaviSystem.COMM_STATUS_FLAG) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCommStatusFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.COMM_STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 日次更新中フラグ(<code>DAILY_UPDATE</code>)<br>
     * 文字列の検索値をセット日次更新中フラグ(<code>DAILY_UPDATE</code>)します。
     */
    public void setDailyUpdate(String value)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdate(String[] values)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, values, true) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdate(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDailyUpdate(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, values, and_or_toNext) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDailyUpdate(String value, String compcode)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, "", "", true) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
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
    public void setDailyUpdate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDailyUpdate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDailyUpdateOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.DAILY_UPDATE, ascorder) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.DAILY_UPDATE, ascorder) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)のグループ順をセットします。
     */
    public void setDailyUpdateGroup()
    {
        setGroup(WarenaviSystem.DAILY_UPDATE) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDailyUpdateGroup(int prio)
    {
        setGroup(WarenaviSystem.DAILY_UPDATE) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の情報取得を設定します。
     */
    public void setDailyUpdateCollect()
    {
        setCollect(WarenaviSystem.DAILY_UPDATE) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDailyUpdateCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.DAILY_UPDATE, sqlfunc, null) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 予定データ取込中フラグ(<code>LOAD_DATA</code>)<br>
     * 文字列の検索値をセット予定データ取込中フラグ(<code>LOAD_DATA</code>)します。
     */
    public void setLoadData(String value)
    {
        setKey(WarenaviSystem.LOAD_DATA, value) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadData(String[] values)
    {
        setKey(WarenaviSystem.LOAD_DATA, values, true) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadData(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadData(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, values, and_or_toNext) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadData(String value, String compcode)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, "", "", true) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
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
    public void setLoadData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadDataOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.LOAD_DATA, ascorder) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadDataOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.LOAD_DATA, ascorder) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)のグループ順をセットします。
     */
    public void setLoadDataGroup()
    {
        setGroup(WarenaviSystem.LOAD_DATA) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadDataGroup(int prio)
    {
        setGroup(WarenaviSystem.LOAD_DATA) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の情報取得を設定します。
     */
    public void setLoadDataCollect()
    {
        setCollect(WarenaviSystem.LOAD_DATA) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadDataCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.LOAD_DATA, sqlfunc, null) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 報告データ作成中フラグ(<code>REPORT_DATA</code>)<br>
     * 文字列の検索値をセット報告データ作成中フラグ(<code>REPORT_DATA</code>)します。
     */
    public void setReportData(String value)
    {
        setKey(WarenaviSystem.REPORT_DATA, value) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportData(String[] values)
    {
        setKey(WarenaviSystem.REPORT_DATA, values, true) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportData(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportData(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, values, and_or_toNext) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReportData(String value, String compcode)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, "", "", true) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
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
    public void setReportData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReportDataOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.REPORT_DATA, ascorder) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportDataOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.REPORT_DATA, ascorder) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)のグループ順をセットします。
     */
    public void setReportDataGroup()
    {
        setGroup(WarenaviSystem.REPORT_DATA) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportDataGroup(int prio)
    {
        setGroup(WarenaviSystem.REPORT_DATA) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の情報取得を設定します。
     */
    public void setReportDataCollect()
    {
        setCollect(WarenaviSystem.REPORT_DATA) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReportDataCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.REPORT_DATA, sqlfunc, null) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)<br>
     * 文字列の検索値をセット出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)します。
     */
    public void setRetrievalAllocate(String value)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAllocate(String[] values)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, values, true) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAllocate(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalAllocate(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, values, and_or_toNext) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalAllocate(String value, String compcode)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, "", "", true) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
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
    public void setRetrievalAllocate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalAllocate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalAllocateOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.RETRIEVAL_ALLOCATE, ascorder) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAllocateOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.RETRIEVAL_ALLOCATE, ascorder) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)のグループ順をセットします。
     */
    public void setRetrievalAllocateGroup()
    {
        setGroup(WarenaviSystem.RETRIEVAL_ALLOCATE) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAllocateGroup(int prio)
    {
        setGroup(WarenaviSystem.RETRIEVAL_ALLOCATE) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の情報取得を設定します。
     */
    public void setRetrievalAllocateCollect()
    {
        setCollect(WarenaviSystem.RETRIEVAL_ALLOCATE) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalAllocateCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.RETRIEVAL_ALLOCATE, sqlfunc, null) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)<br>
     * 文字列の検索値をセット搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)します。
     */
    public void setAllocationClear(String value)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocationClear(String[] values)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, values, true) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocationClear(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAllocationClear(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, values, and_or_toNext) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAllocationClear(String value, String compcode)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, "", "", true) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
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
    public void setAllocationClear(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAllocationClear(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAllocationClearOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.ALLOCATION_CLEAR, ascorder) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAllocationClearOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.ALLOCATION_CLEAR, ascorder) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)のグループ順をセットします。
     */
    public void setAllocationClearGroup()
    {
        setGroup(WarenaviSystem.ALLOCATION_CLEAR) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAllocationClearGroup(int prio)
    {
        setGroup(WarenaviSystem.ALLOCATION_CLEAR) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の情報取得を設定します。
     */
    public void setAllocationClearCollect()
    {
        setCollect(WarenaviSystem.ALLOCATION_CLEAR) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAllocationClearCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.ALLOCATION_CLEAR, sqlfunc, null) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)<br>
     * 文字列の検索値をセット終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)します。
     */
    public void setEndProcessingFlag(String value)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndProcessingFlag(String[] values)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, values, true) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndProcessingFlag(String[] values, String and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndProcessingFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, values, and_or_toNext) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndProcessingFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
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
    public void setEndProcessingFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndProcessingFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndProcessingFlagOrder(boolean ascorder)
    {
        setOrder(WarenaviSystem.END_PROCESSING_FLAG, ascorder) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndProcessingFlagOrder(int prio, boolean ascorder)
    {
        setOrder(WarenaviSystem.END_PROCESSING_FLAG, ascorder) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)のグループ順をセットします。
     */
    public void setEndProcessingFlagGroup()
    {
        setGroup(WarenaviSystem.END_PROCESSING_FLAG) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndProcessingFlagGroup(int prio)
    {
        setGroup(WarenaviSystem.END_PROCESSING_FLAG) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の情報取得を設定します。
     */
    public void setEndProcessingFlagCollect()
    {
        setCollect(WarenaviSystem.END_PROCESSING_FLAG) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEndProcessingFlagCollect(String sqlfunc)
    {
        setCollect(WarenaviSystem.END_PROCESSING_FLAG, sqlfunc, null) ;
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
        return "$Id: WarenaviSystemSearchKey.java 7996 2011-07-06 00:52:24Z kitamaki $" ;
    }
}
