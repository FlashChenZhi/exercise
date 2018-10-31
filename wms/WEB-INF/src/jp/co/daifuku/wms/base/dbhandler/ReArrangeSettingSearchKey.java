// $Id: ReArrangeSettingSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * REARRANGESETTING用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class ReArrangeSettingSearchKey
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
    public ReArrangeSettingSearchKey()
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
     * 文字列の検索値をセットスケジュールNo.(<code>SCHEDULE_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setScheduleNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setScheduleNoOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     */
    public void setScheduleNoGroup()
    {
        setGroup(ReArrangeSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNoGroup(int prio)
    {
        setGroup(ReArrangeSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     */
    public void setScheduleNoCollect()
    {
        setCollect(ReArrangeSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setScheduleNoCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.SCHEDULE_NO, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReArrangeSetting.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.WH_STATION_NO, ascorder) ;
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
        setOrder(ReArrangeSetting.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(ReArrangeSetting.WH_STATION_NO) ;
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
        setGroup(ReArrangeSetting.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(ReArrangeSetting.WH_STATION_NO) ;
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
        setCollect(ReArrangeSetting.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をセットアイルステーションNo.(<code>AISLE_STATION_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
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
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleStationNoOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.AISLE_STATION_NO, ascorder) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.AISLE_STATION_NO, ascorder) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のグループ順をセットします。
     */
    public void setAisleStationNoGroup()
    {
        setGroup(ReArrangeSetting.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNoGroup(int prio)
    {
        setGroup(ReArrangeSetting.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の情報取得を設定します。
     */
    public void setAisleStationNoCollect()
    {
        setCollect(ReArrangeSetting.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAisleStationNoCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.AISLE_STATION_NO, sqlfunc, null) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)<br>
     * 文字列の検索値をセットソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftzoneType(String value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
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
    public void setSoftzoneType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftzoneTypeOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_TYPE, ascorder) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_TYPE, ascorder) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)のグループ順をセットします。
     */
    public void setSoftzoneTypeGroup()
    {
        setGroup(ReArrangeSetting.SOFTZONE_TYPE) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneTypeGroup(int prio)
    {
        setGroup(ReArrangeSetting.SOFTZONE_TYPE) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の情報取得を設定します。
     */
    public void setSoftzoneTypeCollect()
    {
        setCollect(ReArrangeSetting.SOFTZONE_TYPE) ;
    }

    /**
     * ソフトゾーン最適化区分(<code>SOFTZONE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftzoneTypeCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.SOFTZONE_TYPE, sqlfunc, null) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)<br>
     * 文字列の検索値をセット荷姿最適化区分(<code>LOADSIZE_TYPE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadsizeType(String value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
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
    public void setLoadsizeType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadsizeTypeOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_TYPE, ascorder) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_TYPE, ascorder) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)のグループ順をセットします。
     */
    public void setLoadsizeTypeGroup()
    {
        setGroup(ReArrangeSetting.LOADSIZE_TYPE) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeTypeGroup(int prio)
    {
        setGroup(ReArrangeSetting.LOADSIZE_TYPE) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の情報取得を設定します。
     */
    public void setLoadsizeTypeCollect()
    {
        setCollect(ReArrangeSetting.LOADSIZE_TYPE) ;
    }

    /**
     * 荷姿最適化区分(<code>LOADSIZE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadsizeTypeCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.LOADSIZE_TYPE, sqlfunc, null) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化区分(<code>VACANT_TYPE</code>)<br>
     * 文字列の検索値をセット空棚最適化区分(<code>VACANT_TYPE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantType(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setVacantType(String value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
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
    public void setVacantType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setVacantTypeOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_TYPE, ascorder) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_TYPE, ascorder) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)のグループ順をセットします。
     */
    public void setVacantTypeGroup()
    {
        setGroup(ReArrangeSetting.VACANT_TYPE) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantTypeGroup(int prio)
    {
        setGroup(ReArrangeSetting.VACANT_TYPE) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の情報取得を設定します。
     */
    public void setVacantTypeCollect()
    {
        setCollect(ReArrangeSetting.VACANT_TYPE) ;
    }

    /**
     * 空棚最適化区分(<code>VACANT_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setVacantTypeCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.VACANT_TYPE, sqlfunc, null) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態(<code>STATUS_FLAG</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のソート順をセットします。
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
        setOrder(ReArrangeSetting.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(ReArrangeSetting.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(ReArrangeSetting.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(ReArrangeSetting.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)<br>
     * 数値の検索値をセットソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftzoneAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setSoftzoneAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftzoneAllocationQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneAllocationQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)のグループ順をセットします。
     */
    public void setSoftzoneAllocationQtyGroup()
    {
        setGroup(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneAllocationQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の情報取得を設定します。
     */
    public void setSoftzoneAllocationQtyCollect()
    {
        setCollect(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY) ;
    }

    /**
     * ソフトゾーン最適化引当数(<code>SOFTZONE_ALLOCATION_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftzoneAllocationQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.SOFTZONE_ALLOCATION_QTY, sqlfunc, null) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)<br>
     * 数値の検索値をセット荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadsizeAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setLoadsizeAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadsizeAllocationQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeAllocationQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)のグループ順をセットします。
     */
    public void setLoadsizeAllocationQtyGroup()
    {
        setGroup(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeAllocationQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の情報取得を設定します。
     */
    public void setLoadsizeAllocationQtyCollect()
    {
        setCollect(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY) ;
    }

    /**
     * 荷姿最適化引当数(<code>LOADSIZE_ALLOCATION_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadsizeAllocationQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.LOADSIZE_ALLOCATION_QTY, sqlfunc, null) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)<br>
     * 数値の検索値をセット空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantAllocationQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setVacantAllocationQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
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
    public void setVacantAllocationQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantAllocationQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_ALLOCATION_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setVacantAllocationQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantAllocationQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_ALLOCATION_QTY, ascorder) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)のグループ順をセットします。
     */
    public void setVacantAllocationQtyGroup()
    {
        setGroup(ReArrangeSetting.VACANT_ALLOCATION_QTY) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantAllocationQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.VACANT_ALLOCATION_QTY) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の情報取得を設定します。
     */
    public void setVacantAllocationQtyCollect()
    {
        setCollect(ReArrangeSetting.VACANT_ALLOCATION_QTY) ;
    }

    /**
     * 空棚最適化引当数(<code>VACANT_ALLOCATION_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setVacantAllocationQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.VACANT_ALLOCATION_QTY, sqlfunc, null) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)<br>
     * 数値の検索値をセットソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftzoneResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setSoftzoneResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftzoneResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.SOFTZONE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftzoneResultQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_RESULT_QTY, ascorder) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.SOFTZONE_RESULT_QTY, ascorder) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setSoftzoneResultQtyGroup()
    {
        setGroup(ReArrangeSetting.SOFTZONE_RESULT_QTY) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftzoneResultQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.SOFTZONE_RESULT_QTY) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setSoftzoneResultQtyCollect()
    {
        setCollect(ReArrangeSetting.SOFTZONE_RESULT_QTY) ;
    }

    /**
     * ソフトゾーン最適化実績数(<code>SOFTZONE_RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftzoneResultQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.SOFTZONE_RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)<br>
     * 数値の検索値をセット荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadsizeResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setLoadsizeResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadsizeResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LOADSIZE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadsizeResultQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_RESULT_QTY, ascorder) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.LOADSIZE_RESULT_QTY, ascorder) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setLoadsizeResultQtyGroup()
    {
        setGroup(ReArrangeSetting.LOADSIZE_RESULT_QTY) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadsizeResultQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.LOADSIZE_RESULT_QTY) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setLoadsizeResultQtyCollect()
    {
        setCollect(ReArrangeSetting.LOADSIZE_RESULT_QTY) ;
    }

    /**
     * 荷姿最適化実績数(<code>LOADSIZE_RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadsizeResultQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.LOADSIZE_RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)<br>
     * 数値の検索値をセット空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setVacantResultQty(int value, String compcode)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
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
    public void setVacantResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setVacantResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.VACANT_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setVacantResultQtyOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_RESULT_QTY, ascorder) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.VACANT_RESULT_QTY, ascorder) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setVacantResultQtyGroup()
    {
        setGroup(ReArrangeSetting.VACANT_RESULT_QTY) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setVacantResultQtyGroup(int prio)
    {
        setGroup(ReArrangeSetting.VACANT_RESULT_QTY) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setVacantResultQtyCollect()
    {
        setCollect(ReArrangeSetting.VACANT_RESULT_QTY) ;
    }

    /**
     * 空棚最適化実績数(<code>VACANT_RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setVacantResultQtyCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.VACANT_RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ReArrangeSetting.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDateOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(ReArrangeSetting.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDateGroup(int prio)
    {
        setGroup(ReArrangeSetting.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(ReArrangeSetting.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRegistDateCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
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
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPnameOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(ReArrangeSetting.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPnameGroup(int prio)
    {
        setGroup(ReArrangeSetting.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(ReArrangeSetting.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRegistPnameCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDateOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(ReArrangeSetting.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDateGroup(int prio)
    {
        setGroup(ReArrangeSetting.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(ReArrangeSetting.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUpdateDateCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
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
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReArrangeSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(ReArrangeSetting.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePnameOrder(int prio, boolean ascorder)
    {
        setOrder(ReArrangeSetting.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(ReArrangeSetting.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePnameGroup(int prio)
    {
        setGroup(ReArrangeSetting.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(ReArrangeSetting.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUpdatePnameCollect(String sqlfunc)
    {
        setCollect(ReArrangeSetting.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: ReArrangeSettingSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
