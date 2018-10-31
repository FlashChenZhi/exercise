// $Id: LstPCTRetResultWorkInquiryBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.retrieval.listbox.pctretresultworkinquiry;

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
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetResultWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetResultWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetResultWorkInqListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 作業実績照会一覧の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTRetResultWorkInquiryBusiness
        extends LstPCTRetResultWorkInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

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

    /** lst_PCTRetResultWorkInquiry(HIDDEN_TERMINAL_NO) */
    private static final ListCellKey KEY_HIDDEN_TERMINAL_NO =
            new ListCellKey("HIDDEN_TERMINAL_NO", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME =
            new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_REGULAR_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN =
            new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF =
            new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF =
            new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetResultWorkInquiry(LST_WORK_DAY) */
    private static final ListCellKey KEY_LST_WORK_DAY =
            new ListCellKey("LST_WORK_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO =
            new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_NAME =
            new ListCellKey("LST_REGULAR_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY =
            new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY =
            new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_JOB_STATUS) */
    private static final ListCellKey KEY_LST_JOB_STATUS =
            new ListCellKey("LST_JOB_STATUS", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetResultWorkInquiry(LST_LOCATION_NO) */
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

    /** lst_PCTRetResultWorkInquiry kyes */
    private static final ListCellKey[] LST_PCTRETRESULTWORKINQUIRY_KEYS = {
            KEY_HIDDEN_TERMINAL_NO,
            KEY_HIDDEN_CONSIGNOR_CODE,
            KEY_HIDDEN_CONSIGNOR_NAME,
            KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
            KEY_HIDDEN_CUSTOMER_CODE,
            KEY_HIDDEN_JAN,
            KEY_HIDDEN_ITF,
            KEY_HIDDEN_BUNDLE_ITF,
            KEY_LST_WORK_DAY,
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

    /** ListCellModel lst_PCTRetResultWorkInquiry */
    private ListCellModel _lcm_lst_PCTRetResultWorkInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTRetResultWorkInquiryBusiness()
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
     * 検索条件のリストセルを作成します。
     * 
     * @param param
     *            検索条件
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(LstPCTRetResultWorkInquiryDASCHParams param, Connection conn, Locale locale)
            throws Exception
    {

        // 1行目の表示
        ListCellLine line = _lcm_lst_SearchCondition.getNewLine();
        // 荷主コード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W1362"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE));
        // 作業日
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0091"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, WmsFormatter.toDispDate(
                WmsFormatter.toParamDate(param.getDate(LstPCTRetResultWorkInquiryDASCHParams.WORK_DAY)), locale));
        // バッチNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0224"));
        line.setValue(KEY_LST_SEARCH_CONDITION_6, param.getString(LstPCTRetResultWorkInquiryDASCHParams.BATCH_NO));
        _lcm_lst_SearchCondition.add(line);

        // 2行目の表示
        line = _lcm_lst_SearchCondition.getNewLine();
        // バッチSeqNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0150"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(LstPCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO));
        // エリアNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W9915"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, param.getString(LstPCTRetResultWorkInquiryDASCHParams.AREA_NO));
        // エリア名称
        line.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0229"));
        AreaController aController = new AreaController(conn, this.getClass());
        line.setValue(KEY_LST_SEARCH_CONDITION_6,
                aController.getAreaName(param.getString(LstPCTRetResultWorkInquiryDASCHParams.AREA_NO)));
        _lcm_lst_SearchCondition.add(line);

        // 3行目の表示
        line = _lcm_lst_SearchCondition.getNewLine();
        // 得意先コード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0050"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2,
                param.getString(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
        // 出荷先コード
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0192"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, param.getString(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE));
        // オーダーNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W0225"));
        line.setValue(KEY_LST_SEARCH_CONDITION_6, param.getString(LstPCTRetResultWorkInquiryDASCHParams.ORDER_NO));
        _lcm_lst_SearchCondition.add(line);

        // 4行目の表示
        line = _lcm_lst_SearchCondition.getNewLine();
        // 商品コード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0189"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(LstPCTRetResultWorkInquiryDASCHParams.ITEM_CODE));
        // 作業状態
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0086"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4,
                DisplayResource.getPctWorkingStatus(param.getString(LstPCTRetResultWorkInquiryDASCHParams.JOB_STATUS)));
        // ユーザ
        line.setValue(KEY_LST_SEARCH_CONDITION_5, DispResources.getText("LBL-W9902"));
        line.setValue(KEY_LST_SEARCH_CONDITION_6, param.getString(LstPCTRetResultWorkInquiryDASCHParams.USER_ID));
        _lcm_lst_SearchCondition.add(line);

    }

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
        }, locale);

        // initialize lst_PCTRetResultWorkInquiry.
        _lcm_lst_PCTRetResultWorkInquiry =
                new ListCellModel(lst_PCTRetResultWorkInquiry, LST_PCTRETRESULTWORKINQUIRY_KEYS, locale);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_WORK_DAY, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_REGULAR_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_JOB_STATUS, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTRetResultWorkInquiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTRetResultWorkInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 表示順変更
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0051", KEY_LST_REGULAR_CUSTOMER_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0002", KEY_HIDDEN_JAN);
        line.addToolTip("LBL-W0017", KEY_HIDDEN_ITF);
        line.addToolTip("LBL-P0143", KEY_HIDDEN_BUNDLE_ITF);
        line.addToolTip("LBL-P0053", KEY_LST_LOT_QTY);
        line.addToolTip("LBL-W0328", KEY_LST_PLAN_QTY);
        line.addToolTip("LBL-P0054", KEY_LST_RESULT_QTY);
        line.addToolTip("LBL-P0141", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W0086", KEY_LST_JOB_STATUS);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO);
        line.addToolTip("LBL-P0044", KEY_LST_ZONE_NO);
        line.addToolTip("LBL-P0094", KEY_LST_LOCATION_NO);
        // DFKLOOK:ここまで修正
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstPCTRetResultWorkInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstPCTRetResultWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstPCTRetResultWorkInquiryDASCHParams inparam = new LstPCTRetResultWorkInquiryDASCHParams();
            LstPCTRetResultWorkInquiryParams requestParam = new LstPCTRetResultWorkInquiryParams(request);
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.CONSIGNOR_CODE));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.WORK_DAY,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.WORK_DAY));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.BATCH_NO,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.BATCH_NO));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.BATCH_SEQ_NO));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.AREA_NO,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.AREA_NO));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.REGULAR_CUSTOMER_CODE));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.CUSTOMER_CODE));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.ORDER_NO,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.ORDER_NO));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.ITEM_CODE,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.ITEM_CODE));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.JOB_STATUS,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.JOB_STATUS));
            inparam.set(LstPCTRetResultWorkInquiryDASCHParams.USER_ID,
                    requestParam.get(LstPCTRetResultWorkInquiryParams.USER_ID));

            // DFKLOOK:ここから修正
            lst_SearchCondition_Make(inparam, conn, locale);
            // 親画面からエリアNoが渡されていない場合
            if (StringUtil.isBlank(requestParam.getString(LstPCTRetResultWorkInquiryParams.AREA_NO)))
            {
                // ボタン制御
                disabledButton();
                // 対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }
            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTRetResultWorkInquiry.clear();

            if (count == 0)
            {
                // DFKLOOK:ここから修正
                // ボタン制御
                disabledButton();
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
                ListCellLine line = _lcm_lst_PCTRetResultWorkInquiry.getNewLine();
                line.setValue(KEY_HIDDEN_TERMINAL_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.TERMINAL_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_LST_WORK_DAY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.WORK_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_JOB_STATUS, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.JOB_STATUS));
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.LOCATION_NO));
                lst_PCTRetResultWorkInquiry_SetLineToolTip(line);
                _lcm_lst_PCTRetResultWorkInquiry.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTRetResultWorkInquiry.clear();
            // DFKLOOK:ここから修正
            // ボタン制御
            disabledButton();
            // DFKLOOK:ここまで修正
        }
        finally
        {
            if (!isSuccess)
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
        LstPCTRetResultWorkInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstPCTRetResultWorkInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_PCTRetResultWorkInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetResultWorkInquiry.getNewLine();
                line.setValue(KEY_HIDDEN_TERMINAL_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.TERMINAL_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_LST_WORK_DAY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.WORK_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_JOB_STATUS, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.JOB_STATUS));
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstPCTRetResultWorkInquiryDASCHParams.LOCATION_NO));
                lst_PCTRetResultWorkInquiry_SetLineToolTip(line);
                _lcm_lst_PCTRetResultWorkInquiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTRetResultWorkInquiry.clear();
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
            DBUtil.close(dasch.getConnection());
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
            exporter = factory.newPVExporter("PctRetResultWorkInqList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            Date sysDate = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetResultWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetResultWorkInquiry.get(i);
                PctRetResultWorkInqListParams expparam = new PctRetResultWorkInqListParams();
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetResultWorkInqListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DAY));
                expparam.set(PctRetResultWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetResultWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetResultWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctRetResultWorkInqListParams.SYS_DAY, sysDate);
                expparam.set(PctRetResultWorkInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctRetResultWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetResultWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetResultWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetResultWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetResultWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetResultWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetResultWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetResultWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetResultWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetResultWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetResultWorkInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetResultWorkInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newPrinterExporter("PctRetResultWorkInqList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetResultWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetResultWorkInquiry.get(i);
                PctRetResultWorkInqListParams expparam = new PctRetResultWorkInqListParams();
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetResultWorkInqListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DAY));
                expparam.set(PctRetResultWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetResultWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetResultWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctRetResultWorkInqListParams.SYS_DAY, sysDay);
                expparam.set(PctRetResultWorkInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctRetResultWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetResultWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetResultWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetResultWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetResultWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetResultWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetResultWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetResultWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetResultWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetResultWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetResultWorkInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetResultWorkInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
            exporter = factory.newExcelExporter("PctRetResultWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetResultWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetResultWorkInquiry.get(i);
                PctRetResultWorkInqListParams expparam = new PctRetResultWorkInqListParams();
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PctRetResultWorkInqListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DAY));
                expparam.set(PctRetResultWorkInqListParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                expparam.set(PctRetResultWorkInqListParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_CODE,
                        line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_NAME,
                        line.getValue(KEY_LST_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetResultWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetResultWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetResultWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetResultWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetResultWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetResultWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetResultWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetResultWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetResultWorkInqListParams.STATUS, line.getValue(KEY_LST_JOB_STATUS));
                expparam.set(PctRetResultWorkInqListParams.PLAN_AREA_NO, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetResultWorkInqListParams.TERMINAL_NO, line.getValue(KEY_HIDDEN_TERMINAL_NO));
                expparam.set(PctRetResultWorkInqListParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
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
