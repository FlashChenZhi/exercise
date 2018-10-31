// $Id: PassWordHistoryHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>パスワードクラスのinterfaceを実装します。 <br></jp>
 * <en>This class implements the PassWordHistoryHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class PassWordHistoryHandlerImpl
        extends AbstractHandler
        implements PassWordHistoryHandler, Serializable
{
    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    PassWordHistoryHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>パスワードクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given password change log infomration in data
     * base.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createPasswordHistory(LogInfo logInfo)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_PASSWORDHISTORY (USERID,OLDPASSWORD,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, logInfo.getUserId());
            preparedStatement.setString(2, logInfo.getOldPassword());
            preparedStatement.setString(3, logInfo.getUpdateUser());
            preparedStatement.setString(4, logInfo.getUpdateTerminal());
            preparedStatement.setInt(5, logInfo.getUpdateKind());
            preparedStatement.setString(6, logInfo.getUpdateProcess());

            // Create role information
            result = preparedStatement.executeUpdate();

            // if the record is not created throw the exception
            if (result == 0)
            {
                throw new SQLException();
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
     * <jp>ユーザIDでデータベースを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method fetches LogInfo information from database for a given
     * userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return LogInfo[] : <en>Returns LogInfo Information as LogInfo Entity for
     *         given userID.<Returns null If no data found/en>
     * @throws SQLException :
     *             <en>if a database access error occurs. </en>
     */
    public LogInfo[] findByUserId(String userId)
            throws SQLException
    {

        LogInfo[] logInfoEntity = null;
        try
        {

            String sql = "SELECT * FROM COM_PASSWORDHISTORY WHERE USERID='" + userId + "' ORDER BY UPDATE_DATE DESC ";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logResult = new LogInfo();
                // set LogInfo data to LogInfo entity
                logResult.setUserId(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UESRID));
                logResult.setOldPassword(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_OLDPASSWORD));

                logResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_DATE));
                logResult.setUpdateUser(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_USER));
                logResult.setUpdateTerminal(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_TERMINAL));
                logResult.setUpdateKind(resultset.getInt(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_KIND));
                logResult.setUpdateProcess(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_PROCESS));
                list.add(logResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfoEntity = new LogInfo[size];
                logInfoEntity = (LogInfo[])list.toArray(logInfoEntity);
            }

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
        return logInfoEntity;

    }

    /**
     * <jp>ユーザIDでデータベースを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method Methid returns the last updated data for a given user iDuserId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return LogInfo: <en>Last updated data/en>
     * @throws SQLException :
     *             <en>if a database access error occurs. </en>
     */
    public LogInfo findLastPwdChangeDateByUserId(String userId)
            throws SQLException
    {

        LogInfo logResult = null;
        try
        {

            String sql = "SELECT * FROM COM_PASSWORDHISTORY WHERE USERID='" + userId + "' ORDER BY UPDATE_DATE DESC ";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            if (resultset.next())
            {
                logResult = new LogInfo();
                // set LogInfo data to LogInfo entity
                logResult.setUserId(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UESRID));
                logResult.setOldPassword(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_OLDPASSWORD));
                logResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_DATE));
                logResult.setUpdateUser(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_USER));
                logResult.setUpdateTerminal(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_TERMINAL));
                logResult.setUpdateKind(resultset.getInt(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_KIND));
                logResult.setUpdateProcess(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_PROCESS));
            }

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
        return logResult;

    }

    /**
     * <jp>全てのパスワード変更履歴とそれに関連するデータを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method returns all password change and related data.</en>
     * 
     * @return LogInfo[]: <en>Returns password change data as LogInfo entity
     *         array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAll()
            throws SQLException
    {
        LogInfo[] logInfoEntity = null;
        try
        {

            String sql = "SELECT * FROM COM_PASSWORDHISTORY ORDER BY USERID";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logResult = new LogInfo();
                // set LogInfo data to LogInfo entity
                logResult.setUserId(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UESRID));
                logResult.setOldPassword(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_OLDPASSWORD));

                logResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_DATE));
                logResult.setUpdateUser(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_USER));
                logResult.setUpdateTerminal(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_TERMINAL));
                logResult.setUpdateKind(resultset.getInt(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_KIND));
                logResult.setUpdateProcess(resultset.getString(EmTableColumns.COM_PASSWORDHISTORY_UPDATE_PROCESS));

                list.add(logResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfoEntity = new LogInfo[size];
                logInfoEntity = (LogInfo[])list.toArray(logInfoEntity);
            }
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
        return logInfoEntity;
    }

    /**
     * <jp>ユーザIDでデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of records for a given UserId.</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any database access error .</en>
     */
    public int findCountByUserId(String userId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_PASSWORDHISTORY WHERE USERID='" + userId + "'";
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
     * <jp>ユーザIDで、データベースからパスワードに関するデータを削除します。 <br></jp>
     * <en>This method deletes Password Change information for a given userId
     * based on COM_AUTHENTICATION PASSWORDLOGCHECKTIME.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByUserIdAuth(String userId)
            throws SQLException
    {
        int result;
        try
        {

            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM COM_PASSWORDHISTORY ");
            sql.append("WHERE " + EmTableColumns.COM_PASSWORDHISTORY_UESRID + " = '" + userId + "'");
            sql.append("AND " + EmTableColumns.COM_PASSWORDHISTORY_OLDPASSWORD + " IN  (");
            sql.append("  SELECT " + EmTableColumns.COM_PASSWORDHISTORY_OLDPASSWORD + " FROM( ");
            sql.append("    SELECT COM_PASSWORDHISTORY. *, ROW_NUMBER() OVER( ORDER BY UPDATE_DATE DESC ) RN FROM COM_PASSWORDHISTORY ");
            sql.append("    WHERE USERID = '" + userId + "'");
            sql.append("    ORDER BY UPDATE_DATE DESC ");
            sql.append("  ) ");
            sql.append("  WHERE RN > ( ");
            sql.append("    SELECT COM_AUTHENTICATION.PASSWORDLOGCHECKTIME FROM COM_AUTHENTICATION ");
            sql.append("    WHERE AUTHENTICATIONID = '1' ");
            sql.append("  ) ");
            sql.append(") ");

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql.toString());

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
     * <jp>ユーザIDでデータベースからパスワード変更履歴を削除します。 <br></jp>
     * <en>This method deletes Password Change information for a given userId</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByUserId(String userId)
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_PASSWORDHISTORY WHERE USERID='" + userId + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            System.out.println(sql);
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql.toString());

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
