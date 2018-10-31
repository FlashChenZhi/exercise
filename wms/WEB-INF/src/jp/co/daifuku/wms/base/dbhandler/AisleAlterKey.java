// $Id: AisleAlterKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * AISLE用の更新キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class AisleAlterKey
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
    public static final StoreMetaData $storeMetaData = Aisle.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AisleAlterKey()
    {
        super(Aisle.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo.(<code>STATION_NO</code>)<br>
     * 文字列の検索値をステーションNo.(<code>STATION_NO</code>)にセットします。
     */
    public void setStationNo(String value)
    {
        setKey(Aisle.STATION_NO, value) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values)
    {
        setKey(Aisle.STATION_NO, values, true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(Aisle.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Aisle.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(Aisle.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo.(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo.(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(Aisle.STATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(Aisle.WH_STATION_NO, value) ;
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
        setKey(Aisle.WH_STATION_NO, values, true) ;
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
        setKey(Aisle.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Aisle.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Aisle.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Aisle.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(Aisle.WH_STATION_NO, value) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value アイルNo.(<code>AISLE_NO</code>)<br>
     * 文字列の検索値をアイルNo.(<code>AISLE_NO</code>)にセットします。
     */
    public void setAisleNo(String value)
    {
        setKey(Aisle.AISLE_NO, value) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNo(String[] values)
    {
        setKey(Aisle.AISLE_NO, values, true) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNo(String[] values, String and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAisleNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, values, and_or_toNext) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAisleNo(String value, String compcode)
    {
        setKey(Aisle.AISLE_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
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
    public void setAisleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAisleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイルNo.(<code>AISLE_NO</code>)の更新値をセットします。
     * @param value アイルNo.(<code>AISLE_NO</code>)更新値
     */
    public void updateAisleNo(String value)
    {
        setAdhocUpdateValue(Aisle.AISLE_NO, value) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をコントローラーNo.(<code>CONTROLLER_NO</code>)にセットします。
     */
    public void setControllerNo(String value)
    {
        setKey(Aisle.CONTROLLER_NO, value) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values)
    {
        setKey(Aisle.CONTROLLER_NO, values, true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values, String and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の更新値をセットします。
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)更新値
     */
    public void updateControllerNo(String value)
    {
        setAdhocUpdateValue(Aisle.CONTROLLER_NO, value) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)<br>
     * 文字列の検索値をダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)にセットします。
     */
    public void setDoubleDeepKind(String value)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDoubleDeepKind(String[] values)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, values, true) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDoubleDeepKind(String[] values, String and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDoubleDeepKind(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, values, and_or_toNext) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDoubleDeepKind(String value, String compcode)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, "", "", true) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
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
    public void setDoubleDeepKind(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDoubleDeepKind(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の更新値をセットします。
     * @param value ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)更新値
     */
    public void updateDoubleDeepKind(String value)
    {
        setAdhocUpdateValue(Aisle.DOUBLE_DEEP_KIND, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS</code>)<br>
     * 文字列の検索値を状態(<code>STATUS</code>)にセットします。
     */
    public void setStatus(String value)
    {
        setKey(Aisle.STATUS, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values)
    {
        setKey(Aisle.STATUS, values, true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values, String and_or_toNext)
    {
        setKey(Aisle.STATUS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatus(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.STATUS, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatus(String value, String compcode)
    {
        setKey(Aisle.STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
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
    public void setStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS</code>)更新値
     */
    public void updateStatus(String value)
    {
        setAdhocUpdateValue(Aisle.STATUS, value) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)<br>
     * 文字列の検索値を在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)にセットします。
     */
    public void setInventoryCheckFlag(String value)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlag(String[] values)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, values, true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlag(String[] values, String and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setInventoryCheckFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setInventoryCheckFlag(String value, String compcode)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
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
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の更新値をセットします。
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)更新値
     */
    public void updateInventoryCheckFlag(String value)
    {
        setAdhocUpdateValue(Aisle.INVENTORY_CHECK_FLAG, value) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 最大搬送可能数(<code>MAX_CARRY</code>)<br>
     * 数値の検索値を最大搬送可能数(<code>MAX_CARRY</code>)にセットします。
     */
    public void setMaxCarry(int value)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxCarry(int[] values)
    {
        setKey(Aisle.MAX_CARRY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxCarry(int[] values, String and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxCarry(int[] values, boolean and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxCarry(int value, String compcode)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
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
    public void setMaxCarry(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxCarry(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の更新値をセットします。
     * @param value 最大搬送可能数(<code>MAX_CARRY</code>)更新値
     */
    public void updateMaxCarry(int value)
    {
        setAdhocUpdateValue(Aisle.MAX_CARRY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxCarryWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Aisle.MAX_CARRY, source, addvalue);
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
        return "$Id: AisleAlterKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
