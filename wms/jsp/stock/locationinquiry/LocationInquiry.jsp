<%-- $Id: LocationInquiry.jsp 150 2008-10-09 07:49:24Z nakai $ --%>
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
 * @version $Revision: 150 $, $Date:: 2008-10-04 12:07:38 +0900$
 * @author  $Author: nakai $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "jp.co.daifuku.common.text.DisplayText" %>
<%@ page import = "jp.co.daifuku.common.text.StringUtil"%>
<%@ page import = "jp.co.daifuku.wms.base.entity.SystemDefine" %>
<%@ page import = "jp.co.daifuku.wms.base.common.WmsParam" %>
<%@ page import = "jp.co.daifuku.wms.stock.schedule.*" %>
<%@ page import="jp.co.daifuku.wms.stock.listbox.stocklist.LstStockParams"%>
<%@ page import="jp.co.daifuku.wms.stock.display.locationinquiry.LocationInquiryBusiness;"%>
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
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_DisplaySync" templateKey="W_DisplaySync"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
<%
        final String LOCSIZE = "";
        final String EXAMPLE_LOCSIZE    = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String CLR_EMPTY        = "WHITE";          // 空棚
        String CLR_STORAGED     = "GREEN";          // 実棚

        // エリア取得
        String wSelectedArea = (String)session.getAttribute(LocationInquiryBusiness.SELECTED_AREA);

        // バンク取得
        String wSelectedBank = (String)session.getAttribute(LocationInquiryBusiness.SELECTED_BANK);

        // 検索結果の取得
        LocationLevelView[] levelViews = (LocationLevelView[])session.getAttribute(LocationInquiryBusiness.RESULT);
        
        if (levelViews != null) {
            
            int aki = 0;			// 空棚
            int jitsu = 0;			// 実棚
        
            int wBayMax = levelViews[0].getBayView().length;
%>

    <!-- 棚イメージ表示 ------------------------------------------------------------------------------>
    <table BGCOLOR="#DAD9EE" width = "100%" border="0" >
    <tr>
        <td>
            Bank <%= wSelectedBank %> 
            <BR>
            <TABLE TITLE="Bank<%= wSelectedBank %>"  BGCOLOR="#DAD9EE" border="0">
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
                    String syozaiKey = levelViews[i].getBayView()[j].getLocation();
                    String tanaFlg = levelViews[i].getBayView()[j].getStatus();
                    String locInfo = levelViews[i].getBayView()[j].getTooltip();
                    
                    // DMLOCATEにレコードがないとき
                    if (StringUtil.isBlank(tanaFlg))
                    {
%>
                <TD title="">
                    <%= LOCSIZE %>
                </TD>
<%
                    }
                    else
                    {
                        // 空棚時
                        if (SystemDefine.LOCATION_STATUS_FLAG_EMPTY.equals(tanaFlg))
                        {
                            aki++;
%>
                <TD BGCOLOR="<%= CLR_EMPTY %>" title="<%= locInfo %>" >
                    <%= LOCSIZE %>
                </TD>   
<%
                        }
                        // 実棚時
                        else if (SystemDefine.LOCATION_STATUS_FLAG_STORAGED.equals(tanaFlg)) 
                        {
                            jitsu++;
%>
                <TD BGCOLOR="<%= CLR_STORAGED %>" title="<%= locInfo %>" STYLE="cursor : hand;"
                 ONCLICK="openInfoWindow('/wms/stock/listbox/stocklist/LstStock.do?<%= LstStockParams.AREA_NO %>=<%= wSelectedArea %>&<%= LstStockParams.LOCATION_NO %>=<%= syozaiKey %>','popup_locationInq<%= session.getId() %>','width=1015,height=600,scrollbars=yes,resizable=no')" >
                    <%= LOCSIZE %>
                </TD>   
<%
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
                    <FONT ID="SCALE">&nbsp;<%=bay_scale %>&nbsp;</FONT>
                    </I>
                </TD>
<%
            }
%>
				</TD>
            </TR>
        </TABLE>
        <%= EXAMPLE_LOCSIZE %>&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/project/etc/arrow_bay.gif">
        <BR>
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
          <%--<jp>実棚</jp>--%>
          <%--<en>occupied location</en>--%>
          <TD BGCOLOR="<%= CLR_STORAGED %>"><%= EXAMPLE_LOCSIZE %></TD>
          <TD><%= DisplayText.getText("LBL-W0104") %>(<span><%=jitsu%></span>)</TD>
          <TD>&nbsp;&nbsp;</TD>
        </TR>
      </TABLE>
      </TD>
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

</script>