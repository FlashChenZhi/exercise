// $Id: WorkListFinder.java 5151 2009-10-19 04:50:37Z okamura $
// $LastChangedRevision: 5151 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;


/**
 * データベースからWorkList表を検索してResultSetからEntity配列を
 * 取得するためのクラスです。<br>
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 *
 * @version $Revision: 5151 $, $Date: 2009-10-19 13:50:37 +0900 (月, 19 10 2009) $
 * @author  shimizu
 * @author  Last commit: $Author: okamura $
 */
public class WorkListFinder
        extends AbstractDBFinder
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn 接続済みのデータベースコネクション
     */
    public WorkListFinder(Connection conn)
    {
        super(conn, WorkList.$storeMetaData) ;
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
     * @see AbstractDBFinder#createEntity()
     */
    @Override
    protected AbstractEntity createEntity()
    {
        return (new WorkList()) ;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------

    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 5151 $,$Date: 2009-10-19 13:50:37 +0900 (月, 19 10 2009) $") ;
    }
}
