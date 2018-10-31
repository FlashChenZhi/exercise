<%-- $Id: RFTState2.jsp 3481 2009-03-16 02:25:33Z okayama $ --%>
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
 * @version $Revision: 3481 $, $Date: 2009-03-16 11:25:33 +0900 (月, 16 3 2009) $
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
    <TD>
  <%-- header --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR>
        <TR height=1>
          <TD bgColor=#dad9ee height=1></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD></TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE>
  <%-- message --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR>
          <TD width=7 height=23></TD>
          <TD width=4 bgColor=white height=23></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="OperationMsg"/></TD>
          <TD width=7 height=23></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"></TD></TR></TBODY></TABLE>
  <%-- close botton --%>


  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#dad9ee height=5>
          <TD bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee></TD></TR>
        <TR bgColor=#dad9ee>
          <TD noWrap bgColor=#dad9ee><h:label id="lbl_RFTNo" templateKey="W_PcartNo"/></TD>
          <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InRftNo" templateKey="W_In_JavaSet"/></TD></TR>
        <TR bgColor=#dad9ee>
          <TD noWrap bgColor=#dad9ee><h:label id="lbl_TerminalFlag" templateKey="W_TerminalFlag"/></TD>
          <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InTerminalType" templateKey="W_In_JavaSet"/></TD></TR>
        <TR>
          <TD noWrap bgColor=#dad9ee><h:label id="lbl_RFTStatus" templateKey="W_Status"/></TD>
          <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InRftStatus" templateKey="W_In_JavaSet"/></TD></TR>
        <TR>
          <TD noWrap bgColor=#dad9ee><h:label id="lbl_UserName" templateKey="W_UserName"/></TD>
          <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InUserName" templateKey="W_In_JavaSet"/></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_HalfWork" templateKey="W_HalfWork"/></TD>
          <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_HalfWork_Cancel" templateKey="W_HalfWork_Cancel"/></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Submit" templateKey="W_Set2"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_ReDisplay" templateKey="W_ReDisplayFunc"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Close_U" templateKey="W_Close"/> 
                </TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD>

<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
