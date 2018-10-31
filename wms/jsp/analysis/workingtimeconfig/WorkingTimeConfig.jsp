<%-- $Id: WorkingTimeConfig.jsp 441 2008-10-21 08:57:12Z nakai $ --%>
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
 * @version $Revision: 441 $, $Date: 2008-10-21 17:57:12 +0900 (火, 21 10 2008) $
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
          <TD noWrap><h:tab id="tab" templateKey="W_InputCond"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            bgColor=#b8b7d7 border=0>
              <TBODY>
              <TR height=3>
                <TD></TD>
                <TD colSpan=2></TD>
                <TD colSpan=2></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD colSpan=3></TD>
                <TD></TD></TR>
              <TR>
                <TD></TD>
                <TD colSpan=2></TD>
                <TD colSpan=2><h:label id="lbl_TimeSimuInput" templateKey="W_TimeSimuInput"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
                <TD noWrap width=4 height=3></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
                <TD colSpan=3><h:label id="lbl_TimeSimuResult" templateKey="W_TimeSimuResult"/></TD>
                <TD></TD></TR>
              <TR>
                <TD></TD>
                <TD colSpan=2></TD>
                <TD><h:label id="lbl_SecPerItem" templateKey="W_SecPerItem"/> <h:label id="lbl_SecPerItemRequire" templateKey="W_Require"/></TD>
                <TD><h:label id="lbl_SecPerPiece" templateKey="W_SecPerPiece"/> <h:label id="lbl_SecPerPieceRequire" templateKey="W_Require"/></TD>
                <TD></TD>
                <TD noWrap width=4 height=3></TD>
                <TD></TD>
                <TD><h:label id="lbl_SecPerItemResult" templateKey="W_SecPerItem"/></TD>
                <TD><h:label id="lbl_AvePiecePerItem" templateKey="W_AvePiecePerItem"/>&nbsp;&nbsp; </TD>
                <TD><h:label id="lbl_SecPerPieceResult" templateKey="W_SecPerPiece"/></TD>
                <TD></TD></TR>
              <TR>
                <TD><h:label id="lbl_StorageWork" templateKey="W_StorageWork"/></TD>
                <TD colSpan=2><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:numbertextbox id="txt_StorageSecPerItem" templateKey="W_WorkUnitTime"/><h:label id="lbl_StorageUnitSecondItem" templateKey="W_UnitSecond"/></TD>
                <TD><h:numbertextbox id="txt_StorageSecPerPiece" templateKey="W_WorkUnitTimePiece"/><h:label id="lbl_StorageUnitSecondWork" templateKey="W_UnitSecond"/></TD>
                <TD></TD>
                <TD noWrap width=4 height=3></TD>
                <TD></TD>
                <TD><h:numbertextbox id="txt_StorageAveTimeItem" templateKey="W_WorkUnitTime"/><h:label id="lbl_StorageAveTimeUnit" templateKey="W_UnitSecond"/>&nbsp;&nbsp;</TD>
                <TD><h:numbertextbox id="txt_StorageAvePiece" templateKey="W_PieceQty"/></TD>
                <TD><h:numbertextbox id="txt_StorageAveTimePiece" templateKey="W_WorkUnitTime"/><h:label id="lbl_StorageAveTimeUnitP" templateKey="W_UnitSecond"/>&nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_StorageWkTimeResult" templateKey="W_P_WorkTimeResult"/></TD></TR>
              <TR>
                <TD><h:label id="lbl_RetrievalWork" templateKey="W_RetrievalWork"/></TD>
                <TD colSpan=2><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:numbertextbox id="txt_RetrievalSecPerItem" templateKey="W_WorkUnitTime"/><h:label id="lbl_RetrievalUnitSecondItem" templateKey="W_UnitSecond"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalSecPerPiece" templateKey="W_WorkUnitTimePiece"/><h:label id="lbl_RetrievalUnitSecondWork" templateKey="W_UnitSecond"/></TD>
                <TD></TD>
                <TD noWrap width=4 height=3></TD>
                <TD></TD>
                <TD><h:numbertextbox id="txt_RetrievalAveTimeItem" templateKey="W_WorkUnitTime"/><h:label id="lbl_RetrievalAveTimeUnit" templateKey="W_UnitSecond"/>&nbsp;&nbsp;</TD>
                <TD><h:numbertextbox id="txt_RetrievalAvePiece" templateKey="W_PieceQty"/></TD>
                <TD><h:numbertextbox id="txt_RetrievalAveTimePiece" templateKey="W_WorkUnitTime"/><h:label id="lbl_RetrievalAveTimeUnitP" templateKey="W_UnitSecond"/>&nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_RetrievalWkTimeResult" templateKey="W_P_WorkTimeResult"/></TD></TR>
              <TR>
                <TD></TD>
                <TD colSpan=2></TD>
                <TD colSpan=9>&nbsp; </TD>
                <TD></TD></TR>
              <TR>
                <TD></TD>
                <TD colSpan=2></TD>
                <TD colSpan=9>
                  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
                  border=0>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_SecPerItemGuide" templateKey="W_SecPerItem"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:label id="lbl_GuideTimePerItem1" templateKey="W_GuideTimePerItem1"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_SecPerPieceGuide" templateKey="W_SecPerPiece"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:label id="lbl_GuideTimePerPiece" templateKey="W_GuideTimePerPiece"/></TD></TR></TBODY></TABLE></TD>
                <TD></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_Setting" templateKey="W_Set2"/></TD>
                <TD>&nbsp; &nbsp; </TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_Restore" templateKey="W_Restore"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Reflesh" templateKey="W_ReDisplay"/></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE></TD></TR>
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
