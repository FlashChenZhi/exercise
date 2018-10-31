//$Id: SearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 管情報を取得する場合に指定するキー情報の為のインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface SearchKey
        extends FindKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     * 取得するフィールドの指定を行います。<br>
     * 未設定で、単一の表検索の時はすべてのフィールドが取得対象となります。<br>
     * 複数の表を対象とした検索の場合は、少なくとも1つのフィールドが指定され
     * ている必要があります。
     * @param field 取得するフィールド
     */
    public void setCollect(FieldName field);

    /**
     * 取得フィールド指定をクリアします。
     */
    public void clearCollect();

    /**
     * 指定されたフィールドをグループに指定します。<br>
     * 順番はこのメソッドを呼び出した順になります。
     * @param field グループ化するフィールド
     */
    public void setGroup(FieldName field);

    /**
     * グループ化指定をクリアします。
     */
    public void clearGroup();

    /**
     * 指定されたカラムをソート順に指定します。<br>
     * 順番はこのメソッドを呼び出した順になります。
     * @param field 対象フィールド
     * @param ascOrder 昇順の時 true.
     */
    public void setOrder(FieldName field, boolean ascOrder);

    /**
     * ソート順指定をクリアします。
     */
    public void clearOrder();
}
