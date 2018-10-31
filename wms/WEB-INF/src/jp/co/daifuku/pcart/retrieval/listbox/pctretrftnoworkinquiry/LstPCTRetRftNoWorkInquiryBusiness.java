// $Id: LstPCTRetRftNoWorkInquiryBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.retrieval.listbox.pctretrftnoworkinquiry;

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
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetRftNoWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetRftNoWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetRftNoWorkInqListParams;
import jp.co.daifuku.pcart.retrieval.schedule.LstPCTRetRftNoWorkInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.LstPCTRetRftNoWorkInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * Pカート別作業照会一覧の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date: 2010-03-20 16:23:11 +0900 (土, 20 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTRetRftNoWorkInquiryBusiness
        extends LstPCTRetRftNoWorkInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchCondition(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION =
            new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_PCART_RFT_NO) */
    private static final ListCellKey KEY_LST_PCART_RFT_NO =
            new ListCellKey("LST_PCART_RFT_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry2(LST_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_CODE =
            new ListCellKey("LST_REGULAR_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry2(LST_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_REGULAR_CUSTOMER_NAME =
            new ListCellKey("LST_REGULAR_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry2(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry2(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_REGULAR_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_REGULAR_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE =
            new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN =
            new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF =
            new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF =
            new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_CUSTOMER_NAME) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_NAME =
            new ListCellKey("HIDDEN_CUSTOMER_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_REGULAR_CUSTOMER_NAME) */
    private static final ListCellKey KEY_HIDDEN_REGULAR_CUSTOMER_NAME =
            new ListCellKey("HIDDEN_REGULAR_CUSTOMER_NAME", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_PCART_RFT_NO) */
    private static final ListCellKey KEY_HIDDEN_PCART_RFT_NO =
            new ListCellKey("HIDDEN_PCART_RFT_NO", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_USER_ID) */
    private static final ListCellKey KEY_HIDDEN_USER_ID =
            new ListCellKey("HIDDEN_USER_ID", new StringCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO =
            new ListCellKey("HIDDEN_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY =
            new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY =
            new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTRetRftNoWorkInquiry(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_SearchCondition kyes */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
            KEY_LST_SEARCH_CONDITION,
            KEY_LST_PCART_RFT_NO,
    };

    /** lst_PCTRetRftNoWorkInquiry2 kyes */
    private static final ListCellKey[] LST_PCTRETRFTNOWORKINQUIRY2_KEYS = {
            KEY_LST_REGULAR_CUSTOMER_CODE,
            KEY_LST_REGULAR_CUSTOMER_NAME,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_CUSTOMER_NAME,
    };

    /** lst_PCTRetRftNoWorkInquiry kyes */
    private static final ListCellKey[] LST_PCTRETRFTNOWORKINQUIRY_KEYS = {
            KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
            KEY_HIDDEN_CUSTOMER_CODE,
            KEY_HIDDEN_JAN,
            KEY_HIDDEN_ITF,
            KEY_HIDDEN_BUNDLE_ITF,
            KEY_HIDDEN_CUSTOMER_NAME,
            KEY_HIDDEN_REGULAR_CUSTOMER_NAME,
            KEY_HIDDEN_PCART_RFT_NO,
            KEY_HIDDEN_USER_ID,
            KEY_HIDDEN_AREA_NO,
            KEY_LST_ZONE_NO,
            KEY_LST_LOCATION_NO,
            KEY_LST_ITEM_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_LOT_QTY,
            KEY_LST_PLAN_QTY,
            KEY_LST_RESULT_QTY,
            KEY_LST_ORDER_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchCondition */
    private ListCellModel _lcm_lst_SearchCondition;

    /** ListCellModel lst_PCTRetRftNoWorkInquiry2 */
    private ListCellModel _lcm_lst_PCTRetRftNoWorkInquiry2;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTRetRftNoWorkInquiry */
    private ListCellModel _lcm_lst_PCTRetRftNoWorkInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTRetRftNoWorkInquiryBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void slb_Preview_Click(ActionEvent e)
            throws Exception
    {
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
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_PCART_RFT_NO, false);

        // initialize lst_PCTRetRftNoWorkInquiry2.
        _lcm_lst_PCTRetRftNoWorkInquiry2 =
                new ListCellModel(lst_PCTRetRftNoWorkInquiry2, LST_PCTRETRFTNOWORKINQUIRY2_KEYS, locale);
        _lcm_lst_PCTRetRftNoWorkInquiry2.setToolTipVisible(KEY_LST_REGULAR_CUSTOMER_CODE, true);
        _lcm_lst_PCTRetRftNoWorkInquiry2.setToolTipVisible(KEY_LST_REGULAR_CUSTOMER_NAME, true);
        _lcm_lst_PCTRetRftNoWorkInquiry2.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_PCTRetRftNoWorkInquiry2.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_PCTRetRftNoWorkInquiry.
        _lcm_lst_PCTRetRftNoWorkInquiry =
                new ListCellModel(lst_PCTRetRftNoWorkInquiry, LST_PCTRETRFTNOWORKINQUIRY_KEYS, locale);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_PCTRetRftNoWorkInquiry.setToolTipVisible(KEY_LST_ORDER_NO, true);

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
    private void lst_PCTRetRftNoWorkInquiry2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0051", KEY_LST_REGULAR_CUSTOMER_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTRetRftNoWorkInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);

        // DFKLOOK:ここから修正
        // ツールチップの名称を追加
        line.addToolTip("LBL-P0059", KEY_HIDDEN_REGULAR_CUSTOMER_CODE);
        line.addToolTip("LBL-W0114", KEY_HIDDEN_CUSTOMER_CODE);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0002", KEY_HIDDEN_JAN);
        line.addToolTip("LBL-W0017", KEY_HIDDEN_ITF);
        line.addToolTip("LBL-P0143", KEY_HIDDEN_BUNDLE_ITF);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        txt_UserName.setReadOnly(true);
        txt_AreaNo.setReadOnly(true);
        txt_AreaName.setReadOnly(true);
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstPCTRetRftNoWorkInquiryDASCH dasch = null;

        // DFKLOOK:ここから追加
        boolean isSuccess = false;
        // DFKLOOK:ここまで追加

        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstPCTRetRftNoWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input DASCHparameters.
            LstPCTRetRftNoWorkInquiryDASCHParams inparam = new LstPCTRetRftNoWorkInquiryDASCHParams();
            LstPCTRetRftNoWorkInquiryParams requestParam = new LstPCTRetRftNoWorkInquiryParams(request);
            inparam.set(LstPCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO,
                    requestParam.get(LstPCTRetRftNoWorkInquiryParams.PCART_RFT_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTRetRftNoWorkInquiry.clear();

            // DFKLOOK:ここから追加修正
            // 検索条件部に内容を表示
            _lcm_lst_SearchCondition.clear();
            ListCellLine searchLine = _lcm_lst_SearchCondition.getNewLine();
            // Pカート号機No.
            searchLine.setValue(KEY_LST_SEARCH_CONDITION, DispResources.getText("LBL-P0239"));
            searchLine.setValue(KEY_LST_PCART_RFT_NO, inparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO));
            lst_SearchCondition_SetLineToolTip(searchLine);
            _lcm_lst_SearchCondition.add(searchLine);

            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // set input SCHparameters.
            LstPCTRetRftNoWorkInquirySCHParams inparamSCH = new LstPCTRetRftNoWorkInquirySCHParams();
            inparamSCH.set(LstPCTRetRftNoWorkInquirySCHParams.PCART_RFT_NO,
                    requestParam.get(LstPCTRetRftNoWorkInquiryParams.PCART_RFT_NO));
            // 得意先出荷先一覧表示
            lst_PCTRetRftNoWorkInquiry2_Make(inparamSCH, conn, locale, ui);

            // DFKLOOK:ここまで追加修正

            if (count == 0)
            {
                // DFKLOOK:ここから追加
                // 検索データが無い場合使用不可
                btn_Preview.setEnabled(false);
                btn_Print.setEnabled(false);
                btn_XLS.setEnabled(false);
                // DFKLOOK:ここまで追加

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
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry.getNewLine();
                txt_UserName.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.USER_NAME));
                txt_AreaNo.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                txt_AreaName.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NAME));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_LOCATION));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_CUSTOMER_NAME,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_PCART_RFT_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO));
                line.setValue(KEY_HIDDEN_USER_ID, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.USER_ID));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                lst_PCTRetRftNoWorkInquiry_SetLineToolTip(line);
                _lcm_lst_PCTRetRftNoWorkInquiry.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);

            // DFKLOOK:ここから修正
            isSuccess = true;
            // clear.
            txt_UserName.setReadOnly(true);
            txt_AreaNo.setReadOnly(true);
            txt_AreaName.setReadOnly(true);
            // DFKLOOK:ここまで修正

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_SearchCondition.clear();
            _lcm_lst_PCTRetRftNoWorkInquiry.clear();

            // DFKLOOK:ここから追加
            // 検索データが無い場合使用不可
            btn_Preview.setEnabled(false);
            btn_Print.setEnabled(false);
            btn_XLS.setEnabled(false);
            // DFKLOOK:ここまで追加
        }
        finally
        {
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
            }
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstPCTRetRftNoWorkInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstPCTRetRftNoWorkInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_PCTRetRftNoWorkInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry.getNewLine();
                txt_UserName.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.USER_NAME));
                txt_AreaNo.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                txt_AreaName.setValue(outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NAME));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_LOCATION));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.RESULT_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ORDER_NO));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_CUSTOMER_NAME,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_PCART_RFT_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO));
                line.setValue(KEY_HIDDEN_USER_ID, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.USER_ID));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(LstPCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                lst_PCTRetRftNoWorkInquiry_SetLineToolTip(line);
                _lcm_lst_PCTRetRftNoWorkInquiry.add(line);
            }
            // clear.
            txt_UserName.setReadOnly(true);
            txt_AreaNo.setReadOnly(true);
            txt_AreaName.setReadOnly(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTRetRftNoWorkInquiry.clear();
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
            exporter = factory.newPVExporter("PctRetRftNoWorkInqList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetRftNoWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry.get(i);
                PctRetRftNoWorkInqListParams expparam = new PctRetRftNoWorkInqListParams();
                expparam.set(PctRetRftNoWorkInqListParams.USER_NAME, txt_UserName.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NO, txt_AreaNo.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NAME, txt_AreaName.getValue());

                // DFKLOOK:ここから修正
                expparam.set(PctRetRftNoWorkInqListParams.SYS_DAY, sysDay);
                expparam.set(PctRetRftNoWorkInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctRetRftNoWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_LOCATION_NO, line.getStringValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetRftNoWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetRftNoWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_HIDDEN_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.RFT_NO, line.getValue(KEY_HIDDEN_PCART_RFT_NO));
                expparam.set(PctRetRftNoWorkInqListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
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
            exporter = factory.newPrinterExporter("PctRetRftNoWorkInqList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetRftNoWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry.get(i);
                PctRetRftNoWorkInqListParams expparam = new PctRetRftNoWorkInqListParams();
                expparam.set(PctRetRftNoWorkInqListParams.USER_NAME, txt_UserName.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NO, txt_AreaNo.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NAME, txt_AreaName.getValue());

                // DFKLOOK:ここから修正
                expparam.set(PctRetRftNoWorkInqListParams.SYS_DAY, sysDay);
                expparam.set(PctRetRftNoWorkInqListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctRetRftNoWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_LOCATION_NO, line.getStringValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_CODE, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.JAN_CODE, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctRetRftNoWorkInqListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctRetRftNoWorkInqListParams.BUNDLE_ITF, line.getValue(KEY_HIDDEN_BUNDLE_ITF));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_HIDDEN_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_NAME, line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.RFT_NO, line.getValue(KEY_HIDDEN_PCART_RFT_NO));
                expparam.set(PctRetRftNoWorkInqListParams.USER_ID, line.getValue(KEY_HIDDEN_USER_ID));
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
            exporter = factory.newExcelExporter("PctRetRftNoWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTRetRftNoWorkInquiry.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry.get(i);
                PctRetRftNoWorkInqListParams expparam = new PctRetRftNoWorkInqListParams();
                expparam.set(PctRetRftNoWorkInqListParams.USER_NAME, txt_UserName.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NO, txt_AreaNo.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NAME, txt_AreaName.getValue());
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_LOCATION_NO,
                        line.getStringValue(KEY_LST_LOCATION_NO));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_CODE,
                        line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_NAME, line.getValue(KEY_HIDDEN_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_NAME,
                        line.getValue(KEY_HIDDEN_REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.RFT_NO, line.getValue(KEY_HIDDEN_PCART_RFT_NO));
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

    // DFKLOOK:ここから修正
    private void lst_PCTRetRftNoWorkInquiry2_Make(LstPCTRetRftNoWorkInquirySCHParams param, Connection conn,
            Locale locale, DfkUserInfo ui)
            throws Exception
    {
        LstPCTRetRftNoWorkInquirySCH sch = null;
        try
        {
            // open connection.
            sch = new LstPCTRetRftNoWorkInquirySCH(conn, this.getClass(), locale, ui);

            // SCH call.
            List<Params> outparams = sch.query(param);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTRetRftNoWorkInquiry2.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTRetRftNoWorkInquiry2.getNewLine();
                line.setValue(KEY_LST_REGULAR_CUSTOMER_CODE,
                        outparam.get(LstPCTRetRftNoWorkInquirySCHParams.REGULAR_CUSTOMER_CODE));
                line.setValue(KEY_LST_REGULAR_CUSTOMER_NAME,
                        outparam.get(LstPCTRetRftNoWorkInquirySCHParams.REGULAR_CUSTOMER_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(LstPCTRetRftNoWorkInquirySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstPCTRetRftNoWorkInquirySCHParams.CUSTOMER_NAME));
                lst_PCTRetRftNoWorkInquiry2_SetLineToolTip(line);
                _lcm_lst_PCTRetRftNoWorkInquiry2.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
            _lcm_lst_PCTRetRftNoWorkInquiry2.clear();
        }
    }

    // DFKLOOK:ここまで修正

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
