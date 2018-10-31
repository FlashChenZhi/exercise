// $Id: WarenaviSystemAlterKey.java 7996 2011-07-06 00:52:24Z kitamaki $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WARENAVISYSTEM用の更新キークラスです。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */

public class WarenaviSystemAlterKey
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
    public static final StoreMetaData $storeMetaData = WarenaviSystem.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public WarenaviSystemAlterKey()
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
     * 文字列の検索値をシステムNo.(<code>SYSTEM_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSystemNo(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, values, and_or_toNext) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSystemNo(String value, String compcode)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, "", "", true) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
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
    public void setSystemNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSystemNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SYSTEM_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * システムNo.(<code>SYSTEM_NO</code>)の更新値をセットします。
     * @param value システムNo.(<code>SYSTEM_NO</code>)更新値
     */
    public void updateSystemNo(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.SYSTEM_NO, value) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値を作業日(<code>WORK_DAY</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(WarenaviSystem.WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
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
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業日(<code>WORK_DAY</code>)の更新値をセットします。
     * @param value 作業日(<code>WORK_DAY</code>)更新値
     */
    public void updateWorkDay(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.WORK_DAY, value) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日次更新日時(<code>DAILY_UPDATE_DATE</code>)<br>
     * 日付の検索値を日次更新日時(<code>DAILY_UPDATE_DATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDailyUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDailyUpdateDate(Date value, String compcode)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setDailyUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDailyUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 日次更新日時(<code>DAILY_UPDATE_DATE</code>)更新値
     */
    public void updateDailyUpdateDate(Date value)
    {
        setAdhocUpdateValue(WarenaviSystem.DAILY_UPDATE_DATE, value) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)<br>
     * 数値の検索値を実績保持日数(<code>RESULT_HOLD_PERIOD</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setResultHoldPeriod(int[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setResultHoldPeriod(int value, String compcode)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
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
    public void setResultHoldPeriod(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setResultHoldPeriod(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)の更新値をセットします。
     * @param value 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)更新値
     */
    public void updateResultHoldPeriod(int value)
    {
        setAdhocUpdateValue(WarenaviSystem.RESULT_HOLD_PERIOD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)<br>
     * 数値の検索値を予定保持日数(<code>PLAN_HOLD_PERIOD</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanHoldPeriod(int[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanHoldPeriod(int value, String compcode)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
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
    public void setPlanHoldPeriod(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanHoldPeriod(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)の更新値をセットします。
     * @param value 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)更新値
     */
    public void updatePlanHoldPeriod(int value)
    {
        setAdhocUpdateValue(WarenaviSystem.PLAN_HOLD_PERIOD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 入荷パッケージ(<code>RECEIVING_PACK</code>)<br>
     * 文字列の検索値を入荷パッケージ(<code>RECEIVING_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReceivingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, values, and_or_toNext) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReceivingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
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
    public void setReceivingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReceivingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RECEIVING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>)の更新値をセットします。
     * @param value 入荷パッケージ(<code>RECEIVING_PACK</code>)更新値
     */
    public void updateReceivingPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.RECEIVING_PACK, value) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 入庫パッケージ(<code>STORAGE_PACK</code>)<br>
     * 文字列の検索値を入庫パッケージ(<code>STORAGE_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStoragePack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, values, and_or_toNext) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStoragePack(String value, String compcode)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
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
    public void setStoragePack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStoragePack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STORAGE_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>)の更新値をセットします。
     * @param value 入庫パッケージ(<code>STORAGE_PACK</code>)更新値
     */
    public void updateStoragePack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.STORAGE_PACK, value) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 出庫パッケージ(<code>RETRIEVAL_PACK</code>)<br>
     * 文字列の検索値を出庫パッケージ(<code>RETRIEVAL_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, values, and_or_toNext) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalPack(String value, String compcode)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
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
    public void setRetrievalPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>)の更新値をセットします。
     * @param value 出庫パッケージ(<code>RETRIEVAL_PACK</code>)更新値
     */
    public void updateRetrievalPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.RETRIEVAL_PACK, value) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 仕分パッケージ(<code>SORTING_PACK</code>)<br>
     * 文字列の検索値を仕分パッケージ(<code>SORTING_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSortingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, values, and_or_toNext) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSortingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
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
    public void setSortingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSortingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SORTING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>)の更新値をセットします。
     * @param value 仕分パッケージ(<code>SORTING_PACK</code>)更新値
     */
    public void updateSortingPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.SORTING_PACK, value) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 出荷パッケージ(<code>SHIPPING_PACK</code>)<br>
     * 文字列の検索値を出荷パッケージ(<code>SHIPPING_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShippingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, values, and_or_toNext) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShippingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
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
    public void setShippingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShippingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.SHIPPING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>)の更新値をセットします。
     * @param value 出荷パッケージ(<code>SHIPPING_PACK</code>)更新値
     */
    public void updateShippingPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.SHIPPING_PACK, value) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 在庫パッケージ(<code>STOCK_PACK</code>)<br>
     * 文字列の検索値を在庫パッケージ(<code>STOCK_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, values, and_or_toNext) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockPack(String value, String compcode)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
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
    public void setStockPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStockPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>)の更新値をセットします。
     * @param value 在庫パッケージ(<code>STOCK_PACK</code>)更新値
     */
    public void updateStockPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.STOCK_PACK, value) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)<br>
     * 文字列の検索値をクロスドックパッケージ(<code>CROSSDOCK_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCrossdockPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, values, and_or_toNext) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCrossdockPack(String value, String compcode)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, "", "", true) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
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
    public void setCrossdockPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCrossdockPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CROSSDOCK_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)の更新値をセットします。
     * @param value クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)更新値
     */
    public void updateCrossdockPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.CROSSDOCK_PACK, value) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)<br>
     * 文字列の検索値をPCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPctMasterPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, values, and_or_toNext) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPctMasterPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
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
    public void setPctMasterPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPctMasterPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_MASTER_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)の更新値をセットします。
     * @param value PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)更新値
     */
    public void updatePctMasterPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.PCT_MASTER_PACK, value) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)<br>
     * 文字列の検索値をPCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPctReceivingPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPctReceivingPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
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
    public void setPctReceivingPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPctReceivingPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RECEIVING_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)の更新値をセットします。
     * @param value PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)更新値
     */
    public void updatePctReceivingPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.PCT_RECEIVING_PACK, value) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)<br>
     * 文字列の検索値をPCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPctRetrievalPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPctRetrievalPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
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
    public void setPctRetrievalPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPctRetrievalPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_RETRIEVAL_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)の更新値をセットします。
     * @param value PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)更新値
     */
    public void updatePctRetrievalPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.PCT_RETRIEVAL_PACK, value) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)<br>
     * 文字列の検索値をPCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPctInventoryPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, values, and_or_toNext) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPctInventoryPack(String value, String compcode)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, "", "", true) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
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
    public void setPctInventoryPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPctInventoryPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.PCT_INVENTORY_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)の更新値をセットします。
     * @param value PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)更新値
     */
    public void updatePctInventoryPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.PCT_INVENTORY_PACK, value) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)<br>
     * 文字列の検索値を分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAnalysisPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, values, and_or_toNext) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAnalysisPack(String value, String compcode)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, "", "", true) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
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
    public void setAnalysisPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAnalysisPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ANALYSIS_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)の更新値をセットします。
     * @param value 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)更新値
     */
    public void updateAnalysisPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.ANALYSIS_PACK, value) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value AS/RSパッケージ(<code>ASRS_PACK</code>)<br>
     * 文字列の検索値をAS/RSパッケージ(<code>ASRS_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAsrsPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, values, and_or_toNext) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAsrsPack(String value, String compcode)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, "", "", true) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
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
    public void setAsrsPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAsrsPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ASRS_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>)の更新値をセットします。
     * @param value AS/RSパッケージ(<code>ASRS_PACK</code>)更新値
     */
    public void updateAsrsPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.ASRS_PACK, value) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value マスタ管理パッケージ(<code>MASTER_PACK</code>)<br>
     * 文字列の検索値をマスタ管理パッケージ(<code>MASTER_PACK</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMasterPack(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, values, and_or_toNext) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMasterPack(String value, String compcode)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, "", "", true) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
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
    public void setMasterPack(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMasterPack(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_PACK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>)の更新値をセットします。
     * @param value マスタ管理パッケージ(<code>MASTER_PACK</code>)更新値
     */
    public void updateMasterPack(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.MASTER_PACK, value) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value FA/DA区分(<code>FADA_FLAG</code>)<br>
     * 文字列の検索値をFA/DA区分(<code>FADA_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFadaFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, values, and_or_toNext) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFadaFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
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
    public void setFadaFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFadaFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.FADA_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * FA/DA区分(<code>FADA_FLAG</code>)の更新値をセットします。
     * @param value FA/DA区分(<code>FADA_FLAG</code>)更新値
     */
    public void updateFadaFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.FADA_FLAG, value) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)<br>
     * 文字列の検索値を操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControlLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControlLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
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
    public void setControlLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControlLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.CONTROL_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)の更新値をセットします。
     * @param value 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)更新値
     */
    public void updateControlLogFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.CONTROL_LOG_FLAG, value) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)<br>
     * 文字列の検索値をマスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMasterUpdateLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMasterUpdateLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
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
    public void setMasterUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMasterUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)の更新値をセットします。
     * @param value マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)更新値
     */
    public void updateMasterUpdateLogFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.MASTER_UPDATE_LOG_FLAG, value) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)<br>
     * 文字列の検索値を在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockUpdateLogFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockUpdateLogFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
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
    public void setStockUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStockUpdateLogFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)の更新値をセットします。
     * @param value 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)更新値
     */
    public void updateStockUpdateLogFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.STOCK_UPDATE_LOG_FLAG, value) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value ホスト通信状態(<code>COMM_STATUS_FLAG</code>)<br>
     * 文字列の検索値をホスト通信状態(<code>COMM_STATUS_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCommStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCommStatusFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
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
    public void setCommStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCommStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.COMM_STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>)の更新値をセットします。
     * @param value ホスト通信状態(<code>COMM_STATUS_FLAG</code>)更新値
     */
    public void updateCommStatusFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.COMM_STATUS_FLAG, value) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 日次更新中フラグ(<code>DAILY_UPDATE</code>)<br>
     * 文字列の検索値を日次更新中フラグ(<code>DAILY_UPDATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDailyUpdate(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, values, and_or_toNext) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDailyUpdate(String value, String compcode)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, "", "", true) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
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
    public void setDailyUpdate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDailyUpdate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.DAILY_UPDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>)の更新値をセットします。
     * @param value 日次更新中フラグ(<code>DAILY_UPDATE</code>)更新値
     */
    public void updateDailyUpdate(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.DAILY_UPDATE, value) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 予定データ取込中フラグ(<code>LOAD_DATA</code>)<br>
     * 文字列の検索値を予定データ取込中フラグ(<code>LOAD_DATA</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadData(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, values, and_or_toNext) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadData(String value, String compcode)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, "", "", true) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
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
    public void setLoadData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.LOAD_DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>)の更新値をセットします。
     * @param value 予定データ取込中フラグ(<code>LOAD_DATA</code>)更新値
     */
    public void updateLoadData(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.LOAD_DATA, value) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 報告データ作成中フラグ(<code>REPORT_DATA</code>)<br>
     * 文字列の検索値を報告データ作成中フラグ(<code>REPORT_DATA</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReportData(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, values, and_or_toNext) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReportData(String value, String compcode)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, "", "", true) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
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
    public void setReportData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReportData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.REPORT_DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>)の更新値をセットします。
     * @param value 報告データ作成中フラグ(<code>REPORT_DATA</code>)更新値
     */
    public void updateReportData(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.REPORT_DATA, value) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)<br>
     * 文字列の検索値を出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalAllocate(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, values, and_or_toNext) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalAllocate(String value, String compcode)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, "", "", true) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
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
    public void setRetrievalAllocate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalAllocate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.RETRIEVAL_ALLOCATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)の更新値をセットします。
     * @param value 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)更新値
     */
    public void updateRetrievalAllocate(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.RETRIEVAL_ALLOCATE, value) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)<br>
     * 文字列の検索値を搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocationClear(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, values, and_or_toNext) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocationClear(String value, String compcode)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, "", "", true) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
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
    public void setAllocationClear(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocationClear(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.ALLOCATION_CLEAR, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)の更新値をセットします。
     * @param value 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)更新値
     */
    public void updateAllocationClear(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.ALLOCATION_CLEAR, value) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)<br>
     * 文字列の検索値を終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndProcessingFlag(String[] values, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, values, and_or_toNext) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndProcessingFlag(String value, String compcode)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
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
    public void setEndProcessingFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndProcessingFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WarenaviSystem.END_PROCESSING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)の更新値をセットします。
     * @param value 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)更新値
     */
    public void updateEndProcessingFlag(String value)
    {
        setAdhocUpdateValue(WarenaviSystem.END_PROCESSING_FLAG, value) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateResultHoldPeriodWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WarenaviSystem.RESULT_HOLD_PERIOD, source, addvalue);
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePlanHoldPeriodWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WarenaviSystem.PLAN_HOLD_PERIOD, source, addvalue);
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
        return "$Id: WarenaviSystemAlterKey.java 7996 2011-07-06 00:52:24Z kitamaki $" ;
    }
}
