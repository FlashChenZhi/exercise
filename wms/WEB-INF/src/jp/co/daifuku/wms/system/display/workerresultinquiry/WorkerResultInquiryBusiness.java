// $Id: WorkerResultInquiryBusiness.java 7521 2010-03-13 05:53:55Z shibamoto $
package jp.co.daifuku.wms.system.display.workerresultinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkContentsPullDownModel;
import jp.co.daifuku.wms.system.dasch.WorkerResultInquiryDASCH;
import jp.co.daifuku.wms.system.dasch.WorkerResultInquiryDASCHParams;
import jp.co.daifuku.wms.system.exporter.WorkerResultInqListParams;
import jp.co.daifuku.wms.system.listbox.user.LstSystemUserParams;

/**
 * ユーザ別実績照会の画面処理を行います。
 *
 * @version $Revision: 7521 $, $Date: 2010-03-13 14:53:55 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class WorkerResultInquiryBusiness
        extends WorkerResultInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ResultByUserList(LST_WORK_DATE) */
    private static final ListCellKey KEY_LST_WORK_DATE = new ListCellKey("LST_WORK_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ResultByUserList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_ResultByUserList(LST_WORK_CONTENT) */
    private static final ListCellKey KEY_LST_WORK_CONTENT = new ListCellKey("LST_WORK_CONTENT", new StringCellColumn(), true, false);

    /** lst_ResultByUserList(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME = new ListCellKey("LST_START_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_ResultByUserList(LST_END_TIME) */
    private static final ListCellKey KEY_LST_END_TIME = new ListCellKey("LST_END_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_ResultByUserList(LST_WORK_DURATION) */
    private static final ListCellKey KEY_LST_WORK_DURATION = new ListCellKey("LST_WORK_DURATION", new StringCellColumn(), true, false);

    /** lst_ResultByUserList(LST_WORKED_QTY_IN_PIECE) */
    private static final ListCellKey KEY_LST_WORKED_QTY_IN_PIECE = new ListCellKey("LST_WORKED_QTY_IN_PIECE", new NumberCellColumn(0), true, false);

    /** lst_ResultByUserList(LST_WORKED_COUNTS) */
    private static final ListCellKey KEY_LST_WORKED_COUNTS = new ListCellKey("LST_WORKED_COUNTS", new NumberCellColumn(0), true, false);

    /** lst_ResultByUserList(LST_WORKED_QTY_IN_PIECEHR) */
    private static final ListCellKey KEY_LST_WORKED_QTY_IN_PIECEHR = new ListCellKey("LST_WORKED_QTY_IN_PIECEHR", new NumberCellColumn(0), true, false);

    /** lst_ResultByUserList(LST_WORKED_COUNTSHR) */
    private static final ListCellKey KEY_LST_WORKED_COUNTSHR = new ListCellKey("LST_WORKED_COUNTSHR", new NumberCellColumn(0), true, false);

    /** lst_ResultByUserList(LST_RFT) */
    private static final ListCellKey KEY_LST_RFT = new ListCellKey("LST_RFT", new StringCellColumn(), true, false);

    /** lst_ResultByUserList keys */
    private static final ListCellKey[] LST_RESULTBYUSERLIST_KEYS = {
        KEY_LST_WORK_DATE,
        KEY_LST_USER_NAME,
        KEY_LST_WORK_CONTENT,
        KEY_LST_START_TIME,
        KEY_LST_WORK_DURATION,
        KEY_LST_WORKED_QTY_IN_PIECE,
        KEY_LST_WORKED_QTY_IN_PIECEHR,
        KEY_LST_RFT,
        KEY_LST_END_TIME,
        KEY_LST_WORKED_COUNTS,
        KEY_LST_WORKED_COUNTSHR,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_FWorkContents */
    private WmsWorkContentsPullDownModel _pdm_pul_FWorkContents;

    /** RadioButtonGroupModel GroupCondition */
    private RadioButtonGroup _grp_GroupCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ResultByUserList */
    private ListCellModel _lcm_lst_ResultByUserList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public WorkerResultInquiryBusiness()
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
        if (eventSource.equals("btn_PSearchUserName_Click"))
        {
            // process call.
            btn_PSearchUserName_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchUserName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchUserName_Click_Process();
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_FWorkContents.
        _pdm_pul_FWorkContents = new WmsWorkContentsPullDownModel(pul_FWorkContents, locale, ui);

        // initialize GroupCondition.
        _grp_GroupCondition = new RadioButtonGroup(new RadioButton[]{rdo_DetailDisplay, rdo_TotalDisplayDayUnit, rdo_TotalDisplayInPeriod}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ResultByUserList.
        _lcm_lst_ResultByUserList = new ListCellModel(lst_ResultByUserList, LST_RESULTBYUSERLIST_KEYS, locale);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_DATE, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_CONTENT, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_START_TIME, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_END_TIME, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_DURATION, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_QTY_IN_PIECE, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_COUNTS, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_QTY_IN_PIECEHR, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_COUNTSHR, true);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_RFT, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_FWorkContents.
            _pdm_pul_FWorkContents.init(conn, true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
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
    private void lst_ResultByUserList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
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
        setFocus(pul_FWorkContents);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_TotalDisplayDayUnit.setChecked(true);
        txt_UserName.setReadOnly(true);
        _pager.clear();
        _pager.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchUserName_Click_Process()
            throws Exception
    {
        // show dialog.
        ForwardParameters forwardParam = new ForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchUserName_Click");
        redirect("/system/listbox/user/LstSystemUser.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchUserName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstSystemUserParams outparam = new LstSystemUserParams(dialogParams);
        txt_UserName.setValue(outparam.get(LstSystemUserParams.USER_NAME));

        // set focus.
        setFocus(txt_UserName);

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
        WorkerResultInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new WorkerResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            WorkerResultInquiryDASCHParams inparam = new WorkerResultInquiryDASCHParams();
            inparam.set(WorkerResultInquiryDASCHParams.F_WORK_CONTENTS, _pdm_pul_FWorkContents.getSelectedValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY, txt_WorkDate.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY_TO, txt_WorkDateTo.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            // DFKLOOK
            if (rdo_DetailDisplay.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DETAIL);
            }
            else if (rdo_TotalDisplayDayUnit.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DAILY);
            }
            else if (rdo_TotalDisplayInPeriod.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_TEAM);
            }
            //            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ResultByUserList.clear();

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
            _lcm_lst_ResultByUserList.clear();
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
        WorkerResultInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (WorkerResultInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ResultByUserList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ResultByUserList.getNewLine();
                line.setValue(KEY_LST_WORK_DATE, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_WORK_CONTENT, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                line.setValue(KEY_LST_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                line.setValue(KEY_LST_WORK_DURATION, outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION));
                line.setValue(KEY_LST_WORKED_QTY_IN_PIECE, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECE));
                line.setValue(KEY_LST_WORKED_COUNTS, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTS));
                line.setValue(KEY_LST_WORKED_QTY_IN_PIECEHR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECEHR));
                line.setValue(KEY_LST_WORKED_COUNTSHR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTSHR));
                line.setValue(KEY_LST_RFT, outparam.get(WorkerResultInquiryDASCHParams.RFT));
                lst_ResultByUserList_SetLineToolTip(line);
                _lcm_lst_ResultByUserList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ResultByUserList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        pul_FWorkContents.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkerResultInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new WorkerResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            WorkerResultInquiryDASCHParams inparam = new WorkerResultInquiryDASCHParams();
            inparam.set(WorkerResultInquiryDASCHParams.F_WORK_CONTENTS, _pdm_pul_FWorkContents.getSelectedValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY, txt_WorkDate.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY_TO, txt_WorkDateTo.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            // DFKLOOK
            if (rdo_DetailDisplay.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DETAIL);
            }
            else if (rdo_TotalDisplayDayUnit.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DAILY);
            }
            else if (rdo_TotalDisplayInPeriod.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_TEAM);
            }
            //            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("WorkerResultInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                WorkerResultInqListParams expparam = new WorkerResultInqListParams();
                expparam.set(WorkerResultInqListParams.DFK_DS_NO, outparam.get(WorkerResultInquiryDASCHParams.DFK_DS_NO));
                expparam.set(WorkerResultInqListParams.DFK_USER_ID, outparam.get(WorkerResultInquiryDASCHParams.DFK_USER_ID));
                expparam.set(WorkerResultInqListParams.DFK_USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(WorkerResultInqListParams.SYS_DAY, outparam.get(WorkerResultInquiryDASCHParams.SYS_DAY));
                expparam.set(WorkerResultInqListParams.SYS_TIME, outparam.get(WorkerResultInquiryDASCHParams.SYS_TIME));
                expparam.set(WorkerResultInqListParams.SORT_CONDITION, outparam.get(WorkerResultInquiryDASCHParams.SORT_CONDITION));

                // DFKLOOK：ここから修正
                String[] day = null;
                day = WmsFormatter.getFromTo(txt_WorkDate.getText(), txt_WorkDateTo.getText());
                expparam.set(WorkerResultInqListParams.FROM_WORK_DAY, day[0]);
                expparam.set(WorkerResultInqListParams.TO_WORK_DAY, day[1]);
                // DFKLOOK：ここまで修正

                expparam.set(WorkerResultInqListParams.WORK_DAY, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                expparam.set(WorkerResultInqListParams.USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                expparam.set(WorkerResultInqListParams.WORK_DETAIL, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                expparam.set(WorkerResultInqListParams.WORK_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                expparam.set(WorkerResultInqListParams.WORK_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                expparam.set(WorkerResultInqListParams.REAL_WORK_TIME, outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION));
                expparam.set(WorkerResultInqListParams.WORK_QTY, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECE));
                expparam.set(WorkerResultInqListParams.WORK_CNT, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTS));
                expparam.set(WorkerResultInqListParams.WORK_QTY_HOUR,
                        outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECEHR));
                expparam.set(WorkerResultInqListParams.WORK_CNT_HOUR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTSHR));
                expparam.set(WorkerResultInqListParams.TERMINAL_NO, outparam.get(WorkerResultInquiryDASCHParams.RFT));
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
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_FWorkContents.getSelectedStringValue(), "");
            part11List.add(txt_WorkDate.getStringValue(), "");
            part11List.add(txt_WorkDateTo.getStringValue(), "");
            part11List.add(txt_UserName.getStringValue(), "");
            part11List.add(Parameter.COLLECT_CONDITION_DETAIL, "", rdo_DetailDisplay.getChecked());
            part11List.add(Parameter.COLLECT_CONDITION_DAILY, "", rdo_TotalDisplayDayUnit.getChecked());
            part11List.add(Parameter.COLLECT_CONDITION_TEAM, "", rdo_TotalDisplayInPeriod.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
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
        // input validation.
        pul_FWorkContents.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkerResultInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new WorkerResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            WorkerResultInquiryDASCHParams inparam = new WorkerResultInquiryDASCHParams();
            inparam.set(WorkerResultInquiryDASCHParams.F_WORK_CONTENTS, _pdm_pul_FWorkContents.getSelectedValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY, txt_WorkDate.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.WORK_DAY_TO, txt_WorkDateTo.getValue());
            inparam.set(WorkerResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            // DFKLOOK
            if (rdo_DetailDisplay.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DETAIL);
            }
            else if (rdo_TotalDisplayDayUnit.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_DAILY);
            }
            else if (rdo_TotalDisplayInPeriod.getChecked())
            {
                inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, Parameter.COLLECT_CONDITION_TEAM);
            }
            //            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("WorkerResultInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                WorkerResultInqListParams expparam = new WorkerResultInqListParams();

                expparam.set(WorkerResultInqListParams.WORK_DAY, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                expparam.set(WorkerResultInqListParams.USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                expparam.set(WorkerResultInqListParams.WORK_DETAIL, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                expparam.set(WorkerResultInqListParams.WORK_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                expparam.set(WorkerResultInqListParams.WORK_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                expparam.set(WorkerResultInqListParams.REAL_WORK_TIME, outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION));
                expparam.set(WorkerResultInqListParams.WORK_QTY, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECE));
                expparam.set(WorkerResultInqListParams.WORK_CNT, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTS));
                expparam.set(WorkerResultInqListParams.WORK_QTY_HOUR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECEHR));
                expparam.set(WorkerResultInqListParams.WORK_CNT_HOUR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTSHR));
                expparam.set(WorkerResultInqListParams.TERMINAL_NO, outparam.get(WorkerResultInquiryDASCHParams.RFT));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(count) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_FWorkContents.getSelectedStringValue(), "");
            part11List.add(txt_WorkDate.getStringValue(), "");
            part11List.add(txt_WorkDateTo.getStringValue(), "");
            part11List.add(txt_UserName.getStringValue(), "");
            part11List.add(Parameter.COLLECT_CONDITION_DETAIL, "", rdo_DetailDisplay.getChecked());
            part11List.add(Parameter.COLLECT_CONDITION_DAILY, "", rdo_TotalDisplayDayUnit.getChecked());
            part11List.add(Parameter.COLLECT_CONDITION_TEAM, "", rdo_TotalDisplayInPeriod.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
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
            if (dasch != null)
            {
                dasch.close();
            }
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_FWorkContents.setSelectedValue(null);
        txt_WorkDate.setValue(null);
        txt_WorkDateTo.setValue(null);
        txt_UserName.setValue(null);
        rdo_TotalDisplayDayUnit.setChecked(true);

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
