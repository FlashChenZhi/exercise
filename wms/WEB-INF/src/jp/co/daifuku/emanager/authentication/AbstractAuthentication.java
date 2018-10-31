// $Id: AbstractAuthentication.java 5376 2009-11-04 01:43:18Z yamashita $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.RoleFunctionMapHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.database.handler.TerminalUserMapHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmDate;

/**
 * <jp>認証を行なうためのクラスです。<br></jp>
 * <en>This class implements System authentication logic . <br></en>
 * @author Muneendra
 */
public abstract class AbstractAuthentication
{
    // Class fields --------------------------------------------------
    /** <jp>ユーザ名またはパスワードが違います</jp><br><en>User Name or Password is wrong.</en><br> */
    protected final String ERRORMSG_USERPASS = "MSG-T0011";

    /** <jp>この端末からは接続は行なえません。</jp><br><en>Can not connect from this terminal.</en><br> */
    protected final String ERRORMSG_TERMINAL = "MSG-T0024";

    /** <jp>この端末にはログインできないユーザです。</jp><br><en>This user can not login to this terminal.</en><br> */
    protected final String ERRORMSG_TERMINALUSER = "MSG-T0023";

    /** <jp>ログイン可能な最大ユーザ数を超えているためログインできません。管理者へお問い合わせ下さい。</jp><br>
     * <en>Cannot log on. Exceeds max. accessible users. Please contact administrator.</en><br> */
    protected final String ERRORMSG_SYSTEMLOGINMAX = "MSG-T0017";

    /** <jp>同一ユーザログイン可能数を超えているためログインできません。管理者へお問い合わせ下さい。</jp><br>
     * <en>Cannot log on. Exceeds max. logins per user. Please contact administrator.</en><br> */
    protected final String ERRORMSG_USERLOGINMAX = "MSG-T0016";

    /** <jp>再ログイン試行回数を超えました。このユーザはログインできません。</jp><br>
     * <en>Exceeds max. login attempts. This user is not allowed to log on.</en><br> */
    protected final String ERRORMSG_USERLOCK = "MSG-T0013";

    /** <jp>パスワードの有効期限が切れているためログインできません。管理者へお問い合わせ下さい。</jp><br>
     * <en>Cannot log on. Password expires. Please contact administrator.</en><br> */
    protected final String ERRORMSG_PWDEXPIRE = "MSG-T0014";

    /** <jp>自動ログインでログインすることはできません。</jp><br>
     * <en>Can not login by auto login.</en><br> */
    protected final String ERRORMSG_AUTOLOGIN = "MSG-T0033";

    /** <jp>このユーザではログインできません。管理者へお問い合わせ下さい。</jp><br>
     * <en>This user is not allowed to log on. Please contact administrator.</en><br> */
    protected final String ERRORMSG_USERPERMISSION = "MSG-T0012";
    
// 2008/12/10 K.Matsuda Start 無効ユーザのメッセージ追加
    /** このユーザは無効です。管理者へお問い合わせ下さい。 */
    protected final String ERRORMSG_USERDISABLED = "MSG-T0061";
// 2008/12/10 K.Matsuda End
    
    /** <jp>ログインしました。</jp><br><en>Logged in.</en><br> */
    protected static final String LOGMSGKEY_LOGIN = "6400001";

    /** <jp>ログアウトしました。</jp><br><en>Logged out.</en><br> */
    protected static final String LOGMSGKEY_LOGOUT = "6400002";

    /** <jp>ログインに失敗しました。</jp><br><en>It failed in login.</en><br> */
    protected static final String LOGMSGKEY_FAILLOGIN = "6400003";

    /** <jp>ログインはタイムアウトしました。</jp><br><en>Login did the time-out.</en><br> */
    protected static final String LOGMSGKEY_TIMEOUT = "6400004";

    /** <jp>ユーザをロックしました。</jp><br><en>The user was locked.</en><br> */
    protected static final String LOGMSGKEY_USERLOCK = "6400005";


    // Class variables -----------------------------------------------

    // entity declaration
    /** Authentication Entity */
    protected AuthenticationSystem authSystemEntity = null;

    /** LoginUser Entity */
    protected User userEntity = null;

    /** Terminal Entity */
    protected Terminal terminalEntity = null;

