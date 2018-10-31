<%-- $Id: AsShelfMnt.jsp 7534 2010-03-13 08:55:31Z okayama $ --%>
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
 * @version $Revision: 7534 $, $Date: 2010-03-13 17:55:31 +0900 (土, 13 3 2010) $
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
			if (targetElement == "btn_PLocationDetail" || targetElement == "btn_PModify" || targetElement == "btn_PDelete")
			{
				var inputdata = document.getElementById('txt_Location').value;
				var format = document.all.lbl_LocationStyle.innerHTML;

				if (!_isFormatCheck(inputdata,format))
				{
					_setdaMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003103", DispResources.getText("LBL-W0138")))) %>', 2);
				    document.all('txt_Location').focus();
				    return true;
				}
				if (!_isValueInput('txt_Location'))
				{
					document.all('txt_Location').focus();
					// 6003100={0}を入力してください。
					_setdaMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003100", DispResources.getText("LBL-W0138")))) %>', 2);
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
          <TD noWrap><h:tab id="tab_InfomationChange" templateKey="W_InfomationChange"/></TD>
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
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee><h:label id="lbl_Area" templateKey="W_Area"/>&nbsp;&nbsp; </TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif">&nbsp; 
                </TD>
                <TD bgColor=#dad9ee colSpan=7><h:pulldown id="pul_Area" templateKey="W_Area_Event"/></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_LocationStatus" templateKey="W_LocationStatus"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=2><h:checkbox id="chk_EmptyLocation" templateKey="W_EmptyLocation"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:checkbox id="chk_EmptyPBLocation" templateKey="W_EmptyPBLocation"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:checkbox id="chk_StoredLocation" templateKey="W_StoredLocation"/></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_LocationList" templateKey="W_LocationList"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=2><h:submitbutton id="btn_PInquiry" templateKey="W_P_Inquiry"/></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Location" templateKey="W_Location"/><h:label id="lbl_RequireLocation" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=4><h:freetextbox id="txt_Location" templateKey="W_Location"/><h:submitbutton id="btn_PLocationDetail" templateKey="W_P_LocationDetail"/><h:label id="lbl_InputStyleLoc" templateKey="W_InputStyle"/><h:label id="lbl_LocationStyle" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_ProcessFlag" templateKey="W_ProcessFlag"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=2><h:submitbutton id="btn_Submit" templateKey="W_Set5"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_PModify" templateKey="W_P_Modify"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_PDelete" templateKey="W_P_Delete"/></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationAndLocationStatus" templateKey="W_LocationAndLocationStatus"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=2><h:label id="lbl_InJavaSetLocation" templateKey="W_In_JavaSet"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_InJavaSetLocationStatus" templateKey="W_In_JavaSet"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/><h:label id="lbl_RequireItemCode" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=2><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_PSearchItemCode" templateKey="W_P_Search"/></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=5><h:label id="lbl_ItemName" templateKey="W_ItemName"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_ItemName" templateKey="W_ItemName"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SoftZone" templateKey="W_SoftZone"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=7><h:freetextbox id="txt_SoftZoneName" templateKey="W_SoftZoneName"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_EnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_EnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_JanCode" templateKey="W_JanCode"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_JanCode" templateKey="W_JanCode"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseITF" templateKey="W_CaseITF"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_CaseITF" templateKey="W_CaseITF"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotNo" templateKey="W_LotNo"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_LotNo" templateKey="W_LotNo"/>&nbsp;&nbsp; </TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockCaseQty" templateKey="W_StockCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_StockCaseQty" templateKey="W_StockCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockPieceQty" templateKey="W_StockPieceQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_StockPieceQty" templateKey="W_StockPieceQty"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageDate" templateKey="W_StorageDateTime"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=4><h:datetextbox id="txt_StorageDate" templateKey="W_StorageDate"/><h:timetextbox id="txt_StorageTime" templateKey="W_StorageTime"/><h:label id="lbl_InputStyleDate" templateKey="W_InputStyle"/><h:label id="lbl_DateStyle" templateKey="W_InputStyleDate"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Set" templateKey="W_Set2"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
