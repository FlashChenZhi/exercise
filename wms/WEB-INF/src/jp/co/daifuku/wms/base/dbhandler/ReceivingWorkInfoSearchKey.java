// $Id: ReceivingWorkInfoSearchKey.java 5127 2009-10-13 12:20:06Z ota $
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
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * RECEIVINGWORKINFO用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class ReceivingWorkInfoSearchKey
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
    public ReceivingWorkInfoSearchKey()
    {
        super(ReceivingWorkInfo.STORE_NAME) ;
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
        setKey(ReceivingWorkInfo.JOB_NO, value) ;
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
        setKey(ReceivingWorkInfo.JOB_NO, values, true) ;
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
        setKey(ReceivingWorkInfo.JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.JOB_NO, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.JOB_NO, ascorder) ;
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
        setOrder(ReceivingWorkInfo.JOB_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)のグループ順をセットします。
     */
    public void setJobNoGroup()
    {
        setGroup(ReceivingWorkInfo.JOB_NO) ;
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
        setGroup(ReceivingWorkInfo.JOB_NO) ;
    }

    /**
     * 作業No.(<code>JOB_NO</code>)の情報取得を設定します。
     */
    public void setJobNoCollect()
    {
        setCollect(ReceivingWorkInfo.JOB_NO) ;
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
        setCollect(ReceivingWorkInfo.JOB_NO, sqlfunc, null) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 設定単位キー(<code>SETTING_UNIT_KEY</code>)<br>
     * 文字列の検索値をセット設定単位キー(<code>SETTING_UNIT_KEY</code>)します。
     */
    public void setSettingUnitKey(String value)
    {
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, value) ;
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
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, values, true) ;
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
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSettingUnitKey(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSettingUnitKeyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.SETTING_UNIT_KEY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.SETTING_UNIT_KEY, ascorder) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)のグループ順をセットします。
     */
    public void setSettingUnitKeyGroup()
    {
        setGroup(ReceivingWorkInfo.SETTING_UNIT_KEY) ;
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
        setGroup(ReceivingWorkInfo.SETTING_UNIT_KEY) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>)の情報取得を設定します。
     */
    public void setSettingUnitKeyCollect()
    {
        setCollect(ReceivingWorkInfo.SETTING_UNIT_KEY) ;
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
        setCollect(ReceivingWorkInfo.SETTING_UNIT_KEY, sqlfunc, null) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 集約作業No.(<code>COLLECT_JOB_NO</code>)<br>
     * 文字列の検索値をセット集約作業No.(<code>COLLECT_JOB_NO</code>)します。
     */
    public void setCollectJobNo(String value)
    {
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, value) ;
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
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, values, true) ;
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
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCollectJobNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, values, and_or_toNext) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCollectJobNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.COLLECT_JOB_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCollectJobNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.COLLECT_JOB_NO, ascorder) ;
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
        setOrder(ReceivingWorkInfo.COLLECT_JOB_NO, ascorder) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)のグループ順をセットします。
     */
    public void setCollectJobNoGroup()
    {
        setGroup(ReceivingWorkInfo.COLLECT_JOB_NO) ;
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
        setGroup(ReceivingWorkInfo.COLLECT_JOB_NO) ;
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>)の情報取得を設定します。
     */
    public void setCollectJobNoCollect()
    {
        setCollect(ReceivingWorkInfo.COLLECT_JOB_NO) ;
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
        setCollect(ReceivingWorkInfo.COLLECT_JOB_NO, sqlfunc, null) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param value クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)<br>
     * 文字列の検索値をセットクロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)します。
     */
    public void setCrossDockUkey(String value)
    {
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, value) ;
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
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, values, true) ;
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
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCrossDockUkey(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, values, and_or_toNext) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCrossDockUkey(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.CROSS_DOCK_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCrossDockUkeyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.CROSS_DOCK_UKEY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.CROSS_DOCK_UKEY, ascorder) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)のグループ順をセットします。
     */
    public void setCrossDockUkeyGroup()
    {
        setGroup(ReceivingWorkInfo.CROSS_DOCK_UKEY) ;
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
        setGroup(ReceivingWorkInfo.CROSS_DOCK_UKEY) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)の情報取得を設定します。
     */
    public void setCrossDockUkeyCollect()
    {
        setCollect(ReceivingWorkInfo.CROSS_DOCK_UKEY) ;
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
        setCollect(ReceivingWorkInfo.CROSS_DOCK_UKEY, sqlfunc, null) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値をセット作業区分(<code>JOB_TYPE</code>)します。
     */
    public void setJobType(String value)
    {
        setKey(ReceivingWorkInfo.JOB_TYPE, value) ;
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
        setKey(ReceivingWorkInfo.JOB_TYPE, values, true) ;
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
        setKey(ReceivingWorkInfo.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.JOB_TYPE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobTypeOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.JOB_TYPE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.JOB_TYPE, ascorder) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     */
    public void setJobTypeGroup()
    {
        setGroup(ReceivingWorkInfo.JOB_TYPE) ;
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
        setGroup(ReceivingWorkInfo.JOB_TYPE) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     */
    public void setJobTypeCollect()
    {
        setCollect(ReceivingWorkInfo.JOB_TYPE) ;
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
        setCollect(ReceivingWorkInfo.JOB_TYPE, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(ReceivingWorkInfo.STATUS_FLAG, value) ;
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
        setKey(ReceivingWorkInfo.STATUS_FLAG, values, true) ;
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
        setKey(ReceivingWorkInfo.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.STATUS_FLAG, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.STATUS_FLAG, ascorder) ;
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
        setOrder(ReceivingWorkInfo.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(ReceivingWorkInfo.STATUS_FLAG) ;
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
        setGroup(ReceivingWorkInfo.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(ReceivingWorkInfo.STATUS_FLAG) ;
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
        setCollect(ReceivingWorkInfo.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value ハードウェア区分(<code>HARDWARE_TYPE</code>)<br>
     * 文字列の検索値をセットハードウェア区分(<code>HARDWARE_TYPE</code>)します。
     */
    public void setHardwareType(String value)
    {
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, value) ;
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
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, values, true) ;
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
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHardwareType(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, values, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHardwareType(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.HARDWARE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHardwareTypeOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.HARDWARE_TYPE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.HARDWARE_TYPE, ascorder) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)のグループ順をセットします。
     */
    public void setHardwareTypeGroup()
    {
        setGroup(ReceivingWorkInfo.HARDWARE_TYPE) ;
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
        setGroup(ReceivingWorkInfo.HARDWARE_TYPE) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>)の情報取得を設定します。
     */
    public void setHardwareTypeCollect()
    {
        setCollect(ReceivingWorkInfo.HARDWARE_TYPE) ;
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
        setCollect(ReceivingWorkInfo.HARDWARE_TYPE, sqlfunc, null) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)<br>
     * 文字列の検索値をセット予定一意キー(<code>PLAN_UKEY</code>)します。
     */
    public void setPlanUkey(String value)
    {
        setKey(ReceivingWorkInfo.PLAN_UKEY, value) ;
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
        setKey(ReceivingWorkInfo.PLAN_UKEY, values, true) ;
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
        setKey(ReceivingWorkInfo.PLAN_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.PLAN_UKEY, values, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanUkey(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.PLAN_UKEY, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.PLAN_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.PLAN_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanUkeyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.PLAN_UKEY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     */
    public void setPlanUkeyGroup()
    {
        setGroup(ReceivingWorkInfo.PLAN_UKEY) ;
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
        setGroup(ReceivingWorkInfo.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     */
    public void setPlanUkeyCollect()
    {
        setCollect(ReceivingWorkInfo.PLAN_UKEY) ;
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
        setCollect(ReceivingWorkInfo.PLAN_UKEY, sqlfunc, null) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param value TC/DC区分(<code>TCDC_FLAG</code>)<br>
     * 文字列の検索値をセットTC/DC区分(<code>TCDC_FLAG</code>)します。
     */
    public void setTcdcFlag(String value)
    {
        setKey(ReceivingWorkInfo.TCDC_FLAG, value) ;
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
        setKey(ReceivingWorkInfo.TCDC_FLAG, values, true) ;
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
        setKey(ReceivingWorkInfo.TCDC_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTcdcFlag(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.TCDC_FLAG, values, and_or_toNext) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTcdcFlag(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.TCDC_FLAG, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.TCDC_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.TCDC_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTcdcFlagOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.TCDC_FLAG, ascorder) ;
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
        setOrder(ReceivingWorkInfo.TCDC_FLAG, ascorder) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)のグループ順をセットします。
     */
    public void setTcdcFlagGroup()
    {
        setGroup(ReceivingWorkInfo.TCDC_FLAG) ;
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
        setGroup(ReceivingWorkInfo.TCDC_FLAG) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>)の情報取得を設定します。
     */
    public void setTcdcFlagCollect()
    {
        setCollect(ReceivingWorkInfo.TCDC_FLAG) ;
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
        setCollect(ReceivingWorkInfo.TCDC_FLAG, sqlfunc, null) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 予定日(<code>PLAN_DAY</code>)<br>
     * 文字列の検索値をセット予定日(<code>PLAN_DAY</code>)します。
     */
    public void setPlanDay(String value)
    {
        setKey(ReceivingWorkInfo.PLAN_DAY, value) ;
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
        setKey(ReceivingWorkInfo.PLAN_DAY, values, true) ;
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
        setKey(ReceivingWorkInfo.PLAN_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.PLAN_DAY, values, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanDay(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.PLAN_DAY, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.PLAN_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.PLAN_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanDayOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.PLAN_DAY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     */
    public void setPlanDayGroup()
    {
        setGroup(ReceivingWorkInfo.PLAN_DAY) ;
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
        setGroup(ReceivingWorkInfo.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     */
    public void setPlanDayCollect()
    {
        setCollect(ReceivingWorkInfo.PLAN_DAY) ;
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
        setCollect(ReceivingWorkInfo.PLAN_DAY, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, value) ;
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
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, values, true) ;
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
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(ReceivingWorkInfo.CONSIGNOR_CODE) ;
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
        setGroup(ReceivingWorkInfo.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(ReceivingWorkInfo.CONSIGNOR_CODE) ;
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
        setCollect(ReceivingWorkInfo.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 仕入先コード(<code>SUPPLIER_CODE</code>)<br>
     * 文字列の検索値をセット仕入先コード(<code>SUPPLIER_CODE</code>)します。
     */
    public void setSupplierCode(String value)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, value) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCode(String[] values)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, values, true) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCode(String[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSupplierCode(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, values, and_or_toNext) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSupplierCode(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
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
    public void setSupplierCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSupplierCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SUPPLIER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSupplierCodeOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.SUPPLIER_CODE, ascorder) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCodeOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.SUPPLIER_CODE, ascorder) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のグループ順をセットします。
     */
    public void setSupplierCodeGroup()
    {
        setGroup(ReceivingWorkInfo.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCodeGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の情報取得を設定します。
     */
    public void setSupplierCodeCollect()
    {
        setCollect(ReceivingWorkInfo.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSupplierCodeCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.SUPPLIER_CODE, sqlfunc, null) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)<br>
     * 文字列の検索値をセット入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)します。
     */
    public void setReceiveTicketNo(String value)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, value) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNo(String[] values)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, values, true) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNo(String[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveTicketNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, values, and_or_toNext) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReceiveTicketNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, value, compcode, "", "", true) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
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
    public void setReceiveTicketNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveTicketNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_TICKET_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReceiveTicketNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RECEIVE_TICKET_NO, ascorder) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RECEIVE_TICKET_NO, ascorder) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)のグループ順をセットします。
     */
    public void setReceiveTicketNoGroup()
    {
        setGroup(ReceivingWorkInfo.RECEIVE_TICKET_NO) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNoGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.RECEIVE_TICKET_NO) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の情報取得を設定します。
     */
    public void setReceiveTicketNoCollect()
    {
        setCollect(ReceivingWorkInfo.RECEIVE_TICKET_NO) ;
    }

    /**
     * 入荷伝票No.(<code>RECEIVE_TICKET_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReceiveTicketNoCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.RECEIVE_TICKET_NO, sqlfunc, null) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)<br>
     * 数値の検索値をセット入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)します。
     */
    public void setReceiveLineNo(int value)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNo(int[] values)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNo(int[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReceiveLineNo(int value, String compcode)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
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
    public void setReceiveLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReceiveLineNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RECEIVE_LINE_NO, ascorder) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RECEIVE_LINE_NO, ascorder) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)のグループ順をセットします。
     */
    public void setReceiveLineNoGroup()
    {
        setGroup(ReceivingWorkInfo.RECEIVE_LINE_NO) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNoGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.RECEIVE_LINE_NO) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の情報取得を設定します。
     */
    public void setReceiveLineNoCollect()
    {
        setCollect(ReceivingWorkInfo.RECEIVE_LINE_NO) ;
    }

    /**
     * 入荷伝票行No.(<code>RECEIVE_LINE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReceiveLineNoCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.RECEIVE_LINE_NO, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(ReceivingWorkInfo.ITEM_CODE, value) ;
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
        setKey(ReceivingWorkInfo.ITEM_CODE, values, true) ;
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
        setKey(ReceivingWorkInfo.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.ITEM_CODE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(ReceivingWorkInfo.ITEM_CODE) ;
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
        setGroup(ReceivingWorkInfo.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(ReceivingWorkInfo.ITEM_CODE) ;
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
        setCollect(ReceivingWorkInfo.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ロットNo.(<code>PLAN_LOT_NO</code>)<br>
     * 文字列の検索値をセット予定ロットNo.(<code>PLAN_LOT_NO</code>)します。
     */
    public void setPlanLotNo(String value)
    {
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, value) ;
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
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, values, true) ;
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
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLotNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.PLAN_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLotNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.PLAN_LOT_NO, ascorder) ;
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
        setOrder(ReceivingWorkInfo.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     */
    public void setPlanLotNoGroup()
    {
        setGroup(ReceivingWorkInfo.PLAN_LOT_NO) ;
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
        setGroup(ReceivingWorkInfo.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     */
    public void setPlanLotNoCollect()
    {
        setCollect(ReceivingWorkInfo.PLAN_LOT_NO) ;
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
        setCollect(ReceivingWorkInfo.PLAN_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値をセット予定数(<code>PLAN_QTY</code>)します。
     */
    public void setPlanQty(int value)
    {
        setKey(ReceivingWorkInfo.PLAN_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ReceivingWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ReceivingWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(ReceivingWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanQtyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.PLAN_QTY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     */
    public void setPlanQtyGroup()
    {
        setGroup(ReceivingWorkInfo.PLAN_QTY) ;
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
        setGroup(ReceivingWorkInfo.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     */
    public void setPlanQtyCollect()
    {
        setCollect(ReceivingWorkInfo.PLAN_QTY) ;
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
        setCollect(ReceivingWorkInfo.PLAN_QTY, sqlfunc, null) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 実績数(<code>RESULT_QTY</code>)<br>
     * 数値の検索値をセット実績数(<code>RESULT_QTY</code>)します。
     */
    public void setResultQty(int value)
    {
        setKey(ReceivingWorkInfo.RESULT_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ReceivingWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ReceivingWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultQty(int value, String compcode)
    {
        setKey(ReceivingWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultQtyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RESULT_QTY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setResultQtyGroup()
    {
        setGroup(ReceivingWorkInfo.RESULT_QTY) ;
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
        setGroup(ReceivingWorkInfo.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setResultQtyCollect()
    {
        setCollect(ReceivingWorkInfo.RESULT_QTY) ;
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
        setCollect(ReceivingWorkInfo.RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)<br>
     * 数値の検索値をセット欠品数(<code>SHORTAGE_QTY</code>)します。
     */
    public void setShortageQty(int value)
    {
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
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
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShortageQty(int value, String compcode)
    {
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShortageQtyOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.SHORTAGE_QTY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     */
    public void setShortageQtyGroup()
    {
        setGroup(ReceivingWorkInfo.SHORTAGE_QTY) ;
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
        setGroup(ReceivingWorkInfo.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     */
    public void setShortageQtyCollect()
    {
        setCollect(ReceivingWorkInfo.SHORTAGE_QTY) ;
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
        setCollect(ReceivingWorkInfo.SHORTAGE_QTY, sqlfunc, null) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 実績ロットNo.(<code>RESULT_LOT_NO</code>)<br>
     * 文字列の検索値をセット実績ロットNo.(<code>RESULT_LOT_NO</code>)します。
     */
    public void setResultLotNo(String value)
    {
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, value) ;
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
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, values, true) ;
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
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultLotNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.RESULT_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultLotNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.RESULT_LOT_NO, ascorder) ;
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
        setOrder(ReceivingWorkInfo.RESULT_LOT_NO, ascorder) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)のグループ順をセットします。
     */
    public void setResultLotNoGroup()
    {
        setGroup(ReceivingWorkInfo.RESULT_LOT_NO) ;
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
        setGroup(ReceivingWorkInfo.RESULT_LOT_NO) ;
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>)の情報取得を設定します。
     */
    public void setResultLotNoCollect()
    {
        setCollect(ReceivingWorkInfo.RESULT_LOT_NO) ;
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
        setCollect(ReceivingWorkInfo.RESULT_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
     */
    public void setWorkDay(String value)
    {
        setKey(ReceivingWorkInfo.WORK_DAY, value) ;
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
        setKey(ReceivingWorkInfo.WORK_DAY, values, true) ;
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
        setKey(ReceivingWorkInfo.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.WORK_DAY, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.WORK_DAY, ascorder) ;
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
        setOrder(ReceivingWorkInfo.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(ReceivingWorkInfo.WORK_DAY) ;
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
        setGroup(ReceivingWorkInfo.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(ReceivingWorkInfo.WORK_DAY) ;
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
        setCollect(ReceivingWorkInfo.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USER_ID</code>)<br>
     * 文字列の検索値をセットユーザID(<code>USER_ID</code>)します。
     */
    public void setUserId(String value)
    {
        setKey(ReceivingWorkInfo.USER_ID, value) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserId(String[] values)
    {
        setKey(ReceivingWorkInfo.USER_ID, values, true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserId(String[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.USER_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.USER_ID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserId(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.USER_ID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
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
    public void setUserId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.USER_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.USER_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserIdOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserIdOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.USER_ID, ascorder) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     */
    public void setUserIdGroup()
    {
        setGroup(ReceivingWorkInfo.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserIdGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     */
    public void setUserIdCollect()
    {
        setCollect(ReceivingWorkInfo.USER_ID) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUserIdCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.USER_ID, sqlfunc, null) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No.、RFTNo.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値をセット端末No.、RFTNo.(<code>TERMINAL_NO</code>)します。
     */
    public void setTerminalNo(String value)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, value) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, values, true) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalNoOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.TERMINAL_NO, ascorder) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     */
    public void setTerminalNoGroup()
    {
        setGroup(ReceivingWorkInfo.TERMINAL_NO) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNoGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.TERMINAL_NO) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     */
    public void setTerminalNoCollect()
    {
        setCollect(ReceivingWorkInfo.TERMINAL_NO) ;
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalNoCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.TERMINAL_NO, sqlfunc, null) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 作業秒数(<code>WORK_SECOND</code>)<br>
     * 数値の検索値をセット作業秒数(<code>WORK_SECOND</code>)します。
     */
    public void setWorkSecond(int value)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecond(int[] values)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecond(int[] values, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkSecond(int[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkSecond(int value, String compcode)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
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
    public void setWorkSecond(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkSecond(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.WORK_SECOND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkSecondOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.WORK_SECOND, ascorder) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecondOrder(int prio, boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.WORK_SECOND, ascorder) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)のグループ順をセットします。
     */
    public void setWorkSecondGroup()
    {
        setGroup(ReceivingWorkInfo.WORK_SECOND) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkSecondGroup(int prio)
    {
        setGroup(ReceivingWorkInfo.WORK_SECOND) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の情報取得を設定します。
     */
    public void setWorkSecondCollect()
    {
        setCollect(ReceivingWorkInfo.WORK_SECOND) ;
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkSecondCollect(String sqlfunc)
    {
        setCollect(ReceivingWorkInfo.WORK_SECOND, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(ReceivingWorkInfo.REGIST_DATE, value) ;
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
        setKey(ReceivingWorkInfo.REGIST_DATE, values, true) ;
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
        setKey(ReceivingWorkInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(ReceivingWorkInfo.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.REGIST_DATE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(ReceivingWorkInfo.REGIST_DATE) ;
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
        setGroup(ReceivingWorkInfo.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(ReceivingWorkInfo.REGIST_DATE) ;
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
        setCollect(ReceivingWorkInfo.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(ReceivingWorkInfo.REGIST_PNAME, value) ;
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
        setKey(ReceivingWorkInfo.REGIST_PNAME, values, true) ;
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
        setKey(ReceivingWorkInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.REGIST_PNAME, ascorder) ;
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
        setOrder(ReceivingWorkInfo.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(ReceivingWorkInfo.REGIST_PNAME) ;
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
        setGroup(ReceivingWorkInfo.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(ReceivingWorkInfo.REGIST_PNAME) ;
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
        setCollect(ReceivingWorkInfo.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, value) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, values, true) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(ReceivingWorkInfo.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(ReceivingWorkInfo.LAST_UPDATE_DATE) ;
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
        setGroup(ReceivingWorkInfo.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(ReceivingWorkInfo.LAST_UPDATE_DATE) ;
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
        setCollect(ReceivingWorkInfo.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, value) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(ReceivingWorkInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(ReceivingWorkInfo.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(ReceivingWorkInfo.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(ReceivingWorkInfo.LAST_UPDATE_PNAME) ;
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
        setGroup(ReceivingWorkInfo.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(ReceivingWorkInfo.LAST_UPDATE_PNAME) ;
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
        setCollect(ReceivingWorkInfo.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: ReceivingWorkInfoSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
