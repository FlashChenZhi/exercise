<%-- $Id: PCTCustomerProgress.jsp 4235 2009-05-11 06:39:36Z okayama $ --%>
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
 * @version $Revision: 4235 $, $Date: 2009-05-11 15:39:36 +0900 (月, 11 5 2009) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import="jp.co.daifuku.bluedog.util.MessageResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.HTMLUtils"%>
<%@ page import="jp.co.daifuku.bluedog.util.DispResources"%>

<h:html>
<h:head>
	  <script>
		function dynamicAction()
		{
			if (targetElement == "btn_Display" )
			{
				if (!_isValueInput('txt_ConsignorCode'))
				{
					document.all('txt_ConsignorCode').focus();
					// 6003100={0}
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6003100", DispResources.getText("LBL-W1362")) %>',2);
					return true;
				}
				
				if ((document.all('txt_BatchSeqNo').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_BatchSeqNo').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-P0150")))) %>',2);
					return true;
				}

			}
			return false;
		}
	  </script>
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
          height=24></TD>
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
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
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
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=3>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/><h:label id="lbl_Require" templateKey="W_Require"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCodePCT"/>&nbsp; 
                        &nbsp;<h:freetextbox id="txt_ConsignorName" templateKey="T_R_FunctionName"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_txtRegularCustomerCode" templateKey="W_RegularConsignorCode"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_RegularCustomerCode" templateKey="W_RegularCustomerCode"/><h:submitbutton id="btn_P_Search_RegularCustomerCd" templateKey="W_P_Search"/>&nbsp;&nbsp; 
                        <h:freetextbox id="txt_RegularCustomerName" templateKey="T_R_FunctionName"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_AreaNo" templateKey="W_Area"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_1" templateKey="W_BatchNo"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_BatchNoPCT" templateKey="W_BatchNoPCT"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchSeqNo" templateKey="W_BatchSeqNo"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_BatchSeqNo" templateKey="W_BatchSeqNoPCT"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Display" templateKey="W_Display"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_Auto" templateKey="W_ProgressDisplay_Auto"/>&nbsp;&nbsp;&nbsp;&nbsp; 
                        &nbsp;<h:radiobutton id="rdo_Manual" templateKey="W_ProgressDisplay_Manual"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_DisplayCall"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/> 
          </TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE>&nbsp;</TD></TR>
  <TR>
    <TD>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE style="WIDTH: 1130px; HEIGHT: 27px">
              <TBODY>
              <TR>
                <TD><h:scrolllistcell id="lst_PCTCustomerProgress" templateKey="W_PCTCustomerProgress"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
