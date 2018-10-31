<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>Error(例外)が発生したとき表示される画面です。</jp>
 * <en>This screen is displayed when an Error (exception) occurred.</en>
 * <jp>履歴</jp>
 * <en>history</en>
 * <jp>2004/01/29 standard.jscrをincludeしている部分を削除</jp>
 * <en>2004/01/29 The portion which is include standard.jscr is deleted.</en>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
--%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "private"); 
    response.setDateHeader("Expires", 0);
%>
<%-- defined this error page --%>
<%@ page isErrorPage = "true" %>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
	td .stackTrace {
		background-color: #000000;
		color: #FFFFFF;
		font-size: 12px;
	}
</style>
</head>
<body>
<%
	String stackTrace = null;
	try
	{
		StringWriter sw = new StringWriter();
		PrintWriter  pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		stackTrace = sw.toString();
		pw.close();
		sw.close();
	}
	catch (IOException e)
	{
	}
%>
<table>
	<tr>
		<td><pre class="stackTrace"><%= stackTrace %></pre><br></td>
	</tr>
</table>
</body>
</html>