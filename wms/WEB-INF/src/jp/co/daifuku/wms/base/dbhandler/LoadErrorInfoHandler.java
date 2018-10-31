// $Id: LoadErrorInfoHandler.java 5356 2009-11-02 00:47:10Z okamura $
// $LastChangedRevision: 5356 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * LOADERRORINFO用のデータベースハンドラです。
 * 
 * @version $Revision: 5356 $, $Date: 2009-11-02 09:47:10 +0900 (月, 02 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class LoadErrorInfoHandler
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
    public LoadErrorInfoHandler(Connection conn)
    {
        // super(conn, LoadErrorInfo.STORE_NAME) ;
        super(conn, LoadErrorInfo.$storeMetaData) ;
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
        return (new LoadErrorInfo()) ;
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
        return "$Id: LoadErrorInfoHandler.java 5356 2009-11-02 00:47:10Z okamura $" ;
    }
}
