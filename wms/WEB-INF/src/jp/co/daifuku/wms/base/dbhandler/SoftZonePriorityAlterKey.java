// $Id: SoftZonePriorityAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SOFTZONEPRIORITY用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class SoftZonePriorityAlterKey
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
    public static final StoreMetaData $storeMetaData = SoftZonePriority.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public SoftZonePriorityAlterKey()
    {
        super(SoftZonePriority.STORE_NAME) ;
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
        setKey(SoftZonePriority.WH_STATION_NO, value) ;
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
        setKey(SoftZonePriority.WH_STATION_NO, values, true) ;
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
        setKey(SoftZonePriority.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(SoftZonePriority.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(SoftZonePriority.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(SoftZonePriority.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(SoftZonePriority.WH_STATION_NO, value) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をソフトゾーンID(<code>SOFT_ZONE_ID</code>)にセットします。
     */
    public void setSoftZoneId(String value)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, values, true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values, String and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の更新値をセットします。
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)更新値
     */
    public void updateSoftZoneId(String value)
    {
        setAdhocUpdateValue(SoftZonePriority.SOFT_ZONE_ID, value) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)<br>
     * 文字列の検索値を優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)にセットします。
     */
    public void setPrioritySoftZone(String value)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrioritySoftZone(String[] values)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, values, true) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrioritySoftZone(String[] values, String and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPrioritySoftZone(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, values, and_or_toNext) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPrioritySoftZone(String value, String compcode)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value, compcode, "", "", true) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
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
    public void setPrioritySoftZone(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPrioritySoftZone(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の更新値をセットします。
     * @param value 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)更新値
     */
    public void updatePrioritySoftZone(String value)
    {
        setAdhocUpdateValue(SoftZonePriority.PRIORITY_SOFT_ZONE, value) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順(<code>PRIORITY</code>)<br>
     * 数値の検索値をゾーン優先順(<code>PRIORITY</code>)にセットします。
     */
    public void setPriority(int value)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(int[] values)
    {
        setKey(SoftZonePriority.PRIORITY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(int[] values, String and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPriority(int[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPriority(int value, String compcode)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ゾーン優先順(<code>PRIORITY</code>)の更新値をセットします。
     * @param value ゾーン優先順(<code>PRIORITY</code>)更新値
     */
    public void updatePriority(int value)
    {
        setAdhocUpdateValue(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePriorityWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(SoftZonePriority.PRIORITY, source, addvalue);
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
        return "$Id: SoftZonePriorityAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
