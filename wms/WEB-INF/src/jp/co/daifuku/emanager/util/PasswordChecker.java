// $Id: PasswordChecker.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.PassWordHistoryHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;


/**
 * <jp>パスワードの妥当性確認を行うためのクラスです。 <br></jp>
 * <en>It is the class to do the validity confirmation of the password. <br></en>
 * <br>
 * <table border="1" cellpadding="3" cellspacing="0">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor"><td>Date</td><td>Name</td><td>Comment</td></tr>
 * <tr><td>2004/06/23</td><td>M.Kawashima</td><td>created this class</td></tr>
 * </table>
 * <br>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $author$
 */
public class PasswordChecker
{
    // Instance variables 

    /** Connection */
    private Connection conn = null;

    /** New password */
    private String newPassword = null;

    /** Old password */
    private String oldPassword = null;

    /** User ID */
    private String userid = null;

    /** User entity */
    private User userEntity = null;

    /** AuthenticationSystem entity */
    private AuthenticationSystem authEntity = null;

    /** Password change histroy */
    private LogInfo[] pwdHistory = null;

    /**<jp>同一文字の最大連続数 &nbsp;&nbsp;</jp><en>Max number of same character is continued. &nbsp;&nbsp;</en> */
    private final int maxSameChar = 3;

    // Constructors --------------------------------------------------
    /**
     * <jp>コンストラクタ。<BR></jp> 
     * <en>Constructor.	<BR></en> 
     * @param conn Connection
     */
    public PasswordChecker(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * <jp>パスワードの妥当性チェックを行います。 <br></jp>
     * <en>Check the new password. <br></en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param oldPassword <jp>旧パスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en>
     * @param newPassword <jp>新パスワード &nbsp;&nbsp;</jp><en>New password &nbsp;&nbsp;</en>
     * @throws SQLException 
     * @throws EmAuthenticationException 
     */
    public void checkPassword(String userid, String oldPassword, String newPassword)
            throws SQLException,
                EmAuthenticationException
    {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.userid = userid;

        init();

        checkUserExist();
        checkPasswordLength();
        checkHistory();

        if (authEntity.getPassSafetyCheckFlag() == true)
        {
            checkCharacterKind();
            checkUserIdAndPassword();
            checkSameCharacter();
        }
    }

    /**
     * <jp>新規パスワードの為の妥当性チェックを行います。 <br></jp>
     * <en>Check the new password for create new user. <br></en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param newPassword <jp>新パスワード &nbsp;&nbsp;</jp><en>New password &nbsp;&nbsp;</en>
     * @throws SQLException 
     * @throws EmAuthenticationException 
     */
    public void checkNewPassword(String userid, String newPassword)
            throws SQLException,
                EmAuthenticationException
    {
        this.newPassword = newPassword;
        this.userid = userid;

        init();

        checkPasswordLength();

        if (authEntity.getPassSafetyCheckFlag() == true)
        {
            checkCharacterKind();
            checkUserIdAndPassword();
            checkSameCharacter();
        }
    }

    /**
     * Create Entities.
     * @throws SQLException
     */
    private void init()
            throws SQLException
    {
        UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
        userEntity = userHandler.findByUserId(userid);

        AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
        authEntity = authHandler.findAuthenticationSystem();

        PassWordHistoryHandler pwdHistoryHandler = EmHandlerFactory.getPassWordHistoryHandler(conn);
        pwdHistory = pwdHistoryHandler.findByUserId(userid);
    }

    /**
     * <jp>旧パスワードチェックを行ないます。 <br></jp>
     * <en>Check the user exist and old password. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkUserExist()
            throws EmAuthenticationException
    {
        if (userEntity != null && oldPassword != null && oldPassword.length() != 0
                && !userEntity.getPassword().equals(oldPassword))
        {
            throw new EmAuthenticationException("MSG-T0022");
        }
    }

    /**
     * <jp>パスワードの文字数のチェックを行ないます。 <br></jp>
     * <en>Check the password length. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkPasswordLength()
            throws EmAuthenticationException
    {
        int minLength = authEntity.getPassMinLength();

        if (newPassword.length() < minLength)
        {
            throw new EmAuthenticationException("MSG-T0027" + Constants.MSG_DELIM + minLength);
        }
    }

    /**
     * <jp>パスワードの履歴チェックを行ないます。 <br></jp>
     * <en>Check the password history. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkHistory()
            throws EmAuthenticationException
    {
        if (authEntity.getPassLogCheckTime() < 1)
        {
            return;
        }

        if (oldPassword.equals(newPassword))
        {
            throw new EmAuthenticationException("MSG-T0029");
        }

        for (int i = 0; pwdHistory != null && i < pwdHistory.length && i < authEntity.getPassLogCheckTime() - 1; i++)
        {
            if (newPassword.equals(pwdHistory[i].getOldPassword()))
            {
                throw new EmAuthenticationException("MSG-T0029");
            }
        }
    }

    /**
     * <jp>パスワードに含まれる文字種のチェックを行います。 <br></jp>
     * <en>Check the kind of character of password. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkCharacterKind()
            throws EmAuthenticationException
    {
        int aCount = 0;
        int nCount = 0;
        for (int i = 0; i < newPassword.length(); i++)
        {
            char c = newPassword.charAt(i);
            if ('0' <= c && c <= '9')
            {
                nCount++;
            }
            if ('a' <= Character.toLowerCase(c) && 'z' >= Character.toLowerCase(c))
            {
                aCount++;
            }
        }
        if (nCount == 0 || aCount == 0)
        {
            throw new EmAuthenticationException("MSG-T0031");
        }

    }

    /**
     * <jp>パスワードとユーザIDが同一かどうかのチェックを行います。 <br></jp>
     * <en>Check the password and userid are same. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkUserIdAndPassword()
            throws EmAuthenticationException
    {
        if (userid.equals(newPassword))
        {
            throw new EmAuthenticationException("MSG-T0028");
        }
    }

    /**
     * <jp>同一文字が続かないかチェックします。 <br></jp>
     * <en>Check the same character is continued. <br></en>
     * @throws EmAuthenticationException 
     */
    private void checkSameCharacter()
            throws EmAuthenticationException
    {
        char cTemp = '\0';
        int count = 1;
        for (int i = 0; i < newPassword.length(); i++)
        {
            if (cTemp == newPassword.charAt(i))
            {
                count++;
            }
            else
            {
                count = 1;
            }
            if (count >= maxSameChar)
            {
                throw new EmAuthenticationException("MSG-T0030" + Constants.MSG_DELIM + maxSameChar);
            }
            cTemp = newPassword.charAt(i);
        }
    }

}
//end of class