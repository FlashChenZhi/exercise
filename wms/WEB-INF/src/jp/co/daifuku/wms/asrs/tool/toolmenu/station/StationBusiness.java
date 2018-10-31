// $Id: StationBusiness.java 4289 2009-05-14 11:32:30Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.station;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.StationParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;

/**
 * ステーション設定（ステーション）の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4289 $, $Date: 2009-05-14 20:32:30 +0900 (木, 14 5 2009) $
 * @author  $Author: okamura $
 */
public class StationBusiness
        extends Station
        implements WMSToolConstants
{
    // Class fields --------------------------------------------------
    /** 
     * 親ステーションの値を保持するキーです
     */
    public static final String PARENTSTATIONNO_KEY = "PARENTSTATIONNO_KEY";

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
        if (pul_StoreAs.getEnabled())
        {
            setFocus(pul_StoreAs);
        }
        else
        {
            setFocus(txt_StNumber);
        }
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
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);
            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(M_MENUID_KEY, menuID);
            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);
            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }

        Connection conn = null;
        try
        {
            Locale locale = this.getHttpRequest().getLocale();
            //クリア処理
            btn_Clear_Click(null);

            //<jp>製番が入力されているかチェックします。</jp>
            if (!checkProduct())
            {
                btn_Add.setEnabled(false);
                btn_Clear.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.STATION, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            //<jp> プルダウン表示</jp>
            //<en> Display the pull-down list.</en>
            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            // プルダウン表示データ（格納区分）
            String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
            // プルダウン表示データ（AGCNo.）
            String[] agc = pull.getGroupControllerPulldownData("");

            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
            ToolPulldownHelper.setPullDown(pul_AGCNo, agc);

            //<jp>保存ファイル名をセットする</jp>
            //<en>Set the name of the file the data will be saved.</en>
            StationParameter searchParam = new StationParameter();
            searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

            StationParameter[] array = (StationParameter[])factory.query(conn, locale, searchParam);
            if (array != null)
            {
                for (int i = 0; i < array.length; i++)
                {
                    ((ToolScheduleInterface)factory).addInitialParameter(array[i]);
                }
            }
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);

            // 設定区分～出庫指示可能数を入庫専用ステーションで選択可能な
            // 内容に更新します。
            rdo_PrivateStorage_Click(null);
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
        String warehousenumber = "";
        String stationnumber = "";
        String stationname = "";
        String type = "";
        String aislenumber = "";
        String agcnumber = "";
        String settingtype = "";
        String arrivalcheck = "";
        String loadsizecheck = "";
        String operationdisplay = "";
        String restoringoperation = "";
        String restoringinstruction = "";
        String remove = "";
        String modetype = "";
        String maxinstruction = "";
        String maxpalletquantity = "";
        String parentstationnumber = "";

        ///TOOL TIPの銘板
        String title_StationName = DisplayText.getText("LBL-W9015");
        String title_SettingType = DisplayText.getText("LBL-W9018");
        String title_ArrivalCheck = DisplayText.getText("LBL-W9019");
        String title_LoadSizeCheck = DisplayText.getText("LBL-W9020");
        String title_OperationDisplay = DisplayText.getText("LBL-W9021");
        String title_ReStoringOperation = DisplayText.getText("LBL-W9022");
        String title_ReStoringInstruction = DisplayText.getText("LBL-W9023");
        String title_Remove = DisplayText.getText("LBL-W9068");

        //行をすべて削除
        lst_Station.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            StationParameter param = (StationParameter)paramarray[i];

            //リストへ追加するパラメータ
            warehousenumber = getWHNumber(conn, param.getWareHouseStationNo());
            stationnumber = param.getNumber();
            stationname = param.getName();
            type = DisplayText.getText("TSTATION", "STATIONTYPE", String.valueOf(param.getType()));

            //<jp>アイルステーションNo.がセットされていない場合は、RM号機No.は0をセット。</jp>
            //<en>In case the aisle station no. has not been set, set 0 for RM machine no.</en>
            if (param.getAisleStationNo().equals(""))
            {
                aislenumber = "0";
            }
            else
            {
                aislenumber = getALNumber(conn, param.getAisleStationNo());
            }
            agcnumber = String.valueOf(param.getControllerNumber());
            int settype = param.getSettingType();
            if (settype == jp.co.daifuku.wms.asrs.tool.location.Station.IN_SETTING_CONFIRM)
            {
                settype = 0;
            }
            else
            {
                settype = 1;
            }
            settingtype = DisplayText.getText("TSTATION", "SETTINGTYPE", String.valueOf(settype));
            arrivalcheck = DisplayText.getText("TSTATION", "FLUG", String.valueOf(param.getArrivalCheck()));
            loadsizecheck = DisplayText.getText("TSTATION", "FLUG", String.valueOf(param.getLoadSizeCheck()));
            operationdisplay =
                    DisplayText.getText("TSTATION", "OPERATIONDISPLAY", String.valueOf(param.getOperationDisplay()));
            restoringoperation = DisplayText.getText("TSTATION", "FLUG", String.valueOf(param.getReStoringOperation()));
            restoringinstruction =
                    DisplayText.getText("TSTATION", "FLUG", String.valueOf(param.getReStoringInstruction()));
            remove = DisplayText.getText("TSTATION", "REMOVE", String.valueOf(param.getRemove()));
            modetype = DisplayText.getText("TSTATION", "MODETYPE", String.valueOf(param.getModeType()));
            maxinstruction = String.valueOf(param.getMaxInstruction());
            maxpalletquantity = String.valueOf(param.getMaxPalletQuantity());
            parentstationnumber = param.getParentNumber();

            //行追加
            //最終行を取得
            int count = lst_Station.getMaxRows();
            lst_Station.setCurrentRow(count);
            lst_Station.addRow();
            lst_Station.setValue(0, parentstationnumber);
            lst_Station.setValue(3, warehousenumber);
            lst_Station.setValue(4, stationnumber);
            lst_Station.setValue(5, stationname);
            lst_Station.setValue(6, type);
            lst_Station.setValue(7, aislenumber);
            lst_Station.setValue(8, agcnumber);
            lst_Station.setValue(9, modetype);
            lst_Station.setValue(10, maxinstruction);
            lst_Station.setValue(11, maxpalletquantity);

            //TOOL TIPに表示する文字列を作成
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_StationName, stationname);
            toolTip.add(title_SettingType, settingtype);
            toolTip.add(title_ArrivalCheck, arrivalcheck);
            toolTip.add(title_LoadSizeCheck, loadsizecheck);
            toolTip.add(title_OperationDisplay, operationdisplay);
            toolTip.add(title_ReStoringOperation, restoringoperation);
            toolTip.add(title_ReStoringInstruction, restoringinstruction);
            toolTip.add(title_Remove, remove);

            //TOOL TIPをセットする    
            lst_Station.setToolTip(count, toolTip.getText());
        }
        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_Station.setHighlight(modindex + 1);
        }
    }

    /** <jp>
     * 製番入力済のチェックを行います。
     * @return 製番が入力されている場合は、true、入力されていない場合は、falseを返します。
     </jp> */
    private boolean checkProduct()
    {
        //<jp>製番が入力されているかチェックします。</jp>
        String wf = (String)this.getSession().getAttribute("WorkFolder");

        //<jp>製番が入力されていない場合、ボタンすべてを無効にします。</jp>
        boolean product = false;
        if (wf != null)
        {
            if (wf.trim().length() != 0)
            {
                product = true;
            }
        }

        return product;
    }

    /** 
     * 指定された倉庫ステーションナンバーから倉庫ナンバーを取得します。
     * @param  conn コネクション
     * @param  whStNo 倉庫ステーションナンバー
     * @return 倉庫ナンバー
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getWHNumber(Connection conn, String whStNo)
            throws Exception
    {
        ToolWarehouseSearchKey wareKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehd = new ToolWarehouseHandler(conn);
        wareKey.setWarehouseStationNo(whStNo);
        Warehouse[] house = (Warehouse[])warehd.find(wareKey);
        if (house.length <= 0)
        {
            return "*";
        }
        return String.valueOf(house[0].getWarehouseNo());
    }

    /** 
     * 指定されたアイルステーションナンバーからアイルナンバーを取得します。
     * @param  conn コネクション
     * @param  alStNo アイルステーションナンバー
     * @return アイルナンバー
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getALNumber(Connection conn, String alStNo)
            throws Exception
    {
        ToolAisleSearchKey alKey = new ToolAisleSearchKey();
        ToolAisleHandler aislehd = new ToolAisleHandler(conn);
        alKey.setStationNo(alStNo);
        Aisle[] aisle = (Aisle[])aislehd.find(alKey);
        if (aisle.length <= 0)
        {
            return "";
        }
        return String.valueOf(aisle[0].getAisleNo());
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
     * 指定された格納区分とアイルナンバーからアイルステーションナンバーを取得します。
     * @param  conn コネクション
     * @param  whStNo 倉庫ステーションナンバー
     * @param  alNo アイルナンバー
     * @return アイルステーションナンバー
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getALSTNumber(Connection conn, String whStNo, String alNo)
            throws Exception
    {
        ToolAisleSearchKey alKey = new ToolAisleSearchKey();
        ToolAisleHandler aislehd = new ToolAisleHandler(conn);
        alKey.setWhStationNo(whStNo);
        alKey.setAisleNo(alNo);
        Aisle[] aisle = (Aisle[])aislehd.find(alKey);
        if (aisle.length <= 0)
        {
            return "";
        }
        return aisle[0].getStationNo();
    }

    /** 
     * 修正ボタン押下時のラジオボタンの値をセットします。
     * @param  param StationParameter
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private void setRadioButton(StationParameter param)
            throws Exception
    {
        //種別
        rdo_PrivateStorage.setChecked(false);
        rdo_PrivateRetrieval.setChecked(false);
        rdo_StandShuttleCart.setChecked(false);
        rdo_ConveyorStorageSide.setChecked(false);
        rdo_ConveyorRetrievalSide.setChecked(false);
        switch (param.getType())
        {
            //入庫専用
            case StationCreater.TYPE_STORAGE:
                rdo_PrivateStorage.setChecked(true);
                rdo_PrivateStorage_Click(null);
                break;
            //出庫専用
            case StationCreater.TYPE_RETRIEVAL:
                rdo_PrivateRetrieval.setChecked(true);
                rdo_PrivateRetrieval_Click(null);
                break;
            //固定荷受台・自走台車
            case StationCreater.TYPE_INOUTSTATION:
                rdo_StandShuttleCart.setChecked(true);
                rdo_StandShuttleCart_Click(null);
                break;
            //コの字（入庫側）
            case StationCreater.TYPE_FREESTORAGE:
                rdo_ConveyorStorageSide.setChecked(true);
                rdo_ConveyorStorageSide_Click(null);
                break;
            //コの字（出庫側）
            case StationCreater.TYPE_FREERETRIEVAL:
                rdo_ConveyorRetrievalSide.setChecked(true);
                rdo_ConveyorRetrievalSide_Click(null);
                break;
            default:
                break;
        }
        //設定区分
        rdo_LoadConfirmationSetup.setChecked(false);
        rdo_PrecedenceSetup.setChecked(false);
        switch (param.getSettingType())
        {
            //在荷確認設定
            case jp.co.daifuku.wms.asrs.tool.location.Station.IN_SETTING_CONFIRM:
                rdo_LoadConfirmationSetup.setChecked(true);
                break;
            //先行設定
            case jp.co.daifuku.wms.asrs.tool.location.Station.IN_SETTING_PRECEDE:
                rdo_PrecedenceSetup.setChecked(true);
                break;
            default:
                break;
        }
        //到着報告
        rdo_NotAssignedsArrive.setChecked(false);
        rdo_AssignedArrive.setChecked(false);
        switch (param.getArrivalCheck())
        {
            //無し
            case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_ARRIVALCHECK:
                rdo_NotAssignedsArrive.setChecked(true);
                break;
            //有り
            case jp.co.daifuku.wms.asrs.tool.location.Station.ARRIVALCHECK:
                rdo_AssignedArrive.setChecked(true);
                break;
            default:
                break;
        }
        //荷姿検知器
        rdo_NotAssignedsCarryStyle.setChecked(false);
        rdo_AssignedCarryStyle.setChecked(false);
        switch (param.getLoadSizeCheck())
        {
            //無し
            case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_LOADSIZECHECK:
                rdo_NotAssignedsCarryStyle.setChecked(true);
                break;
            //有り
            case jp.co.daifuku.wms.asrs.tool.location.Station.LOADSIZECHECK:
                rdo_AssignedCarryStyle.setChecked(true);
                break;
            default:
                break;
        }
        //作業指示
        rdo_NotAssignedsWork.setChecked(false);
        rdo_JobsDisplay.setChecked(false);
        rdo_OperationDisplay.setChecked(false);
        switch (param.getOperationDisplay())
        {
            //無し
            case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_OPERATIONDISPLAY:
                rdo_NotAssignedsWork.setChecked(true);
                break;
            //作業表示
            case jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONDISPLAY:
                rdo_JobsDisplay.setChecked(true);
                break;
            //作業指示
            case jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONINSTRUCTION:
                rdo_OperationDisplay.setChecked(true);
                break;
            default:
                break;
        }
        //再入庫作業
        rdo_NotAssignedsReStoring.setChecked(false);
        rdo_AssignedReStoring.setChecked(false);
        switch (param.getReStoringOperation())
        {
            //無し
            case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_CREATE_RESTORING:
                rdo_NotAssignedsReStoring.setChecked(true);
                break;
            //有り
            case jp.co.daifuku.wms.asrs.tool.location.Station.CREATE_RESTORING:
                rdo_AssignedReStoring.setChecked(true);
                break;
            default:
                break;
        }
        //再入庫搬送指示
        rdo_NotAssignedsReStoringCarry.setChecked(false);
        rdo_AssignedReStoringCarry.setChecked(false);
        if ((param.getType() == StationCreater.TYPE_FREESTORAGE)
                || (param.getType() == StationCreater.TYPE_FREERETRIEVAL))
        {
            //コの字（入庫側）又は、コの字（出庫側）は再入庫搬送指示を有りにする
            rdo_AssignedReStoringCarry.setChecked(true);
        }
        // 固定荷受台・自走台車の場合
        else if (param.getType() == StationCreater.TYPE_INOUTSTATION)
        {
            // 姿検知器が有りの場合は再入庫搬送指示を有りにする
            if (param.getLoadSizeCheck() == jp.co.daifuku.wms.asrs.tool.location.Station.LOADSIZECHECK)
            {
                rdo_NotAssignedsReStoringCarry.setEnabled(false);
                rdo_AssignedReStoringCarry.setEnabled(false);
                rdo_AssignedReStoringCarry.setChecked(true);
            }
            // 姿検知器が無しの場合は再入庫搬送指示を無しにする
            else
            {
                rdo_NotAssignedsReStoringCarry.setEnabled(false);
                rdo_NotAssignedsReStoringCarry.setChecked(true);
                rdo_AssignedReStoringCarry.setEnabled(false);
            }
        }
        else
        {
            switch (param.getReStoringInstruction())
            {
                //無し
                case jp.co.daifuku.wms.asrs.tool.location.Station.AGC_STORAGE_SEND:
                    rdo_NotAssignedsReStoringCarry.setChecked(true);
                    break;
                //有り
                case jp.co.daifuku.wms.asrs.tool.location.Station.AWC_STORAGE_SEND:
                    rdo_AssignedReStoringCarry.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        //払出し
        rdo_NotAssignedsPayOut.setChecked(false);
        rdo_AssignedPayOut.setChecked(false);
        switch (param.getRemove())
        {
            //払い出し可
            case jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_OK:
                rdo_AssignedPayOut.setChecked(true);
                break;
            //払い出し不可
            case jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG:
                rdo_NotAssignedsPayOut.setChecked(true);
                break;
            default:
                break;
        }
        //モード切替
        rdo_AWCModeChange.setChecked(false);
        rdo_EquipmentModeChange.setChecked(false);
        rdo_AutoModeChange.setChecked(false);
        rdo_NotAssignedsMode.setChecked(false);
        switch (param.getModeType())
        {
            //AWCモード切替
            case jp.co.daifuku.wms.asrs.tool.location.Station.AWC_MODE_CHANGE:
                rdo_AWCModeChange.setChecked(true);
                break;
            //設備モード切替
            case jp.co.daifuku.wms.asrs.tool.location.Station.AGC_MODE_CHANGE:
                rdo_EquipmentModeChange.setChecked(true);
                break;
            //自動モード切替
            case jp.co.daifuku.wms.asrs.tool.location.Station.AUTOMATIC_MODE_CHANGE:
                rdo_AutoModeChange.setChecked(true);
                break;
            //無し
            case jp.co.daifuku.wms.asrs.tool.location.Station.NO_MODE_CHANGE:
                rdo_NotAssignedsMode.setChecked(true);
                break;
            default:
                break;
        }
    }

    /** 
     * 選択されたラジオボタンの値を取得します。
     * @param  param StationParameter
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private void getRadioButton(StationParameter param)
            throws Exception
    {
        //種別
        //入庫専用
        int type = StationCreater.TYPE_STORAGE;
        RadioButton stationtype = rdo_PrivateStorage.getSelectedItem();
        if (stationtype.getId().equals("rdo_PrivateRetrieval"))
        {
            //出庫専用
            type = StationCreater.TYPE_RETRIEVAL;
        }
        else if (stationtype.getId().equals("rdo_StandShuttleCart"))
        {
            //固定荷受台・自走台車
            type = StationCreater.TYPE_INOUTSTATION;
        }
        else if (stationtype.getId().equals("rdo_ConveyorStorageSide"))
        {
            //コの字入庫
            type = StationCreater.TYPE_FREESTORAGE;
        }
        else if (stationtype.getId().equals("rdo_ConveyorRetrievalSide"))
        {
            //コの字出庫
            type = StationCreater.TYPE_FREERETRIEVAL;
        }
        param.setType(type);
        //設定区分
        int settingtype = jp.co.daifuku.wms.asrs.tool.location.Station.IN_SETTING_CONFIRM;
        RadioButton settingtypenum = rdo_LoadConfirmationSetup.getSelectedItem();
        if (settingtypenum.getId().equals("rdo_PrecedenceSetup"))
        {
            settingtype = jp.co.daifuku.wms.asrs.tool.location.Station.IN_SETTING_PRECEDE;
        }
        param.setSettingType(settingtype);
        //到着報告
        //到着チェック無
        int arrivalcheck = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_ARRIVALCHECK;
        RadioButton arrivalchecknum = rdo_NotAssignedsArrive.getSelectedItem();
        if (arrivalchecknum.getId().equals("rdo_AssignedArrive"))
        {
            //到着チェック有
            arrivalcheck = jp.co.daifuku.wms.asrs.tool.location.Station.ARRIVALCHECK;
        }
        param.setArrivalCheck(arrivalcheck);
        //荷姿検知器
        //荷姿チェック無
        int loadsizecheck = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_LOADSIZECHECK;
        RadioButton loadsizechecknum = rdo_NotAssignedsCarryStyle.getSelectedItem();
        if (loadsizechecknum.getId().equals("rdo_AssignedCarryStyle"))
        {
            //荷姿チェック有
            loadsizecheck = jp.co.daifuku.wms.asrs.tool.location.Station.LOADSIZECHECK;
        }
        param.setLoadSizeCheck(loadsizecheck);
        //作業指示
        //作業指示画面なし
        int operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_OPERATIONDISPLAY;
        RadioButton operationdisplaynum = rdo_NotAssignedsWork.getSelectedItem();
        if (operationdisplaynum.getId().equals("rdo_JobsDisplay"))
        {
            //作業表示運用、完了ボタン無し
            operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONDISPLAY;
        }
        else if (operationdisplaynum.getId().equals("rdo_OperationDisplay"))
        {
            //作業指示運用、完了ボタン有り
            operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONINSTRUCTION;
        }
        param.setOperationDisplay(operationdisplay);
        //再入庫作業
        //再入庫データ作成なし
        int restoringoperation = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_CREATE_RESTORING;
        RadioButton restoringoperationnum = rdo_NotAssignedsReStoring.getSelectedItem();
        if (restoringoperationnum.getId().equals("rdo_AssignedReStoring"))
        {
            //再入庫データ作成
            restoringoperation = jp.co.daifuku.wms.asrs.tool.location.Station.CREATE_RESTORING;
        }
        param.setReStoringOperation(restoringoperation);
        //再入庫搬送指示
        //AGC側で自動的に戻り入庫。搬送指示不要
        int restringinstruction = jp.co.daifuku.wms.asrs.tool.location.Station.AGC_STORAGE_SEND;
        RadioButton restringinstructionnum = rdo_NotAssignedsReStoringCarry.getSelectedItem();
        if (restringinstructionnum.getId().equals("rdo_AssignedReStoringCarry"))
        {
            //AWC側で搬送指示必要
            restringinstruction = jp.co.daifuku.wms.asrs.tool.location.Station.AWC_STORAGE_SEND;
        }
        param.setReStoringInstruction(restringinstruction);
        //モード切替
        //モード切替無し
        int modetype = jp.co.daifuku.wms.asrs.tool.location.Station.NO_MODE_CHANGE;
        RadioButton modetypenum = rdo_AWCModeChange.getSelectedItem();
        if (modetypenum.getId().equals("rdo_AWCModeChange"))
        {
            //AWCモード切替
            modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AWC_MODE_CHANGE;
        }
        else if (modetypenum.getId().equals("rdo_EquipmentModeChange"))
        {
            //設備モード切替
            modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AGC_MODE_CHANGE;
        }
        else if (modetypenum.getId().equals("rdo_AutoModeChange"))
        {
            //自動モード切替
            modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AUTOMATIC_MODE_CHANGE;
        }
        param.setModeType(modetype);
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
     * @throws Exception 全ての例外を報告します。
     </en> */
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //入力チェック
            //プルダウンが正常に表示されていない場合
            if (pul_StoreAs.getSelectedValue() == null)
            {
                //6123100 = 倉庫情報がありません。倉庫設定画面で登録してください
                message.setMsgResourceKey("6123100");
                return;
            }
            txt_StNumber.validate(this, true);
            txt_StationName.validate(this, true);
            txt_AisleNumber.validate(this, true);
            if (pul_AGCNo.getSelectedValue() == null)
            {
                //<jp>6123078 = グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                //<en>6123078 = There is no group controller information. </en>
                //<en>Please register in group controller setting screen.</en>
                message.setMsgResourceKey("6123078");
                return;
            }
            txt_MaxInstrucion.validate(this, false);
            txt_MaxPalletQuantity.validate(this, false);

            if (txt_AisleNumber.getInt() < 0)
            {
                //6123282 = アイルNoには０以上の値を指定してください。
                message.setMsgResourceKey("6123282");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            StationParameter param = new StationParameter();
            String whno = "";
            int employmenttype = Warehouse.OPEN;
            int contno = 0;
            String alstno = "";
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();

            if (pul_StoreAs.getSelectedValue() != null)
            {
                //<jp> 格納区分プルダウンにデータがある場合</jp>
                //<en> If the data exists in pull-down list of strage type,</en>
                whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());
                //<jp>倉庫ステーションNo.をセットし検索。</jp>
                //<jp>倉庫インスタンスから運用種別を取得し、運用種別がクローズの場合はデフォルトで払出し不可をセットする。</jp>
                //<en>Set the warehouse station no. and conduct the search.</en>
                //<en>Retrieve the operation type from the warehouse instance. If the type is 'closed operation',</en>
                //<en>set 'unavailable for removal' as default setting.</en>
                warehousekey.setWarehouseStationNo(whno);
                Warehouse[] whArray = (Warehouse[])warehousehandle.find(warehousekey);
                employmenttype = whArray[0].getEmploymentType();
                if (employmenttype == Warehouse.CLOSE)
                {
                    //払出し不可
                    param.setRemove(jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG);
                }
                else
                {
                    //払い出し不可
                    int payout = jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG;
                    RadioButton rdo = rdo_NotAssignedsPayOut.getSelectedItem();
                    if (rdo.getId().equals("rdo_AssignedPayOut"))
                    {
                        //払い出し可
                        payout = jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_OK;
                    }
                    //<jp>入力値</jp>
                    //<en>Input value</en>
                    param.setRemove(payout);
                }
            }
            if (pul_AGCNo.getSelectedValue() != null)
            {
                //<jp> AGCNo.プルダウンにデータがある場合</jp>
                //<en> If the data exists in AGCNo.pull-down list,</en>
                contno = Integer.parseInt(pul_AGCNo.getSelectedValue());
            }
            //<jp> アイル結合ステーションの場合</jp>
            //<en> In case of aisle connected stations:</en>
            if (txt_AisleNumber.getInt() == 0)
            {
                //<jp> アイルステーションNo.は空白をセット</jp>
                //<en> Set blank for the aisle station no.</en>
                param.setAisleStationNo(alstno);
            }
            //<jp> アイル独立ステーションの場合</jp>
            //<en> In case of stand alone stations:</en>
            else
            {
                alstno = getALSTNumber(conn, whno, String.valueOf(txt_AisleNumber.getInt()));
                //<jp> AISLE表に登録されているかチェック</jp>
                //<en> Check to see if the station is registered in AISLE table.</en>
                if (alstno.equals(""))
                {
                    //<jp>入力されたアイルNo.は存在しません。アイル設定画面で登録してください</jp>
                    //<en>Entered aisle no. does not exist in table. Please register on aisle setting screen.</en>
                    message.setMsgResourceKey("6123101");
                    return;
                }
                //<jp> AISLE表に登録されている場合</jp>
                //<en> If the station exists in AISLE table,</en>
                else
                {
                    //<jp>アイルステーションNo.</jp>
                    //<en>aisle station no.</en>
                    param.setAisleStationNo(alstno);
                }
            }

            //<jp>倉庫ステーションNo</jp>
            //<en>warehouse station no.</en>
            param.setWareHouseStationNo(whno);
            //AGCNo.
            param.setControllerNumber(contno);
            //<jp>ステーションNo.</jp>
            //<en>station no.</en>
            param.setNumber(txt_StNumber.getText());
            //<jp>ステーション名称</jp>
            //<en>station name.</en>
            param.setName(txt_StationName.getText());
            //<jp>搬送指示可能数</jp>
            //<en>max number of carry insruction sendable</en>
            int maxInst = 0;
            try
            {
                maxInst = Integer.parseInt(txt_MaxInstrucion.getText());
            }
            catch (NumberFormatException ee)
            {
                // nothing to do
            }
            param.setMaxInstruction(maxInst);
            //<jp>出庫指示可能数</jp>
            //<en>max number of retrieval insruction sendable</en>
            int maxQnt = 0;
            try
            {
                maxQnt = Integer.parseInt(txt_MaxPalletQuantity.getText());
            }
            catch (NumberFormatException ee)
            {
                // nothing to do
            }
            param.setMaxPalletQuantity(maxQnt);
            //<jp>親ステーションNo.</jp>
            //<en>parent station no.</en>    
            param.setParentNumber(this.getViewState().getString(PARENTSTATIONNO_KEY));

            //ラジオボタンの値を取得しParamにセットします。
            getRadioButton(param);

            if (factory.addParameter(conn, param))
            {
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
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

    /** 
     * クリアボタンの処理を実装します。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        txt_StNumber.setText("");
        txt_StationName.setText("");
        txt_AisleNumber.setText("");

        //種別
        rdo_PrivateStorage.setChecked(true);
        rdo_PrivateRetrieval.setChecked(false);
        rdo_StandShuttleCart.setChecked(false);
        rdo_ConveyorStorageSide.setChecked(false);
        rdo_ConveyorRetrievalSide.setChecked(false);

        // 設定区分～出庫指示可能数を入庫専用ステーションで選択可能な
        // 内容に更新します。
        rdo_PrivateStorage_Click(null);

        pul_StoreAs.setSelectedIndex(0);
        pul_AGCNo.setSelectedIndex(0);
    }

    /** <jp>
     * 取り消しボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
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
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called when it clicks on the list.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_Station_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_Station.getActiveRow();
            //**** 修正 ****
            if (lst_Station.getActiveCol() == 1)
            {
                //現在の行をセット
                lst_Station.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_Station.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);
                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                StationParameter param = (StationParameter)factory.getUpdatingParameter();
                //格納区分
                String whst = getWHNumber(conn, param.getWareHouseStationNo());
                pul_StoreAs.setSelectedItem(whst);
                //ステーションNo.
                txt_StNumber.setText(param.getNumber());
                //ステーション名称
                txt_StationName.setText(param.getName());

                //<jp>アイルステーションNo.がセットされていない場合は、RM号機No.は0をセット。</jp>
                //<en>In case the aisle station no. has not been set, set 0 for RM machine no.</en>
                if (param.getAisleStationNo().equals(""))
                {
                    txt_AisleNumber.setInt(0);
                }
                else
                {
                    txt_AisleNumber.setText(getALNumber(conn, param.getAisleStationNo()));
                }
                //AGCNo.
                String agcnumber = String.valueOf(param.getControllerNumber());
                pul_AGCNo.setSelectedItem(agcnumber);

                //ラジオボタンをセット
                setRadioButton(param);

                if (!txt_MaxInstrucion.getReadOnly())
                {
                    //搬送指示可能数
                    txt_MaxInstrucion.setText(String.valueOf(param.getMaxInstruction()));
                }
                if (!txt_MaxPalletQuantity.getReadOnly())
                {
                    //出庫指示可能数
                    txt_MaxPalletQuantity.setText(String.valueOf(param.getMaxPalletQuantity()));
                }
                //親ステーション
                getViewState().setString(PARENTSTATIONNO_KEY, param.getParentNumber());
            }
            //**** 削除 ****
            else
            {
                //ためうち１件削除
                factory.removeParameter(conn, index - 1);
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
            }
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
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
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
            //<jp> スケジュールスタート</jp>
            //<en> Start the scheduling.</en>
            factory.startScheduler(conn);
            //設定区分
            rdo_LoadConfirmationSetup.setChecked(true);
            rdo_PrecedenceSetup.setChecked(false);
            //払出し
            rdo_NotAssignedsPayOut.setChecked(true);
            rdo_AssignedPayOut.setChecked(false);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
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

    /** 
     * 入庫専用ステーションのラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_PrivateStorage_Click(ActionEvent e)
            throws Exception
    {
        setStationSetteigClear();

        // 設定区分（在荷確認設定）
        rdo_LoadConfirmationSetup.setChecked(true);
        rdo_LoadConfirmationSetup.setEnabled(true);
        // 設定区分（先行設定）
        rdo_PrecedenceSetup.setChecked(false);
        rdo_PrecedenceSetup.setEnabled(true);

        // 到着報告（無し）
        rdo_NotAssignedsArrive.setChecked(true);
        rdo_NotAssignedsArrive.setEnabled(true);
        // 到着報告（有り）
        rdo_AssignedArrive.setChecked(false);
        rdo_AssignedArrive.setEnabled(true);

        // 荷姿検知器（無し）
        rdo_NotAssignedsCarryStyle.setChecked(true);
        rdo_NotAssignedsCarryStyle.setEnabled(true);
        // 荷姿検知器（有り）
        rdo_AssignedCarryStyle.setChecked(false);
        rdo_AssignedCarryStyle.setEnabled(true);

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(true);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(true);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(true);

        // 搬送指示可能数
        txt_MaxInstrucion.setText("");
        txt_MaxInstrucion.setReadOnly(false);
    }

    /** 
     * 出庫専用ステーションのラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_PrivateRetrieval_Click(ActionEvent e)
            throws Exception
    {
        setStationSetteigClear();

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(true);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(true);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(true);

        // 再入庫作業（無し）
        rdo_NotAssignedsReStoring.setChecked(true);
        rdo_NotAssignedsReStoring.setEnabled(true);
        // 再入庫作業（有り）
        rdo_AssignedReStoring.setChecked(false);
        rdo_AssignedReStoring.setEnabled(true);

        // 払出し（無し）
        rdo_NotAssignedsPayOut.setChecked(true);
        rdo_NotAssignedsPayOut.setEnabled(true);
        // 払出し（有り）
        rdo_AssignedPayOut.setChecked(false);
        rdo_AssignedPayOut.setEnabled(true);

        // 出庫指示可能数
        txt_MaxPalletQuantity.setText("");
        txt_MaxPalletQuantity.setReadOnly(false);
    }

    /** 
     * 固定荷受台･自走台車のラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_StandShuttleCart_Click(ActionEvent e)
            throws Exception
    {
        setStationSetteigClear();

        // 設定区分（在荷確認設定）
        rdo_LoadConfirmationSetup.setChecked(true);
        rdo_LoadConfirmationSetup.setEnabled(true);
        // 設定区分（先行設定）
        rdo_PrecedenceSetup.setChecked(false);
        rdo_PrecedenceSetup.setEnabled(true);

        // 到着報告（無し）
        rdo_NotAssignedsArrive.setChecked(true);
        rdo_NotAssignedsArrive.setEnabled(true);
        // 到着報告（有り）
        rdo_AssignedArrive.setChecked(false);
        rdo_AssignedArrive.setEnabled(true);

        // 荷姿検知器（無し）
        rdo_NotAssignedsCarryStyle.setChecked(true);
        rdo_NotAssignedsCarryStyle.setEnabled(true);
        // 荷姿検知器（有り）
        rdo_AssignedCarryStyle.setChecked(false);
        rdo_AssignedCarryStyle.setEnabled(true);

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(true);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(true);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(true);

        // 再入庫作業（無し）
        rdo_NotAssignedsReStoring.setChecked(true);
        rdo_NotAssignedsReStoring.setEnabled(true);
        // 再入庫作業（有り）
        rdo_AssignedReStoring.setChecked(false);
        rdo_AssignedReStoring.setEnabled(true);

        // 再入庫搬送指示（無し）
        rdo_NotAssignedsReStoringCarry.setChecked(true);
        rdo_NotAssignedsReStoringCarry.setEnabled(false);
        // 再入庫搬送指示（有り）
        rdo_AssignedReStoringCarry.setChecked(false);
        rdo_AssignedReStoringCarry.setEnabled(false);

        // 払出し（無し）
        rdo_NotAssignedsPayOut.setChecked(true);
        rdo_NotAssignedsPayOut.setEnabled(true);
        // 払出し（有り）
        rdo_AssignedPayOut.setChecked(false);
        rdo_AssignedPayOut.setEnabled(true);

        // モード切替（無し）
        rdo_NotAssignedsMode.setChecked(true);
        rdo_NotAssignedsMode.setEnabled(true);
        // モード切替（AWCモード切替）
        rdo_AWCModeChange.setChecked(false);
        rdo_AWCModeChange.setEnabled(true);
        // モード切替（設備モード切替）
        rdo_EquipmentModeChange.setChecked(false);
        rdo_EquipmentModeChange.setEnabled(true);
        // モード切替（自動モード切替）
        rdo_AutoModeChange.setChecked(false);
        rdo_AutoModeChange.setEnabled(true);

        // 搬送指示可能数
        txt_MaxInstrucion.setText("");
        txt_MaxInstrucion.setReadOnly(false);

        // 出庫指示可能数
        txt_MaxPalletQuantity.setText("");
        txt_MaxPalletQuantity.setReadOnly(false);
    }

    /** 
     * コの字(入庫側)のラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_ConveyorStorageSide_Click(ActionEvent e)
            throws Exception
    {
        setStationSetteigClear();

        // 設定区分（在荷確認設定）
        rdo_LoadConfirmationSetup.setChecked(true);
        rdo_LoadConfirmationSetup.setEnabled(true);
        // 設定区分（先行設定）
        rdo_PrecedenceSetup.setChecked(false);
        rdo_PrecedenceSetup.setEnabled(true);

        // 到着報告（無し）
        rdo_NotAssignedsArrive.setChecked(true);
        rdo_NotAssignedsArrive.setEnabled(true);
        // 到着報告（有り）
        rdo_AssignedArrive.setChecked(false);
        rdo_AssignedArrive.setEnabled(true);

        // 荷姿検知器（無し）
        rdo_NotAssignedsCarryStyle.setChecked(true);
        rdo_NotAssignedsCarryStyle.setEnabled(true);
        // 荷姿検知器（有り）
        rdo_AssignedCarryStyle.setChecked(false);
        rdo_AssignedCarryStyle.setEnabled(true);

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(true);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(true);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(true);

        // 再入庫搬送指示（無し）
        rdo_NotAssignedsReStoringCarry.setChecked(false);
        rdo_NotAssignedsReStoringCarry.setEnabled(false);
        // 再入庫搬送指示（有り）
        rdo_AssignedReStoringCarry.setChecked(true);
        rdo_AssignedReStoringCarry.setEnabled(false);

        // 搬送指示可能数
        txt_MaxInstrucion.setText("");
        txt_MaxInstrucion.setReadOnly(false);
    }

    /** 
     * コの字(出庫側)のラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_ConveyorRetrievalSide_Click(ActionEvent e)
            throws Exception
    {
        setStationSetteigClear();

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(true);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(true);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(true);

        // 再入庫作業（無し）
        rdo_NotAssignedsReStoring.setChecked(true);
        rdo_NotAssignedsReStoring.setEnabled(true);
        // 再入庫作業（有り）
        rdo_AssignedReStoring.setChecked(false);
        rdo_AssignedReStoring.setEnabled(true);

        // 再入庫搬送指示（無し）
        rdo_NotAssignedsReStoringCarry.setChecked(false);
        rdo_NotAssignedsReStoringCarry.setEnabled(false);
        // 再入庫搬送指示（有り）
        rdo_AssignedReStoringCarry.setChecked(true);
        rdo_AssignedReStoringCarry.setEnabled(false);

        // 払出し（無し）
        rdo_NotAssignedsPayOut.setChecked(true);
        rdo_NotAssignedsPayOut.setEnabled(true);
        // 払出し（有り）
        rdo_AssignedPayOut.setChecked(false);
        rdo_AssignedPayOut.setEnabled(true);

        // 出庫指示可能数
        txt_MaxPalletQuantity.setText("");
        txt_MaxPalletQuantity.setReadOnly(false);
    }

    /** 
     * 荷姿検知器（無し）のラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_NotAssignedsCarryStyle_Click(ActionEvent e)
            throws Exception
    {
        RadioButton stationtype = rdo_PrivateStorage.getSelectedItem();
        if (stationtype.getId().equals("rdo_StandShuttleCart"))
        {
            //固定荷受台・自走台車
            // 再入庫搬送指示（無し）
            rdo_NotAssignedsReStoringCarry.setChecked(true);
            rdo_NotAssignedsReStoringCarry.setEnabled(false);
            // 再入庫搬送指示（有り）
            rdo_AssignedReStoringCarry.setChecked(false);
            rdo_AssignedReStoringCarry.setEnabled(false);
        }
    }

    /** 
     * 荷姿検知器（有り）のラジオボタンがクリックされた時に呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_AssignedCarryStyle_Click(ActionEvent e)
            throws Exception
    {
        RadioButton stationtype = rdo_PrivateStorage.getSelectedItem();
        if (stationtype.getId().equals("rdo_StandShuttleCart"))
        {
            // 再入庫搬送指示（無し）
            rdo_NotAssignedsReStoringCarry.setChecked(false);
            rdo_NotAssignedsReStoringCarry.setEnabled(false);
            // 再入庫搬送指示（有り）
            rdo_AssignedReStoringCarry.setChecked(true);
            rdo_AssignedReStoringCarry.setEnabled(false);
        }
    }

    /** 
     * ステーションの設定状態を初期状態にします。
     */
    private void setStationSetteigClear()
    {
        // 設定区分（在荷確認設定）
        rdo_LoadConfirmationSetup.setChecked(true);
        rdo_LoadConfirmationSetup.setEnabled(false);
        // 設定区分（先行設定）
        rdo_PrecedenceSetup.setChecked(false);
        rdo_PrecedenceSetup.setEnabled(false);

        // 到着報告（無し）
        rdo_NotAssignedsArrive.setChecked(true);
        rdo_NotAssignedsArrive.setEnabled(false);
        // 到着報告（有り）
        rdo_AssignedArrive.setChecked(false);
        rdo_AssignedArrive.setEnabled(false);

        // 荷姿検知器（無し）
        rdo_NotAssignedsCarryStyle.setChecked(true);
        rdo_NotAssignedsCarryStyle.setEnabled(false);
        // 荷姿検知器（有り）
        rdo_AssignedCarryStyle.setChecked(false);
        rdo_AssignedCarryStyle.setEnabled(false);

        // 作業指示（無し）
        rdo_NotAssignedsWork.setChecked(true);
        rdo_NotAssignedsWork.setEnabled(false);
        // 作業指示（作業表示）
        rdo_JobsDisplay.setChecked(false);
        rdo_JobsDisplay.setEnabled(false);
        // 作業指示（作業指示）
        rdo_OperationDisplay.setChecked(false);
        rdo_OperationDisplay.setEnabled(false);

        // 再入庫作業（無し）
        rdo_NotAssignedsReStoring.setChecked(true);
        rdo_NotAssignedsReStoring.setEnabled(false);
        // 再入庫作業（有り）
        rdo_AssignedReStoring.setChecked(false);
        rdo_AssignedReStoring.setEnabled(false);

        // 再入庫搬送指示（無し）
        rdo_NotAssignedsReStoringCarry.setChecked(true);
        rdo_NotAssignedsReStoringCarry.setEnabled(false);
        // 再入庫搬送指示（有り）
        rdo_AssignedReStoringCarry.setChecked(false);
        rdo_AssignedReStoringCarry.setEnabled(false);

        // 払出し（無し）
        rdo_NotAssignedsPayOut.setChecked(true);
        rdo_NotAssignedsPayOut.setEnabled(false);
        // 払出し（有り）
        rdo_AssignedPayOut.setChecked(false);
        rdo_AssignedPayOut.setEnabled(false);

        // モード切替（無し）
        rdo_NotAssignedsMode.setChecked(true);
        rdo_NotAssignedsMode.setEnabled(false);
        // モード切替（AWCモード切替）
        rdo_AWCModeChange.setChecked(false);
        rdo_AWCModeChange.setEnabled(false);
        // モード切替（設備モード切替）
        rdo_EquipmentModeChange.setChecked(false);
        rdo_EquipmentModeChange.setEnabled(false);
        // モード切替（自動モード切替）
        rdo_AutoModeChange.setChecked(false);
        rdo_AutoModeChange.setEnabled(false);

        // 搬送指示可能数
        txt_MaxInstrucion.setText("");
        txt_MaxInstrucion.setReadOnly(true);

        // 出庫指示可能数
        txt_MaxPalletQuantity.setText("");
        txt_MaxPalletQuantity.setReadOnly(true);
    }
}
//end of class
