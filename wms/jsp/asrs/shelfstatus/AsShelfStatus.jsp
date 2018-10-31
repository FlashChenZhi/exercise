<%-- $Id: AsShelfStatus.jsp 6556 2009-12-18 08:59:44Z okayama $ --%>
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
 * @version $Revision: 6556 $, $Date: 2009-12-18 17:59:44 +0900 (金, 18 12 2009) $
 * @author  $Author: okayama $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "jp.co.daifuku.common.text.DisplayText" %>
<%@ page import = "jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView" %>
<%@ page import = "jp.co.daifuku.wms.base.entity.SystemDefine" %>
<%@ page import = "jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams" %>
<%@ page import = "jp.co.daifuku.wms.asrs.listbox.fastockdetailnbtn.FaLstAsStockDetailNoBtnParams" %>
<%@ page import = "jp.co.daifuku.wms.asrs.display.shelfstatus.AsShelfStatusBusiness" %>

<h:html>
<h:head>
<style>
.shelf-scroll {
   　　overflow-x		: auto;
       overflow-y		: auto;
   　　width			: 1245px;
       height			: 570px;
       background-color : #DAD9EE;
}
</style>
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
          <TD noWrap><h:tab id="tab_Inquiry" templateKey="W_Inquiry"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=1>
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
                <TD noWrap></TD>
                <TD></TD>
                <TD width="100%"></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap><h:label id="lbl_Area" templateKey="W_Area"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%"><h:pulldown id="pul_Area" templateKey="W_Area"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap><h:label id="lbl_Bank" templateKey="W_Bank"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%"><h:linkedpulldown id="pul_Bank" templateKey="W_Bank"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_DisplaySync" templateKey="W_DisplaySync"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
