// $Id: LogExpImpSetHandler.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.LogExpImpSet;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ログエクスポート インポート設定テーブルに関するデータを扱う様々なメソッドを提供します。

 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public interface LogExpImpSetHandler
{

    /**
     * <jp>LogExpImpSet情報を取得します。。<br>
     * </jp> <en>This method fetches LogExpImpSet info <br>
     * </en>
     * 
     * @return LogExpImpSet LogExpImpSet:
     *         <en>LogExpImpSet info. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public LogExpImpSet[] findAll()
            throws SQLException;

    /**
     * <jp>マスタ用のLogExpImpSet情報を取得します。。<br>
     * </jp> <en>This method fetches all LogExpImpSet records for given master flag condition<br>
     * </en>
     * 
     * @return LogExpImpSet LogExpImpSet: <en>LogExpImpSet info. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public LogExpImpSet[] findMasterData()
            throws SQLException;

    /**
     * <jp>LogExpImpSetのインポート用情報を更新します。<br>
     * </jp>
     * 
     * @param setting
     * @return 更新件数
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public int updateImportInfo(LogExpImpSet setting)
            throws SQLException;

    /**
     * LogExpImpSetのエクスポート用情報を更新します。
     * 
     * @param setting
     * @return 更新件数
     * @throws SQLException
     */
    public int updateExportInfo(LogExpImpSet setting)
            throws SQLException;


}
