// $Id: WidthSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.Width;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WIDTH用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class WidthSearchKey
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
    public WidthSearchKey()
    {
        super(Width.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 荷幅ID(<code>WIDTH_ID</code>)<br>
     * 文字列の検索値をセット荷幅ID(<code>WIDTH_ID</code>)します。
     */
    public void setWidthId(String value)
    {
        setKey(Width.WIDTH_ID, value) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthId(String[] values)
    {
        setKey(Width.WIDTH_ID, values, true) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthId(String[] values, String and_or_toNext)
    {
        setKey(Width.WIDTH_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidthId(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_ID, values, and_or_toNext) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWidthId(String value, String compcode)
    {
        setKey(Width.WIDTH_ID, value, compcode, "", "", true) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
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
    public void setWidthId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.WIDTH_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidthId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWidthIdOrder(boolean ascorder)
    {
        setOrder(Width.WIDTH_ID, ascorder) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthIdOrder(int prio, boolean ascorder)
    {
        setOrder(Width.WIDTH_ID, ascorder) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)のグループ順をセットします。
     */
    public void setWidthIdGroup()
    {
        setGroup(Width.WIDTH_ID) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthIdGroup(int prio)
    {
        setGroup(Width.WIDTH_ID) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の情報取得を設定します。
     */
    public void setWidthIdCollect()
    {
        setCollect(Width.WIDTH_ID) ;
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWidthIdCollect(String sqlfunc)
    {
        setCollect(Width.WIDTH_ID, sqlfunc, null) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷幅名称(<code>WIDTH_NAME</code>)<br>
     * 文字列の検索値をセット荷幅名称(<code>WIDTH_NAME</code>)します。
     */
    public void setWidthName(String value)
    {
        setKey(Width.WIDTH_NAME, value) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthName(String[] values)
    {
        setKey(Width.WIDTH_NAME, values, true) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidthName(String[] values, String and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidthName(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWidthName(String value, String compcode)
    {
        setKey(Width.WIDTH_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
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
    public void setWidthName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidthName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.WIDTH_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWidthNameOrder(boolean ascorder)
    {
        setOrder(Width.WIDTH_NAME, ascorder) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthNameOrder(int prio, boolean ascorder)
    {
        setOrder(Width.WIDTH_NAME, ascorder) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)のグループ順をセットします。
     */
    public void setWidthNameGroup()
    {
        setGroup(Width.WIDTH_NAME) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthNameGroup(int prio)
    {
        setGroup(Width.WIDTH_NAME) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の情報取得を設定します。
     */
    public void setWidthNameCollect()
    {
        setCollect(Width.WIDTH_NAME) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWidthNameCollect(String sqlfunc)
    {
        setCollect(Width.WIDTH_NAME, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
     */
    public void setWhStationNo(String value)
    {
        setKey(Width.WH_STATION_NO, value) ;
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
        setKey(Width.WH_STATION_NO, values, true) ;
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
        setKey(Width.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Width.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Width.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Width.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(Width.WH_STATION_NO, ascorder) ;
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
        setOrder(Width.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(Width.WH_STATION_NO) ;
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
        setGroup(Width.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(Width.WH_STATION_NO) ;
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
        setCollect(Width.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 荷幅(<code>WIDTH</code>)<br>
     * 数値の検索値をセット荷幅(<code>WIDTH</code>)します。
     */
    public void setWidth(int value)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidth(int[] values)
    {
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWidth(int[] values, String and_or_toNext)
    {
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidth(int[] values, boolean and_or_toNext)
    {
        setKey(Width.WIDTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWidth(int value, String compcode)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
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
    public void setWidth(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidth(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWidthOrder(boolean ascorder)
    {
        setOrder(Width.WIDTH, ascorder) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthOrder(int prio, boolean ascorder)
    {
        setOrder(Width.WIDTH, ascorder) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のグループ順をセットします。
     */
    public void setWidthGroup()
    {
        setGroup(Width.WIDTH) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWidthGroup(int prio)
    {
        setGroup(Width.WIDTH) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の情報取得を設定します。
     */
    public void setWidthCollect()
    {
        setCollect(Width.WIDTH) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWidthCollect(String sqlfunc)
    {
        setCollect(Width.WIDTH, sqlfunc, null) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 最大格納数(<code>MAX_STORAGE</code>)<br>
     * 数値の検索値をセット最大格納数(<code>MAX_STORAGE</code>)します。
     */
    public void setMaxStorage(int value)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorage(int[] values)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorage(int[] values, String and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxStorage(int[] values, boolean and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxStorage(int value, String compcode)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
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
    public void setMaxStorage(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxStorage(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Width.MAX_STORAGE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxStorageOrder(boolean ascorder)
    {
        setOrder(Width.MAX_STORAGE, ascorder) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorageOrder(int prio, boolean ascorder)
    {
        setOrder(Width.MAX_STORAGE, ascorder) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)のグループ順をセットします。
     */
    public void setMaxStorageGroup()
    {
        setGroup(Width.MAX_STORAGE) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxStorageGroup(int prio)
    {
        setGroup(Width.MAX_STORAGE) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の情報取得を設定します。
     */
    public void setMaxStorageCollect()
    {
        setCollect(Width.MAX_STORAGE) ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxStorageCollect(String sqlfunc)
    {
        setCollect(Width.MAX_STORAGE, sqlfunc, null) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 開始バンク(<code>START_BANK_NO</code>)<br>
     * 数値の検索値をセット開始バンク(<code>START_BANK_NO</code>)します。
     */
    public void setStartBankNo(int value)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartBankNo(int value, String compcode)
    {
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.START_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartBankNoOrder(boolean ascorder)
    {
        setOrder(Width.START_BANK_NO, ascorder) ;
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
        setOrder(Width.START_BANK_NO, ascorder) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)のグループ順をセットします。
     */
    public void setStartBankNoGroup()
    {
        setGroup(Width.START_BANK_NO) ;
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
        setGroup(Width.START_BANK_NO) ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>)の情報取得を設定します。
     */
    public void setStartBankNoCollect()
    {
        setCollect(Width.START_BANK_NO) ;
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
        setCollect(Width.START_BANK_NO, sqlfunc, null) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 開始ベイ(<code>START_BAY_NO</code>)<br>
     * 数値の検索値をセット開始ベイ(<code>START_BAY_NO</code>)します。
     */
    public void setStartBayNo(int value)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartBayNo(int value, String compcode)
    {
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.START_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartBayNoOrder(boolean ascorder)
    {
        setOrder(Width.START_BAY_NO, ascorder) ;
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
        setOrder(Width.START_BAY_NO, ascorder) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)のグループ順をセットします。
     */
    public void setStartBayNoGroup()
    {
        setGroup(Width.START_BAY_NO) ;
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
        setGroup(Width.START_BAY_NO) ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>)の情報取得を設定します。
     */
    public void setStartBayNoCollect()
    {
        setCollect(Width.START_BAY_NO) ;
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
        setCollect(Width.START_BAY_NO, sqlfunc, null) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 開始レベル(<code>START_LEVEL_NO</code>)<br>
     * 数値の検索値をセット開始レベル(<code>START_LEVEL_NO</code>)します。
     */
    public void setStartLevelNo(int value)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStartLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.START_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStartLevelNo(int value, String compcode)
    {
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.START_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStartLevelNoOrder(boolean ascorder)
    {
        setOrder(Width.START_LEVEL_NO, ascorder) ;
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
        setOrder(Width.START_LEVEL_NO, ascorder) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setStartLevelNoGroup()
    {
        setGroup(Width.START_LEVEL_NO) ;
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
        setGroup(Width.START_LEVEL_NO) ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setStartLevelNoCollect()
    {
        setCollect(Width.START_LEVEL_NO) ;
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
        setCollect(Width.START_LEVEL_NO, sqlfunc, null) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 終了バンク(<code>END_BANK_NO</code>)<br>
     * 数値の検索値をセット終了バンク(<code>END_BANK_NO</code>)します。
     */
    public void setEndBankNo(int value)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndBankNo(int value, String compcode)
    {
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.END_BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndBankNoOrder(boolean ascorder)
    {
        setOrder(Width.END_BANK_NO, ascorder) ;
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
        setOrder(Width.END_BANK_NO, ascorder) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)のグループ順をセットします。
     */
    public void setEndBankNoGroup()
    {
        setGroup(Width.END_BANK_NO) ;
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
        setGroup(Width.END_BANK_NO) ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>)の情報取得を設定します。
     */
    public void setEndBankNoCollect()
    {
        setCollect(Width.END_BANK_NO) ;
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
        setCollect(Width.END_BANK_NO, sqlfunc, null) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 終了ベイ(<code>END_BAY_NO</code>)<br>
     * 数値の検索値をセット終了ベイ(<code>END_BAY_NO</code>)します。
     */
    public void setEndBayNo(int value)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndBayNo(int value, String compcode)
    {
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.END_BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndBayNoOrder(boolean ascorder)
    {
        setOrder(Width.END_BAY_NO, ascorder) ;
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
        setOrder(Width.END_BAY_NO, ascorder) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)のグループ順をセットします。
     */
    public void setEndBayNoGroup()
    {
        setGroup(Width.END_BAY_NO) ;
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
        setGroup(Width.END_BAY_NO) ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>)の情報取得を設定します。
     */
    public void setEndBayNoCollect()
    {
        setCollect(Width.END_BAY_NO) ;
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
        setCollect(Width.END_BAY_NO, sqlfunc, null) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 終了レベル(<code>END_LEVEL_NO</code>)<br>
     * 数値の検索値をセット終了レベル(<code>END_LEVEL_NO</code>)します。
     */
    public void setEndLevelNo(int value)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value)) ;
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
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Width.END_LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndLevelNo(int value, String compcode)
    {
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Width.END_LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndLevelNoOrder(boolean ascorder)
    {
        setOrder(Width.END_LEVEL_NO, ascorder) ;
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
        setOrder(Width.END_LEVEL_NO, ascorder) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setEndLevelNoGroup()
    {
        setGroup(Width.END_LEVEL_NO) ;
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
        setGroup(Width.END_LEVEL_NO) ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setEndLevelNoCollect()
    {
        setCollect(Width.END_LEVEL_NO) ;
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
        setCollect(Width.END_LEVEL_NO, sqlfunc, null) ;
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
        return "$Id: WidthSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
