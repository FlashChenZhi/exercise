// $Id: ShipWorkInfoSearchKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SHIPWORKINFO用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class ShipWorkInfoSearchKey
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
    public ShipWorkInfoSearchKey()
    {
        super(ShipWorkInfo.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>JOB_NO</code>)<br>
     * 文字列の検索値をセット作業No.(<code>JOB_NO</code>)します。
     */
    public void setJobNo(String value)
    {
        setKey(ShipWorkInfo.JOB_NO, value) ;
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
        setKey(ShipWorkInfo.JOB_NO, values, true) ;
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
        setKey(ShipWorkInfo.JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.JOB_NO, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.JOB_NO, ascorder) ;
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
        setOrder(ShipWorkInfo.JOB_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のグループ順をセットします。
     */
    public void setJobNoGroup()
    {
        setGroup(ShipWorkInfo.JOB_NO) ;
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
        setGroup(ShipWorkInfo.JOB_NO) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の情報取得を設定します。
     */
    public void setJobNoCollect()
    {
        setCollect(ShipWorkInfo.JOB_NO) ;
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
        setCollect(ShipWorkInfo.JOB_NO, sqlfunc, null) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)<br>
     * 文字列の検索値をセット設定単位キー(<code>SETTING_UNIT_KEY</code>)します。
     */
    public void setSettingUnitKey(String value)
    {
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, value) ;
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
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, values, true) ;
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
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSettingUnitKey(String value, String compcode)
    {
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSettingUnitKeyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SETTING_UNIT_KEY, ascorder) ;
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
        setOrder(ShipWorkInfo.SETTING_UNIT_KEY, ascorder) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のグループ順をセットします。
     */
    public void setSettingUnitKeyGroup()
    {
        setGroup(ShipWorkInfo.SETTING_UNIT_KEY) ;
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
        setGroup(ShipWorkInfo.SETTING_UNIT_KEY) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の情報取得を設定します。
     */
    public void setSettingUnitKeyCollect()
    {
        setCollect(ShipWorkInfo.SETTING_UNIT_KEY) ;
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
        setCollect(ShipWorkInfo.SETTING_UNIT_KEY, sqlfunc, null) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 集約作業No.(<code>COLLECT_JOB_NO</code>)<br>
     * 文字列の検索値をセット集約作業No.(<code>COLLECT_JOB_NO</code>)します。
     */
    public void setCollectJobNo(String value)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, value) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNo(String[] values)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, values, true) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCollectJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCollectJobNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, value, compcode, "", "", true) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
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
    public void setCollectJobNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCollectJobNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCollectJobNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.COLLECT_JOB_NO, ascorder) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.COLLECT_JOB_NO, ascorder) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のグループ順をセットします。
     */
    public void setCollectJobNoGroup()
    {
        setGroup(ShipWorkInfo.COLLECT_JOB_NO) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCollectJobNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.COLLECT_JOB_NO) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の情報取得を設定します。
     */
    public void setCollectJobNoCollect()
    {
        setCollect(ShipWorkInfo.COLLECT_JOB_NO) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCollectJobNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.COLLECT_JOB_NO, sqlfunc, null) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param value クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)<br>
     * 文字列の検索値をセットクロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)します。
     */
    public void setCrossDockUkey(String value)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, value) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCrossDockUkey(String[] values)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, values, true) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCrossDockUkey(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCrossDockUkey(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, values, and_or_toNext) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCrossDockUkey(String value, String compcode)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, value, compcode, "", "", true) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
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
    public void setCrossDockUkey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCrossDockUkey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.CROSS_DOCK_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCrossDockUkeyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.CROSS_DOCK_UKEY, ascorder) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCrossDockUkeyOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.CROSS_DOCK_UKEY, ascorder) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のグループ順をセットします。
     */
    public void setCrossDockUkeyGroup()
    {
        setGroup(ShipWorkInfo.CROSS_DOCK_UKEY) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCrossDockUkeyGroup(int prio)
    {
        setGroup(ShipWorkInfo.CROSS_DOCK_UKEY) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の情報取得を設定します。
     */
    public void setCrossDockUkeyCollect()
    {
        setCollect(ShipWorkInfo.CROSS_DOCK_UKEY) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCrossDockUkeyCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.CROSS_DOCK_UKEY, sqlfunc, null) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値をセット作業区分(<code>JOB_TYPE</code>)します。
     */
    public void setJobType(String value)
    {
        setKey(ShipWorkInfo.JOB_TYPE, value) ;
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
        setKey(ShipWorkInfo.JOB_TYPE, values, true) ;
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
        setKey(ShipWorkInfo.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(ShipWorkInfo.JOB_TYPE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobTypeOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.JOB_TYPE, ascorder) ;
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
        setOrder(ShipWorkInfo.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     */
    public void setJobTypeGroup()
    {
        setGroup(ShipWorkInfo.JOB_TYPE) ;
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
        setGroup(ShipWorkInfo.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     */
    public void setJobTypeCollect()
    {
        setCollect(ShipWorkInfo.JOB_TYPE) ;
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
        setCollect(ShipWorkInfo.JOB_TYPE, sqlfunc, null) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)します。
     */
    public void setWorkStatusFlag(String value)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, value) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStatusFlag(String[] values)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, values, true) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkStatusFlag(String value, String compcode)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
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
    public void setWorkStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.WORK_STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkStatusFlagOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.WORK_STATUS_FLAG, ascorder) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.WORK_STATUS_FLAG, ascorder) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setWorkStatusFlagGroup()
    {
        setGroup(ShipWorkInfo.WORK_STATUS_FLAG) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkStatusFlagGroup(int prio)
    {
        setGroup(ShipWorkInfo.WORK_STATUS_FLAG) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setWorkStatusFlagCollect()
    {
        setCollect(ShipWorkInfo.WORK_STATUS_FLAG) ;
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkStatusFlagCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.WORK_STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)<br>
     * 文字列の検索値をセットバース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)します。
     */
    public void setBerthStatusFlag(String value)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, value) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthStatusFlag(String[] values)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, values, true) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBerthStatusFlag(String value, String compcode)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
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
    public void setBerthStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBerthStatusFlagOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_STATUS_FLAG, ascorder) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_STATUS_FLAG, ascorder) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setBerthStatusFlagGroup()
    {
        setGroup(ShipWorkInfo.BERTH_STATUS_FLAG) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthStatusFlagGroup(int prio)
    {
        setGroup(ShipWorkInfo.BERTH_STATUS_FLAG) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setBerthStatusFlagCollect()
    {
        setCollect(ShipWorkInfo.BERTH_STATUS_FLAG) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBerthStatusFlagCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.BERTH_STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)<br>
     * 文字列の検索値をセットハードウェア区分(<code>HARDWARE_TYPE</code>)します。
     */
    public void setHardwareType(String value)
    {
        setKey(ShipWorkInfo.HARDWARE_TYPE, value) ;
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
        setKey(ShipWorkInfo.HARDWARE_TYPE, values, true) ;
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
        setKey(ShipWorkInfo.HARDWARE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardwareType(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.HARDWARE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardwareType(String value, String compcode)
    {
        setKey(ShipWorkInfo.HARDWARE_TYPE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardwareTypeOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.HARDWARE_TYPE, ascorder) ;
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
        setOrder(ShipWorkInfo.HARDWARE_TYPE, ascorder) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のグループ順をセットします。
     */
    public void setHardwareTypeGroup()
    {
        setGroup(ShipWorkInfo.HARDWARE_TYPE) ;
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
        setGroup(ShipWorkInfo.HARDWARE_TYPE) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の情報取得を設定します。
     */
    public void setHardwareTypeCollect()
    {
        setCollect(ShipWorkInfo.HARDWARE_TYPE) ;
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
        setCollect(ShipWorkInfo.HARDWARE_TYPE, sqlfunc, null) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)<br>
     * 文字列の検索値をセット予定一意キー(<code>PLAN_UKEY</code>)します。
     */
    public void setPlanUkey(String value)
    {
        setKey(ShipWorkInfo.PLAN_UKEY, value) ;
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
        setKey(ShipWorkInfo.PLAN_UKEY, values, true) ;
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
        setKey(ShipWorkInfo.PLAN_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.PLAN_UKEY, values, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanUkey(String value, String compcode)
    {
        setKey(ShipWorkInfo.PLAN_UKEY, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.PLAN_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.PLAN_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanUkeyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.PLAN_UKEY, ascorder) ;
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
        setOrder(ShipWorkInfo.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     */
    public void setPlanUkeyGroup()
    {
        setGroup(ShipWorkInfo.PLAN_UKEY) ;
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
        setGroup(ShipWorkInfo.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     */
    public void setPlanUkeyCollect()
    {
        setCollect(ShipWorkInfo.PLAN_UKEY) ;
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
        setCollect(ShipWorkInfo.PLAN_UKEY, sqlfunc, null) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param value TC/DC区分(<code>TCDC_FLAG</code>)<br>
     * 文字列の検索値をセットTC/DC区分(<code>TCDC_FLAG</code>)します。
     */
    public void setTcdcFlag(String value)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, value) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTcdcFlag(String[] values)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, values, true) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTcdcFlag(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTcdcFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, values, and_or_toNext) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTcdcFlag(String value, String compcode)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
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
    public void setTcdcFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTcdcFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.TCDC_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTcdcFlagOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.TCDC_FLAG, ascorder) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTcdcFlagOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.TCDC_FLAG, ascorder) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のグループ順をセットします。
     */
    public void setTcdcFlagGroup()
    {
        setGroup(ShipWorkInfo.TCDC_FLAG) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTcdcFlagGroup(int prio)
    {
        setGroup(ShipWorkInfo.TCDC_FLAG) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の情報取得を設定します。
     */
    public void setTcdcFlagCollect()
    {
        setCollect(ShipWorkInfo.TCDC_FLAG) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTcdcFlagCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.TCDC_FLAG, sqlfunc, null) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 予定日(<code>PLAN_DAY</code>)<br>
     * 文字列の検索値をセット予定日(<code>PLAN_DAY</code>)します。
     */
    public void setPlanDay(String value)
    {
        setKey(ShipWorkInfo.PLAN_DAY, value) ;
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
        setKey(ShipWorkInfo.PLAN_DAY, values, true) ;
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
        setKey(ShipWorkInfo.PLAN_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.PLAN_DAY, values, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanDay(String value, String compcode)
    {
        setKey(ShipWorkInfo.PLAN_DAY, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.PLAN_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.PLAN_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanDayOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.PLAN_DAY, ascorder) ;
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
        setOrder(ShipWorkInfo.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     */
    public void setPlanDayGroup()
    {
        setGroup(ShipWorkInfo.PLAN_DAY) ;
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
        setGroup(ShipWorkInfo.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     */
    public void setPlanDayCollect()
    {
        setCollect(ShipWorkInfo.PLAN_DAY) ;
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
        setCollect(ShipWorkInfo.PLAN_DAY, sqlfunc, null) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value バッチNo.(<code>BATCH_NO</code>)<br>
     * 文字列の検索値をセットバッチNo.(<code>BATCH_NO</code>)します。
     */
    public void setBatchNo(String value)
    {
        setKey(ShipWorkInfo.BATCH_NO, value) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNo(String[] values)
    {
        setKey(ShipWorkInfo.BATCH_NO, values, true) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BATCH_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBatchNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BATCH_NO, values, and_or_toNext) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBatchNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.BATCH_NO, value, compcode, "", "", true) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
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
    public void setBatchNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BATCH_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBatchNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BATCH_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBatchNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.BATCH_NO, ascorder) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.BATCH_NO, ascorder) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)のグループ順をセットします。
     */
    public void setBatchNoGroup()
    {
        setGroup(ShipWorkInfo.BATCH_NO) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBatchNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.BATCH_NO) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の情報取得を設定します。
     */
    public void setBatchNoCollect()
    {
        setCollect(ShipWorkInfo.BATCH_NO) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBatchNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.BATCH_NO, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(ShipWorkInfo.CONSIGNOR_CODE, value) ;
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
        setKey(ShipWorkInfo.CONSIGNOR_CODE, values, true) ;
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
        setKey(ShipWorkInfo.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(ShipWorkInfo.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(ShipWorkInfo.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(ShipWorkInfo.CONSIGNOR_CODE) ;
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
        setGroup(ShipWorkInfo.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(ShipWorkInfo.CONSIGNOR_CODE) ;
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
        setCollect(ShipWorkInfo.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)<br>
     * 文字列の検索値をセット出荷先コード(<code>CUSTOMER_CODE</code>)します。
     */
    public void setCustomerCode(String value)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, values, true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerCode(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, values, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCustomerCode(String value, String compcode)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
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
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.CUSTOMER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCustomerCodeOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.CUSTOMER_CODE, ascorder) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCodeOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.CUSTOMER_CODE, ascorder) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のグループ順をセットします。
     */
    public void setCustomerCodeGroup()
    {
        setGroup(ShipWorkInfo.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCodeGroup(int prio)
    {
        setGroup(ShipWorkInfo.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の情報取得を設定します。
     */
    public void setCustomerCodeCollect()
    {
        setCollect(ShipWorkInfo.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCustomerCodeCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.CUSTOMER_CODE, sqlfunc, null) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷伝票No.(<code>SHIP_TICKET_NO</code>)<br>
     * 文字列の検索値をセット出荷伝票No.(<code>SHIP_TICKET_NO</code>)します。
     */
    public void setShipTicketNo(String value)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, value) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNo(String[] values)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, values, true) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipTicketNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, values, and_or_toNext) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShipTicketNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, value, compcode, "", "", true) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
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
    public void setShipTicketNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipTicketNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TICKET_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShipTicketNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_TICKET_NO, ascorder) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_TICKET_NO, ascorder) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)のグループ順をセットします。
     */
    public void setShipTicketNoGroup()
    {
        setGroup(ShipWorkInfo.SHIP_TICKET_NO) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipTicketNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.SHIP_TICKET_NO) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の情報取得を設定します。
     */
    public void setShipTicketNoCollect()
    {
        setCollect(ShipWorkInfo.SHIP_TICKET_NO) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShipTicketNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.SHIP_TICKET_NO, sqlfunc, null) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷伝票行No.(<code>SHIP_LINE_NO</code>)<br>
     * 数値の検索値をセット出荷伝票行No.(<code>SHIP_LINE_NO</code>)します。
     */
    public void setShipLineNo(int value)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNo(int[] values)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNo(int[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShipLineNo(int value, String compcode)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
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
    public void setShipLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShipLineNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_LINE_NO, ascorder) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_LINE_NO, ascorder) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)のグループ順をセットします。
     */
    public void setShipLineNoGroup()
    {
        setGroup(ShipWorkInfo.SHIP_LINE_NO) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipLineNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.SHIP_LINE_NO) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の情報取得を設定します。
     */
    public void setShipLineNoCollect()
    {
        setCollect(ShipWorkInfo.SHIP_LINE_NO) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShipLineNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.SHIP_LINE_NO, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(ShipWorkInfo.ITEM_CODE, value) ;
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
        setKey(ShipWorkInfo.ITEM_CODE, values, true) ;
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
        setKey(ShipWorkInfo.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(ShipWorkInfo.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.ITEM_CODE, ascorder) ;
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
        setOrder(ShipWorkInfo.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(ShipWorkInfo.ITEM_CODE) ;
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
        setGroup(ShipWorkInfo.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(ShipWorkInfo.ITEM_CODE) ;
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
        setCollect(ShipWorkInfo.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ロットNo(<code>PLAN_LOT_NO</code>)<br>
     * 文字列の検索値をセット予定ロットNo(<code>PLAN_LOT_NO</code>)します。
     */
    public void setPlanLotNo(String value)
    {
        setKey(ShipWorkInfo.PLAN_LOT_NO, value) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values)
    {
        setKey(ShipWorkInfo.PLAN_LOT_NO, values, true) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
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
        setKey(ShipWorkInfo.PLAN_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.PLAN_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLotNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.PLAN_LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
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
        setKey(ShipWorkInfo.PLAN_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.PLAN_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLotNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)のソート順をセットします。
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
        setOrder(ShipWorkInfo.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     */
    public void setPlanLotNoGroup()
    {
        setGroup(ShipWorkInfo.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     */
    public void setPlanLotNoCollect()
    {
        setCollect(ShipWorkInfo.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanLotNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.PLAN_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値をセット予定数(<code>PLAN_QTY</code>)します。
     */
    public void setPlanQty(int value)
    {
        setKey(ShipWorkInfo.PLAN_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ShipWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ShipWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(ShipWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanQtyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.PLAN_QTY, ascorder) ;
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
        setOrder(ShipWorkInfo.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     */
    public void setPlanQtyGroup()
    {
        setGroup(ShipWorkInfo.PLAN_QTY) ;
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
        setGroup(ShipWorkInfo.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     */
    public void setPlanQtyCollect()
    {
        setCollect(ShipWorkInfo.PLAN_QTY) ;
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
        setCollect(ShipWorkInfo.PLAN_QTY, sqlfunc, null) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 実績数(<code>RESULT_QTY</code>)<br>
     * 数値の検索値をセット実績数(<code>RESULT_QTY</code>)します。
     */
    public void setResultQty(int value)
    {
        setKey(ShipWorkInfo.RESULT_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ShipWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ShipWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultQty(int value, String compcode)
    {
        setKey(ShipWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultQtyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.RESULT_QTY, ascorder) ;
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
        setOrder(ShipWorkInfo.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setResultQtyGroup()
    {
        setGroup(ShipWorkInfo.RESULT_QTY) ;
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
        setGroup(ShipWorkInfo.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setResultQtyCollect()
    {
        setCollect(ShipWorkInfo.RESULT_QTY) ;
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
        setCollect(ShipWorkInfo.RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)<br>
     * 数値の検索値をセット欠品数(<code>SHORTAGE_QTY</code>)します。
     */
    public void setShortageQty(int value)
    {
        setKey(ShipWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ShipWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ShipWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShortageQty(int value, String compcode)
    {
        setKey(ShipWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShortageQtyOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHORTAGE_QTY, ascorder) ;
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
        setOrder(ShipWorkInfo.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     */
    public void setShortageQtyGroup()
    {
        setGroup(ShipWorkInfo.SHORTAGE_QTY) ;
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
        setGroup(ShipWorkInfo.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     */
    public void setShortageQtyCollect()
    {
        setCollect(ShipWorkInfo.SHORTAGE_QTY) ;
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
        setCollect(ShipWorkInfo.SHORTAGE_QTY, sqlfunc, null) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 実績ロットNo.(<code>RESULT_LOT_NO</code>)<br>
     * 文字列の検索値をセット実績ロットNo.(<code>RESULT_LOT_NO</code>)します。
     */
    public void setResultLotNo(String value)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, value) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultLotNo(String[] values)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, values, true) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultLotNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultLotNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
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
    public void setResultLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultLotNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.RESULT_LOT_NO, ascorder) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultLotNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.RESULT_LOT_NO, ascorder) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のグループ順をセットします。
     */
    public void setResultLotNoGroup()
    {
        setGroup(ShipWorkInfo.RESULT_LOT_NO) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultLotNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.RESULT_LOT_NO) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の情報取得を設定します。
     */
    public void setResultLotNoCollect()
    {
        setCollect(ShipWorkInfo.RESULT_LOT_NO) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setResultLotNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.RESULT_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
     * 
     * @param value 結果バース(<code>RESULT_BERTH</code>)<br>
     * 文字列の検索値をセット結果バース(<code>RESULT_BERTH</code>)します。
     */
    public void setResultBerth(String value)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, value) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultBerth(String[] values)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, values, true) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultBerth(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultBerth(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, values, and_or_toNext) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultBerth(String value, String compcode)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, value, compcode, "", "", true) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
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
    public void setResultBerth(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultBerth(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.RESULT_BERTH, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultBerthOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.RESULT_BERTH, ascorder) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultBerthOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.RESULT_BERTH, ascorder) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)のグループ順をセットします。
     */
    public void setResultBerthGroup()
    {
        setGroup(ShipWorkInfo.RESULT_BERTH) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultBerthGroup(int prio)
    {
        setGroup(ShipWorkInfo.RESULT_BERTH) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の情報取得を設定します。
     */
    public void setResultBerthCollect()
    {
        setCollect(ShipWorkInfo.RESULT_BERTH) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setResultBerthCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.RESULT_BERTH, sqlfunc, null) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
     */
    public void setWorkDay(String value)
    {
        setKey(ShipWorkInfo.WORK_DAY, value) ;
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
        setKey(ShipWorkInfo.WORK_DAY, values, true) ;
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
        setKey(ShipWorkInfo.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(ShipWorkInfo.WORK_DAY, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.WORK_DAY, ascorder) ;
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
        setOrder(ShipWorkInfo.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(ShipWorkInfo.WORK_DAY) ;
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
        setGroup(ShipWorkInfo.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(ShipWorkInfo.WORK_DAY) ;
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
        setCollect(ShipWorkInfo.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 出荷検品ユーザID(<code>SHIP_USER_ID</code>)<br>
     * 文字列の検索値をセット出荷検品ユーザID(<code>SHIP_USER_ID</code>)します。
     */
    public void setShipUserId(String value)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, value) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipUserId(String[] values)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, values, true) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipUserId(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipUserId(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, values, and_or_toNext) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShipUserId(String value, String compcode)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
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
    public void setShipUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShipUserIdOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_USER_ID, ascorder) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_USER_ID, ascorder) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)のグループ順をセットします。
     */
    public void setShipUserIdGroup()
    {
        setGroup(ShipWorkInfo.SHIP_USER_ID) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipUserIdGroup(int prio)
    {
        setGroup(ShipWorkInfo.SHIP_USER_ID) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の情報取得を設定します。
     */
    public void setShipUserIdCollect()
    {
        setCollect(ShipWorkInfo.SHIP_USER_ID) ;
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShipUserIdCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.SHIP_USER_ID, sqlfunc, null) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)します。
     */
    public void setShipTerminalNo(String value)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, value) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTerminalNo(String[] values)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, values, true) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShipTerminalNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setShipTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShipTerminalNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_TERMINAL_NO, ascorder) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_TERMINAL_NO, ascorder) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setShipTerminalNoGroup()
    {
        setGroup(ShipWorkInfo.SHIP_TERMINAL_NO) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipTerminalNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.SHIP_TERMINAL_NO) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setShipTerminalNoCollect()
    {
        setCollect(ShipWorkInfo.SHIP_TERMINAL_NO) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShipTerminalNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.SHIP_TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)<br>
     * 数値の検索値をセット出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)します。
     */
    public void setShipWorkSecond(int value)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipWorkSecond(int[] values)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShipWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShipWorkSecond(int value, String compcode)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
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
    public void setShipWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShipWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.SHIP_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShipWorkSecondOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_WORK_SECOND, ascorder) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipWorkSecondOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.SHIP_WORK_SECOND, ascorder) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)のグループ順をセットします。
     */
    public void setShipWorkSecondGroup()
    {
        setGroup(ShipWorkInfo.SHIP_WORK_SECOND) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShipWorkSecondGroup(int prio)
    {
        setGroup(ShipWorkInfo.SHIP_WORK_SECOND) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の情報取得を設定します。
     */
    public void setShipWorkSecondCollect()
    {
        setCollect(ShipWorkInfo.SHIP_WORK_SECOND) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShipWorkSecondCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.SHIP_WORK_SECOND, sqlfunc, null) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
     * 
     * @param value バース登録ユーザID(<code>BERTH_USER_ID</code>)<br>
     * 文字列の検索値をセットバース登録ユーザID(<code>BERTH_USER_ID</code>)します。
     */
    public void setBerthUserId(String value)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, value) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthUserId(String[] values)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, values, true) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthUserId(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthUserId(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, values, and_or_toNext) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBerthUserId(String value, String compcode)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
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
    public void setBerthUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBerthUserIdOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_USER_ID, ascorder) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_USER_ID, ascorder) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)のグループ順をセットします。
     */
    public void setBerthUserIdGroup()
    {
        setGroup(ShipWorkInfo.BERTH_USER_ID) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthUserIdGroup(int prio)
    {
        setGroup(ShipWorkInfo.BERTH_USER_ID) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の情報取得を設定します。
     */
    public void setBerthUserIdCollect()
    {
        setCollect(ShipWorkInfo.BERTH_USER_ID) ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBerthUserIdCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.BERTH_USER_ID, sqlfunc, null) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)<br>
     * 文字列の検索値をセットバース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)します。
     */
    public void setBerthTerminalNo(String value)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, value) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthTerminalNo(String[] values)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, values, true) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBerthTerminalNo(String value, String compcode)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
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
    public void setBerthTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBerthTerminalNoOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_TERMINAL_NO, ascorder) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_TERMINAL_NO, ascorder) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setBerthTerminalNoGroup()
    {
        setGroup(ShipWorkInfo.BERTH_TERMINAL_NO) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthTerminalNoGroup(int prio)
    {
        setGroup(ShipWorkInfo.BERTH_TERMINAL_NO) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setBerthTerminalNoCollect()
    {
        setCollect(ShipWorkInfo.BERTH_TERMINAL_NO) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBerthTerminalNoCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.BERTH_TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)<br>
     * 数値の検索値をセットバース登録作業秒数(<code>BERTH_WORK_SECOND</code>)します。
     */
    public void setBerthWorkSecond(int value)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthWorkSecond(int[] values)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBerthWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBerthWorkSecond(int value, String compcode)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
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
    public void setBerthWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBerthWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.BERTH_WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBerthWorkSecondOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_WORK_SECOND, ascorder) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthWorkSecondOrder(int prio, boolean ascorder)
    {
        setOrder(ShipWorkInfo.BERTH_WORK_SECOND, ascorder) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)のグループ順をセットします。
     */
    public void setBerthWorkSecondGroup()
    {
        setGroup(ShipWorkInfo.BERTH_WORK_SECOND) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBerthWorkSecondGroup(int prio)
    {
        setGroup(ShipWorkInfo.BERTH_WORK_SECOND) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の情報取得を設定します。
     */
    public void setBerthWorkSecondCollect()
    {
        setCollect(ShipWorkInfo.BERTH_WORK_SECOND) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBerthWorkSecondCollect(String sqlfunc)
    {
        setCollect(ShipWorkInfo.BERTH_WORK_SECOND, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(ShipWorkInfo.REGIST_DATE, value) ;
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
        setKey(ShipWorkInfo.REGIST_DATE, values, true) ;
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
        setKey(ShipWorkInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ShipWorkInfo.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.REGIST_DATE, ascorder) ;
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
        setOrder(ShipWorkInfo.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(ShipWorkInfo.REGIST_DATE) ;
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
        setGroup(ShipWorkInfo.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(ShipWorkInfo.REGIST_DATE) ;
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
        setCollect(ShipWorkInfo.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(ShipWorkInfo.REGIST_PNAME, value) ;
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
        setKey(ShipWorkInfo.REGIST_PNAME, values, true) ;
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
        setKey(ShipWorkInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ShipWorkInfo.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.REGIST_PNAME, ascorder) ;
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
        setOrder(ShipWorkInfo.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(ShipWorkInfo.REGIST_PNAME) ;
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
        setGroup(ShipWorkInfo.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(ShipWorkInfo.REGIST_PNAME) ;
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
        setCollect(ShipWorkInfo.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, value) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, values, true) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(ShipWorkInfo.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(ShipWorkInfo.LAST_UPDATE_DATE) ;
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
        setGroup(ShipWorkInfo.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(ShipWorkInfo.LAST_UPDATE_DATE) ;
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
        setCollect(ShipWorkInfo.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, value) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ShipWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(ShipWorkInfo.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(ShipWorkInfo.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(ShipWorkInfo.LAST_UPDATE_PNAME) ;
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
        setGroup(ShipWorkInfo.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(ShipWorkInfo.LAST_UPDATE_PNAME) ;
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
        setCollect(ShipWorkInfo.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: ShipWorkInfoSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
