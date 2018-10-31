// $Id: MainMenuHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.MainMenu;

/**
 * <jp>メインメニューに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle Menu related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface MainMenuHandler
{

    /**
     * <jp>メインメニュークラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given main menu infomration.</en>
     * 
     * @param mainMenu :
     *            <en> Main menu information to be persisted.</en>
     * @return int : <en>inserted number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during creation.</en>
     */
    public int createMainMenu(MainMenu mainMenu)
            throws SQLException;

    /**
     * <jp>URIでメニューIDを検索し、返却します。 <br></jp>
     * <en>This method fetches MenuID for given URI </en>
     * 
     * @param uri :
     *            <en> uri</en>
     * @return String : <en>Returns Menu id. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public String findMenuIdByUri(String uri)
            throws SQLException;

    /**
     * <jp>メインメニューIDでメインメニューを検索し、返却します。 <br></jp>
     * <en>This method selects all MainMenu information for a given MainMenuId</en>
     * 
     * @param menuId :
     *            <en> menu id </en>
     * @return MainMenu : <en>Returns EMainMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public MainMenu findByMenuId(String menuId)
            throws SQLException;

    /**
     * <jp>ロールIDでメインメニューを検索し、その配列を返却します。 <br></jp>
     * <en>This method selects all MainMenu information for a given roleId</en>
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
            throws SQLException;

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
            throws SQLException;

    /**
     * <jp>利用できるメニューIDを検索し、メインメニューの配列を返却します。 <br></jp>
     * <en>This method returns all the MenuIds available.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By MAINMENUID 1: Order By MENUDISPORDER</en>
     * @return MainMenu[]: <en>Returns MainMenu ids as a MainMenu entity array.
     *         Returns null If no data found</en>*
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public MainMenu[] findAll(int orderBy)
            throws SQLException;

    /**
     * <jp>メインメニューの情報を修正します。 <br></jp>
     * <en>This method modifies Main Menu information based on menu id.</en>
     * 
     * @param mainMenu :
     *            <en> Main Menu information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByMainMenuId(MainMenu mainMenu)
            throws SQLException;

    /**
     * <jp>データベースからメインメニュー番号を検索します。 <br></jp>
     * <en>Finds the number of records in MainMenu table.</en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCount()
            throws SQLException;

    /**
     * <jp>利用できるメインメニューIDを検索し、返却します。 <br></jp>
     * <en>Finds the number of records available from MainMenu where mainmenuid
     * is like given input</en>
     * 
     * @param mainMenuId :
     *            <en> mainMenuId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String mainMenuId)
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;
    
    /**
     * メインメニューIDで以降検索を行い、登録数を取得します。
     * 
     * @param menuId :
     *            <en> menu id </en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountGreaterThan(String menuId)
            throws SQLException;
    
    /**
     * メインメニューIDで以降検索を行いエンティティを返します。

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
    public MainMenu[] findGreaterThan(String menuId, int startRow, int endRow)
            throws SQLException;

}
