// $Id: StoragePlanRegist2Business.java 7341 2010-03-04 02:20:10Z okayama $
package jp.co.daifuku.wms.storage.display.planregist;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.storage.display.planregist.StoragePlanRegist2;
import jp.co.daifuku.wms.storage.display.planregist.ViewStateKeys;
import jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCH;
import jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7341 $, $Date:: 2010-03-04 11:20:10 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StoragePlanRegist2Business
        extends StoragePlanRegist2
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StoragePlanInput(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_LINE) */
    private static final ListCellKey KEY_LST_LINE = new ListCellKey("LST_LINE", new NumberCellColumn(0), true, false);

    /** lst_StoragePlanInput(LST_SERIAL) */
    private static final ListCellKey KEY_LST_SERIAL = new ListCellKey("LST_SERIAL", new NumberCellColumn(0), true, false);

    /** lst_StoragePlanInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_StoragePlanInput(LST_UPC_CODE) */
    private static final ListCellKey KEY_LST_UPC_CODE = new ListCellKey("LST_UPC_CODE", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_CASE_ITF) */
    private static final ListCellKey KEY_LST_CASE_ITF = new ListCellKey("LST_CASE_ITF", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StoragePlanInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StoragePlanInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_STORAGE_AREA) */
    private static final ListCellKey KEY_LST_STORAGE_AREA = new ListCellKey("LST_STORAGE_AREA", new AreaCellColumn(), true, false);

    /** lst_StoragePlanInput(LST_STORAGE_LOCATION) */
    private static final ListCellKey KEY_LST_STORAGE_LOCATION = new ListCellKey("LST_STORAGE_LOCATION", new LocationCellColumn(), true, false);

    /** lst_StoragePlanInput keys */
    private static final ListCellKey[] LST_STORAGEPLANINPUT_KEYS = {
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_LINE,
        KEY_LST_ITEM_CODE,
        KEY_LST_CASE_PACK,
        KEY_LST_UPC_CODE,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_LOT,
        KEY_LST_STORAGE_AREA,
        KEY_LST_SERIAL,
        KEY_LST_ITEM_NAME,
        KEY_LST_CASE_ITF,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_STORAGE_LOCATION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_StorageArea */
    private WmsAreaPullDownModel _pdm_pul_StorageArea;

    /** ListCellModel lst_StoragePlanInput */
    private ListCellModel _lcm_lst_StoragePlanInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StoragePlanRegist2Business()
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
        if (eventSource.equals("btn_PSearchItem_Click"))
        {
            // process call.
            btn_PSearchItem_Click_DlgBack(dialogParams);
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
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchItem_Click_Process();
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
    public void lst_StoragePlanInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StoragePlanInput.getActiveCol();

        // choose process.
        if (_lcm_lst_StoragePlanInput.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_StoragePlanInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_StorageArea.
        _pdm_pul_StorageArea = new WmsAreaPullDownModel(pul_StorageArea, locale, ui);

        // initialize lst_StoragePlanInput.
        _lcm_lst_StoragePlanInput = new ListCellModel(lst_StoragePlanInput, LST_STORAGEPLANINPUT_KEYS, locale);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_LINE, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_SERIAL, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_UPC_CODE, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_CASE_ITF, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_STORAGE_AREA, true);
        _lcm_lst_StoragePlanInput.setToolTipVisible(KEY_LST_STORAGE_LOCATION, true);
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

            // load pul_StorageArea.
            _pdm_pul_StorageArea.init(conn, AreaType.NOT_MOVING, null, "", false);
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
    private void lst_StoragePlanInput_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_LineNo);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        lbl_InPlanDate.setValue(viewState.getObject(ViewStateKeys.STORAGE_PLAN_DATE));
        lbl_InSlip.setValue(viewState.getObject(ViewStateKeys.SLIP_NUMBER));
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        viewState.setObject(ViewStateKeys.LIST_LINE_NO, viewState.getObject(ViewStateKeys.LIST_LINE_NO));
        viewState.setObject(ViewStateKeys.MODIFY_FLAG, viewState.getObject(ViewStateKeys.MODIFY_FLAG));
        viewState.setObject(ViewStateKeys.MODIFY_ROW, viewState.getObject(ViewStateKeys.MODIFY_ROW));

        // clear.
        txt_LineNo.setValue(null);
        txt_BranchNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_StorageArea.setSelectedValue(null);
        txt_StorageLocation.setValue(null);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_StoragePlanInput.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // forward.
            forward("/storage/planregist/StoragePlanRegist.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchItem_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemCode);
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
        txt_LineNo.validate(this, false);
        txt_BranchNo.validate(this, false);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);
        txt_LotNo.validate(this, false);
        pul_StorageArea.validate(this, true);
        txt_StorageLocation.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StoragePlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StoragePlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StoragePlanRegistSCHParams inparam = new StoragePlanRegistSCHParams();
            inparam.set(StoragePlanRegistSCHParams.LINE_NO, txt_LineNo.getValue());
            inparam.set(StoragePlanRegistSCHParams.BRANCH_NO, txt_BranchNo.getValue());
            inparam.set(StoragePlanRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StoragePlanRegistSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(StoragePlanRegistSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(StoragePlanRegistSCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(StoragePlanRegistSCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(StoragePlanRegistSCHParams.PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            inparam.set(StoragePlanRegistSCHParams.PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            inparam.set(StoragePlanRegistSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(StoragePlanRegistSCHParams.STORAGE_AREA, _pdm_pul_StorageArea.getSelectedValue());
            inparam.set(StoragePlanRegistSCHParams.STORAGE_LOCATION, txt_StorageLocation.getValue());
            inparam.set(StoragePlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(StoragePlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
            inparam.set(StoragePlanRegistSCHParams.STORAGE_PLAN_DATE, viewState.getObject(ViewStateKeys.STORAGE_PLAN_DATE));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StoragePlanInput.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_StoragePlanInput.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_StoragePlanInput.get(i);
                StoragePlanRegistSCHParams lineparam = new StoragePlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(StoragePlanRegistSCHParams.LINE, line.getValue(KEY_LST_LINE));
                lineparam.set(StoragePlanRegistSCHParams.SERIAL, line.getValue(KEY_LST_SERIAL));
                lineparam.set(StoragePlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StoragePlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StoragePlanRegistSCHParams.CASE_PACK, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(StoragePlanRegistSCHParams.UPC_CODE, line.getValue(KEY_LST_UPC_CODE));
                lineparam.set(StoragePlanRegistSCHParams.CASE_ITF, line.getValue(KEY_LST_CASE_ITF));
                lineparam.set(StoragePlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(StoragePlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(StoragePlanRegistSCHParams.LOT, line.getValue(KEY_LST_LOT));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_AREA, line.getValue(KEY_LST_STORAGE_AREA));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_LOCATION, line.getValue(KEY_LST_STORAGE_LOCATION));
                lineparam.set(StoragePlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(StoragePlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_PLAN_DATE, viewState.getObject(ViewStateKeys.STORAGE_PLAN_DATE));
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

            // output display.
            int editRow = _lcm_lst_StoragePlanInput.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_StoragePlanInput.getNewLine()
                                        : _lcm_lst_StoragePlanInput.get(editRow);
            line.setValue(KEY_LST_LINE, txt_LineNo.getValue());
            line.setValue(KEY_LST_SERIAL, txt_BranchNo.getValue());
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_CASE_PACK, txt_EnteringQty.getValue());
            line.setValue(KEY_LST_UPC_CODE, txt_JanCode.getValue());
            line.setValue(KEY_LST_CASE_ITF, txt_CaseITF.getValue());
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            line.setValue(KEY_LST_LOT, txt_LotNo.getValue());
            line.setValue(KEY_LST_STORAGE_AREA, _pdm_pul_StorageArea.getSelectedValue());
            line.setValue(KEY_LST_STORAGE_LOCATION, txt_StorageLocation.getValue());

            // add new row or update editing row.
            lst_StoragePlanInput_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_StoragePlanInput.add(line, true);
            }
            else
            {
                _lcm_lst_StoragePlanInput.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_StoragePlanInput.resetEditRow();
            _lcm_lst_StoragePlanInput.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
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
        txt_LineNo.setValue(null);
        txt_BranchNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_StorageArea.setSelectedValue(null);
        txt_StorageLocation.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StoragePlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StoragePlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StoragePlanInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StoragePlanInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StoragePlanRegistSCHParams lineparam = new StoragePlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(StoragePlanRegistSCHParams.LINE, line.getValue(KEY_LST_LINE));
                lineparam.set(StoragePlanRegistSCHParams.SERIAL, line.getValue(KEY_LST_SERIAL));
                lineparam.set(StoragePlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StoragePlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StoragePlanRegistSCHParams.CASE_PACK, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(StoragePlanRegistSCHParams.UPC_CODE, line.getValue(KEY_LST_UPC_CODE));
                lineparam.set(StoragePlanRegistSCHParams.CASE_ITF, line.getValue(KEY_LST_CASE_ITF));
                lineparam.set(StoragePlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(StoragePlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(StoragePlanRegistSCHParams.LOT, line.getValue(KEY_LST_LOT));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_AREA, line.getValue(KEY_LST_STORAGE_AREA));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_LOCATION, line.getValue(KEY_LST_STORAGE_LOCATION));
                lineparam.set(StoragePlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(StoragePlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
                lineparam.set(StoragePlanRegistSCHParams.STORAGE_PLAN_DATE, viewState.getObject(ViewStateKeys.STORAGE_PLAN_DATE));
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
                _lcm_lst_StoragePlanInput.resetEditRow();
                _lcm_lst_StoragePlanInput.resetHighlight();
                _lcm_lst_StoragePlanInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StoragePlanInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_StoragePlanInput.get(i);
                lst_StoragePlanInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(lbl_InPlanDate.getStringValue(), "");
                part11List.add(lbl_InSlip.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_LINE), "");
                part11List.add(line.getViewString(KEY_LST_SERIAL), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_UPC_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CASE_ITF), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_AREA), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_LOCATION), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StoragePlanInput.resetEditRow();
            _lcm_lst_StoragePlanInput.resetHighlight();

            // clear.
            _lcm_lst_StoragePlanInput.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            txt_LineNo.setValue(null);
            txt_BranchNo.setValue(null);
            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            txt_LotNo.setValue(null);
            _pdm_pul_StorageArea.setSelectedValue(null);
            txt_StorageLocation.setValue(null);
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
        _lcm_lst_StoragePlanInput.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StoragePlanInput.getActiveRow();
        lst_StoragePlanInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StoragePlanInput.get(row);

        // output display.
        txt_LineNo.setValue(line.getValue(KEY_LST_LINE));
        txt_BranchNo.setValue(line.getValue(KEY_LST_SERIAL));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_CASE_PACK));
        txt_JanCode.setValue(line.getValue(KEY_LST_UPC_CODE));
        txt_CaseITF.setValue(line.getValue(KEY_LST_CASE_ITF));
        txt_PlanCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_PlanPieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT));
        _pdm_pul_StorageArea.setSelectedValue(line.getValue(KEY_LST_STORAGE_AREA));
        txt_StorageLocation.setValue(line.getValue(KEY_LST_STORAGE_LOCATION));

        // highlight active row.
        _lcm_lst_StoragePlanInput.resetHighlight();
        _lcm_lst_StoragePlanInput.addHighlight(row);
        _lcm_lst_StoragePlanInput.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StoragePlanInput.getActiveRow();
        lst_StoragePlanInput.setCurrentRow(row);

        // reset editing row.
        lst_StoragePlanInput.removeRow(row);
        _lcm_lst_StoragePlanInput.resetEditRow();
        _lcm_lst_StoragePlanInput.resetHighlight();
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
