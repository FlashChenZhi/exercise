// $Id: RoleFunctionMapHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.Role;

/**
 * <jp>ロールファンクションマップハンドラクラスのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle RoleFunction map
 * related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface RoleFunctionMapHandler
{
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
            throws SQLException;

    /**
     * <jp>ユーザIDで端末ユーザマップを検索し、ロールの配列を返却します。 <br></jp>
     * <en>This method fetches TerminalUserMap information from database for a
     * given userId</en>
     * 
     * @param roleId :
     *            <en> roleId</en>
     * @return Role : <en>Returns Role Information as Role Entity for given
     *         roleId. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role[] findByRoleId(String roleId)
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

}
