// $Id: PCTItemAlterKey.java 3213 2009-03-02 06:59:20Z arai $
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
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTITEM用の更新キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTItemAlterKey
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
    public static final StoreMetaData $storeMetaData = PCTItem.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PCTItemAlterKey()
    {
        super(PCTItem.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)<br>
     * 文字列の検索値をシステム管理区分(<code>MANAGEMENT_TYPE</code>)にセットします。
     */
    public void setManagementType(String value)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, value) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementType(String[] values)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, values, true) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementType(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setManagementType(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setManagementType(String value, String compcode)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
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
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setManagementType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>)の更新値をセットします。
     * @param value システム管理区分(<code>MANAGEMENT_TYPE</code>)更新値
     */
    public void updateManagementType(String value)
    {
        setAdhocUpdateValue(PCTItem.MANAGEMENT_TYPE, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値を荷主コード(<code>CONSIGNOR_CODE</code>)にセットします。
     */
    public void setConsignorCode(String value)
    {
        setKey(PCTItem.CONSIGNOR_CODE, value) ;
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
        setKey(PCTItem.CONSIGNOR_CODE, values, true) ;
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
        setKey(PCTItem.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(PCTItem.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(PCTItem.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の更新値をセットします。
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)更新値
     */
    public void updateConsignorCode(String value)
    {
        setAdhocUpdateValue(PCTItem.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)<br>
     * 文字列の検索値を荷主名称(<code>CONSIGNOR_NAME</code>)にセットします。
     */
    public void setConsignorName(String value)
    {
        setKey(PCTItem.CONSIGNOR_NAME, value) ;
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
        setKey(PCTItem.CONSIGNOR_NAME, values, true) ;
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
        setKey(PCTItem.CONSIGNOR_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setConsignorName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.CONSIGNOR_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setConsignorName(String value, String compcode)
    {
        setKey(PCTItem.CONSIGNOR_NAME, value, compcode, "", "", true) ;
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
        setKey(PCTItem.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の更新値をセットします。
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)更新値
     */
    public void updateConsignorName(String value)
    {
        setAdhocUpdateValue(PCTItem.CONSIGNOR_NAME, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値を商品コード(<code>ITEM_CODE</code>)にセットします。
     */
    public void setItemCode(String value)
    {
        setKey(PCTItem.ITEM_CODE, value) ;
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
        setKey(PCTItem.ITEM_CODE, values, true) ;
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
        setKey(PCTItem.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(PCTItem.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(PCTItem.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品コード(<code>ITEM_CODE</code>)の更新値をセットします。
     * @param value 商品コード(<code>ITEM_CODE</code>)更新値
     */
    public void updateItemCode(String value)
    {
        setAdhocUpdateValue(PCTItem.ITEM_CODE, value) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 商品名称(<code>ITEM_NAME</code>)<br>
     * 文字列の検索値を商品名称(<code>ITEM_NAME</code>)にセットします。
     */
    public void setItemName(String value)
    {
        setKey(PCTItem.ITEM_NAME, value) ;
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
        setKey(PCTItem.ITEM_NAME, values, true) ;
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
        setKey(PCTItem.ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItemName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItemName(String value, String compcode)
    {
        setKey(PCTItem.ITEM_NAME, value, compcode, "", "", true) ;
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
        setKey(PCTItem.ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 商品名称(<code>ITEM_NAME</code>)の更新値をセットします。
     * @param value 商品名称(<code>ITEM_NAME</code>)更新値
     */
    public void updateItemName(String value)
    {
        setAdhocUpdateValue(PCTItem.ITEM_NAME, value) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value JANコード(<code>JAN</code>)<br>
     * 文字列の検索値をJANコード(<code>JAN</code>)にセットします。
     */
    public void setJan(String value)
    {
        setKey(PCTItem.JAN, value) ;
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
        setKey(PCTItem.JAN, values, true) ;
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
        setKey(PCTItem.JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setJan(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.JAN, values, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setJan(String value, String compcode)
    {
        setKey(PCTItem.JAN, value, compcode, "", "", true) ;
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
        setKey(PCTItem.JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * JANコード(<code>JAN</code>)の更新値をセットします。
     * @param value JANコード(<code>JAN</code>)更新値
     */
    public void updateJan(String value)
    {
        setAdhocUpdateValue(PCTItem.JAN, value) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value ケースITF(<code>ITF</code>)<br>
     * 文字列の検索値をケースITF(<code>ITF</code>)にセットします。
     */
    public void setItf(String value)
    {
        setKey(PCTItem.ITF, value) ;
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
        setKey(PCTItem.ITF, values, true) ;
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
        setKey(PCTItem.ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setItf(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ITF, values, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setItf(String value, String compcode)
    {
        setKey(PCTItem.ITF, value, compcode, "", "", true) ;
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
        setKey(PCTItem.ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケースITF(<code>ITF</code>)の更新値をセットします。
     * @param value ケースITF(<code>ITF</code>)更新値
     */
    public void updateItf(String value)
    {
        setAdhocUpdateValue(PCTItem.ITF, value) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value ボールITF(<code>BUNDLE_ITF</code>)<br>
     * 文字列の検索値をボールITF(<code>BUNDLE_ITF</code>)にセットします。
     */
    public void setBundleItf(String value)
    {
        setKey(PCTItem.BUNDLE_ITF, value) ;
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
        setKey(PCTItem.BUNDLE_ITF, values, true) ;
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
        setKey(PCTItem.BUNDLE_ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBundleItf(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.BUNDLE_ITF, values, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBundleItf(String value, String compcode)
    {
        setKey(PCTItem.BUNDLE_ITF, value, compcode, "", "", true) ;
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
        setKey(PCTItem.BUNDLE_ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.BUNDLE_ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の更新値をセットします。
     * @param value ボールITF(<code>BUNDLE_ITF</code>)更新値
     */
    public void updateBundleItf(String value)
    {
        setAdhocUpdateValue(PCTItem.BUNDLE_ITF, value) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ロット入数(<code>LOT_ENTERING_QTY</code>)<br>
     * 数値の検索値をロット入数(<code>LOT_ENTERING_QTY</code>)にセットします。
     */
    public void setLotEnteringQty(int value)
    {
        setKey(PCTItem.LOT_ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTItem.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTItem.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLotEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOT_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLotEnteringQty(int value, String compcode)
    {
        setKey(PCTItem.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(PCTItem.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.LOT_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)の更新値をセットします。
     * @param value ロット入数(<code>LOT_ENTERING_QTY</code>)更新値
     */
    public void updateLotEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTItem.LOT_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ケース入数(<code>ENTERING_QTY</code>)<br>
     * 数値の検索値をケース入数(<code>ENTERING_QTY</code>)にセットします。
     */
    public void setEnteringQty(int value)
    {
        setKey(PCTItem.ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTItem.ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTItem.ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty(int value, String compcode)
    {
        setKey(PCTItem.ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(PCTItem.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ケース入数(<code>ENTERING_QTY</code>)の更新値をセットします。
     * @param value ケース入数(<code>ENTERING_QTY</code>)更新値
     */
    public void updateEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTItem.ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)<br>
     * 数値の検索値をボール入数(<code>BUNDLE_ENTERING_QTY</code>)にセットします。
     */
    public void setBundleEnteringQty(int value)
    {
        setKey(PCTItem.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(PCTItem.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(PCTItem.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBundleEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBundleEnteringQty(int value, String compcode)
    {
        setKey(PCTItem.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(PCTItem.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の更新値をセットします。
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)更新値
     */
    public void updateBundleEnteringQty(int value)
    {
        setAdhocUpdateValue(PCTItem.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
     * 
     * @param value 単位(<code>UNIT</code>)<br>
     * 数値の検索値を単位(<code>UNIT</code>)にセットします。
     */
    public void setUnit(int value)
    {
        setKey(PCTItem.UNIT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUnit(int[] values)
    {
        setKey(PCTItem.UNIT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUnit(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.UNIT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUnit(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.UNIT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUnit(int value, String compcode)
    {
        setKey(PCTItem.UNIT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
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
    public void setUnit(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.UNIT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 単位(<code>UNIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUnit(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.UNIT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 単位(<code>UNIT</code>)の更新値をセットします。
     * @param value 単位(<code>UNIT</code>)更新値
     */
    public void updateUnit(int value)
    {
        setAdhocUpdateValue(PCTItem.UNIT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 単重量(<code>SINGLE_WEIGHT</code>)<br>
     * 数値の検索値を単重量(<code>SINGLE_WEIGHT</code>)にセットします。
     */
    public void setSingleWeight(double value)
    {
        setKey(PCTItem.SINGLE_WEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSingleWeight(double[] values)
    {
        setKey(PCTItem.SINGLE_WEIGHT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSingleWeight(double[] values, String and_or_toNext)
    {
        setKey(PCTItem.SINGLE_WEIGHT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSingleWeight(double[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.SINGLE_WEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSingleWeight(double value, String compcode)
    {
        setKey(PCTItem.SINGLE_WEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
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
    public void setSingleWeight(double value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.SINGLE_WEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSingleWeight(double value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.SINGLE_WEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)の更新値をセットします。
     * @param value 単重量(<code>SINGLE_WEIGHT</code>)更新値
     */
    public void updateSingleWeight(double value)
    {
        setAdhocUpdateValue(PCTItem.SINGLE_WEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)<br>
     * 数値の検索値を重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)にセットします。
     */
    public void setWeightDistinctRate(int value)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWeightDistinctRate(int[] values)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWeightDistinctRate(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWeightDistinctRate(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWeightDistinctRate(int value, String compcode)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
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
    public void setWeightDistinctRate(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWeightDistinctRate(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)の更新値をセットします。
     * @param value 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)更新値
     */
    public void updateWeightDistinctRate(int value)
    {
        setAdhocUpdateValue(PCTItem.WEIGHT_DISTINCT_RATE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
     * 
     * @param value 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)<br>
     * 数値の検索値を最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)にセットします。
     */
    public void setMaxInspectionUnitQty(int value)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInspectionUnitQty(int[] values)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInspectionUnitQty(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxInspectionUnitQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxInspectionUnitQty(int value, String compcode)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
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
    public void setMaxInspectionUnitQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxInspectionUnitQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)の更新値をセットします。
     * @param value 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)更新値
     */
    public void updateMaxInspectionUnitQty(int value)
    {
        setAdhocUpdateValue(PCTItem.MAX_INSPECTION_UNIT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
     * 
     * @param value ロケーションNo.1(<code>LOCATION_NO_1</code>)<br>
     * 文字列の検索値をロケーションNo.1(<code>LOCATION_NO_1</code>)にセットします。
     */
    public void setLocationNo1(String value)
    {
        setKey(PCTItem.LOCATION_NO_1, value) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo1(String[] values)
    {
        setKey(PCTItem.LOCATION_NO_1, values, true) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo1(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationNo1(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_1, values, and_or_toNext) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationNo1(String value, String compcode)
    {
        setKey(PCTItem.LOCATION_NO_1, value, compcode, "", "", true) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
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
    public void setLocationNo1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationNo1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーションNo.1(<code>LOCATION_NO_1</code>)の更新値をセットします。
     * @param value ロケーションNo.1(<code>LOCATION_NO_1</code>)更新値
     */
    public void updateLocationNo1(String value)
    {
        setAdhocUpdateValue(PCTItem.LOCATION_NO_1, value) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
     * 
     * @param value ロケーション入数1(<code>ENTERING_QTY_1</code>)<br>
     * 数値の検索値をロケーション入数1(<code>ENTERING_QTY_1</code>)にセットします。
     */
    public void setEnteringQty1(int value)
    {
        setKey(PCTItem.ENTERING_QTY_1, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty1(int[] values)
    {
        setKey(PCTItem.ENTERING_QTY_1, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty1(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_1, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty1(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_1, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty1(int value, String compcode)
    {
        setKey(PCTItem.ENTERING_QTY_1, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
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
    public void setEnteringQty1(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_1, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEnteringQty1(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_1, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)の更新値をセットします。
     * @param value ロケーション入数1(<code>ENTERING_QTY_1</code>)更新値
     */
    public void updateEnteringQty1(int value)
    {
        setAdhocUpdateValue(PCTItem.ENTERING_QTY_1, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
     * 
     * @param value ロケーションNo.2(<code>LOCATION_NO_2</code>)<br>
     * 文字列の検索値をロケーションNo.2(<code>LOCATION_NO_2</code>)にセットします。
     */
    public void setLocationNo2(String value)
    {
        setKey(PCTItem.LOCATION_NO_2, value) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo2(String[] values)
    {
        setKey(PCTItem.LOCATION_NO_2, values, true) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo2(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationNo2(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_2, values, and_or_toNext) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationNo2(String value, String compcode)
    {
        setKey(PCTItem.LOCATION_NO_2, value, compcode, "", "", true) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
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
    public void setLocationNo2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationNo2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーションNo.2(<code>LOCATION_NO_2</code>)の更新値をセットします。
     * @param value ロケーションNo.2(<code>LOCATION_NO_2</code>)更新値
     */
    public void updateLocationNo2(String value)
    {
        setAdhocUpdateValue(PCTItem.LOCATION_NO_2, value) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
     * 
     * @param value ロケーション入数2(<code>ENTERING_QTY_2</code>)<br>
     * 数値の検索値をロケーション入数2(<code>ENTERING_QTY_2</code>)にセットします。
     */
    public void setEnteringQty2(int value)
    {
        setKey(PCTItem.ENTERING_QTY_2, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty2(int[] values)
    {
        setKey(PCTItem.ENTERING_QTY_2, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty2(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_2, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty2(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_2, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty2(int value, String compcode)
    {
        setKey(PCTItem.ENTERING_QTY_2, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
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
    public void setEnteringQty2(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_2, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEnteringQty2(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_2, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)の更新値をセットします。
     * @param value ロケーション入数2(<code>ENTERING_QTY_2</code>)更新値
     */
    public void updateEnteringQty2(int value)
    {
        setAdhocUpdateValue(PCTItem.ENTERING_QTY_2, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
     * 
     * @param value ロケーションNo.3(<code>LOCATION_NO_3</code>)<br>
     * 文字列の検索値をロケーションNo.3(<code>LOCATION_NO_3</code>)にセットします。
     */
    public void setLocationNo3(String value)
    {
        setKey(PCTItem.LOCATION_NO_3, value) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo3(String[] values)
    {
        setKey(PCTItem.LOCATION_NO_3, values, true) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo3(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_3, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationNo3(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_3, values, and_or_toNext) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationNo3(String value, String compcode)
    {
        setKey(PCTItem.LOCATION_NO_3, value, compcode, "", "", true) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
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
    public void setLocationNo3(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_3, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationNo3(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_3, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーションNo.3(<code>LOCATION_NO_3</code>)の更新値をセットします。
     * @param value ロケーションNo.3(<code>LOCATION_NO_3</code>)更新値
     */
    public void updateLocationNo3(String value)
    {
        setAdhocUpdateValue(PCTItem.LOCATION_NO_3, value) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
     * 
     * @param value ロケーション入数3(<code>ENTERING_QTY_3</code>)<br>
     * 数値の検索値をロケーション入数3(<code>ENTERING_QTY_3</code>)にセットします。
     */
    public void setEnteringQty3(int value)
    {
        setKey(PCTItem.ENTERING_QTY_3, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty3(int[] values)
    {
        setKey(PCTItem.ENTERING_QTY_3, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty3(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_3, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty3(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_3, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty3(int value, String compcode)
    {
        setKey(PCTItem.ENTERING_QTY_3, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
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
    public void setEnteringQty3(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_3, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEnteringQty3(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_3, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)の更新値をセットします。
     * @param value ロケーション入数3(<code>ENTERING_QTY_3</code>)更新値
     */
    public void updateEnteringQty3(int value)
    {
        setAdhocUpdateValue(PCTItem.ENTERING_QTY_3, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
     * 
     * @param value ロケーションNo.4(<code>LOCATION_NO_4</code>)<br>
     * 文字列の検索値をロケーションNo.4(<code>LOCATION_NO_4</code>)にセットします。
     */
    public void setLocationNo4(String value)
    {
        setKey(PCTItem.LOCATION_NO_4, value) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo4(String[] values)
    {
        setKey(PCTItem.LOCATION_NO_4, values, true) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationNo4(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_4, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationNo4(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_4, values, and_or_toNext) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationNo4(String value, String compcode)
    {
        setKey(PCTItem.LOCATION_NO_4, value, compcode, "", "", true) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
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
    public void setLocationNo4(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_4, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationNo4(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LOCATION_NO_4, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーションNo.4(<code>LOCATION_NO_4</code>)の更新値をセットします。
     * @param value ロケーションNo.4(<code>LOCATION_NO_4</code>)更新値
     */
    public void updateLocationNo4(String value)
    {
        setAdhocUpdateValue(PCTItem.LOCATION_NO_4, value) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
     * 
     * @param value ロケーション入数4(<code>ENTERING_QTY_4</code>)<br>
     * 数値の検索値をロケーション入数4(<code>ENTERING_QTY_4</code>)にセットします。
     */
    public void setEnteringQty4(int value)
    {
        setKey(PCTItem.ENTERING_QTY_4, HandlerUtil.toObject(value)) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty4(int[] values)
    {
        setKey(PCTItem.ENTERING_QTY_4, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty4(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_4, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEnteringQty4(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_4, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEnteringQty4(int value, String compcode)
    {
        setKey(PCTItem.ENTERING_QTY_4, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
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
    public void setEnteringQty4(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_4, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setEnteringQty4(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.ENTERING_QTY_4, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)の更新値をセットします。
     * @param value ロケーション入数4(<code>ENTERING_QTY_4</code>)更新値
     */
    public void updateEnteringQty4(int value)
    {
        setAdhocUpdateValue(PCTItem.ENTERING_QTY_4, HandlerUtil.toObject(value)) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
     * 
     * @param value メッセージ(<code>INFORMATION</code>)<br>
     * 文字列の検索値をメッセージ(<code>INFORMATION</code>)にセットします。
     */
    public void setInformation(String value)
    {
        setKey(PCTItem.INFORMATION, value) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInformation(String[] values)
    {
        setKey(PCTItem.INFORMATION, values, true) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInformation(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.INFORMATION, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setInformation(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.INFORMATION, values, and_or_toNext) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setInformation(String value, String compcode)
    {
        setKey(PCTItem.INFORMATION, value, compcode, "", "", true) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
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
    public void setInformation(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.INFORMATION, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メッセージ(<code>INFORMATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setInformation(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.INFORMATION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * メッセージ(<code>INFORMATION</code>)の更新値をセットします。
     * @param value メッセージ(<code>INFORMATION</code>)更新値
     */
    public void updateInformation(String value)
    {
        setAdhocUpdateValue(PCTItem.INFORMATION, value) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 賞味期間(<code>USE_BY_PERIOD</code>)<br>
     * 文字列の検索値を賞味期間(<code>USE_BY_PERIOD</code>)にセットします。
     */
    public void setUseByPeriod(String value)
    {
        setKey(PCTItem.USE_BY_PERIOD, value) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUseByPeriod(String[] values)
    {
        setKey(PCTItem.USE_BY_PERIOD, values, true) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUseByPeriod(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.USE_BY_PERIOD, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUseByPeriod(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.USE_BY_PERIOD, values, and_or_toNext) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUseByPeriod(String value, String compcode)
    {
        setKey(PCTItem.USE_BY_PERIOD, value, compcode, "", "", true) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
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
    public void setUseByPeriod(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.USE_BY_PERIOD, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUseByPeriod(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.USE_BY_PERIOD, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 賞味期間(<code>USE_BY_PERIOD</code>)の更新値をセットします。
     * @param value 賞味期間(<code>USE_BY_PERIOD</code>)更新値
     */
    public void updateUseByPeriod(String value)
    {
        setAdhocUpdateValue(PCTItem.USE_BY_PERIOD, value) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)<br>
     * 文字列の検索値を入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)にセットします。
     */
    public void setInstockLimitDate(String value)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, value) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInstockLimitDate(String[] values)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, values, true) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInstockLimitDate(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setInstockLimitDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, values, and_or_toNext) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setInstockLimitDate(String value, String compcode)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
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
    public void setInstockLimitDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setInstockLimitDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.INSTOCK_LIMIT_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)の更新値をセットします。
     * @param value 入荷限度日(<code>INSTOCK_LIMIT_DATE</code>)更新値
     */
    public void updateInstockLimitDate(String value)
    {
        setAdhocUpdateValue(PCTItem.INSTOCK_LIMIT_DATE, value) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)<br>
     * 文字列の検索値を出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)にセットします。
     */
    public void setShippingLimitDate(String value)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, value) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShippingLimitDate(String[] values)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, values, true) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShippingLimitDate(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setShippingLimitDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, values, and_or_toNext) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setShippingLimitDate(String value, String compcode)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
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
    public void setShippingLimitDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setShippingLimitDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.SHIPPING_LIMIT_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)の更新値をセットします。
     * @param value 出荷限度日(<code>SHIPPING_LIMIT_DATE</code>)更新値
     */
    public void updateShippingLimitDate(String value)
    {
        setAdhocUpdateValue(PCTItem.SHIPPING_LIMIT_DATE, value) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)<br>
     * 文字列の検索値を最新賞味期限(<code>LATEST_USE_BY_DATE</code>)にセットします。
     */
    public void setLatestUseByDate(String value)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, value) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestUseByDate(String[] values)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, values, true) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestUseByDate(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLatestUseByDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, values, and_or_toNext) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLatestUseByDate(String value, String compcode)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
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
    public void setLatestUseByDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLatestUseByDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_USE_BY_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)の更新値をセットします。
     * @param value 最新賞味期限(<code>LATEST_USE_BY_DATE</code>)更新値
     */
    public void updateLatestUseByDate(String value)
    {
        setAdhocUpdateValue(PCTItem.LATEST_USE_BY_DATE, value) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)<br>
     * 文字列の検索値を最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)にセットします。
     */
    public void setLatestManufacutureDate(String value)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, value) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestManufacutureDate(String[] values)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, values, true) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestManufacutureDate(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLatestManufacutureDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLatestManufacutureDate(String value, String compcode)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
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
    public void setLatestManufacutureDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLatestManufacutureDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_MANUFACUTURE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)の更新値をセットします。
     * @param value 最新製造日(<code>LATEST_MANUFACUTURE_DATE</code>)更新値
     */
    public void updateLatestManufacutureDate(String value)
    {
        setAdhocUpdateValue(PCTItem.LATEST_MANUFACUTURE_DATE, value) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)<br>
     * 文字列の検索値を最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)にセットします。
     */
    public void setLatestRetrievalDate(String value)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, value) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestRetrievalDate(String[] values)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, values, true) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestRetrievalDate(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLatestRetrievalDate(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, values, and_or_toNext) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLatestRetrievalDate(String value, String compcode)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
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
    public void setLatestRetrievalDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLatestRetrievalDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_RETRIEVAL_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)の更新値をセットします。
     * @param value 最新出庫日(<code>LATEST_RETRIEVAL_DATE</code>)更新値
     */
    public void updateLatestRetrievalDate(String value)
    {
        setAdhocUpdateValue(PCTItem.LATEST_RETRIEVAL_DATE, value) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 最新在庫(<code>LATEST_STOCK</code>)<br>
     * 文字列の検索値を最新在庫(<code>LATEST_STOCK</code>)にセットします。
     */
    public void setLatestStock(String value)
    {
        setKey(PCTItem.LATEST_STOCK, value) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestStock(String[] values)
    {
        setKey(PCTItem.LATEST_STOCK, values, true) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLatestStock(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_STOCK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLatestStock(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_STOCK, values, and_or_toNext) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLatestStock(String value, String compcode)
    {
        setKey(PCTItem.LATEST_STOCK, value, compcode, "", "", true) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
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
    public void setLatestStock(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LATEST_STOCK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLatestStock(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LATEST_STOCK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最新在庫(<code>LATEST_STOCK</code>)の更新値をセットします。
     * @param value 最新在庫(<code>LATEST_STOCK</code>)更新値
     */
    public void updateLatestStock(String value)
    {
        setAdhocUpdateValue(PCTItem.LATEST_STOCK, value) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 最古在庫(<code>OLDEST_STOCK</code>)<br>
     * 文字列の検索値を最古在庫(<code>OLDEST_STOCK</code>)にセットします。
     */
    public void setOldestStock(String value)
    {
        setKey(PCTItem.OLDEST_STOCK, value) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOldestStock(String[] values)
    {
        setKey(PCTItem.OLDEST_STOCK, values, true) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOldestStock(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.OLDEST_STOCK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOldestStock(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.OLDEST_STOCK, values, and_or_toNext) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOldestStock(String value, String compcode)
    {
        setKey(PCTItem.OLDEST_STOCK, value, compcode, "", "", true) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
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
    public void setOldestStock(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.OLDEST_STOCK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOldestStock(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.OLDEST_STOCK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最古在庫(<code>OLDEST_STOCK</code>)の更新値をセットします。
     * @param value 最古在庫(<code>OLDEST_STOCK</code>)更新値
     */
    public void updateOldestStock(String value)
    {
        setAdhocUpdateValue(PCTItem.OLDEST_STOCK, value) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 管理フラグ(<code>MANAGEMENT_FLAG</code>)<br>
     * 文字列の検索値を管理フラグ(<code>MANAGEMENT_FLAG</code>)にセットします。
     */
    public void setManagementFlag(String value)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, value) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementFlag(String[] values)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, values, true) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setManagementFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setManagementFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, values, and_or_toNext) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setManagementFlag(String value, String compcode)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
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
    public void setManagementFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setManagementFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.MANAGEMENT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 管理フラグ(<code>MANAGEMENT_FLAG</code>)の更新値をセットします。
     * @param value 管理フラグ(<code>MANAGEMENT_FLAG</code>)更新値
     */
    public void updateManagementFlag(String value)
    {
        setAdhocUpdateValue(PCTItem.MANAGEMENT_FLAG, value) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 上限在庫数(<code>UPPER_QTY</code>)<br>
     * 数値の検索値を上限在庫数(<code>UPPER_QTY</code>)にセットします。
     */
    public void setUpperQty(int value)
    {
        setKey(PCTItem.UPPER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQty(int[] values)
    {
        setKey(PCTItem.UPPER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpperQty(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.UPPER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpperQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.UPPER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpperQty(int value, String compcode)
    {
        setKey(PCTItem.UPPER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
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
    public void setUpperQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpperQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.UPPER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 上限在庫数(<code>UPPER_QTY</code>)の更新値をセットします。
     * @param value 上限在庫数(<code>UPPER_QTY</code>)更新値
     */
    public void updateUpperQty(int value)
    {
        setAdhocUpdateValue(PCTItem.UPPER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 下限在庫数(<code>LOWER_QTY</code>)<br>
     * 数値の検索値を下限在庫数(<code>LOWER_QTY</code>)にセットします。
     */
    public void setLowerQty(int value)
    {
        setKey(PCTItem.LOWER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQty(int[] values)
    {
        setKey(PCTItem.LOWER_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLowerQty(int[] values, String and_or_toNext)
    {
        setKey(PCTItem.LOWER_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLowerQty(int[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LOWER_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLowerQty(int value, String compcode)
    {
        setKey(PCTItem.LOWER_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
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
    public void setLowerQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLowerQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LOWER_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 下限在庫数(<code>LOWER_QTY</code>)の更新値をセットします。
     * @param value 下限在庫数(<code>LOWER_QTY</code>)更新値
     */
    public void updateLowerQty(int value)
    {
        setAdhocUpdateValue(PCTItem.LOWER_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終使用日(<code>LAST_USED_DATE</code>)<br>
     * 日付の検索値を最終使用日(<code>LAST_USED_DATE</code>)にセットします。
     */
    public void setLastUsedDate(Date value)
    {
        setKey(PCTItem.LAST_USED_DATE, value) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDate(Date[] values)
    {
        setKey(PCTItem.LAST_USED_DATE, values, true) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDate(Date[] values, String and_or_toNext)
    {
        setKey(PCTItem.LAST_USED_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUsedDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LAST_USED_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUsedDate(Date value, String compcode)
    {
        setKey(PCTItem.LAST_USED_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
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
    public void setLastUsedDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTItem.LAST_USED_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUsedDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTItem.LAST_USED_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の更新値をセットします。
     * @param value 最終使用日(<code>LAST_USED_DATE</code>)更新値
     */
    public void updateLastUsedDate(Date value)
    {
        setAdhocUpdateValue(PCTItem.LAST_USED_DATE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(PCTItem.REGIST_DATE, value) ;
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
        setKey(PCTItem.REGIST_DATE, values, true) ;
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
        setKey(PCTItem.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(PCTItem.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTItem.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(PCTItem.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(PCTItem.REGIST_PNAME, value) ;
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
        setKey(PCTItem.REGIST_PNAME, values, true) ;
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
        setKey(PCTItem.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(PCTItem.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTItem.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(PCTItem.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(PCTItem.LAST_UPDATE_DATE, value) ;
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
        setKey(PCTItem.LAST_UPDATE_DATE, values, true) ;
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
        setKey(PCTItem.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(PCTItem.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTItem.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(PCTItem.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(PCTItem.LAST_UPDATE_PNAME, value) ;
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
        setKey(PCTItem.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(PCTItem.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTItem.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(PCTItem.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTItem.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTItem.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(PCTItem.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLotEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.LOT_ENTERING_QTY, source, addvalue);
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.ENTERING_QTY, source, addvalue);
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBundleEnteringQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.BUNDLE_ENTERING_QTY, source, addvalue);
    }

    /**
     * 単位(<code>UNIT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateUnitWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.UNIT, source, addvalue);
    }

    /**
     * 単重量(<code>SINGLE_WEIGHT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSingleWeightWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.SINGLE_WEIGHT, source, addvalue);
    }

    /**
     * 重量誤差率(<code>WEIGHT_DISTINCT_RATE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateWeightDistinctRateWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.WEIGHT_DISTINCT_RATE, source, addvalue);
    }

    /**
     * 最大検品単位数(<code>MAX_INSPECTION_UNIT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxInspectionUnitQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.MAX_INSPECTION_UNIT_QTY, source, addvalue);
    }

    /**
     * ロケーション入数1(<code>ENTERING_QTY_1</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQty1WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.ENTERING_QTY_1, source, addvalue);
    }

    /**
     * ロケーション入数2(<code>ENTERING_QTY_2</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQty2WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.ENTERING_QTY_2, source, addvalue);
    }

    /**
     * ロケーション入数3(<code>ENTERING_QTY_3</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQty3WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.ENTERING_QTY_3, source, addvalue);
    }

    /**
     * ロケーション入数4(<code>ENTERING_QTY_4</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEnteringQty4WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.ENTERING_QTY_4, source, addvalue);
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateUpperQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.UPPER_QTY, source, addvalue);
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLowerQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTItem.LOWER_QTY, source, addvalue);
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
        return "$Id: PCTItemAlterKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
