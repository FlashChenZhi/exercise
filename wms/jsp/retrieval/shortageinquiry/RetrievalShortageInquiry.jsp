<%-- $Id: RetrievalShortageInquiry.jsp 7161 2010-02-19 09:03:49Z fukuwa $ --%>
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
 * @version $Revision: 7161 $, $Date: 2010-02-19 18:03:49 +0900 (金, 19 2 2010) $
 * @author  $Author: fukuwa $
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
		if (targetElement == "btn_PSearchRetrievalStartDate")
		{
			if (_isValueInput('txt_RetrievalStartTime') && !_isValueInput('txt_RetrievalStartDate'))
			{
				document.all('txt_RetrievalStartDate').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W0353")))) %>',2);
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
    <TD><%-- title --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24></TD>
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
          <TD noWrap><h:tab id="tab" templateKey="W_InquiryPrint"/></TD>
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
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalPlanDate" templateKey="W_RetrievalPlanDate"/><h:label id="lbl_Require" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_RetrievalPlanDate" templateKey="W_PlanDate"/><h:label id="lbl_InputStyle1" templateKey="W_InputStyle"/><h:label id="lbl_Day" templateKey="W_InputStyleDay"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalStartDateTime" templateKey="W_RetrievalStartDateTime"/><h:label id="lbl_Require1" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_RetrievalStartDate" templateKey="W_RetrievalStartDate"/><h:timetextbox id="txt_RetrievalStartTime" templateKey="W_RetrievalStartTime"/><h:submitbutton id="btn_PSearchRetrievalStartDate" templateKey="W_P_Search"/><h:label id="lbl_InputStyle2" templateKey="W_InputStyle"/><h:label id="lbl_DateStyle" templateKey="W_InputStyleDate"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchNo" templateKey="W_BatchNo"/></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=2><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_BatchNo" templateKey="W_BatchNo"/>&nbsp;&nbsp; <h:label id="lbl_OrderNo" templateKey="W_OrderNo"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_OrderNo" templateKey="W_OrderNo"/><h:label id="lbl_Range" templateKey="W_Range"/><h:freetextbox id="txt_OrderNoTo" templateKey="W_OrderNo"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>　</TD>
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
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_Display"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Print" templateKey="W_Print"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_XLS" templateKey="W_XLS"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/> 
          </TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE><%-- lower display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
              <TBODY>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7></TD>
                <TD><h:pager id="pgr_U" templateKey="Pager"/></TD>
                <TD></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD noWrap><h:label id="lbl_Allocate" templateKey="W_AllocatedPattern"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_AllocatedPatternNo" templateKey="W_AllocatedPatternNo"/>&nbsp;&nbsp; <h:freetextbox id="txt_AllocatedPattenName" templateKey="W_AllocatedPatternName"/></TD>
                <TD noWrap colSpan=2></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7><h:listcell id="lst_ShortageCheckList" templateKey="W_ShortageCheckList"/></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7></TD>
                <TD><h:pager id="pgr_D" templateKey="Pager"/></TD>
                <TD></TD></TR></TBODY></TABLE>&nbsp;&nbsp; 
</TD></TR></TBODY></TABLE></TD></TR>
  <TR height=15>
    <TD height=15></TD></TR>
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
