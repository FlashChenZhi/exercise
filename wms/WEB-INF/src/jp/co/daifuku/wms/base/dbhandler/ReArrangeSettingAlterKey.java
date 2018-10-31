// $Id: ReArrangeSettingAlterKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.ReArrangeSetting;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * REARRANGESETTING用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class ReArrangeSettingAlterKey
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
    public static final StoreMetaData $storeMetaData = ReArrangeSetting.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public ReArrangeSettingAlterKey()
    {
        super(ReArrangeSetting.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールNo.(<code>SCHEDULE_NO</code>)<br>
     * 文字列の検索値をスケジュールNo.(<code>SCHEDULE_NO</code>)にセットします。
     */
    public void setScheduleNo(String value)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, values, true) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setScheduleNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setScheduleNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の更新値をセットします。
     * @param value スケジュールNo.(<code>SCHEDULE_NO</code>)更新値
     */
    public void updateScheduleNo(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.SCHEDULE_NO, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
     */
    public void setWhStationNo(String value)
    {
        setKey(ReArrangeSetting.WH_STATION_NO, value) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, values, true) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.WH_STATION_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をアイルステーションNo.(<code>AISLE_STATION_NO</code>)にセットします。
     */
    public void setAisleStationNo(String value)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, values, true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
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
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の更新値をセットします。
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)更新値
     */
    public void updateAisleStationNo(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.AISLE_STATION_NO, value) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)<br>
     * 文字列の検索値をソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)にセットします。
     */
    public void setSoftzoneType(String value)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneType(String[] values)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, values, true) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneType(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftzoneType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftzoneType(String value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
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
    public void setSoftzoneType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftzoneType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の更新値をセットします。
     * @param value ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)更新値
     */
    public void updateSoftzoneType(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.SOFTZONE_TYPE, value) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)<br>
     * 文字列の検索値を荷姿最適化区分(<code>LOADSIZE_TYPE</code>)にセットします。
     */
    public void setLoadsizeType(String value)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeType(String[] values)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, values, true) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeType(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadsizeType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadsizeType(String value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
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
    public void setLoadsizeType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadsizeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の更新値をセットします。
     * @param value 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)更新値
     */
    public void updateLoadsizeType(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.LOADSIZE_TYPE, value) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化区分(<code>VACANT_TYPE</code>)<br>
     * 文字列の検索値を空棚最適化区分(<code>VACANT_TYPE</code>)にセットします。
     */
    public void setVacantType(String value)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantType(String[] values)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, values, true) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantType(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setVacantType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setVacantType(String value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
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
    public void setVacantType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setVacantType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の更新値をセットします。
     * @param value 空棚最適化区分(<code>VACANT_TYPE</code>)更新値
     */
    public void updateVacantType(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.VACANT_TYPE, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を状態(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.STATUS_FLAG, value) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)<br>
     * 数値の検索値をソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)にセットします。
     */
    public void setSoftzoneAllocationQty(int value)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneAllocationQty(int[] values)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneAllocationQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftzoneAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftzoneAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setSoftzoneAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftzoneAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の更新値をセットします。
     * @param value ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)更新値
     */
    public void updateSoftzoneAllocationQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)<br>
     * 数値の検索値を荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)にセットします。
     */
    public void setLoadsizeAllocationQty(int value)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeAllocationQty(int[] values)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeAllocationQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadsizeAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadsizeAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setLoadsizeAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadsizeAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の更新値をセットします。
     * @param value 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)更新値
     */
    public void updateLoadsizeAllocationQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)<br>
     * 数値の検索値を空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)にセットします。
     */
    public void setVacantAllocationQty(int value)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantAllocationQty(int[] values)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantAllocationQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setVacantAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setVacantAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setVacantAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setVacantAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の更新値をセットします。
     * @param value 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)更新値
     */
    public void updateVacantAllocationQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)<br>
     * 数値の検索値をソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)にセットします。
     */
    public void setSoftzoneResultQty(int value)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneResultQty(int[] values)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneResultQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftzoneResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftzoneResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setSoftzoneResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftzoneResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の更新値をセットします。
     * @param value ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)更新値
     */
    public void updateSoftzoneResultQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)<br>
     * 数値の検索値を荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)にセットします。
     */
    public void setLoadsizeResultQty(int value)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeResultQty(int[] values)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeResultQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadsizeResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadsizeResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setLoadsizeResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadsizeResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の更新値をセットします。
     * @param value 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)更新値
     */
    public void updateLoadsizeResultQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)<br>
     * 数値の検索値を空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)にセットします。
     */
    public void setVacantResultQty(int value)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantResultQty(int[] values)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setVacantResultQty(int[] values, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setVacantResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setVacantResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
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
    public void setVacantResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setVacantResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の更新値をセットします。
     * @param value 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)更新値
     */
    public void updateVacantResultQty(int value)
    {
        setAdhocUpdateValue(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(ReArrangeSetting.REGIST_DATE, value) ;
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
        setKey(ReArrangeSetting.REGIST_DATE, values, true) ;
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
        setKey(ReArrangeSetting.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ReArrangeSetting.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(ReArrangeSetting.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, value) ;
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
        setKey(ReArrangeSetting.REGIST_PNAME, values, true) ;
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
        setKey(ReArrangeSetting.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, values, true) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(ReArrangeSetting.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(ReArrangeSetting.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSoftzoneAllocationQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, source, addvalue);
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLoadsizeAllocationQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, source, addvalue);
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateVacantAllocationQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.VACANT_ALLOCATION_QTY, source, addvalue);
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSoftzoneResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.SOFTZONE_RESULT_QTY, source, addvalue);
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLoadsizeResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.LOADSIZE_RESULT_QTY, source, addvalue);
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateVacantResultQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(ReArrangeSetting.VACANT_RESULT_QTY, source, addvalue);
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
        return "$Id: ReArrangeSettingAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
