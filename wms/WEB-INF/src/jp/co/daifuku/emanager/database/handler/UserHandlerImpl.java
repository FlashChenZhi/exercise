// $Id: UserHandlerImpl.java 5376 2009-11-04 01:43:18Z yamashita $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <jp>ユーザハンドラクラスのinterfaceを実装します。 <br></jp>
 * <en>This class implements the UserHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class UserHandlerImpl
        extends AbstractHandler
        implements UserHandler
{

    /**
     * @param conn <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    UserHandlerImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>ユーザクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given user infomration in data base.</en>
     * 
     * @param userEntity :
     *            <en> User information which has to be persisted.</en>
     * @return int : <en>modified number of rows.</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createUser(User userEntity)
            throws SQLException
    {
        int result;
        try
        {
// 2008/12/08 K.Matsuda Start テーブル仕様変更に伴い、列名称を変更。列名を定数から取得するように修正。
            // Inset SQL statement
//            String sql =
//                    "INSERT INTO COM_LOGINUSER (USERID,PASSWORD,USERNAME,ROLEID,DUMMYPASSWORD_FLAG,USERSTATUS,PWDEXPIRES,PWDCHANGEINTERVAL,"
//                            + "LASTACCESSDATE,SAMEUSERLOGINMAX,FAILEDLOGINATTEMPTS,FAILEDCOUNT,FAILEDSTARTDATE,UPDATE_DATE,UPDATE_USER,UPDATE_TERMINAL,UPDATE_KIND,UPDATE_PROCESS,DELETESTATUS,DEPARTMENT,REMARK)"
//                            + " VALUES(?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,SYSDATE,?,?,?,?,?,?,?)";
            // Insert SQL statement
            StringBuffer sql = new StringBuffer();
            sql.append("insert into COM_LOGINUSER(");
            sql.append(EmTableColumns.LOGINUSER_USERID + ",");
            sql.append(EmTableColumns.LOGINUSER_PASSWORD + ",");
            sql.append(EmTableColumns.LOGINUSER_USERNAME + ",");
            sql.append(EmTableColumns.LOGINUSER_ROLEID + ",");
            sql.append(EmTableColumns.LOGINUSER_DUMMYPASSWORD_FLAG + ",");
            sql.append(EmTableColumns.LOGINUSER_USERSTATUS + ",");
            sql.append(EmTableColumns.LOGINUSER_PWDEXPIRES + ",");
            sql.append(EmTableColumns.LOGINUSER_PWDCHANGEINTERVAL + ",");
            sql.append(EmTableColumns.LOGINUSER_LASTACCESSDATE + ",");
            sql.append(EmTableColumns.LOGINUSER_SAMEUSERLOGINMAX + ",");
            sql.append(EmTableColumns.LOGINUSER_FAILEDLOGINATTEMPTS + ",");
            sql.append(EmTableColumns.LOGINUSER_FAILEDCOUNT + ",");
            sql.append(EmTableColumns.LOGINUSER_FAILEDSTARTDATE + ",");
            sql.append(EmTableColumns.LOGINUSER_DELETESTATUS + ",");
            sql.append(EmTableColumns.LOGINUSER_DELETE_DATE + ",");
            sql.append(EmTableColumns.LOGINUSER_DEPARTMENT + ",");
            sql.append(EmTableColumns.LOGINUSER_REMARK + ",");
            sql.append(EmTableColumns.LOGINUSER_UPDATE_DATE + ",");
            sql.append(EmTableColumns.LOGINUSER_UPDATE_USER + ",");
            sql.append(EmTableColumns.LOGINUSER_UPDATE_TERMINAL + ",");
            sql.append(EmTableColumns.LOGINUSER_UPDATE_KIND + ",");
            sql.append(EmTableColumns.LOGINUSER_UPDATE_PROCESS);
            sql.append(") ");
            sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?)");

            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            // create porepared statement
            super.preparedStatement = connection.prepareStatement(sql.toString());
            int i=1;
            // Set data
            preparedStatement.setString    (i++, userEntity.getUserID());
            preparedStatement.setString    (i++, userEntity.getPassword());
            preparedStatement.setString    (i++, userEntity.getUserName());
            preparedStatement.setString    (i++, userEntity.getRoleID());
            preparedStatement.setBoolean   (i++, userEntity.getDummyPassFlag());
            preparedStatement.setInt       (i++, userEntity.getUserStatus());
            preparedStatement.setTimestamp (i++, getSqlDate(userEntity.getPwdExpire()));
            preparedStatement.setInt       (i++, userEntity.getPwdChangeInterval());
            preparedStatement.setTimestamp (i++, getSqlDate(userEntity.getLastAccessDate()));
            preparedStatement.setInt       (i++, userEntity.getSameUserLoginMax ());
            preparedStatement.setInt       (i++, userEntity.getFailLoginAttem ());
            preparedStatement.setInt       (i++, userEntity.getFailedCount ());
            preparedStatement.setTimestamp (i++, getSqlDate(userEntity.getFailedStartDate ()));
            preparedStatement.setInt       (i++, userEntity.getDeleteStatus());
            preparedStatement.setTimestamp (i++, getSqlDate(userEntity.getDeleteDate()));
            preparedStatement.setString    (i++, userEntity.getDepartment());
            preparedStatement.setString    (i++, userEntity.getRemarks());
            preparedStatement.setString    (i++, userEntity.getUpdateUser ());
            preparedStatement.setString    (i++, userEntity.getUpdateTerminal ());
            preparedStatement.setInt       (i++, userEntity.getUpdateKind ());
            preparedStatement.setString    (i++, userEntity.getUpdateProcess ());
// 2008/12/08 K.Matsuda End

            // Create user information
            result = preparedStatement.executeUpdate();

            // if user information is not created throw exception
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
            throws SQLException
    {
        int result;
        try
        {
            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_LOGINUSER SET ");

            if (userEntity.getUserName() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_USERNAME + "='" + userEntity.getUserName() + "',");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_USERNAME + "=NULL,");
            }
            if (userEntity.getPassword() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_PASSWORD + "='" + userEntity.getPassword() + "',");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_PASSWORD + "=NULL,");
            }
            if (userEntity.getRoleID() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_ROLEID + "='" + userEntity.getRoleID() + "',");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_ROLEID + "=NULL,");
            }
            if (userEntity.getDummyPassFlag())
            {
                sql.append(EmTableColumns.LOGINUSER_DUMMYPASSWORD_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_DUMMYPASSWORD_FLAG + "=0,");
            }
            
// 2008/12/08 K.Matsuda Start テーブル列名変更に伴い、列名定数、型、getterを修正
            sql.append(EmTableColumns.LOGINUSER_USERSTATUS + "=" + userEntity.getUserStatus() + ",");
// 2008/12/08 K.Matsuda End
            
            if (userEntity.getPwdExpire() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_PWDEXPIRES + "=" + getDateFormat(userEntity.getPwdExpire()) + ",");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_PWDEXPIRES + "=NULL,");
            }

            sql.append(EmTableColumns.LOGINUSER_PWDCHANGEINTERVAL + "=" + userEntity.getPwdChangeInterval() + ",");

            if (userEntity.getLastAccessDate() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_LASTACCESSDATE + "="
                        + getDateFormat(userEntity.getLastAccessDate()) + ",");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_LASTACCESSDATE + "=NULL,");
            }
            sql.append(EmTableColumns.LOGINUSER_SAMEUSERLOGINMAX + "=" + userEntity.getSameUserLoginMax() + ",");
            sql.append(EmTableColumns.LOGINUSER_FAILEDLOGINATTEMPTS + "=" + userEntity.getFailLoginAttem() + ",");
            sql.append(EmTableColumns.LOGINUSER_FAILEDCOUNT + "=" + userEntity.getFailedCount() + ",");

            if (userEntity.getFailedStartDate() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_FAILEDSTARTDATE + "="
                        + getDateFormat(userEntity.getFailedStartDate()) + ",");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_FAILEDSTARTDATE + "=NULL,");
            }

            if (userEntity.getDepartment() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_DEPARTMENT + "='" + userEntity.getDepartment() + "'" + ",");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_DEPARTMENT + "=NULL,");
            }
            if (userEntity.getRemarks() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_REMARK + "='" + userEntity.getRemarks() + "'" + ",");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_REMARK + "=NULL,");
            }

            sql.append(EmTableColumns.LOGINUSER_UPDATE_DATE + "=SYSDATE,");

            if (userEntity.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_USER + "='" + userEntity.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_USER + "=NULL,");
            }

            if (userEntity.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_TERMINAL + "='" + userEntity.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.LOGINUSER_UPDATE_KIND + "=" + userEntity.getUpdateKind() + ",");

            if (userEntity.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_PROCESS + "='" + userEntity.getUpdateProcess() + "'");
            }
            else
            {
                sql.append(EmTableColumns.LOGINUSER_UPDATE_PROCESS + "=NULL");
            }

            // Where conditions
            sql.append(" WHERE USERID='" + userEntity.getUserID() + "'");
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            // create porepared statement
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
     * <jp>ユーザIDでデータベースからユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches user information from database for a given userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return EUser : <en>Returns user Information as User Entity for given
     *         userID. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User findByUserId(String userId)
            throws SQLException
    {

        User userEntity = null;
        try
        {

            String sql =
                    "SELECT * FROM COM_LOGINUSER WHERE USERID ='" + userId.trim() + "'" + " AND DELETESTATUS = "
                            + EmConstants.DELETESTATUS_EFFECTIVE;
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
            // set user data to user entity
            userEntity = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End
            
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
        return userEntity;

    }

    /**
     * <jp>ユーザIDでデータベースから削除済みのユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches user information from database for a given userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return EUser : <en>Returns user Information as User Entity for given
     *         userID. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User findByUserIdDeleted(String userId)
            throws SQLException
    {

        User userEntity = null;
        try
        {

            String sql =
                    "SELECT * FROM COM_LOGINUSER WHERE USERID ='" + userId.trim() + "'" + " AND DELETESTATUS = "
                            + EmConstants.DELETESTATUS_DELETE;
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
            // set user data to user entity
            userEntity = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End

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
        return userEntity;

    }

    /**
     * <jp>ユーザID、状態、ロールIDでデータベースからユーザを検索し、それを返却します。 <br></jp>
     * <en>This method fetches user information from database for a given userId,lockstatus,roleid</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @param userStatus :
     *            <en> userStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return User : <en>Returns user Information as User Entity for given
     *         userID. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
    public User findByUserStatusRoleId(String userId, int userStatus, String roleId)
            throws SQLException
// 2008/12/08 K.Matsuda End
    {

        User userEntity = null;
        try
        {
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
            String sql =
                    "SELECT * FROM COM_LOGINUSER WHERE USERID ='" + userId.trim() + "' AND USERSTATUS=" + userStatus
                            + " AND " + "ROLEID = '" + roleId + "'" + " AND DELETESTATUS = "
                            + EmConstants.DELETESTATUS_EFFECTIVE;
// 2008/12/08 K.Matsuda End
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }
            
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
            // set user data to user entity
            userEntity = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End

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
        return userEntity;

    }


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
     * @return MainMenu[] : <en>Returns User array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public User[] findUserLike(String userId, int startRow, int endRow)
            throws SQLException
    {

        User[] userEntity = null;
        try
        {
// 2009/07/09 T.Kajiwara Mod 追加したDAIFUKU_PRIVATE_USERを対象外に変更
            String sql =
                    "SELECT * FROM( SELECT COM_LOGINUSER.*,ROW_NUMBER() OVER(ORDER BY USERID ASC) RN FROM COM_LOGINUSER WHERE USERID LIKE '"
                            + userId
                            + "' AND NOT USERID='"
                            + EmConstants.DAIFUKU_SV_USER
                            + "' AND NOT USERID='"
                            + EmConstants.DAIFUKU_PRIVATE_USER
                            + "' AND DELETESTATUS="
                            + EmConstants.DELETESTATUS_EFFECTIVE
                            + " ORDER BY USERID ) WHERE RN BETWEEN "
                            + startRow
                            + " AND " + endRow;
// 2009/07/09 T.Kajiwara End
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
                // set user data to user entity
                User userResult = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End
                
                list.add(userResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                userEntity = new User[size];
                userEntity = (User[])list.toArray(userEntity);
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
        return userEntity;

    }

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
            throws SQLException
    {

        User[] userEntity = null;
        try
        {

            String sql =
                    "SELECT * FROM( SELECT COM_LOGINUSER.*,ROW_NUMBER() OVER(ORDER BY USERID ASC)"
                            + " RN FROM COM_LOGINUSER WHERE ROLEID!= '" + EmConstants.ADMIN_ROLE
                            + "' AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE
                            + " ORDER BY USERID ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
                // set user data to user entity
                User userResult = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End
                
                list.add(userResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                userEntity = new User[size];
                userEntity = (User[])list.toArray(userEntity);
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
        return userEntity;

    }

    /**
     * <jp>ユーザID入力でワイルドカード検索し、該当するユーザを配列で返却します。 <br></jp>
     * <en> This method finds the user records where user id is like given input
     * parameter. </en>
     * 
     * @param userId :
     *            <en> userId </en>
     * @param userStatus :
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
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
    public User[] findUserLikeUserStatusRoleId(String userId, int userStatus, String roleId, int startRow, int endRow)
            throws SQLException
// 2008/12/08 K.Matsuda End
    {
        User[] userEntity = null;
        try
        {

// 2009/07/09 T.Kajiwara Mod SQL count query
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM( SELECT COM_LOGINUSER.*,ROW_NUMBER() OVER(ORDER BY USERID ASC) RN FROM COM_LOGINUSER WHERE ");
            sql.append(EmTableColumns.LOGINUSER_USERID + " LIKE '" + userId + "'");
            sql.append(" AND NOT " + EmTableColumns.LOGINUSER_USERID + " = '" + EmConstants.DAIFUKU_SV_USER + "'");
            sql.append(" AND NOT " + EmTableColumns.LOGINUSER_USERID + " = '" + EmConstants.DAIFUKU_PRIVATE_USER + "'");
            sql.append(" AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE);
// 2009/07/09 T.Kajiwara End            
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
            if (userStatus != EmConstants.LOCKSTATUS_ALL)
            {
                sql.append(" AND " + EmTableColumns.LOGINUSER_USERSTATUS + " = " + userStatus);
            }
// 2008/12/08 K.Matsuda End
            
            if (!EmConstants.ROLE_ALL.equals(roleId))
            {
                sql.append(" AND " + EmTableColumns.LOGINUSER_ROLEID + " = '" + roleId + "'");
            }

            sql.append(" ORDER BY USERID ) WHERE RN BETWEEN ");
            sql.append(startRow + " AND " + endRow);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql.toString());

            List list = new ArrayList();

            while (resultset.next())
            {
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
                // set user data to user entity
                User userResult = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End
                
                list.add(userResult);
            }
            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                userEntity = new User[size];
                userEntity = (User[])list.toArray(userEntity);
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
        return userEntity;
    }

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
            throws SQLException
    {
        User userEntity[] = null;
        try
        {

            String sql =
                    "SELECT * FROM COM_LOGINUSER" + " WHERE DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE
                            + " ORDER BY USERID";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            this.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
                // set user data to user entity
                User userResult = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End

                list.add(userResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                userEntity = new User[size];
                userEntity = (User[])list.toArray(userEntity);
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
        return userEntity;
    }

    /**
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of records available for user where userId is like
     * given input</en>
     * 
     * @param userId :
     *            <en> submenuId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String userId)
            throws SQLException
    {
        int count;
        try
        {
// 2009/07/09 T.Kajiwara Mod SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE USERID LIKE '" + userId
                            + "' AND NOT USERID='" + EmConstants.DAIFUKU_SV_USER 
                            + "' AND NOT USERID='" + EmConstants.DAIFUKU_PRIVATE_USER
                            + "' AND DELETESTATUS=" + EmConstants.DELETESTATUS_EFFECTIVE;
// 2009/07/09 T.Kajiwara End            
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
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>returns the count of users who are not admins </en>

     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountAllNotAdmin()
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE ROLEID!='" + EmConstants.ADMIN_ROLE + "'"
                            + " AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE;

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
     * <jp>ユーザID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of records available for user where userId is like
     * given input</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @param userStatus :
     *            <en> lockStatus</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
    public int finCountLikeUserStatusRoleId(String userId, int userStatus, String roleId)
            throws SQLException
// 2008/12/08 K.Matsuda End
    {
        int count;
        try
        {
// 2009/07/09 T.Kajiwara Mod SQL count query
            StringBuffer sqlCount = new StringBuffer();
            sqlCount.append("SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE ");
            sqlCount.append(EmTableColumns.LOGINUSER_USERID + " LIKE '" + userId + "'");
            sqlCount.append(" AND NOT " + EmTableColumns.LOGINUSER_USERID + " = '" + EmConstants.DAIFUKU_SV_USER + "'");
            sqlCount.append(" AND NOT " + EmTableColumns.LOGINUSER_USERID + " = '" + EmConstants.DAIFUKU_PRIVATE_USER + "'");
            sqlCount.append(" AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE);
// 2009/07/09 T.Kajiwara End            
// 2008/12/08 K.Matsuda Start テーブル列名変更に併せて、名称変更
            if (userStatus != EmConstants.LOCKSTATUS_ALL)
            {
                sqlCount.append(" AND " + EmTableColumns.LOGINUSER_USERSTATUS + " = " + userStatus);
            }
// 2008/12/08 K.Matsuda End
            if (!EmConstants.ROLE_ALL.equals(roleId))
            {
                sqlCount.append(" AND " + EmTableColumns.LOGINUSER_ROLEID + " = '" + roleId + "'");
            }

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sqlCount.toString());
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
     * <jp>ロールID入力でデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of available user records for given roleId</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountByRoleId(String roleId)
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE ROLEID = '" + roleId + "'" + " AND DELETESTATUS = "
                            + EmConstants.DELETESTATUS_EFFECTIVE;
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
     * <jp>データベースから削除済みユーザの登録数を検索します。 <br></jp>
     * <en>returns the count of users who are not admins </en>

     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountAllDeletedUser()
            throws SQLException
    {
        int count;
        try
        {
            // SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE DELETESTATUS = " + EmConstants.DELETESTATUS_DELETE;

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
            throws SQLException
    {
        int result;
        try
        {
            String sql = "DELETE FROM COM_LOGINUSER WHERE USERID='" + userId.trim() + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete user from LoginUser table
            result = statement.executeUpdate(sql);
            super.closeStatment_Resultset();

            // if there are no records to be deleted throw exception
            if (result == 0)
            {
                return result;
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
            throws SQLException
    {
        int result;
        try
        {
            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_LOGINUSER SET ");

            sql.append(EmTableColumns.LOGINUSER_DELETESTATUS + "=" + EmConstants.DELETESTATUS_DELETE + ",");
            sql.append(EmTableColumns.LOGINUSER_DELETE_DATE + "=SYSDATE");

            // Where conditions
            sql.append(" WHERE USERID='" + userEntity.getUserID() + "'");
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            // create porepared statement
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
            throws SQLException
    {
        int result;
        try
        {
            String sql =
                    "DELETE FROM COM_LOGINUSER WHERE USERID ='" + userId.trim() + "' AND USERSTATUS=" + lockStatus
                            + " AND " + "ROLEID = '" + roleId + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete user from LoginUser table
            result = statement.executeUpdate(sql);
            super.closeStatment_Resultset();

            // if there are no records to be deleted throw exception
            if (result == 0)
            {
                return result;
            }
            // delete user information from UserAttribute table
            String userAttibuteSql = "DELETE FROM COM_USERATTRIBUTE WHERE USERID = '" + userId.trim() + "'";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + userAttibuteSql);
            super.statement = connection.createStatement();
            // delete user from LoginUser table
            result = statement.executeUpdate(userAttibuteSql);

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
            throws SQLException
    {
        int result;
        try
        {
            String sql =
                    "DELETE FROM COM_LOGINUSER WHERE DELETESTATUS = " + EmConstants.DELETESTATUS_DELETE + " AND "
                            + "DELETE_DATE + " + (sameUserCreateBlockPeriod - 1) + " < TRUNC(SYSDATE)";
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            // delete user from LoginUser table
            result = statement.executeUpdate(sql);
            super.closeStatment_Resultset();

            // if there are no records to be deleted throw exception
            if (result == 0)
            {
                return result;
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.UserHandler#isSameUserBlockPeriod(java.lang.String, int)
     */
    public boolean isSameUserBlockPeriod(String userId, int sameUserBlockPeriod)
            throws SQLException
    {
        boolean result = false;;
        try
        {
            String sql =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE USERID = '" + userId + "' AND DELETESTATUS = "
                            + EmConstants.DELETESTATUS_DELETE + " AND " + "DELETE_DATE + " + (sameUserBlockPeriod - 1)
                            + " >= TRUNC(SYSDATE)";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            this.resultset = statement.executeQuery(sql);
            resultset.next();

            int count = resultset.getInt("COUNT");
            if (count > 0)
            {
                result = true;
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
            throws SQLException
    {
        int result = 0;
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COM_AUTHENTICATION.PWDCHANGEINTERVAL SYSTEM ,COM_ROLE.PWDCHANGEINTERVAL ROLE ,COM_LOGINUSER.PWDCHANGEINTERVAL UESR ");
            sql.append("FROM COM_AUTHENTICATION ,COM_ROLE ,COM_LOGINUSER ");
            sql.append("WHERE COM_LOGINUSER.USERID = '" + userId + "' ");
            sql.append("AND COM_LOGINUSER.ROLEID = COM_ROLE.ROLEID AND COM_AUTHENTICATION.AUTHENTICATIONID = 1");
            sql.append(" AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE);

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql.toString());

            if (resultset.next())
            {
                int systemValue = resultset.getInt(1);
                int roleValue = resultset.getInt(2);
                int userValue = resultset.getInt(3);

                if (userValue == -2)
                {
                    if (roleValue == -2)
                    {
                        result = systemValue;
                    }
                    else
                    {
                        result = roleValue;
                    }
                }
                else
                {
                    result = userValue;
                }

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
            throws SQLException
    {
        int result = 0;
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COM_AUTHENTICATION.FAILEDLOGINATTEMPTS SYSTEM ,COM_ROLE.FAILEDLOGINATTEMPTS ROLE ,COM_LOGINUSER.FAILEDLOGINATTEMPTS UESR ");
            sql.append("FROM COM_AUTHENTICATION ,COM_ROLE ,COM_LOGINUSER ");
            sql.append("WHERE COM_LOGINUSER.USERID = '" + userId + "' ");
            sql.append("AND COM_LOGINUSER.ROLEID = COM_ROLE.ROLEID AND COM_AUTHENTICATION.AUTHENTICATIONID = 1");
            sql.append(" AND DELETESTATUS = " + EmConstants.DELETESTATUS_EFFECTIVE);
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql.toString());

            if (resultset.next())
            {
                int systemValue = resultset.getInt(1);
                int roleValue = resultset.getInt(2);
                int userValue = resultset.getInt(3);

                if (userValue == -2)
                {
                    if (roleValue == -2)
                    {
                        result = systemValue;
                    }
                    else
                    {
                        result = roleValue;
                    }
                }
                else
                {
                    result = userValue;
                }
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

    /* (non-Javadoc)
     * @see jp.co.daifuku.emanager.database.handler.UserHandler#findCountGreaterThan(java.lang.String)
     */
    public int findCountGreaterThan(String userId)
            throws SQLException
    {
        int count;
        try
        {
// 2009/07/09 T.Kajiwara Mod SQL count query
            String sqlCount =
                    "SELECT COUNT(1) COUNT FROM COM_LOGINUSER WHERE "
            	            + "NOT USERID='" + EmConstants.DAIFUKU_SV_USER 
            	            + "' AND NOT USERID='" + EmConstants.DAIFUKU_PRIVATE_USER
                            + "' AND DELETESTATUS=" + EmConstants.DELETESTATUS_EFFECTIVE;
// 2009/07/09 T.Kajiwara End            
            if (userId != null && !userId.equals(""))
            {
                sqlCount = sqlCount + " AND USERID>='" + userId + "' ";
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
     * @see jp.co.daifuku.emanager.database.handler.UserHandler#findUserGreaterThan(java.lang.String, int, int)
     */
    public User[] findUserGreaterThan(String userId, int startRow, int endRow)
            throws SQLException
    {
        User[] userEntity = null;
        try
        {
// 2009/07/09 T.Kajiwara Mod 追加したDAIFUKU_PRIVATE_USERを対象外に変更
            String sql =
                    "SELECT * FROM( SELECT COM_LOGINUSER.*,ROW_NUMBER() OVER(ORDER BY USERID ASC) RN FROM COM_LOGINUSER WHERE "
                            + "NOT USERID='" + EmConstants.DAIFUKU_SV_USER
                            + "' AND NOT USERID='" + EmConstants.DAIFUKU_PRIVATE_USER
                            + "' AND DELETESTATUS=" + EmConstants.DELETESTATUS_EFFECTIVE;
// 2009/07/09 T.Kajiwara End
            if (userId != null && !userId.equals(""))
            {
                sql = sql + " AND USERID>='" + userId + "' ";
            }
            sql = sql + "ORDER BY USERID ) WHERE RN BETWEEN " + startRow + " AND " + endRow;

            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            List list = new ArrayList();

            while (resultset.next())
            {
// 2008/12/08 K.Matsuda Start Entityへのセットを1箇所にまとめました
                // set user data to user entity
                User userResult = makeUserEntity(resultset);
// 2008/12/08 K.Matsuda End

                list.add(userResult);
            }

            // If array size is zero .. no records
            int size = list.size();
            if (size != 0)
            {
                userEntity = new User[size];
                userEntity = (User[])list.toArray(userEntity);
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

        return userEntity;
    }

    /**
     * ユーザエンティティクラスをResultSetから生成します。
     * @param rset ResultSet
     * @return ユーザエンティティクラス
     * @throws SQLException DBエラー
     */
    private User makeUserEntity(ResultSet rset) throws SQLException
    {
        User entity = new User();
    
        // set user data to user entity
        entity.setUserID(rset.getString(EmTableColumns.LOGINUSER_USERID));
        entity.setPassword(rset.getString(EmTableColumns.LOGINUSER_PASSWORD));
        entity.setUserName(rset.getString(EmTableColumns.LOGINUSER_USERNAME));
        entity.setRoleID(rset.getString(EmTableColumns.LOGINUSER_ROLEID));
        entity.setDummyPassFlag(rset.getBoolean(EmTableColumns.LOGINUSER_DUMMYPASSWORD_FLAG));
        entity.setUserStatus(rset.getInt(EmTableColumns.LOGINUSER_USERSTATUS));
        entity.setPwdExpire(rset.getTimestamp(EmTableColumns.LOGINUSER_PWDEXPIRES));
        entity.setPwdChangeInterval(rset.getInt(EmTableColumns.LOGINUSER_PWDCHANGEINTERVAL));
        entity.setLastAccessDate(rset.getTimestamp(EmTableColumns.LOGINUSER_LASTACCESSDATE));
        entity.setSameUserLoginMax(rset.getInt(EmTableColumns.LOGINUSER_SAMEUSERLOGINMAX));
        entity.setFailLoginAttem(rset.getInt(EmTableColumns.LOGINUSER_FAILEDLOGINATTEMPTS));
        entity.setFailedCount(rset.getInt(EmTableColumns.LOGINUSER_FAILEDCOUNT));
        entity.setFailedStartDate(rset.getTimestamp(EmTableColumns.LOGINUSER_FAILEDSTARTDATE));
        entity.setDeleteStatus(rset.getInt(EmTableColumns.LOGINUSER_DELETESTATUS));
        entity.setDeleteDate(rset.getTimestamp(EmTableColumns.LOGINUSER_DELETE_DATE));
        entity.setDepartment(rset.getString(EmTableColumns.LOGINUSER_DEPARTMENT));
        entity.setRemarks(rset.getString(EmTableColumns.LOGINUSER_REMARK));
        entity.setUpdateDate(rset.getTimestamp(EmTableColumns.LOGINUSER_UPDATE_DATE));
        entity.setUpdateUser(rset.getString(EmTableColumns.LOGINUSER_UPDATE_USER));
        entity.setUpdateTerminal(rset.getString(EmTableColumns.LOGINUSER_UPDATE_TERMINAL));
        entity.setUpdateKind(rset.getInt(EmTableColumns.LOGINUSER_UPDATE_KIND));
        entity.setUpdateProcess(rset.getString(EmTableColumns.LOGINUSER_UPDATE_PROCESS));
        
        return entity;
    }
}
