// $Id: AccessLogHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.AccessLog;

/**
 * <jp>画面アクセスログテーブルに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to access and update AccesssLog data.<br></en>
 * 
 * @author $Author: Muneendra
 */
public interface AccessLogHandler
{
    /**
     * <jp>画面アクセスログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given ScreenAccess log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAccessLog(AccessLog logInfo)
            throws SQLException;

    /**
     *  <jp> <br></jp>
     * <en>Finds the number of records in Access Logfor given conditions.</en>
     * 
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param dateFormat :<en> Dateformat</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public int findCountAccessLog(String tableName, String startDate, String endDate, String userId, String dsNumber,
            String dateFormat)
            throws SQLException;


    /**
     * <jp> <br></jp>
     * <en>This method returns all Access log information for given conditions</en>
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param startRow :<en> Start record. Should be >0</en>
     * @param endRow :<en> End record. </en>
     * @param dateFormat :<en> date format </en>
     * @return AccessLog[]: <en>Returns Accesslog data as AccessLog entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public AccessLog[] findAccessLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, int startRow, int endRow, String dateFormat)
            throws SQLException;

    /**
     *  <jp> <br></jp>
     * <en>Finds the page name resource key for a given ds number</en>
     * 
     * @param dsNumber :<en> dsNumber</en>
     * @param tableName :<en> tableName</en>	 *
     * @return String :<en>Page name resource Key</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public String findPageNameByDSNum(String tableName, String dsNumber)
            throws SQLException;


}
