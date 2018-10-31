// $Id: WidthAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.Width;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WIDTH用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class WidthAlterKey
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
    public static final StoreMetaData $storeMetaData = Width.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public WidthAlterKey()
    {
        super(Width.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 荷幅ID(<code>WIDTH_ID</code>)<br>
     * 文字列の検索値を荷幅ID(<code>WIDTH_ID</code>)にセットします。
     */
    public void setWidthId(String value)
    {
        setKey(Width.WIDTH_ID, value) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthId(String[] values)
    {
        setKey(Width.WIDTH_ID, values, true) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthId(String[] values, String and_or_toNext)
    {
        setKey(Width.WIDTH_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWidthId(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_ID, values, and_or_toNext) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWidthId(String value, String compcode)
    {
        setKey(Width.WIDTH_ID, value, compcode, "", "", true) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
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
    public void setWidthId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.WIDTH_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWidthId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の更新値をセットします。
     * @param value 荷幅ID(<code>WIDTH_ID</code>)更新値
     */
    public void updateWidthId(String value)
    {
        setAdhocUpdateValue(Width.WIDTH_ID, value) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷幅名称(<code>WIDTH_NAME</code>)<br>
     * 文字列の検索値を荷幅名称(<code>WIDTH_NAME</code>)にセットします。
     */
    public void setWidthName(String value)
    {
        setKey(Width.WIDTH_NAME, value) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthName(String[] values)
    {
        setKey(Width.WIDTH_NAME, values, true) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthName(String[] values, String and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWidthName(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWidthName(String value, String compcode)
    {
        setKey(Width.WIDTH_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
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
    public void setWidthName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWidthName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の更新値をセットします。
     * @param value 荷幅名称(<code>WIDTH_NAME</code>)更新値
     */
    public void updateWidthName(String value)
    {
        setAdhocUpdateValue(Width.WIDTH_NAME, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(Width.WH_STATION_NO, value) ;
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
        setKey(Width.WH_STATION_NO, values, true) ;
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
        setKey(Width.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Width.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Width.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(Width.WH_STATION_NO, value) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 荷幅(<code>WIDTH</code>)<br>
     * 数値の検索値を荷幅(<code>WIDTH</code>)にセットします。
     */
    public void setWidth(int value)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value)) ;
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
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWidth(int[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWidth(int value, String compcode)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷幅(<code>WIDTH</code>)の更新値をセットします。
     * @param value 荷幅(<code>WIDTH</code>)更新値
     */
    public void updateWidth(int value)
    {
        setAdhocUpdateValue(Width.WIDTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 最大格納数(<code>MAX_STORAGE</code>)<br>
     * 数値の検索値を最大格納数(<code>MAX_STORAGE</code>)にセットします。
     */
    public void setMaxStorage(int value)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorage(int[] values)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorage(int[] values, String and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxStorage(int[] values, boolean and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxStorage(int value, String compcode)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
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
    public void setMaxStorage(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxStorage(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の更新値をセットします。
     * @param value 最大格納数(<code>MAX_STORAGE</code>)更新値
     */
    public void updateMaxStorage(int value)
    {
        setAdhocUpdateValue(Width.MAX_STORAGE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 開始バンク(<code>START_BANK_NO</code>)<br>
     * 数値の検索値を開始バンク(<code>START_BANK_NO</code>)にセットします。
     */
    public void setStartBankNo(int value)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartBankNo(int[] values)
    {
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartBankNo(int[] values, String and_or_toNext)
    {
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartBankNo(int value, String compcode)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
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
    public void setStartBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始バンク(<code>START_BANK_NO</code>)の更新値をセットします。
     * @param value 開始バンク(<code>START_BANK_NO</code>)更新値
     */
    public void updateStartBankNo(int value)
    {
        setAdhocUpdateValue(Width.START_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 開始ベイ(<code>START_BAY_NO</code>)<br>
     * 数値の検索値を開始ベイ(<code>START_BAY_NO</code>)にセットします。
     */
    public void setStartBayNo(int value)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartBayNo(int[] values)
    {
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartBayNo(int[] values, String and_or_toNext)
    {
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartBayNo(int value, String compcode)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
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
    public void setStartBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の更新値をセットします。
     * @param value 開始ベイ(<code>START_BAY_NO</code>)更新値
     */
    public void updateStartBayNo(int value)
    {
        setAdhocUpdateValue(Width.START_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)<br>
     * 数値の検索値を開始レベル(<code>START_LEVEL_NO</code>)にセットします。
     */
    public void setStartLevelNo(int value)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartLevelNo(int[] values)
    {
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStartLevelNo(int[] values, String and_or_toNext)
    {
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartLevelNo(int value, String compcode)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
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
    public void setStartLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStartLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の更新値をセットします。
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)更新値
     */
    public void updateStartLevelNo(int value)
    {
        setAdhocUpdateValue(Width.START_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 終了バンク(<code>END_BANK_NO</code>)<br>
     * 数値の検索値を終了バンク(<code>END_BANK_NO</code>)にセットします。
     */
    public void setEndBankNo(int value)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndBankNo(int[] values)
    {
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndBankNo(int[] values, String and_or_toNext)
    {
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndBankNo(int value, String compcode)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
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
    public void setEndBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了バンク(<code>END_BANK_NO</code>)の更新値をセットします。
     * @param value 終了バンク(<code>END_BANK_NO</code>)更新値
     */
    public void updateEndBankNo(int value)
    {
        setAdhocUpdateValue(Width.END_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 終了ベイ(<code>END_BAY_NO</code>)<br>
     * 数値の検索値を終了ベイ(<code>END_BAY_NO</code>)にセットします。
     */
    public void setEndBayNo(int value)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndBayNo(int[] values)
    {
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndBayNo(int[] values, String and_or_toNext)
    {
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndBayNo(int value, String compcode)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
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
    public void setEndBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の更新値をセットします。
     * @param value 終了ベイ(<code>END_BAY_NO</code>)更新値
     */
    public void updateEndBayNo(int value)
    {
        setAdhocUpdateValue(Width.END_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)<br>
     * 数値の検索値を終了レベル(<code>END_LEVEL_NO</code>)にセットします。
     */
    public void setEndLevelNo(int value)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndLevelNo(int[] values)
    {
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndLevelNo(int[] values, String and_or_toNext)
    {
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndLevelNo(int value, String compcode)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
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
    public void setEndLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEndLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の更新値をセットします。
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)更新値
     */
    public void updateEndLevelNo(int value)
    {
        setAdhocUpdateValue(Width.END_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWidthWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.WIDTH, source, addvalue);
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxStorageWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.MAX_STORAGE, source, addvalue);
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.START_BANK_NO, source, addvalue);
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.START_BAY_NO, source, addvalue);
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.START_LEVEL_NO, source, addvalue);
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.END_BANK_NO, source, addvalue);
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.END_BAY_NO, source, addvalue);
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Width.END_LEVEL_NO, source, addvalue);
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
        return "$Id: WidthAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
