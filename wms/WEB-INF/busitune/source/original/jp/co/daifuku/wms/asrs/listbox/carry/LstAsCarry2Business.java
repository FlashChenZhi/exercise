// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.listbox.carry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
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
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsCarryDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsCarryDASCHParams;
import jp.co.daifuku.wms.asrs.listbox.carry.LstAsCarry2;
import jp.co.daifuku.wms.asrs.listbox.carry.LstAsCarry2Params;
import jp.co.daifuku.wms.asrs.listbox.carry.ViewStateKeys;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class LstAsCarry2Business
        extends LstAsCarry2
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

    /** lst_SearchCondition(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_DEST_STATION_NO) */
    private static final ListCellKey KEY_HIDDEN_DEST_STATION_NO = new ListCellKey("HIDDEN_DEST_STATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_CARRY_FLAG) */
    private static final ListCellKey KEY_HIDDEN_CARRY_FLAG = new ListCellKey("HIDDEN_CARRY_FLAG", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_CMD_STATUS) */
    private static final ListCellKey KEY_HIDDEN_CMD_STATUS = new ListCellKey("HIDDEN_CMD_STATUS", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_NO = new ListCellKey("HIDDEN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_SCHEDULE_NO) */
    private static final ListCellKey KEY_HIDDEN_SCHEDULE_NO = new ListCellKey("HIDDEN_SCHEDULE_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_SOURCE_STATION_NAME) */
    private static final ListCellKey KEY_HIDDEN_SOURCE_STATION_NAME = new ListCellKey("HIDDEN_SOURCE_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_DEST_STATION_NAME) */
    private static final ListCellKey KEY_HIDDEN_DEST_STATION_NAME = new ListCellKey("HIDDEN_DEST_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_WORK_TYPE) */
    private static final ListCellKey KEY_HIDDEN_WORK_TYPE = new ListCellKey("HIDDEN_WORK_TYPE", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_RETRIEVAL_DETAIL) */
    private static final ListCellKey KEY_HIDDEN_RETRIEVAL_DETAIL = new ListCellKey("HIDDEN_RETRIEVAL_DETAIL", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(HIDDEN_SOURCE_STATION_NO) */
    private static final ListCellKey KEY_HIDDEN_SOURCE_STATION_NO = new ListCellKey("HIDDEN_SOURCE_STATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkIncludeButtonList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_PRIORITY) */
    private static final ListCellKey KEY_LST_PRIORITY = new ListCellKey("LST_PRIORITY", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_CARRY_KEY) */
    private static final ListCellKey KEY_LST_CARRY_KEY = new ListCellKey("LST_CARRY_KEY", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_SOURCE_DEST) */
    private static final ListCellKey KEY_LST_SOURCE_DEST = new ListCellKey("LST_SOURCE_DEST", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_CARRY_FLAG) */
    private static final ListCellKey KEY_LST_CARRY_FLAG = new ListCellKey("LST_CARRY_FLAG", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_CMD_STATUS) */
    private static final ListCellKey KEY_LST_CMD_STATUS = new ListCellKey("LST_CMD_STATUS", new StringCellColumn(), true, false);

    /** lst_RestWorkIncludeButtonList(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
    };

    /** lst_RestWorkIncludeButtonList keys */
    private static final ListCellKey[] LST_RESTWORKINCLUDEBUTTONLIST_KEYS = {
        KEY_HIDDEN_DEST_STATION_NO,
        KEY_HIDDEN_CARRY_FLAG,
        KEY_HIDDEN_CMD_STATUS,
        KEY_HIDDEN_LOCATION_NO,
        KEY_HIDDEN_SCHEDULE_NO,
        KEY_HIDDEN_SOURCE_STATION_NAME,
        KEY_HIDDEN_DEST_STATION_NAME,
        KEY_HIDDEN_WORK_TYPE,
        KEY_HIDDEN_RETRIEVAL_DETAIL,
        KEY_HIDDEN_AREA_NO,
        KEY_HIDDEN_SOURCE_STATION_NO,
        KEY_LST_SELECT,
        KEY_LST_PRIORITY,
        KEY_LST_CARRY_KEY,
        KEY_LST_STATION_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_SOURCE_DEST,
        KEY_LST_CARRY_FLAG,
        KEY_LST_CMD_STATUS,
        KEY_LST_WORK_NO,
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

    /** ListCellModel lst_RestWorkIncludeButtonList */
    private ScrollListCellModel _lcm_lst_RestWorkIncludeButtonList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAsCarry2Business()
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
    public void pager_First(ActionEvent e)
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
    public void pager_Prev(ActionEvent e)
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
    public void pager_Next(ActionEvent e)
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
    public void pager_Last(ActionEvent e)
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
    public void lst_RestWorkIncludeButtonList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_RestWorkIncludeButtonList.getActiveCol();

        // choose process.
        if (_lcm_lst_RestWorkIncludeButtonList.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_RestWorkIncludeButtonList.
        _lcm_lst_RestWorkIncludeButtonList = new ScrollListCellModel(lst_RestWorkIncludeButtonList, LST_RESTWORKINCLUDEBUTTONLIST_KEYS, locale);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_PRIORITY, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_CARRY_KEY, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_SOURCE_DEST, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_CARRY_FLAG, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_CMD_STATUS, true);
        _lcm_lst_RestWorkIncludeButtonList.setToolTipVisible(KEY_LST_WORK_NO, true);
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
    private void lst_RestWorkIncludeButtonList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_CMD_STATUS);
        line.addToolTip("", KEY_HIDDEN_LOCATION_NO);
        line.addToolTip("", KEY_HIDDEN_SCHEDULE_NO);
        line.addToolTip("", KEY_HIDDEN_WORK_TYPE);
        line.addToolTip("", KEY_HIDDEN_RETRIEVAL_DETAIL);
        line.addToolTip("LBL-W0165", KEY_LST_CARRY_KEY);
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
        else if (eventSource.equals("btn_ReDisplayFunc_Click"))
        {
            // process call.
            btn_ReDisplayFunc_Click_SetList();
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
        setFocus(pager);
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
        LstAsCarryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsCarryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsCarryDASCHParams inparam = new LstAsCarryDASCHParams();
            LstAsCarry2Params requestParam = new LstAsCarry2Params(request);
            inparam.set(LstAsCarryDASCHParams.WORK_PLACE_KEY, requestParam.get(LstAsCarry2Params.WORK_PLACE_KEY));
            inparam.set(LstAsCarryDASCHParams.STATION_NO_KEY, requestParam.get(LstAsCarry2Params.STATION_NO_KEY));
            inparam.set(LstAsCarryDASCHParams.TERMINAL_NO_KEY, requestParam.get(LstAsCarry2Params.TERMINAL_NO_KEY));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RestWorkIncludeButtonList.clear();

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
            _lcm_lst_RestWorkIncludeButtonList.clear();
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
        LstAsCarryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsCarryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RestWorkIncludeButtonList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RestWorkIncludeButtonList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsCarryDASCHParams.SELECT));
                line.setValue(KEY_LST_PRIORITY, outparam.get(LstAsCarryDASCHParams.PRIORITY_NAME));
                line.setValue(KEY_LST_CARRY_KEY, outparam.get(LstAsCarryDASCHParams.CARRY_KEY));
                line.setValue(KEY_LST_STATION_NO, outparam.get(LstAsCarryDASCHParams.STATION_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstAsCarryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_SOURCE_DEST, outparam.get(LstAsCarryDASCHParams.SOURCE_DEST));
                line.setValue(KEY_LST_CARRY_FLAG, outparam.get(LstAsCarryDASCHParams.CARRY_FLAG_NAME));
                line.setValue(KEY_LST_CMD_STATUS, outparam.get(LstAsCarryDASCHParams.CMD_STATUS_NAME));
                line.setValue(KEY_LST_WORK_NO, outparam.get(LstAsCarryDASCHParams.WORK_NO));
                line.setValue(KEY_HIDDEN_SOURCE_STATION_NO, outparam.get(LstAsCarryDASCHParams.SOURCE_STATION_NO));
                line.setValue(KEY_HIDDEN_SOURCE_STATION_NAME, outparam.get(LstAsCarryDASCHParams.SOURCE_STATION_NAME));
                line.setValue(KEY_HIDDEN_DEST_STATION_NO, outparam.get(LstAsCarryDASCHParams.DEST_STATION_NO));
                line.setValue(KEY_HIDDEN_DEST_STATION_NAME, outparam.get(LstAsCarryDASCHParams.DEST_STATION_NAME));
                line.setValue(KEY_HIDDEN_WORK_TYPE, outparam.get(LstAsCarryDASCHParams.WORK_TYPE_NAME));
                line.setValue(KEY_HIDDEN_RETRIEVAL_DETAIL, outparam.get(LstAsCarryDASCHParams.RETRIEVAL_DETAIL_NAME));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(LstAsCarryDASCHParams.AREA_NO));
                line.setValue(KEY_HIDDEN_CARRY_FLAG, outparam.get(LstAsCarryDASCHParams.CARRY_FLAG));
                line.setValue(KEY_HIDDEN_CMD_STATUS, outparam.get(LstAsCarryDASCHParams.CMD_STATUS));
                line.setValue(KEY_HIDDEN_LOCATION_NO, outparam.get(LstAsCarryDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_SCHEDULE_NO, outparam.get(LstAsCarryDASCHParams.SCHEDULE_NO));
                lst_RestWorkIncludeButtonList_SetLineToolTip(line);
                _lcm_lst_RestWorkIncludeButtonList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RestWorkIncludeButtonList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RestWorkIncludeButtonList.getActiveRow();
        lst_RestWorkIncludeButtonList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RestWorkIncludeButtonList.get(row);

        // output parameter.
        LstAsCarry2Params outparam = new LstAsCarry2Params();
        outparam.set(LstAsCarry2Params.CARRY_KEY, line.getValue(KEY_LST_CARRY_KEY));
        outparam.set(LstAsCarry2Params.WORK_NO, line.getValue(KEY_LST_WORK_NO));
        outparam.set(LstAsCarry2Params.WORK_TYPE, line.getValue(KEY_HIDDEN_WORK_TYPE));
        outparam.set(LstAsCarry2Params.RETRIEVAL_DETAIL, line.getValue(KEY_HIDDEN_RETRIEVAL_DETAIL));
        outparam.set(LstAsCarry2Params.SOURCE_STATION_NO, line.getValue(KEY_HIDDEN_SOURCE_STATION_NO));
        outparam.set(LstAsCarry2Params.SOURCE_STATION_NAME, line.getValue(KEY_HIDDEN_SOURCE_STATION_NAME));
        outparam.set(LstAsCarry2Params.DEST_STATION_NO, line.getValue(KEY_HIDDEN_DEST_STATION_NO));
        outparam.set(LstAsCarry2Params.DEST_STATION_NAME, line.getValue(KEY_HIDDEN_DEST_STATION_NAME));
        outparam.set(LstAsCarry2Params.AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
        outparam.set(LstAsCarry2Params.CARRY_FLAG, line.getValue(KEY_LST_CARRY_FLAG));
        outparam.set(LstAsCarry2Params.CMD_STATUS, line.getValue(KEY_LST_CMD_STATUS));
        outparam.set(LstAsCarry2Params.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
        outparam.set(LstAsCarry2Params.SCHEDULE_NO, line.getValue(KEY_HIDDEN_SCHEDULE_NO));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstAsCarryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_ReDisplayFunc_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsCarryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsCarryDASCHParams inparam = new LstAsCarryDASCHParams();
            inparam.set(LstAsCarryDASCHParams.WORK_PLACE_KEY, viewState.getObject(ViewStateKeys.WORK_PLACE_KEY));
            inparam.set(LstAsCarryDASCHParams.STATION_NO_KEY, viewState.getObject(ViewStateKeys.STATION_NO_KEY));
            inparam.set(LstAsCarryDASCHParams.TERMINAL_NO_KEY, viewState.getObject(ViewStateKeys.TERMINAL_NO_KEY));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RestWorkIncludeButtonList.clear();

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
            _lcm_lst_RestWorkIncludeButtonList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_ReDisplayFunc_Click_SetList();
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
    private void btn_ReDisplayFunc_Click_SetList()
            throws Exception
    {
        LstAsCarryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsCarryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RestWorkIncludeButtonList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RestWorkIncludeButtonList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsCarryDASCHParams.SELECT));
                line.setValue(KEY_LST_PRIORITY, outparam.get(LstAsCarryDASCHParams.PRIORITY_NAME));
                line.setValue(KEY_LST_CARRY_KEY, outparam.get(LstAsCarryDASCHParams.CARRY_KEY));
                line.setValue(KEY_LST_STATION_NO, outparam.get(LstAsCarryDASCHParams.STATION_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstAsCarryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_SOURCE_DEST, outparam.get(LstAsCarryDASCHParams.SOURCE_DEST));
                line.setValue(KEY_LST_CARRY_FLAG, outparam.get(LstAsCarryDASCHParams.CARRY_FLAG_NAME));
                line.setValue(KEY_LST_CMD_STATUS, outparam.get(LstAsCarryDASCHParams.CMD_STATUS_NAME));
                line.setValue(KEY_LST_WORK_NO, outparam.get(LstAsCarryDASCHParams.WORK_NO));
                line.setValue(KEY_HIDDEN_SOURCE_STATION_NO, outparam.get(LstAsCarryDASCHParams.SOURCE_STATION_NO));
                line.setValue(KEY_HIDDEN_SOURCE_STATION_NAME, outparam.get(LstAsCarryDASCHParams.SOURCE_STATION_NAME));
                line.setValue(KEY_HIDDEN_DEST_STATION_NO, outparam.get(LstAsCarryDASCHParams.DEST_STATION_NO));
                line.setValue(KEY_HIDDEN_DEST_STATION_NAME, outparam.get(LstAsCarryDASCHParams.DEST_STATION_NAME));
                line.setValue(KEY_HIDDEN_WORK_TYPE, outparam.get(LstAsCarryDASCHParams.WORK_TYPE_NAME));
                line.setValue(KEY_HIDDEN_RETRIEVAL_DETAIL, outparam.get(LstAsCarryDASCHParams.RETRIEVAL_DETAIL_NAME));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(LstAsCarryDASCHParams.AREA_NO));
                line.setValue(KEY_HIDDEN_CARRY_FLAG, outparam.get(LstAsCarryDASCHParams.CARRY_FLAG));
                line.setValue(KEY_HIDDEN_CMD_STATUS, outparam.get(LstAsCarryDASCHParams.CMD_STATUS));
                line.setValue(KEY_HIDDEN_LOCATION_NO, outparam.get(LstAsCarryDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_SCHEDULE_NO, outparam.get(LstAsCarryDASCHParams.SCHEDULE_NO));
                lst_RestWorkIncludeButtonList_SetLineToolTip(line);
                _lcm_lst_RestWorkIncludeButtonList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RestWorkIncludeButtonList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Click_Process()
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
