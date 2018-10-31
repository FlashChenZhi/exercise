// $Id: LstPCTRetWorkingInquiryBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.retrieval.listbox.pctretworkinginquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
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
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetWorkingInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetWorkingInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetWorkingInqListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 作業状況照会一覧の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
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
    private static final String _KEY_BATCH_NO = "_KEY_BATCH_NO";

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
    private static final String _KEY_PLAN_DAY = "_KEY_PLAN_DAY";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** key */
    private static final String _KEY_REGULAR_CUSTOMER_CODE = "_KEY_REGULAR_CUSTOMER_CODE";

    /** key */
    private static final String _KEY_JOB_STATUS = "_KEY_JOB_STATUS";

    /** key */
    private static final String _KEY_USER_ID = "_KEY_USER_ID";

    /** lst_SearchCondition(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 =
            new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 =
            new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 =
            new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 =
            new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_5) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_5 =
            new ListCellKey("LST_SEARCH_CONDITION_5", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_6) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_6 =
            new ListCellKey("LST_SEARCH_CONDITION_6", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME =
            new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_REGULAR_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN =
            new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF =
            new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF =
            new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(HIDDEN_TERMINAL_NO) */
    private static final ListCellKey KEY_HIDDEN_TERMINAL_NO =
            new ListCellKey("HIDDEN_TERMINAL_NO", new StringCellColumn(), false, false);

    /** lst_PCTRetWorkingRequiry(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY =
            new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTRetWorkingRequiry(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO =
            new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_NAME =
            new ListCellKey("LST_REGULAR_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY =
            new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY =
            new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetWorkingRequiry(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_JOB_STATUS) */
    private static final ListCellKey KEY_LST_JOB_STATUS =
            new ListCellKey("LST_JOB_STATUS", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetWorkingRequiry(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_SearchCondition kyes */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
            KEY_LST_SEARCH_CONDITION_1,
            KEY_LST_SEARCH_CONDITION_2,
            KEY_LST_SEARCH_CONDITION_3,
            KEY_LST_SEARCH_CONDITION_4,
            KEY_LST_SEARCH_CONDITION_5,
            KEY_LST_SEARCH_CONDITION_6,
    };

    /** lst_PCTRetWorkingRequiry kyes */
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
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
        page_Load_SetList(false);
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
    // DFKLOOK:ここから修正
    /**
     * ボタン制御を行います。
     */
    protected void disabledButton()
    {
        // プレビューボタン無効化
        btn_Preview.setEnabled(false);
        // 印刷ボタン無効化
        btn_Print.setEnabled(false);
        // XLSボタン無効化
        btn_XLS.setEnabled(false);
    }

    // DFKLOOK:ここまで修正

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
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale, PagerModel.LIMIT_UNLIMITED);

        // initialize lst_PCTRetWorkingRequiry.
        _lcm_lst_PCTRetWorkingRequiry =
                new ListCellModel(lst_PCTRetWorkingRequiry, LST_PCTRETWORKINGREQUIRY_KEYS, locale);
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
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-P0246", KEY_HIDDEN_JAN);
        line.addToolTip("LBL-W0017", KEY_HIDDEN_ITF);
        line.addToolTip("LBL-P0143", KEY_HIDDEN_BUNDLE_ITF);
        line.addToolTip("LBL-P0053", KEY_LST_LOT_QTY);
        line.addToolTip("LBL-W0328", KEY_LST_PLAN_QTY);
        line.addToolTip("LBL-P0054", KEY_LST_RESULT_QTY);
        line.addToolTip("LBL-P0158", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W0086", KEY_LST_JOB_STATUS);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO);
        line.addToolTip("LBL-P0044", KEY_LST_ZONE_NO);
        line.addToolTip("LBL-P0094", KEY_LST_LOCATION_NO);
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
        // criteria to retain ViewState.
        LstPCTRetWorkingInquiryParams requestParam = new LstPCTRetWorkingInquiryParams(request);
        viewState.setObject(_KEY_AREA_NO, requestParam.get(LstPCTRetWorkingInquiryParams.AREA_NO));
        viewState.setObject(_KEY_BATCH_NO, requestParam.get(LstPCTRetWorkingInquiryParams.BATCH_NO));
        viewState.setObject(_KEY_BATCH_SEQ_NO, requestParam.get(LstPCTRetWorkingInquiryParams.BATCH_SEQ_NO));
        viewState.setObject(_KEY_CONSIGNOR_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.CONSIGNOR_CODE));
        viewState.setObject(_KEY_CUSTOMER_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.CUSTOMER_CODE));
        viewState.setObject(_KEY_ITEM_CODE, requestParam.get(LstPCTRetWorkingInquiryParams.ITEM_CODE));
        viewState.setObject(_KEY_ORDER_NO, requestParam.get(LstPCTRetWorkingInquiryParams.ORDER_NO));
        viewState.setObject(_KEY_PLAN_DAY, requestParam.get(LstPCTRetWorkingInquiryParams.PLAN_DAY));
        viewState.setObject(_KEY_REGULAR_CUSTOMER_CODE,
                requestParam.get(LstPCTRetWorkingInquiryParams.REGULAR_CUSTOMER_CODE));
        viewState.setObject(_KEY_JOB_STATUS, requestParam.get(LstPCTRetWorkingInquiryParams.JOB_STATUS));
        viewState.setObject(_KEY_USER_ID, requestParam.get(LstPCTRetWorkingInquiryParams.USER));

        // 親画面からエリアNoが渡されていない場合
        if (StringUtil.isBlank(requestParam.getString(LstPCTRetWorkingInquiryParams.AREA_NO)))
        {
            // ボタン制御
            disabledButton();
            // 対象データはありませんでした。
            message.setMsgResourceKey("6003011");
            return;
        }

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
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.BATCH_NO, viewState.getObject(_KEY_BATCH_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO, viewState.getObject(_KEY_BATCH_SEQ_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE, viewState.getObject(_KEY_CONSIGNOR_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE, viewState.getObject(_KEY_CUSTOMER_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.ITEM_CODE, viewState.getObject(_KEY_ITEM_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.ORDER_NO, viewState.getObject(_KEY_ORDER_NO));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.PLAN_DAY, viewState.getObject(_KEY_PLAN_DAY));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE,
                    viewState.getObject(_KEY_REGULAR_CUSTOMER_CODE));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.JOB_STATUS, viewState.getObject(_KEY_JOB_STATUS));
            inparam.set(LstPCTRetWorkingInquiryDASCHParams.USER, viewState.getObject(_KEY_USER_ID));

            // DFKLOOK:ここから修正
            // 検索条件部に内容を表示
            _lcm_lst_SearchCondition.clear();
            ListCellLine line_1 = _lcm_lst_SearchCondition.getNewLine();
            // 荷主コード
            line_1.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W1362"));
            line_1.setValue(KEY_LST_SEARCH_CONDITION_2, inparam.get(LstPCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE));
            // 予定日
            line_1.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0185"));
            String planDay = WmsFormatter.toDispDate((inparam.getDate(LstPCTRetWorkingInquiryParams.PLAN_DAY)), locale);
            line_1.setValue(KEY_LST_SEARCH_CONDITION_4, planDay);
            // バッチNo.
            line_1.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0224"));
            line_1.setValue(KEY_LST_SEARCH_CONDITION_6, inparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_NO));
            lst_SearchCondition_SetLineToolTip(line_1);
            _lcm_lst_SearchCondition.add(line_1);

            // 2行目の表示
            ListCellLine line_2 = _lcm_lst_SearchCondition.getNewLine();
            // バッチSeqNo.
            line_2.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0150"));
            line_2.setValue(KEY_LST_SEARCH_CONDITION_2, inparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO));
            // エリアNo.
            line_2.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W9915"));
            line_2.setValue(KEY_LST_SEARCH_CONDITION_4, inparam.get(LstPCTRetWorkingInquiryDASCHParams.AREA_NO));
            // エリア名称
            line_2.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0229"));
            AreaController aController = new AreaController(conn, this.getClass());
            line_2.setValue(KEY_LST_SEARCH_CONDITION_6,
                    aController.getAreaName(inparam.getString(LstPCTRetWorkingInquiryDASCHParams.AREA_NO)));

            lst_SearchCondition_SetLineToolTip(line_2);
            _lcm_lst_SearchCondition.add(line_2);

            // 3行目の表示
            ListCellLine line_3 = _lcm_lst_SearchCondition.getNewLine();
            // 得意先コード
            line_3.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0050"));
            line_3.setValue(KEY_LST_SEARCH_CONDITION_2,
                    inparam.get(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
            // 出荷先コード
            line_3.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0192"));
            line_3.setValue(KEY_LST_SEARCH_CONDITION_4, inparam.get(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE));
            // オーダーNo.
            line_3.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0225"));
            line_3.setValue(KEY_LST_SEARCH_CONDITION_6, inparam.get(LstPCTRetWorkingInquiryDASCHParams.ORDER_NO));
            lst_SearchCondition_SetLineToolTip(line_3);
            _lcm_lst_SearchCondition.add(line_3);

            // 4行目の表示
            ListCellLine line_4 = _lcm_lst_SearchCondition.getNewLine();
            // 商品コード
            line_4.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0189"));
            line_4.setValue(KEY_LST_SEARCH_CONDITION_2, inparam.get(LstPCTRetWorkingInquiryDASCHParams.ITEM_CODE));
            // 作業状態
            line_4.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0086"));
            line_4.setValue(
                    KEY_LST_SEARCH_CONDITION_4,
                    DisplayResource.getPctWorkingStatus2(inparam.getString(LstPCTRetWorkingInquiryDASCHParams.JOB_STATUS)));
            // ユーザ
            line_4.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-P0134"));
            line_4.setValue(KEY_LST_SEARCH_CONDITION_6, inparam.get(LstPCTRetWorkingInquiryDASCHParams.USER));
            lst_SearchCondition_SetLineToolTip(line_4);
            _lcm_lst_SearchCondition.add(line_4);
            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTRetWorkingRequiry.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");

                // DFKLOOK:ここから修正
                // ボタン制御
                disabledButton();
                // DFKLOOK:ここまで修正

                return;
            }
            else if (isFirst && count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else if (isFirst)
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetWorkingInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTRetWorkingInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_JOB_STATUS, outparam.get(LstPCTRetWorkingInquiryDASCHParams.JOB_STATUS));
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                        outparam.get(LstPCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME,
                        outparam.get(LstPCTRetWorkingInquiryDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE, outparam.get(LstPCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstPCTRetWorkingInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstPCTRetWorkingInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(LstPCTRetWorkingInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_TERMINAL_NO, outparam.get(LstPCTRetWorkingInquiryDASCHParams.TERMINAL_NO));
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

            // DFKLOOK:ここから修正
            // ボタン制御
            disabledButton();
            // DFKLOOK:ここまで修正
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

        PrintExporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("PctRetWorkingInqList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetWorkingRequiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.get(i);
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, sysDay);
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

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

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
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

        PrintExporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctRetWorkingInqList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetWorkingRequiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetWorkingRequiry.get(i);
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, sysDay);
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

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

        Exporter exporter = null;
        try
        {
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
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE,
                        line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME,
                        line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
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
