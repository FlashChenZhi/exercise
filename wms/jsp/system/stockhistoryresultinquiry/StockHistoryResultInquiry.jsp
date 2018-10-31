<%-- $Id: StockHistoryResultInquiry.jsp 7134 2010-02-18 07:39:31Z shibamoto $ --%>
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
          <TD noWrap><h:tab id="tab" templateKey="W_InquiryPrint"/></TD>
          <TD width="100%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE><%-- upper display --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#b8b7d7 
        border=0><TBODY>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SearchDate" templateKey="W_SearchDate"/></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7 colSpan=5><h:datetextbox id="txt_SearchDateFrom" templateKey="W_SearchDate"/><h:timetextbox id="txt_SearchTimeFrom" templateKey="W_SearchTime"/><h:label id="lbl_Range" templateKey="W_Range"/><h:datetextbox id="txt_SearchDateTo" templateKey="W_SearchDate"/><h:timetextbox id="txt_SearchTimeTo" templateKey="W_SearchTime"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_DateStyle" templateKey="W_InputStyleDate"/></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD></TR>
        <TR>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/></TD>
          <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotNo" templateKey="W_LotNo"/></TD>
          <TD noWrap bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7><h:freetextbox id="txt_LotNo" templateKey="W_LotNo"/></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7 colSpan=5><h:pulldown id="pul_Area" templateKey="W_Area"/></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkFlag" templateKey="W_WorkFlag"/></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7><h:pulldown id="pul_WorkFlag" templateKey="W_F_WorkFlag3"/></TD>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD></TR>
        <TR>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UserName" templateKey="W_UserName"/></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
          <TD noWrap bgColor=#b8b7d7 colSpan=5><h:freetextbox id="txt_UserName" templateKey="W_UserName"/><h:submitbutton id="btn_PSearch" templateKey="W_P_Search"/></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD noWrap bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD noWrap bgColor=#b8b7d7 colSpan=5></TD>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
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
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE><%-- lower display --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#b8b7d7 
        border=0><TBODY>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD>&nbsp;&nbsp;<IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;</TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:pager id="pgr_U" templateKey="Pager"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:listcell id="lst_StorageRetrievalResultList" templateKey="W_StorageRetrievalResultList"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;<h:pager id="pgr_D" templateKey="Pager"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR></TBODY></TABLE>　</TD>
          <TD width="100%"></TD>
          <TD><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD>
          <TD bgColor=#dad9ee><IMG 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7 border=0></TD></TR></TBODY></TABLE></TD></TR>
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