<%@ page contentType="text/html;charset=UTF-8" %>
<meta content="bluedog" name=generator>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>

<%
	String title = (String)request.getAttribute("WindowTitleKey");
	String path = (String)request.getAttribute("PreviewPath");
	if (path == null || path.equals(""))
	{
	    path = request.getParameter("PDFPath");
	}
	if (title == null || title.equals(""))
	{
	    title = request.getParameter("WindowTitleKey");
	}
	// 2011/12/29 Titleを取得できない場合はPathを表示する。
	if(title == null || title.equals(""))
	{
		title = path;
	}
%>
<h:html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="Cache-control"content="no-cache">
  <meta http-equiv="Pragma" content="no-cache">
  <link rel="stylesheet" type="text/css" href="/wms/css/common.css">
  <link rel="stylesheet" type="text/css" href="/wms/css/listcell.css">
  <link rel="stylesheet" type="text/css" href="/wms/css/tool.css">
  <link rel="stylesheet" type="text/css" href="/wms/css/user.css">
  <title><%= title %></title>
</head>

<frameset rows="*" framespacing=0 frameborder="no">
  <frame src="<%= path %>" name=pdf>
</frameset>

</h:html>