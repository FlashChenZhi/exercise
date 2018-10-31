<%-- $Id: Error.jsp 7996 2011-07-06 00:52:24Z kitamaki $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %>
<%@ page import="jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%@ page import="jp.co.daifuku.emanager.util.EmProperties"%>

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
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <h:head>
	<style>
		font#errmsg {
			color : red;
			font-weight : bold;
		}
	</style>

    </h:head>
    <h:page>
<%
	String ErrorJspMsg1 = DispResources.getText("LBL-T0038");
	String ErrorJspMsg2 = DispResources.getText("LBL-T0037");

	Throwable exception = (Throwable) request.getAttribute(Throwable.class.getName());
	String  errorMsg = exception .getMessage();
	if(errorMsg==null || "".equals(errorMsg))
	{
		errorMsg = exception .getClass().getName();
	}

	/*StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	t.printStackTrace(pw);
	try {
		pw.close();
		sw.close();
	} catch (IOException e) {
	}

	String errorMsg = sw.toString();
	errorMsg = errorMsg.trim();
	int limitPos = 1000;
	if(errorMsg.length() > limitPos)
	{
		int lastPos = errorMsg.lastIndexOf(")" , limitPos);
		if(lastPos == -1)
		{
	lastPos = limitPos;
		}
		errorMsg = errorMsg.substring(0, lastPos+1) + "  and more...";
	}*/

	String contextpath  = request.getContextPath();
	String productName = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME);
	String logoImgPath = contextpath + "/img/project/login/menu_bg-" + productName + ".gif";
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39></TD>
  </TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD  height=5 width=100% background="<%=logoImgPath%>"></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0>
    </TD></TR>

  <TR>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD align=middle width=100% background="<%=logoImgPath%>" height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
              <TBODY>
              <TR >
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD height=12></TD>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR>
              <TR>
                <TD></TD>
                <TD width=800 >
		            <table width=100% border="0" cellspacing="0" cellpadding="0" bgcolor="white">
		              <tr>
		                <td nowrap align="center"><%= ErrorJspMsg1 %><BR><BR><%= ErrorJspMsg2 %><BR></td>
		              </tr>
		              <tr>
		                <td nowrap align="center">
		                	<span class="lbl-error">
		                		<IMG  src="<%=request.getContextPath()%>/img/control/message/Error.gif">  <%= errorMsg %>
		                	</span>
		                </td>
		              </tr>
		            </table>
                </TD>
                <TD></TD>
              </TR>
              <TR >
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD height=12></TD>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                </TR>
              </TBODY></TABLE>
                </TD>
              </TR>
             </TBODY>
             </TABLE></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD height=5 width=100% background="<%=logoImgPath%>" ></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0>
    </TD></TR>

  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG src="<%=request.getContextPath()%>/img/common/logo_menu.gif" border=0>
    </TD></TR>
  <TR>
    <TD
    background=<%=request.getContextPath()%>/img/common/menu_bg2.gif
    colSpan=5 height=7>
    </TD>
  </TR></TBODY></TABLE>
</h:page>
</h:html>
<script>
document.title = "Error";
</script>