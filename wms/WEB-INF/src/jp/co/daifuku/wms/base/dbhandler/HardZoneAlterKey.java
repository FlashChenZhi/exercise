// $Id: HardZoneAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * HARDZONE用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class HardZoneAlterKey
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
    public static final StoreMetaData $storeMetaData = HardZone.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public HardZoneAlterKey()
    {
        super(HardZone.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ハードゾーンID(<code>HARD_ZONE_ID</code>)<br>
     * 文字列の検索値をハードゾーンID(<code>HARD_ZONE_ID</code>)にセットします。
     */
    public void setHardZoneId(String value)
    {
        setKey(HardZone.HARD_ZONE_ID, value) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneId(String[] values)
    {
        setKey(HardZone.HARD_ZONE_ID, values, true) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
        setKey(HardZone.HARD_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHardZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHardZoneId(String value, String compcode)
    {
        setKey(HardZone.HARD_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
        setKey(HardZone.HARD_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の更新値をセットします。
     * @param value ハードゾーンID(<code>HARD_ZONE_ID</code>)更新値
     */
    public void updateHardZoneId(String value)
    {
        setAdhocUpdateValue(HardZone.HARD_ZONE_ID, value) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value ゾーン名称(<code>HARD_ZONE_NAME</code>)<br>
     * 文字列の検索値をゾーン名称(<code>HARD_ZONE_NAME</code>)にセットします。
     */
    public void setHardZoneName(String value)
    {
        setKey(HardZone.HARD_ZONE_NAME, value) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneName(String[] values)
    {
        setKey(HardZone.HARD_ZONE_NAME, values, true) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneName(String[] values, String and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHardZoneName(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, values, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHardZoneName(String value, String compcode)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
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
    public void setHardZoneName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setHardZoneName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の更新値をセットします。
     * @param value ゾーン名称(<code>HARD_ZONE_NAME</code>)更新値
     */
    public void updateHardZoneName(String value)
    {
        setAdhocUpdateValue(HardZone.HARD_ZONE_NAME, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(HardZone.WH_STATION_NO, value) ;
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
        setKey(HardZone.WH_STATION_NO, values, true) ;
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
        setKey(HardZone.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(HardZone.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(HardZone.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(HardZone.WH_STATION_NO, value) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 荷高(<code>HEIGHT</code>)<br>
     * 数値の検索値を荷高(<code>HEIGHT</code>)にセットします。
     */
    public void setHeight(int value)
    {
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.HEIGHT, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.HEIGHT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setHeight(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setHeight(int value, String compcode)
    {
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷高(<code>HEIGHT</code>)の更新値をセットします。
     * @param value 荷高(<code>HEIGHT</code>)更新値
     */
    public void updateHeight(int value)
    {
        setAdhocUpdateValue(HardZone.HEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順(<code>PRIORITY</code>)<br>
     * 文字列の検索値をゾーン優先順(<code>PRIORITY</code>)にセットします。
     */
    public void setPriority(String value)
    {
        setKey(HardZone.PRIORITY, value) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values)
    {
        setKey(HardZone.PRIORITY, values, true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values, String and_or_toNext)
    {
        setKey(HardZone.PRIORITY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPriority(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.PRIORITY, values, and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPriority(String value, String compcode)
    {
        setKey(HardZone.PRIORITY, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.PRIORITY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPriority(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.PRIORITY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ゾーン優先順(<code>PRIORITY</code>)の更新値をセットします。
     * @param value ゾーン優先順(<code>PRIORITY</code>)更新値
     */
    public void updatePriority(String value)
    {
        setAdhocUpdateValue(HardZone.PRIORITY, value) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 開始バンク(<code>START_BANK_NO</code>)<br>
     * 数値の検索値を開始バンク(<code>START_BANK_NO</code>)にセットします。
     */
    public void setStartBankNo(int value)
    {
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.START_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.START_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartBankNo(int value, String compcode)
    {
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始バンク(<code>START_BANK_NO</code>)の更新値をセットします。
     * @param value 開始バンク(<code>START_BANK_NO</code>)更新値
     */
    public void updateStartBankNo(int value)
    {
        setAdhocUpdateValue(HardZone.START_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 開始ベイ(<code>START_BAY_NO</code>)<br>
     * 数値の検索値を開始ベイ(<code>START_BAY_NO</code>)にセットします。
     */
    public void setStartBayNo(int value)
    {
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.START_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.START_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartBayNo(int value, String compcode)
    {
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の更新値をセットします。
     * @param value 開始ベイ(<code>START_BAY_NO</code>)更新値
     */
    public void updateStartBayNo(int value)
    {
        setAdhocUpdateValue(HardZone.START_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)<br>
     * 数値の検索値を開始レベル(<code>START_LEVEL_NO</code>)にセットします。
     */
    public void setStartLevelNo(int value)
    {
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.START_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.START_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStartLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStartLevelNo(int value, String compcode)
    {
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の更新値をセットします。
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)更新値
     */
    public void updateStartLevelNo(int value)
    {
        setAdhocUpdateValue(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 終了バンク(<code>END_BANK_NO</code>)<br>
     * 数値の検索値を終了バンク(<code>END_BANK_NO</code>)にセットします。
     */
    public void setEndBankNo(int value)
    {
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.END_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.END_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndBankNo(int value, String compcode)
    {
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了バンク(<code>END_BANK_NO</code>)の更新値をセットします。
     * @param value 終了バンク(<code>END_BANK_NO</code>)更新値
     */
    public void updateEndBankNo(int value)
    {
        setAdhocUpdateValue(HardZone.END_BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 終了ベイ(<code>END_BAY_NO</code>)<br>
     * 数値の検索値を終了ベイ(<code>END_BAY_NO</code>)にセットします。
     */
    public void setEndBayNo(int value)
    {
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.END_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.END_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndBayNo(int value, String compcode)
    {
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の更新値をセットします。
     * @param value 終了ベイ(<code>END_BAY_NO</code>)更新値
     */
    public void updateEndBayNo(int value)
    {
        setAdhocUpdateValue(HardZone.END_BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)<br>
     * 数値の検索値を終了レベル(<code>END_LEVEL_NO</code>)にセットします。
     */
    public void setEndLevelNo(int value)
    {
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value)) ;
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
        setKey(HardZone.END_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(HardZone.END_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setEndLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setEndLevelNo(int value, String compcode)
    {
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の更新値をセットします。
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)更新値
     */
    public void updateEndLevelNo(int value)
    {
        setAdhocUpdateValue(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateHeightWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.HEIGHT, source, addvalue);
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.START_BANK_NO, source, addvalue);
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.START_BAY_NO, source, addvalue);
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateStartLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.START_LEVEL_NO, source, addvalue);
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndBankNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.END_BANK_NO, source, addvalue);
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndBayNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.END_BAY_NO, source, addvalue);
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateEndLevelNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(HardZone.END_LEVEL_NO, source, addvalue);
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
        return "$Id: HardZoneAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
