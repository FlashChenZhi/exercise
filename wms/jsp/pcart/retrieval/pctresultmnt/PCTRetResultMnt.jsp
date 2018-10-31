<%-- $Id: PCTRetResultMnt.jsp 3207 2009-03-02 05:30:50Z arai $ --%>
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
 * @version $Revision: 3207 $, $Date: 2009-03-02 14:30:50 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
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
          <TD noWrap><h:tab id="tab" templateKey="W_Progress"/></TD>
          <TD width="90%" height=28><h:tab id="tab_62" templateKey="W_Progress"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_9" templateKey="W_ItemCode"/><h:label id="lbl_RequireConsignor" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ConsignorCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_SearchConsignor" templateKey="T_Search"/></TD>
                      <TD><h:freetextbox id="txt_48" templateKey="W_ItemCode"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_OrderNo" templateKey="W_OrderNo"/><h:label id="lbl_RequireOrderNo" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_OrderNo" templateKey="W_OrderNo"/><h:submitbutton id="btn_SearchOrderNo" templateKey="T_Search"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_11" templateKey="W_LineNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:numbertextbox id="txt_53" templateKey="W_LineNo"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_SearchItemCode" templateKey="T_Search"/></TD>
                      <TD><h:freetextbox id="txt_49" templateKey="W_ItemCode"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_View" templateKey="T_View"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="T_Clear"/>&nbsp;&nbsp;&nbsp; 
            <h:submitbutton id="btn_44" templateKey="T_Search"/></TD>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_ModifySet" templateKey="W_ModifySet"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ListClear" templateKey="W_ListClear"/></TD>
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
                <TD bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_26" templateKey="AST_W_WorkDay"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:datetextbox id="txt_57" templateKey="W_WorkDate"/></TD>
                      <TD>&nbsp;&nbsp; <h:label id="lbl_BatchNo" templateKey="W_BatchNo"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_BatchNo" templateKey="W_BatchNo"/></TD>
                      <TD>&nbsp;&nbsp; <h:label id="lbl_Area" templateKey="W_Area"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_Area" templateKey="W_Area"/></TD>
                      <TD><h:freetextbox id="txt_AreaName" templateKey="W_AreaName"/></TD></TR></TBODY></TABLE>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_60" templateKey="AST_W_WorkDay"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_31" templateKey="W_CustomerCode"/></TD>
                      <TD><h:freetextbox id="txt_32" templateKey="W_CustomerName"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_61" templateKey="AST_W_WorkDay"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_CustomerCode" templateKey="W_CustomerCode"/></TD>
                      <TD><h:freetextbox id="txt_CustomerName" templateKey="W_CustomerName"/></TD></TR></TBODY></TABLE></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:listcell id="lst_8" templateKey="W_RetrievalListMaintenance"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp; &nbsp;</TD>
                <TD bgColor=#b8b7d7></TD>
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
