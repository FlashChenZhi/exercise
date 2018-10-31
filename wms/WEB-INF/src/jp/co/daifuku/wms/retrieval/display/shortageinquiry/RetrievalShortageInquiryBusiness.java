package jp.co.daifuku.wms.retrieval.display.shortageinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalShortageInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalShortageInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.ShortageCheckListParams;
import jp.co.daifuku.wms.retrieval.listbox.startdate.LstRetrievalStartDateParams;

/**
 * 欠品情報照会の画面処理を行います。
 *
 * @version $Revision: 7521 $, $Date: 2010-03-13 14:53:55 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RetrievalShortageInquiryBusiness
        extends RetrievalShortageInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ShortageCheckList(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO =
            new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), false, false);

    /** lst_ShortageCheckList(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK =
            new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY =
            new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY =
            new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_REPLENISHMENT_CASE_QTY) */
    private static final ListCellKey KEY_LST_REPLENISHMENT_CASE_QTY =
            new ListCellKey("LST_REPLENISHMENT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_REPLENISHMENT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_REPLENISHMENT_PIECE_QTY =
            new ListCellKey("LST_REPLENISHMENT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_SHORTAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_CASE_QTY =
            new ListCellKey("LST_SHORTAGE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_SHORTAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_PIECE_QTY =
            new ListCellKey("LST_SHORTAGE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShortageCheckList(LST_ALLOCATED) */
    private static final ListCellKey KEY_LST_ALLOCATED =
            new ListCellKey("LST_ALLOCATED", new StringCellColumn(), true, false);

    /** lst_ShortageCheckList kyes */
    private static final ListCellKey[] LST_SHORTAGECHECKLIST_KEYS = {
            KEY_LST_PLAN_LOT_NO,
            KEY_LST_BATCH,
            KEY_LST_ORDER,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_ITEM_CODE,
            KEY_LST_CASE_PACK,
            KEY_LST_PLAN_CASE_QTY,
            KEY_LST_REPLENISHMENT_CASE_QTY,
            KEY_LST_SHORTAGE_CASE_QTY,
            KEY_LST_ALLOCATED,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_ITEM_NAME,
            KEY_LST_PLAN_PIECE_QTY,
            KEY_LST_REPLENISHMENT_PIECE_QTY,
            KEY_LST_SHORTAGE_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ShortageCheckList */
    private ListCellModel _lcm_lst_ShortageCheckList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalShortageInquiryBusiness()
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
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_PSearchRetrievalStartDate_Click"))
        {
            // process call.
            btn_PSearchRetrievalStartDate_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchRetrievalStartDate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchRetrievalStartDate_Click_Process();
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
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
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_Display_Click_SetList();
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_ShortageCheckList.
        _lcm_lst_ShortageCheckList = new ListCellModel(lst_ShortageCheckList, LST_SHORTAGECHECKLIST_KEYS, locale);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_BATCH, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_ORDER, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_REPLENISHMENT_CASE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_REPLENISHMENT_PIECE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_SHORTAGE_CASE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_SHORTAGE_PIECE_QTY, true);
        _lcm_lst_ShortageCheckList.setToolTipVisible(KEY_LST_ALLOCATED, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ShortageCheckList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK
        // HIDDEN項目をバルーン表示時、ラベル名が定義できないため。
        //        line.addToolTip("", KEY_LST_PLAN_LOT_NO);
        line.addToolTip("LBL-W0035", KEY_LST_PLAN_LOT_NO);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
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
        txt_AllocatedPatternNo.setReadOnly(true);
        txt_AllocatedPattenName.setReadOnly(true);
        _pager.clear();
        _pager.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchRetrievalStartDate_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRetrievalStartDateParams inparam = new LstRetrievalStartDateParams();
        inparam.set(LstRetrievalStartDateParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstRetrievalStartDateParams.PLAN_DATE, txt_RetrievalPlanDate.getValue());
        inparam.set(LstRetrievalStartDateParams.START_DATE, txt_RetrievalStartDate.getValue());
        inparam.set(LstRetrievalStartDateParams.START_TIME, txt_RetrievalStartTime.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchRetrievalStartDate_Click");
        redirect("/retrieval/listbox/startdate/LstRetrievalStartDate.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchRetrievalStartDate_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRetrievalStartDateParams outparam = new LstRetrievalStartDateParams(dialogParams);
        txt_RetrievalStartDate.setValue(outparam.get(LstRetrievalStartDateParams.START_DATETIME));
        txt_RetrievalStartTime.setValue(outparam.get(LstRetrievalStartDateParams.START_DATETIME));

        // set focus.
        setFocus(txt_RetrievalStartDate);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK
        // 表示ボタン処理時、必須チェックが必要
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_RetrievalStartDate.validate(this, true);
        txt_RetrievalStartTime.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        RetrievalShortageInquiryDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new RetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            RetrievalShortageInquiryDASCHParams inparam = new RetrievalShortageInquiryDASCHParams();
            inparam.set(RetrievalShortageInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalShortageInquiryDASCHParams.PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_DATE, txt_RetrievalStartDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_TIME, txt_RetrievalStartTime.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ShortageCheckList.clear();

            if (count == 0)
            {
                // DFKLOOK:ここから修正
                // 引当パターンをクリア
                txt_AllocatedPatternNo.setValue(null);
                txt_AllocatedPattenName.setValue(null);
                // DFKLOOK:ここまで修正

                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShortageCheckList.getNewLine();
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalShortageInquiryDASCHParams.BATCH));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalShortageInquiryDASCHParams.ORDER));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(RetrievalShortageInquiryDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_CASE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                line.setValue(KEY_LST_ALLOCATED, outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED));
                txt_AllocatedPatternNo.setValue(outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTERN_NO));
                txt_AllocatedPattenName.setValue(outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTEN_NAME));
                lst_ShortageCheckList_SetLineToolTip(line);
                _lcm_lst_ShortageCheckList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShortageCheckList.clear();
        }
        finally
        {
            // DFKLOOK finally追加
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        RetrievalShortageInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (RetrievalShortageInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_ShortageCheckList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShortageCheckList.getNewLine();
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalShortageInquiryDASCHParams.BATCH));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalShortageInquiryDASCHParams.ORDER));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(RetrievalShortageInquiryDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_CASE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                line.setValue(KEY_LST_ALLOCATED, outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED));
                txt_AllocatedPatternNo.setValue(outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTERN_NO));
                txt_AllocatedPattenName.setValue(outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTEN_NAME));
                lst_ShortageCheckList_SetLineToolTip(line);
                _lcm_lst_ShortageCheckList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShortageCheckList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_RetrievalStartDate.validate(this, true);
        txt_RetrievalStartTime.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalShortageInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalShortageInquiryDASCHParams inparam = new RetrievalShortageInquiryDASCHParams();
            inparam.set(RetrievalShortageInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalShortageInquiryDASCHParams.PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_DATE, txt_RetrievalStartDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_TIME, txt_RetrievalStartTime.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            // DFKLOOK ここから
            //inparam.set(RetrievalShortageInquiryDASCHParams.START_UNIT_KEY, "START_UNIT_KEY");
            //inparam.set(RetrievalShortageInquiryDASCHParams.REPLENISHMENT_SHORTAGE_FLAG, "REPLENISHMENT_SHORTAGE_FLAG");
            // DFKLOOK ここまで

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
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
            exporter = factory.newPrinterExporter("ShortageCheckList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageCheckListParams expparam = new ShortageCheckListParams();
                expparam.set(ShortageCheckListParams.DFK_DS_NO, outparam.get(RetrievalShortageInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ShortageCheckListParams.DFK_USER_ID, outparam.get(RetrievalShortageInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ShortageCheckListParams.DFK_USER_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ShortageCheckListParams.PLAN_DAY, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_DAY));
                expparam.set(ShortageCheckListParams.SYS_DAY, outparam.get(RetrievalShortageInquiryDASCHParams.SYS_DAY));
                expparam.set(ShortageCheckListParams.SYS_TIME, outparam.get(RetrievalShortageInquiryDASCHParams.SYS_TIME));
                expparam.set(ShortageCheckListParams.START_DAY, outparam.get(RetrievalShortageInquiryDASCHParams.START_DAY));
                expparam.set(ShortageCheckListParams.START_TIME, outparam.get(RetrievalShortageInquiryDASCHParams.START_TIM));
                expparam.set(ShortageCheckListParams.BATCH_NO, outparam.get(RetrievalShortageInquiryDASCHParams.BATCH));
                expparam.set(ShortageCheckListParams.ALLOCATE_NO,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTERN_NO));
                expparam.set(ShortageCheckListParams.ORDER_NO, outparam.get(RetrievalShortageInquiryDASCHParams.ORDER));
                expparam.set(ShortageCheckListParams.CUSTOMER_CODE,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(ShortageCheckListParams.CUSTOMER_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(ShortageCheckListParams.ITEM_CODE, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageCheckListParams.ITEM_NAME, outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageCheckListParams.PLAN_LOT_NO, outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_LOT_NO));
                expparam.set(ShortageCheckListParams.ENTERING_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.CASE_PACK));
                expparam.set(ShortageCheckListParams.PLAN_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(ShortageCheckListParams.PLAN_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ShortageCheckListParams.REP_CASE_QTY, outparam.get(RetrievalShortageInquiryDASCHParams.REP_CASE_QTY));
                expparam.set(ShortageCheckListParams.REP_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_PIECE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(ShortageCheckListParams.ALLOCATION, outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED));
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
            part11List.add(txt_RetrievalPlanDate.getStringValue(), "");
            part11List.add(txt_RetrievalStartDate.getStringValue(), txt_RetrievalStartTime.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_OrderNoTo.getStringValue(), "");
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
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_RetrievalStartDate.validate(this, true);
        txt_RetrievalStartTime.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalShortageInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalShortageInquiryDASCHParams inparam = new RetrievalShortageInquiryDASCHParams();
            inparam.set(RetrievalShortageInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalShortageInquiryDASCHParams.PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_DATE, txt_RetrievalStartDate.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.START_TIME, txt_RetrievalStartTime.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalShortageInquiryDASCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("ShortageCheckList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageCheckListParams expparam = new ShortageCheckListParams();
                expparam.set(ShortageCheckListParams.ALLOCATE_NO,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTERN_NO));
                expparam.set(ShortageCheckListParams.BATCH_NO, outparam.get(RetrievalShortageInquiryDASCHParams.BATCH));
                expparam.set(ShortageCheckListParams.ORDER_NO, outparam.get(RetrievalShortageInquiryDASCHParams.ORDER));
                expparam.set(ShortageCheckListParams.CUSTOMER_CODE,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(ShortageCheckListParams.CUSTOMER_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(ShortageCheckListParams.ITEM_CODE,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageCheckListParams.ITEM_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageCheckListParams.ENTERING_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CASE_PACK));
                expparam.set(ShortageCheckListParams.PLAN_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(ShortageCheckListParams.PLAN_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ShortageCheckListParams.REP_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_CASE_QTY));
                expparam.set(ShortageCheckListParams.REP_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_PIECE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(ShortageCheckListParams.ALLOCATION,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(count) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_RetrievalPlanDate.getStringValue(), "");
            part11List.add(txt_RetrievalStartDate.getStringValue(), txt_RetrievalStartTime.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_OrderNoTo.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());
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
        txt_RetrievalStartDate.setValue(null);
        txt_RetrievalStartTime.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_OrderNoTo.setValue(null);

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
