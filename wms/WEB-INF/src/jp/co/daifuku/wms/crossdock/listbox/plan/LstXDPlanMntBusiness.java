// $Id: LstXDPlanMntBusiness.java 6842 2010-01-22 11:06:59Z kanda $
package jp.co.daifuku.wms.crossdock.listbox.plan;

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
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
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
import jp.co.daifuku.wms.crossdock.dasch.LstXDPlanMntDASCH;
import jp.co.daifuku.wms.crossdock.dasch.LstXDPlanMntDASCHParams;


/**
 * TC予定検索の画面処理を行います。
 *
 * @version $Revision: 6842 $, $Date: 2010-01-22 20:06:59 +0900 (金, 22 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class LstXDPlanMntBusiness
        extends LstXDPlanMnt
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

    /** lst_TcPlanSearchList(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK =
            new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), false, false);

    /** lst_TcPlanSearchList(LST_JAN_CODE) */
    private static final ListCellKey KEY_LST_JAN_CODE =
            new ListCellKey("LST_JAN_CODE", new StringCellColumn(), false, false);

    /** lst_TcPlanSearchList(LST_CASE_ITF) */
    private static final ListCellKey KEY_LST_CASE_ITF =
            new ListCellKey("LST_CASE_ITF", new StringCellColumn(), false, false);

    /** lst_TcPlanSearchList(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 =
            new ListCellKey("LST_COLUMN_1", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_PLAN_DATE) */
    private static final ListCellKey KEY_LST_PLAN_DATE =
            new ListCellKey("LST_PLAN_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_TcPlanSearchList(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_SUPPLIER_CODE) */
    private static final ListCellKey KEY_LST_SUPPLIER_CODE =
            new ListCellKey("LST_SUPPLIER_CODE", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_SUPPLIER_NAME) */
    private static final ListCellKey KEY_LST_SUPPLIER_NAME =
            new ListCellKey("LST_SUPPLIER_NAME", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_RECEIVING_TICKET) */
    private static final ListCellKey KEY_LST_RECEIVING_TICKET =
            new ListCellKey("LST_RECEIVING_TICKET", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_RECEIVING_TICKET_LINE) */
    private static final ListCellKey KEY_LST_RECEIVING_TICKET_LINE =
            new ListCellKey("LST_RECEIVING_TICKET_LINE", new NumberCellColumn(0), true, false);

    /** lst_TcPlanSearchList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_TcPlanSearchList(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);


    /** lst_TcPlanSearchList kyes */
    private static final ListCellKey[] LST_TCPLANSEARCHLIST_KEYS = {
            KEY_LST_CASE_PACK,
            KEY_LST_JAN_CODE,
            KEY_LST_CASE_ITF,
            KEY_LST_COLUMN_1,
            KEY_LST_PLAN_DATE,
            KEY_LST_BATCH,
            KEY_LST_SUPPLIER_CODE,
            KEY_LST_RECEIVING_TICKET,
            KEY_LST_ITEM_CODE,
            KEY_LST_LOT,
            KEY_LST_SUPPLIER_NAME,
            KEY_LST_RECEIVING_TICKET_LINE,
            KEY_LST_ITEM_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_TcPlanSearchList */
    private ListCellModel _lcm_lst_TcPlanSearchList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstXDPlanMntBusiness()
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
    public void lst_TcPlanSearchList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_TcPlanSearchList.getActiveCol();

        // choose process.
        if (_lcm_lst_TcPlanSearchList.getColumnIndex(KEY_LST_COLUMN_1) == activeCol)
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
    public void lst_TcPlanSearchList_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_TcPlanSearchList_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_TcPlanSearchList_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_TcPlanSearchList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_TcPlanSearchList_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_TcPlanSearchList_Change(ActionEvent e)
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
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_TcPlanSearchList.
        _lcm_lst_TcPlanSearchList = new ListCellModel(lst_TcPlanSearchList, LST_TCPLANSEARCHLIST_KEYS, locale);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_PLAN_DATE, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_BATCH, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_SUPPLIER_CODE, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_SUPPLIER_NAME, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_RECEIVING_TICKET, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_RECEIVING_TICKET_LINE, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_TcPlanSearchList.setToolTipVisible(KEY_LST_LOT, false);
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
    private void lst_TcPlanSearchList_SetLineToolTip(ListCellLine line)
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstXDPlanMntDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstXDPlanMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstXDPlanMntDASCHParams inparam = new LstXDPlanMntDASCHParams();
            LstXDPlanMntParams requestParam = new LstXDPlanMntParams(request);
            //            inparam.set(LstXDPlanMntDASCHParams.PLAN_DATE, requestParam.get(LstXDPlanMntParams.PLAN_DATE));
            inparam.set(LstXDPlanMntDASCHParams.BATCH, requestParam.get(LstXDPlanMntParams.BATCH));
            inparam.set(LstXDPlanMntDASCHParams.ITEM_CODE, requestParam.get(LstXDPlanMntParams.ITEM_CODE));
            inparam.set(LstXDPlanMntDASCHParams.ITEM_NAME, requestParam.get(LstXDPlanMntParams.ITEM_NAME));
            // DFKLOOK: DAC modified from here
            inparam.set(LstXDPlanMntDASCHParams.LOT, requestParam.get(LstXDPlanMntParams.LOT));
            // DFKLOOK: DAC modified to here
            inparam.set(LstXDPlanMntDASCHParams.PLAN_DATE, requestParam.get(LstXDPlanMntParams.PLAN_DATE));
            // DFKLOOK: DAC modified from here
            inparam.set(LstXDPlanMntDASCHParams.RECEIVING_TICKET,
                    requestParam.get(LstXDPlanMntParams.RECEIVING_SLIP_NUMBER));
            inparam.set(LstXDPlanMntDASCHParams.RECEIVING_TICKET_LINE, requestParam.get(LstXDPlanMntParams.LINE_NO));
            // DFKLOOK: DAC modified to here
            inparam.set(LstXDPlanMntDASCHParams.SUPPLIER_CODE, requestParam.get(LstXDPlanMntParams.SUPPLIER_CODE));
            inparam.set(LstXDPlanMntDASCHParams.SUPPLIER_NAME, requestParam.get(LstXDPlanMntParams.SUPPLIER_NAME));
            inparam.set(LstXDPlanMntDASCHParams.CONSIGNOR_CODE, requestParam.get(LstXDPlanMntParams.CONSIGNOR_CODE));
            // DAC added from here
            inparam.set(LstXDPlanMntDASCHParams.CASE_PACK, requestParam.get(LstXDPlanMntParams.CASE_PACK));
            inparam.set(LstXDPlanMntDASCHParams.JAN_CODE, requestParam.get(LstXDPlanMntParams.JAN_CODE));
            inparam.set(LstXDPlanMntDASCHParams.CASE_ITF, requestParam.get(LstXDPlanMntParams.CASE_ITF));
            // DAC added to here


            // get count.
            int count = dasch.count(inparam);
            //_pager.clear();
            _pager.setMax(count);
            //int startIndex = _pager.getIndex() -1;

            // new source by New BusiTune from here
            _lcm_lst_TcPlanSearchList.clear();

            if (count == 0)
            {
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
            // new source by New BusiTune from here

            // DASCH call.
            dasch.search(inparam);
            message.setMsgResourceKey(dasch.getMessage());

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());

            _lcm_lst_TcPlanSearchList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanSearchList.getNewLine();
                line.setValue(KEY_LST_COLUMN_1, outparam.get(LstXDPlanMntDASCHParams.COLUMN_1));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(LstXDPlanMntDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH, outparam.get(LstXDPlanMntDASCHParams.BATCH));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(LstXDPlanMntDASCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(LstXDPlanMntDASCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_RECEIVING_TICKET, outparam.get(LstXDPlanMntDASCHParams.RECEIVING_TICKET));
                line.setValue(KEY_LST_RECEIVING_TICKET_LINE,
                        outparam.get(LstXDPlanMntDASCHParams.RECEIVING_TICKET_LINE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstXDPlanMntDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstXDPlanMntDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(LstXDPlanMntDASCHParams.LOT));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(LstXDPlanMntDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_JAN_CODE, outparam.get(LstXDPlanMntDASCHParams.JAN_CODE));
                line.setValue(KEY_LST_CASE_ITF, outparam.get(LstXDPlanMntDASCHParams.CASE_ITF));
                lst_TcPlanSearchList_SetLineToolTip(line);
                _lcm_lst_TcPlanSearchList.add(line);
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
            _lcm_lst_TcPlanSearchList.clear();
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
        LstXDPlanMntDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstXDPlanMntDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            //int startIndex = _pager.getIndex() -1;
            //List<Params> outparams = dasch.get(startIndex, _pager.getLinesPerPage());
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_TcPlanSearchList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanSearchList.getNewLine();
                line.setValue(KEY_LST_COLUMN_1, outparam.get(LstXDPlanMntDASCHParams.COLUMN_1));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(LstXDPlanMntDASCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH, outparam.get(LstXDPlanMntDASCHParams.BATCH));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(LstXDPlanMntDASCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(LstXDPlanMntDASCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_RECEIVING_TICKET, outparam.get(LstXDPlanMntDASCHParams.RECEIVING_TICKET));
                line.setValue(KEY_LST_RECEIVING_TICKET_LINE,
                        outparam.get(LstXDPlanMntDASCHParams.RECEIVING_TICKET_LINE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstXDPlanMntDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstXDPlanMntDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(LstXDPlanMntDASCHParams.LOT));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(LstXDPlanMntDASCHParams.CASE_PACK));
                line.setValue(KEY_LST_JAN_CODE, outparam.get(LstXDPlanMntDASCHParams.JAN_CODE));
                line.setValue(KEY_LST_CASE_ITF, outparam.get(LstXDPlanMntDASCHParams.CASE_ITF));
                lst_TcPlanSearchList_SetLineToolTip(line);
                _lcm_lst_TcPlanSearchList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_TcPlanSearchList.clear();
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
        int row = lst_TcPlanSearchList.getActiveRow();
        lst_TcPlanSearchList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_TcPlanSearchList.get(row);

        // output parameter.
        LstXDPlanMntParams outparam = new LstXDPlanMntParams();
        outparam.set(LstXDPlanMntParams.PLAN_DATE, line.getValue(KEY_LST_PLAN_DATE));
        outparam.set(LstXDPlanMntParams.BATCH, line.getValue(KEY_LST_BATCH));
        outparam.set(LstXDPlanMntParams.SUPPLIER_CODE, line.getValue(KEY_LST_SUPPLIER_CODE));
        outparam.set(LstXDPlanMntParams.SUPPLIER_NAME, line.getValue(KEY_LST_SUPPLIER_NAME));
        outparam.set(LstXDPlanMntParams.RECEIVING_TICKET, line.getValue(KEY_LST_RECEIVING_TICKET));
        outparam.set(LstXDPlanMntParams.RECEIVING_TICKET_LINE, line.getValue(KEY_LST_RECEIVING_TICKET_LINE));
        outparam.set(LstXDPlanMntParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(LstXDPlanMntParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(LstXDPlanMntParams.LOT, line.getValue(KEY_LST_LOT));
        outparam.set(LstXDPlanMntParams.CASE_PACK, line.getValue(KEY_LST_CASE_PACK));
        outparam.set(LstXDPlanMntParams.JAN_CODE, line.getValue(KEY_LST_JAN_CODE));
        outparam.set(LstXDPlanMntParams.CASE_ITF, line.getValue(KEY_LST_CASE_ITF));
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

            // DAC modified from here
            this.lbl_ListName.setResourceKey(title);
            // DAC modified to here
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // If there is no title , get the title key from ResourceKey (page.xml)
            // this is used for POPUP tilte keys

            // DAC modified from here
            this.lbl_ListName.setResourceKey(this.getTitleResourceKey());
            // DAC modified to here
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // Set screen title

            // DAC modified from here
            this.lbl_ListName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
            // DAC modified to here
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
