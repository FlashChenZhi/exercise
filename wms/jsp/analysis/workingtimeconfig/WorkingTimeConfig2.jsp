<%-- $Id: WorkingTimeConfig2.jsp 440 2008-10-21 08:56:50Z nakai $ --%>
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
 * @version $Revision: 440 $, $Date: 2008-10-21 17:56:50 +0900 (火, 21 10 2008) $
 * @author  $Author: nakai $
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
  <TR></TR>
  <TR>
    <TD>
  <%-- header --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=1>
          <TD bgColor=#dad9ee colSpan=3 height=1></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR>
        <TR>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#413a8a>&nbsp;<h:label id="lbl_ListName" templateKey="In_SettingName"/></TD>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%">
  
  <%-- display --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
            border=0>
              <TBODY>
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
                <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE><IMG height=10 src="<%=request.getContextPath()%>/img/common/spacer.gif" width="100%" border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      border=0>
        <TBODY>
        <TR bgColor=#b8b7d7>
          <TD width="100%">
  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            bgColor=#b8b7d7 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD><h:label id="lbl_AveTimePerItem" templateKey="W_SecPerItem"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_AveTimeItem" templateKey="W_WorkUnitTime"/><h:label id="lbl_AveTimeUnit" templateKey="W_UnitSecond"/>&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_AvePiecePerItem" templateKey="W_AvePiecePerItem"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_AvePiece" templateKey="W_PieceQty"/>&nbsp;&nbsp;&nbsp;&nbsp;<h:label id="lbl_AveTimePerPiece" templateKey="W_SecPerPiece"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_AveTimePiece" templateKey="W_WorkUnitTime"/><h:label id="lbl_AveTimeUnitP" templateKey="W_UnitSecond"/> </TD></TR></TBODY></TABLE></TD>
          <TD vAlign=bottom align=right>
          <TD><h:submitbutton id="btn_Close_U" templateKey="W_Close"/></TD>
          <TD width=7 height=10><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TD></TR></TBODY></TABLE>
      <TABLE>
        <TBODY>
        <TR>
          <TD height=5></TD></TR></TBODY></TABLE>

  <%-- List start --%>   
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 colSpan=4></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 colSpan=4><h:verticalbarlinechart id="vbc_Chart" templateKey="W_WorkTimeResult"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 colSpan=4></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR></TBODY></TABLE><BR>

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
