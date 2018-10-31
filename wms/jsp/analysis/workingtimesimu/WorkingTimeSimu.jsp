<%-- $Id: WorkingTimeSimu.jsp 7560 2010-03-15 05:38:25Z okayama $ --%>
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
 * @version $Revision: 7560 $, $Date: 2010-03-15 14:38:25 +0900 (月, 15 3 2010) $
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
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
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
          <TD noWrap><h:tab id="tab" templateKey="W_WorkSimu"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
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
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SearchObject" templateKey="W_SearchObject"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_SearchPlanDateAll" templateKey="W_SearchPlanDateAll"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:radiobutton id="rdo_SearchPlanDateInput" templateKey="W_SearchPlanDateInput"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkPlanDate" templateKey="W_WorkPlanDate"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_WorkPlanDate" templateKey="W_PlanDate"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_Day" templateKey="W_InputStyleDay"/>&nbsp;&nbsp; <h:checkbox id="chk_BeforePlanDate" templateKey="W_BeforePlanDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:checkbox id="chk_AfterPlanDate" templateKey="W_AfterPlanDate"/></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_Select" templateKey="W_Display"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR>
        <TR>
          <TD noWrap bgColor=#dad9ee colSpan=4 height=6></TD></TR></TBODY></TABLE> 
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD><h:label id="lbl_WorkerNum" templateKey="W_WorkerNum"/> <h:label id="lbl_WorkerNumRequire" templateKey="W_Require"/></TD>
                <TD><h:label id="lbl_WorkStartTime" templateKey="W_WorkStartTime"/> <h:label id="lbl_WorkStartTimeRequire" templateKey="W_Require"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;<h:label id="lbl_PlanItemQty" templateKey="W_PlanItemQty"/></TD>
                <TD><h:label id="lbl_PlanPieceQty" templateKey="W_PlanTotalPieceQty"/></TD>
                <TD><h:label id="lbl_InputItemQty" templateKey="W_InputItemQty"/></TD>
                <TD><h:label id="lbl_InputPieceQty" templateKey="W_InputPieceQty"/></TD>
                <TD><h:label id="lbl_WorkingTime" templateKey="W_WorkingTime"/></TD>
                <TD><h:label id="lbl_WorkEndTime" templateKey="W_WorkEndTime"/>&nbsp;&nbsp; 　</TD>
                <TD></TD></TR>
              <TR>
                <TD><h:label id="lbl_StorageWork" templateKey="W_StorageWork"/></TD>
                <TD>&nbsp; <IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:numbertextbox id="txt_StorageWorkerNum" templateKey="W_WorkerNum"/><h:label id="lbl_StorageUnitPerson" templateKey="W_UnitPerson"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp; <h:timetextbox id="txt_StorageWorkStartTime" templateKey="W_WorkStartTime"/></TD>
                <TD><h:numbertextbox id="txt_StorageItemQtyPlan" templateKey="W_R_ItemQty"/></TD>
                <TD><h:numbertextbox id="txt_StoragePieceQtyPlan" templateKey="W_R_PieceQty"/></TD>
                <TD><h:numbertextbox id="txt_StorageItemQtyInp" templateKey="W_ItemQty"/></TD>
                <TD><h:numbertextbox id="txt_StoragePieceQtyInp" templateKey="W_PieceQty"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:numbertextbox id="txt_StorageWorkingTime" templateKey="W_WorkingTime"/><h:label id="lbl_StorageUnitMinute" templateKey="W_UnitMinute"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp; <h:timetextbox id="txt_StorageWorkEndTime" templateKey="W_WorkEndTime"/></TD>
                <TD>&nbsp;&nbsp; </TD></TR>
              <TR>
                <TD><h:label id="lbl_RetrievalWork" templateKey="W_RetrievalWork"/></TD>
                <TD>&nbsp; <IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:numbertextbox id="txt_RetrievalWorkerNum" templateKey="W_WorkerNum"/><h:label id="lbl_RetrievalUnitPerson" templateKey="W_UnitPerson"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp; <h:timetextbox id="txt_RetrievalWorkStartTime" templateKey="W_WorkStartTime"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalItemQtyPlan" templateKey="W_R_ItemQty"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalPieceQtyPlan" templateKey="W_R_PieceQty"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalItemQtyInp" templateKey="W_ItemQty"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalPieceQtyInp" templateKey="W_PieceQty"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:numbertextbox id="txt_RetrievalWorkingTime" templateKey="W_WorkingTime"/><h:label id="lbl_RetrievalUnitMinute" templateKey="W_UnitMinute"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp; <h:timetextbox id="txt_RetrievalWorkEndTime" templateKey="W_WorkEndTime"/></TD>
                <TD>&nbsp;&nbsp; </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_Simulate" templateKey="W_Simulate"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
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