    /** Real password change interval of user */
    protected int realPwdChangeInterval = 0;

    /** 0 Default, 1: Dummy passoword , redirect user to change password screen */
    protected int resultStatus = EmConstants.AUTHENTICATION_DEFAULT;

// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
    /** パスワード期限切れを例外とするかのフラグ */
    protected boolean isExpireToException = true;
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
    
    /** IP address */
    protected String ipAddress = null;

    /** Real failed login attempts */
    protected int realFailedLoginAttempts = 0;


    /**
     * <jp>新しい<code>SystemAuthentication</code>を構築します。<br></jp>
     * <en>New <code>SystemAuthentication</code> is created<br></en>
     * @throws SQLException
     */
    AbstractAuthentication() throws SQLException
    {
        super();
    }


    /**
     * <jp>認証結果を取得します。<br></jp>
     * <en>Returns resultStatus.<br></en>
     * @return resultStatus
     */
    public int getResultStatus()
    {
        return this.resultStatus;
    }

    /**
     * <jp>ログアウトの処理を行います。</jp><br>
     * <en>Execute logout process.</en><br>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP Address &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    public void logout(String userId, String ipAddress)
            throws SQLException
    {
        String terminalNo = "";
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getConnection();

            makeTerminalEntity(conn, ipAddress);
            terminalNo = terminalEntity.getTerminalNumber();
        }
        catch (EmAuthenticationException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
        EmDBLog.writeAuthenticationLog(userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_LOGOUT,
                LOGMSGKEY_LOGOUT, "", this.getClass().getName());
    }

    /**
     * <jp>タイムアウトの処理を行います。</jp><br>
     * <en>Execute logout process.</en><br>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP Address &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    public void timeout(String userId, String ipAddress)
            throws SQLException
    {
        String terminalNo = "";
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getConnection();

            makeTerminalEntity(conn, ipAddress);
            terminalNo = terminalEntity.getTerminalNumber();
        }
        catch (EmAuthenticationException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
        EmDBLog.writeAuthenticationLog(userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_TIMEOUT,
                LOGMSGKEY_TIMEOUT, "", this.getClass().getName());
    }


    /**
     * <jp>ユーザ情報を更新します。<br></jp>
     * <en>Updates the user data for given User Id.<br></en>
     * @param conn Connection
     * @throws SQLException <jp>データベースのアクセスエラーが発生したときにスローします。 &nbsp;&nbsp;</jp>
     *                        <en>If a database access error occurs during creation. &nbsp;&nbsp;</en>
     */
    protected void updateUserData(Connection conn)
            throws SQLException
    {
        userEntity.setLastAccessDate(new Date());
        userEntity.setUpdateUser(userEntity.getUserID());
        userEntity.setUpdateTerminal(ipAddress);
        userEntity.setUpdateKind(EmConstants.UPDATE_KIND_UPDATE);
        userEntity.setUpdateProcess(this.getClass().getName());

        try
        {
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            userHandler.updateByUserId(userEntity);
        }
        catch (SQLException sqlException)
        {
            EmConnectionHandler.rollback(conn);
            throw sqlException;
        }
    }


    /**
     * <jp>端末情報の取得を行います。 <br></jp>
     * <en>This method gets the terminal info for give Up address.<br>
     * If no terminal info found for a given address, returns UNDEFINED_TERMINAL terminal info.<br></en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param conn Connection
     * @throws SQLException
     * @throws EmAuthenticationException <jp>端末が存在しないとき &nbsp;&nbsp;</jp>
     *                                    <en>Terminal is not exist &nbsp;&nbsp;</en>
     */
    protected void makeTerminalEntity(Connection conn, String ipAddress)
            throws SQLException,
                EmAuthenticationException
    {
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
        Terminal terminalInfo = terminalHandler.findByAddress(ipAddress);
        if (terminalInfo == null)
        {
            String hostname = EManagerUtil.getHostnameByIpAddress(ipAddress);
            terminalInfo = terminalHandler.findByAddress(hostname);
        }
        if (terminalInfo == null)
        {
            terminalInfo = terminalHandler.findByAddress(EmConstants.UNDEFINED_TERMINAL);
        }
        if (terminalInfo == null)
        {
            // If UNDEFINED_TERMINAL is not exist, throw exception
            throw new EmAuthenticationException(ERRORMSG_TERMINAL, EmAuthenticationException.AUTH_ERR_TERMINAL);
        }
        terminalEntity = terminalInfo;

    }

