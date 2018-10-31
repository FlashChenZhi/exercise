// $Id: PCTLstItemListBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.master.listbox.pctitemlist;

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
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.master.dasch.PCTLstItemListDASCH;
import jp.co.daifuku.pcart.master.dasch.PCTLstItemListDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PCTItemMasterListParams;
import jp.co.daifuku.pcart.master.listbox.pctitemlist.PCTLstItemList;
import jp.co.daifuku.pcart.master.listbox.pctitemlist.PCTLstItemListParams;
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
public class PCTLstItemListBusiness
        extends PCTLstItemList
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

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 = new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 = new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 = new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 = new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTItemMasterList(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME = new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTItemMasterList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY = new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_LST_SINGLE_WEIGHT = new ListCellKey("LST_SINGLE_WEIGHT", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_WEIGHT_DISTINCT_RATE) */
    private static final ListCellKey KEY_LST_WEIGHT_DISTINCT_RATE = new ListCellKey("LST_WEIGHT_DISTINCT_RATE", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_MAX_INSPECTION_QTY) */
    private static final ListCellKey KEY_LST_MAX_INSPECTION_QTY = new ListCellKey("LST_MAX_INSPECTION_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_LST_LAST_UPDATE_DATE = new ListCellKey("LST_LAST_UPDATE_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTItemMasterList(LST_LAST_USED_DATE) */
    private static final ListCellKey KEY_LST_LAST_USED_DATE = new ListCellKey("LST_LAST_USED_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTItemMasterList(LST_ITEM_PICTURE_REGIST) */
    private static final ListCellKey KEY_LST_ITEM_PICTURE_REGIST = new ListCellKey("LST_ITEM_PICTURE_REGIST", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE = new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn keys */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
        KEY_LST_SEARCH_CONDITION_1,
        KEY_LST_SEARCH_CONDITION_2,
        KEY_LST_SEARCH_CONDITION_3,
        KEY_LST_SEARCH_CONDITION_4,
    };

    /** lst_PCTItemMasterList keys */
    private static final ListCellKey[] LST_PCTITEMMASTERLIST_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_CONSIGNOR_NAME,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_QTY,
        KEY_LST_JAN,
        KEY_LST_LOCATION_NO,
        KEY_LST_SINGLE_WEIGHT,
        KEY_LST_MAX_INSPECTION_QTY,
        KEY_LST_LAST_UPDATE_DATE,
        KEY_LST_ITEM_PICTURE_REGIST,
        KEY_LST_MESSAGE,
        KEY_LST_ITEM_NAME,
        KEY_LST_ITF,
        KEY_LST_WEIGHT_DISTINCT_RATE,
        KEY_LST_LAST_USED_DATE,
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

    /** ListCellModel lst_PCTItemMasterList */
    private ListCellModel _lcm_lst_PCTItemMasterList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTLstItemListBusiness()
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
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_PCTItemMasterList.
        _lcm_lst_PCTItemMasterList = new ListCellModel(lst_PCTItemMasterList, LST_PCTITEMMASTERLIST_KEYS, locale);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_SINGLE_WEIGHT, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_WEIGHT_DISTINCT_RATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_MAX_INSPECTION_QTY, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LAST_UPDATE_DATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LAST_USED_DATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_PICTURE_REGIST, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_MESSAGE, true);

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
    private void lst_PCTItemMasterList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-P0209", KEY_LST_ITEM_PICTURE_REGIST);
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
        PCTLstItemListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new PCTLstItemListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            PCTLstItemListDASCHParams inparam = new PCTLstItemListDASCHParams();
            PCTLstItemListParams requestParam = new PCTLstItemListParams(request);
            inparam.set(PCTLstItemListDASCHParams.CONSIGNOR_CODE, requestParam.get(PCTLstItemListParams.CONSIGNOR_CODE));
            inparam.set(PCTLstItemListDASCHParams.FROM_ITEM_CODE, requestParam.get(PCTLstItemListParams.FROM_ITEM_CODE));
            inparam.set(PCTLstItemListDASCHParams.TO_ITEM_CODE, requestParam.get(PCTLstItemListParams.TO_ITEM_CODE));
            inparam.set(PCTLstItemListDASCHParams.LOT_QTY, requestParam.get(PCTLstItemListParams.LOT_QTY));
            inparam.set(PCTLstItemListDASCHParams.JAN, requestParam.get(PCTLstItemListParams.JAN));
            inparam.set(PCTLstItemListDASCHParams.ITF, requestParam.get(PCTLstItemListParams.ITF));
            inparam.set(PCTLstItemListDASCHParams.FROM_SINGLE_WEIGHT, requestParam.get(PCTLstItemListParams.FROM_SINGLE_WEIGHT));
            inparam.set(PCTLstItemListDASCHParams.TO_SINGLE_WEIGHT, requestParam.get(PCTLstItemListParams.TO_SINGLE_WEIGHT));
            inparam.set(PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST, requestParam.get(PCTLstItemListParams.ITEM_PICTURE_REGIST));
            inparam.set(PCTLstItemListDASCHParams.FROM_LOCATION_NO, requestParam.get(PCTLstItemListParams.FROM_LOCATION_NO));
            inparam.set(PCTLstItemListDASCHParams.TO_LOCATION_NO, requestParam.get(PCTLstItemListParams.TO_LOCATION_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_PCTItemMasterList.clear();

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
            _lcm_lst_PCTItemMasterList.clear();
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
        PCTLstItemListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (PCTLstItemListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_PCTItemMasterList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTLstItemListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTLstItemListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTLstItemListDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(PCTLstItemListDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(PCTLstItemListDASCHParams.ITF));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTLstItemListDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT_DISTINCT_RATE, outparam.get(PCTLstItemListDASCHParams.WEIGHT_DISTINCT_RATE));
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(PCTLstItemListDASCHParams.MAX_INSPECTION_QTY));
                line.setValue(KEY_LST_MAX_INSPECTION_QTY, outparam.get(PCTLstItemListDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_LAST_USED_DATE, outparam.get(PCTLstItemListDASCHParams.LAST_USED_DATE));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST));
                line.setValue(KEY_LST_ITEM_PICTURE_REGIST, outparam.get(PCTLstItemListDASCHParams.MESSAGE));
                line.setValue(KEY_LST_MESSAGE, outparam.get(PCTLstItemListDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(PCTLstItemListDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME, outparam.get(PCTLstItemListDASCHParams.CONSIGNOR_NAME));
                lst_PCTItemMasterList_SetLineToolTip(line);
                _lcm_lst_PCTItemMasterList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTItemMasterList.clear();
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
            exporter = factory.newPVExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PCTItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PCTItemMasterListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTItemMasterListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PCTItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PCTItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PCTItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PCTItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
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
            exporter = factory.newPrinterExporter("PctItemMasterList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PCTItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));
                expparam.set(PCTItemMasterListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTItemMasterListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PCTItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PCTItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PCTItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PCTItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
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
            exporter = factory.newExcelExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PCTItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PCTItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PCTItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PCTItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
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
