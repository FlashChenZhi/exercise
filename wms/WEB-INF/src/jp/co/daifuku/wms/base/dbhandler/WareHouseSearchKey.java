// $Id: WareHouseSearchKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WAREHOUSE用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class WareHouseSearchKey
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
    public WareHouseSearchKey()
    {
        super(WareHouse.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo(<code>STATION_NO</code>)<br>
     * 文字列の検索値をセットステーションNo(<code>STATION_NO</code>)します。
     */
    public void setStationNo(String value)
    {
        setKey(WareHouse.STATION_NO, value) ;
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
        setKey(WareHouse.STATION_NO, values, true) ;
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
        setKey(WareHouse.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(WareHouse.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
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
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(WareHouse.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(WareHouse.STATION_NO) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNoGroup(int prio)
    {
        setGroup(WareHouse.STATION_NO) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(WareHouse.STATION_NO) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStationNoCollect(String sqlfunc)
    {
        setCollect(WareHouse.STATION_NO, sqlfunc, null) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 格納区分(<code>WAREHOUSE_NO</code>)<br>
     * 文字列の検索値をセット格納区分(<code>WAREHOUSE_NO</code>)します。
     */
    public void setWarehouseNo(String value)
    {
        setKey(WareHouse.WAREHOUSE_NO, value) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNo(String[] values)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, true) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNo(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWarehouseNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, values, and_or_toNext) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWarehouseNo(String value, String compcode)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
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
    public void setWarehouseNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWarehouseNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWarehouseNoOrder(boolean ascorder)
    {
        setOrder(WareHouse.WAREHOUSE_NO, ascorder) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNoOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.WAREHOUSE_NO, ascorder) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)のグループ順をセットします。
     */
    public void setWarehouseNoGroup()
    {
        setGroup(WareHouse.WAREHOUSE_NO) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNoGroup(int prio)
    {
        setGroup(WareHouse.WAREHOUSE_NO) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の情報取得を設定します。
     */
    public void setWarehouseNoCollect()
    {
        setCollect(WareHouse.WAREHOUSE_NO) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWarehouseNoCollect(String sqlfunc)
    {
        setCollect(WareHouse.WAREHOUSE_NO, sqlfunc, null) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 最大混載数(<code>MAX_MIXEDPALLET</code>)<br>
     * 数値の検索値をセット最大混載数(<code>MAX_MIXEDPALLET</code>)します。
     */
    public void setMaxMixedpallet(int value)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpallet(int[] values)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpallet(int[] values, String and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxMixedpallet(int[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxMixedpallet(int value, String compcode)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
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
    public void setMaxMixedpallet(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxMixedpallet(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.MAX_MIXEDPALLET, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxMixedpalletOrder(boolean ascorder)
    {
        setOrder(WareHouse.MAX_MIXEDPALLET, ascorder) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpalletOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.MAX_MIXEDPALLET, ascorder) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)のグループ順をセットします。
     */
    public void setMaxMixedpalletGroup()
    {
        setGroup(WareHouse.MAX_MIXEDPALLET) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxMixedpalletGroup(int prio)
    {
        setGroup(WareHouse.MAX_MIXEDPALLET) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の情報取得を設定します。
     */
    public void setMaxMixedpalletCollect()
    {
        setCollect(WareHouse.MAX_MIXEDPALLET) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxMixedpalletCollect(String sqlfunc)
    {
        setCollect(WareHouse.MAX_MIXEDPALLET, sqlfunc, null) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 倉庫名称(<code>WAREHOUSE_NAME</code>)<br>
     * 文字列の検索値をセット倉庫名称(<code>WAREHOUSE_NAME</code>)します。
     */
    public void setWarehouseName(String value)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseName(String[] values)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, true) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseName(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWarehouseName(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, values, and_or_toNext) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWarehouseName(String value, String compcode)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
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
    public void setWarehouseName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWarehouseName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.WAREHOUSE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWarehouseNameOrder(boolean ascorder)
    {
        setOrder(WareHouse.WAREHOUSE_NAME, ascorder) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNameOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.WAREHOUSE_NAME, ascorder) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)のグループ順をセットします。
     */
    public void setWarehouseNameGroup()
    {
        setGroup(WareHouse.WAREHOUSE_NAME) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWarehouseNameGroup(int prio)
    {
        setGroup(WareHouse.WAREHOUSE_NAME) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の情報取得を設定します。
     */
    public void setWarehouseNameCollect()
    {
        setCollect(WareHouse.WAREHOUSE_NAME) ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWarehouseNameCollect(String sqlfunc)
    {
        setCollect(WareHouse.WAREHOUSE_NAME, sqlfunc, null) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)<br>
     * 文字列の検索値をセット最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)します。
     */
    public void setLastUsedStationNo(String value)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, true) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUsedStationNo(String value, String compcode)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
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
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUsedStationNoOrder(boolean ascorder)
    {
        setOrder(WareHouse.LAST_USED_STATION_NO, ascorder) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.LAST_USED_STATION_NO, ascorder) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)のグループ順をセットします。
     */
    public void setLastUsedStationNoGroup()
    {
        setGroup(WareHouse.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNoGroup(int prio)
    {
        setGroup(WareHouse.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の情報取得を設定します。
     */
    public void setLastUsedStationNoCollect()
    {
        setCollect(WareHouse.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUsedStationNoCollect(String sqlfunc)
    {
        setCollect(WareHouse.LAST_USED_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)<br>
     * 文字列の検索値をセット自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)します。
     */
    public void setEmploymentType(String value)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentType(String[] values)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, true) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEmploymentType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEmploymentType(String value, String compcode)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
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
    public void setEmploymentType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEmploymentType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.EMPLOYMENT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEmploymentTypeOrder(boolean ascorder)
    {
        setOrder(WareHouse.EMPLOYMENT_TYPE, ascorder) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentTypeOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.EMPLOYMENT_TYPE, ascorder) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)のグループ順をセットします。
     */
    public void setEmploymentTypeGroup()
    {
        setGroup(WareHouse.EMPLOYMENT_TYPE) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEmploymentTypeGroup(int prio)
    {
        setGroup(WareHouse.EMPLOYMENT_TYPE) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の情報取得を設定します。
     */
    public void setEmploymentTypeCollect()
    {
        setCollect(WareHouse.EMPLOYMENT_TYPE) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEmploymentTypeCollect(String sqlfunc)
    {
        setCollect(WareHouse.EMPLOYMENT_TYPE, sqlfunc, null) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)<br>
     * 文字列の検索値をセットフリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)します。
     */
    public void setFreeAllocationType(String value)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationType(String[] values)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, true) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFreeAllocationType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, values, and_or_toNext) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFreeAllocationType(String value, String compcode)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
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
    public void setFreeAllocationType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFreeAllocationType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.FREE_ALLOCATION_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFreeAllocationTypeOrder(boolean ascorder)
    {
        setOrder(WareHouse.FREE_ALLOCATION_TYPE, ascorder) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationTypeOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.FREE_ALLOCATION_TYPE, ascorder) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)のグループ順をセットします。
     */
    public void setFreeAllocationTypeGroup()
    {
        setGroup(WareHouse.FREE_ALLOCATION_TYPE) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFreeAllocationTypeGroup(int prio)
    {
        setGroup(WareHouse.FREE_ALLOCATION_TYPE) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の情報取得を設定します。
     */
    public void setFreeAllocationTypeCollect()
    {
        setCollect(WareHouse.FREE_ALLOCATION_TYPE) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFreeAllocationTypeCollect(String sqlfunc)
    {
        setCollect(WareHouse.FREE_ALLOCATION_TYPE, sqlfunc, null) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)<br>
     * 文字列の検索値をセット空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)します。
     */
    public void setLocationSearchType(String value)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchType(String[] values)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, true) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationSearchType(String value, String compcode)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setLocationSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.LOCATION_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationSearchTypeOrder(boolean ascorder)
    {
        setOrder(WareHouse.LOCATION_SEARCH_TYPE, ascorder) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchTypeOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.LOCATION_SEARCH_TYPE, ascorder) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)のグループ順をセットします。
     */
    public void setLocationSearchTypeGroup()
    {
        setGroup(WareHouse.LOCATION_SEARCH_TYPE) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchTypeGroup(int prio)
    {
        setGroup(WareHouse.LOCATION_SEARCH_TYPE) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の情報取得を設定します。
     */
    public void setLocationSearchTypeCollect()
    {
        setCollect(WareHouse.LOCATION_SEARCH_TYPE) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocationSearchTypeCollect(String sqlfunc)
    {
        setCollect(WareHouse.LOCATION_SEARCH_TYPE, sqlfunc, null) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)<br>
     * 文字列の検索値をセットアイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)します。
     */
    public void setAisleSearchType(String value)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchType(String[] values)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, true) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchType(String[] values, String and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleSearchType(String[] values, boolean and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, values, and_or_toNext) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleSearchType(String value, String compcode)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
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
    public void setAisleSearchType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleSearchType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WareHouse.AISLE_SEARCH_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleSearchTypeOrder(boolean ascorder)
    {
        setOrder(WareHouse.AISLE_SEARCH_TYPE, ascorder) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchTypeOrder(int prio, boolean ascorder)
    {
        setOrder(WareHouse.AISLE_SEARCH_TYPE, ascorder) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)のグループ順をセットします。
     */
    public void setAisleSearchTypeGroup()
    {
        setGroup(WareHouse.AISLE_SEARCH_TYPE) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleSearchTypeGroup(int prio)
    {
        setGroup(WareHouse.AISLE_SEARCH_TYPE) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の情報取得を設定します。
     */
    public void setAisleSearchTypeCollect()
    {
        setCollect(WareHouse.AISLE_SEARCH_TYPE) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAisleSearchTypeCollect(String sqlfunc)
    {
        setCollect(WareHouse.AISLE_SEARCH_TYPE, sqlfunc, null) ;
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
        return "$Id: WareHouseSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
