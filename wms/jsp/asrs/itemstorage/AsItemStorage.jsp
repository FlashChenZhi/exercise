<%-- $Id: AsItemStorage.jsp 7134 2010-02-18 07:39:31Z shibamoto $ --%>
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
        if (targetElement == "btn_PSearchStoragePlan" || targetElement == "btn_Input")
        {
            if ((document.all('txt_ItemCode').value).indexOf("*", 0) >= 0)
            {
                document.all('txt_ItemCode').focus();
                _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0189")))) %>',2);
                return true;
            }
            if ((document.all('txt_StoragePlanLotNo').value).indexOf("*", 0) >= 0)
            {
                document.all('txt_StoragePlanLotNo').focus();
                _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0273")))) %>',2);
                return true;
            }
            if (targetElement == "btn_Input")
            {
                if ((document.all('txt_StorageLotNo').value).indexOf("*", 0) >= 0)
                {
                    document.all('txt_StorageLotNo').focus();
                    _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0274")))) %>',2);
                    return true;
                }
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
          <TD noWrap><h:tab id="tab_Set" templateKey="W_Set"/></TD>
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
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=7><h:pulldown id="pul_Area" templateKey="W_Area"/>&nbsp;&nbsp; <h:label id="lbl_Zone" templateKey="W_Zone"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_Zone" templateKey="W_Zone"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD noWrap bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#dad9ee colSpan=7><h:linkedpulldown id="pul_WorkPlace" templateKey="W_WorkPlace"/>&nbsp;&nbsp; 
                  <h:label id="lbl_Station" templateKey="W_Station"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_Station" templateKey="W_Station"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StoregePlanDate" templateKey="W_StoregePlanDate"/><h:label id="lbl_RequireStoragePlanDate" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=2><h:datetextbox id="txt_StoragePlanDate" templateKey="W_PlanDate"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_Day" templateKey="W_InputStyleDay"/>&nbsp;&nbsp; </TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/><h:label id="lbl_RequireItemCode" templateKey="W_Require"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; <h:label id="lbl_StoragePlanLotNo" templateKey="W_StoragePlanLotNo"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=2><h:freetextbox id="txt_StoragePlanLotNo" templateKey="W_StoragePlanLotNo"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:submitbutton id="btn_PSearchPlan" templateKey="W_P_SearchPlan"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_EnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_InEnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; <h:label id="lbl_RestCaseQty" templateKey="W_RestCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_InRestCaseQty" templateKey="W_RemainingCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; <h:label id="lbl_RestPieceQty" templateKey="W_RestPieceQty"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_InRestPieceQty" templateKey="W_RemainingPieceQty"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageLotNo" templateKey="W_StorageLotNo"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_StorageLotNo" templateKey="W_StorageLotNo"/></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; <h:label id="lbl_StorageCaseQty" templateKey="W_StorageCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD noWrap bgColor=#b8b7d7><h:numbertextbox id="txt_StorageCaseQty" templateKey="W_StockCaseQty"/></TD>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; <h:label id="lbl_StoragePieceQty" templateKey="W_StoragePieceQty"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_StoragePieceQty" templateKey="W_StockPieceQty"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Input" templateKey="W_Input"/>&nbsp; 
            &nbsp;<h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_StorageStart" templateKey="W_Set2"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AllClear" templateKey="W_AllClear"/> 
          </TD>
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
                <TD width="100%" bgColor=#b8b7d7><h:checkbox id="chk_IssueReport" templateKey="W_IssueReport"/>&nbsp;&nbsp; <h:label id="lbl_LSoftZone" templateKey="W_SoftZone"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_LSoftZone" templateKey="W_SoftZone"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_ASRSStorageSet" templateKey="W_ASRSStorageSet"/></TD></TR>
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
