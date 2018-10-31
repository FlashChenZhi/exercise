// $Id: ToolListBoxUtil.java 835 2008-10-28 06:44:01Z okamura $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.servlet.http.HttpSession;

import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionRet;


/**
 * ListBoxに関する処理のUtilityクラスです。<BR>
 * ListBoxのBusinessクラスより使用します。<BR>
 * <BR>
 * Designer : Y.Okamura <BR>
 * Maker    : Y.Okamura <BR>
 * @version $Revision: 835 $, $Date: 2008-10-28 15:44:01 +0900 (火, 28 10 2008) $
 * @author  Last commit: $Author: okamura $
 */
public final class ToolListBoxUtil
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
    private ToolListBoxUtil()
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
     * @param listbox SessionRet
     * @param session セッション情報
     */
    public static void registSessionRet(HttpSession session, ToolSessionRet listbox)
    {
        // 取り残されているセッションがないかチェックする。
        deleteSessionRet(session);

        // Sessionにlistboxを保持
        session.setAttribute("LISTBOX", listbox);

    }

    /**
     * セッション情報より<code>SessionRet</code>クラスを取得します。<BR>
     * キーワードは"LISTBOX"です。
     * 
     * @param session セッション情報
     * @return <code>SessionRet</code>
     */
    public static ToolSessionRet getSessionRet(HttpSession session)
    {
        return (ToolSessionRet)session.getAttribute("LISTBOX");

    }

    /**
     * セッションに取り残された<code>SessionRet</code>クラスとそのコネクションをcloseします。<BR>
     * また、その <code>SessionRet</code>クラスをセッションから削除します。<BR>
     * 
     * @param session セッション情報
     */
    public static void deleteSessionRet(HttpSession session)
    {
        try
        {
            if (session.getAttribute("LISTBOX") instanceof ToolSessionRet)
            {
                //Sessionに取り残されているオブジェクトのコネクションをクローズする
                ToolSessionRet listbox = (ToolSessionRet)session.getAttribute("LISTBOX");
                if (listbox != null)
                {
                    // コネクションのクローズ(同時にfinderもclose）
                    listbox.closeConnection();
                }
                // セッションから削除する
                session.removeAttribute("LISTBOX");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        return "$Id: ToolListBoxUtil.java 835 2008-10-28 06:44:01Z okamura $";
    }
}
