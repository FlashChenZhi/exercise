// $Id: OperationLogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.Converter;
import jp.co.daifuku.bluedog.util.StringUtils;
import jp.co.daifuku.emanager.database.entity.OperationLog;
import jp.co.daifuku.emanager.util.EmLog4jLogger;


/**
 * <jp>操作ログテーブルに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to access and update OperationLog data.<br></en>
 * 
 * @author $Author: Muneendra
 */
public class OperationLogHandlerImpl
        extends AbstractHandler
        implements OperationLogHandler
{
    /**
     * 項目の件数
     */
    private static final int ITEM_COUNT = 40;

    /**
     * @param conn DBコネクション
     */
    public OperationLogHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: OperationLogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $";
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.OperationLogHandler#createOperationLog(jp.co.daifuku.emanager.database.entity.OperationLog)
     */
    public int createOperationLog(OperationLog operationLog)
            throws SQLException
    {
        StringBuffer sql = new StringBuffer();

        sql.append("INSERT INTO COM_OPERATIONLOG (LOG_DATE, LOG_DATE_GMT, USER_ID, USER_NAME, ");
        sql.append("TERMINAL_NUMBER, TERMINAL_NAME, IPADDRESS, DS_NO, PAGENAMERESOURCEKEY, OPERATION_TYPE, DETAIL, ");
        for (int i = 1; i <= ITEM_COUNT; i++)
        {
            sql.append("ITEM_" + i);
            if (i != ITEM_COUNT)
            {
                sql.append(", ");
            }
        }
        sql.append(")");
        sql.append("VALUES (SYSTIMESTAMP, SYS_EXTRACT_UTC(CURRENT_TIMESTAMP), ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        for (int i = 1; i <= ITEM_COUNT; i++)
        {
            sql.append("?");
            if (i != ITEM_COUNT)
            {
                sql.append(", ");
            }
        }
        sql.append(")");

        EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

        int result;

        try
        {
            super.preparedStatement = connection.prepareStatement(sql.toString());

            int idx = 1;

            preparedStatement.setString(idx++, operationLog.getUserId());
            preparedStatement.setString(idx++, operationLog.getUserName());
            preparedStatement.setString(idx++, operationLog.getTerminalNumber());
            preparedStatement.setString(idx++, operationLog.getTerminalName());
            preparedStatement.setString(idx++, operationLog.getIpAddress());
            preparedStatement.setString(idx++, operationLog.getDsNumber());
            preparedStatement.setString(idx++, operationLog.getPageName());
            preparedStatement.setInt(idx++, operationLog.getOperationType());
            preparedStatement.setString(idx++, operationLog.getDetails());

            List itemList = operationLog.getItemList();
            for (int i = 0; i < ITEM_COUNT; i++)
            {
                String item = null;
                if (itemList.size() > i)
                {
                    item = (String)itemList.get(i);
                    try
                    {
                        if (StringUtils.getByteLength(item) > 128)
                        {
                            item = StringUtils.substrb(item, 0, 128);
                        }
                    }
                    catch (Exception ex)
                    {
                        item = null;
                    }
                }
                preparedStatement.setString(idx++, item);
            }

            // Create OperationLog
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
     * <en>Finds the number of records in Operation Log for given conditions.</en>
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
    public int findCountOperationLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, String dateFormat)
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
     * <en>This method returns all Operation log information for given conditions</en>
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param startRow :<en> Start record. Should be >0</en>
     * @param endRow :<en> End record. </en>
     * @param dateFormat :<en> date format </en>
     * @return OperationLog[]: <en>Returns Accesslog data as AccessLog entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public OperationLog[] findOperationLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, int startRow, int endRow, String dateFormat)
            throws SQLException
    {
        OperationLog operationLog[] = null;
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
                OperationLog temp = new OperationLog();
                temp.setLogDate(resultset.getTimestamp(EmTableColumns.COM_OPERATIONLOG_LOG_DATE));
                temp.setGmtDate(resultset.getTimestamp(EmTableColumns.COM_OPERATIONLOG_DATE_GMT));
                temp.setUserId(resultset.getString(EmTableColumns.COM_OPERATIONLOG_USER_ID));
                temp.setUserName(resultset.getString(EmTableColumns.COM_OPERATIONLOG_USER_NAME));
                temp.setTerminalNumber(resultset.getString(EmTableColumns.COM_OPERATIONLOG_TERMINAL_NUMBER));
                temp.setTerminalName(resultset.getString(EmTableColumns.COM_OPERATIONLOG_TERMINAL_NAME));
                temp.setIpAddress(resultset.getString(EmTableColumns.COM_OPERATIONLOG_IPADDRESS));
                temp.setDsNumber(resultset.getString(EmTableColumns.COM_OPERATIONLOG_DS_NO));
                temp.setPageName(resultset.getString(EmTableColumns.COM_OPERATIONLOG_PAGENAMERESOURCEKEY));
                temp.setAccessType(resultset.getInt(EmTableColumns.COM_OPERATIONLOG_OPERATION_TYPE));
                temp.setDetails(resultset.getString(EmTableColumns.COM_OPERATIONLOG_DETAIL));

                List items = new ArrayList();
                for (int i = 1; i <= ITEM_COUNT; i++)
                {
                    items.add(Converter.nullToStr(resultset.getString(EmTableColumns.COM_OPERATIONLOG_ITEM + i), ""));
                    temp.setItemList(items);
                }

                list.add(temp);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                operationLog = new OperationLog[size];
                operationLog = (OperationLog[])list.toArray(operationLog);
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
        return operationLog;
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
                pageName = resultset.getString(EmTableColumns.COM_OPERATIONLOG_PAGENAMERESOURCEKEY);
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
