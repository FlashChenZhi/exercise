//$Id: FindKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 検索条件を保持するスーパーインターフェースです。<br>
 * データベースやファイルに使用されます。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface FindKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 指定されたフィールドに検索値をセットします。<br>
     * このメソッドは、AND条件, 完全一致(LIKE, IS NULLを含む)の
     * 検索条件となります。<br>
     * 以下のようなコードと同様です。<br>
     * 
     * <code>
     * setKey(field, "=", value, "", "", true) ;
     * </code>
     * 
     * @param field 対象フィールド
     * @param value 検索値:
     * String, BigDecimal, java.util.Date のいずれかを使用してください。
     */
    public void setKey(FieldName field, Object value);

    /**
     * 指定されたフィールドに検索値と条件をセットします。
     * @param field 対象フィールド
     * @param value 検索値
     * @param compcode 比較条件 (=，＜，＞，!=，＜=，＞=)
     * @param left_Paren 左括弧
     * @param right_Paren 右括弧
     * @param and_or_toNext "AND"または"OR"条件 (true = AND)
     */
    public void setKey(FieldName field, Object value, String compcode, String left_Paren, String right_Paren,
            boolean and_or_toNext);

    /**
     * 複数の検索値をセットします。
     * @param field 対象フィールド
     * @param values 検索値(ORで結ばれます)
     * @param and_or_toNext 次の条件との論理和指定 (ANDの時 true)
     */
    public void setKey(FieldName field, Object[] values, boolean and_or_toNext);

    /**
     * 検索キーから検索条件を取得して、セットします。<br>
     * すでに検索条件がセットされている場合、追加の条件として
     * セットされます。
     * 
     * @param key 検索条件
     */
    public void setKey(FindKey key);

    /**
     * エンティティから検索条件を取得して、セットします。<br>
     * エンティティにセットされているフィールド値をすべて
     * AND条件, 完全一致で検索する場合に使用します。
     * 
     * @param ent 検索対象データ
     */
    public void setKey(Entity ent);

    /**
     * 範囲指定キーをセットします。<br>
     * rangeLow と rangeHigh が同じ値を持つ場合は、フィールドに対して単一の絞込条件
     * として判断されます。<br>
     * rangeLow と rangeHigh の大小比較はこのメソッドの中で行われますので、
     * セットするアプリケーションは判定する必要はありません。<br>
     * 
     * @param fld キー項目のフィールド
     * @param rangeLow 範囲下限 (この値を含みます)<br>
     * <code>null</code> (Stringの場合 "" も同様) が指定されたとき、下限なしとみなします。
     * @param rangeHigh 範囲上限 (この値を含みます)<br>
     * <code>null</code> (Stringの場合 "" も同様) が指定されたとき、上限なしとみなします。
     * @param and_or_toNext 次の条件との論理和指定 (ANDの時 <code>true</code>)
     */
    public void setRangeKey(FieldName fld, Object rangeLow, Object rangeHigh, boolean and_or_toNext);

    /**
     * 取得条件リストを取得します。
     * 
     * @return 取得条件リスト
     */
    public List getLimitConditionList();

    /**
     * すべての条件をクリアします。
     */
    public void clear();

    /**
     * 検索値,条件をクリアします。
     */
    public void clearKeys();

    /**
     * 対象のストア名を返します。
     * 
     * @return ストア名(ファイル名・テーブル名)
     */
    public String getStoreName();

    /**
     * 対象のストア名をセットします。
     * 
     * @param storeName ストア名(ファイル名・テーブル名)
     */
    public void setStoreName(String storeName);
}
