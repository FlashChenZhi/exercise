// $Id: RoleFunctionMapHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.database.entity.Role;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>ロールファンクションマップハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements RoleFunctionMapHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class RoleFunctionMapHandlerImpl
        extends AbstractHandler
        implements RoleFunctionMapHandler, Serializable
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    RoleFunctionMapHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>ロールファンクションマップクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given RoleFunctionMap infomration.</en>
     * 
     * @param roleInfo :
     *            <en> RoleFunctionMap roleInfo which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during RoleFunctionMap
     *             info creation.</en>
     */
    public int createRoleFunctionMap(Role roleInfo)
            throws SQLException
    {
        int result;

        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_ROLEFUNCTIONMAP(ROLEID,FUNCTIONID,"
                            + "UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, roleInfo.getRoleID());
            preparedStatement.setString(2, roleInfo.getFunctionId());
            preparedStatement.setString(3, roleInfo.getUpdateUser());
            preparedStatement.setString(4, roleInfo.getUpdateTerminal());
            preparedStatement.setInt(5, roleInfo.getUpdateKind());
            preparedStatement.setString(6, roleInfo.getUpdateProcess());

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
     * <jp>ユーザIDで端末ユーザマップを検索し、ロールの配列を返却します。 <br></jp>
     * <en>This method fetches TerminalUserMap information from database for a
     * given userId</en>
     * 
     * @param roleId :
     *            <en> roleId</en>
     * @return Role[] : <en>Returns Role Information as Role Entity for given
     *         roleId. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findByRoleId(String roleId)
            throws SQLException
    {
        Role[] roleInfo = null;
        try
        {
            // get number of records available
            int count = this.findCountByRoleId(roleId);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }

            String sql = "SELECT * FROM COM_ROLEFUNCTIONMAP WHERE ROLEID = '" + roleId + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            roleInfo = new Role[count];
            int temp = 0;
            while (resultset.next())
            {
                roleInfo[temp] = new Role();
                roleInfo[temp].setFunctionId(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_FUNCTIONID));
                roleInfo[temp].setRoleID(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_ROLEID));

                roleInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_DATE));
                roleInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_USER));
                roleInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_TERMINAL));
                roleInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_KIND));
                roleInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_PROCESS));
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
        return roleInfo;
    }

    /**
     * <jp>全てのロールファンクションマップを検索し、ロールの配列を返却します。 <br></jp>
     * <en>This method returns all Role Function map information.</en>
     * 
     * @return Role[] <en>Returns roleids and other related information as Role
     *         entity array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findAll()
            throws SQLException
    {
        Role[] roleEntity = null;
        try
        {

            String sql = "SELECT * FROM COM_ROLEFUNCTIONMAP ORDER BY ROLEID";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);
            List list = new ArrayList();
            while (resultset.next())
            {
                Role roleResult = new Role();
                // set role data to user entity
                roleResult.setRoleID(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_ROLEID));
                roleResult.setFunctionId(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_FUNCTIONID));

                roleResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_DATE));
                roleResult.setUpdateUser(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_USER));
                roleResult.setUpdateTerminal(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_TERMINAL));
                roleResult.setUpdateKind(resultset.getInt(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_KIND));
                roleResult.setUpdateProcess(resultset.getString(EmTableColumns.ROLEFUNCTIONMAP_UPDATE_PROCESS));

                list.add(roleResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                roleEntity = new Role[size];
                roleEntity = (Role[])list.toArray(roleEntity);
            }

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
        return roleEntity;
    }

    /**
     * <jp>ロールIDで登録数を検索し、返却します。 <br></jp>
     * <en>Finds the number of records for a given role id.</en>
     * 
     * @param roleId
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountByRoleId(String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_ROLEFUNCTIONMAP WHERE ROLEID= '" + roleId + "'";
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
     * <jp>ファンクションIDとロールIDで登録数を検索し、返却します。 <br></jp>
     * <en>Finds the number of records for a given functionid and role id.</en>
     * 
     * @param functionId
     *            <en> functionId</en> *
     * @param roleId
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountByFunctionRoleId(String functionId, String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_ROLEFUNCTIONMAP WHERE FUNCTIONID= '" + functionId
                            + "' AND ROLEID = '" + roleId + "'";
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
     * <jp>ロールIDでデータベースからロール情報を削除します。 <br></jp>
     * <en>This method deletes Role Function infomration for a given RoleId.</en>
     * 
     * @param roleId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByRoleId(String roleId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_ROLEFUNCTIONMAP WHERE ROLEID='" + roleId + "'";
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

    /**
     * <jp>ロールIDでデータベースからロール情報を削除します。 <br></jp>
     * <en>This method deletes Role Function infomration for a given functionId.</en>
     * 
     * @param functionId :
     *            <en>functionId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByFunctionId(String functionId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_ROLEFUNCTIONMAP WHERE FUNCTIONID='" + functionId + "'";
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
