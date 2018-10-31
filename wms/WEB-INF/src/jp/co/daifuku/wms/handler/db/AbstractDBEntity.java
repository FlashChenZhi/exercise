//$Id: AbstractDBEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.AbstractEntity;

/**
 * データベース用の仮想エンティティクラスです。
 * データベース用の各エンティティは、このクラスを継承して作成されます。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractDBEntity
        extends AbstractEntity
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ROWIDを保存する仮想列です */
    //    public static final FieldName ROWID = new FieldName("", "ROWID", "");
    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを初期化します。
     */
    public AbstractDBEntity()
    {
        super();
        // getFieldList().add(COUNT_RESULT) ;
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractDBEntity.java 87 2008-10-04 03:07:38Z admin $";
    }
}
