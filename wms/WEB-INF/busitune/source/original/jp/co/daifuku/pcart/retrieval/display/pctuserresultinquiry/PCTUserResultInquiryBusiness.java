// $Id: PCTUserResultInquiryBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctuserresultinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCHParams;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultBatchDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultBatchDASCHParams;
import jp.co.daifuku.pcart.retrieval.display.pctuserresultinquiry.PCTUserResultInquiry;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultBatchListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworkdate.LstPCTUserResultWorkDateParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5329 $, $Date:: 2009-10-29 14:35:28 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTUserResultInquiryBusiness
        extends PCTUserResultInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel rdo_group_DateSelect */
    private RadioButtonGroup _grp_rdo_group_DateSelect;

    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** RadioButtonGroupModel rdo_CollectCondition */
    private RadioButtonGroup _grp_rdo_CollectCondition;

    /** RadioButtonGroupModel rdo_DisplayRank */
    private RadioButtonGroup _grp_rdo_DisplayRank;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTUserResultInquiryBusiness()
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
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_DlgBack(dialogParams);
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
    public void rdo_DateSelect_All_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_DateSelect_All_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_DateSelect_Assignation_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_DateSelect_Assignation_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_DateSelectWeekday_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DateSelectWeekday_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_DateSelectClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DateSelectClear_Click_Process();
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

        // initialize rdo_group_DateSelect.
        _grp_rdo_group_DateSelect = new RadioButtonGroup(new RadioButton[]{rdo_DateSelect_All, rdo_DateSelect_Assignation}, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize rdo_CollectCondition.
        _grp_rdo_CollectCondition = new RadioButtonGroup(new RadioButton[]{rdo_CollectCondition_Worker, rdo_CollectCondition_WorkDate, rdo_CollectCondition_Consignor, rdo_CollectCondition_Area, rdo_CollectCondition_Batch}, locale);

        // initialize rdo_DisplayRank.
        _grp_rdo_DisplayRank = new RadioButtonGroup(new RadioButton[]{rdo_DisplayRank_Lot, rdo_DisplayRank_Order, rdo_DisplayRank_Line}, locale);

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

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.ALL);

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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_WorkDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_DateSelect_All.setChecked(true);
        rdo_CollectCondition_Worker.setChecked(true);
        rdo_DisplayRank_Lot.setChecked(true);
        chk_Monday.setEnabled(false);
        chk_Tuesday.setEnabled(false);
        chk_Wednesday.setEnabled(false);
        chk_Thursday.setEnabled(false);
        chk_Friday.setEnabled(false);
        chk_Saturday.setEnabled(false);
        chk_Sunday.setEnabled(false);
        chk_Monday.setChecked(true);
        chk_Tuesday.setChecked(true);
        chk_Wednesday.setChecked(true);
        chk_Thursday.setChecked(true);
        chk_Friday.setChecked(true);
        chk_Saturday.setChecked(true);
        chk_Sunday.setChecked(true);
        chk_levelA.setChecked(true);
        chk_levelB.setChecked(true);
        chk_levelC.setChecked(true);
        btn_DateSelectWeekday.setEnabled(false);
        btn_DateSelectClear.setEnabled(false);

        // set focus.
        setFocus(txt_WorkDate);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_DateSelect_All_Click_Process()
            throws Exception
    {
        // clear.
        chk_Monday.setChecked(true);
        chk_Tuesday.setChecked(true);
        chk_Wednesday.setChecked(true);
        chk_Thursday.setChecked(true);
        chk_Friday.setChecked(true);
        chk_Saturday.setChecked(true);
        chk_Sunday.setChecked(true);
        chk_Monday.setEnabled(false);
        chk_Tuesday.setEnabled(false);
        chk_Wednesday.setEnabled(false);
        chk_Thursday.setEnabled(false);
        chk_Friday.setEnabled(false);
        chk_Saturday.setEnabled(false);
        chk_Sunday.setEnabled(false);
        btn_DateSelectWeekday.setEnabled(false);
        btn_DateSelectClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_DateSelect_Assignation_Click_Process()
            throws Exception
    {
        // clear.
        chk_Monday.setEnabled(true);
        chk_Tuesday.setEnabled(true);
        chk_Wednesday.setEnabled(true);
        chk_Thursday.setEnabled(true);
        chk_Friday.setEnabled(true);
        chk_Saturday.setEnabled(true);
        chk_Sunday.setEnabled(true);
        btn_DateSelectWeekday.setEnabled(true);
        btn_DateSelectClear.setEnabled(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_DateSelectWeekday_Click_Process()
            throws Exception
    {
        // clear.
        chk_Monday.setChecked(true);
        chk_Tuesday.setChecked(true);
        chk_Wednesday.setChecked(true);
        chk_Thursday.setChecked(true);
        chk_Friday.setChecked(true);
        chk_Saturday.setChecked(false);
        chk_Sunday.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_DateSelectClear_Click_Process()
            throws Exception
    {
        // clear.
        chk_Monday.setChecked(false);
        chk_Tuesday.setChecked(false);
        chk_Wednesday.setChecked(false);
        chk_Thursday.setChecked(false);
        chk_Friday.setChecked(false);
        chk_Saturday.setChecked(false);
        chk_Sunday.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstPCTUserResultWorkDateParams inparam = new LstPCTUserResultWorkDateParams();
        inparam.set(LstPCTUserResultWorkDateParams.WORKDAY_FROM, txt_WorkDate.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.WORKDAY_TO, txt_ToWorkDate.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.USEDAY_OF_WEEK_FLAG, _grp_rdo_group_DateSelect.getSelectedValue());
        inparam.set(LstPCTUserResultWorkDateParams.MONDAY, chk_Monday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.TUESDAY, chk_Tuesday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.WEDNESDAY, chk_Wednesday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.THURSDAY, chk_Thursday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.FRIDAY, chk_Friday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.SATURDAY, chk_Saturday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.SUNDAY, chk_Sunday.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.AREA, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstPCTUserResultWorkDateParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.LEVEL_A, chk_levelA.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.LEVEL_B, chk_levelB.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.LEVEL_C, chk_levelC.getValue());
        inparam.set(LstPCTUserResultWorkDateParams.COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());
        inparam.set(LstPCTUserResultWorkDateParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
        redirect("/pcart/retrieval/listbox/pctuserresultworkdate/LstPCTUserResultWorkDate.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Display_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
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
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        pul_Area.validate(this, true);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultBatchDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultBatchDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultBatchDASCHParams inparam = new LstPCTUserResultBatchDASCHParams();
            inparam.set(LstPCTUserResultBatchDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.USEDAY_OF_WEEK_FLAG, _grp_rdo_group_DateSelect.getSelectedValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctUserResultBatchList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultBatchListParams expparam = new PctUserResultBatchListParams();
                expparam.set(PctUserResultBatchListParams.SYS_DAY, outparam.get(LstPCTUserResultBatchDASCHParams.SYS_DAY));
                expparam.set(PctUserResultBatchListParams.SYS_TIME, outparam.get(LstPCTUserResultBatchDASCHParams.SYS_TIME));
                expparam.set(PctUserResultBatchListParams.WORK_DAY_FROM, outparam.get(LstPCTUserResultBatchDASCHParams.WORK_DAY_FROM));
                expparam.set(PctUserResultBatchListParams.WORK_DAY_TO, outparam.get(LstPCTUserResultBatchDASCHParams.WORK_DAY_TO));
                expparam.set(PctUserResultBatchListParams.WEEK_DAY, outparam.get(LstPCTUserResultBatchDASCHParams.WEEK_DAY));
                expparam.set(PctUserResultBatchListParams.BATCH_NO, outparam.get(LstPCTUserResultBatchDASCHParams.BATCH_NO));
                expparam.set(PctUserResultBatchListParams.LEVEL, outparam.get(LstPCTUserResultBatchDASCHParams.LEVEL));
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_CODE, outparam.get(LstPCTUserResultBatchDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_NAME, outparam.get(LstPCTUserResultBatchDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultBatchListParams.AREA_NO, outparam.get(LstPCTUserResultBatchDASCHParams.AREA_NO));
                expparam.set(PctUserResultBatchListParams.AREA_NAME, outparam.get(LstPCTUserResultBatchDASCHParams.AREA_NAME));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_MISS_RATE));
                expparam.set(PctUserResultBatchListParams.BATCH_NO1, outparam.get(LstPCTUserResultBatchDASCHParams.BATCH_NO1));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE, outparam.get(LstPCTUserResultBatchDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS, outparam.get(LstPCTUserResultBatchDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT, outparam.get(LstPCTUserResultBatchDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.START_DATE, outparam.get(LstPCTUserResultBatchDASCHParams.START_TIME));
                expparam.set(PctUserResultBatchListParams.END_DATE, outparam.get(LstPCTUserResultBatchDASCHParams.END_TIME));
                expparam.set(PctUserResultBatchListParams.WORK_TIME, outparam.get(LstPCTUserResultBatchDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR, outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR, outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR, outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR, outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR, outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER, outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER, outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE, outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE, outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS, outparam.get(LstPCTUserResultBatchDASCHParams.MISS_RATE));
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
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        pul_Area.validate(this, true);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultAreaDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultAreaDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultAreaDASCHParams inparam = new LstPCTUserResultAreaDASCHParams();
            inparam.set(LstPCTUserResultAreaDASCHParams.WORK_DATE, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.TO_WORK_DATE, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.USEDAY_OF_WEEK_FLAG, _grp_rdo_group_DateSelect.getSelectedValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newExcelExporter("PctUserResultBatchList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultBatchListParams expparam = new PctUserResultBatchListParams();
                expparam.set(PctUserResultBatchListParams.WORK_DAY_FROM, outparam.get(LstPCTUserResultAreaDASCHParams.WORK_DAY_FROM));
                expparam.set(PctUserResultBatchListParams.WORK_DAY_TO, outparam.get(LstPCTUserResultAreaDASCHParams.WORK_DAY_TO));
                expparam.set(PctUserResultBatchListParams.WEEK_DAY, outparam.get(LstPCTUserResultAreaDASCHParams.WEEK_DAY));
                expparam.set(PctUserResultBatchListParams.BATCH_NO, outparam.get(LstPCTUserResultAreaDASCHParams.BATCH_NO));
                expparam.set(PctUserResultBatchListParams.LEVEL, outparam.get(LstPCTUserResultAreaDASCHParams.LEVEL));
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_CODE, outparam.get(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_NAME, outparam.get(LstPCTUserResultAreaDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultBatchListParams.AREA_NO, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NO));
                expparam.set(PctUserResultBatchListParams.AREA_NAME, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NAME));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT_PER_HOUR_AVG));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_MISS_RATE));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE, outparam.get(LstPCTUserResultAreaDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS, outparam.get(LstPCTUserResultAreaDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT, outparam.get(LstPCTUserResultAreaDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.START_DATE, outparam.get(LstPCTUserResultAreaDASCHParams.START_TIME));
                expparam.set(PctUserResultBatchListParams.END_DATE, outparam.get(LstPCTUserResultAreaDASCHParams.END_TIME));
                expparam.set(PctUserResultBatchListParams.WORK_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_CNLINE_COUNT_PER_HOURT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.LOTLOT_QTY_CNT));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS, outparam.get(LstPCTUserResultAreaDASCHParams.MISS_RATE));
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
        txt_WorkDate.setValue(null);
        txt_ToWorkDate.setValue(null);
        rdo_DateSelect_All.setChecked(true);
        chk_Monday.setChecked(true);
        chk_Tuesday.setChecked(true);
        chk_Wednesday.setChecked(true);
        chk_Thursday.setChecked(true);
        chk_Friday.setChecked(true);
        chk_Saturday.setChecked(true);
        chk_Sunday.setChecked(true);
        txt_ConsignorCode.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_BatchNo.setValue(null);
        chk_levelA.setChecked(true);
        chk_levelB.setChecked(true);
        chk_levelC.setChecked(true);
        rdo_CollectCondition_Worker.setChecked(true);
        rdo_DisplayRank_Lot.setChecked(true);
        chk_Monday.setEnabled(false);
        chk_Tuesday.setEnabled(false);
        chk_Wednesday.setEnabled(false);
        chk_Thursday.setEnabled(false);
        chk_Friday.setEnabled(false);
        chk_Saturday.setEnabled(false);
        chk_Sunday.setEnabled(false);
        btn_DateSelectWeekday.setEnabled(false);
        btn_DateSelectClear.setEnabled(false);

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
