<%-- $Id: XDPlanRegist2.jsp 2866 2009-01-21 06:02:04Z rnakai $ --%>
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
 * @version $Revision: 2866 $, $Date: 2009-01-21 15:02:04 +0900 (水, 21 1 2009) $
 * @author  $Author: rnakai $
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
          <TD noWrap><h:tab id="tab_SetRegist2" templateKey="W_SetRegist2"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_Back" templateKey="W_Back"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
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
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_PlanDate" templateKey="W_PlanDate"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InPlanDate" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_BatchNo" templateKey="W_BatchNo"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=5><h:label id="lbl_InBatchNo" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_ReceivingSlipNo" templateKey="W_ReceivingSlipNo"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InReceivingSlipNo" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_ReceivingSlipLineNo" templateKey="W_ReceivingSlipLineNo"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=5><h:label id="lbl_InReceivingSlipLineNo" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Supplier" templateKey="W_Supplier"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InSupplierCode" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee colSpan=7><h:label id="lbl_InSupplierName" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Item" templateKey="W_Item"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InItemCode" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee colSpan=7><h:label id="lbl_InItemName" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_EnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InEnteringQty" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_JanCode" templateKey="W_JanCode"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_InJanCode" templateKey="W_In_JavaSet"/>&nbsp;&nbsp; </TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_CaseITF" templateKey="W_CaseITF"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=2><h:label id="lbl_InCaseITF" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_LotNo" templateKey="W_LotNo"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=8><h:label id="lbl_InLotNo" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_PlanCaseQty" templateKey="W_PlanCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_PlanCaseQty" templateKey="W_WorkCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_PlanPieceQty" templateKey="W_PlanPieceQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=5><h:numbertextbox id="txt_PlanPieceQty" templateKey="W_WorkPieceQty"/>&nbsp;&nbsp;&nbsp;</TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShipSlipNo" templateKey="W_ShipSlipNo"/><h:label id="lbl_RequireShipSlipNo" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_ShipSlipNumber" templateKey="W_SlipNumberKeyEvent"/>&nbsp;&nbsp;&nbsp;</TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShipSlipLineNo" templateKey="W_ShipSlipLineNo"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=5><h:numbertextbox id="txt_ShipSlipLineNo" templateKey="W_LineNo"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerCode" templateKey="W_CustomerCode"/><h:label id="lbl_RequireCustomerCode" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_CustomerCode" templateKey="W_CustomerCode"/><h:submitbutton id="btn_PSearch" templateKey="W_P_Search"/>&nbsp;&nbsp; 
                  <h:label id="lbl_CustomerName" templateKey="W_CustomerName"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SortPlace" templateKey="W_SortPlace"/><h:label id="lbl_RequireSortPlace" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_SortPlace" templateKey="W_SortPlace"/></TD>
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
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Input" templateKey="W_Input"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Set" templateKey="W_Set"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AllClear" templateKey="W_AllClear"/></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7>&nbsp;</TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_TcPlanDataRegist" templateKey="W_TcPlanDataRegist"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
