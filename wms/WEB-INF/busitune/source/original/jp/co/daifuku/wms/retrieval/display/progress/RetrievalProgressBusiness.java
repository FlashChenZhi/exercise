// $Id: RetrievalProgressBusiness.java 7677 2010-03-18 00:44:12Z okayama $
package jp.co.daifuku.wms.retrieval.display.progress;

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
import jp.co.daifuku.wms.retrieval.display.progress.RetrievalProgress;
import jp.co.daifuku.wms.retrieval.display.progress.ViewStateKeys;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalProgressSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalProgressSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7677 $, $Date:: 2010-03-18 09:44:12 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalProgressBusiness
        extends RetrievalProgress
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalProgress1(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalProgress1(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT = new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_TICKET_COUNT) */
    private static final ListCellKey KEY_LST_TICKET_COUNT = new ListCellKey("LST_TICKET_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_RETRIEVAL_COUNT) */
    private static final ListCellKey KEY_LST_RETRIEVAL_COUNT = new ListCellKey("LST_RETRIEVAL_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalProgress2(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 = new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 = new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_TICKET_COUNT_2) */
    private static final ListCellKey KEY_LST_TICKET_COUNT_2 = new ListCellKey("LST_TICKET_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT_2 = new ListCellKey("LST_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_RETRIEVAL_COUNT_2) */
    private static final ListCellKey KEY_LST_RETRIEVAL_COUNT_2 = new ListCellKey("LST_RETRIEVAL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_CASE_QTY_2 = new ListCellKey("LST_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_PIECE_QTY_2 = new ListCellKey("LST_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_RetrievalProgress1 keys */
    private static final ListCellKey[] LST_RETRIEVALPROGRESS1_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_COUNT,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_TICKET_COUNT,
        KEY_LST_RETRIEVAL_COUNT,
        KEY_LST_PIECE_QTY,
    };

    /** lst_RetrievalProgress2 keys */
    private static final ListCellKey[] LST_RETRIEVALPROGRESS2_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_BATCH_NO_2,
        KEY_LST_ORDER_COUNT_2,
        KEY_LST_DETAIL_COUNT_2,
        KEY_LST_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_TICKET_COUNT_2,
        KEY_LST_RETRIEVAL_COUNT_2,
        KEY_LST_PIECE_QTY_2,
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

    /** ListCellModel lst_RetrievalProgress1 */
    private ListCellModel _lcm_lst_RetrievalProgress1;

    /** ListCellModel lst_RetrievalProgress2 */
    private ListCellModel _lcm_lst_RetrievalProgress2;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalProgressBusiness()
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
        _grp_Group = new RadioButtonGroup(new RadioButton[]{rdo_BatchNoUnit, rdo_PlanDateUnit, rdo_AllPlanDate}, locale);

        // initialize lst_RetrievalProgress1.
        _lcm_lst_RetrievalProgress1 = new ListCellModel(lst_RetrievalProgress1, LST_RETRIEVALPROGRESS1_KEYS, locale);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_TICKET_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_RETRIEVAL_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_CASE_QTY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_PIECE_QTY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_RetrievalProgress2.
        _lcm_lst_RetrievalProgress2 = new ListCellModel(lst_RetrievalProgress2, LST_RETRIEVALPROGRESS2_KEYS, locale);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_TICKET_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_DETAIL_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_RETRIEVAL_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_CASE_QTY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_PIECE_QTY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);
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
    private void lst_RetrievalProgress1_SetLineToolTip(ListCellLine line)
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
    private void lst_RetrievalProgress2_SetLineToolTip(ListCellLine line)
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
        RetrievalProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalProgressSCHParams inparam = new RetrievalProgressSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalProgress1.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));
                lst_RetrievalProgress1_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress1.add(line);
            }

            // clear.
            rdo_Auto.setChecked(true);
            rdo_BatchNoUnit.setChecked(true);
            txt_ProgressRate1.setReadOnly(true);
            txt_ProgressRate2.setReadOnly(true);

            // set focus.
            setFocus(rdo_Auto);
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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalProgressSCHParams inparam = new RetrievalProgressSCHParams();
            inparam.set(RetrievalProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE1, txt_ProgressRate1.getValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE2, txt_ProgressRate2.getValue());
            inparam.set(RetrievalProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalProgress1.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE1, txt_ProgressRate1.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE2, txt_ProgressRate2.getValue());
                lst_RetrievalProgress1_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress1.add(line);
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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalProgressSCHParams inparam = new RetrievalProgressSCHParams();
            inparam.set(RetrievalProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE1, txt_ProgressRate1.getValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE2, txt_ProgressRate2.getValue());
            inparam.set(RetrievalProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalProgress1.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE1, txt_ProgressRate1.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE2, txt_ProgressRate2.getValue());
                lst_RetrievalProgress1_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress1.add(line);
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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalProgressSCHParams inparam = new RetrievalProgressSCHParams();
            inparam.set(RetrievalProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.GROUP, _grp_Group.getSelectedValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE1, txt_ProgressRate1.getValue());
            inparam.set(RetrievalProgressSCHParams.PROGRESS_RATE2, txt_ProgressRate2.getValue());
            inparam.set(RetrievalProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalProgress1.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE1, txt_ProgressRate1.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE2, txt_ProgressRate2.getValue());
                lst_RetrievalProgress1_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress1.add(line);
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
