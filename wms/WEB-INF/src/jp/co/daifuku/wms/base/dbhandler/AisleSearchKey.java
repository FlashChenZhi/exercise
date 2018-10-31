// $Id: AisleSearchKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * AISLE用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class AisleSearchKey
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
    public AisleSearchKey()
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
     * 文字列の検索値をセットステーションNo.(<code>STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Aisle.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
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
        setKey(Aisle.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(Aisle.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
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
        setOrder(Aisle.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(Aisle.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNoGroup(int prio)
    {
        setGroup(Aisle.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(Aisle.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStationNoCollect(String sqlfunc)
    {
        setCollect(Aisle.STATION_NO, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Aisle.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Aisle.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Aisle.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(Aisle.WH_STATION_NO, ascorder) ;
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
        setOrder(Aisle.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(Aisle.WH_STATION_NO) ;
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
        setGroup(Aisle.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(Aisle.WH_STATION_NO) ;
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
        setCollect(Aisle.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value アイルNo.(<code>AISLE_NO</code>)<br>
     * 文字列の検索値をセットアイルNo.(<code>AISLE_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, values, and_or_toNext) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleNo(String value, String compcode)
    {
        setKey(Aisle.AISLE_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
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
    public void setAisleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.AISLE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleNoOrder(boolean ascorder)
    {
        setOrder(Aisle.AISLE_NO, ascorder) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNoOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.AISLE_NO, ascorder) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のグループ順をセットします。
     */
    public void setAisleNoGroup()
    {
        setGroup(Aisle.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleNoGroup(int prio)
    {
        setGroup(Aisle.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の情報取得を設定します。
     */
    public void setAisleNoCollect()
    {
        setCollect(Aisle.AISLE_NO) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAisleNoCollect(String sqlfunc)
    {
        setCollect(Aisle.AISLE_NO, sqlfunc, null) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をセットコントローラーNo.(<code>CONTROLLER_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControllerNoOrder(boolean ascorder)
    {
        setOrder(Aisle.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNoOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     */
    public void setControllerNoGroup()
    {
        setGroup(Aisle.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNoGroup(int prio)
    {
        setGroup(Aisle.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     */
    public void setControllerNoCollect()
    {
        setCollect(Aisle.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setControllerNoCollect(String sqlfunc)
    {
        setCollect(Aisle.CONTROLLER_NO, sqlfunc, null) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)<br>
     * 文字列の検索値をセットダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDoubleDeepKind(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, values, and_or_toNext) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDoubleDeepKind(String value, String compcode)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, "", "", true) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
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
    public void setDoubleDeepKind(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDoubleDeepKind(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.DOUBLE_DEEP_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDoubleDeepKindOrder(boolean ascorder)
    {
        setOrder(Aisle.DOUBLE_DEEP_KIND, ascorder) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDoubleDeepKindOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.DOUBLE_DEEP_KIND, ascorder) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)のグループ順をセットします。
     */
    public void setDoubleDeepKindGroup()
    {
        setGroup(Aisle.DOUBLE_DEEP_KIND) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDoubleDeepKindGroup(int prio)
    {
        setGroup(Aisle.DOUBLE_DEEP_KIND) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の情報取得を設定します。
     */
    public void setDoubleDeepKindCollect()
    {
        setCollect(Aisle.DOUBLE_DEEP_KIND) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDoubleDeepKindCollect(String sqlfunc)
    {
        setCollect(Aisle.DOUBLE_DEEP_KIND, sqlfunc, null) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS</code>)<br>
     * 文字列の検索値をセット状態(<code>STATUS</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatus(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.STATUS, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatus(String value, String compcode)
    {
        setKey(Aisle.STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
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
    public void setStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusOrder(boolean ascorder)
    {
        setOrder(Aisle.STATUS, ascorder) ;
    }

    /**
     * 状態(<code>STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.STATUS, ascorder) ;
    }

    /**
     * 状態(<code>STATUS</code>)のグループ順をセットします。
     */
    public void setStatusGroup()
    {
        setGroup(Aisle.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusGroup(int prio)
    {
        setGroup(Aisle.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)の情報取得を設定します。
     */
    public void setStatusCollect()
    {
        setCollect(Aisle.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusCollect(String sqlfunc)
    {
        setCollect(Aisle.STATUS, sqlfunc, null) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)<br>
     * 文字列の検索値をセット在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setInventoryCheckFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setInventoryCheckFlag(String value, String compcode)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
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
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setInventoryCheckFlagOrder(boolean ascorder)
    {
        setOrder(Aisle.INVENTORY_CHECK_FLAG, ascorder) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.INVENTORY_CHECK_FLAG, ascorder) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のグループ順をセットします。
     */
    public void setInventoryCheckFlagGroup()
    {
        setGroup(Aisle.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlagGroup(int prio)
    {
        setGroup(Aisle.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の情報取得を設定します。
     */
    public void setInventoryCheckFlagCollect()
    {
        setCollect(Aisle.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setInventoryCheckFlagCollect(String sqlfunc)
    {
        setCollect(Aisle.INVENTORY_CHECK_FLAG, sqlfunc, null) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 最大搬送可能数(<code>MAX_CARRY</code>)<br>
     * 数値の検索値をセット最大搬送可能数(<code>MAX_CARRY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxCarry(int[] values, boolean and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxCarry(int value, String compcode)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
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
    public void setMaxCarry(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxCarry(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Aisle.MAX_CARRY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxCarryOrder(boolean ascorder)
    {
        setOrder(Aisle.MAX_CARRY, ascorder) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxCarryOrder(int prio, boolean ascorder)
    {
        setOrder(Aisle.MAX_CARRY, ascorder) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)のグループ順をセットします。
     */
    public void setMaxCarryGroup()
    {
        setGroup(Aisle.MAX_CARRY) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxCarryGroup(int prio)
    {
        setGroup(Aisle.MAX_CARRY) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の情報取得を設定します。
     */
    public void setMaxCarryCollect()
    {
        setCollect(Aisle.MAX_CARRY) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxCarryCollect(String sqlfunc)
    {
        setCollect(Aisle.MAX_CARRY, sqlfunc, null) ;
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
        return "$Id: AisleSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
