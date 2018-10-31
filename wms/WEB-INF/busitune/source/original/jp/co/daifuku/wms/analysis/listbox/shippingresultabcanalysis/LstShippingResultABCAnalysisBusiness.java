// $Id: LstShippingResultABCAnalysisBusiness.java 7573 2010-03-15 09:45:36Z okayama $
package jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis;

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
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysis;
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7573 $, $Date:: 2010-03-15 18:45:36 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
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

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstShippingResultABCAnalysisDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

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

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_AbcList.clear();

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
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AbcList.clear();
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
                line.setValue(KEY_PERCENT, outparam.get(LstShippingResultABCAnalysisDASCHParams.PERCENT));
                line.setValue(KEY_CUMULATIVE_PERCENT, outparam.get(LstShippingResultABCAnalysisDASCHParams.CUMULATIVE_PRECENT));
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
                expparam.set(ShippingResultABCAnalysisParams.RATIO, line.getValue(KEY_HIDDEN_PERCENT));
                expparam.set(ShippingResultABCAnalysisParams.ACCUM_RATIO, line.getValue(KEY_HIDDEN_CUM_PERCENT));
                if (!exporter.write(expparam))
                {
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
