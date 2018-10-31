// $Id: PctOperationLogBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.system.display.pctoperationlog;

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
import jp.co.daifuku.pcart.system.dasch.PctOperationLogDASCH;
import jp.co.daifuku.pcart.system.dasch.PctOperationLogDASCHParams;
import jp.co.daifuku.pcart.system.display.pctoperationlog.PctOperationLog;
import jp.co.daifuku.pcart.system.listbox.dsno.DsNoListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
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
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ScreenControlLog(HIDDEN_PAGENAME_RESOURCE_KEY) */
    private static final ListCellKey KEY_HIDDEN_PAGENAME_RESOURCE_KEY = new ListCellKey("HIDDEN_PAGENAME_RESOURCE_KEY", new StringCellColumn(), false, false);

    /** lst_ScreenControlLog(HIDDEN_OPERATION_TYPE) */
    private static final ListCellKey KEY_HIDDEN_OPERATION_TYPE = new ListCellKey("HIDDEN_OPERATION_TYPE", new StringCellColumn(), false, false);

    /** lst_ScreenControlLog(HIDDEN_LOG_DAY) */
    private static final ListCellKey KEY_HIDDEN_LOG_DAY = new ListCellKey("HIDDEN_LOG_DAY", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_ScreenControlLog(LST_DETAIL) */
    private static final ListCellKey KEY_LST_DETAIL = new ListCellKey("LST_DETAIL", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_LOG_DAY) */
    private static final ListCellKey KEY_LST_LOG_DAY = new ListCellKey("LST_LOG_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ScreenControlLog(LST_LOG_TIME) */
    private static final ListCellKey KEY_LST_LOG_TIME = new ListCellKey("LST_LOG_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_ScreenControlLog(LST_OPERATION_TYPE) */
    private static final ListCellKey KEY_LST_OPERATION_TYPE = new ListCellKey("LST_OPERATION_TYPE", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS = new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME = new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_DS_NO) */
    private static final ListCellKey KEY_LST_DS_NO = new ListCellKey("LST_DS_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog(LST_SCREEN_NAME) */
    private static final ListCellKey KEY_LST_SCREEN_NAME = new ListCellKey("LST_SCREEN_NAME", new StringCellColumn(), true, false);

    /** lst_ScreenControlLog keys */
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

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
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PctOperationLogDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new PctOperationLogDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
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
            _lcm_lst_ScreenControlLog.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
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
    private void btn_Display_Click_SetList()
            throws Exception
    {
        PctOperationLogDASCH dasch = null;
        try
        {
            // get session.
            dasch = (PctOperationLogDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ScreenControlLog.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ScreenControlLog.getNewLine();
                line.setValue(KEY_HIDDEN_PAGENAME_RESOURCE_KEY, outparam.get(PctOperationLogDASCHParams.PAGENAME_RESOURCE_KEY));
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