    /**
     * <jp>ユーザ情報の取得を行います。 <br></jp>
     * <en>This method gets the user info for give Up userId.<br></en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param conn Connection
     * @throws SQLException
     * @throws EmAuthenticationException <jp>ユーザが存在しないとき &nbsp;&nbsp;</jp>
     *                                    <en>User is not exist &nbsp;&nbsp;</en>
     */
    protected void makeUserEntity(Connection conn, String userId)
            throws SQLException,
                EmAuthenticationException
    {
        // get User info from DB
        UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
        userEntity = userHandler.findByUserId(userId);
        if (userEntity == null || userHandler.findByUserId(userId).getDeleteStatus() == EmConstants.DELETESTATUS_DELETE)
        {
            throw new EmAuthenticationException(ERRORMSG_USERPASS, EmAuthenticationException.AUTH_ERR_USERPASS);
        }
        realPwdChangeInterval = userHandler.getRealPwdChangeIntervaluByUserId(userId);
        realFailedLoginAttempts = userHandler.getRealFailedLoginAttempts(userId);
    }

    /**
     * <jp>システム設定の取得を行います。 <br></jp>
     * <en>This method gets the user info for give Up address.<br>
     * If no terminal info found for a given user, returns UNDEFINED_TERMINAL terminal user info.<br></en>
     * @param conn Connection
     * @throws SQLException
     */
    protected void makeAuthSystemEntity(Connection conn)
            throws SQLException
    {
        // get SystemAuthentication information from DB
        AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
        authSystemEntity = authHandler.findAuthenticationSystem();
    }


    /**
     * <jp>DfkUserInfo クラスを生成します。 <br></jp>
     * <en>Make the DfkUserInfo class <br></en>
     * @return DfkUserInfo
     */
    protected DfkUserInfo makeDfkUserInfo()
    {
        DfkUserInfo sessionUserInfo = new DfkSessionListener();

        // set User Entity data to userinfo
        if (userEntity != null)
        {
            sessionUserInfo.setUserId(userEntity.getUserID());
            sessionUserInfo.setUserName(userEntity.getUserName());
            sessionUserInfo.setLastAccessDate(userEntity.getLastAccessDate());
            sessionUserInfo.setMenuRoleId(userEntity.getRoleID());
            sessionUserInfo.setRoleID(userEntity.getRoleID());
            sessionUserInfo.setSystemLoginUserId(userEntity.getUserID());
            sessionUserInfo.setSystemLoginUserName(userEntity.getUserName());
        }

        // set Terminal data to userinfo
        if (terminalEntity != null)
        {
            sessionUserInfo.setTerminalAddress(ipAddress);
            sessionUserInfo.setTerminalName(terminalEntity.getTerminalName());
            sessionUserInfo.setTerminalNumber(terminalEntity.getTerminalNumber());
            sessionUserInfo.setTerminalRoleId(terminalEntity.getRoleId());
            // プリンタ名称を設定 eManager2.0.1(BusiTune対応)
            sessionUserInfo.setTerminalPrinterName(terminalEntity.getPrinterName());
        }

        // set menu information to userinfo  
        if (authSystemEntity != null)
        {
            sessionUserInfo.setMainMenuType(authSystemEntity.getMainMenuShowType());
        }

        return sessionUserInfo;
    }

