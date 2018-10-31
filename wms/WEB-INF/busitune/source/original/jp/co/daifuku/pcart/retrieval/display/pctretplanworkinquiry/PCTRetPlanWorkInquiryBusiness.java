// $Id: PCTRetPlanWorkInquiryBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctretplanworkinquiry;

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
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetPlanWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetPlanWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.display.pctretplanworkinquiry.PCTRetPlanWorkInquiry;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetPlanWorkInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.customer.LstCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretplanworkinquiry.LstPCTRetPlanWorkInquiryParams;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetPlanWorkInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetPlanWorkInquirySCHParams;
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
public class PCTRetPlanWorkInquiryBusiness
        extends PCTRetPlanWorkInquiry
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
    public PCTRetPlanWorkInquiryBusiness()
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
        PCTRetPlanWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetPlanWorkInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTRetPlanWorkInquirySCHParams inparam = new PCTRetPlanWorkInquirySCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTRetPlanWorkInquirySCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTRetPlanWorkInquirySCHParams.CONSIGNOR_NAME));
            }

            // clear.
            txt_PlanDay.setValue(null);
            txt_BatchNo.setValue(null);
            txt_BatchSeqNo.setValue(null);
            _pdm_pul_AreaNo.setSelectedValue(null);
            txt_RegularCustomerCode.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_OrderNo.setValue(null);
            _pdm_pul_WorkStatus.setSelectedValue(null);
            txt_ConsignorName.setReadOnly(true);
            txt_RegularCustomerName.setReadOnly(true);
            txt_CustomerName.setReadOnly(true);

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
        inparam.set(LstRegularCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstRegularCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
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
    private void btn_P_Search_CustomerCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstCustomerParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

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
    private void btn_P_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstPCTRetPlanWorkInquiryParams inparam = new LstPCTRetPlanWorkInquiryParams();
        inparam.set(LstPCTRetPlanWorkInquiryParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstPCTRetPlanWorkInquiryParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Display_Click");
        redirect("/pcart/retrieval/listbox/pctretplanworkinquiry/LstPCTRetPlanWorkInquiry.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Display_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);
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
        txt_PlanDay.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_RegularCustomerCode.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_OrderNo.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetPlanWorkInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetPlanWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetPlanWorkInquiryDASCHParams inparam = new PCTRetPlanWorkInquiryDASCHParams();
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY, txt_PlanDay.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());

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
            exporter = factory.newPrinterExporter("PctRetPlanWorkInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetPlanWorkInqListParams expparam = new PctRetPlanWorkInqListParams();
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_DAY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_SEQ_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.ORDER_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetPlanWorkInqListParams.SYS_DAY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.SYS_DAY));
                expparam.set(PctRetPlanWorkInqListParams.SYS_TIME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.SYS_TIME));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetPlanWorkInqListParams.JAN_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.JAN));
                expparam.set(PctRetPlanWorkInqListParams.ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITF));
                expparam.set(PctRetPlanWorkInqListParams.BUNDLE_ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetPlanWorkInqListParams.LOT_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetPlanWorkInqListParams.STATUS, outparam.get(PCTRetPlanWorkInquiryDASCHParams.JOB_STATUS));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_AREA_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ZONE_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_LOCATION_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.LOCATION_NO));
                expparam.set(PctRetPlanWorkInqListParams.TERMINAL_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.TERMINAL_NO));
                expparam.set(PctRetPlanWorkInqListParams.USER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.USER_NAME));
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
        txt_PlanDay.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_RegularCustomerCode.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_OrderNo.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetPlanWorkInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetPlanWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetPlanWorkInquiryDASCHParams inparam = new PCTRetPlanWorkInquiryDASCHParams();
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY, txt_PlanDay.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetPlanWorkInquiryDASCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());

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
            exporter = factory.newExcelExporter("PctRetPlanWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetPlanWorkInqListParams expparam = new PctRetPlanWorkInqListParams();
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CONSIGNOR_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_DAY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_DAY));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_NO));
                expparam.set(PctRetPlanWorkInqListParams.BATCH_SEQ_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BATCH_SEQ_NO));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.REGULAR_CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetPlanWorkInqListParams.CUSTOMER_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetPlanWorkInqListParams.ORDER_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ORDER_NO));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITEM_NAME, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetPlanWorkInqListParams.JAN_CODE, outparam.get(PCTRetPlanWorkInquiryDASCHParams.JAN_CODE));
                expparam.set(PctRetPlanWorkInqListParams.ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.ITF));
                expparam.set(PctRetPlanWorkInqListParams.BUNDLE_ITF, outparam.get(PCTRetPlanWorkInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetPlanWorkInqListParams.LOT_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_QTY, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetPlanWorkInqListParams.STATUS, outparam.get(PCTRetPlanWorkInquiryDASCHParams.STATUS));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_AREA_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_AREA_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_ZONE_NO));
                expparam.set(PctRetPlanWorkInqListParams.PLAN_LOCATION_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.PLAN_LOCATION_NO));
                expparam.set(PctRetPlanWorkInqListParams.TERMINAL_NO, outparam.get(PCTRetPlanWorkInquiryDASCHParams.TERMINAL_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // set focus.
            setFocus(txt_ConsignorCode);

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
        txt_PlanDay.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNo.setValue(null);
        _pdm_pul_AreaNo.setSelectedValue(null);
        txt_RegularCustomerCode.setValue(null);
        txt_RegularCustomerName.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_OrderNo.setValue(null);
        _pdm_pul_WorkStatus.setSelectedValue(null);
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
