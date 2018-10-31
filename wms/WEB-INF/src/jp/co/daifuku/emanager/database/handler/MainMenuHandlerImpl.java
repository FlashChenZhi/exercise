// $Id: MainMenuHandlerImpl.java 4839 2009-08-20 06:09:33Z kajiwara $
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

import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>メインメニューハンドラのinterfaceを実装します。 <br></jp>
 * <en>This class implements MainMenuHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class MainMenuHandlerImpl
        extends AbstractHandler
        implements MainMenuHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    MainMenuHandlerImpl(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * <jp>メインメニュークラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given main menu infomration.</en>
     * 
     * @param mainMenu :
     *            <en> Main menu information to be persisted.</en>
     * @return int : <en>inserted number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createMainMenu(MainMenu mainMenu)
            throws SQLException
    {

        int result;
        try
        {
            // Inset SQL statement

            String sql =
                    "INSERT INTO COM_MAINMENU(MAINMENUID,MENUDISPORDER,MUNERESOURCEKEY,UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS)"
                            + " VALUES(?,?,?,SYSDATE,?,?,?,?)";

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);

            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql);

            // Set data
            preparedStatement.setString(1, mainMenu.getMainMenuId());
            preparedStatement.setInt(2, mainMenu.getMenuDisplayOrder());
            preparedStatement.setString(3, mainMenu.getMenuResourceKey());
            preparedStatement.setString(4, mainMenu.getUpdateUser());
            preparedStatement.setString(5, mainMenu.getUpdateTerminal());
            preparedStatement.setInt(6, mainMenu.getUpdateKind());
            preparedStatement.setString(7, mainMenu.getUpdateProcess());

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
     * <jp>URIでメニューIDを検索し、返却します。 <br></jp>
     * <en>This method fetches MenuID for given URI </en>
     * 
     * @param uri :
     *            <en> uri</en>
     * @return String : <en>Returns Menu id. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public String findMenuIdByUri(String uri)
            throws SQLException
    {

        String id = null;

        try
        {

            String sql =
                    "SELECT COM_MAINMENU.MAINMENUID FROM COM_MAINMENU, COM_SUBMENU, COM_FUNCTION "
                            + "WHERE COM_MAINMENU.MAINMENUID = COM_SUBMENU.MAINMENUID "
                            + "AND COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID " + "AND COM_FUNCTION.URI = '" + uri
                            + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            id = resultset.getString(1);

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

        return id;
    }

    /**
     * <jp>メインメニューIDでメインメニューを検索し、返却します。 <br></jp>
     * <en>This method selects all MainMenu information for a given MainMenuId</en>
     * 
     * @param menuId :
     *            <en> menu id </en>
     * @return EMainMenu : <en>Returns EMainMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public MainMenu findByMenuId(String menuId)
            throws SQLException
    {

        MainMenu eMainMenu = null;
        try
        {

            String sql = "SELECT * FROM COM_MAINMENU WHERE MAINMENUID =  '" + menuId + "'";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            eMainMenu = new MainMenu();
            eMainMenu.setMainMenuId(resultset.getString(EmTableColumns.MAINMENU_MAINMENUID));
            eMainMenu.setMenuDisplayOrder(resultset.getInt(EmTableColumns.MAINMENU_MENUDISPORDER));
            eMainMenu.setMenuResourceKey(resultset.getString(EmTableColumns.MAINMENU_MENURESOURCEKEY));

            eMainMenu.setUpdateDate(resultset.getTimestamp(EmTableColumns.MAINMENU_UPDATE_DATE));
            eMainMenu.setUpdateUser(resultset.getString(EmTableColumns.MAINMENU_UPDATE_USER));
            eMainMenu.setUpdateTerminal(resultset.getString(EmTableColumns.MAINMENU_UPDATE_TERMINAL));
            eMainMenu.setUpdateKind(resultset.getInt(EmTableColumns.MAINMENU_UPDATE_KIND));
            eMainMenu.setUpdateProcess(resultset.getString(EmTableColumns.MAINMENU_UPDATE_PROCESS));

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
        return eMainMenu;
    }

    /**
     * <jp>メインメニューIDから番号を検索し、配列を検索します。 <br></jp>
     * <en> This method finds the main menu records where main menu id is like
     * given input parameter. </en>
     * 
     * @param menuId :
     *            <en> menu id </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return MainMenu[] : <en>Returns MainMenu entity array. Returns null If
     *         no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public MainMenu[] findMenuLike(String menuId, int startRow, int endRow)
            throws SQLException
    {

        MainMenu[] mainMenu = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_MAINMENU.*,ROW_NUMBER() OVER(ORDER BY TO_NUMBER(MAINMENUID) ASC) RN FROM COM_MAINMENU WHERE MAINMENUID LIKE '"
                            + menuId
                            + "' "
                            + "ORDER BY TO_NUMBER(MAINMENUID) ) WHERE RN BETWEEN "
                            + startRow
                            + " AND "
                            + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                MainMenu resultMenu = new MainMenu();
                resultMenu.setMainMenuId(resultset.getString(EmTableColumns.MAINMENU_MAINMENUID));
                resultMenu.setMenuDisplayOrder(resultset.getInt(EmTableColumns.MAINMENU_MENUDISPORDER));
                resultMenu.setMenuResourceKey(resultset.getString(EmTableColumns.MAINMENU_MENURESOURCEKEY));

                resultMenu.setUpdateDate(resultset.getTimestamp(EmTableColumns.MAINMENU_UPDATE_DATE));
                resultMenu.setUpdateUser(resultset.getString(EmTableColumns.MAINMENU_UPDATE_USER));
                resultMenu.setUpdateTerminal(resultset.getString(EmTableColumns.MAINMENU_UPDATE_TERMINAL));
                resultMenu.setUpdateKind(resultset.getInt(EmTableColumns.MAINMENU_UPDATE_KIND));
                resultMenu.setUpdateProcess(resultset.getString(EmTableColumns.MAINMENU_UPDATE_PROCESS));
                list.add(resultMenu);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                mainMenu = new MainMenu[size];
                mainMenu = (MainMenu[])list.toArray(mainMenu);
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
        return mainMenu;
    }

    /**
     * <jp>ロールIDでメインメニューを検索し、その配列を返却します。 <br></jp>
     * <en>This method selects all MainMenu information for a given roleIds</en>
     * 
     * @param mainMenuRoleId :
     *            <en> mainMenuRoleId </en>
     * @param terminalRoleId :
     *            <en> terminalRoleId </en>
     * @return MainMenu[] : <en>Returns MainMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */

    public MainMenu[] findByRoleIds(String mainMenuRoleId, String terminalRoleId)
            throws SQLException
    {

        MainMenu[] mainMenu = null;
        try
        {
            // get number of records available
            int count = this.findCountByRoleIds(mainMenuRoleId, terminalRoleId);
            // if count is zero .. no records available
            if (count == 0)
            {
                return null;
            }
            // SQL count query
            StringBuffer sqlBfr = new StringBuffer();

// 2009/08/20 T.Kajiwara Mod 非公開画面しか設定されていないメニューは表示しない
            sqlBfr.append("SELECT COM_MAINMENU.MAINMENUID,COM_MAINMENU.MENUDISPORDER,COM_MAINMENU.MUNERESOURCEKEY");
            sqlBfr.append(" FROM  COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            sqlBfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            sqlBfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            sqlBfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            sqlBfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" AND COM_MAINMENU.MENUDISPORDER>0");
            sqlBfr.append(" AND COM_ROLE.ROLEID='" + mainMenuRoleId + "'");
            sqlBfr.append(" AND COM_FUNCTION.HIDDEN_FLAG='0'");
            sqlBfr.append(" AND COM_ROLEFUNCTIONMAP.FUNCTIONID IN");
            sqlBfr.append(" (");
            sqlBfr.append(" SELECT COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            sqlBfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            sqlBfr.append(" AND COM_SUBMENU.SUBMENUID =COM_FUNCTION.SUBMENUID");
            sqlBfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            sqlBfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" AND COM_ROLE.ROLEID='" + terminalRoleId + "'");
            sqlBfr.append(" AND COM_FUNCTION.HIDDEN_FLAG='0'");
            sqlBfr.append(" )");
            sqlBfr.append(" GROUP BY COM_MAINMENU.MAINMENUID,COM_MAINMENU.MENUDISPORDER,COM_MAINMENU.MUNERESOURCEKEY");
            sqlBfr.append("  ORDER BY COM_MAINMENU.MENUDISPORDER");
// 2009/08/20 T.Kajiwara End

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlBfr.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlBfr.toString());

            mainMenu = new MainMenu[count];

            int temp = 0;
            while (resultset.next())
            {
                mainMenu[temp] = new MainMenu();

                // set data to entity
                mainMenu[temp].setMainMenuId(resultset.getString(EmTableColumns.MAINMENU_MAINMENUID));
                mainMenu[temp].setMenuDisplayOrder(resultset.getInt(EmTableColumns.MAINMENU_MENUDISPORDER));
                mainMenu[temp].setMenuResourceKey(resultset.getString(EmTableColumns.MAINMENU_MENURESOURCEKEY));
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
        return mainMenu;
    }

    /**
     * <jp>利用できるメニューIDを検索し、メインメニューの配列を返却します。 <br></jp>
     * <en>This method returns all the MenuIds available.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By MAINMENUID 1: Order By MENUDISPORDER</en>
     * @return MainMenu[]: <en>Returns MainMenu ids as a MainMenu entity array.
     *         Returns null If no data found</en> *
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public MainMenu[] findAll(int orderBy)
            throws SQLException
    {

        MainMenu mainMenu[] = null;
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
                sql = "SELECT * FROM COM_MAINMENU ORDER BY MAINMENUID";
            }
            else
            {
                sql = "SELECT * FROM COM_MAINMENU ORDER BY MENUDISPORDER";
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            mainMenu = new MainMenu[count];

            int temp = 0;
            while (resultset.next())
            {
                mainMenu[temp] = new MainMenu();

                // set data to entity
                mainMenu[temp].setMainMenuId(resultset.getString(EmTableColumns.MAINMENU_MAINMENUID));
                mainMenu[temp].setMenuDisplayOrder(resultset.getInt(EmTableColumns.MAINMENU_MENUDISPORDER));
                mainMenu[temp].setMenuResourceKey(resultset.getString(EmTableColumns.MAINMENU_MENURESOURCEKEY));

                mainMenu[temp].setUpdateDate(resultset.getTimestamp(EmTableColumns.MAINMENU_UPDATE_DATE));
                mainMenu[temp].setUpdateUser(resultset.getString(EmTableColumns.MAINMENU_UPDATE_USER));
                mainMenu[temp].setUpdateTerminal(resultset.getString(EmTableColumns.MAINMENU_UPDATE_TERMINAL));
                mainMenu[temp].setUpdateKind(resultset.getInt(EmTableColumns.MAINMENU_UPDATE_KIND));
                mainMenu[temp].setUpdateProcess(resultset.getString(EmTableColumns.MAINMENU_UPDATE_PROCESS));
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
        return mainMenu;
    }

    /**
     * <jp>データベースからメインメニュー番号を検索します。 <br></jp>
     * <en>Finds the number of records in MainMenu table.</en>
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
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_MAINMENU";
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
     * <jp>ロールIDで利用できる番号を検索し、返却します。 <br></jp>
     * <en>Finds the number of records available for given Role Ids.</en>
     * 
     * @param mainMenuRoleId :
     *            <en> mainMenuRoleId</en>
     * @param terminalRoleId :
     *            <en> terminalRoleId </en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountByRoleIds(String mainMenuRoleId, String terminalRoleId)
            throws SQLException
    {

        int count;
        try
        {

            // count SQL
// 2009/08/20 T.Kajiwara Mod 非公開画面しか設定されていないメニューは表示しない
            StringBuffer sqlBfr = new StringBuffer();
            sqlBfr.append("SELECT 	COUNT(DISTINCT COM_MAINMENU.MAINMENUID)");
            sqlBfr.append(" FROM  COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            sqlBfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            sqlBfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            sqlBfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            sqlBfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" AND COM_MAINMENU.MENUDISPORDER>0");
            sqlBfr.append(" AND COM_ROLE.ROLEID='" + mainMenuRoleId + "'");
            sqlBfr.append(" AND COM_FUNCTION.HIDDEN_FLAG='0'");
            sqlBfr.append(" AND COM_ROLEFUNCTIONMAP.FUNCTIONID IN");
            sqlBfr.append(" (");
            sqlBfr.append(" SELECT COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            sqlBfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            sqlBfr.append(" AND COM_SUBMENU.SUBMENUID =COM_FUNCTION.SUBMENUID");
            sqlBfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            sqlBfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            sqlBfr.append(" AND COM_ROLE.ROLEID='" + terminalRoleId + "'");
            sqlBfr.append(" AND COM_FUNCTION.HIDDEN_FLAG='0'");
            sqlBfr.append(" )");
// 2009/08/20 T.Kajiwara End

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlBfr.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sqlBfr.toString());

            resultset.next();
            count = resultset.getInt(1);

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
     * <jp>利用できるメインメニューIDを検索し、返却します。 <br></jp>
     * <en>Finds the number of records available from MainMenu where mainmenuid
     * is like given input</en>
     * 
     * @param mainMenuId :
     *            <en>mainMenuId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String mainMenuId)
            throws SQLException
    {

        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_MAINMENU WHERE MAINMENUID LIKE '" + mainMenuId + "'";

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
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
     * <jp>メインメニューの情報を修正します。 <br></jp>
     * <en>This method modifies Main Menu information .</en>
     * 
     * @param mainMenu :
     *            <en> Main Menu information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByMainMenuId(MainMenu mainMenu)
            throws SQLException
    {
        int result;
        try
        {

            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_MAINMENU SET ");

            sql.append(EmTableColumns.MAINMENU_MENUDISPORDER + "=" + mainMenu.getMenuDisplayOrder() + ",");

            if (mainMenu.getMenuResourceKey() != null)
            {
                sql.append(EmTableColumns.MAINMENU_MENURESOURCEKEY + "='" + mainMenu.getMenuResourceKey() + "',");
            }
            else
            {
                sql.append(EmTableColumns.MAINMENU_MENURESOURCEKEY + "=NULL,");
            }

            sql.append(EmTableColumns.MAINMENU_UPDATE_DATE + "=SYSDATE,");

            if (mainMenu.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_USER + "='" + mainMenu.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_USER + "=NULL,");
            }

            if (mainMenu.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_TERMINAL + "='" + mainMenu.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.MAINMENU_UPDATE_KIND + "=" + mainMenu.getUpdateKind() + ",");

            if (mainMenu.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_PROCESS + "='" + mainMenu.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.MAINMENU_UPDATE_PROCESS + "=NULL");
            }
            // Where contions
            sql.append(" WHERE MAINMENUID='" + mainMenu.getMainMenuId() + "'");
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
     * <jp>データベースからメインメニューを削除します。 <br></jp>
     * <en>This method deletes main menu infomration for a given main menu id.</en>
     * 
     * @param mainMenuId :
     *            <en>main menu id</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMainMenu(String mainMenuId)
            throws SQLException
    {
        int result;
        try
        {

            String sql = "DELETE FROM COM_MAINMENU WHERE MAINMENUID='" + mainMenuId + "'";
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
     * @see jp.co.daifuku.emanager.database.handler.MainMenuHandler#findCountGreaterThan(java.lang.String)
     */
    public int findCountGreaterThan(String menuId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount = "SELECT COUNT(1) COUNT FROM COM_MAINMENU ";
            if (menuId != null && !menuId.equals(""))
            {
                sqlCount += " WHERE MAINMENUID >= '" + menuId + "'";
            }
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
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
     * @see jp.co.daifuku.emanager.database.handler.MainMenuHandler#findGreaterThan(java.lang.String, int, int)
     */
    public MainMenu[] findGreaterThan(String menuId, int startRow, int endRow)
            throws SQLException
    {
        MainMenu[] mainMenu = null;
        try
        {
            String sql =
                    "SELECT * FROM( SELECT COM_MAINMENU.*,ROW_NUMBER() OVER(ORDER BY TO_NUMBER(MAINMENUID) ASC) RN FROM COM_MAINMENU";
            if (menuId != null && !menuId.equals(""))
            {
                sql += " WHERE MAINMENUID >= '" + menuId + "' ";
            }
            sql += "ORDER BY TO_NUMBER(MAINMENUID) ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
                MainMenu resultMenu = new MainMenu();
                resultMenu.setMainMenuId(resultset.getString(EmTableColumns.MAINMENU_MAINMENUID));
                resultMenu.setMenuDisplayOrder(resultset.getInt(EmTableColumns.MAINMENU_MENUDISPORDER));
                resultMenu.setMenuResourceKey(resultset.getString(EmTableColumns.MAINMENU_MENURESOURCEKEY));

                resultMenu.setUpdateDate(resultset.getTimestamp(EmTableColumns.MAINMENU_UPDATE_DATE));
                resultMenu.setUpdateUser(resultset.getString(EmTableColumns.MAINMENU_UPDATE_USER));
                resultMenu.setUpdateTerminal(resultset.getString(EmTableColumns.MAINMENU_UPDATE_TERMINAL));
                resultMenu.setUpdateKind(resultset.getInt(EmTableColumns.MAINMENU_UPDATE_KIND));
                resultMenu.setUpdateProcess(resultset.getString(EmTableColumns.MAINMENU_UPDATE_PROCESS));
                list.add(resultMenu);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                mainMenu = new MainMenu[size];
                mainMenu = (MainMenu[])list.toArray(mainMenu);
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
        return mainMenu;
    }
}
