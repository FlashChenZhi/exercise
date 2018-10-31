// $Id: WareHouseAlterKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WAREHOUSE用の更新キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class WareHouseAlterKey
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
    public static final StoreMetaData $storeMetaData = WareHouse.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public WareHouseAlterKey()
    {
        super(WareHouse.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo(<code>STATION_NO</code>)<br>
     * 文字列の検索値をステーションNo(<code>STATION_NO</code>)にセットします。
     */
    public void setStationNo(String value)
    {
        setKey(WareHouse.STATION_NO, value) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values)
    {
        setKey(WareHouse.STATION_NO, values, true) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(WareHouse.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(WareHouse.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(WareHouse.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(WareHouse.STATION_NO, value) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 格納区分(<code>WAREHOUSE_NO</code>)<br>
     * 文字列の検索値を格納区分(<code>WAREHOUSE_NO</code>)にセットします。
     */
    public void setWarehouseNo(String value)
    {
        setKey(WareHouse.WAREHOUSE_NO, value) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNo(String[] values)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, true) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNo(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWarehouseNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, and_or_toNext) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWarehouseNo(String value, String compcode)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
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
    public void setWarehouseNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWarehouseNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の更新値をセットします。
     * @param value 格納区分(<code>WAREHOUSE_NO</code>)更新値
     */
    public void updateWarehouseNo(String value)
    {
        setAdhocUpdateValue(WareHouse.WAREHOUSE_NO, value) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 最大混載数(<code>MAX_MIXEDPALLET</code>)<br>
     * 数値の検索値を最大混載数(<code>MAX_MIXEDPALLET</code>)にセットします。
     */
    public void setMaxMixedpallet(int value)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpallet(int[] values)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpallet(int[] values, String and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxMixedpallet(int[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxMixedpallet(int value, String compcode)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
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
    public void setMaxMixedpallet(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxMixedpallet(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の更新値をセットします。
     * @param value 最大混載数(<code>MAX_MIXEDPALLET</code>)更新値
     */
    public void updateMaxMixedpallet(int value)
    {
        setAdhocUpdateValue(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value)) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 倉庫名称(<code>WAREHOUSE_NAME</code>)<br>
     * 文字列の検索値を倉庫名称(<code>WAREHOUSE_NAME</code>)にセットします。
     */
    public void setWarehouseName(String value)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseName(String[] values)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, true) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseName(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWarehouseName(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, and_or_toNext) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWarehouseName(String value, String compcode)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
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
    public void setWarehouseName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWarehouseName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の更新値をセットします。
     * @param value 倉庫名称(<code>WAREHOUSE_NAME</code>)更新値
     */
    public void updateWarehouseName(String value)
    {
        setAdhocUpdateValue(WareHouse.WAREHOUSE_NAME, value) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)<br>
     * 文字列の検索値を最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)にセットします。
     */
    public void setLastUsedStationNo(String value)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, true) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUsedStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUsedStationNo(String value, String compcode)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
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
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の更新値をセットします。
     * @param value 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)更新値
     */
    public void updateLastUsedStationNo(String value)
    {
        setAdhocUpdateValue(WareHouse.LAST_USED_STATION_NO, value) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)<br>
     * 文字列の検索値を自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)にセットします。
     */
    public void setEmploymentType(String value)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentType(String[] values)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, true) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEmploymentType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEmploymentType(String value, String compcode)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
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
    public void setEmploymentType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEmploymentType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の更新値をセットします。
     * @param value 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)更新値
     */
    public void updateEmploymentType(String value)
    {
        setAdhocUpdateValue(WareHouse.EMPLOYMENT_TYPE, value) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)<br>
     * 文字列の検索値をフリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)にセットします。
     */
    public void setFreeAllocationType(String value)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationType(String[] values)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, true) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFreeAllocationType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, and_or_toNext) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFreeAllocationType(String value, String compcode)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
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
    public void setFreeAllocationType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFreeAllocationType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の更新値をセットします。
     * @param value フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)更新値
     */
    public void updateFreeAllocationType(String value)
    {
        setAdhocUpdateValue(WareHouse.FREE_ALLOCATION_TYPE, value) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)<br>
     * 文字列の検索値を空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)にセットします。
     */
    public void setLocationSearchType(String value)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchType(String[] values)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, true) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationSearchType(String value, String compcode)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setLocationSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の更新値をセットします。
     * @param value 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)更新値
     */
    public void updateLocationSearchType(String value)
    {
        setAdhocUpdateValue(WareHouse.LOCATION_SEARCH_TYPE, value) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)<br>
     * 文字列の検索値をアイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)にセットします。
     */
    public void setAisleSearchType(String value)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchType(String[] values)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, true) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAisleSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAisleSearchType(String value, String compcode)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setAisleSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAisleSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の更新値をセットします。
     * @param value アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)更新値
     */
    public void updateAisleSearchType(String value)
    {
        setAdhocUpdateValue(WareHouse.AISLE_SEARCH_TYPE, value) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxMixedpalletWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(WareHouse.MAX_MIXEDPALLET, source, addvalue);
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
        return "$Id: WareHouseAlterKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
