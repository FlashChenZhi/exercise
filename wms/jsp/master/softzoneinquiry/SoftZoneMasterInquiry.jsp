<%-- $Id: SoftZoneMasterInquiry.jsp 6643 2009-12-25 02:39:48Z okayama $ --%>
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
 * @version $Revision: 6643 $, $Date: 2009-12-25 11:39:48 +0900 (金, 25 12 2009) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView" %>
<%@ page import = "jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView.AsLocationBayView" %>
<%@ page import = "jp.co.daifuku.wms.base.entity.SystemDefine" %>
<%@ page import = "jp.co.daifuku.wms.master.display.softzoneinquiry.SoftZoneMasterInquiryBusiness" %>
<%@ page import = "jp.co.daifuku.wms.base.entity.*" %>


<h:html>
<style>
.shelf-scroll {
   　　overflow-x		: auto;
       overflow-y		: auto;
   　　width			: 1245px;
       height			: 570px;
       background-color : #DAD9EE;
}
</style>
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
          height=24> 
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
          <TD noWrap><h:tab id="tab" templateKey="W_Inquiry"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_Area" templateKey="W_Area"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Bank" templateKey="W_Bank"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:linkedpulldown id="pul_Bank" templateKey="W_Bank"/></TD></TR>
              </TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Display" templateKey="W_DisplaySync"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
<%
	// 棚イメージサイズ
	final String LOCSIZE	= "";
	final String EXAMPLE_LOCSIZE	= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

	// ソフトゾーンごとの背景色を定義
	String[] CLR_ZONE = {
			"white",
			"RED",
			"GREEN",
			"YELLOW",
			"GRAY",
			"FUCHSIA",
			"BLUE",
			"BLACK",
			"LIME",
			"MAROON"
	};
	
	// バンク取得
	String bankNo = (String)session.getAttribute(SoftZoneMasterInquiryBusiness.SELECTED_BANK);

	// 検索結果の取得
	AsLocationLevelView[] levelViews = (AsLocationLevelView[])session.getAttribute(SoftZoneMasterInquiryBusiness.RESULT);
    
    if (levelViews != null)
    {
		// ゾーンを取得
		SoftZone[] zones = (SoftZone[])session.getAttribute(SoftZoneMasterInquiryBusiness.ZONE);
		// 各ゾーンの棚数をカウント保持するための変数
		int[] zone_len = new int[zones.length];
%>

	<!-- 棚イメージ表示 --------------------------------------------------------------->
	<!-- Display the location image. -->
	<table BGCOLOR="#DAD9EE" width = "100%">
	<TR>
 	  <TD>
		Bank <%= bankNo %> 
		<BR>
<%
		// インナースクロールの表示はレベルを見て判断する
		if(levelViews.length < 23){
%>
		<!-- レベルが23未満はスクロール非表示 -->
		<TABLE TITLE="Bank<%= bankNo %>"  BGCOLOR="#DAD9EE" border="0">
<%
		}
		else
		{
%>
		<!-- レベルが23以上はスクロール表示 -->
		<div id="SoftZoneMasterInquiryTableDiv" class="shelf-scroll"><TABLE id="SoftZoneMasterInquiryTable" TABLE TITLE="Bank<%= bankNo %>"  BGCOLOR="#DAD9EE" >
<%
		}
%>
 		<TR>
 		  <TD VALIGN="bottom" ROWSPAN="<%=levelViews.length + 2%>" >
 		  	<IMG SRC="<%=request.getContextPath()%>/img/project/etc/arrow_lvl.gif">
 		  </TD>
 		</TR>
<% 
		for (int i = 0; i < levelViews.length; i++)
		{
%>
		<TR>
		  <!-- レベル方向の目盛りWrite -->
		  <TD><I><FONT ID="SCALE"><%= levelViews[i].getLevel() %></FONT></I>
		  </TD>
<%
			for (AsLocationBayView bayView : levelViews[i].getBayView())
			{
				String location = bayView.getLocation();
				String accessNgFlag = bayView.getAccessNgFlg();
                String locInfo = bayView.getTooltip();
                String softZoneId = bayView.getSoftzoneId();

				int zoneIdx = -1;
				
				// 棚No.が取得できない場合
				if (location == null || location.trim().equals(""))
				{
%>
                <TD><%= LOCSIZE %></TD>   
<%
				}
				// ゾーンIDごとに描写・件数チェック
				else
				{
				    for (int zoneCnt = 0; zoneCnt < zones.length; zoneCnt++)
				    {
				        if (softZoneId.equals(zones[zoneCnt].getSoftZoneId()))
				        {
				            zoneIdx = zoneCnt;
				            break;
				        }
				    }
				    
					if (zoneIdx != -1)
					{
						zone_len[zoneIdx]++;
%>
					  <TD BGCOLOR="<%= CLR_ZONE[zoneIdx % CLR_ZONE.length] %>" title="<%= locInfo %>"><%= LOCSIZE %>
					  </TD>
<%
					}
					else
					{
%>
					    <TD><%= LOCSIZE %></TD>
<%
					}
				}

				
			}
		}
%>
		</TR>
		<TR><TD></TD>
<%
		
		// ベイ方向の目盛りWrite
		for (int n = 0; n < levelViews[0].getBayView().length; n++)
		{
 			String bay_scale = levelViews[0].getBayView()[n].getBayNo();
%>
			<TD><I><FONT ID="SCALE"><%= bay_scale %></FONT></I></TD>
<%
		}
	
%>
		</TR>
		</TABLE>
		<%= EXAMPLE_LOCSIZE %>&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/project/etc/arrow_bay.gif">
		<BR><BR>
	  </TD>
	</TR>
	</TABLE>
<!-- 棚イメージ説明 -->
<!-- Describe the location image  -->
	<TABLE BGCOLOR="#DAD9EE" width = "100%">
	<TR>
	  <TD>
	  <TABLE BGCOLOR="#DAD9EE">
	    <TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
<%
		for (int i = 0; i < zones.length; i++)
		{
%>
		  <TD BGCOLOR="<%= CLR_ZONE[i % CLR_ZONE.length] %>"><%= EXAMPLE_LOCSIZE %></TD><TD><%= zones[i].getSoftZoneName() %> (<%= zone_len[i] %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
<%
			if ((i+1)%5 == 0)
			{
%>

		</TR>
		<TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
<%
			}
		}
%>
		</TR>
	  </TABLE>
	</TABLE>
	</TD>
  </TR>

<%
	}
%>

<%--  </TD></TR> --%>
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
<script>
function setScroll()
{
	var tableHeight = document.getElementById("SoftZoneMasterInquiryTable").clientHeight;
	var divHeight = document.getElementById("SoftZoneMasterInquiryTableDiv").clientHeight;
	document.getElementById("SoftZoneMasterInquiryTableDiv").scrollTop = tableHeight - divHeight + 50;
}

</script>
