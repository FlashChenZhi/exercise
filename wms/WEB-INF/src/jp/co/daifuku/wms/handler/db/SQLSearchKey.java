//$Id: SQLSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * データベース用の検索キークラス・インターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface SQLSearchKey
        extends SearchKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //public static final int FIELD_VALUE = 1 ;

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

    /**
     * 取得の際のSQL関数付きで、取得するフィールドを設定します。<br>
     * 未設定で、単一の表検索の時はすべてのフィールドが
     * 取得対象です。<br>
     * 複数の表を対象とした検索の場合は、少なくとも1つのフィールドが指定
     * されている必要があります。
     * @param field 取得対象フィールド
     * @param sqlFunc SQL関数の指定 (ex: UNIQUE, COUNT)<br>
     * またはフォーマット形式指定 ({0}にはカラム名がセットされます)<br>
     * ex: UNIQUE({0}), COUNT({0}), COUNT(DISTINCT{0}))<br>
     * 不要の場合は "" または <code>null</code> を指定します。
     * @param saveField 保存先のフィールド (COUNTなどで別のフィールドに結果を
     * 保存したいときに使用します。<br>
     * 未使用の時は "" または <code>null</code> を指定します)
     */
    public void setCollect(FieldName field, String sqlFunc, FieldName saveField);

    /**
     * 結合条件を指定します。
     * @param field1 結合フィールド1
     * @param field1Postfix 結合条件文字 "(+)" など
     * @param field2 結合フィールド2
     * @param field2Postfix 結合条件文字 "(+)" など
     */
    public void setJoin(FieldName field1, String field1Postfix, FieldName field2, String field2Postfix);

    /**
     * 結合条件を指定します。<br>
     * 結合は等価結合となります。(setJoin(field1, null, field2, null)と同様です。)
     * @param field1 結合フィールド1
     * @param field2 結合フィールド2
     */
    public void setJoin(FieldName field1, FieldName field2);

    /**
     * 結合条件をクリアします。
     */
    public void clearJoin();

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return 取得条件リストを返します。<br>
     * 未設定の時は <code>null</code> が返ります。
     */
    public List getCollectConditionList();

    /**
     * @return グループ化条件リストを返します。<br>
     * 未設定の時は <code>null</code> が返ります。
     */
    public List getGroupConditionList();

    /**
     * @return ソート条件リストを返します。<br>
     * 未設定の時は <code>null</code> が返ります。
     */
    public List getOrderConditionList();

    /**
     * @return 結合条件リストを返します。<br>
     * 未設定の時は <code>null</code> が返ります。
     */
    public List getJoinConditionList();

    /**
     * @param cList 取得条件リストを設定します。
     */
    public void setCollectConditionList(List cList);

    /**
     * @param gList グループ化条件リストを設定します。
     */
    public void setGroupConditionList(List gList);

    /**
     * @param sList ソート条件リストを設定します。
     */
    public void setOrderConditionList(List sList);

    /**
     * @param jList 結合条件リストを設定します。
     */
    public void setJoinConditionList(List jList);
}
