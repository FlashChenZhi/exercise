<%-- $Id: ShippingResultABCAnalysis.jsp 7134 2010-02-18 07:39:31Z shibamoto $ --%>
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
 * @version $Revision: 7134 $, $Date: 2010-02-18 16:39:31 +0900 (木, 18 2 2010) $
 * @author  $Author: shibamoto $
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
		if (targetElement == "btn_ListAll" || targetElement == "btn_ListA" || targetElement == "btn_ListB" || targetElement == "btn_ListC")
		{
			if (!_isValueInput('txt_AnaFromDate')) 
			{
				document.all('txt_AnaFromDate').focus();
				// LBL-W1201=???
				// 6003100={0}??B
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1201")))) %>',2);
				return true;
			}
			if (!_isValueInput('txt_AnaToDate')) 
			{
				document.all('txt_AnaToDate').focus();
				// LBL-W1201=???
				// 6003100={0}??B
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1201")))) %>',2);
				return true;
			}
			if (!_isValueInput('txt_ThresholdA')) 
			{
				document.all('txt_ThresholdA').focus();
				// LBL-W1213=ANl
				// 6003100={0}??B
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1213")))) %>',2);
				return true;
			}
			if (!_isValueInput('txt_ThresholdB')) 
			{
				document.all('txt_ThresholdB').focus();
				// LBL-W1214=BNl
				// 6003100={0}??B
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1214")))) %>',2);
				return true;
			}
			if (!_isCorrectRange(document.all('txt_ThresholdA').value, document.all('txt_ThresholdB').value))
			{
				document.all('txt_ThresholdB').focus();
				// 6023602=ANl?ABNl?l??B
				_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6023602") %>',2);
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6023602"))) %>',2);
				return true;
			}
			if ((document.all('txt_CustomerCode').value).indexOf("*", 0) >= 0)
			{
				document.all('txt_CustomerCode').focus();
				// LBL-W0192=o?R[h
				// 6003101={0}??gp??????B
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0192")))) %>',2);
				return true;
			}
			return false;
		}
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
          <TD noWrap><h:tab id="tab" templateKey="W_ABCAnalysis"/></TD>
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
          <TD></TD></TR>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_AnalysisTerm" templateKey="W_AnalysisTerm"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_AnalysisTermRequire" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_AnaFromDate" templateKey="W_AnalysisDate"/><h:label id="lbl_fromTo" templateKey="W_Range"/>&nbsp;<h:datetextbox id="txt_AnaToDate" templateKey="W_AnalysisDate"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_Day" templateKey="W_InputStyleDay"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerCode" templateKey="W_CustomerCode"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_CustomerCode" templateKey="W_CustomerCode"/><h:submitbutton id="btn_CustomerSearch" templateKey="W_P_Search"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_CustomerName" templateKey="W_CustomerName"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_AnalysisType" templateKey="W_AnalysisType"/></TD>
                <TD></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_WorkingCnt" templateKey="W_WorkingCnt"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_ShippingCnt" templateKey="W_ShippingCnt"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ThresholdA" templateKey="W_ThresholdA"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ThresholdARequire" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_ThresholdA" templateKey="W_ThresholdA"/><h:label id="lbl_ThresholdUnitA" templateKey="W_ThresholdUnit"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ThresholdB" templateKey="W_ThresholdB"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ThresholdBRequire" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_ThresholdB" templateKey="W_ThresholdB"/><h:label id="lbl_ThresholdUnitB" templateKey="W_ThresholdUnit"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_Next" templateKey="W_NextGraph"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListAll" templateKey="W_P_ListAll"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListA" templateKey="W_P_ListA"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListB" templateKey="W_P_ListB"/> 
                  &nbsp;&nbsp;<h:submitbutton id="btn_ListC" templateKey="W_P_ListC"/></TD></TR></TBODY></TABLE></TD>
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
