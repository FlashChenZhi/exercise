// $Id: SoftZonePrioritySearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SOFTZONEPRIORITY用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class SoftZonePrioritySearchKey
        extends DefaultSQLSearchKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //  public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のテーブル名とカラムのリストを準備して、インスタンスを
     * 生成します。
     */
    public SoftZonePrioritySearchKey()
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
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(SoftZonePriority.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(SoftZonePriority.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZonePriority.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(SoftZonePriority.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNoGroup(int prio)
    {
        setGroup(SoftZonePriority.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(SoftZonePriority.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWhStationNoCollect(String sqlfunc)
    {
        setCollect(SoftZonePriority.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をセットソフトゾーンID(<code>SOFT_ZONE_ID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftZoneIdOrder(boolean ascorder)
    {
        setOrder(SoftZonePriority.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZonePriority.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setSoftZoneIdGroup()
    {
        setGroup(SoftZonePriority.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdGroup(int prio)
    {
        setGroup(SoftZonePriority.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setSoftZoneIdCollect()
    {
        setCollect(SoftZonePriority.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftZoneIdCollect(String sqlfunc)
    {
        setCollect(SoftZonePriority.SOFT_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)<br>
     * 文字列の検索値をセット優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrioritySoftZone(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, values, and_or_toNext) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPrioritySoftZone(String value, String compcode)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value, compcode, "", "", true) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrioritySoftZone(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY_SOFT_ZONE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPrioritySoftZoneOrder(boolean ascorder)
    {
        setOrder(SoftZonePriority.PRIORITY_SOFT_ZONE, ascorder) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrioritySoftZoneOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZonePriority.PRIORITY_SOFT_ZONE, ascorder) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)のグループ順をセットします。
     */
    public void setPrioritySoftZoneGroup()
    {
        setGroup(SoftZonePriority.PRIORITY_SOFT_ZONE) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrioritySoftZoneGroup(int prio)
    {
        setGroup(SoftZonePriority.PRIORITY_SOFT_ZONE) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の情報取得を設定します。
     */
    public void setPrioritySoftZoneCollect()
    {
        setCollect(SoftZonePriority.PRIORITY_SOFT_ZONE) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPrioritySoftZoneCollect(String sqlfunc)
    {
        setCollect(SoftZonePriority.PRIORITY_SOFT_ZONE, sqlfunc, null) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順(<code>PRIORITY</code>)<br>
     * 数値の検索値をセットゾーン優先順(<code>PRIORITY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(int[] values, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPriority(int value, String compcode)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZonePriority.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPriorityOrder(boolean ascorder)
    {
        setOrder(SoftZonePriority.PRIORITY, ascorder) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPriorityOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZonePriority.PRIORITY, ascorder) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のグループ順をセットします。
     */
    public void setPriorityGroup()
    {
        setGroup(SoftZonePriority.PRIORITY) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPriorityGroup(int prio)
    {
        setGroup(SoftZonePriority.PRIORITY) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の情報取得を設定します。
     */
    public void setPriorityCollect()
    {
        setCollect(SoftZonePriority.PRIORITY) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPriorityCollect(String sqlfunc)
    {
        setCollect(SoftZonePriority.PRIORITY, sqlfunc, null) ;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: SoftZonePrioritySearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
