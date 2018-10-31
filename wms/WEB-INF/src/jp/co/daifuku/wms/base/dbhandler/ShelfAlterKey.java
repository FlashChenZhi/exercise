// $Id: ShelfAlterKey.java 4122 2009-04-10 10:58:38Z ota $
// $LastChangedRevision: 4122 $
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
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SHELF用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class ShelfAlterKey
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
    public static final StoreMetaData $storeMetaData = Shelf.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public ShelfAlterKey()
    {
        super(Shelf.STORE_NAME) ;
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
        setKey(Shelf.STATION_NO, value) ;
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
        setKey(Shelf.STATION_NO, values, true) ;
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
        setKey(Shelf.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Shelf.STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Shelf.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Shelf.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(Shelf.STATION_NO, value) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value バンク(<code>BANK_NO</code>)<br>
     * 数値の検索値をバンク(<code>BANK_NO</code>)にセットします。
     */
    public void setBankNo(int value)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBankNo(int value, String compcode)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
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
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バンク(<code>BANK_NO</code>)の更新値をセットします。
     * @param value バンク(<code>BANK_NO</code>)更新値
     */
    public void updateBankNo(int value)
    {
        setAdhocUpdateValue(Shelf.BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value ベイ(<code>BAY_NO</code>)<br>
     * 数値の検索値をベイ(<code>BAY_NO</code>)にセットします。
     */
    public void setBayNo(int value)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBayNo(int value, String compcode)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
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
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ベイ(<code>BAY_NO</code>)の更新値をセットします。
     * @param value ベイ(<code>BAY_NO</code>)更新値
     */
    public void updateBayNo(int value)
    {
        setAdhocUpdateValue(Shelf.BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value レベル(<code>LEVEL_NO</code>)<br>
     * 数値の検索値をレベル(<code>LEVEL_NO</code>)にセットします。
     */
    public void setLevelNo(int value)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLevelNo(int value, String compcode)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
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
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * レベル(<code>LEVEL_NO</code>)の更新値をセットします。
     * @param value レベル(<code>LEVEL_NO</code>)更新値
     */
    public void updateLevelNo(int value)
    {
        setAdhocUpdateValue(Shelf.LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value アドレス(<code>ADDRESS_NO</code>)<br>
     * 数値の検索値をアドレス(<code>ADDRESS_NO</code>)にセットします。
     */
    public void setAddressNo(int value)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNo(int[] values)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAddressNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAddressNo(int value, String compcode)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
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
    public void setAddressNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAddressNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アドレス(<code>ADDRESS_NO</code>)の更新値をセットします。
     * @param value アドレス(<code>ADDRESS_NO</code>)更新値
     */
    public void updateAddressNo(int value)
    {
        setAdhocUpdateValue(Shelf.ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(Shelf.WH_STATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values)
    {
        setKey(Shelf.WH_STATION_NO, values, true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values, String and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Shelf.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
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
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(Shelf.WH_STATION_NO, value) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>PROHIBITION_FLAG</code>)<br>
     * 文字列の検索値を状態(<code>PROHIBITION_FLAG</code>)にセットします。
     */
    public void setProhibitionFlag(String value)
    {
        setKey(Shelf.PROHIBITION_FLAG, value) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlag(String[] values)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, true) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setProhibitionFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setProhibitionFlag(String value, String compcode)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
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
    public void setProhibitionFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setProhibitionFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の更新値をセットします。
     * @param value 状態(<code>PROHIBITION_FLAG</code>)更新値
     */
    public void updateProhibitionFlag(String value)
    {
        setAdhocUpdateValue(Shelf.PROHIBITION_FLAG, value) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を棚状態(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(Shelf.STATUS_FLAG, value) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(Shelf.STATUS_FLAG, values, true) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(Shelf.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(Shelf.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(Shelf.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚状態(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 棚状態(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(Shelf.STATUS_FLAG, value) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ハードゾーン(<code>HARD_ZONE_ID</code>)<br>
     * 文字列の検索値をハードゾーン(<code>HARD_ZONE_ID</code>)にセットします。
     */
    public void setHardZoneId(String value)
    {
        setKey(Shelf.HARD_ZONE_ID, value) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneId(String[] values)
    {
        setKey(Shelf.HARD_ZONE_ID, values, true) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneId(String[] values, String and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHardZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHardZoneId(String value, String compcode)
    {
        setKey(Shelf.HARD_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の更新値をセットします。
     * @param value ハードゾーン(<code>HARD_ZONE_ID</code>)更新値
     */
    public void updateHardZoneId(String value)
    {
        setAdhocUpdateValue(Shelf.HARD_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をソフトゾーン(<code>SOFT_ZONE_ID</code>)にセットします。
     */
    public void setSoftZoneId(String value)
    {
        setKey(Shelf.SOFT_ZONE_ID, value) ;
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
        setKey(Shelf.SOFT_ZONE_ID, values, true) ;
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
        setKey(Shelf.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の更新値をセットします。
     * @param value ソフトゾーン(<code>SOFT_ZONE_ID</code>)更新値
     */
    public void updateSoftZoneId(String value)
    {
        setAdhocUpdateValue(Shelf.SOFT_ZONE_ID, value) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 親ステーションNo(<code>PARENT_STATION_NO</code>)<br>
     * 文字列の検索値を親ステーションNo(<code>PARENT_STATION_NO</code>)にセットします。
     */
    public void setParentStationNo(String value)
    {
        setKey(Shelf.PARENT_STATION_NO, value) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values)
    {
        setKey(Shelf.PARENT_STATION_NO, values, true) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setParentStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setParentStationNo(String value, String compcode)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
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
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の更新値をセットします。
     * @param value 親ステーションNo(<code>PARENT_STATION_NO</code>)更新値
     */
    public void updateParentStationNo(String value)
    {
        setAdhocUpdateValue(Shelf.PARENT_STATION_NO, value) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)<br>
     * 文字列の検索値をアクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)にセットします。
     */
    public void setAccessNgFlag(String value)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlag(String[] values)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, true) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAccessNgFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, and_or_toNext) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAccessNgFlag(String value, String compcode)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
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
    public void setAccessNgFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAccessNgFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の更新値をセットします。
     * @param value アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)更新値
     */
    public void updateAccessNgFlag(String value)
    {
        setAdhocUpdateValue(Shelf.ACCESS_NG_FLAG, value) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 空棚検索順(<code>PRIORITY</code>)<br>
     * 数値の検索値を空棚検索順(<code>PRIORITY</code>)にセットします。
     */
    public void setPriority(int value)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(int[] values)
    {
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(int[] values, String and_or_toNext)
    {
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPriority(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPriority(int value, String compcode)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚検索順(<code>PRIORITY</code>)の更新値をセットします。
     * @param value 空棚検索順(<code>PRIORITY</code>)更新値
     */
    public void updatePriority(int value)
    {
        setAdhocUpdateValue(Shelf.PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ペアステーションNo(<code>PAIR_STATION_NO</code>)<br>
     * 文字列の検索値をペアステーションNo(<code>PAIR_STATION_NO</code>)にセットします。
     */
    public void setPairStationNo(String value)
    {
        setKey(Shelf.PAIR_STATION_NO, value) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNo(String[] values)
    {
        setKey(Shelf.PAIR_STATION_NO, values, true) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNo(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPairStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPairStationNo(String value, String compcode)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
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
    public void setPairStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPairStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の更新値をセットします。
     * @param value ペアステーションNo(<code>PAIR_STATION_NO</code>)更新値
     */
    public void updatePairStationNo(String value)
    {
        setAdhocUpdateValue(Shelf.PAIR_STATION_NO, value) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 手前、奥棚区分(<code>SIDE</code>)<br>
     * 文字列の検索値を手前、奥棚区分(<code>SIDE</code>)にセットします。
     */
    public void setSide(String value)
    {
        setKey(Shelf.SIDE, value) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSide(String[] values)
    {
        setKey(Shelf.SIDE, values, true) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSide(String[] values, String and_or_toNext)
    {
        setKey(Shelf.SIDE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSide(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.SIDE, values, and_or_toNext) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSide(String value, String compcode)
    {
        setKey(Shelf.SIDE, value, compcode, "", "", true) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
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
    public void setSide(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.SIDE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSide(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.SIDE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 手前、奥棚区分(<code>SIDE</code>)の更新値をセットします。
     * @param value 手前、奥棚区分(<code>SIDE</code>)更新値
     */
    public void updateSide(String value)
    {
        setAdhocUpdateValue(Shelf.SIDE, value) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 荷幅(<code>WIDTH</code>)<br>
     * 数値の検索値を荷幅(<code>WIDTH</code>)にセットします。
     */
    public void setWidth(int value)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidth(int[] values)
    {
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidth(int[] values, String and_or_toNext)
    {
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWidth(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWidth(int value, String compcode)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
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
    public void setWidth(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWidth(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷幅(<code>WIDTH</code>)の更新値をセットします。
     * @param value 荷幅(<code>WIDTH</code>)更新値
     */
    public void updateWidth(int value)
    {
        setAdhocUpdateValue(Shelf.WIDTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)<br>
     * 文字列の検索値を棚使用フラグ(<code>LOCATION_USE_FLAG</code>)にセットします。
     */
    public void setLocationUseFlag(String value)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlag(String[] values)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, true) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationUseFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationUseFlag(String value, String compcode)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
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
    public void setLocationUseFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationUseFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の更新値をセットします。
     * @param value 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)更新値
     */
    public void updateLocationUseFlag(String value)
    {
        setAdhocUpdateValue(Shelf.LOCATION_USE_FLAG, value) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.BANK_NO, source, addvalue);
    }

    /**
     * ベイ(<code>BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.BAY_NO, source, addvalue);
    }

    /**
     * レベル(<code>LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.LEVEL_NO, source, addvalue);
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateAddressNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.ADDRESS_NO, source, addvalue);
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePriorityWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.PRIORITY, source, addvalue);
    }

    /**
     * 荷幅(<code>WIDTH</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWidthWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Shelf.WIDTH, source, addvalue);
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
        return "$Id: ShelfAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
