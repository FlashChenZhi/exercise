// $Id: LstShippingResultABCAnalysisBusiness.java 7819 2010-04-12 13:15:28Z shibamoto $
package jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Date;
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
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.dasch.LstShippingResultABCAnalysisDASCH;
import jp.co.daifuku.wms.analysis.dasch.LstShippingResultABCAnalysisDASCHParams;
import jp.co.daifuku.wms.analysis.exporter.ShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 出荷実績ABC分析一覧の画面処理を行います。
 *
 * @version $Revision: 7819 $, $Date: 2010-04-12 22:15:28 +0900 (月, 12 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
@SuppressWarnings("serial")
public class LstShippingResultABCAnalysisBusiness
        extends LstShippingResultABCAnalysis
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH_LIST = "_KEY_DASCH_LIST";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 = new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 = new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_AbcList(HIDDEN_PERCENT) */
    private static final ListCellKey KEY_HIDDEN_PERCENT = new ListCellKey("HIDDEN_PERCENT", new StringCellColumn(), false, false);

    /** lst_AbcList(HIDDEN_CUM_PERCENT) */
    private static final ListCellKey KEY_HIDDEN_CUM_PERCENT = new ListCellKey("HIDDEN_CUM_PERCENT", new StringCellColumn(), false, false);

    /** lst_AbcList(NO) */
    private static final ListCellKey KEY_NO = new ListCellKey("NO", new NumberCellColumn(0), true, false);

    /** lst_AbcList(ITEM_CODE) */
    private static final ListCellKey KEY_ITEM_CODE = new ListCellKey("ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_AbcList(ITEM_NAME) */
    private static final ListCellKey KEY_ITEM_NAME = new ListCellKey("ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_AbcList(CLASS) */
    private static final ListCellKey KEY_CLASS = new ListCellKey("CLASS", new StringCellColumn(), true, false);

    /** lst_AbcList(SHIP_QTY) */
    private static final ListCellKey KEY_SHIP_QTY = new ListCellKey("SHIP_QTY", new NumberCellColumn(0), true, false);

    /** lst_AbcList(WORK_QTY) */
    private static final ListCellKey KEY_WORK_QTY = new ListCellKey("WORK_QTY", new NumberCellColumn(0), true, false);

    /** lst_AbcList(SHIP_COUNT) */
    private static final ListCellKey KEY_SHIP_COUNT = new ListCellKey("SHIP_COUNT", new NumberCellColumn(0), true, false);

    /** lst_AbcList(PERCENT) */
    private static final ListCellKey KEY_PERCENT = new ListCellKey("PERCENT", new StringCellColumn(), true, false);

    /** lst_AbcList(CUMULATIVE_PERCENT) */
    private static final ListCellKey KEY_CUMULATIVE_PERCENT = new ListCellKey("CUMULATIVE_PERCENT", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn keys */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_AbcList keys */
    private static final ListCellKey[] LST_ABCLIST_KEYS = {
        KEY_HIDDEN_PERCENT,
        KEY_HIDDEN_CUM_PERCENT,
        KEY_NO,
        KEY_ITEM_CODE,
        KEY_ITEM_NAME,
        KEY_CLASS,
        KEY_SHIP_QTY,
        KEY_WORK_QTY,
        KEY_SHIP_COUNT,
        KEY_PERCENT,
        KEY_CUMULATIVE_PERCENT,
    };

    // DFKLOOK ここから追加
    // 検索条件表示行番号
    /**
     * 検索条件表示行番号 : 分析対象期間
     */
    private static final int CONDITION_PERIOD = 1;

    /**
     * 検索条件表示行番号 : 出荷先
     */
    private static final int CONDITION_CUSTOMER = 2;

    /**
     * 検索条件表示行番号 : 分析種別
     */
    private static final int CONDITION_ANALYSISTYPE = 3;

    /**
     * 検索条件表示行番号 : しきい値
     */
    private static final int CONDITION_THRESHOLD = 4;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST1 = 1;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST2 = 2;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST3 = 3;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST4 = 4;
    // DFK LOOK ここまで追加

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

    /** ListCellModel lst_AbcList */
    private ListCellModel _lcm_lst_AbcList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstShippingResultABCAnalysisBusiness()
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
    public void lst_SearchConditionTwoColumn_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionTwoColumn_ColumClick(ActionEvent e)
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
    public void lst_AbcList_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_AbcList_ColumClick(ActionEvent e)
            throws Exception
    {
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
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_AbcList.
        _lcm_lst_AbcList = new ListCellModel(lst_AbcList, LST_ABCLIST_KEYS, locale);
        _lcm_lst_AbcList.setToolTipVisible(KEY_NO, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_ITEM_CODE, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_ITEM_NAME, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_CLASS, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_SHIP_QTY, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_WORK_QTY, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_SHIP_COUNT, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_PERCENT, true);
        _lcm_lst_AbcList.setToolTipVisible(KEY_CUMULATIVE_PERCENT, true);

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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    @SuppressWarnings("all")
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
    private void lst_AbcList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_ITEM_NAME);
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
        LstShippingResultABCAnalysisDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // set input parameters.
            LstShippingResultABCAnalysisDASCHParams inparam = new LstShippingResultABCAnalysisDASCHParams();
            LstShippingResultABCAnalysisParams requestParam = new LstShippingResultABCAnalysisParams(request);
            inparam.set(LstShippingResultABCAnalysisDASCHParams.ANALYSIS_TYPE, requestParam.get(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.FROM_DATE, requestParam.get(LstShippingResultABCAnalysisParams.FROM_DATE));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.TO_DATE, requestParam.get(LstShippingResultABCAnalysisParams.TO_DATE));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.CONSIGNOR_CODE, requestParam.get(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.CUSTOMER_CODE, requestParam.get(LstShippingResultABCAnalysisParams.CUSTOMER_CODE));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_A, requestParam.get(LstShippingResultABCAnalysisParams.THRESHOLD_A));
            inparam.set(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_B, requestParam.get(LstShippingResultABCAnalysisParams.THRESHOLD_B));

            // DFKLOOK ここから追加
            // 検索条件を編集
            _lcm_lst_SearchConditionTwoColumn.clear();
            lst_SearchConditionTwoColumn.setVisible(true);
            setSearchlist(requestParam);
            // DFKLOOK ここまで追加

            // DFKLOOK ここから Connection取得とDASCHの生成をtry直下から、ここへ移動
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstShippingResultABCAnalysisDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);
            // DFKLOOK ここまで

            // get count.
            int count = dasch.count(inparam);

            // DFKLOOK ここから追加
            if (count <= 0 && dasch.getResult() == null)
            {
                // DSCHで分析結果が取得出来なかった原因のメッセージを取得する
                btn_XLS.setEnabled(false);
                message.setMsgResourceKey(dasch.getMessage());
                return;
            }

            // 指定のランク以外のデータを削除
            AnalysisOutParameter param = dasch.getResult();
            param.removeUndisplayRecords((String)requestParam.get(LstShippingResultABCAnalysisParams.RANK));
            // 計算結果を再度保存。
            dasch.setResult(param);
            // 表示できる分析一覧の件数を取得。
            count = param.getSizeOfAbcAnaList();
            // DFKLOOK ここまで追加
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_AbcList.clear();

            if (count == 0)
            {
                // DFKLOOK ここから追加
                btn_XLS.setEnabled(false);
                // DFKLOOK ここまで追加
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
            // DFKLOOK count()で分析一覧の計算処理を実行しているので、search()はコメント
            // dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AbcList.clear();
            // DFKLOOK ここから追加
            // エラーが発生した時、XLSボタンを無効にする。
            btn_XLS.setEnabled(false);
            // DFKLOOK ここまで
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
        LstShippingResultABCAnalysisDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstShippingResultABCAnalysisDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_AbcList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AbcList.getNewLine();
                line.setValue(KEY_NO, outparam.get(LstShippingResultABCAnalysisDASCHParams.NO));
                line.setValue(KEY_ITEM_CODE, outparam.get(LstShippingResultABCAnalysisDASCHParams.ITEM_CODE));
                line.setValue(KEY_ITEM_NAME, outparam.get(LstShippingResultABCAnalysisDASCHParams.ITEM_NAME));
                line.setValue(KEY_CLASS, outparam.get(LstShippingResultABCAnalysisDASCHParams.CLASS));
                line.setValue(KEY_SHIP_QTY, outparam.get(LstShippingResultABCAnalysisDASCHParams.SHIP_QTY));
                line.setValue(KEY_WORK_QTY, outparam.get(LstShippingResultABCAnalysisDASCHParams.WORK_QTY));
                line.setValue(KEY_SHIP_COUNT, outparam.get(LstShippingResultABCAnalysisDASCHParams.SHIP_COUNT));
                // DFKLOOK ここから変更
                // パーセント表示のフォーマット編集
                line.setValue(KEY_PERCENT, WmsFormatter.toComponentRate(new BigDecimal(
                        String.valueOf(outparam.get(LstShippingResultABCAnalysisDASCHParams.PERCENT))).setScale(5,
                        BigDecimal.ROUND_UP).doubleValue()));
                line.setValue(KEY_CUMULATIVE_PERCENT, WmsFormatter.toComponentRate(new BigDecimal(
                        String.valueOf(outparam.get(LstShippingResultABCAnalysisDASCHParams.CUMULATIVE_PERCENT))).setScale(5,
                        BigDecimal.ROUND_UP).doubleValue()));
                // DFKLOOK ここまで変更
                line.setValue(KEY_HIDDEN_PERCENT, outparam.get(LstShippingResultABCAnalysisDASCHParams.PERCENT));
                line.setValue(KEY_HIDDEN_CUM_PERCENT, outparam.get(LstShippingResultABCAnalysisDASCHParams.CUMULATIVE_PERCENT));
                lst_AbcList_SetLineToolTip(line);
                _lcm_lst_AbcList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AbcList.clear();
            // DFKLOOK XLSボタンを無効にする。
            btn_XLS.setEnabled(false);
            disposeDasch();
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
            exporter = factory.newExcelExporter("ShippingResultABCAnalysis", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_AbcList.size(); i++)
            {
                ListCellLine line = _lcm_lst_AbcList.get(i);
                ShippingResultABCAnalysisParams expparam = new ShippingResultABCAnalysisParams();
                expparam.set(ShippingResultABCAnalysisParams.ITEM_CODE, line.getValue(KEY_ITEM_CODE));
                expparam.set(ShippingResultABCAnalysisParams.ITEM_NAME, line.getValue(KEY_ITEM_NAME));
                expparam.set(ShippingResultABCAnalysisParams.RANK, line.getValue(KEY_CLASS));
                expparam.set(ShippingResultABCAnalysisParams.SHIPPING_QTY, line.getValue(KEY_SHIP_QTY));
                expparam.set(ShippingResultABCAnalysisParams.WORKING_QTY, line.getValue(KEY_WORK_QTY));
                expparam.set(ShippingResultABCAnalysisParams.SHIPPING_CNT, line.getValue(KEY_SHIP_COUNT));
                // DFKLOOK ここから変更
                // パーセント表示のフォーマット編集
                expparam.set(ShippingResultABCAnalysisParams.RATIO, WmsFormatter.toComponentRate(new BigDecimal(
                        String.valueOf(line.getValue(KEY_HIDDEN_PERCENT))).setScale(5,
                        BigDecimal.ROUND_UP).doubleValue()));
                expparam.set(ShippingResultABCAnalysisParams.ACCUM_RATIO, WmsFormatter.toComponentRate(new BigDecimal(
                        String.valueOf(line.getValue(KEY_HIDDEN_CUM_PERCENT))).setScale(5,
                        BigDecimal.ROUND_UP).doubleValue()));
                // DFKLOOK ここまで変更
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(_lcm_lst_AbcList.size()) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AbcList.size(); i++)
            {
                ListCellLine line = _lcm_lst_AbcList.get(i);
                lst_AbcList.setCurrentRow(i);

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_CLASS), "");
                part11List.add(line.getViewString(KEY_SHIP_QTY), "");
                part11List.add(line.getViewString(KEY_WORK_QTY), "");
                part11List.add(line.getViewString(KEY_SHIP_COUNT), "");
                part11List.add(line.getViewString(KEY_PERCENT), "");
                part11List.add(line.getViewString(KEY_CUMULATIVE_PERCENT), "");
                // DFKLOOK start
                // 開始行
                part11List.add(String.valueOf(pgr_U.getIndex()), "");
                // 終了行(開始行 + リストの行数 - 1)
                part11List.add(String.valueOf(pgr_U.getIndex() + _lcm_lst_AbcList.size() - 1), "");
                // 最大行
                part11List.add(String.valueOf(pgr_U.getMax()), "");
                // DFKLOOK end
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS_LIST), part11List);
            }

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
        // DFKLOOK ここから追加
        // タイトルのランクを取得
        LstShippingResultABCAnalysisParams requestParam = new LstShippingResultABCAnalysisParams(request);
        String rank = (String)requestParam.get(LstShippingResultABCAnalysisParams.DISP_RANK);
        // DFKLOOK ここまで追加

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

            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            title = title + "(" + rank + ")";
            lbl_ListName.setResourceKey(title);
            // DFKLOOK ここまで追加と変更
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            // lbl_ListName.setResourceKey(this.getTitleResourceKey());
            String titleName = DispResources.getText(this.getTitleResourceKey());
            titleName = titleName + "(" + rank + ")";
            lbl_ListName.setText(titleName);
            // DFKLOOK ここまで追加と変更
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            //lbl_ListName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
            String titleName = viewState.getString(Constants.M_TITLE_KEY);
            titleName = titleName + "(" + rank + ")";
            lbl_ListName.setText(titleName);
            // DFKLOOK ここまで追加と変更
        }
    }

    // DFKLOOK ここから追加
    // 検索条件を編集
    /**
     * 検索条件の表示をセットする時に使用するメソッドです。
     * <BR>
     * @param inparam requestParam
     * @throws Exception 全ての例外を報告します。
     */
    private void setSearchlist(Params inparam)
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        lst_SearchConditionTwoColumn.clearRow();
        lst_SearchConditionTwoColumn.addRow();

        // １行目 : 開始分析対象期間と終了分析対象期間
        // 開始分析対象期間と、終了分析対象期間を取得します
        Date[] fromToDate = WmsFormatter.getFromTo(inparam.getDate(LstShippingResultABCAnalysisParams.FROM_DATE)
                                                    , inparam.getDate(LstShippingResultABCAnalysisParams.TO_DATE));
        // 開始分析対象期間
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_PERIOD);
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W1218"));
        if (!StringUtil.isBlank(fromToDate[0]))
        {
            lst_SearchConditionTwoColumn.setValue(S_LIST2, WmsFormatter.toDispDate(fromToDate[0], locale));
        }
        // 終了分析対象期間
        lst_SearchConditionTwoColumn.setValue(S_LIST3, DisplayText.getText("LBL-W1219"));
        if (!StringUtil.isBlank(fromToDate[1]))
        {
            lst_SearchConditionTwoColumn.setValue(S_LIST4, WmsFormatter.toDispDate(fromToDate[1], locale));
        }

        // ２行目 : 出荷先
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_CUSTOMER);
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W0192"));

        // 出荷先コード
        String customer = inparam.getString(LstShippingResultABCAnalysisParams.CUSTOMER_CODE);
        if (!StringUtil.isBlank(customer))
        {
            lst_SearchConditionTwoColumn.setValue(S_LIST2, customer);
        }
        // 出荷先名称
        
        lst_SearchConditionTwoColumn.setValue(S_LIST3, DisplayText.getText("LBL-W0115"));
        if (!StringUtil.isBlank(customer))
        {
            String customerName = inparam.getString(LstShippingResultABCAnalysisParams.CUSTOMER_NAME);
            lst_SearchConditionTwoColumn.setValue(S_LIST4, customerName);
        }

        // ３行目 : 分析種別
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_ANALYSISTYPE);
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W1202"));
        lst_SearchConditionTwoColumn.setValue(S_LIST2, inparam.getString(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE));

        // ４行目 : しきい値
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_THRESHOLD);

        // しきい値を編集
        String percent = DisplayText.getText("LBL-W0221");
        DecimalFormat formatter = new DecimalFormat("##0");
        String thresholdA =
                formatter.format(Double.valueOf(
                        inparam.getInt(LstShippingResultABCAnalysisParams.THRESHOLD_A)).doubleValue()) + percent;
        String thresholdB =
                formatter.format(Double.valueOf(
                        inparam.getInt(LstShippingResultABCAnalysisParams.THRESHOLD_B)).doubleValue()) + percent;

        // Aランクしきい値
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W1213"));
        lst_SearchConditionTwoColumn.setValue(S_LIST2, thresholdA);
        // Bランクしきい値
        lst_SearchConditionTwoColumn.setValue(S_LIST3, DisplayText.getText("LBL-W1214"));
        lst_SearchConditionTwoColumn.setValue(S_LIST4, thresholdB);
    }
    // DFKLOOK ここまで追加

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
