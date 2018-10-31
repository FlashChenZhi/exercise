// $Id: AllocatePriorityAlterKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ALLOCATEPRIORITY用の更新キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class AllocatePriorityAlterKey
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
    public static final StoreMetaData $storeMetaData = AllocatePriority.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AllocatePriorityAlterKey()
    {
        super(AllocatePriority.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
     * 
     * @param value 引当パターンNo.(<code>ALLOCATE_NO</code>)<br>
     * 文字列の検索値を引当パターンNo.(<code>ALLOCATE_NO</code>)にセットします。
     */
    public void setAllocateNo(String value)
    {
        setKey(AllocatePriority.ALLOCATE_NO, value) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateNo(String[] values)
    {
        setKey(AllocatePriority.ALLOCATE_NO, values, true) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateNo(String[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocateNo(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NO, values, and_or_toNext) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocateNo(String value, String compcode)
    {
        setKey(AllocatePriority.ALLOCATE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
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
    public void setAllocateNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocateNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>)の更新値をセットします。
     * @param value 引当パターンNo.(<code>ALLOCATE_NO</code>)更新値
     */
    public void updateAllocateNo(String value)
    {
        setAdhocUpdateValue(AllocatePriority.ALLOCATE_NO, value) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
     * 
     * @param value 引当パターン名称(<code>ALLOCATE_NAME</code>)<br>
     * 文字列の検索値を引当パターン名称(<code>ALLOCATE_NAME</code>)にセットします。
     */
    public void setAllocateName(String value)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, value) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateName(String[] values)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, values, true) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateName(String[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocateName(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, values, and_or_toNext) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocateName(String value, String compcode)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
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
    public void setAllocateName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocateName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>)の更新値をセットします。
     * @param value 引当パターン名称(<code>ALLOCATE_NAME</code>)更新値
     */
    public void updateAllocateName(String value)
    {
        setAdhocUpdateValue(AllocatePriority.ALLOCATE_NAME, value) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 引当パターン区分(<code>ALLOCATE_TYPE</code>)<br>
     * 文字列の検索値を引当パターン区分(<code>ALLOCATE_TYPE</code>)にセットします。
     */
    public void setAllocateType(String value)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, value) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateType(String[] values)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, values, true) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateType(String[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocateType(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocateType(String value, String compcode)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
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
    public void setAllocateType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocateType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>)の更新値をセットします。
     * @param value 引当パターン区分(<code>ALLOCATE_TYPE</code>)更新値
     */
    public void updateAllocateType(String value)
    {
        setAdhocUpdateValue(AllocatePriority.ALLOCATE_TYPE, value) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
     * 
     * @param value 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)<br>
     * 文字列の検索値を引当対象エリアNo.(<code>ALLOCATE_AREA</code>)にセットします。
     */
    public void setAllocateArea(String value)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, value) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateArea(String[] values)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, values, true) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocateArea(String[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocateArea(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, values, and_or_toNext) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocateArea(String value, String compcode)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, value, compcode, "", "", true) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
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
    public void setAllocateArea(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocateArea(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_AREA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)の更新値をセットします。
     * @param value 引当対象エリアNo.(<code>ALLOCATE_AREA</code>)更新値
     */
    public void updateAllocateArea(String value)
    {
        setAdhocUpdateValue(AllocatePriority.ALLOCATE_AREA, value) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 引当対象作業場(<code>STATION_NO</code>)<br>
     * 文字列の検索値を引当対象作業場(<code>STATION_NO</code>)にセットします。
     */
    public void setStationNo(String value)
    {
        setKey(AllocatePriority.STATION_NO, value) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values)
    {
        setKey(AllocatePriority.STATION_NO, values, true) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(AllocatePriority.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(AllocatePriority.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(AllocatePriority.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 引当対象作業場(<code>STATION_NO</code>)の更新値をセットします。
     * @param value 引当対象作業場(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(AllocatePriority.STATION_NO, value) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 優先順位(<code>ALLOCATE_PRIORITY</code>)<br>
     * 数値の検索値を優先順位(<code>ALLOCATE_PRIORITY</code>)にセットします。
     */
    public void setAllocatePriority(int value)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocatePriority(int[] values)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAllocatePriority(int[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAllocatePriority(int[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAllocatePriority(int value, String compcode)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
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
    public void setAllocatePriority(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAllocatePriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.ALLOCATE_PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)の更新値をセットします。
     * @param value 優先順位(<code>ALLOCATE_PRIORITY</code>)更新値
     */
    public void updateAllocatePriority(int value)
    {
        setAdhocUpdateValue(AllocatePriority.ALLOCATE_PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)<br>
     * 文字列の検索値を補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)にセットします。
     */
    public void setReplenishmentAreaType(String value)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, value) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentAreaType(String[] values)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, values, true) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReplenishmentAreaType(String[] values, String and_or_toNext)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setReplenishmentAreaType(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setReplenishmentAreaType(String value, String compcode)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
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
    public void setReplenishmentAreaType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setReplenishmentAreaType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(AllocatePriority.REPLENISHMENT_AREA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)の更新値をセットします。
     * @param value 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)更新値
     */
    public void updateReplenishmentAreaType(String value)
    {
        setAdhocUpdateValue(AllocatePriority.REPLENISHMENT_AREA_TYPE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(AllocatePriority.REGIST_DATE, value) ;
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
        setKey(AllocatePriority.REGIST_DATE, values, true) ;
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
        setKey(AllocatePriority.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(AllocatePriority.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(AllocatePriority.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AllocatePriority.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(AllocatePriority.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(AllocatePriority.REGIST_PNAME, value) ;
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
        setKey(AllocatePriority.REGIST_PNAME, values, true) ;
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
        setKey(AllocatePriority.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(AllocatePriority.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(AllocatePriority.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AllocatePriority.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(AllocatePriority.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(AllocatePriority.LAST_UPDATE_DATE, value) ;
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
        setKey(AllocatePriority.LAST_UPDATE_DATE, values, true) ;
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
        setKey(AllocatePriority.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(AllocatePriority.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(AllocatePriority.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AllocatePriority.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(AllocatePriority.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(AllocatePriority.LAST_UPDATE_PNAME, value) ;
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
        setKey(AllocatePriority.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(AllocatePriority.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(AllocatePriority.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(AllocatePriority.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(AllocatePriority.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(AllocatePriority.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(AllocatePriority.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateAllocatePriorityWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(AllocatePriority.ALLOCATE_PRIORITY, source, addvalue);
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
        return "$Id: AllocatePriorityAlterKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
