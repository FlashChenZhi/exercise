// $Id: TerminalChangeMapHandler.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>端末変更マップクラスに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle TerminalChangeMap
 * related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface TerminalChangeMapHandler
{
    /**
     * <jp>端末変更クラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given terminal infomration in termnal Change
     * maptable.</en>
     * 
     * @param terminalInfo :
     *            <en> Terminal change information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal change
     *             info creation.</en>
     */
    public int createTerminalChangeMap(Terminal terminalInfo)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースから端末情報を検索し、その配列を返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal number</en>
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
     * <en>Finds total number of records available for a given Terminal Number
     * </en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>i
     *             <jp> <br></jp>f a database access error occurs during modification.</en>
     */
    public int findCountByTerminalNumber(String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末番号に該当する端末変更マップ情報をデータベースから削除します。 <br></jp>
     * <en>This method deletes TerminalChangeMap infomration for given
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
    public int deleteTerminalChangeMap(String terminalNumber)
            throws SQLException;
}