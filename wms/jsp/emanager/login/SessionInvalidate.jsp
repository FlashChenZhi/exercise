<!-- $Id: SessionInvalidate.jsp 8001 2011-08-12 08:46:37Z nagao $ -->

<!--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
-->

<%@ page contentType="text/html; charset=utf-8" %>
<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="Content-Style-Type" content="text/css; charset=utf-8">
	<script>
	function sessionInvalidate()
	{
		<%
			session.invalidate();
		%>
		// Mod Start. 2010/11/11
		window.opener = 0;
		window.open("","_self");
		window.close();
		// Mod End. 2010/11/11
	}
	</script>
</head>
<body onload="sessionInvalidate()" onContextMenu="return false">
</body>
</html>