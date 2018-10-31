// $Id: AutoAuthentication.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;

/**
 * <jp>自動ログイン認証を行なうためのクラスです。</jp><br>
 * <en>Authenctication class for auto login.</en><br>
 * @author K.Fukumori
 * 
 */
public class AutoAuthentication
        extends AbstractAuthentication
{
    /**
     * <jp>コンストラクタ。</jp><br>
     * <en>Constractor</en><br>
     * @throws SQLException
     */
    public AutoAuthentication() throws SQLException
    {
        super();
    }

    /**
     * <jp>自動ログイン画面の為の認証メソッドです。 <br></jp>
     * <en>This method Authenticates the user and does various system checks for auto login.<br></en>
     * @param ipAddress <en></en>
     * @param session <en></en>
     * @return DfkUserInfo <en></en>
     * @throws EmAuthenticationException
     * @throws SQLException 
     */
    public DfkUserInfo authenticate(String ipAddress, HttpSession session)
            throws EmAuthenticationException,
                SQLException
    {

        Connection conn = null;
        this.ipAddress = ipAddress;
        DfkUserInfo sessionUserInfo = null;
        String userId = EmConstants.ANONYMOUS_USER;
        // Check User Information
        try
        {
            conn = EmConnectionHandler.getConnection();

            // <jp>エンティティの生成</jp><en>prepare the entity</en>
            makeAuthSystemEntity(conn);
            makeUserEntity(conn, userId);
            makeTerminalEntity(conn, ipAddress);

            // <jp>自動ログインの有効チェック</jp><en>Autologin enabled check</en>
            if (authSystemEntity.getAutoLoginFlag() == false)
            {
                throw new EmAuthenticationException(ERRORMSG_AUTOLOGIN, EmAuthenticationException.AUTH_ERR_AUTOLOGIN);
            }

            // <jp>認証チェック</jp> <en>Authentication check</en>
            // <jp>ユーザロックのチェック</jp><en>Check if the user lock status</en>
            checkUserLock();

            // <jp>ログイン制限チェック</jp><en>Login limit check.</en>
            // <jp>最大ログイン数チェック</jp><en>User maximum login checks</en>
            checkLoginMax();
            // <jp>同一ユーザログイン数チェック</jp><en>Same login user max check</en>
            checkSameUserLoginMax();
            // <jp>IPアドレスチェック</jp><en>IP ADDRESS Check</en>
            checkIpRangeAndTerminalAddress(ipAddress);
            // <jp>端末ユーザチェック</jp><en>Terminal user check</en>
            checkTerminalUser(conn);


            // Update the datebase
            userEntity.setFailedCount(0);
            updateUserData(conn);

            // <jp>DfkUserInfoの生成</jp><en>Make DfkUserInfo</en>
            sessionUserInfo = makeDfkUserInfo();

// 2008/12/16 K.Matsuda Start 自動ログインのタイムアウト値をemanager.propatiesより取得
            // <jp>セッションタイムアウト値の設定</jp><en>Set Session time out period in seconds.</en>
            session.setMaxInactiveInterval(Integer.parseInt(EmProperties.getProperty(EmConstants.EMPARAMKEY_AUTOLOGIN_TIMEOUT)));
// 2008/12/16 K.Matsuda End

            // Add logged user to Logged User Data Util
            LoggedUserDataUtil.addLoginUser(userId, session.getId(), ipAddress, session);

            // Logging
            EmDBLog.writeAuthenticationLog(userId, ipAddress, terminalEntity.getTerminalNumber(),
                    EmConstants.AUTHLOG_CLASS_LOGIN, LOGMSGKEY_LOGIN, "", this.getClass().getName());

            // Commit
            EmConnectionHandler.commit(conn);

        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);
            throw ex;
        }
        catch (EmAuthenticationException ex)
        {
            //Commit
            EmConnectionHandler.commit(conn);
            // Logging
            String terminalNo = "";
            if (terminalEntity != null)
            {
                terminalNo = terminalEntity.getTerminalNumber();
            }
            String msg = DispResources.getText(EManagerUtil.getLogginLocale(), ex.getMessageResourcekey());
            EmDBLog.writeAuthenticationLog(userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_FAILED,
                    LOGMSGKEY_FAILLOGIN, msg, this.getClass().getName());
            throw ex;
        }
        finally
        {
            // close the connection.. This connection aquaired at the constructor of this class 
            EmConnectionHandler.closeConnection(conn);
        }
        return sessionUserInfo;

    }
}
