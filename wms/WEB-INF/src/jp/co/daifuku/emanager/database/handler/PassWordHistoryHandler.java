// $Id: PassWordHistoryHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.LogInfo;

/**
 * <jp>パスワードハンドラに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle Password History
 * related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface PassWordHistoryHandler
{
    /**
     * <jp>パスワードクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given password change log infomration in data
     * base.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createPasswordHistory(LogInfo logInfo)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method fetches LogInfo information from database for a given
     * userId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return LogInfo[] : <en>Returns LogInfo Information as LogInfo Entity for
     *         given userID.<Returns null If no data found/en>
     * @throws SQLException :
     *             <en>if a database access error occurs. </en>
     */
    public LogInfo[] findByUserId(String userId)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method Methid returns the last updated data for a given user iDuserId</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @return LogInfo: <en>Last updated data/en>
     * @throws SQLException :
     *             <en>if a database access error occurs. </en>
     */
    public LogInfo findLastPwdChangeDateByUserId(String userId)
            throws SQLException;


    /**
     * <jp>全てのパスワード変更履歴とそれに関連するデータを検索し、ログの配列を返却します。 <br></jp>
     * <en>This method returns all password change and related data.</en>
     * 
     * @return LogInfo[]: <en>Returns password change data as LogInfo entity
     *         array. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAll()
            throws SQLException;


    /**
     * <jp>ユーザIDで、データベースからパスワードに関するデータを削除します。 <br></jp>
     * <en>This method deletes Password Change information for a given userId
     * based on COM_AUTHENTICATION PASSWORDLOGCHECKTIME.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByUserIdAuth(String userId)
            throws SQLException;

    /**
     * <jp>ユーザIDでデータベースからパスワード変更履歴を削除します。 <br></jp>
     * <en>This method deletes Password Change information for a given userId</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteByUserId(String userId)
            throws SQLException;

}
