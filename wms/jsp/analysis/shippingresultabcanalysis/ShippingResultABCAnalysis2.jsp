<%-- $Id: ShippingResultABCAnalysis2.jsp 886 2008-10-29 00:32:22Z tanaka $ --%>
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
 * @version $Revision: 886 $, $Date: 2008-10-29 09:32:22 +0900 (水, 29 10 2008) $
 * @author  $Author: tanaka $
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
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
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
          <TD noWrap><h:tab id="tab" templateKey="W_ABCAnalysis"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_Back" templateKey="W_Back"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_ToMenu" templateKey="To_MenuNoFunc"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_AnalysisTerm" templateKey="W_AnalysisTerm"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_AnaFromDate" templateKey="W_In_JavaSet"/><h:label id="lbl_fromTo" templateKey="W_Range"/><h:label id="lbl_AnaToDate" templateKey="W_In_JavaSet"/></TD>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Customer" templateKey="W_Customer"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap width="100%" bgColor=#dad9ee>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR bgColor=#dad9ee>
                      <TD width="16%" bgColor=#dad9ee><h:label id="lbl_CustomerCode" templateKey="W_In_JavaSet"/></TD>
                      <TD bgColor=#dad9ee><h:label id="lbl_CustomerName" templateKey="W_In_JavaSet"/></TD></TR></TBODY></TABLE></TD>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_AnalysisTypeLabel" templateKey="W_AnalysisType"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_AnalysisType" templateKey="W_In_JavaSet"/></TD>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_ThresholdALabel" templateKey="W_ThresholdA"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee colSpan=6><h:label id="lbl_ThresholdA" templateKey="W_In_JavaSet"/><h:label id="lbl_ThresholdUnitA" templateKey="W_ThresholdUnit"/>&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_ThresholdBLabel" templateKey="W_ThresholdB"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:label id="lbl_ThresholdB" templateKey="W_In_JavaSet"/><h:label id="lbl_ThresholdUnitB" templateKey="W_ThresholdUnit"/></TD></TR>
              <TR bgColor=#dad9ee></TR></TBODY></TABLE>
            <TABLE>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD align=right></TD></TR>
              <TR>
                <TD></TD>
                <TD><h:generalchart id="glc_Chart" templateKey="W_ShipResultABC"/></TD>
                <TD align=right></TD></TR>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD align=right></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD><h:submitbutton id="btn_ListAll" templateKey="W_P_ListAll"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListA" templateKey="W_P_ListA"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListB" templateKey="W_P_ListB"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListC" templateKey="W_P_ListC"/></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
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
