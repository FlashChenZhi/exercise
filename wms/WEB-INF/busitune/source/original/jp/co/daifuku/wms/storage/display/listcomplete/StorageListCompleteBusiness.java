// $Id: StorageListCompleteBusiness.java 7341 2010-03-04 02:20:10Z okayama $
package jp.co.daifuku.wms.storage.display.listcomplete;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
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
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.storage.display.listcomplete.StorageListComplete;
import jp.co.daifuku.wms.storage.schedule.StorageListCompleteSCH;
import jp.co.daifuku.wms.storage.schedule.StorageListCompleteSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7341 $, $Date:: 2010-03-04 11:20:10 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StorageListCompleteBusiness
        extends StorageListComplete
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageListResultInput(HIDDEN_PLAN_LOT_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOT_NO = new ListCellKey("HIDDEN_PLAN_LOT_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDEEN_PLAN_AREA_NO) */
    private static final ListCellKey KEY_HIDEEN_PLAN_AREA_NO = new ListCellKey("HIDEEN_PLAN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDDEN_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOCATION_NO = new ListCellKey("HIDDEN_PLAN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDDEN_STORAGE_CASE_QTY) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_CASE_QTY = new ListCellKey("HIDDEN_STORAGE_CASE_QTY", new NumberCellColumn(0), false, false);

    /** lst_StorageListResultInput(HIDDEN_STORAGE_PIECE_QTY) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_PIECE_QTY = new ListCellKey("HIDDEN_STORAGE_PIECE_QTY", new NumberCellColumn(0), false, false);

    /** lst_StorageListResultInput(HIDDEN_COLLECT_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_COLLECT_JOBNO = new ListCellKey("HIDDEN_COLLECT_JOBNO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_StorageListResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StorageListResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StorageListResultInput(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_STORAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_CASE_QTY = new ListCellKey("LST_STORAGE_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StorageListResultInput(LST_STORAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_PIECE_QTY = new ListCellKey("LST_STORAGE_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StorageListResultInput(LST_SHORTAGE) */
    private static final ListCellKey KEY_LST_SHORTAGE = new ListCellKey("LST_SHORTAGE", new CheckBoxColumn(), true, true);

    /** lst_StorageListResultInput keys */
    private static final ListCellKey[] LST_STORAGELISTRESULTINPUT_KEYS = {
        KEY_HIDDEN_PLAN_LOT_NO,
        KEY_HIDEEN_PLAN_AREA_NO,
        KEY_HIDDEN_PLAN_LOCATION_NO,
        KEY_HIDDEN_STORAGE_CASE_QTY,
        KEY_HIDDEN_STORAGE_PIECE_QTY,
        KEY_HIDDEN_COLLECT_JOBNO,
        KEY_LST_SELECT,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_PLAN_LOCATION_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_STORAGE_CASE_QTY,
        KEY_LST_SHORTAGE,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_STORAGE_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_StorageListResultInput */
    private ListCellModel _lcm_lst_StorageListResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageListCompleteBusiness()
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
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearStorageQty_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearStorageQty_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_StorageListResultInput.
        _lcm_lst_StorageListResultInput = new ListCellModel(lst_StorageListResultInput, LST_STORAGELISTRESULTINPUT_KEYS, locale);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_LOT_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_AREA_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_STORAGE_CASE_QTY, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_STORAGE_PIECE_QTY, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_SHORTAGE, true);
    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
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
    private void lst_StorageListResultInput_SetLineToolTip(ListCellLine line)
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
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_ListWorkNo.setValue(null);
        chk_InitialInputStorageNumber.setChecked(false);
        btn_Complete.setEnabled(false);
        btn_ClearStorageQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRStoragePlanDate.setReadOnly(true);
        txt_LRListWorkNo.setReadOnly(true);
        _lcm_lst_StorageListResultInput.clear();
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, true);
        txt_ListWorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageListCompleteSCHParams inparam = new StorageListCompleteSCHParams();
            inparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_StoragePlanDate.getValue());
            inparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_ListWorkNo.getValue());
            inparam.set(StorageListCompleteSCHParams.STORAGE_QTY_INPUT_NUMBER, chk_InitialInputStorageNumber.getValue());
            inparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.getNewLine();
                txt_LRStoragePlanDate.setValue(outparam.get(StorageListCompleteSCHParams.PLAN_DAY));
                txt_LRListWorkNo.setValue(outparam.get(StorageListCompleteSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StorageListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDEEN_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_HIDDEN_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_HIDDEN_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(StorageListCompleteSCHParams.COLLECT_JOBNO));
                lst_StorageListResultInput_SetLineToolTip(line);
                _lcm_lst_StorageListResultInput.add(line);
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_ClearStorageQty.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
        txt_ListWorkNo.setValue(null);
        chk_InitialInputStorageNumber.setChecked(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Complete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListResultInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StorageListCompleteSCHParams lineparam = new StorageListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_LRListWorkNo.getValue());
                lineparam.set(StorageListCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StorageListCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StorageListCompleteSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(StorageListCompleteSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(StorageListCompleteSCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(StorageListCompleteSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(StorageListCompleteSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(StorageListCompleteSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(StorageListCompleteSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_STORAGE_CASE_QTY));
                lineparam.set(StorageListCompleteSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_STORAGE_PIECE_QTY));
                lineparam.set(StorageListCompleteSCHParams.SHORTAGE, line.getValue(KEY_LST_SHORTAGE));
                lineparam.set(StorageListCompleteSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_LRStoragePlanDate.getValue());
                lineparam.set(StorageListCompleteSCHParams.COLLECT_JOBNO, line.getValue(KEY_HIDDEN_COLLECT_JOBNO));
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
                _lcm_lst_StorageListResultInput.resetEditRow();
                _lcm_lst_StorageListResultInput.resetHighlight();
                _lcm_lst_StorageListResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.get(i);
                lst_StorageListResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_StoragePlanDate.getStringValue(), "");
                part11List.add(txt_ListWorkNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STORAGE_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STORAGE_PIECE_QTY), "0");

                if (lst_StorageListResultInput.getChecked(_lcm_lst_StorageListResultInput.getColumnIndex(KEY_LST_SHORTAGE)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("2", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StorageListResultInput.resetEditRow();
            _lcm_lst_StorageListResultInput.resetHighlight();

            // set input parameters.
            StorageListCompleteSCHParams inparam = new StorageListCompleteSCHParams();
            inparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_LRStoragePlanDate.getValue());
            inparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_LRListWorkNo.getValue());
            inparam.set(StorageListCompleteSCHParams.STORAGE_QTY_INPUT_NUMBER, chk_InitialInputStorageNumber.getValue());
            inparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StorageListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.getNewLine();
                txt_LRStoragePlanDate.setValue(outparam.get(StorageListCompleteSCHParams.PLAN_DAY));
                txt_LRListWorkNo.setValue(outparam.get(StorageListCompleteSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StorageListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDEEN_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_HIDDEN_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_HIDDEN_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(StorageListCompleteSCHParams.COLLECT_JOBNO));
                lst_StorageListResultInput_SetLineToolTip(line);
                _lcm_lst_StorageListResultInput.add(line);
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
    private void btn_ClearStorageQty_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListResultInput.get(i);
            lst_StorageListResultInput.setCurrentRow(i);
            clearLine.setValue(KEY_LST_STORAGE_CASE_QTY, null);
            clearLine.setValue(KEY_LST_STORAGE_PIECE_QTY, null);
            clearLine.setValue(KEY_LST_SHORTAGE, Boolean.FALSE);
            lst_StorageListResultInput_SetLineToolTip(clearLine);
            _lcm_lst_StorageListResultInput.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_StorageListResultInput.clear();
        btn_Complete.setEnabled(false);
        btn_ClearStorageQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRStoragePlanDate.setValue(null);
        txt_LRListWorkNo.setValue(null);
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
