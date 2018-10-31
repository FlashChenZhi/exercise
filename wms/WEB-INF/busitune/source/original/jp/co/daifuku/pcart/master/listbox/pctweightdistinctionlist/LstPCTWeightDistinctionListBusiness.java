// $Id: LstPCTWeightDistinctionListBusiness.java 7955 2010-05-25 04:00:07Z shibamoto $
package jp.co.daifuku.pcart.master.listbox.pctweightdistinctionlist;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.pcart.master.dasch.LstPCTWeightDistinctionListDASCH;
import jp.co.daifuku.pcart.master.dasch.LstPCTWeightDistinctionListDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PCTWeightDistinctionListParams;
import jp.co.daifuku.pcart.master.listbox.pctweightdistinctionlist.LstPCTWeightDistinctionList;
import jp.co.daifuku.pcart.master.listbox.pctweightdistinctionlist.LstPCTWeightDistinctionListParams;
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
 * @version $Revision: 7955 $, $Date:: 2010-05-25 13:00:07 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstPCTWeightDistinctionListBusiness
        extends LstPCTWeightDistinctionList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONSIGNOR_CODE = "_KEY_CONSIGNOR_CODE";

    /** key */
    private static final String _KEY_FROM_OCCUR_DAY = "_KEY_FROM_OCCUR_DAY";

    /** key */
    private static final String _KEY_FROM_OCCUR_TIME = "_KEY_FROM_OCCUR_TIME";

    /** key */
    private static final String _KEY_ITEM_CODE = "_KEY_ITEM_CODE";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** key */
    private static final String _KEY_TO_OCCUR_DAY = "_KEY_TO_OCCUR_DAY";

    /** key */
    private static final String _KEY_TO_OCCUR_TIME = "_KEY_TO_OCCUR_TIME";

    /** lst_SearchConditionTwoColumn(LST_S_L_CONDITION_1) */
    private static final ListCellKey KEY_LST_S_L_CONDITION_1 = new ListCellKey("LST_S_L_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_S_D_CONDITION_1) */
    private static final ListCellKey KEY_LST_S_D_CONDITION_1 = new ListCellKey("LST_S_D_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_S_L_CONDITION_2) */
    private static final ListCellKey KEY_LST_S_L_CONDITION_2 = new ListCellKey("LST_S_L_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_S_D_CONDITION_2) */
    private static final ListCellKey KEY_LST_S_D_CONDITION_2 = new ListCellKey("LST_S_D_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME = new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_USER_ID) */
    private static final ListCellKey KEY_HIDDEN_USER_ID = new ListCellKey("HIDDEN_USER_ID", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_OCCUR_DAY) */
    private static final ListCellKey KEY_HIDDEN_OCCUR_DAY = new ListCellKey("HIDDEN_OCCUR_DAY", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_OCCUR_TIME) */
    private static final ListCellKey KEY_HIDDEN_OCCUR_TIME = new ListCellKey("HIDDEN_OCCUR_TIME", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_QTY) */
    private static final ListCellKey KEY_LST_QTY = new ListCellKey("LST_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_LST_SINGLE_WEIGHT = new ListCellKey("LST_SINGLE_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_WEIGHT) */
    private static final ListCellKey KEY_LST_WEIGHT = new ListCellKey("LST_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_AMOUNT) */
    private static final ListCellKey KEY_LST_AMOUNT = new ListCellKey("LST_AMOUNT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_CORRECT_QTY) */
    private static final ListCellKey KEY_LST_CORRECT_QTY = new ListCellKey("LST_CORRECT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_CORRECT_WEIGHT) */
    private static final ListCellKey KEY_LST_CORRECT_WEIGHT = new ListCellKey("LST_CORRECT_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_DIFFERENCE) */
    private static final ListCellKey KEY_LST_DIFFERENCE = new ListCellKey("LST_DIFFERENCE", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_OCCUR_DAY) */
    private static final ListCellKey KEY_LST_OCCUR_DAY = new ListCellKey("LST_OCCUR_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTWeightDistinctionList(LST_OCCUR_TIME) */
    private static final ListCellKey KEY_LST_OCCUR_TIME = new ListCellKey("LST_OCCUR_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_PCTWeightDistinctionList(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO = new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_FRONTAGE) */
    private static final ListCellKey KEY_LST_FRONTAGE = new ListCellKey("LST_FRONTAGE", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn keys */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
        KEY_LST_S_L_CONDITION_1,
        KEY_LST_S_D_CONDITION_1,
        KEY_LST_S_L_CONDITION_2,
        KEY_LST_S_D_CONDITION_2,
    };

    /** lst_PCTWeightDistinctionList keys */
    private static final ListCellKey[] LST_PCTWEIGHTDISTINCTIONLIST_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_CONSIGNOR_NAME,
        KEY_HIDDEN_USER_ID,
        KEY_HIDDEN_OCCUR_DAY,
        KEY_HIDDEN_OCCUR_TIME,
        KEY_LST_ITEM_CODE,
        KEY_LST_QTY,
        KEY_LST_SINGLE_WEIGHT,
        KEY_LST_WEIGHT,
        KEY_LST_AMOUNT,
        KEY_LST_CORRECT_QTY,
        KEY_LST_CORRECT_WEIGHT,
        KEY_LST_DIFFERENCE,
        KEY_LST_OCCUR_DAY,
        KEY_LST_RFT_NO,
        KEY_LST_FRONTAGE,
        KEY_LST_USER_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_OCCUR_TIME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchConditionTwoColumn */
    private ListCellModel _lcm_lst_SearchConditionTwoColumn;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTWeightDistinctionList */
    private ListCellModel _lcm_lst_PCTWeightDistinctionList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTWeightDistinctionListBusiness()
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

        // initialize lst_SearchConditionTwoColumn.
        _lcm_lst_SearchConditionTwoColumn = new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_L_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_D_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_L_CONDITION_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_D_CONDITION_2, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale, PagerModel.LIMIT_UNLIMITED);

        // initialize lst_PCTWeightDistinctionList.
        _lcm_lst_PCTWeightDistinctionList = new ListCellModel(lst_PCTWeightDistinctionList, LST_PCTWEIGHTDISTINCTIONLIST_KEYS, locale);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_QTY, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_SINGLE_WEIGHT, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_WEIGHT, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_AMOUNT, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_CORRECT_QTY, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_CORRECT_WEIGHT, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_DIFFERENCE, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_OCCUR_DAY, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_OCCUR_TIME, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_RFT_NO, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_FRONTAGE, true);
        _lcm_lst_PCTWeightDistinctionList.setToolTipVisible(KEY_LST_USER_NAME, true);

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
    private void lst_SearchConditionTwoColumn_SetLineToolTip(ListCellLine line)
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
    private void lst_PCTWeightDistinctionList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_USER_ID);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
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
        LstPCTWeightDistinctionListParams requestParam = new LstPCTWeightDistinctionListParams(request);
        viewState.setObject(_KEY_CONSIGNOR_CODE, requestParam.get(LstPCTWeightDistinctionListParams.CONSIGNOR_CODE));
        viewState.setObject(_KEY_ITEM_CODE, requestParam.get(LstPCTWeightDistinctionListParams.ITEM_CODE));
        viewState.setObject(_KEY_FROM_OCCUR_DAY, requestParam.get(LstPCTWeightDistinctionListParams.FROM_OCCUR_DAY));
        viewState.setObject(_KEY_FROM_OCCUR_TIME, requestParam.get(LstPCTWeightDistinctionListParams.FROM_OCCUR_TIME));
        viewState.setObject(_KEY_TO_OCCUR_DAY, requestParam.get(LstPCTWeightDistinctionListParams.TO_OCCUR_DAY));
        viewState.setObject(_KEY_TO_OCCUR_TIME, requestParam.get(LstPCTWeightDistinctionListParams.TO_OCCUR_TIME));

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
        LstPCTWeightDistinctionListDASCH dasch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTWeightDistinctionListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTWeightDistinctionListDASCHParams inparam = new LstPCTWeightDistinctionListDASCHParams();
            inparam.set(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE, viewState.getObject(_KEY_CONSIGNOR_CODE));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.ITEM_CODE, viewState.getObject(_KEY_ITEM_CODE));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.FROM_OCCUR_DAY, viewState.getObject(_KEY_FROM_OCCUR_DAY));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.FROM_OCCUR_TIME, viewState.getObject(_KEY_FROM_OCCUR_TIME));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY, viewState.getObject(_KEY_TO_OCCUR_DAY));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_TIME, viewState.getObject(_KEY_TO_OCCUR_TIME));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTWeightDistinctionList.clear();

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
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTWeightDistinctionListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTWeightDistinctionListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_QTY, outparam.get(LstPCTWeightDistinctionListDASCHParams.QTY));
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(LstPCTWeightDistinctionListDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT, outparam.get(LstPCTWeightDistinctionListDASCHParams.WEIGHT));
                line.setValue(KEY_LST_AMOUNT, outparam.get(LstPCTWeightDistinctionListDASCHParams.AMOUNT));
                line.setValue(KEY_LST_CORRECT_QTY, outparam.get(LstPCTWeightDistinctionListDASCHParams.CORRECT_QTY));
                line.setValue(KEY_LST_CORRECT_WEIGHT, outparam.get(LstPCTWeightDistinctionListDASCHParams.CORRECT_WEIGHT));
                line.setValue(KEY_LST_DIFFERENCE, outparam.get(LstPCTWeightDistinctionListDASCHParams.DIFFERENCE));
                line.setValue(KEY_LST_OCCUR_DAY, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_DAY));
                line.setValue(KEY_LST_OCCUR_TIME, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_TIME));
                line.setValue(KEY_LST_RFT_NO, outparam.get(LstPCTWeightDistinctionListDASCHParams.RFT_NO));
                line.setValue(KEY_LST_FRONTAGE, outparam.get(LstPCTWeightDistinctionListDASCHParams.FRONTAGE));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTWeightDistinctionListDASCHParams.USER_NAME));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME, outparam.get(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_HIDDEN_USER_ID, outparam.get(LstPCTWeightDistinctionListDASCHParams.USER_ID));
                line.setValue(KEY_HIDDEN_OCCUR_DAY, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_DAY));
                line.setValue(KEY_HIDDEN_OCCUR_TIME, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_TIME));
                lst_PCTWeightDistinctionList_SetLineToolTip(line);
                _lcm_lst_PCTWeightDistinctionList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTWeightDistinctionList.clear();
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
            exporter = factory.newPVExporter("PctWeightDistinctionList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PCTWeightDistinctionListParams expparam = new PCTWeightDistinctionListParams();
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PCTWeightDistinctionListParams.SYS_DAY, "");
                expparam.set(PCTWeightDistinctionListParams.SYS_TIME, "");
                expparam.set(PCTWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PCTWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PCTWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PCTWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PCTWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PCTWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PCTWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PCTWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newPrinterExporter("PctWeightDistinctionList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PCTWeightDistinctionListParams expparam = new PCTWeightDistinctionListParams();
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PCTWeightDistinctionListParams.SYS_DAY, "");
                expparam.set(PCTWeightDistinctionListParams.SYS_TIME, "");
                expparam.set(PCTWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PCTWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PCTWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PCTWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PCTWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PCTWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PCTWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PCTWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newExcelExporter("PctWeightDistinctionList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PCTWeightDistinctionListParams expparam = new PCTWeightDistinctionListParams();
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PCTWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PCTWeightDistinctionListParams.SYS_DAY, "");
                expparam.set(PCTWeightDistinctionListParams.SYS_TIME, "");
                expparam.set(PCTWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PCTWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PCTWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PCTWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PCTWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PCTWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PCTWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PCTWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PCTWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PCTWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
