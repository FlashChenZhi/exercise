<%-- $Id: MainMenuB.jsp 7996 2011-07-06 00:52:24Z kitamaki $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import = "jp.co.daifuku.emanager.util.EmProperties"%>
<%@ page import = "jp.co.daifuku.emanager.JspConstants"%>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private");
	response.setDateHeader("Expires", 0);

	String contextpath  = request.getContextPath();

	int mainmenuCount = Integer.parseInt((String)request.getAttribute(JspConstants.RPARAMKEY_MAINMENUCOUNT));
	boolean displayLogoutBtnFlag = Boolean.valueOf((String)request.getAttribute(JspConstants.RPARAMKEY_LOGOUTBUTTON)).booleanValue();
	boolean displayLogViewFlag = Boolean.valueOf((String)request.getAttribute(JspConstants.RPARAMKEY_LOGVIEW_DISPLAY)).booleanValue();
	String userName = (String)request.getAttribute(JspConstants.RPARAMKEY_LOGINUSERNAME);
	String mainMenuHtml = (String)request.getAttribute(JspConstants.RPARAMKEY_MAINMENUHTML);

	String productName = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME);
	String logoImgPath = contextpath + "/img/project/menu/typeb/logo_product-" + productName + ".gif";
%>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Main menu screen.
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/08/20 N.Sawa     created
 * 2005/06/20 N.Sawa     for BlueDOG V3.4.6
 * 2006/12/31 K.Fukumori for eMnager
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8">
		<meta http-equiv="imagetoolbar" content="no">
		<meta http-equiv="Cache-control"content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/common.css">
		<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/listcell.css">
		<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/tool.css">
		<link rel="stylesheet" type="text/css" href="<%= contextpath %>/css/user.css">
		<title></title>
		<script language="javascript" src="<%= contextpath %>/jscript/jquery.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/jcarousellite.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/jquery.mousewheel.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/common.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/user.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/xmlHttpRequest.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/xmlHttpRequestProgress.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/autocomplete.js"></script>
		<script language="javascript" src="<%= contextpath %>/jscript/bdmsg.js"></script>
		<script language="JavaScript" src="<%= contextpath %>/jscript/cookie.js"></script>
		<script language="JavaScript" src="<%= contextpath %>/jscript/dynamicaction.js"></script>
    <script>
    setContextPath("<%= contextpath %>");
    // TODO
    setMenuCount(<%= mainmenuCount %>);

    var wSubmitFlag=true;
    function openSubMenu(id)
    {

        var subMenuURL = "<%= contextpath %>/menu/SubMenu.do?<%=JspConstants.RPARAMKEY_MAINMENUID%>=" + id;
        window.open(subMenuURL, "frame2");
        setTimeout("wSubmitFlag=true;", 500);
    }


    function openWindow(argurl,target)
    {
    	var url = "<%= contextpath %>" + argurl;
		var sWidth  = screen.availWidth;
		var sHeight = screen.availHeight;
		var w  = 1013;
		var h  = 730;
		if( w > sWidth ) { w = sWidth }
		if( h > sHeight ) { h = sHeight }
		var x = (sWidth  - w) / 2;
		var y = (sHeight - h) / 2;
    	window.open(url, target ,"width=" + w + ",height=" + h + ",top=" + y + ",left= " + x + ",resizable=yes,scrollbars=yes");

    }
    </script>
    <style>
    body {
      background-color : #241e4a;
      background-image : url("<%= contextpath %>/img/project/menu/typeb/bg_img.gif");
    }
    </style>
    </head>
    <h:page>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="201"><img src="<%=logoImgPath%>" width="201" height="77" border="0"></td>

<!-- MainMenuStart -->
<%=mainMenuHtml%>
<!-- MainMenuEnd -->

			</tr>
			</table>
			</td>
			<td align="right" valign="bottom">
			<table border="0" cellpadding="0" cellspacing="0">

		<!-- Log View Start------------------------------------------------>
			<tr><td nowrap align="right" colspan="4">
			  <a href = "#__DUMMY_TARGET"><img id="logView" src="<%= contextpath %>/img/project/menu/typeb/icon_log.gif" border="0" onclick="openLogWindow()"></a>
			  <img src="<%= contextpath %>/img/common/spacer.gif" width="5" height="1" border="0"></td><td></td></tr>
			<tr><td colspan="5"><img src="<%= contextpath %>/img/common/spacer.gif" width="1" height="10" border="0"></td></tr>
		<!-- Log View End ------------------------------------------------>

			<tr><td nowrap><div class="lbl-white-003"><%=DispResources.getText("LBL-T0060")%>&nbsp;:&nbsp;</div></td>
				<td>
					<table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccad2">
					<tr>
						<td>
						<table border="0" cellspacing="0" cellpadding="0" bgcolor="#241e4a" height="22">
						<tr>
							<td width="8"></td>
							<td nowrap><span id=username class="lbl-white-004"><%=userName%></span></td>
							<td width="8"></td>
						</tr>
						</table>
						</td>
					</tr>
					</table>
				</td>
				<td width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td>
					<h:submitbutton id="btn_Logout" templateKey="T_Logout" />
				</td>

				<td width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="5"><td colspan="5"><img src="<%= contextpath %>/img/common/spacer.gif" width="1" height="5" border="0"></td></tr>
			</table>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</h:page>