    /**
     * <jp>パスワードのチェックを行います。<br>
     * </jp>
     * <en> 1.This method checks for the valid password.<br>
     * 2.If the password is invalid, Checks for the number of times user enterted wrong password.<br>
     * 3.Check how many times user allowed to enter wrong password.<br>
     * 4. increment the wrong count update the data base with number of wrong password attempts.<br>
     * </en>
     * 
     * @param conn Connection
     * @param password <jp>ユーザが入力したパスワード。 &nbsp;&nbsp;</jp>
     *                  <en>User entered password. &nbsp;&nbsp;</en>
     * @throws EmAuthenticationException 
     *          <jp>認証に失敗したときにスローします。 &nbsp;&nbsp;</jp>
     *          <en>If authentication error occurs, throw this exception. &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    protected void checkPassword(Connection conn, String password)
            throws EmAuthenticationException,
                SQLException
    {
        // ANONYMOUS_USER can not be login 
        if (userEntity.getUserID().equals(EmConstants.ANONYMOUS_USER))
        {
            throw new EmAuthenticationException(ERRORMSG_USERPERMISSION,
                    EmAuthenticationException.AUTH_ERR_USERPERMISSION);
        }

        // Failed password.
        if (!userEntity.getPassword().equals(password))
        {
            userEntity.setFailedStartDate(new Date());
            // update user lock status
// 2009/07/09 T.Kajiwara Mod DAIFUKUユーザとServiceユーザとメンテナンスユーザはロックしない
            if (authSystemEntity.getFaieldLoginUserLockFlag()
                    && !userEntity.getUserID().equals(EmConstants.DAIFUKU_SV_USER)
                    && !userEntity.getUserID().equals(EmConstants.DAIFUKU_PRIVATE_USER)
                    && !userEntity.getUserID().equals(EmConstants.USERMAINTENANCE_USER))
// 2009/07/09 T.Kajiwara End            	
            {
                // update failed count.
                int failedCount = userEntity.getFailedCount();

                if (failedCount <= realFailedLoginAttempts)
                {
                    failedCount++;
                }

// 2008/12/10 K.Matsuda Start テーブル列名変更に伴い、メソッド名変更
                // If over the failed login attempts, lock the user.
                if (failedCount > realFailedLoginAttempts
                        && realFailedLoginAttempts != EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED
                        && userEntity.getUserStatus() == EmConstants.USERSTATUS_ACTIVE)
                {
                    userEntity.setUserStatus(EmConstants.USERSTATUS_LOCKED);
// 2008/12/10 K.Matsuda End
                    userEntity.setFailedCount(realFailedLoginAttempts + 1);

                    resultStatus = EmConstants.AUTHENTICATION_USERLOCK;

                    // handyの場合はログ出力しない(terminalEntityがnull)
                    if (terminalEntity != null)
                    {
                        String terminalNo = terminalEntity.getTerminalNumber();
                        EmDBLog.writeMaintenanceLog(userEntity.getUserID(), ipAddress, terminalNo,
                                EmConstants.MAINTELOG_CLASS_USER, LOGMSGKEY_USERLOCK, "", this.getClass().getName());
                    }
                }
                else
                {
                    userEntity.setFailedCount(failedCount);
                }
            }
            updateUserData(conn);
            throw new EmAuthenticationException(ERRORMSG_USERPASS, EmAuthenticationException.AUTH_ERR_USERPASS);
        }
    }

    /**
     * <jp>仮パスワード状態チェックを行います。 <br></jp>
     * <en>Checks if the use has the dummy password. If user has dummy password , result
     * status is set to 1 and user has to change the password after login.<br></en>
     */
    protected void checkDummyPwd()
    {
        // if Sysytem Dummy password flag is true and dummy user
        if (authSystemEntity.getDummyPassFlag() && userEntity.getDummyPassFlag())
        {
            this.resultStatus = EmConstants.AUTHENTICATION_DUMMYPASSWORD;
        }
    }

