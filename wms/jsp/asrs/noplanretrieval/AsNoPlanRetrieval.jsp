<%-- $Id: AsNoPlanRetrieval.jsp 7570 2010-03-15 08:06:33Z okayama $ --%>
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
 * @version $Revision: 7570 $, $Date: 2010-03-15 17:06:33 +0900 (月, 15 3 2010) $
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
          <TD noWrap><h:tab id="tab_NoPlanRetieval" templateKey="W_Set"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE><%-- upper display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD><h:label id="lbl_RetrievalArea" templateKey="W_RetrievalArea"/></TD>
                <TD><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:pulldown id="pul_RetrievalArea" templateKey="W_Area_Event"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#dad9ee height=5>
                <TD><h:label id="lbl_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:linkedpulldown id="pul_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD><h:label id="lbl_Station" templateKey="W_Station"/></TD>
                <TD><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:linkedpulldown id="pul_Station" templateKey="W_Station"/></TD>
                <TD 
                  width="100%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/><h:label id="lbl_RequireItemCode" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/>&nbsp;&nbsp; <h:label id="lbl_LotNo" templateKey="W_LotNo"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_LotNo" templateKey="W_LotNo"/></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalLocation" templateKey="W_RetrievalLocation"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:freetextbox id="txt_FromLocation" templateKey="W_Location"/><h:label id="lbl_RangeRetrievalLocation" templateKey="W_Range"/><h:freetextbox id="txt_ToLocation" templateKey="W_Location"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_In_LocationStyle" templateKey="W_In_JavaSet"/></TD>
                <TD bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_Display"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE>
      <TABLE>
        <TBODY>
        <TR>
          <TD></TD></TR></TBODY></TABLE><%-- lower display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_urt3.gif" 
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Set" templateKey="W_Set2"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ClearListInput" templateKey="W_ClearListInput"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_AllCheck" templateKey="W_AllCheck"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_AllCheckClear" templateKey="W_AllCheckClear"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_AllClear" templateKey="W_AllClear"/></TD>
          <TD bgColor=#b8b7d7><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" 
            width=42 border=0></TD></TR></TBODY></TABLE>
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
                <TD width="100%" bgColor=#b8b7d7><h:checkbox id="chk_LIssueReport" templateKey="W_IssueReport"/>&nbsp;&nbsp; </TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_Item" templateKey="W_Item"/>&nbsp;&nbsp; <IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif">&nbsp;&nbsp; 
                  <h:freetextbox id="txt_LItemCode" templateKey="W_ItemCode"/>&nbsp;&nbsp; <h:freetextbox id="txt_LItemName" templateKey="W_ItemName"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_NoPlanRetrieval" templateKey="W_NoPlanRetrieval"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
