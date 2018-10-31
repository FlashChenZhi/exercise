<%-- $Id: PCTUserResultInquiry.jsp 4435 2009-06-15 03:08:58Z okayama $ --%>
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
 * @version $Revision: 4435 $, $Date: 2009-06-15 12:08:58 +0900 (月, 15 6 2009) $
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
			if (targetElement == "btn_Display")
			{
				if ((document.all('txt_ConsignorCode').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_ConsignorCode').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-P0015")))) %>',2);
					return true;
				}
				if ((document.all('txt_BatchNo').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_BatchNo').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6003101", DispResources.getText("LBL-W0224")))) %>',2);
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
          <TD noWrap><h:tab id="tab" templateKey="W_Inquiry2"/></TD>
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
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkDay" templateKey="W_WorkDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_WorkDate" templateKey="W_WorkDate"/><h:label id="lbl_Range" templateKey="W_Range"/><h:datetextbox id="txt_ToWorkDate" templateKey="W_WorkDate"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_DateRange" templateKey="W_SelectWeekDay"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_DateSelect_All" templateKey="P_DateSelect_All"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_DateSelect_Assignation" templateKey="P_DateSelect_Assignation"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WeekDay" templateKey="W_WeekDay"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:checkbox id="chk_Monday" templateKey="P_Monday"/><h:checkbox id="chk_Tuesday" templateKey="P_Tuesday"/><h:checkbox id="chk_Wednesday" templateKey="P_Wednesday"/><h:checkbox id="chk_Thursday" templateKey="P_Thursday"/><h:checkbox id="chk_Friday" templateKey="P_Friday"/><h:checkbox id="chk_Saturday" templateKey="P_Saturday"/><h:checkbox id="chk_Sunday" templateKey="P_Sunday"/><h:submitbutton id="btn_DateSelectWeekday" templateKey="W_BesidesWeekend"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_DateSelectClear" templateKey="W_ClearDay"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_Area" templateKey="W_Area"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BatchNo" templateKey="W_BatchNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_BatchNo" templateKey="W_BatchNoPCT"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Level" templateKey="W_PCTLeval"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:checkbox id="chk_levelA" templateKey="P_levelA"/>&nbsp;&nbsp; <h:checkbox id="chk_levelB" templateKey="P_levelB"/>&nbsp;&nbsp; <h:checkbox id="chk_levelC" templateKey="P_levelC"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CollectUnit" templateKey="W_CollectUnit"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_CollectCondition_Worker" templateKey="P_CollectCondition_Worker"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CollectCondition_WorkDate" templateKey="P_CollectCondition_WorkDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CollectCondition_Consignor" templateKey="P_CollectCondition_Consignor"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CollectCondition_Area" templateKey="P_CollectCondition_Area"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CollectCondition_Batch" templateKey="P_CollectCondition_Batch"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_DispRank" templateKey="W_DispRank"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_DisplayRank_Lot" templateKey="P_DisplayRank_Lot"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_DisplayRank_Order" templateKey="P_DisplayRank_Order"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_DisplayRank_Line" templateKey="P_DisplayRank_Line"/><h:submitlabel id="slb_Download" templateKey="ANA_Download"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Display" templateKey="W_P_Display"/>&nbsp;&nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_Print" templateKey="W_Print"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_XLS" templateKey="W_XLS"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
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
