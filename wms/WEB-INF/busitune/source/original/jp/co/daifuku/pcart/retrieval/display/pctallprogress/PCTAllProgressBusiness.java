// $Id: PCTAllProgressBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.pctallprogress;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
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
import jp.co.daifuku.pcart.retrieval.display.pctallprogress.PCTAllProgress;
import jp.co.daifuku.pcart.retrieval.display.pctallprogress.ViewStateKeys;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAllProgressSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAllProgressSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTAllProgressBusiness
        extends PCTAllProgress
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTAllProgress1(LST_PLAN_DATE_1) */
    private static final ListCellKey KEY_LST_PLAN_DATE_1 = new ListCellKey("LST_PLAN_DATE_1", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTAllProgress1(LST_BATCH_SEQ_NO_1) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_1 = new ListCellKey("LST_BATCH_SEQ_NO_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_BATCH_NO_1) */
    private static final ListCellKey KEY_LST_BATCH_NO_1 = new ListCellKey("LST_BATCH_NO_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_ORDER_COUNT_1) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_1 = new ListCellKey("LST_ORDER_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_BOX_COUNT_1) */
    private static final ListCellKey KEY_LST_BOX_COUNT_1 = new ListCellKey("LST_BOX_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress1(LST_LINE_COUNT_1) */
    private static final ListCellKey KEY_LST_LINE_COUNT_1 = new ListCellKey("LST_LINE_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_LOT_COUNT_1) */
    private static final ListCellKey KEY_LST_LOT_COUNT_1 = new ListCellKey("LST_LOT_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_END_PLAN_TIME_1) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_1 = new ListCellKey("LST_END_PLAN_TIME_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_CART_COUNT_1) */
    private static final ListCellKey KEY_LST_CART_COUNT_1 = new ListCellKey("LST_CART_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress2(LST_PLAN_DATE_2) */
    private static final ListCellKey KEY_LST_PLAN_DATE_2 = new ListCellKey("LST_PLAN_DATE_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTAllProgress2(LST_BATCH_SEQ_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_2 = new ListCellKey("LST_BATCH_SEQ_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 = new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 = new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_BOX_COUNT_2) */
    private static final ListCellKey KEY_LST_BOX_COUNT_2 = new ListCellKey("LST_BOX_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 = new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 = new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_END_PLAN_TIME_2) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_2 = new ListCellKey("LST_END_PLAN_TIME_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_CART_COUNT_2) */
    private static final ListCellKey KEY_LST_CART_COUNT_2 = new ListCellKey("LST_CART_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress3(LST_PLAN_DATE_3) */
    private static final ListCellKey KEY_LST_PLAN_DATE_3 = new ListCellKey("LST_PLAN_DATE_3", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTAllProgress3(LST_BATCH_SEQ_NO_3) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_3 = new ListCellKey("LST_BATCH_SEQ_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_BATCH_NO_3) */
    private static final ListCellKey KEY_LST_BATCH_NO_3 = new ListCellKey("LST_BATCH_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_ORDER_COUNT_3) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_3 = new ListCellKey("LST_ORDER_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_BOX_COUNT_3) */
    private static final ListCellKey KEY_LST_BOX_COUNT_3 = new ListCellKey("LST_BOX_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 = new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 = new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_END_PLAN_TIME_3) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_3 = new ListCellKey("LST_END_PLAN_TIME_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_CART_COUNT_3) */
    private static final ListCellKey KEY_LST_CART_COUNT_3 = new ListCellKey("LST_CART_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress4(LST_PLAN_DATE_4) */
    private static final ListCellKey KEY_LST_PLAN_DATE_4 = new ListCellKey("LST_PLAN_DATE_4", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_PCTAllProgress4(LST_BATCH_SEQ_NO_4) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_4 = new ListCellKey("LST_BATCH_SEQ_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_BATCH_NO_4) */
    private static final ListCellKey KEY_LST_BATCH_NO_4 = new ListCellKey("LST_BATCH_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_ORDER_COUNT_4) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_4 = new ListCellKey("LST_ORDER_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_BOX_COUNT_4) */
    private static final ListCellKey KEY_LST_BOX_COUNT_4 = new ListCellKey("LST_BOX_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 = new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 = new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_END_PLAN_TIME_4) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_4 = new ListCellKey("LST_END_PLAN_TIME_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_CART_COUNT_4) */
    private static final ListCellKey KEY_LST_CART_COUNT_4 = new ListCellKey("LST_CART_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress1 keys */
    private static final ListCellKey[] LST_PCTALLPROGRESS1_KEYS = {
        KEY_LST_PLAN_DATE_1,
        KEY_LST_BATCH_SEQ_NO_1,
        KEY_LST_BATCH_NO_1,
        KEY_LST_ORDER_COUNT_1,
        KEY_LST_BOX_COUNT_1,
        KEY_LST_LINE_COUNT_1,
        KEY_LST_LOT_COUNT_1,
        KEY_LST_END_PLAN_TIME_1,
        KEY_LST_CART_COUNT_1,
    };

    /** lst_PCTAllProgress2 keys */
    private static final ListCellKey[] LST_PCTALLPROGRESS2_KEYS = {
        KEY_LST_PLAN_DATE_2,
        KEY_LST_BATCH_SEQ_NO_2,
        KEY_LST_BATCH_NO_2,
        KEY_LST_ORDER_COUNT_2,
        KEY_LST_BOX_COUNT_2,
        KEY_LST_LINE_COUNT_2,
        KEY_LST_LOT_COUNT_2,
        KEY_LST_END_PLAN_TIME_2,
        KEY_LST_CART_COUNT_2,
    };

    /** lst_PCTAllProgress3 keys */
    private static final ListCellKey[] LST_PCTALLPROGRESS3_KEYS = {
        KEY_LST_PLAN_DATE_3,
        KEY_LST_BATCH_SEQ_NO_3,
        KEY_LST_BATCH_NO_3,
        KEY_LST_ORDER_COUNT_3,
        KEY_LST_BOX_COUNT_3,
        KEY_LST_LINE_COUNT_3,
        KEY_LST_LOT_COUNT_3,
        KEY_LST_END_PLAN_TIME_3,
        KEY_LST_CART_COUNT_3,
    };

    /** lst_PCTAllProgress4 keys */
    private static final ListCellKey[] LST_PCTALLPROGRESS4_KEYS = {
        KEY_LST_PLAN_DATE_4,
        KEY_LST_BATCH_SEQ_NO_4,
        KEY_LST_BATCH_NO_4,
        KEY_LST_ORDER_COUNT_4,
        KEY_LST_BOX_COUNT_4,
        KEY_LST_LINE_COUNT_4,
        KEY_LST_LOT_COUNT_4,
        KEY_LST_END_PLAN_TIME_4,
        KEY_LST_CART_COUNT_4,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** RadioButtonGroupModel Group */
    private RadioButtonGroup _grp_Group;

    /** RadioButtonGroupModel EndPlanTime */
    private RadioButtonGroup _grp_EndPlanTime;

    /** ListCellModel lst_PCTAllProgress1 */
    private ListCellModel _lcm_lst_PCTAllProgress1;

    /** ListCellModel lst_PCTAllProgress2 */
    private ListCellModel _lcm_lst_PCTAllProgress2;

    /** ListCellModel lst_PCTAllProgress3 */
    private ListCellModel _lcm_lst_PCTAllProgress3;

    /** ListCellModel lst_PCTAllProgress4 */
    private ListCellModel _lcm_lst_PCTAllProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTAllProgressBusiness()
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
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Auto_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Manual_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_BatchNoUnit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_BatchNoUnit_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_PlanDateUnit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_PlanDateUnit_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_AllPlanDate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_AllPlanDate_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LotStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LotStandard_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_OrderStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_OrderStandard_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LineStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LineStandard_Click_Process();
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_Auto, rdo_Manual}, locale);

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[]{rdo_BatchNoUnit, rdo_PlanDateUnit, rdo_AllPlanDate}, locale);

        // initialize EndPlanTime.
        _grp_EndPlanTime = new RadioButtonGroup(new RadioButton[]{rdo_LotStandard, rdo_OrderStandard, rdo_LineStandard}, locale);

        // initialize lst_PCTAllProgress1.
        _lcm_lst_PCTAllProgress1 = new ListCellModel(lst_PCTAllProgress1, LST_PCTALLPROGRESS1_KEYS, locale);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_PLAN_DATE_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BATCH_NO_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BOX_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_LINE_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_LOT_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_END_PLAN_TIME_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_CART_COUNT_1, true);

        // initialize lst_PCTAllProgress2.
        _lcm_lst_PCTAllProgress2 = new ListCellModel(lst_PCTAllProgress2, LST_PCTALLPROGRESS2_KEYS, locale);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_PLAN_DATE_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BOX_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_END_PLAN_TIME_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_CART_COUNT_2, false);

        // initialize lst_PCTAllProgress3.
        _lcm_lst_PCTAllProgress3 = new ListCellModel(lst_PCTAllProgress3, LST_PCTALLPROGRESS3_KEYS, locale);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_PLAN_DATE_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BATCH_NO_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_ORDER_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BOX_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_END_PLAN_TIME_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_CART_COUNT_3, false);

        // initialize lst_PCTAllProgress4.
        _lcm_lst_PCTAllProgress4 = new ListCellModel(lst_PCTAllProgress4, LST_PCTALLPROGRESS4_KEYS, locale);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_PLAN_DATE_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BATCH_NO_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_ORDER_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BOX_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_END_PLAN_TIME_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_CART_COUNT_4, false);
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

            // load pul_AreaNo.
            _pdm_pul_AreaNo.init(conn, AreaType.FLOOR, StationType.ALL, "", true);
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
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAllProgress1_SetLineToolTip(ListCellLine line)
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
    private void lst_PCTAllProgress2_SetLineToolTip(ListCellLine line)
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
    private void lst_PCTAllProgress3_SetLineToolTip(ListCellLine line)
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
    private void lst_PCTAllProgress4_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_ConsignorCode);
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
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTAllProgressSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTAllProgressSCHParams.CONSIGNOR_NAME));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, outparam.get(PCTAllProgressSCHParams.CONSIGNOR_CODE));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, outparam.get(PCTAllProgressSCHParams.CONSIGNOR_NAME));
            }

            // clear.
            rdo_Auto.setChecked(true);
            rdo_BatchNoUnit.setChecked(true);
            rdo_LotStandard.setChecked(true);
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
    private void rdo_Auto_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // clear.
        rdo_Auto.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Manual_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // clear.
        rdo_Manual.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_BatchNoUnit_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void rdo_PlanDateUnit_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void rdo_AllPlanDate_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void rdo_LotStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void rdo_OrderStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void rdo_LineStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
        txt_ConsignorCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAllProgressSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
                _pdm_pul_AreaNo.setSelectedValue(viewState.getObject(ViewStateKeys.VS_AREA_NO));
                _grp_Group.setSelectedValue(viewState.getObject(ViewStateKeys.VS_GROUP));
                _grp_EndPlanTime.setSelectedValue(viewState.getObject(ViewStateKeys.VS_END_PLAN_TIME));
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
        txt_ConsignorCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAllProgressSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, "COLLECT_FLAG");
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, "END_PLAN_TIME_FLAG");
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAllProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAllProgress1.getNewLine();
                txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
                _pdm_pul_AreaNo.setSelectedValue(viewState.getObject(ViewStateKeys.VS_AREA_NO));
                _grp_Group.setSelectedValue(viewState.getObject(ViewStateKeys.VS_GROUP));
                _grp_EndPlanTime.setSelectedValue(viewState.getObject(ViewStateKeys.VS_END_PLAN_TIME));
                line.setValue(KEY_LST_PLAN_DATE_1, outparam.get(PCTAllProgressSCHParams.PLAN_DATE));
                line.setValue(KEY_LST_BATCH_SEQ_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_NO_1, outparam.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_END_PLAN_TIME_1, outparam.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                line.setValue(KEY_LST_CART_COUNT_1, outparam.get(PCTAllProgressSCHParams.CART_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTAllProgressSCHParams.BUTTON_FLAG));
                lst_PCTAllProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAllProgress1.add(line);
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
