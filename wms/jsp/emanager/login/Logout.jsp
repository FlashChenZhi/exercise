<%-- $Id: Logout.jsp 8041 2012-05-17 09:39:40Z nagao $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Logout screen.
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  $Author: nagao $
--%>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private");
	response.setDateHeader("Expires", 0);
	String contextpath  = request.getContextPath();
	session.invalidate(); // 2012/02/06 ログアウト時にセッションを開放していない不具合対応
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<script type="text/javascript">
	// show the screen for the Login authentication.
	function showLogin()
	{
		// search most parent window.
		var pWind = window;
		while( pWind.opener != null        && pWind.opener != "undefined"        && !isClosed(pWind.opener) &&
		       pWind.opener.opener != null && pWind.opener.opener != "undefined" && !isClosed(pWind.opener.opener))
		{
			pWind = pWind.opener;
		}
		if( pWind.opener != null && pWind.opener != "undefined" && !isClosed(pWind.opener))
		{
			pWind = window;
		}
		// Open the main window.
		var mainWindowObject = pWind.open( '<%= contextpath %>/jsp', '_parent');
	}

	// Update for Windows update KB918899
	function isClosed(windowObject)
	{
		try
		{
			return ( windowObject == null || windowObject.closed );
		}
		catch(e)
		{
			return true;
		}
	}
</script>
</head>
<body onLoad="showLogin()" oncontextmenu="return false">
</body>
</html>
