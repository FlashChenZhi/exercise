<%-- $Id: OperationLogList.jsp 6602 2009-12-23 01:16:12Z okayama $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
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
 * @version $Revision: 6602 $, $Date: 2009-12-23 10:16:12 +0900 (水, 23 12 2009) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
</h:head>
<h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
  <TR>
    <TD><%-- header --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG 
            src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" 
            border=0></TD></TR></TBODY></TABLE><%-- title --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD>&nbsp;<h:label id="lbl_SettingName" templateKey="T_In_SettingName"/></TD>
          <TD></TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE><%-- message --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR>
          <TD width=7 height=23></TD>
          <TD width=4 bgColor=white height=23></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="W_LstOperationMsg"/></TD>
          <TD width=7 height=23></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"></TD></TR></TBODY></TABLE><%-- close botton --%><%-- display --%>
      <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 bgColor=#dad9ee 
      border=0>
        <TBODY>
        <TR bgColor=#dad9ee>
          <TD width=7 bgColor=#dad9ee>&nbsp;&nbsp;</TD>
          <TD width=7 bgColor=#dad9ee>&nbsp;&nbsp;</TD>
          <TD bgColor=#dad9ee></TD>
          <TD width=7 bgColor=#dad9ee>&nbsp;&nbsp;</TD></TR>
        <TR bgColor=#dad9ee>
          <TD width=7 bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee><h:listcell id="lst_SearchConditionTwoColumn" templateKey="T_SearchConditionTwoColumn"/></TD>
          <TD width=7 bgColor=#dad9ee></TD></TR>
        <TR bgColor=#dad9ee>
          <TD width=7 bgColor=#dad9ee>&nbsp;</TD>
          <TD bgColor=#dad9ee calSpan="2"></TD>
          <TD width=7 bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7 height=7>
          <TD width=7 bgColor=#dad9ee>&nbsp;</TD>
          <TD width=7 bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD width=7 bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width=7 bgColor=#dad9ee></TD>
          <TD width=7 bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7><h:scrolllistcell id="lst_ScreenControlLogDetails" templateKey="W_ScreenControlLogDetails"/></TD>
          <TD width=7 bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7 height=7>
          <TD width=7 bgColor=#dad9ee>&nbsp;</TD>
          <TD width=7 bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD width=7 bgColor=#dad9ee></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" 
            width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Close" templateKey="W_Close"/></TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD><%-- footer --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" 
            src="<%=request.getContextPath()%>/img/common/logo_dp.gif" 
            width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
