// $Id: HandyAuthentication.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;

/**
 * <jp>ハンディでログイン認証を行なうためのクラスです。</jp><br>
 * <en>Authenctication class for handy.</en><br>
 * @author K.Fukumori
 * 
 */
public class HandyAuthentication
        extends AbstractAuthentication
{
    /** <jp>認証ログをDB出力するかどうかのフラグ &nbsp;&nbsp;</jp><en>Flag for authentication log on DB. &nbsp;&nbsp;</en> */
    private boolean isDBLogginAuthLog = false;

    /**
     * <jp>コンストラクタ。</jp><br>
     * <en>Constractor</en><br>
     * @throws SQLException
     */
    public HandyAuthentication() throws SQLException
    {
        super();
    }

    /**
     * <jp>ハンディからログインする為の認証メソッドです。 <br></jp>
     * <en>This method Authenticates the user and does various system checks for handy terminal Login.<br></en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>Password &nbsp;&nbsp;</en>
     * @param ipAddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param terminalNo 
     * @return UserInfo <jp>DfkUserInfoクラス &nbsp;&nbsp;</jp><en>DfkUserInfo class &nbsp;&nbsp;</en>
     * @throws EmAuthenticationException
     * @throws SQLException 
     */
    public DfkUserInfo handyAuthenticate(String userId, String password, String ipAddress, String terminalNo)
            throws EmAuthenticationException,
                SQLException
    {
        Connection conn = null;
        // Check User Information
        DfkUserInfo sessionUserInfo = null;
        try
        {

            conn = EmConnectionHandler.getConnection();
            // prepare the entity
            makeAuthSystemEntity(conn);
            makeUserEntity(conn, userId);

            // Authentication check
            // check if the user lock status
            this.checkUserLock();
            // Password Check
            this.checkPassword(conn, password);
            // Password Expire check
            this.checkPwdExpire();
            // dummy pwd check
            this.checkDummyPwd();

            // Update the datebase
            userEntity.setFailedCount(0);
            updateUserData(conn);
            //			 <jp>DfkUserInfoの生成</jp><en>Make DfkUserInfo</en>
            sessionUserInfo = this.makeDfkUserInfo();
            // Logging
            this.writeAuthenticationLog(conn, userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_LOGIN,
                    LOGMSGKEY_LOGIN, "Handy", this.getClass().getName());

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
            String msg = DispResources.getText(EManagerUtil.getLogginLocale(), ex.getMessageResourcekey());
            this.writeAuthenticationLog(conn, userId, ipAddress, terminalNo, EmConstants.AUTHLOG_CLASS_FAILED,
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

    /**
     * <jp>DfkUserInfo クラスを生成します。 <br></jp>
     * <en>Make the DfkUserInfo class <br></en>
     * @return DfkUserInfo
     */
    protected DfkUserInfo makeDfkUserInfo()
    {
        DfkUserInfo userInfo = new DfkUserInfo();

        // set User Entity data to userinfo
        if (userEntity != null)
        {
            userInfo.setUserId(userEntity.getUserID());
            userInfo.setUserName(userEntity.getUserName());
            userInfo.setLastAccessDate(userEntity.getLastAccessDate());
            userInfo.setMenuRoleId(userEntity.getRoleID());
            userInfo.setRoleID(userEntity.getRoleID());
        }
        // set menu information to userinfo  
        if (authSystemEntity != null)
        {
            isDBLogginAuthLog = authSystemEntity.getAuthLogFlag();
            userInfo.setMainMenuType(authSystemEntity.getMainMenuShowType());
        }

        return userInfo;
    }

    /**
     * <jp>認証ログを書き込みます。 <br></jp>
     * <en>Write the authentication log. <br></en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
     * @param terminalNumber <jp>端末番号 &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en>
     * @param messageNo <jp>メッセージ番号 &nbsp;&nbsp;</jp><en>Message number &nbsp;&nbsp;</en>
     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
     * @param procName <jp>更新プロセス名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    private void writeAuthenticationLog(Connection conn, String userid, String ipaddress, String terminalNumber,
            int logClass, String messageNo, String detail, String procName)
            throws SQLException
    {
        //		String message = EManagerUtil.convertMsg(messageNo);
        //		Date updateDate = new Date();
        //		
        //		// log4j Logging
        //		//EmLog4jAuthLogger.writeInfo(message);
        //		
        //		if(isDBLogginAuthLog == true)
        //		{	
        //			LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);
        //			LogInfo loginfo = this.makeLogInfo(updateDate, userid, ipaddress,terminalNumber, logClass, message, detail, procName);
        //			logHandler.createAuthenticationLog(loginfo);
        //
        //			EmConnectionHandler.commit(conn);
        //		}
    }

    //    /**
    //     * <jp>DB に設定する為の LogInfo エンティティを作成します。 <br></jp>
    //     * <en>Create the LogInfo Entity for DB logging. <br></en>
    //     * @param updateDate <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
    //     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
    //     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
    //     * @param terminalNumber <jp>端末No. &nbsp;&nbsp;</jp><en>TerminalNo. &nbsp;&nbsp;</en>
    //     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
    //     * @param msg <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en>
    //     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
    //     * @param procName <jp>更新プロセス名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en>
    //     * @return <jp>LogInfo クラス &nbsp;&nbsp;</jp><en>LogInfo class &nbsp;&nbsp;</en>
    //     */
    //    private LogInfo makeLogInfo(Date updateDate, String userid, String ipaddress, String terminalNumber, int logClass,
    //            String msg, String detail, String procName)
    //    {
    //        LogInfo loginfo = new LogInfo();
    //        loginfo.setUserId(userid);
    //        loginfo.setIpAddress(ipaddress);
    //        loginfo.setTerminalNumber(terminalNumber);
    //        loginfo.setLogDate(updateDate);
    //        loginfo.setLogClass(logClass);
    //        loginfo.setMessage(msg);
    //        loginfo.setDetails(detail);
    //
    //        loginfo.setUpdateDate(updateDate);
    //        loginfo.setUpdateKind(EmConstants.UPDATE_KIND_INSERT);
    //        loginfo.setUpdateProcess(procName);
    //        loginfo.setUpdateTerminal(ipaddress);
    //        loginfo.setUpdateUser(userid);
    //
    //        return loginfo;
    //    }

}
