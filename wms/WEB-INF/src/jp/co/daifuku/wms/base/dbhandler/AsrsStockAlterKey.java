// $Id: AsrsStockAlterKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.AsrsStock;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ASRSSTOCK用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class AsrsStockAlterKey
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
    public static final StoreMetaData $storeMetaData = AsrsStock.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AsrsStockAlterKey()
    {
        super(AsrsStock.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をエリアNo.(<code>AREA_NO</code>)にセットします。
     */
    public void setAreaNo(String value)
    {
        setKey(AsrsStock.AREA_NO, value) ;
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
        setKey(AsrsStock.AREA_NO, values, true) ;
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
        setKey(AsrsStock.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(AsrsStock.AREA_NO, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * エリアNo.(<code>AREA_NO</code>)の更新値をセットします。
     * @param value エリアNo.(<code>AREA_NO</code>)更新値
     */
    public void updateAreaNo(String value)
    {
        setAdhocUpdateValue(AsrsStock.AREA_NO, value) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 棚No.(<code>LOCATION_NO</code>)<br>
     * 文字列の検索値を棚No.(<code>LOCATION_NO</code>)にセットします。
     */
    public void setLocationNo(String value)
    {
        setKey(AsrsStock.LOCATION_NO, value) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo(String[] values)
    {
        setKey(AsrsStock.LOCATION_NO, values, true) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo(String[] values, String and_or_toNext)
    {
        setKey(AsrsStock.LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationNo(String value, String compcode)
    {
        setKey(AsrsStock.LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
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
    public void setLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AsrsStock.LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚No.(<code>LOCATION_NO</code>)の更新値をセットします。
     * @param value 棚No.(<code>LOCATION_NO</code>)更新値
     */
    public void updateLocationNo(String value)
    {
        setAdhocUpdateValue(AsrsStock.LOCATION_NO, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値を商品コード(<code>ITEM_CODE</code>)にセットします。
     */
    public void setItemCode(String value)
    {
        setKey(AsrsStock.ITEM_CODE, value) ;
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
        setKey(AsrsStock.ITEM_CODE, values, true) ;
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
        setKey(AsrsStock.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(AsrsStock.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品コード(<code>ITEM_CODE</code>)の更新値をセットします。
     * @param value 商品コード(<code>ITEM_CODE</code>)更新値
     */
    public void updateItemCode(String value)
    {
        setAdhocUpdateValue(AsrsStock.ITEM_CODE, value) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 商品名称(<code>ITEM_NAME</code>)<br>
     * 文字列の検索値を商品名称(<code>ITEM_NAME</code>)にセットします。
     */
    public void setItemName(String value)
    {
        setKey(AsrsStock.ITEM_NAME, value) ;
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
        setKey(AsrsStock.ITEM_NAME, values, true) ;
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
        setKey(AsrsStock.ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemName(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemName(String value, String compcode)
    {
        setKey(AsrsStock.ITEM_NAME, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品名称(<code>ITEM_NAME</code>)の更新値をセットします。
     * @param value 商品名称(<code>ITEM_NAME</code>)更新値
     */
    public void updateItemName(String value)
    {
        setAdhocUpdateValue(AsrsStock.ITEM_NAME, value) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value ロットNo.(<code>LOT_NO</code>)<br>
     * 文字列の検索値をロットNo.(<code>LOT_NO</code>)にセットします。
     */
    public void setLotNo(String value)
    {
        setKey(AsrsStock.LOT_NO, value) ;
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
        setKey(AsrsStock.LOT_NO, values, true) ;
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
        setKey(AsrsStock.LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.LOT_NO, values, and_or_toNext) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLotNo(String value, String compcode)
    {
        setKey(AsrsStock.LOT_NO, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロットNo.(<code>LOT_NO</code>)の更新値をセットします。
     * @param value ロットNo.(<code>LOT_NO</code>)更新値
     */
    public void updateLotNo(String value)
    {
        setAdhocUpdateValue(AsrsStock.LOT_NO, value) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ケース入数(<code>ENTERING_QTY</code>)<br>
     * 数値の検索値をケース入数(<code>ENTERING_QTY</code>)にセットします。
     */
    public void setEnteringQty(int value)
    {
        setKey(AsrsStock.ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(AsrsStock.ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(AsrsStock.ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty(int value, String compcode)
    {
        setKey(AsrsStock.ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(AsrsStock.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケース入数(<code>ENTERING_QTY</code>)の更新値をセットします。
     * @param value ケース入数(<code>ENTERING_QTY</code>)更新値
     */
    public void updateEnteringQty(int value)
    {
        setAdhocUpdateValue(AsrsStock.ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 在庫数(<code>STOCK_QTY</code>)<br>
     * 数値の検索値を在庫数(<code>STOCK_QTY</code>)にセットします。
     */
    public void setStockQty(int value)
    {
        setKey(AsrsStock.STOCK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockQty(int[] values)
    {
        setKey(AsrsStock.STOCK_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockQty(int[] values, String and_or_toNext)
    {
        setKey(AsrsStock.STOCK_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockQty(int[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.STOCK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockQty(int value, String compcode)
    {
        setKey(AsrsStock.STOCK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
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
    public void setStockQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AsrsStock.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStockQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫数(<code>STOCK_QTY</code>)の更新値をセットします。
     * @param value 在庫数(<code>STOCK_QTY</code>)更新値
     */
    public void updateStockQty(int value)
    {
        setAdhocUpdateValue(AsrsStock.STOCK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 入庫日時(<code>STORAGE_DATE</code>)<br>
     * 日付の検索値を入庫日時(<code>STORAGE_DATE</code>)にセットします。
     */
    public void setStorageDate(Date value)
    {
        setKey(AsrsStock.STORAGE_DATE, value) ;
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
        setKey(AsrsStock.STORAGE_DATE, values, true) ;
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
        setKey(AsrsStock.STORAGE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.STORAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageDate(Date value, String compcode)
    {
        setKey(AsrsStock.STORAGE_DATE, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.STORAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.STORAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の更新値をセットします。
     * @param value 入庫日時(<code>STORAGE_DATE</code>)更新値
     */
    public void updateStorageDate(Date value)
    {
        setAdhocUpdateValue(AsrsStock.STORAGE_DATE, value) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value JANコード(<code>JAN</code>)<br>
     * 文字列の検索値をJANコード(<code>JAN</code>)にセットします。
     */
    public void setJan(String value)
    {
        setKey(AsrsStock.JAN, value) ;
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
        setKey(AsrsStock.JAN, values, true) ;
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
        setKey(AsrsStock.JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJan(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.JAN, values, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJan(String value, String compcode)
    {
        setKey(AsrsStock.JAN, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * JANコード(<code>JAN</code>)の更新値をセットします。
     * @param value JANコード(<code>JAN</code>)更新値
     */
    public void updateJan(String value)
    {
        setAdhocUpdateValue(AsrsStock.JAN, value) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value ケースITF(<code>ITF</code>)<br>
     * 文字列の検索値をケースITF(<code>ITF</code>)にセットします。
     */
    public void setItf(String value)
    {
        setKey(AsrsStock.ITF, value) ;
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
        setKey(AsrsStock.ITF, values, true) ;
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
        setKey(AsrsStock.ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItf(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.ITF, values, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItf(String value, String compcode)
    {
        setKey(AsrsStock.ITF, value, compcode, "", "", true) ;
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
        setKey(AsrsStock.ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AsrsStock.ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケースITF(<code>ITF</code>)の更新値をセットします。
     * @param value ケースITF(<code>ITF</code>)更新値
     */
    public void updateItf(String value)
    {
        setAdhocUpdateValue(AsrsStock.ITF, value) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)<br>
     * 文字列の検索値を現在ステーションNo.(<code>CURRENT_STATION_NO</code>)にセットします。
     */
    public void setCurrentStationNo(String value)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, value) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentStationNo(String[] values)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, values, true) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentStationNo(String[] values, String and_or_toNext)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCurrentStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCurrentStationNo(String value, String compcode)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
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
    public void setCurrentStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCurrentStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.CURRENT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)の更新値をセットします。
     * @param value 現在ステーションNo.(<code>CURRENT_STATION_NO</code>)更新値
     */
    public void updateCurrentStationNo(String value)
    {
        setAdhocUpdateValue(AsrsStock.CURRENT_STATION_NO, value) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value パレットId(<code>PALLET_ID</code>)<br>
     * 文字列の検索値をパレットId(<code>PALLET_ID</code>)にセットします。
     */
    public void setPalletId(String value)
    {
        setKey(AsrsStock.PALLET_ID, value) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletId(String[] values)
    {
        setKey(AsrsStock.PALLET_ID, values, true) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
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
        setKey(AsrsStock.PALLET_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPalletId(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.PALLET_ID, values, and_or_toNext) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPalletId(String value, String compcode)
    {
        setKey(AsrsStock.PALLET_ID, value, compcode, "", "", true) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
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
        setKey(AsrsStock.PALLET_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットId(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPalletId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.PALLET_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パレットId(<code>PALLET_ID</code>)の更新値をセットします。
     * @param value パレットId(<code>PALLET_ID</code>)更新値
     */
    public void updatePalletId(String value)
    {
        setAdhocUpdateValue(AsrsStock.PALLET_ID, value) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 空パレット状態(<code>EMPTY_FLAG</code>)<br>
     * 文字列の検索値を空パレット状態(<code>EMPTY_FLAG</code>)にセットします。
     */
    public void setEmptyFlag(String value)
    {
        setKey(AsrsStock.EMPTY_FLAG, value) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmptyFlag(String[] values)
    {
        setKey(AsrsStock.EMPTY_FLAG, values, true) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmptyFlag(String[] values, String and_or_toNext)
    {
        setKey(AsrsStock.EMPTY_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEmptyFlag(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.EMPTY_FLAG, values, and_or_toNext) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEmptyFlag(String value, String compcode)
    {
        setKey(AsrsStock.EMPTY_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
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
    public void setEmptyFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AsrsStock.EMPTY_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEmptyFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.EMPTY_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>)の更新値をセットします。
     * @param value 空パレット状態(<code>EMPTY_FLAG</code>)更新値
     */
    public void updateEmptyFlag(String value)
    {
        setAdhocUpdateValue(AsrsStock.EMPTY_FLAG, value) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)<br>
     * 文字列の検索値をパレット在庫状態(<code>PALLET_STATUS_FLAG</code>)にセットします。
     */
    public void setPalletStatusFlag(String value)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, value) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletStatusFlag(String[] values)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, values, true) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPalletStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPalletStatusFlag(String value, String compcode)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
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
    public void setPalletStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPalletStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AsrsStock.PALLET_STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)の更新値をセットします。
     * @param value パレット在庫状態(<code>PALLET_STATUS_FLAG</code>)更新値
     */
    public void updatePalletStatusFlag(String value)
    {
        setAdhocUpdateValue(AsrsStock.PALLET_STATUS_FLAG, value) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AsrsStock.ENTERING_QTY, source, addvalue);
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStockQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AsrsStock.STOCK_QTY, source, addvalue);
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
        return "$Id: AsrsStockAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
