// $Id: StationTypeAlterKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.StationType;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * STATIONTYPE用の更新キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class StationTypeAlterKey
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
    public static final StoreMetaData $storeMetaData = StationType.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public StationTypeAlterKey()
    {
        super(StationType.STORE_NAME) ;
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
        setKey(StationType.STATION_NO, value) ;
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
        setKey(StationType.STATION_NO, values, true) ;
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
        setKey(StationType.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(StationType.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(StationType.STATION_NO, value, compcode, "", "", true) ;
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
        setKey(StationType.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StationType.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(StationType.STATION_NO, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value クラス名(<code>CLASS_NAME</code>)<br>
     * 文字列の検索値をクラス名(<code>CLASS_NAME</code>)にセットします。
     */
    public void setClassName(String value)
    {
        setKey(StationType.CLASS_NAME, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values)
    {
        setKey(StationType.CLASS_NAME, values, true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values, String and_or_toNext)
    {
        setKey(StationType.CLASS_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setClassName(String[] values, boolean and_or_toNext)
    {
        setKey(StationType.CLASS_NAME, values, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setClassName(String value, String compcode)
    {
        setKey(StationType.CLASS_NAME, value, compcode, "", "", true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
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
    public void setClassName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StationType.CLASS_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setClassName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StationType.CLASS_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * クラス名(<code>CLASS_NAME</code>)の更新値をセットします。
     * @param value クラス名(<code>CLASS_NAME</code>)更新値
     */
    public void updateClassName(String value)
    {
        setAdhocUpdateValue(StationType.CLASS_NAME, value) ;
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
        return "$Id: StationTypeAlterKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