<script>

function openLogWindow()
{
	window.open('<%= contextpath %>/log/logview/LogView.do','pop_logview','toolbar=no,directories=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=no');
}

function doOnload()
{
	window.focus();
}

// For close session
var closeButtonCheck = true;
function doOnunload()
{
	if(closeButtonCheck == true)
	{
		// Mod Start. 2010/11/04 ×ボタンクリック時のセッションの開放対策
		window.open('<%= contextpath + EmProperties.getProperty("SESSIONINVALIDATE_URI")%>', '',"resizable=yes,scrollbars=yes,width=1px,height=1px,top=10000,left=10000");
		// location.href='<%= contextpath + EmProperties.getProperty("SESSIONINVALIDATE_URI")%>';
		// Mod End. 2010/11/04 ×ボタンクリック時のセッションの開放対策
	}
	else if(  self.closed ||	(event.clientX <= -8000 && event.clientY <= -9000) || (event.clientX >= 31000 && event.clientY >= 32000) )
	{
		window.open('<%= contextpath + EmProperties.getProperty("SESSIONINVALIDATE_URI")%>', '',"resizable=yes,scrollbars=yes");
 	}
}

var enterOrders = new Array();
var currentFocusElementId = "";
window.document.onkeydown = doOnkeydown;
function doOnkeydown()
{
    // Enter key with Focus Moving
    if ( event.keyCode == 13 )
    {
        if ( event.srcElement.focus  && enterOrders.length > 0 )
        {
            var nextElement = getNextEnterOrderElement(event.srcElement.id, event.shiftKey );
            if ( nextElement != null )
            {
                setTimeout("setFoucusTimer('"+nextElement.id+"')",1 );
                return false;
            }
        }
    }

    // click Function key
    //Ignore Function key and shortcut key event
    if(ignoreKeyEvent() == false) return false;
    return true;
}
document.onhelp=_onhelp;
function _onhelp(){event.returnValue=false};
function setFoucusTimer( elemId )
{
    currentFocusElementId = elemId;
    document.all(elemId).focus();
}

function postBack()
{
    var frm  = document.forms[0];
    var args = postBack.arguments;
    var eventArgs = "";
    if ( args == null || args.length < 2 )
    {
        return;
    }
    for ( var i = 1; i < args.length ; i++ )
    {
        if (i!=1)
        {
            eventArgs += ",";
        }
        eventArgs += args[i];
    }
    if (!canSend(args[0], eventArgs))
    {
        return;
    }
    document.all("__EVENTTARGET").value = args[0];
    document.all("__EVENTARGS").value = eventArgs;
    frm.target = "_self";
    frm.submit();
}

var _tempTarget = "";
var _tempArgs   = "";
function canSend(target, args)
{
    if (_tempTarget == target && _tempArgs == args)
    {
        return false;
    }
    _tempTarget = target;
    _tempArgs   = args;
    return true;
}

function getNextEnterOrderElement( id, prev )
{
    var nextIndex = -1;
    for ( var i = 0; i < enterOrders.length ; i ++ )
    {
        if ( enterOrders[i] == id )
        {
            nextIndex = i;
            break;
        }
    }
    if ( nextIndex < 0 )
    {
        return getNextEnterOrderElement( currentFocusElementId, prev );
    }
    if ( !prev )
    {
        nextIndex++;
        if ( nextIndex >= enterOrders.length )
        {
            nextIndex = 0;
        }
    }
    else
    {
        nextIndex--;
        if ( nextIndex < 0 )
        {
            nextIndex = enterOrders.length-1;
        }
    }
    return document.all(enterOrders[nextIndex]);
}

// Hide the logout button
hideLogoutButton(<%=displayLogoutBtnFlag%>);
function hideLogoutButton(flag)
{
	if(flag == false)
	{
		document.getElementById('btn_Logout').style.display = "none";
	}
}

// Hide the logview icon
hideLoView(<%=displayLogViewFlag%>);
function hideLoView(flag)
{
	if(flag == false)
	{
		document.getElementById('logView').style.display = "none";
	}
}

// Called by logout button click.
function dynamicAction()
{
	closeButtonCheck = false;
}

// Set window title
setWindowTitle();
function setWindowTitle()
{
	if(window.parent != null)
	{
		window.parent.document.title = "<%=EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_TITLE) %>";
	}
}

</script>
</h:html>
