// $Id: FunctionHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.io.Serializable;
import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.Function;

/**
 * <jp>このInterfaceはFunctionクラスに関連するデータを扱う様々なメソッドを提供します。 <br>
 * </jp> <en>This Interface provides the various methods to handle Function
 * related data.</en>
 * 
 * @author $Author: Muneendra
 */
public interface FunctionHandler
        extends Serializable
{
    /**
     * <jp>Functionクラスをデータベースに追加します。 <br>
     * </jp> <en>This method persists given Function infomration.<br>
     * </en>
     * 
     * @param function
     *            <en> Function information to be persisted.</en>
     * @return int <en>inserted number of rows</en>
     * @throws SQLException
     *             <en>if a database access error occurs during creation.</en>
     */
    public int createFunction(Function function)
            throws SQLException;

    /**
     * <jp>FunctionIDでデータベースを検索し、Functionエンティティを返却します。 <br>
     * </jp> <en>This method selects all Function information for a given
     * Function ID.<br>
     * </en>
     * 
     * @param functionId
     *            <en> function Id </en>
     * @return Function <en>Returns Function entity. Returns null If no data
     *         found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public Function findByFunctionId(String functionId)
            throws SQLException;

    /**
     * <jp>SubMenuIDでデータベースを検索しFunctionエンティティの配列を返却します。 <br>
     * </jp> <en>This method selects all Function information for given SubMenu
     * Id.<br>
     * </en>
     * 
     * @param subMenuId
     *            <en> Sub Menu Id </en>
     * @return Function[] <en>Returns Function entity array. Returns null If no
     *         data found</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public Function[] findBySubMenuId(String subMenuId)
            throws SQLException;

    /**
     * <jp>FunctionIDとそれに関連するデータの配列を返します。 <br></jp>
     * <en>This method returns all Function ids and related data.<br></en>
     * 
     * @param orderBy <jp>検索結果のソートを設定します 0:ファンクションID, 1: ボタン表示順 &nbsp;&nbsp;</jp>
     *                 <en>0:Order By FUNCTIONID 1: Order By BUTTONDISPORDER &nbsp;&nbsp;</en>
     * @return Function[] <jp>Functionエンティティの配列を返却します。検索結果がない場合は NULL を返却します。 &nbsp;&nbsp;</jp>
     *                     <en>Returns function data as function entity array. Returns null If no data found &nbsp;&nbsp;</en>
     * @throws SQLException <jp>DBエラーが発生したとき. &nbsp;&nbsp;</jp>
     *                        <en>For any other database error. &nbsp;&nbsp;</en>
     */
    public Function[] findAll(int orderBy)
            throws SQLException;

    /**
     * <jp>FunctionIDの情報を更新します。 <br>
     * </jp> <en>This method modifies Function Id information.<br>
     * </en>
     * 
     * @param function
     *            <en> function information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateByFunctionId(Function function)
            throws SQLException;

    /**
     * <jp>FunctionテーブルからRecord番号を検索します。 <br>
     * </jp> <en>Finds the number of records in Function table.<br>
     * </en>
     * 
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCount()
            throws SQLException;

    /**
     * <jp>FunctionIDでFunctionテーブルからRecord番号を検索します。 <br>
     * </jp> <en>Finds the number of records in Function table for given
     * functionId ID.<br>
     * </en>
     * 
     * @param functionId
     *            <en> functionId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountByFunctionId(String functionId)
            throws SQLException;

    /**
     * <jp>SubMenuIdでFunctionテーブルからRecord番号を検索します。 <br>
     * </jp> <en>Finds the number of records in Function table for given
     * subMenuId.<br>
     * </en>
     * 
     * @param subMenuId
     *            <en> subMenuId</en>
     * @return int :<en>Total number of records</en>
     * @throws SQLException :
     *             <en>for any other database error.</en>
     */
    public int findCountBySubMenuId(String subMenuId)
            throws SQLException;

    /**
     * <jp>FunctionIDに該当するものをデータベースから削除します。<br>
     * </jp> <en>This method deletes Function infomration for a given
     * functionId.<br>
     * </en>
     * 
     * @param functionId :
     *            <en>functionId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteFunction(String functionId)
            throws SQLException;

    /**
     * <jp>SubMenuIDに該当するものをデータベースから削除します。 <br>
     * </jp> <en>This method deletes Function infomration for a given submenuId.<br>
     * </en>
     * 
     * @param submenuId :
     *            <en>submenuId</en>
     * @return int : <en>deleted number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>This exception is thrown in case of data base error..</en>
     */
    public int deleteFunctionBySubmenuId(String submenuId)
            throws SQLException;

    /**
     * 指定されたDS番号に該当するレコードを取得します。
     * 
     * @param dsNo DS番号
     * @return レコード
     * @throws SQLException DBアクセスエラーが発生した場合
     */
    public Function[] findByDsNo(String dsNo)
            throws SQLException;

    /**
     * 指定されたファンクションID以外で指定されたDS番号に該当するレコードを取得します。
     * 
     * @param dsNo DS番号
     * @param functionId ファンクションID
     * @return レコード
     * @throws SQLException DBアクセスエラーが発生した場合
     */
    public Function[] findByDsNoExceptFunctionId(String dsNo, String functionId)
            throws SQLException;

    /**
     * 指定されたDS番号のレコードのページ名リソースキーを更新します。
     * 
     * @param function DS番号
     * @return 更新レコード数
     * @throws SQLException DBアクセスエラーが発生した場合
     */
    public int updatePageNameResourceKeyByDsNo(Function function)
            throws SQLException;


}
