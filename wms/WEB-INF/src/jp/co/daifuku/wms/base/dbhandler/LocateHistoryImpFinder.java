// $Id: LocateHistoryImpFinder.java 5551 2009-11-09 09:46:21Z fukuwa $
// $LastChangedRevision: 5551 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.LocateHistoryImp;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;


/**
 * データベースからLocateHistoryImp表を検索してResultSetからEntity配列を
 * 取得するためのクラスです。<br>
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 *
 * @version $Revision: 5551 $, $Date: 2009-11-09 18:46:21 +0900 (月, 09 11 2009) $
 * @author  shimizu
 * @author  Last commit: $Author: fukuwa $
 */
public class LocateHistoryImpFinder
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
    public LocateHistoryImpFinder(Connection conn)
    {
        super(conn, LocateHistoryImp.$storeMetaData) ;
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
        return (new LocateHistoryImp()) ;
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
        return ("$Revision: 5551 $,$Date: 2009-11-09 18:46:21 +0900 (月, 09 11 2009) $") ;
    }
}
