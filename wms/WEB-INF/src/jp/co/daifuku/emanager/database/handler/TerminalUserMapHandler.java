// $Id: TerminalUserMapHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.Terminal;

/**
 * <jp>端末ユーザマップハンドラに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle TerminalUserMap
 * related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface TerminalUserMapHandler
{

    /**
     * <jp>端末ユーザマップクラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given TerminalUserMap infomration.</en>
     * 
     * @param terminalInfo :
     *            <en> TerminalUserMap information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal info
     *             creation.</en>
     */
    public int createTerminalUserMap(Terminal terminalInfo)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースを検索し、端末情報の配列を返却します。 <br></jp>
     * <en>This method fetches TerminalUserMap information from database for a
     * given terminal number</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return Terminal[] : <en>Returns terminal Information. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findByTerminalNumber(String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースを検索し、登録数を返却します。 <br></jp>
     * <en>Finds the number of records for a given terminal Number.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any database access error .</en>
     */
    public int findCountByTerminalNumber(String terminalNumber)
            throws SQLException;

    /**
     * <jp>ユーザIDと端末番号でデータベースを検索し、登録数を返却します。 <br></jp>
     * <en>Finds the number of records for a given UserId and terminal Number.</en>
     * 
     * @param userId :
     *            <en> userId</en>
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any database access error .</en>
     */
    public int findCountByUserIdTerminalNumber(String userId, String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースから該当する端末ユーザマップ情報を削除します。 <br></jp>
     * <en>This method deletes TerminalUserMap infomration for given
     * terminalNuber.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteTerminalUserMap(String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースから該当する端末ユーザマップ情報を削除します。 <br></jp>
     * <en>This method deletes TerminalUserMap infomration for given
     * userId.</en>
     * 
     * @param userId :
     *            <en>userId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteByUserId(String userId)
            throws SQLException;
}
