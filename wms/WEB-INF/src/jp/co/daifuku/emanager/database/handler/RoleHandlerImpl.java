// $Id: RoleHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>ロールハンドラクラスのinterfaceを実装します。 <br></jp>
 * <en>This class implements the RoleHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class RoleHandlerImpl
        extends AbstractHandler
        implements Serializable, RoleHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    RoleHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * <jp>ロールクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given role infomration in data base.</en>
     * 
     * @param roleInfo :
     *            <en> Role information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createRole(Role roleInfo)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_ROLE (ROLEID,ROLENAME,TARGET,FAILEDLOGINATTEMPTS,PWDCHANGEINTERVAL,UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, roleInfo.getRoleID());
            preparedStatement.setString(2, roleInfo.getRoleName());
            preparedStatement.setInt(3, roleInfo.getTarget());
            preparedStatement.setInt(4, roleInfo.getFailLoginAttem());
            preparedStatement.setInt(5, roleInfo.getPassChangeInterval());
            preparedStatement.setString(6, roleInfo.getUpdateUser());
            preparedStatement.setString(7, roleInfo.getUpdateTerminal());
            preparedStatement.setInt(8, roleInfo.getUpdateKind());
            preparedStatement.setString(9, roleInfo.getUpdateProcess());

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
     * <jp>ロール情報をデータベースから検索し、ロールの配列を返却します。 <br></jp>
     * <en>This method returns all RoleIds and respective information.</en>
     * 
     * @return Role[] <en>Returns roleids and other related information as Role
     *         entity array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findAll()
            throws SQLException
    {
        Role roleInfo[] = null;
        try
        {
            // get number of records available
            int count = this.findCount();
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }
            String sql = "SELECT * FROM COM_ROLE ORDER BY ROLEID";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            roleInfo = new Role[count];

            int temp = 0;
            while (resultset.next())
            {
                roleInfo[temp] = new Role();

                // set data to entity
                roleInfo[temp].setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
                roleInfo[temp].setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
                roleInfo[temp].setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
                roleInfo[temp].setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
                roleInfo[temp].setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

                roleInfo[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
                roleInfo[temp].setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
                roleInfo[temp].setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
                roleInfo[temp].setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
                roleInfo[temp].setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));
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
        return roleInfo;
    }

    /**
     * <jp>ロールIDでデータベースからロール情報を検索し、返却します。 <br></jp>
     * <en>This method selects all Role information for a given RoleId</en>
     * 
     * @param roleId :
     *            <en> role id </en>
     * @return Role : <en>Returns EMainMenu entit. Returns null If no data
     *         foundy</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role findByRoleId(String roleId)
            throws SQLException
    {
        Role roleInfo = null;
        try
        {

            String sql = "SELECT * FROM COM_ROLE WHERE ROLEID = '" + roleId + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            roleInfo = new Role();
            // set data to entity
            roleInfo.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
            roleInfo.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
            roleInfo.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
            roleInfo.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
            roleInfo.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

            roleInfo.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
            roleInfo.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
            roleInfo.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
            roleInfo.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
            roleInfo.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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
     * <jpロールIDでデータベースからロール情報を検索し、返却します。<br></jp>
     * <en>This method selects all Role information for a given RoleId and target</en>
     * 
     * @param roleId :
     *            <en> role id </en>
     * @param target :
     *            <en> target </en>
     * @return Role : <en>Returns EMainMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role findByRoleIdAndTarget(String roleId, int target)
            throws SQLException
    {
        Role roleInfo = null;
        try
        {

            String sql = "SELECT * FROM COM_ROLE WHERE ROLEID = '" + roleId + "' AND TARGET = " + target;
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            roleInfo = new Role();
            // set data to entity
            roleInfo.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
            roleInfo.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
            roleInfo.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
            roleInfo.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
            roleInfo.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

            roleInfo.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
            roleInfo.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
            roleInfo.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
            roleInfo.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
            roleInfo.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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
     * <jp>ロールID入力時、ワイルドカード検索して該当する配列を返却します。 <br></jp>
     * <en> This method finds the role records where role id is like given input
     * parameter. </en>
     * 
     * @param roleId :
     *            <en> roleId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Role[] : <en>Returns Role array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findRoleLike(String roleId, int startRow, int endRow)
            throws SQLException
    {

        Role[] roleEntity = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_ROLE.*,ROW_NUMBER() OVER(ORDER BY ROLEID ASC) RN FROM COM_ROLE WHERE ROLEID LIKE '"
                            + roleId + "' " + "ORDER BY ROLEID ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Role roleResult = new Role();
                // set role data to user entity
                roleResult.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
                roleResult.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
                roleResult.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
                roleResult.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
                roleResult.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

                roleResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
                roleResult.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
                roleResult.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
                roleResult.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
                roleResult.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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
     * <jp>ロールID入力時、ワイルドカード検索して該当する配列を返却します。 <br></jp>
     * <en>Finds the role recordsavailable for role where roleId is like and target is not like the given input
     * </en>
     * 
     * @param roleId :
     *            <en> roleId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @param target :
     *            <en> This Target will be excluded from the count. input 1 to exclude user target,input 2 to exclude terminal target</en>
     * @return Role[] : <en>Returns Role array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findRoleLikeAndNotTarget(String roleId, int startRow, int endRow, int target)
            throws SQLException
    {

        Role[] roleEntity = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_ROLE.*,ROW_NUMBER() OVER(ORDER BY ROLEID ASC) RN FROM COM_ROLE WHERE "
                            + "ROLEID LIKE '" + roleId + "' AND TARGET !=" + target + " ORDER BY ROLEID )"
                            + " WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Role roleResult = new Role();
                // set role data to user entity
                roleResult.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
                roleResult.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
                roleResult.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
                roleResult.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
                roleResult.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

                roleResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
                roleResult.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
                roleResult.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
                roleResult.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
                roleResult.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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
     * <jp>データベースに登録されているロール情報を修正します。 <br></jp>
     * <en>This method modifies role information .</en>
     * 
     * @param roleInfo :
     *            <en> Role information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByRoleId(Role roleInfo)
            throws SQLException
    {
        int result;
        try
        {

            // Modify SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_ROLE SET ");

            if (roleInfo.getRoleName() != null)
            {
                sql.append(EmTableColumns.ROLE_ROLENAME + "='" + roleInfo.getRoleName() + "',");
            }
            else
            {
                sql.append(EmTableColumns.ROLE_ROLENAME + "=NULL,");
            }

            sql.append(EmTableColumns.ROLE_TARGET + "=" + roleInfo.getTarget() + ",");

            sql.append(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS + "=" + roleInfo.getFailLoginAttem() + ",");

            sql.append(EmTableColumns.ROLE_PWDCHANGEINTERVAL + "=" + roleInfo.getPassChangeInterval() + ",");

            sql.append(EmTableColumns.ROLE_UPDATE_DATE + "=SYSDATE,");

            if (roleInfo.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.ROLE_UPDATE_USER + "='" + roleInfo.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.ROLE_UPDATE_USER + "=NULL,");
            }

            if (roleInfo.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.ROLE_UPDATE_TERMINAL + "='" + roleInfo.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.ROLE_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.ROLE_UPDATE_KIND + "=" + roleInfo.getUpdateKind() + ",");

            if (roleInfo.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.ROLE_UPDATE_PROCESS + "='" + roleInfo.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.ROLE_UPDATE_PROCESS + "=NULL");
            }

            // Where contions
            sql.append(" WHERE ROLEID='" + roleInfo.getRoleID() + "'");
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            // create porepared statement
            super.statement = connection.createStatement();

            // Update role information
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
     * <jp>データベースに登録されているロールの登録数を検索します。 <br></jp>
     * <en>Finds the number of records in Role table.</en>
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
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_ROLE";
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
     * <jp>ロールIDでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds the number of records available for role where roleId is like
     * given input</en>
     * 
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_ROLE WHERE ROLEID LIKE '" + roleId + "'";
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
     * <jp>ロールIDでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds the number of records available for role where roleId is like and target is not like the given input
     * </en>
     * 
     * @param roleId :
     *            <en> roleId</en>
     * @param notTarget :
     *            <en> This Target will be excluded from the count. input 1 to exclude user target,input 2 to exclude terminal target</en>
     *            
     *            
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLikeAndNotTarget(String roleId, int notTarget)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_ROLE WHERE ROLEID LIKE '" + roleId + "' AND TARGET !=" + notTarget;

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
     * <jp>ロールIDでデータベースに登録されているロールを削除します。 <br></jp>
     * <en>This method deletes Role infomration for a given RoleId.</en>
     * 
     * @param roleId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteRole(String roleId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_ROLE WHERE ROLEID='" + roleId + "'";
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
     * @see jp.co.daifuku.emanager.database.handler.RoleHandler#findCountGreaterThanAndNotTarget(java.lang.String, int)
     */
    public int findCountGreaterThanAndNotTarget(String roleId, int notTarget)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_ROLE WHERE TARGET !=" + notTarget;
            if (roleId != null && !roleId.equals(""))
            {
                sqlCount += " AND ROLEID >= '" + roleId + "'";
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
     * @see jp.co.daifuku.emanager.database.handler.RoleHandler#findCountGreaterThan(java.lang.String)
     */
    public int findCountGreaterThan(String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_ROLE";
            if (roleId != null && !roleId.equals(""))
            {
                sqlCount += " WHERE ROLEID >= '" + roleId + "'";
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
     * @see jp.co.daifuku.emanager.database.handler.RoleHandler#findRoleGreaterThan(java.lang.String, int, int)
     */
    public Role[] findRoleGreaterThan(String roleId, int startRow, int endRow)
            throws SQLException
    {
        Role[] roleEntity = null;
        try
        {
            String sql = "SELECT * FROM( SELECT COM_ROLE.*,ROW_NUMBER() OVER(ORDER BY ROLEID ASC) RN FROM COM_ROLE";
            if (roleId != null && !roleId.equals(""))
            {
                sql += " WHERE ROLEID >= '" + roleId + "' ";
            }
            sql += " ORDER BY ROLEID ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Role roleResult = new Role();
                // set role data to user entity
                roleResult.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
                roleResult.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
                roleResult.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
                roleResult.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
                roleResult.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

                roleResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
                roleResult.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
                roleResult.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
                roleResult.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
                roleResult.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.RoleHandler#findRoleGreaterThaAndNotTarget(java.lang.String, int, int, int)
     */
    public Role[] findRoleGreaterThaAndNotTarget(String roleId, int startRow, int endRow, int target)
            throws SQLException
    {
        Role[] roleEntity = null;
        try
        {
            String sql =
                    "SELECT * FROM( SELECT COM_ROLE.*,ROW_NUMBER() OVER(ORDER BY ROLEID ASC) RN FROM COM_ROLE WHERE TARGET !="
                            + target;
            if (roleId != null && !roleId.equals(""))
            {
                sql += " AND ROLEID >= '" + roleId + "' ";
            }
            sql += " ORDER BY ROLEID ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();
            while (resultset.next())
            {
                Role roleResult = new Role();
                // set role data to user entity
                roleResult.setRoleID(resultset.getString(EmTableColumns.ROLE_ROLEID));
                roleResult.setRoleName(resultset.getString(EmTableColumns.ROLE_ROLENAME));
                roleResult.setTarget(resultset.getInt(EmTableColumns.ROLE_TARGET));
                roleResult.setFailLoginAttem(resultset.getInt(EmTableColumns.ROLE_FAILEDLOGINATTEMPTS));
                roleResult.setPassChangeInterval(resultset.getInt(EmTableColumns.ROLE_PWDCHANGEINTERVAL));

                roleResult.setUpdateDate(resultset.getTimestamp(EmTableColumns.ROLE_UPDATE_DATE));
                roleResult.setUpdateUser(resultset.getString(EmTableColumns.ROLE_UPDATE_USER));
                roleResult.setUpdateTerminal(resultset.getString(EmTableColumns.ROLE_UPDATE_TERMINAL));
                roleResult.setUpdateKind(resultset.getInt(EmTableColumns.ROLE_UPDATE_KIND));
                roleResult.setUpdateProcess(resultset.getString(EmTableColumns.ROLE_UPDATE_PROCESS));

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
}
