// $Id: ConnectionCloser.java 6701 2010-01-19 02:30:01Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.dbhandler;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.da.DataAccessSCH;

/**
 * <jp> ・ログアウトボタンクリック<BR>
 * ・セッションのタイムアウト<BR>
 * ・IEウィンドウ「×」でウィンドウが閉じられたとき<BR>
 * これらのセッションタイムアウト・イベントでコネクションをコネクションプールに返却します。<BR>
 * 返却後、SessionRetをセッションから削除します。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2005/06/30</TD>
 * <TD>N.Sawa</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 6701 $, $Date: 2010-01-19 11:30:01 +0900 (火, 19 1 2010) $
 * @author $Author: okamura $ </jp>
 */
/**
 * <en> A connection is returned at the time of the session time-out. <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2005/06/30</TD>
 * <TD>N.Sawa</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 6701 $, $Date: 2010-01-19 11:30:01 +0900 (火, 19 1 2010) $
 * @author $Author: okamura $ </en>
 */
public final class ConnectionCloser
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * <jp> このクラスのバージョンを返します。
     * 
     * @return バージョンと日付 </jp>
     */
    /**
     * <en> Returns the version of this class and date.
     * 
     * @return - version and date. </en>
     */
    public static String getVersion()
    {
        return ("$Revision: 6701 $,$Date: 2010-01-19 11:30:01 +0900 (火, 19 1 2010) $");
    }

    /**
     * <jp> 返却されていないコネクションをコネクションプールに返し、セッションから削除します。<BR>
     * 
     * @param session
     *            <code>HttpSession</code> </jp>
     */
    /**
     * <en> A connection is returned at the time of the session time-out.
     * 
     * @param session
     *            <code>HttpSession</code> </en>
     */
    @SuppressWarnings("unchecked")
    public void close(HttpSession session)
    {
        try
        {
            for (Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements();)
            {
                String name = e.nextElement();
                Object obj = session.getAttribute(name);
                if (obj instanceof DataAccessSCH)
                {
                    DataAccessSCH dasch = (DataAccessSCH)obj;
                    if (dasch != null)
                    {
                        // コネクションのクローズ(同時にfinderもclose）
                        dasch.close();
                        DBUtil.close(dasch.getConnection());
                    }
                    // セッションから削除する
                    session.removeAttribute(name);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
// end of class
