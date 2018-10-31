<%-- $Id: 04_uppertab-lowertab.jsp 87 2008-10-04 03:07:38Z admin $ --%>
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
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
  <TR>
    <TD>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24>&nbsp;</TD>
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
  <%-- tab --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
      border=0>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7></TD>
          <TD width=7></TD>
          <TD noWrap><h:tab id="tab" templateKey="W_WorkDisplay"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- upper display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Station" templateKey="W_Station"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_Station" templateKey="W_Station"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_ReDisplay" templateKey="W_ReDisplayFunc"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE>
      <TABLE>
        <TBODY>
        <TR>
          <TD height=5></TD></TR></TBODY></TABLE>
  <%-- lower display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_urt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Complete" templateKey="W_Complete"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;</TD>
                <TD></TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_PrevWork" templateKey="W_PrevWork"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_NextWork" templateKey="W_NextWork"/></TD></TR></TBODY></TABLE></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR height=1>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7 colSpan=10></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RestWork" templateKey="W_RestWork_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:numbertextbox id="txt_RestWork" templateKey="W_RestWork_Large"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkNo" templateKey="W_WorkNo_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap width="100%" bgColor=#b8b7d7 colSpan=8 rowSpan=2>
                  <TABLE cellSpacing=0 cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_WorkNo" templateKey="W_WorkNo_Large"/>&nbsp;&nbsp;&nbsp;</TD>
                      <TD align="right"><h:label id="lbl_Location" templateKey="W_Location_Large"/></TD>
                      <TD><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                      <TD><h:freetextbox id="txt_Location" templateKey="W_Location_Large"/></TD></TR>
                    <TR>
                      <TD><h:freetextbox id="txt_CarryingFlag" templateKey="W_CarryingFlag_Large"/></TD>
                      <TD><h:label id="lbl_InstructionDetail" templateKey="W_RetrievalInstructionDetail_Large"/></TD>
                      <TD><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                      <TD><h:freetextbox id="txt_InstructionDetail" templateKey="W_RetrievalInstructionDetail_Large"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CarryingFlag" templateKey="W_CarryingFlag_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap width="100%" bgColor=#b8b7d7 colSpan=8></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7 colSpan=10></TD></TR>
              <TR height=2>
                <TD noWrap bgColor=#413a8a colSpan=10></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7 colSpan=10></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode_Large"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap width="100%" bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_ItemName" templateKey="W_ItemName_Large"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotNo" templateKey="W_LotNo_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_LotNo" templateKey="W_LotNo_Large"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockQtyWorkQty" templateKey="W_StockQtyWorkQty_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:numbertextbox id="txt_StockQty" templateKey="W_StockQty_Large"/><h:numbertextbox id="txt_WorkQty" templateKey="W_WorkQty_Large"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageDateTime" templateKey="W_StorageDateTime_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_StorageDate" templateKey="W_Day_Large"/><h:freetextbox id="txt_StorageTime" templateKey="W_Time_Large"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7 colSpan=10></TD></TR>
              <TR height=2>
                <TD noWrap bgColor=#413a8a colSpan=10></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7 colSpan=10></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchNoTicketNo" templateKey="W_BatchNoTicketNo_Large"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8>
                  <TABLE cellSpacing=0 cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_BatchNo" templateKey="W_BatchNo_Large"/><h:freetextbox id="txt_SlipNumber" templateKey="W_SlipNumber_Large"/>&nbsp;&nbsp;&nbsp;</TD>
                      <TD><h:label id="lbl_LineNo" templateKey="W_LineNo_Large"/></TD>
                      <TD><IMG style="WIDTH: 29px; HEIGHT: 34px" height=33 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=31><h:numbertextbox id="txt_LineNo" templateKey="W_LineNo_Large"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7 
        colSpan=10></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
