// $Id: TerminalChangeMapHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>端末変更マップハンドラのinterfaceを実装するクラスです。 <br></jp>
 * This class implments the TerminalChangeMapHandler interface
 * 
 * @author $Author: Muneendra *
 */
public class TerminalChangeMapHandlerImpl
        extends AbstractHandler
        implements TerminalChangeMapHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    TerminalChangeMapHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * <jp>端末変更クラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given terminal infomration in termnal Change
     * maptable.</en>
     * 
     * @param terminalInfo :
     *            <en> Terminal change information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal change
     *             info creation.</en>
     */
    public int createTerminalChangeMap(Terminal terminalInfo)
            throws SQLException
    {
        int result = 0;
        try
        {
            boolean hasArray = terminalInfo.isChangneTerminalNumbeArray();
            if (!hasArray)
            {
                throw new SQLException("No Terminal numbers to create");
            }

            String[] terminalNumberArray = terminalInfo.getChangneTerminalNumbeArray();
            if (terminalNumberArray == null)
            {
                throw new SQLException("No Terminal numbers to create");
            }

            // Insert SQL statement
            String sql =
                    "INSERT INTO COM_TERMINALCHANGEMAP (TERMINALNUMBER,CHANGETERMINALNUMBER,UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < terminalNumberArray.length; i++)
            {
                // Set data
                preparedStatement.setString(1, terminalInfo.getTerminalNumber());
                preparedStatement.setString(2, terminalNumberArray[i]);
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
     * <jp>端末番号でデータベースから端末情報を検索し、その配列を返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal number</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return Terminal[] : <en>Returns terminal Information. Returns null If no
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

            int count = this.findCountByTerminalNumber(terminalNumber);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql =
                    "SELECT COM_TERMINAL.*  FROM COM_TERMINAL,COM_TERMINALCHANGEMAP WHERE"
                            + " COM_TERMINALCHANGEMAP.CHANGETERMINALNUMBER = COM_TERMINAL.TERMINALNUMBER AND COM_TERMINALCHANGEMAP.TERMINALNUMBER = '"
                            + terminalNumber + "' ORDER BY COM_TERMINAL.TERMINALNUMBER";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            terminalInfo = new Terminal[count];
            int temp = 0;
            while (resultset.next())
            {
                terminalInfo[temp] = new Terminal();

                // set data to entity
                terminalInfo[temp].setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
                terminalInfo[temp].setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
                terminalInfo[temp].setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
                terminalInfo[temp].setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
                terminalInfo[temp].setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
                terminalInfo[temp].setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

                terminalInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
                terminalInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
                terminalInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
                terminalInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
                terminalInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));
                // increment rray variable
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
     * <en>Finds total number of records available for a given Terminal Number
     * </en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountByTerminalNumber(String terminalNumber)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query

            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_TERMINAL,COM_TERMINALCHANGEMAP "
                            + "WHERE COM_TERMINALCHANGEMAP.CHANGETERMINALNUMBER = COM_TERMINAL.TERMINALNUMBER "
                            + "AND COM_TERMINALCHANGEMAP.TERMINALNUMBER='" + terminalNumber + "'";
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
     * <jp>端末番号に該当する端末変更マップ情報をデータベースから削除します。 <br></jp>
     * <en>This method deletes TerminalChangeMap infomration for given
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
    public int deleteTerminalChangeMap(String terminalNumber)
            throws SQLException
    {
        int result;

        try
        {
            String sql = "DELETE FROM COM_TERMINALCHANGEMAP WHERE TERMINALNUMBER='" + terminalNumber + "'";
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
