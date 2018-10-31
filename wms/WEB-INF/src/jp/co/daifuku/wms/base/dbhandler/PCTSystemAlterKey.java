// $Id: PCTSystemAlterKey.java 3213 2009-03-02 06:59:20Z arai $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * PCTSYSTEM用の更新キークラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTSystemAlterKey
        extends DefaultSQLAlterKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this alter key */
    public static final StoreMetaData $storeMetaData = PCTSystem.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public PCTSystemAlterKey()
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
     * 数値の検索値をランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setARankStandardValue(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setARankStandardValue(int value, String compcode)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setARankStandardValue(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)の更新値をセットします。
     * @param value ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)更新値
     */
    public void updateARankStandardValue(int value)
    {
        setAdhocUpdateValue(PCTSystem.A_RANK_STANDARD_VALUE, HandlerUtil.toObject(value)) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)<br>
     * 数値の検索値をランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setBRankStandardValue(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setBRankStandardValue(int value, String compcode)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setBRankStandardValue(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)の更新値をセットします。
     * @param value ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)更新値
     */
    public void updateBRankStandardValue(int value)
    {
        setAdhocUpdateValue(PCTSystem.B_RANK_STANDARD_VALUE, HandlerUtil.toObject(value)) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)<br>
     * 文字列の検索値をランク設定フラグ(<code>RANK_SETTING_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRankSettingFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, values, and_or_toNext) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRankSettingFlag(String value, String compcode)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRankSettingFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.RANK_SETTING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)の更新値をセットします。
     * @param value ランク設定フラグ(<code>RANK_SETTING_FLAG</code>)更新値
     */
    public void updateRankSettingFlag(String value)
    {
        setAdhocUpdateValue(PCTSystem.RANK_SETTING_FLAG, value) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value センター名(<code>CENTER_NAME</code>)<br>
     * 文字列の検索値をセンター名(<code>CENTER_NAME</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCenterName(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, values, and_or_toNext) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCenterName(String value, String compcode)
    {
        setKey(PCTSystem.CENTER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * センター名(<code>CENTER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCenterName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.CENTER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * センター名(<code>CENTER_NAME</code>)の更新値をセットします。
     * @param value センター名(<code>CENTER_NAME</code>)更新値
     */
    public void updateCenterName(String value)
    {
        setAdhocUpdateValue(PCTSystem.CENTER_NAME, value) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value SeqNo.桁数(<code>SEQ_DIGIT</code>)<br>
     * 数値の検索値をSeqNo.桁数(<code>SEQ_DIGIT</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSeqDigit(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSeqDigit(int value, String compcode)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSeqDigit(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)の更新値をセットします。
     * @param value SeqNo.桁数(<code>SEQ_DIGIT</code>)更新値
     */
    public void updateSeqDigit(int value)
    {
        setAdhocUpdateValue(PCTSystem.SEQ_DIGIT, HandlerUtil.toObject(value)) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)<br>
     * 文字列の検索値をチェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCheckDigitFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, values, and_or_toNext) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCheckDigitFlag(String value, String compcode)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCheckDigitFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.CHECK_DIGIT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)の更新値をセットします。
     * @param value チェックディジット有無フラグ(<code>CHECK_DIGIT_FLAG</code>)更新値
     */
    public void updateCheckDigitFlag(String value)
    {
        setAdhocUpdateValue(PCTSystem.CHECK_DIGIT_FLAG, value) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)<br>
     * 文字列の検索値をPCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPctmasterLoadFlag(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, values, and_or_toNext) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPctmasterLoadFlag(String value, String compcode)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPctmasterLoadFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.PCTMASTER_LOAD_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)の更新値をセットします。
     * @param value PCT商品マスタ取込フラグ(<code>PCTMASTER_LOAD_FLAG</code>)更新値
     */
    public void updatePctmasterLoadFlag(String value)
    {
        setAdhocUpdateValue(PCTSystem.PCTMASTER_LOAD_FLAG, value) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)<br>
     * 数値の検索値を初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDefultDistinctRate(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDefultDistinctRate(int value, String compcode)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDefultDistinctRate(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)の更新値をセットします。
     * @param value 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)更新値
     */
    public void updateDefultDistinctRate(int value)
    {
        setAdhocUpdateValue(PCTSystem.DEFULT_DISTINCT_RATE, HandlerUtil.toObject(value)) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)<br>
     * 数値の検索値をオーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOrderMaxWeight(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOrderMaxWeight(int value, String compcode)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOrderMaxWeight(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)の更新値をセットします。
     * @param value オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)更新値
     */
    public void updateOrderMaxWeight(int value)
    {
        setAdhocUpdateValue(PCTSystem.ORDER_MAX_WEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value ゾーン優先順位(<code>ZONE_PRIORITY</code>)<br>
     * 数値の検索値をゾーン優先順位(<code>ZONE_PRIORITY</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setZonePriority(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setZonePriority(int value, String compcode)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setZonePriority(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)の更新値をセットします。
     * @param value ゾーン優先順位(<code>ZONE_PRIORITY</code>)更新値
     */
    public void updateZonePriority(int value)
    {
        setAdhocUpdateValue(PCTSystem.ZONE_PRIORITY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位1(<code>LOC_PRIORITY_1</code>)<br>
     * 数値の検索値を棚優先順位1(<code>LOC_PRIORITY_1</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocPriority1(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocPriority1(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocPriority1(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)の更新値をセットします。
     * @param value 棚優先順位1(<code>LOC_PRIORITY_1</code>)更新値
     */
    public void updateLocPriority1(int value)
    {
        setAdhocUpdateValue(PCTSystem.LOC_PRIORITY_1, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位2(<code>LOC_PRIORITY_2</code>)<br>
     * 数値の検索値を棚優先順位2(<code>LOC_PRIORITY_2</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocPriority2(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocPriority2(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocPriority2(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)の更新値をセットします。
     * @param value 棚優先順位2(<code>LOC_PRIORITY_2</code>)更新値
     */
    public void updateLocPriority2(int value)
    {
        setAdhocUpdateValue(PCTSystem.LOC_PRIORITY_2, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位3(<code>LOC_PRIORITY_3</code>)<br>
     * 数値の検索値を棚優先順位3(<code>LOC_PRIORITY_3</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocPriority3(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocPriority3(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocPriority3(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)の更新値をセットします。
     * @param value 棚優先順位3(<code>LOC_PRIORITY_3</code>)更新値
     */
    public void updateLocPriority3(int value)
    {
        setAdhocUpdateValue(PCTSystem.LOC_PRIORITY_3, HandlerUtil.toObject(value)) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 棚優先順位4(<code>LOC_PRIORITY_4</code>)<br>
     * 数値の検索値を棚優先順位4(<code>LOC_PRIORITY_4</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocPriority4(int[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocPriority4(int value, String compcode)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocPriority4(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)の更新値をセットします。
     * @param value 棚優先順位4(<code>LOC_PRIORITY_4</code>)更新値
     */
    public void updateLocPriority4(int value)
    {
        setAdhocUpdateValue(PCTSystem.LOC_PRIORITY_4, HandlerUtil.toObject(value)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(PCTSystem.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(PCTSystem.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(PCTSystem.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(PCTSystem.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(PCTSystem.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(PCTSystem.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(PCTSystem.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * ランクA基準値(<code>A_RANK_STANDARD_VALUE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateARankStandardValueWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.A_RANK_STANDARD_VALUE, source, addvalue);
    }

    /**
     * ランクB基準値(<code>B_RANK_STANDARD_VALUE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateBRankStandardValueWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.B_RANK_STANDARD_VALUE, source, addvalue);
    }

    /**
     * SeqNo.桁数(<code>SEQ_DIGIT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSeqDigitWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.SEQ_DIGIT, source, addvalue);
    }

    /**
     * 初期重量誤差率(<code>DEFULT_DISTINCT_RATE</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateDefultDistinctRateWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.DEFULT_DISTINCT_RATE, source, addvalue);
    }

    /**
     * オーダー最大重量(<code>ORDER_MAX_WEIGHT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateOrderMaxWeightWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.ORDER_MAX_WEIGHT, source, addvalue);
    }

    /**
     * ゾーン優先順位(<code>ZONE_PRIORITY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateZonePriorityWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.ZONE_PRIORITY, source, addvalue);
    }

    /**
     * 棚優先順位1(<code>LOC_PRIORITY_1</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLocPriority1WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.LOC_PRIORITY_1, source, addvalue);
    }

    /**
     * 棚優先順位2(<code>LOC_PRIORITY_2</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLocPriority2WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.LOC_PRIORITY_2, source, addvalue);
    }

    /**
     * 棚優先順位3(<code>LOC_PRIORITY_3</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLocPriority3WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.LOC_PRIORITY_3, source, addvalue);
    }

    /**
     * 棚優先順位4(<code>LOC_PRIORITY_4</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateLocPriority4WithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(PCTSystem.LOC_PRIORITY_4, source, addvalue);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ストアメタデータを返します。
     * @return ストアメタデータ
     */
    @Override
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTSystemAlterKey.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}
