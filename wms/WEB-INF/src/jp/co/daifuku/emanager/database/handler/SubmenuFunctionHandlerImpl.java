// $Id: SubmenuFunctionHandlerImpl.java 3965 2009-04-06 02:55:05Z admin $
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
import jp.co.daifuku.emanager.database.entity.SubmenuFunction;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>Authenticationハンドラクラスのinterfaceを実装するクラスです。 <br></jp>
 * <en>This class implements AuthenticationHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class SubmenuFunctionHandlerImpl
        extends AbstractHandler
        implements SubmenuFunctionHandler
{
    /**
     * Constructor
     * 
     * @param conn
     *            Connection
     */
    SubmenuFunctionHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>メニューIDでデータベースを検索し、サブメニューファンクションの配列を返却します。 <br></jp>
     * <en>This method selects Submenu and corresponding Function information
     * for a given mainMenuId</en>
     * 
     * @param mainMenuId
     *            <en> mainMenuId</en>
     * @param hidden
     *            <en> If hidden is 0: Exclude HIDDEN_FLAG (ALL). if hidden is
     *            1.. include HIDDEN_FLAG.</en>
     * @param noRelation
     *            <en> If noRelation is 0: Exclude (COM_SUBMENU.SUBMENUID = '*'
     *            OR COM_MAINMENU.MAINMENUID = '*') <BR>
     *            noRelation is 1: Include (COM_SUBMENU.SUBMENUID = '*' OR
     *            COM_MAINMENU.MAINMENUID = '*') if hidden is 1.. include
     *            HIDDEN_FLAG.</en>
     * @return SubmenuFunction[] <en>Returns SubmenuFunction entity array</en><en>Returns
     *         null if there is no data is available</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public SubmenuFunction[] findSubmenuFunctionByMenuId(String mainMenuId, int hidden, int noRelation)
            throws SQLException
    {
        List resultList = null;
        try
        {
            int numberOfRows = this.findCountByMenuId(mainMenuId, hidden, noRelation);
            int tempRow = 1;
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT DISTINCT COM_SUBMENU.SUBMENURESOURCEKEY,");
            sql.append(" COM_SUBMENU.SUBMENUDISPORDER,");
            sql.append(" COM_SUBMENU.SUBMENUID,");
            sql.append(" COM_SUBMENU.MAINMENUID,");
            sql.append(" COM_FUNCTION.FUNCTIONID,");
            sql.append(" COM_FUNCTION.SUBMENUID,");
            sql.append(" COM_FUNCTION.DS_NO,");
            sql.append(" COM_FUNCTION.BUTTONRESOURCEKEY,");
            sql.append(" COM_FUNCTION.BUTTONDISPORDER,");
            sql.append(" COM_FUNCTION.DOAUTHENTICATION_FLAG,");
            sql.append(" COM_FUNCTION.PAGENAMERESOURCEKEY,");
            sql.append(" COM_FUNCTION.URI,");
            sql.append(" COM_FUNCTION.FRAMENAME,");
            sql.append(" COM_FUNCTION.FUNCTIONID,");
            sql.append(" COM_FUNCTION.HIDDEN_FLAG");
            sql.append(" FROM COM_SUBMENU,COM_FUNCTION,COM_MAINMENU");
            sql.append(" WHERE COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID(+)");
            sql.append(" AND COM_SUBMENU.MAINMENUID='" + mainMenuId + "'");
            sql.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");

            if (hidden == 1)
            {
                sql.append(" AND COM_FUNCTION.HIDDEN_FLAG ='0'");
            }
            if (noRelation == 1)
            {
                sql.append(" AND (COM_SUBMENU.SUBMENUID = '*' OR COM_MAINMENU.MAINMENUID = '*')");
            }

            sql.append(" ORDER BY COM_SUBMENU.SUBMENUDISPORDER,COM_FUNCTION.BUTTONDISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + ":::" + sql);

            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql.toString());

            // move the result set to last row			
            if (!resultset.next())
            {
                // if no records are available, return null
                return null;
            }
            // submenfunction array
            resultList = new ArrayList();
            // function array for each submenu
            List functionList = null;
            String tempSubmenuId = "";

            // get the first submenuId.. ResultSet Cursor is moved to first row during null check
            String submenuId = resultset.getString(EmTableColumns.SUBMENU_SUBMENUID);

            resultList = new ArrayList();

            while (numberOfRows >= tempRow)
            {
                SubmenuFunction submenuFunction = new SubmenuFunction();
                tempSubmenuId = submenuId;
                submenuFunction.setSubMenuId(submenuId);
                submenuFunction.setSubMenuDisplayOrder(resultset.getInt(EmTableColumns.SUBMENU_SUBMENUDISPORDER));
                submenuFunction.setSubMenuResourceKey(resultset.getString(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY));
                submenuFunction.setMainMenuId(resultset.getString(EmTableColumns.SUBMENU_MAINMENUID));


                functionList = new ArrayList();
                //iterate the loop until submenu ids or get all the functions for the same submenu
                while ((tempSubmenuId.equals(submenuId)) && numberOfRows >= tempRow)
                {
                    String functionId = resultset.getString(EmTableColumns.FUNCTION_FUNCTIONID);

                    // if Function id null, Function record is not available though Submenu id is available
                    if (functionId != null && !"".equals(functionId))
                    {
                        Function function = new Function();
                        function.setFunctionId(functionId);
                        function.setSubMenuId(resultset.getString(EmTableColumns.FUNCTION_SUBMENUID));
                        function.setDsNumber(resultset.getString(EmTableColumns.FUNCTION_DS_NO));
                        function.setButtonDisplayOrder(resultset.getInt(EmTableColumns.FUNCTION_BUTTONDISPORDER));
                        function.setButtonResourceKey(resultset.getString(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY));
                        function.setPageResourceKey(resultset.getString(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY));
                        function.setDoAuthenticationFlag(resultset.getBoolean(EmTableColumns.FUNCTION_DOAUTHENTICATION));
                        function.setUrl(resultset.getString(EmTableColumns.FUNCTION_URI));
                        function.setFrameName(resultset.getString(EmTableColumns.FUNCTION_FRAMENAME));
                        function.setHiddenFlag(resultset.getBoolean(EmTableColumns.FUNCTION_HIDDEN_FLAG));

                        functionList.add(function);
                    }

                    if (numberOfRows > tempRow)
                    { // get the next result or move the ResultSet Cursor to next position
                        resultset.next();
                        submenuId = resultset.getString(EmTableColumns.SUBMENU_SUBMENUID);
                    }
                    // increment the row count
                    tempRow++;
                }
                // add the function array to SubmenuFucntion entity
                if (functionList.size() > 0)
                {
                    Function functionEntity[] = (Function[])functionList.toArray(new Function[functionList.size()]);
                    submenuFunction.setFunctionArray(functionEntity);
                }
                // add the submenufunction to array list 
                resultList.add(submenuFunction);
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
        SubmenuFunction submenuFunctionEntity[] =
                (SubmenuFunction[])resultList.toArray(new SubmenuFunction[resultList.size()]);
        return submenuFunctionEntity;
    }

    /**
     * <jp>メインメニューIDでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds the number of records available for a given main menuId</en>
     * 
     * @param mainMenuId
     *            <en> mainMenuId</en>
     * @return int :<en>Total number of records</en>
     * @param hidden
     *            <en> If hidden is 0: Exclude HIDDEN_FLAG (ALL). if hidden is
     *            1.. include HIDDEN_FLAG.</en>
     * @param noRelation
     *            <en> If noRelation is 0: Exclude (COM_SUBMENU.SUBMENUID = '*'
     *            OR COM_MAINMENU.MAINMENUID = '*') <BR>
     *            noRelation is 1: Include (COM_SUBMENU.SUBMENUID = '*' OR
     *            COM_MAINMENU.MAINMENUID = '*')
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    private int findCountByMenuId(String mainMenuId, int hidden, int noRelation)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query

            StringBuffer sqlCount = new StringBuffer();

            sqlCount.append(" SELECT COUNT( DISTINCT COM_SUBMENU.SUBMENUID+NVL(COM_FUNCTION.FUNCTIONID,0) ) COUNT FROM COM_SUBMENU,COM_FUNCTION,COM_MAINMENU WHERE COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID(+)");
            sqlCount.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            sqlCount.append(" AND COM_SUBMENU.MAINMENUID='" + mainMenuId + "'");

            if (hidden == 1)
            {
                sqlCount.append(" AND COM_FUNCTION.HIDDEN_FLAG ='0'");
            }
            if (noRelation == 1)
            {
                sqlCount.append(" AND (COM_SUBMENU.SUBMENUID = '*' OR COM_MAINMENU.MAINMENUID = '*')");
            }
            sqlCount.append(" ORDER BY COM_SUBMENU.SUBMENUDISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + ":::" + sqlCount.toString());
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
     * <jp>メインメニューIDでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds the number of records available for a given main menuId</en>
     * 
     * @param mainMenuId
     *            <en> mainMenuId</en>
     * @return int :<en>Total number of records</en>
     * @param hidden
     *            <en> If hidden is 0: Exclude HIDDEN_FLAG (ALL). if hidden is
     *            1.. include HIDDEN_FLAG.</en>
     * @param noRelation
     *            <en> If noRelation is 0: Exclude (COM_SUBMENU.SUBMENUID = '*'
     *            OR COM_MAINMENU.MAINMENUID = '*') <BR>
     *            noRelation is 1: Include (COM_SUBMENU.SUBMENUID = '*' OR
     *            COM_MAINMENU.MAINMENUID = '*')
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findSubmenuFunctionCountByMenuId(String mainMenuId, int hidden, int noRelation)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query

            StringBuffer sqlCount = new StringBuffer();

            sqlCount.append(" SELECT COUNT(DISTINCT COM_SUBMENU.SUBMENUID) COUNT FROM COM_SUBMENU,COM_FUNCTION,COM_MAINMENU WHERE COM_SUBMENU.SUBMENUID = COM_FUNCTION.SUBMENUID(+)");
            sqlCount.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            sqlCount.append(" AND COM_SUBMENU.MAINMENUID='" + mainMenuId + "'");

            if (hidden == 1)
            {
                sqlCount.append(" AND COM_FUNCTION.HIDDEN_FLAG ='0'");
            }
            if (noRelation == 1)
            {
                sqlCount.append(" AND (COM_SUBMENU.SUBMENUID = '*' OR COM_MAINMENU.MAINMENUID = '*')");
            }
            sqlCount.append(" ORDER BY COM_SUBMENU.SUBMENUDISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + ":::" + sqlCount.toString());
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
     * <jp>メインメニューIDとメインロールIDと端末ロールIDでサブメニューファンクションを検索し、その配列を返却します。 <br></jp>
     * <en>This method selects all submenus and Functions information for given
     * mainmenuId, Mainrole id and terminal role id</en>
     * 
     * @param mainMenuId
     *            <en>mainMenuId</en>
     * @param menuRoleId
     *            <en>Main Login user role Id</en>
     * @param terminalRoleId
     *            <en>Terminal role Id</en>
     * @return SubmenuFunction[] <en>Returns SubmenuFunction[]<br>
     *         This SubmenuFunction array contains SubmenuFunction and
     *         corresponding function entity objects as array <br>
     *         For each submenu, SubmenuFunction entity object is created and
     *         Array of Function entitys is created for gien submenuId. Returns
     *         null If no data found. </en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public SubmenuFunction[] findFunctionsByRoles(String mainMenuId, String menuRoleId, String terminalRoleId)
            throws SQLException
    {
        List resultList = null;
        try
        {
            // SQL count query
            // get total number of rows available
            int numberOfRows = this.findCountByRoles(mainMenuId, menuRoleId, terminalRoleId);
            int tempRow = 1;
            StringBuffer countbfr = new StringBuffer();
            countbfr.append("SELECT COM_SUBMENU.SUBMENUID,COM_SUBMENU.MAINMENUID,COM_SUBMENU.SUBMENUDISPORDER,COM_SUBMENU.SUBMENURESOURCEKEY,COM_FUNCTION.FUNCTIONID,");
            countbfr.append("COM_FUNCTION.SUBMENUID,COM_FUNCTION.DS_NO,COM_FUNCTION.BUTTONDISPORDER,COM_FUNCTION.BUTTONRESOURCEKEY,COM_FUNCTION.DOAUTHENTICATION_FLAG,COM_FUNCTION.URI,");
            countbfr.append("COM_FUNCTION.PAGENAMERESOURCEKEY,COM_FUNCTION.FRAMENAME,COM_FUNCTION.HIDDEN_FLAG");
            countbfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            countbfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            countbfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            countbfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            countbfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" AND COM_ROLE.ROLEID='" + menuRoleId + "'");
            countbfr.append(" AND COM_SUBMENU.MAINMENUID='" + mainMenuId + "'");
            countbfr.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            countbfr.append(" AND COM_ROLEFUNCTIONMAP.FUNCTIONID IN");
            countbfr.append("(");
            countbfr.append(" SELECT COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            countbfr.append(" WHERE COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            countbfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            countbfr.append(" AND COM_SUBMENU.MAINMENUID=COM_MAINMENU.MAINMENUID");
            countbfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" AND COM_ROLE.ROLEID='" + terminalRoleId + "'");
            countbfr.append(" AND COM_FUNCTION.HIDDEN_FLAG=0");
            countbfr.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            countbfr.append(" )");
            countbfr.append(" ORDER BY COM_SUBMENU.SUBMENUDISPORDER,COM_FUNCTION.BUTTONDISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + ":::" + countbfr.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(countbfr.toString());

            // move the result set to last row			
            if (!resultset.next())
            {
                // if no records are available, return null
                return null;
            }

            // submenfunction array
            resultList = new ArrayList();
            // function array for each submenu
            List functionList = null;
            String tempSubmenuId = "";

            // get the first submenuId.. ResultSet Cursor is moved to first row during null check
            String submenuId = resultset.getString(EmTableColumns.SUBMENU_SUBMENUID);

            while (numberOfRows >= tempRow)
            {
                SubmenuFunction submenuFunction = new SubmenuFunction();
                tempSubmenuId = submenuId;
                submenuFunction.setSubMenuId(submenuId);
                submenuFunction.setSubMenuDisplayOrder(resultset.getInt(EmTableColumns.SUBMENU_SUBMENUDISPORDER));
                submenuFunction.setSubMenuResourceKey(resultset.getString(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY));
                submenuFunction.setMainMenuId(resultset.getString(EmTableColumns.SUBMENU_MAINMENUID));

                functionList = new ArrayList();
                //iterate the loop until submenu ids or get all the functions for the same submenu
                while ((tempSubmenuId.equals(submenuId)) && numberOfRows >= tempRow)
                {
                    Function function = new Function();
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

                    functionList.add(function);

                    if (numberOfRows > tempRow)
                    { // get the next result or move the ResultSet Cursor to next position
                        resultset.next();
                        submenuId = resultset.getString(EmTableColumns.SUBMENU_SUBMENUID);
                    }
                    // increment the row count
                    tempRow++;
                }
                // add the function array to SubmenuFucntion entity
                if (functionList.size() > 0)
                {
                    Function functionEntity[] = (Function[])functionList.toArray(new Function[functionList.size()]);
                    submenuFunction.setFunctionArray(functionEntity);
                }
                // add the submenufunction to array list 
                resultList.add(submenuFunction);
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
        SubmenuFunction submenuFunctionEntity[] =
                (SubmenuFunction[])resultList.toArray(new SubmenuFunction[resultList.size()]);
        return submenuFunctionEntity;
    }

    /**
     * <jp>メインメニューIDとメインロールIDと端末ロールIDでデータベースから利用できる番号を検索します。 <br></jp>
     * <en>Finds number of available records for given mainmenuId, Mainrole id
     * and terminal role id</en>
     * 
     * @param mainMenuId
     *            <en>mainMenuId</en>
     * @param mainRoleId
     *            <en>Main Login user role Id</en>
     * @param terminalRoleId
     *            <en>Terminal role Id</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountByRoles(String mainMenuId, String mainRoleId, String terminalRoleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            StringBuffer countbfr = new StringBuffer();
            countbfr.append("SELECT COUNT(1) COUNT");
            countbfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            countbfr.append(" WHERE  COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            countbfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            countbfr.append(" AND COM_MAINMENU.MAINMENUID=COM_SUBMENU.MAINMENUID");
            countbfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" AND COM_ROLE.ROLEID='" + mainRoleId + "'");
            countbfr.append(" AND COM_SUBMENU.MAINMENUID='" + mainMenuId + "'");
            countbfr.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            countbfr.append(" AND COM_ROLEFUNCTIONMAP.FUNCTIONID IN");
            countbfr.append("(");
            countbfr.append(" SELECT COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" FROM COM_MAINMENU,COM_SUBMENU,COM_FUNCTION,COM_ROLE,COM_ROLEFUNCTIONMAP");
            countbfr.append(" WHERE COM_ROLE.ROLEID=COM_ROLEFUNCTIONMAP.ROLEID");
            countbfr.append(" AND COM_SUBMENU.SUBMENUID=COM_FUNCTION.SUBMENUID");
            countbfr.append(" AND COM_SUBMENU.MAINMENUID=COM_MAINMENU.MAINMENUID");
            countbfr.append(" AND COM_FUNCTION.FUNCTIONID=COM_ROLEFUNCTIONMAP.FUNCTIONID");
            countbfr.append(" AND COM_ROLE.ROLEID='" + terminalRoleId + "'");
            countbfr.append(" AND COM_FUNCTION.HIDDEN_FLAG=0");
            countbfr.append(" AND NOT COM_SUBMENU.SUBMENUDISPORDER ='-1'");
            countbfr.append(" )");
            countbfr.append(" ORDER BY COM_SUBMENU.SUBMENUDISPORDER,COM_FUNCTION.BUTTONDISPORDER");

            EmLog4jLogger.sqlFind(this.getClass().getName() + ":::" + countbfr.toString());
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(countbfr.toString());
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
}
