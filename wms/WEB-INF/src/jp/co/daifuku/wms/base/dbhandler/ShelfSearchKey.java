// $Id: ShelfSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SHELF用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class ShelfSearchKey
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
    public ShelfSearchKey()
    {
        super(Shelf.STORE_NAME) ;
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
        setKey(Shelf.STATION_NO, value) ;
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
        setKey(Shelf.STATION_NO, values, true) ;
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
        setKey(Shelf.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Shelf.STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Shelf.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Shelf.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(Shelf.STATION_NO, ascorder) ;
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
        setOrder(Shelf.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(Shelf.STATION_NO) ;
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
        setGroup(Shelf.STATION_NO) ;
    }

    /**
     * ステーションNo(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(Shelf.STATION_NO) ;
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
        setCollect(Shelf.STATION_NO, sqlfunc, null) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value バンク(<code>BANK_NO</code>)<br>
     * 数値の検索値をセットバンク(<code>BANK_NO</code>)します。
     */
    public void setBankNo(int value)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBankNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBankNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.BANK_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBankNo(int value, String compcode)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
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
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBankNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.BANK_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBankNoOrder(boolean ascorder)
    {
        setOrder(Shelf.BANK_NO, ascorder) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBankNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.BANK_NO, ascorder) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のグループ順をセットします。
     */
    public void setBankNoGroup()
    {
        setGroup(Shelf.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBankNoGroup(int prio)
    {
        setGroup(Shelf.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の情報取得を設定します。
     */
    public void setBankNoCollect()
    {
        setCollect(Shelf.BANK_NO) ;
    }

    /**
     * バンク(<code>BANK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBankNoCollect(String sqlfunc)
    {
        setCollect(Shelf.BANK_NO, sqlfunc, null) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value ベイ(<code>BAY_NO</code>)<br>
     * 数値の検索値をセットベイ(<code>BAY_NO</code>)します。
     */
    public void setBayNo(int value)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBayNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBayNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.BAY_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBayNo(int value, String compcode)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
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
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBayNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.BAY_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBayNoOrder(boolean ascorder)
    {
        setOrder(Shelf.BAY_NO, ascorder) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBayNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.BAY_NO, ascorder) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のグループ順をセットします。
     */
    public void setBayNoGroup()
    {
        setGroup(Shelf.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBayNoGroup(int prio)
    {
        setGroup(Shelf.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の情報取得を設定します。
     */
    public void setBayNoCollect()
    {
        setCollect(Shelf.BAY_NO) ;
    }

    /**
     * ベイ(<code>BAY_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBayNoCollect(String sqlfunc)
    {
        setCollect(Shelf.BAY_NO, sqlfunc, null) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value レベル(<code>LEVEL_NO</code>)<br>
     * 数値の検索値をセットレベル(<code>LEVEL_NO</code>)します。
     */
    public void setLevelNo(int value)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLevelNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLevelNo(int value, String compcode)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
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
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLevelNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.LEVEL_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLevelNoOrder(boolean ascorder)
    {
        setOrder(Shelf.LEVEL_NO, ascorder) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.LEVEL_NO, ascorder) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のグループ順をセットします。
     */
    public void setLevelNoGroup()
    {
        setGroup(Shelf.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLevelNoGroup(int prio)
    {
        setGroup(Shelf.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の情報取得を設定します。
     */
    public void setLevelNoCollect()
    {
        setCollect(Shelf.LEVEL_NO) ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLevelNoCollect(String sqlfunc)
    {
        setCollect(Shelf.LEVEL_NO, sqlfunc, null) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value アドレス(<code>ADDRESS_NO</code>)<br>
     * 数値の検索値をセットアドレス(<code>ADDRESS_NO</code>)します。
     */
    public void setAddressNo(int value)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNo(int[] values)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNo(int[] values, String and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddressNo(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAddressNo(int value, String compcode)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
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
    public void setAddressNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddressNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.ADDRESS_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAddressNoOrder(boolean ascorder)
    {
        setOrder(Shelf.ADDRESS_NO, ascorder) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.ADDRESS_NO, ascorder) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)のグループ順をセットします。
     */
    public void setAddressNoGroup()
    {
        setGroup(Shelf.ADDRESS_NO) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddressNoGroup(int prio)
    {
        setGroup(Shelf.ADDRESS_NO) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の情報取得を設定します。
     */
    public void setAddressNoCollect()
    {
        setCollect(Shelf.ADDRESS_NO) ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAddressNoCollect(String sqlfunc)
    {
        setCollect(Shelf.ADDRESS_NO, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo(<code>WH_STATION_NO</code>)します。
     */
    public void setWhStationNo(String value)
    {
        setKey(Shelf.WH_STATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values)
    {
        setKey(Shelf.WH_STATION_NO, values, true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
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
        setKey(Shelf.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Shelf.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
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
        setKey(Shelf.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(Shelf.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)のソート順をセットします。
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
        setOrder(Shelf.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(Shelf.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNoGroup(int prio)
    {
        setGroup(Shelf.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(Shelf.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWhStationNoCollect(String sqlfunc)
    {
        setCollect(Shelf.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>PROHIBITION_FLAG</code>)<br>
     * 文字列の検索値をセット状態(<code>PROHIBITION_FLAG</code>)します。
     */
    public void setProhibitionFlag(String value)
    {
        setKey(Shelf.PROHIBITION_FLAG, value) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlag(String[] values)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, true) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setProhibitionFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setProhibitionFlag(String value, String compcode)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
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
    public void setProhibitionFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setProhibitionFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PROHIBITION_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setProhibitionFlagOrder(boolean ascorder)
    {
        setOrder(Shelf.PROHIBITION_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.PROHIBITION_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)のグループ順をセットします。
     */
    public void setProhibitionFlagGroup()
    {
        setGroup(Shelf.PROHIBITION_FLAG) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setProhibitionFlagGroup(int prio)
    {
        setGroup(Shelf.PROHIBITION_FLAG) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の情報取得を設定します。
     */
    public void setProhibitionFlagCollect()
    {
        setCollect(Shelf.PROHIBITION_FLAG) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setProhibitionFlagCollect(String sqlfunc)
    {
        setCollect(Shelf.PROHIBITION_FLAG, sqlfunc, null) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット棚状態(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(Shelf.STATUS_FLAG, value) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(Shelf.STATUS_FLAG, values, true) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(Shelf.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(Shelf.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(Shelf.STATUS_FLAG, ascorder) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.STATUS_FLAG, ascorder) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(Shelf.STATUS_FLAG) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(Shelf.STATUS_FLAG) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(Shelf.STATUS_FLAG) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(Shelf.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ハードゾーン(<code>HARD_ZONE_ID</code>)<br>
     * 文字列の検索値をセットハードゾーン(<code>HARD_ZONE_ID</code>)します。
     */
    public void setHardZoneId(String value)
    {
        setKey(Shelf.HARD_ZONE_ID, value) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneId(String[] values)
    {
        setKey(Shelf.HARD_ZONE_ID, values, true) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
        setKey(Shelf.HARD_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardZoneId(String value, String compcode)
    {
        setKey(Shelf.HARD_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
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
        setKey(Shelf.HARD_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.HARD_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardZoneIdOrder(boolean ascorder)
    {
        setOrder(Shelf.HARD_ZONE_ID, ascorder) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)のソート順をセットします。
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
        setOrder(Shelf.HARD_ZONE_ID, ascorder) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setHardZoneIdGroup()
    {
        setGroup(Shelf.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardZoneIdGroup(int prio)
    {
        setGroup(Shelf.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setHardZoneIdCollect()
    {
        setCollect(Shelf.HARD_ZONE_ID) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHardZoneIdCollect(String sqlfunc)
    {
        setCollect(Shelf.HARD_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をセットソフトゾーン(<code>SOFT_ZONE_ID</code>)します。
     */
    public void setSoftZoneId(String value)
    {
        setKey(Shelf.SOFT_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values)
    {
        setKey(Shelf.SOFT_ZONE_ID, values, true) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
        setKey(Shelf.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftZoneIdOrder(boolean ascorder)
    {
        setOrder(Shelf.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
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
        setOrder(Shelf.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setSoftZoneIdGroup()
    {
        setGroup(Shelf.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdGroup(int prio)
    {
        setGroup(Shelf.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setSoftZoneIdCollect()
    {
        setCollect(Shelf.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftZoneIdCollect(String sqlfunc)
    {
        setCollect(Shelf.SOFT_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 親ステーションNo(<code>PARENT_STATION_NO</code>)<br>
     * 文字列の検索値をセット親ステーションNo(<code>PARENT_STATION_NO</code>)します。
     */
    public void setParentStationNo(String value)
    {
        setKey(Shelf.PARENT_STATION_NO, value) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values)
    {
        setKey(Shelf.PARENT_STATION_NO, values, true) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setParentStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setParentStationNo(String value, String compcode)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
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
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PARENT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setParentStationNoOrder(boolean ascorder)
    {
        setOrder(Shelf.PARENT_STATION_NO, ascorder) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.PARENT_STATION_NO, ascorder) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)のグループ順をセットします。
     */
    public void setParentStationNoGroup()
    {
        setGroup(Shelf.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNoGroup(int prio)
    {
        setGroup(Shelf.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の情報取得を設定します。
     */
    public void setParentStationNoCollect()
    {
        setCollect(Shelf.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setParentStationNoCollect(String sqlfunc)
    {
        setCollect(Shelf.PARENT_STATION_NO, sqlfunc, null) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)<br>
     * 文字列の検索値をセットアクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)します。
     */
    public void setAccessNgFlag(String value)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlag(String[] values)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, true) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAccessNgFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, values, and_or_toNext) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAccessNgFlag(String value, String compcode)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
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
    public void setAccessNgFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAccessNgFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.ACCESS_NG_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAccessNgFlagOrder(boolean ascorder)
    {
        setOrder(Shelf.ACCESS_NG_FLAG, ascorder) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.ACCESS_NG_FLAG, ascorder) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)のグループ順をセットします。
     */
    public void setAccessNgFlagGroup()
    {
        setGroup(Shelf.ACCESS_NG_FLAG) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAccessNgFlagGroup(int prio)
    {
        setGroup(Shelf.ACCESS_NG_FLAG) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の情報取得を設定します。
     */
    public void setAccessNgFlagCollect()
    {
        setCollect(Shelf.ACCESS_NG_FLAG) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAccessNgFlagCollect(String sqlfunc)
    {
        setCollect(Shelf.ACCESS_NG_FLAG, sqlfunc, null) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 空棚検索順(<code>PRIORITY</code>)<br>
     * 数値の検索値をセット空棚検索順(<code>PRIORITY</code>)します。
     */
    public void setPriority(int value)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(int[] values)
    {
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
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
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPriority(int value, String compcode)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
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
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPriorityOrder(boolean ascorder)
    {
        setOrder(Shelf.PRIORITY, ascorder) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)のソート順をセットします。
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
        setOrder(Shelf.PRIORITY, ascorder) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)のグループ順をセットします。
     */
    public void setPriorityGroup()
    {
        setGroup(Shelf.PRIORITY) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPriorityGroup(int prio)
    {
        setGroup(Shelf.PRIORITY) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の情報取得を設定します。
     */
    public void setPriorityCollect()
    {
        setCollect(Shelf.PRIORITY) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPriorityCollect(String sqlfunc)
    {
        setCollect(Shelf.PRIORITY, sqlfunc, null) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ペアステーションNo(<code>PAIR_STATION_NO</code>)<br>
     * 文字列の検索値をセットペアステーションNo(<code>PAIR_STATION_NO</code>)します。
     */
    public void setPairStationNo(String value)
    {
        setKey(Shelf.PAIR_STATION_NO, value) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNo(String[] values)
    {
        setKey(Shelf.PAIR_STATION_NO, values, true) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNo(String[] values, String and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPairStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPairStationNo(String value, String compcode)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
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
    public void setPairStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPairStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.PAIR_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPairStationNoOrder(boolean ascorder)
    {
        setOrder(Shelf.PAIR_STATION_NO, ascorder) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.PAIR_STATION_NO, ascorder) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)のグループ順をセットします。
     */
    public void setPairStationNoGroup()
    {
        setGroup(Shelf.PAIR_STATION_NO) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPairStationNoGroup(int prio)
    {
        setGroup(Shelf.PAIR_STATION_NO) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の情報取得を設定します。
     */
    public void setPairStationNoCollect()
    {
        setCollect(Shelf.PAIR_STATION_NO) ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPairStationNoCollect(String sqlfunc)
    {
        setCollect(Shelf.PAIR_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 手前、奥棚区分(<code>SIDE</code>)<br>
     * 文字列の検索値をセット手前、奥棚区分(<code>SIDE</code>)します。
     */
    public void setSide(String value)
    {
        setKey(Shelf.SIDE, value) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSide(String[] values)
    {
        setKey(Shelf.SIDE, values, true) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSide(String[] values, String and_or_toNext)
    {
        setKey(Shelf.SIDE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSide(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.SIDE, values, and_or_toNext) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSide(String value, String compcode)
    {
        setKey(Shelf.SIDE, value, compcode, "", "", true) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
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
    public void setSide(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.SIDE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSide(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.SIDE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSideOrder(boolean ascorder)
    {
        setOrder(Shelf.SIDE, ascorder) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSideOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.SIDE, ascorder) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)のグループ順をセットします。
     */
    public void setSideGroup()
    {
        setGroup(Shelf.SIDE) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSideGroup(int prio)
    {
        setGroup(Shelf.SIDE) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の情報取得を設定します。
     */
    public void setSideCollect()
    {
        setCollect(Shelf.SIDE) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSideCollect(String sqlfunc)
    {
        setCollect(Shelf.SIDE, sqlfunc, null) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 荷幅(<code>WIDTH</code>)<br>
     * 数値の検索値をセット荷幅(<code>WIDTH</code>)します。
     */
    public void setWidth(int value)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value)) ;
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
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWidth(int[] values, boolean and_or_toNext)
    {
        setKey(Shelf.WIDTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWidth(int value, String compcode)
    {
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Shelf.WIDTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWidthOrder(boolean ascorder)
    {
        setOrder(Shelf.WIDTH, ascorder) ;
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
        setOrder(Shelf.WIDTH, ascorder) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)のグループ順をセットします。
     */
    public void setWidthGroup()
    {
        setGroup(Shelf.WIDTH) ;
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
        setGroup(Shelf.WIDTH) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>)の情報取得を設定します。
     */
    public void setWidthCollect()
    {
        setCollect(Shelf.WIDTH) ;
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
        setCollect(Shelf.WIDTH, sqlfunc, null) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)<br>
     * 文字列の検索値をセット棚使用フラグ(<code>LOCATION_USE_FLAG</code>)します。
     */
    public void setLocationUseFlag(String value)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlag(String[] values)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, true) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlag(String[] values, String and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationUseFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationUseFlag(String value, String compcode)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
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
    public void setLocationUseFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationUseFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Shelf.LOCATION_USE_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationUseFlagOrder(boolean ascorder)
    {
        setOrder(Shelf.LOCATION_USE_FLAG, ascorder) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Shelf.LOCATION_USE_FLAG, ascorder) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)のグループ順をセットします。
     */
    public void setLocationUseFlagGroup()
    {
        setGroup(Shelf.LOCATION_USE_FLAG) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationUseFlagGroup(int prio)
    {
        setGroup(Shelf.LOCATION_USE_FLAG) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の情報取得を設定します。
     */
    public void setLocationUseFlagCollect()
    {
        setCollect(Shelf.LOCATION_USE_FLAG) ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocationUseFlagCollect(String sqlfunc)
    {
        setCollect(Shelf.LOCATION_USE_FLAG, sqlfunc, null) ;
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
        return "$Id: ShelfSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
