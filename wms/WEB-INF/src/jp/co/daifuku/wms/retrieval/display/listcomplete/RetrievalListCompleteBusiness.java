// $Id: RetrievalListCompleteBusiness.java 7960 2010-05-27 06:00:25Z shibamoto $
package jp.co.daifuku.wms.retrieval.display.listcomplete;

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
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListCompleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListCompleteSCHParams;

/**
 * 出庫リスト作業結果入力の画面処理を行います。
 * 
 * @version $Revision: 7960 $, $Date: 2010-05-27 15:00:25 +0900 (木, 27 5 2010) $
 * @author BusiTune 1.0 Generator.
 * @author Last commit: $Author: shibamoto $
 */
public class RetrievalListCompleteBusiness
        extends RetrievalListComplete
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:ここから
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK:ここまで

    /** lst_RetrievalListResultInput(HIDDEN_COLLECT_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_COLLECT_JOBNO = new ListCellKey("HIDDEN_COLLECT_JOBNO", new StringCellColumn(), false, false);

    /** lst_RetrievalListResultInput(HIDDEN_PLAN_LOT) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOT = new ListCellKey("HIDDEN_PLAN_LOT", new StringCellColumn(), false, false);

    /** lst_RetrievalListResultInput(HIDDEN_PLAN_AREA_NAME) */
    private static final ListCellKey KEY_HIDDEN_PLAN_AREA_NAME = new ListCellKey("HIDDEN_PLAN_AREA_NAME", new StringCellColumn(), false, false);

    /** lst_RetrievalListResultInput(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_RetrievalListResultInput(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_RetrievalListResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalListResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalListResultInput(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, true);

    /** lst_RetrievalListResultInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalListResultInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalListResultInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalListResultInput(LST_RESULT_CASE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_CASE_QTY = new ListCellKey("LST_RESULT_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalListResultInput(LST_RESULT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_PIECE_QTY = new ListCellKey("LST_RESULT_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalListResultInput(LST_SHORTAGE) */
    private static final ListCellKey KEY_LST_SHORTAGE = new ListCellKey("LST_SHORTAGE", new CheckBoxColumn(), true, true);

    /** lst_RetrievalListResultInput keys */
    private static final ListCellKey[] LST_RETRIEVALLISTRESULTINPUT_KEYS = {
        KEY_HIDDEN_COLLECT_JOBNO,
        KEY_HIDDEN_PLAN_LOT,
        KEY_HIDDEN_PLAN_AREA_NAME,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RESULT_CASE_QTY,
        KEY_LST_SHORTAGE,
        KEY_LST_PLAN_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RESULT_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_RetrievalListResultInput */
    private ListCellModel _lcm_lst_RetrievalListResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalListCompleteBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
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
        if (eventSource.startsWith("btn_Complete_Click"))
        {
            btn_Complete_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
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
        btn_Complete_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearRetrievalQty_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearRetrievalQty_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalListResultInput_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalListResultInput_ColumClick(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_RetrievalListResultInput.
        _lcm_lst_RetrievalListResultInput = new ListCellModel(lst_RetrievalListResultInput, LST_RETRIEVALLISTRESULTINPUT_KEYS, locale);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_PLAN_LOT_NO, false);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_RESULT_CASE_QTY, false);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_RESULT_PIECE_QTY, false);
        _lcm_lst_RetrievalListResultInput.setToolTipVisible(KEY_LST_SHORTAGE, true);

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
    private void lst_RetrievalListResultInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK S 
        line.addToolTip("LBL-W0117", KEY_HIDDEN_PLAN_AREA_NAME);
        // DFKLOOK E
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
        setFocus(txt_RetrievalPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Complete.setEnabled(false);
        btn_ClearRetrievalQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRRetrievalPlanDate.setReadOnly(true);
        txt_LRListWorkNo.setReadOnly(true);
        txt_OrderNo.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_ListWorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalListCompleteSCHParams inparam = new RetrievalListCompleteSCHParams();
            inparam.set(RetrievalListCompleteSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalListCompleteSCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(RetrievalListCompleteSCHParams.INITIAL_INPUT_RETRIEVAL_NO, chk_InitialInputRetrievalNo.getValue());
            inparam.set(RetrievalListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalListResultInput.clear();

            // DFKLOOK
            if (outparams.size() == 0)
            {
                btn_Complete.setEnabled(false);
                btn_ClearRetrievalQty.setEnabled(false);
                btn_ListClear.setEnabled(false);
                txt_LRRetrievalPlanDate.setValue(null);
                txt_LRListWorkNo.setValue(null);
                txt_OrderNo.setValue(null);
                return;
            }
            // DFKLOOK

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalListResultInput.getNewLine();
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(RetrievalListCompleteSCHParams.COLLECT_JOBNO));
                line.setValue(KEY_HIDDEN_PLAN_LOT, outparam.get(RetrievalListCompleteSCHParams.LOT_NO));
                line.setValue(KEY_HIDDEN_PLAN_AREA_NAME, outparam.get(RetrievalListCompleteSCHParams.AREA_NAME));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalListCompleteSCHParams.AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(RetrievalListCompleteSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(RetrievalListCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(RetrievalListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(RetrievalListCompleteSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(RetrievalListCompleteSCHParams.RESULT_PIECE_QTY));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.INITIAL_INPUT_RETRIEVAL_NO, chk_InitialInputRetrievalNo.getValue());
                txt_LRRetrievalPlanDate.setValue(outparam.get(RetrievalListCompleteSCHParams.RETRIEVAL_PLAN_DATE));
                txt_LRListWorkNo.setValue(outparam.get(RetrievalListCompleteSCHParams.WORK_NO));
                txt_OrderNo.setValue(outparam.get(RetrievalListCompleteSCHParams.ORDER_NO));
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE,
                        outparam.get(RetrievalListCompleteSCHParams.CONSIGNOR_CODE));
                lst_RetrievalListResultInput_SetLineToolTip(line);
                _lcm_lst_RetrievalListResultInput.add(line);
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_ClearRetrievalQty.setEnabled(true);
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
        txt_RetrievalPlanDate.setValue(null);
        txt_ListWorkNo.setValue(null);
        chk_InitialInputRetrievalNo.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    {

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalListResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalListResultInput.get(i);

                // DFKLOOK
                //				if (!(line.isAppend() || line.isEdited())) {
                //					continue;
                //				}
                lst_RetrievalListResultInput.setCurrentRow(i);
                lst_RetrievalListResultInput.validate(line.getIndex(KEY_LST_PLAN_LOT_NO), false);
                // DFKLOOK

                RetrievalListCompleteSCHParams lineparam = new RetrievalListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalListCompleteSCHParams.CONSIGNOR_CODE,
                        viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(RetrievalListCompleteSCHParams.COLLECT_JOBNO, line.getValue(KEY_HIDDEN_COLLECT_JOBNO));
                lineparam.set(RetrievalListCompleteSCHParams.LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT));
                lineparam.set(RetrievalListCompleteSCHParams.AREA_NAME, line.getValue(KEY_HIDDEN_PLAN_AREA_NAME));
                lineparam.set(RetrievalListCompleteSCHParams.AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(RetrievalListCompleteSCHParams.LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(RetrievalListCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(RetrievalListCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(RetrievalListCompleteSCHParams.LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(RetrievalListCompleteSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(RetrievalListCompleteSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(RetrievalListCompleteSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(RetrievalListCompleteSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_RESULT_CASE_QTY));
                lineparam.set(RetrievalListCompleteSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_RESULT_PIECE_QTY));
                lineparam.set(RetrievalListCompleteSCHParams.SHORTAGE, line.getValue(KEY_LST_SHORTAGE));
                lineparam.set(RetrievalListCompleteSCHParams.INITIAL_INPUT_RETRIEVAL_NO,
                        viewState.getObject(ViewStateKeys.INITIAL_INPUT_RETRIEVAL_NO));
                lineparam.set(RetrievalListCompleteSCHParams.WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
                lineparam.set(RetrievalListCompleteSCHParams.RETRIEVAL_PLAN_DATE,
                        viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK Start

            // ハイライトのリセット
            _lcm_lst_RetrievalListResultInput.resetEditRow();
            _lcm_lst_RetrievalListResultInput.resetHighlight();
            if (StringUtil.isBlank(eventSource))
            {
                // リスト件数分繰り返す
                for (ScheduleParams p : inparams)
                {
                    // リスト入力チェックを行う
                    if (!sch.check(p, p.getRowIndex()))
                    {
                        // ハイライト
                        _lcm_lst_RetrievalListResultInput.addHighlight(p.getRowIndex(), ControlColor.Warning);

                        // メッセージのセット
                        message.setMsgResourceKey(sch.getMessage());
                        return;
                    }
                }
                
                // TODO: display message
                // 完了しますか?
                this.setConfirm("MSG-W0043", false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
                return;
            }

            // DFKLOOK End

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalListResultInput.resetEditRow();
                _lcm_lst_RetrievalListResultInput.resetHighlight();
                _lcm_lst_RetrievalListResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalListResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalListResultInput.get(i);
                lst_RetrievalListResultInput.setCurrentRow(i);

                //DFKLOOK:ここから修正
                //                // exclusion unmodified row.
                //                if (!(line.isAppend() || line.isEdited()))
                //                {
                //                    continue;
                //                }
                //DFKLOOK:ここまで修正

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_LRRetrievalPlanDate.getStringValue(), "");
                part11List.add(txt_LRListWorkNo.getStringValue(), "");
                part11List.add(txt_OrderNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_RESULT_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_RESULT_PIECE_QTY), "0");
                part11List.add("1", "");

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING),
                        part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalListResultInput.resetEditRow();
            _lcm_lst_RetrievalListResultInput.resetHighlight();

            // clear.
            _lcm_lst_RetrievalListResultInput.clear();
            btn_Complete.setEnabled(false);
            btn_ClearRetrievalQty.setEnabled(false);
            btn_ListClear.setEnabled(false);
            txt_LRRetrievalPlanDate.setValue(null);
            txt_LRListWorkNo.setValue(null);
            txt_OrderNo.setValue(null);

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
    private void btn_ClearRetrievalQty_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_RetrievalListResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_RetrievalListResultInput.get(i);
            lst_RetrievalListResultInput.setCurrentRow(i);
            // DFKLOOK S
            clearLine.setValue(KEY_LST_RESULT_CASE_QTY, 0);
            clearLine.setValue(KEY_LST_RESULT_PIECE_QTY, 0);
            clearLine.setValue(KEY_LST_SHORTAGE, Boolean.FALSE);
            // DFKLOOK E
            lst_RetrievalListResultInput_SetLineToolTip(clearLine);
            _lcm_lst_RetrievalListResultInput.set(i, clearLine);
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
        _lcm_lst_RetrievalListResultInput.clear();
        btn_Complete.setEnabled(false);
        btn_ClearRetrievalQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRRetrievalPlanDate.setValue(null);
        txt_LRListWorkNo.setValue(null);
        txt_OrderNo.setValue(null);

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
