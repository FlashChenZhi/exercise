// $Id: PCTItemMasterInquiryBusiness.java 7522 2010-03-13 06:01:48Z shibamoto $
package jp.co.daifuku.pcart.master.display.pctiteminquiry;

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
import jp.co.daifuku.pcart.master.dasch.PCTItemMasterInquiryDASCH;
import jp.co.daifuku.pcart.master.dasch.PCTItemMasterInquiryDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PctItemMasterListParams;
import jp.co.daifuku.pcart.master.listbox.pctitem.PCTLstItemParams;
import jp.co.daifuku.pcart.master.listbox.pctitemlist.PCTLstItemListParams;
import jp.co.daifuku.pcart.master.schedule.PCTItemMasterInquirySCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemMasterInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 商品マスタ照会の画面処理を行います。
 *
 * @version $Revision: 7522 $, $Date:: 2010-03-13 15:01:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTItemMasterInquiryBusiness
        extends PCTItemMasterInquiry
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
    /** PullDownModel pul_ItemImageSet */
    private DefaultPullDownModel _pdm_pul_ItemImageSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTItemMasterInquiryBusiness()
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
        if (eventSource.equals("btn_SearchItemCode_Click"))
        {
            // process call.
            btn_SearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchToItemCode_Click"))
        {
            // process call.
            btn_SearchToItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchJanCode_Click"))
        {
            // process call.
            btn_SearchJanCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchCaseITF_Click"))
        {
            // process call.
            btn_SearchCaseITF_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_Display_Click"))
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
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemCode_Click_Process();
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchToItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchToItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchJanCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchJanCode_Click_Process();
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchCaseITF_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchCaseITF_Click_Process();
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

        // initialize pul_ItemImageSet.
        _pdm_pul_ItemImageSet = new DefaultPullDownModel(pul_ItemImageSet, locale, ui);

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

            // load pul_ItemImageSet.
            _pdm_pul_ItemImageSet.init(conn);

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
    private void btn_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_ItemCode1.getValue());
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
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
        PCTItemMasterInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemMasterInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemMasterInquirySCHParams inparam = new PCTItemMasterInquirySCHParams();

            // DFKLOOK:ここから修正
            // SCH call.
            Params outparam = sch.initFind(inparam);
            message.setMsgResourceKey(sch.getMessage());

            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTItemMasterInquirySCHParams.CONSIGNOR_CODE));
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
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_ItemCode1.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemCode1);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchToItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_ItemCode2.getValue());
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchToItemCode_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchToItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_ItemCode2.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemCode2);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchJanCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.JAN, txt_JanCode.getValue());
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.LOT_QTY, txt_LotEnteringQty.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // DFKLOOK:ここから修正
        // 商品コードの大小を入れ替えてリストボックスに渡す
        String[] itemList = WmsFormatter.getFromTo(txt_ItemCode1.getText(), txt_ItemCode2.getText());

        inparam.set(PCTLstItemParams.ITEM_CODE, itemList[0]);
        inparam.set(PCTLstItemParams.TO_ITEM_CODE, itemList[1]);
        // DFKLOOK:ここまで修正

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchJanCode_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchJanCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_JanCode.setValue(outparam.get(PCTLstItemParams.JAN));

        // set focus.
        setFocus(txt_JanCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchCaseITF_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.ITF, txt_CaseITF.getValue());
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.LOT_QTY, txt_LotEnteringQty.getValue());
        inparam.set(PCTLstItemParams.JAN, txt_JanCode.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // DFKLOOK:ここから修正
        // 商品コードの大小を入れ替えてリストボックスに渡す
        String[] itemList = WmsFormatter.getFromTo(txt_ItemCode1.getText(), txt_ItemCode2.getText());

        inparam.set(PCTLstItemParams.ITEM_CODE, itemList[0]);
        inparam.set(PCTLstItemParams.TO_ITEM_CODE, itemList[1]);
        // DFKLOOK:ここまで修正

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchCaseITF_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchCaseITF_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_CaseITF.setValue(outparam.get(PCTLstItemParams.ITF));

        // set focus.
        setFocus(txt_CaseITF);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemListParams inparam = new PCTLstItemListParams();
        inparam.set(PCTLstItemListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemListParams.FROM_ITEM_CODE, txt_ItemCode1.getValue());
        inparam.set(PCTLstItemListParams.TO_ITEM_CODE, txt_ItemCode2.getValue());
        inparam.set(PCTLstItemListParams.LOT_QTY, txt_LotEnteringQty.getValue());
        inparam.set(PCTLstItemListParams.JAN, txt_JanCode.getValue());
        inparam.set(PCTLstItemListParams.ITF, txt_CaseITF.getValue());
        inparam.set(PCTLstItemListParams.FROM_SINGLE_WEIGHT, txt_FromUnitWeight.getValue());
        inparam.set(PCTLstItemListParams.TO_SINGLE_WEIGHT, txt_ToUnitWeight.getValue());
        inparam.set(PCTLstItemListParams.ITEM_PICTURE_REGIST, _pdm_pul_ItemImageSet.getSelectedValue());
        inparam.set(PCTLstItemListParams.FROM_LOCATION_NO, txt_FromLocationNo.getValue());
        inparam.set(PCTLstItemListParams.TO_LOCATION_NO, txt_ToLocationNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
        redirect("/pcart/master/listbox/pctitemlist/PCTLstItemList.do", forwardParam, "/Progress.do");
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
        txt_ConsignorCode.validate(this, true);
        txt_ItemCode1.validate(this, false);
        txt_ItemCode2.validate(this, false);
        txt_LotEnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_FromUnitWeight.validate(this, false);
        txt_ToUnitWeight.validate(this, false);
        txt_FromLocationNo.validate(this, false);
        txt_ToLocationNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemMasterInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTItemMasterInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTItemMasterInquiryDASCHParams inparam = new PCTItemMasterInquiryDASCHParams();
            inparam.set(PCTItemMasterInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_ITEM_CODE, txt_ItemCode1.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_ITEM_CODE, txt_ItemCode2.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.LOT_QTY, txt_LotEnteringQty.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.JAN, txt_JanCode.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_SINGLE_WEIGHT, txt_FromUnitWeight.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_SINGLE_WEIGHT, txt_ToUnitWeight.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.ITEM_PICTURE_REGIST, _pdm_pul_ItemImageSet.getSelectedValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_LOCATION_NO, txt_FromLocationNo.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_LOCATION_NO, txt_ToLocationNo.getValue());

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
            exporter = factory.newPrinterExporter("PctItemMasterList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.CONSIGNOR_CODE, outparam.get(PCTItemMasterInquiryDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctItemMasterListParams.CONSIGNOR_NAME, outparam.get(PCTItemMasterInquiryDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctItemMasterListParams.SYS_DAY, outparam.get(PCTItemMasterInquiryDASCHParams.SYS_DAY));
                expparam.set(PctItemMasterListParams.SYS_TIME, outparam.get(PCTItemMasterInquiryDASCHParams.SYS_TIME));
                expparam.set(PctItemMasterListParams.ITEM_CODE, outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, outparam.get(PCTItemMasterInquiryDASCHParams.LOT_QTY));
                expparam.set(PctItemMasterListParams.JAN, outparam.get(PCTItemMasterInquiryDASCHParams.JAN));
                expparam.set(PctItemMasterListParams.ITF, outparam.get(PCTItemMasterInquiryDASCHParams.ITF));

                // DFKLOOK:ここから修正
                // そのまま渡すと小数点以下2桁になるためフォーマット
                expparam.set(
                        PctItemMasterListParams.SINGLE_WEIGHT,
                        WmsFormatter.getNumFormat(Formatter.getDouble(outparam.getString(PCTItemMasterInquiryDASCHParams.SINGLE_WEIGHT))));
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE,
                        outparam.get(PCTItemMasterInquiryDASCHParams.WEIGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY,
                        outparam.get(PCTItemMasterInquiryDASCHParams.MAX_INSPECTION_UNIT_QTY));
                expparam.set(PctItemMasterListParams.LAST_UPDATE, outparam.get(PCTItemMasterInquiryDASCHParams.LAST_UPDATE));
                expparam.set(PctItemMasterListParams.WORK_DAY, outparam.get(PCTItemMasterInquiryDASCHParams.LAST_USED_DATE));
                expparam.set(PctItemMasterListParams.ITEM_PICTURE_FLAG,
                        outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_PICTURE_REGIST));
                expparam.set(PctItemMasterListParams.MESSAGE1, outparam.get(PCTItemMasterInquiryDASCHParams.MESSAGE));

                // DFKLOOK:ここから修正
                // デフォルト棚でフォーマットを行う
                expparam.set(PctItemMasterListParams.LOCATION_NO, WmsFormatter.toDispLocation(
                        outparam.getString(PCTItemMasterInquiryDASCHParams.LOCATION_NO), WmsParam.DEFAULT_LOCATE_STYLE));
                // DFKLOOK:ここまで修正

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
        txt_ItemCode1.validate(this, false);
        txt_ItemCode2.validate(this, false);
        txt_LotEnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_FromUnitWeight.validate(this, false);
        txt_ToUnitWeight.validate(this, false);
        txt_FromLocationNo.validate(this, false);
        txt_ToLocationNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemMasterInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTItemMasterInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTItemMasterInquiryDASCHParams inparam = new PCTItemMasterInquiryDASCHParams();
            inparam.set(PCTItemMasterInquiryDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_ITEM_CODE, txt_ItemCode1.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_ITEM_CODE, txt_ItemCode2.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.LOT_QTY, txt_LotEnteringQty.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.JAN, txt_JanCode.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_SINGLE_WEIGHT, txt_FromUnitWeight.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_SINGLE_WEIGHT, txt_ToUnitWeight.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.ITEM_PICTURE_REGIST, _pdm_pul_ItemImageSet.getSelectedValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.FROM_LOCATION_NO, txt_FromLocationNo.getValue());
            inparam.set(PCTItemMasterInquiryDASCHParams.TO_LOCATION_NO, txt_ToLocationNo.getValue());

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
            exporter = factory.newExcelExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.ITEM_CODE, outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY,
                        outparam.get(PCTItemMasterInquiryDASCHParams.LOT_QTY));
                expparam.set(PctItemMasterListParams.JAN, outparam.get(PCTItemMasterInquiryDASCHParams.JAN));
                expparam.set(PctItemMasterListParams.ITF, outparam.get(PCTItemMasterInquiryDASCHParams.ITF));

                // DFKLOOK:ここから修正
                // デフォルト棚でフォーマットを行う
                expparam.set(PctItemMasterListParams.LOCATION_NO, WmsFormatter.toDispLocation(
                        outparam.getString(PCTItemMasterInquiryDASCHParams.LOCATION_NO), WmsParam.DEFAULT_LOCATE_STYLE));

                // そのまま渡すと小数点以下2桁になるためフォーマット
                expparam.set(
                        PctItemMasterListParams.SINGLE_WEIGHT,
                        WmsFormatter.getNumFormat(Formatter.getDouble(outparam.getString(PCTItemMasterInquiryDASCHParams.SINGLE_WEIGHT))));
                // DFKLOOK:ここまで修正
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE,
                        outparam.get(PCTItemMasterInquiryDASCHParams.WEIGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY,
                        outparam.get(PCTItemMasterInquiryDASCHParams.MAX_INSPECTION_UNIT_QTY));
                expparam.set(PctItemMasterListParams.LAST_UPDATE,
                        outparam.get(PCTItemMasterInquiryDASCHParams.LAST_UPDATE));
                expparam.set(PctItemMasterListParams.WORK_DAY,
                        outparam.get(PCTItemMasterInquiryDASCHParams.LAST_USED_DATE));
                expparam.set(PctItemMasterListParams.ITEM_PICTURE_FLAG,
                        outparam.get(PCTItemMasterInquiryDASCHParams.ITEM_PICTURE_REGIST));
                expparam.set(PctItemMasterListParams.MESSAGE1, outparam.get(PCTItemMasterInquiryDASCHParams.MESSAGE));
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
        txt_ItemCode1.setValue(null);
        txt_ItemCode2.setValue(null);
        txt_LotEnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_FromUnitWeight.setValue(null);
        txt_ToUnitWeight.setValue(null);
        _pdm_pul_ItemImageSet.setSelectedValue(null);
        txt_FromLocationNo.setValue(null);
        txt_ToLocationNo.setValue(null);

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
