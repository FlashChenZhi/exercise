package jp.co.daifuku.wms.system.display.workerresultinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import java.io.File;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkContentsPullDownModel;
import jp.co.daifuku.wms.system.dasch.WorkerResultInquiryDASCH;
import jp.co.daifuku.wms.system.dasch.WorkerResultInquiryDASCHParams;
import jp.co.daifuku.wms.system.display.workerresultinquiry.WorkerResultInquiry;
import jp.co.daifuku.wms.system.exporter.WorkerResultInqListParams;
import jp.co.daifuku.wms.system.listbox.user.LstSystemUserParams;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version
 * @author BusiTune 1.0 Generator.
 */
@SuppressWarnings("serial")
public class ViewWorkerResultInquiryBusiness
        extends WorkerResultInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK Preview対応
    /** key */
//    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK Preview対応

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ResultByUserList(LST_WORK_DATE) */
    private static final ListCellKey KEY_LST_WORK_DATE = new ListCellKey("LST_WORK_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_ResultByUserList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_ResultByUserList(LST_WORK_CONTENT) */
    private static final ListCellKey KEY_LST_WORK_CONTENT = new ListCellKey("LST_WORK_CONTENT", new StringCellColumn(), true, false);

    /** lst_ResultByUserList(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME = new ListCellKey("LST_START_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE, DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_ResultByUserList(LST_END_TIME) */
    private static final ListCellKey KEY_LST_END_TIME = new ListCellKey("LST_END_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE, DateCellColumn.TIME_TYPE_SEC), true, false);

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

    /** lst_ResultByUserList kyes */
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
    public ViewWorkerResultInquiryBusiness()
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
        // DFKLOOK Preview対応
        // get event source.
//        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
//        if (eventSource == null)
//        {
//            return;
//        }
//
//        // remove event source.
//        viewState.remove(_KEY_CONFIRMSOURCE);
//
//        // check result.
//        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
//        if (!isExecute)
//        {
//            return;
//        }
//
//        // choose process.
//        if (eventSource.equals("btn_Print_Click"))
//        {
//            // process call.
//            btn_Print_Click_Process(false);
//        }
        // DFKLOOK Preview対応
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
    public void btn_PDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PDisplay_Click_Process();
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
        // DFKLOOK Preview対応
        // btn_Print_Click_Process(true);
        btn_Print_Click_Process();
        // DFKLOOK Preview対応
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
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
        btn_PDisplay_Click_SetList();
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
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
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_DATE, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_USER_NAME, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_CONTENT, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_START_TIME, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_END_TIME, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORK_DURATION, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_QTY_IN_PIECE, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_COUNTS, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_QTY_IN_PIECEHR, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_WORKED_COUNTSHR, false);
        _lcm_lst_ResultByUserList.setToolTipVisible(KEY_LST_RFT, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ResultByUserList_SetLineToolTip(ListCellLine line)
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
    private void btn_PDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        WorkerResultInquiryDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
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
            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            int startIndex = _pager.getIndex() -1;

            // DASCH call.
            dasch.search(inparam);
            message.setMsgResourceKey(dasch.getMessage());

            // output display.
            List<Params> outparams = dasch.get(startIndex, _pager.getLinesPerPage());
            _lcm_lst_ResultByUserList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ResultByUserList.getNewLine();
                line.setValue(KEY_LST_WORK_DATE, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_WORK_CONTENT, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                line.setValue(KEY_LST_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                // DFKLOOK
                // 表示形式がBusiTuneに定義されていない(HHH:mm:ss)形式にため、変更します。
                line.setValue(KEY_LST_WORK_DURATION, DisplayUtil.getTimeToDate(outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION).toString()));
                // line.setValue(KEY_LST_WORK_DURATION, outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION));
                line.setValue(KEY_LST_WORKED_QTY_IN_PIECE, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECE));
                line.setValue(KEY_LST_WORKED_COUNTS, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTS));
                line.setValue(KEY_LST_WORKED_QTY_IN_PIECEHR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECEHR));
                line.setValue(KEY_LST_WORKED_COUNTSHR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTSHR));
                line.setValue(KEY_LST_RFT, outparam.get(WorkerResultInquiryDASCHParams.RFT));
                lst_ResultByUserList_SetLineToolTip(line);
                _lcm_lst_ResultByUserList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                message.setMsgResourceKey(dasch.getMessage());
            }
            _pager.clear();
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
    private void btn_PDisplay_Click_SetList()
            throws Exception
    {
        WorkerResultInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (WorkerResultInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            int startIndex = _pager.getIndex() -1;
            List<Params> outparams = dasch.get(startIndex, _pager.getLinesPerPage());
            _lcm_lst_ResultByUserList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ResultByUserList.getNewLine();
                line.setValue(KEY_LST_WORK_DATE, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                line.setValue(KEY_LST_USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_WORK_CONTENT, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                line.setValue(KEY_LST_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                // DFKLOOK
                // 表示形式がBusiTuneに定義されていない(HHH:mm:ss)形式にため、変更します。
                line.setValue(KEY_LST_WORK_DURATION, DisplayUtil.getTimeToDate(outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION).toString()));
                // line.setValue(KEY_LST_WORK_DURATION, outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION));
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
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                message.setMsgResourceKey(dasch.getMessage());
            }
            _pager.clear();
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
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    // DFKLOOK Preview対応
    // private void btn_Print_Click_Process(boolean confirm)
    private void btn_Print_Click_Process()
    // DFKLOOK Preview対応
            throws Exception
    {
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
            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            // DFKLOOK Preview対応
//            if (confirm && count > 0)
//            {
//                // show confirm message.
//                this.setConfirm(dasch.getDispMessage(), false, true);
//                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
//                return;
//            }
//            else if (count == 0)
            if (count == 0)
            // DFKLOOK Preview対応
            {
                message.setMsgResourceKey(dasch.getMessage());
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            // DFKLOOK Preview対応
            exporter = factory.newPVExporter("WorkerResultInqList");
            File downloadFile = exporter.open();
//            exporter = factory.newPrinterExporter("WorkerResultInqList", false);
//            exporter.open();
            // DFKLOOK Preview対応

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                WorkerResultInqListParams expparam = new WorkerResultInqListParams();
                expparam.set(WorkerResultInqListParams.DFK_DS_NO, outparam.get(WorkerResultInquiryDASCHParams.DFK_DS_NO));
                expparam.set(WorkerResultInqListParams.DFK_USER_ID, outparam.get(WorkerResultInquiryDASCHParams.DFK_USER_ID));
                expparam.set(WorkerResultInqListParams.DFK_USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(WorkerResultInqListParams.FROM_WORK_DAY, txt_WorkDate.getValue());
                expparam.set(WorkerResultInqListParams.TO_WORK_DAY, txt_WorkDateTo.getValue());
                expparam.set(WorkerResultInqListParams.SYS_DAY, outparam.get(WorkerResultInquiryDASCHParams.SYS_DAY));
                expparam.set(WorkerResultInqListParams.SYS_TIME, outparam.get(WorkerResultInquiryDASCHParams.SYS_TIME));
                expparam.set(WorkerResultInqListParams.SORT_CONDITION, outparam.get(WorkerResultInquiryDASCHParams.SORT_CONDITION));
                // DFKLOOK
                // Radio-Buttonから名称取得が必要なため、ロジックを挿入します。
                // expparam.set(WorkerResultInqListParams.SORT_CONDITION, getCondtionName(_grp_GroupCondition.getSelectedValue()));
                expparam.set(WorkerResultInqListParams.WORK_DAY, outparam.get(WorkerResultInquiryDASCHParams.WORK_DAY));
                expparam.set(WorkerResultInqListParams.USER_NAME, outparam.get(WorkerResultInquiryDASCHParams.USER_NAME));
                expparam.set(WorkerResultInqListParams.WORK_DETAIL, outparam.get(WorkerResultInquiryDASCHParams.WORK_CONTENT));
                expparam.set(WorkerResultInqListParams.WORK_START_TIME, outparam.get(WorkerResultInquiryDASCHParams.START_TIME));
                expparam.set(WorkerResultInqListParams.WORK_END_TIME, outparam.get(WorkerResultInquiryDASCHParams.END_TIME));
                // DFKLOOK
                // 表示形式がBusiTuneに定義されていない(HHH:mm:ss)形式にため、変更します。
                expparam.set(WorkerResultInqListParams.REAL_WORK_TIME, DisplayUtil.getTimeToDate(outparam.get(WorkerResultInquiryDASCHParams.WORK_DURATION).toString()));
                // expparam.set(WorkerResultInqListParams.REAL_WORK_TIME, outparam.get(WorkerResultInquiryDASCHParams.REAL_WORK_TIME));
                expparam.set(WorkerResultInqListParams.WORK_QTY, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECE));
                expparam.set(WorkerResultInqListParams.WORK_CNT, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTS));
                expparam.set(WorkerResultInqListParams.WORK_QTY_HOUR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_QTY_IN_PIECEHR));
                expparam.set(WorkerResultInqListParams.WORK_CNT_HOUR, outparam.get(WorkerResultInquiryDASCHParams.WORKED_COUNTSHR));
                expparam.set(WorkerResultInqListParams.TERMINAL_NO, outparam.get(WorkerResultInquiryDASCHParams.RFT));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // DFKLOOK Preview対応
//            exporter.print();
//            message.setMsgResourceKey(dasch.getMessage());
            downloadFile = exporter.print();

            // redirect.
            previewPDF(downloadFile.getPath());
            // DFKLOOK Preview対応

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                message.setMsgResourceKey(dasch.getMessage());
            }
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
            inparam.set(WorkerResultInquiryDASCHParams.GROUP_CONDITION, _grp_GroupCondition.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey(dasch.getMessage());
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("WorkerResultInqList");
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                WorkerResultInqListParams expparam = new WorkerResultInqListParams();
                expparam.set(WorkerResultInqListParams.SORT_CONDITION, outparam.get(WorkerResultInquiryDASCHParams.SORT_CONDITION));
                // DFKLOOK
                // Radio-Buttonから名称取得が必要なため、ロジックを挿入します。
                // expparam.set(WorkerResultInqListParams.SORT_CONDITION, getCondtionName(_grp_GroupCondition.getSelectedValue()));
                expparam.set(WorkerResultInqListParams.FROM_WORK_DAY, txt_WorkDate.getValue());
                expparam.set(WorkerResultInqListParams.TO_WORK_DAY, txt_WorkDateTo.getValue());
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
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                message.setMsgResourceKey(dasch.getMessage());
            }
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

    /**
     * 集約条件値より、名称を取得します。
     * @param pCode Condition Code:集約条件コード
     * @return String Condition Name:集約条件名称
     */
    private String getCondtionName(Object pCode)
    {
        String pName = null;
        
        if (pCode.equals(SystemInParameter.COLLECT_CONDITION_TEAM))
        {
            // 期間内合計表示
            pName =  DisplayText.getText("TLE-W1503");
        }
        else if (pCode.equals(SystemInParameter.COLLECT_CONDITION_DAILY))
        {
            // 日別合計表示
            pName = DisplayText.getText("TLE-W1504");
        }
        else if (pCode.equals(SystemInParameter.COLLECT_CONDITION_DETAIL))
        {
            // 詳細表示
            pName = DisplayText.getText("TLE-W1505");
        }
        
        return pName;

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
