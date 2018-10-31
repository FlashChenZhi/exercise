//$Id: LoggedUserData.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.authentication;

import java.util.Date;

import javax.servlet.http.HttpSession;

/** 
 * <jp>AccountTableクラスでユーザ情報を保持するためのクラスです。<br></jp>
 * <en>It is the class to hold user information in the AccountTable class. <br></en>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/21</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class LoggedUserData
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /** User ID */
    private String wUserID = null;

    /** Session ID */
    private String wSessionID = null;

    /** IP address */
    private String wIPaddress = null;

    /** Loing date */
    private Date wLoginDate = null;

    /** Last access date */
    private Date wLastAccessDate = null;

    /** HttpSession */
    private HttpSession session = null;

    // Class method --------------------------------------------------
    /** 
     * <jp>このクラスのバージョンを返します。<br></jp>
     * <en>Returns the version of this class and date.<br></en>
     * @return <jp>バージョンと日付<BR></jp><en>version and date.<br></en> 
     */
    public static String getVersion()
    {
        return ("$Revision: 3965 $,$Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * <jp>新しい<code>AccountUserData</code>を構築します。 <br></jp>
     * <en>Create new <code>AccountUserData</code>. <br></en>
     */
    public LoggedUserData()
    {
        super();
    }

    // Public methods ------------------------------------------------

    /**
     * <jp>SessionIDが同一の場合、同じオブジェクトして扱うためオーバライドする。 <br></jp>
     * <en>Overwrite equals method to do the same object and to handle it when SessionID is the same. <br></en>
     * @param o object
     * @return true:equal, false:not equal
     */
    public boolean equals(Object o)
    {
        if (wSessionID.equals(((LoggedUserData)o).wSessionID))
        {
            return true;
        }
        return false;
    }

    /**
     * <jp>UserIDを設定します。 <br></jp>
     * <en>Sets the UserID. <br></en>
     * @param arg UserID
     */
    public void setUserID(String arg)
    {
        this.wUserID = arg;
    }

    /** 
     * <jp>UserIDを取得します。<br></jp>
     * <en>Returns the UserID.<br></en>
     * @return UserID
     */
    public String getUserID()
    {
        return this.wUserID;
    }

    /**
     * <jp>SessionIDを設定します。<br></jp>
     * <en>Sets the SessionID.<br></en>
     * @param arg SessionID
     */
    public void setSessionID(String arg)
    {
        this.wSessionID = arg;
    }

    /**
     * <jp>SessionIDを取得します。<br></jp>
     * <en>Returns the SessionID.<br></en>
     * @return SessionID
     */
    public String getSessionID()
    {
        return this.wSessionID;
    }

    /**
     * <jp>IPaddressを設定します。 <br></jp>
     * <en>Sets the IPaddress. <br></en>
     * @param arg IPaddress
     */
    public void setIPaddress(String arg)
    {
        this.wIPaddress = arg;
    }

    /**
     * <jp>IPaddressを取得します。 <br></jp>
     * <en>Returns the IPaddress. <br></en>
     * @return IPaddress
     */
    public String getIPaddress()
    {
        return this.wIPaddress;
    }

    /**
     * <jp>LoginDateを設定します。 <br></jp>
     * <en>Sets the LoginDate. <br></en>
     * @param arg LoginDate
     */
    public void setLoginDate(Date arg)
    {
        this.wLoginDate = arg;
    }

    /**
     * <jp>LoginDateを取得します。 <br></jp>
     * <en>Returns the LoginDate. <br></en>
     * @return LoginDate
     */
    public Date getLoginDate()
    {
        return this.wLoginDate;
    }

    /**
     * <jp>LastAccessDateを設定します。 <br></jp>
     * <en>Sets the LastAccessDate. <br></en>
     * @param arg LastAccessDate
     */
    public void setLastAccessDate(Date arg)
    {
        this.wLastAccessDate = arg;
    }

    /**
     * <jp>LastAccessDateを取得します。 <br></jp>
     * <en>Returns the LastAccessDate. <br></en>
     * @return LastAccessDate
     */
    public Date getLastAccessDate()
    {
        return this.wLastAccessDate;
    }


    /**
     * <jp>sessionを設定します。 <br></jp>
     * <en>Sets the session. <br></en>
     * @param session
     */
    public void setSession(HttpSession session)
    {
        this.session = session;
    }

    /**
     * <jp>sessionを取得します。 <br></jp>
     * <en>Returns session. <br></en>
     * @return session
     */
    public HttpSession getSession()
    {
        return session;
    }

}
