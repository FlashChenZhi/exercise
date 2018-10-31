// $Id: TerminalUserMapHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>端末ユーザマップハンドラクラスのinterfaceを実装したクラスです。 <br></jp>
 * <en>This class implements TerminalUserMapHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class TerminalUserMapHandlerImpl
        extends AbstractHandler
        implements TerminalUserMapHandler, Serializable
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    TerminalUserMapHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>端末ユーザマップクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given TerminalUserMap infomration.</en>
     * 
     * @param terminalInfo :
     *            <en> TerminalUserMap information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal info
     *             creation.</en>
     */
    public int createTerminalUserMap(Terminal terminalInfo)
            throws SQLException
    {

        int result = 0;

        try
        {
            boolean hasUserIdArray = terminalInfo.isUserIdArray();
            if (!hasUserIdArray)
            {
                throw new SQLException("No Users to create");
            }

            String[] userIdArray = terminalInfo.getUserIdArray();
            if (userIdArray == null)
            {
                throw new SQLException("No Users to create");
            }

            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_TERMINALUSERMAP (TERMINALNUMBER,USERID,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < userIdArray.length; i++)
            {
                // Set data
                preparedStatement.setString(1, terminalInfo.getTerminalNumber());
                preparedStatement.setString(2, userIdArray[i]);
                preparedStatement.setString(3, terminalInfo.getUpdateUser());
                preparedStatement.setString(4, terminalInfo.getUpdateTerminal());
                preparedStatement.setInt(5, terminalInfo.getUpdateKind());
                preparedStatement.setString(6, terminalInfo.getUpdateProcess());
                // Create role information
                int temp = preparedStatement.executeUpdate();
                // if the record is not created throw the exception
                if (temp == 0)
                {
                    throw new SQLException();
                }
                result = result + temp;
                preparedStatement.clearParameters();
            }

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

    /**
     * <jp>端末番号でデータベースを検索し、端末情報の配列を返却します。 <br></jp>
     * <en>This method fetches TerminalUserMap information from database for a
     * given terminal number</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return Terminal : <en>Returns terminal Information. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findByTerminalNumber(String terminalNumber)
            throws SQLException
    {
        Terminal[] terminalInfo = null;
        try
        {
            // get number of records available
            int count = this.findCountByTerminalNumber(terminalNumber);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql = "SELECT * FROM COM_TERMINALUSERMAP WHERE TERMINALNUMBER = '" + terminalNumber + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            terminalInfo = new Terminal[count];

            int temp = 0;
            while (resultset.next())
            {
                terminalInfo[temp] = new Terminal();
                // set data to entity
                terminalInfo[temp].setTerminalNumber(resultset.getString(EmTableColumns.TERMINALUSERMAP_TERMINALNUMBER));
                terminalInfo[temp].setUserId(resultset.getString(EmTableColumns.TERMINALUSERMAP_USERID));
                terminalInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINALUSERMAP_UPDATE_DATE));
                terminalInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.TERMINALUSERMAP_UPDATE_USER));
                terminalInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.TERMINALUSERMAP_UPDATE_TERMINAL));
                terminalInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.TERMINALUSERMAP_UPDATE_KIND));
                terminalInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.TERMINALUSERMAP_UPDATE_PROCESS));
                temp++;
            }

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {

            this.closeStatment_Resultset();
        }
        return terminalInfo;
    }


    /**
     * <jp>端末番号でデータベースを検索し、登録数を返却します。 <br></jp>
     * <en>Finds the number of records for a given terminal Number.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any database access error .</en>
     */
    public int findCountByTerminalNumber(String terminalNumber)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_TERMINALUSERMAP WHERE TERMINALNUMBER='" + terminalNumber + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount);
            resultset.next();
            count = resultset.getInt("COUNT");

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {

            this.closeStatment_Resultset();
        }
        // renturn the result
        return count;
    }

    /**
     * <jp>ユーザIDと端末番号でデータベースを検索し、登録数を返却します。 <br></jp>
     * <en>Finds the number of records for a given UserId and terminal Number.</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any database access error .</en>
     */
    public int findCountByUserIdTerminalNumber(String userId, String terminalNumber)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_TERMINALUSERMAP WHERE USERID='" + userId + "'"
                            + " AND TERMINALNUMBER='" + terminalNumber + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount);
            resultset.next();
            count = resultset.getInt("COUNT");

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {

            this.closeStatment_Resultset();
        }
        // renturn the result
        return count;
    }

    /**
     * <jp>端末番号でデータベースから該当する端末ユーザマップ情報を削除します。 <br></jp>
     * <en>This method deletes TerminalUserMap infomration for given
     * terminalNuber.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteTerminalUserMap(String terminalNumber)
            throws SQLException
    {
        int result;

        try
        {
            String sql = "DELETE FROM COM_TERMINALUSERMAP WHERE TERMINALNUMBER='" + terminalNumber + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql);

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw new SQLException("");

        }
        finally
        {

            super.closeStatment_Resultset();
        }
        return result;
    }

    /**
     * <jp>端末番号でデータベースから該当する端末ユーザマップ情報を削除します。 <br></jp>
     * <en>This method deletes TerminalUserMap infomration for given
     * userId.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteByUserId(String userId)
            throws SQLException
    {
        int result;

        try
        {
            String sql = "DELETE FROM COM_TERMINALUSERMAP WHERE USERID='" + userId + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql);

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw new SQLException("");

        }
        finally
        {

            super.closeStatment_Resultset();
        }
        return result;
    }
}
