// $Id: UserMasterChangeLogHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.util.EmLog4jLogger;


/**
 * <jp>ログハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements the LogHandlerImpl interface. <br></en>
 * @author $Author: Muneendra
 */
public class UserMasterChangeLogHandlerImpl
        extends AbstractHandler
        implements UserMasterChangeLogHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    UserMasterChangeLogHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>マスタ改廃ログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given MasterChange log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createUserMasterChangeLog(UserMasterLog logInfo)
            throws SQLException
    {
        int result;
        try
        {
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_USERHISTORY (LOG_DATE,LOG_DATE_GMT,USER_ID,USER_NAME,TERMINAL_NUMBER,TERMINAL_NAME,IPADDRESS,DS_NO,PAGENAMERESOURCEKEY,UPDATE_KIND,MASTER_USER_ID,USER_NAME_BEFORE,PASSWORD_BEFORE,PASSWORDCHANGEINTERVAL_BEFORE,"
                            + "PWDEXPIRES_BEFORE,USERSTATUS_BEFORE,SAMEUSERLOGINMAX_BEFORE,FAILEDLOGINATTEMPTS_BEFORE,ROLE_ID_BEFORE,DEPARTMENT_BEFORE,REMARK_BEFORE,USER_NAME_AFTER,PASSWORD_AFTER,PASSWORDCHANGEINTERVAL_AFTER,PWDEXPIRES_AFTER,"
                            + "USERSTATUS_AFTER,SAMEUSERLOGINMAX_AFTER,FAILEDLOGINATTEMPTS_AFTER,ROLE_ID_AFTER,DEPARTMENT_AFTER,REMARK_AFTER)"
                            + " VALUES(SYSTIMESTAMP, SYS_EXTRACT_UTC(CURRENT_TIMESTAMP),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
// 2009/01/20 K.Matsuda End

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
            preparedStatement.setInt(8, logInfo.getUpdateKind());
            preparedStatement.setString(9, logInfo.getMasterUserId());
            preparedStatement.setString(10, logInfo.getUserNameBefore());
            preparedStatement.setString(11, logInfo.getPasswordBefore());
            preparedStatement.setInt(12, logInfo.getPasswordChangeIntervalBefore());
            preparedStatement.setTimestamp(13, getSqlDate(logInfo.getPwdExpiresBefore()));
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
            preparedStatement.setInt(14, logInfo.getUserStatusBefore());
// 2009/01/20 K.Matsuda End
            preparedStatement.setInt(15, logInfo.getSameUserLoginMaxBefore());
            preparedStatement.setInt(16, logInfo.getFailedLoginAttemptsBefore());
            preparedStatement.setString(17, logInfo.getRoleIdBefore());
            preparedStatement.setString(18, logInfo.getDepartmentBefore());
            preparedStatement.setString(19, logInfo.getRemarkBefore());
            preparedStatement.setString(20, logInfo.getUserNameAfter());
            preparedStatement.setString(21, logInfo.getPasswordAfter());
            preparedStatement.setInt(22, logInfo.getPasswordChangeIntervalAfter());
            preparedStatement.setTimestamp(23, getSqlDate(logInfo.getPwdExpiresAfter()));
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
            preparedStatement.setInt(24, logInfo.getUserStatusAfter());
// 2009/01/20 K.Matsuda End
            preparedStatement.setInt(25, logInfo.getSameUserLoginMaxAfter());
            preparedStatement.setInt(26, logInfo.getFailedLoginAttemptsAfter());
            preparedStatement.setString(27, logInfo.getRoleIdAfter());
            preparedStatement.setString(28, logInfo.getDepartmentAfter());
            preparedStatement.setString(29, logInfo.getRemarkAfter());

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
    public UserMasterLog[] findUserMasterLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, int startRow, int endRow, String dateFormat)
            throws SQLException
    {
        UserMasterLog masterChangeLog[] = null;
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
                sql.append(" AND DS_NO= " + dsNumber);
            }
            sql.append(") WHERE RN BETWEEN " + startRow + " AND " + endRow + " ORDER BY RN");

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
                UserMasterLog temp = new UserMasterLog();
                temp.setLogDate(resultset.getTimestamp(EmTableColumns.COM_USERHISTORY_LOG_DATE));
                temp.setGmtDate(resultset.getTimestamp(EmTableColumns.COM_USERHISTORY_LOG_DATE_GMT));
                temp.setUserId(resultset.getString(EmTableColumns.COM_USERHISTORY_USER_ID));
                temp.setUserName(resultset.getString(EmTableColumns.COM_USERHISTORY_USER_NAME));
                temp.setTerminalNumber(resultset.getString(EmTableColumns.COM_USERHISTORY_TERMINAL_NUMBER));
                temp.setTerminalName(resultset.getString(EmTableColumns.COM_USERHISTORY_TERMINAL_NAME));
                temp.setIpAddress(resultset.getString(EmTableColumns.COM_USERHISTORY_IPADDRESS));
                temp.setDsNumber(resultset.getString(EmTableColumns.COM_USERHISTORY_DS_NO));
                temp.setPageName(resultset.getString(EmTableColumns.COM_USERHISTORY_PAGENAMERESOURCEKEY));
                temp.setUpdateKind(resultset.getInt(EmTableColumns.COM_USERHISTORY_UPDATE_KIND));

                temp.setMasterUserId(resultset.getString(EmTableColumns.COM_USERHISTORY_MASTER_USER_ID));

                // before values
                temp.setUserNameBefore(resultset.getString(EmTableColumns.COM_USERHISTORY_USER_NAME_BEFORE));
                temp.setPasswordBefore(resultset.getString(EmTableColumns.COM_USERHISTORY_PASSWORD_BEFORE));
                temp.setPasswordChangeIntervalBefore(resultset.getInt(EmTableColumns.COM_USERHISTORY_PASSWORDCHANGEINTERVAL_BEFORE));
                temp.setPwdExpiresBefore(resultset.getTimestamp(EmTableColumns.COM_USERHISTORY_PWDEXPIRES_BEFORE));
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
                temp.setUserStatusBefore(resultset.getInt(EmTableColumns.COM_USERHISTORY_USERSTATUS_BEFORE));
// 2009/01/20 K.Matsuda End
                temp.setSameUserLoginMaxBefore(resultset.getInt(EmTableColumns.COM_USERHISTORY_SAMEUSERLOGINMAX_BEFORE));
                temp.setFailedLoginAttemptsBefore(resultset.getInt(EmTableColumns.COM_USERHISTORY_FAILEDLOGINATTEMPTS_BEFORE));
                temp.setRoleIdBefore(resultset.getString(EmTableColumns.COM_USERHISTORY_ROLE_ID_BEFORE));
                temp.setDepartmentBefore(resultset.getString(EmTableColumns.COM_USERHISTORY_DEPARTMENT_BEFORE));
                temp.setRemarkBefore(resultset.getString(EmTableColumns.COM_USERHISTORY_REMARK_BEFORE));

                //After values
                temp.setUserNameAfter(resultset.getString(EmTableColumns.COM_USERHISTORY_USER_NAME_AFTER));
                temp.setPasswordAfter(resultset.getString(EmTableColumns.COM_USERHISTORY_PASSWORD_AFTER));
                temp.setPasswordChangeIntervalAfter(resultset.getInt(EmTableColumns.COM_USERHISTORY_PASSWORDCHANGEINTERVAL_AFTER));
                temp.setPwdExpiresAfter(resultset.getTimestamp(EmTableColumns.COM_USERHISTORY_PWDEXPIRES_AFTER));
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
                temp.setUserStatusAfter(resultset.getInt(EmTableColumns.COM_USERHISTORY_USERSTATUS_AFTER));
// 2009/01/20 K.Matsuda End
                temp.setSameUserLoginMaxAfter(resultset.getInt(EmTableColumns.COM_USERHISTORY_SAMEUSERLOGINMAX_AFTER));
                temp.setFailedLoginAttemptsAfter(resultset.getInt(EmTableColumns.COM_USERHISTORY_FAILEDLOGINATTEMPTS_AFTER));
                temp.setRoleIdAfter(resultset.getString(EmTableColumns.COM_USERHISTORY_ROLE_ID_AFTER));
                temp.setDepartmentAfter(resultset.getString(EmTableColumns.COM_USERHISTORY_DEPARTMENT_AFTER));
                temp.setRemarkAfter(resultset.getString(EmTableColumns.COM_USERHISTORY_REMARK_AFTER));

                list.add(temp);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                masterChangeLog = new UserMasterLog[size];
                masterChangeLog = (UserMasterLog[])list.toArray(masterChangeLog);
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
        return masterChangeLog;
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
    public int findCountUserMasterLog(String tableName, String startDate, String endDate, String userId,
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
                pageName = resultset.getString(EmTableColumns.COM_USERHISTORY_PAGENAMERESOURCEKEY);
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
