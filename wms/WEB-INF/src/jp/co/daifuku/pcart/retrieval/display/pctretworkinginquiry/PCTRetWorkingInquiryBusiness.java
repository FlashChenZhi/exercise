// $Id: PCTRetWorkingInquiryBusiness.java 7522 2010-03-13 06:01:48Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctretworkinginquiry;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetWorkingInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetWorkingInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetWorkingInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.customer.LstCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.item.LstItemParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretworkinginquiry.LstPCTRetWorkingInquiryParams;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.userid.LstUserIdParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetWorkingInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetWorkingInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 作業状況照会の画面処理を行います。
 *
 * @version $Revision: 7522 $, $Date:: 2010-03-13 15:01:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTRetWorkingInquiryBusiness
        extends PCTRetWorkingInquiry
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
    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    /** PullDownModel pul_WorkStatus */
    private DefaultPullDownModel _pdm_pul_WorkStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTRetWorkingInquiryBusiness()
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
        if (eventSource.equals("btn_P_Search_RegularCustomerCd_Click"))
        {
            // process call.
            btn_P_Search_RegularCustomerCd_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_Search_CustomerCode_Click"))
        {
            // process call.
            btn_P_Search_CustomerCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_Search_ItemCode_Click"))
        {
            // process call.
            btn_P_Search_ItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_User_Click"))
        {
            // process call.
            btn_User_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_Display_Click"))
        {
            // process call.
            btn_P_Display_Click_DlgBack(dialogParams);
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
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setFocus(txt_PlanDay);
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
        // DFKLOOK:ここまで修正
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
    public void btn_P_Search_CustomerCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_CustomerCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_ItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_ItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_User_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_User_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Display_Click_Process();
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

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

        // initialize pul_WorkStatus.
        _pdm_pul_WorkStatus = new DefaultPullDownModel(pul_WorkStatus, locale, ui);

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

            // load pul_WorkStatus.
            _pdm_pul_WorkStatus.init(conn);

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
        PCTRetWorkingInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetWorkingInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTRetWorkingInquirySCHParams inparam = new PCTRetWorkingInquirySCHParams();

            // SCH call.
            // DFKLOOK:ここから修正
            Params outparam = sch.initFind(inparam);

            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTRetWorkingInquirySCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTRetWorkingInquirySCHParams.CONSIGNOR_NAME));
            }
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstRegularCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_WORK);

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
    private void btn_P_Search_CustomerCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_WORK);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_CustomerCode_Click");
        redirect("/pcart/retrieval/listbox/customer/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_CustomerCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstCustomerParams.CUSTOMER_NAME));

        // set focus.
        setFocus(txt_CustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Search_ItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstItemParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstItemParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstItemParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstItemParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstItemParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstItemParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstItemParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_WORK);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_ItemCode_Click");
        redirect("/pcart/retrieval/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_ItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_User_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstUserIdParams inparam = new LstUserIdParams();
        inparam.set(LstUserIdParams.USER_ID, txt_User.getValue());
        inparam.set(LstUserIdParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstUserIdParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstUserIdParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstUserIdParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstUserIdParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstUserIdParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstUserIdParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstUserIdParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstUserIdParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstUserIdParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
        inparam.set(LstUserIdParams.SEARCHTABLE, InParameter.SEARCH_TABLE_WORK);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_User_Click");
        redirect("/pcart/retrieval/listbox/userid/LstUserId.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_User_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstUserIdParams outparam = new LstUserIdParams(dialogParams);
        txt_User.setValue(outparam.get(LstUserIdParams.USER_ID));
        txt_UserName.setValue(outparam.get(LstUserIdParams.USER_NAME));

        // set focus.
        setFocus(txt_User);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstPCTRetWorkingInquiryParams inparam = new LstPCTRetWorkingInquiryParams();
        inparam.set(LstPCTRetWorkingInquiryParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstPCTRetWorkingInquiryParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstPCTRetWorkingInquiryParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
        inparam.set(LstPCTRetWorkingInquiryParams.USER, txt_User.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Display_Click");
        redirect("/pcart/retrieval/listbox/pctretworkinginquiry/LstPCTRetWorkingInquiry.do", forwardParam,
                "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Display_Click_DlgBack(DialogParameters dialogParams)
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
        txt_ConsignorCode.validate(this, true);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetWorkingInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetWorkingInquiryDASCHParams inparam = new PCTRetWorkingInquiryDASCHParams();
            inparam.set(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.PLAN_DAY, txt_PlanDay.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.USER, txt_User.getValue());

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
            exporter = factory.newPrinterExporter("PctRetWorkingInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE, outparam.get(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME, outparam.get(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY, outparam.get(PCTRetWorkingInquiryDASCHParams.PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE, outparam.get(PCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME, outparam.get(PCTRetWorkingInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.SYS_DAY, outparam.get(PCTRetWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(PctRetWorkingInqListParams.SYS_TIME, outparam.get(PCTRetWorkingInquiryDASCHParams.SYS_TIME));
                expparam.set(PctRetWorkingInqListParams.ITEM_CODE, outparam.get(PCTRetWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetWorkingInqListParams.JAN_CODE, outparam.get(PCTRetWorkingInquiryDASCHParams.JAN));
                expparam.set(PctRetWorkingInqListParams.ITEM_NAME, outparam.get(PCTRetWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_QTY, outparam.get(PCTRetWorkingInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetWorkingInqListParams.RESULT_QTY, outparam.get(PCTRetWorkingInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetWorkingInqListParams.STATUS, outparam.get(PCTRetWorkingInquiryDASCHParams.JOB_STATUS));
                expparam.set(PctRetWorkingInqListParams.LOT_QTY, outparam.get(PCTRetWorkingInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetWorkingInqListParams.PLAN_AREA_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.ZONE_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_LOCATION_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(PctRetWorkingInqListParams.ITF, outparam.get(PCTRetWorkingInquiryDASCHParams.ITF));
                expparam.set(PctRetWorkingInqListParams.BUNDLE_ITF, outparam.get(PCTRetWorkingInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetWorkingInqListParams.TERMINAL_NO, outparam.get(PCTRetWorkingInquiryDASCHParams.TERMINAL_NO));
                expparam.set(PctRetWorkingInqListParams.USER_NAME, outparam.get(PCTRetWorkingInquiryDASCHParams.USER_NAME));
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
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {

        // input validation.
        txt_ConsignorCode.validate(this, true);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetWorkingInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetWorkingInquiryDASCHParams inparam = new PCTRetWorkingInquiryDASCHParams();
            inparam.set(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.PLAN_DAY, txt_PlanDay.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            inparam.set(PCTRetWorkingInquiryDASCHParams.USER, txt_User.getValue());

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
            exporter = factory.newExcelExporter("PctRetWorkingInqList", getSession());
            File downloadFile = exporter.open();

            // export.8
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetWorkingInqListParams expparam = new PctRetWorkingInqListParams();
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_CODE,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetWorkingInqListParams.CONSIGNOR_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_DAY,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.PLAN_DAY));
                expparam.set(PctRetWorkingInqListParams.BATCH_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetWorkingInqListParams.BATCH_SEQ_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetWorkingInqListParams.ORDER_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_CODE,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.REGULAR_CUSTOMER_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_CODE,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetWorkingInqListParams.CUSTOMER_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetWorkingInqListParams.ITEM_CODE,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetWorkingInqListParams.JAN_CODE, outparam.get(PCTRetWorkingInquiryDASCHParams.JAN));
                expparam.set(PctRetWorkingInqListParams.ITEM_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetWorkingInqListParams.PLAN_QTY,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetWorkingInqListParams.RESULT_QTY,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetWorkingInqListParams.STATUS,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.JOB_STATUS));
                expparam.set(PctRetWorkingInqListParams.LOT_QTY, outparam.get(PCTRetWorkingInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetWorkingInqListParams.PLAN_AREA_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_ZONE_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.ZONE_NO));
                expparam.set(PctRetWorkingInqListParams.PLAN_LOCATION_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(PctRetWorkingInqListParams.ITF, outparam.get(PCTRetWorkingInquiryDASCHParams.ITF));
                expparam.set(PctRetWorkingInqListParams.BUNDLE_ITF,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetWorkingInqListParams.TERMINAL_NO,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.TERMINAL_NO));
                expparam.set(PctRetWorkingInqListParams.USER_NAME,
                        outparam.get(PCTRetWorkingInquiryDASCHParams.USER_NAME));
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
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ConsignorName.setValue(null);
        txt_PlanDay.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNo.setValue(null);
        _pdm_pul_AreaNo.setSelectedValue(null);
        txt_RegularCustomerCode.setValue(null);
        txt_RegularCustomerName.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_OrderNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        _pdm_pul_WorkStatus.setSelectedValue(null);
        txt_User.setValue(null);
        txt_UserName.setValue(null);

        // DFKLOOK:ここから修正
        page_Load_Process();
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
