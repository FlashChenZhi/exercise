// $Id: MachineAlterKey.java 7253 2010-02-26 05:58:01Z kanda $
// $LastChangedRevision: 7253 $
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
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * MACHINE用の更新キークラスです。
 *
 * @version $Revision: 7253 $, $Date: 2010-02-26 14:58:01 +0900 (金, 26 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kanda $
 */

public class MachineAlterKey
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
    public static final StoreMetaData $storeMetaData = Machine.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public MachineAlterKey()
    {
        super(Machine.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo.(<code>STATION_NO</code>)<br>
     * 文字列の検索値をステーションNo.(<code>STATION_NO</code>)にセットします。
     */
    public void setStationNo(String value)
    {
        setKey(Machine.STATION_NO, value) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values)
    {
        setKey(Machine.STATION_NO, values, true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values, String and_or_toNext)
    {
        setKey(Machine.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Machine.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
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
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo.(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo.(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(Machine.STATION_NO, value) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 機器種別(<code>MACHINE_TYPE</code>)<br>
     * 文字列の検索値を機器種別(<code>MACHINE_TYPE</code>)にセットします。
     */
    public void setMachineType(String value)
    {
        setKey(Machine.MACHINE_TYPE, value) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineType(String[] values)
    {
        setKey(Machine.MACHINE_TYPE, values, true) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineType(String[] values, String and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMachineType(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMachineType(String value, String compcode)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
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
    public void setMachineType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMachineType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の更新値をセットします。
     * @param value 機器種別(<code>MACHINE_TYPE</code>)更新値
     */
    public void updateMachineType(String value)
    {
        setAdhocUpdateValue(Machine.MACHINE_TYPE, value) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 機器No.(<code>MACHINE_NO</code>)<br>
     * 文字列の検索値を機器No.(<code>MACHINE_NO</code>)にセットします。
     */
    public void setMachineNo(String value)
    {
        setKey(Machine.MACHINE_NO, value) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNo(String[] values)
    {
        setKey(Machine.MACHINE_NO, values, true) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNo(String[] values, String and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMachineNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, values, and_or_toNext) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMachineNo(String value, String compcode)
    {
        setKey(Machine.MACHINE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
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
    public void setMachineNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMachineNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 機器No.(<code>MACHINE_NO</code>)の更新値をセットします。
     * @param value 機器No.(<code>MACHINE_NO</code>)更新値
     */
    public void updateMachineNo(String value)
    {
        setAdhocUpdateValue(Machine.MACHINE_NO, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を状態(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(Machine.STATUS_FLAG, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(Machine.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(Machine.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(Machine.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(Machine.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(Machine.STATUS_FLAG, value) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 異常コード(<code>ERROR_CODE</code>)<br>
     * 文字列の検索値を異常コード(<code>ERROR_CODE</code>)にセットします。
     */
    public void setErrorCode(String value)
    {
        setKey(Machine.ERROR_CODE, value) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCode(String[] values)
    {
        setKey(Machine.ERROR_CODE, values, true) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCode(String[] values, String and_or_toNext)
    {
        setKey(Machine.ERROR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setErrorCode(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.ERROR_CODE, values, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setErrorCode(String value, String compcode)
    {
        setKey(Machine.ERROR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
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
    public void setErrorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.ERROR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setErrorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.ERROR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 異常コード(<code>ERROR_CODE</code>)の更新値をセットします。
     * @param value 異常コード(<code>ERROR_CODE</code>)更新値
     */
    public void updateErrorCode(String value)
    {
        setAdhocUpdateValue(Machine.ERROR_CODE, value) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をコントローラー番号(<code>CONTROLLER_NO</code>)にセットします。
     */
    public void setControllerNo(String value)
    {
        setKey(Machine.CONTROLLER_NO, value) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values)
    {
        setKey(Machine.CONTROLLER_NO, values, true) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values, String and_or_toNext)
    {
        setKey(Machine.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Machine.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の更新値をセットします。
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)更新値
     */
    public void updateControllerNo(String value)
    {
        setAdhocUpdateValue(Machine.CONTROLLER_NO, value) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 機器名称(<code>DEVICE_NAME</code>)<br>
     * 文字列の検索値を機器名称(<code>DEVICE_NAME</code>)にセットします。
     */
    public void setDeviceName(String value)
    {
        setKey(Machine.DEVICE_NAME, value) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceName(String[] values)
    {
        setKey(Machine.DEVICE_NAME, values, true) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceName(String[] values, String and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDeviceName(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, values, and_or_toNext) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDeviceName(String value, String compcode)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
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
    public void setDeviceName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDeviceName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 機器名称(<code>DEVICE_NAME</code>)の更新値をセットします。
     * @param value 機器名称(<code>DEVICE_NAME</code>)更新値
     */
    public void updateDeviceName(String value)
    {
        setAdhocUpdateValue(Machine.DEVICE_NAME, value) ;
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
        return "$Id: MachineAlterKey.java 7253 2010-02-26 05:58:01Z kanda $" ;
    }
}
