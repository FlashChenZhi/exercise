// $Id: AlterKeyProductfile.java 1749 2008-12-04 00:32:00Z dmori $
// $LastChangedRevision: 1749 $
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
import jp.co.daifuku.wms.base.entity.InventoryHist;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * INVENTORYHIST用の更新キークラスです。
 *
 * @version $Revision: 1746 $, $Date: 2008-12-04 09:21:00 +0900 (木, 04 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class InventoryHistAlterKey
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
    public static final StoreMetaData $storeMetaData = InventoryHist.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public InventoryHistAlterKey()
    {
        super(InventoryHist.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(InventoryHist.CONSIGNOR_CODE, value) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, values, true) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(InventoryHist.CONSIGNOR_CODE, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値を商品コード(<code>ITEM_CODE</code>)にセットします。
     */
    public void setItemCode(String value)
    {
        setKey(InventoryHist.ITEM_CODE, value) ;
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
        setKey(InventoryHist.ITEM_CODE, values, true) ;
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
        setKey(InventoryHist.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(InventoryHist.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(InventoryHist.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventoryHist.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品コード(<code>ITEM_CODE</code>)の更新値をセットします。
     * @param value 商品コード(<code>ITEM_CODE</code>)更新値
     */
    public void updateItemCode(String value)
    {
        setAdhocUpdateValue(InventoryHist.ITEM_CODE, value) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value ロットNo(<code>LOT_NO</code>)<br>
     * 文字列の検索値をロットNo(<code>LOT_NO</code>)にセットします。
     */
    public void setLotNo(String value)
    {
        setKey(InventoryHist.LOT_NO, value) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values)
    {
        setKey(InventoryHist.LOT_NO, values, true) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
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
        setKey(InventoryHist.LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, values, and_or_toNext) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLotNo(String value, String compcode)
    {
        setKey(InventoryHist.LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
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
        setKey(InventoryHist.LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロットNo(<code>LOT_NO</code>)の更新値をセットします。
     * @param value ロットNo(<code>LOT_NO</code>)更新値
     */
    public void updateLotNo(String value)
    {
        setAdhocUpdateValue(InventoryHist.LOT_NO, value) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 集計日(<code>SUMMARY_DATE</code>)<br>
     * 文字列の検索値を集計日(<code>SUMMARY_DATE</code>)にセットします。
     */
    public void setSummaryDate(String value)
    {
        setKey(InventoryHist.SUMMARY_DATE, value) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDate(String[] values)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, true) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDate(String[] values, String and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSummaryDate(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, and_or_toNext) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSummaryDate(String value, String compcode)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
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
    public void setSummaryDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSummaryDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 集計日(<code>SUMMARY_DATE</code>)の更新値をセットします。
     * @param value 集計日(<code>SUMMARY_DATE</code>)更新値
     */
    public void updateSummaryDate(String value)
    {
        setAdhocUpdateValue(InventoryHist.SUMMARY_DATE, value) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日在庫数(<code>STOCK_QTY</code>)<br>
     * 数値の検索値を当日在庫数(<code>STOCK_QTY</code>)にセットします。
     */
    public void setStockQty(int value)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockQty(int[] values)
    {
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
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
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStockQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStockQty(int value, String compcode)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
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
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStockQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の更新値をセットします。
     * @param value 当日在庫数(<code>STOCK_QTY</code>)更新値
     */
    public void updateStockQty(int value)
    {
        setAdhocUpdateValue(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日入庫数(<code>STORAGE_QTY</code>)<br>
     * 数値の検索値を当日入庫数(<code>STORAGE_QTY</code>)にセットします。
     */
    public void setStorageQty(int value)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQty(int[] values)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQty(int[] values, String and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStorageQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStorageQty(int value, String compcode)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
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
    public void setStorageQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStorageQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の更新値をセットします。
     * @param value 当日入庫数(<code>STORAGE_QTY</code>)更新値
     */
    public void updateStorageQty(int value)
    {
        setAdhocUpdateValue(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日出庫数(<code>RETRIEVAL_QTY</code>)<br>
     * 数値の検索値を当日出庫数(<code>RETRIEVAL_QTY</code>)にセットします。
     */
    public void setRetrievalQty(int value)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQty(int[] values)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQty(int[] values, String and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRetrievalQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRetrievalQty(int value, String compcode)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
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
    public void setRetrievalQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRetrievalQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の更新値をセットします。
     * @param value 当日出庫数(<code>RETRIEVAL_QTY</code>)更新値
     */
    public void updateRetrievalQty(int value)
    {
        setAdhocUpdateValue(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStockQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(InventoryHist.STOCK_QTY, source, addvalue);
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStorageQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(InventoryHist.STORAGE_QTY, source, addvalue);
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateRetrievalQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(InventoryHist.RETRIEVAL_QTY, source, addvalue);
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
        return "$Id: AlterKeyBasefile.java 1746 2008-12-04 00:21:00Z dmori $" ;
    }
}
