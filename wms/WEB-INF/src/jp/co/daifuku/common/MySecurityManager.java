// $Id: MySecurityManager.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * <CODE>SecurityManager</CODE>オブジェクトを構築します。<BR>
 * あるクラスのあるメソッドをどのクラスが呼び出したかを調べる時に使用できます。
 * <PRE>
 * 例 こんな感じで使用して下さい。
 * System.setSecurityManager(new MySecurityManager()) ;
 * SecurityManager securityManager = System.getSecurityManager() ;
 * Class[] stack = ((MySecurityManager)securityManager).getStackTrace() ;
 * for (int i = 0; i < stack.length; i++)
 * {
 *     System.out.println(stack[i].getName()) ;
 * }
 * 
 * 実行結果例
 * jp.co.daifuku.common.MySecurityManager
 * jp.co.daifuku.common.RmiMsgLogClient
 * jp.co.daifuku.common.RmiMsgLogClient
 * jp.co.daifuku.awc.display.web.WebUI
 * _0002fMessageCtrl_0002ejspMessageCtrl_jsp_1
 * org.apache.jasper.runtime.HttpJspBase
 * javax.servlet.http.HttpServlet
 * org.apache.jasper.servlet.JspServlet$JspServletWrapper
 * org.apache.jasper.servlet.JspServlet
 * org.apache.jasper.servlet.JspServlet
 * javax.servlet.http.HttpServlet
 * org.apache.tomcat.core.ServletWrapper
 * org.apache.tomcat.core.Handler
 * org.apache.tomcat.core.ServletWrapper
 * org.apache.tomcat.core.ContextManager
 * org.apache.tomcat.core.ContextManager
 * org.apache.tomcat.service.http.HttpConnectionHandler
 * org.apache.tomcat.service.TcpWorkerThread
 * org.apache.tomcat.util.ThreadPool$ControlRunnable
 * java.lang.Thread
 * </PRE>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class MySecurityManager extends SecurityManager
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * <CODE>MySecurityManager</CODE>を構築します。
	 */
	public MySecurityManager()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * 現在の例外実行スタックをクラスの配列として返します。<BR>
	 * 配列の長さは、実行スタック上にあるメソッドの数になります。
	 * インデックス 0の要素は現在メソッドを実行中のクラス、インデックス１の要素はそのメソッドの呼び出しクラスとなります。
	 * @return 実行スタック
	 */
	public Class[] getStackTrace()
	{
		return this.getClassContext() ;
    }

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Debug methods -------------------------------------------------

}
//end of class
