// $Id: XDProgressBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.crossdock.display.progress;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.crossdock.display.progress.ViewStateKeys;
import jp.co.daifuku.wms.crossdock.display.progress.XDProgress;
import jp.co.daifuku.wms.crossdock.schedule.XDProgressSCH;
import jp.co.daifuku.wms.crossdock.schedule.XDProgressSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class XDProgressBusiness
        extends XDProgress
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_TcPlanProgress(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_TcPlanProgress(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_RECEIVE_DETAIL_COUNT = new ListCellKey("LST_RECEIVE_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_SORT_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_SORT_DETAIL_COUNT = new ListCellKey("LST_SORT_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_CASE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVE_CASE_QTY = new ListCellKey("LST_RECEIVE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVE_PIECE_QTY = new ListCellKey("LST_RECEIVE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_SORT_CASE_QTY) */
    private static final ListCellKey KEY_LST_SORT_CASE_QTY = new ListCellKey("LST_SORT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_SORT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SORT_PIECE_QTY = new ListCellKey("LST_SORT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_TcPlanProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 = new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_RECEIVE_DETAIL_COUNT_2 = new ListCellKey("LST_RECEIVE_DETAIL_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_SORT_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_SORT_DETAIL_COUNT_2 = new ListCellKey("LST_SORT_DETAIL_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_RECEIVE_CASE_QTY_2 = new ListCellKey("LST_RECEIVE_CASE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_RECEIVE_PIECE_QTY_2 = new ListCellKey("LST_RECEIVE_PIECE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_SORT_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_SORT_CASE_QTY_2 = new ListCellKey("LST_SORT_CASE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_SORT_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_SORT_PIECE_QTY_2 = new ListCellKey("LST_SORT_PIECE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress2(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_TcPlanProgress keys */
    private static final ListCellKey[] LST_TCPLANPROGRESS_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_RECEIVE_DETAIL_COUNT,
        KEY_LST_RECEIVE_CASE_QTY,
        KEY_LST_SORT_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_SORT_DETAIL_COUNT,
        KEY_LST_RECEIVE_PIECE_QTY,
        KEY_LST_SORT_PIECE_QTY,
    };

    /** lst_TcPlanProgress2 keys */
    private static final ListCellKey[] LST_TCPLANPROGRESS2_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_BATCH_NO_2,
        KEY_LST_RECEIVE_DETAIL_COUNT_2,
        KEY_LST_RECEIVE_CASE_QTY_2,
        KEY_LST_SORT_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_SORT_DETAIL_COUNT_2,
        KEY_LST_RECEIVE_PIECE_QTY_2,
        KEY_LST_SORT_PIECE_QTY_2,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** RadioButtonGroupModel Group */
    private RadioButtonGroup _grp_Group;

    /** ListCellModel lst_TcPlanProgress */
    private ListCellModel _lcm_lst_TcPlanProgress;

    /** ListCellModel lst_TcPlanProgress2 */
    private ListCellModel _lcm_lst_TcPlanProgress2;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public XDProgressBusiness()
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
    public void btn_PrevPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevPage_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextPage_Click_Process();
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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_Auto, rdo_Manual}, locale);

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[]{rdo_PlanDateBatchNoUnit, rdo_PlanDateUnit}, locale);

        // initialize lst_TcPlanProgress.
        _lcm_lst_TcPlanProgress = new ListCellModel(lst_TcPlanProgress, LST_TCPLANPROGRESS_KEYS, locale);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_DETAIL_COUNT, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_DETAIL_COUNT, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_CASE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_PIECE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_CASE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_PIECE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_TcPlanProgress2.
        _lcm_lst_TcPlanProgress2 = new ListCellModel(lst_TcPlanProgress2, LST_TCPLANPROGRESS2_KEYS, locale);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_DETAIL_COUNT_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_DETAIL_COUNT_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_CASE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_PIECE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_CASE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_PIECE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);
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
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_TcPlanProgress_SetLineToolTip(ListCellLine line)
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
    private void lst_TcPlanProgress2_SetLineToolTip(ListCellLine line)
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
        setFocus(rdo_Auto);
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
        XDProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            XDProgressSCHParams inparam = new XDProgressSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_TcPlanProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanProgress.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));
                lst_TcPlanProgress_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress.add(line);
            }

            // clear.
            rdo_Auto.setChecked(true);
            rdo_PlanDateBatchNoUnit.setChecked(true);
            txt_ProgressRateReceiving.setReadOnly(true);
            txt_ProgressRateSort.setReadOnly(true);
            txt_ProgressRateReceiving2.setReadOnly(true);
            txt_ProgressRateSort2.setReadOnly(true);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            XDProgressSCHParams inparam = new XDProgressSCHParams();
            inparam.set(XDProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(XDProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
            inparam.set(XDProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_TcPlanProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanProgress.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
                lst_TcPlanProgress_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress.add(line);
            }
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
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            XDProgressSCHParams inparam = new XDProgressSCHParams();
            inparam.set(XDProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(XDProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
            inparam.set(XDProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_TcPlanProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanProgress.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
                lst_TcPlanProgress_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress.add(line);
            }
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
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            XDProgressSCHParams inparam = new XDProgressSCHParams();
            inparam.set(XDProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(XDProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
            inparam.set(XDProgressSCHParams.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
            inparam.set(XDProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_TcPlanProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_TcPlanProgress.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());
                lst_TcPlanProgress_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress.add(line);
            }
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
