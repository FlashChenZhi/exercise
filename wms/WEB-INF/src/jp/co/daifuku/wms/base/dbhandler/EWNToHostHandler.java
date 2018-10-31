// $Id: EWNToHostHandler.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * EWNTOHOST用のデータベースハンドラです。
 * 
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class EWNToHostHandler
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
    public EWNToHostHandler(Connection conn)
    {
        // super(conn, EWNToHost.STORE_NAME) ;
        super(conn, EWNToHost.$storeMetaData) ;
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
        return (new EWNToHost()) ;
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
        return "$Id: EWNToHostHandler.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
