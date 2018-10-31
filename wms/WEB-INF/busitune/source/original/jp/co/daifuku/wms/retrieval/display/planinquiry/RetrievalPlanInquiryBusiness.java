// $Id: RetrievalPlanInquiryBusiness.java 7446 2010-03-08 04:20:52Z okayama $
package jp.co.daifuku.wms.retrieval.display.planinquiry;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PagerModel;
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
import jp.co.daifuku.bluedog.ui.control.Pager;
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
import jp.co.daifuku.wms.retrieval.dasch.RetrievalPlanInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalPlanInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.display.planinquiry.RetrievalPlanInquiry;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalPlanInqListParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7446 $, $Date:: 2010-03-08 13:20:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalPlanInquiryBusiness
        extends RetrievalPlanInquiry
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

    /** lst_RetrievalPlanList(LST_START_STATUS) */
    private static final ListCellKey KEY_LST_START_STATUS = new ListCellKey("LST_START_STATUS", new StringCellColumn(), false, false);

    /** lst_RetrievalPlanList(LST_PLAN_DATE) */
    private static final ListCellKey KEY_LST_PLAN_DATE = new ListCellKey("LST_PLAN_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalPlanList(LST_TICKET) */
    private static final ListCellKey KEY_LST_TICKET = new ListCellKey("LST_TICKET", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_LINE) */
    private static final ListCellKey KEY_LST_LINE = new ListCellKey("LST_LINE", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_SERIAL) */
    private static final ListCellKey KEY_LST_SERIAL = new ListCellKey("LST_SERIAL", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_RESULT_CASE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_CASE_QTY = new ListCellKey("LST_RESULT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_RESULT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_PIECE_QTY = new ListCellKey("LST_RESULT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_SHORTAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_CASE_QTY = new ListCellKey("LST_SHORTAGE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_SHORTAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_PIECE_QTY = new ListCellKey("LST_SHORTAGE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanList(LST_PICKING_AREA) */
    private static final ListCellKey KEY_LST_PICKING_AREA = new ListCellKey("LST_PICKING_AREA", new AreaCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_PICKING_LOCATION) */
    private static final ListCellKey KEY_LST_PICKING_LOCATION = new ListCellKey("LST_PICKING_LOCATION", new LocationCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_UPC_CODE) */
    private static final ListCellKey KEY_LST_UPC_CODE = new ListCellKey("LST_UPC_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_CASE_ITF) */
    private static final ListCellKey KEY_LST_CASE_ITF = new ListCellKey("LST_CASE_ITF", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList(LST_WORK_STATUS) */
    private static final ListCellKey KEY_LST_WORK_STATUS = new ListCellKey("LST_WORK_STATUS", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanList keys */
    private static final ListCellKey[] LST_RETRIEVALPLANLIST_KEYS = {
        KEY_LST_START_STATUS,
        KEY_LST_PLAN_DATE,
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
        KEY_LST_WORK_STATUS,
        KEY_LST_LINE,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RESULT_PIECE_QTY,
        KEY_LST_SHORTAGE_PIECE_QTY,
        KEY_LST_PICKING_LOCATION,
        KEY_LST_CASE_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_FStartStatus */
    private DefaultPullDownModel _pdm_pul_FStartStatus;

    /** PullDownModel pul_FWorkStatus */
    private DefaultPullDownModel _pdm_pul_FWorkStatus;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_RetrievalPlanList */
    private ListCellModel _lcm_lst_RetrievalPlanList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalPlanInquiryBusiness()
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

        // initialize pul_FStartStatus.
        _pdm_pul_FStartStatus = new DefaultPullDownModel(pul_FStartStatus, locale, ui);

        // initialize pul_FWorkStatus.
        _pdm_pul_FWorkStatus = new DefaultPullDownModel(pul_FWorkStatus, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_RetrievalPlanList.
        _lcm_lst_RetrievalPlanList = new ListCellModel(lst_RetrievalPlanList, LST_RETRIEVALPLANLIST_KEYS, locale);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_PLAN_DATE, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_TICKET, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_LINE, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_SERIAL, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_BATCH, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_ORDER, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_RESULT_CASE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_RESULT_PIECE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_SHORTAGE_CASE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_SHORTAGE_PIECE_QTY, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_PICKING_AREA, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_PICKING_LOCATION, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_UPC_CODE, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_CASE_ITF, true);
        _lcm_lst_RetrievalPlanList.setToolTipVisible(KEY_LST_WORK_STATUS, true);
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

            // load pul_FStartStatus.
            _pdm_pul_FStartStatus.init(conn);

            // load pul_FWorkStatus.
            _pdm_pul_FWorkStatus.init(conn);
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
    private void lst_RetrievalPlanList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_LST_START_STATUS);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0183", KEY_LST_PLAN_CASE_QTY);
        line.addToolTip("LBL-W0184", KEY_LST_PLAN_PIECE_QTY);
        line.addToolTip("LBL-W0101", KEY_LST_RESULT_CASE_QTY);
        line.addToolTip("LBL-W0102", KEY_LST_RESULT_PIECE_QTY);
        line.addToolTip("LBL-W0116", KEY_LST_PICKING_AREA);
        line.addToolTip("LBL-W0124", KEY_LST_PICKING_LOCATION);
        line.addToolTip("LBL-W0002", KEY_LST_UPC_CODE);
        line.addToolTip("LBL-W0017", KEY_LST_CASE_ITF);
        line.addToolTip("LBL-W0086", KEY_LST_WORK_STATUS);
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
        _pdm_pul_FStartStatus.setSelectedValue(null);
        _pdm_pul_FWorkStatus.setSelectedValue(null);
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
        RetrievalPlanInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new RetrievalPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            RetrievalPlanInquiryDASCHParams inparam = new RetrievalPlanInquiryDASCHParams();
            inparam.set(RetrievalPlanInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(RetrievalPlanInquiryDASCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.SLIP_NO, txt_SlipNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_START_STATUS, _pdm_pul_FStartStatus.getSelectedValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_WORK_STATUS, _pdm_pul_FWorkStatus.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RetrievalPlanList.clear();

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
            _lcm_lst_RetrievalPlanList.clear();
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
        RetrievalPlanInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (RetrievalPlanInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RetrievalPlanList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanList.getNewLine();
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_TICKET, outparam.get(RetrievalPlanInquiryDASCHParams.TICKET));
                line.setValue(KEY_LST_LINE, outparam.get(RetrievalPlanInquiryDASCHParams.LINE));
                line.setValue(KEY_LST_SERIAL, outparam.get(RetrievalPlanInquiryDASCHParams.SERIAL));
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalPlanInquiryDASCHParams.BATCH));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalPlanInquiryDASCHParams.ORDER));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(RetrievalPlanInquiryDASCHParams.LOT));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(RetrievalPlanInquiryDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                line.setValue(KEY_LST_PICKING_AREA, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_AREA));
                line.setValue(KEY_LST_PICKING_LOCATION, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_LOCATION));
                line.setValue(KEY_LST_UPC_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.UPC_CODE));
                line.setValue(KEY_LST_CASE_ITF, outparam.get(RetrievalPlanInquiryDASCHParams.CASE_ITF));
                line.setValue(KEY_LST_WORK_STATUS, outparam.get(RetrievalPlanInquiryDASCHParams.WORK_STATUS));
                line.setValue(KEY_LST_START_STATUS, outparam.get(RetrievalPlanInquiryDASCHParams.START_STATUS));
                lst_RetrievalPlanList_SetLineToolTip(line);
                _lcm_lst_RetrievalPlanList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RetrievalPlanList.clear();
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
        txt_SlipNo.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_CustomerCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalPlanInquiryDASCHParams inparam = new RetrievalPlanInquiryDASCHParams();
            inparam.set(RetrievalPlanInquiryDASCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.SLIP_NO, txt_SlipNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_START_STATUS, _pdm_pul_FStartStatus.getSelectedValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_WORK_STATUS, _pdm_pul_FWorkStatus.getSelectedValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            exporter = factory.newPrinterExporter("RetrievalPlanInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                RetrievalPlanInqListParams expparam = new RetrievalPlanInqListParams();
                expparam.set(RetrievalPlanInqListParams.DFK_DS_NO, outparam.get(RetrievalPlanInquiryDASCHParams.DFK_DS_NO));
                expparam.set(RetrievalPlanInqListParams.DFK_USER_ID, outparam.get(RetrievalPlanInquiryDASCHParams.DFK_USER_ID));
                expparam.set(RetrievalPlanInqListParams.DFK_USER_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(RetrievalPlanInqListParams.SYS_DAY, outparam.get(RetrievalPlanInquiryDASCHParams.SYS_DAY));
                expparam.set(RetrievalPlanInqListParams.SYS_TIME, outparam.get(RetrievalPlanInquiryDASCHParams.SYS_TIME));
                expparam.set(RetrievalPlanInqListParams.PLAN_DAY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_DATE));
                expparam.set(RetrievalPlanInqListParams.RECEIVE_TICKET_NO, outparam.get(RetrievalPlanInquiryDASCHParams.TICKET));
                expparam.set(RetrievalPlanInqListParams.RECEIVE_LINE_NO, outparam.get(RetrievalPlanInquiryDASCHParams.LINE));
                expparam.set(RetrievalPlanInqListParams.BRANCH_NO, outparam.get(RetrievalPlanInquiryDASCHParams.SERIAL));
                expparam.set(RetrievalPlanInqListParams.BATCH_NO, outparam.get(RetrievalPlanInquiryDASCHParams.BATCH));
                expparam.set(RetrievalPlanInqListParams.ORDER_NO, outparam.get(RetrievalPlanInquiryDASCHParams.ORDER));
                expparam.set(RetrievalPlanInqListParams.CUSTOMER_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(RetrievalPlanInqListParams.ITEM_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_CODE));
                expparam.set(RetrievalPlanInqListParams.ITEM_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_NAME));
                expparam.set(RetrievalPlanInqListParams.ENTERING_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.CASE_PACK));
                expparam.set(RetrievalPlanInqListParams.PLAN_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.PLAN_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.RESULT_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.RESULT_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.SHORTAGE_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.SHORTAGE_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.AREA_NO, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_AREA));
                expparam.set(RetrievalPlanInqListParams.PLAN_LOCATION_NO, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_LOCATION));
                expparam.set(RetrievalPlanInqListParams.PLAN_LOT_NO, outparam.get(RetrievalPlanInquiryDASCHParams.LOT));
                expparam.set(RetrievalPlanInqListParams.STATUS_FLAG, outparam.get(RetrievalPlanInquiryDASCHParams.WORK_STATUS));
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
            part11List.add(txt_SlipNo.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_CustomerCode.getStringValue(), "");
            part11List.add(_pdm_pul_FStartStatus.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_FWorkStatus.getSelectedStringValue(), "");
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
        txt_SlipNo.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_CustomerCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalPlanInquiryDASCHParams inparam = new RetrievalPlanInquiryDASCHParams();
            inparam.set(RetrievalPlanInquiryDASCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.SLIP_NO, txt_SlipNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.CUSTOMER_CODE_NO, txt_CustomerCode.getValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_START_STATUS, _pdm_pul_FStartStatus.getSelectedValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.F_WORK_STATUS, _pdm_pul_FWorkStatus.getSelectedValue());
            inparam.set(RetrievalPlanInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            exporter = factory.newExcelExporter("RetrievalPlanInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                RetrievalPlanInqListParams expparam = new RetrievalPlanInqListParams();
                expparam.set(RetrievalPlanInqListParams.PLAN_DAY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_DATE));
                expparam.set(RetrievalPlanInqListParams.RECEIVE_TICKET_NO, outparam.get(RetrievalPlanInquiryDASCHParams.TICKET));
                expparam.set(RetrievalPlanInqListParams.RECEIVE_LINE_NO, outparam.get(RetrievalPlanInquiryDASCHParams.LINE));
                expparam.set(RetrievalPlanInqListParams.BRANCH_NO, outparam.get(RetrievalPlanInquiryDASCHParams.SERIAL));
                expparam.set(RetrievalPlanInqListParams.BATCH_NO, outparam.get(RetrievalPlanInquiryDASCHParams.BATCH));
                expparam.set(RetrievalPlanInqListParams.ORDER_NO, outparam.get(RetrievalPlanInquiryDASCHParams.ORDER));
                expparam.set(RetrievalPlanInqListParams.CUSTOMER_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(RetrievalPlanInqListParams.CUSTOMER_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(RetrievalPlanInqListParams.ITEM_CODE, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_CODE));
                expparam.set(RetrievalPlanInqListParams.ITEM_NAME, outparam.get(RetrievalPlanInquiryDASCHParams.ITEM_NAME));
                expparam.set(RetrievalPlanInqListParams.ENTERING_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.CASE_PACK));
                expparam.set(RetrievalPlanInqListParams.PLAN_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.PLAN_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.RESULT_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.RESULT_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.RESULT_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.SHORTAGE_CASE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(RetrievalPlanInqListParams.SHORTAGE_PIECE_QTY, outparam.get(RetrievalPlanInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(RetrievalPlanInqListParams.AREA_NO, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_AREA));
                expparam.set(RetrievalPlanInqListParams.PLAN_LOCATION_NO, outparam.get(RetrievalPlanInquiryDASCHParams.PICKING_LOCATION));
                expparam.set(RetrievalPlanInqListParams.PLAN_LOT_NO, outparam.get(RetrievalPlanInquiryDASCHParams.LOT));
                expparam.set(RetrievalPlanInqListParams.JAN, outparam.get(RetrievalPlanInquiryDASCHParams.UPC_CODE));
                expparam.set(RetrievalPlanInqListParams.ITF, outparam.get(RetrievalPlanInquiryDASCHParams.CASE_ITF));
                expparam.set(RetrievalPlanInqListParams.STATUS_FLAG, outparam.get(RetrievalPlanInquiryDASCHParams.WORK_STATUS));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_RetrievalPlanDate.getStringValue(), "");
            part11List.add(txt_SlipNo.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_OrderNo.getStringValue(), "");
            part11List.add(txt_CustomerCode.getStringValue(), "");
            part11List.add(_pdm_pul_FStartStatus.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_FWorkStatus.getSelectedStringValue(), "");
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
        txt_RetrievalPlanDate.setValue(null);
        txt_SlipNo.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerCode.setValue(null);
        _pdm_pul_FStartStatus.setSelectedValue(null);
        _pdm_pul_FWorkStatus.setSelectedValue(null);
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
