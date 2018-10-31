<!-- $Id: index.jsp 8001 2011-08-12 08:46:37Z nagao $ -->

<!--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
-->

<!--
 * welcome page
 * @version $Revision: 8001 $, $Date: 2011-08-12 17:46:37 +0900 (金, 12 8 2011) $
 * @author  $Author: nagao $
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="jp.co.daifuku.emanager.database.handler.EmHandlerFactory"%>
<%@page import="jp.co.daifuku.emanager.database.handler.EmConnectionHandler"%>
<%@page import="jp.co.daifuku.emanager.util.EmProperties"%>
<%@page import="jp.co.daifuku.emanager.EmConstants"%>
<%@page import="jp.co.daifuku.emanager.database.entity.Terminal"%>
<%@page import="jp.co.daifuku.emanager.util.EManagerUtil"%>
<%@page import="jp.co.daifuku.emanager.database.entity.AuthenticationSystem"%>

<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%
	String contextpath  = request.getContextPath();
	String id           = session.getId();
	String uri          = contextpath + EmProperties.getProperty(EmConstants.EMPARAMKEY_DEFAULTLOGIN);
	String lang   	    = request.getLocale().getLanguage();

	// Get the login type
	String requestLoginType = (String)request.getParameter("logintype");
	if(requestLoginType != null)
	{
		// Get the login type from request.
		if("auto".equals(requestLoginType))
		{
	uri = contextpath + EmProperties.getProperty(EmConstants.EMPARAMKEY_AUTOLOGINURI);
		}
		else if("login".equals(requestLoginType))
		{
	uri = contextpath + EmProperties.getProperty(EmConstants.EMPARAMKEY_LOGINURI);
		}
	}
	else
	{
		// Get the login type from database.
		Connection conn = null;
		try
		{
	conn = EmConnectionHandler.getConnection();
	String ipAddress = request.getRemoteAddr();

	Terminal terminalEntity = EmHandlerFactory.getTerminalHandler(conn).findByAddress(ipAddress);
	AuthenticationSystem authEntity = EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();
	if(terminalEntity == null)
	{
		String hostname = EManagerUtil.getHostnameByIpAddress(ipAddress);
		terminalEntity = EmHandlerFactory.getTerminalHandler(conn).findByAddress(hostname);
	}

	if(terminalEntity == null || authEntity ==null)
	{
		terminalEntity = EmHandlerFactory.getTerminalHandler(conn).findByAddress(EmConstants.UNDEFINED_TERMINAL);
	}

	if(terminalEntity != null || authEntity !=null)
	{
		boolean autoLoginEnabled = authEntity.getAutoLoginFlag();
		boolean autoLoginType = terminalEntity.getAutoLoginFlag();

		if(autoLoginType == true && autoLoginEnabled == true)
		{
			uri = contextpath + EmProperties.getProperty(EmConstants.EMPARAMKEY_AUTOLOGINURI);
		}
		else
		{
			uri = contextpath + EmProperties.getProperty(EmConstants.EMPARAMKEY_LOGINURI);
		}
	}

		}
		catch(SQLException e)
		{
		}
		catch(Exception e)
		{
	throw e;
		}
		finally
		{
	EmConnectionHandler.closeConnection(conn);
		}
	}
%>
<%
	String flag = request.getParameter( "noimage" );
	if(flag != null && flag.equals("true"))
	{
		uri = uri + "?noimage=true";
	}
%>
<h:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css; charset=utf-8">
<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
<script language="javascript" src="<%= contextpath %>/jscript/user.js"></script>
<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/common.css" >
<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/common_<%= lang %>.css" >
<script>
<!--
	// the main window object
	var mainWindowObject = 0;
	// the main window target
	var mainWindowName   ="wnd_daifuku<%= id %>";

	// show the screen for the Login authentication.
	function showLogin()
	{
		if ( mainWindowObject == 0 )
		{
		}
		else
		{
			mainWindowObject.close();
			mainWindowObject = 0;
		}
		// 2011/01/19 Mod Start. <jp>AsyncErrorWindowのclose処理</jp>
		var win = window.open("", "AsyncErrorWindow","width=1,height=1,left=10000,top=10000");
		if (!win.closed)
		{
			win.close();
		}
		// 2011/01/19 Mod End.
		// 2011/01/19 Mod Start. <jp>pop_logviewのclose処理</jp>
		var winLog = window.open("", "pop_logview","width=1,height=1,left=10000,top=10000");
		if (!winLog.closed)
		{
			winLog.close();
		}
		// 2011/01/19 Mod End.
		// Open the main window.
		mainWindowObject = window.open( '<%= uri %>', mainWindowName,'resizable=yes');

		if( window.name != mainWindowName )
		{
			// The window should close itself.
			var wind=window.open("","_self");
			window.opener = true;
			wind.close();
		}
	}

	// Key operation is invalidated.
	function getKey()
	{
		// Ctrl + N
		if (window.event.ctrlKey)
		{
			if (window.event.keyCode == '78')
			{
				return false;
			}
		}
	}
	window.document.onkeydown = getKey;
-->
</script>
</head>
<body onload="showLogin()" onContextMenu="return false">
</body>
</h:html>
<script>
<!--
	windowResize();
-->
</script>