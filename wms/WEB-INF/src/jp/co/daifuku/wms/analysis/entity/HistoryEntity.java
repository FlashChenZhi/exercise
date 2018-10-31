// $Id: HistoryEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.entity;

import jp.co.daifuku.wms.handler.field.FieldName;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 履歴情報エンティティを規定するインターフェイスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/22</td><td nowrap>清水 正人</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */


public interface HistoryEntity
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
    /**
     * キー項目となるフィールド名リストを取得します。<BR>
     * @return キーフィールド名リスト<BR>
     */
    public FieldName[] getKeyNames();

    /**
     * 更新キー項目のフィールド名リストを取得します。<BR>
     * @return 更新キーフィールド名リスト<BR>
     */
    public FieldName[] getUpdateKeyNames();

    /**
     * 更新対象項目のフィールド名リストを取得します。<BR>
     * @return 更新対象フィールド名リスト<BR>
     */
    public FieldName[] getUpdateFieldNames();

    /**
     * <code>getUpdateFieldNames()</code>で取得した各フィールドについて
     * 加算更新を行なうかどうかを返します。<BR>
     * <code>true</code>が設定されていれば加算更新を行なうものとし、
     * <code>false</code>ならば、上書き更新となります。<BR>
     * @return 加算更新区分<BR>
     */
    public boolean[] getAddCondition();

    /**
     * カラム名で指定された項目を設定します。<BR>
     * @param column カラム名<BR>
     * @param value 設定値<BR>
     */
    public void setValue(String column, Object value);

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 履歴テーブルを更新する際に取り出される検索条件の有効/無効を設定します。<BR>
     * @param enable <code>true</code>を指定したキーを有効とします。<BR>
     */
    public void setUpdateKeyCondition(boolean[] enable);

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * パラメータで指定されたカラム名の値を取得します。<BR>
     * もし項目の値がnullの時は、""(空のString) が返ります。
     * @param columnName カラム名<BR>
     * @return 項目の値<BR>
     */
    public Object getValue(FieldName columnName);

    /**
     * フィールド名リストを取得します。<BR>
     * @return フィールド名リスト<BR>
     */
    public FieldName[] getFieldNames();
}
