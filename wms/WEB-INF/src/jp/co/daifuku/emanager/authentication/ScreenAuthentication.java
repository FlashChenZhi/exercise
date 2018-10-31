// $Id: ScreenAuthentication.java 3965 2009-04-06 02:55:05Z admin $
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
import jp.co.daifuku.bluedog.util.Converter;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmDBLog;

/**
 * <jp>画面ログイン認証を行なうためのクラスです。</jp><br>
 * <en>Authenctication class for screen login.</en><br>
 * @author K.Fukumori
 *
 */
public class ScreenAuthentication
        extends AbstractAuthentication
{

    /**
     * <jp>コンストラクタ。</jp><br>
     * <en>Constractor</en><br>
     * @throws SQLException
     */
    //private DfkUserInfo dfkuserInfo = null;
    public ScreenAuthentication() throws SQLException
    {
        super();
    }

// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
    /**
     * <jp>画面ログイン画面の為の認証メソッドです。 <br></jp>
     * <en>This method Authenticates the user and does various system checks for screen login.<br></en>
     * @param userinfo <jp>現在の DfkUserInfo クラス &nbsp;&nbsp;</jp><en>DfkUserInfo class &nbsp;&nbsp;</en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>Password &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param session <jp>HTTPセッション &nbsp;&nbsp;</jp><en>HttpSession &nbsp;&nbsp;</en>
     * @param expchekflg <jp>パスワード有効期限切れを例外とする &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @return UserInfo <jp>DfkUserInfoクラス &nbsp;&nbsp;</jp><en>DfkUserInfo class &nbsp;&nbsp;</en>
     * @throws EmAuthenticationException
     * @throws SQLException 
     */
    public DfkUserInfo authenticate(DfkUserInfo userinfo, String userId, String password, String ipAddress, HttpSession session, boolean expchekflg)
            throws EmAuthenticationException, SQLException
// 2008/12/11 K.Matsuda End
    {
        Connection conn = null;
        // set user info to current object
        //this.dfkuserInfo = userinfo;
        this.ipAddress = ipAddress;
        
// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
        this.isExpireToException = expchekflg;
// 2008/12/11 K.Matsuda End
        
        // Check User Information
        try
        {
            conn = EmConnectionHandler.getConnection();

            String screenName = EManagerUtil.getScreenName(userinfo.getPageNameResourceKey());

            // <jp>エンティティの生成</jp><en>prepare the entity</en>
            makeAuthSystemEntity(conn);
            makeUserEntity(conn, userId);
            makeTerminalEntity(conn, userinfo.getTerminalNumber());

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
                // <jp>システムログインユーザと同じであればチェックしない</jp>
                // <en>Not check if same user to system login </en>
                if (userinfo == null || !userinfo.getUserId().equals(userId))
                {
                    // eManager2.0にて削除開始
                    //                    // <jp>最大ログイン数チェック</jp><en>User maximum login checks</en>
                    //                    checkLoginMax();
                    // eManager2.0にて削除終了
                    // <jp>同一ユーザログイン数チェック</jp><en>Same login user max check</en>
                    checkSameUserLoginMax();
                    // <jp>端末ユーザチェック</jp><en>Terminal user check</en>
                    checkTerminalUser(conn);
                }
                // check screen permission by user
                checkScreenPermission(conn, userinfo.getFunctionId(), userEntity.getRoleID());
            }

            // Update datebase
            userEntity.setFailedCount(0);
            updateUserData(conn);

            // <jp>DfkUserInfoの生成</jp><en>Make DfkUserInfo</en>
            // <jp>システムログインユーザと同じであるときはそのまま</jp>
            // <en>if Screen Login user is different than　System login user Id, update DfkUserInfo</en>
            if (userinfo != null && !userinfo.getUserId().equals(userId))
            {
                // <jp>ログアウト</jp>
                // <en>Logout old user <en>
                ((DfkSessionListener)userinfo).isLogoutClick = true;
                // <jp>新しいユーザ情報を作成</jp>
                // <en>Create new user info</en>
                DfkUserInfo newUserInfo = makeDfkUserInfo();
                newUserInfo.setMenuRoleId(userinfo.getMenuRoleId());
                newUserInfo.setSystemLoginUserId(userinfo.getSystemLoginUserId());
                newUserInfo.setSystemLoginUserName(userinfo.getSystemLoginUserName());
                newUserInfo.setPageNameResourceKey(userinfo.getPageNameResourceKey());
                newUserInfo.setDsNumber(userinfo.getDsNumber());
                newUserInfo.setFunctionId(userinfo.getFunctionId());

                // update Userinfo reference to new newUserInfo location
                userinfo = newUserInfo;
                // Add logged user to Logged User Data Util
                LoggedUserDataUtil.addLoginUser(userId, session.getId(), ipAddress, session);
                // <jp>セッションタイムアウト値の設定</jp><en>Set Session time out period in seconds.</en>
                session.setMaxInactiveInterval(authSystemEntity.getSessionTimeoutTime());

                String teminalNo = terminalEntity.getTerminalNumber();
                // Emanager ver 1.0 Authentication log
                EmDBLog.writeAuthenticationLog(newUserInfo.getUserId(), ipAddress, teminalNo,
                        EmConstants.AUTHLOG_CLASS_LOGIN, LOGMSGKEY_LOGIN, screenName, this.getClass().getName());
            }
            else
            {
                if (userinfo != null && userEntity != null)
                {
                    String oldUserName = Converter.nullToStr(userinfo.getUserName(), "");
                    String newUserName = Converter.nullToStr(userEntity.getUserName(), "");
                    if (!oldUserName.equals(newUserName))
                    {
                        // ユーザIDが同じ場合でもユーザ名が変更されている場合はユーザ名のみ更新する
                        userinfo.setUserName(newUserName);
                    }
                }
            }

            EmConnectionHandler.commit(conn);
        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);
            throw ex;
        }
        catch (EmAuthenticationException ex)
        {
            // Logging
            String terminalNo = "";
            if (terminalEntity != null)
            {
                terminalNo = terminalEntity.getTerminalNumber();
            }
            String msg = DispResources.getText(EManagerUtil.getLogginLocale(), ex.getMessageResourcekey());
            EmDBLog.writeAuthenticationLog(userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_FAILED,
                    LOGMSGKEY_FAILLOGIN, msg, this.getClass().getName());

            //this.part11AccessLog(EmConstants.AUTHLOG_CLASS_FAILED,msg,conn,userinfo);

            //Commit
            EmConnectionHandler.commit(conn);
            throw ex;
        }
        finally
        {
            // close the connection.. This connection aquaired at the constructor of this class 
            EmConnectionHandler.closeConnection(conn);
        }
        return userinfo;
    }


    /**
     * <jp>端末No. より端末情報を取得します。</jp><br>
     * <en>Gets the termnal info from terminal No.</en><br>
     * @param conn Connection
     * @param terminalNo <jp>端末No. &nbsp;&nbsp;</jp><en>Terminal No. &nbsp;&nbsp;</en>
     * @throws SQLException 
     * @throws EmAuthenticationException <jp>端末が存在しないとき &nbsp;&nbsp;</jp>
     *                                    <en>Terminal is not exist &nbsp;&nbsp;</en>
     */
    protected void makeTerminalEntity(Connection conn, String terminalNo)
            throws SQLException,
                EmAuthenticationException
    {
        TerminalHandler termHandler = EmHandlerFactory.getTerminalHandler(conn);
        terminalEntity = termHandler.findByTerminalNumber(terminalNo);
        if (terminalEntity == null)
        {
            // If TERMINAL is not exist, throw exception
            throw new EmAuthenticationException(ERRORMSG_TERMINAL, EmAuthenticationException.AUTH_ERR_TERMINAL);
        }
    }

}
