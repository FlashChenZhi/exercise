// $Id: DsNoHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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
import jp.co.daifuku.emanager.database.entity.DsNo;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>DsNoHandlerのinterfaceを実装します。 <br></jp>
 * <en>This class implements the LogHandlerImpl interface. <br></en>
 * 
 * @author $Author: T.Ogawa
 */
public class DsNoHandlerImpl
        extends AbstractHandler
        implements DsNoHandler
{
    /**
     * 
     * @param conn DBコネクション
     */
    public DsNoHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }


    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.DsNoHandler#findCountDsNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
     */
    public int findCountDsNo(String tableName, String startDate, String endDate, String userId, String dsNumber,
            String dateFormat, boolean isLikeSearch)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(DISTINCT DS_NO) as COUNT FROM ").append(tableName).append(" WHERE");

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
                if (isLikeSearch)
                {
                    sqlCount.append(" AND DS_NO LIKE  '" + dsNumber + "'");                    
                }
                else
                {
                    sqlCount.append(" AND DS_NO >= '" + dsNumber + "'");  
                }
            }
            //  System.out.println(sqlCount.toString());
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


    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.DsNoHandler#findDsNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, boolean)
     */
    public DsNo[] findDsNo(String tableName, String startDate, String endDate, String userId, String dsNumber,
            int startRow, int endRow, String daetFormat, boolean isLikeSearch)
            throws SQLException
    {
        DsNo[] result = null;
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM(SELECT ").append(tableName + ".*,").append(
                    " ROW_NUMBER() OVER(ORDER BY DS_NO ASC) RN");
            sql.append(" FROM ");
            sql.append(" (SELECT DISTINCT DS_NO,PAGENAMERESOURCEKEY ");
            sql.append(" FROM ").append(tableName);

            sql.append(" WHERE");

            if (endDate != null)
            {
                sql.append(" LOG_DATE <= TO_TIMESTAMP('" + endDate + "','" + daetFormat + "')");
            }
            else
            {
                sql.append(" LOG_DATE <= SYSDATE");
            }

            if (startDate != null)
            {
                sql.append(" AND LOG_DATE >= TO_TIMESTAMP('" + startDate + "','" + daetFormat + "')");
            }

            if (userId != null)
            {
                sql.append(" AND USER_ID= '" + userId + "'");
            }
            if (dsNumber != null)
            {
                if (isLikeSearch)
                {
                    sql.append(" AND DS_NO LIKE '" + dsNumber + "'");
                }
                else
                {
                    sql.append(" AND DS_NO >= '" + dsNumber + "'");
                }
            }
            sql.append(" )").append(tableName).append(")");
            sql.append(" WHERE RN BETWEEN " + startRow + " AND " + endRow);

            //System.out.println(sql.toString());
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            List dsList = new ArrayList();
            while (resultset.next())
            {
                DsNo dsNo = new DsNo();

                dsNo.setDsNo(resultset.getString(EmTableColumns.COM_ACCESSLOG_DS_NO));
                dsNo.setPageNameResourceKey(resultset.getString(EmTableColumns.COM_ACCESSLOG_PAGENAMERESOURCEKEY));
                dsList.add(dsNo);
            }

            if (dsList.size() > 0)
            {
                result = new DsNo[dsList.size()];
                dsList.toArray(result);
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
        return result;
    }

}
