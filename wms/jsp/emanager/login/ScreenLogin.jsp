<%-- $Id: ScreenLogin.jsp 7996 2011-07-06 00:52:24Z kitamaki $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Screen Login screen.
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
--%>
<%-- @start page directives --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix="h" %>
<%
	String contextPath = request.getContextPath();
	String ypos = "194";
	String backgroundImgPath = contextPath + "/img/project/login/sauth_bg.gif";
	String backgroundImgPath2 = contextPath + "/img/project/login/sauth2_bg.gif";

%>
<%-- @end page directives --%>
<h:html>
<h:head>
</h:head>
<h:page>
<!-- Layout TABLE  -->
<TABLE cellspacing="0" cellpadding="0" width="100%" height="60%" border="0" background="<%=backgroundImgPath%>">
<TBODY>
  <TR>
    <TD valign="top" align="middle">
      <!-- Main Field-->
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
      <TBODY>
        <TR>
          <TD align="middle" style="background-repeat:no-repeat;background-position:50% 0%;" >
            <!-- Input Area -->
            <TABLE cellspacing="0" cellpadding="0" border="0" aligh="center"  >
            <TBODY>
              <TR height="<%=ypos %>"><!-- spacer -->
                <TD colspan="5"></TD>
              </TR>
              <TR height="42"><!-- message label -->
                <TD width="330"></TD>
                <TD nowrap="nowrap" align="middle" colspan="3">
                  <h:label id="lbl_Message1" templateKey="T_LoginMessage1"/><BR>
                  <h:label id="lbl_Message2" templateKey="T_LoginMessage2"/>
                </TD>
                <TD width="330"></TD>
              </TR>
              <TR height="40"><!-- user id -->
                <TD width="330"></TD>
                <TD nowrap="nowrap"><h:label id="lbl_LoginID" templateKey="T_LoginUserID"/></TD>
                <TD><SPAN class="lbl-white-002">:</SPAN></TD>
                <TD><h:freetextbox id="txt_LoginID" templateKey="T_LoginUserID"/></TD>
                <TD width="330"></TD>
              </TR>
              <TR height="40"><!-- password -->
                <TD width="330"></TD>
                <TD nowrap="nowrap"><h:label id="lbl_Password" templateKey="T_LoginPassword"/></TD>
                <TD><SPAN class="lbl-white-002">:</SPAN></TD>
                <TD><h:freetextbox id="txt_Password" templateKey="T_LoginPassword"/></TD>
                <TD width="330"></TD>
              </TR>
              <TR height="42"><!-- button -->
                <TD width="330"></TD>
                <TD colspan="3" align="middle">
                  <h:submitbutton id="btn_Login" templateKey="T_Login"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <h:imagebutton id="btn_ChangePassword" templateKey="T_ChangePassword"/>
                </TD>
                <TD width="330"></TD>
              </TR>
              <TR height="100%"><!-- spacer -->
                <TD width="330"></TD>
                <TD colspan="3" width="330" height="165"></TD>
                <TD width="330"></TD>
              </TR>
            </TBODY>
            </TABLE>
          </TD>
        </TR>
      </TBODY>
      </TABLE>
    </TD>
  </TR>
    <TR height=80>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG height=24 src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width=380 border=0></TD></TR>
  <TR height=7>
    <TD background=<%=request.getContextPath()%>/img/common/menu_bg2.gif colSpan=5 height=7></TD></TR>
</TBODY>
</TABLE>

</h:page>
</h:html>
<script>
function changeFrame(uri)
{
	window.parent.location.href = uri;
}
</script>