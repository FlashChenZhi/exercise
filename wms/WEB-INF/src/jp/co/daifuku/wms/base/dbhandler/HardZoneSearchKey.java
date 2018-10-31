// $Id: HardZoneSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * HARDZONE用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class HardZoneSearchKey
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
    public HardZoneSearchKey()
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
     * 文字列の検索値をセットハードゾーンID(<code>HARD_ZONE_ID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardZoneId(String value, String compcode)
    {
        setKey(HardZone.HARD_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardZoneIdOrder(boolean ascorder)
    {
        setOrder(HardZone.HARD_ZONE_ID, ascorder) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneIdOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.HARD_ZONE_ID, ascorder) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setHardZoneIdGroup()
    {
        setGroup(HardZone.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneIdGroup(int prio)
    {
        setGroup(HardZone.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setHardZoneIdCollect()
    {
        setCollect(HardZone.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーンID(<code>HARD_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHardZoneIdCollect(String sqlfunc)
    {
        setCollect(HardZone.HARD_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value ゾーン名称(<code>HARD_ZONE_NAME</code>)<br>
     * 文字列の検索値をセットゾーン名称(<code>HARD_ZONE_NAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneName(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, values, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardZoneName(String value, String compcode)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
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
    public void setHardZoneName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.HARD_ZONE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardZoneNameOrder(boolean ascorder)
    {
        setOrder(HardZone.HARD_ZONE_NAME, ascorder) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneNameOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.HARD_ZONE_NAME, ascorder) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)のグループ順をセットします。
     */
    public void setHardZoneNameGroup()
    {
        setGroup(HardZone.HARD_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneNameGroup(int prio)
    {
        setGroup(HardZone.HARD_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の情報取得を設定します。
     */
    public void setHardZoneNameCollect()
    {
        setCollect(HardZone.HARD_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>HARD_ZONE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHardZoneNameCollect(String sqlfunc)
    {
        setCollect(HardZone.HARD_ZONE_NAME, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(HardZone.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(HardZone.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(HardZone.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(HardZone.WH_STATION_NO, ascorder) ;
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
        setOrder(HardZone.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(HardZone.WH_STATION_NO) ;
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
        setGroup(HardZone.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(HardZone.WH_STATION_NO) ;
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
        setCollect(HardZone.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 荷高(<code>HEIGHT</code>)<br>
     * 数値の検索値をセット荷高(<code>HEIGHT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHeight(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.HEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHeight(int value, String compcode)
    {
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
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
    public void setHeight(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHeight(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHeightOrder(boolean ascorder)
    {
        setOrder(HardZone.HEIGHT, ascorder) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHeightOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.HEIGHT, ascorder) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のグループ順をセットします。
     */
    public void setHeightGroup()
    {
        setGroup(HardZone.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHeightGroup(int prio)
    {
        setGroup(HardZone.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の情報取得を設定します。
     */
    public void setHeightCollect()
    {
        setCollect(HardZone.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHeightCollect(String sqlfunc)
    {
        setCollect(HardZone.HEIGHT, sqlfunc, null) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順(<code>PRIORITY</code>)<br>
     * 文字列の検索値をセットゾーン優先順(<code>PRIORITY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(String[] values, boolean and_or_toNext)
    {
        setKey(HardZone.PRIORITY, values, and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPriority(String value, String compcode)
    {
        setKey(HardZone.PRIORITY, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.PRIORITY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.PRIORITY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPriorityOrder(boolean ascorder)
    {
        setOrder(HardZone.PRIORITY, ascorder) ;
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
        setOrder(HardZone.PRIORITY, ascorder) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)のグループ順をセットします。
     */
    public void setPriorityGroup()
    {
        setGroup(HardZone.PRIORITY) ;
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
        setGroup(HardZone.PRIORITY) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>)の情報取得を設定します。
     */
    public void setPriorityCollect()
    {
        setCollect(HardZone.PRIORITY) ;
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
        setCollect(HardZone.PRIORITY, sqlfunc, null) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 開始バンク(<code>START_BANK_NO</code>)<br>
     * 数値の検索値をセット開始バンク(<code>START_BANK_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartBankNo(int value, String compcode)
    {
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
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
    public void setStartBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartBankNoOrder(boolean ascorder)
    {
        setOrder(HardZone.START_BANK_NO, ascorder) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartBankNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.START_BANK_NO, ascorder) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のグループ順をセットします。
     */
    public void setStartBankNoGroup()
    {
        setGroup(HardZone.START_BANK_NO) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartBankNoGroup(int prio)
    {
        setGroup(HardZone.START_BANK_NO) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の情報取得を設定します。
     */
    public void setStartBankNoCollect()
    {
        setCollect(HardZone.START_BANK_NO) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStartBankNoCollect(String sqlfunc)
    {
        setCollect(HardZone.START_BANK_NO, sqlfunc, null) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 開始ベイ(<code>START_BAY_NO</code>)<br>
     * 数値の検索値をセット開始ベイ(<code>START_BAY_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartBayNo(int value, String compcode)
    {
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
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
    public void setStartBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartBayNoOrder(boolean ascorder)
    {
        setOrder(HardZone.START_BAY_NO, ascorder) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartBayNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.START_BAY_NO, ascorder) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のグループ順をセットします。
     */
    public void setStartBayNoGroup()
    {
        setGroup(HardZone.START_BAY_NO) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartBayNoGroup(int prio)
    {
        setGroup(HardZone.START_BAY_NO) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の情報取得を設定します。
     */
    public void setStartBayNoCollect()
    {
        setCollect(HardZone.START_BAY_NO) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStartBayNoCollect(String sqlfunc)
    {
        setCollect(HardZone.START_BAY_NO, sqlfunc, null) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)<br>
     * 数値の検索値をセット開始レベル(<code>START_LEVEL_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.START_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartLevelNo(int value, String compcode)
    {
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
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
    public void setStartLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartLevelNoOrder(boolean ascorder)
    {
        setOrder(HardZone.START_LEVEL_NO, ascorder) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartLevelNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.START_LEVEL_NO, ascorder) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setStartLevelNoGroup()
    {
        setGroup(HardZone.START_LEVEL_NO) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStartLevelNoGroup(int prio)
    {
        setGroup(HardZone.START_LEVEL_NO) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setStartLevelNoCollect()
    {
        setCollect(HardZone.START_LEVEL_NO) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStartLevelNoCollect(String sqlfunc)
    {
        setCollect(HardZone.START_LEVEL_NO, sqlfunc, null) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 終了バンク(<code>END_BANK_NO</code>)<br>
     * 数値の検索値をセット終了バンク(<code>END_BANK_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndBankNo(int value, String compcode)
    {
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
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
    public void setEndBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndBankNoOrder(boolean ascorder)
    {
        setOrder(HardZone.END_BANK_NO, ascorder) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndBankNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.END_BANK_NO, ascorder) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のグループ順をセットします。
     */
    public void setEndBankNoGroup()
    {
        setGroup(HardZone.END_BANK_NO) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndBankNoGroup(int prio)
    {
        setGroup(HardZone.END_BANK_NO) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の情報取得を設定します。
     */
    public void setEndBankNoCollect()
    {
        setCollect(HardZone.END_BANK_NO) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEndBankNoCollect(String sqlfunc)
    {
        setCollect(HardZone.END_BANK_NO, sqlfunc, null) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 終了ベイ(<code>END_BAY_NO</code>)<br>
     * 数値の検索値をセット終了ベイ(<code>END_BAY_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndBayNo(int value, String compcode)
    {
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
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
    public void setEndBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndBayNoOrder(boolean ascorder)
    {
        setOrder(HardZone.END_BAY_NO, ascorder) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndBayNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.END_BAY_NO, ascorder) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のグループ順をセットします。
     */
    public void setEndBayNoGroup()
    {
        setGroup(HardZone.END_BAY_NO) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndBayNoGroup(int prio)
    {
        setGroup(HardZone.END_BAY_NO) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の情報取得を設定します。
     */
    public void setEndBayNoCollect()
    {
        setCollect(HardZone.END_BAY_NO) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEndBayNoCollect(String sqlfunc)
    {
        setCollect(HardZone.END_BAY_NO, sqlfunc, null) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)<br>
     * 数値の検索値をセット終了レベル(<code>END_LEVEL_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(HardZone.END_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndLevelNo(int value, String compcode)
    {
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
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
    public void setEndLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(HardZone.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndLevelNoOrder(boolean ascorder)
    {
        setOrder(HardZone.END_LEVEL_NO, ascorder) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndLevelNoOrder(int prio, boolean ascorder)
    {
        setOrder(HardZone.END_LEVEL_NO, ascorder) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setEndLevelNoGroup()
    {
        setGroup(HardZone.END_LEVEL_NO) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndLevelNoGroup(int prio)
    {
        setGroup(HardZone.END_LEVEL_NO) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setEndLevelNoCollect()
    {
        setCollect(HardZone.END_LEVEL_NO) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEndLevelNoCollect(String sqlfunc)
    {
        setCollect(HardZone.END_LEVEL_NO, sqlfunc, null) ;
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
        return "$Id: HardZoneSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
