<%-- $Id: SearchAuthLog2.jsp 7279 2010-02-26 10:07:48Z okayama $ --%>
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
 * @version $Revision: 7279 $, $Date: 2010-02-26 19:07:48 +0900 (金, 26 2 2010) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import="jp.co.daifuku.bluedog.util.MessageResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.HTMLUtils"%>

<h:html>
<h:head>
 <script>
    function dynamicAction()
    {
        var ret=false;

        if(document.all.txt_LogDateRetrievalBeginning.value=="" )
        {
        	 _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403049"))) %>', 2);
        	 document.all.txt_LogDateRetrievalBeginning.focus();
        	ret=true;
        }
        else if(document.all.txt_LogTimeRetrievalBeginn.value=="")
        {
        	 _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403049"))) %>', 2);
        	 document.all.txt_LogTimeRetrievalBeginn.focus();
        	 ret=true;
        }
        else if(document.all.txt_LogDateRetrievalEnd.value=="")
        {
        	 _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403050"))) %>', 2);
        	 document.all.txt_LogDateRetrievalEnd.focus();
        	 ret=true;
        }
        else if(document.all.txt_LogTimeRetrievalEnd.value=="")
        {
        	 _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403050"))) %>', 2);
        	 document.all.txt_LogTimeRetrievalEnd.focus();
        	 ret=true;
        }
        else if(document.all.txt_UserId.value=="")
        {
        	 _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6403027"))) %>', 2);
        	 document.all.txt_UserId.focus();
        	 ret=true;
        }
        return ret;
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
          <TD><h:label id="lbl_SettingName" templateKey="T_In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif
          height=24><h:linkbutton id="btn_Help" templateKey="T_Help"/>
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
          <TD bgColor=white height=23><h:message id="message" templateKey="T_OperationMsg"/></TD>
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
          <TD noWrap><h:tab id="tab" templateKey="T_CsvOut"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_Back" templateKey="T_Back"/>&nbsp;<h:submitbutton id="btn_ToMenu" templateKey="To_MenuNoFunc"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- upper display --%>
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
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LogDateRetrievalPeriod" templateKey="T_RetrievalPeriod"/><h:label id="lbl_RequireLogDate" templateKey="T_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_LogDateRetrievalBeginning" templateKey="T_RetrievalBeginning"/><h:timetextbox id="txt_LogTimeRetrievalBeginn" templateKey="T_RetrievalBeginning"/><h:label id="lbl_Hyphen" templateKey="T_Hyphen"/><h:datetextbox id="txt_LogDateRetrievalEnd" templateKey="T_RetrievalEnd"/><h:timetextbox id="txt_LogTimeRetrievalEnd" templateKey="T_RetrievalEnd"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LogClass" templateKey="T_LogClass"/><h:label id="lbl_RequireLogClass" templateKey="T_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_LogClass" templateKey="T_LogClass"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UserId" templateKey="T_UserId"/><h:label id="lbl_RequireUserId" templateKey="T_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_UserId" templateKey="T_UserId"/><h:submitbutton id="btn_SearchUserId" templateKey="T_P_Search"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_SearchLog" templateKey="T_LogSearchPopup"/></TD>
                <TD>　</TD>
                <TD>　　　　　</TD>
                <TD>&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="T_Clear"/></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE>
  <%-- lower display --%>
  </TD></TR>
  <TR height=15>
    <TD height=15></TD></TR>
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
          <TD
          background=<%=request.getContextPath()%>/img/common/bg_end.gif
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
