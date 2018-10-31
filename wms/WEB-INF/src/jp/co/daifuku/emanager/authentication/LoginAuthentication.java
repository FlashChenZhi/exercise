// $Id: LoginAuthentication.java 3965 2009-04-06 02:55:05Z admin $
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
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EManagerUtil;

/**
 * <jp>通常ログイン用の認証クラスです。<br></jp>
 * <en>Authentication class for nomal login.<br></en>
 * @author K.Fukumori
 */
public class LoginAuthentication
        extends AbstractAuthentication
{
    /**
     * <jp>コンストラクタ。</jp><br>
     * <en>Constractor</en><br>
     * @throws SQLException
     */
    public LoginAuthentication() throws SQLException
    {
        super();
    }

// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
    /**
     * <jp>ログインチェックを行います。 <br>パスワード有効期限切れを例外とします<br></jp>
     * <en>This method Authenticates the user and does various system checks for system login.<br></en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>Password &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param session <jp>HTTPセッション &nbsp;&nbsp;</jp><en>HttpSession &nbsp;&nbsp;</en>
     * @return UserInfo <jp>DfkUserInfoクラス &nbsp;&nbsp;</jp><en>DfkUserInfo class &nbsp;&nbsp;</en>
     * @throws EmAuthenticationException <jp>認証エラー &nbsp;&nbsp;</jp>
     *                                    <en>Authentication Error &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    public DfkUserInfo authenticate(String userId, String password, String ipAddress, HttpSession session) 
        throws EmAuthenticationException, SQLException
    {
        return authenticate(userId, password, ipAddress, session, true);
    }
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
    
// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
    /**
     * <jp>ログインチェックを行います。 <br></jp>
     * <en>This method Authenticates the user and does various system checks for system login.<br></en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>Password &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param session <jp>HTTPセッション &nbsp;&nbsp;</jp><en>HttpSession &nbsp;&nbsp;</en>
     * @param expchekflg <jp>パスワード有効期限切れを例外とする &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @return UserInfo <jp>DfkUserInfoクラス &nbsp;&nbsp;</jp><en>DfkUserInfo class &nbsp;&nbsp;</en>
     * @throws EmAuthenticationException <jp>認証エラー &nbsp;&nbsp;</jp>
     *                                    <en>Authentication Error &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    public DfkUserInfo authenticate(String userId, String password, String ipAddress, HttpSession session, boolean expchekflg)
            throws EmAuthenticationException,
                SQLException
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
    {
        Connection conn = null;

        this.ipAddress = ipAddress;
        DfkUserInfo sessionUserInfo = null;
        
// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
        this.isExpireToException = expchekflg;
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
        
        // Check User Information
        try
        {
            conn = EmConnectionHandler.getConnection();

            // <jp>エンティティの生成</jp><en>prepare the entity</en>
            makeAuthSystemEntity(conn);
            makeUserEntity(conn, userId);
            makeTerminalEntity(conn, ipAddress);

            // <jp>認証チェック</jp> <en>Authentication check</en>
            // <jp>ユーザロックのチェック</jp><en>Check if the user lock status</en>
            checkUserLock();
            // <jp>パスワードチェック</jp><en>Password Check</en>
            checkPassword(conn, password);
            // <jp>パスワード有効期間チェック</jp><en>Password Expire check</en>
            checkPwdExpire();
            // <jp>仮パスワードチェック</jp><en>dummy password check</en>
            checkDummyPwd();

            // <jp>ログイン制限チェック</jp><en>Login limit check.</en>
            if (!userId.equals(EmConstants.DAIFUKU_SV_USER))
            {
                // <jp>最大ログイン数チェック</jp><en>User maximum login checks</en>
                this.checkLoginMax();
                // <jp>同一ユーザログイン数チェック</jp><en>Same login user max check</en>
                this.checkSameUserLoginMax();
                // <jp>IPアドレスチェック</jp><en>IP ADDRESS Check</en>
                this.checkIpRangeAndTerminalAddress(ipAddress);
                // <jp>端末ユーザチェック</jp><en>Terminal user check</en>
                this.checkTerminalUser(conn);
            }

            // Update the datebase
            userEntity.setFailedCount(0);
            updateUserData(conn);

            // <jp>DfkUserInfoの生成</jp><en>Make DfkUserInfo</en>
            sessionUserInfo = makeDfkUserInfo();

            // <jp>セッションタイムアウト値の設定</jp><en>Set Session time out period in seconds.</en>
            session.setMaxInactiveInterval(authSystemEntity.getSessionTimeoutTime());

            // Add logged user to Logged User Data Util
            LoggedUserDataUtil.addLoginUser(userId, session.getId(), ipAddress, session);

            // Logging
            String teminalNo = terminalEntity.getTerminalNumber();
            EmDBLog.writeAuthenticationLog(userId, ipAddress, teminalNo, EmConstants.AUTHLOG_CLASS_LOGIN,
                    LOGMSGKEY_LOGIN, "", this.getClass().getName());

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
            // Commit
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
            // Close the connection.. This connection aquaired at the constructor of this class 
            EmConnectionHandler.closeConnection(conn);
        }
        return sessionUserInfo;
    }

}
