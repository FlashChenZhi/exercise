// $Id: DfkSessionListener.java 8075 2014-09-19 07:16:57Z okayama $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;

/**
 * <jp>Sessionが生成、消滅したときのイベントを実装するためのクラス<br></jp>
 * <en>The class for Session to mount the event when it was created and invalidate.<br></en>
 * <br>
 * <table border="1" cellpadding="3" cellspacing="0">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor"><td>Date</td><td>Name</td><td>Comment</td></tr>
 * <tr><td>2004/06/22</td><td>M.Kawashima</td><td>created this class</td></tr>
 * </table>
 * <br>
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 */
public final class DfkSessionListener
        extends DfkUserInfo
        implements HttpSessionBindingListener, HttpSessionListener
{
    // Class variables -----------------------------------------------
    /** Property file name */
    private static final String DEFAULT_RESOURCE = "CommonParam.properties";

    /** Key for connection closer class name in properties file.  */
    private static final String CONNECTION_CLOSER_CLASSNAME = "CONNECTION_CLOSER_CLASSNAME";

    /**ログアウトボタンがクリックされたかどうかのフラグ <jp> &nbsp;&nbsp;</jp><en>Logout button clicked flag &nbsp;&nbsp;</en> */
    public boolean isLogoutClick = false;

    /**
     * <jp> Sessinが生成されたとき呼ばれます。<br></jp>
     * <en> It is called when Sessin is formed. <br></en>
     * @param ev  <code>HttpSessionBindingEvent</code>
     */
    @Override
    public void valueBound(HttpSessionBindingEvent ev)
    {
        //
    }

    /**
     * <jp>Sessionが消滅したり、このSessionListenerオブジェクト自身が
     * Sessionから削除された場合は、このメソッドが呼ばれます。<br></jp>
     * <en>This method is called when Session became extinct and this SessionListener object
     * itself was deleted from Session.<br></en>
     * @param ev  <code>HttpSessionBindingEvent</code>
     */
    @Override
    public void valueUnbound(HttpSessionBindingEvent ev)
    {
        //		String ipAddress = LoggedUserDataUtil.getIPAddress(ev.getSession().getId());
        String ipAddress = this.getTerminalAddress();
        if (ipAddress == null || ipAddress.equals(""))
        {
            ipAddress = "UNKNOWN";
        }

        //remove the login user
        LoggedUserDataUtil.removeLoginUser(ev.getSession().getId());

        try
        {
            LoginAuthentication auth = new LoginAuthentication();
            if (!this.isLogoutClick)
            {
                auth.timeout(userId, ipAddress);
            }
            else
            {
                auth.logout(userId, ipAddress);
            }

            // **** updted for eManager-2.0
            // create Access log in case of session timeout.. Access log has to be created only if Access log flag is true
            if (!this.isLogoutClick && P11Config.isAccessLog() && this.getDsNumber() != null)
            {
                Connection conn = null;
                try
                {
                    conn = EmConnectionHandler.getConnection();
                    String message = "Session Timeout";
                    P11LogWriter p11logWriter = new P11LogWriter(conn);
                    FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
                    Function func = functionHandler.findByFunctionId(getFunctionId());
                    // 画面ログインをする画面からのタイムアウトの場合
                    if (func != null && func.getDoAuthenticationFlag())
                    {
                        p11logWriter.createAccessLog(this, EmConstants.AUTHLOG_CLASS_TIMEOUT, ipAddress, message);
                    }
                    // 画面ログインをしない画面からのタイムアウトの場合
                    else
                    {
                        // 画面ログインをしない場合はシステムログインユーザIDでログを登録する
                        p11logWriter.createAccessLog(this, getSystemLoginUserId(), getSystemLoginUserName(),
                                EmConstants.AUTHLOG_CLASS_TIMEOUT, ipAddress, message);
                    }

                    EmConnectionHandler.commit(conn);
                }
                catch (SQLException sqlException)
                {
                    EmConnectionHandler.rollback(conn);
                    throw sqlException;
                }
                finally
                {
                    EmConnectionHandler.closeConnection(conn);
                }
            }
            // update end

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * <jp>セッションが作成されたことを通知します。<br></jp>
     * <en>Notification that a session was created.<br></en>
     * @param se  <code>HttpSessionEvent</code>
     * @since 2005/06/30 Add By N.Sawa
     */
    @Override
    public void sessionCreated(HttpSessionEvent se)
    {
        //
    }

    /**
     * <jp>セッションが無効になったことを通知します。<br></jp>
     * <en>Notification that a session was invalidated.<br></en>
     * @param se  <code>HttpSessionEvent</code>
     * @since 2005/06/30 Add By N.Sawa
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
        // <jp>コネクション返却用クラスを取得し、close(HttpSession session) メソッドを実行する。</jp>
        // <en>Get the closer for database connection and execute 'close(HttpSession session)' method.</en>
        try
        {
            Object object = Class.forName(getClassName()).newInstance();
            Method method = object.getClass().getMethod("close", new Class[] {
                HttpSession.class
            });
            method.invoke(object, new Object[] {
                se.getSession()
            });
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Session Timeout");
            //e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("Session Timeout");
            //e.printStackTrace();
        }
    }

    /**
     * <jp>コネクション返却用クラス名を取得します。<br></jp>
     * <en>return the connetion closer class name.<br></en>
     * @return <jp>コネクション返却用クラス名<br></jp><en>The connetion closer class name<br></en>
     * @throws IOException
     * @since 2006/06/30 Add By N.Sawa
     */
    @SuppressWarnings("static-method")
    private String getClassName()
            throws IOException
    {
        Properties prop = new Properties();
        InputStream in = null;
        try
        {
            in = (DfkSessionListener.class).getClassLoader().getResourceAsStream(DEFAULT_RESOURCE);

            if (in == null)
            {
                return null;
            }
            prop.load(in);
            return prop.getProperty(CONNECTION_CLOSER_CLASSNAME, null);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }
}
//end of class
