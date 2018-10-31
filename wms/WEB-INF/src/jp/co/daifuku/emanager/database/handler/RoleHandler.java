// $Id: RoleHandler.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>ロールハンドラクラスに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle Role related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface RoleHandler
{

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
            throws SQLException;

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
            throws SQLException;

    /**
     * <jpロールIDでデータベースからロール情報を検索し、返却します。<br></jp>
     * <en>This method selects all Role information for a given RoleId</en>
     * 
     * @param roleId :
     *            <en> role id </en>
     * @return Role : <en>Returns EMainMenu entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Role findByRoleId(String roleId)
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

    /**
     * <jp>データベースに登録されているロールの登録数を検索します。 <br></jp>
     * <en>Finds the number of records in Role table.</en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCount()
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

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
            throws SQLException;

    /**
     * 指定したロールの種類以外でロールIDの以降検索を行い登録数を取得します。
     *
     * @param roleId ロールID
     * @param notTarget 対象外にするロールの種類
     *           
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountGreaterThanAndNotTarget(String roleId, int notTarget)
            throws SQLException;
    
    /**
     * ロールIDの以降検索を行い登録数を取得します。
     *
     * @param roleId ロールID
     *           
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountGreaterThan(String roleId)
            throws SQLException;
    
    /**
     * 指定したロールの種類以外でロールIDの以降検索を行いデータをエンティティとして取得します。
     * 
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
    public Role[] findRoleGreaterThaAndNotTarget(String roleId, int startRow, int endRow, int target)
            throws SQLException;
    
    /**
     * ロールIDの以降検索を行いデータをエンティティとして取得します。
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
    public Role[] findRoleGreaterThan(String roleId, int startRow, int endRow)
            throws SQLException;
}
