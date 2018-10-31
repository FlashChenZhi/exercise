// $Id: PctOperationLogBusiness.java 6439 2009-12-11 09:56:30Z kumano $
package jp.co.daifuku.pcart.system.display.pctoperationlog;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.pcart.system.dasch.OperationLogListDASCHParams;
import jp.co.daifuku.pcart.system.dasch.PctOperationLogDASCH;
import jp.co.daifuku.pcart.system.dasch.PctOperationLogDASCHParams;
import jp.co.daifuku.pcart.system.listbox.dsno.DsNoListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 画面操作履歴一覧の画面処理を行います。
 *
 * @version $Revision: 6439 $, $Date:: 2009-12-11 18:56:30 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PctOperationLogBusiness
        extends PctOperationLog
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ScreenControlLog(HIDDEN_PAGENAME_RESOURCE_KEY) */
    private static final ListCellKey KEY_HIDDEN_PAGENAME_RESOURCE_KEY =
            new ListCellKey("HIDDEN_PAGENAME_RESOURCE_KEY", new StringCellColumn(), false, false);

    /** lst_ScreenControlLog(HIDDEN_OPERATION_TYPE) */
    private static final ListCellKey KEY_HIDDEN_OPERATION_TYPE =
            new ListCellKey("HIDDEN_OPERATION_TYPE", new StringCellColumn(), false, false);

    /** lst_ScreenControlLog(HIDDEN_LOG_DAY) */
    private static final ListCellKey KEY_HIDDEN_LOG_DAY =
            new ListCellKey("HIDDEN_LOG_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_MSEC), false, false);

    /** lst_ScreenControlLog(LST_DETAIL) */
    private static final ListCellKey KEY_LST_DETAIL =
            new ListCellKey("LST_DETAIL", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO =
            new ListCellKey("LST_LINE_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_LOG_DAY) */
    private static final ListCellKey KEY_LST_LOG_DAY =
            new ListCellKey("LST_LOG_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_ScreenControlLog(LST_LOG_TIME) */
    private static final ListCellKey KEY_LST_LOG_TIME =
            new ListCellKey("LST_LOG_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_ScreenControlLog(LST_OPERATION_TYPE) */
    private static final ListCellKey KEY_LST_OPERATION_TYPE =
            new ListCellKey("LST_OPERATION_TYPE", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID =
            new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS =
            new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME =
            new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_DS_NO) */
    private static final ListCellKey KEY_LST_DS_NO = new ListCellKey("LST_DS_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_SCREEN_NAME) */
    private static final ListCellKey KEY_LST_SCREEN_NAME =
            new ListCellKey("LST_SCREEN_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog kyes */
    private static final ListCellKey[] LST_SCREENCONTROLLOG_KEYS = {
            KEY_HIDDEN_PAGENAME_RESOURCE_KEY,
            KEY_HIDDEN_OPERATION_TYPE,
            KEY_HIDDEN_LOG_DAY,
            KEY_LST_DETAIL,
            KEY_LST_LINE_NO,
            KEY_LST_LOG_DAY,
            KEY_LST_OPERATION_TYPE,
            KEY_LST_USER_ID,
            KEY_LST_IP_ADDRESS,
            KEY_LST_DS_NO,
            KEY_LST_LOG_TIME,
            KEY_LST_USER_NAME,
            KEY_LST_TERMINAL_NAME,
            KEY_LST_SCREEN_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ScreenControlLog */
    private ListCellModel _lcm_lst_ScreenControlLog;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PctOperationLogBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_P_Search_Click"))
        {
            // process call.
            btn_P_Search_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ScreenControlLog_Click(ActionEvent e)
            throws Exception
    {
        lst_ScreenControlLogDetails_Click_Process();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
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
        btn_Display_Click_SetList();
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
     * 画面入力フィールドの条件チェックを行います。
     * <BR>
     * 概要:<BR>
     *<DIR>
     *    1.検索開始日＜＝検索終了日
     *    2.検索日付、時間の必須チェック、時間が入力されている場合、日付は必須入力
     *@return 入力に不正があればfalse 無ければtrueを返す
     */
    protected boolean checkInput()
    {
        WmsChecker checker = new WmsChecker();

        if (!checker.checkDate(txt_StartSearchDate.getDate(), txt_StartSearchTime.getTime()))
        {
            setFocus(txt_StartSearchDate);
            // 6023009 = 時刻入力時は、日付入力も行ってください。
            message.setMsgResourceKey("6023009");
            return false;
        }
        if (!checker.checkDate(txt_EndSearchDate.getDate(), txt_EndSearchTime.getTime()))
        {
            setFocus(txt_EndSearchDate);
            // 6023009 = 時刻入力時は、日付入力も行ってください。
            message.setMsgResourceKey("6023009");
            return false;
        }
        return true;
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_ScreenControlLog.
        _lcm_lst_ScreenControlLog = new ListCellModel(lst_ScreenControlLog, LST_SCREENCONTROLLOG_KEYS, locale);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_DETAIL, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_LINE_NO, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_LOG_DAY, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_LOG_TIME, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_OPERATION_TYPE, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_USER_ID, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_IP_ADDRESS, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_TERMINAL_NAME, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_DS_NO, true);
        _lcm_lst_ScreenControlLog.setToolTipVisible(KEY_LST_SCREEN_NAME, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ScreenControlLog_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W8006", KEY_LST_TERMINAL_NAME);
        line.addToolTip("LBL-W8007", KEY_LST_SCREEN_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StartSearchDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_RecordCount.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        DsNoListParams inparam = new DsNoListParams();
        inparam.set(DsNoListParams.DS_NO, txt_DSNo.getValue());
        inparam.set(DsNoListParams.FROM_SEARCH_DATE, txt_StartSearchDate.getValue());
        inparam.set(DsNoListParams.FROM_SEARCH_TIME, txt_StartSearchTime.getValue());
        inparam.set(DsNoListParams.TO_SEARCH_DATE, txt_EndSearchDate.getValue());
        inparam.set(DsNoListParams.TO_SEARCH_TIME, txt_EndSearchTime.getValue());
        inparam.set(DsNoListParams.USER_ID, txt_User.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_Click");
        redirect("/pcart/system/listbox/dsno/DsNoList.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        DsNoListParams outparam = new DsNoListParams(dialogParams);
        txt_DSNo.setValue(outparam.get(DsNoListParams.DS_NO));
        txt_R_PageName.setValue(outparam.get(DsNoListParams.SCREEN_NAME));

        // set focus.
        setFocus(txt_DSNo);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 入力チェック        
        if (!checkInput())
        {
            return;
        }

        txt_RecordCount.setText("");
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        PctOperationLogDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new PctOperationLogDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            // DFKLOOK:ここから修正
            Date[] tmp =
                WmsFormatter.getFromTo(txt_StartSearchDate.getDate(), txt_StartSearchTime.getTime(), txt_EndSearchDate.getDate(),
                		txt_EndSearchTime.getTime());
            txt_StartSearchDate.setText(WmsFormatter.toDispDate(tmp[0], locale));
            txt_StartSearchTime.setText(WmsFormatter.toDispTime(tmp[0], locale));
            txt_EndSearchDate.setText(WmsFormatter.toDispDate(tmp[1], locale));
            txt_EndSearchTime.setText(WmsFormatter.toDispTime(tmp[1], locale));
            // DFKLOOK:ここまで修正
            
            PctOperationLogDASCHParams inparam = new PctOperationLogDASCHParams();
            inparam.set(PctOperationLogDASCHParams.FROM_SEARCH_DATE, txt_StartSearchDate.getValue());
            inparam.set(PctOperationLogDASCHParams.FROM_SEARCH_TIME, txt_StartSearchTime.getValue());
            inparam.set(PctOperationLogDASCHParams.TO_SEARCH_DATE, txt_EndSearchDate.getValue());
            inparam.set(PctOperationLogDASCHParams.TO_SEARCH_TIME, txt_EndSearchTime.getValue());
            inparam.set(PctOperationLogDASCHParams.USER_ID, txt_User.getValue());
            inparam.set(PctOperationLogDASCHParams.DS_NO, txt_DSNo.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ScreenControlLog.clear();

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

            // DFKLOOK:ここから修正
            txt_RecordCount.setInt(count);
            // DFKLOOK:ここまで修正

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ScreenControlLog.getNewLine();
                line.setValue(KEY_HIDDEN_PAGENAME_RESOURCE_KEY,
                        outparam.get(PctOperationLogDASCHParams.PAGENAME_RESOURCE_KEY));
                line.setValue(KEY_HIDDEN_OPERATION_TYPE, outparam.get(PctOperationLogDASCHParams.HIDDEN_OPERATION_TYPE));
                line.setValue(KEY_HIDDEN_LOG_DAY, outparam.get(PctOperationLogDASCHParams.HIDDEN_LOG_DAY));
                line.setValue(KEY_LST_DETAIL, outparam.get(PctOperationLogDASCHParams.DETAIL));
                line.setValue(KEY_LST_LINE_NO, outparam.get(PctOperationLogDASCHParams.LINE_NO));
                line.setValue(KEY_LST_LOG_DAY, outparam.get(PctOperationLogDASCHParams.LOG_DAY));
                line.setValue(KEY_LST_LOG_TIME, outparam.get(PctOperationLogDASCHParams.LOG_TIME));
                line.setValue(KEY_LST_OPERATION_TYPE, outparam.get(PctOperationLogDASCHParams.OPERATION_TYPE));
                line.setValue(KEY_LST_USER_ID, outparam.get(PctOperationLogDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(PctOperationLogDASCHParams.USER_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(PctOperationLogDASCHParams.IP_ADDRESS));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(PctOperationLogDASCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_DS_NO, outparam.get(PctOperationLogDASCHParams.DS_NO));
                line.setValue(KEY_LST_SCREEN_NAME, outparam.get(PctOperationLogDASCHParams.SCREEN_NAME));
                lst_ScreenControlLog_SetLineToolTip(line);
                _lcm_lst_ScreenControlLog.add(line);
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
            _lcm_lst_ScreenControlLog.clear();
            if (dasch != null)
            {
                dasch.close();
            }
            DBUtil.close(conn);
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
    private void btn_Display_Click_SetList()
            throws Exception
    {
        PctOperationLogDASCH dasch = null;
        try
        {
            // get session.
            dasch = (PctOperationLogDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_ScreenControlLog.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ScreenControlLog.getNewLine();
                line.setValue(KEY_HIDDEN_PAGENAME_RESOURCE_KEY,
                        outparam.get(PctOperationLogDASCHParams.PAGENAME_RESOURCE_KEY));
                line.setValue(KEY_HIDDEN_OPERATION_TYPE, outparam.get(PctOperationLogDASCHParams.HIDDEN_OPERATION_TYPE));
                line.setValue(KEY_HIDDEN_LOG_DAY, outparam.get(PctOperationLogDASCHParams.HIDDEN_LOG_DAY));
                line.setValue(KEY_LST_DETAIL, outparam.get(PctOperationLogDASCHParams.DETAIL));
                line.setValue(KEY_LST_LINE_NO, outparam.get(PctOperationLogDASCHParams.LINE_NO));
                line.setValue(KEY_LST_LOG_DAY, outparam.get(PctOperationLogDASCHParams.LOG_DAY));
                line.setValue(KEY_LST_LOG_TIME, outparam.get(PctOperationLogDASCHParams.LOG_TIME));
                line.setValue(KEY_LST_OPERATION_TYPE, outparam.get(PctOperationLogDASCHParams.OPERATION_TYPE));
                line.setValue(KEY_LST_USER_ID, outparam.get(PctOperationLogDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(PctOperationLogDASCHParams.USER_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(PctOperationLogDASCHParams.IP_ADDRESS));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(PctOperationLogDASCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_DS_NO, outparam.get(PctOperationLogDASCHParams.DS_NO));
                line.setValue(KEY_LST_SCREEN_NAME, outparam.get(PctOperationLogDASCHParams.SCREEN_NAME));
                lst_ScreenControlLog_SetLineToolTip(line);
                _lcm_lst_ScreenControlLog.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ScreenControlLog.clear();
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_StartSearchDate.setValue(null);
        txt_StartSearchTime.setValue(null);
        txt_EndSearchDate.setValue(null);
        txt_EndSearchTime.setValue(null);
        txt_User.setValue(null);
        txt_DSNo.setValue(null);
        txt_R_PageName.setValue(null);

    }

    /**
     *_lcm_lst_ScreenControlLog
     * @throws Exception
     */
    private void lst_ScreenControlLogDetails_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        Locale locale = httpRequest.getLocale();
        int row = lst_ScreenControlLog.getActiveRow();
        ListCellLine line = _lcm_lst_ScreenControlLog.get(row);

        String fromdDay =
                WmsFormatter.toDispDate(WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_LOG_DAY)), locale);
        String fromdTime =
                WmsFormatter.toDispTime(WmsFormatter.toParamTime((Date)line.getValue(KEY_LST_LOG_TIME)), locale);

        String Date = fromdDay + " " + fromdTime;
        OperationLogListDASCHParams inparam = new OperationLogListDASCHParams();
        inparam.set(OperationLogListDASCHParams.LOG_DAY, Date);
        inparam.set(OperationLogListDASCHParams.HIDDEN_LOG_DAY, line.getValue(KEY_HIDDEN_LOG_DAY));
        inparam.set(OperationLogListDASCHParams.DS_NO, line.getValue(KEY_LST_DS_NO));
        inparam.set(OperationLogListDASCHParams.OPERATION_TYPE, line.getValue(KEY_LST_OPERATION_TYPE));
        inparam.set(OperationLogListDASCHParams.USER_ID, line.getValue(KEY_LST_USER_ID));
        inparam.set(OperationLogListDASCHParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
        inparam.set(OperationLogListDASCHParams.IP_ADDRESS, line.getValue(KEY_LST_IP_ADDRESS));
        inparam.set(OperationLogListDASCHParams.SCREEN_NAME, line.getValue(KEY_LST_SCREEN_NAME));
        inparam.set(OperationLogListDASCHParams.TERMINAL_NAME, line.getValue(KEY_LST_TERMINAL_NAME));
        inparam.set(OperationLogListDASCHParams.PAGENAME_RESOURCE_KEY, line.getValue(KEY_HIDDEN_PAGENAME_RESOURCE_KEY));
        inparam.set(OperationLogListDASCHParams.HIDDEN_OPERATION_TYPE, line.getValue(KEY_HIDDEN_OPERATION_TYPE));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "lst_ScreenControlLog_Click");
        redirect("/pcart/system/listbox/operationlog/OperationLogList.do", forwardParam, "/Progress.do");
        // DFKLOOK:ここまで修正
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
