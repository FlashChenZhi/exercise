// $Id: LstPCTRetPlanWorkInquiryBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.listbox.pctretplanworkinquiry;

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
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetPlanWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetPlanWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetPlanWorkInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretplanworkinquiry.LstPCTRetPlanWorkInquiry;
import jp.co.daifuku.pcart.retrieval.listbox.pctretplanworkinquiry.LstPCTRetPlanWorkInquiryParams;
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
public class LstPCTRetPlanWorkInquiryBusiness
        extends LstPCTRetPlanWorkInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

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

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME = new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF = new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN = new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_CODE = new ListCellKey("HIDDEN_REGULAR_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE = new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetPlanWorkInquiry(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO = new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_NAME = new ListCellKey("LST_REGULAR_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY = new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY = new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_JOB_STATUS) */
    private static final ListCellKey KEY_LST_JOB_STATUS = new ListCellKey("LST_JOB_STATUS", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO = new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetPlanWorkInquiry(LST_LOCATION_NO) */
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

    /** lst_PCTRetPlanWorkInquiry keys */
    private static final ListCellKey[] LST_PCTRETPLANWORKINQUIRY_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_CONSIGNOR_NAME,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_BUNDLE_ITF,
        KEY_HIDDEN_JAN,
        KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
        KEY_HIDDEN_CUSTOMER_CODE,
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

    /** ListCellModel lst_PCTRetPlanWorkInquiry */
    private ListCellModel _lcm_lst_PCTRetPlanWorkInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTRetPlanWorkInquiryBusiness()
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
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_PCTRetPlanWorkInquiry.
        _lcm_lst_PCTRetPlanWorkInquiry = new ListCellModel(lst_PCTRetPlanWorkInquiry, LST_PCTRETPLANWORKINQUIRY_KEYS, locale);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_REGULAR_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_JOB_STATUS, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTRetPlanWorkInquiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);

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
    private void lst_PCTRetPlanWorkInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_ITF);
        line.addToolTip("", KEY_HIDDEN_BUNDLE_ITF);
        line.addToolTip("", KEY_HIDDEN_JAN);
        line.addToolTip("LBL-P0051", KEY_LST_REGULAR_CUSTOMER_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-P0053", KEY_LST_LOT_QTY);
        line.addToolTip("LBL-W0328", KEY_LST_PLAN_QTY);
        line.addToolTip("LBL-P0054", KEY_LST_RESULT_QTY);
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
            page_Load_SetList();
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetPlanWorkInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new PCTRetPlanWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            PCTRetPlanWorkInquiryDASCHParams inparam = new PCTRetPlanWorkInquiryDASCHParams();
            LstPCTRetPlanWorkInquiryParams requestParam = new LstPCTRetPlanWorkInquiryParams(request);
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.AREA_NO, requestParam.get(LstPCTRetPlanWorkInquiryParams.AREA_NO));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO, requestParam.get(LstPCTRetPlanWorkInquiryParams.BATCH_NO));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO, requestParam.get(LstPCTRetPlanWorkInquiryParams.BATCH_SEQ_NO));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE, requestParam.get(LstPCTRetPlanWorkInquiryParams.CONSIGNOR_CODE));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE, requestParam.get(LstPCTRetPlanWorkInquiryParams.CUSTOMER_CODE));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.JOB_STATUS, requestParam.get(LstPCTRetPlanWorkInquiryParams.JOB_STATUS));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO, requestParam.get(LstPCTRetPlanWorkInquiryParams.ORDER_NO));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY, requestParam.get(LstPCTRetPlanWorkInquiryParams.PLAN_DAY));
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE, requestParam.get(LstPCTRetPlanWorkInquiryParams.REGULAR_CUSTOMER_CODE));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_PCTRetPlanWorkInquiry.clear();

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
            _lcm_lst_PCTRetPlanWorkInquiry.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                page_Load_SetList();
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
    private void page_Load_SetList()
            throws Exception
    {
        PCTRetPlanWorkInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (PCTRetPlanWorkInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_PCTRetPlanWorkInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetPlanWorkInquiry.getNewLine();
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(PCTRetPlanWorkInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_JOB_STATUS, outparam.get(PCTRetPlanWorkInquiryDASCHParams.JOB_STATUS));
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.LOCATION_NO));
                lst_PCTRetPlanWorkInquiry_SetLineToolTip(line);
                _lcm_lst_PCTRetPlanWorkInquiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTRetPlanWorkInquiry.clear();
            disposeDasch();
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
            exporter = factory.newPVExporter("PctRetPlanWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetPlanWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetPlanWorkInquiry.get(i);
                PctRetPlanWorkInqListParams expparam = new PctRetPlanWorkInqListParams();
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetPlanWorkInqListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PctRetPlanWorkInqListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PctRetPlanWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetPlanWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetPlanWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetPlanWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetPlanWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetPlanWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetPlanWorkInqListParams.TERMINAL_NO, "TERMINAL_NO");
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
            exporter = factory.newPrinterExporter("PctRetPlanWorkInqList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetPlanWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetPlanWorkInquiry.get(i);
                PctRetPlanWorkInqListParams expparam = new PctRetPlanWorkInqListParams();
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetPlanWorkInqListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PctRetPlanWorkInqListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PctRetPlanWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetPlanWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetPlanWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetPlanWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetPlanWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetPlanWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetPlanWorkInqListParams.TERMINAL_NO, "");
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
            exporter = factory.newExcelExporter("PctRetPlanWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetPlanWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetPlanWorkInquiry.get(i);
                PctRetPlanWorkInqListParams expparam = new PctRetPlanWorkInqListParams();
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetPlanWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetPlanWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetPlanWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetPlanWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetPlanWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetPlanWorkInqListParams.TERMINAL_NO, "");
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
