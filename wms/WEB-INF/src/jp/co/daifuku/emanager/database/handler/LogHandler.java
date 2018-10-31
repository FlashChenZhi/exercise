// $Id: LogHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.emanager.database.entity.LogInfo;

/**
 * <jp>ログテーブルに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to operan on Log tables.<br></en>
 * 
 * @author $Author: Muneendra
 */
public interface LogHandler
{

    /**
     * <jp>ユーザメンテナンスログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given user miantenance log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createMainteLog(LogInfo logInfo)
            throws SQLException;

    /**
     * <jp>認証システムログクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given authenticaltion log infomration in database.<br></en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAuthenticationLog(LogInfo logInfo)
            throws SQLException;

    /**
     * <jp>全てのメンテナンスログを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all maintenace Log information.<br></en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteLog()
            throws SQLException;

    /**
     * <jp>メンテナンスログの番号を検索し、返却します。 <br></jp>
     * <en>Finds the number of records in Maintenance log information.<br></en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteLog()
            throws SQLException;

    /**
     * <jp>全ての認証システムログを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all Authentication Log information.<br></en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthLog()
            throws SQLException;

    /**
     * <jp>認証システムログの番号を検索し、返却します。 <br></jp>
     * <en>Finds the number of records in Authentication log information.<br></en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthLog()
            throws SQLException;

    /**
     * <jp>メンテナンスログをデータベースから削除します。 <br></jp>
     * <en>This method deletes Maintenance log records upto given number.<br></en>
     * 
     * @param rowNum :
     *            <en>number of records to delete</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMaitenLogByRownum(String rowNum)
            throws SQLException;

    /**
     * <en>This method deletes Maintenance log records between the given dates.<br></en>
     * 
     * @param fromDate
     *            <en>from Date</en>
     * @param toDate
     *            <en>to Date</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMaitenLogByDates(Date fromDate, Date toDate)
            throws SQLException;

    /**
     * <jp>認証システムログをデータベースから削除します。 <br></jp>
     * <en>This method deletes Authentication log records upto given number.<br></en>
     * 
     * @param rowNum :
     *            <en>number of records to delete</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthLogByRownum(String rowNum)
            throws SQLException;

    /**
     * <en>This method deletes Authentication log records between the given dates.<br></en>
     * 
     * @param fromDate
     *            <en>from Date</en>
     * @param toDate
     *            <en>to Date</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to delete</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthLogByDates(Date fromDate, Date toDate)
            throws SQLException;

}
