<%-- $Id: 04_uppertab-lowertab.jsp 87 2008-10-04 03:07:38Z admin $ --%>
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
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
          <TD noWrap><h:tab id="tab" templateKey="W_Set"/></TD>
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
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#dad9ee><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee colSpan=7><h:pulldown id="pul_Area" templateKey="W_Area_Event"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD bgColor=#dad9ee><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee colSpan=7><h:linkedpulldown id="pul_WorkPlace" templateKey="W_WorkPlace"/>&nbsp;&nbsp;&nbsp;&nbsp;<h:label id="lbl_Station" templateKey="W_Station"/><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_Station" templateKey="W_Station"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SearchCondition" templateKey="W_SearchCondition"/></TD>
                <TD bgColor=#b8b7d7><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:radiobutton id="rdo_Search_ItemCode" templateKey="W_Search_ItemCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:radiobutton id="rdo_Search_Location" templateKey="W_Search_Location"/></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD bgColor=#b8b7d7><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode_EntEvent"/><h:submitbutton id="btn_PItemCodeSearch" templateKey="W_P_Search"/>&nbsp;
                  &nbsp;</TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD bgColor=#b8b7d7><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ItemName" templateKey="W_ItemName"/><h:submitbutton id="btn_PItemNameSearch" templateKey="W_P_Search"/>
                </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="30%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotNo" templateKey="W_LotNo"/></TD>
                <TD bgColor=#b8b7d7><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_LotNo" templateKey="W_LotNo"/></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_RMNo" templateKey="W_RMNo"/></TD>
                <TD bgColor=#b8b7d7><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:numbertextbox id="txt_FromRMNo" templateKey="W_RMNo"/><h:label id="lbl_Range" templateKey="W_Range"/><h:numbertextbox id="txt_ToRMNo" templateKey="W_RMNo"/><h:label id="lbl_Location" templateKey="W_Location"/><IMG
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Location" templateKey="W_Location"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_LocationStyle" templateKey="W_In_JavaSet"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_Display"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
          <TD height=5></TD></TR></TBODY></TABLE><%-- lower display --%>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_WorkStart" templateKey="W_Set2"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_ClearListInput" templateKey="W_ClearListInput"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AllCheck" templateKey="W_AllCheck"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AllCheckClear" templateKey="W_AllCheckClear"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_ListClear" templateKey="W_AllClear"/></TD>
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
                <TD bgColor=#b8b7d7 colSpan=2><h:checkbox id="chk_LWorkListPrint" templateKey="W_WorkListPrint"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
                <TD bgColor=#b8b7d7 colSpan=2>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pager" templateKey="Pager"/></TD>
                      <TD>&nbsp;&nbsp;&nbsp;</TD>
                      <TD><h:label id="lbl_LPriorityFlag" templateKey="W_PriorityFlag"/></TD>
                      <TD><IMG
                        src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:pulldown id="pul_LPrioriryFlag" templateKey="W_PriorityFlag"/></TD></TR></TBODY></TABLE>&nbsp;</TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7 colSpan=2><h:scrolllistcell id="lst_FaInquiryRetrieval" templateKey="W_FaInquiryRetrieval"/></TD></TR></TBODY></TABLE>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
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
