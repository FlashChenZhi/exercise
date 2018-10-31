// $Id: SubmenuFunctionHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.SubmenuFunction;

/**
 * <jp>サブメニューファンクションクラスに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle SubMenu and
 * Function table related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface SubmenuFunctionHandler
{

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
            throws SQLException;

    /**
     * <jp>メインメニューIDとメインロールIDと端末ロールIDでサブメニューファンクションを検索し、その配列を返却します。 <br></jp>
     * <en>This method selects all submenus and Functions information for given
     * mainmenuId, Mainrole id and terminal role id</en>
     * 
     * @param mainMenuId
     *            <en>mainMenuId</en>
     * @param mainRoleId
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
    public SubmenuFunction[] findFunctionsByRoles(String mainMenuId, String mainRoleId, String terminalRoleId)
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

}
