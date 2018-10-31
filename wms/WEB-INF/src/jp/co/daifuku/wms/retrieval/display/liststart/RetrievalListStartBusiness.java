// $Id: RetrievalListStartBusiness.java 7407 2010-03-06 02:46:57Z okayama $
package jp.co.daifuku.wms.retrieval.display.liststart;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalListStartDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalListStartDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalStartListParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListStartSCHParams;

/**
 * 出庫リスト作業開始の画面処理を行います。
 * 
 * @version $Revision: 7407 $, $Date: 2010-03-06 11:46:57 +0900 (土, 06 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalListStartBusiness
        extends RetrievalListStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:ここから修正    
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK:ここまで修正

    /** lst_RetrievalListStart(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_RetrievalListStart(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalListStart(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME = new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalListStart(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_RetrievalListStart keys */
    private static final ListCellKey[] LST_RETRIEVALLISTSTART_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_AREA_NAME,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_RetrievalListStart */
    private ListCellModel _lcm_lst_RetrievalListStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalListStartBusiness()
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
        // DFKLOOK:ここから修正
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
        if (eventSource.startsWith("btn_WorkStart_Click"))
        {
            btn_WorkStart_Click_Process(eventSource);
        }
        // DFKLOOK:ここまで修正
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
    public void btn_WorkStart_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkStart_Click_Process(null);
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

        // initialize lst_RetrievalListStart.
        _lcm_lst_RetrievalListStart = new ListCellModel(lst_RetrievalListStart, LST_RETRIEVALLISTSTART_KEYS, locale);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_AREA_NAME, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_RetrievalListStart.setToolTipVisible(KEY_LST_DETAIL_COUNT, true);

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
    private void lst_RetrievalListStart_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0117", KEY_LST_AREA_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // DFKLOOK ここから
        // 一覧クリアボタン押下時確認ダイアログ"全情報が取り消されます。よろしいですか？"
        btn_ListClear.setBeforeConfirm("MSG-W0015");
        // DFKLOOK ここまで

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
        btn_WorkStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        _lcm_lst_RetrievalListStart.clear();
        chk_LIssueReport.setEnabled(false);
        chk_LIssueReport.setChecked(true);

        //      get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListStartSCH(conn, this.getClass(), locale, ui);

            RetrievalListStartSCHParams inParam = new RetrievalListStartSCHParams();
            inParam.set(RetrievalListStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);

            if (outParam != null)
            {
                String printflg = outParam.getString(RetrievalListStartSCHParams.L_ISSUE_REPORT);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_LIssueReport.setChecked(false);
                }
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
        //DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalListStartSCHParams inparam = new RetrievalListStartSCHParams();
            inparam.set(RetrievalListStartSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalListStartSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalListStartSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(RetrievalListStartSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(RetrievalListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalListStart.clear();

            // DFKLOOK ここから
            // clear.
            if (outparams.size() == 0)
            {
                return;
            }
            // DFKLOOK ここまで

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalListStart.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalListStartSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalListStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalListStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalListStartSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(RetrievalListStartSCHParams.AREA_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalListStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalListStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalListStartSCHParams.DETAIL_COUNT));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
                lst_RetrievalListStart_SetLineToolTip(line);
                _lcm_lst_RetrievalListStart.add(line);
            }

            // clear.
            btn_WorkStart.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);

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
        txt_BatchNo.setValue(null);
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_WorkStart_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalListStart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalListStart.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // 6123103 = データを選択してください。
            message.setMsgResourceKey("6123103");
            return;
        }

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 開始しますか？
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkStart_Click");
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalListStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalListStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RetrievalListStartSCHParams lineparam = new RetrievalListStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalListStartSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(RetrievalListStartSCHParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(RetrievalListStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(RetrievalListStartSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(RetrievalListStartSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(RetrievalListStartSCHParams.AREA_NAME, line.getValue(KEY_LST_AREA_NAME));
                lineparam.set(RetrievalListStartSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalListStartSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalListStartSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_DETAIL_COUNT));
                lineparam.set(RetrievalListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(RetrievalListStartSCHParams.L_ISSUE_REPORT, chk_LIssueReport.getValue());
                // DFKLOOK ここから
                lineparam.set(RetrievalListStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK ここまで
                inparamList.add(lineparam);
            }

            // DFKLOOK ここから
            RetrievalListStartSCHParams queryparam = new RetrievalListStartSCHParams();
            queryparam.set(RetrievalListStartSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            queryparam.set(RetrievalListStartSCHParams.RETRIEVAL_PLAN_DATE,
                    viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
            queryparam.set(RetrievalListStartSCHParams.ORDER_NO_FROM, viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
            queryparam.set(RetrievalListStartSCHParams.ORDER_NO_TO, viewState.getObject(ViewStateKeys.ORDER_NO_TO));
            // DFKLOOK ここまで

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK ここから　startSCH -> startSCHgetParams に変更
            //          復帰条件BooleanからListのnull時に変更
            // SCH call.
            List<Params> schparams = sch.startSCHgetParams(inparams);
            if (schparams == null)
            {
                // DFKLOOK ここまで

                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalListStart.resetEditRow();
                _lcm_lst_RetrievalListStart.resetHighlight();
                _lcm_lst_RetrievalListStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalListStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalListStart.get(i);
                lst_RetrievalListStart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");

                if (chk_LIssueReport.getChecked())
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

            // DFKLOOK ここから
            // 帳票発行処理
            Boolean blnPrint = true;
            if (chk_LIssueReport.getChecked())
            {
                // process call.
                if (sb_Print_Click_Process(schparams) == false)
                {
                    blnPrint = false;
                }
            }

            // SCH call.
            List<Params> outparams = sch.query(queryparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalListStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalListStart.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalListStartSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalListStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalListStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalListStartSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(RetrievalListStartSCHParams.AREA_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalListStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalListStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalListStartSCHParams.DETAIL_COUNT));
                lst_RetrievalListStart_SetLineToolTip(line);
                _lcm_lst_RetrievalListStart.add(line);
            }

            if (blnPrint == true)
            {
                // 6021021 = 開始しました。
                message.setMsgResourceKey("6021021");
            }
            else
            {
                // 6007042 = 設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey("6007042");
            }

            if (outparams.size() == 0)
            {
                // reset editing row.
                _lcm_lst_RetrievalListStart.resetEditRow();
                _lcm_lst_RetrievalListStart.resetHighlight();

                // clear.
                _lcm_lst_RetrievalListStart.clear();
                btn_WorkStart.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
            }

            // DFKLOOK ここまで

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

    // DFKLOOK ここから
    /**
     * 開始ボタン後、印刷処理
     * @throws Exception
     */
    private boolean sb_Print_Click_Process(List<Params> schparams)
            throws Exception
    {

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;

        PrintExporter exporter = null;
        RetrievalListStartDASCH dasch = null;

        try
        {
            // set input parameters.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalListStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // 設定単位キーの取得
            Iterator<Params> it = schparams.iterator();
            while (it.hasNext())
            {
                // スケジュールから受け取ったパラメータを編集
                RetrievalListStartDASCHParams printparam = new RetrievalListStartDASCHParams();
                Params pNextparam = (Params)it.next();
                printparam.set(RetrievalListStartDASCHParams.SETTING_UNIT_KEY,
                        pNextparam.get(RetrievalListStartDASCHParams.SETTING_UNIT_KEY));

                // check count.
                int count = dasch.count(printparam);
                if (count == 0)
                {
                    message.setMsgResourceKey("6003011");
                    return false;
                }

                // DASCH call.
                dasch.search(printparam);

                // open exporter.
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("RetrievalStartList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    RetrievalStartListParams expparam = new RetrievalStartListParams();
                    expparam.set(RetrievalStartListParams.DFK_DS_NO,
                            outparam.get(RetrievalListStartDASCHParams.DFK_DS_NO));
                    expparam.set(RetrievalStartListParams.DFK_USER_ID,
                            outparam.get(RetrievalListStartDASCHParams.DFK_USER_ID));
                    expparam.set(RetrievalStartListParams.DFK_USER_NAME,
                            outparam.get(RetrievalListStartDASCHParams.DFK_USER_NAME));
                    expparam.set(RetrievalStartListParams.JOB_NO, outparam.get(RetrievalListStartDASCHParams.JOB_NO));
                    expparam.set(RetrievalStartListParams.PLAN_DAY,
                            outparam.get(RetrievalListStartDASCHParams.PLAN_DAY));
                    expparam.set(RetrievalStartListParams.BATCH_NO,
                            outparam.get(RetrievalListStartDASCHParams.BATCH_NO));
                    expparam.set(RetrievalStartListParams.SYS_DAY, outparam.get(RetrievalListStartDASCHParams.SYS_DAY));
                    expparam.set(RetrievalStartListParams.SYS_TIME,
                            outparam.get(RetrievalListStartDASCHParams.SYS_TIME));
                    expparam.set(RetrievalStartListParams.ORDER_NO,
                            outparam.get(RetrievalListStartDASCHParams.ORDER_NO));
                    expparam.set(RetrievalStartListParams.CUSTOMER_CODE,
                            outparam.get(RetrievalListStartDASCHParams.CUSTOMER_CODE));
                    expparam.set(RetrievalStartListParams.CUSTOMER_NAME,
                            outparam.get(RetrievalListStartDASCHParams.CUSTOMER_NAME));
                    expparam.set(RetrievalStartListParams.AREA_NO, outparam.get(RetrievalListStartDASCHParams.AREA_NO));
                    expparam.set(RetrievalStartListParams.AREA_NAME,
                            outparam.get(RetrievalListStartDASCHParams.AREA_NAME));
                    expparam.set(RetrievalStartListParams.PLAN_LOCATION_NO,
                            outparam.get(RetrievalListStartDASCHParams.PLAN_LOCATION_NO));
                    expparam.set(RetrievalStartListParams.ITEM_CODE,
                            outparam.get(RetrievalListStartDASCHParams.ITEM_CODE));
                    expparam.set(RetrievalStartListParams.ITEM_NAME,
                            outparam.get(RetrievalListStartDASCHParams.ITEM_NAME));
                    expparam.set(RetrievalStartListParams.PLAN_LOT_NO,
                            outparam.get(RetrievalListStartDASCHParams.PLAN_LOT_NO));
                    expparam.set(RetrievalStartListParams.ENTERING_QTY,
                            outparam.get(RetrievalListStartDASCHParams.ENTERING_QTY));
                    expparam.set(RetrievalStartListParams.PLAN_CASE_QTY,
                            outparam.get(RetrievalListStartDASCHParams.PLAN_CASE_QTY));
                    expparam.set(RetrievalStartListParams.PLAN_PIECE_QTY,
                            outparam.get(RetrievalListStartDASCHParams.PLAN_PIECE_QTY));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
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
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;
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
            DBUtil.close(conn);
        }
    }

    // DFKLOOK ここまで

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_RetrievalListStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_RetrievalListStart.get(i);
            lst_RetrievalListStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_RetrievalListStart_SetLineToolTip(clearLine);
            _lcm_lst_RetrievalListStart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_RetrievalListStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_RetrievalListStart.get(i);
            lst_RetrievalListStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_RetrievalListStart_SetLineToolTip(clearLine);
            _lcm_lst_RetrievalListStart.set(i, clearLine);
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
        _lcm_lst_RetrievalListStart.clear();
        btn_WorkStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);

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
