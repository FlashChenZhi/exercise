// $Id: RftSettingHandler.java 2514 2009-01-06 02:20:40Z kishimoto $
// Handler v3.8.1
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.RftSetting;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * RFTSETTING用のデータベースハンドラです。
 * 
 * @version $Revision: 2514 $, $Date: 2009-01-06 11:20:40 +0900 (火, 06 1 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class RftSettingHandler
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
    public RftSettingHandler(Connection conn)
    {
        // super(conn, RftSetting.STORE_NAME) ;
        super(conn, RftSetting.$storeMetaData) ;
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
        return (new RftSetting()) ;
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
        return "$Id: RftSettingHandler.java 2514 2009-01-06 02:20:40Z kishimoto $" ;
    }
}
