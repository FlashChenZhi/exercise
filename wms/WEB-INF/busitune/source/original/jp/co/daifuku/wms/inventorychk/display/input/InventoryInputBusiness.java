// $Id: InventoryInputBusiness.java 7828 2010-04-16 10:10:58Z shibamoto $
package jp.co.daifuku.wms.inventorychk.display.input;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCH;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCHParams;
import jp.co.daifuku.wms.inventorychk.display.dataadd.InventoryDataAddParams;
import jp.co.daifuku.wms.inventorychk.display.input.InventoryInput;
import jp.co.daifuku.wms.inventorychk.display.input.ViewStateKeys;
import jp.co.daifuku.wms.inventorychk.exporter.InventoryCheckListParams;
import jp.co.daifuku.wms.inventorychk.listbox.jobno.LstInventoryJobNoParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInputSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInputSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7828 $, $Date:: 2010-04-16 19:10:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryInputBusiness
        extends InventoryInput
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_InventoryResultInput(HIDDEN_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_JOBNO = new ListCellKey("HIDDEN_JOBNO", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(HIDDEN_STATUSFLAG) */
    private static final ListCellKey KEY_HIDDEN_STATUSFLAG = new ListCellKey("HIDDEN_STATUSFLAG", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(HIDDEN_INVENTCASEQTY) */
    private static final ListCellKey KEY_HIDDEN_INVENTCASEQTY = new ListCellKey("HIDDEN_INVENTCASEQTY", new NumberCellColumn(0), false, false);

    /** lst_InventoryResultInput(HIDDEN_INVENTPIECEQTY) */
    private static final ListCellKey KEY_HIDDEN_INVENTPIECEQTY = new ListCellKey("HIDDEN_INVENTPIECEQTY", new NumberCellColumn(0), false, false);

    /** lst_InventoryResultInput(HIDDEN_NEWDATE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_NEWDATE_FLAG = new ListCellKey("HIDDEN_NEWDATE_FLAG", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT = new ListCellKey("LST_CYCLE_COUNT", new CheckBoxColumn(), true, true);

    /** lst_InventoryResultInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA = new ListCellKey("LST_AREA", new AreaCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_LOCATION) */
    private static final ListCellKey KEY_LST_LOCATION = new ListCellKey("LST_LOCATION", new LocationCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT_CASE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_CASE_QTY = new ListCellKey("LST_CYCLE_COUNT_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_PIECE_QTY = new ListCellKey("LST_CYCLE_COUNT_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_InventoryResultInput keys */
    private static final ListCellKey[] LST_INVENTORYRESULTINPUT_KEYS = {
        KEY_HIDDEN_JOBNO,
        KEY_HIDDEN_STATUSFLAG,
        KEY_HIDDEN_INVENTCASEQTY,
        KEY_HIDDEN_INVENTPIECEQTY,
        KEY_HIDDEN_NEWDATE_FLAG,
        KEY_LST_CYCLE_COUNT,
        KEY_LST_CANCEL,
        KEY_LST_DELETE,
        KEY_LST_STATUS,
        KEY_LST_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_CYCLE_COUNT_CASE_QTY,
        KEY_LST_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_CYCLE_COUNT_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel Inventory */
    private RadioButtonGroup _grp_Inventory;

    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_InventoryResultInput */
    private ListCellModel _lcm_lst_InventoryResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryInputBusiness()
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
        if (eventSource.equals("btn_P_Search_JobNo_Click"))
        {
            // process call.
            btn_P_Search_JobNo_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_AddNewData_Click"))
        {
            // process call.
            btn_P_AddNewData_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_InventoryListIssue_Click"))
        {
            // process call.
            btn_InventoryListIssue_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_JobNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_JobNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LocationRange_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LocationRange_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_JobNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_JobNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_InventoryListIssue_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_InventoryListIssue_Click_Process(true);
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_AddNewData_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_AddNewData_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_InventoryResultInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_InventoryResultInput.getActiveCol();

        // choose process.
        if (_lcm_lst_InventoryResultInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
        else if (_lcm_lst_InventoryResultInput.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
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

        // initialize Inventory.
        _grp_Inventory = new RadioButtonGroup(new RadioButton[]{rdo_JobNo, rdo_LocationRange}, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize lst_InventoryResultInput.
        _lcm_lst_InventoryResultInput = new ListCellModel(lst_InventoryResultInput, LST_INVENTORYRESULTINPUT_KEYS, locale);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_AREA, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_LOCATION, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT_CASE_QTY, false);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT_PIECE_QTY, false);
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);
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
    private void lst_InventoryResultInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        setFocus(rdo_JobNo);
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
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                viewState.setObject(ViewStateKeys.MASTER, outparam.get(InventoryInputSCHParams.STOCK_MASTER));
            }

            // clear.
            rdo_JobNo.setChecked(true);
            txt_ListWorkNo.setReadOnly(false);
            btn_P_Search_JobNo.setEnabled(true);
            pul_Area.setEnabled(false);
            txt_LocationFrom.setReadOnly(true);
            txt_LocationTo.setReadOnly(true);
            txt_ItemCode.setReadOnly(true);
            chk_InventoryOnlyDisp.setEnabled(false);
            btn_Set.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_P_AddNewData.setEnabled(false);
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
    private void rdo_JobNo_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setReadOnly(false);
        btn_P_Search_JobNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        chk_InventoryOnlyDisp.setEnabled(false);

        // set focus.
        setFocus(txt_ListWorkNo);
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_LocationRange_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setReadOnly(true);
        btn_P_Search_JobNo.setEnabled(false);
        pul_Area.setEnabled(true);
        txt_LocationFrom.setReadOnly(false);
        txt_LocationTo.setReadOnly(false);
        txt_ItemCode.setReadOnly(false);
        chk_InventoryOnlyDisp.setEnabled(true);

        // set focus.
        setFocus(pul_Area);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Search_JobNo_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstInventoryJobNoParams inparam = new LstInventoryJobNoParams();
        inparam.set(LstInventoryJobNoParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
        inparam.set(LstInventoryJobNoParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_JobNo_Click");
        redirect("/inventorychk/listbox/jobno/LstInventoryJobNo.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_JobNo_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstInventoryJobNoParams outparam = new LstInventoryJobNoParams(dialogParams);
        txt_ListWorkNo.setValue(outparam.get(LstInventoryJobNoParams.LIST_WORK_NO));

        // set focus.
        setFocus(txt_ListWorkNo);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_JobNo.validate(true);
        txt_ListWorkNo.validate(this, false);
        txt_LocationFrom.validate(this, false);
        txt_LocationTo.validate(this, false);
        txt_ItemCode.validate(this, false);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.set(InventoryInputSCHParams.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
            inparam.set(InventoryInputSCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryInputSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(InventoryInputSCHParams.LOCATION_FROM, txt_LocationFrom.getValue());
            inparam.set(InventoryInputSCHParams.LOCATION_TO, txt_LocationTo.getValue());
            inparam.set(InventoryInputSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryInputSCHParams.CONDITION_SELECT, _grp_Inventory.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_InventoryResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryResultInput.getNewLine();
                line.setValue(KEY_LST_STATUS, outparam.get(InventoryInputSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_AREA, outparam.get(InventoryInputSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION, outparam.get(InventoryInputSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryInputSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(InventoryInputSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(InventoryInputSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryInputSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                viewState.setObject(ViewStateKeys.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
                viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION_FROM, txt_LocationFrom.getValue());
                viewState.setObject(ViewStateKeys.LOCATION_TO, txt_LocationTo.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
                viewState.setObject(ViewStateKeys.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
                viewState.setObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
                line.setValue(KEY_HIDDEN_JOBNO, outparam.get(InventoryInputSCHParams.JOBNO));
                line.setValue(KEY_HIDDEN_STATUSFLAG, outparam.get(InventoryInputSCHParams.STATUSFLAG));
                line.setValue(KEY_HIDDEN_INVENTCASEQTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_HIDDEN_INVENTPIECEQTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_HIDDEN_NEWDATE_FLAG, outparam.get(InventoryInputSCHParams.NEWDATE_FLAG));
                lst_InventoryResultInput_SetLineToolTip(line);
                _lcm_lst_InventoryResultInput.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_P_AddNewData.setEnabled(true);
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
     * @param confirm
     * @throws Exception
     */
    private void btn_InventoryListIssue_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_ListWorkNo.validate(this, false);
        pul_Area.validate(this, true);
        txt_LocationFrom.validate(this, false);
        txt_LocationTo.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCheckDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new InventoryCheckDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            InventoryCheckDASCHParams inparam = new InventoryCheckDASCHParams();
            inparam.set(InventoryCheckDASCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryCheckDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(InventoryCheckDASCHParams.LOCATION_FROM, txt_LocationFrom.getValue());
            inparam.set(InventoryCheckDASCHParams.LOCATION_TO, txt_LocationTo.getValue());
            inparam.set(InventoryCheckDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
            inparam.set(InventoryCheckDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryCheckDASCHParams.INVENTORY, _grp_Inventory.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_InventoryListIssue_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("InventoryCheckList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                InventoryCheckListParams expparam = new InventoryCheckListParams();
                expparam.set(InventoryCheckListParams.DFK_DS_NO, outparam.get(InventoryCheckDASCHParams.DFK_DS_NO));
                expparam.set(InventoryCheckListParams.DFK_USER_ID, outparam.get(InventoryCheckDASCHParams.DFK_USER_ID));
                expparam.set(InventoryCheckListParams.DFK_USER_NAME, outparam.get(InventoryCheckDASCHParams.DFK_USER_NAME));
                expparam.set(InventoryCheckListParams.JOB_NO, outparam.get(InventoryCheckDASCHParams.JOB_NO));
                expparam.set(InventoryCheckListParams.SYS_DAY, outparam.get(InventoryCheckDASCHParams.SYS_DAY));
                expparam.set(InventoryCheckListParams.SYS_TIME, outparam.get(InventoryCheckDASCHParams.SYS_TIME));
                expparam.set(InventoryCheckListParams.AREA_NO, outparam.get(InventoryCheckDASCHParams.AREA_NO));
                expparam.set(InventoryCheckListParams.AREA_NAME, outparam.get(InventoryCheckDASCHParams.AREA_NAME));
                expparam.set(InventoryCheckListParams.LOCATION_NO, outparam.get(InventoryCheckDASCHParams.LOCATION_NO));
                expparam.set(InventoryCheckListParams.ITEM_CODE, outparam.get(InventoryCheckDASCHParams.ITEM_CODE));
                expparam.set(InventoryCheckListParams.ITEM_NAME, outparam.get(InventoryCheckDASCHParams.ITEM_NAME));
                expparam.set(InventoryCheckListParams.LOT_NO, outparam.get(InventoryCheckDASCHParams.LOT_NO));
                expparam.set(InventoryCheckListParams.ENTERING_QTY, outparam.get(InventoryCheckDASCHParams.ENTERING_QTY));
                expparam.set(InventoryCheckListParams.STOCK_CASE_QTY, outparam.get(InventoryCheckDASCHParams.STOCK_CASE_QTY));
                expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY, outparam.get(InventoryCheckDASCHParams.STOCK_PIECE_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            if (rdo_JobNo.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
                part11List.add(txt_ListWorkNo.getStringValue(), "");
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(txt_LocationFrom.getStringValue(), "");
                part11List.add(txt_LocationTo.getStringValue(), "");
                part11List.add(txt_ItemCode.getStringValue(), "");
            }

            if (chk_InventoryStockQtyReport.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
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
        rdo_JobNo.setChecked(true);
        txt_ListWorkNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_LocationFrom.setValue(null);
        txt_LocationTo.setValue(null);
        txt_ItemCode.setValue(null);
        chk_InventoryOnlyDisp.setChecked(false);
        chk_InventoryQtyInput.setChecked(false);
        chk_InventoryStockQtyReport.setChecked(false);
        txt_ListWorkNo.setReadOnly(false);
        btn_P_Search_JobNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        chk_InventoryOnlyDisp.setEnabled(false);

        // set focus.
        setFocus(txt_ListWorkNo);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_InventoryResultInput.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_InventoryResultInput.setCurrentRow(i);
            lst_InventoryResultInput.validate(checkline.getIndex(KEY_LST_CYCLE_COUNT_CASE_QTY), false);
            lst_InventoryResultInput.validate(checkline.getIndex(KEY_LST_CYCLE_COUNT_PIECE_QTY), false);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_InventoryResultInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                InventoryInputSCHParams lineparam = new InventoryInputSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(InventoryInputSCHParams.JOBNO, line.getValue(KEY_HIDDEN_JOBNO));
                lineparam.set(InventoryInputSCHParams.STATUSFLAG, line.getValue(KEY_HIDDEN_STATUSFLAG));
                lineparam.set(InventoryInputSCHParams.INVENTCASEQTY, line.getValue(KEY_HIDDEN_INVENTCASEQTY));
                lineparam.set(InventoryInputSCHParams.INVENTPIECEQTY, line.getValue(KEY_HIDDEN_INVENTPIECEQTY));
                lineparam.set(InventoryInputSCHParams.CYCLE_COUNT, line.getValue(KEY_LST_CYCLE_COUNT));
                lineparam.set(InventoryInputSCHParams.STATUS_FLAG, line.getValue(KEY_LST_STATUS));
                lineparam.set(InventoryInputSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
                lineparam.set(InventoryInputSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION));
                lineparam.set(InventoryInputSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(InventoryInputSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(InventoryInputSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(InventoryInputSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(InventoryInputSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(InventoryInputSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(InventoryInputSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_CASE_QTY));
                lineparam.set(InventoryInputSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_PIECE_QTY));
                lineparam.set(InventoryInputSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP, viewState.getObject(ViewStateKeys.INVENTORY_ONLY_DISP));
                lineparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT, viewState.getObject(ViewStateKeys.INVENTORY_QTY_INPUT));
                lineparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT, viewState.getObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT));
                lineparam.set(InventoryInputSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                lineparam.set(InventoryInputSCHParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
                lineparam.set(InventoryInputSCHParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
                lineparam.set(InventoryInputSCHParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
                lineparam.set(InventoryInputSCHParams.SEARCH_CRITERIA, viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
                lineparam.set(InventoryInputSCHParams.NEWDATE_FLAG, line.getValue(KEY_HIDDEN_NEWDATE_FLAG));
                lineparam.set(InventoryInputSCHParams.PROCESS_FLAG, "10");
                lineparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
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
                _lcm_lst_InventoryResultInput.resetEditRow();
                _lcm_lst_InventoryResultInput.resetHighlight();
                _lcm_lst_InventoryResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_InventoryResultInput.get(i);
                lst_InventoryResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_PIECE_QTY), "");

                if (!StringUtil.isBlank(line.getValue(KEY_HIDDEN_NEWDATE_FLAG)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_InventoryResultInput.resetEditRow();
            _lcm_lst_InventoryResultInput.resetHighlight();

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.set(InventoryInputSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP, viewState.getObject(ViewStateKeys.INVENTORY_ONLY_DISP));
            inparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT, viewState.getObject(ViewStateKeys.INVENTORY_QTY_INPUT));
            inparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT, viewState.getObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT));
            inparam.set(InventoryInputSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(InventoryInputSCHParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
            inparam.set(InventoryInputSCHParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
            inparam.set(InventoryInputSCHParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
            inparam.set(InventoryInputSCHParams.CONDITION_SELECT, viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_InventoryResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryResultInput.getNewLine();
                line.setValue(KEY_HIDDEN_JOBNO, outparam.get(InventoryInputSCHParams.JOBNO));
                line.setValue(KEY_HIDDEN_STATUSFLAG, outparam.get(InventoryInputSCHParams.STATUSFLAG));
                line.setValue(KEY_HIDDEN_INVENTCASEQTY, outparam.get(InventoryInputSCHParams.INVENTCASEQTY));
                line.setValue(KEY_HIDDEN_INVENTPIECEQTY, outparam.get(InventoryInputSCHParams.INVENTPIECEQTY));
                line.setValue(KEY_LST_STATUS, outparam.get(InventoryInputSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_AREA, outparam.get(InventoryInputSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION, outparam.get(InventoryInputSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryInputSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(InventoryInputSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(InventoryInputSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryInputSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                lst_InventoryResultInput_SetLineToolTip(line);
                _lcm_lst_InventoryResultInput.add(line);
            }
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResultInput.get(i);
            lst_InventoryResultInput.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CYCLE_COUNT, Boolean.TRUE);
            lst_InventoryResultInput_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResultInput.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResultInput.get(i);
            lst_InventoryResultInput.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CYCLE_COUNT, Boolean.FALSE);
            lst_InventoryResultInput_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResultInput.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_AddNewData_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        InventoryDataAddParams inparam = new InventoryDataAddParams();
        inparam.set(InventoryDataAddParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
        inparam.set(InventoryDataAddParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
        inparam.set(InventoryDataAddParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
        inparam.set(InventoryDataAddParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
        inparam.set(InventoryDataAddParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
        inparam.set(InventoryDataAddParams.CONDITION, viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
        inparam.set(InventoryDataAddParams.MASTER, viewState.getObject(ViewStateKeys.MASTER));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_AddNewData_Click");
        redirect("/inventorychk/dataadd/InventoryDataAdd.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_AddNewData_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        InventoryDataAddParams outparam = new InventoryDataAddParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.AREA_NO));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.LOCATION_NO));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.ITEM_CODE));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.ITEM_NAME));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.LOT_NO));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.ENTERING_QTY));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.RESULT_CASE_QTY));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.RESULT_PIECE_QTY));
        txt_ItemCode.setValue(outparam.get(InventoryDataAddParams.NEWDATE_FLAG));
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_InventoryResultInput.getActiveRow();
        lst_InventoryResultInput.setCurrentRow(row);

        // reset editing row.
        lst_InventoryResultInput.removeRow(row);
        _lcm_lst_InventoryResultInput.resetEditRow();
        _lcm_lst_InventoryResultInput.resetHighlight();
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_InventoryResultInput.getActiveRow();
        lst_InventoryResultInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_InventoryResultInput.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(InventoryInputSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
            inparam.set(InventoryInputSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION));
            inparam.set(InventoryInputSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
            inparam.set(InventoryInputSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
            inparam.set(InventoryInputSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
            inparam.set(InventoryInputSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
            inparam.set(InventoryInputSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
            inparam.set(InventoryInputSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
            inparam.set(InventoryInputSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_CASE_QTY));
            inparam.set(InventoryInputSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_PIECE_QTY));
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_InventoryResultInput.resetEditRow();
                _lcm_lst_InventoryResultInput.resetHighlight();
                _lcm_lst_InventoryResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(line.getViewString(KEY_LST_AREA), "");
            part11List.add(line.getViewString(KEY_LST_LOCATION), "");
            part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
            part11List.add(line.getViewString(KEY_LST_LOT), "");
            part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
            part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
            part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
            part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_CASE_QTY), "");
            part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_PIECE_QTY), "");

            if (!StringUtil.isBlank(line.getValue(KEY_HIDDEN_NEWDATE_FLAG)))
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_InventoryResultInput.removeRow(row);
            _lcm_lst_InventoryResultInput.resetEditRow();
            _lcm_lst_InventoryResultInput.resetHighlight();
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
