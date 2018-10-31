// $Id: OperationLogHandler.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.OperationLog;


/**
 * <jp>操作ログテーブルに関する様々なメソッドのinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to access and update OperationLog data.<br></en>
 * 
 * @author $Author: Muneendra
 */

public interface OperationLogHandler
{
    /**
     * オペレーションログの登録を行います。
     * @param operationLog オペレーションログエンティティ
     * @return 登録件数
     * @throws SQLException
     */
    public int createOperationLog(OperationLog operationLog)
            throws SQLException;

    /**
     *  <jp> <br></jp>
     * <en>Finds the number of records in Operation Log for given conditions.</en>
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
    public int findCountOperationLog(String tableName, String startDate, String endDate, String userId,
            String dsNumber, String dateFormat)
            throws SQLException;

    /**
     * <jp> <br></jp>
     * <en>This method returns all Operation log information for given conditions</en>
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param startRow :<en> Start record. Should be >0</en>
     * @param endRow :<en> End record. </en>
     * @param dateFormat :<en> date format </en>
     * @return OperationLog[]: <en>Returns Accesslog data as AccessLog entity array. Returns
     *         null If no data found</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public OperationLog[] findOperationLog(String tableName, String startDate, String endDate, String userId,
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
