// $Id: TerminalHandler.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>端末ハンドラに関する様々なメソッドを提供するinterfaceです。 <br></jp>
 * <en>This Interface provides the various methods to handle Terminal related data</en>
 * 
 * @author $Author: Muneendra
 */
public interface TerminalHandler
{

    /**
     * <jp>端末クラスをデータベースに追加します。 <br></jp>
     * <en>This method persists given terminal infomration.</en>
     * 
     * @param terminalInfo :
     *            <en> Terminal information which has to be persisted.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during Terminal info
     *             creation.</en>
     */
    public int createTerminal(Terminal terminalInfo)
            throws SQLException;

    /**
     * <jp>端末番号でデータベースから端末情報を検索し、返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal number</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return Terminal : <en>Returns terminal Information. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal findByTerminalNumber(String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末アドレスで端末情報をデータベースから検索し、それを返却します。 <br></jp>
     * <en>This method fetches Terminal information from database for a given
     * terminal address</en>
     * 
     * @param address :
     *            <en>Computer address</en>
     * @return Terminal : <en>Returns terminal Information. returns null if no
     *         data found</en>
     * @throws SQLException :
     *             <en>for any database access error </en>
     */
    public Terminal findByAddress(String address)
            throws SQLException;

    /**
     * <jp>データベースから端末情報を検索し、その配列を返却します。 <br></jp>
     * <en>This method returns all terminal information.</en>
     * 
     * @param orderBy :
     *            <en> 0:Order By TERMINALNUMBER 1: Order By TERMINALADDRESS</en>
     * @return Terminal[] <en>Returns terminal information as entity array.
     *         Returns null If no data found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findAll(int orderBy)
            throws SQLException;

    /**
     * <jp>端末ID入力で、ワイルドカード検索して該当するものを配列として返却します。 <br></jp>
     * <en> This method finds the Terminal records where Terminal id is like
     * given input parameter. </en>
     * 
     * @param terminalId :
     *            <en> terminalId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Terminal[] : <en>Returns Terminal array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findTerminalLike(String terminalId, int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>端末ID入力で、ワイルドカード検索して該当するものを配列として返却します。 <br></jp>
     * <en> This method all terminal records except the given terminal number</en>
     * 
     * @param terminalId :
     *            <en> terminalId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Terminal[] : <en>Returns Terminal array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findAllNotTerminal(String terminalId, int startRow, int endRow)
            throws SQLException;

    /**
     * <jp>端末番号で、データベースから利用できる番号を検索し、それを返却します。 <br></jp>
     * <en>Finds the number of records available for Terminal where
     * terminalNumber is like given input</en>
     * 
     * @param terminalNumber :
     *            <en> terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountLike(String terminalNumber)
            throws SQLException;

    /**
     * <jp>端末番号で、データベースから利用できる番号を検索し、それを返却します。 <br></jp>
     * <en>Finds the number of records records excluding the given number
     * terminalNumber is like given input</en>
     * 
     * @param terminalNumber :
     *            <en> terminalNumber</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountNotTerminal(String terminalNumber)
            throws SQLException;

    /**
     * <jp>データベースから端末番号の登録数を検索して、それを返却します。 <br></jp>
     * <en>Finds the number of terminal numbers.</en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCount()
            throws SQLException;

    /**
     * <jp>ロールIDでデータベースから登録数を検索します。 <br></jp>
     * <en>Finds the number of available user records for given roleId</en>
     * @param roleId :
     *            <en> roleId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int finCountByRoleId(String roleId)
            throws SQLException;

    /**
     * <jp>端末番号で、データベースに登録されている端末情報を修正します。 <br></jp>
     * <en>This method modifies terminal information for a given terminal number .</en>
     * 
     * @param terminalInfo :
     *            <enTerminalterminalInfo which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByTerminalNumber(Terminal terminalInfo)
            throws SQLException;

    /**
     * <jp>端末IDでデータベースを検索し、該当する端末情報を削除します。 <br></jp>
     * <en>This method deletes Terminal infomration for given terminalId.</en>
     * 
     * @param terminalNumber :
     *            <en>terminalNumber</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error
     *             during terminalNumber deletion.</en>
     */
    public int deleteTerminal(String terminalNumber)
            throws SQLException;
    
    /**
     * 端末番号で以降検索を行い登録数を取得します。

     * @param terminalId :
     *            <en> terminalId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int findCountGreaterThan(String terminalId)
            throws SQLException;
    
    /**
     * <jp>端末ID入力で、ワイルドカード検索して該当するものを配列として返却します。 <br></jp>
     * <en> This method finds the Terminal records where Terminal id is like
     * given input parameter. </en>
     * 
     * @param terminalId :
     *            <en> terminalId </en>
     * @param startRow :
     *            <en> Start record. Should be >0</en>
     * @param endRow :
     *            <en> End record</en>
     * @return Terminal[] : <en>Returns Terminal array. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public Terminal[] findTerminalGreatherThan(String terminalId, int startRow, int endRow)
            throws SQLException;
}
