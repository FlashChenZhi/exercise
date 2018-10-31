// $Id: SubMenuHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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

import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>サブメニューハンドラクラスのinterfaceを実装します。 <br></jp>
 * <en>This class implements SubMenuHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class SubMenuHandlerImpl
        extends AbstractHandler
        implements SubMenuHandler, Serializable
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    SubMenuHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * <jp>サブメニュークラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given sub menu infomration.</en>
     * 
     * @param subMenu :
     *            <en> SubMenu subMenu information to be persisted.</en>
     * @return int : <en>inserted number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during creation.</en>
     */
    public int createSubMenu(SubMenu subMenu)
            throws SQLException
    {
        int result;
        try
        {
            // Inset SQL statement
            String sql =
                    "INSERT INTO COM_SUBMENU(SUBMENUID,MAINMENUID,SUBMENUDISPORDER,SUBMENURESOURCEKEY,UPDATE_DATE,"
                            + "UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,?,SYSDATE,?,?,?,?)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, subMenu.getSubMenuId());
            preparedStatement.setString(2, subMenu.getMainMenuId());
            preparedStatement.setInt(3, subMenu.getSubMenuDisplayOrder());
            preparedStatement.setString(4, subMenu.getSubMenuResourceKey());
            preparedStatement.setString(5, subMenu.getUpdateUser());
            preparedStatement.setString(6, subMenu.getUpdateTerminal());
            preparedStatement.setInt(7, subMenu.getUpdateKind());
            preparedStatement.setString(8, subMenu.getUpdateProcess());

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
     * <jp>サブメニューIDでデータベースから検索し、サブメニューを返却します。 <br></jp>
     * <en>This method selects all SubMenu information for a given subMenuId</en>
     * 
     * @param subMenuId :
     *            <en> submenu id </en>
     * @return SubMenu : <en>Returns SubMenu entity. Returns null If no data
     *         found</en>
     * 
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public SubMenu findBySubMenuId(String subMenuId)
            throws SQLException
    {
        SubMenu eSubMenu = null;
        try
        {

            String sql = "SELECT * FROM COM_SUBMENU WHERE SUBMENUID =  '" + subMenuId + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            eSubMenu = new SubMenu();
            eSubMenu.setSubMenuId(resultset.getString(EmTableColumns.SUBMENU_SUBMENUID));
            eSubMenu.setMainMenuId(resultset.getString(EmTableColumns.SUBMENU_MAINMENUID));
            eSubMenu.setSubMenuDisplayOrder(resultset.getInt(EmTableColumns.SUBMENU_SUBMENUDISPORDER));
            eSubMenu.setSubMenuResourceKey(resultset.getString(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY));

            eSubMenu.setUpdateDate(resultset.getTimestamp(EmTableColumns.SUBMENU_UPDATE_DATE));
            eSubMenu.setUpdateUser(resultset.getString(EmTableColumns.SUBMENU_UPDATE_USER));
            eSubMenu.setUpdateTerminal(resultset.getString(EmTableColumns.SUBMENU_UPDATE_TERMINAL));
            eSubMenu.setUpdateKind(resultset.getInt(EmTableColumns.SUBMENU_UPDATE_KIND));
            eSubMenu.setUpdateProcess(resultset.getString(EmTableColumns.SUBMENU_UPDATE_PROCESS));

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
        return eSubMenu;
    }

    /**
     * <jp>サブメニューについてデータベースから検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all SubMenuIds and respective data available.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By SUBMENUID 1: Order By SUBMENUDISPORDER</en>
     * @param dispOrder :
     *            <en> 0:All 1: exclude SUBMENUDISPORDER =-1.<br>
     * @return SubMenu[]: <en>Returns SubMenu ids as a SubMenu entity array.
     *         Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public SubMenu[] findAll(int orderBy, int dispOrder)
            throws SQLException
    {

        SubMenu eSubMenu[] = null;
        try
        {
            // get number of records available
            int count = this.findCount(dispOrder);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }
            String sql = "SELECT * FROM COM_SUBMENU";
            // if dipOrderFlag is false, add where condition
            if (dispOrder == 1)
            {
                sql = sql + " WHERE SUBMENUDISPORDER!=-1";
            }

            if (orderBy == 0)
            {
                sql = sql + " ORDER BY SUBMENUID";
            }
            else
            {
                sql = sql + " ORDER BY SUBMENUDISPORDER";
            }
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            eSubMenu = new SubMenu[count];

            int temp = 0;
            while (resultset.next())
            {
                eSubMenu[temp] = new SubMenu();
                eSubMenu[temp].setSubMenuId(resultset.getString(EmTableColumns.SUBMENU_SUBMENUID));
                eSubMenu[temp].setMainMenuId(resultset.getString(EmTableColumns.SUBMENU_MAINMENUID));
                eSubMenu[temp].setSubMenuDisplayOrder(resultset.getInt(EmTableColumns.SUBMENU_SUBMENUDISPORDER));
                eSubMenu[temp].setSubMenuResourceKey(resultset.getString(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY));

                eSubMenu[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.SUBMENU_UPDATE_DATE));
                eSubMenu[temp].setUpdateUser(resultset.getString(EmTableColumns.SUBMENU_UPDATE_USER));
                eSubMenu[temp].setUpdateTerminal(resultset.getString(EmTableColumns.SUBMENU_UPDATE_TERMINAL));
                eSubMenu[temp].setUpdateKind(resultset.getInt(EmTableColumns.SUBMENU_UPDATE_KIND));
                eSubMenu[temp].setUpdateProcess(resultset.getString(EmTableColumns.SUBMENU_UPDATE_PROCESS));
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
        return eSubMenu;
    }

    /**
     * <jp>メインメニューIdでデータベースからサブメニューを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all Submenus and respective available data for a
     * given MainMenuId.</en>
     * 
     * @param mainMenuId :
     *            <en> mainMenuId </en>
     * @param orderBy :
     *            <en> 0:Order By SUBMENUID 1: Order By SUBMENUDISPORDER</en>
     * @return SubMenu[]: <en>Returns SubMenu ids as a SubMenu entity array.
     *         Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public SubMenu[] findByMainMenuId(String mainMenuId, int orderBy)
            throws SQLException
    {

        SubMenu subMenu[] = null;
        try
        {
            // get number of records available
            int count = this.findCountByMainMenuId(mainMenuId);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }
            String sql;
            if (orderBy == 0)
            {
                sql = "SELECT * FROM COM_SUBMENU WHERE MAINMENUID='" + mainMenuId + "' ORDER BY SUBMENUID";
            }
            else
            {
                sql = "SELECT * FROM COM_SUBMENU WHERE MAINMENUID='" + mainMenuId + "' ORDER BY SUBMENUDISPORDER";
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            subMenu = new SubMenu[count];

            int temp = 0;
            while (resultset.next())
            {
                subMenu[temp] = new SubMenu();
                subMenu[temp].setSubMenuId(resultset.getString(EmTableColumns.SUBMENU_SUBMENUID));
                subMenu[temp].setMainMenuId(resultset.getString(EmTableColumns.SUBMENU_MAINMENUID));
                subMenu[temp].setSubMenuDisplayOrder(resultset.getInt(EmTableColumns.SUBMENU_SUBMENUDISPORDER));
                subMenu[temp].setSubMenuResourceKey(resultset.getString(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY));

                subMenu[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.SUBMENU_UPDATE_DATE));
                subMenu[temp].setUpdateUser(resultset.getString(EmTableColumns.SUBMENU_UPDATE_USER));
                subMenu[temp].setUpdateTerminal(resultset.getString(EmTableColumns.SUBMENU_UPDATE_TERMINAL));
                subMenu[temp].setUpdateKind(resultset.getInt(EmTableColumns.SUBMENU_UPDATE_KIND));
                subMenu[temp].setUpdateProcess(resultset.getString(EmTableColumns.SUBMENU_UPDATE_PROCESS));
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
        return subMenu;
    }

    /**
     * <jp>サブメニューの情報を修正します。 <br></jp>
     * <en>This method modifies SubMenu information .</en>
     * 
     * @param subMenu :
     *            <en> SubMenu subMenu information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateBySubMenuId(SubMenu subMenu)
            throws SQLException
    {
        int result;
        try
        {

            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_SUBMENU SET ");

            if (subMenu.getMainMenuId() != null)
            {
                sql.append(EmTableColumns.SUBMENU_MAINMENUID + "='" + subMenu.getMainMenuId() + "',");
            }
            else
            {
                sql.append(EmTableColumns.SUBMENU_MAINMENUID + "=NULL,");
            }
            sql.append(EmTableColumns.SUBMENU_SUBMENUDISPORDER + "=" + subMenu.getSubMenuDisplayOrder() + ",");
            if (subMenu.getSubMenuResourceKey() != null)
            {
                sql.append(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY + "='" + subMenu.getSubMenuResourceKey() + "',");
            }
            else
            {
                sql.append(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY + "=NULL,");
            }

            sql.append(EmTableColumns.SUBMENU_UPDATE_DATE + "=SYSDATE,");

            if (subMenu.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_USER + "='" + subMenu.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_USER + "=NULL,");
            }

            if (subMenu.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_TERMINAL + "='" + subMenu.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.SUBMENU_UPDATE_KIND + "=" + subMenu.getUpdateKind() + ",");

            if (subMenu.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_PROCESS + "='" + subMenu.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.SUBMENU_UPDATE_PROCESS + "=NULL");
            }

            // Where contions
            sql.append(" WHERE SUBMENUID='" + subMenu.getSubMenuId() + "'");

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
     * <jp>データベースからサブメニューの登録数を検索します。 <br></jp>
     * <en>Finds the number of records in SubMenu table.</en>
     * 
     * @param dispOrder :
     *            <en> 0:All 1: exclude SUBMENUDISPORDER =-1.<br>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCount(int dispOrder)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount = null;
            if (dispOrder == 1)
            {
                sqlCount = "SELECT COUNT(1) COUNT FROM COM_SUBMENU WHERE SUBMENUDISPORDER!=-1";

            }
            else
            {
                sqlCount = "SELECT COUNT(1) COUNT FROM COM_SUBMENU";
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

    /**
     * <jp>メインメニューIdでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds the number of records available for given mainMenuId</en>
     * 
     * @param mainMenuId :
     *            <en> mainMenuId </en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountByMainMenuId(String mainMenuId)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_SUBMENU WHERE MAINMENUID='" + mainMenuId
                            + "' ORDER BY SUBMENUDISPORDER";
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
     * <jp>サブメニューIDで、データベースから該当するサブメニュー情報を削除します。 <br></jp>
     * <en>This method deletes Submenu infomration for a given submenu id.</en>
     * 
     * @param subMenuId :
     *            <en>subMenuId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteSubMenu(String subMenuId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_SUBMENU WHERE SUBMENUID='" + subMenuId + "'";
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
     * <jp>メインメニューIDで、データベースから該当するサブメニュー情報を削除します。 <br></jp>
     * <en>This method deletes Submenu infomration for a given mainMenuId.</en>
     * 
     * @param mainMenuId :
     *            <en>mainMenuId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByMainMenuId(String mainMenuId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_SUBMENU WHERE MAINMENUID='" + mainMenuId + "'";
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

}
