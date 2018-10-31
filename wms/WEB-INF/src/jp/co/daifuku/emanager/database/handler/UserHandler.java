// $Id: UserHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.User;

/**
 * <jp>ユーザハンドラクラスに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle User related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface UserHandler
{

    /**
     * <jp>ユーザクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given user infomration in data base.</en>
     * 
     * @param user :
     *            <en> User information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createUser(User user)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースからユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches user information from database for a given userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return User : <en>Returns user Information as User Entity for given
     *         userID.< Returns null If no data found/en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User findByUserId(String userId)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースから削除済みのユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches deleteduser information from database for a given userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return EUser : <en>Returns user Information as User Entity for given
     *         userID. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User findByUserIdDeleted(String userId)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースからユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches user information from database for a given userId,lockstatus,roleid</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @param lockStatus :
     *            <en> lockStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return User : <en>Returns user Information as User Entity for given
     *         userID. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User findByUserStatusRoleId(String userId, int lockStatus, String roleId)
            throws SQLException;

    /**
     * <jp>ユーザID入力でワイルドカード検索し、該当するユーザを配列で返却します。 <br></jp>
     * <en> This method finds the user records where user id is like given input
     * parameter. </en>
     * 
     * @param userId :
     *            <en> userId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return User[] : <en>Returns User array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User[] findUserLike(String userId, int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>ユーザID入力でワイルドカード検索し、該当するユーザを配列で返却します。 <br></jp>
     * <en> This method finds the user records where user id , 
     * loack status and role is like given input parameter. </en>
     * 
     * @param userId :
     *            <en> userId </en>
     * @param lockStatus :
     *            <en> lockStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return MainMenu[] : <en>Returns User array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User[] findUserLikeUserStatusRoleId(String userId, int lockStatus, String roleId, int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>returns all users info who are not admins</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return User[] : <en>Returns User array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User[] findAllNotAdmin(int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>ユーザID以降のデータを検索し、該当するユーザを配列で返却します。 <br></jp>
     * 
     * @param userId :
     *            <en> userId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return User[] : <en>Returns User array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User[] findUserGreaterThan(String userId, int startRow, int endRow)
            throws SQLException;
    
    /**
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of records available for user whereuser id , 
     * loack status and role  is like given input</en>
     * @param userId :
     *            <en> userId</en>
     * @param lockStatus :
     *            <en> lockStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLikeUserStatusRoleId(String userId, int lockStatus, String roleId)
            throws SQLException;

    /**
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>returns the count of users who are not admins </en>

     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountAllNotAdmin()
            throws SQLException;


    /**
     * <jp>データベースからユーザ情報を検索し、該当するものを配列として返却します。 <br></jp>
     * <en>This method returns all Users and related data.</en>
     * 
     * @return User[]: <en>Returns user data as user entity array. Returns null
     *         If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public User[] findAll()
            throws SQLException;


    /**
     * <jp>ユーザIDでデータベースを検索し、該当するものを修正します。 <br></jp>
     * <en>This method modifies the user information .</en>
     * 
     * @param userEntity :
     *            <en> User information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByUserId(User userEntity)
            throws SQLException;

    /**
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of records available for user where userId is like
     * given input</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String userId)
            throws SQLException;

    /**
     * <jp>ロールID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of available user records for given roleId</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountByRoleId(String roleId)
            throws SQLException;

    /**
     * <jp>データベースから削除済みユーザの登録数を検索します。 <br></jp>
     * <en>returns the count of users who are not admins </en>

     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountAllDeletedUser()
            throws SQLException;
    
    /**
     * ユーザID以降の登録数を検索します。
     * @param userId ユーザID
     * @return int :登録数
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountGreaterThan(String userId)
            throws SQLException;
    
    /**
     * <jp>ユーザID入力で該当するユーザをデータベースから削除します。 <br></jp>
     * <en>This method deletes user infomration for given userId.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during user creation.</en>
     */
    public int deleteUser(String userId)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースを検索し、該当するものの削除ステータスを修正します。 <br></jp>
     * <en></en>
     * 
     * @param userEntity :
     *            <en> </en>
     * @return int : <en></en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int deleteStatusuByUserId(User userEntity)
            throws SQLException;

    /**
     * <jp>ユーザID入力で該当するユーザをデータベースから削除します。 <br></jp>
     * <en>This method deletes user infomration for given userId,lockStatus and Roleid.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     
     * @param lockStatus :
     *            <en> lockStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during user creation.</en>
     */

    public int deleteByUserIdStatusRole(String userId, int lockStatus, String roleId)
            throws SQLException;

    /**
     * <jp>同一ユーザ作成禁止期間を過ぎたユーザをデータベースから削除します。 <br></jp>
     * 
     * @param sameUserCreateBlockPeriod :
     *            <en>sameUserCreateBlockPeriod</en>	
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during user creation.</en>
     */
    public int deleteUsers(int sameUserCreateBlockPeriod)
            throws SQLException;

    /**
     * 指定されたユーザIDが同一ユーザ作成禁止期間中のユーザかどうかを判定します。
     * 
     * @param userId ユーザID
     * @param sameUserBlockPeriod 同一ユーザ作成禁止期間
     * @return trueの場合、同一ユーザ作成禁止期間中
     * @throws SQLException
     */
    public boolean isSameUserBlockPeriod(String userId, int sameUserBlockPeriod)
            throws SQLException;

    /**
     * <jp>システム設定、ロール設定、ユーザ設定より実際のパスワード更新間隔を取得します。<br></jp>
     * <en>Returns the real value of password change interval by system, role and user settings.<br></en>
     * @param userId : <jp>ユーザID</jp>
     *                  <en>userId</en>
     *            
     * @return Real value of password change interval.
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during user creation.</en>
     */
    public int getRealPwdChangeIntervaluByUserId(String userId)
            throws SQLException;


    /**
     * <jp>システム設定、ロール設定、ユーザ設定より実際の認証ミス猶予回数を取得します。<br></jp>
     * <en>Returns the real value of failed login attempts by system, role and user settings.<br></en>
     * @param userId : <jp>ユーザID</jp>
     *                  <en>userId</en>
     * @return Real value of failed login attempts.
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during user creation.</en>
     */
    public int getRealFailedLoginAttempts(String userId)
            throws SQLException;

}
