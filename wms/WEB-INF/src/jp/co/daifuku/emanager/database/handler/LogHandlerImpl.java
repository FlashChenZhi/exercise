// $Id: LogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>ログハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements the LogHandlerImpl interface. <br></en>
 * @author $Author: Muneendra
 */
public class LogHandlerImpl
        extends AbstractHandler
        implements LogHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    LogHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>ユーザメンテナンスログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given user miantenance log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createMainteLog(LogInfo logInfo)
            throws SQLException
    {

        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_MAINTENANCELOG (LOGDATE,USERID,IPADDRESS,TERMINALNUMBER,LOGCLASS,MESSAGE,DETAIL,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(SYSDATE,?,?,?,?,?,?,?,?,?,?,?)";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, logInfo.getUserId());
            preparedStatement.setString(2, logInfo.getIpAddress());
            preparedStatement.setString(3, logInfo.getTerminalNumber());
            preparedStatement.setInt(4, logInfo.getLogClass());
            preparedStatement.setString(5, logInfo.getMessage());
            preparedStatement.setString(6, logInfo.getDetails());

            if (logInfo.getUpdateDate() != null)
            {
                preparedStatement.setTimestamp(7, getSqlDate(logInfo.getUpdateDate()));
            }
            else
            {
                preparedStatement.setTimestamp(7, getSqlDate(new Date()));
                //preparedStatement.setString (7, "SYSDATE");
            }

            preparedStatement.setString(8, logInfo.getUpdateUser());
            preparedStatement.setString(9, logInfo.getUpdateTerminal());
            preparedStatement.setInt(10, logInfo.getUpdateKind());
            preparedStatement.setString(11, logInfo.getUpdateProcess());

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
     * <jp>認証システムログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given authenticaltion log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAuthenticationLog(LogInfo logInfo)
            throws SQLException
    {

        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_AUTHENTICATIONLOG (LOGDATE,USERID,IPADDRESS,TERMINALNUMBER,LOGCLASS,MESSAGE,DETAIL,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(SYSDATE,?,?,?,?,?,?,?,?,?,?,?)";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, logInfo.getUserId());
            preparedStatement.setString(2, logInfo.getIpAddress());
            preparedStatement.setString(3, logInfo.getTerminalNumber());
            preparedStatement.setInt(4, logInfo.getLogClass());
            preparedStatement.setString(5, logInfo.getMessage());
            preparedStatement.setString(6, logInfo.getDetails());

            if (logInfo.getUpdateDate() != null)
            {
                preparedStatement.setTimestamp(7, getSqlDate(logInfo.getUpdateDate()));
            }
            else
            {
                preparedStatement.setTimestamp(7, getSqlDate(new Date()));
            }
            preparedStatement.setString(8, logInfo.getUpdateUser());
            preparedStatement.setString(9, logInfo.getUpdateTerminal());
            preparedStatement.setInt(10, logInfo.getUpdateKind());
            preparedStatement.setString(11, logInfo.getUpdateProcess());

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
     * <jp>全てのメンテナンスログを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all maintenace Log information.<br></en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteLog()
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {
            // get number of records available
            int count = this.findCountMainteLog();
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql = "SELECT * FROM COM_MAINTENANCELOG ORDER BY LOGDATE DESC";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            logInfo = new LogInfo[count];

            int temp = 0;
            while (resultset.next())
            {
                logInfo[temp] = new LogInfo();
                logInfo[temp].setLogDate(resultset.getTimestamp(EmTableColumns.COM_MAINTENANCELOG_LOGDATE));
                logInfo[temp].setUserId(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_USERID));
                logInfo[temp].setIpAddress(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_IPADDRESS));
                logInfo[temp].setTerminalNumber(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_TERMINALNUMBER));
                logInfo[temp].setLogClass(resultset.getInt(EmTableColumns.COM_MAINTENANCELOG_LOGCLASS));
                logInfo[temp].setMessage(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_MESSAGE));
                logInfo[temp].setDetails(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_DETAIL));
                // logInfo[temp].setUpdateDate(getUtilDate(resultset.getTimestamp(EmTableColumns.COM_MAINTENANCELOG_UPDATE_DATE)));
                logInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_MAINTENANCELOG_UPDATE_DATE));
                logInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_UPDATE_USER));
                logInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_UPDATE_TERMINAL));
                logInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.COM_MAINTENANCELOG_UPDATE_KIND));
                logInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.COM_MAINTENANCELOG_UPDATE_PROCESS));
                // increment menuId array variable
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
        // renturn the result
        return logInfo;
    }

    /**
     * <jp>全ての認証システムログを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all maintenace Log information.<br></en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthLog()
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {
            // get number of records available
            int count = this.findCountAuthLog();
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql = "SELECT * FROM COM_AUTHENTICATIONLOG ORDER BY LOGDATE DESC";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            logInfo = new LogInfo[count];

            int temp = 0;
            while (resultset.next())
            {
                logInfo[temp] = new LogInfo();
                logInfo[temp].setLogDate(resultset.getTimestamp(EmTableColumns.COM_AUTHENTICATIONLOG_LOGDATE));
                logInfo[temp].setUserId(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_USERID));
                logInfo[temp].setIpAddress(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_IPADDRESS));
                logInfo[temp].setTerminalNumber(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_TERMINALNUMBER));
                logInfo[temp].setLogClass(resultset.getInt(EmTableColumns.COM_AUTHENTICATIONLOG_LOGCLASS));
                logInfo[temp].setMessage(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_MESSAGE));
                logInfo[temp].setDetails(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_DETAIL));
                // logInfo[temp].setUpdateDate(getUtilDate(resultset.getTimestamp(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_DATE)));
                logInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_DATE));
                logInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_USER));
                logInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_TERMINAL));
                logInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_KIND));
                logInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.COM_AUTHENTICATIONLOG_UPDATE_PROCESS));
                // increment menuId array variable
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
        // renturn the result
        return logInfo;
    }


    /**
     * <jp>メンテナンスログの番号を検索し、返却します。 <br></jp>
     * <en>Finds the number of records in User Maintenance log information.<br></en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteLog()
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_MAINTENANCELOG";
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
     * <jp>認証システムログの番号を検索し、返却します。 <br></jp>
     * <en>Finds the number of records in Authentication log information.<br></en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthLog()
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_AUTHENTICATIONLOG";
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
     * <jp>認証システムログをデータベースから削除します。 <br></jp>
     * <en>This method deletes Authentication log records upto given number.<br></en>
     * 
     * @param rowNum :
     *            <en>number of records to delete</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthLogByRownum(String rowNum)
            throws SQLException
    {
        int result;
        try
        {

            String sql =
                    "DELETE FROM (SELECT * FROM  COM_AUTHENTICATIONLOG ORDER BY LOGDATE) WHERE ROWNUM <= " + rowNum;
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql);

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
     * <en>This method deletes Authentication log records between the given dates.<br></en>
     * 
     * @param fromDate
     *            <en>from Date</en>
     * @param toDate
     *            <en>to Date</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthLogByDates(Date fromDate, Date toDate)
            throws SQLException
    {
        int result;
        try
        {

            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM COM_AUTHENTICATIONLOG WHERE LOGDATE BETWEEN ");
            sql.append(getDateFormat(fromDate));
            sql.append(" AND ");
            sql.append(getDateFormat(toDate));

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();

            // delete role from Role table
            result = statement.executeUpdate(sql.toString());

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
     * <jp>メンテナンスログをデータベースから削除します。 <br></jp>
     * <en>This method deletes Maintenance log records upto given number.<br></en>
     * 
     * @param rowNum :
     *            <en>number of records to delete</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMaitenLogByRownum(String rowNum)
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM (SELECT * FROM  COM_MAINTENANCELOG ORDER BY LOGDATE) WHERE ROWNUM <= " + rowNum;
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete role from Role table
            result = statement.executeUpdate(sql);

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
     * <en>This method deletes Maintenance log records between the given dates.<br></en>
     * 
     * @param fromDate
     *            <en>from Date</en>
     * @param toDate
     *            <en>to Date</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMaitenLogByDates(Date fromDate, Date toDate)
            throws SQLException
    {
        int result;
        try
        {

            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM COM_MAINTENANCELOG WHERE LOGDATE BETWEEN ");
            sql.append(getDateFormat(fromDate));
            sql.append(" AND ");
            sql.append(getDateFormat(toDate));

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();

            // delete role from Role table
            result = statement.executeUpdate(sql.toString());

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
}
