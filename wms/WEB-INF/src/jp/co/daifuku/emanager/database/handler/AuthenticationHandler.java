// $Id: AuthenticationHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;

/**
 * <jp>このInterfaceはAuthenticationSystemクラスに関連するデータを扱う様々なメソッドを提供します。<br>
 * </jp> <en>This Interface provides the various methods to handle
 * AuthenticationSystem related data<br>
 * </en>
 * 
 * @author $Author: Muneendra
 */
public interface AuthenticationHandler
{

    /**
     * <jp>AuthenticationSystemクラスにインフォメーションを取って来ます。<br>
     * </jp> <en>This method fetches AuthenticationSystem info <br>
     * </en>
     * 
     * @return AuthenticationSystem authenticationSystem:
     *         <en>AuthenticationSystem info. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */
    public AuthenticationSystem findAuthenticationSystem()
            throws SQLException;

    /**
     * <jp>AuthenticationSystemクラスの情報を変更します。 <br>
     * </jp> <en>This method modifies AuthenticationSystem information.<br>
     * </en>
     * 
     * @param authenSystem
     *            <en> Main Menu information which has to be modified.</en>
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateAuthenticationSystem(AuthenticationSystem authenSystem)
            throws SQLException;

    /**
     * 
     * <en>This method updates work date.<br>
     * </en>
     * 
     * @param authenSystem       
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateWorkDate(AuthenticationSystem authenSystem)
            throws SQLException;

}
