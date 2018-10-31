<%-- $Id: SessionTimeout.jsp 7996 2011-07-06 00:52:24Z kitamaki $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>「ログインしていないか、セッションがタイムアウトにより切断されました。」を表示する画面です。</jp>
 * <en>This screen displays the message: "You may not have logged in, or session time out."</en>
 *
 * <jp>[履歴]</jp>
 * <en>[history]</en>
 * <jp>2003/12/02 update sawa 「ログイン画面へ」がListBox（サブウィンドウ）に表示される場合、Targetはopener.parent.windowに修正した。</jp>
 * <en>2003/12/02 update sawa Corrected: When "To login screen" is displayed on the listBox (sub window), Target will be opener.parent.window.</en>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
--%>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "private");
    response.setDateHeader("Expires", 0);
    String contextPath  = request.getContextPath();

	String productName = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME);
	String backgroundImgPath = contextPath + "/img/project/login/menu_bg-" + productName + ".gif";
	String title             = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_TITLE);
%>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix="h" %>
<%-- import --%>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import="jp.co.daifuku.emanager.util.EmProperties"%>
<h:html>
<h:head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css" >
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common_<%= request.getLocale().getLanguage() %>.css" >
</h:head>
<script>
	//<jp> ログアウト画面を表示します。</jp>
	//<jp> 「ログイン画面へ」がメインのウィンドウに表示される場合はTargetは"_parent"になります。</jp>
	//<jp> 「ログイン画面へ」がListBox（サブウィンドウ）に表示される場合はTargetはparent.opener.windowになります。</jp>
	//<en> Logout screen is displayed.</en>
	//<en> When "To login screen" is displayed on main window, Target will be "_parent".</en>
	//<en> When "To login screen" is dispalyed on listBox (sub window), Target will be parent.opener.window.</en>
	function dispLoginPage()
	{
		var myWindowName = window.name;
		var parentFrameName = "_parent";
		try
		{
			parentFrameName = opener.parent.window.name;

			/*
				Muni	--Start
			   if Session time at list box (POPUP) for examle.. Close button at List box is true
			   Session time out message is opend in new popup..  now we have 3 windows (main, list box and session time out window)
			   get grandParent window , main window
			 */
			try
			{
				grandParent = opener.opener.parent.window;
				// if grand parent is available, make parentFrameName as grandparent window name
				if(grandParent)
				{
					parentFrameName = 	grandParent.name;
				}

			}catch(e)
			{
				// if exception , means no three windows
			}
			// Muni --End
			window.close();
		}
		catch(e)
		{
			parentFrameName='_self';
		}
		// 2010/10/15 Mod Start. AsyncErrorWindowのclose処理
		var win = window.open("", "AsyncErrorWindow","width=1,height=1,left=10000,top=10000");
		if (!win.closed)
		{
			win.close();
		}
		// 2010/10/15 Mod End.
		window.open('<%= contextPath + EmProperties.getProperty("LOGOUT_URI") %>', parentFrameName,"width=1,height=1,left=10000,top=10000");
	}

	document.title = "<%=title%>";
</script>
<h:page>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr height="39">
    <td colspan="5" bgcolor="#413a8a" height="39"></td>
  </tr>
  <tr height="251">
    <td bgcolor="#413a8a" width="7" height="251"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="7" height="1" border="0"></td>
    <td align="center" width="100%" height="251" colspan="3" background="<%=backgroundImgPath%>">
      <table border="0" cellspacing="0" cellpadding="1">
        <tr>
          <td bgcolor="#413a8a">
            <table border="0" cellspacing="0" cellpadding="0" bgcolor="white">
              <tr height="12">
                <td width="9" height="12"></td>
                <td height="12"></td>
                <td width="9" height="12"></td>
              </tr>
              <tr>
                <td width="9"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="9" height="1" border="0"></td>
                <td nowrap><span class="lbl-SessionTimeout"><%= DispResources.getText("MSG-T0008") %></span></td>
                <td width="9"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="9" height="1" border="0"></td>
              </tr>
              <tr>
                <td width="9"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="9" height="1" border="0"></td>
                <td nowrap align="center"><a href="javascript:dispLoginPage()"><span class="lbl-SessionTimeout-link"><%= DispResources.getText("MSG-T0009") %></span></a></td>
                <td width="9"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="9" height="1" border="0"></td>
              </tr>
              <tr height="12">
                <td width="9" height="12"></td>
                <td height="12"></td>
                <td width="9" height="12"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td bgcolor="#413a8a" width="7" height="251"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="7" height="1" border="0"></td>
  </tr>
  <tr><td colspan="5" bgcolor="#413a8a" height="39"><img src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width="380" height="24" border="0"></td></tr>
  <tr><td colspan="5" height="7" background="<%=request.getContextPath()%>/img/common/menu_bg2.gif"></td></tr>
</table>
</h:page>
</h:html>
