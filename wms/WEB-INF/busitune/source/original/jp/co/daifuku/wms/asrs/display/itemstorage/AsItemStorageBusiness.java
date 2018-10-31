// $Id: AsItemStorageBusiness.java 7452 2010-03-08 04:28:56Z okayama $
package jp.co.daifuku.wms.asrs.display.itemstorage;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.itemstorage.AsItemStorage;
import jp.co.daifuku.wms.asrs.display.itemstorage.ViewStateKeys;
import jp.co.daifuku.wms.asrs.listbox.storageplan.LstAsStoragePlanParams;
import jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsHardZonePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7452 $, $Date:: 2010-03-08 13:28:56 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsItemStorageBusiness
        extends AsItemStorage
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ASRSStorageSet(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_LOCATION) */
    private static final ListCellKey KEY_HIDDEN_LOCATION = new ListCellKey("HIDDEN_LOCATION", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_PLAN_LOT_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOT_NO = new ListCellKey("HIDDEN_PLAN_LOT_NO", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_JAN_CODE) */
    private static final ListCellKey KEY_HIDDEN_JAN_CODE = new ListCellKey("HIDDEN_JAN_CODE", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_DISP_LOCATION) */
    private static final ListCellKey KEY_HIDDEN_DISP_LOCATION = new ListCellKey("HIDDEN_DISP_LOCATION", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_SOFT_ZONE_NAME) */
    private static final ListCellKey KEY_HIDDEN_SOFT_ZONE_NAME = new ListCellKey("HIDDEN_SOFT_ZONE_NAME", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ASRSStorageSet(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet keys */
    private static final ListCellKey[] LST_ASRSSTORAGESET_KEYS = {
        KEY_HIDDEN_AREA_NO,
        KEY_HIDDEN_LOCATION,
        KEY_HIDDEN_PLAN_LOT_NO,
        KEY_HIDDEN_JAN_CODE,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_DISP_LOCATION,
        KEY_HIDDEN_SOFT_ZONE_NAME,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_PLAN_DAY,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_CASE_QTY,
        KEY_LST_ITEM_NAME,
        KEY_LST_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_Zone */
    private WmsHardZonePullDownModel _pdm_pul_Zone;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PullDownModel pul_LSoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_LSoftZone;

    /** ListCellModel lst_ASRSStorageSet */
    private ListCellModel _lcm_lst_ASRSStorageSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsItemStorageBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        setTitle();

        // save a popup event source.
        viewState.setString(_KEY_POPUPSOURCE, request.getParameter(_KEY_POPUPSOURCE));

        // initialize pulldown models.
        initializePulldownModels();

        // process call.
        page_Load_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize components.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_PSearchPlan_Click"))
        {
            // process call.
            btn_PSearchPlan_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchPlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchPlan_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Input_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_StorageStart_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_StorageStart_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStorageSet_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ASRSStorageSet.getActiveCol();

        // choose process.
        if (_lcm_lst_ASRSStorageSet.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_ASRSStorageSet.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // セッションからコネクションを削除する
        SessionUtil.deleteSession(getSession());
        // メニューへ遷移します
        forward(BusinessClassHelper.getSubMenuPath(viewState.getString(Constants.M_MENUID_KEY)));
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_Zone.
        _pdm_pul_Zone = new WmsHardZonePullDownModel(pul_Zone, locale, ui);
        _pdm_pul_Zone.setParent(_pdm_pul_Area);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_LSoftZone.
        _pdm_pul_LSoftZone = new WmsSoftZonePullDownModel(pul_LSoftZone, locale, ui);
        _pdm_pul_LSoftZone.setParent(_pdm_pul_Area);

        // initialize lst_ASRSStorageSet.
        _lcm_lst_ASRSStorageSet = new ListCellModel(lst_ASRSStorageSet, LST_ASRSSTORAGESET_KEYS, locale);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_CASE_QTY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PIECE_QTY, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.STORAGE, "", false);

            // load pul_Zone.
            _pdm_pul_Zone.init(conn);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.STORAGE);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.STORAGE, Distribution.AUTO);

            // load pul_LSoftZone.
            _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ASRSStorageSet_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_AREA_NO);
        line.addToolTip("", KEY_HIDDEN_PLAN_LOT_NO);
        line.addToolTip("", KEY_HIDDEN_JAN_CODE);
        line.addToolTip("", KEY_HIDDEN_ITF);
        line.addToolTip("", KEY_HIDDEN_DISP_LOCATION);
        line.addToolTip("", KEY_HIDDEN_SOFT_ZONE_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StoragePlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsItemStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsItemStorageSCHParams inparam = new AsItemStorageSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                viewState.setObject(ViewStateKeys.NEED_PUL_CHANGE, outparam.get(AsItemStorageSCHParams.NEED_PUL_CHANGE));
            }

            // clear.
            txt_InEnteringQty.setReadOnly(true);
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setChecked(true);
            chk_IssueReport.setEnabled(false);
            pul_LSoftZone.setEnabled(false);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStoragePlanParams inparam = new LstAsStoragePlanParams();
        inparam.set(LstAsStoragePlanParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstAsStoragePlanParams.PLAN_DAY, txt_StoragePlanDate.getValue());
        inparam.set(LstAsStoragePlanParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstAsStoragePlanParams.PLAN_LOT_NO, txt_StoragePlanLotNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchPlan_Click");
        redirect("/asrs/listbox/storageplan/LstAsStoragePlan.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchPlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsStoragePlanParams outparam = new LstAsStoragePlanParams(dialogParams);
        txt_StoragePlanDate.setValue(outparam.get(LstAsStoragePlanParams.PLAN_DAY));
        txt_ItemCode.setValue(outparam.get(LstAsStoragePlanParams.ITEM_CODE));
        txt_StoragePlanLotNo.setValue(outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO));
        txt_InEnteringQty.setValue(outparam.get(LstAsStoragePlanParams.ENTERING_QTY));
        txt_StorageLotNo.setValue(outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO));
        txt_StorageCaseQty.setValue(outparam.get(LstAsStoragePlanParams.CASE_QTY));
        txt_StoragePieceQty.setValue(outparam.get(LstAsStoragePlanParams.PIECE_QTY));

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_StoragePlanLotNo.validate(this, false);
        txt_InEnteringQty.validate(this, false);
        txt_StorageLotNo.validate(this, false);
        txt_StorageCaseQty.validate(this, false);
        txt_StoragePieceQty.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsItemStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsItemStorageSCHParams inparam = new AsItemStorageSCHParams();
            inparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(AsItemStorageSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, viewState.getObject(ViewStateKeys.PLAN_LOT_NO));
            inparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, viewState.getObject(ViewStateKeys.PLAN_AREA));
            inparam.set(AsItemStorageSCHParams.LOCATION, viewState.getObject(ViewStateKeys.PLAN_LOCATION));
            inparam.set(AsItemStorageSCHParams.PRINT_FLAG, chk_IssueReport.getValue());
            inparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(AsItemStorageSCHParams.JOB_TYPE, "");
            inparam.set(AsItemStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsItemStorageSCHParams.ZONE_NO, _pdm_pul_Zone.getSelectedValue());
            inparam.set(AsItemStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsItemStorageSCHParams.STORAGE_QTY, 0);
            inparam.set(AsItemStorageSCHParams.PLAN_QTY, 0);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_ASRSStorageSet.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                AsItemStorageSCHParams lineparam = new AsItemStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
                lineparam.set(AsItemStorageSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, viewState.getObject(ViewStateKeys.PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, viewState.getObject(ViewStateKeys.PLAN_AREA));
                lineparam.set(AsItemStorageSCHParams.LOCATION, viewState.getObject(ViewStateKeys.PLAN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsItemStorageSCHParams.JOB_TYPE, "");
                lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
                lineparam.set(AsItemStorageSCHParams.LOCATION, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_CASE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_PIECE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_QTY, 0);
                lineparam.set(AsItemStorageSCHParams.PLAN_QTY, 0);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (confirm && !sch.check(inparam, inparams))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                int editRow = _lcm_lst_ASRSStorageSet.getEditRow();
                Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
                ListCellLine line = newline ? _lcm_lst_ASRSStorageSet.getNewLine()
                                            : _lcm_lst_ASRSStorageSet.get(editRow);
                line.setValue(KEY_LST_PLAN_DAY, txt_StoragePlanDate.getValue());
                line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsItemStorageSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_CASE_QTY, txt_StorageCaseQty.getValue());
                line.setValue(KEY_LST_PLAN_LOT_NO, txt_StorageLotNo.getValue());
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsItemStorageSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PIECE_QTY, txt_StoragePieceQty.getValue());
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(AsItemStorageSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_LOCATION, outparam.get(AsItemStorageSCHParams.LOCATION));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(AsItemStorageSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDDEN_JAN_CODE, outparam.get(AsItemStorageSCHParams.JAN_CODE));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(AsItemStorageSCHParams.ITF));
                line.setValue(KEY_HIDDEN_DISP_LOCATION, outparam.get(AsItemStorageSCHParams.DISP_LOCATION));
                line.setValue(KEY_HIDDEN_SOFT_ZONE_NAME, outparam.get(AsItemStorageSCHParams.SOFT_ZONE_NAME));

                // add new row or update editing row.
                lst_ASRSStorageSet_SetLineToolTip(line);
                if (newline)
                {
                    _lcm_lst_ASRSStorageSet.add(line, true);
                }
                else
                {
                    _lcm_lst_ASRSStorageSet.set(editRow, line);
                }
                break;
            }

            // reset editing row.
            _lcm_lst_ASRSStorageSet.resetEditRow();
            _lcm_lst_ASRSStorageSet.resetHighlight();

            // clear.
            btn_StorageStart.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_IssueReport.setEnabled(true);
            pul_LSoftZone.setEnabled(true);

            // set focus.
            setFocus(txt_StorageLotNo);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_ItemCode.setValue(null);
        txt_StoragePlanLotNo.setValue(null);
        txt_InEnteringQty.setValue(null);
        txt_InRestCaseQty.setValue(null);
        txt_InRestPieceQty.setValue(null);
        txt_StorageLotNo.setValue(null);
        txt_StorageCaseQty.setValue(null);
        txt_StoragePieceQty.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_StorageStart_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsItemStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsItemStorageSCHParams lineparam = new AsItemStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsItemStorageSCHParams.PRINT_FLAG, chk_IssueReport.getValue());
                lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsItemStorageSCHParams.JOB_TYPE, "");
                lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(AsItemStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
                lineparam.set(AsItemStorageSCHParams.LOCATION, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.ZONE_NO, _pdm_pul_Zone.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_CASE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_PIECE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_QTY, 0);
                lineparam.set(AsItemStorageSCHParams.SOFT_ZONE_ID, _pdm_pul_LSoftZone.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.FUNCTION_ID, "M_FUNCTIONID");
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_ASRSStorageSet.resetEditRow();
                _lcm_lst_ASRSStorageSet.resetHighlight();
                _lcm_lst_ASRSStorageSet.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                lst_ASRSStorageSet.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Zone.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_HIDDEN_JAN_CODE), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ITF), "");
                part11List.add(line.getViewString(KEY_HIDDEN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_LOCATION), "");
                part11List.add(line.getViewString(KEY_HIDDEN_PLAN_LOT_NO), "");

                if (chk_IssueReport.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                    part11List.add(_pdm_pul_LSoftZone.getSelectedStringValue(), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ASRSStorageSet.resetEditRow();
            _lcm_lst_ASRSStorageSet.resetHighlight();

            // clear.
            _lcm_lst_ASRSStorageSet.clear();
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setEnabled(false);
            pul_LSoftZone.setEnabled(false);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ASRSStorageSet.clear();
        btn_StorageStart.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_IssueReport.setEnabled(false);
        pul_LSoftZone.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ASRSStorageSet.getActiveRow();
        lst_ASRSStorageSet.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ASRSStorageSet.get(row);

        // output display.
        txt_StoragePlanDate.setValue(line.getValue(KEY_LST_PLAN_DAY));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_StoragePlanLotNo.setValue(line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
        txt_InEnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_StorageLotNo.setValue(line.getValue(KEY_LST_PLAN_LOT_NO));
        txt_StorageCaseQty.setValue(line.getValue(KEY_LST_CASE_QTY));
        txt_StoragePieceQty.setValue(line.getValue(KEY_LST_PIECE_QTY));

        // highlight active row.
        _lcm_lst_ASRSStorageSet.resetHighlight();
        _lcm_lst_ASRSStorageSet.addHighlight(row);
        _lcm_lst_ASRSStorageSet.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ASRSStorageSet.getActiveRow();
        lst_ASRSStorageSet.setCurrentRow(row);

        // reset editing row.
        lst_ASRSStorageSet.removeRow(row);
        _lcm_lst_ASRSStorageSet.resetEditRow();
        _lcm_lst_ASRSStorageSet.resetHighlight();

    }

    /**
     * 画面タイトルを設定します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void setTitle()
            throws Exception
    {
        // httpRequestからメニュー用パラメータを取得する
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // ViewStateへ保存する
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
