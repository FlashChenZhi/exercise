// $Id: EmAuthenticationException.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.exception.AuthenticationException;

/**
 * <jp>認証例外クラスです。認証エラー発生時にスローされます。</jp><br>  
 * <en>EmSystemAuthenticateException might want to throw by Application in case specific data is not available in database.</en><br>
 * @author  $Author: Muneendra 
 */
public class EmAuthenticationException
        extends AuthenticationException
{
    /** <jp>ユーザIDもしくはパスワードが不正<br></jp><en>Illegal user ID or password<br></en> */
    public static final int AUTH_ERR_USERPASS = 1;

    /** <jp>未登録端末<br></jp><en>Undefined terminal<br></en> */
    public static final int AUTH_ERR_TERMINAL = 2;

    /** <jp>端末ユーザ制限<br></jp><en>Undefined terminal user<br></en> */
    public static final int AUTH_ERR_TERMINALUSER = 3;

    /** <jp>システムログイン数オーバー<br></jp><en>Over the max login in system<br></en> */
    public static final int AUTH_ERR_SYSTEMLOGINMAX = 4;

    /** <jp>ユーザログイン数オーバー<en></jp>Over the max login for user.<br></en> */
    public static final int AUTH_ERR_USERLOGIINMAX = 5;

    /** <jp>ユーザロック<br></jp><en>User id Locked<br></en> */
    public static final int AUTH_ERR_USERLOCK = 6;

    /** <jp>パスワード有効期限切れ<br></jp><en>Password is expired.<br></en> */
    public static final int AUTH_ERR_PWDEXPIRE = 7;

    /** <jp>自動ログイン不可<br></jp><en>Can not login by auto.<br></en> */
    public static final int AUTH_ERR_AUTOLOGIN = 8;

    /** <jp>ログイン権限なし<br></jp><en>No user permission.<br></en> */
    public static final int AUTH_ERR_USERPERMISSION = 9;

// 2008/12/04 K.Matsuda Start ユーザの状態に無効を追加
    /** <jp>ユーザ無効<br></jp><en>User id Locked<br></en> */
    public static final int AUTH_ERR_USERDISABLED = 10;
// 2008/12/04 K.Matsuda End ユーザの状態に無効を追加

    /**<jp>エラーコード<br></jp><en>Exception code<br></en> */
    protected int errorCode = 0;

    /**<jp>エラーメッセージのリソースキー<br></jp><en>Exception message<br></en> */
    protected String messageResourceKey = "";

    /**
     * <jp>EmAuthenticationException クラスを生成します。<br></jp>
     * <en>Constructs a new EmSystemAuthenticateException.<br></en>
     * @param messageResourceKey <jp>エラーメッセージのリソースキー&nbsp;&nbsp;<jp><en>ResourceKey of message&nbsp;&nbsp;</en>      
     */
    public EmAuthenticationException(String messageResourceKey)
    {
        super();
        this.messageResourceKey = messageResourceKey;
    }

    /**
     * <jp>EmAuthenticationException クラスを生成します。<br></jp>
     * <en>Constructs a new EmSystemAuthenticateException.<br></en>
     * @param messageResourceKey <jp>エラーメッセージのリソースキー&nbsp;&nbsp;<jp><en>ResourceKey of message&nbsp;&nbsp;</en>      
     * @param errorCode <jp>エラーコード &nbsp;&nbsp;<jp><en> Detailed error code &nbsp;&nbsp;</en>
     */
    public EmAuthenticationException(String messageResourceKey, int errorCode)
    {
        super();
        this.messageResourceKey = messageResourceKey;
        this.errorCode = errorCode;
    }

    /**
     * <jp>例外メッセージのリソースキーを返却します。<br></jp>
     * <en>Returns the resourceKey of message. <br></en>
     * @return <jp>メッセージ<jp>&nbsp;&nbsp;<en>message</en>
     */
    public String getMessageResourcekey()
    {
        return messageResourceKey;
    }

    /**
     * <jp>例外メッセージを返却します。<br></jp>
     * <en>Returns the message. <br></en>
     * @return <jp>メッセージ<jp>&nbsp;&nbsp;<en>message</en>
     */
    public String getMessage()
    {
        String message = "";
        try
        {
            message = DispResources.getText(messageResourceKey);
        }
        catch (Exception ex)
        {
        }

        return message;
    }

    /**
     * <jp>エラーコードを返却します。<br></jp>
     * <en>Returns the detail error message. <br></en>
     * @return <jp>エラーコード<jp>&nbsp;&nbsp;<en>error code</en>
     */
    public int getErrorCode()
    {
        return errorCode;
    }

}
