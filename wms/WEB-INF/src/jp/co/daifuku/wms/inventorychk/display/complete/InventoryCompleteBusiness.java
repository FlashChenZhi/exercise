// $Id: InventoryCompleteBusiness.java 7538 2010-03-13 11:10:44Z ota $
package jp.co.daifuku.wms.inventorychk.display.complete;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.util.Green;
import jp.co.daifuku.bluedog.util.Orange;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.ToolTipHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCompleteDASCH;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCompleteDASCHParams;
import jp.co.daifuku.wms.inventorychk.exporter.InventoryDifferenceListParams;
import jp.co.daifuku.wms.inventorychk.listbox.jobno.LstInventoryJobNoParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryCompleteSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryCompleteSCHParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;

/**
 * 棚卸結果確定の画面処理を行います。
 * 
 * @version $Revision: 7538 $, $Date: 2010-03-13 20:10:44 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class InventoryCompleteBusiness
        extends InventoryComplete
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_InventoryResult(HIDDEN_JOB_NO) */
    private static final ListCellKey KEY_HIDDEN_JOB_NO = new ListCellKey("HIDDEN_JOB_NO", new StringCellColumn(), false, false);

    /** lst_InventoryResult(LST_CONFIRM) */
    private static final ListCellKey KEY_LST_CONFIRM = new ListCellKey("LST_CONFIRM", new CheckBoxColumn(), true, true);

    /** lst_InventoryResult(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA = new ListCellKey("LST_AREA", new AreaCellColumn(), true, false);

    /** lst_InventoryResult(LST_LOCATION) */
    private static final ListCellKey KEY_LST_LOCATION = new ListCellKey("LST_LOCATION", new LocationCellColumn(), true, false);

    /** lst_InventoryResult(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_CYCLE_COUNT_CASE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_CASE_QTY = new ListCellKey("LST_CYCLE_COUNT_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_CYCLE_COUNT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_PIECE_QTY = new ListCellKey("LST_CYCLE_COUNT_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_CURRENT_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_CURRENT_STOCK_CASE_QTY = new ListCellKey("LST_CURRENT_STOCK_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult(LST_CURRENT_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_CURRENT_STOCK_PIECE_QTY = new ListCellKey("LST_CURRENT_STOCK_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_InventoryResult keys */
    private static final ListCellKey[] LST_INVENTORYRESULT_KEYS = {
        KEY_HIDDEN_JOB_NO,
        KEY_LST_CONFIRM,
        KEY_LST_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_CYCLE_COUNT_CASE_QTY,
        KEY_LST_CURRENT_STOCK_CASE_QTY,
        KEY_LST_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_CYCLE_COUNT_PIECE_QTY,
        KEY_LST_CURRENT_STOCK_PIECE_QTY,
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

    /** ListCellModel lst_InventoryResult */
    private ListCellModel _lcm_lst_InventoryResult;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryCompleteBusiness()
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
        if (eventSource.equals("btn_PSearchListWorkNo_Click"))
        {
            // process call.
            btn_PSearchListWorkNo_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_DiffListIssue_Click"))
        {
            // process call.
            btn_DiffListIssue_Click_Process(false);
        }
        if (eventSource.equals("btn_Decision_Click"))
        {
            // process call.
            btn_Decision_Click_Process(eventSource);
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
    public void btn_PSearchListWorkNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchListWorkNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正
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
    public void btn_DiffListIssue_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DiffListIssue_Click_Process(true);
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
    public void btn_Decision_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Decision_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NoDiffSelect_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NoDiffSelect_Click_Process();
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

        // initialize lst_InventoryResult.
        _lcm_lst_InventoryResult = new ListCellModel(lst_InventoryResult, LST_INVENTORYRESULT_KEYS, locale);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CONFIRM, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_AREA, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_LOCATION, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CYCLE_COUNT_CASE_QTY, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CYCLE_COUNT_PIECE_QTY, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CURRENT_STOCK_CASE_QTY, true);
        _lcm_lst_InventoryResult.setToolTipVisible(KEY_LST_CURRENT_STOCK_PIECE_QTY, true);

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
    private void lst_InventoryResult_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        // clear.
        rdo_JobNo.setChecked(true);
        txt_ListWorkNo.setReadOnly(false);
        btn_PSearchListWorkNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        btn_Decision.setEnabled(false);
        btn_NoDiffSelect.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);

        // DFKLOOK:ここから修正
        // 生成したプルダウンの初期値を取得。
        _pdm_pul_Area.setSelectedValue(0);
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));

        // DFKLOOK:ここまで修正

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
        btn_PSearchListWorkNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);

        txt_LocationFrom.setText("");
        txt_LocationTo.setText("");
        txt_ItemCode.setText("");
        
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
        btn_PSearchListWorkNo.setEnabled(false);
        pul_Area.setEnabled(true);
        txt_LocationFrom.setReadOnly(false);
        txt_LocationTo.setReadOnly(false);
        txt_ItemCode.setReadOnly(false);

        txt_ListWorkNo.setText("");
        
        // set focus.
        setFocus(pul_Area);

        // DFKLOOK 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchListWorkNo_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstInventoryJobNoParams inparam = new LstInventoryJobNoParams();
        inparam.set(LstInventoryJobNoParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
        inparam.set(LstInventoryJobNoParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstInventoryJobNoParams.STATUSFLAG, InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchListWorkNo_Click");
        redirect("/inventorychk/listbox/jobno/LstInventoryJobNo.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchListWorkNo_Click_DlgBack(DialogParameters dialogParams)
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
        // DFKLOOK ここから修正
        _lcm_lst_InventoryResult.clear();
        if (rdo_JobNo.getChecked())
        {
            txt_ListWorkNo.validate(this, true);
        }
        else
        {
            txt_LocationFrom.validate(this, false);
            txt_LocationTo.validate(this, false);
            txt_ItemCode.validate(this, false);
            pul_Area.validate(this, true);
        }
        // DFKLOOK ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryCompleteSCHParams inparam = new InventoryCompleteSCHParams();
            inparam.set(InventoryCompleteSCHParams.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
            inparam.set(InventoryCompleteSCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryCompleteSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
                inparam.set(InventoryCompleteSCHParams.LOCATION_FROM, loc);
                loc =
                        WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
                inparam.set(InventoryCompleteSCHParams.LOCATION_TO, loc);
            }
            // DFKLOOK end
            inparam.set(InventoryCompleteSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            int count = 1;

            // output display.
            _lcm_lst_InventoryResult.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryResult.getNewLine();
                line.setValue(KEY_LST_AREA, outparam.get(InventoryCompleteSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION, outparam.get(InventoryCompleteSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(InventoryCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(InventoryCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(InventoryCompleteSCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryCompleteSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_CURRENT_STOCK_CASE_QTY, outparam.get(InventoryCompleteSCHParams.CASE_QTY));
                line.setValue(KEY_LST_CURRENT_STOCK_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.PIECE_QTY));
                viewState.setObject(ViewStateKeys.INVENTORY, _grp_Inventory.getSelectedValue());
                viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION_FROM, txt_LocationFrom.getValue());
                viewState.setObject(ViewStateKeys.LOCATION_TO, txt_LocationTo.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(InventoryCompleteSCHParams.JOB_NO));

                //元在庫数
                long inventStockCase = outparam.getLong(InventoryCompleteSCHParams.STOCK_CASE_QTY);
                long inventStockPiece = outparam.getLong(InventoryCompleteSCHParams.STOCK_PIECE_QTY);
                //棚卸数
                long resultCase = outparam.getLong(InventoryCompleteSCHParams.RESULT_CASE_QTY);
                long resultPiece = outparam.getLong(InventoryCompleteSCHParams.RESULT_PIECE_QTY);
                //現在庫数
                long stockCase = outparam.getLong(InventoryCompleteSCHParams.CASE_QTY);
                long stockPiece = outparam.getLong(InventoryCompleteSCHParams.PIECE_QTY);

                ToolTipHelper toolTip = new ToolTipHelper();

                Orange orange = new Orange();
                Green green = new Green();

                _lcm_lst_InventoryResult.add(line);

                if (!(stockCase == resultCase && stockPiece == resultPiece))
                {
                    //「棚卸数と現在庫数が異なっています。」
                    toolTip.add(DisplayText.getText("LBL-W0351"), DisplayText.getText("MSG-W0040"));
                    lst_InventoryResult.setToolTip(count, toolTip.getText());
                    DisplayUtil.addHighlight(lst_InventoryResult, count, orange);
                }
                else if (!(inventStockCase == resultCase && inventStockPiece == resultPiece))
                {
                    //「元在庫数と現在庫数が異なっています。」
                    toolTip.add(DisplayText.getText("LBL-W0351"), DisplayText.getText("MSG-W0039"));
                    lst_InventoryResult.setToolTip(count, toolTip.getText());
                    DisplayUtil.addHighlight(lst_InventoryResult, count, green);
                }
                count++;
            }


            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }

            if (_lcm_lst_InventoryResult.getListCell().getMaxRows() > 1)
            {
                // clear.
                btn_Decision.setEnabled(true);
                btn_NoDiffSelect.setEnabled(true);
                btn_AllCheckClear.setEnabled(true);
                return;
            }
            else
            {
                // clear.
                btn_Decision.setEnabled(false);
                btn_NoDiffSelect.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正

        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
    private void btn_DiffListIssue_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            txt_ListWorkNo.validate(this, true);
        }
        else
        {
            txt_LocationFrom.validate(this, false);
            txt_LocationTo.validate(this, false);
            txt_ItemCode.validate(this, false);
            pul_Area.validate(this, true);
        }
        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else
        {
            setFocus(rdo_LocationRange);
        }

        // DFKLOOK ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCompleteDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new InventoryCompleteDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            InventoryCompleteDASCHParams inparam = new InventoryCompleteDASCHParams();
            inparam.set(InventoryCompleteDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryCompleteDASCHParams.INVENTORY, _grp_Inventory.getSelectedValue());
            inparam.set(InventoryCompleteDASCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryCompleteDASCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            String locFrom = "";
            String locTo = "";
            String style = "";
            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                style = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
                locFrom = WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(), style);
                inparam.set(InventoryCompleteSCHParams.LOCATION_FROM, locFrom);
                locTo = WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(), style);
                inparam.set(InventoryCompleteSCHParams.LOCATION_TO, locTo);
            }
            // DFKLOOK end
            inparam.set(InventoryCompleteDASCHParams.ITEM_CODE, txt_ItemCode.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_DiffListIssue_Click");
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
            exporter = factory.newPrinterExporter("InventoryDifferenceList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                InventoryDifferenceListParams expparam = new InventoryDifferenceListParams();
                expparam.set(InventoryDifferenceListParams.DFK_DS_NO,
                        outparam.get(InventoryCompleteDASCHParams.DFK_DS_NO));
                expparam.set(InventoryDifferenceListParams.DFK_USER_ID,
                        outparam.get(InventoryCompleteDASCHParams.DFK_USER_ID));
                expparam.set(InventoryDifferenceListParams.DFK_USER_NAME,
                        outparam.get(InventoryCompleteDASCHParams.DFK_USER_NAME));
                expparam.set(InventoryDifferenceListParams.AREA_NO, outparam.get(InventoryCompleteDASCHParams.AREA_NO));
                expparam.set(InventoryDifferenceListParams.AREA_NAME,
                        outparam.get(InventoryCompleteDASCHParams.AREA_NAME));
                expparam.set(InventoryDifferenceListParams.SYS_DAY, outparam.get(InventoryCompleteDASCHParams.SYS_DAY));
                expparam.set(InventoryDifferenceListParams.SYS_TIME,
                        outparam.get(InventoryCompleteDASCHParams.SYS_TIME));
                expparam.set(InventoryDifferenceListParams.LOCATION_NO,
                        outparam.get(InventoryCompleteDASCHParams.LOCATION_NO));
                expparam.set(InventoryDifferenceListParams.ITEM_CODE,
                        outparam.get(InventoryCompleteDASCHParams.ITEM_CODE));
                expparam.set(InventoryDifferenceListParams.ITEM_NAME,
                        outparam.get(InventoryCompleteDASCHParams.ITEM_NAME));
                expparam.set(InventoryDifferenceListParams.LOT_NO, outparam.get(InventoryCompleteDASCHParams.LOT_NO));
                expparam.set(InventoryDifferenceListParams.ENTERING_QTY,
                        outparam.get(InventoryCompleteDASCHParams.ENTERING_QTY));
                expparam.set(InventoryDifferenceListParams.BEFORE_STOCK_CASE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.BEFORE_STOCK_CASE_QTY));
                expparam.set(InventoryDifferenceListParams.BEFORE_STOCK_PIECE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.BEFORE_STOCK_PIECE_QTY));
                expparam.set(InventoryDifferenceListParams.INVENTORY_STOCK_CASE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.INVENTORY_STOCK_CASE_QTY));
                expparam.set(InventoryDifferenceListParams.INVENTORY_STOCK_PIECE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.INVENTORY_STOCK_PIECE_QTY));
                expparam.set(InventoryDifferenceListParams.AFTER_STOCK_CASE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.AFTER_STOCK_CASE_QTY));
                expparam.set(InventoryDifferenceListParams.AFTER_STOCK_PIECE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.AFTER_STOCK_PIECE_QTY));
                expparam.set(InventoryDifferenceListParams.DIFFERENCE_CASE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.DIFFERENCE_CASE_QTY));
                expparam.set(InventoryDifferenceListParams.DIFFERENCE_PIECE_QTY,
                        outparam.get(InventoryCompleteDASCHParams.DIFFERENCE_PIECE_QTY));
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
            }
            //DFKLOOK:ここから修正
            part11List.add(txt_ListWorkNo.getStringValue(), "");
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(WmsFormatter.toDispLocation(locFrom, style), "");
            part11List.add(WmsFormatter.toDispLocation(locTo, style), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            //DFKLOOK:ここまで修正

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();

        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
        txt_ListWorkNo.setReadOnly(false);
        btn_PSearchListWorkNo.setEnabled(true);
        _pdm_pul_Area.setSelectedValue(null);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setValue(null);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setValue(null);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setValue(null);
        txt_ItemCode.setReadOnly(true);

        setFocus(rdo_JobNo);

        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Decision_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_InventoryResult.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_InventoryResult.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK ここから修正
            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
            // DFKLOOK ここまで修正

            message.setMsgResourceKey("6003001");
            return;
        }

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
            // 確定しますか？
            this.setConfirm("MSG-W0047", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Decision_Click");
            return;
        }
        // DFKLOOK:ここまで修正


        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_InventoryResult.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_InventoryResult.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                InventoryCompleteSCHParams lineparam = new InventoryCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(InventoryCompleteSCHParams.CONFIRM, line.getValue(KEY_LST_CONFIRM));
                lineparam.set(InventoryCompleteSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
                lineparam.set(InventoryCompleteSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION));
                lineparam.set(InventoryCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(InventoryCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(InventoryCompleteSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(InventoryCompleteSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(InventoryCompleteSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(InventoryCompleteSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(InventoryCompleteSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_CASE_QTY));
                lineparam.set(InventoryCompleteSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_PIECE_QTY));
                lineparam.set(InventoryCompleteSCHParams.CASE_QTY, line.getValue(KEY_LST_CURRENT_STOCK_CASE_QTY));
                lineparam.set(InventoryCompleteSCHParams.PIECE_QTY, line.getValue(KEY_LST_CURRENT_STOCK_PIECE_QTY));
                lineparam.set(InventoryCompleteSCHParams.JOB_NO, line.getValue(KEY_HIDDEN_JOB_NO));
                lineparam.set(InventoryCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // DFKLOOK ここから修正
                lineparam.set(InventoryCompleteSCHParams.ROW_NO, i);
                // DFKLOOK ここまで修正
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
                _lcm_lst_InventoryResult.resetEditRow();
                _lcm_lst_InventoryResult.resetHighlight();
                _lcm_lst_InventoryResult.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_InventoryResult.size(); i++)
            {
                ListCellLine line = _lcm_lst_InventoryResult.get(i);
                lst_InventoryResult.setCurrentRow(i);

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
                part11List.add(line.getViewString(KEY_LST_CURRENT_STOCK_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CURRENT_STOCK_PIECE_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_InventoryResult.resetEditRow();
            _lcm_lst_InventoryResult.resetHighlight();

            // set input parameters.
            InventoryCompleteSCHParams inparam = new InventoryCompleteSCHParams();
            inparam.set(InventoryCompleteSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(InventoryCompleteSCHParams.SEARCH_CRITERIA, viewState.getObject(ViewStateKeys.INVENTORY));
            inparam.set(InventoryCompleteSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(InventoryCompleteSCHParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
            inparam.set(InventoryCompleteSCHParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
            inparam.set(InventoryCompleteSCHParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
            inparam.set(InventoryCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            int count = 1;

            // output display.
            _lcm_lst_InventoryResult.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryResult.getNewLine();
                line.setValue(KEY_LST_AREA, outparam.get(InventoryCompleteSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION, outparam.get(InventoryCompleteSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(InventoryCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(InventoryCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(InventoryCompleteSCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryCompleteSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_CURRENT_STOCK_CASE_QTY, outparam.get(InventoryCompleteSCHParams.CASE_QTY));
                line.setValue(KEY_LST_CURRENT_STOCK_PIECE_QTY, outparam.get(InventoryCompleteSCHParams.PIECE_QTY));
                viewState.setObject(ViewStateKeys.INVENTORY, _grp_Inventory.getSelectedValue());
                viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION_FROM, txt_LocationFrom.getValue());
                viewState.setObject(ViewStateKeys.LOCATION_TO, txt_LocationTo.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(InventoryCompleteSCHParams.JOB_NO));

                //元在庫数
                long inventStockCase = outparam.getLong(InventoryCompleteSCHParams.STOCK_CASE_QTY);
                long inventStockPiece = outparam.getLong(InventoryCompleteSCHParams.STOCK_PIECE_QTY);
                //棚卸数
                long resultCase = outparam.getLong(InventoryCompleteSCHParams.RESULT_CASE_QTY);
                long resultPiece = outparam.getLong(InventoryCompleteSCHParams.RESULT_PIECE_QTY);
                //現在庫数
                long stockCase = outparam.getLong(InventoryCompleteSCHParams.CASE_QTY);
                long stockPiece = outparam.getLong(InventoryCompleteSCHParams.PIECE_QTY);

                ToolTipHelper toolTip = new ToolTipHelper();

                Orange orange = new Orange();
                Green green = new Green();

                _lcm_lst_InventoryResult.add(line);

                if (!(stockCase == resultCase && stockPiece == resultPiece))
                {
                    //「棚卸数と現在庫数が異なっています。」
                    toolTip.add(DisplayText.getText("LBL-W0351"), DisplayText.getText("MSG-W0040"));
                    lst_InventoryResult.setToolTip(count, toolTip.getText());
                    DisplayUtil.addHighlight(lst_InventoryResult, count, orange);
                }
                else if (!(inventStockCase == resultCase && inventStockPiece == resultPiece))
                {
                    //「元在庫数と現在庫数が異なっています。」
                    toolTip.add(DisplayText.getText("LBL-W0351"), DisplayText.getText("MSG-W0039"));
                    lst_InventoryResult.setToolTip(count, toolTip.getText());
                    DisplayUtil.addHighlight(lst_InventoryResult, count, green);
                }
                count++;
            }

            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }

            if (_lcm_lst_InventoryResult.getListCell().getMaxRows() > 1)
            {
                // clear.
                btn_Decision.setEnabled(true);
                btn_NoDiffSelect.setEnabled(true);
                btn_AllCheckClear.setEnabled(true);
                return;
            }
            else
            {
                // clear.
                btn_Decision.setEnabled(false);
                btn_NoDiffSelect.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正

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
    private void btn_NoDiffSelect_Click_Process()
            throws Exception
    {
        // DFKLOOK ここから修正
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResult.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResult.get(i);
            lst_InventoryResult.setCurrentRow(i);

            String inventCase = clearLine.getStringValue(KEY_LST_CYCLE_COUNT_CASE_QTY);
            String inventPiece = clearLine.getStringValue(KEY_LST_CYCLE_COUNT_PIECE_QTY);
            String resultCase = clearLine.getStringValue(KEY_LST_CURRENT_STOCK_CASE_QTY);
            String resultPiece = clearLine.getStringValue(KEY_LST_CURRENT_STOCK_PIECE_QTY);

            if (inventCase.equals(resultCase) && inventPiece.equals(resultPiece))
            {
                clearLine.setValue(KEY_LST_CONFIRM, Boolean.TRUE);
            }

            lst_InventoryResult_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResult.set(i, clearLine);
        }
        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else
        {
            setFocus(rdo_LocationRange);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResult.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResult.get(i);
            lst_InventoryResult.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CONFIRM, Boolean.FALSE);
            lst_InventoryResult_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResult.set(i, clearLine);
        }

        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else
        {
            setFocus(rdo_LocationRange);
        }
        // DFKLOOK ここまで修正

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
