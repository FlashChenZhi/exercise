// $Id: StockRegistBusiness.java 7443 2010-03-08 04:18:39Z okayama $
package jp.co.daifuku.wms.stock.display.regist;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
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
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.listbox.item.LstItemParams;
import jp.co.daifuku.wms.stock.display.regist.StockRegist;
import jp.co.daifuku.wms.stock.display.regist.ViewStateKeys;
import jp.co.daifuku.wms.stock.schedule.StockRegistSCH;
import jp.co.daifuku.wms.stock.schedule.StockRegistSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7443 $, $Date:: 2010-03-08 13:18:39 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StockRegistBusiness
        extends StockRegist
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StockInfoInput(HIDDEN_LOCATION) */
    private static final ListCellKey KEY_HIDDEN_LOCATION = new ListCellKey("HIDDEN_LOCATION", new StringCellColumn(), false, false);

    /** lst_StockInfoInput(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_StockInfoInput(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_StockInfoInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfoInput(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfoInput(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfoInput(LST_STORAGE_DAY) */
    private static final ListCellKey KEY_LST_STORAGE_DAY = new ListCellKey("LST_STORAGE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StockInfoInput(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_StockInfoInput(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_StockInfoInput(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_StockInfoInput keys */
    private static final ListCellKey[] LST_STOCKINFOINPUT_KEYS = {
        KEY_HIDDEN_LOCATION,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_AREA_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_STORAGE_DAY,
        KEY_LST_JAN,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_STORAGE_TIME,
        KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_StockInfoInput */
    private ListCellModel _lcm_lst_StockInfoInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StockRegistBusiness()
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
        if (eventSource.equals("btn_ItemCodeSearch_Click"))
        {
            // process call.
            btn_ItemCodeSearch_Click_DlgBack(dialogParams);
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
    public void btn_ItemCodeSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemCodeSearch_Click_Process();
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
    public void lst_StockInfoInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StockInfoInput.getActiveCol();

        // choose process.
        if (_lcm_lst_StockInfoInput.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_StockInfoInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize lst_StockInfoInput.
        _lcm_lst_StockInfoInput = new ListCellModel(lst_StockInfoInput, LST_STOCKINFOINPUT_KEYS, locale);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_STORAGE_DAY, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_STORAGE_TIME, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_StockInfoInput.setToolTipVisible(KEY_LST_ITF, true);
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
    private void lst_StockInfoInput_SetLineToolTip(ListCellLine line)
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
        setFocus(pul_Area);
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
        StockRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockRegistSCHParams inparam = new StockRegistSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                viewState.setObject(ViewStateKeys.MASTER, outparam.get(StockRegistSCHParams.MASTER_FLAG));
            }
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
    private void btn_ItemCodeSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemCodeSearch_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemCodeSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

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
        pul_Area.validate(this, true);
        txt_Location.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_StockCaseQty.validate(this, false);
        txt_StockPieceQty.validate(this, false);
        txt_StorageDate.validate(this, false);
        txt_StorageTime.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockRegistSCHParams inparam = new StockRegistSCHParams();
            inparam.set(StockRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(StockRegistSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(StockRegistSCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(StockRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockRegistSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(StockRegistSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(StockRegistSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(StockRegistSCHParams.STOCK_CASE_QTY, txt_StockCaseQty.getValue());
            inparam.set(StockRegistSCHParams.STOCK_PIECE_QTY, txt_StockPieceQty.getValue());
            inparam.set(StockRegistSCHParams.JAN, txt_JanCode.getValue());
            inparam.set(StockRegistSCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(StockRegistSCHParams.STORAGE_DAY, txt_StorageDate.getValue());
            inparam.set(StockRegistSCHParams.STORAGE_TIME, txt_StorageTime.getValue());
            inparam.set(StockRegistSCHParams.MASTER_FLAG, viewState.getObject(ViewStateKeys.MASTER));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StockInfoInput.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_StockInfoInput.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_StockInfoInput.get(i);
                StockRegistSCHParams lineparam = new StockRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(StockRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(StockRegistSCHParams.MASTER_FLAG, viewState.getObject(ViewStateKeys.MASTER));
                lineparam.set(StockRegistSCHParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(StockRegistSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(StockRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StockRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StockRegistSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(StockRegistSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(StockRegistSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(StockRegistSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(StockRegistSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(StockRegistSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(StockRegistSCHParams.STORAGE_DAY, line.getValue(KEY_LST_STORAGE_DAY));
                lineparam.set(StockRegistSCHParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
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
                int editRow = _lcm_lst_StockInfoInput.getEditRow();
                Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
                ListCellLine line = newline ? _lcm_lst_StockInfoInput.getNewLine()
                                            : _lcm_lst_StockInfoInput.get(editRow);
                txt_ItemName.setValue(outparam.get(StockRegistSCHParams.ITEM_CODE));
                txt_EnteringQty.setValue(outparam.get(StockRegistSCHParams.ENTERING_QTY));
                txt_JanCode.setValue(outparam.get(StockRegistSCHParams.JAN));
                txt_CaseITF.setValue(outparam.get(StockRegistSCHParams.ITF));
                line.setValue(KEY_HIDDEN_LOCATION, txt_Location.getValue());
                line.setValue(KEY_LST_AREA_NO, _pdm_pul_Area.getSelectedValue());
                line.setValue(KEY_LST_LOCATION_NO, txt_Location.getValue());
                line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
                line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
                line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getValue());
                line.setValue(KEY_LST_STOCK_CASE_QTY, txt_StockCaseQty.getValue());
                line.setValue(KEY_LST_STOCK_PIECE_QTY, txt_StockPieceQty.getValue());
                line.setValue(KEY_LST_STORAGE_DAY, txt_StorageDate.getValue());
                line.setValue(KEY_LST_STORAGE_TIME, txt_StorageTime.getValue());
                line.setValue(KEY_LST_JAN, txt_JanCode.getValue());
                line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());
                viewState.setObject(ViewStateKeys.STORAGE_DAY, "");
                viewState.setObject(ViewStateKeys.STORAGE_TIME, "");

                // add new row or update editing row.
                lst_StockInfoInput_SetLineToolTip(line);
                if (newline)
                {
                    _lcm_lst_StockInfoInput.add(line, true);
                }
                else
                {
                    _lcm_lst_StockInfoInput.set(editRow, line);
                }
                break;
            }

            // reset editing row.
            _lcm_lst_StockInfoInput.resetEditRow();
            _lcm_lst_StockInfoInput.resetHighlight();

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
        _pdm_pul_Area.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);
        txt_StockCaseQty.setValue(null);
        txt_StockPieceQty.setValue(null);
        txt_StorageDate.setValue(null);
        txt_StorageTime.setValue(null);
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
        StockRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StockInfoInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StockInfoInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StockRegistSCHParams lineparam = new StockRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(StockRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(StockRegistSCHParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(StockRegistSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(StockRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StockRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StockRegistSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(StockRegistSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(StockRegistSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(StockRegistSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(StockRegistSCHParams.STORAGE_DAY, line.getValue(KEY_LST_STORAGE_DAY));
                lineparam.set(StockRegistSCHParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
                lineparam.set(StockRegistSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(StockRegistSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(StockRegistSCHParams.STORAGE_DATE, "");
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
                _lcm_lst_StockInfoInput.resetEditRow();
                _lcm_lst_StockInfoInput.resetHighlight();
                _lcm_lst_StockInfoInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StockInfoInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_StockInfoInput.get(i);
                lst_StockInfoInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_DAY), line.getViewString(KEY_LST_STORAGE_TIME), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StockInfoInput.resetEditRow();
            _lcm_lst_StockInfoInput.resetHighlight();

            // clear.
            _lcm_lst_StockInfoInput.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
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
        _lcm_lst_StockInfoInput.clear();
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
        int row = lst_StockInfoInput.getActiveRow();
        lst_StockInfoInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StockInfoInput.get(row);

        // output display.
        _pdm_pul_Area.setSelectedValue(line.getValue(KEY_LST_AREA_NO));
        txt_Location.setValue(line.getValue(KEY_LST_LOCATION_NO));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_StockCaseQty.setValue(line.getValue(KEY_LST_STOCK_CASE_QTY));
        txt_StockPieceQty.setValue(line.getValue(KEY_LST_STOCK_PIECE_QTY));
        txt_StorageDate.setValue(line.getValue(KEY_LST_STORAGE_DAY));
        txt_StorageTime.setValue(line.getValue(KEY_LST_STORAGE_TIME));
        txt_JanCode.setValue(line.getValue(KEY_LST_JAN));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));

        // highlight active row.
        _lcm_lst_StockInfoInput.resetHighlight();
        _lcm_lst_StockInfoInput.addHighlight(row);
        _lcm_lst_StockInfoInput.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StockInfoInput.getActiveRow();
        lst_StockInfoInput.setCurrentRow(row);

        // reset editing row.
        lst_StockInfoInput.removeRow(row);
        _lcm_lst_StockInfoInput.resetEditRow();
        _lcm_lst_StockInfoInput.resetHighlight();
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
