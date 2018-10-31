<%-- $Id: PCTRetPlanWorkInquiry.jsp 3520 2009-03-16 08:40:25Z okayama $ --%>
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
 * @version $Revision: 3520 $, $Date: 2009-03-16 17:40:25 +0900 (月, 16 3 2009) $
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
		if (targetElement == "btn_P_Display")
		{
			if (!_isValueInput('txt_ConsignorCode'))
			{
				document.all('txt_ConsignorCode').focus();
				// 6003100={0}を入力してください。
                   _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1362")))) %>',2);
				return true;
			}

			if ((document.all('txt_ConsignorCode').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_ConsignorCode').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W1362")))) %>',2);
				return true;
			}

			if ((document.all('txt_BatchNo').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_BatchNo').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0224")))) %>',2);
				return true;
			}

			if ((document.all('txt_BatchSeqNo').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_BatchSeqNo').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-P0150")))) %>',2);
				return true;
			}

			if ((document.all('txt_RegularCustomerCode').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_RegularCustomerCode').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-P0050")))) %>',2);
				return true;
			}

			if ((document.all('txt_CustomerCode').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_CustomerCode').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0192")))) %>',2);
				return true;
			}

			if ((document.all('txt_OrderNo').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_OrderNo').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0225")))) %>',2);
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
          <TD noWrap></TD>
          <TD width="90%" height=28><h:tab id="tab" templateKey="W_Inquiry2"/></TD>
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
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/><h:label id="lbl_Require" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCodePCT"/>&nbsp;&nbsp;&nbsp;<h:freetextbox id="txt_ConsignorName" templateKey="W_ConsignorName"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_PlanDay" templateKey="W_PlanDay"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_PlanDay" templateKey="W_PlanDate"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchNo" templateKey="W_BatchNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_BatchNo" templateKey="W_BatchNoPCT"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchSeqNo" templateKey="W_BatchSeqNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_BatchSeqNo" templateKey="W_BatchSeqNoPCT"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_AreaNo" templateKey="W_RetrievalArea"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_txtRegularCustomerCode" templateKey="W_RegularConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_RegularCustomerCode" templateKey="W_RegularCustomerCode"/><h:submitbutton id="btn_P_Search_RegularCustomerCd" templateKey="W_P_Search"/>&nbsp; 
                  &nbsp;<h:freetextbox id="txt_RegularCustomerName" templateKey="W_RegularCustomerName"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerCode" templateKey="W_CustomerCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_CustomerCode" templateKey="W_CustomerCode"/><h:submitbutton id="btn_P_Search_CustomerCode" templateKey="W_P_Search"/>&nbsp;&nbsp; 
                  <h:freetextbox id="txt_CustomerName" templateKey="W_CustomerName"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_OrderNo" templateKey="W_OrderNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_OrderNo" templateKey="W_OrderNoPCT"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkStatus" templateKey="W_WorkStatus"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_WorkStatus" templateKey="W_WorkStatusPCT3"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_P_Display" templateKey="W_P_Display"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Print" templateKey="W_Print"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_XLS" templateKey="W_XLS"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/> 
          </TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
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
