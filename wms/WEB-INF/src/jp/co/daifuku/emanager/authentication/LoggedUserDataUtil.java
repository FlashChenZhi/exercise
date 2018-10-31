// $Id: LoggedUserDataUtil.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

/** 
 * <jp>システムに対するログイン人数や認証に失敗した回数を保持するクラス
 * アプリケーション（javax.servlet.ServletContext）スコープに保持され、
 * Tomcatの終了でこの情報は削除される。<br></jp>
 *<en>The class which holds the number of times that it failed in the login number to 
 * the system and the authentication. It is held in the application (javax.servlet.ServletContext) scope, 
 * and this information is deleted by the end of Tomcat. <br></en>
 * <br>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/21</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class LoggedUserDataUtil
{
    /** <jp>ユーザ情報を保持する為のリスト &nbsp;&nbsp;</jp><en>List for contain the user information. &nbsp;&nbsp;</en> */
    private static List wUserList;

    // Constructors --------------------------------------------------
    /** 
     * <jp>新しいユーザ情報を保持する為のリストを構築します。<br></jp>
     * <en>Create new list. <br></en>
     */
    static
    {
        wUserList = new Vector();
    }

    // Public methods ------------------------------------------------

    /** 
     * <jp>ログインしたユーザの登録を行います。
     * 同一SessionIDの場合は、そのSessionIDを持つデータを上書きします。<br></jp>
     * <en>The registration of the user who logged in is done.
     * A data class returns true when wSessionID is the same because equals method was overwrited.<br></en>
     * @param user <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param sessionid <jp>セッションID &nbsp;&nbsp;</jp><en>Session ID &nbsp;&nbsp;</en>
     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP Address &nbsp;&nbsp;</en>
     * @param session HttpSession
     */
    public static void addLoginUser(String user, String sessionid, String ipaddress, HttpSession session)
    {
        LoggedUserData data = new LoggedUserData();
        data.setUserID(user);
        data.setSessionID(sessionid);
        data.setIPaddress(ipaddress);

        Date now = new Date();
        data.setLoginDate(now);
        data.setLastAccessDate(now);
        data.setSession(session);
        wUserList.add(data);

    }


    /** 
     * <jp>引数で指定したユーザの登録を削除します。<br></jp>
     * <en>The registration of the user specified in the argument is deleted.<br></en>
     * @param sessionid <jp>セッションID &nbsp;&nbsp;</jp><en>Session ID &nbsp;&nbsp;</en>
     */
    public static void removeLoginUser(String sessionid)
    {
        //Create dummy data for remove.
        LoggedUserData rdata = new LoggedUserData();
        rdata.setSessionID(sessionid);
        wUserList.remove(rdata);
    }

    /** 
     * <jp>引数で指定したユーザのIPAddressを取得します.<br></jp>
     * <en>Return the IPAddress of the user specified in the argument.<br></en>
     * @param sessionid <jp>セッションID &nbsp;&nbsp;</jp><en>Session ID &nbsp;&nbsp;</en>
     * @return  <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP Address &nbsp;&nbsp;</en>
     */
    public static String getIPAddress(String sessionid)
    {
        for (int i = 0; i < wUserList.size(); i++)
        {
            LoggedUserData data = (LoggedUserData)wUserList.get(i);
            if ((data.getSessionID()).equals(sessionid))
            {
                return data.getIPaddress();
            }
        }
        return "";
    }


    /** 
     * <jp>引数で指定したユーザのログイン数を取得します。<br></jp>
     * <en>The number of login of the user specified in the argument is acquired.<br></en>
     * @param user <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @return <jp>ユーザのログイン数<br></jp><en>Number of login user.<br></en>
     */
    public static int getLoginUserCount(String user)
    {

        Iterator itr = wUserList.iterator();
        int count = 0;
        while (itr.hasNext())
        {
            LoggedUserData data = (LoggedUserData)itr.next();
            if (data.getUserID().equals(user))
            {
                ++count;
            }
        }
        return count;
    }

    /** 
     * <jp>ユーザが既にログインしているかどうかチェックします。 <br></jp>
     * <en>This method checks if the given user is logged already. <br></en>
     * @param user <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @return <jp>既にログインしている場合はTRUEを返します。 &nbsp;&nbsp;</jp><en>True if the user is already logged in &nbsp;&nbsp;</en>
     */
    public static boolean checkLoggedUser(String user)
    {

        Iterator itr = wUserList.iterator();
        while (itr.hasNext())
        {
            LoggedUserData data = (LoggedUserData)itr.next();
            if (data.getUserID().equals(user))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * <jp>ログイン数の合計を返します。<br></jp>
     * <en>The total of the number of login is returned.<br></en>
     * @return total
     */
    public static int getAllUserCount()
    {
        return wUserList.size();
    }

    /**
     * <jp>現在ログインしているユーザの一覧をリストで返します。
     * 内部のデータは<code>AccountUserData</code>型です。<br></jp>
     * <en>The list of the user who logs in at present is returned by the list.
     * Inside data are code types <code>AccountUserData</code> .<br></en>
     * @return List
     */
    public static List getUserList()
    {
        return wUserList;
    }
}
