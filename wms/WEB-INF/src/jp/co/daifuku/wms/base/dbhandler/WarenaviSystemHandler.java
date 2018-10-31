// $Id: WarenaviSystemHandler.java 4852 2009-08-20 11:17:42Z ota $
// $LastChangedRevision: 4852 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * WARENAVISYSTEM用のデータベースハンドラです。
 * 
 * @version $Revision: 4852 $, $Date: 2009-08-20 20:17:42 +0900 (木, 20 8 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class WarenaviSystemHandler
        extends AbstractDBHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //  public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn 接続済みのデータベースコネクション
     */
    public WarenaviSystemHandler(Connection conn)
    {
        // super(conn, WarenaviSystem.STORE_NAME) ;
        super(conn, WarenaviSystem.$storeMetaData) ;
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
    /**
     * @see AbstractDBHandler#createEntity()
     */
    @Override
    protected AbstractEntity createEntity()
    {
        return (new WarenaviSystem()) ;
    }


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
        return "$Id: WarenaviSystemHandler.java 4852 2009-08-20 11:17:42Z ota $" ;
    }
}
