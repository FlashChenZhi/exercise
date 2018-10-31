<%-- $Id: Login.jsp 8001 2011-08-12 08:46:37Z nagao $ --%>
<%--
 * Copyright 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>
<%--
 * @version $Revision: 8001 $, $Date: 2011-08-12 17:46:37 +0900 (金, 12 8 2011) $
 * @author  $Author: nagao $
--%>
<%-- @start page directives --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="jp.co.daifuku.emanager.util.EmProperties"%>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%@ page import="jp.co.daifuku.bluedog.webapp.ResizeToParameters" %>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix="h" %>
<%
	String contextPath		= request.getContextPath();
	String imgPathName		= contextPath + "/img/project/login/";
	String title			= EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_TITLE);
	// ウィンドウズ幅に合わせてスライスした左右の画像の表示幅を変更します(標準サイズは1268)
	int windowWidth			= 1268;
	int sideWidth			= (windowWidth - 416) / 2;
%>
<%-- Simple Login 2011/03/25 T.Sumiyama --%>
<%
String flagstr = request.getParameter( "noimage" );
Cookie cookieFlag = null;
Cookie[] cookies = request.getCookies();
if(cookies != null)
{
	for(Cookie c : cookies)
	{
		if(c != null && c.getName().equals("noimageFlag"))
		{
			cookieFlag = c;
			break;
		}
	}
}
if(flagstr == null)
{
	flagstr = (cookieFlag == null) ?"false" :"true";
}
else
{
	if(cookieFlag==null)
	{
		response.addCookie(new Cookie("noimageFlag","true"));
	}
}
%>
<%-- Simple Login 2011/03/25 T.Sumiyama --%>
<%-- @end page directives --%>
<h:html>
<h:head>
<% if(flagstr.equals("false")){%>
<style>
	/* ページの初期化 */
	body {background:url(<%=imgPathName%>bg.jpg) 0 500px repeat-x #f5f4f9;margin: 0;padding: 0;overflow: auto;}
	div {margin	:0;padding	: 0;}
	/* レイアウト構造 */
	#headerDiv {background	: #241d51;width: 100%;height: 30px;text-align: right;}
	#bodyDiv {height: 100%;}
	#topDiv, #bottomDiv,#bodyDiv {width: 100%;text-align: center;}
	#topDiv {background-color: #000000;height:390px;}
	#bottomDiv {height: 100px;background:url(<%=imgPathName%>bg.jpg) 0 70px repeat-x #000000;}

	/* 表示詳細 */
	#topImgs{background: #000000;}
	#topImgs,#bottomImgs {width: <%=windowWidth%>px;margin: 0 auto;overflow:hidden;}
	#topImgs,#tImg_left, #tImg_right, #tImg_center{height: 390px;}
	#bottomImgs,#bImg_left , #bImg_right,#bImg_center{height: 256px;}

	#tImg_left, #tImg_right, #tImg_center,#bImg_left , #bImg_right,#bImg_center{float: left;}
	#tImg_left, #tImg_right,#bImg_left ,#bImg_right{width: <%=sideWidth%>px;}
	#tImg_center,#bImg_center{width: 415px;}
	#tImg_left {background	: url(<%=imgPathName%>login_tLeft.jpg) right no-repeat;}
	#tImg_center {background: url(<%=imgPathName%>login_tCenter.jpg) no-repeat;}
	#tImg_right {background	: url(<%=imgPathName%>login_tRight.jpg) left no-repeat;}
	#bImg_left {background: url(<%=imgPathName%>login_bLeft.jpg) right no-repeat;}
	#bImg_center {background: url(<%=imgPathName%>login_bCenter.jpg) no-repeat;text-align: center;}
	#bImg_right {background	: url(<%=imgPathName%>login_bRight.jpg) left no-repeat;}
	/*copyright*/
	#copyright{width:100%;text-align:center;font:bold 11px Arial,sans-serif;color:#8d8d8d;visibility:hidden;}
	/*form*/
	#form{width:363px; height:200px;margin:20px auto 0;border:0;}
</style>
<%}%>
</h:head>
<h:page>
<% if(flagstr.equals("false")){%>
<!-- Layout TABLE  -->
	<div id="headerDiv">
		<img src="<%=imgPathName%>head_logo.jpg" style="margin-right:20px;margin-top:5px;">
	</div>
	<div id="topDiv">
		<div id="topImgs">
			<div id="tImg_left" ></div>
			<div id="tImg_center"></div>
			<div id="tImg_right"></div>
		</div>
	</div>
	<div id="bottomDiv">
		<div id="bottomImgs">
			<div id="bImg_left"></div>
			<div id="bImg_center">
				<table id="form">
					<tr>
						<td style="text-align:center;">
							<h:label id="lbl_Message1" templateKey="T_LoginMessage1"/><BR>
							<h:label id="lbl_Message2" templateKey="T_LoginMessage2"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left;padding:0 10px;">
							<span style="box-sizing:border-box;display:inline-block;width:95px;">
								<h:label id="lbl_LoginID" templateKey="T_LoginUserID"/>
							</span>
							<h:freetextbox id="txt_LoginID" templateKey="T_LoginUserID"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left;padding:0 10px;">
							<span style="box-sizing:border-box;display:inline-block;width:95px;">
								<h:label id="lbl_Password" templateKey="T_LoginPassword"/>
							</span>
							<h:freetextbox id="txt_Password" templateKey="T_LoginPassword"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:center;">
							<h:submitbutton id="btn_Login" templateKey="T_Login"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<h:imagebutton id="btn_ChangePassword" templateKey="T_ChangePassword"/>
						</td>
					</tr>
					<tr>
						<td>
							<span id="copyright">WareNavi 3.5 Copyright&copy; 2011 DAIFUKU CO., Ltd. All rights reserved.</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="bImg_right"></div>
		</div>
	</div>
<%}else{ %>
	<div id="__LAYOUT_WRAP">
		<h:label id="lbl_Message1" templateKey="LoginMessage1"/>
		<h:label id="lbl_Message2" templateKey="LoginMessage2"/>
		<h:label id="lbl_LoginID" templateKey="A_UserID"/>
		<h:freetextbox id="txt_LoginID" templateKey="A_UserID"/>
		<h:label id="lbl_Password" templateKey="Password"/>
		<h:freetextbox id="txt_Password" templateKey="Password"/>
		<h:submitbutton id="btn_Login" templateKey="Login"/>
		<h:imagebutton id="btn_ChangePassword" templateKey="T_ChangePassword"/>
	</div>
<% }%>
</h:page>
</h:html>
<script>
	<%-- Simple Login 2011/03/25 T.Sumiyama --%>
	var noimageFlag = <%=flagstr %>;
	if(noimageFlag)
	{
		var IDS = ["lbl_Message1","lbl_Message2","lbl_LoginID","txt_LoginID","lbl_Password","txt_Password","btn_Login","btn_ChangePassword"];
		var HTML = "<span style=\"font:bold 120% monospace;\">For Remote Maintenance</span><table id=\"INPUT_FORM\">";
		for(i=0;i<IDS.length;i++)
		{
			var ele = document.getElementById(IDS[i]);
			if(ele!=null)
			{
				if(IDS[i].indexOf("lbl_") >= 0) ele.style.color = "black";
				HTML = HTML + "<tr id=\"" + IDS[i] + " _row\"><td style=\"padding:2px 5px;\">" + ele.outerHTML + "</td></tr>";
			}
		}
		HTML = HTML + "</table><input class=\"btn-001\" id=\"btn_sessionDelete\" type=\"button\" value=\"Default Screen\" onclick=\"clearSendFlag();postBack('btn_sessionDelete','click');\">"
		var layoutTable = document.getElementById("__LAYOUT_WRAP");
		layoutTable.outerHTML = HTML;
		var formTable = document.getElementById("INPUT_FORM");
		formTable.style.background = "#b0add3";
		document.body.style.background = "#dad9ee";
		document.body.style.textAlign = "center";
		document.title = "For Remote Maintenance";
	}
	else
	{
		<%-- Simple Login 2011/03/25 T.Sumiyama --%>
		window.document.title = "<%=title %>";
		var idLbl = document.getElementById("lbl_LoginID");
		var passLbl = document.getElementById("lbl_Password");
		var msg1Lbl = document.getElementById("lbl_Message1");
		var msg2Lbl = document.getElementById("lbl_Message2");
		idLbl.style.color = "black";
		passLbl.style.color = "black";
		msg1Lbl.style.color = "black";
		msg2Lbl.style.color = "black";
	}
	windowResize();
</script>