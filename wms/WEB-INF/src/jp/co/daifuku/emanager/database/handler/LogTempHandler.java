// $Id: LogTempHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;
import java.sql.Timestamp;

import jp.co.daifuku.emanager.database.entity.LogInfo;

/**
 * <jp>ログテンプレートに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to operan on Log_Temp Tables</en>
 * 
 * @author $Author: Muneendra
 */
public interface LogTempHandler
{

    /**
     * <jp>認証システムログのテンプレートクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given Auth log temp infomration in data base.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createAuthLogTemp(LogInfo logInfo)
            throws SQLException;

    /**
     * <jp>メンテナンスログのテンプレートクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given maitenance log temp infomration in database.</en>
     * 
     * @param logInfo :
     *            <en> Log information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during user creation.</en>
     */
    public int createMainteLogTemp(LogInfo logInfo)
            throws SQLException;

    /**
     * <jp>distinctデータ、ユーザ、およびプロセスに基づく情報の配列を認証システムログに返します。 <br></jp>
     * <en>This method returns Authentication Log information based on distinct date,user and process.</en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthLogbyDistinct()
            throws SQLException;

    /**
     * <jp>distinctデータ、ユーザ、およびプロセスに基づく情報の配列をメンテナンスログテンプレートに返却します。 <br></jp>
     * <en>This method returns COM_MAINTELOG_TEMP Log information based on
     * distinct date,user and process.</en>
     * 
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteLogbyDistinct()
            throws SQLException;

    /**
     * <jp>全ての認証ログテンプレートを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all AuthLogTemp information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllAuthTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException;

    /**
     * <jp>認証ログテンプレートから番号を検索します。 <br></jp>
     * <en>Finds the number of records in AuthLogTemp for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException;

    /**
     * <jp>全てのメンテナンスログテンプレートを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all MaintenLog information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAllMainteTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass,
            int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>メンテナンスログテンプレートを検索し、その番号を返却します。 <br></jp>
     * <en>Finds the number of records in MaintenLog for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteTemp(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException;

    /**
     * <jp>認証ログと認証ログテンプレートを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns Union of data related to AuthLog and AuthLogTemp
     * information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findAuthView(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException;

    /**
     * <jp>認証ログを検索し、その番号を返却します。 <br></jp>
     * <en>Finds the number of records in AuthLog View for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountAuthView(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException;

    /**
     * <jp>メンテナンスログとメンテナンスログテンプレートを検索し、その配列を返却します。 <br></jp>
     * <en>This method returns Union of data related to MaintanenceLog and
     * MaintanenceLogTemp information for given conditions</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record. </en>
     * @return LogInfo[]: <en>Returns Log data as LogInfo entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public LogInfo[] findMainteView(Timestamp startDate, Timestamp endDate, String userId, int logClass, int startRow,
            int endRow)
            throws SQLException;

    /**
     * <jp>メンテナンスビューを検索し、その番号を返却します。 <br></jp>
     * <en>Finds the number of records from MaintenView for given conditions.</en>
     * 
     * @param startDate :
     *            <en> startDate</en>
     * @param endDate :
     *            <en> endDate</en>
     * @param userId :
     *            <en> userId</en>
     * @param logClass :
     *            <en> logClass</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountMainteView(Timestamp startDate, Timestamp endDate, String userId, int logClass)
            throws SQLException;

    /**
     * <jp>全ての認証ログテンプレートをデータベースから削除します。 <br></jp>
     * <en>This method deletes all records.</en>
     * 
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAllAuthTemp()
            throws SQLException;

    /**
     * <jp>認証ログテンプレートのファイル名をデータベースから削除します。 <br></jp>
     * <en>This method deletes deletes the records for a given fileName</en>
     * 
     * @param fileName :
     *            <en>fileName</en>
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAuthTempByFileName(String fileName)
            throws SQLException;

    /**
     * <jp>全てのメンテナンスログテンプレートをデータベースから削除します。 <br></jp>
     * <en>This method deletes all records.</en>
     * 
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteAllMainteTemp()
            throws SQLException;

    /**
     * <jp>メンテナンスログテンプレートのファイル名をデータベースから削除します。 <br></jp>
     * <en>This method deletes deletes the records for a given fileName</en>
     * 
     * @param fileName :
     *            <en>fileName</en>
     * @return int : <en>Returns deleted number of records.</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteMainteTempByFileName(String fileName)
            throws SQLException;
}
