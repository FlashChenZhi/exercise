// $Id: PCTRetResultWorkInquiryBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctretresultworkinquiry;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetResultWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetResultWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.display.pctretresultworkinquiry.PCTRetResultWorkInquiry;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetResultWorkInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.customer.LstCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.item.LstItemParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretresultworkinquiry.LstPCTRetResultWorkInquiryParams;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.userid.LstUserIdParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultWorkInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultWorkInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
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
public class PCTRetResultWorkInquiryBusiness
        extends PCTRetResultWorkInquiry
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
    public PCTRetResultWorkInquiryBusiness()
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
        else if (eventSource.equals("btn_P_Search_User_Click"))
        {
            // process call.
            btn_P_Search_User_Click_DlgBack(dialogParams);
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
    public void btn_P_Search_User_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_User_Click_Process();
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
        PCTRetResultWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetResultWorkInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTRetResultWorkInquirySCHParams inparam = new PCTRetResultWorkInquirySCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTRetResultWorkInquirySCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTRetResultWorkInquirySCHParams.CONSIGNOR_NAME));
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
    private void btn_P_Search_RegularCustomerCd_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegularCustomerParams inparam = new LstRegularCustomerParams();
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.WORK_DAY, txt_WorkDate.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstRegularCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_RESULT);

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
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstCustomerParams.WORK_DAY, txt_WorkDate.getValue());
        inparam.set(LstCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_RESULT);

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
        inparam.set(LstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstItemParams.WORK_DAY, txt_WorkDate.getValue());
        inparam.set(LstItemParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstItemParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstItemParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstItemParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstItemParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstItemParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_RESULT);

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
    private void btn_P_Search_User_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstUserIdParams inparam = new LstUserIdParams();
        inparam.set(LstUserIdParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstUserIdParams.WORK_DAY, txt_WorkDate.getValue());
        inparam.set(LstUserIdParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstUserIdParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstUserIdParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstUserIdParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstUserIdParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstUserIdParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstUserIdParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstUserIdParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
        inparam.set(LstUserIdParams.USER_ID, txt_User.getValue());
        inparam.set(LstUserIdParams.SEARCHTABLE, InParameter.SEARCH_TABLE_RESULT);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_User_Click");
        redirect("/pcart/retrieval/listbox/userid/LstUserId.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_User_Click_DlgBack(DialogParameters dialogParams)
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
        LstPCTRetResultWorkInquiryParams inparam = new LstPCTRetResultWorkInquiryParams();
        inparam.set(LstPCTRetResultWorkInquiryParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.WORK_DAY, txt_WorkDate.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
        inparam.set(LstPCTRetResultWorkInquiryParams.USER_ID, txt_User.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Display_Click");
        redirect("/pcart/retrieval/listbox/pctretresultworkinquiry/LstPCTRetResultWorkInquiry.do", forwardParam, "/Progress.do");
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
        txt_WorkDate.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_RegularCustomerCode.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_User.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetResultWorkInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetResultWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetResultWorkInquiryDASCHParams inparam = new PCTRetResultWorkInquiryDASCHParams();
            inparam.set(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.WORK_DAY, txt_WorkDate.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.USER_ID, txt_User.getValue());

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
            exporter = factory.newPrinterExporter("PctRetResultWorkInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetResultWorkInqListParams expparam = new PctRetResultWorkInqListParams();
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetResultWorkInqListParams.WORK_DAY, outparam.get(PCTRetResultWorkInquiryDASCHParams.WORK_DAY));
                expparam.set(PctRetResultWorkInqListParams.BATCH_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetResultWorkInqListParams.BATCH_SEQ_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.ORDER_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetResultWorkInqListParams.SYS_DAY, outparam.get(PCTRetResultWorkInquiryDASCHParams.SYS_DAY));
                expparam.set(PctRetResultWorkInqListParams.SYS_TIME, outparam.get(PCTRetResultWorkInquiryDASCHParams.SYS_TIME));
                expparam.set(PctRetResultWorkInqListParams.ITEM_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetResultWorkInqListParams.ITEM_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetResultWorkInqListParams.JAN_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.JAN));
                expparam.set(PctRetResultWorkInqListParams.ITF, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITF));
                expparam.set(PctRetResultWorkInqListParams.BUNDLE_ITF, outparam.get(PCTRetResultWorkInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetResultWorkInqListParams.LOT_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetResultWorkInqListParams.PLAN_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetResultWorkInqListParams.RESULT_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetResultWorkInqListParams.STATUS, outparam.get(PCTRetResultWorkInquiryDASCHParams.JOB_STATUS));
                expparam.set(PctRetResultWorkInqListParams.PLAN_AREA_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.ZONE_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_LOCATION_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.LOCATION_NO));
                expparam.set(PctRetResultWorkInqListParams.TERMINAL_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.TERMINAL_NO));
                expparam.set(PctRetResultWorkInqListParams.USER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.USER_NAME));
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
        txt_ConsignorCode.validate(this, true);
        txt_WorkDate.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_RegularCustomerCode.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_User.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetResultWorkInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetResultWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetResultWorkInquiryDASCHParams inparam = new PCTRetResultWorkInquiryDASCHParams();
            inparam.set(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.WORK_DAY, txt_WorkDate.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            inparam.set(PCTRetResultWorkInquiryDASCHParams.USER_ID, txt_User.getValue());

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
            exporter = factory.newExcelExporter("PctRetResultWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetResultWorkInqListParams expparam = new PctRetResultWorkInqListParams();
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetResultWorkInqListParams.CONSIGNOR_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetResultWorkInqListParams.WORK_DAY, outparam.get(PCTRetResultWorkInquiryDASCHParams.WORK_DAY));
                expparam.set(PctRetResultWorkInqListParams.BATCH_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetResultWorkInqListParams.BATCH_SEQ_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.REGULAR_CUSTOMER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetResultWorkInqListParams.CUSTOMER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetResultWorkInqListParams.ORDER_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetResultWorkInqListParams.ITEM_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetResultWorkInqListParams.ITEM_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetResultWorkInqListParams.JAN_CODE, outparam.get(PCTRetResultWorkInquiryDASCHParams.JAN));
                expparam.set(PctRetResultWorkInqListParams.ITF, outparam.get(PCTRetResultWorkInquiryDASCHParams.ITF));
                expparam.set(PctRetResultWorkInqListParams.BUNDLE_ITF, outparam.get(PCTRetResultWorkInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetResultWorkInqListParams.LOT_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetResultWorkInqListParams.PLAN_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetResultWorkInqListParams.RESULT_QTY, outparam.get(PCTRetResultWorkInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetResultWorkInqListParams.STATUS, outparam.get(PCTRetResultWorkInquiryDASCHParams.JOB_STATUS));
                expparam.set(PctRetResultWorkInqListParams.PLAN_AREA_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.ZONE_NO));
                expparam.set(PctRetResultWorkInqListParams.PLAN_LOCATION_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.LOCATION_NO));
                expparam.set(PctRetResultWorkInqListParams.TERMINAL_NO, outparam.get(PCTRetResultWorkInquiryDASCHParams.TERMINAL_NO));
                expparam.set(PctRetResultWorkInqListParams.USER_NAME, outparam.get(PCTRetResultWorkInquiryDASCHParams.USER_NAME));
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
        txt_ConsignorCode.setValue(null);
        txt_ConsignorName.setValue(null);
        txt_WorkDate.setValue(null);
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
