// $Id: CarryInfoAlterKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * CARRYINFO用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class CarryInfoAlterKey
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
    public static final StoreMetaData $storeMetaData = CarryInfo.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public CarryInfoAlterKey()
    {
        super(CarryInfo.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 搬送Key(<code>CARRY_KEY</code>)<br>
     * 文字列の検索値を搬送Key(<code>CARRY_KEY</code>)にセットします。
     */
    public void setCarryKey(String value)
    {
        setKey(CarryInfo.CARRY_KEY, value) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKey(String[] values)
    {
        setKey(CarryInfo.CARRY_KEY, values, true) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKey(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCarryKey(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, values, and_or_toNext) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCarryKey(String value, String compcode)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
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
    public void setCarryKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCarryKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送Key(<code>CARRY_KEY</code>)の更新値をセットします。
     * @param value 搬送Key(<code>CARRY_KEY</code>)更新値
     */
    public void updateCarryKey(String value)
    {
        setAdhocUpdateValue(CarryInfo.CARRY_KEY, value) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value パレットID(<code>PALLET_ID</code>)<br>
     * 文字列の検索値をパレットID(<code>PALLET_ID</code>)にセットします。
     */
    public void setPalletId(String value)
    {
        setKey(CarryInfo.PALLET_ID, value) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletId(String[] values)
    {
        setKey(CarryInfo.PALLET_ID, values, true) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletId(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPalletId(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, values, and_or_toNext) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPalletId(String value, String compcode)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, "", "", true) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
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
    public void setPalletId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPalletId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パレットID(<code>PALLET_ID</code>)の更新値をセットします。
     * @param value パレットID(<code>PALLET_ID</code>)更新値
     */
    public void updatePalletId(String value)
    {
        setAdhocUpdateValue(CarryInfo.PALLET_ID, value) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業種別(<code>WORK_TYPE</code>)<br>
     * 文字列の検索値を作業種別(<code>WORK_TYPE</code>)にセットします。
     */
    public void setWorkType(String value)
    {
        setKey(CarryInfo.WORK_TYPE, value) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkType(String[] values)
    {
        setKey(CarryInfo.WORK_TYPE, values, true) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkType(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkType(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkType(String value, String compcode)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
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
    public void setWorkType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業種別(<code>WORK_TYPE</code>)の更新値をセットします。
     * @param value 作業種別(<code>WORK_TYPE</code>)更新値
     */
    public void updateWorkType(String value)
    {
        setAdhocUpdateValue(CarryInfo.WORK_TYPE, value) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 出庫グループNo.(<code>GROUP_NO</code>)<br>
     * 数値の検索値を出庫グループNo.(<code>GROUP_NO</code>)にセットします。
     */
    public void setGroupNo(int value)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNo(int[] values)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNo(int[] values, String and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setGroupNo(int[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setGroupNo(int value, String compcode)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
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
    public void setGroupNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setGroupNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の更新値をセットします。
     * @param value 出庫グループNo.(<code>GROUP_NO</code>)更新値
     */
    public void updateGroupNo(int value)
    {
        setAdhocUpdateValue(CarryInfo.GROUP_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 搬送状態(<code>CMD_STATUS</code>)<br>
     * 文字列の検索値を搬送状態(<code>CMD_STATUS</code>)にセットします。
     */
    public void setCmdStatus(String value)
    {
        setKey(CarryInfo.CMD_STATUS, value) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatus(String[] values)
    {
        setKey(CarryInfo.CMD_STATUS, values, true) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatus(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCmdStatus(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, values, and_or_toNext) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCmdStatus(String value, String compcode)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
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
    public void setCmdStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCmdStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送状態(<code>CMD_STATUS</code>)の更新値をセットします。
     * @param value 搬送状態(<code>CMD_STATUS</code>)更新値
     */
    public void updateCmdStatus(String value)
    {
        setAdhocUpdateValue(CarryInfo.CMD_STATUS, value) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 優先区分(<code>PRIORITY</code>)<br>
     * 文字列の検索値を優先区分(<code>PRIORITY</code>)にセットします。
     */
    public void setPriority(String value)
    {
        setKey(CarryInfo.PRIORITY, value) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values)
    {
        setKey(CarryInfo.PRIORITY, values, true) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPriority(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, values, and_or_toNext) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPriority(String value, String compcode)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, "", "", true) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPriority(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 優先区分(<code>PRIORITY</code>)の更新値をセットします。
     * @param value 優先区分(<code>PRIORITY</code>)更新値
     */
    public void updatePriority(String value)
    {
        setAdhocUpdateValue(CarryInfo.PRIORITY, value) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 再入庫区分(<code>RESTORING_FLAG</code>)<br>
     * 文字列の検索値を再入庫区分(<code>RESTORING_FLAG</code>)にセットします。
     */
    public void setRestoringFlag(String value)
    {
        setKey(CarryInfo.RESTORING_FLAG, value) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlag(String[] values)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, true) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlag(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRestoringFlag(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, and_or_toNext) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRestoringFlag(String value, String compcode)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
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
    public void setRestoringFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRestoringFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の更新値をセットします。
     * @param value 再入庫区分(<code>RESTORING_FLAG</code>)更新値
     */
    public void updateRestoringFlag(String value)
    {
        setAdhocUpdateValue(CarryInfo.RESTORING_FLAG, value) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 搬送区分(<code>CARRY_FLAG</code>)<br>
     * 文字列の検索値を搬送区分(<code>CARRY_FLAG</code>)にセットします。
     */
    public void setCarryFlag(String value)
    {
        setKey(CarryInfo.CARRY_FLAG, value) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlag(String[] values)
    {
        setKey(CarryInfo.CARRY_FLAG, values, true) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlag(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCarryFlag(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, values, and_or_toNext) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCarryFlag(String value, String compcode)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
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
    public void setCarryFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCarryFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の更新値をセットします。
     * @param value 搬送区分(<code>CARRY_FLAG</code>)更新値
     */
    public void updateCarryFlag(String value)
    {
        setAdhocUpdateValue(CarryInfo.CARRY_FLAG, value) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)<br>
     * 文字列の検索値を出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)にセットします。
     */
    public void setRetrievalStationNo(String value)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNo(String[] values)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, true) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalStationNo(String value, String compcode)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
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
    public void setRetrievalStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の更新値をセットします。
     * @param value 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)更新値
     */
    public void updateRetrievalStationNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.RETRIEVAL_STATION_NO, value) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)<br>
     * 文字列の検索値を出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)にセットします。
     */
    public void setRetrievalDetail(String value)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetail(String[] values)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, true) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetail(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalDetail(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, and_or_toNext) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalDetail(String value, String compcode)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, "", "", true) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
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
    public void setRetrievalDetail(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalDetail(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の更新値をセットします。
     * @param value 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)更新値
     */
    public void updateRetrievalDetail(String value)
    {
        setAdhocUpdateValue(CarryInfo.RETRIEVAL_DETAIL, value) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>WORK_NO</code>)<br>
     * 文字列の検索値を作業No.(<code>WORK_NO</code>)にセットします。
     */
    public void setWorkNo(String value)
    {
        setKey(CarryInfo.WORK_NO, value) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values)
    {
        setKey(CarryInfo.WORK_NO, values, true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkNo(String value, String compcode)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
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
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業No.(<code>WORK_NO</code>)の更新値をセットします。
     * @param value 作業No.(<code>WORK_NO</code>)更新値
     */
    public void updateWorkNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.WORK_NO, value) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)<br>
     * 文字列の検索値を搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)にセットします。
     */
    public void setSourceStationNo(String value)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNo(String[] values)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, true) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSourceStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSourceStationNo(String value, String compcode)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
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
    public void setSourceStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSourceStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の更新値をセットします。
     * @param value 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)更新値
     */
    public void updateSourceStationNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.SOURCE_STATION_NO, value) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)<br>
     * 文字列の検索値を搬送先ステーションNo.(<code>DEST_STATION_NO</code>)にセットします。
     */
    public void setDestStationNo(String value)
    {
        setKey(CarryInfo.DEST_STATION_NO, value) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNo(String[] values)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, true) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDestStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDestStationNo(String value, String compcode)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
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
    public void setDestStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDestStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の更新値をセットします。
     * @param value 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)更新値
     */
    public void updateDestStationNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.DEST_STATION_NO, value) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 到着日時(<code>ARRIVAL_DATE</code>)<br>
     * 日付の検索値を到着日時(<code>ARRIVAL_DATE</code>)にセットします。
     */
    public void setArrivalDate(Date value)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDate(Date[] values)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, true) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDate(Date[] values, String and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setArrivalDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, and_or_toNext) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setArrivalDate(Date value, String compcode)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
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
    public void setArrivalDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setArrivalDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の更新値をセットします。
     * @param value 到着日時(<code>ARRIVAL_DATE</code>)更新値
     */
    public void updateArrivalDate(Date value)
    {
        setAdhocUpdateValue(CarryInfo.ARRIVAL_DATE, value) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 制御情報(<code>CONTROLINFO</code>)<br>
     * 文字列の検索値を制御情報(<code>CONTROLINFO</code>)にセットします。
     */
    public void setControlinfo(String value)
    {
        setKey(CarryInfo.CONTROLINFO, value) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfo(String[] values)
    {
        setKey(CarryInfo.CONTROLINFO, values, true) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControlinfo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, values, and_or_toNext) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControlinfo(String value, String compcode)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, "", "", true) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
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
    public void setControlinfo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControlinfo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 制御情報(<code>CONTROLINFO</code>)の更新値をセットします。
     * @param value 制御情報(<code>CONTROLINFO</code>)更新値
     */
    public void updateControlinfo(String value)
    {
        setAdhocUpdateValue(CarryInfo.CONTROLINFO, value) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value キャンセル要求区分(<code>CANCEL_REQUEST</code>)<br>
     * 文字列の検索値をキャンセル要求区分(<code>CANCEL_REQUEST</code>)にセットします。
     */
    public void setCancelRequest(String value)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequest(String[] values)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, true) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequest(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCancelRequest(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, and_or_toNext) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCancelRequest(String value, String compcode)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, "", "", true) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
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
    public void setCancelRequest(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCancelRequest(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の更新値をセットします。
     * @param value キャンセル要求区分(<code>CANCEL_REQUEST</code>)更新値
     */
    public void updateCancelRequest(String value)
    {
        setAdhocUpdateValue(CarryInfo.CANCEL_REQUEST, value) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)<br>
     * 日付の検索値をキャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)にセットします。
     */
    public void setCancelRequestDate(Date value)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDate(Date[] values)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, true) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDate(Date[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCancelRequestDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, and_or_toNext) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCancelRequestDate(Date value, String compcode)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
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
    public void setCancelRequestDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCancelRequestDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の更新値をセットします。
     * @param value キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)更新値
     */
    public void updateCancelRequestDate(Date value)
    {
        setAdhocUpdateValue(CarryInfo.CANCEL_REQUEST_DATE, value) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールNo(<code>SCHEDULE_NO</code>)<br>
     * 文字列の検索値をスケジュールNo(<code>SCHEDULE_NO</code>)にセットします。
     */
    public void setScheduleNo(String value)
    {
        setKey(CarryInfo.SCHEDULE_NO, value) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, true) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setScheduleNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setScheduleNo(String value, String compcode)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の更新値をセットします。
     * @param value スケジュールNo(<code>SCHEDULE_NO</code>)更新値
     */
    public void updateScheduleNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.SCHEDULE_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をアイルステーションNo.(<code>AISLE_STATION_NO</code>)にセットします。
     */
    public void setAisleStationNo(String value)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
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
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の更新値をセットします。
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)更新値
     */
    public void updateAisleStationNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.AISLE_STATION_NO, value) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終ステーションNo.(<code>END_STATION_NO</code>)<br>
     * 文字列の検索値を最終ステーションNo.(<code>END_STATION_NO</code>)にセットします。
     */
    public void setEndStationNo(String value)
    {
        setKey(CarryInfo.END_STATION_NO, value) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNo(String[] values)
    {
        setKey(CarryInfo.END_STATION_NO, values, true) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndStationNo(String value, String compcode)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
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
    public void setEndStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の更新値をセットします。
     * @param value 最終ステーションNo.(<code>END_STATION_NO</code>)更新値
     */
    public void updateEndStationNo(String value)
    {
        setAdhocUpdateValue(CarryInfo.END_STATION_NO, value) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 異常コード(<code>ERROR_CODE</code>)<br>
     * 文字列の検索値を異常コード(<code>ERROR_CODE</code>)にセットします。
     */
    public void setErrorCode(String value)
    {
        setKey(CarryInfo.ERROR_CODE, value) ;
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
        setKey(CarryInfo.ERROR_CODE, values, true) ;
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
        setKey(CarryInfo.ERROR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setErrorCode(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.ERROR_CODE, values, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setErrorCode(String value, String compcode)
    {
        setKey(CarryInfo.ERROR_CODE, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.ERROR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.ERROR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 異常コード(<code>ERROR_CODE</code>)の更新値をセットします。
     * @param value 異常コード(<code>ERROR_CODE</code>)更新値
     */
    public void updateErrorCode(String value)
    {
        setAdhocUpdateValue(CarryInfo.ERROR_CODE, value) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)<br>
     * 文字列の検索値をメンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)にセットします。
     */
    public void setMaintenanceTerminal(String value)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminal(String[] values)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, true) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminal(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaintenanceTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaintenanceTerminal(String value, String compcode)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, "", "", true) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
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
    public void setMaintenanceTerminal(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaintenanceTerminal(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の更新値をセットします。
     * @param value メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)更新値
     */
    public void updateMaintenanceTerminal(String value)
    {
        setAdhocUpdateValue(CarryInfo.MAINTENANCE_TERMINAL, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(CarryInfo.REGIST_DATE, value) ;
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
        setKey(CarryInfo.REGIST_DATE, values, true) ;
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
        setKey(CarryInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(CarryInfo.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(CarryInfo.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(CarryInfo.REGIST_PNAME, value) ;
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
        setKey(CarryInfo.REGIST_PNAME, values, true) ;
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
        setKey(CarryInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(CarryInfo.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(CarryInfo.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, value) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, values, true) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(CarryInfo.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, value) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(CarryInfo.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateGroupNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(CarryInfo.GROUP_NO, source, addvalue);
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
        return "$Id: CarryInfoAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
