// $Id: Part11LogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>Part11ログハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements the Part11LogHandler interface. <br></en>
 * 
 * @author $Author: T.Ogawa
 */
public class Part11LogHandlerImpl
        extends AbstractHandler
        implements Part11LogHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 
     * @param conn DBコネクション
     */
    public Part11LogHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.Part11LogHandler#findMinLogDate(java.lang.String)
     */
    public Date findMinLogDate(String table)
            throws SQLException
    {
        Date result = null;

        try
        {
            String sql = "SELECT MIN(LOG_DATE) FROM " + table;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            statement = connection.createStatement();
            resultset = statement.executeQuery(sql);

            resultset.next();

            result = resultset.getTimestamp(1);
        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {
            super.closeStatment_Resultset();
        }

        return result;
    }


    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.Part11LogHandler#deleteByBaseDate(java.lang.String, java.util.Date)
     */
    public int deleteOutofRangeLog(String table, Date baseDate)
            throws SQLException
    {
        int result = 0;

        try
        {
            String sql = "DELETE FROM " + table + " WHERE LOG_DATE < ?";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, getSqlDate(baseDate));

            result = preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {
            super.closeStatment_Resultset();
        }

        return result;
    }


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
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
        return "$Id: Part11LogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $";
    }

}
