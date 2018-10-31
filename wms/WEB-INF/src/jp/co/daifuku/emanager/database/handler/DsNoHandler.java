// $Id: DsNoHandler.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;
import jp.co.daifuku.emanager.database.entity.DsNo;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * <jp>DS番号を取得するためのinterfaceです。 <br></jp>
 * 
 * @author $Author: T.Ogawa
 */
public interface DsNoHandler
{


    /**
     *  <jp> <br></jp>
     * <en>Finds the number of ds no records for given conditions.</en>
     * 
     * @param tableName :<en> tableName</en>
     * @param startDate :<en> startDate</en>
     * @param endDate :<en> endDate</en>
     * @param userId :<en> userId</en>
     * @param dsNumber :<en> dsNumber</en>
     * @param dateFormat :<en> Dateformat</en>
     * @param isLikeSearch : <en> isLikeSearch</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :<en>for any other database error.</en>
     */
    public int findCountDsNo(String tableName, String startDate, String endDate, String userId, String dsNumber,
            String dateFormat, boolean isLikeSearch)
            throws SQLException;

    /**
     * <jp>テーブルに応じたDS番号の配列を返却します。 <br></jp>
     * 
     * @param table テーブル名
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @param userId ユーザID
     * @param dsNumber :<en> dsNumber</en>
     * @param startRow :<en> Start record. Should be >0</en>
     * @param endRow :<en> End record. </en>
     * @param dateFormat :<en> date format </en>
     * @param isLikeSearch : <en> isLikeSearch</en>
     * @return DsNo <en>Returns DsNo List.</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public DsNo[] findDsNo(String table, String startDate, String endDate, String userId, String dsNumber,
            int startRow, int endRow, String dateFormat, boolean isLikeSearch)
            throws SQLException;

}
