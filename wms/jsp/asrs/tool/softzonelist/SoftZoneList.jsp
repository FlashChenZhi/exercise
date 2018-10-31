<%-- $Id: SoftZoneList.jsp 4118 2009-04-10 10:55:30Z ota $ --%>
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
 * @version $Revision: 4118 $, $Date: 2009-04-10 19:55:30 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "java.text.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jp.co.daifuku.wms.asrs.tool.*" %>
<%@ page import = "jp.co.daifuku.wms.asrs.tool.common.*" %>
<%@ page import = "jp.co.daifuku.wms.asrs.tool.location.*" %>
<%@ page import = "jp.co.daifuku.wms.asrs.tool.dbhandler.*" %>
<%@ page import = "jp.co.daifuku.wms.asrs.tool.toolmenu.*" %>
<%@ page import = "jp.co.daifuku.common.*" %>
<%@ page import = "jp.co.daifuku.bluedog.sql.ConnectionManager" %>
<%@ page import = "jp.co.daifuku.wms.base.util.WmsFormatter" %>
<%@ page import="jp.co.daifuku.wms.asrs.tool.location.Area"%>
<%@ page import="jp.co.daifuku.bluedog.util.MessageResources"%>
<%@ page import="jp.co.daifuku.bluedog.util.HTMLUtils"%>

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
          <TD noWrap><h:tab id="tab" templateKey="AST_Inquiry"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WareHouseNumber" templateKey="AST_WareHouseNumber"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_StoreAs" templateKey="AST_StoreAs"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Bank" templateKey="AST_Bank"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:linkedpulldown id="pul_Bank" templateKey="AST_Bank"/></TD></TR>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Query" templateKey="AST_Query"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD>
