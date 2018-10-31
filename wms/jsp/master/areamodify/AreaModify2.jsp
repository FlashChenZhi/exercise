<%-- $Id: AreaModify2.jsp 5886 2009-11-16 00:54:52Z okayama $ --%>
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
 * @version $Revision: 5886 $, $Date: 2009-11-16 09:54:52 +0900 (月, 16 11 2009) $
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
    <TD><%-- title --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE><%-- message --%>
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
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE><%-- tab --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=1 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
      border=0>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7></TD>
          <TD width=7></TD>
          <TD noWrap><h:tab id="tab" templateKey="W_SetModifyDelete2"/></TD>
          <TD width="90%" height=28>　</TD>
          <TD width="90%" height=28><h:submitbutton id="btn_Back" templateKey="W_Back"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ToMenu" templateKey="To_MenuNoFunc"/>　　　　　　　　　　　　</TD>
          <TD></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE><%-- display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InArea" templateKey="W_In_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_AreaType" templateKey="W_AreaType"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InAreaType" templateKey="W_In_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_LocationType" templateKey="W_LocationType"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InLocationType" templateKey="W_In_JavaSet"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_LocationStyle" templateKey="W_LocationStyle"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_InLocationStyle" templateKey="W_In_JavaSet"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_AreaName" templateKey="W_AreaName"/><h:label id="lbl_Require" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_AreaName" templateKey="W_AreaName"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TemporaryArea" templateKey="W_TemporaryArea"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_TemporaryArea" templateKey="W_TemporaryArea"/><h:checkbox id="chk_MoveTemporaryStorage" templateKey="W_MoveTemporaryArea"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_VacantSearchType" templateKey="W_VacantSearchType"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_VacantSearchType" templateKey="W_VacantSearchType"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ReceivingArea" templateKey="W_ReceivingArea"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_ReceivingArea" templateKey="W_ReceivingArea"/><h:checkbox id="chk_ReceivingArea" templateKey="W_ReceivingArea"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Modify" templateKey="W_ModifySet"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Delete" templateKey="W_Delete"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
