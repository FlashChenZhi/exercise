// $Id: RetrievalOrderStartBusiness.java 7851 2010-04-21 08:49:56Z shibamoto $
package jp.co.daifuku.wms.retrieval.display.orderstart;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Green;
import jp.co.daifuku.bluedog.util.Red;
import jp.co.daifuku.bluedog.util.Yellow;
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel.AllocateType;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCHParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOutParameter;

/**
 * 出庫開始の画面処理を行います。
 *
 * @version $Revision: 7851 $, $Date: 2010-04-21 17:49:56 +0900 (水, 21 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RetrievalOrderStartBusiness
        extends RetrievalOrderStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK ここまで修正   

    /** lst_RetrievalStart(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 =
            new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_RetrievalStart(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_NOOF_RECORDS) */
    private static final ListCellKey KEY_LST_NOOF_RECORDS =
            new ListCellKey("LST_NOOF_RECORDS", new NumberCellColumn(0), true, false);

    /** lst_RetrievalStart(LST_ALLOCATION_RESULT) */
    private static final ListCellKey KEY_LST_ALLOCATION_RESULT =
            new ListCellKey("LST_ALLOCATION_RESULT", new StringCellColumn(), true, false);

    /** lst_RetrievalStart kyes */
    private static final ListCellKey[] LST_RETRIEVALSTART_KEYS = {
            KEY_LST_COLUMN_1,
            KEY_LST_BATCH,
            KEY_LST_ORDER,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_NOOF_RECORDS,
            KEY_LST_ALLOCATION_RESULT,
            KEY_LST_CUSTOMER_NAME,
    };

    // DFKLOOK ここから修正
    /**
     * 欠品完了チェックに関するダイアログフラグ用キー(true : 確認中)
     */
    protected static final String DIALOG_SHORTAGE_FINISH = "DIALOG_SHORTAGE_FINISH";

    /**
     * ASRSの引当パターンに関するダイアログフラグ用キー(true : 確認中)
     */
    protected static final String DIALOG_ASRS_ALLOCATE_PATTERN = "DIALOG_ASRS_ALLOCATE_PATTERN";

    /**
     * 緊急補充に関するダイアログフラグ用キー(true : 確認中)
     */
    protected static final String DIALOG_EMERGENCY_REPLENISHMENT = "DIALOG_EMERGENCY_REPLENISHMENT";

    /**
     * 選択チェック(リストセル)
     */
    protected static final int LST_CHECK = 1;

    /**
     * バッチNo.(リストセル)
     */
    protected static final int LST_BATCH_NO = 2;

    /**
     * オーダーNo.(リストセル)
     */
    protected static final int LST_ORDER_NO = 3;

    /**
     * 出荷先コード(リストセル)
     */
    protected static final int LST_CUSTOMER_CODE = 4;

    /**
     * 出荷先名称(リストセル)
     */
    protected static final int LST_CUSTOMER_NAME = 7;

    /**
     * 明細数(リストセル)
     */
    protected static final int LST_DETAIL_CNT = 5;

    /**
     * 引当結果(リストセル)
     */
    protected static final int LST_ALLOCATE_RESULT = 6;

    // DFKLOOK ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_LAllocatedPattern */
    private WmsAllocPriorityPullDownModel _pdm_pul_LAllocatedPattern;

    /** ListCellModel lst_RetrievalStart */
    private ListCellModel _lcm_lst_RetrievalStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalOrderStartBusiness()
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
        // initialize componenets.
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
    }

    // DFKLOOK ここから修正
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        page_ConfirmBack_Process(e);
    }

    // DFKLOOK ここまで修正


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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process(null);
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
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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

        // initialize pul_LAllocatedPattern.
        _pdm_pul_LAllocatedPattern = new WmsAllocPriorityPullDownModel(pul_LAllocatedPattern, locale, ui);

        // initialize lst_RetrievalStart.
        _lcm_lst_RetrievalStart = new ListCellModel(lst_RetrievalStart, LST_RETRIEVALSTART_KEYS, locale);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_BATCH, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_ORDER, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_NOOF_RECORDS, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_ALLOCATION_RESULT, false);

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

            // load pul_LAllocatedPattern.
            _pdm_pul_LAllocatedPattern.init(conn, AllocateType.NORMAL);

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
    private void lst_RetrievalStart_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_PlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LRetrievalPlanDate.setReadOnly(true);
        pul_LAllocatedPattern.setEnabled(false);
        _lcm_lst_RetrievalStart.clear();
        chk_LNoReplenishmentShortage.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_PlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalOrderStartSCHParams inparam = new RetrievalOrderStartSCHParams();
            inparam.set(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_PlanDate.getValue());
            inparam.set(RetrievalOrderStartSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalOrderStartSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(RetrievalOrderStartSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(RetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            if (outparams.size() == 0)
            {
                // clear.
                _lcm_lst_RetrievalStart.clear();

                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                pul_LAllocatedPattern.setEnabled(false);
                chk_LNoReplenishmentShortage.setEnabled(false);

                txt_LRetrievalPlanDate.setText("");

                return;
            }
            // DFKLOOK ここまで修正


            // output display.
            _lcm_lst_RetrievalStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalStart.getNewLine();
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalOrderStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalOrderStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalOrderStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalOrderStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_NOOF_RECORDS, outparam.get(RetrievalOrderStartSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_ALLOCATION_RESULT, outparam.get(RetrievalOrderStartSCHParams.ALLOCATION_RESULT));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_PlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
                txt_LRetrievalPlanDate.setValue(txt_PlanDate.getValue());
                lst_RetrievalStart_SetLineToolTip(line);
                _lcm_lst_RetrievalStart.add(line);
            }

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            pul_LAllocatedPattern.setEnabled(true);
            chk_LNoReplenishmentShortage.setEnabled(true);

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
        txt_PlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        pul_LAllocatedPattern.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalStart.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }
        // DFKLOOK ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 開始しますか？
            this.setConfirm("MSG-W0031", true, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return;
        }
        
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // 確認することがあるかどうかを判定する
            if (hasConfirmMessage())
            {
                return;
            }
            // エラーメッセージが設定されている場合は処理終了
            if (!StringUtil.isBlank(message.getMsgResourceKey()))
            {
                return;
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

        // 出庫開始
        retrievalStart();

        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_RetrievalStart.get(i);
            lst_RetrievalStart.setCurrentRow(i);
            clearline.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_RetrievalStart_SetLineToolTip(clearline);
            _lcm_lst_RetrievalStart.set(i, clearline);
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
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_RetrievalStart.get(i);
            lst_RetrievalStart.setCurrentRow(i);
            clearline.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_RetrievalStart_SetLineToolTip(clearline);
            _lcm_lst_RetrievalStart.set(i, clearline);
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
        _lcm_lst_RetrievalStart.clear();
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LRetrievalPlanDate.setValue(null);
        pul_LAllocatedPattern.setEnabled(false);
        chk_LNoReplenishmentShortage.setEnabled(false);

    }

    // DFKLOOK ここから修正

    /**
     * 出庫開始処理を行います。<BR>
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void retrievalStart()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RetrievalOrderStartSCHParams lineparam = new RetrievalOrderStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalOrderStartSCHParams.COLUMN_1, line.getValue(KEY_LST_COLUMN_1));
                lineparam.set(RetrievalOrderStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH));
                lineparam.set(RetrievalOrderStartSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER));
                lineparam.set(RetrievalOrderStartSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalOrderStartSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalOrderStartSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_NOOF_RECORDS));
                lineparam.set(RetrievalOrderStartSCHParams.ALLOCATION_RESULT, line.getValue(KEY_LST_ALLOCATION_RESULT));
                lineparam.set(RetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_LRetrievalPlanDate.getValue());
                lineparam.set(RetrievalOrderStartSCHParams.ALLOCATED_PATTERN,
                        _pdm_pul_LAllocatedPattern.getSelectedValue());
                lineparam.set(RetrievalOrderStartSCHParams.SHORTAGE_COMPLETION_FLAG,
                        chk_LNoReplenishmentShortage.getValue());
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            try
            {
                // SCH call.
                if (!sch.startSCH(inparams))
                {
                    // rollback.
                    conn.rollback();
                    message.setMsgResourceKey(sch.getMessage());

                    // reset editing row or highligiting error row.
                    _lcm_lst_RetrievalStart.resetEditRow();
                    _lcm_lst_RetrievalStart.resetHighlight();
                    _lcm_lst_RetrievalStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                    return;
                }
                
                for(RetrievalOutParameter outParams :sch.getOutParam())
                {
                    P11LogWriter part11Writer = new P11LogWriter(conn);
                    Part11List part11List = new Part11List();
                    part11List.add(txt_LRetrievalPlanDate.getStringValue(), "");
                    part11List.add(_pdm_pul_LAllocatedPattern.getSelectedStringValue(), "");
                    part11List.add(lst_RetrievalStart.getValue(LST_BATCH_NO), "");
                    part11List.add(lst_RetrievalStart.getValue(LST_ORDER_NO), "");
                    part11List.add(lst_RetrievalStart.getValue(LST_CUSTOMER_CODE), "");
                    part11List.add(outParams.getAllocateResult(), "");
                    if (RetrievalInParameter.ALLOCATE_RESULT_SHORTAGECOMP.equals(outParams.getAllocateResult()))
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
                
                // DFKLOOK:ここから修正
                // 統計情報の取得を行います。
                RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
                WorkInfoHandler workInfoHandler = new WorkInfoHandler(conn);
                StockHandler stockInfoHandler = new StockHandler(conn);
                ShortageInfoHandler shortageInfoHandler = new ShortageInfoHandler(conn);
                planHandler.getStatics();
                workInfoHandler.getStatics();
                stockInfoHandler.getStatics();
                shortageInfoHandler.getStatics();
                // DFKLOOK:ここまで修正
            }
            finally
            {
                if (sch.getOutParam() != null)
                {
                    // 引当結果をリストセルに反映する
                    setAllocateResult(sch.getOutParam());
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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     * ダイアログボタンから、戻ってくるときにこのメソッドが呼ばれます。<BR>
     * <CODE>Page</CODE>に定義されている<CODE>page_ConfirmBack</CODE>をオーバライドします。<BR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    private void page_ConfirmBack_Process(ActionEvent e)
            throws Exception
    {

        // 今回の確認が引当パターンの確認であった場合
        if (getViewState().getBoolean(DIALOG_ASRS_ALLOCATE_PATTERN))
        {
            // 引当パターンの確認フラグを初期化する
            getViewState().setBoolean(DIALOG_ASRS_ALLOCATE_PATTERN, false);

            // 緊急補充に関する確認項目があるかどうかの確認を行う
            if (isConfirmEmergencyReplenishment())
            {
                getViewState().setBoolean(DIALOG_EMERGENCY_REPLENISHMENT, true);
                return;
            }
            // エラーメッセージが設定されている場合は処理終了
            if (!StringUtil.isBlank(message.getMsgResourceKey()))
            {
                return;
            }

            // 欠品完了に関する確認項目があるかどうかの確認を行う
            if (isConfirmShortageFinish())
            {
                getViewState().setBoolean(DIALOG_SHORTAGE_FINISH, true);
                return;
            }
            // エラーメッセージが設定されている場合は処理終了
            if (!StringUtil.isBlank(message.getMsgResourceKey()))
            {
                return;
            }

            // 確認→OK の場合は出庫開始
            retrievalStart();
        }
        else
        {
            // get event source.
            String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
            if (eventSource == null)
            {
                return;
            }

            // 確認状態の取得
            viewState.remove(_KEY_CONFIRMSOURCE);

            // キャンセル押下時処理
            boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
            if (!isExecute)
            {
                return;
            }

            // choose process.
            if (eventSource.startsWith("btn_Start_Click"))
            {
                btn_Start_Click_Process(eventSource);
            }
            else
            {
                retrievalStart();
            }
        }
    }

    /**
     * 出庫開始処理前に確認することがあるかどうかを判定します。<BR>
     * 
     * @return 要確認の場合true、それ以外false
     * @throws Exception 全ての例外を報告します。
     */
    private boolean hasConfirmMessage()
            throws Exception
    {
        // 引当パターンの確認
        if (isConfirmAsrsArea())
        {
            getViewState().setBoolean(DIALOG_ASRS_ALLOCATE_PATTERN, true);
            return true;
        }

        // 緊急補充の確認
        if (isConfirmEmergencyReplenishment())
        {
            getViewState().setBoolean(DIALOG_EMERGENCY_REPLENISHMENT, true);
            return true;
        }
        // エラーメッセージ取得時、表示する
        if (!StringUtil.isBlank(message.getMsgResourceKey()))
        {
            return false;
        }

        // 欠品完了の確認
        if (isConfirmShortageFinish())
        {
            getViewState().setBoolean(DIALOG_SHORTAGE_FINISH, true);
            return true;
        }
        return false;
    }

    /**
     * 欠品完了確認ダイアログを表示するかどうかを判定します。<BR>
     * 
     * @return 確認が必要な場合はtrue
     */
    private boolean isConfirmShortageFinish()
    {
        // 欠品発生時、補充を行わずに欠品完了チェックがある場合
        if (chk_LNoReplenishmentShortage.getChecked())
        {
            // MSG-W0035=欠品完了します。よろしいですか？
            setConfirm("MSG-W0035", true, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "shortage");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return true;
        }
        return false;
    }

    /**
     * 引当パターンのASRSエリアに関する確認ダイアログを表示するかどうかを判定します。<BR>
     * 
     * @return 確認が必要な場合はtrue
     * @throws Exception 全ての例外を報告します。
     */
    private boolean isConfirmAsrsArea()
            throws Exception
    {
        RetrievalOrderStartSCH schedule = null;
        Connection conn = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            conn = ConnectionManager.getRequestConnection(this);

            schedule = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // 引当パターンを設定
            RetrievalOrderStartSCHParams checkParam = new RetrievalOrderStartSCHParams();
            checkParam.set(RetrievalOrderStartSCHParams.ALLOCATED_PATTERN, pul_LAllocatedPattern.getSelectedValue());

            if (!schedule.check(checkParam))
            {
                if (schedule.isConfirmFlag())
                {
                    setConfirm(schedule.getMessage(), true, true);
                    // 「処理中です」メッセージ表示
                    message.setMsgResourceKey("6001017");
                    return true;
                }
                // エラーメッセージを設定
                message.setMsgResourceKey(schedule.getMessage());
            }
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex2)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex2, this));
            }
        }
        return false;
    }

    /**
     * 緊急補充に関する確認ダイアログを表示するかどうかを判定します。<BR>
     * 
     * @return 確認が必要な場合はtrue
     * @throws Exception 全ての例外を報告します。
     */
    private boolean isConfirmEmergencyReplenishment()
            throws Exception
    {
        // 欠品完了チェックがない場合はチェックしない
        if (!chk_LNoReplenishmentShortage.getChecked())
        {
            return false;
        }

        RetrievalOrderStartSCH schedule = null;
        Connection conn = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            conn = ConnectionManager.getRequestConnection(this);

            schedule = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            if (schedule.hasEmergencyReplenishment(null))
            {
                // 未作業の緊急補充作業が存在します。欠品完了してよろしいですか？
                setConfirm("MSG-W0045", true, true);
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
                return true;
            }
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex2)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex2, this));
            }
        }
        return false;
    }

    /**
     * リストセルの引当結果の設定を行います。<BR>
     * 又、引当結果の状態に応じて選択チェックボックスやボタンの使用可否の制御を行います。<BR>
     * 
     * @param outParams 出庫開始処理で返された出庫出力パラメータ
     */
    private void setAllocateResult(RetrievalOutParameter[] outParams)
            throws Exception
    {
        // 一度背景色をリセットする
        lst_RetrievalStart.resetHighlight();
        
        for (RetrievalOutParameter out : outParams)
        {
            lst_RetrievalStart.setCurrentRow(out.getRowNo());

            lst_RetrievalStart.setValue(LST_ALLOCATE_RESULT, DisplayResource.getAllocateResult(out.getAllocateResult()));

            // 引当状態が完了か欠品完了になっていれば選択チェックボックスを入力不可にします
            if (RetrievalInParameter.ALLOCATE_RESULT_COMPLETION.equals(out.getAllocateResult())
                    || RetrievalInParameter.ALLOCATE_RESULT_SHORTAGECOMP.equals(out.getAllocateResult()))
            {
                lst_RetrievalStart.setCellEnabled(LST_CHECK, false);
            }

            // 欠品、欠品完了の場合はハイライト「黄色」
            if (RetrievalInParameter.ALLOCATE_RESULT_SHORTAGE.equals(out.getAllocateResult())
                    || RetrievalInParameter.ALLOCATE_RESULT_SHORTAGECOMP.equals(out.getAllocateResult()))
            {
                DisplayUtil.addHighlight(lst_RetrievalStart, out.getRowNo(), new Yellow());
            }
            // エラーの場合はハイライト「赤」
            else if (RetrievalInParameter.ALLOCATE_RESULT_ERROR.equals(out.getAllocateResult()))
            {
                DisplayUtil.addHighlight(lst_RetrievalStart, out.getRowNo(), new Red());
            }
            // 補充ありの場合はハイライト「緑」
            else if (RetrievalInParameter.ALLOCATE_RESULT_REPLENISHMENT.equals(out.getAllocateResult()))
            {
                DisplayUtil.addHighlight(lst_RetrievalStart, out.getRowNo(), new Green());
            }

        }
        boolean isRemainder = false;

        for (int i = 1; i < lst_RetrievalStart.getMaxRows(); i++)
        {
            // 対象行の設定
            lst_RetrievalStart.setCurrentRow(i);

            if (StringUtil.isBlank(lst_RetrievalStart.getCellEnabled(LST_CHECK)))
            {
                isRemainder = true;
            }

        }
        // ためうちに作業可能データがなければ一覧クリアボタン以外を無効にします
        if (isRemainder)
        {
            // ボタン押下を可能にする
            setEnableListCellArea(true);
        }
        else
        {
            // ためうち部のボタンを無効にします。
            setEnableListCellArea(false);
            // 一覧クリアボタンのみ入力可にします
            btn_AllClear.setEnabled(true);
        }
    }

    /**
     * リストセルエリアのボタンの有効/無効の設定、チェックボックスの使用可否の設定を行います。<BR>
     * <BR>
     * @param flag true : ボタン有効 false : ボタン無効
     */
    private void setEnableListCellArea(boolean flag)
    {
        // 出庫開始ボタン無効
        btn_Start.setEnabled(flag);
        // 全件選択ボタン無効
        btn_AllCheck.setEnabled(flag);
        // 全選択解除ボタン無効
        btn_AllCheckClear.setEnabled(flag);
        // 一覧クリアボタン無効
        btn_AllClear.setEnabled(flag);
        // 欠品完了チェック
        chk_LNoReplenishmentShortage.setEnabled(flag);
        // 引当パターンプルダウン無効
        pul_LAllocatedPattern.setEnabled(flag);
    }

    // DFKLOOK ここまで修正


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