    /**
     * <jp>ユーザパスワードの有効期限のチェックを行います。<br></jp>
     * <en> 1.Checks for the user password expiration date. <br>
     * 2. Check password expire alert days.<br></en>
     * @throws EmAuthenticationException
     */
    protected void checkPwdExpire()
            throws EmAuthenticationException
    {
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        Date expireDate = userEntity.getPwdExpire();

        // check password expire
        if (authSystemEntity.getPassExpireFlag()
                && realPwdChangeInterval != EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED)
        {
            // <jp>パスワードの有効期間のチェックありで、有効期間がNULLのユーザに対しては有効期間をセットする。</jp>
            // <en>If password expire day is not set and enable check it, sets the password expire day.</en>
            if (expireDate == null)
            {
                cal.setTime(today);
                cal.add(Calendar.DATE, realPwdChangeInterval);
                userEntity.setPwdExpire(cal.getTime());
            }
            else
            {
                EmDate emToday = new EmDate(today);
                EmDate emExpireDate = new EmDate(expireDate);
                // 有効期限切れの場合(時刻は無視して日付のみで比較する)
                if (emToday.afterDate(emExpireDate))
                {
// 2008/12/10 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
                    this.resultStatus = EmConstants.AUTHENTICATION_PWDEXPIRED;
                    if(this.isExpireToException)
                    {
                        throw new EmAuthenticationException(ERRORMSG_PWDEXPIRE,
                                EmAuthenticationException.AUTH_ERR_PWDEXPIRE);
                    }
// 2008/12/10 K.Matsuda End
                }

                EmDate alertDate = new EmDate(emExpireDate);
                // パスワード期限切れ警告日数から警告を開始する日付を求める
                alertDate.add(Calendar.DATE, -1 * authSystemEntity.getPassExpireAlertDays());

// 2009/07/09 T.Kajiwara Mod 今日が警告開始日付以降もしくは今日が警告開始日付の場合
                if ((emToday.afterDate(alertDate) || emToday.equalsDate(alertDate))
                        && !EmConstants.DAIFUKU_SV_USER.equals(userEntity.getUserID())
                        && !EmConstants.DAIFUKU_PRIVATE_USER.equals(userEntity.getUserID()))
// 2009/07/09 T.Kajiwara End                	
                {
                    // set Password Expire Alert
                    this.resultStatus = EmConstants.AUTHENTICATION_PWDEXPIRE_ALERT;
                }
            }
        }
    }

    /**
     * <jp>ユーザのロック状態をチェックします。<br></jp>
     * <en>Checks for user lock status.<br></en>
     * @throws EmAuthenticationException 
     *    <jp>ユーザがロックされているとき発生します &nbsp;&nbsp;</jp>
     *    <en>If User is Locked, throws this exception. &nbsp;&nbsp;</en>
     */
    protected void checkUserLock()
            throws EmAuthenticationException
    {
// 2008/12/04 K.Matsuda Start ユーザ状態が無効の処理を追加
        // If user is locked
        if (authSystemEntity.getFaieldLoginUserLockFlag() && userEntity.getUserStatus() == EmConstants.USERSTATUS_LOCKED)
        {
            throw new EmAuthenticationException(ERRORMSG_USERLOCK, EmAuthenticationException.AUTH_ERR_USERLOCK);
        }
        else if(userEntity.getUserStatus()==EmConstants.USERSTATUS_DISABLED)
        {
            // ユーザが無効。
            throw new EmAuthenticationException(ERRORMSG_USERDISABLED, EmAuthenticationException.AUTH_ERR_USERDISABLED);
        }
// 2008/12/04 K.Matsuda End ユーザ状態が無効の処理を追加
    }

    /**
     * <jp>システムにログイン可能最大数のチェックを行います。 <br></jp>
     * <en>Checks for the maxmimum logins system can allow.<br></en>
     * @throws EmAuthenticationException
     */
    protected void checkLoginMax()
            throws EmAuthenticationException
    {
        // if Max login check
        if (authSystemEntity.getLoginMax() <= LoggedUserDataUtil.getAllUserCount())
        {
            throw new EmAuthenticationException(ERRORMSG_SYSTEMLOGINMAX,
                    EmAuthenticationException.AUTH_ERR_SYSTEMLOGINMAX);
        }

    }

    /**
     * <jp>同一ユーザログイン最大数のチェックを行います。 <br></jp>
     * <en>Checks for the maxmimum logins system can allow for the same user.<br></en>
     * @throws EmAuthenticationException
     */
    protected void checkSameUserLoginMax()
            throws EmAuthenticationException
    {
        // Same user Logged in Check
        int userCount = LoggedUserDataUtil.getLoginUserCount(userEntity.getUserID());

        if (authSystemEntity.getSameLoginUserFlag() == false)
        {
            // Can not login same user
            if (userCount > 0)
            {
                throw new EmAuthenticationException(ERRORMSG_USERLOGINMAX,
                        EmAuthenticationException.AUTH_ERR_USERLOGIINMAX);
            }
        }
        else
        {
            // Over the count of same login user
            if (userCount >= userEntity.getSameUserLoginMax()
                    && userEntity.getSameUserLoginMax() != EmConstants.SAMEUSERLOGINMAX_STATUS_NOLIMITED)
            {
                throw new EmAuthenticationException(ERRORMSG_USERLOGINMAX,
                        EmAuthenticationException.AUTH_ERR_USERLOGIINMAX);
            }
        }
    }

