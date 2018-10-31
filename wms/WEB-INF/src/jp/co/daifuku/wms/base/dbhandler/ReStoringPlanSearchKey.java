// $Id: ReStoringPlanSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
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
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * RESTORINGPLAN用の検索キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class ReStoringPlanSearchKey
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
    public ReStoringPlanSearchKey()
    {
        super(ReStoringPlan.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)<br>
     * 文字列の検索値をセット予定一意キー(<code>PLAN_UKEY</code>)します。
     */
    public void setPlanUkey(String value)
    {
        setKey(ReStoringPlan.PLAN_UKEY, value) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values)
    {
        setKey(ReStoringPlan.PLAN_UKEY, values, true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_UKEY, values, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanUkey(String value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_UKEY, value, compcode, "", "", true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
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
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanUkeyOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkeyOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     */
    public void setPlanUkeyGroup()
    {
        setGroup(ReStoringPlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkeyGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     */
    public void setPlanUkeyCollect()
    {
        setCollect(ReStoringPlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanUkeyCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_UKEY, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(ReStoringPlan.STATUS_FLAG, value) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(ReStoringPlan.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(ReStoringPlan.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(ReStoringPlan.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(ReStoringPlan.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
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
        setOrder(ReStoringPlan.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(ReStoringPlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(ReStoringPlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(ReStoringPlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo.(<code>STATION_NO</code>)<br>
     * 文字列の検索値をセットステーションNo.(<code>STATION_NO</code>)します。
     */
    public void setStationNo(String value)
    {
        setKey(ReStoringPlan.STATION_NO, value) ;
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
        setKey(ReStoringPlan.STATION_NO, values, true) ;
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
        setKey(ReStoringPlan.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(ReStoringPlan.STATION_NO, value, compcode, "", "", true) ;
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
        setKey(ReStoringPlan.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReStoringPlan.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.STATION_NO, ascorder) ;
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
        setOrder(ReStoringPlan.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(ReStoringPlan.STATION_NO) ;
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
        setGroup(ReStoringPlan.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(ReStoringPlan.STATION_NO) ;
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
        setCollect(ReStoringPlan.STATION_NO, sqlfunc, null) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>WORK_NO</code>)<br>
     * 文字列の検索値をセット作業No.(<code>WORK_NO</code>)します。
     */
    public void setWorkNo(String value)
    {
        setKey(ReStoringPlan.WORK_NO, value) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values)
    {
        setKey(ReStoringPlan.WORK_NO, values, true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkNo(String value, String compcode)
    {
        setKey(ReStoringPlan.WORK_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
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
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkNoOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.WORK_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.WORK_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のグループ順をセットします。
     */
    public void setWorkNoGroup()
    {
        setGroup(ReStoringPlan.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNoGroup(int prio)
    {
        setGroup(ReStoringPlan.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の情報取得を設定します。
     */
    public void setWorkNoCollect()
    {
        setCollect(ReStoringPlan.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkNoCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.WORK_NO, sqlfunc, null) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 予定日(<code>PLAN_DAY</code>)<br>
     * 文字列の検索値をセット予定日(<code>PLAN_DAY</code>)します。
     */
    public void setPlanDay(String value)
    {
        setKey(ReStoringPlan.PLAN_DAY, value) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values)
    {
        setKey(ReStoringPlan.PLAN_DAY, values, true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_DAY, values, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanDay(String value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
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
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanDayOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDayOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     */
    public void setPlanDayGroup()
    {
        setGroup(ReStoringPlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDayGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     */
    public void setPlanDayCollect()
    {
        setCollect(ReStoringPlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanDayCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_DAY, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, values, true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
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
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(ReStoringPlan.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeGroup(int prio)
    {
        setGroup(ReStoringPlan.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(ReStoringPlan.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setConsignorCodeCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 予定エリアNo.(<code>PLAN_AREA_NO</code>)<br>
     * 文字列の検索値をセット予定エリアNo.(<code>PLAN_AREA_NO</code>)します。
     */
    public void setPlanAreaNo(String value)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, value) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, values, true) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanAreaNo(String value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
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
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanAreaNoOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_AREA_NO, ascorder) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_AREA_NO, ascorder) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のグループ順をセットします。
     */
    public void setPlanAreaNoGroup()
    {
        setGroup(ReStoringPlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNoGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の情報取得を設定します。
     */
    public void setPlanAreaNoCollect()
    {
        setCollect(ReStoringPlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanAreaNoCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_AREA_NO, sqlfunc, null) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 予定棚No.(<code>PLAN_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット予定棚No.(<code>PLAN_LOCATION_NO</code>)します。
     */
    public void setPlanLocationNo(String value)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, value) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, values, true) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLocationNo(String value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
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
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLocationNoOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_LOCATION_NO, ascorder) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_LOCATION_NO, ascorder) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setPlanLocationNoGroup()
    {
        setGroup(ReStoringPlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNoGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setPlanLocationNoCollect()
    {
        setCollect(ReStoringPlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanLocationNoCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(ReStoringPlan.ITEM_CODE, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values)
    {
        setKey(ReStoringPlan.ITEM_CODE, values, true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(ReStoringPlan.ITEM_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
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
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(ReStoringPlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeGroup(int prio)
    {
        setGroup(ReStoringPlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(ReStoringPlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItemCodeCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ロットNo.(<code>PLAN_LOT_NO</code>)<br>
     * 文字列の検索値をセット予定ロットNo.(<code>PLAN_LOT_NO</code>)します。
     */
    public void setPlanLotNo(String value)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, value) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, values, true) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLotNo(String value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
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
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLotNoOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     */
    public void setPlanLotNoGroup()
    {
        setGroup(ReStoringPlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNoGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     */
    public void setPlanLotNoCollect()
    {
        setCollect(ReStoringPlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanLotNoCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 入庫日(<code>STORAGE_DAY</code>)<br>
     * 文字列の検索値をセット入庫日(<code>STORAGE_DAY</code>)します。
     */
    public void setStorageDay(String value)
    {
        setKey(ReStoringPlan.STORAGE_DAY, value) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDay(String[] values)
    {
        setKey(ReStoringPlan.STORAGE_DAY, values, true) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDay(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DAY, values, and_or_toNext) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageDay(String value, String compcode)
    {
        setKey(ReStoringPlan.STORAGE_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
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
    public void setStorageDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageDayOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.STORAGE_DAY, ascorder) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDayOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.STORAGE_DAY, ascorder) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のグループ順をセットします。
     */
    public void setStorageDayGroup()
    {
        setGroup(ReStoringPlan.STORAGE_DAY) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDayGroup(int prio)
    {
        setGroup(ReStoringPlan.STORAGE_DAY) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の情報取得を設定します。
     */
    public void setStorageDayCollect()
    {
        setCollect(ReStoringPlan.STORAGE_DAY) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageDayCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.STORAGE_DAY, sqlfunc, null) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 入庫日時(<code>STORAGE_DATE</code>)<br>
     * 日付の検索値をセット入庫日時(<code>STORAGE_DATE</code>)します。
     */
    public void setStorageDate(Date value)
    {
        setKey(ReStoringPlan.STORAGE_DATE, value) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDate(Date[] values)
    {
        setKey(ReStoringPlan.STORAGE_DATE, values, true) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDate(Date[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageDate(Date value, String compcode)
    {
        setKey(ReStoringPlan.STORAGE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
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
    public void setStorageDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.STORAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageDateOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.STORAGE_DATE, ascorder) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDateOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.STORAGE_DATE, ascorder) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のグループ順をセットします。
     */
    public void setStorageDateGroup()
    {
        setGroup(ReStoringPlan.STORAGE_DATE) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageDateGroup(int prio)
    {
        setGroup(ReStoringPlan.STORAGE_DATE) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の情報取得を設定します。
     */
    public void setStorageDateCollect()
    {
        setCollect(ReStoringPlan.STORAGE_DATE) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageDateCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.STORAGE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 最終出庫日(<code>RETRIEVAL_DAY</code>)<br>
     * 文字列の検索値をセット最終出庫日(<code>RETRIEVAL_DAY</code>)します。
     */
    public void setRetrievalDay(String value)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, value) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDay(String[] values)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, values, true) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDay(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, values, and_or_toNext) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalDay(String value, String compcode)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
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
    public void setRetrievalDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalDayOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.RETRIEVAL_DAY, ascorder) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDayOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.RETRIEVAL_DAY, ascorder) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のグループ順をセットします。
     */
    public void setRetrievalDayGroup()
    {
        setGroup(ReStoringPlan.RETRIEVAL_DAY) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDayGroup(int prio)
    {
        setGroup(ReStoringPlan.RETRIEVAL_DAY) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の情報取得を設定します。
     */
    public void setRetrievalDayCollect()
    {
        setCollect(ReStoringPlan.RETRIEVAL_DAY) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalDayCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.RETRIEVAL_DAY, sqlfunc, null) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
     * 
     * @param value 払出日時(<code>REMOVE_DATE</code>)<br>
     * 日付の検索値をセット払出日時(<code>REMOVE_DATE</code>)します。
     */
    public void setRemoveDate(Date value)
    {
        setKey(ReStoringPlan.REMOVE_DATE, value) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveDate(Date[] values)
    {
        setKey(ReStoringPlan.REMOVE_DATE, values, true) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveDate(Date[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.REMOVE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemoveDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REMOVE_DATE, values, and_or_toNext) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRemoveDate(Date value, String compcode)
    {
        setKey(ReStoringPlan.REMOVE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
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
    public void setRemoveDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.REMOVE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemoveDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REMOVE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRemoveDateOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.REMOVE_DATE, ascorder) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveDateOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.REMOVE_DATE, ascorder) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)のグループ順をセットします。
     */
    public void setRemoveDateGroup()
    {
        setGroup(ReStoringPlan.REMOVE_DATE) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveDateGroup(int prio)
    {
        setGroup(ReStoringPlan.REMOVE_DATE) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の情報取得を設定します。
     */
    public void setRemoveDateCollect()
    {
        setCollect(ReStoringPlan.REMOVE_DATE) ;
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRemoveDateCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.REMOVE_DATE, sqlfunc, null) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値をセット予定数(<code>PLAN_QTY</code>)します。
     */
    public void setPlanQty(int value)
    {
        setKey(ReStoringPlan.PLAN_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values)
    {
        setKey(ReStoringPlan.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(ReStoringPlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
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
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanQtyOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     */
    public void setPlanQtyGroup()
    {
        setGroup(ReStoringPlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQtyGroup(int prio)
    {
        setGroup(ReStoringPlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     */
    public void setPlanQtyCollect()
    {
        setCollect(ReStoringPlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanQtyCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.PLAN_QTY, sqlfunc, null) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 実績数(<code>RESULT_QTY</code>)<br>
     * 数値の検索値をセット実績数(<code>RESULT_QTY</code>)します。
     */
    public void setResultQty(int value)
    {
        setKey(ReStoringPlan.RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values)
    {
        setKey(ReStoringPlan.RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultQty(int value, String compcode)
    {
        setKey(ReStoringPlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
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
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultQtyOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setResultQtyGroup()
    {
        setGroup(ReStoringPlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultQtyGroup(int prio)
    {
        setGroup(ReStoringPlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setResultQtyCollect()
    {
        setCollect(ReStoringPlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setResultQtyCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)<br>
     * 数値の検索値をセット欠品数(<code>SHORTAGE_QTY</code>)します。
     */
    public void setShortageQty(int value)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShortageQty(int value, String compcode)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
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
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShortageQtyOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQtyOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     */
    public void setShortageQtyGroup()
    {
        setGroup(ReStoringPlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQtyGroup(int prio)
    {
        setGroup(ReStoringPlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     */
    public void setShortageQtyCollect()
    {
        setCollect(ReStoringPlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShortageQtyCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.SHORTAGE_QTY, sqlfunc, null) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 実績報告区分(<code>REPORT_FLAG</code>)<br>
     * 文字列の検索値をセット実績報告区分(<code>REPORT_FLAG</code>)します。
     */
    public void setReportFlag(String value)
    {
        setKey(ReStoringPlan.REPORT_FLAG, value) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values)
    {
        setKey(ReStoringPlan.REPORT_FLAG, values, true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.REPORT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REPORT_FLAG, values, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReportFlag(String value, String compcode)
    {
        setKey(ReStoringPlan.REPORT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
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
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.REPORT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REPORT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReportFlagOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.REPORT_FLAG, ascorder) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlagOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.REPORT_FLAG, ascorder) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のグループ順をセットします。
     */
    public void setReportFlagGroup()
    {
        setGroup(ReStoringPlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlagGroup(int prio)
    {
        setGroup(ReStoringPlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の情報取得を設定します。
     */
    public void setReportFlagCollect()
    {
        setCollect(ReStoringPlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReportFlagCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.REPORT_FLAG, sqlfunc, null) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
     */
    public void setWorkDay(String value)
    {
        setKey(ReStoringPlan.WORK_DAY, value) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values)
    {
        setKey(ReStoringPlan.WORK_DAY, values, true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values, String and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(ReStoringPlan.WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
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
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayOrder(int prio, boolean ascorder)
    {
        setOrder(ReStoringPlan.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(ReStoringPlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayGroup(int prio)
    {
        setGroup(ReStoringPlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(ReStoringPlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkDayCollect(String sqlfunc)
    {
        setCollect(ReStoringPlan.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(ReStoringPlan.REGIST_DATE, value) ;
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
        setKey(ReStoringPlan.REGIST_DATE, values, true) ;
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
        setKey(ReStoringPlan.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ReStoringPlan.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(ReStoringPlan.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReStoringPlan.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.REGIST_DATE, ascorder) ;
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
        setOrder(ReStoringPlan.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(ReStoringPlan.REGIST_DATE) ;
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
        setGroup(ReStoringPlan.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(ReStoringPlan.REGIST_DATE) ;
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
        setCollect(ReStoringPlan.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(ReStoringPlan.REGIST_PNAME, value) ;
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
        setKey(ReStoringPlan.REGIST_PNAME, values, true) ;
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
        setKey(ReStoringPlan.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ReStoringPlan.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReStoringPlan.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReStoringPlan.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.REGIST_PNAME, ascorder) ;
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
        setOrder(ReStoringPlan.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(ReStoringPlan.REGIST_PNAME) ;
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
        setGroup(ReStoringPlan.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(ReStoringPlan.REGIST_PNAME) ;
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
        setCollect(ReStoringPlan.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(ReStoringPlan.LAST_UPDATE_DATE, value) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_DATE, values, true) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ReStoringPlan.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(ReStoringPlan.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(ReStoringPlan.LAST_UPDATE_DATE) ;
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
        setGroup(ReStoringPlan.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(ReStoringPlan.LAST_UPDATE_DATE) ;
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
        setCollect(ReStoringPlan.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, value) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReStoringPlan.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(ReStoringPlan.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(ReStoringPlan.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(ReStoringPlan.LAST_UPDATE_PNAME) ;
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
        setGroup(ReStoringPlan.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(ReStoringPlan.LAST_UPDATE_PNAME) ;
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
        setCollect(ReStoringPlan.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: ReStoringPlanSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
