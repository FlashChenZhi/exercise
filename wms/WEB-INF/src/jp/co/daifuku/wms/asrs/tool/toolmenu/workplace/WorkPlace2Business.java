// $Id: WorkPlace2Business.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.AsrsToolListBoxDefine;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.stationlist.StationListBusiness;
import jp.co.daifuku.wms.base.common.ListBoxDefine;

/**
 * ステーション設定（作業場）２画面目の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class WorkPlace2Business
        extends WorkPlace2
        implements WMSToolConstants
{
    // Class fields --------------------------------------------------
    /** 
     * 作業場種別の値を保持するキーです
     */
    public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /** <jp>
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called before each control event call.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // カーソルのセット
        setFocus(btn_Search);
    }

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // 画面タイトル
        lbl_SettingName.setResourceKey(this.getViewState().getString(M_TITLE_KEY));

        Connection conn = null;
        try
        {
            // タブを選択状態に
            tab_WorkPlace_Create.setSelectedIndex(2);
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            ToolStationSearchKey wstkey = new ToolStationSearchKey();
            ToolWorkPlaceHandler wsthdle = new ToolWorkPlaceHandler(conn);
            wstkey.setStationNo(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNO_KEY));
            Station[] wst = (Station[])wsthdle.find(wstkey);

            ToolFindUtil futil = new ToolFindUtil(conn);

            //<jp>倉庫名称</jp>
            //<en>workshop no.</en>
            lbl_In_WareHouseName.setText(futil.getWarehouseName(Integer.parseInt(this.getViewState().getString(
                    WorkPlace1Business.WAREHOUSENUMBER_KEY))));
            //<jp>作業場No.</jp>
            //<en>workshop no.</en>
            lbl_In_ParentStationNumber.setText(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNO_KEY));
            //<jp>作業場名称</jp>
            //<jp>新規作業場の場合、画面入力値から取得</jp>
            //<en>workshop name</en>
            //<en>In case of new workshop, retrieve data from entered value on the screen.</en>
            if (wst.length <= 0)
            {
                lbl_In_ParentStationName.setText(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNAME_KEY));
            }
            //<jp>データベースから取得</jp>
            //<en>Retrieve from database.</en>
            else
            {
                lbl_In_ParentStationName.setText(wst[0].getStationName());
            }
            //<jp>作業場種別名称</jp>
            //<jp>代表ステーションの場合</jp>
            //<en>workshop type name</en>
            //<en>In case of main station,</en>
            if (Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)) == 1)
            {
                lbl_In_WorkPlaceType.setText(DisplayText.getText("TSTATION", "WORKPLACETYPE", Integer.toString(3)));
                //作業場種別の値を保持します
                getViewState().setString(WORKPLACETYPE_KEY, "3");
            }
            //<jp>作業場の場合</jp>
            //<en>In case of workshop,</en>
            else
            {
                lbl_In_WorkPlaceType.setText(DisplayText.getText("TSTATION", "WORKPLACETYPE",
                        this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY)));
                //作業場種別の値を保持します
                getViewState().setString(WORKPLACETYPE_KEY,
                        this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY));
            }
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * When it is returned, this method is called from Popup window.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        String stationworkplaceno = param.getParameter(StationListBusiness.STATIONNO_KEY);
        String stationworkplacename = param.getParameter(StationListBusiness.STATIONNAME_KEY);
        String stationype = param.getParameter(StationListBusiness.WORKPLACETYPE_KEY);
        String aislestationno = param.getParameter(StationListBusiness.AISLESTATIONNO_KEY);
        String agcnumber = param.getParameter(StationListBusiness.AGCNO_KEY);
        //空ではないときに値をセットする
        if (!StringUtil.isBlank(stationworkplaceno))
        {
            lbl_In_StationNoWorkPlaceNo.setText(stationworkplaceno);
            lbl_In_StNameWorkPlaceName.setText(stationworkplacename);
            lbl_In_StationType.setText(stationype);
            lbl_In_AisleStationNumber.setText(aislestationno);
            lbl_In_AGCNumber.setText(agcnumber);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** <jp>
     * ため打ちエリアに値をセットします。
     * @param conn Connection 
     * @param factory ScheduleInterface 
     * @throws Exception 
     </jp> */
    /** <en>
     * Set data to preset area.
     * @param conn Connection 
     * @param factory ScheduleInterface 
     * @throws Exception 
     </en> */
    private void setList(Connection conn, ScheduleInterface factory)
            throws Exception
    {
        String stationnoworkplaceno = "";
        String stationnameworkplacename = "";
        String stationtype = "";
        String aislestationnumber = "";
        String agcnumber = "";

        //行をすべて削除
        lst_WorkPlace.clearRow();

        //Tipで使用する名称
        String title_stationworkplace = DisplayText.getText("LBL-W9052");
        String title_workplacetype = DisplayText.getText("LBL-W9040");

        ToolFindUtil futil = new ToolFindUtil(conn);
        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            WorkPlaceParameter param = (WorkPlaceParameter)paramarray[i];
            //リストへ追加するパラメータ
            //<jp>ステーションNo.</jp>
            //<en>station no.</en>
            stationnoworkplaceno = param.getNumber();
            //<jp>ステーション名称</jp>
            //<en>station name</en>
            stationnameworkplacename = futil.getStationName(param.getNumber());
            //<jp>種別</jp>
            //<en>station type</en>
            if (param.getNumber().equals(""))
            {
                stationtype = "";
            }
            else
            {
                String cl = getClassName(conn, param.getNumber());
                if (cl.equals(""))
                {
                    stationtype = DisplayText.getText("LBL-W9036");
                }
                else
                {
                    stationtype = DisplayText.getText("TSTATION", "STATIONTYPE", cl);
                }
            }
            //<jp>アイルステーションNo.</jp>
            //<en>aisle station no.</en>
            if (param.getAisleNumber().equals(""))
            {
                aislestationnumber = DisplayText.getText("LBL-W9036");
            }
            else
            {
                aislestationnumber = param.getAisleNumber();
            }
            //AGCNo.
            agcnumber = Integer.toString(param.getControllerNumber());

            //行追加
            //最終行を取得
            int count = lst_WorkPlace.getMaxRows();
            lst_WorkPlace.setCurrentRow(count);
            lst_WorkPlace.addRow();
            lst_WorkPlace.setValue(2, stationnoworkplaceno);
            lst_WorkPlace.setValue(3, stationnameworkplacename);
            lst_WorkPlace.setValue(4, stationtype);
            lst_WorkPlace.setValue(5, aislestationnumber);
            lst_WorkPlace.setValue(6, agcnumber);

            //TOOL TIPに表示する文字列を作成
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_stationworkplace, stationnameworkplacename);
            toolTip.add(title_workplacetype, stationtype);

            //TOOL TIPをセットする    
            lst_WorkPlace.setToolTip(count, toolTip.getText());
        }
    }

    /** 
     * 指定された倉庫ナンバーから倉庫ステーションナンバーを取得します。
     * @param  conn コネクション
     * @param  whNo 倉庫ナンバー
     * @return 倉庫ステーションナンバー 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getWHSTNumber(Connection conn, String whNo)
            throws Exception
    {
        ToolWarehouseSearchKey wareKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehd = new ToolWarehouseHandler(conn);
        wareKey.setWarehouseNo(Integer.parseInt(whNo));
        Warehouse[] house = (Warehouse[])warehd.find(wareKey);
        if (house.length <= 0)
        {
            return "";
        }
        return house[0].getStationNo();
    }

    /** 
     * ステーションNo.を検索キーにしてステーション種別を返します。
     * @param  conn コネクション
     * @param  stno ステーションNo.
     * @return ステーション種別
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getClassName(Connection conn, String stno)
            throws Exception
    {
        if (StringUtil.isBlank(stno))
        {
            return "";
        }

        ToolStationSearchKey skey = new ToolStationSearchKey();
        ToolWorkPlaceHandler shdle = new ToolWorkPlaceHandler(conn);
        skey.setStationNo(stno);

        Station[] st = (Station[])shdle.find(skey);

        if (st != null && st.length > 0)
        {
            //<jp>入庫専用</jp>
            //<en>dedicated for storage</en>
            if (st[0].getClassName().equals(StationCreater.CLASS_STORAGE))
            {
                return "0";
            }
            //<jp>出庫専用</jp>
            //<en>dedicated for retrieval</en>
            else if (st[0].getClassName().equals(StationCreater.CLASS_RETRIEVAL))
            {
                return "1";
            }
            //<jp>固定荷受台・自走台車</jp>
            //<en>P&D stand, self-drive cart</en>
            else if (st[0].getClassName().equals(StationCreater.CLASS_INOUTSTATION))
            {
                return "2";
            }
            //<jp>コの字（入庫側）</jp>
            //<en>U-shaped (storage)</en>
            else if (st[0].getClassName().equals(StationCreater.CLASS_FREESTORAGE))
            {
                return "3";
            }
            //<jp>コの字（出庫側）</jp>
            //<en>U-shaped (retrieval)</en>
            else if (st[0].getClassName().equals(StationCreater.CLASS_FREERETRIEVAL))
            {
                return "4";
            }
            else
            {
                return "";
            }
        }
        return "";
    }

    // Event handler methods -----------------------------------------
    /**
     * メニューへボタンがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(getViewState().getString(M_MENUID_KEY)));
    }

    /** <jp>
     * 入力ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when an Add button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //入力チェック
            if (StringUtil.isBlank(lbl_In_StationNoWorkPlaceNo.getText()))
            {
                //6123112 = ステーションNo.または作業場No.を入力してください
                message.setMsgResourceKey("6123112");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            WorkPlaceParameter param = new WorkPlaceParameter();

            //<jp>倉庫ステーションNo.</jp>
            //<en>warehouse station no.</en>
            param.setWareHouseStationNo(getWHSTNumber(conn, this.getViewState().getString(
                    WorkPlace1Business.WAREHOUSENUMBER_KEY)));
            //<jp>作業場No.</jp>
            //<en>workshop no.</en>
            param.setParentNumber(lbl_In_ParentStationNumber.getText());
            //<jp>作業場名称</jp>
            //<en>workshop name</en>
            param.setParentName(lbl_In_ParentStationName.getText());
            //<jp>作業場種別</jp>
            //<jp>作業場</jp>
            //<en>workshop type</en>
            //<en>workshop</en>
            if (Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)) == 0)
            {
                param.setWorkPlaceType(Integer.parseInt((this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY))));
            }
            //<jp>代表ステーション</jp>
            //<en>main station</en>
            else
            {
                param.setWorkPlaceType(3);
            }
            //<jp>代表ステーション</jp>
            //<en>main station</en>
            param.setMainStation(Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)));
            //<jp>ステーションNo.</jp>
            //<en> station no.</en>
            param.setNumber(lbl_In_StationNoWorkPlaceNo.getText());

            ToolStationSearchKey skey = new ToolStationSearchKey();
            ToolWorkPlaceHandler shdle = new ToolWorkPlaceHandler(conn);
            skey.setStationNo(lbl_In_StationNoWorkPlaceNo.getText());
            Station[] st = (Station[])shdle.find(skey);
            if (st.length > 0)
            {
                //<jp>出庫指示可能数</jp>
                //<en>max number of retrieval instruction</en>
                param.setMaxPalletQuantity(st[0].getMaxPalletQty());
                //<jp>搬送指示可能数</jp>
                //<en>max number of carry instruction</en>
                param.setMaxInstruction(st[0].getMaxInstruction());
                //AGCNo.
                param.setControllerNumber(st[0].getGroupController().getControllerNumber());
                //<jp>種別</jp>
                //<en>type</en>
                param.setType(st[0].getStationType());
                //<jp>アイルステーションNo.</jp>
                //<en>aisle station no.</en>
                param.setAisleNumber(st[0].getAisleStationNo());
                //<jp>クラス名称</jp>
                //<en>class name</en>
                param.setClassName(st[0].getClassName());
                //<jp>再入庫搬送指示送信有無</jp>
                //<en>restorage transfer command send existence</en>
                param.setReStoringInstruction(st[0].getReStoringInstruction());
                //<jp>払出し区分</jp>
                //<en>remove type</en>
                param.setRemove(st[0].isRemove());
                //<jp>モード切替種別</jp>
                //<en>mode change type</en>
                param.setModeType(st[0].getModeType());
            }
            else
            {
                //<jp>出庫指示可能数</jp>
                //<en>max number of retrievaly instruction</en>
                param.setMaxPalletQuantity(0);
                //<jp>搬送指示可能数</jp>
                //<en>max number of carry instruction</en>
                param.setMaxInstruction(0);
                //AGCNo.
                param.setControllerNumber(0);
                //<jp>種別</jp>
                //<en>type</en>
                param.setType(0);
                //<jp>アイルステーションNo.</jp>
                //<en>aisle station no.</en>
                param.setAisleNumber("");
                //<jp>クラス名称</jp>
                //<en>class name</en>
                param.setClassName("");
                //<jp>再入庫搬送指示送信有無</jp>
                //<en>restorage transfer command send existence</en>
                param.setReStoringInstruction(0);
                //<jp>払出し区分</jp>
                //<en>remove type</en>
                param.setRemove(true);
                //<jp>モード切替種別</jp>
                //<en>mode change type</en>
                param.setModeType(0);
            }
            //<jp>ためうち処理を行う。</jp>
            //<jp>ためうち成功時は入力エリアをクリアにする。</jp>
            //<en>Process the presetting of data.</en>
            //<en>Clear the data input area when presetting of data succeeded.</en>
            if (factory.addParameter(conn, param))
            {
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
                //クリア処理
                lbl_In_StationNoWorkPlaceNo.setText("");
                lbl_In_StNameWorkPlaceName.setText("");
                lbl_In_StationType.setText("");
                lbl_In_AisleStationNumber.setText("");
                lbl_In_AGCNumber.setText("");
            }
            //セッションに保持する
            this.getSession().setAttribute("FACTORY", factory);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a commit button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            //<jp>ためうちエリアにデータがない場合、倉庫ステーションNo.と作業場をセットする</jp>
            //<en>If the there is no data in preset area, set the warehouse station no. and workshop.</en>
            Parameter[] paramarray = factory.getParameters();
            if (paramarray.length == 0)
            {
                WorkPlaceParameter param = new WorkPlaceParameter();
                //<jp>倉庫ステーションNo.</jp>
                //<en>warehouse station no.</en>
                param.setWareHouseStationNo(getWHSTNumber(conn, this.getViewState().getString(
                        WorkPlace1Business.WAREHOUSENUMBER_KEY)));
                //<jp>作業場No.</jp>
                //<en>workshop no.</en>
                param.setParentNumber(lbl_In_ParentStationNumber.getText());
                factory.addParameter(conn, param);
            }
            //<jp> スケジュールスタート</jp>
            //<en> Start the scheduling.</en>
            factory.startScheduler(conn);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
            //<jp>ためうちエリアにデータがない場合にセットした作業場を、スケジュールが完了したあとにクリアする</jp>
            //<en>If the workshop has been set for the preset area with no data, </en>
            //<en>clear this workshop after the scheduling complete.</en>
            if (paramarray.length == 0)
            {
                factory.removeAllParameters(conn);
            }
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * 取り消しボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a cancel button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //ためうち全削除
            factory.removeAllParameters(conn);
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * リストがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when it clicks on the list.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_WorkPlace_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_WorkPlace.getActiveRow();
            //ためうち１件削除
            factory.removeParameter(conn, index - 1);
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //<jp>コネクションクローズ</jp>
                //<en> Close the connection.</en>
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * 検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a reference button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        //ステーション一覧リスト画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(StationListBusiness.MAINSTATION_KEY, this.getViewState().getString(
                WorkPlace1Business.MAINSTATION_KEY));
        param.setParameter(StationListBusiness.WHSTATIONNO_KEY, this.getViewState().getString(
                WorkPlace1Business.WAREHOUSENUMBER_KEY));
        param.setParameter(StationListBusiness.PARENTSTNO_KEY, this.getViewState().getString(
                WorkPlace1Business.PARENTSTATIONNO_KEY));
        param.setParameter(StationListBusiness.WORKPLACETYPE_KEY, this.getViewState().getString(WORKPLACETYPE_KEY));
        param.setParameter(StationListBusiness.STATIONNO_KEY, "");

        //処理中画面->結果画面
        redirect(AsrsToolListBoxDefine.LST_TOOL_STASION, param, ListBoxDefine.LST_PROGRESS);
    }

    /** <jp>
     * 戻るボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a back button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        forward("/asrs/tool/workplace/WorkPlace1.do");
    }

}
//end of class
