// $Id: LstPCTWeightDistinctionListBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.master.listbox.pctweightdistinctionlist;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.master.dasch.LstPCTWeightDistinctionListDASCH;
import jp.co.daifuku.pcart.master.dasch.LstPCTWeightDistinctionListDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PctWeightDistinctionListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 重量差異リスト一覧の画面表示を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
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
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** key */
    private static final String _KEY_TO_OCCUR_DAY = "_KEY_TO_OCCUR_DAY";

    /** key */
    private static final String _KEY_TO_OCCUR_TIME = "_KEY_TO_OCCUR_TIME";

    // DFKLOOK:ここから修正
    /** key */
    private static final String _KEY_FROM_OCCUR_DATE = "_KEY_FROM_OCCUR_DATE";

    /** key */
    private static final String _KEY_TO_OCCUR_DATE = "_KEY_TO_OCCUR_DATE";

    /** _lcm_lst_SearchConditionTwoColumn(LST_S_L_CONDITION_1) */
    private static final ListCellKey KEY_LST_S_L_CONDITION_1 =
            new ListCellKey("LST_S_L_CONDITION_1", new StringCellColumn(), true, false);

    /** _lcm_lst_SearchConditionTwoColumn(LST_S_D_CONDITION_1) */
    private static final ListCellKey KEY_LST_S_D_CONDITION_1 =
            new ListCellKey("LST_S_D_CONDITION_1", new StringCellColumn(), true, false);

    /** _lcm_lst_SearchConditionTwoColumn(LST_S_L_CONDITION_2) */
    private static final ListCellKey KEY_LST_S_L_CONDITION_2 =
            new ListCellKey("LST_S_L_CONDITION_2", new StringCellColumn(), true, false);

    /** _lcm_lst_SearchConditionTwoColumn(LST_S_D_CONDITION_2) */
    private static final ListCellKey KEY_LST_S_D_CONDITION_2 =
            new ListCellKey("LST_S_D_CONDITION_2", new StringCellColumn(), true, false);

    // DFKLOOK:ここまで修正

    /** lst_PCTWeightDistinctionList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME =
            new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(HIDDEN_USER_ID) */
    private static final ListCellKey KEY_HIDDEN_USER_ID =
            new ListCellKey("HIDDEN_USER_ID", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(KEY_HIDDEN_OCCUR_DAY) */
    private static final ListCellKey KEY_HIDDEN_OCCUR_DAY =
            new ListCellKey("KEY_HIDDEN_OCCUR_DAY", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(KEY_HIDDEN_OCCUR_TIME) */
    private static final ListCellKey KEY_HIDDEN_OCCUR_TIME =
            new ListCellKey("KEY_HIDDEN_OCCUR_TIME", new StringCellColumn(), false, false);

    /** lst_PCTWeightDistinctionList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_QTY) */
    private static final ListCellKey KEY_LST_QTY = new ListCellKey("LST_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_LST_SINGLE_WEIGHT =
            new ListCellKey("LST_SINGLE_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_WEIGHT) */
    private static final ListCellKey KEY_LST_WEIGHT =
            new ListCellKey("LST_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_AMOUNT) */
    private static final ListCellKey KEY_LST_AMOUNT =
            new ListCellKey("LST_AMOUNT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_CORRECT_QTY) */
    private static final ListCellKey KEY_LST_CORRECT_QTY =
            new ListCellKey("LST_CORRECT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_CORRECT_WEIGHT) */
    private static final ListCellKey KEY_LST_CORRECT_WEIGHT =
            new ListCellKey("LST_CORRECT_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_DIFFERENCE) */
    private static final ListCellKey KEY_LST_DIFFERENCE =
            new ListCellKey("LST_DIFFERENCE", new NumberCellColumn(0), true, false);

    /** lst_PCTWeightDistinctionList(LST_OCCUR_DAY) */
    private static final ListCellKey KEY_LST_OCCUR_DAY =
            new ListCellKey("LST_OCCUR_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTWeightDistinctionList(LST_OCCUR_TIME) */
    private static final ListCellKey KEY_LST_OCCUR_TIME =
            new ListCellKey("LST_OCCUR_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_PCTWeightDistinctionList(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO =
            new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_FRONTAGE) */
    private static final ListCellKey KEY_LST_FRONTAGE =
            new ListCellKey("LST_FRONTAGE", new StringCellColumn(), true, false);

    /** lst_PCTWeightDistinctionList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    // DFKLOOK:ここから修正
    /** lst_SearchConditionTwoColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {

            KEY_LST_S_L_CONDITION_1,
            KEY_LST_S_D_CONDITION_1,
            KEY_LST_S_L_CONDITION_2,
            KEY_LST_S_D_CONDITION_2,
    };

    // DFKLOOK:ここまで修正

    /** lst_PCTWeightDistinctionList kyes */
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
        _lcm_lst_SearchConditionTwoColumn =
                new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);

        // DFKLOOK:ここから修正
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_L_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_D_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_L_CONDITION_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_S_D_CONDITION_2, false);
        // DFKLOOK:ここまで修正

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale, PagerModel.LIMIT_UNLIMITED);

        // initialize lst_PCTWeightDistinctionList.
        _lcm_lst_PCTWeightDistinctionList =
                new ListCellModel(lst_PCTWeightDistinctionList, LST_PCTWEIGHTDISTINCTIONLIST_KEYS, locale);
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
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-P0081", KEY_HIDDEN_USER_ID);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);

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
        LstPCTWeightDistinctionListParams requestParam = new LstPCTWeightDistinctionListParams(request);
        viewState.setObject(_KEY_CONSIGNOR_CODE, requestParam.get(LstPCTWeightDistinctionListParams.CONSIGNOR_CODE));
        viewState.setObject(_KEY_ITEM_CODE, requestParam.get(LstPCTWeightDistinctionListParams.ITEM_CODE));
        viewState.setObject(_KEY_FROM_OCCUR_DAY, requestParam.get(LstPCTWeightDistinctionListParams.FROM_OCCUR_DAY));
        viewState.setObject(_KEY_FROM_OCCUR_TIME, requestParam.get(LstPCTWeightDistinctionListParams.FROM_OCCUR_TIME));
        viewState.setObject(_KEY_TO_OCCUR_DAY, requestParam.get(LstPCTWeightDistinctionListParams.TO_OCCUR_DAY));
        viewState.setObject(_KEY_TO_OCCUR_TIME, requestParam.get(LstPCTWeightDistinctionListParams.TO_OCCUR_TIME));

        // DFKLOOK:ここから修正
        viewState.setObject(_KEY_FROM_OCCUR_DATE, requestParam.get(LstPCTWeightDistinctionListParams.FROM_OCCUR_DAY));
        viewState.setObject(_KEY_TO_OCCUR_DATE, requestParam.get(LstPCTWeightDistinctionListParams.TO_OCCUR_DAY));
        // DFKLOOK:ここまで修正

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
            inparam.set(LstPCTWeightDistinctionListDASCHParams.FROM_OCCUR_TIME,
                    viewState.getObject(_KEY_FROM_OCCUR_TIME));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY, viewState.getObject(_KEY_TO_OCCUR_DAY));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_TIME, viewState.getObject(_KEY_TO_OCCUR_TIME));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.FROM_OCCUR_DAY,
                    viewState.getObject(_KEY_FROM_OCCUR_DATE));
            inparam.set(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY, viewState.getObject(_KEY_TO_OCCUR_DATE));

            // DFKLOOK:ここから修正
            // 検索条件部に内容を表示
            _lcm_lst_SearchConditionTwoColumn.clear();
            ListCellLine line_1 = _lcm_lst_SearchConditionTwoColumn.getNewLine();
            // 荷主コード
            line_1.setValue(KEY_LST_S_L_CONDITION_1, DispResources.getText("LBL-W1362"));
            line_1.setValue(KEY_LST_S_D_CONDITION_1, inparam.get(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE));
            lst_SearchConditionTwoColumn_SetLineToolTip(line_1);
            _lcm_lst_SearchConditionTwoColumn.add(line_1);

            // 2行目の表示
            ListCellLine line_2 = _lcm_lst_SearchConditionTwoColumn.getNewLine();
            // 商品コード
            line_2.setValue(KEY_LST_S_L_CONDITION_1, DispResources.getText("LBL-W0189"));
            line_2.setValue(KEY_LST_S_D_CONDITION_1, inparam.get(LstPCTWeightDistinctionListDASCHParams.ITEM_CODE));
            lst_SearchConditionTwoColumn_SetLineToolTip(line_2);
            _lcm_lst_SearchConditionTwoColumn.add(line_2);

            // 3行目表示の前に日付の前後を入替え
            Date[] fromTo =
                    WmsFormatter.getFromTo(inparam.getDate(LstPCTWeightDistinctionListDASCHParams.FROM_OCCUR_DAY),
                            inparam.getDate(LstPCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY));

            // 3行目の表示
            ListCellLine line_3 = _lcm_lst_SearchConditionTwoColumn.getNewLine();
            // 開始発生日時
            line_3.setValue(KEY_LST_S_L_CONDITION_1, DispResources.getText("LBL-P0135"));
            line_3.setValue(KEY_LST_S_D_CONDITION_1, WmsFormatter.toDispDateTime(
                    WmsFormatter.toParamDateTime(fromTo[0]), locale));

            // 終了発生日時
            line_3.setValue(KEY_LST_S_L_CONDITION_2, DispResources.getText("LBL-P0136"));
            line_3.setValue(KEY_LST_S_D_CONDITION_2, WmsFormatter.toDispDateTime(
                    WmsFormatter.toParamDateTime(fromTo[1]), locale));

            lst_SearchConditionTwoColumn_SetLineToolTip(line_3);
            _lcm_lst_SearchConditionTwoColumn.add(line_3);
            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTWeightDistinctionList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");

                // DFKLOOK:ここから修正
                btn_Preview.setEnabled(false);
                btn_Print.setEnabled(false);
                btn_XLS.setEnabled(false);
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
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTWeightDistinctionListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTWeightDistinctionListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_QTY, outparam.get(LstPCTWeightDistinctionListDASCHParams.QTY));
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(LstPCTWeightDistinctionListDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT, outparam.get(LstPCTWeightDistinctionListDASCHParams.WEIGHT));
                line.setValue(KEY_LST_AMOUNT, outparam.get(LstPCTWeightDistinctionListDASCHParams.AMOUNT));
                line.setValue(KEY_LST_CORRECT_QTY, outparam.get(LstPCTWeightDistinctionListDASCHParams.CORRECT_QTY));
                line.setValue(KEY_LST_CORRECT_WEIGHT,
                        outparam.get(LstPCTWeightDistinctionListDASCHParams.CORRECT_WEIGHT));
                line.setValue(KEY_LST_DIFFERENCE, outparam.get(LstPCTWeightDistinctionListDASCHParams.DIFFERENCE));
                line.setValue(KEY_LST_OCCUR_DAY, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_DAY));
                line.setValue(KEY_LST_OCCUR_TIME, outparam.get(LstPCTWeightDistinctionListDASCHParams.OCCUR_TIME));
                line.setValue(KEY_LST_RFT_NO, outparam.get(LstPCTWeightDistinctionListDASCHParams.RFT_NO));
                line.setValue(KEY_LST_FRONTAGE, outparam.get(LstPCTWeightDistinctionListDASCHParams.FRONTAGE));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTWeightDistinctionListDASCHParams.USER_NAME));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                        outparam.get(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME,
                        outparam.get(LstPCTWeightDistinctionListDASCHParams.CONSIGNOR_NAME));
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

            // DFKLOOK:ここから修正
            btn_Preview.setEnabled(false);
            btn_Print.setEnabled(false);
            btn_XLS.setEnabled(false);
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
            exporter = factory.newPVExporter("PctWeightDistinctionList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PctWeightDistinctionListParams expparam = new PctWeightDistinctionListParams();
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctWeightDistinctionListParams.SYS_DAY, sysDay);
                expparam.set(PctWeightDistinctionListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PctWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PctWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PctWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PctWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PctWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PctWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PctWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PctWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newPrinterExporter("PctWeightDistinctionList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PctWeightDistinctionListParams expparam = new PctWeightDistinctionListParams();
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctWeightDistinctionListParams.SYS_DAY, sysDay);
                expparam.set(PctWeightDistinctionListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PctWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PctWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PctWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PctWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PctWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PctWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PctWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PctWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newExcelExporter("PctWeightDistinctionList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTWeightDistinctionList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTWeightDistinctionList.get(i);
                PctWeightDistinctionListParams expparam = new PctWeightDistinctionListParams();
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctWeightDistinctionListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctWeightDistinctionListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctWeightDistinctionListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_QTY));
                expparam.set(PctWeightDistinctionListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_WEIGHT, line.getValue(KEY_LST_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_QTY, line.getValue(KEY_LST_AMOUNT));
                expparam.set(PctWeightDistinctionListParams.CORRECT_QTY, line.getValue(KEY_LST_CORRECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_WEIGHT, line.getValue(KEY_LST_CORRECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.DIFFERENCE_WEIGHT, line.getValue(KEY_LST_DIFFERENCE));
                expparam.set(PctWeightDistinctionListParams.DATE, line.getValue(KEY_HIDDEN_OCCUR_DAY));
                expparam.set(PctWeightDistinctionListParams.TIME, line.getValue(KEY_HIDDEN_OCCUR_TIME));
                expparam.set(PctWeightDistinctionListParams.TERMINAL_NO, line.getValue(KEY_LST_RFT_NO));
                expparam.set(PctWeightDistinctionListParams.LOCATION_NO, line.getValue(KEY_LST_FRONTAGE));
                expparam.set(PctWeightDistinctionListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
                expparam.set(PctWeightDistinctionListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
