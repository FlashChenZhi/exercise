// $Id: EmConnectionHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.EmProperties;

/**
 * <jp>データベースに接続するためのハンドラです。<br>
 * </jp> <en> This class is a Database Connection handler.Can be used to handle
 * database connection related activities like close, open etc </en>
 * 
 * @author $Author: K.Fukumori
 */
public class EmConnectionHandler
{
    /** <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en> */
    private static String dataSourceName = null;

    /**
     * <jp>有効期間が Request のコネクションを取得。</jp><en>Gets the database Connection. For
     * given request.</en>
     * 
     * @param page
     *            <jp>ビジネスロジックのページオブジェクト</jp><en>BusinessLogic page object</en>
     * @return Connection <jp>コネクション</jp><en>Database Connection to default
     *         database</en>
     * @throws SQLException
     *             <jp>データベースへのアクセスエラーが発生したとき</jp><en>if a database access
     *             error occurs during modification.</en>
     */
    public static Connection getPageDbConnection(Page page)
            throws SQLException
    {
        Connection conn = null;
        if (dataSourceName == null)
        {
            dataSourceName = EmProperties.getProperty(EmConstants.EMPARAMKEY_DATASOURCENAME);
        }
        try
        {
            conn = ConnectionManager.getRequestConnection(dataSourceName, page);
        }
        catch (SQLException ex)
        {
            throw new SQLException(ex.getMessage());
        }
        return conn;
    }

    /**
     * <jp>有効期間が Session のコネクションを取得。</jp><en>Gets the database Connection. For
     * given session.</en>
     * 
     * @param page
     *            <jp>ビジネスロジックのページオブジェクト</jp><en>BusinessLogic page object</en>
     * @return Connection <jp>コネクション</jp><en>Database Connection to default
     *         database</en>
     * @throws SQLException
     *             <jp>データベースへのアクセスエラーが発生したとき</jp><en>if a database access
     *             error occurs during modification.</en>
     */
    public static Connection getSessionDbConnection(Page page)
            throws SQLException
    {
        Connection conn = null;
        if (dataSourceName == null)
        {
            dataSourceName = EmProperties.getProperty(EmConstants.EMPARAMKEY_DATASOURCENAME);
        }
        try
        {
            conn = ConnectionManager.getSessionConnection(dataSourceName, page);
        }
        catch (SQLException ex)
        {
            throw new SQLException(ex.getMessage());
        }
        return conn;
    }

    /**
     * <jp>データベース接続を確立します。 <br>
     * </jp> <en>Gets the database Connection</en>
     * 
     * @return Connection : <en>Database Connection to default database</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public static Connection getConnection()
            throws SQLException
    {
        Connection conn = null;
        if (dataSourceName == null)
        {
            dataSourceName = EmProperties.getProperty(EmConstants.EMPARAMKEY_DATASOURCENAME);
        }
        try
        {
            conn = ConnectionManager.getConnection(dataSourceName);
        }
        catch (SQLException ex)
        {
            //ex.printStackTrace ();
            throw new SQLException(ex.getMessage());
        }
        return conn;
    }

    /**
     * <jp>データベース接続を終了します。 <br>
     * </jp> <en>Close database Connection</en>
     * 
     * @param conn
     *            <en> closes database connection</en>
     */
    public static void closeConnection(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
        catch (SQLException ex)
        {
        }
    }

    /**
     * <jp>データベース接続状態を保ちます。 <br>
     * </jp> <en>This helps to roll back the database operations for given
     * connection</en>
     * 
     * @param conn
     *            <en>database connection</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during rollback.</en>
     */
    public static void rollback(Connection conn)
            throws SQLException
    {
        try
        {
            if (conn != null)
            {
                conn.rollback();
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * <jp>確立した接続のためにデータベース操作を遂行するのを助けます。 <br>
     * </jp> <en>This helps to commit the database operations for given
     * connection</en>
     * 
     * @param conn
     *            <en>database connection</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during commit.</en>
     */
    public static void commit(Connection conn)
            throws SQLException
    {
        try
        {
            if (conn != null)
            {
                conn.commit();
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException(ex.getMessage());
        }
    }

}
