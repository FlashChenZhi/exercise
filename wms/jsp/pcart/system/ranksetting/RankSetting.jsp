<%-- $Id: RankSetting.jsp 4080 2009-04-10 09:29:47Z okayama $ --%>
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
 * @version $Revision: 4080 $, $Date: 2009-04-10 18:29:47 +0900 (金, 10 4 2009) $
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
          <TD noWrap><h:tab id="tab" templateKey="W_Set"/></TD>
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
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/><h:label id="lbl_ConsignorRequire" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_Area" templateKey="W_Area"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Display" templateKey="W_Display"/>&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
                <TD><h:submitbutton id="btn_Set" templateKey="W_Set2"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AutoCalculation" templateKey="W_AutoCalculation"/>&nbsp;&nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_ListClear" templateKey="W_ListClear"/></TD></TR></TBODY></TABLE></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>
                  <TABLE style="WIDTH: 582px; HEIGHT: 57px" cellSpacing=0 
                  cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_RankA" templateKey="W_RankA"/>&nbsp;</TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:label id="lbl_PercentRankA" templateKey="W_In_JavaSet"/><h:label id="lbl_PercentA" templateKey="W_Percent"/></TD>
                      <TD>&nbsp;&nbsp; </TD>
                      <TD><h:label id="lbl_RankB" templateKey="W_RankB"/></TD>
                      <TD><IMG height=27 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=24></TD>
                      <TD><h:label id="lbl_PercentRankB" templateKey="W_In_JavaSet"/><h:label id="lbl_PercentB" templateKey="W_Percent"/>&nbsp;</TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_ConsignorCodeLower" templateKey="W_ConsignorCode"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD colSpan=9><h:label id="lbl_ConsignorCodeDisp" templateKey="W_In_JavaSet"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_AreaLower" templateKey="W_Area"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD colSpan=9><h:label id="lbl_AreaDisp" templateKey="W_In_JavaSet"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_LotNumber_h" templateKey="W_LotNumber_h"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_LotPerH" templateKey="W_LotPerHour"/></TD>
                      <TD>&nbsp;&nbsp;&nbsp; </TD>
                      <TD><h:label id="lbl_OrderNumber_h" templateKey="W_OrderNumber_h"/></TD>
                      <TD><IMG height=27 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=24></TD>
                      <TD><h:numbertextbox id="txt_OrderPerH" templateKey="W_OrderPerHour"/></TD>
                      <TD>&nbsp;&nbsp;&nbsp; </TD>
                      <TD><h:label id="lbl_LineNumber_h" templateKey="W_LineNumber_h"/></TD>
                      <TD><IMG height=27 src="<%=request.getContextPath()%>/img/common/icon_colon3.gif" width=24></TD>
                      <TD><h:numbertextbox id="txt_LinePerH" templateKey="W_LinePerHour"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>&nbsp;</TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:listcell id="lst_RankSet" templateKey="W_RankSet"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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