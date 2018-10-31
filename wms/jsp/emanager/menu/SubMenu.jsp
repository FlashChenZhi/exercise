<%-- $Id: SubMenu.jsp 7996 2011-07-06 00:52:24Z kitamaki $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page errorPage   = "/jsp/emanager/error/DebugError.jsp" %>
<%@ page import = "jp.co.daifuku.emanager.JspConstants"%>
<%@ page import = "jp.co.daifuku.bluedog.util.LocaleUtils"%>
<%@ page import = "java.util.Locale"%>
<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private");
	response.setDateHeader("Expires", 0);
	String contextpath  = request.getContextPath();
	String menutitle = (String)request.getAttribute(JspConstants.RPARAMKEY_SUBMENUTITLE);
	String subMenuHtml = (String)request.getAttribute(JspConstants.RPARAMKEY_SUBMENUHTML);
// 2008/12/19 K.Matsuda Start
	String userName = (String)request.getAttribute(JspConstants.RPARAMKEY_LOGINUSERNAME);
// 2008/12/19 K.Matsuda End
	Locale loc = LocaleUtils.fullLocale(request.getLocale());
	String fullLocale = loc.getLanguage() + "_" + loc.getCountry();
%>
<meta content="bluedog" name=generator>
<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Sub menu screen.
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/08/20 N.Sawa     created
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<html>
<head>
<%-- <link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/common.css" > --%>
<%-- <link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/common_<%= fullLocale %>.css" > --%>
<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/tool.css" >
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<%-- script --%>
<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
<script type="text/javascript">
	window.document.onkeydown = ignoreKeyEvent;
	var sendable = true;
	function openWindow()
	{
		if (sendable)
		{
			sendable = false;
			window.open(arguments[0], arguments[1]);
		}
	}
// 2008/12/19 K.Matsuda Start
	try{window.parent.frame1.document.getElementById('username').innerHTML='<%=userName%>';}catch(e){alert(e.message)}
// 2008/12/19 K.Matsuda End
// 2009/08/26 Add F1無効対応
	document.onhelp=_onhelp;
	function _onhelp(){event.returnValue=false};
// 2009/08/26 End F1無効対応
</script>
</head>
<body leftmargin="0" marginheight="0" marginwidth="0" topmargin="0" oncontextmenu="return false">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr height="6"><td colspan="16" height="6">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr height="6">
	    <td colspan="16" height="6" >
		  <table bgcolor="#413a8a" width="100%" border="0" cellspacing="0" cellpadding="0">
		    <tr>
			  <td width="7"></td>
			  <td><span class="lbl-menuTitle"><%= menutitle %></span></td>
			  <td align="right"></td>
			  <td width="7"></td></tr>
		    <tr height="2">
		      <td colspan="4" height="4"></td></tr></table></td></tr>
		<tr height="6"><td height="6" background="<%= contextpath %>/img/common/bg_up.gif"></td></tr></table></td></tr>
<tr><td>
<!-- SubMenuStart -->
<%=subMenuHtml %>
<!-- SubMenuEnd --></td></tr>
<tr height="6">
  <td colspan="16" height="6" >
    <TABLE WIDTH="100%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
      <TR HEIGHT="41">
	    <TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="41"><IMG SRC="<%= contextpath %>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
	    <TD BGCOLOR="#DAD9EE" HEIGHT="41"><IMG SRC="<%= contextpath %>/img/common/logo_dp.gif" ALT="" WIDTH="374" HEIGHT="23" BORDER="0"></TD></TR>
	  <TR HEIGHT="8">
	    <TD WIDTH="7" HEIGHT="8" BACKGROUND="<%= contextpath %>/img/common/bg_end.gif"></TD>
	    <TD HEIGHT="8" BACKGROUND="<%= contextpath %>/img/common/bg_end.gif"></TD></TR></TABLE></td></tr>
</table>
</body>
</html>