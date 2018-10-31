// $Id: MoveWorkInfoAlterKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * MOVEWORKINFO用の更新キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class MoveWorkInfoAlterKey
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
    public static final StoreMetaData $storeMetaData = MoveWorkInfo.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public MoveWorkInfoAlterKey()
    {
        super(MoveWorkInfo.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>JOB_NO</code>)<br>
     * 文字列の検索値を作業No.(<code>JOB_NO</code>)にセットします。
     */
    public void setJobNo(String value)
    {
        setKey(MoveWorkInfo.JOB_NO, value) ;
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
        setKey(MoveWorkInfo.JOB_NO, values, true) ;
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
        setKey(MoveWorkInfo.JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.JOB_NO, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業No.(<code>JOB_NO</code>)の更新値をセットします。
     * @param value 作業No.(<code>JOB_NO</code>)更新値
     */
    public void updateJobNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.JOB_NO, value) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)<br>
     * 文字列の検索値を設定単位キー(<code>SETTING_UNIT_KEY</code>)にセットします。
     */
    public void setSettingUnitKey(String value)
    {
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, value) ;
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
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, values, true) ;
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
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSettingUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSettingUnitKey(String value, String compcode)
    {
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の更新値をセットします。
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)更新値
     */
    public void updateSettingUnitKey(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.SETTING_UNIT_KEY, value) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値を作業区分(<code>JOB_TYPE</code>)にセットします。
     */
    public void setJobType(String value)
    {
        setKey(MoveWorkInfo.JOB_TYPE, value) ;
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
        setKey(MoveWorkInfo.JOB_TYPE, values, true) ;
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
        setKey(MoveWorkInfo.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(MoveWorkInfo.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
    public void setJobType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業区分(<code>JOB_TYPE</code>)の更新値をセットします。
     * @param value 作業区分(<code>JOB_TYPE</code>)更新値
     */
    public void updateJobType(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.JOB_TYPE, value) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を状態フラグ(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(MoveWorkInfo.STATUS_FLAG, value) ;
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
        setKey(MoveWorkInfo.STATUS_FLAG, values, true) ;
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
        setKey(MoveWorkInfo.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(MoveWorkInfo.STATUS_FLAG, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STATUS_FLAG, value) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)<br>
     * 文字列の検索値をハードウェア区分(<code>HARDWARE_TYPE</code>)にセットします。
     */
    public void setHardwareType(String value)
    {
        setKey(MoveWorkInfo.HARDWARE_TYPE, value) ;
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
        setKey(MoveWorkInfo.HARDWARE_TYPE, values, true) ;
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
        setKey(MoveWorkInfo.HARDWARE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHardwareType(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.HARDWARE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHardwareType(String value, String compcode)
    {
        setKey(MoveWorkInfo.HARDWARE_TYPE, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の更新値をセットします。
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)更新値
     */
    public void updateHardwareType(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.HARDWARE_TYPE, value) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 入荷フラグ(<code>RECEIVING_FLAG</code>)<br>
     * 文字列の検索値を入荷フラグ(<code>RECEIVING_FLAG</code>)にセットします。
     */
    public void setReceivingFlag(String value)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, value) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingFlag(String[] values)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, values, true) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceivingFlag(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReceivingFlag(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, values, and_or_toNext) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReceivingFlag(String value, String compcode)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
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
    public void setReceivingFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReceivingFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RECEIVING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>)の更新値をセットします。
     * @param value 入荷フラグ(<code>RECEIVING_FLAG</code>)更新値
     */
    public void updateReceivingFlag(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RECEIVING_FLAG, value) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 在庫ID(<code>STOCK_ID</code>)<br>
     * 文字列の検索値を在庫ID(<code>STOCK_ID</code>)にセットします。
     */
    public void setStockId(String value)
    {
        setKey(MoveWorkInfo.STOCK_ID, value) ;
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
        setKey(MoveWorkInfo.STOCK_ID, values, true) ;
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
        setKey(MoveWorkInfo.STOCK_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STOCK_ID, values, and_or_toNext) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockId(String value, String compcode)
    {
        setKey(MoveWorkInfo.STOCK_ID, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.STOCK_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.STOCK_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫ID(<code>STOCK_ID</code>)の更新値をセットします。
     * @param value 在庫ID(<code>STOCK_ID</code>)更新値
     */
    public void updateStockId(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STOCK_ID, value) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)<br>
     * 文字列の検索値を入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)にセットします。
     */
    public void setWorkConnKey(String value)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, value) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKey(String[] values)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, values, true) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKey(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkConnKey(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, values, and_or_toNext) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkConnKey(String value, String compcode)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
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
    public void setWorkConnKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkConnKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.WORK_CONN_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の更新値をセットします。
     * @param value 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)更新値
     */
    public void updateWorkConnKey(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.WORK_CONN_KEY, value) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)<br>
     * 文字列の検索値を移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)にセットします。
     */
    public void setRetrievalAreaNo(String value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, value) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNo(String[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, values, true) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalAreaNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
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
    public void setRetrievalAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の更新値をセットします。
     * @param value 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)更新値
     */
    public void updateRetrievalAreaNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_AREA_NO, value) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)<br>
     * 文字列の検索値を移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)にセットします。
     */
    public void setRetrievalLocationNo(String value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, value) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNo(String[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, values, true) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalLocationNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
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
    public void setRetrievalLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の更新値をセットします。
     * @param value 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)更新値
     */
    public void updateRetrievalLocationNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_LOCATION_NO, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(MoveWorkInfo.CONSIGNOR_CODE, value) ;
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
        setKey(MoveWorkInfo.CONSIGNOR_CODE, values, true) ;
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
        setKey(MoveWorkInfo.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(MoveWorkInfo.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.CONSIGNOR_CODE, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値を商品コード(<code>ITEM_CODE</code>)にセットします。
     */
    public void setItemCode(String value)
    {
        setKey(MoveWorkInfo.ITEM_CODE, value) ;
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
        setKey(MoveWorkInfo.ITEM_CODE, values, true) ;
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
        setKey(MoveWorkInfo.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(MoveWorkInfo.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品コード(<code>ITEM_CODE</code>)の更新値をセットします。
     * @param value 商品コード(<code>ITEM_CODE</code>)更新値
     */
    public void updateItemCode(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.ITEM_CODE, value) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value ロットNo.(<code>LOT_NO</code>)<br>
     * 文字列の検索値をロットNo.(<code>LOT_NO</code>)にセットします。
     */
    public void setLotNo(String value)
    {
        setKey(MoveWorkInfo.LOT_NO, value) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values)
    {
        setKey(MoveWorkInfo.LOT_NO, values, true) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.LOT_NO, values, and_or_toNext) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLotNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
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
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロットNo.(<code>LOT_NO</code>)の更新値をセットします。
     * @param value ロットNo.(<code>LOT_NO</code>)更新値
     */
    public void updateLotNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.LOT_NO, value) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 入庫日(<code>STORAGE_DAY</code>)<br>
     * 文字列の検索値を入庫日(<code>STORAGE_DAY</code>)にセットします。
     */
    public void setStorageDay(String value)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, value) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDay(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, values, true) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDay(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, values, and_or_toNext) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageDay(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
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
    public void setStorageDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入庫日(<code>STORAGE_DAY</code>)の更新値をセットします。
     * @param value 入庫日(<code>STORAGE_DAY</code>)更新値
     */
    public void updateStorageDay(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_DAY, value) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 入庫日時(<code>STORAGE_DATE</code>)<br>
     * 日付の検索値を入庫日時(<code>STORAGE_DATE</code>)にセットします。
     */
    public void setStorageDate(Date value)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, value) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDate(Date[] values)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, values, true) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
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
    public void setStorageDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の更新値をセットします。
     * @param value 入庫日時(<code>STORAGE_DATE</code>)更新値
     */
    public void updateStorageDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_DATE, value) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 最終出庫日(<code>RETRIEVAL_DAY</code>)<br>
     * 文字列の検索値を最終出庫日(<code>RETRIEVAL_DAY</code>)にセットします。
     */
    public void setRetrievalDay(String value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, value) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDay(String[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, values, true) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDay(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, values, and_or_toNext) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalDay(String value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
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
    public void setRetrievalDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の更新値をセットします。
     * @param value 最終出庫日(<code>RETRIEVAL_DAY</code>)更新値
     */
    public void updateRetrievalDay(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_DAY, value) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)<br>
     * 文字列の検索値を移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)にセットします。
     */
    public void setRetrievalUserId(String value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, value) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserId(String[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, values, true) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserId(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalUserId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, values, and_or_toNext) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalUserId(String value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
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
    public void setRetrievalUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の更新値をセットします。
     * @param value 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)更新値
     */
    public void updateRetrievalUserId(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_USER_ID, value) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)<br>
     * 文字列の検索値を移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)にセットします。
     */
    public void setRetrievalTerminalNo(String value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, value) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNo(String[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, values, true) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalTerminalNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setRetrievalTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の更新値をセットします。
     * @param value 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)更新値
     */
    public void updateRetrievalTerminalNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_TERMINAL_NO, value) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)<br>
     * 日付の検索値を移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)にセットします。
     */
    public void setRetrievalWorkDate(Date value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, value) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDate(Date[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, values, true) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalWorkDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, values, and_or_toNext) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalWorkDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
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
    public void setRetrievalWorkDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalWorkDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の更新値をセットします。
     * @param value 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)更新値
     */
    public void updateRetrievalWorkDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_WORK_DATE, value) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)<br>
     * 数値の検索値を移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)にセットします。
     */
    public void setRetrievalWorkSecond(int value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecond(int[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalWorkSecond(int value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
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
    public void setRetrievalWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の更新値をセットします。
     * @param value 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)更新値
     */
    public void updateRetrievalWorkSecond(int value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値を予定数(<code>PLAN_QTY</code>)にセットします。
     */
    public void setPlanQty(int value)
    {
        setKey(MoveWorkInfo.PLAN_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(MoveWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(MoveWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(MoveWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 予定数(<code>PLAN_QTY</code>)の更新値をセットします。
     * @param value 予定数(<code>PLAN_QTY</code>)更新値
     */
    public void updatePlanQty(int value)
    {
        setAdhocUpdateValue(MoveWorkInfo.PLAN_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)<br>
     * 数値の検索値を出庫数(<code>RETRIEVAL_RESULT_QTY</code>)にセットします。
     */
    public void setRetrievalResultQty(int value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQty(int[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQty(int[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalResultQty(int value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
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
    public void setRetrievalResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の更新値をセットします。
     * @param value 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)更新値
     */
    public void updateRetrievalResultQty(int value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 入庫数(<code>STORAGE_RESULT_QTY</code>)<br>
     * 数値の検索値を入庫数(<code>STORAGE_RESULT_QTY</code>)にセットします。
     */
    public void setStorageResultQty(int value)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQty(int[] values)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQty(int[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageResultQty(int value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setStorageResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の更新値をセットします。
     * @param value 入庫数(<code>STORAGE_RESULT_QTY</code>)更新値
     */
    public void updateStorageResultQty(int value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫エリア(<code>STORAGE_AREA_NO</code>)<br>
     * 文字列の検索値を移動入庫エリア(<code>STORAGE_AREA_NO</code>)にセットします。
     */
    public void setStorageAreaNo(String value)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, value) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNo(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, values, true) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageAreaNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
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
    public void setStorageAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の更新値をセットします。
     * @param value 移動入庫エリア(<code>STORAGE_AREA_NO</code>)更新値
     */
    public void updateStorageAreaNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_AREA_NO, value) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)<br>
     * 文字列の検索値を移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)にセットします。
     */
    public void setStorageLocationNo(String value)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, value) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNo(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, values, true) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageLocationNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
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
    public void setStorageLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の更新値をセットします。
     * @param value 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)更新値
     */
    public void updateStorageLocationNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_LOCATION_NO, value) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)<br>
     * 文字列の検索値を移動入庫ユーザID(<code>STORAGE_USER_ID</code>)にセットします。
     */
    public void setStorageUserId(String value)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, value) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserId(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, values, true) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserId(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageUserId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, values, and_or_toNext) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageUserId(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
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
    public void setStorageUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の更新値をセットします。
     * @param value 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)更新値
     */
    public void updateStorageUserId(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_USER_ID, value) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)<br>
     * 文字列の検索値を移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)にセットします。
     */
    public void setStorageTerminalNo(String value)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, value) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNo(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, values, true) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageTerminalNo(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setStorageTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の更新値をセットします。
     * @param value 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)更新値
     */
    public void updateStorageTerminalNo(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_TERMINAL_NO, value) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)<br>
     * 日付の検索値を移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)にセットします。
     */
    public void setStorageWorkDate(Date value)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, value) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDate(Date[] values)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, values, true) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageWorkDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, values, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageWorkDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
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
    public void setStorageWorkDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageWorkDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の更新値をセットします。
     * @param value 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)更新値
     */
    public void updateStorageWorkDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_WORK_DATE, value) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)<br>
     * 文字列の検索値を移動入庫作業日(<code>STORAGE_WORK_DAY</code>)にセットします。
     */
    public void setStorageWorkDay(String value)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, value) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDay(String[] values)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, values, true) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDay(String[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageWorkDay(String value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
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
    public void setStorageWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の更新値をセットします。
     * @param value 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)更新値
     */
    public void updateStorageWorkDay(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_WORK_DAY, value) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)<br>
     * 数値の検索値を移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)にセットします。
     */
    public void setStorageWorkSecond(int value)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecond(int[] values)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageWorkSecond(int value, String compcode)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
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
    public void setStorageWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の更新値をセットします。
     * @param value 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)更新値
     */
    public void updateStorageWorkSecond(int value)
    {
        setAdhocUpdateValue(MoveWorkInfo.STORAGE_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)<br>
     * 日付の検索値を出庫開始日時(<code>RETRIEVAL_START_DATE</code>)にセットします。
     */
    public void setRetrievalStartDate(Date value)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, value) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDate(Date[] values)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, values, true) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, values, and_or_toNext) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalStartDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
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
    public void setRetrievalStartDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.RETRIEVAL_START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の更新値をセットします。
     * @param value 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)更新値
     */
    public void updateRetrievalStartDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.RETRIEVAL_START_DATE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(MoveWorkInfo.REGIST_DATE, value) ;
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
        setKey(MoveWorkInfo.REGIST_DATE, values, true) ;
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
        setKey(MoveWorkInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(MoveWorkInfo.REGIST_PNAME, value) ;
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
        setKey(MoveWorkInfo.REGIST_PNAME, values, true) ;
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
        setKey(MoveWorkInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(MoveWorkInfo.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, value) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, values, true) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(MoveWorkInfo.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, value) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(MoveWorkInfo.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRetrievalWorkSecondWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(MoveWorkInfo.RETRIEVAL_WORK_SECOND, source, addvalue);
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePlanQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(MoveWorkInfo.PLAN_QTY, source, addvalue);
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRetrievalResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(MoveWorkInfo.RETRIEVAL_RESULT_QTY, source, addvalue);
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStorageResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(MoveWorkInfo.STORAGE_RESULT_QTY, source, addvalue);
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStorageWorkSecondWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(MoveWorkInfo.STORAGE_WORK_SECOND, source, addvalue);
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
        return "$Id: MoveWorkInfoAlterKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
