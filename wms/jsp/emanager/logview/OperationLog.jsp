<%-- $Id: OperationLog.jsp 7765 2010-03-31 02:29:07Z shibamoto $ --%>
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
 * @version $Revision: 7765 $, $Date: 2010-03-31 11:29:07 +0900 (水, 31 3 2010) $
 * @author  $Author: shibamoto $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%@ page import="jp.co.daifuku.bluedog.util.MessageResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.HTMLUtils"%>
<%@ page import="jp.co.daifuku.bluedog.util.DispResources"%>

<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
<script>
function dynamicAction()
	{
		if (targetElement == "btn_View" || targetElement == "btn_SearchPopup" )
		{
			if (_isValueInput('txt_TimeRetrievalBeginning') && !_isValueInput('txt_RetrievalBeginning'))
			{
				document.all('txt_RetrievalBeginning').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403085"))) %>',2);
				return true;
			}
			if (_isValueInput('txt_TimeRetrievalEnd') && !_isValueInput('txt_RetrievalEnd'))
			{
				document.all('txt_RetrievalEnd').focus();
				_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403085"))) %>',2);
				return true;
			}
			
			if (targetElement == "btn_View")
			{
				if ((document.all('txt_DSNo').value).indexOf("*", 0) >= 0)
				{
					document.all('txt_DSNo').focus();
					_setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403087", DispResources.getText("LBL-T0131")))) %>',2);
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
          <TD><h:label id="lbl_SettingName" templateKey="T_In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24><h:linkbutton id="btn_Help" templateKey="T_Help"/> 
          </TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE><%-- message --%>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR>
          <TD width=7 height=23></TD>
          <TD width=4 bgColor=white height=23></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="T_OperationMsg"/></TD>
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
          <TD width=7></TD><!-- <TD noWrap></TD> -->
          <TD noWrap><h:tab id="tab_AccessLog" templateKey="T_AccessLog"/></TD>
          <TD noWrap><h:tab id="tab_MasterLog" templateKey="T_MasterLog"/></TD>
          <TD noWrap><h:tab id="tab_InventoryLog" templateKey="T_InventoryLog"/></TD>
          <TD noWrap><h:tab id="tab_OperationLog" templateKey="T_OperationLog"/></TD>
          <TD width="99%" height=28>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="T_ToMenu"/></TD></TR></TBODY></TABLE>
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
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalPeriod" templateKey="T_RetrievalPeriod"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_RetrievalBeginning" templateKey="T_RetrievalBeginning"/><h:timetextbox id="txt_TimeRetrievalBeginning" templateKey="T_RetrievalBeginning"/><h:label id="lbl_Hyphen" templateKey="T_Hyphen"/><h:datetextbox id="txt_RetrievalEnd" templateKey="T_RetrievalEnd"/><h:timetextbox id="txt_TimeRetrievalEnd" templateKey="T_RetrievalEnd"/><h:label id="lbl_InputStyle" templateKey="W_InputStyle"/><h:label id="lbl_Day" templateKey="W_In_JavaSet"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UserID" templateKey="T_UserID"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_UserId" templateKey="T_UserId"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_DSNo" templateKey="T_DSNo"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_DSNo" templateKey="T_DsNo"/><h:submitbutton id="btn_SearchPopup" templateKey="T_SearchPopup"/>&nbsp;&nbsp; 
                  <h:freetextbox id="txt_R_PageName" templateKey="T_R_PageName"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_View" templateKey="T_View"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="T_Clear"/></TD>
          <TD><IMG height=40 
            src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE><%-- lower display --%>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7 colSpan=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7 colSpan=2><h:submitbutton id="btn_BackPage" templateKey="T_BackPage"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_NextPage" templateKey="T_NextPage"/>&nbsp; 
                  <h:label id="lbl_RecordCount" templateKey="T_RecordCount"/>&nbsp; <h:numbertextbox id="txt_RecordCount" templateKey="T_RecordCount"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7>&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7 colSpan=2><h:listcell id="lst_ScreenControlLog" templateKey="T_ScreenControlLog"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;</TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
