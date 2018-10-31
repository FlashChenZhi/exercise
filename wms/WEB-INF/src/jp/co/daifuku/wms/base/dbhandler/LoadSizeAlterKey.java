// $Id: LoadSizeAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * LOADSIZE用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class LoadSizeAlterKey
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
    public static final StoreMetaData $storeMetaData = LoadSize.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public LoadSizeAlterKey()
    {
        super(LoadSize.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 荷姿(<code>LOAD_SIZE</code>)<br>
     * 数値の検索値を荷姿(<code>LOAD_SIZE</code>)にセットします。
     */
    public void setLoadSize(int value)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(int[] values)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadSize(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadSize(int value, String compcode)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
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
    public void setLoadSize(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadSize(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿(<code>LOAD_SIZE</code>)の更新値をセットします。
     * @param value 荷姿(<code>LOAD_SIZE</code>)更新値
     */
    public void updateLoadSize(int value)
    {
        setAdhocUpdateValue(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷姿名称(<code>LOAD_SIZE_NAME</code>)<br>
     * 文字列の検索値を荷姿名称(<code>LOAD_SIZE_NAME</code>)にセットします。
     */
    public void setLoadSizeName(String value)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeName(String[] values)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, true) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeName(String[] values, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadSizeName(String[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadSizeName(String value, String compcode)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
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
    public void setLoadSizeName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadSizeName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の更新値をセットします。
     * @param value 荷姿名称(<code>LOAD_SIZE_NAME</code>)更新値
     */
    public void updateLoadSizeName(String value)
    {
        setAdhocUpdateValue(LoadSize.LOAD_SIZE_NAME, value) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 荷長(<code>LENGTH</code>)<br>
     * 数値の検索値を荷長(<code>LENGTH</code>)にセットします。
     */
    public void setLength(int value)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLength(int[] values)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLength(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLength(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLength(int value, String compcode)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
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
    public void setLength(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLength(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷長(<code>LENGTH</code>)の更新値をセットします。
     * @param value 荷長(<code>LENGTH</code>)更新値
     */
    public void updateLength(int value)
    {
        setAdhocUpdateValue(LoadSize.LENGTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 荷高(<code>HEIGHT</code>)<br>
     * 数値の検索値を荷高(<code>HEIGHT</code>)にセットします。
     */
    public void setHeight(int value)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHeight(int[] values)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHeight(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHeight(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHeight(int value, String compcode)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
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
    public void setHeight(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setHeight(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷高(<code>HEIGHT</code>)の更新値をセットします。
     * @param value 荷高(<code>HEIGHT</code>)更新値
     */
    public void updateHeight(int value)
    {
        setAdhocUpdateValue(LoadSize.HEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLoadSizeWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(LoadSize.LOAD_SIZE, source, addvalue);
    }

    /**
     * 荷長(<code>LENGTH</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLengthWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(LoadSize.LENGTH, source, addvalue);
    }

    /**
     * 荷高(<code>HEIGHT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateHeightWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(LoadSize.HEIGHT, source, addvalue);
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
        return "$Id: LoadSizeAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
