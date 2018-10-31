<%-- $Id: StationList.jsp 1789 2008-12-08 05:39:39Z okamura $ --%>
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
 * @version $Revision: 1789 $, $Date: 2008-12-08 14:39:39 +0900 (月, 08 12 2008) $
 * @author  $Author: okamura $
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
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=1>
          <TD bgColor=#dad9ee colSpan=3 height=1></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR>
        <TR>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#413a8a>&nbsp;<h:label id="lbl_ListName" templateKey="In_SettingName"/></TD>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"><IMG height=10 src="<%=request.getContextPath()%>/img/common/spacer.gif" width="100%" border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%">
  
  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            bgColor=#dad9ee border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#dad9ee>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR></TBODY></TABLE></TD>
          <TD vAlign=bottom align=right>

  <%-- close botton --%>
  <TABLE cellSpacing=0 cellPadding=0 align=right bgColor=#dad9ee 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=10></TD>
                <TD width=7 height=10></TD>
                <TD align=right height=10>
                  <TABLE cellSpacing=0 cellPadding=0 bgColor=#b8b7d7 border=0>
                    <TBODY>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_U" templateKey="W_Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR></TBODY></TABLE></TD>
                <TD width=7 
      height=10></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>

  <%-- List start --%>   
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#b8b7d7 width="100%">
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD align=right bgColor=#b8b7d7><h:pager id="pgr_U" templateKey="Pager"/></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:label id="lbl_InMsg" templateKey="AST_In_ErrorJsp"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_StationList" templateKey="AST_StationList"/></TD>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7>&nbsp;</TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD align=right bgColor=#b8b7d7><h:pager id="pgr_D" templateKey="Pager"/></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR></TBODY></TABLE><BR>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"></TD>
          <TD align=right>

  
  <%-- close botton --%>
  <TABLE cellSpacing=0 cellPadding=0 align=right bgColor=#dad9ee 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=10></TD>
                <TD width=7 height=10></TD>
                <TD align=right height=10>
                  <TABLE cellSpacing=0 cellPadding=0 bgColor=#b8b7d7 border=0>
                    <TBODY>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_D" templateKey="W_Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR></TBODY></TABLE></TD>
                <TD width=7 
        height=10></TD></TR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

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
