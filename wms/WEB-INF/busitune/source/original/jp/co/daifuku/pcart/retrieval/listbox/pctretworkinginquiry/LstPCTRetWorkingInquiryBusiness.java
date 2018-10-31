// $Id: LstPCTRetWorkingInquiryBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.listbox.pctretworkinginquiry;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetWorkingInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetWorkingInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetWorkingInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretworkinginquiry.LstPCTRetWorkingInquiry;
import jp.co.daifuku.pcart.retrieval.listbox.pctretworkinginquiry.LstPCTRetWorkingInquiryParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5329 $, $Date:: 2009-10-29 14:35:28 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstPCTRetWorkingInquiryBusiness
        extends LstPCTRetWorkingInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_AREA_NO = "_KEY_AREA_NO";

    /** key */
    private static final String _KEY_BATCH_NO_PCT = "_KEY_BATCH_NO_PCT";

    /** key */
    private static final String _KEY_BATCH_SEQ_NO = "_KEY_BATCH_SEQ_NO";

    /** key */
    private static final String _KEY_CONSIGNOR_CODE = "_KEY_CONSIGNOR_CODE";

    /** key */
    private static final String _KEY_CUSTOMER_CODE = "_KEY_CUSTOMER_CODE";

    /** key */
    private static final String _KEY_ITEM_CODE = "_KEY_ITEM_CODE";

    /** key */
    private static final String _KEY_ORDER_NO = "_KEY_ORDER_NO";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_PLAN_DAY = "_KEY_PLAN_DAY";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** key */
    private static final String _KEY_REGULAR_CUSTOMER_CODE = "_KEY_REGULAR_CUSTOMER_CODE";

    /** key */
    private static final String _KEY_WORK_STATUS = "_KEY_WORK_STATUS";

    /** lst_SearchCondition(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 = new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 = new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 = new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 = new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_5) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_5 = new ListCellKey("LST_SEARCH_CONDITION_5", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_6) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_6 = new ListCellKey("LST_SEARCH_CONDITION_6", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME = new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_CODE = new ListCellKey("HIDDEN_REGULAR_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE = new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN = new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF = new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_TERMINAL_NO) */
    private static final ListCellKey KEY_HIDDEN_TERMINAL_NO = new ListCellKey("HIDDEN_TERMINAL_NO", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTRetWorkingRequiry(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO = new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_NAME = new ListCellKey("LST_REGULAR_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY = new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY = new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_JOB_STATUS) */
    private static final ListCellKey KEY_LST_JOB_STATUS = new ListCellKey("LST_JOB_STATUS", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO = new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION_1,
        KEY_LST_SEARCH_CONDITION_2,
        KEY_LST_SEARCH_CONDITION_3,
        KEY_LST_SEARCH_CONDITION_4,
        KEY_LST_SEARCH_CONDITION_5,
        KEY_LST_SEARCH_CONDITION_6,
    };

    /** lst_PCTRetWorkingRequiry keys */
    private static final ListCellKey[] LST_PCTRETWORKINGREQUIRY_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_CONSIGNOR_NAME,
        KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
        KEY_HIDDEN_CUSTOMER_CODE,
        KEY_HIDDEN_JAN,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_BUNDLE_ITF,
        KEY_HIDDEN_TERMINAL_NO,
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_BATCH_SEQ_NO,
        KEY_LST_REGULAR_CUSTOMER_NAME,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ORDER_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_QTY,
        KEY_LST_PLAN_QTY,
        KEY_LST_RESULT_QTY,
        KEY_LST_USER_NAME,
        KEY_LST_JOB_STATUS,
        KEY_LST_AREA_NO,
        KEY_LST_ZONE_NO,
        KEY_LST_LOCATION_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchCondition */
    private ListCellModel _lcm_lst_SearchCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTRetWorkingRequiry */
    private ListCellModel _lcm_lst_PCTRetWorkingRequiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTRetWorkingInquiryBusiness()
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
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Preview_Click_Process();
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
        btn_Print_Click_Process();
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
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_5, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_6, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale, PagerModel.LIMIT_UNLIMITED);

        // initialize lst_PCTRetWorkingRequiry.
        _lcm_lst_PCTRetWorkingRequiry = new ListCellModel(lst_PCTRetWorkingRequiry, LST_PCTRETWORKINGREQUIRY_KEYS, locale);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_REGULAR_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_JOB_STATUS, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTRetWorkingRequiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);

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
    private void lst_SearchCondition_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTRetWorkingRequiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0051", KEY_LST_REGULAR_CUSTOMER_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-P0053", KEY_LST_LOT_QTY);
        line.addToolTip("LBL-W0328", KEY_LST_PLAN_QTY);
        line.addToolTip("LBL-P0054", KEY_LST_RESULT_QTY);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W0086", KEY_LST_JOB_STATUS);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO);
        line.addToolTip("LBL-P0044", KEY_LST_ZONE_NO);
        line.addToolTip("LBL-P0094", KEY_LST_LOCATION_NO);
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
        if (eventSource.equals("page_Load"))
        {
            // process call.
            page_Load_SetList(false);
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
        setFocus(pgr_U);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // save a pager event source.
        viewState.setString(_KEY_PAGERSOURCE, "page_Load");

        // criteria to retain ViewState.
        LstPCTRetWorkingInquiryParams requestParam = new LstPCTRetWorkingInquiryParams(request);
        viewState.setObject(_KEY_AREA_NO, requestParam.get(LstPCTRetWorkingInquiryParams.AREA_NO));
        viewState.setObject(_KEY_BATCH_NO_PCT, requestParam.get(LstPCTRetWorkingInquiryParams.BATCH_NO_PCT));
        viewState.setObject(_KEY_BATCH_SEQ_NO, requestParam.get(LstPCTRetWorkingInquiryParams.BATCH_SEQ_NO));
        viewState.setObject(_KEY_CONSIGNOR_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.CONSIGNOR_CODE));
        viewState.setObject(_KEY_CUSTOMER_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.CUSTOMER_CODE));
        viewState.setObject(_KEY_ITEM_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.ITEM_CODE));
        viewState.setObject(_KEY_ORDER_NO, requestParam.get(LstPCTRetWorkingInquiryParams.ORDER_NO));
        viewState.setObject(_KEY_PLAN_DAY, requestParam.get(LstPCTRetWorkingInquiryParams.PLAN_DAY));
        viewState.setObject(_KEY_REGULAR_CUSTOMER_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.REGULAR_CUSTOMER_CODE));
        viewState.setObject(_KEY_WORK_STATUS, requestParam.get(LstPCTRetWorkingInquiryParams.WORK_STATUS));

        // set list.
        _pager.clear();
        page_Load_SetList(true);
    }

    /**
     *
     * @param isFirst boolean
     * @throws Exception
     */
    private void page_Load_SetList(boolean isFirst)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTRetWorkingInquiryDASCH dasch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTRetWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTRetWorkingInquiryDASCHParams inparam = new LstPCTRetWorkingInquiryDASCHParams();
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.AREA_NO, viewState.getObject(_KEY_AREA_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.BATCH_NO_PCT, viewState.getObject(_KEY_BATCH_NO_PCT));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO, viewState.getObject(_KEY_BATCH_SEQ_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE, viewState.getObject(_KEY_CONSIGNOR_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE, viewState.getObject(_KEY_CUSTOMER_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.ITEM_CODE, viewState.getObject(_KEY_ITEM_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.ORDER_NO, viewState.getObject(_KEY_ORDER_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.PLAN_DAY, viewState.getObject(_KEY_PLAN_DAY));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE, viewState.getObject(_KEY_REGULAR_CUSTOMER_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.WORK_STATUS, viewState.getObject(_KEY_WORK_STATUS));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTRetWorkingRequiry.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (isFirst && count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else if (isFirst)
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.JOB_STATUS));
                line.setValue(KEY_LST_JOB_STATUS, outparam.get(LstPCTRetWorkingInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ZONE_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.LOCATION_NO));
                lst_PCTRetWorkingRequiry_SetLineToolTip(line);
                _lcm_lst_PCTRetWorkingRequiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTRetWorkingRequiry.clear();
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("PctRetWorkingInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetWorkingRequiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.get(i);
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, "");
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, "");
                expparam.set(PctRetWorkingInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetWorkingInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetWorkingInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetWorkingInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetWorkingInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetWorkingInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetWorkingInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetWorkingInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetWorkingInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetWorkingInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetWorkingInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                downloadFile = exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
            }

            // redirect.
            previewPDF(downloadFile.getPath());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
    private void btn_Print_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctRetWorkingInqList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetWorkingRequiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.get(i);
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, "");
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, "");
                expparam.set(PctRetWorkingInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetWorkingInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetWorkingInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetWorkingInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetWorkingInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetWorkingInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetWorkingInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetWorkingInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetWorkingInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetWorkingInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetWorkingInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("PctRetWorkingInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetWorkingRequiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.get(i);
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, "");
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, "");
                expparam.set(PctRetWorkingInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetWorkingInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetWorkingInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetWorkingInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetWorkingInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetWorkingInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetWorkingInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetWorkingInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetWorkingInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetWorkingInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetWorkingInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

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
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_D_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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
