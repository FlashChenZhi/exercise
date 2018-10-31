// $Id: PCTCustomerProgressGraphBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressgraph;

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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressgraph.PCTCustomerProgressGraph;
import jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressgraph.ViewStateKeys;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressGraphSCHParams;
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
public class PCTCustomerProgressGraphBusiness
        extends PCTCustomerProgressGraph
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTCustomerProgress1(LST_CUSTOMER_CODE_1) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_1 = new ListCellKey("LST_CUSTOMER_CODE_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_CUSTOMER_NAME_1) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_1 = new ListCellKey("LST_CUSTOMER_NAME_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_ORDER_COUNT_1) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_1 = new ListCellKey("LST_ORDER_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_BOX_COUNT_1) */
    private static final ListCellKey KEY_LST_BOX_COUNT_1 = new ListCellKey("LST_BOX_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress1(LST_LINE_COUNT_1) */
    private static final ListCellKey KEY_LST_LINE_COUNT_1 = new ListCellKey("LST_LINE_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_LOT_COUNT_1) */
    private static final ListCellKey KEY_LST_LOT_COUNT_1 = new ListCellKey("LST_LOT_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_CUSTOMER_CODE_2) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_2 = new ListCellKey("LST_CUSTOMER_CODE_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_CUSTOMER_NAME_2) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_2 = new ListCellKey("LST_CUSTOMER_NAME_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 = new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_BOX_COUNT_2) */
    private static final ListCellKey KEY_LST_BOX_COUNT_2 = new ListCellKey("LST_BOX_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 = new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 = new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_CUSTOMER_CODE_3) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_3 = new ListCellKey("LST_CUSTOMER_CODE_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_CUSTOMER_NAME_3) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_3 = new ListCellKey("LST_CUSTOMER_NAME_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_ORDER_COUNT_3) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_3 = new ListCellKey("LST_ORDER_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_BOX_COUNT_3) */
    private static final ListCellKey KEY_LST_BOX_COUNT_3 = new ListCellKey("LST_BOX_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 = new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 = new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_CUSTOMER_CODE_4) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_4 = new ListCellKey("LST_CUSTOMER_CODE_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_CUSTOMER_NAME_4) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_4 = new ListCellKey("LST_CUSTOMER_NAME_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_ORDER_COUNT_4) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_4 = new ListCellKey("LST_ORDER_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_BOX_COUNT_4) */
    private static final ListCellKey KEY_LST_BOX_COUNT_4 = new ListCellKey("LST_BOX_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 = new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 = new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1 keys */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS1_KEYS = {
        KEY_LST_CUSTOMER_CODE_1,
        KEY_LST_CUSTOMER_NAME_1,
        KEY_LST_ORDER_COUNT_1,
        KEY_LST_BOX_COUNT_1,
        KEY_LST_LINE_COUNT_1,
        KEY_LST_LOT_COUNT_1,
    };

    /** lst_PCTCustomerProgress2 keys */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS2_KEYS = {
        KEY_LST_CUSTOMER_CODE_2,
        KEY_LST_CUSTOMER_NAME_2,
        KEY_LST_ORDER_COUNT_2,
        KEY_LST_BOX_COUNT_2,
        KEY_LST_LINE_COUNT_2,
        KEY_LST_LOT_COUNT_2,
    };

    /** lst_PCTCustomerProgress3 keys */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS3_KEYS = {
        KEY_LST_CUSTOMER_CODE_3,
        KEY_LST_CUSTOMER_NAME_3,
        KEY_LST_ORDER_COUNT_3,
        KEY_LST_BOX_COUNT_3,
        KEY_LST_LINE_COUNT_3,
        KEY_LST_LOT_COUNT_3,
    };

    /** lst_PCTCustomerProgress4 keys */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS4_KEYS = {
        KEY_LST_CUSTOMER_CODE_4,
        KEY_LST_CUSTOMER_NAME_4,
        KEY_LST_ORDER_COUNT_4,
        KEY_LST_BOX_COUNT_4,
        KEY_LST_LINE_COUNT_4,
        KEY_LST_LOT_COUNT_4,
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

    /** ListCellModel lst_PCTCustomerProgress1 */
    private ListCellModel _lcm_lst_PCTCustomerProgress1;

    /** ListCellModel lst_PCTCustomerProgress2 */
    private ListCellModel _lcm_lst_PCTCustomerProgress2;

    /** ListCellModel lst_PCTCustomerProgress3 */
    private ListCellModel _lcm_lst_PCTCustomerProgress3;

    /** ListCellModel lst_PCTCustomerProgress4 */
    private ListCellModel _lcm_lst_PCTCustomerProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerProgressGraphBusiness()
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
        if (eventSource.equals("btn_P_Search_RegularCustomerCd_Click"))
        {
            // process call.
            btn_P_Search_RegularCustomerCd_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_RegularCustomerCd_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_RegularCustomerCd_Click_Process();
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

        // initialize lst_PCTCustomerProgress1.
        _lcm_lst_PCTCustomerProgress1 = new ListCellModel(lst_PCTCustomerProgress1, LST_PCTCUSTOMERPROGRESS1_KEYS, locale);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_CUSTOMER_CODE_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_CUSTOMER_NAME_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_BOX_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_LINE_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_LOT_COUNT_1, true);

        // initialize lst_PCTCustomerProgress2.
        _lcm_lst_PCTCustomerProgress2 = new ListCellModel(lst_PCTCustomerProgress2, LST_PCTCUSTOMERPROGRESS2_KEYS, locale);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_CUSTOMER_CODE_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_CUSTOMER_NAME_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_BOX_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, true);

        // initialize lst_PCTCustomerProgress3.
        _lcm_lst_PCTCustomerProgress3 = new ListCellModel(lst_PCTCustomerProgress3, LST_PCTCUSTOMERPROGRESS3_KEYS, locale);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_CUSTOMER_CODE_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_CUSTOMER_NAME_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_ORDER_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_BOX_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, true);

        // initialize lst_PCTCustomerProgress4.
        _lcm_lst_PCTCustomerProgress4 = new ListCellModel(lst_PCTCustomerProgress4, LST_PCTCUSTOMERPROGRESS4_KEYS, locale);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_CUSTOMER_CODE_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_CUSTOMER_NAME_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_ORDER_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_BOX_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, true);
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
    private void lst_PCTCustomerProgress1_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_1);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_2);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress3_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_3);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress4_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_4);
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
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_NAME));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_NAME));
            }

            // clear.
            txt_RegularCustomerCode.setValue(null);
            txt_RegularCustomerName.setValue(null);
            _pdm_pul_AreaNo.setSelectedValue(null);
            txt_BatchNo.setValue(null);
            txt_BatchSeqNo.setValue(null);
            rdo_Auto.setChecked(true);

            // set focus.
            setFocus(txt_ConsignorCode);
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
    private void btn_P_Search_RegularCustomerCd_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegularCustomerParams inparam = new LstRegularCustomerParams();
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_RegularCustomerCd_Click");
        redirect("/pcart/retrieval/listbox/regularCustomer/LstRegularCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_RegularCustomerCd_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRegularCustomerParams outparam = new LstRegularCustomerParams(dialogParams);
        txt_RegularCustomerCode.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE));
        txt_RegularCustomerName.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_NAME));

        // set focus.
        setFocus(txt_RegularCustomerCode);
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
        txt_ConsignorCode.validate(this, false);

        // clear.
        rdo_Manual.setChecked(true);
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
        txt_RegularCustomerCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress1.getNewLine();
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
                viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME, txt_RegularCustomerName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
                line.setValue(KEY_LST_CUSTOMER_CODE_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG));
                lst_PCTCustomerProgress1_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress1.add(line);
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE, viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress1.getNewLine();
                txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
                txt_RegularCustomerCode.setValue(viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
                txt_RegularCustomerName.setValue(viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME));
                _pdm_pul_AreaNo.setSelectedValue(viewState.getObject(ViewStateKeys.VS_AREA_NO));
                txt_BatchNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_NO));
                txt_BatchSeqNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG));
                lst_PCTCustomerProgress1_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress1.add(line);
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE, viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress1.getNewLine();
                txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
                txt_RegularCustomerCode.setValue(viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
                txt_RegularCustomerName.setValue(viewState.getObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME));
                _pdm_pul_AreaNo.setSelectedValue(viewState.getObject(ViewStateKeys.VS_AREA_NO));
                txt_BatchNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_NO));
                txt_BatchSeqNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME_1, outparam.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, outparam.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG));
                lst_PCTCustomerProgress1_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress1.add(line);
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
