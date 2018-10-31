// $Id: FunctionHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>Functionハンドラのinterfaceを実装します。<br></jp>
 * <en>This class implements FunctionHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class FunctionHandlerImpl
        extends AbstractHandler
        implements FunctionHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    FunctionHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>Functionクラスをデータベースに追加します。 <br>
     * </jp> <en>This method persists given Function infomration.</en>
     * 
     * @param function
     *            <en> Function information to be persisted.</en>
     * @return int <en>inserted number of rows</en>
     * @throws SQLException
     *             <en>if a database access error occurs during creation.</en>
     */
    public int createFunction(Function function)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_FUNCTION(FUNCTIONID,SUBMENUID,DS_NO,BUTTONDISPORDER,BUTTONRESOURCEKEY,PAGENAMERESOURCEKEY,"
                            + "DOAUTHENTICATION_FLAG,URI,FRAMENAME,HIDDEN_FLAG,UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?)";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, function.getFunctionId());
            preparedStatement.setString(2, function.getSubMenuId());
            preparedStatement.setString(3, function.getDsNumber());
            preparedStatement.setInt(4, function.getButtonDisplayOrder());
            preparedStatement.setString(5, function.getButtonResourceKey());
            preparedStatement.setString(6, function.getPageResourceKey());
            preparedStatement.setBoolean(7, function.getDoAuthenticationFlag());
            preparedStatement.setString(8, function.getUrl());
            preparedStatement.setString(9, function.getFrameName());
            preparedStatement.setBoolean(10, function.getHiddenFlag());
            preparedStatement.setString(11, function.getUpdateUser());
            preparedStatement.setString(12, function.getUpdateTerminal());
            preparedStatement.setInt(13, function.getUpdateKind());
            preparedStatement.setString(14, function.getUpdateProcess());

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
     * <jp>FunctionIDでデータベースを検索し、Functionエンティティを返却します。 <br></jp> 
     * <en>This method selects all Function information for a givenFunction ID</en>
     * 
     * @param functionId
     *            <en> function Id </en>
     * @return Function <en>Returns Function entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public Function findByFunctionId(String functionId)
            throws SQLException
    {
        Function function = null;
        try
        {

            String sql = "SELECT * FROM COM_FUNCTION WHERE FUNCTIONID=  '" + functionId + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            function = new Function();
            function.setFunctionId(resultset.getString(EmTableColumns.FUNCTION_FUNCTIONID));
            function.setSubMenuId(resultset.getString(EmTableColumns.FUNCTION_SUBMENUID));
            function.setDsNumber(resultset.getString(EmTableColumns.FUNCTION_DS_NO));
            function.setButtonDisplayOrder(resultset.getInt(EmTableColumns.FUNCTION_BUTTONDISPORDER));
            function.setButtonResourceKey(resultset.getString(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY));
            function.setPageResourceKey(resultset.getString(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY));
            function.setDoAuthenticationFlag(resultset.getBoolean(EmTableColumns.FUNCTION_DOAUTHENTICATION));
            function.setUrl(resultset.getString(EmTableColumns.FUNCTION_URI));
            function.setFrameName(resultset.getString(EmTableColumns.FUNCTION_FRAMENAME));
            function.setHiddenFlag(resultset.getBoolean(EmTableColumns.FUNCTION_HIDDEN_FLAG));

            function.setUpdateDate(resultset.getTimestamp(EmTableColumns.FUNCTION_UPDATE_DATE));
            function.setUpdateUser(resultset.getString(EmTableColumns.FUNCTION_UPDATE_USER));
            function.setUpdateTerminal(resultset.getString(EmTableColumns.FUNCTION_UPDATE_TERMINAL));
            function.setUpdateKind(resultset.getInt(EmTableColumns.FUNCTION_UPDATE_KIND));
            function.setUpdateProcess(resultset.getString(EmTableColumns.FUNCTION_UPDATE_PROCESS));

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
        return function;
    }

    /**
     * <jp>SubMenuIDでデータベースを検索しFunctionエンティティの配列を返却します。 <br></jp> 
     * <en>This method selects all Function information for given SubMenuId</en>
     * 
     * @param subMenuId
     *            <en> Sub Menu Id </en>
     * @return Function[] <en>Returns Function entity array. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public Function[] findBySubMenuId(String subMenuId)
            throws SQLException
    {

        Function function[] = null;
        try
        {
            // get number of records available
            int count = this.findCountBySubMenuId(subMenuId);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql =
                    "SELECT * FROM COM_SUBMENU,COM_FUNCTION WHERE "
                            + "COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID AND COM_SUBMENU.SUBMENUID ='" + subMenuId
                            + "' ORDER BY COM_FUNCTION.BUTTONDISPORDER";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            function = new Function[count];

            int temp = 0;
            while (resultset.next())
            {
                function[temp] = new Function();
                function[temp].setFunctionId(resultset.getString(EmTableColumns.FUNCTION_FUNCTIONID));
                function[temp].setSubMenuId(resultset.getString(EmTableColumns.FUNCTION_SUBMENUID));
                function[temp].setDsNumber(resultset.getString(EmTableColumns.FUNCTION_DS_NO));
                function[temp].setButtonDisplayOrder(resultset.getInt(EmTableColumns.FUNCTION_BUTTONDISPORDER));
                function[temp].setButtonResourceKey(resultset.getString(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY));
                function[temp].setPageResourceKey(resultset.getString(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY));
                function[temp].setDoAuthenticationFlag(resultset.getBoolean(EmTableColumns.FUNCTION_DOAUTHENTICATION));
                function[temp].setUrl(resultset.getString(EmTableColumns.FUNCTION_URI));
                function[temp].setFrameName(resultset.getString(EmTableColumns.FUNCTION_FRAMENAME));
                function[temp].setHiddenFlag(resultset.getBoolean(EmTableColumns.FUNCTION_HIDDEN_FLAG));

                function[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.FUNCTION_UPDATE_DATE));
                function[temp].setUpdateUser(resultset.getString(EmTableColumns.FUNCTION_UPDATE_USER));
                function[temp].setUpdateTerminal(resultset.getString(EmTableColumns.FUNCTION_UPDATE_TERMINAL));
                function[temp].setUpdateKind(resultset.getInt(EmTableColumns.FUNCTION_UPDATE_KIND));
                function[temp].setUpdateProcess(resultset.getString(EmTableColumns.FUNCTION_UPDATE_PROCESS));
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

        return function;
    }

    /**
     * <jp>FunctionIDとそれに関連するデータの配列を返します。 <br></jp> 
     * <en>This method returns all Function ids and related data.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By FUNCTIONID 1: Order By BUTTONDISPORDER</en>
     * @return Function[]: <en>Returns function data as function entity array.
     *         Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public Function[] findAll(int orderBy)
            throws SQLException
    {
        Function function[] = null;
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
                sql = "SELECT * FROM COM_FUNCTION ORDER BY FUNCTIONID";
            }
            else
            {
                sql = "SELECT * FROM COM_FUNCTION ORDER BY BUTTONDISPORDER";
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            function = new Function[count];

            int temp = 0;
            while (resultset.next())
            {
                function[temp] = new Function();
                function[temp].setFunctionId(resultset.getString(EmTableColumns.FUNCTION_FUNCTIONID));
                function[temp].setSubMenuId(resultset.getString(EmTableColumns.FUNCTION_SUBMENUID));
                function[temp].setDsNumber(resultset.getString(EmTableColumns.FUNCTION_DS_NO));
                function[temp].setButtonDisplayOrder(resultset.getInt(EmTableColumns.FUNCTION_BUTTONDISPORDER));
                function[temp].setButtonResourceKey(resultset.getString(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY));
                function[temp].setPageResourceKey(resultset.getString(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY));
                function[temp].setDoAuthenticationFlag(resultset.getBoolean(EmTableColumns.FUNCTION_DOAUTHENTICATION));
                function[temp].setUrl(resultset.getString(EmTableColumns.FUNCTION_URI));
                function[temp].setFrameName(resultset.getString(EmTableColumns.FUNCTION_FRAMENAME));
                function[temp].setHiddenFlag(resultset.getBoolean(EmTableColumns.FUNCTION_HIDDEN_FLAG));

                function[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.FUNCTION_UPDATE_DATE));
                function[temp].setUpdateUser(resultset.getString(EmTableColumns.FUNCTION_UPDATE_USER));
                function[temp].setUpdateTerminal(resultset.getString(EmTableColumns.FUNCTION_UPDATE_TERMINAL));
                function[temp].setUpdateKind(resultset.getInt(EmTableColumns.FUNCTION_UPDATE_KIND));
                function[temp].setUpdateProcess(resultset.getString(EmTableColumns.FUNCTION_UPDATE_PROCESS));
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
        return function;
    }

    /**
     * <jp>FunctionIDの情報を更新します。 <br></jp> 
     * <en>This method modifies Function Id information</en>
     * 
     * @param function
     *            <en> function information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByFunctionId(Function function)
            throws SQLException
    {
        int result;
        try
        {

            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_FUNCTION SET ");

            if (function.getSubMenuId() != null)
            {
                sql.append(EmTableColumns.FUNCTION_SUBMENUID + "='" + function.getSubMenuId() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_SUBMENUID + "=NULL,");
            }

            if (function.getDsNumber() != null)
            {
                sql.append(EmTableColumns.FUNCTION_DS_NO + "='" + function.getDsNumber() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_DS_NO + "=NULL,");
            }

            sql.append(EmTableColumns.FUNCTION_BUTTONDISPORDER + "=" + function.getButtonDisplayOrder() + ",");

            if (function.getButtonResourceKey() != null)
            {
                sql.append(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY + "='" + function.getButtonResourceKey() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY + "=NULL,");
            }

            if (function.getPageResourceKey() != null)
            {
                sql.append(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY + "='" + function.getPageResourceKey() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY + "=NULL,");
            }

            if (function.getDoAuthenticationFlag())
            {
                sql.append(EmTableColumns.FUNCTION_DOAUTHENTICATION + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_DOAUTHENTICATION + "=0,");
            }

            if (function.getUrl() != null)
            {
                sql.append(EmTableColumns.FUNCTION_URI + "='" + function.getUrl() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_URI + "=NULL,");
            }

            if (function.getFrameName() != null)
            {
                sql.append(EmTableColumns.FUNCTION_FRAMENAME + "='" + function.getFrameName() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_FRAMENAME + "=NULL,");
            }

            if (function.getHiddenFlag())
            {
                sql.append(EmTableColumns.FUNCTION_HIDDEN_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_HIDDEN_FLAG + "=0,");
            }

            sql.append(EmTableColumns.FUNCTION_UPDATE_DATE + "=SYSDATE,");

            if (function.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_USER + "='" + function.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_USER + "=NULL,");
            }

            if (function.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_TERMINAL + "='" + function.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.FUNCTION_UPDATE_KIND + "=" + function.getUpdateKind() + ",");

            if (function.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_PROCESS + "='" + function.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.FUNCTION_UPDATE_PROCESS + "=NULL");
            }

            // Where contions
            sql.append(" WHERE FUNCTIONID='" + function.getFunctionId() + "'");

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            // create statement
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
     * <jp>FunctionテーブルからRecord番号を検索します。 <br></jp> 
     * <en>Finds the number of records in Function table.</en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCount()
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_FUNCTION";
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
     * <jp>FunctionIDでFunctionテーブルからRecord番号を検索します。 <br></jp> 
     * <en>Finds the number of records in Function table for given functionId ID.</en>
     * 
     * @param functionId
     *            <en> functionId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountByFunctionId(String functionId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_FUNCTION WHERE FUNCTIONID= '" + functionId + "'";
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
     * <jp>SubMenuIdでFunctionテーブルからRecord番号を検索します。 <br></jp> 
     * <en>Finds the number of records in Function table for given subMenuId..</en>
     * 
     * @param subMenuId
     *            <en> subMenuId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountBySubMenuId(String subMenuId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_SUBMENU,COM_FUNCTION WHERE "
                            + "COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID AND COM_SUBMENU.SUBMENUID ='" + subMenuId
                            + "'";;
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
     * <jp>FunctionIDに該当するものをデータベースから削除します。 <br></jp> 
     * <en>This method deletes Function infomration for a given functionId.</en>
     * 
     * @param functionId :
     *            <en>functionId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteFunction(String functionId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_FUNCTION WHERE FUNCTIONID='" + functionId + "'";
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
     * <jp>SubMenuIDに該当するものをデータベースから削除します。 <br></jp> 
     * <en>This method deletes Function infomration for a given submenuId.</en>
     * 
     * @param submenuId :
     *            <en>submenuId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteFunctionBySubmenuId(String submenuId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_FUNCTION WHERE SUBMENUID='" + submenuId + "'";
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.FunctionHandler#findByDsNo(java.lang.String)
     */
    public Function[] findByDsNo(String dsNo)
            throws SQLException
    {
        Function function[] = null;
        try
        {
            // SQL count query
            String sql = "SELECT * FROM COM_FUNCTION WHERE DS_NO = ?";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dsNo);
            resultset = this.preparedStatement.executeQuery();

            List functionList = new ArrayList();

            while (resultset.next())
            {
                Function func = new Function();

                func.setFunctionId(resultset.getString(EmTableColumns.FUNCTION_FUNCTIONID));
                func.setSubMenuId(resultset.getString(EmTableColumns.FUNCTION_SUBMENUID));
                func.setDsNumber(resultset.getString(EmTableColumns.FUNCTION_DS_NO));
                func.setButtonDisplayOrder(resultset.getInt(EmTableColumns.FUNCTION_BUTTONDISPORDER));
                func.setButtonResourceKey(resultset.getString(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY));
                func.setPageResourceKey(resultset.getString(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY));
                func.setDoAuthenticationFlag(resultset.getBoolean(EmTableColumns.FUNCTION_DOAUTHENTICATION));
                func.setUrl(resultset.getString(EmTableColumns.FUNCTION_URI));
                func.setFrameName(resultset.getString(EmTableColumns.FUNCTION_FRAMENAME));
                func.setHiddenFlag(resultset.getBoolean(EmTableColumns.FUNCTION_HIDDEN_FLAG));

                func.setUpdateDate(resultset.getTimestamp(EmTableColumns.FUNCTION_UPDATE_DATE));
                func.setUpdateUser(resultset.getString(EmTableColumns.FUNCTION_UPDATE_USER));
                func.setUpdateTerminal(resultset.getString(EmTableColumns.FUNCTION_UPDATE_TERMINAL));
                func.setUpdateKind(resultset.getInt(EmTableColumns.FUNCTION_UPDATE_KIND));
                func.setUpdateProcess(resultset.getString(EmTableColumns.FUNCTION_UPDATE_PROCESS));

                functionList.add(func);
            }

            if (functionList.size() > 0)
            {
                int size = functionList.size();
                function = (Function[])functionList.toArray(new Function[size]);
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
        return function;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.FunctionHandler#findByDsNoExceptFunctionId(java.lang.String, java.lang.String)
     */
    public Function[] findByDsNoExceptFunctionId(String dsNo, String functionId)
            throws SQLException
    {
        Function[] functions = findByDsNo(dsNo);

        List result = new ArrayList();

        if (functions != null && functions.length > 0)
        {
            for (int i = 0; i < functions.length; i++)
            {
                // 指定されたファンクションID以外の場合
                if (!functions[i].getFunctionId().equals(functionId))
                {
                    result.add(functions[i]);
                }
            }
        }

        return (Function[])result.toArray(new Function[result.size()]);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.FunctionHandler#updatePageNameResourceKeyByDsNo(jp.co.daifuku.emanager.database.entity.Function)
     */
    public int updatePageNameResourceKeyByDsNo(Function function)
            throws SQLException
    {
        int result;
        try
        {

            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_FUNCTION SET PAGENAMERESOURCEKEY = ?, UPDATE_DATE = SYSDATE, UPDATE_USER = ?, UPDATE_TERMINAL = ?, UPDATE_KIND = ?, UPDATE_PROCESS = ? WHERE DS_NO = ?");

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());

            // create statement
            preparedStatement = connection.prepareStatement(sql.toString());

            preparedStatement.setString(1, function.getPageResourceKey());
            preparedStatement.setString(2, function.getUpdateUser());
            preparedStatement.setString(3, function.getUpdateTerminal());
            preparedStatement.setInt(4, function.getUpdateKind());
            preparedStatement.setString(5, function.getUpdateProcess());
            preparedStatement.setString(6, function.getDsNumber());

            // Update user information
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
