// $Id: LogTempHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp> <br></jp>
 * <en>This class implements the LogTempHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class LogTempHandlerImpl
        extends AbstractHandler
        implements LogTempHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    LogTempHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>認証システムログのテンプレートクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given Auth log temp infomration in database.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAuthLogTemp(LogInfo logInfo)
            throws SQLException
    {

        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_AUTHLOG_TEMP (LOGDATE,USERID,IPADDRESS,LOGCLASS,MESSAGE,DETAIL,FILENAME,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            if (logInfo.getLogDate() != null)
            {
                preparedStatement.setTimestamp(1, getSqlDate(logInfo.getLogDate()));
            }
            else
            {
                preparedStatement.setTimestamp(1, getSqlDate(new Date()));
                //preparedStatement.setString (1, "SYSDATE");
            }
            preparedStatement.setString(2, logInfo.getUserId());
            preparedStatement.setString(3, logInfo.getIpAddress());
            preparedStatement.setInt(4, logInfo.getLogClass());
            preparedStatement.setString(5, logInfo.getMessage());
            preparedStatement.setString(6, logInfo.getDetails());
            preparedStatement.setString(7, logInfo.getFileName());

            if (logInfo.getUpdateDate() != null)
            {
                preparedStatement.setTimestamp(8, getSqlDate(logInfo.getUpdateDate()));
            }
            else
            {
                preparedStatement.setTimestamp(8, getSqlDate(new Date()));
                //preparedStatement.setString (8, "SYSDATE");
            }
            preparedStatement.setString(9, logInfo.getUpdateUser());
            preparedStatement.setString(10, logInfo.getUpdateTerminal());
            preparedStatement.setInt(11, logInfo.getUpdateKind());
            preparedStatement.setString(12, logInfo.getUpdateProcess());

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
     * <jp>メンテナンスログのテンプレートクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given Auth log temp infomration in data base.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createMainteLogTemp(LogInfo logInfo)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_MAINTELOG_TEMP (LOGDATE,USERID,IPADDRESS,LOGCLASS,MESSAGE,DETAIL,FILENAME,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            if (logInfo.getLogDate() != null)
            {
                preparedStatement.setTimestamp(1, getSqlDate(logInfo.getLogDate()));
            }
            else
            {
                preparedStatement.setTimestamp(1, getSqlDate(new Date()));
                //preparedStatement.setString (1, "SYSDATE");
            }
            preparedStatement.setString(2, logInfo.getUserId());
            preparedStatement.setString(3, logInfo.getIpAddress());
            preparedStatement.setInt(4, logInfo.getLogClass());
            preparedStatement.setString(5, logInfo.getMessage());
            preparedStatement.setString(6, logInfo.getDetails());
            preparedStatement.setString(7, logInfo.getFileName());

            if (logInfo.getUpdateDate() != null)
            {
                preparedStatement.setTimestamp(8, getSqlDate(logInfo.getUpdateDate()));
            }
            else
            {
                preparedStatement.setTimestamp(8, getSqlDate(new Date()));
                //preparedStatement.setString (8, "SYSDATE");
            }
            preparedStatement.setString(9, logInfo.getUpdateUser());
            preparedStatement.setString(10, logInfo.getUpdateTerminal());
            preparedStatement.setInt(11, logInfo.getUpdateKind());
            preparedStatement.setString(12, logInfo.getUpdateProcess());

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
     * <jp>全ての認証ログテンプレートを検索し、その配列を返却します <br></jp>
     * <en>This method returns all AuthLogTemp information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {

            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM(SELECT COM_AUTHLOG_TEMP.*, ROW_NUMBER() OVER(ORDER BY LOGDATE DESC) RN");
            sql.append(" FROM COM_AUTHLOG_TEMP WHERE");

            if (endDate != null)
            {
                sql.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sql.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOGDATE>= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sql.append(" AND LOGCLASS= " + logClass);
            }
            sql.append(" ORDER BY LOGDATE DESC)WHERE RN BETWEEN " + startRow + " AND " + endRow);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logInfoResult = new LogInfo();

                logInfoResult.setLogDate(resultset.getTimestamp(EmTableColumns.COM_AUTHLOG_TEMP_LOGDATE));
                logInfoResult.setUserId(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_USERID));
                logInfoResult.setIpAddress(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_IPADDRESS));
                logInfoResult.setTerminalNumber(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_TERMINALNUMBER));
                logInfoResult.setLogClass(resultset.getInt(EmTableColumns.COM_AUTHLOG_TEMP_LOGCLASS));
                logInfoResult.setMessage(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_MESSAGE));
                logInfoResult.setDetails(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_DETAIL));
                logInfoResult.setFileName(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_FILENAME));

                logInfoResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_DATE));
                logInfoResult.setUpdateUser(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_USER));
                logInfoResult.setUpdateTerminal(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_TERMINAL));
                logInfoResult.setUpdateKind(resultset.getInt(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_KIND));
                logInfoResult.setUpdateProcess(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_PROCESS));

                list.add(logInfoResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>全てのメンテナンスログテンプレートを検索し、その配列を返却します <br></jp>
     * <en>This method returns all MaintenLog information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass,
            int startRow, int endRow)
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {

            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM(SELECT COM_MAINTELOG_TEMP.*, ROW_NUMBER() OVER(ORDER BY LOGDATE DESC) RN");
            sql.append(" FROM COM_MAINTELOG_TEMP WHERE");

            if (endDate != null)
            {
                sql.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sql.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sql.append(" AND LOGCLASS= " + logClass);
            }
            sql.append(" ORDER BY LOGDATE DESC) WHERE RN BETWEEN " + startRow + " AND " + endRow);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logInfoResult = new LogInfo();

                logInfoResult.setLogDate(resultset.getTimestamp(EmTableColumns.COM_MAINTELOG_TEMP_LOGDATE));
                logInfoResult.setUserId(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_USERID));
                logInfoResult.setIpAddress(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_IPADDRESS));
                logInfoResult.setTerminalNumber(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_TERMINALNUMBER));
                logInfoResult.setLogClass(resultset.getInt(EmTableColumns.COM_MAINTELOG_TEMP_LOGCLASS));
                logInfoResult.setMessage(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_MESSAGE));
                logInfoResult.setDetails(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_DETAIL));
                logInfoResult.setFileName(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_FILENAME));

                logInfoResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_DATE));
                logInfoResult.setUpdateUser(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_USER));
                logInfoResult.setUpdateTerminal(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_TERMINAL));
                logInfoResult.setUpdateKind(resultset.getInt(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_KIND));
                logInfoResult.setUpdateProcess(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_PROCESS));

                list.add(logInfoResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>認証ログテンプレートを検索し、その番号を返却する。 <br></jp>
     * <en>Finds the number of records in AuthLogTemp for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM COM_AUTHLOG_TEMP WHERE");

            if (endDate != null)
            {
                sqlCount.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sqlCount.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sqlCount.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sqlCount.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sqlCount.append(" AND LOGCLASS= " + logClass);
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount.toString());
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
     * <jp>メンテナンスログテンプレートを検索して、その弁号を検索しています。 <br></jp>
     * <en>Finds the number of records in MaintenLog for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM COM_MAINTELOG_TEMP WHERE");

            if (endDate != null)
            {
                sqlCount.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sqlCount.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sqlCount.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sqlCount.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sqlCount.append(" AND LOGCLASS= " + logClass);
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount.toString());
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
     * <jp>認証ログと認証ログテンプレートでデータベースを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns Union of data related to AuthLog and AuthLogTemp
     * information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAuthView(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {
            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM ( SELECT COM_AUTHLOG_VIEW.*, ROW_NUMBER() OVER(ORDER BY LOGDATE DESC)");
            sql.append(" RN FROM COM_AUTHLOG_VIEW WHERE");

            if (endDate != null)
            {
                sql.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sql.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sql.append(" AND LOGCLASS= " + logClass);
            }
            sql.append(" ORDER BY LOGDATE DESC) WHERE RN BETWEEN " + startRow + " AND " + endRow);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logInfoResult = new LogInfo();
                logInfoResult.setLogDate(resultset.getTimestamp(EmTableColumns.COM_AUTHLOG_VIEW_LOGDATE));
                logInfoResult.setUserId(resultset.getString(EmTableColumns.COM_AUTHLOG_VIEW_USERID));
                logInfoResult.setIpAddress(resultset.getString(EmTableColumns.COM_AUTHLOG_VIEW_IPADDRESS));
                logInfoResult.setTerminalNumber(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_TERMINALNUMBER));
                logInfoResult.setLogClass(resultset.getInt(EmTableColumns.COM_AUTHLOG_VIEW_LOGCLASS));
                logInfoResult.setMessage(resultset.getString(EmTableColumns.COM_AUTHLOG_VIEW_MESSAGE));
                logInfoResult.setDetails(resultset.getString(EmTableColumns.COM_AUTHLOG_VIEW_DETAIL));

                list.add(logInfoResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>>認証ログを検索し、その番号を返却します。 <br></jp>
     * <en>Finds the number of records in AuthLog View for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthView(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM COM_AUTHLOG_VIEW WHERE");

            if (endDate != null)
            {
                sqlCount.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sqlCount.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sqlCount.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sqlCount.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sqlCount.append(" AND LOGCLASS= " + logClass);
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount.toString());
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
     * <jp>メンテナンスログとメンテナンスログテンプレートを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns Union of data related to AuthLog and AuthLogTemp
     * information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findMainteView(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {

            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM ( SELECT COM_MAINTELOG_VIEW.*, ROW_NUMBER() OVER(ORDER BY LOGDATE DESC)");
            sql.append(" RN FROM COM_MAINTELOG_VIEW WHERE");

            if (endDate != null)
            {
                sql.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sql.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOGDATE>= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sql.append(" AND LOGCLASS= " + logClass);
            }
            sql.append(" ORDER BY LOGDATE DESC) WHERE RN BETWEEN " + startRow + " AND " + endRow);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo logInfoResult = new LogInfo();
                logInfoResult.setLogDate(resultset.getTimestamp(EmTableColumns.COM_MAINTELOG_VIEW_LOGDATE));
                logInfoResult.setUserId(resultset.getString(EmTableColumns.COM_MAINTELOG_VIEW_USERID));
                logInfoResult.setIpAddress(resultset.getString(EmTableColumns.COM_MAINTELOG_VIEW_IPADDRESS));
                logInfoResult.setTerminalNumber(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_TERMINALNUMBER));
                logInfoResult.setLogClass(resultset.getInt(EmTableColumns.COM_MAINTELOG_VIEW_LOGCLASS));
                logInfoResult.setMessage(resultset.getString(EmTableColumns.COM_MAINTELOG_VIEW_MESSAGE));
                logInfoResult.setDetails(resultset.getString(EmTableColumns.COM_MAINTELOG_VIEW_DETAIL));

                list.add(logInfoResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>メンテナンスビューを検索し、その番号を返却します。 <br></jp>
     * <en>Finds the number of records from MaintenViedw for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteView(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String format = "YYYY-MM-DD hh24:mi:ss.ff3";
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM COM_MAINTELOG_VIEW WHERE");

            if (endDate != null)
            {
                sqlCount.append(" LOGDATE <= TO_TIMESTAMP('" + endDate.toString() + "','" + format + "')");
            }
            else
            {
                sqlCount.append(" LOGDATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sqlCount.append(" AND LOGDATE >= TO_TIMESTAMP('" + startDate.toString() + "','" + format + "')");
            }

            if (userId != null)
            {
                sqlCount.append(" AND USERID= '" + userId + "'");
            }
            if (logClass != 0)
            {
                sqlCount.append(" AND LOGCLASS= " + logClass);
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlCount.toString());
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
     * <jp>distinctデータ、ユーザ、およびプロセスに基づく情報の配列を認証システムログに返します。 <br></jp>
     * <en>This method returns COM_AUTHLOG_TEMP Log information based on
     * distinct date,user and process.</en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthLogbyDistinct()
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {

            String sql = "SELECT DISTINCT UPDATE_DATE, UPDATE_USER,FILENAME FROM COM_AUTHLOG_TEMP";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo resultLog = new LogInfo();

                resultLog.setUpdateDate(getUtilDate(resultset.getTimestamp(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_DATE)));
                resultLog.setUpdateUser(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_UPDATE_USER));
                resultLog.setFileName(resultset.getString(EmTableColumns.COM_AUTHLOG_TEMP_FILENAME));

                list.add(resultLog);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>distinctデータ、ユーザ、およびプロセスに基づく情報の配列をメンテナンスログテンプレートに返却します。 <br></jp>
     * <en>This method returns COM_MAINTELOG_TEMP Log information based on
     * distinct date,user and process.</en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteLogbyDistinct()
            throws SQLException
    {
        LogInfo logInfo[] = null;
        try
        {

            String sql = "SELECT DISTINCT UPDATE_DATE, UPDATE_USER,FILENAME FROM COM_MAINTELOG_TEMP";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                LogInfo resultLog = new LogInfo();

                resultLog.setUpdateDate(getUtilDate(resultset.getTimestamp(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_DATE)));
                resultLog.setUpdateUser(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_UPDATE_USER));
                resultLog.setFileName(resultset.getString(EmTableColumns.COM_MAINTELOG_TEMP_FILENAME));
                list.add(resultLog);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                logInfo = new LogInfo[size];
                logInfo = (LogInfo[])list.toArray(logInfo);
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
     * <jp>全ての認証ログテンプレートをデータベースから削除します。 <br></jp>
     * <en>This method deletes all records.</en>
     * 
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAllAuthTemp()
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_AUTHLOG_TEMP";
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
     * <jp>全ての認証ログテンプレートをデータベースから削除します。 <br></jp>
     * <en>This method deletes deletes the records for a given fileName</en>
     * 
     * @param fileName :
     *            <en>fileName</en>
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthTempByFileName(String fileName)
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_AUTHLOG_TEMP WHERE FILENAME='" + fileName + "'";
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
     * <jp>全てのメンテナンスログテンプレートをデータベースから削除します。 <br></jp>
     * <en>This method deletes all records.</en>
     * 
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAllMainteTemp()
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_MAINTELOG_TEMP";
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
     * <jp>メンテナンスログテンプレートのファイル名をデータベースから削除します。 <br></jp>
     * <en>This method deletes deletes the records for a given fileName</en>
     * 
     * @param fileName :
     *            <en>fileName</en>
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMainteTempByFileName(String fileName)
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_MAINTELOG_TEMP WHERE FILENAME='" + fileName + "'";
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
}
