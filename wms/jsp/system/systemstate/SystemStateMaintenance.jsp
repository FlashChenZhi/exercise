<%-- $Id: SystemStateMaintenance.jsp 6029 2009-11-18 04:22:03Z fukuwa $ --%>
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
 * @version $Revision: 6029 $, $Date: 2009-11-18 13:22:03 +0900 (水, 18 11 2009) $
 * @author  $Author: fukuwa $
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
            width=309 border=0></TD></TR>
        <TR height=1>
          <TD bgColor=#dad9ee height=1></TD></TR></TBODY></TABLE><%-- title --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR bgColor=#413a8a height=4>
          <TD width=7></TD>
          <TD height=4><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD></TR>
        <TR bgColor=#413a8a height=4>
          <TD width=7 colSpan=2></TD></TR></TBODY></TABLE><%-- message area --%>
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
          <TD colSpan=4 height=4></TD></TR></TBODY></TABLE><%-- display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=5></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee colSpan=5></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee colSpan=5></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_DailyProcess" templateKey="W_DailyProcess"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_InDailyProcess" templateKey="W_InProcessState"/><h:submitbutton id="btn_DailyProcess" templateKey="W_NoProcess"/></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=2></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LoadPlanData" templateKey="W_LoadPlanData"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_InLoadPlanData" templateKey="W_InProcessState"/><h:submitbutton id="btn_LoadPlanData" templateKey="W_NoProcess"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CreateReportData" templateKey="W_CreateReportData"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=5><h:freetextbox id="txt_InCreateReportData" templateKey="W_InProcessState"/><h:submitbutton id="btn_CreateReportData" templateKey="W_NoProcess"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalAllocate" templateKey="W_RetrievalAllocate"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=5><h:freetextbox id="txt_InRetrievalAllocate" templateKey="W_R_SystemMnt"/><h:submitbutton id="btn_RetrievalAllocate" templateKey="W_NoProcess"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_AllocationClear" templateKey="W_AllocationClear"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=5><h:freetextbox id="txt_InAllocationClear" templateKey="W_R_SystemMnt"/><h:submitbutton id="btn_AllocationClear" templateKey="W_NoProcess"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_HostCommunication" templateKey="W_HostCommunication"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=5><h:radiobutton id="rdo_HostCommunication_Effectiv" templateKey="W_HostCommunication_Effective"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:radiobutton id="rdo_HostCommunication_Invalidi" templateKey="W_HostCommunication_Invalidity"/><h:submitbutton id="btn_Set" templateKey="W_Set2"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_ReDisplay" templateKey="W_ReDisplayFunc"/>&nbsp; 
            &nbsp;<h:submitbutton id="btn_Close" templateKey="W_Close"/></TD>
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
