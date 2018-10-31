// $Id: CustomerHistoryImpHandler.java 1841 2008-12-09 10:12:59Z okayama $
// $LastChangedRevision: 1841 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.CustomerHistoryImp;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * CUSTOMERHISTORYIMP用のデータベースハンドラです。
 * 
 * @version $Revision: 1841 $, $Date: 2008-12-09 19:12:59 +0900 (火, 09 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class CustomerHistoryImpHandler
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
    public CustomerHistoryImpHandler(Connection conn)
    {
        // super(conn, CustomerHistoryImp.STORE_NAME) ;
        super(conn, CustomerHistoryImp.$storeMetaData) ;
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
        return (new CustomerHistoryImp()) ;
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
        return "$Id: CustomerHistoryImpHandler.java 1841 2008-12-09 10:12:59Z okayama $" ;
    }
}
