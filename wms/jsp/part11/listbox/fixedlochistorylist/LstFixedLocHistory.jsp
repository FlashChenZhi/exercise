<%-- $Id: LstFixedLocHistory.jsp 7707 2010-03-20 07:23:11Z okayama $ --%>
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
 * 2007/03/06 T.Ogawa    created
 *
 * @version $Revision: 7707 $, $Date: 2010-03-20 16:23:11 +0900 (土, 20 3 2010) $
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
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" width=309 border=0></TD></TR>
        <TR height=1>
          <TD bgColor=#dad9ee height=1></TD></TR></TBODY></TABLE>

        <%-- title --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR bgColor=#413a8a height=4>
          <TD width=7></TD>
          <TD height=4><h:label id="lbl_ListName" templateKey="In_SettingName"/></TD></TR>
        <TR bgColor=#413a8a height=4>
          <TD width=7 colSpan=2></TD></TR></TBODY></TABLE>

        <%-- message area --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR height=23>
          <TD width=7></TD>
          <TD width=4 bgColor=white></TD>
          <TD bgColor=white><h:message id="message" templateKey="W_LstOperationMsg"/></TD>
          <TD width=7></TD></TR>
        <TR>
          <TD colSpan=4 height=4></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee
        border=0><TBODY>
        <TR>
          <TD>&nbsp;&nbsp;&nbsp;</TD>
          <TD>
                <%-- search condition --%>
                <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee
            border=0>
              <TBODY>
              <TR height=14>
                <TD></TD></TR>
              <TR>
                <TD><h:listcell id="lst_SearchCondition" templateKey="SearchConditionTwoColumn"/></TD></TR>
              <TR height=7>
                <TD></TD></TR></TBODY></TABLE><BR>
                <%-- list start --%>
		        <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0
            bgColor=#dad9ee border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD>&nbsp;&nbsp;&nbsp;</TD>
                <TD>
                  <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0
                  bgColor=#dad9ee border=0>
                    <TBODY>
                    <TR bgColor=#b8b7d7 height=7>
                      <TD>&nbsp;</TD></TR>
                          <%-- pager --%>
                          <TR bgColor=#b8b7d7>
                      <TD>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:pager id="pgr_U" templateKey="Pager"/></TD>
                            <TD>&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                              &nbsp;&nbsp; &nbsp;&nbsp;
                    </TD></TR></TBODY></TABLE></TD></TR>
                    <TR bgColor=#b8b7d7 height=7>
                      <TD>&nbsp;</TD></TR></TBODY></TABLE>
                  <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0
                  bgColor=#dad9ee border=0>
                    <TBODY>
                          <%-- listcell --%>
                          <TR bgColor=#b8b7d7>
                      <TD><h:listcell id="lst_FixedLocHistoryList" templateKey="W_FixedLocHistoryList"/></TD></TR></TBODY></TABLE>
                  <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0
                  bgColor=#dad9ee border=0>
                    <TBODY>
                    <TR bgColor=#b8b7d7 height=7>
                      <TD>&nbsp;</TD></TR>
                         <%-- pager --%>
                          <TR bgColor=#b8b7d7>
                      <TD>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:pager id="pgr_D" templateKey="Pager"/></TD>
                            <TD>&nbsp;&nbsp;
                  </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR></TD>
                <TD>&nbsp;&nbsp;&nbsp;</TD></TR></TBODY></TABLE>
            <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0
border=0>
              <TBODY>
              <TR>
                <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
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
                      <TD></TD></TR></TBODY></TABLE>&nbsp;&nbsp;<h:submitbutton id="btn_Close_D" templateKey="Close"/>&nbsp;&nbsp;
                </TD>
                <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD>
          <TD>&nbsp;&nbsp;&nbsp;</TD></TR></TBODY></TABLE>

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