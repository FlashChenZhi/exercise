// $Id: PCTPickingResultAlterKey.java 5740 2009-11-12 13:29:27Z kumano $
// $LastChangedRevision: 5740 $
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
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTPICKINGRESULT用の更新キークラスです。
 *
 * @version $Revision: 5740 $, $Date: 2009-11-12 22:29:27 +0900 (木, 12 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: kumano $
 */

public class PCTPickingResultAlterKey
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
    public static final StoreMetaData $storeMetaData = PCTPickingResult.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PCTPickingResultAlterKey()
    {
        super(PCTPickingResult.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(PCTPickingResult.USER_ID, value) ;
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
        setKey(PCTPickingResult.USER_ID, values, true) ;
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
        setKey(PCTPickingResult.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(PCTPickingResult.USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
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
    public void setUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.USER_ID, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名称(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(PCTPickingResult.USER_NAME, value) ;
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
        setKey(PCTPickingResult.USER_NAME, values, true) ;
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
        setKey(PCTPickingResult.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(PCTPickingResult.USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
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
    public void setUserName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名称(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名称(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.USER_NAME, value) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
     * 
     * @param value ランク(<code>RANK</code>)<br>
     * 文字列の検索値をランク(<code>RANK</code>)にセットします。
     */
    public void setRank(String value)
    {
        setKey(PCTPickingResult.RANK, value) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRank(String[] values)
    {
        setKey(PCTPickingResult.RANK, values, true) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRank(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.RANK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRank(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.RANK, values, and_or_toNext) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRank(String value, String compcode)
    {
        setKey(PCTPickingResult.RANK, value, compcode, "", "", true) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
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
    public void setRank(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.RANK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランク(<code>RANK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRank(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.RANK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ランク(<code>RANK</code>)の更新値をセットします。
     * @param value ランク(<code>RANK</code>)更新値
     */
    public void updateRank(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.RANK, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No.(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(PCTPickingResult.TERMINAL_NO, value) ;
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
        setKey(PCTPickingResult.TERMINAL_NO, values, true) ;
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
        setKey(PCTPickingResult.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(PCTPickingResult.TERMINAL_NO, value, compcode, "", "", true) ;
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
        setKey(PCTPickingResult.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTPickingResult.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No.(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No.(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.TERMINAL_NO, value) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値を作業日(<code>WORK_DAY</code>)にセットします。
     */
    public void setWorkDay(String value)
    {
        setKey(PCTPickingResult.WORK_DAY, value) ;
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
        setKey(PCTPickingResult.WORK_DAY, values, true) ;
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
        setKey(PCTPickingResult.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(PCTPickingResult.WORK_DAY, value, compcode, "", "", true) ;
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
        setKey(PCTPickingResult.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTPickingResult.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業日(<code>WORK_DAY</code>)の更新値をセットします。
     * @param value 作業日(<code>WORK_DAY</code>)更新値
     */
    public void updateWorkDay(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.WORK_DAY, value) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
     * 
     * @param value 曜日(<code>DAY_OF_WEEK</code>)<br>
     * 数値の検索値を曜日(<code>DAY_OF_WEEK</code>)にセットします。
     */
    public void setDayOfWeek(int value)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, HandlerUtil.toObject(value)) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDayOfWeek(int[] values)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDayOfWeek(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDayOfWeek(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDayOfWeek(int value, String compcode)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
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
    public void setDayOfWeek(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDayOfWeek(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.DAY_OF_WEEK, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 曜日(<code>DAY_OF_WEEK</code>)の更新値をセットします。
     * @param value 曜日(<code>DAY_OF_WEEK</code>)更新値
     */
    public void updateDayOfWeek(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.DAY_OF_WEEK, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(PCTPickingResult.CONSIGNOR_CODE, value) ;
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
        setKey(PCTPickingResult.CONSIGNOR_CODE, values, true) ;
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
        setKey(PCTPickingResult.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(PCTPickingResult.CONSIGNOR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
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
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)<br>
     * 文字列の検索値を荷主名称(<code>CONSIGNOR_NAME</code>)にセットします。
     */
    public void setConsignorName(String value)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, value) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorName(String[] values)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, values, true) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorName(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorName(String value, String compcode)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
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
    public void setConsignorName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setConsignorName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の更新値をセットします。
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)更新値
     */
    public void updateConsignorName(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.CONSIGNOR_NAME, value) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をエリアNo.(<code>AREA_NO</code>)にセットします。
     */
    public void setAreaNo(String value)
    {
        setKey(PCTPickingResult.AREA_NO, value) ;
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
        setKey(PCTPickingResult.AREA_NO, values, true) ;
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
        setKey(PCTPickingResult.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(PCTPickingResult.AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
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
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリアNo.(<code>AREA_NO</code>)の更新値をセットします。
     * @param value エリアNo.(<code>AREA_NO</code>)更新値
     */
    public void updateAreaNo(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.AREA_NO, value) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value エリア名称(<code>AREA_NAME</code>)<br>
     * 文字列の検索値をエリア名称(<code>AREA_NAME</code>)にセットします。
     */
    public void setAreaName(String value)
    {
        setKey(PCTPickingResult.AREA_NAME, value) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaName(String[] values)
    {
        setKey(PCTPickingResult.AREA_NAME, values, true) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaName(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NAME, values, and_or_toNext) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaName(String value, String compcode)
    {
        setKey(PCTPickingResult.AREA_NAME, value, compcode, "", "", true) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
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
    public void setAreaName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAreaName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.AREA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリア名称(<code>AREA_NAME</code>)の更新値をセットします。
     * @param value エリア名称(<code>AREA_NAME</code>)更新値
     */
    public void updateAreaName(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.AREA_NAME, value) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value バッチNo.(<code>BATCH_NO</code>)<br>
     * 文字列の検索値をバッチNo.(<code>BATCH_NO</code>)にセットします。
     */
    public void setBatchNo(String value)
    {
        setKey(PCTPickingResult.BATCH_NO, value) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNo(String[] values)
    {
        setKey(PCTPickingResult.BATCH_NO, values, true) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNo(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.BATCH_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBatchNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BATCH_NO, values, and_or_toNext) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBatchNo(String value, String compcode)
    {
        setKey(PCTPickingResult.BATCH_NO, value, compcode, "", "", true) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
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
    public void setBatchNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.BATCH_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBatchNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BATCH_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バッチNo.(<code>BATCH_NO</code>)の更新値をセットします。
     * @param value バッチNo.(<code>BATCH_NO</code>)更新値
     */
    public void updateBatchNo(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.BATCH_NO, value) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
     * 
     * @param value 作業開始日時(<code>START_TIME</code>)<br>
     * 日付の検索値を作業開始日時(<code>START_TIME</code>)にセットします。
     */
    public void setStartTime(Date value)
    {
        setKey(PCTPickingResult.START_TIME, value) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartTime(Date[] values)
    {
        setKey(PCTPickingResult.START_TIME, values, true) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartTime(Date[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.START_TIME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartTime(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.START_TIME, values, and_or_toNext) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartTime(Date value, String compcode)
    {
        setKey(PCTPickingResult.START_TIME, value, compcode, "", "", true) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
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
    public void setStartTime(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.START_TIME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業開始日時(<code>START_TIME</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartTime(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.START_TIME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業開始日時(<code>START_TIME</code>)の更新値をセットします。
     * @param value 作業開始日時(<code>START_TIME</code>)更新値
     */
    public void updateStartTime(Date value)
    {
        setAdhocUpdateValue(PCTPickingResult.START_TIME, value) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
     * 
     * @param value 作業終了日時(<code>END_TIME</code>)<br>
     * 日付の検索値を作業終了日時(<code>END_TIME</code>)にセットします。
     */
    public void setEndTime(Date value)
    {
        setKey(PCTPickingResult.END_TIME, value) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndTime(Date[] values)
    {
        setKey(PCTPickingResult.END_TIME, values, true) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndTime(Date[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.END_TIME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndTime(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.END_TIME, values, and_or_toNext) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndTime(Date value, String compcode)
    {
        setKey(PCTPickingResult.END_TIME, value, compcode, "", "", true) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
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
    public void setEndTime(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.END_TIME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndTime(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.END_TIME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業終了日時(<code>END_TIME</code>)の更新値をセットします。
     * @param value 作業終了日時(<code>END_TIME</code>)更新値
     */
    public void updateEndTime(Date value)
    {
        setAdhocUpdateValue(PCTPickingResult.END_TIME, value) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
     * 
     * @param value 稼働時間(<code>OPERATE_TIME</code>)<br>
     * 数値の検索値を稼働時間(<code>OPERATE_TIME</code>)にセットします。
     */
    public void setOperateTime(int value)
    {
        setKey(PCTPickingResult.OPERATE_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperateTime(int[] values)
    {
        setKey(PCTPickingResult.OPERATE_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperateTime(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.OPERATE_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOperateTime(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.OPERATE_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOperateTime(int value, String compcode)
    {
        setKey(PCTPickingResult.OPERATE_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
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
    public void setOperateTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.OPERATE_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOperateTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.OPERATE_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 稼働時間(<code>OPERATE_TIME</code>)の更新値をセットします。
     * @param value 稼働時間(<code>OPERATE_TIME</code>)更新値
     */
    public void updateOperateTime(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.OPERATE_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 作業数量(<code>WORK_QTY</code>)<br>
     * 数値の検索値を作業数量(<code>WORK_QTY</code>)にセットします。
     */
    public void setWorkQty(int value)
    {
        setKey(PCTPickingResult.WORK_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTPickingResult.WORK_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTPickingResult.WORK_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkQty(int value, String compcode)
    {
        setKey(PCTPickingResult.WORK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
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
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業数量(<code>WORK_QTY</code>)の更新値をセットします。
     * @param value 作業数量(<code>WORK_QTY</code>)更新値
     */
    public void updateWorkQty(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.WORK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
     * 
     * @param value 作業数量(バラ)(<code>PIECE_QTY</code>)<br>
     * 数値の検索値を作業数量(バラ)(<code>PIECE_QTY</code>)にセットします。
     */
    public void setPieceQty(int value)
    {
        setKey(PCTPickingResult.PIECE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPieceQty(int[] values)
    {
        setKey(PCTPickingResult.PIECE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPieceQty(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.PIECE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPieceQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.PIECE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPieceQty(int value, String compcode)
    {
        setKey(PCTPickingResult.PIECE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
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
    public void setPieceQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.PIECE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPieceQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.PIECE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)の更新値をセットします。
     * @param value 作業数量(バラ)(<code>PIECE_QTY</code>)更新値
     */
    public void updatePieceQty(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.PIECE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数(明細数)(<code>WORK_CNT</code>)<br>
     * 数値の検索値を作業回数(明細数)(<code>WORK_CNT</code>)にセットします。
     */
    public void setWorkCnt(int value)
    {
        setKey(PCTPickingResult.WORK_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkCnt(int[] values)
    {
        setKey(PCTPickingResult.WORK_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
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
        setKey(PCTPickingResult.WORK_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkCnt(int value, String compcode)
    {
        setKey(PCTPickingResult.WORK_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
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
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)の更新値をセットします。
     * @param value 作業回数(明細数)(<code>WORK_CNT</code>)更新値
     */
    public void updateWorkCnt(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.WORK_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数(オーダー数)(<code>ORDER_CNT</code>)<br>
     * 数値の検索値を作業回数(オーダー数)(<code>ORDER_CNT</code>)にセットします。
     */
    public void setOrderCnt(int value)
    {
        setKey(PCTPickingResult.ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderCnt(int[] values)
    {
        setKey(PCTPickingResult.ORDER_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
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
        setKey(PCTPickingResult.ORDER_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.ORDER_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderCnt(int value, String compcode)
    {
        setKey(PCTPickingResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
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
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)の更新値をセットします。
     * @param value 作業回数(オーダー数)(<code>ORDER_CNT</code>)更新値
     */
    public void updateOrderCnt(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)<br>
     * 数値の検索値を作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)にセットします。
     */
    public void setPlanOrderCnt(int value)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanOrderCnt(int[] values)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanOrderCnt(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanOrderCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanOrderCnt(int value, String compcode)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
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
    public void setPlanOrderCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanOrderCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.PLAN_ORDER_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)の更新値をセットします。
     * @param value 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)更新値
     */
    public void updatePlanOrderCnt(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.PLAN_ORDER_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
     * 
     * @param value 集品箱数(<code>BOX_CNT</code>)<br>
     * 数値の検索値を集品箱数(<code>BOX_CNT</code>)にセットします。
     */
    public void setBoxCnt(int value)
    {
        setKey(PCTPickingResult.BOX_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBoxCnt(int[] values)
    {
        setKey(PCTPickingResult.BOX_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBoxCnt(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.BOX_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBoxCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BOX_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBoxCnt(int value, String compcode)
    {
        setKey(PCTPickingResult.BOX_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
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
    public void setBoxCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.BOX_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBoxCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BOX_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 集品箱数(<code>BOX_CNT</code>)の更新値をセットします。
     * @param value 集品箱数(<code>BOX_CNT</code>)更新値
     */
    public void updateBoxCnt(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.BOX_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 作業時間(<code>WORK_TIME</code>)<br>
     * 数値の検索値を作業時間(<code>WORK_TIME</code>)にセットします。
     */
    public void setWorkTime(int value)
    {
        setKey(PCTPickingResult.WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTime(int[] values)
    {
        setKey(PCTPickingResult.WORK_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
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
        setKey(PCTPickingResult.WORK_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkTime(int value, String compcode)
    {
        setKey(PCTPickingResult.WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
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
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業時間(<code>WORK_TIME</code>)の更新値をセットします。
     * @param value 作業時間(<code>WORK_TIME</code>)更新値
     */
    public void updateWorkTime(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 実作業時間(<code>REAL_WORK_TIME</code>)<br>
     * 数値の検索値を実作業時間(<code>REAL_WORK_TIME</code>)にセットします。
     */
    public void setRealWorkTime(int value)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRealWorkTime(int[] values)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
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
        setKey(PCTPickingResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRealWorkTime(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRealWorkTime(int value, String compcode)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
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
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRealWorkTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REAL_WORK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)の更新値をセットします。
     * @param value 実作業時間(<code>REAL_WORK_TIME</code>)更新値
     */
    public void updateRealWorkTime(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.REAL_WORK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value ミススキャン数(<code>MISS_SCAN_CNT</code>)<br>
     * 数値の検索値をミススキャン数(<code>MISS_SCAN_CNT</code>)にセットします。
     */
    public void setMissScanCnt(int value)
    {
        setKey(PCTPickingResult.MISS_SCAN_CNT, HandlerUtil.toObject(value)) ;
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
        setKey(PCTPickingResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTPickingResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMissScanCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.MISS_SCAN_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMissScanCnt(int value, String compcode)
    {
        setKey(PCTPickingResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
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
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMissScanCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.MISS_SCAN_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)の更新値をセットします。
     * @param value ミススキャン数(<code>MISS_SCAN_CNT</code>)更新値
     */
    public void updateMissScanCnt(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.MISS_SCAN_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
     * 
     * @param value バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)<br>
     * 数値の検索値をバッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)にセットします。
     */
    public void setBatteryChangeTime(int value)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatteryChangeTime(int[] values)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatteryChangeTime(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBatteryChangeTime(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBatteryChangeTime(int value, String compcode)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
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
    public void setBatteryChangeTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBatteryChangeTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BATTERY_CHANGE_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)の更新値をセットします。
     * @param value バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)更新値
     */
    public void updateBatteryChangeTime(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.BATTERY_CHANGE_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
     * 
     * @param value 休憩時間(<code>BREAK_TIME</code>)<br>
     * 数値の検索値を休憩時間(<code>BREAK_TIME</code>)にセットします。
     */
    public void setBreakTime(int value)
    {
        setKey(PCTPickingResult.BREAK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBreakTime(int[] values)
    {
        setKey(PCTPickingResult.BREAK_TIME, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBreakTime(int[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.BREAK_TIME, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBreakTime(int[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BREAK_TIME, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBreakTime(int value, String compcode)
    {
        setKey(PCTPickingResult.BREAK_TIME, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
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
    public void setBreakTime(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.BREAK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBreakTime(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.BREAK_TIME, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 休憩時間(<code>BREAK_TIME</code>)の更新値をセットします。
     * @param value 休憩時間(<code>BREAK_TIME</code>)更新値
     */
    public void updateBreakTime(int value)
    {
        setAdhocUpdateValue(PCTPickingResult.BREAK_TIME, HandlerUtil.toObject(value)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(PCTPickingResult.REGIST_DATE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values)
    {
        setKey(PCTPickingResult.REGIST_DATE, values, true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(PCTPickingResult.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
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
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(PCTPickingResult.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(PCTPickingResult.REGIST_PNAME, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values)
    {
        setKey(PCTPickingResult.REGIST_PNAME, values, true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(PCTPickingResult.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
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
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, values, true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(PCTPickingResult.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, values, true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values, String and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
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
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTPickingResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(PCTPickingResult.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateDayOfWeekWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.DAY_OF_WEEK, source, addvalue);
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOperateTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.OPERATE_TIME, source, addvalue);
    }

    /**
     * 作業数量(<code>WORK_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.WORK_QTY, source, addvalue);
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePieceQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.PIECE_QTY, source, addvalue);
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.WORK_CNT, source, addvalue);
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.ORDER_CNT, source, addvalue);
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePlanOrderCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.PLAN_ORDER_CNT, source, addvalue);
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBoxCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.BOX_CNT, source, addvalue);
    }

    /**
     * 作業時間(<code>WORK_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.WORK_TIME, source, addvalue);
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRealWorkTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.REAL_WORK_TIME, source, addvalue);
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMissScanCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.MISS_SCAN_CNT, source, addvalue);
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBatteryChangeTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.BATTERY_CHANGE_TIME, source, addvalue);
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBreakTimeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTPickingResult.BREAK_TIME, source, addvalue);
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
        return "$Id: PCTPickingResultAlterKey.java 5740 2009-11-12 13:29:27Z kumano $" ;
    }
}
