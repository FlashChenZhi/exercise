// $Id: MoveResultSearchKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.MoveResult;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * MOVERESULT用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class MoveResultSearchKey
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
    public MoveResultSearchKey()
    {
        super(MoveResult.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)<br>
     * 文字列の検索値をセット移動入庫作業日(<code>STORAGE_WORK_DAY</code>)します。
     */
    public void setStorageWorkDay(String value)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, value) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDay(String[] values)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, values, true) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDay(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageWorkDay(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
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
    public void setStorageWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageWorkDayOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_DAY, ascorder) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDayOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_DAY, ascorder) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)のグループ順をセットします。
     */
    public void setStorageWorkDayGroup()
    {
        setGroup(MoveResult.STORAGE_WORK_DAY) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDayGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_WORK_DAY) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の情報取得を設定します。
     */
    public void setStorageWorkDayCollect()
    {
        setCollect(MoveResult.STORAGE_WORK_DAY) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageWorkDayCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>JOB_NO</code>)<br>
     * 文字列の検索値をセット作業No.(<code>JOB_NO</code>)します。
     */
    public void setJobNo(String value)
    {
        setKey(MoveResult.JOB_NO, value) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobNo(String[] values)
    {
        setKey(MoveResult.JOB_NO, values, true) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobNo(String value, String compcode)
    {
        setKey(MoveResult.JOB_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
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
    public void setJobNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.JOB_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.JOB_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のグループ順をセットします。
     */
    public void setJobNoGroup()
    {
        setGroup(MoveResult.JOB_NO) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobNoGroup(int prio)
    {
        setGroup(MoveResult.JOB_NO) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の情報取得を設定します。
     */
    public void setJobNoCollect()
    {
        setCollect(MoveResult.JOB_NO) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJobNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.JOB_NO, sqlfunc, null) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)<br>
     * 文字列の検索値をセット設定単位キー(<code>SETTING_UNIT_KEY</code>)します。
     */
    public void setSettingUnitKey(String value)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, value) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKey(String[] values)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, values, true) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKey(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSettingUnitKey(String value, String compcode)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
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
    public void setSettingUnitKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingUnitKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSettingUnitKeyOrder(boolean ascorder)
    {
        setOrder(MoveResult.SETTING_UNIT_KEY, ascorder) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKeyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.SETTING_UNIT_KEY, ascorder) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のグループ順をセットします。
     */
    public void setSettingUnitKeyGroup()
    {
        setGroup(MoveResult.SETTING_UNIT_KEY) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSettingUnitKeyGroup(int prio)
    {
        setGroup(MoveResult.SETTING_UNIT_KEY) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の情報取得を設定します。
     */
    public void setSettingUnitKeyCollect()
    {
        setCollect(MoveResult.SETTING_UNIT_KEY) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSettingUnitKeyCollect(String sqlfunc)
    {
        setCollect(MoveResult.SETTING_UNIT_KEY, sqlfunc, null) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値をセット作業区分(<code>JOB_TYPE</code>)します。
     */
    public void setJobType(String value)
    {
        setKey(MoveResult.JOB_TYPE, value) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values)
    {
        setKey(MoveResult.JOB_TYPE, values, true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(MoveResult.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
    public void setJobType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobTypeOrder(boolean ascorder)
    {
        setOrder(MoveResult.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     */
    public void setJobTypeGroup()
    {
        setGroup(MoveResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeGroup(int prio)
    {
        setGroup(MoveResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     */
    public void setJobTypeCollect()
    {
        setCollect(MoveResult.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJobTypeCollect(String sqlfunc)
    {
        setCollect(MoveResult.JOB_TYPE, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(MoveResult.STATUS_FLAG, value) ;
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
        setKey(MoveResult.STATUS_FLAG, values, true) ;
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
        setKey(MoveResult.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(MoveResult.STATUS_FLAG, value, compcode, "", "", true) ;
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
        setKey(MoveResult.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(MoveResult.STATUS_FLAG, ascorder) ;
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
        setOrder(MoveResult.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(MoveResult.STATUS_FLAG) ;
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
        setGroup(MoveResult.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(MoveResult.STATUS_FLAG) ;
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
        setCollect(MoveResult.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)<br>
     * 文字列の検索値をセットハードウェア区分(<code>HARDWARE_TYPE</code>)します。
     */
    public void setHardwareType(String value)
    {
        setKey(MoveResult.HARDWARE_TYPE, value) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareType(String[] values)
    {
        setKey(MoveResult.HARDWARE_TYPE, values, true) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareType(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.HARDWARE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardwareType(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.HARDWARE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardwareType(String value, String compcode)
    {
        setKey(MoveResult.HARDWARE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
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
    public void setHardwareType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.HARDWARE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardwareType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.HARDWARE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardwareTypeOrder(boolean ascorder)
    {
        setOrder(MoveResult.HARDWARE_TYPE, ascorder) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareTypeOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.HARDWARE_TYPE, ascorder) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のグループ順をセットします。
     */
    public void setHardwareTypeGroup()
    {
        setGroup(MoveResult.HARDWARE_TYPE) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHardwareTypeGroup(int prio)
    {
        setGroup(MoveResult.HARDWARE_TYPE) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の情報取得を設定します。
     */
    public void setHardwareTypeCollect()
    {
        setCollect(MoveResult.HARDWARE_TYPE) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHardwareTypeCollect(String sqlfunc)
    {
        setCollect(MoveResult.HARDWARE_TYPE, sqlfunc, null) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 在庫ID(<code>STOCK_ID</code>)<br>
     * 文字列の検索値をセット在庫ID(<code>STOCK_ID</code>)します。
     */
    public void setStockId(String value)
    {
        setKey(MoveResult.STOCK_ID, value) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockId(String[] values)
    {
        setKey(MoveResult.STOCK_ID, values, true) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockId(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STOCK_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STOCK_ID, values, and_or_toNext) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStockId(String value, String compcode)
    {
        setKey(MoveResult.STOCK_ID, value, compcode, "", "", true) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
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
    public void setStockId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STOCK_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STOCK_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStockIdOrder(boolean ascorder)
    {
        setOrder(MoveResult.STOCK_ID, ascorder) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockIdOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STOCK_ID, ascorder) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)のグループ順をセットします。
     */
    public void setStockIdGroup()
    {
        setGroup(MoveResult.STOCK_ID) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockIdGroup(int prio)
    {
        setGroup(MoveResult.STOCK_ID) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の情報取得を設定します。
     */
    public void setStockIdCollect()
    {
        setCollect(MoveResult.STOCK_ID) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStockIdCollect(String sqlfunc)
    {
        setCollect(MoveResult.STOCK_ID, sqlfunc, null) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)<br>
     * 文字列の検索値をセット入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)します。
     */
    public void setWorkConnKey(String value)
    {
        setKey(MoveResult.WORK_CONN_KEY, value) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKey(String[] values)
    {
        setKey(MoveResult.WORK_CONN_KEY, values, true) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKey(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.WORK_CONN_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkConnKey(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.WORK_CONN_KEY, values, and_or_toNext) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkConnKey(String value, String compcode)
    {
        setKey(MoveResult.WORK_CONN_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
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
    public void setWorkConnKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.WORK_CONN_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkConnKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.WORK_CONN_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkConnKeyOrder(boolean ascorder)
    {
        setOrder(MoveResult.WORK_CONN_KEY, ascorder) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKeyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.WORK_CONN_KEY, ascorder) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)のグループ順をセットします。
     */
    public void setWorkConnKeyGroup()
    {
        setGroup(MoveResult.WORK_CONN_KEY) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkConnKeyGroup(int prio)
    {
        setGroup(MoveResult.WORK_CONN_KEY) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の情報取得を設定します。
     */
    public void setWorkConnKeyCollect()
    {
        setCollect(MoveResult.WORK_CONN_KEY) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkConnKeyCollect(String sqlfunc)
    {
        setCollect(MoveResult.WORK_CONN_KEY, sqlfunc, null) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)<br>
     * 文字列の検索値をセット移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)します。
     */
    public void setRetrievalAreaNo(String value)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, value) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNo(String[] values)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, values, true) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalAreaNo(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
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
    public void setRetrievalAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalAreaNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_AREA_NO, ascorder) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_AREA_NO, ascorder) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)のグループ順をセットします。
     */
    public void setRetrievalAreaNoGroup()
    {
        setGroup(MoveResult.RETRIEVAL_AREA_NO) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalAreaNoGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_AREA_NO) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の情報取得を設定します。
     */
    public void setRetrievalAreaNoCollect()
    {
        setCollect(MoveResult.RETRIEVAL_AREA_NO) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalAreaNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_AREA_NO, sqlfunc, null) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)します。
     */
    public void setRetrievalLocationNo(String value)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, value) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNo(String[] values)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, values, true) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalLocationNo(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
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
    public void setRetrievalLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalLocationNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_LOCATION_NO, ascorder) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_LOCATION_NO, ascorder) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setRetrievalLocationNoGroup()
    {
        setGroup(MoveResult.RETRIEVAL_LOCATION_NO) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalLocationNoGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_LOCATION_NO) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setRetrievalLocationNoCollect()
    {
        setCollect(MoveResult.RETRIEVAL_LOCATION_NO) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalLocationNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(MoveResult.CONSIGNOR_CODE, value) ;
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
        setKey(MoveResult.CONSIGNOR_CODE, values, true) ;
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
        setKey(MoveResult.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(MoveResult.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(MoveResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(MoveResult.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(MoveResult.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(MoveResult.CONSIGNOR_CODE) ;
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
        setGroup(MoveResult.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(MoveResult.CONSIGNOR_CODE) ;
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
        setCollect(MoveResult.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷主名称(<code>CONSIGNOR_NAME</code>)<br>
     * 文字列の検索値をセット荷主名称(<code>CONSIGNOR_NAME</code>)します。
     */
    public void setConsignorName(String value)
    {
        setKey(MoveResult.CONSIGNOR_NAME, value) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorName(String[] values)
    {
        setKey(MoveResult.CONSIGNOR_NAME, values, true) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorName(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.CONSIGNOR_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorName(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.CONSIGNOR_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorName(String value, String compcode)
    {
        setKey(MoveResult.CONSIGNOR_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
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
    public void setConsignorName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.CONSIGNOR_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorNameOrder(boolean ascorder)
    {
        setOrder(MoveResult.CONSIGNOR_NAME, ascorder) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorNameOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.CONSIGNOR_NAME, ascorder) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)のグループ順をセットします。
     */
    public void setConsignorNameGroup()
    {
        setGroup(MoveResult.CONSIGNOR_NAME) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorNameGroup(int prio)
    {
        setGroup(MoveResult.CONSIGNOR_NAME) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の情報取得を設定します。
     */
    public void setConsignorNameCollect()
    {
        setCollect(MoveResult.CONSIGNOR_NAME) ;
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setConsignorNameCollect(String sqlfunc)
    {
        setCollect(MoveResult.CONSIGNOR_NAME, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(MoveResult.ITEM_CODE, value) ;
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
        setKey(MoveResult.ITEM_CODE, values, true) ;
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
        setKey(MoveResult.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(MoveResult.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(MoveResult.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(MoveResult.ITEM_CODE, ascorder) ;
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
        setOrder(MoveResult.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(MoveResult.ITEM_CODE) ;
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
        setGroup(MoveResult.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(MoveResult.ITEM_CODE) ;
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
        setCollect(MoveResult.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 商品名称(<code>ITEM_NAME</code>)<br>
     * 文字列の検索値をセット商品名称(<code>ITEM_NAME</code>)します。
     */
    public void setItemName(String value)
    {
        setKey(MoveResult.ITEM_NAME, value) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemName(String[] values)
    {
        setKey(MoveResult.ITEM_NAME, values, true) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemName(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.ITEM_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemName(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.ITEM_NAME, values, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemName(String value, String compcode)
    {
        setKey(MoveResult.ITEM_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
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
    public void setItemName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.ITEM_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.ITEM_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemNameOrder(boolean ascorder)
    {
        setOrder(MoveResult.ITEM_NAME, ascorder) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemNameOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.ITEM_NAME, ascorder) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のグループ順をセットします。
     */
    public void setItemNameGroup()
    {
        setGroup(MoveResult.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemNameGroup(int prio)
    {
        setGroup(MoveResult.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の情報取得を設定します。
     */
    public void setItemNameCollect()
    {
        setCollect(MoveResult.ITEM_NAME) ;
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItemNameCollect(String sqlfunc)
    {
        setCollect(MoveResult.ITEM_NAME, sqlfunc, null) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value JANコード(<code>JAN</code>)<br>
     * 文字列の検索値をセットJANコード(<code>JAN</code>)します。
     */
    public void setJan(String value)
    {
        setKey(MoveResult.JAN, value) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJan(String[] values)
    {
        setKey(MoveResult.JAN, values, true) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJan(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.JAN, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJan(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.JAN, values, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJan(String value, String compcode)
    {
        setKey(MoveResult.JAN, value, compcode, "", "", true) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
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
    public void setJan(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.JAN, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * JANコード(<code>JAN</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJan(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.JAN, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * JANコード(<code>JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJanOrder(boolean ascorder)
    {
        setOrder(MoveResult.JAN, ascorder) ;
    }

    /**
     * JANコード(<code>JAN</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJanOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.JAN, ascorder) ;
    }

    /**
     * JANコード(<code>JAN</code>)のグループ順をセットします。
     */
    public void setJanGroup()
    {
        setGroup(MoveResult.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJanGroup(int prio)
    {
        setGroup(MoveResult.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)の情報取得を設定します。
     */
    public void setJanCollect()
    {
        setCollect(MoveResult.JAN) ;
    }

    /**
     * JANコード(<code>JAN</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJanCollect(String sqlfunc)
    {
        setCollect(MoveResult.JAN, sqlfunc, null) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value ケースITF(<code>ITF</code>)<br>
     * 文字列の検索値をセットケースITF(<code>ITF</code>)します。
     */
    public void setItf(String value)
    {
        setKey(MoveResult.ITF, value) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItf(String[] values)
    {
        setKey(MoveResult.ITF, values, true) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItf(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItf(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.ITF, values, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItf(String value, String compcode)
    {
        setKey(MoveResult.ITF, value, compcode, "", "", true) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
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
    public void setItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItfOrder(boolean ascorder)
    {
        setOrder(MoveResult.ITF, ascorder) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItfOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.ITF, ascorder) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のグループ順をセットします。
     */
    public void setItfGroup()
    {
        setGroup(MoveResult.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItfGroup(int prio)
    {
        setGroup(MoveResult.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の情報取得を設定します。
     */
    public void setItfCollect()
    {
        setCollect(MoveResult.ITF) ;
    }

    /**
     * ケースITF(<code>ITF</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItfCollect(String sqlfunc)
    {
        setCollect(MoveResult.ITF, sqlfunc, null) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value ボールITF(<code>BUNDLE_ITF</code>)<br>
     * 文字列の検索値をセットボールITF(<code>BUNDLE_ITF</code>)します。
     */
    public void setBundleItf(String value)
    {
        setKey(MoveResult.BUNDLE_ITF, value) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItf(String[] values)
    {
        setKey(MoveResult.BUNDLE_ITF, values, true) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItf(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ITF, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleItf(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ITF, values, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBundleItf(String value, String compcode)
    {
        setKey(MoveResult.BUNDLE_ITF, value, compcode, "", "", true) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
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
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ITF, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleItf(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ITF, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBundleItfOrder(boolean ascorder)
    {
        setOrder(MoveResult.BUNDLE_ITF, ascorder) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItfOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.BUNDLE_ITF, ascorder) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のグループ順をセットします。
     */
    public void setBundleItfGroup()
    {
        setGroup(MoveResult.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleItfGroup(int prio)
    {
        setGroup(MoveResult.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の情報取得を設定します。
     */
    public void setBundleItfCollect()
    {
        setCollect(MoveResult.BUNDLE_ITF) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBundleItfCollect(String sqlfunc)
    {
        setCollect(MoveResult.BUNDLE_ITF, sqlfunc, null) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ケース入数(<code>ENTERING_QTY</code>)<br>
     * 数値の検索値をセットケース入数(<code>ENTERING_QTY</code>)します。
     */
    public void setEnteringQty(int value)
    {
        setKey(MoveResult.ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty(int[] values)
    {
        setKey(MoveResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEnteringQty(int value, String compcode)
    {
        setKey(MoveResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
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
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEnteringQtyOrder(boolean ascorder)
    {
        setOrder(MoveResult.ENTERING_QTY, ascorder) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQtyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.ENTERING_QTY, ascorder) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のグループ順をセットします。
     */
    public void setEnteringQtyGroup()
    {
        setGroup(MoveResult.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEnteringQtyGroup(int prio)
    {
        setGroup(MoveResult.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の情報取得を設定します。
     */
    public void setEnteringQtyCollect()
    {
        setCollect(MoveResult.ENTERING_QTY) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEnteringQtyCollect(String sqlfunc)
    {
        setCollect(MoveResult.ENTERING_QTY, sqlfunc, null) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value ボール入数(<code>BUNDLE_ENTERING_QTY</code>)<br>
     * 数値の検索値をセットボール入数(<code>BUNDLE_ENTERING_QTY</code>)します。
     */
    public void setBundleEnteringQty(int value)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQty(int[] values)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQty(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleEnteringQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBundleEnteringQty(int value, String compcode)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
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
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBundleEnteringQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBundleEnteringQtyOrder(boolean ascorder)
    {
        setOrder(MoveResult.BUNDLE_ENTERING_QTY, ascorder) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQtyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.BUNDLE_ENTERING_QTY, ascorder) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のグループ順をセットします。
     */
    public void setBundleEnteringQtyGroup()
    {
        setGroup(MoveResult.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBundleEnteringQtyGroup(int prio)
    {
        setGroup(MoveResult.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の情報取得を設定します。
     */
    public void setBundleEnteringQtyCollect()
    {
        setCollect(MoveResult.BUNDLE_ENTERING_QTY) ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBundleEnteringQtyCollect(String sqlfunc)
    {
        setCollect(MoveResult.BUNDLE_ENTERING_QTY, sqlfunc, null) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value ロットNo.(<code>LOT_NO</code>)<br>
     * 文字列の検索値をセットロットNo.(<code>LOT_NO</code>)します。
     */
    public void setLotNo(String value)
    {
        setKey(MoveResult.LOT_NO, value) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values)
    {
        setKey(MoveResult.LOT_NO, values, true) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.LOT_NO, values, and_or_toNext) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLotNo(String value, String compcode)
    {
        setKey(MoveResult.LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
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
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLotNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.LOT_NO, ascorder) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLotNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.LOT_NO, ascorder) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)のグループ順をセットします。
     */
    public void setLotNoGroup()
    {
        setGroup(MoveResult.LOT_NO) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLotNoGroup(int prio)
    {
        setGroup(MoveResult.LOT_NO) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の情報取得を設定します。
     */
    public void setLotNoCollect()
    {
        setCollect(MoveResult.LOT_NO) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLotNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.LOT_NO, sqlfunc, null) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 入庫日(<code>STORAGE_DAY</code>)<br>
     * 文字列の検索値をセット入庫日(<code>STORAGE_DAY</code>)します。
     */
    public void setStorageDay(String value)
    {
        setKey(MoveResult.STORAGE_DAY, value) ;
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
        setKey(MoveResult.STORAGE_DAY, values, true) ;
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
        setKey(MoveResult.STORAGE_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_DAY, values, and_or_toNext) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageDay(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_DAY, value, compcode, "", "", true) ;
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
        setKey(MoveResult.STORAGE_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.STORAGE_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageDayOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_DAY, ascorder) ;
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
        setOrder(MoveResult.STORAGE_DAY, ascorder) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)のグループ順をセットします。
     */
    public void setStorageDayGroup()
    {
        setGroup(MoveResult.STORAGE_DAY) ;
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
        setGroup(MoveResult.STORAGE_DAY) ;
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>)の情報取得を設定します。
     */
    public void setStorageDayCollect()
    {
        setCollect(MoveResult.STORAGE_DAY) ;
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
        setCollect(MoveResult.STORAGE_DAY, sqlfunc, null) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 入庫日時(<code>STORAGE_DATE</code>)<br>
     * 日付の検索値をセット入庫日時(<code>STORAGE_DATE</code>)します。
     */
    public void setStorageDate(Date value)
    {
        setKey(MoveResult.STORAGE_DATE, value) ;
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
        setKey(MoveResult.STORAGE_DATE, values, true) ;
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
        setKey(MoveResult.STORAGE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageDate(Date value, String compcode)
    {
        setKey(MoveResult.STORAGE_DATE, value, compcode, "", "", true) ;
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
        setKey(MoveResult.STORAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.STORAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_DATE, ascorder) ;
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
        setOrder(MoveResult.STORAGE_DATE, ascorder) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)のグループ順をセットします。
     */
    public void setStorageDateGroup()
    {
        setGroup(MoveResult.STORAGE_DATE) ;
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
        setGroup(MoveResult.STORAGE_DATE) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>)の情報取得を設定します。
     */
    public void setStorageDateCollect()
    {
        setCollect(MoveResult.STORAGE_DATE) ;
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
        setCollect(MoveResult.STORAGE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 最終出庫日(<code>RETRIEVAL_DAY</code>)<br>
     * 文字列の検索値をセット最終出庫日(<code>RETRIEVAL_DAY</code>)します。
     */
    public void setRetrievalDay(String value)
    {
        setKey(MoveResult.RETRIEVAL_DAY, value) ;
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
        setKey(MoveResult.RETRIEVAL_DAY, values, true) ;
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
        setKey(MoveResult.RETRIEVAL_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalDay(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_DAY, values, and_or_toNext) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalDay(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_DAY, value, compcode, "", "", true) ;
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
        setKey(MoveResult.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.RETRIEVAL_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalDayOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_DAY, ascorder) ;
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
        setOrder(MoveResult.RETRIEVAL_DAY, ascorder) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)のグループ順をセットします。
     */
    public void setRetrievalDayGroup()
    {
        setGroup(MoveResult.RETRIEVAL_DAY) ;
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
        setGroup(MoveResult.RETRIEVAL_DAY) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>)の情報取得を設定します。
     */
    public void setRetrievalDayCollect()
    {
        setCollect(MoveResult.RETRIEVAL_DAY) ;
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
        setCollect(MoveResult.RETRIEVAL_DAY, sqlfunc, null) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)<br>
     * 文字列の検索値をセット移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)します。
     */
    public void setRetrievalUserId(String value)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, value) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserId(String[] values)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, values, true) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserId(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalUserId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, values, and_or_toNext) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalUserId(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
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
    public void setRetrievalUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalUserIdOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_USER_ID, ascorder) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_USER_ID, ascorder) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)のグループ順をセットします。
     */
    public void setRetrievalUserIdGroup()
    {
        setGroup(MoveResult.RETRIEVAL_USER_ID) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserIdGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_USER_ID) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の情報取得を設定します。
     */
    public void setRetrievalUserIdCollect()
    {
        setCollect(MoveResult.RETRIEVAL_USER_ID) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalUserIdCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_USER_ID, sqlfunc, null) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)<br>
     * 文字列の検索値をセット移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)します。
     */
    public void setRetrievalUserName(String value)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, value) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserName(String[] values)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, values, true) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserName(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalUserName(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, values, and_or_toNext) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalUserName(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
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
    public void setRetrievalUserName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalUserNameOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_USER_NAME, ascorder) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserNameOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_USER_NAME, ascorder) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)のグループ順をセットします。
     */
    public void setRetrievalUserNameGroup()
    {
        setGroup(MoveResult.RETRIEVAL_USER_NAME) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalUserNameGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_USER_NAME) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の情報取得を設定します。
     */
    public void setRetrievalUserNameCollect()
    {
        setCollect(MoveResult.RETRIEVAL_USER_NAME) ;
    }

    /**
     * 移動出庫ユーザ名称(<code>RETRIEVAL_USER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalUserNameCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_USER_NAME, sqlfunc, null) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)します。
     */
    public void setRetrievalTerminalNo(String value)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, value) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNo(String[] values)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, values, true) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalTerminalNo(String value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setRetrievalTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalTerminalNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_TERMINAL_NO, ascorder) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_TERMINAL_NO, ascorder) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setRetrievalTerminalNoGroup()
    {
        setGroup(MoveResult.RETRIEVAL_TERMINAL_NO) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalTerminalNoGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_TERMINAL_NO) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setRetrievalTerminalNoCollect()
    {
        setCollect(MoveResult.RETRIEVAL_TERMINAL_NO) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalTerminalNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)<br>
     * 日付の検索値をセット移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)します。
     */
    public void setRetrievalWorkDate(Date value)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, value) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDate(Date[] values)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, values, true) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalWorkDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, values, and_or_toNext) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalWorkDate(Date value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
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
    public void setRetrievalWorkDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalWorkDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalWorkDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_WORK_DATE, ascorder) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDateOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_WORK_DATE, ascorder) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)のグループ順をセットします。
     */
    public void setRetrievalWorkDateGroup()
    {
        setGroup(MoveResult.RETRIEVAL_WORK_DATE) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkDateGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_WORK_DATE) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の情報取得を設定します。
     */
    public void setRetrievalWorkDateCollect()
    {
        setCollect(MoveResult.RETRIEVAL_WORK_DATE) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalWorkDateCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_WORK_DATE, sqlfunc, null) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)<br>
     * 数値の検索値をセット移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)します。
     */
    public void setRetrievalWorkSecond(int value)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecond(int[] values)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalWorkSecond(int value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
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
    public void setRetrievalWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalWorkSecondOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_WORK_SECOND, ascorder) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecondOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_WORK_SECOND, ascorder) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)のグループ順をセットします。
     */
    public void setRetrievalWorkSecondGroup()
    {
        setGroup(MoveResult.RETRIEVAL_WORK_SECOND) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalWorkSecondGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_WORK_SECOND) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の情報取得を設定します。
     */
    public void setRetrievalWorkSecondCollect()
    {
        setCollect(MoveResult.RETRIEVAL_WORK_SECOND) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalWorkSecondCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_WORK_SECOND, sqlfunc, null) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値をセット予定数(<code>PLAN_QTY</code>)します。
     */
    public void setPlanQty(int value)
    {
        setKey(MoveResult.PLAN_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(MoveResult.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(MoveResult.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(MoveResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(MoveResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanQtyOrder(boolean ascorder)
    {
        setOrder(MoveResult.PLAN_QTY, ascorder) ;
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
        setOrder(MoveResult.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     */
    public void setPlanQtyGroup()
    {
        setGroup(MoveResult.PLAN_QTY) ;
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
        setGroup(MoveResult.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     */
    public void setPlanQtyCollect()
    {
        setCollect(MoveResult.PLAN_QTY) ;
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
        setCollect(MoveResult.PLAN_QTY, sqlfunc, null) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)<br>
     * 数値の検索値をセット出庫数(<code>RETRIEVAL_RESULT_QTY</code>)します。
     */
    public void setRetrievalResultQty(int value)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQty(int[] values)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQty(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalResultQty(int value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
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
    public void setRetrievalResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalResultQtyOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_RESULT_QTY, ascorder) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_RESULT_QTY, ascorder) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setRetrievalResultQtyGroup()
    {
        setGroup(MoveResult.RETRIEVAL_RESULT_QTY) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalResultQtyGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_RESULT_QTY) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setRetrievalResultQtyCollect()
    {
        setCollect(MoveResult.RETRIEVAL_RESULT_QTY) ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalResultQtyCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 入庫数(<code>STORAGE_RESULT_QTY</code>)<br>
     * 数値の検索値をセット入庫数(<code>STORAGE_RESULT_QTY</code>)します。
     */
    public void setStorageResultQty(int value)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQty(int[] values)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQty(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageResultQty(int value, String compcode)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
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
    public void setStorageResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageResultQtyOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_RESULT_QTY, ascorder) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_RESULT_QTY, ascorder) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setStorageResultQtyGroup()
    {
        setGroup(MoveResult.STORAGE_RESULT_QTY) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageResultQtyGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_RESULT_QTY) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setStorageResultQtyCollect()
    {
        setCollect(MoveResult.STORAGE_RESULT_QTY) ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageResultQtyCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫エリア(<code>STORAGE_AREA_NO</code>)<br>
     * 文字列の検索値をセット移動入庫エリア(<code>STORAGE_AREA_NO</code>)します。
     */
    public void setStorageAreaNo(String value)
    {
        setKey(MoveResult.STORAGE_AREA_NO, value) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNo(String[] values)
    {
        setKey(MoveResult.STORAGE_AREA_NO, values, true) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageAreaNo(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
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
    public void setStorageAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageAreaNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_AREA_NO, ascorder) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_AREA_NO, ascorder) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)のグループ順をセットします。
     */
    public void setStorageAreaNoGroup()
    {
        setGroup(MoveResult.STORAGE_AREA_NO) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageAreaNoGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_AREA_NO) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の情報取得を設定します。
     */
    public void setStorageAreaNoCollect()
    {
        setCollect(MoveResult.STORAGE_AREA_NO) ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageAreaNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_AREA_NO, sqlfunc, null) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)します。
     */
    public void setStorageLocationNo(String value)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, value) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNo(String[] values)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, values, true) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageLocationNo(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
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
    public void setStorageLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageLocationNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_LOCATION_NO, ascorder) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_LOCATION_NO, ascorder) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setStorageLocationNoGroup()
    {
        setGroup(MoveResult.STORAGE_LOCATION_NO) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageLocationNoGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_LOCATION_NO) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setStorageLocationNoCollect()
    {
        setCollect(MoveResult.STORAGE_LOCATION_NO) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageLocationNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)<br>
     * 文字列の検索値をセット移動入庫ユーザID(<code>STORAGE_USER_ID</code>)します。
     */
    public void setStorageUserId(String value)
    {
        setKey(MoveResult.STORAGE_USER_ID, value) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserId(String[] values)
    {
        setKey(MoveResult.STORAGE_USER_ID, values, true) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserId(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageUserId(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_ID, values, and_or_toNext) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageUserId(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
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
    public void setStorageUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageUserIdOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_USER_ID, ascorder) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_USER_ID, ascorder) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)のグループ順をセットします。
     */
    public void setStorageUserIdGroup()
    {
        setGroup(MoveResult.STORAGE_USER_ID) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserIdGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_USER_ID) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の情報取得を設定します。
     */
    public void setStorageUserIdCollect()
    {
        setCollect(MoveResult.STORAGE_USER_ID) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageUserIdCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_USER_ID, sqlfunc, null) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)<br>
     * 文字列の検索値をセット移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)します。
     */
    public void setStorageUserName(String value)
    {
        setKey(MoveResult.STORAGE_USER_NAME, value) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserName(String[] values)
    {
        setKey(MoveResult.STORAGE_USER_NAME, values, true) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserName(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageUserName(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_NAME, values, and_or_toNext) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageUserName(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_USER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
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
    public void setStorageUserName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageUserName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_USER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageUserNameOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_USER_NAME, ascorder) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserNameOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_USER_NAME, ascorder) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)のグループ順をセットします。
     */
    public void setStorageUserNameGroup()
    {
        setGroup(MoveResult.STORAGE_USER_NAME) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageUserNameGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_USER_NAME) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の情報取得を設定します。
     */
    public void setStorageUserNameCollect()
    {
        setCollect(MoveResult.STORAGE_USER_NAME) ;
    }

    /**
     * 移動入庫ユーザ名称(<code>STORAGE_USER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageUserNameCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_USER_NAME, sqlfunc, null) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)します。
     */
    public void setStorageTerminalNo(String value)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, value) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNo(String[] values)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, values, true) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageTerminalNo(String value, String compcode)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setStorageTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageTerminalNoOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_TERMINAL_NO, ascorder) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_TERMINAL_NO, ascorder) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setStorageTerminalNoGroup()
    {
        setGroup(MoveResult.STORAGE_TERMINAL_NO) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageTerminalNoGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_TERMINAL_NO) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setStorageTerminalNoCollect()
    {
        setCollect(MoveResult.STORAGE_TERMINAL_NO) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageTerminalNoCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)<br>
     * 日付の検索値をセット移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)します。
     */
    public void setStorageWorkDate(Date value)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, value) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDate(Date[] values)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, values, true) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, values, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageWorkDate(Date value, String compcode)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
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
    public void setStorageWorkDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageWorkDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_DATE, ascorder) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDateOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_DATE, ascorder) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)のグループ順をセットします。
     */
    public void setStorageWorkDateGroup()
    {
        setGroup(MoveResult.STORAGE_WORK_DATE) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkDateGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_WORK_DATE) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の情報取得を設定します。
     */
    public void setStorageWorkDateCollect()
    {
        setCollect(MoveResult.STORAGE_WORK_DATE) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageWorkDateCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_WORK_DATE, sqlfunc, null) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 実績報告区分(<code>REPORT_FLAG</code>)<br>
     * 文字列の検索値をセット実績報告区分(<code>REPORT_FLAG</code>)します。
     */
    public void setReportFlag(String value)
    {
        setKey(MoveResult.REPORT_FLAG, value) ;
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
        setKey(MoveResult.REPORT_FLAG, values, true) ;
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
        setKey(MoveResult.REPORT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportFlag(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.REPORT_FLAG, values, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReportFlag(String value, String compcode)
    {
        setKey(MoveResult.REPORT_FLAG, value, compcode, "", "", true) ;
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
        setKey(MoveResult.REPORT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.REPORT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReportFlagOrder(boolean ascorder)
    {
        setOrder(MoveResult.REPORT_FLAG, ascorder) ;
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
        setOrder(MoveResult.REPORT_FLAG, ascorder) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のグループ順をセットします。
     */
    public void setReportFlagGroup()
    {
        setGroup(MoveResult.REPORT_FLAG) ;
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
        setGroup(MoveResult.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の情報取得を設定します。
     */
    public void setReportFlagCollect()
    {
        setCollect(MoveResult.REPORT_FLAG) ;
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
        setCollect(MoveResult.REPORT_FLAG, sqlfunc, null) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)<br>
     * 数値の検索値をセット移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)します。
     */
    public void setStorageWorkSecond(int value)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecond(int[] values)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageWorkSecond(int value, String compcode)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
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
    public void setStorageWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.STORAGE_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageWorkSecondOrder(boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_SECOND, ascorder) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecondOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.STORAGE_WORK_SECOND, ascorder) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)のグループ順をセットします。
     */
    public void setStorageWorkSecondGroup()
    {
        setGroup(MoveResult.STORAGE_WORK_SECOND) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageWorkSecondGroup(int prio)
    {
        setGroup(MoveResult.STORAGE_WORK_SECOND) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の情報取得を設定します。
     */
    public void setStorageWorkSecondCollect()
    {
        setCollect(MoveResult.STORAGE_WORK_SECOND) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageWorkSecondCollect(String sqlfunc)
    {
        setCollect(MoveResult.STORAGE_WORK_SECOND, sqlfunc, null) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)<br>
     * 日付の検索値をセット出庫開始日時(<code>RETRIEVAL_START_DATE</code>)します。
     */
    public void setRetrievalStartDate(Date value)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, value) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDate(Date[] values)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, values, true) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDate(Date[] values, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalStartDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, values, and_or_toNext) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalStartDate(Date value, String compcode)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
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
    public void setRetrievalStartDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalStartDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(MoveResult.RETRIEVAL_START_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalStartDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_START_DATE, ascorder) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDateOrder(int prio, boolean ascorder)
    {
        setOrder(MoveResult.RETRIEVAL_START_DATE, ascorder) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)のグループ順をセットします。
     */
    public void setRetrievalStartDateGroup()
    {
        setGroup(MoveResult.RETRIEVAL_START_DATE) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStartDateGroup(int prio)
    {
        setGroup(MoveResult.RETRIEVAL_START_DATE) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の情報取得を設定します。
     */
    public void setRetrievalStartDateCollect()
    {
        setCollect(MoveResult.RETRIEVAL_START_DATE) ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalStartDateCollect(String sqlfunc)
    {
        setCollect(MoveResult.RETRIEVAL_START_DATE, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(MoveResult.REGIST_DATE, value) ;
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
        setKey(MoveResult.REGIST_DATE, values, true) ;
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
        setKey(MoveResult.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(MoveResult.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(MoveResult.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.REGIST_DATE, ascorder) ;
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
        setOrder(MoveResult.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(MoveResult.REGIST_DATE) ;
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
        setGroup(MoveResult.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(MoveResult.REGIST_DATE) ;
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
        setCollect(MoveResult.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(MoveResult.REGIST_PNAME, value) ;
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
        setKey(MoveResult.REGIST_PNAME, values, true) ;
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
        setKey(MoveResult.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(MoveResult.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(MoveResult.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(MoveResult.REGIST_PNAME, ascorder) ;
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
        setOrder(MoveResult.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(MoveResult.REGIST_PNAME) ;
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
        setGroup(MoveResult.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(MoveResult.REGIST_PNAME) ;
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
        setCollect(MoveResult.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(MoveResult.LAST_UPDATE_DATE, value) ;
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
        setKey(MoveResult.LAST_UPDATE_DATE, values, true) ;
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
        setKey(MoveResult.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(MoveResult.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(MoveResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(MoveResult.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(MoveResult.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(MoveResult.LAST_UPDATE_DATE) ;
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
        setGroup(MoveResult.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(MoveResult.LAST_UPDATE_DATE) ;
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
        setCollect(MoveResult.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(MoveResult.LAST_UPDATE_PNAME, value) ;
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
        setKey(MoveResult.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(MoveResult.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(MoveResult.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(MoveResult.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(MoveResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(MoveResult.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(MoveResult.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(MoveResult.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(MoveResult.LAST_UPDATE_PNAME) ;
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
        setGroup(MoveResult.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(MoveResult.LAST_UPDATE_PNAME) ;
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
        setCollect(MoveResult.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: MoveResultSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
