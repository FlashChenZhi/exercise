<%-- $Id: PCTItemRegist.jsp 3207 2009-03-02 05:30:50Z arai $ --%>
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
 * @version $Revision: 3207 $, $Date: 2009-03-02 14:30:50 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
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
          <TD noWrap><h:tab id="tab" templateKey="W_Regist"/></TD>
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
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=4></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/><h:label id="lbl_RequireNinushi" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_SearchConsignor" templateKey="W_P_Search"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ConsignorName" templateKey="W_ConsignorName"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/><h:label id="lbl_RequireShohin" templateKey="W_Require"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_SearchItem" templateKey="W_P_Search"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_JanCode" templateKey="W_JanCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_JanCode" templateKey="W_JanCode"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=3>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:freetextbox id="txt_ItemName" templateKey="W_ItemName"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LotEnteringQty" templateKey="W_LotEnteringQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:numbertextbox id="txt_LotEnteringQty" templateKey="W_LotEnteringQty"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_EnteringQty" templateKey="W_EnteringQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:numbertextbox id="txt_EnteringQty" templateKey="W_EnteringQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><h:label id="lbl_CasITF" templateKey="W_CasITF"/>&nbsp;</TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_CaseITF" templateKey="W_CaseITF"/> 
                  </TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEnteringQty" templateKey="W_BundleEnteringQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:numbertextbox id="txt_BundleEnteringQty" templateKey="W_BundleEnteringQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                      <TD>&nbsp;<IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_BundleItf" templateKey="W_BundleItf"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Unit" templateKey="W_Unit"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:numbertextbox id="txt_Unit" templateKey="W_Unit"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo1" templateKey="W_LocationNo1"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:formattextbox id="txt_LocationNo1" templateKey="W_LocationNo1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
                      <TD><h:label id="lbl_EnteringQty1" templateKey="W_EnteringQty1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_EnteringQty1" templateKey="W_EnteringQty1"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo2" templateKey="W_LocationNo2"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:formattextbox id="txt_LocationNo2" templateKey="W_LocationNo2"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><h:label id="lbl_EnteringQty2" templateKey="W_EnteringQty2"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_EnteringQty2" templateKey="W_EnteringQty2"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo3" templateKey="W_LocationNo3"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:formattextbox id="txt_LocationNo3" templateKey="W_LocationNo3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><h:label id="lbl_EnteringQty3" templateKey="W_EnteringQty3"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_EnteringQty3" templateKey="W_EnteringQty3"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo4" templateKey="W_LocationNo4"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:formattextbox id="txt_LocationNo4" templateKey="W_LocationNo4"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><h:label id="lbl_EnteringQty4" templateKey="W_EnteringQty4"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                      </TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_EnteringQty4" templateKey="W_EnteringQty4"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UseByPeriod" templateKey="W_UseByPeriod"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:datetextbox id="txt_UseByPeriod" templateKey="W_UseByPeriod"/>&nbsp;&nbsp; <h:label id="lbl_InstockLimitDate" templateKey="W_InstockLimitDate"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_InstockLimitDate" templateKey="W_InstockLimitDate"/>&nbsp;&nbsp; &nbsp;</TD>
                      <TD><h:label id="lbl_ShippingLimitDate" templateKey="W_ShippingLimitDate"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:datetextbox id="txt_ShippingLimitDate" templateKey="W_ShippingLimitDate"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LatestUseByDate" templateKey="W_LatestUseByDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:datetextbox id="txt_LatestUseByDate" templateKey="W_LatestUseByDate"/>&nbsp;&nbsp; <h:label id="lbl_LatestManufacutureDate" templateKey="W_LatestManufacutureDate"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_LatestManufacutureDate" templateKey="W_LatestManufacutureDate"/>&nbsp;&nbsp; &nbsp;</TD>
                      <TD><h:label id="lbl_LatestRetrievalDate" templateKey="W_LatestRetrievalDate"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:datetextbox id="txt_LatestRetrievalDate" templateKey="W_LatestRetrievalDate"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LatestStock" templateKey="W_LatestStock"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=4>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:datetextbox id="txt_LatestStock" templateKey="W_LatestStock"/>&nbsp;&nbsp; <h:label id="lbl_OldestStock" templateKey="W_OldestStock"/>&nbsp;&nbsp;&nbsp; 
                        <IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_OldestStock" templateKey="W_OldestStock"/>&nbsp;&nbsp; &nbsp;</TD>
                      <TD><h:label id="lbl_ManagementFlag" templateKey="W_ManagementFlag"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_ManagementFlag" templateKey="W_ManagementFlag"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Set" templateKey="W_Set"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="W_Clear"/></TD>
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
          <TD 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
