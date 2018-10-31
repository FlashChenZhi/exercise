// $Id: SubMenuHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.SubMenu;

/**
 * <jp>サブメニュークラスに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle SubMenu related
 * data</en>
 * 
 * @author $Author: Muneendra
 */
public interface SubMenuHandler
{
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
            throws SQLException;

    /**
     * <jp>サブメニューIDでデータベースから検索し、サブメニューを返却します。 <br></jp>
     * <en>This method selects all SubMenu information for a given subMenuId</en>
     * 
     * @param subMenuId :
     *            <en> submenu id </en>
     * @return SubMenu : <en>Returns SubMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public SubMenu findBySubMenuId(String subMenuId)
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

}
