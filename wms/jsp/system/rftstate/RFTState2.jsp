<%-- $Id: RFTState2.jsp 217 2008-10-15 02:56:14Z rnakai $ --%>
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
 * @version $Revision: 217 $, $Date: 2008-10-15 11:56:14 +0900 (水, 15 10 2008) $
 * @author  $Author: rnakai $
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
    <TD>
        <%-- header --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" width=309 border=0></TD></TR>
        <TR height=1>
          <TD bgColor=#dad9ee height=1></TD></TR></TBODY></TABLE>
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
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE>

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
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_RFTNo" templateKey="W_RFTNo"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif">　　　　　　　　　　　　　　　　　　　　　</TD>
                <TD><h:label id="lbl_JavaSetRftNo" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee>　　　　　</TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_TerminalFlag" templateKey="W_TerminalFlag"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_JavaSetTerminalType" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_RFTStatus" templateKey="W_RFTStatus"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_JavaSetRftStatus" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee>　　</TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_UserName" templateKey="W_UserName"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#dad9ee><h:label id="lbl_JavaSetUserName" templateKey="W_In_JavaSet"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD noWrap bgColor=#dad9ee colSpan=3></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7 colSpan=3></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_HalfWork" templateKey="W_HalfWork"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_HalfWork_Cancel" templateKey="W_HalfWork_Cancel"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_HalfWork_Commit" templateKey="W_HalfWork_Commit"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_DeficiencyQty" templateKey="W_DeficiencyQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_Deficiency_PartialDelivery" templateKey="W_Deficiency_PartialDelivery"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_Deficiency_Shortage" templateKey="W_Deficiency_Shortage"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Submit" templateKey="W_Set2"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ReDisplay" templateKey="W_ReDisplayFunc"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Close_U" templateKey="W_Close"/></TD>
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
