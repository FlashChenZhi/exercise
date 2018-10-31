<%-- $Id: AutoLogin.jsp 87 2008-10-04 03:07:38Z admin $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="jp.co.daifuku.emanager.util.EmProperties"%>
<%@ page import="jp.co.daifuku.emanager.EmConstants"%>
<%
	String contextPath = request.getContextPath();
	String productName = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME);
	String backgroundImgPath = contextPath + "/img/project/login/menu_bg-" + productName + ".gif";
	String title             = EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_TITLE);
%>
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
<h:head>
</h:head>
<h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39></TD>
  </TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD align=middle width="100%" background="<%=backgroundImgPath%>" height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
            <TBODY>
              <TR>
                <TD height=60></TD>
                <TD width=800 align=center><h:label id="lbl_Message" templateKey="T_InJspMessage"/></TD>
                <TD></TD>
              </TR>
            </TBODY>
            </TABLE>
          </TD>
        </TR>
      </TBODY>
      </TABLE>
    </TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
  </TR>
  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG src="<%=request.getContextPath()%>/img/common/logo_menu.gif" border=0></TD>
  </TR>
  <TR>
    <TD background="<%=request.getContextPath()%>/img/common/menu_bg2.gif" colSpan=5 height=7></TD>
  </TR>
</TBODY>
</TABLE>

</h:page>
</h:html>
<script>
document.title = "<%=title%>";
windowResize();
</script>