    /**
     * <jp>端末アドレス範囲チェックを行ないます 。<br></jp>
     * <en>Checks If the given Ip address is with in the given range and 
     * also Terminal Address. <br></en>
     * 
     * @param ipAddress : <en>ipAddress</en>
     * @throws EmAuthenticationException
     */
    protected void checkIpRangeAndTerminalAddress(String ipAddress)
            throws EmAuthenticationException
    {
        boolean rangeResult = false;
        boolean terminalResult = false;

        boolean isRangeCheck = authSystemEntity.getIpRangeCheckFlag();
        boolean isTerminalCheck = authSystemEntity.getTerminalCheckFlag();

        // Check the range of IP
        if (isRangeCheck)
        {
            String maxRange = authSystemEntity.getIprangeMax();
            String minRange = authSystemEntity.getIprangeMin();

            rangeResult = EManagerUtil.checkIpRange(ipAddress, maxRange, minRange);
        }

        // Check the registed terminal check
        if (isTerminalCheck)
        {
            if (!terminalEntity.getTerminalAddress().equals(EmConstants.UNDEFINED_TERMINAL))
            {
                terminalResult = true;
            }
        }

        // Throw exception
        if (isRangeCheck == true && isTerminalCheck == true)
        {
            if (rangeResult == false && terminalResult == false)
            {
                throw new EmAuthenticationException(ERRORMSG_TERMINAL, EmAuthenticationException.AUTH_ERR_TERMINAL);
            }
        }
        else if (isRangeCheck == true && rangeResult == false)
        {
            throw new EmAuthenticationException(ERRORMSG_TERMINAL, EmAuthenticationException.AUTH_ERR_TERMINAL);
        }
        else if (isTerminalCheck == true && terminalResult == false)
        {
            throw new EmAuthenticationException(ERRORMSG_TERMINAL, EmAuthenticationException.AUTH_ERR_TERMINAL);
        }
    }

    /**
     * <jp>端末にログイン可能なユーザのチェックを行ないます。<br></jp>
     * <en>Checks for the mapping between user id and terminal number<br></en>
     * @param conn Connection
     * @throws EmAuthenticationException
     * @throws SQLException
     */
    protected void checkTerminalUser(Connection conn)
            throws EmAuthenticationException,
                SQLException
    {
        String userId = userEntity.getUserID();
        String terminalNumber = terminalEntity.getTerminalNumber();

        if ((!authSystemEntity.getTerminalUserCheckFlag()) || userEntity.getRoleID().equals(EmConstants.ADMIN_ROLE))
        {
            return;
        }

        TerminalUserMapHandler terminalUserHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);
        int count = terminalUserHandler.findCountByUserIdTerminalNumber(userId, terminalNumber);
        if (count < 1)
        {
            throw new EmAuthenticationException(ERRORMSG_TERMINALUSER, EmAuthenticationException.AUTH_ERR_TERMINALUSER);
        }
    }

    /**
     * <jp>指定したロールで画面にアクセス可能かチェックします。 <br></jp>
     * <en>Check the permission for access to screen with role of argument. <br></en>
     * @param conn Connection
     * @param functionId <jp>機能ID &nbsp;&nbsp;</jp><en>Function ID &nbsp;&nbsp;</en>
     * @param roleId <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en>
     * @throws SQLException
     * @throws EmAuthenticationException
     */
    protected void checkScreenPermission(Connection conn, String functionId, String roleId)
            throws SQLException,
                EmAuthenticationException
    {
        RoleFunctionMapHandler handler = EmHandlerFactory.getRoleFunctionMapHandler(conn);
        int screenPermission = handler.findCountByFunctionRoleId(functionId, roleId);
        if (screenPermission == 0)
        {
            throw new EmAuthenticationException(ERRORMSG_USERPERMISSION,
                    EmAuthenticationException.AUTH_ERR_USERPERMISSION);
        }
    }
}
