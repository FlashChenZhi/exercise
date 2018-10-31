// $Id: PCTRetRftNoWorkInquiryBusiness.java 7522 2010-03-13 06:01:48Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctretrftnoworkinquiry;

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
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetRftNoWorkInquiryDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.PCTRetRftNoWorkInquiryDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctRetRftNoWorkInqListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctretrftnoworkinquiry.LstPCTRetRftNoWorkInquiryParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * Pカート別作業照会の画面処理を行います。
 *
 * @version $Revision: 7522 $, $Date: 2010-03-13 15:01:48 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTRetRftNoWorkInquiryBusiness
        extends PCTRetRftNoWorkInquiry
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTRetRftNoWorkInquiryBusiness()
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
        if (eventSource.equals("btn_P_Display_Click"))
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_PcartNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_PcartNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstPCTRetRftNoWorkInquiryParams inparam = new LstPCTRetRftNoWorkInquiryParams();
        inparam.set(LstPCTRetRftNoWorkInquiryParams.PCART_RFT_NO, txt_PcartNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Display_Click");
        redirect("/pcart/retrieval/listbox/pctretrftnoworkinquiry/LstPCTRetRftNoWorkInquiry.do", forwardParam,
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
        txt_PcartNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetRftNoWorkInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetRftNoWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetRftNoWorkInquiryDASCHParams inparam = new PCTRetRftNoWorkInquiryDASCHParams();
            inparam.set(PCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO, txt_PcartNo.getValue());

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
            exporter = factory.newPrinterExporter("PctRetRftNoWorkInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetRftNoWorkInqListParams expparam = new PctRetRftNoWorkInqListParams();
                expparam.set(PctRetRftNoWorkInqListParams.RFT_NO, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO));
                expparam.set(PctRetRftNoWorkInqListParams.USER_ID, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.USER_ID));
                expparam.set(PctRetRftNoWorkInqListParams.USER_NAME, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.USER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NO, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NAME, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.AREA_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_CODE,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_CODE, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_NAME, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.SYS_DAY, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.SYS_DAY));
                expparam.set(PctRetRftNoWorkInqListParams.SYS_TIME, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.SYS_TIME));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_ZONE_NO, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_ZONE_NO));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_LOCATION_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_LOCATION));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_CODE, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_NAME, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.LOT_QTY, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_QTY, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.RESULT_QTY, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.JAN_CODE, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.JAN));
                expparam.set(PctRetRftNoWorkInqListParams.ITF, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ITF));
                expparam.set(PctRetRftNoWorkInqListParams.BUNDLE_ITF, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.BUNDLE_ITF));
                expparam.set(PctRetRftNoWorkInqListParams.ORDER_NO, outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ORDER_NO));
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
        txt_PcartNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetRftNoWorkInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTRetRftNoWorkInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTRetRftNoWorkInquiryDASCHParams inparam = new PCTRetRftNoWorkInquiryDASCHParams();
            inparam.set(PCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO, txt_PcartNo.getValue());

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
            exporter = factory.newExcelExporter("PctRetRftNoWorkInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctRetRftNoWorkInqListParams expparam = new PctRetRftNoWorkInqListParams();
                expparam.set(PctRetRftNoWorkInqListParams.RFT_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PCART_RFT_NO));
                expparam.set(PctRetRftNoWorkInqListParams.USER_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.USER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.AREA_NO));
                expparam.set(PctRetRftNoWorkInqListParams.AREA_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.AREA_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_CODE,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.REGULAR_CUSTOMER_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.REGULAR_CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_CODE,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.CUSTOMER_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_ZONE_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_ZONE_NO));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_LOCATION_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_LOCATION));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_CODE,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctRetRftNoWorkInqListParams.ITEM_NAME,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctRetRftNoWorkInqListParams.LOT_QTY,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.LOT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.PLAN_QTY,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.PLAN_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.RESULT_QTY,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.RESULT_QTY));
                expparam.set(PctRetRftNoWorkInqListParams.ORDER_NO,
                        outparam.get(PCTRetRftNoWorkInquiryDASCHParams.ORDER_NO));
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
        txt_PcartNo.setValue(null);

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