<%
	Connection conn = null;
	try
	{
			//検索格納区分取得
			String wSelectedWareHouseNumber = (String)session.getAttribute("wSelectedWareHouseStationNumber");
			session.setAttribute("wSelectedWareHouseStationNumber", null);

			//<jp> 検索バンク取得</jp>
			//<en> Get the searched bank.</en>
			String wSelectedBank = (String)session.getAttribute("wSelectedBank");
			session.setAttribute("wSelectedBank", null);

			//<jp> データベースのコネクションを取得する</jp>
			//<en> Get the connection with database.</en>
			conn = ConnectionManager.getConnection(WMSToolConstants.DATASOURCE_NAME);

/*  2003/12/11  INSERT  okamura START  */
			ToolFindUtil wToolFindUtil = new ToolFindUtil(conn);
			ToolShelfHandler shelfhandle = new ToolShelfHandler(conn);
			ToolShelfSearchKey shelfkeyacc = new ToolShelfSearchKey();

			shelfkeyacc.setAccessNgFlag(Shelf.ACCESS_NG);
			Shelf[] shelfAcc = (Shelf[])shelfhandle.findFreeAllocationMinShelf(shelfkeyacc);

			Hashtable accNGhash = new Hashtable();
			if(shelfAcc.length > 0)
			{
				for(int i=0; i < shelfAcc.length; i++)
				{
//					int whno = wToolFindUtil.getWarehouseNumber( shelfAcc[i].getWarehouseStationNo() );
					String location = Shelf.getNumber( Integer.parseInt(wSelectedWareHouseNumber), shelfAcc[i].getBankNo(), shelfAcc[i].getBayNo(), shelfAcc[i].getLevelNo() );
					accNGhash.put(location, "");
				}
			}
/*  2003/12/11  INSERT  okamura END  */

			//<jp> バンク配列のIndex</jp>
			//<en> Index of bank array</en>
			String selectedindex = "0";
			//<jp> 棚イメージサイズ</jp>
			//<en> Size of the Location image</en>
			//String locsize          = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			String locsize          = "";
			String example_locsize  = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			//SOFTZONE0
			String CLR_ZONE0        = "white";		
			//SOFTZONE1
			String CLR_ZONE1        = "RED";		
			//SOFTZONE2
			String CLR_ZONE2        = "GREEN";		
			//SOFTZONE3
			String CLR_ZONE3        = "YELLOW";		
			//SOFTZONE4
			String CLR_ZONE4        = "GRAY";		
			//SOFTZONE5
			String CLR_ZONE5        = "FUCHSIA";		
			//SOFTZONE6
			String CLR_ZONE6        = "BLUE";	
			//SOFTZONE7
			String CLR_ZONE7        = "BLACK";		
			//SOFTZONE8
			String CLR_ZONE8	    = "LIME";  		
			//SOFTZONE9
			String CLR_ZONE9        = "MAROON";  	
			
			//<jp> 倉庫検索</jp>
			//<en> warehouse search</en>
			Warehouse[] wareHouse = null;	

			//<jp> ASRSParamの棚規模の定義を読み込むために使用</jp>
			//<en> This is used to load the definition of location size in ASRSParam</en>
			int    wBank_Max     = 0;
			int[]  wBay_Max      = null;
			int[]  wLevel_Max    = null;
			int[]  wBankNum      = null;
			//<jp>allshelfのデータから混載データ削除時に使用</jp>
			//<en>This is used when deleting mix-load data from allshelf data.</en>
			//<jp>前のデータのステーションNo.</jp>
			//<en>station no. in previous data.</en>
			String lastSTNo = "";		
			String wSelectedWareHouseStationNumber = "";

			//<jp>画面が最初に表示されるとき、有効なBankが取得できません。</jp>
			//<jp>そのため、最初に表示されたとき（wSelectedWareHouseStationNumberがnull）の場合</jp>
			//<jp>このフラグをtrueとします。</jp>
			//<en>When a screen is dispalyed at first, valid bank cannot be obtained.</en>
			//<en>Therefore, for the first dispaly (when wSelectedWareHouseStationNumber is null),</en>
			//<en>this flag should be true.</en>
			boolean firstDisp = false;
			//<jp> 全ての棚検索</jp>
			//<en> search all locations</en>
			Shelf[] allshelf = null;	

			//<jp> 倉庫No</jp>
			//<en> warehouse no.</en>
			int 	  	wWareHouseNumber  = 0;		
			// ZONE0
			int 	  	zone0_len  = 0;				
			// ZONE1
			int 	  	zone1_len  = 0;				
			// ZONE2
			int 	  	zone2_len  = 0;				
			// ZONE3
			int 	  	zone3_len  = 0;				
			// ZONE4
			int 	  	zone4_len  = 0;				
			// ZONE5
			int 	  	zone5_len  = 0;				
			// ZONE6
			int 	  	zone6_len  = 0;				
			// ZONE7
			int 	  	zone7_len  = 0;				
			// ZONE8
			int 	  	zone8_len  = 0;				
			// ZONE9
			int 	  	zone9_len  = 0;				

			ToolWarehouseHandler    whHandler = new ToolWarehouseHandler(conn) ;
			ToolWarehouseSearchKey  whKey     = new ToolWarehouseSearchKey() ;
			if(wSelectedWareHouseNumber != null)
			{
            	whKey.setWarehouseNo(Integer.parseInt(wSelectedWareHouseNumber));
            }
			wareHouse               = (Warehouse[])whHandler.find(whKey) ;

			String[]  	zone_name = null;
			int[]  		zone_id = new int[10];

			if ( wSelectedWareHouseNumber == null || wSelectedBank == null)
			{
				wSelectedWareHouseNumber = "";
				wSelectedBank = "0";
				firstDisp = true;
			}
			else
			{
				//<jp> 検索倉庫取得</jp>
				//<en> Get the searched warehouse.</en>
/*  2003/12/11  DELETE  okamura START */
    //<jp> 上部で宣言するため、ここはコメントにします。</jp>
    //<en> As this is declared in upper class, please see comment here.</en>
//				ToolFindUtil wToolFindUtil = new ToolFindUtil(conn);
/*  2003/12/11  DELETE  okamura END  */

				wSelectedWareHouseStationNumber = wToolFindUtil.getWarehouseStationNumber(Integer.parseInt(wSelectedWareHouseNumber));

				// SHELFのソフトゾーン情報を取得する
				ToolShelfSearchKey shelfkeyzone = new ToolShelfSearchKey();
				shelfkeyzone.setWarehouseStationNo(wareHouse[0].getStationNo());
				shelfkeyzone.setSoftZoneIdOrder(1, true);
				int[] softZones = (int[])shelfhandle.findZones(shelfkeyzone);
				
				// ソフトゾーンをMAX10まで表示する
				int zonecnt = softZones.length;
				Vector tmpVect = new Vector(10) ;
				int zoneid = 0;
				String zone = null;
				for (int i=0 ; i < 10; i++)
				{
					if(i < zonecnt)
					{
						zoneid = softZones[i];
						zone = wToolFindUtil.getSoftZoneName(softZones[i]);
					}
					else
					{
						zoneid = -1;
						zone = "";
					}
					zone_id[i] = zoneid;
					
					if(zoneid == 0)
					{
						zone = "SOFTZONE0";
					}
					tmpVect.add(zone) ;
				}
				// ZONE_NAME
				zone_name = new String[tmpVect.size()] ;
				tmpVect.copyInto(zone_name);
			}

			//ToolZoneInformationHandler zh = new ToolZoneInformationHandler(conn);
			//ToolZoneInformationSearchKey zkey = new ToolZoneInformationSearchKey();

			//<jp> WAREHOUSE表を全件検索した結果と、画面で選択された倉庫ステーションNoと</jp>
			//<jp> マッチングさせマッチしたときに以下の処理を行う</jp>
			//<en> Compare to match the search result of all WAREHOUSE table with the selected warehouse station no.</en>
			//<en> on the screen. If data matches, follow the process below.</en>
			for (int i = 0; i < wareHouse.length; i++)
			{
				if(wSelectedWareHouseStationNumber.equals(wareHouse[i].getStationNo()))
				{
					//<jp> 倉庫No取得</jp>
					//<en> Get the warehouse no.</en>
					wWareHouseNumber = wareHouse[i].getWarehouseNo();
					//<jp> バンクの一覧を取得</jp>
					//<en> Get the bank list.</en>
					wBankNum = shelfhandle.findBanks(wSelectedWareHouseStationNumber);

					if (wBankNum == null || wBankNum.length <= 0)
					{
						firstDisp = true;
//						isError2 = true;
						break;
					}

					//<jp>選択されたBankがBank配列の何番目に有るかを取得する。</jp>
					//<en>Get the index of selected Bank in the Bank array.</en>
					selectedindex = ShelfStatusWebUI.getIndexOfBankArray(wSelectedBank, wBankNum);

					//<jp> バンクのMAX値を取得</jp>
					//<en> Get the max. value of the bank.</en>
					wBank_Max = wBankNum.length;

					//<jp> ベイのバンクごとの一覧を取得</jp>
					//<en> Get the list of bay per bank.</en>
					wBay_Max = shelfhandle.findBays(wSelectedWareHouseStationNumber);

					//<jp> レベルのバンクごとの一覧を取得</jp>
					//<en> Get the list of level per bank.</en>
					wLevel_Max = shelfhandle.findLevels(wSelectedWareHouseStationNumber);
					//long stime = System.currentTimeMillis();

					allshelf = ShelfStatusWebUI.findShelf(conn, wSelectedWareHouseStationNumber, wareHouse[i], 99, wBankNum[Integer.parseInt(selectedindex)]);

					//etime = System.currentTimeMillis();
					//long time = etime-stime;

					break;
				}
			}
			//<jp>最初の表示の場合 画面にはバンク選択のプルダウンだけを表示する。</jp>
			//<en>If it is the firsl display, only tje pull-down list of banks will be displayed for selection.</en>
			if(firstDisp)
			{
			}
			//<jp> 最初の表示では無い場合</jp>
			//<en> If it is not the first display,</en>
			else
			{

				//<jp> allshelfのBank</jp>
				//<en> Bank of allshelf</en>
				int wBank  = 0; 
				//<jp> allshelfのBay</jp>
				//<en> Bay of allshelf</en>
				int wBay   = 0; 
				//<jp> allshelfのLevel</jp>
				//<en> Level of allshelf</en>
				int wLevel = 0; 
				//<jp> allshelfのindexで使用します</jp>
				//<en> Used in index of allshelf index.</en>
				int countshelf = 0; 
				//<jp> 取得したバンク配列のインデックスから、どのバンクを表示するか判断する</jp>
				//<en> Determine which bank to display based on the index of bank array obtained.</en>
				int startbank = 0;
				if ( selectedindex != null && !selectedindex.equals("-1") )
				{
					startbank = Integer.parseInt(selectedindex);
			//<jp>      ----- 現状不要になった計算ロジックですが、削除しないで下さい。 By sawa -----</jp>
			//<jp>		// 選択されたバンクから表示開始するために、不要なインスタンス分カウンタを進める</jp>
			//<en>      ----- This calculation logic is not necessary any longer, however, please do not delete. By sawa -----</en>
			//<en>		// For the display to start by selected bank, proceed the counter for unrequired instances.</en>
			//		for (int i = 0; i < startbank; i++)
			//		{
			//			for (int j = wLevel_Max[i]; j > 0; j--)
			//			{
			//				for (int k = 0; k < wBay_Max[i]; k++)
			//				{
			//					countshelf++;
			//				}
			//			}
			//		}
					wBank_Max = startbank + 1;
				}

				// ゾーン情報から棚表示形式を取得します。
				ToolAreaSearchKey areaKey = new ToolAreaSearchKey();
				areaKey.setAreaType(Area.AREA_TYPE_ASRS);
				areaKey.setWhStationNo(wSelectedWareHouseStationNumber);
				ToolAreaHandler areaHandle = new ToolAreaHandler(conn);
				Area[] area = (Area[])areaHandle.find(areaKey);
				String wLocationStyle = area[0].getLocationStyle();

%>
	<!--<jp> 棚イメージ表示 </jp>-->
	<!--<en> Display the location image. </en>-->
	<table BGCOLOR="#DAD9EE" width = "100%">
	<TR>
 	  <TD>
		<BR><BR>
		Bank <%= wSelectedBank %> 
		<BR>
		<TABLE TITLE="Bank<%= wSelectedBank %>"  BGCOLOR="#DAD9EE" >
 		<TR>
 		  <TD VALIGN="bottom" ROWSPAN="<%= wLevel_Max[startbank]+1 %>" ><IMG SRC="<%=request.getContextPath()%>/img/project/etc/arrow_lvl.gif">
 		  </TD>
<% 
				//<jp> Levelは棚イメージが上下反対なので注意</jp>
				//<en> Attention: Image of location is upside-down with Level.</en>
		 		for (int j = wLevel_Max[startbank]; j > 0; j--)
		 		{
					//<jp> 保持している棚データ以上を要求された場合は無効棚表示とします</jp>
					//<en> If more of location data is requested than preserved, display 'invalid location'.</en>
					if (countshelf >= allshelf.length)
					{
%>
	  <TD><%= locsize %>
	  </TD>
<%
						break;
					}

		 			//<jp> レベル方向の目盛り編集</jp>
		 			//<en> Edit the scales for level direction.</en>
		 			DecimalFormat fmt = new DecimalFormat("000");
		 			String level_scale = fmt.format(allshelf[countshelf].getLevelNo());
					if( j != wLevel_Max[startbank] ){
%>
		<TR>
<%
					}
%>
		<!--<jp> レベル方向の目盛りWrite </jp>-->
		<!--<en> Write the scales for level direction. </en>-->
		  <TD><I><FONT ID="SCALE"><%= level_scale %></FONT></I>
		  </TD>
<%
					for (int k = 0; k < wBay_Max[startbank]; k++)
					{
						//<jp> 保持している棚データ以上を要求された場合は無効棚表示とします</jp>
						//<en> If more of location data is requested than preserved, display 'invalid location'.</en>
						if (countshelf >= allshelf.length)
						{
%>
		  <TD><%= locsize %>
		  </TD>
<%
							break;
						}

						wBank  = allshelf[countshelf].getBankNo();
						wBay   = allshelf[countshelf].getBayNo();
						wLevel = allshelf[countshelf].getLevelNo();
/*  2003/12/11  INSERT  okamura START  */
						int whno = wToolFindUtil.getWarehouseNumber(allshelf[countshelf].getWarehouseStationNo());
						String shelflocation = allshelf[countshelf].getNumber( whno, wBank, wBay, wLevel ) ;
/*  2003/12/11  INSERT  okamura END  */

						//<jp>無効棚では無い場合</jp>
						//<jp> 検索結果のBankとASRSParamに定義してあるBankNumとマッチング</jp>
						//<en> Match the Bank of search result with BankNum defined in ASRSParam.</en>
						if ( ( wBank  == wBankNum[startbank] ) && 	
						     //<jp> indexは'wLevel_Max[startbank]'から始まるので 'wLevel'</jp>
						     //<en> As index starts from 'wLevel_Max[startbank]', 'wLevel'</en>
						     ( wLevel  == j ) &&
						     //<jp> indexは'0'から始まるので 'wBay - 1'</jp>
						     //<en> As index starts from '0', 'wBay - 1'</en>
						     ( wBay - 1 == k ) )			
						{
							String templocno = Shelf.getShelfNumber(wBank, wBay, wLevel);
                            String locInfo = WmsFormatter.toDispLocation(templocno, wLocationStyle);

/*  2003/12/11  INSERT  okamura START  */
							//<jp>使用不可棚設定がされていないか</jp>
							//<en>whether/not the lcoatoin is set unavailable.</en>
							if( !accNGhash.containsKey(shelflocation) )
							{
/*  2003/12/11  INSERT  okamura END  */

								//<jp> 注意 禁止棚かどうかを先にチェックします。</jp>
								//<jp> 禁止棚</jp>
								//<en> Attention : Check first if the location is restricted.</en>
								//<en> Restricted location</en>
								if ( allshelf[countshelf].getSoftZoneId() == zone_id[0] )
								{
										zone0_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE0 %>" title="<%= locInfo %>" ><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[1] )
								{
										zone1_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE1 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[2] )
								{
										zone2_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE2 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[3] )
								{
										zone3_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE3 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[4] )
								{
										zone4_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE4 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[5] )
								{
										zone5_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE5 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[6] )
								{
										zone6_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE6 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[7] )
								{
										zone7_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE7 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[8] )
								{
										zone8_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE8 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
								else if ( allshelf[countshelf].getSoftZoneId() == zone_id[9] )
								{
										zone9_len++;
%>
		  <TD BGCOLOR="<%= CLR_ZONE9 %>" title="<%= locInfo %>"><%= locsize %>
		  </TD>
<%
								}
/*  2003/12/11  INSERT  okamura START  */
							}
							else
							{
%>
			  <TD><%= locsize %>
			  </TD>
<%
							}
/*  2003/12/11  INSERT  okamura END  */
							//<jp> 次の結果へカーソル移動</jp>
							//<en> Move the cursor to the next result data.</en>
							countshelf++;	
						}
						//<jp> 無効棚</jp>
						//<en> Invalid location</en>
						else
						{
%>
		  <TD><%= locsize %>
		  </TD>
<%
						}
					}
%>
		</TR>
<%
				}
				//<jp> ベイ方向の目盛りWrite</jp>
				//<en> Write the scales for bay direction.</en>
				out.write("<TR><TD></TD>");
				for (int n = 0; n < wBay_Max[startbank]; n++)
				{
		 			DecimalFormat fmt = new DecimalFormat("000");
		 			String bay_scale = fmt.format(n + 1);
					out.write("<TD><I><FONT ID=\"SCALE\">" + bay_scale + "</FONT></I></TD>");
				}
				out.write("</TR>");
			
%>
		</TABLE>
		<%= example_locsize %>&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/project/etc/arrow_bay.gif">
		<BR><BR>
	  </TD>
	</TR>
	</TABLE>
	<!--<jp> 棚イメージ説明 </jp>-->
	<!--<en> Describe the location image  </en>-->
	<TABLE BGCOLOR="#DAD9EE" width = "100%">
	<TR>
	  <TD>
	  <TABLE BGCOLOR="#DAD9EE">
	    <TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
	      <%--zone0 --%>
		  <TD BGCOLOR="<%= CLR_ZONE0 %>"><%= example_locsize %></TD><TD><%= zone_name[0] %> (<%= zone0_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone1 --%>
		  <TD BGCOLOR="<%= CLR_ZONE1 %>"><%= example_locsize %></TD><TD><%= zone_name[1] %> (<%= zone1_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone2 --%>
		  <TD BGCOLOR="<%= CLR_ZONE2 %>"><%= example_locsize %></TD><TD><%= zone_name[2] %> (<%= zone2_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone3 --%>
		  <TD BGCOLOR="<%= CLR_ZONE3 %>"><%= example_locsize %></TD><TD><%= zone_name[3] %> (<%= zone3_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone4 --%>
		  <TD BGCOLOR="<%= CLR_ZONE4 %>"><%= example_locsize %></TD><TD><%= zone_name[4] %> (<%= zone4_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
		</TR>
		<TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
	      <%--zone5 --%>
		  <TD BGCOLOR="<%= CLR_ZONE5 %>"><%= example_locsize %></TD><TD><%= zone_name[5] %> (<%= zone5_len %>)</TD><TD>&nbsp;&nbsp;</TD>
	      <%--zone6 --%>
		  <TD BGCOLOR="<%= CLR_ZONE6 %>"><%= example_locsize %></TD><TD><%= zone_name[6] %> (<%= zone6_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone7 --%>
		  <TD BGCOLOR="<%= CLR_ZONE7 %>"><%= example_locsize %></TD><TD><%= zone_name[7] %> (<%= zone7_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone8 --%>
		  <TD BGCOLOR="<%= CLR_ZONE8 %>"><%= example_locsize %></TD><TD><%= zone_name[8] %> (<%= zone8_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
	      <%--zone9 --%>
		  <TD BGCOLOR="<%= CLR_ZONE9 %>"><%= example_locsize %></TD><TD><%= zone_name[9] %> (<%= zone9_len %>)</TD>
		  <TD>&nbsp;&nbsp;</TD>
		</TR>
	  </TABLE>
	</TABLE>
	</TD>
  </TR>

<%
			//<jp>最初の表示のときに表示しないエリア終了の括弧</jp>
			//<en>Bracket for area end that will not be displayed at initial display</en>
			}
	}
	catch(SQLException e)
	{
		e.printStackTrace();
		//<jp>6127005 = データベースエラーが発生しました。</jp>
		//<en>6127005 = Database error occurred.</en>
%>
	    <script>
        <!--
            var contextPath = "<%=request.getContextPath()%>";
            _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6127005"))) %>', 4);
        -->
        </script>
<%
	}
	catch(ReadWriteException e)
	{
		e.printStackTrace();
		//<jp>6127005 = データベースエラーが発生しました。</jp>
		//<en>6127005 = Database error occurred.</en>
%>
	    <script>
        <!--
            var contextPath = "<%=request.getContextPath()%>";
            _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6127005"))) %>', 4);
        -->
        </script>
<%
	}
	catch(Exception e)
	{
		e.printStackTrace();
		//<jp>6127006 = 致命的なエラーが発生しました。</jp>
		//<en>6127006 = Fatal error occurred.</en>
%>
	    <script>
        <!--
            var contextPath = "<%=request.getContextPath()%>";
            _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6127006"))) %>', 4);
        -->
        </script>
<%
	}
	finally
	{
		try
		{
			if(conn != null) conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//<jp>6127005 = データベースエラーが発生しました。</jp>
			//<en>6127005 = Database error occurred.</en>
%>
            <script>
            <!--
                var contextPath = "<%=request.getContextPath()%>";
                _setMessage('<%= HTMLUtils.escapeScript(HTMLUtils.encode(MessageResources.getText("6127005"))) %>', 4);
            -->
            </script>
<%
		}
	}
%>
</TD></TR>
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
