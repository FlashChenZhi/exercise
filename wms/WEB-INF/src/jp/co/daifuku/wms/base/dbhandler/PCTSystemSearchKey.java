// $Id: PCTSystemSearchKey.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTSYSTEM用の検索キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */


public class PCTSystemSearchKey
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
    public PCTSystemSearchKey()
    {
        super(PCTSystem.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)<br>
     * 数値の検索値をセットランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)します。
     */
    public void setARankStandardValue(int value)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value)) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setARankStandardValue(int[] values)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setARankStandardValue(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setARankStandardValue(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setARankStandardValue(int value, String compcode)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
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
    public void setARankStandardValue(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setARankStandardValue(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setARankStandardValueOrder(boolean ascorder)
    {
        setOrder(PCTSystem.A_RANK_STANDARD_VALUE, ascorder) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setARankStandardValueOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.A_RANK_STANDARD_VALUE, ascorder) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)のグループ順をセットします。
     */
    public void setARankStandardValueGroup()
    {
        setGroup(PCTSystem.A_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setARankStandardValueGroup(int prio)
    {
        setGroup(PCTSystem.A_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の情報取得を設定します。
     */
    public void setARankStandardValueCollect()
    {
        setCollect(PCTSystem.A_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setARankStandardValueCollect(String sqlfunc)
    {
        setCollect(PCTSystem.A_RANK_STANDARD_VALUE, sqlfunc, null) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)<br>
     * 数値の検索値をセットランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)します。
     */
    public void setBRankStandardValue(int value)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value)) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBRankStandardValue(int[] values)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBRankStandardValue(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBRankStandardValue(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBRankStandardValue(int value, String compcode)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
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
    public void setBRankStandardValue(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBRankStandardValue(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBRankStandardValueOrder(boolean ascorder)
    {
        setOrder(PCTSystem.B_RANK_STANDARD_VALUE, ascorder) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBRankStandardValueOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.B_RANK_STANDARD_VALUE, ascorder) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)のグループ順をセットします。
     */
    public void setBRankStandardValueGroup()
    {
        setGroup(PCTSystem.B_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBRankStandardValueGroup(int prio)
    {
        setGroup(PCTSystem.B_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の情報取得を設定します。
     */
    public void setBRankStandardValueCollect()
    {
        setCollect(PCTSystem.B_RANK_STANDARD_VALUE) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBRankStandardValueCollect(String sqlfunc)
    {
        setCollect(PCTSystem.B_RANK_STANDARD_VALUE, sqlfunc, null) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)<br>
     * 文字列の検索値をセットランク設定フラグ(<code>RANK_SETTING_FLAG</code>)します。
     */
    public void setRankSettingFlag(String value)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRankSettingFlag(String[] values)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, values, true) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRankSettingFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRankSettingFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, values, and_or_toNext) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRankSettingFlag(String value, String compcode)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
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
    public void setRankSettingFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRankSettingFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRankSettingFlagOrder(boolean ascorder)
    {
        setOrder(PCTSystem.RANK_SETTING_FLAG, ascorder) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRankSettingFlagOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.RANK_SETTING_FLAG, ascorder) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)のグループ順をセットします。
     */
    public void setRankSettingFlagGroup()
    {
        setGroup(PCTSystem.RANK_SETTING_FLAG) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRankSettingFlagGroup(int prio)
    {
        setGroup(PCTSystem.RANK_SETTING_FLAG) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の情報取得を設定します。
     */
    public void setRankSettingFlagCollect()
    {
        setCollect(PCTSystem.RANK_SETTING_FLAG) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRankSettingFlagCollect(String sqlfunc)
    {
        setCollect(PCTSystem.RANK_SETTING_FLAG, sqlfunc, null) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value センター名(<code>CENTER_NAME</code>)<br>
     * 文字列の検索値をセットセンター名(<code>CENTER_NAME</code>)します。
     */
    public void setCenterName(String value)
    {
        setKey(PCTSystem.CENTER_NAME, value) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCenterName(String[] values)
    {
        setKey(PCTSystem.CENTER_NAME, values, true) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCenterName(String[] values, String and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCenterName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, values, and_or_toNext) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCenterName(String value, String compcode)
    {
        setKey(PCTSystem.CENTER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
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
    public void setCenterName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCenterName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCenterNameOrder(boolean ascorder)
    {
        setOrder(PCTSystem.CENTER_NAME, ascorder) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCenterNameOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.CENTER_NAME, ascorder) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)のグループ順をセットします。
     */
    public void setCenterNameGroup()
    {
        setGroup(PCTSystem.CENTER_NAME) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCenterNameGroup(int prio)
    {
        setGroup(PCTSystem.CENTER_NAME) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の情報取得を設定します。
     */
    public void setCenterNameCollect()
    {
        setCollect(PCTSystem.CENTER_NAME) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCenterNameCollect(String sqlfunc)
    {
        setCollect(PCTSystem.CENTER_NAME, sqlfunc, null) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value SeqNo.桁数(<code>SEQ_DIGIT</code>)<br>
     * 数値の検索値をセットSeqNo.桁数(<code>SEQ_DIGIT</code>)します。
     */
    public void setSeqDigit(int value)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value)) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSeqDigit(int[] values)
    {
        setKey(PCTSystem.SEQ_DIGIT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSeqDigit(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSeqDigit(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSeqDigit(int value, String compcode)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
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
    public void setSeqDigit(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSeqDigit(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSeqDigitOrder(boolean ascorder)
    {
        setOrder(PCTSystem.SEQ_DIGIT, ascorder) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSeqDigitOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.SEQ_DIGIT, ascorder) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)のグループ順をセットします。
     */
    public void setSeqDigitGroup()
    {
        setGroup(PCTSystem.SEQ_DIGIT) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSeqDigitGroup(int prio)
    {
        setGroup(PCTSystem.SEQ_DIGIT) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の情報取得を設定します。
     */
    public void setSeqDigitCollect()
    {
        setCollect(PCTSystem.SEQ_DIGIT) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSeqDigitCollect(String sqlfunc)
    {
        setCollect(PCTSystem.SEQ_DIGIT, sqlfunc, null) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)<br>
     * 文字列の検索値をセットチェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)します。
     */
    public void setCheckDigitFlag(String value)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCheckDigitFlag(String[] values)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, values, true) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCheckDigitFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCheckDigitFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, values, and_or_toNext) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCheckDigitFlag(String value, String compcode)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
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
    public void setCheckDigitFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCheckDigitFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCheckDigitFlagOrder(boolean ascorder)
    {
        setOrder(PCTSystem.CHECK_DIGIT_FLAG, ascorder) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCheckDigitFlagOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.CHECK_DIGIT_FLAG, ascorder) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)のグループ順をセットします。
     */
    public void setCheckDigitFlagGroup()
    {
        setGroup(PCTSystem.CHECK_DIGIT_FLAG) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCheckDigitFlagGroup(int prio)
    {
        setGroup(PCTSystem.CHECK_DIGIT_FLAG) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の情報取得を設定します。
     */
    public void setCheckDigitFlagCollect()
    {
        setCollect(PCTSystem.CHECK_DIGIT_FLAG) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCheckDigitFlagCollect(String sqlfunc)
    {
        setCollect(PCTSystem.CHECK_DIGIT_FLAG, sqlfunc, null) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)<br>
     * 文字列の検索値をセットPCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)します。
     */
    public void setPctmasterLoadFlag(String value)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctmasterLoadFlag(String[] values)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, values, true) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPctmasterLoadFlag(String[] values, String and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctmasterLoadFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, values, and_or_toNext) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPctmasterLoadFlag(String value, String compcode)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
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
    public void setPctmasterLoadFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPctmasterLoadFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPctmasterLoadFlagOrder(boolean ascorder)
    {
        setOrder(PCTSystem.PCTMASTER_LOAD_FLAG, ascorder) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctmasterLoadFlagOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.PCTMASTER_LOAD_FLAG, ascorder) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)のグループ順をセットします。
     */
    public void setPctmasterLoadFlagGroup()
    {
        setGroup(PCTSystem.PCTMASTER_LOAD_FLAG) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPctmasterLoadFlagGroup(int prio)
    {
        setGroup(PCTSystem.PCTMASTER_LOAD_FLAG) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の情報取得を設定します。
     */
    public void setPctmasterLoadFlagCollect()
    {
        setCollect(PCTSystem.PCTMASTER_LOAD_FLAG) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPctmasterLoadFlagCollect(String sqlfunc)
    {
        setCollect(PCTSystem.PCTMASTER_LOAD_FLAG, sqlfunc, null) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)<br>
     * 数値の検索値をセット初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)します。
     */
    public void setDefultDistinctRate(int value)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDefultDistinctRate(int[] values)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDefultDistinctRate(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDefultDistinctRate(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDefultDistinctRate(int value, String compcode)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
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
    public void setDefultDistinctRate(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDefultDistinctRate(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDefultDistinctRateOrder(boolean ascorder)
    {
        setOrder(PCTSystem.DEFULT_DISTINCT_RATE, ascorder) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDefultDistinctRateOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.DEFULT_DISTINCT_RATE, ascorder) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)のグループ順をセットします。
     */
    public void setDefultDistinctRateGroup()
    {
        setGroup(PCTSystem.DEFULT_DISTINCT_RATE) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDefultDistinctRateGroup(int prio)
    {
        setGroup(PCTSystem.DEFULT_DISTINCT_RATE) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の情報取得を設定します。
     */
    public void setDefultDistinctRateCollect()
    {
        setCollect(PCTSystem.DEFULT_DISTINCT_RATE) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDefultDistinctRateCollect(String sqlfunc)
    {
        setCollect(PCTSystem.DEFULT_DISTINCT_RATE, sqlfunc, null) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)<br>
     * 数値の検索値をセットオーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)します。
     */
    public void setOrderMaxWeight(int value)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderMaxWeight(int[] values)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOrderMaxWeight(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOrderMaxWeight(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setOrderMaxWeight(int value, String compcode)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
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
    public void setOrderMaxWeight(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOrderMaxWeight(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setOrderMaxWeightOrder(boolean ascorder)
    {
        setOrder(PCTSystem.ORDER_MAX_WEIGHT, ascorder) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOrderMaxWeightOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.ORDER_MAX_WEIGHT, ascorder) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)のグループ順をセットします。
     */
    public void setOrderMaxWeightGroup()
    {
        setGroup(PCTSystem.ORDER_MAX_WEIGHT) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOrderMaxWeightGroup(int prio)
    {
        setGroup(PCTSystem.ORDER_MAX_WEIGHT) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の情報取得を設定します。
     */
    public void setOrderMaxWeightCollect()
    {
        setCollect(PCTSystem.ORDER_MAX_WEIGHT) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setOrderMaxWeightCollect(String sqlfunc)
    {
        setCollect(PCTSystem.ORDER_MAX_WEIGHT, sqlfunc, null) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順位(<code>ZONE_PRIORITY</code>)<br>
     * 数値の検索値をセットゾーン優先順位(<code>ZONE_PRIORITY</code>)します。
     */
    public void setZonePriority(int value)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setZonePriority(int[] values)
    {
        setKey(PCTSystem.ZONE_PRIORITY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setZonePriority(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setZonePriority(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setZonePriority(int value, String compcode)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
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
    public void setZonePriority(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setZonePriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setZonePriorityOrder(boolean ascorder)
    {
        setOrder(PCTSystem.ZONE_PRIORITY, ascorder) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setZonePriorityOrder(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.ZONE_PRIORITY, ascorder) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)のグループ順をセットします。
     */
    public void setZonePriorityGroup()
    {
        setGroup(PCTSystem.ZONE_PRIORITY) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setZonePriorityGroup(int prio)
    {
        setGroup(PCTSystem.ZONE_PRIORITY) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の情報取得を設定します。
     */
    public void setZonePriorityCollect()
    {
        setCollect(PCTSystem.ZONE_PRIORITY) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setZonePriorityCollect(String sqlfunc)
    {
        setCollect(PCTSystem.ZONE_PRIORITY, sqlfunc, null) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位1(<code>LOC_PRIORITY_1</code>)<br>
     * 数値の検索値をセット棚優先順位1(<code>LOC_PRIORITY_1</code>)します。
     */
    public void setLocPriority1(int value)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority1(int[] values)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority1(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority1(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocPriority1(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
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
    public void setLocPriority1(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority1(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocPriority1Order(boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_1, ascorder) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority1Order(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_1, ascorder) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)のグループ順をセットします。
     */
    public void setLocPriority1Group()
    {
        setGroup(PCTSystem.LOC_PRIORITY_1) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority1Group(int prio)
    {
        setGroup(PCTSystem.LOC_PRIORITY_1) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の情報取得を設定します。
     */
    public void setLocPriority1Collect()
    {
        setCollect(PCTSystem.LOC_PRIORITY_1) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocPriority1Collect(String sqlfunc)
    {
        setCollect(PCTSystem.LOC_PRIORITY_1, sqlfunc, null) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位2(<code>LOC_PRIORITY_2</code>)<br>
     * 数値の検索値をセット棚優先順位2(<code>LOC_PRIORITY_2</code>)します。
     */
    public void setLocPriority2(int value)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority2(int[] values)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority2(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority2(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocPriority2(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
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
    public void setLocPriority2(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority2(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocPriority2Order(boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_2, ascorder) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority2Order(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_2, ascorder) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)のグループ順をセットします。
     */
    public void setLocPriority2Group()
    {
        setGroup(PCTSystem.LOC_PRIORITY_2) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority2Group(int prio)
    {
        setGroup(PCTSystem.LOC_PRIORITY_2) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の情報取得を設定します。
     */
    public void setLocPriority2Collect()
    {
        setCollect(PCTSystem.LOC_PRIORITY_2) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocPriority2Collect(String sqlfunc)
    {
        setCollect(PCTSystem.LOC_PRIORITY_2, sqlfunc, null) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位3(<code>LOC_PRIORITY_3</code>)<br>
     * 数値の検索値をセット棚優先順位3(<code>LOC_PRIORITY_3</code>)します。
     */
    public void setLocPriority3(int value)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority3(int[] values)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority3(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority3(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocPriority3(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
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
    public void setLocPriority3(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority3(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocPriority3Order(boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_3, ascorder) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority3Order(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_3, ascorder) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)のグループ順をセットします。
     */
    public void setLocPriority3Group()
    {
        setGroup(PCTSystem.LOC_PRIORITY_3) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority3Group(int prio)
    {
        setGroup(PCTSystem.LOC_PRIORITY_3) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の情報取得を設定します。
     */
    public void setLocPriority3Collect()
    {
        setCollect(PCTSystem.LOC_PRIORITY_3) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocPriority3Collect(String sqlfunc)
    {
        setCollect(PCTSystem.LOC_PRIORITY_3, sqlfunc, null) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位4(<code>LOC_PRIORITY_4</code>)<br>
     * 数値の検索値をセット棚優先順位4(<code>LOC_PRIORITY_4</code>)します。
     */
    public void setLocPriority4(int value)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority4(int[] values)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority4(int[] values, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority4(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocPriority4(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
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
    public void setLocPriority4(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocPriority4(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocPriority4Order(boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_4, ascorder) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority4Order(int prio, boolean ascorder)
    {
        setOrder(PCTSystem.LOC_PRIORITY_4, ascorder) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)のグループ順をセットします。
     */
    public void setLocPriority4Group()
    {
        setGroup(PCTSystem.LOC_PRIORITY_4) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocPriority4Group(int prio)
    {
        setGroup(PCTSystem.LOC_PRIORITY_4) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の情報取得を設定します。
     */
    public void setLocPriority4Collect()
    {
        setCollect(PCTSystem.LOC_PRIORITY_4) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocPriority4Collect(String sqlfunc)
    {
        setCollect(PCTSystem.LOC_PRIORITY_4, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(PCTSystem.REGIST_DATE, value) ;
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
        setKey(PCTSystem.REGIST_DATE, values, true) ;
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
        setKey(PCTSystem.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(PCTSystem.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTSystem.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTSystem.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(PCTSystem.REGIST_DATE, ascorder) ;
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
        setOrder(PCTSystem.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(PCTSystem.REGIST_DATE) ;
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
        setGroup(PCTSystem.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(PCTSystem.REGIST_DATE) ;
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
        setCollect(PCTSystem.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(PCTSystem.REGIST_PNAME, value) ;
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
        setKey(PCTSystem.REGIST_PNAME, values, true) ;
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
        setKey(PCTSystem.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(PCTSystem.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTSystem.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTSystem.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(PCTSystem.REGIST_PNAME, ascorder) ;
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
        setOrder(PCTSystem.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(PCTSystem.REGIST_PNAME) ;
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
        setGroup(PCTSystem.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(PCTSystem.REGIST_PNAME) ;
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
        setCollect(PCTSystem.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, value) ;
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
        setKey(PCTSystem.LAST_UPDATE_DATE, values, true) ;
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
        setKey(PCTSystem.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(PCTSystem.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTSystem.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(PCTSystem.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(PCTSystem.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(PCTSystem.LAST_UPDATE_DATE) ;
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
        setGroup(PCTSystem.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(PCTSystem.LAST_UPDATE_DATE) ;
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
        setCollect(PCTSystem.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, value) ;
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
        setKey(PCTSystem.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(PCTSystem.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(PCTSystem.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(PCTSystem.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(PCTSystem.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(PCTSystem.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(PCTSystem.LAST_UPDATE_PNAME) ;
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
        setGroup(PCTSystem.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(PCTSystem.LAST_UPDATE_PNAME) ;
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
        setCollect(PCTSystem.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: PCTSystemSearchKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
