// $Id: LstReceivingPlanMntBusiness.java 6850 2010-01-22 11:08:42Z kanda $
package jp.co.daifuku.wms.receiving.listbox.plan;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
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
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.receiving.dasch.LstReceivingPlanMntDASCH;
import jp.co.daifuku.wms.receiving.dasch.LstReceivingPlanMntDASCHParams;

/**
 * 入荷予定検索の画面処理を行います。
 *
 * @version $Revision: 6850 $, $Date: 2010-01-22 20:08:42 +0900 (金, 22 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class LstReceivingPlanMntBusiness
        extends LstReceivingPlanMnt
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

    /** lst_ReceivingPlanSearch(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanSearch(LST_PLAN_DATE) */
    private static final ListCellKey KEY_LST_PLAN_DATE = new ListCellKey("LST_PLAN_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_ReceivingPlanSearch(LST_SUPPLIER_CODE) */
    private static final ListCellKey KEY_LST_SUPPLIER_CODE = new ListCellKey("LST_SUPPLIER_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanSearch(LST_SUPPLIER_NAME) */
    private static final ListCellKey KEY_LST_SUPPLIER_NAME = new ListCellKey("LST_SUPPLIER_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanSearch(LST_TICKET) */
    private static final ListCellKey KEY_LST_TICKET = new ListCellKey("LST_TICKET", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanSearch kyes */
    private static final ListCellKey[] LST_RECEIVINGPLANSEARCH_KEYS = {
            KEY_LST_COLUMN_1,
            KEY_LST_PLAN_DATE,
            KEY_LST_SUPPLIER_CODE,
            KEY_LST_TICKET,
            KEY_LST_SUPPLIER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ReceivingPlanSearch */
    private ListCellModel _lcm_lst_ReceivingPlanSearch;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstReceivingPlanMntBusiness()
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
    public void lbl_ListName_Server(ActionEvent e)
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
    public void btn_Close_U_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ReceivingPlanSearch.getActiveCol();

        // choose process.
        if (_lcm_lst_ReceivingPlanSearch.getColumnIndex(KEY_LST_COLUMN_1) == activeCol)
        {
            // process call.
            lst_Column_1_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanSearch_Change(ActionEvent e)
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
    public void btn_Close_D_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
    }

    /**
     * Menu button click event handling.

     * @param e ActionEvent, event information
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // Delete Connection From Session
        SessionUtil.deleteSession(getSession());
        // Forward to Submenu screen
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ReceivingPlanSearch.
        _lcm_lst_ReceivingPlanSearch = new ListCellModel(lst_ReceivingPlanSearch, LST_RECEIVINGPLANSEARCH_KEYS, locale);
        _lcm_lst_ReceivingPlanSearch.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_ReceivingPlanSearch.setToolTipVisible(KEY_LST_PLAN_DATE, false);
        _lcm_lst_ReceivingPlanSearch.setToolTipVisible(KEY_LST_SUPPLIER_CODE, false);
        _lcm_lst_ReceivingPlanSearch.setToolTipVisible(KEY_LST_SUPPLIER_NAME, false);
        _lcm_lst_ReceivingPlanSearch.setToolTipVisible(KEY_LST_TICKET, false);

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
    private void lst_ReceivingPlanSearch_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        LstReceivingPlanMntDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstReceivingPlanMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstReceivingPlanMntDASCHParams inparam = new LstReceivingPlanMntDASCHParams();
            LstReceivingPlanMntParams requestParam = new LstReceivingPlanMntParams(request);
            inparam.set(LstReceivingPlanMntDASCHParams.CONSIGNOR_CODE, requestParam.get(LstReceivingPlanMntParams.CONSIGNOR_CODE));
            inparam.set(LstReceivingPlanMntDASCHParams.PLAN_DATE, requestParam.get(LstReceivingPlanMntParams.PLAN_DATE));
            inparam.set(LstReceivingPlanMntDASCHParams.RECEIVING_PLAN_DATE, requestParam.get(LstReceivingPlanMntParams.RECEIVING_PLAN_DATE));
            inparam.set(LstReceivingPlanMntDASCHParams.SLIP_NUMBER, requestParam.get(LstReceivingPlanMntParams.SLIP_NUMBER));
            inparam.set(LstReceivingPlanMntDASCHParams.SUPPLIER_CODE, requestParam.get(LstReceivingPlanMntParams.SUPPLIER_CODE));
            inparam.set(LstReceivingPlanMntDASCHParams.SUPPLIER_NAME, requestParam.get(LstReceivingPlanMntParams.SUPPLIER_NAME));
            inparam.set(LstReceivingPlanMntDASCHParams.TICKET, requestParam.get(LstReceivingPlanMntParams.TICKET));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_ReceivingPlanSearch.clear();

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

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingPlanSearch.getNewLine();
                line.setValue(KEY_LST_COLUMN_1, outparam.get(LstReceivingPlanMntDASCHParams.COLUMN_1));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(LstReceivingPlanMntDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(LstReceivingPlanMntDASCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(LstReceivingPlanMntDASCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_TICKET, outparam.get(LstReceivingPlanMntDASCHParams.TICKET));
                lst_ReceivingPlanSearch_SetLineToolTip(line);
                _lcm_lst_ReceivingPlanSearch.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ReceivingPlanSearch.clear();
        }
        finally
        {
            // DFKLOOK finally追加
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstReceivingPlanMntDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstReceivingPlanMntDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ReceivingPlanSearch.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingPlanSearch.getNewLine();
                line.setValue(KEY_LST_COLUMN_1, outparam.get(LstReceivingPlanMntDASCHParams.COLUMN_1));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(LstReceivingPlanMntDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(LstReceivingPlanMntDASCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(LstReceivingPlanMntDASCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_TICKET, outparam.get(LstReceivingPlanMntDASCHParams.TICKET));
                lst_ReceivingPlanSearch_SetLineToolTip(line);
                _lcm_lst_ReceivingPlanSearch.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ReceivingPlanSearch.clear();
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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
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
    private void lst_Column_1_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ReceivingPlanSearch.getActiveRow();
        lst_ReceivingPlanSearch.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ReceivingPlanSearch.get(row);

        // output parameter.
        LstReceivingPlanMntParams outparam = new LstReceivingPlanMntParams();
        outparam.set(LstReceivingPlanMntParams.PLAN_DATE, line.getValue(KEY_LST_PLAN_DATE));
        outparam.set(LstReceivingPlanMntParams.SUPPLIER_CODE, line.getValue(KEY_LST_SUPPLIER_CODE));
        outparam.set(LstReceivingPlanMntParams.SUPPLIER_NAME, line.getValue(KEY_LST_SUPPLIER_NAME));
        outparam.set(LstReceivingPlanMntParams.TICKET, line.getValue(KEY_LST_TICKET));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
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
     * Displays Screen title.
     *
     * @throws Exception
     */
    private void setTitle()
            throws Exception
    {
        // get Menu parameters from httpRequest
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // update Title information to view state to display when screen is refreshed
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            // DAC Modifiyed from here
            //lbl_SettingName.setResourceKey(title);
            this.lbl_ListName.setResourceKey(title);
            // DAC Modifiyed from here

        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // If there is no title , get the title key from ResourceKey (page.xml)
            // this is used for POPUP tilte keys
            // DAC Modifiyed from here
            this.lbl_ListName.setResourceKey(this.getTitleResourceKey());
            // DAC Modifiyed from here

        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // Set screen title
            // DAC Modifiyed from here
            // lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
            this.lbl_ListName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
            // DAC Modifiyed from here
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
