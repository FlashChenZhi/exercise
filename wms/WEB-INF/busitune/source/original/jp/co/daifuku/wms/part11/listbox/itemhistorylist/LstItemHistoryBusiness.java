// $Id: LstItemHistoryBusiness.java 7599 2010-03-16 01:33:58Z kishimoto $
package jp.co.daifuku.wms.part11.listbox.itemhistorylist;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.part11.dasch.LstItemHistoryDASCH;
import jp.co.daifuku.wms.part11.dasch.LstItemHistoryDASCHParams;
import jp.co.daifuku.wms.part11.listbox.itemhistorylist.LstItemHistory;
import jp.co.daifuku.wms.part11.listbox.itemhistorylist.LstItemHistoryParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7599 $, $Date:: 2010-03-16 10:33:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstItemHistoryBusiness
        extends LstItemHistory
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

    /** lst_SearchCondition(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 = new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 = new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_LOGDATE_DAY) */
    private static final ListCellKey KEY_LST_LOGDATE_DAY = new ListCellKey("LST_LOGDATE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ItemHistoryList(LST_LOGDATE_TIME) */
    private static final ListCellKey KEY_LST_LOGDATE_TIME = new ListCellKey("LST_LOGDATE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_KIND) */
    private static final ListCellKey KEY_LST_UPDATE_KIND = new ListCellKey("LST_UPDATE_KIND", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS = new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME = new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_ITEM_NAME) */
    private static final ListCellKey KEY_LST_UPDATE_ITEM_NAME = new ListCellKey("LST_UPDATE_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_SOFT_ZONE_ID) */
    private static final ListCellKey KEY_LST_SOFT_ZONE_ID = new ListCellKey("LST_SOFT_ZONE_ID", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_SOFT_ZONE_ID) */
    private static final ListCellKey KEY_LST_UPDATE_SOFT_ZONE_ID = new ListCellKey("LST_UPDATE_SOFT_ZONE_ID", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_JAN) */
    private static final ListCellKey KEY_LST_UPDATE_JAN = new ListCellKey("LST_UPDATE_JAN", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_ITF) */
    private static final ListCellKey KEY_LST_UPDATE_ITF = new ListCellKey("LST_UPDATE_ITF", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_UPDATE_ENTERING_QTY = new ListCellKey("LST_UPDATE_ENTERING_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPPER_QTY) */
    private static final ListCellKey KEY_LST_UPPER_QTY = new ListCellKey("LST_UPPER_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_UPPER_QTY) */
    private static final ListCellKey KEY_LST_UPDATE_UPPER_QTY = new ListCellKey("LST_UPDATE_UPPER_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_LOWER_QTY) */
    private static final ListCellKey KEY_LST_LOWER_QTY = new ListCellKey("LST_LOWER_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_LOWER_QTY) */
    private static final ListCellKey KEY_LST_UPDATE_LOWER_QTY = new ListCellKey("LST_UPDATE_LOWER_QTY", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_TEMPORARY_TYPE) */
    private static final ListCellKey KEY_LST_TEMPORARY_TYPE = new ListCellKey("LST_TEMPORARY_TYPE", new StringCellColumn(), true, false);

    /** lst_ItemHistoryList(LST_UPDATE_TEMPORARY_TYPE) */
    private static final ListCellKey KEY_LST_UPDATE_TEMPORARY_TYPE = new ListCellKey("LST_UPDATE_TEMPORARY_TYPE", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_ItemHistoryList keys */
    private static final ListCellKey[] LST_ITEMHISTORYLIST_KEYS = {
        KEY_LST_NO,
        KEY_LST_LOGDATE_DAY,
        KEY_LST_UPDATE_KIND,
        KEY_LST_USER_ID,
        KEY_LST_IP_ADDRESS,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_SOFT_ZONE_ID,
        KEY_LST_JAN,
        KEY_LST_ITF,
        KEY_LST_ENTERING_QTY,
        KEY_LST_UPPER_QTY,
        KEY_LST_LOWER_QTY,
        KEY_LST_TEMPORARY_TYPE,
        KEY_LST_LOGDATE_TIME,
        KEY_LST_USER_NAME,
        KEY_LST_TERMINAL_NAME,
        KEY_LST_UPDATE_ITEM_NAME,
        KEY_LST_UPDATE_SOFT_ZONE_ID,
        KEY_LST_UPDATE_JAN,
        KEY_LST_UPDATE_ITF,
        KEY_LST_UPDATE_ENTERING_QTY,
        KEY_LST_UPDATE_UPPER_QTY,
        KEY_LST_UPDATE_LOWER_QTY,
        KEY_LST_UPDATE_TEMPORARY_TYPE,
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

    /** ListCellModel lst_ItemHistoryList */
    private ListCellModel _lcm_lst_ItemHistoryList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstItemHistoryBusiness()
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
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ItemHistoryList.
        _lcm_lst_ItemHistoryList = new ListCellModel(lst_ItemHistoryList, LST_ITEMHISTORYLIST_KEYS, locale);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_LOGDATE_DAY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_LOGDATE_TIME, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_KIND, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_USER_ID, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_IP_ADDRESS, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_TERMINAL_NAME, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_ITEM_NAME, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_SOFT_ZONE_ID, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_SOFT_ZONE_ID, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_JAN, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_ITF, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_ENTERING_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPPER_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_UPPER_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_LOWER_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_LOWER_QTY, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_TEMPORARY_TYPE, true);
        _lcm_lst_ItemHistoryList.setToolTipVisible(KEY_LST_UPDATE_TEMPORARY_TYPE, true);
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
    private void lst_ItemHistoryList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W8006", KEY_LST_TERMINAL_NAME);
        line.addToolTip("LBL-W8017", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W8018", KEY_LST_UPDATE_ITEM_NAME);
        line.addToolTip("LBL-W8019", KEY_LST_JAN);
        line.addToolTip("LBL-W8020", KEY_LST_UPDATE_JAN);
        line.addToolTip("LBL-W8021", KEY_LST_ITF);
        line.addToolTip("LBL-W8022", KEY_LST_UPDATE_ITF);
        line.addToolTip("LBL-W8023", KEY_LST_ENTERING_QTY);
        line.addToolTip("LBL-W8024", KEY_LST_UPDATE_ENTERING_QTY);
        line.addToolTip("LBL-W8025", KEY_LST_UPPER_QTY);
        line.addToolTip("LBL-W8026", KEY_LST_UPDATE_UPPER_QTY);
        line.addToolTip("LBL-W8027", KEY_LST_LOWER_QTY);
        line.addToolTip("LBL-W8028", KEY_LST_UPDATE_LOWER_QTY);
        line.addToolTip("LBL-W1447", KEY_LST_TEMPORARY_TYPE);
        line.addToolTip("LBL-W1448", KEY_LST_UPDATE_TEMPORARY_TYPE);
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
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstItemHistoryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstItemHistoryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstItemHistoryDASCHParams inparam = new LstItemHistoryDASCHParams();
            LstItemHistoryParams requestParam = new LstItemHistoryParams(request);
            inparam.set(LstItemHistoryDASCHParams.TABLE_NAME, requestParam.get(LstItemHistoryParams.TABLE_NAME));
            inparam.set(LstItemHistoryDASCHParams.DISPFROMDAY_KEY, requestParam.get(LstItemHistoryParams.DISPFROMDAY_KEY));
            inparam.set(LstItemHistoryDASCHParams.DISPFROMTIME_KEY, requestParam.get(LstItemHistoryParams.DISPFROMTIME_KEY));
            inparam.set(LstItemHistoryDASCHParams.DISPTODAY_KEY, requestParam.get(LstItemHistoryParams.DISPTODAY_KEY));
            inparam.set(LstItemHistoryDASCHParams.DISPTOTIME_KEY, requestParam.get(LstItemHistoryParams.DISPTOTIME_KEY));
            inparam.set(LstItemHistoryDASCHParams.USERID_KEY, requestParam.get(LstItemHistoryParams.USERID_KEY));
            inparam.set(LstItemHistoryDASCHParams.DSNUMBER_KEY, requestParam.get(LstItemHistoryParams.DSNUMBER_KEY));
            inparam.set(LstItemHistoryDASCHParams.PAGENAMERESOURCEKEY, requestParam.get(LstItemHistoryParams.PAGENAMERESOURCEKEY));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ItemHistoryList.clear();

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
            _lcm_lst_ItemHistoryList.clear();
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
        LstItemHistoryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstItemHistoryDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ItemHistoryList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ItemHistoryList.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(LstItemHistoryDASCHParams.NO));
                line.setValue(KEY_LST_LOGDATE_DAY, outparam.get(LstItemHistoryDASCHParams.LOGDATE_DAY));
                line.setValue(KEY_LST_LOGDATE_TIME, outparam.get(LstItemHistoryDASCHParams.LOGDATE_TIME));
                line.setValue(KEY_LST_UPDATE_KIND, outparam.get(LstItemHistoryDASCHParams.UPDATE_KIND));
                line.setValue(KEY_LST_USER_ID, outparam.get(LstItemHistoryDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstItemHistoryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(LstItemHistoryDASCHParams.IP_ADDRESS));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(LstItemHistoryDASCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstItemHistoryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstItemHistoryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_UPDATE_ITEM_NAME, outparam.get(LstItemHistoryDASCHParams.UPDATE_ITEM_NAME));
                line.setValue(KEY_LST_SOFT_ZONE_ID, outparam.get(LstItemHistoryDASCHParams.SOFT_ZONE_ID));
                line.setValue(KEY_LST_UPDATE_SOFT_ZONE_ID, outparam.get(LstItemHistoryDASCHParams.UPDATE_SOFT_ZONE_ID));
                line.setValue(KEY_LST_JAN, outparam.get(LstItemHistoryDASCHParams.JAN));
                line.setValue(KEY_LST_UPDATE_JAN, outparam.get(LstItemHistoryDASCHParams.UPDATE_JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstItemHistoryDASCHParams.ITF));
                line.setValue(KEY_LST_UPDATE_ITF, outparam.get(LstItemHistoryDASCHParams.UPDATE_ITF));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstItemHistoryDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_UPDATE_ENTERING_QTY, outparam.get(LstItemHistoryDASCHParams.UPDATE_ENTERING_QTY));
                line.setValue(KEY_LST_UPPER_QTY, outparam.get(LstItemHistoryDASCHParams.UPPER_QTY));
                line.setValue(KEY_LST_UPDATE_UPPER_QTY, outparam.get(LstItemHistoryDASCHParams.UPDATE_UPPER_QTY));
                line.setValue(KEY_LST_LOWER_QTY, outparam.get(LstItemHistoryDASCHParams.LOWER_QTY));
                line.setValue(KEY_LST_UPDATE_LOWER_QTY, outparam.get(LstItemHistoryDASCHParams.UPDATE_LOWER_QTY));
                line.setValue(KEY_LST_TEMPORARY_TYPE, outparam.get(LstItemHistoryDASCHParams.TEMPORARY_TYPE));
                line.setValue(KEY_LST_UPDATE_TEMPORARY_TYPE, outparam.get(LstItemHistoryDASCHParams.UPDATE_TEMPORARY_TYPE));
                lst_ItemHistoryList_SetLineToolTip(line);
                _lcm_lst_ItemHistoryList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ItemHistoryList.clear();
            disposeDasch();
        }
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
