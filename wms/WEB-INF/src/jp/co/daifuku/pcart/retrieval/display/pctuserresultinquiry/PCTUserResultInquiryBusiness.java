// $Id: PCTUserResultInquiryBusiness.java 7522 2010-03-13 06:01:48Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctuserresultinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCHParams;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultBatchDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultBatchDASCHParams;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultConsignorDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultConsignorDASCHParams;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkDateDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkDateDASCHParams;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkerDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkerDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultAreaListParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultBatchListParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultConsignorListParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultWorkDateListParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultWorkerListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultarea.LstPCTUserResultAreaParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultbatch.LstPCTUserResultBatchParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultconsignor.LstPCTUserResultConsignorParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworkdate.LstPCTUserResultWorkDateParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworker.LstPCTUserResultWorkerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 実績集計照会の画面処理を行います。
 *
 * @version $Revision: 7522 $, $Date:: 2010-03-13 15:01:48 +0900#$
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
        _grp_rdo_group_DateSelect = new RadioButtonGroup(new RadioButton[] {
                rdo_DateSelect_All,
                rdo_DateSelect_Assignation
        }, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize rdo_CollectCondition.
        _grp_rdo_CollectCondition = new RadioButtonGroup(new RadioButton[] {
                rdo_CollectCondition_Worker,
                rdo_CollectCondition_WorkDate,
                rdo_CollectCondition_Consignor,
                rdo_CollectCondition_Area,
                rdo_CollectCondition_Batch
        }, locale);

        // initialize rdo_DisplayRank.
        _grp_rdo_DisplayRank = new RadioButtonGroup(new RadioButton[] {
                rdo_DisplayRank_Lot,
                rdo_DisplayRank_Order,
                rdo_DisplayRank_Line
        }, locale);

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
        pul_Area.validate(this, true);

        // DFKLOOK:ここから修正
        // 集約単位：作業者別
        if (rdo_CollectCondition_Worker.getChecked())
        {
            // dialog parameters set.
            LstPCTUserResultWorkerParams inparam = new LstPCTUserResultWorkerParams();
            inparam.set(LstPCTUserResultWorkerParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkerParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkerParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkerParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkerParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkerParams.LEVEL_C, chk_levelC.getValue());


            boolean usedayFlag = true;
            if (rdo_DateSelect_All.getChecked())
            {
                usedayFlag = false;
            }
            inparam.set(LstPCTUserResultWorkerParams.USEDAY_OF_WEEK_FLAG, usedayFlag);

            // 表示ランク：ロット単位
            if (rdo_DisplayRank_Lot.getChecked())
            {
                inparam.set(LstPCTUserResultWorkerParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LOT);
            }
            // 表示ランク：オーダー単位
            else if (rdo_DisplayRank_Order.getChecked())
            {
                inparam.set(LstPCTUserResultWorkerParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_ORDER);
            }
            // 表示ランク：行単位
            else
            {
                inparam.set(LstPCTUserResultWorkerParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LINE);
            }

            inparam.set(LstPCTUserResultWorkerParams.COLLECT_CONDITION,
                    PCTRetrievalInParameter.COLLECT_CONDITION_WORKER);

            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
            redirect("/pcart/retrieval/listbox/pctuserresultworker/LstPCTUserResultWorker.do", forwardParam,
                    "/Progress.do");
        }
        // 集約単位：作業日別
        else if (rdo_CollectCondition_WorkDate.getChecked())
        {
            // dialog parameters set.
            LstPCTUserResultWorkDateParams inparam = new LstPCTUserResultWorkDateParams();
            inparam.set(LstPCTUserResultWorkDateParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkDateParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkDateParams.LEVEL_C, chk_levelC.getValue());

            boolean usedayFlag = true;
            if (rdo_DateSelect_All.getChecked())
            {
                usedayFlag = false;
            }
            inparam.set(LstPCTUserResultWorkDateParams.USEDAY_OF_WEEK_FLAG, usedayFlag);

            // 表示ランク：ロット単位
            if (rdo_DisplayRank_Lot.getChecked())
            {
                inparam.set(LstPCTUserResultWorkDateParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LOT);
            }
            // 表示ランク：オーダー単位
            else if (rdo_DisplayRank_Order.getChecked())
            {
                inparam.set(LstPCTUserResultWorkDateParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_ORDER);
            }
            // 表示ランク：行単位
            else
            {
                inparam.set(LstPCTUserResultWorkDateParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LINE);
            }

            inparam.set(LstPCTUserResultWorkDateParams.COLLECT_CONDITION,
                    PCTRetrievalInParameter.COLLECT_CONDITION_WORKDATE);

            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
            redirect("/pcart/retrieval/listbox/pctuserresultworkdate/LstPCTUserResultWorkDate.do", forwardParam,
                    "/Progress.do");
        }
        // 集約単位：荷主別
        else if (rdo_CollectCondition_Consignor.getChecked())
        {
            // dialog parameters set.
            LstPCTUserResultConsignorParams inparam = new LstPCTUserResultConsignorParams();
            inparam.set(LstPCTUserResultConsignorParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultConsignorParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultConsignorParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultConsignorParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultConsignorParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultConsignorParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultConsignorParams.LEVEL_C, chk_levelC.getValue());

            boolean usedayFlag = true;
            if (rdo_DateSelect_All.getChecked())
            {
                usedayFlag = false;
            }
            inparam.set(LstPCTUserResultConsignorParams.USEDAY_OF_WEEK_FLAG, usedayFlag);

            // 表示ランク：ロット単位
            if (rdo_DisplayRank_Lot.getChecked())
            {
                inparam.set(LstPCTUserResultConsignorParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LOT);
            }
            // 表示ランク：オーダー単位
            else if (rdo_DisplayRank_Order.getChecked())
            {
                inparam.set(LstPCTUserResultConsignorParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_ORDER);
            }
            // 表示ランク：行単位
            else
            {
                inparam.set(LstPCTUserResultConsignorParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LINE);
            }

            inparam.set(LstPCTUserResultConsignorParams.COLLECT_CONDITION,
                    PCTRetrievalInParameter.COLLECT_CONDITION_CONSIGNOR);

            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
            redirect("/pcart/retrieval/listbox/pctuserresultconsignor/LstPCTUserResultConsignor.do", forwardParam,
                    "/Progress.do");
        }
        // 集約単位：エリア別
        else if (rdo_CollectCondition_Area.getChecked())
        {
            // dialog parameters set.
            LstPCTUserResultAreaParams inparam = new LstPCTUserResultAreaParams();
            inparam.set(LstPCTUserResultAreaParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultAreaParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultAreaParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultAreaParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultAreaParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultAreaParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultAreaParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultAreaParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultAreaParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultAreaParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultAreaParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultAreaParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultAreaParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultAreaParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultAreaParams.LEVEL_C, chk_levelC.getValue());

            boolean usedayFlag = true;
            if (rdo_DateSelect_All.getChecked())
            {
                usedayFlag = false;
            }
            inparam.set(LstPCTUserResultAreaParams.USEDAY_OF_WEEK_FLAG, usedayFlag);

            // 表示ランク：ロット単位
            if (rdo_DisplayRank_Lot.getChecked())
            {
                inparam.set(LstPCTUserResultAreaParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LOT);
            }
            // 表示ランク：オーダー単位
            else if (rdo_DisplayRank_Order.getChecked())
            {
                inparam.set(LstPCTUserResultAreaParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_ORDER);
            }
            // 表示ランク：行単位
            else
            {
                inparam.set(LstPCTUserResultAreaParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LINE);
            }

            inparam.set(LstPCTUserResultAreaParams.COLLECT_CONDITION, PCTRetrievalInParameter.COLLECT_CONDITION_AREA);

            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
            redirect("/pcart/retrieval/listbox/pctuserresultarea/LstPCTUserResultArea.do", forwardParam, "/Progress.do");
        }
        // 集約単位：バッチ別
        else
        {
            // dialog parameters set.
            LstPCTUserResultBatchParams inparam = new LstPCTUserResultBatchParams();
            inparam.set(LstPCTUserResultBatchParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultBatchParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultBatchParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultBatchParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultBatchParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultBatchParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultBatchParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultBatchParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultBatchParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultBatchParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultBatchParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultBatchParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultBatchParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultBatchParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultBatchParams.LEVEL_C, chk_levelC.getValue());

            boolean usedayFlag = true;
            if (rdo_DateSelect_All.getChecked())
            {
                usedayFlag = false;
            }
            inparam.set(LstPCTUserResultBatchParams.USEDAY_OF_WEEK_FLAG, usedayFlag);

            // 表示ランク：ロット単位
            if (rdo_DisplayRank_Lot.getChecked())
            {
                inparam.set(LstPCTUserResultBatchParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LOT);
            }
            // 表示ランク：オーダー単位
            else if (rdo_DisplayRank_Order.getChecked())
            {
                inparam.set(LstPCTUserResultBatchParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_ORDER);
            }
            // 表示ランク：行単位
            else
            {
                inparam.set(LstPCTUserResultBatchParams.DISPLAY_RANK, PCTRetrievalInParameter.DISPLAY_RANK_LINE);
            }

            inparam.set(LstPCTUserResultBatchParams.COLLECT_CONDITION, PCTRetrievalInParameter.COLLECT_CONDITION_BATCH);

            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
            redirect("/pcart/retrieval/listbox/pctuserresultbatch/LstPCTUserResultBatch.do", forwardParam,
                    "/Progress.do");
        }
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        // 集約単位：作業者別
        if (rdo_CollectCondition_Worker.getChecked())
        {
            printWorker(confirm);
        }
        // 集約単位：作業日別
        else if (rdo_CollectCondition_WorkDate.getChecked())
        {
            printWorkDate(confirm);
        }
        // 集約単位：荷主別
        else if (rdo_CollectCondition_Consignor.getChecked())
        {
            printConsignor(confirm);
        }
        // 集約単位：エリア別
        else if (rdo_CollectCondition_Area.getChecked())
        {
            printArea(confirm);
        }
        // 集約単位：バッチ別
        else
        {
            printBatch(confirm);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 集約単位：作業者別
        if (rdo_CollectCondition_Worker.getChecked())
        {
            xlsWorker();
        }
        // 集約単位：作業日別
        else if (rdo_CollectCondition_WorkDate.getChecked())
        {
            xlsWorkDate();
        }
        // 集約単位：荷主別
        else if (rdo_CollectCondition_Consignor.getChecked())
        {
            xlsConsignor();
        }
        // 集約単位：エリア別
        else if (rdo_CollectCondition_Area.getChecked())
        {
            xlsArea();
        }
        // 集約単位：バッチ別
        else
        {
            xlsBatch();
        }
        // DFKLOOK:ここまで修正
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
        chk_Monday.setEnabled(false);
        chk_Tuesday.setEnabled(false);
        chk_Wednesday.setEnabled(false);
        chk_Thursday.setEnabled(false);
        chk_Friday.setEnabled(false);
        chk_Saturday.setEnabled(false);
        chk_Sunday.setEnabled(false);
        txt_ConsignorCode.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_BatchNo.setValue(null);
        chk_levelA.setChecked(true);
        chk_levelB.setChecked(true);
        chk_levelC.setChecked(true);
        rdo_CollectCondition_Worker.setChecked(true);
        rdo_DisplayRank_Lot.setChecked(true);
        btn_DateSelectWeekday.setEnabled(false);
        btn_DateSelectClear.setEnabled(false);

    }

    // DFKLOOK:ここから修正 
    /**
     * ユーザー別印刷処理
     * @para boolean confirm
     * @throws ValidateException 
     * 
     */
    private void printWorker(boolean confirm)
            throws ValidateException
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
        LstPCTUserResultWorkerDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultWorkerDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultWorkerDASCHParams inparam = new LstPCTUserResultWorkerDASCHParams();
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultWorkerDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctUserResultWorkerList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultWorkerListParams expparam = new PctUserResultWorkerListParams();
                expparam.set(PctUserResultWorkerListParams.SYS_DAY, outparam.get(LstPCTUserResultWorkerDASCHParams.SYS_DAY));
                expparam.set(PctUserResultWorkerListParams.SYS_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.SYS_TIME));

                // 検索条件
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultWorkerListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultWorkerListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultWorkerListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultWorkerListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultWorkerListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultWorkerListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultWorkerListParams.RANK_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS_AVG, outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_MISS_RATE));


                // 詳細リストセル
                expparam.set(PctUserResultWorkerListParams.WORKER_NO, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_ID));
                expparam.set(PctUserResultWorkerListParams.WORKER_NAME, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_NAME));
                expparam.set(PctUserResultWorkerListParams.LEVEL1, outparam.get(LstPCTUserResultWorkerDASCHParams.LEVEL));
                expparam.set(PctUserResultWorkerListParams.RANK, outparam.get(LstPCTUserResultWorkerDASCHParams.RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.START_DATE, outparam.get(LstPCTUserResultWorkerDASCHParams.START_TIME));
                expparam.set(PctUserResultWorkerListParams.END_DATE, outparam.get(LstPCTUserResultWorkerDASCHParams.END_TIME));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT, outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT, outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT, outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT, outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT, outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS, outparam.get(LstPCTUserResultWorkerDASCHParams.MISS_RATE));


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
            DBUtil.close(conn);
        }
    }

    /**
     * 作業日別印刷処理
     * @param confirm
     * @throws ValidateException
     */
    private void printWorkDate(boolean confirm)
            throws ValidateException
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
        LstPCTUserResultWorkDateDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultWorkDateDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultWorkDateDASCHParams inparam = new LstPCTUserResultWorkDateDASCHParams();
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.USEDAY_OF_WEEK_FLAG,
                    rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctUserResultWorkDateList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultWorkDateListParams expparam = new PctUserResultWorkDateListParams();

                expparam.set(PctUserResultWorkDateListParams.SYS_DAY, outparam.get(LstPCTUserResultWorkDateDASCHParams.SYS_DAY));
                expparam.set(PctUserResultWorkDateListParams.SYS_TIME, outparam.get(LstPCTUserResultWorkDateDASCHParams.SYS_TIME));

                // 検索条件
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultWorkDateListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultWorkDateListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultWorkDateListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultWorkDateListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultWorkDateListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultWorkDateListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultWorkDateListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultWorkDateListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkDateListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultWorkDateListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.MISS_AVG, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY, outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKDAY));
                expparam.set(PctUserResultWorkDateListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultWorkDateListParams.START_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.START_TIME));
                expparam.set(PctUserResultWorkDateListParams.END_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.END_TIME));
                expparam.set(PctUserResultWorkDateListParams.HEADS, outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultWorkDateListParams.WORK_TIME, outparam.get(LstPCTUserResultWorkDateDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.MISS, outparam.get(LstPCTUserResultWorkDateDASCHParams.MISS_RATE));

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
            DBUtil.close(conn);
        }
    }

    /**
     * 荷主別印刷処理
     * @throws ValidateException 
     */
    private void printConsignor(boolean confirm)
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultConsignorDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultConsignorDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultConsignorDASCHParams inparam = new LstPCTUserResultConsignorDASCHParams();
            inparam.set(LstPCTUserResultConsignorDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.USEDAY_OF_WEEK_FLAG,
                    rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultConsignorDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctUserResultConsignorList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultConsignorListParams expparam = new PctUserResultConsignorListParams();

                expparam.set(PctUserResultConsignorListParams.SYS_DAY, outparam.get(LstPCTUserResultConsignorDASCHParams.SYS_DAY));
                expparam.set(PctUserResultConsignorListParams.SYS_TIME, outparam.get(LstPCTUserResultConsignorDASCHParams.SYS_TIME));

                // 検索条件
                expparam.set(PctUserResultConsignorListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultConsignorListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultConsignorListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultConsignorListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultConsignorListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultConsignorListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultConsignorListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultConsignorListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultConsignorListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultConsignorListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultConsignorListParams.CNT_AVG, outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_CART_COUNT));
                expparam.set(PctUserResultConsignorListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.MISS_AVG, outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_CODE1,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_NAME1,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultConsignorListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultConsignorListParams.START_DATE, outparam.get(LstPCTUserResultConsignorDASCHParams.START_TIME));
                expparam.set(PctUserResultConsignorListParams.END_DATE, outparam.get(LstPCTUserResultConsignorDASCHParams.END_TIME));
                expparam.set(PctUserResultConsignorListParams.HEADS, outparam.get(LstPCTUserResultConsignorDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultConsignorListParams.CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultConsignorListParams.WORK_TIME, outparam.get(LstPCTUserResultConsignorDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT, outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.MISS, outparam.get(LstPCTUserResultConsignorDASCHParams.MISS_RATE));

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
            DBUtil.close(conn);
        }
    }

    /**
     * エリア別印刷処理
     * @throws ValidateException 
     * 
     */
    private void printArea(boolean confirm)
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultAreaDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultAreaDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultAreaDASCHParams inparam = new LstPCTUserResultAreaDASCHParams();
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultAreaDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctUserResultAreaList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultAreaListParams expparam = new PctUserResultAreaListParams();
                expparam.set(PctUserResultAreaListParams.SYS_DAY, outparam.get(LstPCTUserResultAreaDASCHParams.SYS_DAY));
                expparam.set(PctUserResultAreaListParams.SYS_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.SYS_TIME));

                // 検索条件
                expparam.set(PctUserResultAreaListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultAreaListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultAreaListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultAreaListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultAreaListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultAreaListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultAreaListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultAreaListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.HEADS_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS_AVG, outparam.get(LstPCTUserResultAreaDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultAreaListParams.AREA_NO1, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NO));
                expparam.set(PctUserResultAreaListParams.AREA_NAME1, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NAME));
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.START_DATE, outparam.get(LstPCTUserResultAreaDASCHParams.START_TIME));
                expparam.set(PctUserResultAreaListParams.END_DATE, outparam.get(LstPCTUserResultAreaDASCHParams.END_TIME));
                expparam.set(PctUserResultAreaListParams.HEADS, outparam.get(LstPCTUserResultAreaDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT, outparam.get(LstPCTUserResultAreaDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS, outparam.get(LstPCTUserResultAreaDASCHParams.MISS_RATE));


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
            DBUtil.close(conn);
        }
    }

    /**
     * バッチ別印刷処理
     * @throws ValidateException 
     */
    private void printBatch(boolean confirm)
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
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
            inparam.set(LstPCTUserResultBatchDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
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
            inparam.set(LstPCTUserResultBatchDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
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

                // 検索条件
                expparam.set(PctUserResultBatchListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultBatchListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultBatchListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultBatchListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultBatchListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultBatchListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultBatchListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultBatchListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS_AVG, outparam.get(LstPCTUserResultBatchDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultBatchListParams.BATCH_NO1, outparam.get(LstPCTUserResultBatchDASCHParams.BATCH_NO));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.START_DATE, outparam.get(LstPCTUserResultBatchDASCHParams.START_TIME));
                expparam.set(PctUserResultBatchListParams.END_DATE, outparam.get(LstPCTUserResultBatchDASCHParams.END_TIME));
                expparam.set(PctUserResultBatchListParams.HEADS, outparam.get(LstPCTUserResultBatchDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT, outparam.get(LstPCTUserResultBatchDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME, outparam.get(LstPCTUserResultBatchDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT, outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_LINE_COUNT));
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
            DBUtil.close(conn);
        }
    }

    /**
     * ユーザー別XLS出力
     * @throws ValidateException 
     * 
     */
    private void xlsWorker()
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultWorkerDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultWorkerDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultWorkerDASCHParams inparam = new LstPCTUserResultWorkerDASCHParams();
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultWorkerDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultWorkerDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newExcelExporter("PctUserResultWorkerList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultWorkerListParams expparam = new PctUserResultWorkerListParams();
                // 検索条件
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultWorkerListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultWorkerListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultWorkerListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultWorkerListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultWorkerListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultWorkerListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultWorkerListParams.RANK_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS_AVG,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.AVG_MISS_RATE));


                // 詳細リストセル
                expparam.set(PctUserResultWorkerListParams.WORKER_NO,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.USER_ID));
                expparam.set(PctUserResultWorkerListParams.WORKER_NAME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.USER_NAME));
                expparam.set(PctUserResultWorkerListParams.LEVEL1,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LEVEL));
                expparam.set(PctUserResultWorkerListParams.RANK, outparam.get(LstPCTUserResultWorkerDASCHParams.RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.START_DATE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.START_TIME));
                expparam.set(PctUserResultWorkerListParams.END_DATE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.END_TIME));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.MISS_RATE));

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
            DBUtil.close(conn);
        }
    }

    /**
     * 作業日別XLS出力
     * @throws ValidateException 
     * 
     */
    private void xlsWorkDate()
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultWorkDateDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultWorkDateDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultWorkDateDASCHParams inparam = new LstPCTUserResultWorkDateDASCHParams();
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.USEDAY_OF_WEEK_FLAG,
                    rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultWorkDateDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newExcelExporter("PctUserResultWorkDateList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultWorkDateListParams expparam = new PctUserResultWorkDateListParams();
                // 検索条件
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultWorkDateListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultWorkDateListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultWorkDateListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultWorkDateListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultWorkDateListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultWorkDateListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultWorkDateListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultWorkDateListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkDateListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultWorkDateListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.MISS_AVG,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultWorkDateListParams.WORK_DAY,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKDAY));
                expparam.set(PctUserResultWorkDateListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultWorkDateListParams.START_DATE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.START_TIME));
                expparam.set(PctUserResultWorkDateListParams.END_DATE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.END_TIME));
                expparam.set(PctUserResultWorkDateListParams.HEADS,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultWorkDateListParams.WORK_TIME,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultWorkDateListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultWorkDateListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY));
                expparam.set(PctUserResultWorkDateListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultWorkDateListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkDateListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkDateListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkDateListParams.MISS,
                        outparam.get(LstPCTUserResultWorkDateDASCHParams.MISS_RATE));
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
            DBUtil.close(conn);
        }
    }


    /**
     * 荷主別XLS出力処理
     * @throws ValidateException 
     * 
     */
    private void xlsConsignor()
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultConsignorDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new LstPCTUserResultConsignorDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            LstPCTUserResultConsignorDASCHParams inparam = new LstPCTUserResultConsignorDASCHParams();
            inparam.set(LstPCTUserResultConsignorDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.USEDAY_OF_WEEK_FLAG,
                    rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultConsignorDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_A, chk_levelA.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_B, chk_levelB.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.LEVEL_C, chk_levelC.getValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultConsignorDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
            exporter = factory.newExcelExporter("PctUserResultConsignorList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultConsignorListParams expparam = new PctUserResultConsignorListParams();
                // 検索条件
                expparam.set(PctUserResultConsignorListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultConsignorListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultConsignorListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultConsignorListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultConsignorListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultConsignorListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultConsignorListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultConsignorListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultConsignorListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultConsignorListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultConsignorListParams.CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_CART_COUNT));
                expparam.set(PctUserResultConsignorListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.MISS_AVG,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_CODE1,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctUserResultConsignorListParams.CONSIGNOR_NAME1,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultConsignorListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultConsignorListParams.START_DATE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.START_TIME));
                expparam.set(PctUserResultConsignorListParams.END_DATE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.END_TIME));
                expparam.set(PctUserResultConsignorListParams.HEADS,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultConsignorListParams.CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultConsignorListParams.WORK_TIME,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultConsignorListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultConsignorListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY));
                expparam.set(PctUserResultConsignorListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultConsignorListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultConsignorListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultConsignorListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultConsignorListParams.MISS,
                        outparam.get(LstPCTUserResultConsignorDASCHParams.MISS_RATE));
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
            DBUtil.close(conn);
        }
    }

    /**
     * エリア別XLS出力処理
     * @throws ValidateException 
     */
    private void xlsArea()
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
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
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_FROM, txt_WorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_TO, txt_ToWorkDate.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
            inparam.set(LstPCTUserResultAreaDASCHParams.MONDAY, chk_Monday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.TUESDAY, chk_Tuesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.WEDNESDAY, chk_Wednesday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.THURSDAY, chk_Thursday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.FRIDAY, chk_Friday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SATURDAY, chk_Saturday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.SUNDAY, chk_Sunday.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(LstPCTUserResultAreaDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
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
            exporter = factory.newExcelExporter("PctUserResultAreaList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctUserResultAreaListParams expparam = new PctUserResultAreaListParams();
                // 検索条件
                expparam.set(PctUserResultAreaListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultAreaListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultAreaListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultAreaListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultAreaListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultAreaListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultAreaListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultAreaListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS_AVG,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultAreaListParams.AREA_NO1,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NO));
                expparam.set(PctUserResultAreaListParams.AREA_NAME1,
                        outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NAME));
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.START_DATE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.START_TIME));
                expparam.set(PctUserResultAreaListParams.END_DATE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.END_TIME));
                expparam.set(PctUserResultAreaListParams.HEADS,
                        outparam.get(LstPCTUserResultAreaDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME,
                        outparam.get(LstPCTUserResultAreaDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS, outparam.get(LstPCTUserResultAreaDASCHParams.MISS_RATE));
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
            DBUtil.close(conn);
        }
    }


    /**
     * バッチ別XLS出力処理
     * @throws ValidateException 
     */
    private void xlsBatch()
            throws ValidateException
    {
        // input validation.
        txt_WorkDate.validate(this, false);
        txt_ToWorkDate.validate(this, false);
        rdo_DateSelect_All.validate(false);
        txt_ConsignorCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        rdo_CollectCondition_Worker.validate(false);
        rdo_DisplayRank_Lot.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstPCTUserResultBatchDASCH dasch = null;
        Exporter exporter = null;
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
            inparam.set(LstPCTUserResultBatchDASCHParams.USEDAY_OF_WEEK_FLAG, rdo_DateSelect_Assignation.getChecked());
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
            inparam.set(LstPCTUserResultBatchDASCHParams.COLLECT_CONDITION,
                    _grp_rdo_CollectCondition.getSelectedValue());
            inparam.set(LstPCTUserResultBatchDASCHParams.DISPLAY_RANK, _grp_rdo_DisplayRank.getSelectedValue());

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
                // 検索条件
                expparam.set(PctUserResultBatchListParams.WORK_DAY_FROM, txt_WorkDate.getValue());
                expparam.set(PctUserResultBatchListParams.WORK_DAY_TO, txt_ToWorkDate.getValue());
                expparam.set(PctUserResultBatchListParams.WEEK_DAY, toDispWeek());
                expparam.set(PctUserResultBatchListParams.BATCH_NO, txt_BatchNo.getValue());
                expparam.set(PctUserResultBatchListParams.LEVEL, toDispLevel());
                expparam.set(PctUserResultBatchListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                expparam.set(
                        PctUserResultBatchListParams.CONSIGNOR_NAME,
                        StringUtil.isBlank(txt_ConsignorCode.getText()) ? ""
                                                                       : outparam.get(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctUserResultBatchListParams.AREA_NO, pul_Area.getSelectedValue());
                expparam.set(
                        PctUserResultBatchListParams.AREA_NAME,
                        WmsParam.ALL_AREA_NO.equals(pul_Area.getSelectedValue()) ? DispResources.getText("CMB-W0023")
                                                                                : outparam.get(LstPCTUserResultWorkerDASCHParams.AREA_NAME));

                // 平均リストセル
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.HEADS_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS_AVG,
                        outparam.get(LstPCTUserResultBatchDASCHParams.AVG_MISS_RATE));

                // 詳細リストセル
                expparam.set(PctUserResultBatchListParams.BATCH_NO1,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BATCH_NO));
                expparam.set(PctUserResultBatchListParams.PRODUCTION_RATE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PRODUCTION_RATE));
                expparam.set(PctUserResultBatchListParams.START_DATE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.START_TIME));
                expparam.set(PctUserResultBatchListParams.END_DATE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.END_TIME));
                expparam.set(PctUserResultBatchListParams.HEADS,
                        outparam.get(LstPCTUserResultBatchDASCHParams.WORKER_COUNT));
                expparam.set(PctUserResultBatchListParams.CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.TERMINAL_COUNT));
                expparam.set(PctUserResultBatchListParams.WORK_TIME,
                        outparam.get(LstPCTUserResultBatchDASCHParams.OPERATE_TIME));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT));
                expparam.set(PctUserResultBatchListParams.ORDER_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT));
                expparam.set(PctUserResultBatchListParams.BOX_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LINE_CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.LINE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.LOT_CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY));
                expparam.set(PctUserResultBatchListParams.LOT_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY));
                expparam.set(PctUserResultBatchListParams.PIECE_CNT_PER_HOUR,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultBatchListParams.BOX_PER_ORDER,
                        outparam.get(LstPCTUserResultBatchDASCHParams.BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LINE_PER_ORDER,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultBatchListParams.LOT_PER_LINE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.PIECE_PER_LINE,
                        outparam.get(LstPCTUserResultBatchDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultBatchListParams.MISS,
                        outparam.get(LstPCTUserResultBatchDASCHParams.MISS_RATE));
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
            DBUtil.close(conn);
        }
    }

    /**
     * 表示用曜日に変換処理
     * 
     * @return String 表示用曜日
     */
    private String toDispLevel()
    {
        // レベル
        String level = "";
        if (chk_levelA.getChecked())
        {
            level = DispResources.getText("LBL-P0247");
        }
        if (chk_levelB.getChecked())
        {
            level = level + DispResources.getText("LBL-P0248");
        }
        if (chk_levelC.getChecked())
        {
            level = level + DispResources.getText("LBL-P0249");
        }
        return level;
    }

    /**
     * 表示用曜日に変換処理
     * 
     * @return String 表示用曜日
     */
    private String toDispWeek()
    {
        // 曜日
        String dayOfWeek = "";
        // 月曜
        if (chk_Monday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0001");
        }
        // 火曜
        if (chk_Tuesday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0002");
        }
        // 水曜
        if (chk_Wednesday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0003");
        }
        // 木曜
        if (chk_Thursday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0004");
        }
        // 金曜
        if (chk_Friday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0005");
        }
        // 土曜
        if (chk_Saturday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0006");
        }
        // 日曜
        if (chk_Sunday.getChecked())
        {
            dayOfWeek += DispResources.getText("CHK-P0007");
        }
        return dayOfWeek;
    }

    // DFKLOOK:ここまで修正

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
