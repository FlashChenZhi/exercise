// $Id: XDPlanRegistBusiness.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.crossdock.display.planregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.LineNumber;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.supp.LstSupplierParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.crossdock.listbox.plan.LstXDPlanMntParams;
import jp.co.daifuku.wms.crossdock.schedule.XDOutParameter;
import jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCH;
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCH;

/**
 * TC予定データ登録（基本情報設定）の画面処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class XDPlanRegistBusiness
        extends XDPlanRegist
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:ここから修正
    /**
     * 最大行No.
     */
    private static final int MAX_LINE_NO = 1000;

    // DFKLOOK:ここまで修正

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
    public XDPlanRegistBusiness()
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
        if (eventSource.equals("btn_SearchSupplier_Click"))
        {
            // process call.
            btn_SearchSupplier_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PSearchPlan_Click"))
        {
            // process call.
            btn_PSearchPlan_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchItem_Click"))
        {
            // process call.
            btn_SearchItem_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchSupplier_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchSupplier_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ReceivingSlipNumber_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        int lineNo = LineNumber.getNextLineNo(this.txt_ReceivingSlipNumber.getText(), 4, this, WmsParam.AUTO_LINE_NO);

        // Checks the Max Line No.
        // If the Line No. exceeds 999, then shows an error message
        //test = 998;  // for debug
        if (!isLineNoOver(lineNo))
        {
            this.txt_LineNo.setInt(lineNo);
        }

        if (WmsParam.AUTO_LINE_NO)
        {
            setFocus(txt_ItemCode);
        }
        else
        {
            setFocus(txt_LineNo);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchPlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchPlan_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItem_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Next_Click_Process();
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
        setFocus(txt_PlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK: DAC added from here
        Connection conn = null;
        XDPlanRegistSCH sch = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            // Need to check master package existece
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDPlanRegistSCH(conn, this.getClass(), locale, ui);
            XDOutParameter outparam = (XDOutParameter)sch.initFind(new Parameter());
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.isMasterFlag());
            if (this.viewState.getBoolean(ViewStateKeys.MASTER))
            {
                txt_SupplierName.setReadOnly(true);
                txt_ItemName.setReadOnly(true);
                txt_CasePack.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);
            }

            if (WmsParam.AUTO_LINE_NO)
            {
                this.txt_LineNo.setReadOnly(true);
            }
            else
            {
                this.txt_LineNo.setReadOnly(false);
            }

            // Receiving Plan Date
            if (StringUtil.isBlank(viewState.getDate(ViewStateKeys.PLAN_DATE)))
            {
                this.txt_PlanDate.setValue(null);
            }
            else
            {
                this.txt_PlanDate.setValue(viewState.getDate((ViewStateKeys.PLAN_DATE)));
            }
            // Batch No.
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.BATCH_NO)))
            {
                this.txt_BatchNo.setValue(null);
            }
            else
            {
                this.txt_BatchNo.setValue(viewState.getString((ViewStateKeys.BATCH_NO)));
            }
            // Supplier code
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.SUPPLIER_CODE)))
            {
                this.txt_SupplierCode.setValue(null);
            }
            else
            {
                this.txt_SupplierCode.setValue(viewState.getString(ViewStateKeys.SUPPLIER_CODE));
            }
            // Supplier Name
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.SUPPLIER_NAME)))
            {
                this.txt_SupplierName.setValue(null);
            }
            else
            {
                this.txt_SupplierName.setValue(viewState.getString(ViewStateKeys.SUPPLIER_NAME));
            }
            // Receiving Slip No.
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.RECEIVING_SLIP_NUMBER)))
            {
                this.txt_ReceivingSlipNumber.setValue(null);
            }
            else
            {
                this.txt_ReceivingSlipNumber.setValue(viewState.getString(ViewStateKeys.RECEIVING_SLIP_NUMBER));
            }
            // Receiving Slip Line No.
            BigDecimal bd = (BigDecimal)viewState.getBigDecimal(ViewStateKeys.LINE_NO);
            if (bd == null || StringUtil.isBlank(bd.toString()))
            {
                this.txt_LineNo.setValue(null);
            }
            else
            {
                this.txt_LineNo.setValue(viewState.getBigDecimal(ViewStateKeys.LINE_NO));
            }
            // Item Code
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.ITEM_CODE)))
            {
                this.txt_ItemCode.setValue(null);
            }
            else
            {
                this.txt_ItemCode.setValue(viewState.getString(ViewStateKeys.ITEM_CODE));
            }
            // Item Name
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.ITEM_NAME)))
            {
                this.txt_ItemName.setValue(null);
            }
            else
            {
                this.txt_ItemName.setValue(viewState.getString(ViewStateKeys.ITEM_NAME));
            }
            // Case Pack
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.CASE_PACK)))
            {
                this.txt_CasePack.setValue(null);
            }
            else
            {
                this.txt_CasePack.setInt(viewState.getInt(ViewStateKeys.CASE_PACK));
            }
            // UPC Code
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.JAN_CODE)))
            {
                this.txt_JanCode.setValue(null);
            }
            else
            {
                this.txt_JanCode.setValue(viewState.getString(ViewStateKeys.JAN_CODE));
            }
            // Case ITF
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.CASE_ITF)))
            {
                this.txt_CaseITF.setValue(null);
            }
            else
            {
                this.txt_CaseITF.setValue(viewState.getString(ViewStateKeys.CASE_ITF));
            }
            // Lot No.
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.LOT_NO)))
            {
                this.txt_LotNo.setValue(null);
            }
            else
            {
                this.txt_LotNo.setValue(viewState.getString(ViewStateKeys.LOT_NO));
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
        // DFKLOOK:DAC added to here
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchSupplier_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstSupplierParams inparam = new LstSupplierParams();
        inparam.set(LstSupplierParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
        inparam.set(LstSupplierParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchSupplier_Click");
        redirect("/base/listbox/supp/LstSupplier.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchSupplier_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstSupplierParams outparam = new LstSupplierParams(dialogParams);
        txt_SupplierCode.setValue(outparam.get(LstSupplierParams.SUPPLIER_CODE));
        txt_SupplierName.setValue(outparam.get(LstSupplierParams.SUPPLIER_NAME));

        // set focus.
        setFocus(txt_SupplierCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstXDPlanMntParams inparam = new LstXDPlanMntParams();
        // DFKLOOK: modifed from here
        inparam.set(LstXDPlanMntParams.PLAN_DATE, WmsFormatter.toParamDate(txt_PlanDate.getDate()));
        // DFKLOOK: modifed to here
        inparam.set(LstXDPlanMntParams.BATCH, txt_BatchNo.getValue());
        inparam.set(LstXDPlanMntParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
        inparam.set(LstXDPlanMntParams.SUPPLIER_NAME, txt_SupplierName.getValue());
        inparam.set(LstXDPlanMntParams.RECEIVING_SLIP_NUMBER, txt_ReceivingSlipNumber.getValue());
        inparam.set(LstXDPlanMntParams.LINE_NO, txt_LineNo.getValue());
        inparam.set(LstXDPlanMntParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstXDPlanMntParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstXDPlanMntParams.CASE_PACK, txt_CasePack.getValue());
        inparam.set(LstXDPlanMntParams.JAN_CODE, txt_JanCode.getValue());
        inparam.set(LstXDPlanMntParams.CASE_ITF, txt_CaseITF.getValue());
        inparam.set(LstXDPlanMntParams.LOT, txt_LotNo.getValue());
        inparam.set(LstXDPlanMntParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchPlan_Click");
        redirect("/crossdock/listbox/plan/LstXDPlanMnt.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchPlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstXDPlanMntParams outparam = new LstXDPlanMntParams(dialogParams);
        txt_PlanDate.setValue(outparam.get(LstXDPlanMntParams.PLAN_DATE));
        txt_BatchNo.setValue(outparam.get(LstXDPlanMntParams.BATCH));
        txt_SupplierCode.setValue(outparam.get(LstXDPlanMntParams.SUPPLIER_CODE));
        txt_SupplierName.setValue(outparam.get(LstXDPlanMntParams.SUPPLIER_NAME));
        txt_ReceivingSlipNumber.setValue(outparam.get(LstXDPlanMntParams.RECEIVING_TICKET));
        txt_LineNo.setValue(outparam.get(LstXDPlanMntParams.RECEIVING_TICKET_LINE));
        txt_ItemCode.setValue(outparam.get(LstXDPlanMntParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstXDPlanMntParams.ITEM_NAME));
        txt_CasePack.setValue(outparam.get(LstXDPlanMntParams.CASE_PACK));
        txt_JanCode.setValue(outparam.get(LstXDPlanMntParams.JAN_CODE));
        txt_CaseITF.setValue(outparam.get(LstXDPlanMntParams.CASE_ITF));
        txt_LotNo.setValue(outparam.get(LstXDPlanMntParams.LOT));

        // set focus.
        setFocus(txt_ReceivingSlipNumber);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItem_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_CasePack.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_PlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_SupplierCode.validate(this, true);
        txt_SupplierName.validate(this, false);
        txt_ReceivingSlipNumber.validate(this, true);
        txt_LineNo.validate(this, false);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_CasePack.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);

        // DFKLOOK: DAC from here
        if (this.viewState.getBoolean(ViewStateKeys.MASTER))
        {
            // check supplier and item

            // show item infomation
            boolean check = existSupplierCode(txt_SupplierCode.getText());
            if (!check)
            {
                return;
            }
            check = existItemCode(txt_ItemCode.getText());
            if (!check)
            {
                return;
            }
        }
        // DFKLOOK: DAC to here

        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.PLAN_DATE, txt_PlanDate.getValue());
        viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
        viewState.setObject(ViewStateKeys.SUPPLIER_CODE, txt_SupplierCode.getValue());
        viewState.setObject(ViewStateKeys.SUPPLIER_NAME, txt_SupplierName.getValue());
        viewState.setObject(ViewStateKeys.RECEIVING_SLIP_NUMBER, txt_ReceivingSlipNumber.getValue());
        viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
        viewState.setObject(ViewStateKeys.ITEM_NAME, txt_ItemName.getValue());
        viewState.setObject(ViewStateKeys.CASE_PACK, txt_CasePack.getValue());
        viewState.setObject(ViewStateKeys.JAN_CODE, txt_JanCode.getValue());
        viewState.setObject(ViewStateKeys.CASE_ITF, txt_CaseITF.getValue());
        viewState.setObject(ViewStateKeys.LOT_NO, txt_LotNo.getValue());

        // DFKLOOK: DAC from here   
        // For Line No.
        if (WmsParam.AUTO_LINE_NO)
        {
            int lineNo = LineNumber.getNextLineNo(txt_ReceivingSlipNumber.getText(), 4, this, WmsParam.AUTO_LINE_NO);
            txt_LineNo.setInt(lineNo);
        }
        else
        {
            txt_LineNo.setInt(txt_LineNo.getInt());
        }
        viewState.setObject(ViewStateKeys.LINE_NO, txt_LineNo.getValue());
        // DFKLOOK: DAC to here

        // forward.
        forward("/crossdock/planregist/XDPlanRegist2.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_PlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_SupplierCode.setValue(null);
        txt_SupplierName.setValue(null);
        txt_ReceivingSlipNumber.setValue(null);
        txt_LineNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_CasePack.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);

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

    // DFKLOOK: DAC added from here
    /**
     * Checks if the given Supplier code exists or not in Master
     */
    private boolean existSupplierCode(String code)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        ReceivingPlanRegistSCH sch = null;

        Connection conn = null;
        Locale locale = httpRequest.getLocale();

        boolean check = true;

        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingPlanRegistSCH(conn, this.getClass(), locale, ui);
            ReceivingInParameter param = new ReceivingInParameter(new WmsUserInfo(ui));
            ReceivingOutParameter outparam = (ReceivingOutParameter)sch.initFind(new Parameter());
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.isMasterFlag());
            if (this.viewState.getBoolean(ViewStateKeys.MASTER))
            {
                param.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                param.setSupplierCode(code);

                check = sch.existSupplierCode(param);
                if (!check)
                {
                    message.setMsgResourceKey("6023137");
                }
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
        return check;
    }

    /**
     * Check if the given Item code exists or not in Master
     * @param code
     * @return
     */
    private boolean existItemCode(String code)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDPlanRegistSCH sch = null;

        Connection conn = null;
        Locale locale = httpRequest.getLocale();

        boolean check = true;

        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDPlanRegistSCH(conn, this.getClass(), locale, ui);
            check = sch.existItemCode(code);
            if (!check)
            {
                message.setMsgResourceKey("6023021");
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
        return check;
    }

    /**
     * Checks if the Line No is over the max or not.
     * If it exceeds the Max, then shows a message.
     */
    private boolean isLineNoOver(int lineNo)
            throws Exception
    {
        if (lineNo >= MAX_LINE_NO)
        {
            this.txt_LineNo.setReadOnly(false);
            message.setMsgResourceKey("6023008"); // 6407008 -> 6023008
            return true;
        }
        return false;
    }

    // DFKLOOK: DAC added to here

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
