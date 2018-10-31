// $Id: LogExpImpSetHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.LogExpImpSet;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * ログエクスポート インポート設定テーブルに関するデータを扱う様々なメソッドを提供します。
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public class LogExpImpSetHandlerImpl
        extends AbstractHandler
        implements LogExpImpSetHandler
{
    /**
     * @param conn DBコネクション
     */
    public LogExpImpSetHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: LogExpImpSetHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $";
    }


    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler#findAll()
     */
    public LogExpImpSet[] findAll()
            throws SQLException
    {
        LogExpImpSet[] result = null;

        try
        {
            String sql = "SELECT * FROM COM_LOGEXPIMP_SET ORDER BY DISPORDER";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List settings = new ArrayList();

            while (resultset.next())
            {
                LogExpImpSet setting = new LogExpImpSet();

                setting.setExportTable(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_EXPORT_TABLENAME));
                setting.setImportTable(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_IMPORT_TABLENAME));
                setting.setTableResourceKey(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_TEBLERESOURCEKEY));
                setting.setCsvFilePrefix(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_CSVFILEPREFIX));
                setting.setImportDate(resultset.getTimestamp(EmTableColumns.COM_LOGEXPIMP_SET_IMPORT_DATE));
                setting.setImportFileName(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_IMPORT_FILENAME));
                setting.setDispOrder(resultset.getInt(EmTableColumns.COM_LOGEXPIMP_SET_DISPORDER));
                setting.setMasterUri(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_MASTER_URI));
                setting.setMasterFlag(resultset.getInt(EmTableColumns.COM_LOGEXPIMP_SET_MASTER_FLAG));
                setting.setExportFileName(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_EXPORT_FILENAME));
                setting.setExportFileLogDateTo(resultset.getTimestamp(EmTableColumns.COM_LOGEXPIMP_SET_EXPORT_FILE_LOG_DATE_TO));
                setting.setNextExportLogDateFrom(resultset.getTimestamp(EmTableColumns.COM_LOGEXPIMP_SET_NEXT_EXPORT_LOG_DATE_FROM));

                settings.add(setting);
            }

            if (settings.size() > 0)
            {
                result = (LogExpImpSet[])settings.toArray(new LogExpImpSet[settings.size()]);
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
     * <jp>マスタ用のLogExpImpSet情報を取得します。。<br>
     * </jp> <en>This method fetches all LogExpImpSet records for given master flag condition<br>
     * </en>
     * 
     * @return LogExpImpSet LogExpImpSet: <en>LogExpImpSet info. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public LogExpImpSet[] findMasterData()
            throws SQLException
    {
        LogExpImpSet[] result = null;

        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM COM_LOGEXPIMP_SET WHERE MASTER_FLAG IN (");
            sql.append(EmConstants.LOG_SETTING_TOOLS_MASTER).append(", ").append(EmConstants.LOG_SETTING_PRODUCT_MASTER).append(
                    ")");
            sql.append(" ORDER BY DISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql.toString());

            List settings = new ArrayList();

            while (resultset.next())
            {
                LogExpImpSet setting = new LogExpImpSet();
                setting.setExportTable(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_EXPORT_TABLENAME));
                setting.setImportTable(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_IMPORT_TABLENAME));
                setting.setTableResourceKey(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_TEBLERESOURCEKEY));
                setting.setMasterUri(resultset.getString(EmTableColumns.COM_LOGEXPIMP_SET_MASTER_URI));
                setting.setMasterFlag(resultset.getInt(EmTableColumns.COM_LOGEXPIMP_SET_MASTER_FLAG));
                settings.add(setting);
            }
            if (settings.size() > 0)
            {
                result = (LogExpImpSet[])settings.toArray(new LogExpImpSet[settings.size()]);
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler#updateImportInfo(jp.co.daifuku.emanager.database.entity.LogExpImpSet)
     */
    public int updateImportInfo(LogExpImpSet setting)
            throws SQLException
    {
        int result;
        try
        {
            // Modify SQL
            String sql =
                    "UPDATE COM_LOGEXPIMP_SET SET IMPORT_DATE = SYSDATE, IMPORT_FILENAME = ? WHERE IMPORT_TABLENAME = ?";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            // create statement
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, setting.getImportFileName());
            preparedStatement.setString(2, setting.getImportTable());

            // Update LogExpImpSet information
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler#updateExportInfo(jp.co.daifuku.emanager.database.entity.LogExpImpSet)
     */
    public int updateExportInfo(LogExpImpSet setting)
            throws SQLException
    {
        int result;
        try
        {
            // Modify SQL
            String sql =
                    "UPDATE COM_LOGEXPIMP_SET SET EXPORT_FILENAME = ?, EXPORT_FILE_LOG_DATE_TO = ?, NEXT_EXPORT_LOG_DATE_FROM = ? WHERE EXPORT_TABLENAME = ?";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            // create statement
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, setting.getExportFileName());
            preparedStatement.setTimestamp(2, getSqlDate(setting.getExportFileLogDateTo()));
            preparedStatement.setTimestamp(3, getSqlDate(setting.getNextExportLogDateFrom()));
            preparedStatement.setString(4, setting.getExportTable());

            // Update LogExpImpSet information
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

}