<%
        final String LOCSIZE = "";
        final String EXAMPLE_LOCSIZE    = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String CLR_ACCESS_NG    = "BLACK";          // アクセス不可棚
        String CLR_EMPTY        = "white";          // 空棚
        String CLR_STORAGED     = "GREEN";          // 実棚
        String CLR_RESERVATION  = "LIGHTPINK";      // 入庫予約
        String CLR_STATUS_NG    = "YELLOW";         // 禁止棚
        String CLR_ALLOCATION   = "CYAN";           // 出庫予約
        String CLR_RETRIVAL     = "BLUE";           // 出庫中
        String CLR_IRREGULAR    = "RED";            // 異常棚
        String CLR_EMPTYPALLET = "powderblue";      // 空パレット


        // エリア(倉庫)取得
        String wSelectedArea = (String)session.getAttribute(AsShelfStatusBusiness.SELECTED_AREA);
        
        
        // バンク取得
        String wSelectedBank = (String)session.getAttribute(AsShelfStatusBusiness.SELECTED_BANK);

        // 検索結果の取得
        AsLocationLevelView[] levelViews = (AsLocationLevelView[])session.getAttribute(AsShelfStatusBusiness.RESULT);
        
        if (levelViews != null) {
            
            int aki = 0;
            int kinshi = 0;
            int nyukoYoyaku = 0;
            int syukkoYoyaku = 0;
            int syukkoTyu = 0;
            int ijyo = 0;
            int jitsu = 0;
            int kara = 0;
            int huka = 0;
        
            int wBayMax = levelViews[0].getBayView().length;

        // FA/DA区分の取得
        boolean FaDaStatus = (Boolean)session.getAttribute(AsShelfStatusBusiness.FADA_STATUS);

%>

    <!-- 棚イメージ表示 ------------------------------------------------------------------------------>
    <table BGCOLOR="#DAD9EE" width = "100%" border="0" >
    <tr>
        <td>
            Bank <%= wSelectedBank %> 
            <BR>
<%
            // インナースクロールの表示はレベルを見て判断する
            if(levelViews.length < 23){
%>
            <!-- レベルが23未満はスクロール非表示 -->
            <TABLE TITLE="Bank<%= wSelectedBank %>"  BGCOLOR="#DAD9EE" border="0">
<%
            }
            else
            {
%>
            <!-- レベルが23以上はスクロール表示 -->
            <div id="AsShelfStatusTableDiv" class="shelf-scroll"><TABLE id="AsShelfStatusTable" TITLE="Bank<%= wSelectedBank %>"  BGCOLOR="#DAD9EE" border="0">
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
                // レベル方向の目盛り編集
                String level_scale = levelViews[i].getLevel();

%>
            <TR>
                <!-- レベル方向の目盛りWrite -->
                <TD>
                    <I>
                        <FONT ID="SCALE"><%= level_scale %></FONT>
                    </I>
                </TD>
<%
                for (int j = 0; j < levelViews[i].getBayView().length; j++) 
                {
                    // データの取得。
                    // ロケーション（ステーションNo.）
                    String location = levelViews[i].getBayView()[j].getLocation();
                    // 状態（棚の使用状態）
                    String prohibitFlg = levelViews[i].getBayView()[j].getProhibitFlg();
                    // アクセス不可棚
                    String accessNgFlg = levelViews[i].getBayView()[j].getAccessNgFlg();
                    // 棚状態
                    String status = levelViews[i].getBayView()[j].getStatus();
                    // パレット在庫状態
                    String pstatus = levelViews[i].getBayView()[j].getPStatus();
                    // 空パレット状態
                    String pempty = levelViews[i].getBayView()[j].getEmpty();
                    // ロケーション（バルーン用）
                    String locInfo = levelViews[i].getBayView()[j].getTooltip();
                    // パレットID
                    String palletId = levelViews[i].getBayView()[j].getPalletId();
                    if (location == null || "".equals(location.trim()))
                    {
%>
                <TD><%= LOCSIZE %></TD>   
<%
					}
                    // アクセス不可棚時
                    else if (SystemDefine.ACCESS_NG_FLAG_NG.equals(accessNgFlg))
                    {
                        huka++;
                        // 空棚時
                        if(SystemDefine.LOCATION_STATUS_FLAG_EMPTY.equals(status))
                        {
%>
 	              <TD BGCOLOR="<%= CLR_ACCESS_NG %>" title="<%= locInfo %>" >
                    <%= LOCSIZE %>
                </TD>   
<%
                        }
                        // 実棚時
                        else
                        {
                            // FAシステム
                            if(FaDaStatus)
                            {
%>
                   <TD BGCOLOR="<%= CLR_ACCESS_NG %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0010" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                            // DAシステム
                            }
                            else
                            {
%>
                   <TD BGCOLOR="<%= CLR_ACCESS_NG %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                            }
                        }
                    }
                    else
                    {
                        // 禁止棚時
                        if (SystemDefine.PROHIBITION_FLAG_NG.equals(prohibitFlg))
                        {
                            kinshi++;
                            // 実棚または予約棚時
                            if (SystemDefine.LOCATION_STATUS_FLAG_STORAGED.equals(status) || SystemDefine.LOCATION_STATUS_FLAG_RESERVATION.equals(status)) 
                            {
                                // FAシステム
                                if(FaDaStatus)
                                {
%>
                   <TD BGCOLOR="<%= CLR_STATUS_NG %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0059" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                                // DAシステム
                                else
                                {
%>
                   <TD BGCOLOR="<%= CLR_STATUS_NG %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                            }
                            // 空棚
                            else
                            {
%>
                <TD BGCOLOR="<%= CLR_STATUS_NG %>" title="<%= locInfo %>" >
                    <%= LOCSIZE %>
                </TD>   
<%
                            }
                        }
                        // 空棚
                        else if (SystemDefine.LOCATION_STATUS_FLAG_EMPTY.equals(status))
                        {
                            aki++;
%>
                <TD BGCOLOR="<%= CLR_EMPTY %>" title="<%= locInfo %>" >
                    <%= LOCSIZE %>
                </TD>   
<%
                        }
                        // 実棚または予約棚
                        else if (SystemDefine.LOCATION_STATUS_FLAG_STORAGED.equals(status) || SystemDefine.LOCATION_STATUS_FLAG_RESERVATION.equals(status))
                        {
                            // 入庫予約時
                            if (SystemDefine.PALLET_STATUS_STORAGE_PLAN.equals(pstatus))
                            {

                                nyukoYoyaku++;
                                // FAシステム
                                if(FaDaStatus)
                                {
%>
                   <TD BGCOLOR="<%= CLR_RESERVATION %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0454" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                                // DAシステム
                                else
                                {
%>
                   <TD BGCOLOR="<%= CLR_RESERVATION %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                            }
                            // 出庫予約時
                            else if (SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN.equals(pstatus)) 
                            {
                                syukkoYoyaku++;
                                // FAシステム
                                if(FaDaStatus)
                                {
%>
                   <TD BGCOLOR="<%= CLR_ALLOCATION %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0455" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                                // DAシステム
                                else
                                {
%>
                   <TD BGCOLOR="<%= CLR_ALLOCATION %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                            }
                            // 出庫中時
                            else if (SystemDefine.PALLET_STATUS_RETRIEVAL.equals(pstatus)) 
                            {
                                syukkoTyu++;
                                // FAシステム
                                if(FaDaStatus)
                                {
%>
                   <TD BGCOLOR="<%= CLR_RETRIVAL %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0457" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                                // DAシステム
                                else
                                {
%>
                   <TD BGCOLOR="<%= CLR_RETRIVAL %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                            }
                            // 異常棚時
                            else if (SystemDefine.PALLET_STATUS_IRREGULAR.equals(pstatus)) 
                            {
                                ijyo++;
                                // FAシステム
                                if(FaDaStatus)
                                {
%>
                   <TD BGCOLOR="<%= CLR_IRREGULAR %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0036" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                                // DAシステム
                                else
                                {
%>
                   <TD BGCOLOR="<%= CLR_IRREGULAR %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                }
                            }
                            // 実棚時
                            else if (SystemDefine.PALLET_STATUS_REGULAR.equals(pstatus))
                            {
                                 // 空PB時
                                if (SystemDefine.EMPTY_FLAG_EMPTY.equals(pempty))
                                {
                                    kara++;
                                    // FAシステム
                                    if(FaDaStatus)
                                    {
%>
                   <TD BGCOLOR="<%= CLR_EMPTYPALLET %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0456" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                    }
                                    // DAシステム
                                    else
                                    {
%>
                   <TD BGCOLOR="<%= CLR_EMPTYPALLET %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                    }
                                }
                                else
                                {
                                    jitsu++;
                                    // FAシステム
                                    if(FaDaStatus)
                                    {
%>
                   <TD BGCOLOR="<%= CLR_STORAGED %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do?<%= FaLstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= FaLstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= FaLstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>&<%= FaLstAsStockDetailNoBtnParams.SHELF_STATUS %>=<%= "LBL-W0104" %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                    }
                                    // DAシステム
                                    else
                                    {
%>
                   <TD BGCOLOR="<%= CLR_STORAGED %>" title="<%= locInfo %>" STYLE="cursor : hand;"
              ONCLICK="openInfoWindow('/wms/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do?<%= LstAsStockDetailNoBtnParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstAsStockDetailNoBtnParams.LOCATION_NO %>=<%= location %>&<%= LstAsStockDetailNoBtnParams.PALLET_ID %>=<%= palletId %>','popup_asShelfStatus<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
                                    }
                                }
                            }
                            // 不明...
                            else
                            {
%>
                <TD title="">
                    <%= LOCSIZE %>
                </TD>
<%
                            }
                        }
                        else
                        {
%>
                <TD title="">
                    <%= LOCSIZE %>
                </TD>
<%
                        }
                    }
                }
%>
            </TR>
<%          } 
%>
            <TR>
                <TD>
<%
            // ベイ方向の目盛りWrite
            for (int n = 0; n < wBayMax; n++)
            {
                String bay_scale = levelViews[0].getBayView()[n].getBayNo();
%>
                <TD>
                    <I>
                        <FONT ID="SCALE"><%=bay_scale %></FONT>
                    </I>
                </TD>
<%
            }
%>
            </TR>
        </TABLE>
        <%= EXAMPLE_LOCSIZE %>&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/project/etc/arrow_bay.gif">
        <BR>
      </div>
      </td>
    </tr>
    </TABLE>

<%--<jp> 棚イメージ説明 --------------------------------------------------------------------------------</jp>--%>
<%--<en> Describe the location image --------------------------------------------------------------------------------</en>--%>

    <TABLE BGCOLOR="#DAD9EE" width = "100%">
    <TR>
      <TD>
      <TABLE BGCOLOR="#DAD9EE">
        <TR>
          <td><img src="<%=request.getContextPath()%>/images/etc/spacer.gif" width="15" height="1" border="0"></td>
          <%--<jp>空棚 </jp>--%>
          <%--<en>empty location </en>--%>
          <TD BGCOLOR="<%= CLR_EMPTY %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0061") %>(<span><%=aki%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--<jp>入庫予約棚</jp> --%>
          <%--<en>reserved location </en>--%>
          <TD BGCOLOR="<%= CLR_RESERVATION %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0454") %>(<span><%=nyukoYoyaku%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--出庫予約--%>
          <TD BGCOLOR="<%= CLR_ALLOCATION %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0455") %>(<span><%=syukkoYoyaku %></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--<jp>異常棚 </jp>--%>
          <%--<en>error location </en>--%>
          <TD BGCOLOR="<%= CLR_IRREGULAR %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0036") %>(<span><%=ijyo%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--<jp>アクセス不可棚</jp> --%>
          <%--<en>inaccessible location </en>--%>
          <TD BGCOLOR="<%= CLR_ACCESS_NG %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0010") %>(<span><%=huka%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
        </TR>
        <TR>
          <td><img src="<%=request.getContextPath()%>/images/etc/spacer.gif" width="15" height="1" border="0"></td>
          <%--<jp>実棚</jp>--%>
          <%--<en>occupied location</en>--%>
          <TD BGCOLOR="<%= CLR_STORAGED %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0104") %>(<span><%=jitsu%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--<jp>空パレット </jp>--%>
          <%--<en>empty pallet </en>--%>
          <TD BGCOLOR="<%= CLR_EMPTYPALLET %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0456") %>(<span><%=kara%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
           <%--<jp>出庫中 </jp>--%>
          <%--<en>being retrieved </en>--%>
          <TD BGCOLOR="<%= CLR_RETRIVAL %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0457") %>(<span><%=syukkoTyu%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
          <%--<jp>禁止棚 </jp>--%>
          <%--<en>restricted location</en>--%>
          <TD BGCOLOR="<%= CLR_STATUS_NG %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0059") %>(<span><%=kinshi%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
        </TR>
      </TABLE>

<%
        } //if (levelViews != null)の終了 
%>
            
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR height=21>
    <TD width=7 bgColor=#dad9ee></TD>
  </TR></TBODY></TABLE>
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

function openInfoWindow()
{
    dialogWindow = window.open(arguments[0], arguments[1], arguments[2]);
    childWindowResize( dialogWindow );
    addWindowObject( dialogWindow );
}

// ウィンドウの中央表示
function childWindowResize(childWindow)
{
    if (typeof _mainWindowWidth == "undefined"){
        _mainWindowWidth = 1024;
    }
    if (typeof _mainWindowHeight == "undefined"){
        _mainWindowHeight = 768;
    }
    var w = _mainWindowWidth;
    var h = _mainWindowHeight;
    var x = (screen.availWidth - w) / 2;
    var y = (screen.availHeight - h) / 2;

    if ( screen.availWidth < w)
    {
        x = 0;
        w = screen.availWidth;
    }
    if ( screen.availHeight < h)
    {
        y = 0;
        h = screen.availHeight;
    }
    childWindow.resizeTo(w,h);
    childWindow.moveTo(x,y);
}

function setScroll()
{
	var tableHeight = document.getElementById("AsShelfStatusTable").clientHeight;
	var divHeight = document.getElementById("AsShelfStatusTableDiv").clientHeight;
	document.getElementById("AsShelfStatusTableDiv").scrollTop = tableHeight - divHeight + 50;
}
</script>
