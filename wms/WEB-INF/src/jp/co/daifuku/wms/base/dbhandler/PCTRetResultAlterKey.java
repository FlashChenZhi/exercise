// $Id: PCTRetResultAlterKey.java 3213 2009-03-02 06:59:20Z arai $
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
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTRETRESULT用の更新キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTRetResultAlterKey
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
    public static final StoreMetaData $storeMetaData = PCTRetResult.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PCTRetResultAlterKey()
    {
        super(PCTRetResult.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値を作業日(<code>WORK_DAY</code>)にセットします。
     */
    public void setWorkDay(String value)
    {
        setKey(PCTRetResult.WORK_DAY, value) ;
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
        setKey(PCTRetResult.WORK_DAY, values, true) ;
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
        setKey(PCTRetResult.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(PCTRetResult.WORK_DAY, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業日(<code>WORK_DAY</code>)の更新値をセットします。
     * @param value 作業日(<code>WORK_DAY</code>)更新値
     */
    public void updateWorkDay(String value)
    {
        setAdhocUpdateValue(PCTRetResult.WORK_DAY, value) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>JOB_NO</code>)<br>
     * 文字列の検索値を作業No.(<code>JOB_NO</code>)にセットします。
     */
    public void setJobNo(String value)
    {
        setKey(PCTRetResult.JOB_NO, value) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobNo(String[] values)
    {
        setKey(PCTRetResult.JOB_NO, values, true) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobNo(String value, String compcode)
    {
        setKey(PCTRetResult.JOB_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
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
    public void setJobNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setJobNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業No.(<code>JOB_NO</code>)の更新値をセットします。
     * @param value 作業No.(<code>JOB_NO</code>)更新値
     */
    public void updateJobNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.JOB_NO, value) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)<br>
     * 文字列の検索値を設定単位キー(<code>SETTING_UNIT_KEY</code>)にセットします。
     */
    public void setSettingUnitKey(String value)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, value) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKey(String[] values)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, values, true) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKey(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSettingUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSettingUnitKey(String value, String compcode)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
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
    public void setSettingUnitKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSettingUnitKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の更新値をセットします。
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)更新値
     */
    public void updateSettingUnitKey(String value)
    {
        setAdhocUpdateValue(PCTRetResult.SETTING_UNIT_KEY, value) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 集約作業No(<code>COLLECT_JOB_NO</code>)<br>
     * 文字列の検索値を集約作業No(<code>COLLECT_JOB_NO</code>)にセットします。
     */
    public void setCollectJobNo(String value)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, value) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNo(String[] values)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, values, true) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCollectJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCollectJobNo(String value, String compcode)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, value, compcode, "", "", true) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
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
    public void setCollectJobNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCollectJobNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>)の更新値をセットします。
     * @param value 集約作業No(<code>COLLECT_JOB_NO</code>)更新値
     */
    public void updateCollectJobNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.COLLECT_JOB_NO, value) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を状態フラグ(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(PCTRetResult.STATUS_FLAG, value) ;
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
        setKey(PCTRetResult.STATUS_FLAG, values, true) ;
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
        setKey(PCTRetResult.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(PCTRetResult.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(PCTRetResult.STATUS_FLAG, value) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)<br>
     * 文字列の検索値をハードウェア区分(<code>HARDWARE_TYPE</code>)にセットします。
     */
    public void setHardwareType(String value)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, value) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareType(String[] values)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, values, true) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareType(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHardwareType(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHardwareType(String value, String compcode)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
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
    public void setHardwareType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setHardwareType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.HARDWARE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の更新値をセットします。
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)更新値
     */
    public void updateHardwareType(String value)
    {
        setAdhocUpdateValue(PCTRetResult.HARDWARE_TYPE, value) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)<br>
     * 文字列の検索値を予定一意キー(<code>PLAN_UKEY</code>)にセットします。
     */
    public void setPlanUkey(String value)
    {
        setKey(PCTRetResult.PLAN_UKEY, value) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values)
    {
        setKey(PCTRetResult.PLAN_UKEY, values, true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanUkey(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_UKEY, values, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanUkey(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_UKEY, value, compcode, "", "", true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
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
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の更新値をセットします。
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)更新値
     */
    public void updatePlanUkey(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_UKEY, value) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 在庫ID(<code>STOCK_ID</code>)<br>
     * 文字列の検索値を在庫ID(<code>STOCK_ID</code>)にセットします。
     */
    public void setStockId(String value)
    {
        setKey(PCTRetResult.STOCK_ID, value) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockId(String[] values)
    {
        setKey(PCTRetResult.STOCK_ID, values, true) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockId(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.STOCK_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockId(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.STOCK_ID, values, and_or_toNext) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockId(String value, String compcode)
    {
        setKey(PCTRetResult.STOCK_ID, value, compcode, "", "", true) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
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
    public void setStockId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.STOCK_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStockId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.STOCK_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫ID(<code>STOCK_ID</code>)の更新値をセットします。
     * @param value 在庫ID(<code>STOCK_ID</code>)更新値
     */
    public void updateStockId(String value)
    {
        setAdhocUpdateValue(PCTRetResult.STOCK_ID, value) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value システム接続キー(<code>SYSTEM_CONN_KEY</code>)<br>
     * 文字列の検索値をシステム接続キー(<code>SYSTEM_CONN_KEY</code>)にセットします。
     */
    public void setSystemConnKey(String value)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, value) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSystemConnKey(String[] values)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, values, true) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSystemConnKey(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSystemConnKey(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, values, and_or_toNext) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSystemConnKey(String value, String compcode)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, value, compcode, "", "", true) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
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
    public void setSystemConnKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSystemConnKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SYSTEM_CONN_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>)の更新値をセットします。
     * @param value システム接続キー(<code>SYSTEM_CONN_KEY</code>)更新値
     */
    public void updateSystemConnKey(String value)
    {
        setAdhocUpdateValue(PCTRetResult.SYSTEM_CONN_KEY, value) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 予定日(<code>PLAN_DAY</code>)<br>
     * 文字列の検索値を予定日(<code>PLAN_DAY</code>)にセットします。
     */
    public void setPlanDay(String value)
    {
        setKey(PCTRetResult.PLAN_DAY, value) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values)
    {
        setKey(PCTRetResult.PLAN_DAY, values, true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanDay(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_DAY, values, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanDay(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
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
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定日(<code>PLAN_DAY</code>)の更新値をセットします。
     * @param value 予定日(<code>PLAN_DAY</code>)更新値
     */
    public void updatePlanDay(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_DAY, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(PCTRetResult.CONSIGNOR_CODE, value) ;
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
        setKey(PCTRetResult.CONSIGNOR_CODE, values, true) ;
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
        setKey(PCTRetResult.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(PCTRetResult.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(PCTRetResult.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)<br>
     * 文字列の検索値を荷主名称(<code>CONSIGNOR_NAME</code>)にセットします。
     */
    public void setConsignorName(String value)
    {
        setKey(PCTRetResult.CONSIGNOR_NAME, value) ;
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
        setKey(PCTRetResult.CONSIGNOR_NAME, values, true) ;
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
        setKey(PCTRetResult.CONSIGNOR_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CONSIGNOR_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorName(String value, String compcode)
    {
        setKey(PCTRetResult.CONSIGNOR_NAME, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の更新値をセットします。
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)更新値
     */
    public void updateConsignorName(String value)
    {
        setAdhocUpdateValue(PCTRetResult.CONSIGNOR_NAME, value) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)<br>
     * 文字列の検索値を得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)にセットします。
     */
    public void setRegularCustomerCode(String value)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, value) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegularCustomerCode(String[] values)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, values, true) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegularCustomerCode(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegularCustomerCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, values, and_or_toNext) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegularCustomerCode(String value, String compcode)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
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
    public void setRegularCustomerCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegularCustomerCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)の更新値をセットします。
     * @param value 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)更新値
     */
    public void updateRegularCustomerCode(String value)
    {
        setAdhocUpdateValue(PCTRetResult.REGULAR_CUSTOMER_CODE, value) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)<br>
     * 文字列の検索値を得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)にセットします。
     */
    public void setRegularCustomerName(String value)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, value) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegularCustomerName(String[] values)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, values, true) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegularCustomerName(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegularCustomerName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, values, and_or_toNext) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegularCustomerName(String value, String compcode)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
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
    public void setRegularCustomerName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegularCustomerName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGULAR_CUSTOMER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)の更新値をセットします。
     * @param value 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)更新値
     */
    public void updateRegularCustomerName(String value)
    {
        setAdhocUpdateValue(PCTRetResult.REGULAR_CUSTOMER_NAME, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)<br>
     * 文字列の検索値を出荷先コード(<code>CUSTOMER_CODE</code>)にセットします。
     */
    public void setCustomerCode(String value)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, values, true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCustomerCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, values, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCustomerCode(String value, String compcode)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
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
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の更新値をセットします。
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)更新値
     */
    public void updateCustomerCode(String value)
    {
        setAdhocUpdateValue(PCTRetResult.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 出荷先名称(<code>CUSTOMER_NAME</code>)<br>
     * 文字列の検索値を出荷先名称(<code>CUSTOMER_NAME</code>)にセットします。
     */
    public void setCustomerName(String value)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, value) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, values, true) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCustomerName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, values, and_or_toNext) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCustomerName(String value, String compcode)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
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
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>)の更新値をセットします。
     * @param value 出荷先名称(<code>CUSTOMER_NAME</code>)更新値
     */
    public void updateCustomerName(String value)
    {
        setAdhocUpdateValue(PCTRetResult.CUSTOMER_NAME, value) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
     * 
     * @param value 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)<br>
     * 文字列の検索値を出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)にセットします。
     */
    public void setCustomerCategory(String value)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, value) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCategory(String[] values)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, values, true) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCategory(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCustomerCategory(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, values, and_or_toNext) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCustomerCategory(String value, String compcode)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
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
    public void setCustomerCategory(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCustomerCategory(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.CUSTOMER_CATEGORY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)の更新値をセットします。
     * @param value 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)更新値
     */
    public void updateCustomerCategory(String value)
    {
        setAdhocUpdateValue(PCTRetResult.CUSTOMER_CATEGORY, value) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷伝票No(<code>SHIP_TICKET_NO</code>)<br>
     * 文字列の検索値を出荷伝票No(<code>SHIP_TICKET_NO</code>)にセットします。
     */
    public void setShipTicketNo(String value)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, value) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNo(String[] values)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, values, true) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShipTicketNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, values, and_or_toNext) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShipTicketNo(String value, String compcode)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, value, compcode, "", "", true) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
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
    public void setShipTicketNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShipTicketNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_TICKET_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>)の更新値をセットします。
     * @param value 出荷伝票No(<code>SHIP_TICKET_NO</code>)更新値
     */
    public void updateShipTicketNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.SHIP_TICKET_NO, value) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷伝票行(<code>SHIP_LINE_NO</code>)<br>
     * 数値の検索値を出荷伝票行(<code>SHIP_LINE_NO</code>)にセットします。
     */
    public void setShipLineNo(int value)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNo(int[] values)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNo(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShipLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShipLineNo(int value, String compcode)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
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
    public void setShipLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShipLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)の更新値をセットします。
     * @param value 出荷伝票行(<code>SHIP_LINE_NO</code>)更新値
     */
    public void updateShipLineNo(int value)
    {
        setAdhocUpdateValue(PCTRetResult.SHIP_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)<br>
     * 数値の検索値を出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)にセットします。
     */
    public void setShipBranchNo(int value)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipBranchNo(int[] values)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipBranchNo(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShipBranchNo(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShipBranchNo(int value, String compcode)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
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
    public void setShipBranchNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShipBranchNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHIP_BRANCH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)の更新値をセットします。
     * @param value 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)更新値
     */
    public void updateShipBranchNo(int value)
    {
        setAdhocUpdateValue(PCTRetResult.SHIP_BRANCH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value バッチNo.(<code>BATCH_NO</code>)<br>
     * 文字列の検索値をバッチNo.(<code>BATCH_NO</code>)にセットします。
     */
    public void setBatchNo(String value)
    {
        setKey(PCTRetResult.BATCH_NO, value) ;
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
        setKey(PCTRetResult.BATCH_NO, values, true) ;
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
        setKey(PCTRetResult.BATCH_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBatchNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BATCH_NO, values, and_or_toNext) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBatchNo(String value, String compcode)
    {
        setKey(PCTRetResult.BATCH_NO, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.BATCH_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.BATCH_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バッチNo.(<code>BATCH_NO</code>)の更新値をセットします。
     * @param value バッチNo.(<code>BATCH_NO</code>)更新値
     */
    public void updateBatchNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.BATCH_NO, value) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
     * 
     * @param value バッチSeqNo.(<code>BATCH_SEQ_NO</code>)<br>
     * 文字列の検索値をバッチSeqNo.(<code>BATCH_SEQ_NO</code>)にセットします。
     */
    public void setBatchSeqNo(String value)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, value) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchSeqNo(String[] values)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, values, true) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchSeqNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBatchSeqNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, values, and_or_toNext) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBatchSeqNo(String value, String compcode)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, value, compcode, "", "", true) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
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
    public void setBatchSeqNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBatchSeqNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BATCH_SEQ_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>)の更新値をセットします。
     * @param value バッチSeqNo.(<code>BATCH_SEQ_NO</code>)更新値
     */
    public void updateBatchSeqNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.BATCH_SEQ_NO, value) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value オーダNo.(<code>ORDER_NO</code>)<br>
     * 文字列の検索値をオーダNo.(<code>ORDER_NO</code>)にセットします。
     */
    public void setOrderNo(String value)
    {
        setKey(PCTRetResult.ORDER_NO, value) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderNo(String[] values)
    {
        setKey(PCTRetResult.ORDER_NO, values, true) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_NO, values, and_or_toNext) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderNo(String value, String compcode)
    {
        setKey(PCTRetResult.ORDER_NO, value, compcode, "", "", true) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
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
    public void setOrderNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダNo.(<code>ORDER_NO</code>)の更新値をセットします。
     * @param value オーダNo.(<code>ORDER_NO</code>)更新値
     */
    public void updateOrderNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_NO, value) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
     * 
     * @param value オーダSeq(<code>ORDER_SEQ</code>)<br>
     * 文字列の検索値をオーダSeq(<code>ORDER_SEQ</code>)にセットします。
     */
    public void setOrderSeq(String value)
    {
        setKey(PCTRetResult.ORDER_SEQ, value) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderSeq(String[] values)
    {
        setKey(PCTRetResult.ORDER_SEQ, values, true) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderSeq(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_SEQ, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderSeq(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_SEQ, values, and_or_toNext) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderSeq(String value, String compcode)
    {
        setKey(PCTRetResult.ORDER_SEQ, value, compcode, "", "", true) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
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
    public void setOrderSeq(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_SEQ, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderSeq(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_SEQ, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダSeq(<code>ORDER_SEQ</code>)の更新値をセットします。
     * @param value オーダSeq(<code>ORDER_SEQ</code>)更新値
     */
    public void updateOrderSeq(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_SEQ, value) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
     * 
     * @param value オーダ情報コメント(<code>ORDER_INFO</code>)<br>
     * 文字列の検索値をオーダ情報コメント(<code>ORDER_INFO</code>)にセットします。
     */
    public void setOrderInfo(String value)
    {
        setKey(PCTRetResult.ORDER_INFO, value) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderInfo(String[] values)
    {
        setKey(PCTRetResult.ORDER_INFO, values, true) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderInfo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_INFO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderInfo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_INFO, values, and_or_toNext) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderInfo(String value, String compcode)
    {
        setKey(PCTRetResult.ORDER_INFO, value, compcode, "", "", true) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
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
    public void setOrderInfo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_INFO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderInfo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_INFO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>)の更新値をセットします。
     * @param value オーダ情報コメント(<code>ORDER_INFO</code>)更新値
     */
    public void updateOrderInfo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_INFO, value) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 予定オーダNo.(<code>PLAN_ORDER_NO</code>)<br>
     * 文字列の検索値を予定オーダNo.(<code>PLAN_ORDER_NO</code>)にセットします。
     */
    public void setPlanOrderNo(String value)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, value) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanOrderNo(String[] values)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, values, true) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanOrderNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanOrderNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, values, and_or_toNext) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanOrderNo(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
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
    public void setPlanOrderNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanOrderNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ORDER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>)の更新値をセットします。
     * @param value 予定オーダNo.(<code>PLAN_ORDER_NO</code>)更新値
     */
    public void updatePlanOrderNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_ORDER_NO, value) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 実績オーダNo.(<code>RESULT_ORDER_NO</code>)<br>
     * 文字列の検索値を実績オーダNo.(<code>RESULT_ORDER_NO</code>)にセットします。
     */
    public void setResultOrderNo(String value)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, value) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultOrderNo(String[] values)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, values, true) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultOrderNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setResultOrderNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, values, and_or_toNext) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setResultOrderNo(String value, String compcode)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, value, compcode, "", "", true) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
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
    public void setResultOrderNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setResultOrderNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_ORDER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>)の更新値をセットします。
     * @param value 実績オーダNo.(<code>RESULT_ORDER_NO</code>)更新値
     */
    public void updateResultOrderNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.RESULT_ORDER_NO, value) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value 通番(<code>THROUGH_NO</code>)<br>
     * 数値の検索値を通番(<code>THROUGH_NO</code>)にセットします。
     */
    public void setThroughNo(int value)
    {
        setKey(PCTRetResult.THROUGH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setThroughNo(int[] values)
    {
        setKey(PCTRetResult.THROUGH_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setThroughNo(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.THROUGH_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setThroughNo(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.THROUGH_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setThroughNo(int value, String compcode)
    {
        setKey(PCTRetResult.THROUGH_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
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
    public void setThroughNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.THROUGH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通番(<code>THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setThroughNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.THROUGH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 通番(<code>THROUGH_NO</code>)の更新値をセットします。
     * @param value 通番(<code>THROUGH_NO</code>)更新値
     */
    public void updateThroughNo(int value)
    {
        setAdhocUpdateValue(PCTRetResult.THROUGH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
     * 
     * @param value オーダー内商品数(<code>ORDER_ITEM_QTY</code>)<br>
     * 数値の検索値をオーダー内商品数(<code>ORDER_ITEM_QTY</code>)にセットします。
     */
    public void setOrderItemQty(int value)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderItemQty(int[] values)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderItemQty(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderItemQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderItemQty(int value, String compcode)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
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
    public void setOrderItemQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderItemQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_ITEM_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)の更新値をセットします。
     * @param value オーダー内商品数(<code>ORDER_ITEM_QTY</code>)更新値
     */
    public void updateOrderItemQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_ITEM_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value オーダー通番(<code>ORDER_THROUGH_NO</code>)<br>
     * 数値の検索値をオーダー通番(<code>ORDER_THROUGH_NO</code>)にセットします。
     */
    public void setOrderThroughNo(int value)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderThroughNo(int[] values)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderThroughNo(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderThroughNo(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderThroughNo(int value, String compcode)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
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
    public void setOrderThroughNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderThroughNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)の更新値をセットします。
     * @param value オーダー通番(<code>ORDER_THROUGH_NO</code>)更新値
     */
    public void updateOrderThroughNo(int value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_THROUGH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
     * 
     * @param value オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)<br>
     * 数値の検索値をオーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)にセットします。
     */
    public void setOrderThroughNoCnt(int value)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderThroughNoCnt(int[] values)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderThroughNoCnt(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderThroughNoCnt(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderThroughNoCnt(int value, String compcode)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
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
    public void setOrderThroughNoCnt(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderThroughNoCnt(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)の更新値をセットします。
     * @param value オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)更新値
     */
    public void updateOrderThroughNoCnt(int value)
    {
        setAdhocUpdateValue(PCTRetResult.ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
     * 
     * @param value 汎用フラグ(<code>GENERAL_FLAG</code>)<br>
     * 文字列の検索値を汎用フラグ(<code>GENERAL_FLAG</code>)にセットします。
     */
    public void setGeneralFlag(String value)
    {
        setKey(PCTRetResult.GENERAL_FLAG, value) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGeneralFlag(String[] values)
    {
        setKey(PCTRetResult.GENERAL_FLAG, values, true) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGeneralFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.GENERAL_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setGeneralFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.GENERAL_FLAG, values, and_or_toNext) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setGeneralFlag(String value, String compcode)
    {
        setKey(PCTRetResult.GENERAL_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
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
    public void setGeneralFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.GENERAL_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setGeneralFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.GENERAL_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>)の更新値をセットします。
     * @param value 汎用フラグ(<code>GENERAL_FLAG</code>)更新値
     */
    public void updateGeneralFlag(String value)
    {
        setAdhocUpdateValue(PCTRetResult.GENERAL_FLAG, value) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
     * 
     * @param value シュートNo.(<code>SHOOT_NO</code>)<br>
     * 文字列の検索値をシュートNo.(<code>SHOOT_NO</code>)にセットします。
     */
    public void setShootNo(String value)
    {
        setKey(PCTRetResult.SHOOT_NO, value) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShootNo(String[] values)
    {
        setKey(PCTRetResult.SHOOT_NO, values, true) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShootNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SHOOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShootNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHOOT_NO, values, and_or_toNext) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShootNo(String value, String compcode)
    {
        setKey(PCTRetResult.SHOOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
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
    public void setShootNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SHOOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShootNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHOOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * シュートNo.(<code>SHOOT_NO</code>)の更新値をセットします。
     * @param value シュートNo.(<code>SHOOT_NO</code>)更新値
     */
    public void updateShootNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.SHOOT_NO, value) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 予定エリア(<code>PLAN_AREA_NO</code>)<br>
     * 文字列の検索値を予定エリア(<code>PLAN_AREA_NO</code>)にセットします。
     */
    public void setPlanAreaNo(String value)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, value) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, values, true) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanAreaNo(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
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
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>)の更新値をセットします。
     * @param value 予定エリア(<code>PLAN_AREA_NO</code>)更新値
     */
    public void updatePlanAreaNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_AREA_NO, value) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ゾーン(<code>PLAN_ZONE_NO</code>)<br>
     * 文字列の検索値を予定ゾーン(<code>PLAN_ZONE_NO</code>)にセットします。
     */
    public void setPlanZoneNo(String value)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, value) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanZoneNo(String[] values)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, values, true) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanZoneNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanZoneNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanZoneNo(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
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
    public void setPlanZoneNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanZoneNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_ZONE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>)の更新値をセットします。
     * @param value 予定ゾーン(<code>PLAN_ZONE_NO</code>)更新値
     */
    public void updatePlanZoneNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_ZONE_NO, value) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 作業ゾーン(<code>WORK_ZONE_NO</code>)<br>
     * 文字列の検索値を作業ゾーン(<code>WORK_ZONE_NO</code>)にセットします。
     */
    public void setWorkZoneNo(String value)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, value) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkZoneNo(String[] values)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, values, true) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkZoneNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkZoneNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, values, and_or_toNext) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkZoneNo(String value, String compcode)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
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
    public void setWorkZoneNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkZoneNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.WORK_ZONE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>)の更新値をセットします。
     * @param value 作業ゾーン(<code>WORK_ZONE_NO</code>)更新値
     */
    public void updateWorkZoneNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.WORK_ZONE_NO, value) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 予定棚(<code>PLAN_LOCATION_NO</code>)<br>
     * 文字列の検索値を予定棚(<code>PLAN_LOCATION_NO</code>)にセットします。
     */
    public void setPlanLocationNo(String value)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, value) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, values, true) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanLocationNo(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
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
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>)の更新値をセットします。
     * @param value 予定棚(<code>PLAN_LOCATION_NO</code>)更新値
     */
    public void updatePlanLocationNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_LOCATION_NO, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値を商品コード(<code>ITEM_CODE</code>)にセットします。
     */
    public void setItemCode(String value)
    {
        setKey(PCTRetResult.ITEM_CODE, value) ;
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
        setKey(PCTRetResult.ITEM_CODE, values, true) ;
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
        setKey(PCTRetResult.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(PCTRetResult.ITEM_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
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
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品コード(<code>ITEM_CODE</code>)の更新値をセットします。
     * @param value 商品コード(<code>ITEM_CODE</code>)更新値
     */
    public void updateItemCode(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ITEM_CODE, value) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 商品名称(<code>ITEM_NAME</code>)<br>
     * 文字列の検索値を商品名称(<code>ITEM_NAME</code>)にセットします。
     */
    public void setItemName(String value)
    {
        setKey(PCTRetResult.ITEM_NAME, value) ;
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
        setKey(PCTRetResult.ITEM_NAME, values, true) ;
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
        setKey(PCTRetResult.ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemName(String value, String compcode)
    {
        setKey(PCTRetResult.ITEM_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
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
    public void setItemName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItemName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品名称(<code>ITEM_NAME</code>)の更新値をセットします。
     * @param value 商品名称(<code>ITEM_NAME</code>)更新値
     */
    public void updateItemName(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ITEM_NAME, value) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ケース入数(<code>ENTERING_QTY</code>)<br>
     * 数値の検索値をケース入数(<code>ENTERING_QTY</code>)にセットします。
     */
    public void setEnteringQty(int value)
    {
        setKey(PCTRetResult.ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTRetResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTRetResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty(int value, String compcode)
    {
        setKey(PCTRetResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
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
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケース入数(<code>ENTERING_QTY</code>)の更新値をセットします。
     * @param value ケース入数(<code>ENTERING_QTY</code>)更新値
     */
    public void updateEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)<br>
     * 数値の検索値をボール入数(<code>BUNDLE_ENTERING_QTY</code>)にセットします。
     */
    public void setBundleEnteringQty(int value)
    {
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBundleEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBundleEnteringQty(int value, String compcode)
    {
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
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
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の更新値をセットします。
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)更新値
     */
    public void updateBundleEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ロット入数(<code>LOT_ENTERING_QTY</code>)<br>
     * 数値の検索値をロット入数(<code>LOT_ENTERING_QTY</code>)にセットします。
     */
    public void setLotEnteringQty(int value)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotEnteringQty(int[] values)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLotEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLotEnteringQty(int value, String compcode)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
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
    public void setLotEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLotEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の更新値をセットします。
     * @param value ロット入数(<code>LOT_ENTERING_QTY</code>)更新値
     */
    public void updateLotEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.LOT_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value JANコード(<code>JAN</code>)<br>
     * 文字列の検索値をJANコード(<code>JAN</code>)にセットします。
     */
    public void setJan(String value)
    {
        setKey(PCTRetResult.JAN, value) ;
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
        setKey(PCTRetResult.JAN, values, true) ;
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
        setKey(PCTRetResult.JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJan(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.JAN, values, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJan(String value, String compcode)
    {
        setKey(PCTRetResult.JAN, value, compcode, "", "", true) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
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
    public void setJan(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setJan(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * JANコード(<code>JAN</code>)の更新値をセットします。
     * @param value JANコード(<code>JAN</code>)更新値
     */
    public void updateJan(String value)
    {
        setAdhocUpdateValue(PCTRetResult.JAN, value) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value ケースITF(<code>ITF</code>)<br>
     * 文字列の検索値をケースITF(<code>ITF</code>)にセットします。
     */
    public void setItf(String value)
    {
        setKey(PCTRetResult.ITF, value) ;
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
        setKey(PCTRetResult.ITF, values, true) ;
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
        setKey(PCTRetResult.ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItf(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITF, values, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItf(String value, String compcode)
    {
        setKey(PCTRetResult.ITF, value, compcode, "", "", true) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
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
    public void setItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケースITF(<code>ITF</code>)の更新値をセットします。
     * @param value ケースITF(<code>ITF</code>)更新値
     */
    public void updateItf(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ITF, value) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value ボールITF(<code>BUNDLE_ITF</code>)<br>
     * 文字列の検索値をボールITF(<code>BUNDLE_ITF</code>)にセットします。
     */
    public void setBundleItf(String value)
    {
        setKey(PCTRetResult.BUNDLE_ITF, value) ;
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
        setKey(PCTRetResult.BUNDLE_ITF, values, true) ;
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
        setKey(PCTRetResult.BUNDLE_ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBundleItf(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ITF, values, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBundleItf(String value, String compcode)
    {
        setKey(PCTRetResult.BUNDLE_ITF, value, compcode, "", "", true) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
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
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.BUNDLE_ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の更新値をセットします。
     * @param value ボールITF(<code>BUNDLE_ITF</code>)更新値
     */
    public void updateBundleItf(String value)
    {
        setAdhocUpdateValue(PCTRetResult.BUNDLE_ITF, value) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 基準日付(<code>USE_BY_DATE</code>)<br>
     * 文字列の検索値を基準日付(<code>USE_BY_DATE</code>)にセットします。
     */
    public void setUseByDate(String value)
    {
        setKey(PCTRetResult.USE_BY_DATE, value) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUseByDate(String[] values)
    {
        setKey(PCTRetResult.USE_BY_DATE, values, true) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUseByDate(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.USE_BY_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUseByDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.USE_BY_DATE, values, and_or_toNext) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUseByDate(String value, String compcode)
    {
        setKey(PCTRetResult.USE_BY_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
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
    public void setUseByDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.USE_BY_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUseByDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.USE_BY_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 基準日付(<code>USE_BY_DATE</code>)の更新値をセットします。
     * @param value 基準日付(<code>USE_BY_DATE</code>)更新値
     */
    public void updateUseByDate(String value)
    {
        setAdhocUpdateValue(PCTRetResult.USE_BY_DATE, value) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
     * 
     * @param value アイテム情報コメント(<code>ITEM_INFO</code>)<br>
     * 文字列の検索値をアイテム情報コメント(<code>ITEM_INFO</code>)にセットします。
     */
    public void setItemInfo(String value)
    {
        setKey(PCTRetResult.ITEM_INFO, value) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemInfo(String[] values)
    {
        setKey(PCTRetResult.ITEM_INFO, values, true) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemInfo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_INFO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemInfo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_INFO, values, and_or_toNext) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemInfo(String value, String compcode)
    {
        setKey(PCTRetResult.ITEM_INFO, value, compcode, "", "", true) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
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
    public void setItemInfo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_INFO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setItemInfo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.ITEM_INFO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>)の更新値をセットします。
     * @param value アイテム情報コメント(<code>ITEM_INFO</code>)更新値
     */
    public void updateItemInfo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.ITEM_INFO, value) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ロットNo(<code>PLAN_LOT_NO</code>)<br>
     * 文字列の検索値を予定ロットNo(<code>PLAN_LOT_NO</code>)にセットします。
     */
    public void setPlanLotNo(String value)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, value) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, values, true) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanLotNo(String value, String compcode)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
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
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の更新値をセットします。
     * @param value 予定ロットNo(<code>PLAN_LOT_NO</code>)更新値
     */
    public void updatePlanLotNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_LOT_NO, value) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値を予定数(<code>PLAN_QTY</code>)にセットします。
     */
    public void setPlanQty(int value)
    {
        setKey(PCTRetResult.PLAN_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values)
    {
        setKey(PCTRetResult.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(PCTRetResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
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
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定数(<code>PLAN_QTY</code>)の更新値をセットします。
     * @param value 予定数(<code>PLAN_QTY</code>)更新値
     */
    public void updatePlanQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.PLAN_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 実績数(<code>RESULT_QTY</code>)<br>
     * 数値の検索値を実績数(<code>RESULT_QTY</code>)にセットします。
     */
    public void setResultQty(int value)
    {
        setKey(PCTRetResult.RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values)
    {
        setKey(PCTRetResult.RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setResultQty(int value, String compcode)
    {
        setKey(PCTRetResult.RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
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
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実績数(<code>RESULT_QTY</code>)の更新値をセットします。
     * @param value 実績数(<code>RESULT_QTY</code>)更新値
     */
    public void updateResultQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)<br>
     * 数値の検索値を欠品数(<code>SHORTAGE_QTY</code>)にセットします。
     */
    public void setShortageQty(int value)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShortageQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShortageQty(int value, String compcode)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
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
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の更新値をセットします。
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)更新値
     */
    public void updateShortageQty(int value)
    {
        setAdhocUpdateValue(PCTRetResult.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 実績報告区分(<code>REPORT_FLAG</code>)<br>
     * 文字列の検索値を実績報告区分(<code>REPORT_FLAG</code>)にセットします。
     */
    public void setReportFlag(String value)
    {
        setKey(PCTRetResult.REPORT_FLAG, value) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values)
    {
        setKey(PCTRetResult.REPORT_FLAG, values, true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.REPORT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReportFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REPORT_FLAG, values, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReportFlag(String value, String compcode)
    {
        setKey(PCTRetResult.REPORT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
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
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.REPORT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REPORT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の更新値をセットします。
     * @param value 実績報告区分(<code>REPORT_FLAG</code>)更新値
     */
    public void updateReportFlag(String value)
    {
        setAdhocUpdateValue(PCTRetResult.REPORT_FLAG, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をユーザID(<code>USER_ID</code>)にセットします。
     */
    public void setUserId(String value)
    {
        setKey(PCTRetResult.USER_ID, value) ;
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
        setKey(PCTRetResult.USER_ID, values, true) ;
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
        setKey(PCTRetResult.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(PCTRetResult.USER_ID, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USER_ID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USER_ID</code>)更新値
     */
    public void updateUserId(String value)
    {
        setAdhocUpdateValue(PCTRetResult.USER_ID, value) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名称(<code>USER_NAME</code>)<br>
     * 文字列の検索値をユーザ名称(<code>USER_NAME</code>)にセットします。
     */
    public void setUserName(String value)
    {
        setKey(PCTRetResult.USER_NAME, value) ;
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
        setKey(PCTRetResult.USER_NAME, values, true) ;
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
        setKey(PCTRetResult.USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.USER_NAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserName(String value, String compcode)
    {
        setKey(PCTRetResult.USER_NAME, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名称(<code>USER_NAME</code>)の更新値をセットします。
     * @param value ユーザ名称(<code>USER_NAME</code>)更新値
     */
    public void updateUserName(String value)
    {
        setAdhocUpdateValue(PCTRetResult.USER_NAME, value) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No、RFTNo(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(PCTRetResult.TERMINAL_NO, value) ;
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
        setKey(PCTRetResult.TERMINAL_NO, values, true) ;
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
        setKey(PCTRetResult.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(PCTRetResult.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
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
        setKey(PCTRetResult.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No、RFTNo(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(PCTRetResult.TERMINAL_NO, value) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 作業秒数(<code>WORK_SECOND</code>)<br>
     * 数値の検索値を作業秒数(<code>WORK_SECOND</code>)にセットします。
     */
    public void setWorkSecond(int value)
    {
        setKey(PCTRetResult.WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecond(int[] values)
    {
        setKey(PCTRetResult.WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(PCTRetResult.WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkSecond(int value, String compcode)
    {
        setKey(PCTRetResult.WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
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
    public void setWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTRetResult.WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTRetResult.WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業秒数(<code>WORK_SECOND</code>)の更新値をセットします。
     * @param value 作業秒数(<code>WORK_SECOND</code>)更新値
     */
    public void updateWorkSecond(int value)
    {
        setAdhocUpdateValue(PCTRetResult.WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(PCTRetResult.REGIST_DATE, value) ;
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
        setKey(PCTRetResult.REGIST_DATE, values, true) ;
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
        setKey(PCTRetResult.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(PCTRetResult.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(PCTRetResult.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(PCTRetResult.REGIST_PNAME, value) ;
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
        setKey(PCTRetResult.REGIST_PNAME, values, true) ;
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
        setKey(PCTRetResult.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(PCTRetResult.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(PCTRetResult.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(PCTRetResult.LAST_UPDATE_DATE, value) ;
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
        setKey(PCTRetResult.LAST_UPDATE_DATE, values, true) ;
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
        setKey(PCTRetResult.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(PCTRetResult.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(PCTRetResult.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(PCTRetResult.LAST_UPDATE_PNAME, value) ;
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
        setKey(PCTRetResult.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(PCTRetResult.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTRetResult.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(PCTRetResult.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTRetResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTRetResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(PCTRetResult.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateShipLineNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.SHIP_LINE_NO, source, addvalue);
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateShipBranchNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.SHIP_BRANCH_NO, source, addvalue);
    }

    /**
     * 通番(<code>THROUGH_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateThroughNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.THROUGH_NO, source, addvalue);
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderItemQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.ORDER_ITEM_QTY, source, addvalue);
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderThroughNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.ORDER_THROUGH_NO, source, addvalue);
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderThroughNoCntWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.ORDER_THROUGH_NO_CNT, source, addvalue);
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.ENTERING_QTY, source, addvalue);
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBundleEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.BUNDLE_ENTERING_QTY, source, addvalue);
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLotEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.LOT_ENTERING_QTY, source, addvalue);
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePlanQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.PLAN_QTY, source, addvalue);
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.RESULT_QTY, source, addvalue);
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateShortageQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.SHORTAGE_QTY, source, addvalue);
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWorkSecondWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTRetResult.WORK_SECOND, source, addvalue);
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
        return "$Id: PCTRetResultAlterKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
