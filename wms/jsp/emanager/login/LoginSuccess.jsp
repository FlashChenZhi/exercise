<%-- $Id: LoginSuccess.jsp 8001 2011-08-12 08:46:37Z nagao $ --%>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 *
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/02/13 N.Sawa     created
 *
 * @version $Revision: 8001 $, $Date: 2011-08-12 17:46:37 +0900 (金, 12 8 2011) $
 * @author  $Author: nagao $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%@ page import="jp.co.daifuku.emanager.util.EmProperties"%>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%@ page import="jp.co.daifuku.emanager.JspConstants"%>
<%@ page import="java.util.Date"%>

<%
	String contextPath = request.getContextPath();
	String productName = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME);
	String title             = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_TITLE);
	String imagePath = contextPath + "/img/project/login/menu_bg-" + productName + ".gif";
//	 work date is used only in eWareNavi
	Date workDate= (Date)request.getAttribute(JspConstants.WORKDATE);
%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
<h:head>
</h:head>
<h:page>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=3 height=39></TD></TR>
  <TR height=251>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD align=middle width="100%" background="<%=imagePath%>" height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
              <TBODY>
              <TR height=12>
                <TD colSpan=5 height=12><IMG height=12 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR>
              <TR>
                <TD width=20><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD noWrap align=middle colSpan=3 height=30><h:label id="lbl_Msg01" templateKey="T_InJspMessage"/></TD>
                <TD width=20><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR>
              <TR>
                <TD noWrap align=middle colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0 rules=none width="90%" bgColor=#dad9ee border=1>
                    <TBODY>
                      <TR><TD align=middle>
                        <TABLE>
                          <TR>
                            <TD align=middle colspan="3" height=30><h:label id="lbl_Msg02" templateKey="T_InJspMessage"/></TD></TR>
                          <TR>
                            <TD><h:label id="lbl_TerminalName" templateKey="T_TerminalName"/></TD>
                            <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                            <TD><h:pulldown id="pul_Terminal" templateKey="T_TerminalNumber"/>&nbsp; <h:submitbutton id="btn_Modify" templateKey="T_Modify"/></TD>
                          </TR>
                          <!--   work date is used only in eWareNavi -->
                          <% if (workDate!=null) { %>
	                      <TR>
	                        <TD><h:label id="lbl_WorkDay" templateKey="T_WorkDay"/></TD>
	                        <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
	                        <TD style="text-align:left;"><h:datetextbox id="txt_WorkDay" templateKey="T_WorkDate"/></TD>
	                      </TR>
                        <% } %>
                        </TABLE>
                      </TD></TR>
                      </TBODY></TABLE></TD></TR>
              <TR height=12>
                <TD height=12><IMG height=12 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD>
                <TD colSpan=3></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG height=24 src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width=380 border=0></TD></TR>
  <TR height=7>
    <TD background=<%=request.getContextPath()%>/img/common/menu_bg2.gif colSpan=5 height=7></TD></TR></TBODY></TABLE>

</h:page>
</h:html>
<script>

document.title = "<%=title%>";

function reloadMainMenu()
{
	window.focus();
	parent.frames.frame1.closeButtonCheck = false;
	parent.frames.frame1.location.reload(true);
}

</script>