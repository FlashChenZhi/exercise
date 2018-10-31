// $Id: AccessNgShelfAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.AccessNgShelf;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ACCESSNGSHELF用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class AccessNgShelfAlterKey
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
    public static final StoreMetaData $storeMetaData = AccessNgShelf.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AccessNgShelfAlterKey()
    {
        super(AccessNgShelf.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(AccessNgShelf.WH_STATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values)
    {
        setKey(AccessNgShelf.WH_STATION_NO, values, true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
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
        setKey(AccessNgShelf.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(AccessNgShelf.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
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
        setKey(AccessNgShelf.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(AccessNgShelf.WH_STATION_NO, value) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value バンク(<code>BANK_NO</code>)<br>
     * 数値の検索値をバンク(<code>BANK_NO</code>)にセットします。
     */
    public void setBankNo(int value)
    {
        setKey(AccessNgShelf.BANK_NO, HandlerUtil.toObject(value)) ;
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
        setKey(AccessNgShelf.BANK_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(AccessNgShelf.BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBankNo(int value, String compcode)
    {
        setKey(AccessNgShelf.BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(AccessNgShelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AccessNgShelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * バンク(<code>BANK_NO</code>)の更新値をセットします。
     * @param value バンク(<code>BANK_NO</code>)更新値
     */
    public void updateBankNo(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value ベイ(<code>BAY_NO</code>)<br>
     * 数値の検索値をベイ(<code>BAY_NO</code>)にセットします。
     */
    public void setBayNo(int value)
    {
        setKey(AccessNgShelf.BAY_NO, HandlerUtil.toObject(value)) ;
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
        setKey(AccessNgShelf.BAY_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(AccessNgShelf.BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBayNo(int value, String compcode)
    {
        setKey(AccessNgShelf.BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(AccessNgShelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AccessNgShelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ベイ(<code>BAY_NO</code>)の更新値をセットします。
     * @param value ベイ(<code>BAY_NO</code>)更新値
     */
    public void updateBayNo(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value レベル(<code>LEVEL_NO</code>)<br>
     * 数値の検索値をレベル(<code>LEVEL_NO</code>)にセットします。
     */
    public void setLevelNo(int value)
    {
        setKey(AccessNgShelf.LEVEL_NO, HandlerUtil.toObject(value)) ;
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
        setKey(AccessNgShelf.LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(AccessNgShelf.LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLevelNo(int value, String compcode)
    {
        setKey(AccessNgShelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(AccessNgShelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AccessNgShelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * レベル(<code>LEVEL_NO</code>)の更新値をセットします。
     * @param value レベル(<code>LEVEL_NO</code>)更新値
     */
    public void updateLevelNo(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 荷幅(<code>WIDTH</code>)<br>
     * 数値の検索値を荷幅(<code>WIDTH</code>)にセットします。
     */
    public void setWidth(int value)
    {
        setKey(AccessNgShelf.WIDTH, HandlerUtil.toObject(value)) ;
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
        setKey(AccessNgShelf.WIDTH, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(AccessNgShelf.WIDTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWidth(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.WIDTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWidth(int value, String compcode)
    {
        setKey(AccessNgShelf.WIDTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(AccessNgShelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AccessNgShelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷幅(<code>WIDTH</code>)の更新値をセットします。
     * @param value 荷幅(<code>WIDTH</code>)更新値
     */
    public void updateWidth(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.WIDTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 開始アドレス(<code>START_ADDRESS_NO</code>)<br>
     * 数値の検索値を開始アドレス(<code>START_ADDRESS_NO</code>)にセットします。
     */
    public void setStartAddressNo(int value)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartAddressNo(int[] values)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartAddressNo(int[] values, String and_or_toNext)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartAddressNo(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartAddressNo(int value, String compcode)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
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
    public void setStartAddressNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartAddressNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.START_ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)の更新値をセットします。
     * @param value 開始アドレス(<code>START_ADDRESS_NO</code>)更新値
     */
    public void updateStartAddressNo(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.START_ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 終了アドレス(<code>END_ADDRESS_NO</code>)<br>
     * 数値の検索値を終了アドレス(<code>END_ADDRESS_NO</code>)にセットします。
     */
    public void setEndAddressNo(int value)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndAddressNo(int[] values)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndAddressNo(int[] values, String and_or_toNext)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndAddressNo(int[] values, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndAddressNo(int value, String compcode)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
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
    public void setEndAddressNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndAddressNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AccessNgShelf.END_ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)の更新値をセットします。
     * @param value 終了アドレス(<code>END_ADDRESS_NO</code>)更新値
     */
    public void updateEndAddressNo(int value)
    {
        setAdhocUpdateValue(AccessNgShelf.END_ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.BANK_NO, source, addvalue);
    }

    /**
     * ベイ(<code>BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.BAY_NO, source, addvalue);
    }

    /**
     * レベル(<code>LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.LEVEL_NO, source, addvalue);
    }

    /**
     * 荷幅(<code>WIDTH</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWidthWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.WIDTH, source, addvalue);
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartAddressNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.START_ADDRESS_NO, source, addvalue);
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndAddressNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AccessNgShelf.END_ADDRESS_NO, source, addvalue);
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
        return "$Id: AccessNgShelfAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
