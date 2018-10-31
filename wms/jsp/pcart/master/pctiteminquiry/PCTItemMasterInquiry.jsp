<%-- $Id: PCTItemMasterInquiry.jsp 4238 2009-05-11 07:05:50Z okayama $ --%>
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
 * @version $Revision: 4238 $, $Date: 2009-05-11 16:05:50 +0900 (月, 11 5 2009) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import="jp.co.daifuku.bluedog.util.MessageResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.DispResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.HTMLUtils"%>

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
					// 6003100={0}を入力してください。
                    _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W1362")))) %>',2);
					return true;
				}
				
				if ((document.all('txt_ItemCode1').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_ItemCode1').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0482")))) %>',2);
					return true;
				}

				if ((document.all('txt_ItemCode2').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_ItemCode2').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0483")))) %>',2);
					return true;
				}

				if ((document.all('txt_JanCode').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_JanCode').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0193")))) %>',2);
					return true;
				}

				if ((document.all('txt_CaseITF').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_CaseITFerNo').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0194")))) %>',2);
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
          <TD noWrap><h:tab id="tab_Inquiry2" templateKey="W_Inquiry2"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%">
            <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            width="98%" bgColor=#b8b7d7 border=0>
              <TBODY>
              <TR>
                <TD>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR bgColor=#dad9ee>
                      <TD noWrap bgColor=#dad9ee></TD>
                      <TD bgColor=#dad9ee colSpan=2></TD>
                      <TD width="100%" bgColor=#dad9ee></TD></TR>
                    <TR bgColor=#b8b7d7>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/> </TD>
                      <TD bgColor=#b8b7d7><h:label id="lbl_Require" templateKey="W_Require"/></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/></TD></TR>
                    <TR bgColor=#b8b7d7>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode1" templateKey="W_ItemCode"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode1" templateKey="W_ItemCode"/><h:submitbutton id="btn_SearchItemCode" templateKey="W_P_Search"/><h:label id="lbl_Range" templateKey="W_Range"/><h:freetextbox id="txt_ItemCode2" templateKey="W_ItemCode"/><h:submitbutton id="btn_SearchToItemCode" templateKey="W_P_Search"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotEnteringQty" templateKey="W_LotEnteringQty"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_LotEnteringQty" templateKey="W_LotEnteringQty"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_JanCode" templateKey="W_JanCode"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_JanCode" templateKey="W_JanCode"/><h:submitbutton id="btn_SearchJanCode" templateKey="W_P_Search"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseITF" templateKey="W_CaseITF"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_CaseITF" templateKey="W_CaseITF"/><h:submitbutton id="btn_SearchCaseITF" templateKey="W_P_Search"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UnitWeight" templateKey="W_UnitWeight"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_FromUnitWeight" templateKey="W_UnitWeight"/><h:label id="lbl_13" templateKey="W_Range"/><h:numbertextbox id="txt_ToUnitWeight" templateKey="W_UnitWeight"/></TD></TR>
                    <TR>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemImageSet" templateKey="W_ItemImageSet"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_ItemImageSet" templateKey="W_ItemImageSet"/></TD></TR>
                    <TR bgColor=#b8b7d7>
                      <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo" templateKey="W_LocationNo"/></TD>
                      <TD bgColor=#b8b7d7></TD>
                      <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD width="100%" bgColor=#b8b7d7><h:formattextbox id="txt_FromLocationNo" templateKey="W_LocationNo"/><h:label id="lbl_17" templateKey="W_Range"/><h:formattextbox id="txt_ToLocationNo" templateKey="W_LocationNo"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

  <%-- display --%>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_P_Display"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Print" templateKey="W_Print"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_XLS" templateKey="W_XLS"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="W_Clear"/> 
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
