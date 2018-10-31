// $Id: TerminalHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>端末ハンドラクラスのinterfaceを実装するクラスです。 <br></jp>
 * <en>This class implments the terminalhandler interface</en>
 * 
 * @author $Author: Muneendra *
 */
public class TerminalHandlerImpl
        extends AbstractHandler
        implements TerminalHandler, Serializable
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    TerminalHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * <jp>端末クラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given terminal infomration.</en>
     * 
     * @param terminalInfo :
     *            <en> Terminal information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal info
     *             creation.</en>
     */
    public int createTerminal(Terminal terminalInfo)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_TERMINAL (TERMINALNUMBER,TERMINALNAME,TERMINALADDRESS,ROLEID,PRINTERNAME,AUTOLOGIN_FLAG,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, terminalInfo.getTerminalNumber());
            preparedStatement.setString(2, terminalInfo.getTerminalName());
            preparedStatement.setString(3, terminalInfo.getTerminalAddress());
            preparedStatement.setString(4, terminalInfo.getRoleId());
            preparedStatement.setString(5, terminalInfo.getPrinterName());
            preparedStatement.setBoolean(6, terminalInfo.getAutoLoginFlag());
            preparedStatement.setString(7, terminalInfo.getUpdateUser());
            preparedStatement.setString(8, terminalInfo.getUpdateTerminal());
            preparedStatement.setInt(9, terminalInfo.getUpdateKind());
            preparedStatement.setString(10, terminalInfo.getUpdateProcess());

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
     * <jp>端末番号でデータベースから端末情報を検索し、返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal number</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return Terminal : <en>Returns terminal Information. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal findByTerminalNumber(String terminalNumber)
            throws SQLException
    {

        Terminal terminalInfo = null;
        try
        {

            String sql = "SELECT * FROM COM_TERMINAL WHERE TERMINALNUMBER = '" + terminalNumber + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            terminalInfo = new Terminal();
            // set data to entity
            terminalInfo.setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
            terminalInfo.setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
            terminalInfo.setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
            terminalInfo.setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
            terminalInfo.setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
            terminalInfo.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

            terminalInfo.setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
            terminalInfo.setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
            terminalInfo.setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
            terminalInfo.setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
            terminalInfo.setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));

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
     * <jp>端末アドレスで端末情報をデータベースから検索し、それを返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal address</en>
     * 
     * @param address :
     *            <en>Computer address</en>
     * @return Terminal : <en>Returns terminal Information. returns null if no
     *         data found</en>
     * @throws SQLException :
     *             <en>for any database access error </en>
     */
    public Terminal findByAddress(String address)
            throws SQLException
    {

        Terminal terminalInfo = null;

        try
        {

            String ipSql = "SELECT * FROM COM_TERMINAL WHERE LOWER(TERMINALADDRESS) =" + "LOWER('" + address + "')";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + ipSql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(ipSql);

            // if no record is found for IP address , Search with name
            if (!resultset.next())
            {
                return null;
            }
            terminalInfo = new Terminal();
            // set data to entity
            terminalInfo.setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
            terminalInfo.setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
            terminalInfo.setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
            terminalInfo.setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
            terminalInfo.setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
            terminalInfo.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

            terminalInfo.setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
            terminalInfo.setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
            terminalInfo.setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
            terminalInfo.setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
            terminalInfo.setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));

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
     * <jp>端末ID入力で、ワイルドカード検索して該当するものを配列として返却します。 <br></jp>
     * <en> This method finds the Terminal records where Terminal id is like
     * given input parameter. </en>
     * 
     * @param terminalId :
     *            <en> terminalId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Terminal[] : <en>Returns Terminal array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findTerminalLike(String terminalId, int startRow, int endRow)
            throws SQLException
    {
        Terminal terminalInfo[] = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_TERMINAL.*,ROW_NUMBER() OVER(ORDER BY TERMINALNUMBER ASC) RN FROM COM_TERMINAL WHERE TERMINALNUMBER LIKE '"
                            + terminalId
                            + "' "
                            + "ORDER BY TERMINALNUMBER ) WHERE RN BETWEEN "
                            + startRow
                            + " AND "
                            + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Terminal terminalResult = new Terminal();

                // set data to entity
                terminalResult.setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
                terminalResult.setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
                terminalResult.setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
                terminalResult.setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
                terminalResult.setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
                terminalResult.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

                terminalResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
                terminalResult.setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
                terminalResult.setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
                terminalResult.setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
                terminalResult.setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));

                list.add(terminalResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                terminalInfo = new Terminal[size];
                terminalInfo = (Terminal[])list.toArray(terminalInfo);
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
        return terminalInfo;
    }

    /**
     * <jp>端末ID入力で、ワイルドカード検索して該当するものを配列として返却します。 <br></jp>
     * <en> This method all terminal records except the given terminal number</en>
     * 
     * @param terminalId :
     *            <en> terminalId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Terminal[] : <en>Returns Terminal array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findAllNotTerminal(String terminalId, int startRow, int endRow)
            throws SQLException
    {
        Terminal terminalInfo[] = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_TERMINAL.*,ROW_NUMBER() OVER(ORDER BY TERMINALNUMBER ASC) RN FROM COM_TERMINAL WHERE TERMINALNUMBER != '"
                            + terminalId
                            + "' "
                            + "ORDER BY TERMINALNUMBER ) WHERE RN BETWEEN "
                            + startRow
                            + " AND "
                            + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Terminal terminalResult = new Terminal();

                // set data to entity
                terminalResult.setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
                terminalResult.setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
                terminalResult.setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
                terminalResult.setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
                terminalResult.setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
                terminalResult.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

                terminalResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
                terminalResult.setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
                terminalResult.setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
                terminalResult.setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
                terminalResult.setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));

                list.add(terminalResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                terminalInfo = new Terminal[size];
                terminalInfo = (Terminal[])list.toArray(terminalInfo);
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
        return terminalInfo;
    }

    /**
     * <jp>データベースから端末情報を検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all terminal information.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By TERMINALNUMBER 1: Order By TERMINALADDRESS</en>
     * @return Terminal[] <en>Returns terminal information as entity array.
     *         Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findAll(int orderBy)
            throws SQLException
    {
        Terminal terminalInfo[] = null;
        try
        {
            // get number of records available
            int count = this.findCount();
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql;

            if (orderBy == 0)
            {
                sql = "SELECT * FROM COM_TERMINAL ORDER BY TERMINALNUMBER";
            }
            else
            {
                sql = "SELECT * FROM COM_TERMINAL ORDER BY TERMINALADDRESS";
            }
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
        // renturn the result
        return terminalInfo;
    }

    /**
     * <jp>端末番号で、データベースに登録されている端末情報を修正します。 <br></jp>
     * <en>This method modifies terminal information for a given terminal number .</en>
     * 
     * @param terminalInfo :
     *            <enTerminalterminalInfo which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByTerminalNumber(Terminal terminalInfo)
            throws SQLException
    {
        int result;
        try
        {

            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_TERMINAL SET ");

            if (terminalInfo.getTerminalName() != null)
            {
                sql.append(EmTableColumns.TERMINAL_TERMINALNAME + "='" + terminalInfo.getTerminalName() + "',");
            }
            if (terminalInfo.getTerminalAddress() != null)
            {
                sql.append(EmTableColumns.TERMINAL_TERMINALADDRESS + "='" + terminalInfo.getTerminalAddress() + "',");
            }
            if (terminalInfo.getRoleId() != null)
            {
                sql.append(EmTableColumns.TERMINAL_ROLEID + "='" + terminalInfo.getRoleId() + "',");
            }
            if (terminalInfo.getPrinterName() != null)
            {
                sql.append(EmTableColumns.TERMINAL_PRINTERNAME + "='" + terminalInfo.getPrinterName() + "',");
            }
            if (terminalInfo.getAutoLoginFlag())
            {
                sql.append(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG + "=0,");
            }

            sql.append(EmTableColumns.TERMINAL_UPDATE_DATE + "=SYSDATE,");

            if (terminalInfo.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_USER + "='" + terminalInfo.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_USER + "=NULL,");
            }

            if (terminalInfo.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_TERMINAL + "='" + terminalInfo.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.TERMINAL_UPDATE_KIND + "=" + terminalInfo.getUpdateKind() + ",");

            if (terminalInfo.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_PROCESS + "='" + terminalInfo.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.TERMINAL_UPDATE_PROCESS + "=NULL");
            }

            // Where conditions
            sql.append(" WHERE TERMINALNUMBER='" + terminalInfo.getTerminalNumber() + "'");
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            // create porepared statement
            super.statement = connection.createStatement();
            // Update user information
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
     * <jp>端末番号で、データベースから利用できる番号を検索し、それを返却します。 <br></jp>
     * <en>Finds the number of records available for Terminal where
     * terminalNumber is like given input</en>
     * 
     * @param terminalNumber :
     *            <en> terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String terminalNumber)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_TERMINAL WHERE TERMINALNUMBER LIKE '" + terminalNumber + "'";

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
     * <jp>端末番号で、データベースから利用できる番号を検索し、それを返却します。 <br></jp>
     * <en>Finds the number of records records excluding the given number
     * terminalNumber is like given input</en>
     * 
     * @param terminalNumber :
     *            <en> terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountNotTerminal(String terminalNumber)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_TERMINAL WHERE TERMINALNUMBER != '" + terminalNumber + "'";

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
     * <jp>データベースから端末番号の登録数を検索して、それを返却します。 <br></jp>
     * <en>Finds the number of terminal numbers.</en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCount()
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_TERMINAL";
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
     * <jp>ロールIDでデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of available user records for given roleId</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountByRoleId(String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_TERMINAL WHERE ROLEID = '" + roleId + "'";
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
     * <jp>端末IDでデータベースを検索し、該当する端末情報を削除します。 <br></jp>
     * <en>This method 	deletes Terminal infomration for given terminalId.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteTerminal(String terminalNumber)
            throws SQLException
    {
        int result;

        try
        {
            String sql = "DELETE FROM COM_TERMINAL WHERE TERMINALNUMBER='" + terminalNumber + "'";
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.TerminalHandler#findCountGreaterThan(java.lang.String)
     */
    public int findCountGreaterThan(String terminalId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_TERMINAL";
            if (terminalId != null && !terminalId.equals(""))
            {
                sqlCount += " WHERE TERMINALNUMBER >= '" + terminalId + "'";
            }

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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.TerminalHandler#findTerminalGreatherThan(java.lang.String, int, int)
     */
    public Terminal[] findTerminalGreatherThan(String terminalId, int startRow, int endRow)
            throws SQLException
    {
        Terminal terminalInfo[] = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_TERMINAL.*,ROW_NUMBER() OVER(ORDER BY TERMINALNUMBER ASC) RN FROM COM_TERMINAL ";
            if (terminalId != null && !terminalId.equals(""))
            {
                sql += "WHERE TERMINALNUMBER >= '" + terminalId + "' ";
            }
            sql += "ORDER BY TERMINALNUMBER ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Terminal terminalResult = new Terminal();

                // set data to entity
                terminalResult.setTerminalNumber(resultset.getString(EmTableColumns.TERMINAL_TERMINALNUMBER));
                terminalResult.setTerminalName(resultset.getString(EmTableColumns.TERMINAL_TERMINALNAME));
                terminalResult.setTerminalAddress(resultset.getString(EmTableColumns.TERMINAL_TERMINALADDRESS));
                terminalResult.setRoleId(resultset.getString(EmTableColumns.TERMINAL_ROLEID));
                terminalResult.setPrinterName(resultset.getString(EmTableColumns.TERMINAL_PRINTERNAME));
                terminalResult.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.TERMINAL_AUTOLOGIN_FLAG));

                terminalResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.TERMINAL_UPDATE_DATE));
                terminalResult.setUpdateUser(resultset.getString(EmTableColumns.TERMINAL_UPDATE_USER));
                terminalResult.setUpdateTerminal(resultset.getString(EmTableColumns.TERMINAL_UPDATE_TERMINAL));
                terminalResult.setUpdateKind(resultset.getInt(EmTableColumns.TERMINAL_UPDATE_KIND));
                terminalResult.setUpdateProcess(resultset.getString(EmTableColumns.TERMINAL_UPDATE_PROCESS));

                list.add(terminalResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                terminalInfo = new Terminal[size];
                terminalInfo = (Terminal[])list.toArray(terminalInfo);
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
        return terminalInfo;
    }
}
