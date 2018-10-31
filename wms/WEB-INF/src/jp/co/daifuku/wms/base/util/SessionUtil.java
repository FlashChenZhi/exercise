package jp.co.daifuku.wms.base.util;

//$Id: SessionUtil.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.servlet.http.HttpSession;

import jp.co.daifuku.wms.base.dbhandler.ConnectionCloser;


/**
 * Sessionに関する処理のUtilityクラスです。<BR>
 * <BR>
 * Designer : Y.Okamura <BR>
 * Maker    : Y.Okamura <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Last commit: $Author: admin $
 */
public final class SessionUtil
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * staticメソッドのみ持ちます。
     */
    private SessionUtil()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * セッションに取り残された<code>SessionRet</code>クラスとそのコネクションをcloseします。<BR>
     * また、その <code>SessionRet</code>クラスをセッションから削除します。<BR>
     * 
     * @param session セッション情報
     */
    public static void deleteSession(HttpSession session)
    {
        ConnectionCloser cc = new ConnectionCloser();
        cc.close(session);

    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: SessionUtil.java 87 2008-10-04 03:07:38Z admin $";
    }
}
