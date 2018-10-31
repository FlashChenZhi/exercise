// $Id: RetrievalResultInquiryBusiness.java 7446 2010-03-08 04:20:52Z okayama $
package jp.co.daifuku.wms.retrieval.display.resultinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
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
import jp.co.daifuku.wms.retrieval.dasch.RetrievalResultInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalResultInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.display.resultinquiry.RetrievalResultInquiry;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalResultInqListParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7446 $, $Date:: 2010-03-08 13:20:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalResultInquiryBusiness
        extends RetrievalResultInquiry
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalResultList(LST_PICKING_DATE) */
    private static final ListCellKey KEY_LST_PICKING_DATE = new ListCellKey("LST_PICKING_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalResultList(LST_PLAN_DATE) */
    private static final ListCellKey KEY_LST_PLAN_DATE = new ListCellKey("LST_PLAN_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalResultList(LST_TICKET) */
    private static final ListCellKey KEY_LST_TICKET = new ListCellKey("LST_TICKET", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_LINE) */
    private static final ListCellKey KEY_LST_LINE = new ListCellKey("LST_LINE", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_SERIAL) */
    private static final ListCellKey KEY_LST_SERIAL = new ListCellKey("LST_SERIAL", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_RESULT_CASE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_CASE_QTY = new ListCellKey("LST_RESULT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_RESULT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_PIECE_QTY = new ListCellKey("LST_RESULT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_SHORTAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_CASE_QTY = new ListCellKey("LST_SHORTAGE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_SHORTAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_PIECE_QTY = new ListCellKey("LST_SHORTAGE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultList(LST_PICKING_AREA) */
    private static final ListCellKey KEY_LST_PICKING_AREA = new ListCellKey("LST_PICKING_AREA", new AreaCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_PICKING_LOCATION) */
    private static final ListCellKey KEY_LST_PICKING_LOCATION = new ListCellKey("LST_PICKING_LOCATION", new LocationCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_UPC_CODE) */
    private static final ListCellKey KEY_LST_UPC_CODE = new ListCellKey("LST_UPC_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_CASE_ITF) */
    private static final ListCellKey KEY_LST_CASE_ITF = new ListCellKey("LST_CASE_ITF", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList(LST_WORK_DATE) */
    private static final ListCellKey KEY_LST_WORK_DATE = new ListCellKey("LST_WORK_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalResultList(LST_WORK_TIME) */
    private static final ListCellKey KEY_LST_WORK_TIME = new ListCellKey("LST_WORK_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_RetrievalResultList(LST_TERMINAL) */
    private static final ListCellKey KEY_LST_TERMINAL = new ListCellKey("LST_TERMINAL", new StringCellColumn(), true, false);

    /** lst_RetrievalResultList keys */
    private static final ListCellKey[] LST_RETRIEVALRESULTLIST_KEYS = {
        KEY_LST_PICKING_DATE,
        KEY_LST_TICKET,
        KEY_LST_SERIAL,
        KEY_LST_BATCH,
        KEY_LST_ORDER,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RESULT_CASE_QTY,
        KEY_LST_SHORTAGE_CASE_QTY,
        KEY_LST_PICKING_AREA,
        KEY_LST_UPC_CODE,
        KEY_LST_USER_NAME,
        KEY_LST_WORK_DATE,
        KEY_LST_TERMINAL,
        KEY_LST_PLAN_DATE,
        KEY_LST_LINE,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RESULT_PIECE_QTY,
        KEY_LST_SHORTAGE_PIECE_QTY,
        KEY_LST_PICKING_LOCATION,
        KEY_LST_CASE_ITF,
        KEY_LST_WORK_TIME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RetrievalArea */
    private WmsAreaPullDownModel _pdm_pul_RetrievalArea;

    /** RadioButtonGroupModel GroupCondition */
    private RadioButtonGroup _grp_GroupCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_RetrievalResultList */
    private ListCellModel _lcm_lst_RetrievalResultList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalResultInquiryBusiness()
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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

        // initialize pul_RetrievalArea.
        _pdm_pul_RetrievalArea = new WmsAreaPullDownModel(pul_RetrievalArea, locale, ui);

        // initialize GroupCondition.
        _grp_GroupCondition = new RadioButtonGroup(new RadioButton[]{rdo_GroupCondition_DetailDispl, rdo_GroupCondition_PlannedUnit}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_RetrievalResultList.
        _lcm_lst_RetrievalResultList = new ListCellModel(lst_RetrievalResultList, LST_RETRIEVALRESULTLIST_KEYS, locale);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PICKING_DATE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PLAN_DATE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_TICKET, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_LINE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_SERIAL, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_BATCH, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_ORDER, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_RESULT_CASE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_RESULT_PIECE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_SHORTAGE_CASE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_SHORTAGE_PIECE_QTY, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PICKING_AREA, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_PICKING_LOCATION, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_UPC_CODE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_CASE_ITF, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_WORK_DATE, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_WORK_TIME, true);
        _lcm_lst_RetrievalResultList.setToolTipVisible(KEY_LST_TERMINAL, true);
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

            // load pul_RetrievalArea.
            _pdm_pul_RetrievalArea.init(conn, AreaType.NOT_MOVING, StationType.ALL, "", true);
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
        // dispose DASCH.
        disposeDasch();
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
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RetrievalResultList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0019", KEY_LST_CASE_PACK);
        line.addToolTip("LBL-W0183", KEY_LST_PLAN_CASE_QTY);
        line.addToolTip("LBL-W0184", KEY_LST_PLAN_PIECE_QTY);
        line.addToolTip("LBL-W0101", KEY_LST_RESULT_CASE_QTY);
        line.addToolTip("LBL-W0102", KEY_LST_RESULT_PIECE_QTY);
        line.addToolTip("LBL-W0063", KEY_LST_SHORTAGE_CASE_QTY);
        line.addToolTip("LBL-W0064", KEY_LST_SHORTAGE_PIECE_QTY);
        line.addToolTip("LBL-W0116", KEY_LST_PICKING_AREA);
        line.addToolTip("LBL-W0124", KEY_LST_PICKING_LOCATION);
        line.addToolTip("LBL-W0002", KEY_LST_UPC_CODE);
        line.addToolTip("LBL-W0017", KEY_LST_CASE_ITF);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W0164", KEY_LST_WORK_DATE);
        line.addToolTip("LBL-W0490", KEY_LST_WORK_TIME);
        line.addToolTip("LBL-W0142", KEY_LST_TERMINAL);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_RetrievalDateFrom);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_GroupCondition_DetailDispl.setChecked(true);
        _pager.clear();
        _pager.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalResultInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new RetrievalResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            RetrievalResultInquiryDASCHParams inparam = new RetrievalResultInquiryDASCHParams();
            inparam.set(RetrievalResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_FROM, txt_RetrievalDateFrom.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_TO, txt_RetrievalDateTo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.SLIP_NUMBER, txt_SlipNumber.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(RetrievalResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RetrievalResultList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RetrievalResultList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        RetrievalResultInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (RetrievalResultInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RetrievalResultList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalResultList.getNewLine();
                line.setValue(KEY_LST_PICKING_DATE, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_DATE));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_TICKET, outparam.get(RetrievalResultInquiryDASCHParams.TICKET));
                line.setValue(KEY_LST_LINE, outparam.get(RetrievalResultInquiryDASCHParams.LINE));
                line.setValue(KEY_LST_SERIAL, outparam.get(RetrievalResultInquiryDASCHParams.SERIAL));
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalResultInquiryDASCHParams.BATCH));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalResultInquiryDASCHParams.ORDER));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalResultInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(RetrievalResultInquiryDASCHParams.LOT));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(RetrievalResultInquiryDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                line.setValue(KEY_LST_PICKING_AREA, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_AREA));
                line.setValue(KEY_LST_PICKING_LOCATION, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_LOCATION));
                line.setValue(KEY_LST_UPC_CODE, outparam.get(RetrievalResultInquiryDASCHParams.UPC_CODE));
                line.setValue(KEY_LST_CASE_ITF, outparam.get(RetrievalResultInquiryDASCHParams.CASE_ITF));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(RetrievalResultInquiryDASCHParams.WORK_DATE));
                line.setValue(KEY_LST_WORK_TIME, outparam.get(RetrievalResultInquiryDASCHParams.WORK_TIME));
                line.setValue(KEY_LST_TERMINAL, outparam.get(RetrievalResultInquiryDASCHParams.TERMINAL));
                lst_RetrievalResultList_SetLineToolTip(line);
                _lcm_lst_RetrievalResultList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RetrievalResultList.clear();
            disposeDasch();
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
        txt_SlipNumber.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_CustomerCode.validate(this, false);
        pul_RetrievalArea.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalResultInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalResultInquiryDASCHParams inparam = new RetrievalResultInquiryDASCHParams();
            inparam.set(RetrievalResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_FROM, txt_RetrievalDateFrom.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_TO, txt_RetrievalDateTo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.SLIP_NUMBER, txt_SlipNumber.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(RetrievalResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

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
            exporter = factory.newPrinterExporter("RetrievalResultInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                RetrievalResultInqListParams expparam = new RetrievalResultInqListParams();
                expparam.set(RetrievalResultInqListParams.DFK_DS_NO, outparam.get(RetrievalResultInquiryDASCHParams.DFK_DS_NO));
                expparam.set(RetrievalResultInqListParams.DFK_USER_ID, outparam.get(RetrievalResultInquiryDASCHParams.DFK_USER_ID));
                expparam.set(RetrievalResultInqListParams.DFK_USER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(RetrievalResultInqListParams.SYS_DAY, outparam.get(RetrievalResultInquiryDASCHParams.SYS_DAY));
                expparam.set(RetrievalResultInqListParams.SYS_TIME, outparam.get(RetrievalResultInquiryDASCHParams.SYS_TIME));
                expparam.set(RetrievalResultInqListParams.WORK_DAY, outparam.get(RetrievalResultInquiryDASCHParams.WORK_DAY));
                expparam.set(RetrievalResultInqListParams.PLAN_DAY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_DATE));
                expparam.set(RetrievalResultInqListParams.SHIP_TICKET_NO, outparam.get(RetrievalResultInquiryDASCHParams.TICKET));
                expparam.set(RetrievalResultInqListParams.SHIP_LINE_NO, outparam.get(RetrievalResultInquiryDASCHParams.LINE));
                expparam.set(RetrievalResultInqListParams.BRANCH_NO, outparam.get(RetrievalResultInquiryDASCHParams.SERIAL));
                expparam.set(RetrievalResultInqListParams.BATCH_NO, outparam.get(RetrievalResultInquiryDASCHParams.BATCH));
                expparam.set(RetrievalResultInqListParams.ORDER_NO, outparam.get(RetrievalResultInquiryDASCHParams.ORDER));
                expparam.set(RetrievalResultInqListParams.CUSTOMER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(RetrievalResultInqListParams.ITEM_CODE, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_CODE));
                expparam.set(RetrievalResultInqListParams.ITEM_NAME, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_NAME));
                expparam.set(RetrievalResultInqListParams.ENTERING_QTY, outparam.get(RetrievalResultInquiryDASCHParams.CASE_PACK));
                expparam.set(RetrievalResultInqListParams.PLAN_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.PLAN_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.RESULT_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.RESULT_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.SHORTAGE_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.SHORTAGE_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.AREA_NO, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_LOCATION));
                expparam.set(RetrievalResultInqListParams.LOCATION_NO, outparam.get(RetrievalResultInquiryDASCHParams.LOCATION_NO));
                expparam.set(RetrievalResultInqListParams.LOT_NO, outparam.get(RetrievalResultInquiryDASCHParams.LOT));
                expparam.set(RetrievalResultInqListParams.USER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.USER_NAME));
                expparam.set(RetrievalResultInqListParams.TERMINAL_NO, outparam.get(RetrievalResultInquiryDASCHParams.TERMINAL));
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
            part11List.add(txt_RetrievalDateFrom.getStringValue(), "");
            part11List.add(txt_RetrievalDateTo.getStringValue(), "");
            part11List.add(txt_SlipNumber.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_CustomerCode.getStringValue(), "");
            part11List.add(_pdm_pul_RetrievalArea.getSelectedStringValue(), "");

            if (rdo_GroupCondition_DetailDispl.getChecked())
            {
                part11List.add("2", "");
            }
            else
            {
                part11List.add("1", "");
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
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_SlipNumber.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_CustomerCode.validate(this, false);
        pul_RetrievalArea.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalResultInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalResultInquiryDASCHParams inparam = new RetrievalResultInquiryDASCHParams();
            inparam.set(RetrievalResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_FROM, txt_RetrievalDateFrom.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_DATE_TO, txt_RetrievalDateTo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.SLIP_NUMBER, txt_SlipNumber.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalResultInquiryDASCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(RetrievalResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

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
            exporter = factory.newExcelExporter("RetrievalResultInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                RetrievalResultInqListParams expparam = new RetrievalResultInqListParams();
                expparam.set(RetrievalResultInqListParams.WORK_DAY, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_DATE));
                expparam.set(RetrievalResultInqListParams.PLAN_DAY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_DATE));
                expparam.set(RetrievalResultInqListParams.SHIP_TICKET_NO, outparam.get(RetrievalResultInquiryDASCHParams.TICKET));
                expparam.set(RetrievalResultInqListParams.SHIP_LINE_NO, outparam.get(RetrievalResultInquiryDASCHParams.LINE));
                expparam.set(RetrievalResultInqListParams.BRANCH_NO, outparam.get(RetrievalResultInquiryDASCHParams.SERIAL));
                expparam.set(RetrievalResultInqListParams.BATCH_NO, outparam.get(RetrievalResultInquiryDASCHParams.BATCH));
                expparam.set(RetrievalResultInqListParams.ORDER_NO, outparam.get(RetrievalResultInquiryDASCHParams.ORDER));
                expparam.set(RetrievalResultInqListParams.CUSTOMER_CODE, outparam.get(RetrievalResultInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(RetrievalResultInqListParams.CUSTOMER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(RetrievalResultInqListParams.ITEM_CODE, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_CODE));
                expparam.set(RetrievalResultInqListParams.ITEM_NAME, outparam.get(RetrievalResultInquiryDASCHParams.ITEM_NAME));
                expparam.set(RetrievalResultInqListParams.ENTERING_QTY, outparam.get(RetrievalResultInquiryDASCHParams.CASE_PACK));
                expparam.set(RetrievalResultInqListParams.PLAN_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.PLAN_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.RESULT_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.RESULT_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.RESULT_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.SHORTAGE_CASE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(RetrievalResultInqListParams.SHORTAGE_PIECE_QTY, outparam.get(RetrievalResultInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(RetrievalResultInqListParams.AREA_NO, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_AREA));
                expparam.set(RetrievalResultInqListParams.LOCATION_NO, outparam.get(RetrievalResultInquiryDASCHParams.PICKING_LOCATION));
                expparam.set(RetrievalResultInqListParams.LOT_NO, outparam.get(RetrievalResultInquiryDASCHParams.LOT));
                expparam.set(RetrievalResultInqListParams.JAN, outparam.get(RetrievalResultInquiryDASCHParams.UPC_CODE));
                expparam.set(RetrievalResultInqListParams.ITF, outparam.get(RetrievalResultInquiryDASCHParams.CASE_ITF));
                expparam.set(RetrievalResultInqListParams.USER_NAME, outparam.get(RetrievalResultInquiryDASCHParams.USER_NAME));
                expparam.set(RetrievalResultInqListParams.REGIST_DAY, outparam.get(RetrievalResultInquiryDASCHParams.WORK_DATE));
                expparam.set(RetrievalResultInqListParams.REGIST_TIME, outparam.get(RetrievalResultInquiryDASCHParams.WORK_TIME));
                expparam.set(RetrievalResultInqListParams.TERMINAL_NO, outparam.get(RetrievalResultInquiryDASCHParams.TERMINAL));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_RetrievalDateFrom.getStringValue(), "");
            part11List.add(txt_RetrievalDateTo.getStringValue(), "");
            part11List.add(txt_SlipNumber.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_CustomerCode.getStringValue(), "");
            part11List.add(_pdm_pul_RetrievalArea.getSelectedStringValue(), "");

            if (rdo_GroupCondition_DetailDispl.getChecked())
            {
                part11List.add("2", "");
            }
            else
            {
                part11List.add("1", "");
            }

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
        txt_RetrievalDateFrom.setValue(null);
        txt_RetrievalDateTo.setValue(null);
        txt_SlipNumber.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerCode.setValue(null);
        _pdm_pul_RetrievalArea.setSelectedValue(null);
        rdo_GroupCondition_DetailDispl.setChecked(true);
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
