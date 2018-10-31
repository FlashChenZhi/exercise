// $Id: AccessLogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.emanager.database.entity.AccessLog;
import jp.co.daifuku.emanager.util.EmLog4jLogger;


/**
 * <jp>画面アクセスログハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements the AccessLogHandler interface. <br></en>
 * @author $Author: Muneendra
 */
public class AccessLogHandlerImpl
        extends AbstractHandler
        implements AccessLogHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    AccessLogHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>画面アクセスログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given ScreenAccess log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAccessLog(AccessLog logInfo)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_ACCESSLOG (LOG_DATE,LOG_DATE_GMT,USER_ID,USER_NAME,TERMINAL_NUMBER,TERMINAL_NAME,IPADDRESS,DS_NO,PAGENAMERESOURCEKEY,ACCESS_TYPE,DETAIL)"
                            + " VALUES(SYSTIMESTAMP, SYS_EXTRACT_UTC(CURRENT_TIMESTAMP),?,?,?,?,?,?,?,?,?)";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, logInfo.getUserId());
            preparedStatement.setString(2, logInfo.getUserName());
            preparedStatement.setString(3, logInfo.getTerminalNumber());
            preparedStatement.setString(4, logInfo.getTerminalName());
            preparedStatement.setString(5, logInfo.getIpAddress());
            preparedStatement.setString(6, logInfo.getDsNumber());
            preparedStatement.setString(7, logInfo.getPageName());
            preparedStatement.setInt(8, logInfo.getAccessType());
            preparedStatement.setString(9, logInfo.getDetails());
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
     * <jp> <br></jp>
     * <en>Finds the number of records in Access Logfor given conditions.</en>
     * 
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param dateFormat :<en> Dateformat</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public int findCountAccessLog(String tableName, String startDate, String endDate, String userId, String dsNumber,
            String dateFormat)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM ").append(tableName).append(" WHERE");

            if (endDate != null)
            {
                sqlCount.append(" LOG_DATE <= TO_TIMESTAMP('" + endDate + "','" + dateFormat + "')");
            }
            else
            {
                sqlCount.append(" LOG_DATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sqlCount.append(" AND LOG_DATE >= TO_TIMESTAMP('" + startDate + "','" + dateFormat + "')");
            }

            if (userId != null)
            {
                sqlCount.append(" AND USER_ID= '" + userId + "'");
            }
            if (dsNumber != null)
            {
                sqlCount.append(" AND DS_NO= '" + dsNumber + "'");
            }
            System.out.println(sqlCount.toString());
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
     * <jp> <br></jp>
     * <en>This method returns all Access log information for given conditions</en>
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param startRow :<en> Start record. Should be >0</en>
     * @param endRow :<en> End record. </en>
     * @param dateFormat :<en> date format </en>
     * @return AccessLog[]: <en>Returns Accesslog data as AccessLog entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public AccessLog[] findAccessLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, int startRow, int endRow, String dateFormat)
            throws SQLException
    {
        AccessLog accessLog[] = null;
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM(SELECT ").append(tableName + ".*,").append(
                    " ROW_NUMBER() OVER(ORDER BY LOG_DATE ASC) RN");
            sql.append(" FROM ").append(tableName).append(" WHERE");

            if (endDate != null)
            {
                sql.append(" LOG_DATE <= TO_TIMESTAMP('" + endDate + "','" + dateFormat + "')");
            }
            else
            {
                sql.append(" LOG_DATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOG_DATE>= TO_TIMESTAMP('" + startDate + "','" + dateFormat + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USER_ID= '" + userId + "'");
            }
            if (dsNumber != null)
            {
                sql.append(" AND DS_NO= '" + dsNumber + "'");
            }
            sql.append(")WHERE RN BETWEEN " + startRow + " AND " + endRow + " ORDER BY RN");

            System.out.println(sql.toString());
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                AccessLog temp = new AccessLog();
                temp.setLogDate(resultset.getTimestamp(EmTableColumns.COM_ACCESSLOG_LOG_DATE));
                temp.setGmtDate(resultset.getTimestamp(EmTableColumns.COM_ACCESSLOG_LOG_DATE_GMT));
                temp.setUserId(resultset.getString(EmTableColumns.COM_ACCESSLOG_USER_ID));
                temp.setUserName(resultset.getString(EmTableColumns.COM_ACCESSLOG_USER_NAME));
                temp.setTerminalNumber(resultset.getString(EmTableColumns.COM_ACCESSLOG_TERMINAL_NUMBER));
                temp.setTerminalName(resultset.getString(EmTableColumns.COM_ACCESSLOG_TERMINAL_NAME));
                temp.setIpAddress(resultset.getString(EmTableColumns.COM_ACCESSLOG_IPADDRESS));
                temp.setDsNumber(resultset.getString(EmTableColumns.COM_ACCESSLOG_DS_NO));
                temp.setPageName(resultset.getString(EmTableColumns.COM_ACCESSLOG_PAGENAMERESOURCEKEY));
                temp.setAccessType(resultset.getInt(EmTableColumns.COM_ACCESSLOG_ACCESS_TYPE));
                temp.setDetails(resultset.getString(EmTableColumns.COM_ACCESSLOG_DETAIL));

                list.add(temp);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                accessLog = new AccessLog[size];
                accessLog = (AccessLog[])list.toArray(accessLog);
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
        return accessLog;
    }

    /**
     *  <jp> <br></jp>
     * <en>Finds the page name resource key for a given ds number</en>
     * 
     * @param dsNumber :<en> dsNumber</en>
     * @param tableName :<en> tableName</en>	 *
     * @return String :<en>Page name resource Key</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public String findPageNameByDSNum(String tableName, String dsNumber)
            throws SQLException
    {
        String pageName = null;
        if (dsNumber == null)
        {
            return pageName;
        }
        //SQL count query
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT PAGENAMERESOURCEKEY FROM ").append(tableName).append(" WHERE");
        sql.append(" DS_NO= '" + dsNumber + "'");
        try
        {
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());
            if (resultset.next())
            {
                pageName = resultset.getString(EmTableColumns.COM_ACCESSLOG_PAGENAMERESOURCEKEY);
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
        return pageName;
    }
}
