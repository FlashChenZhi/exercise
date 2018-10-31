// $Id: XDPlanRegistBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.crossdock.display.planregist;

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
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.supp.LstSupplierParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.crossdock.display.planregist.ViewStateKeys;
import jp.co.daifuku.wms.crossdock.display.planregist.XDPlanRegist;
import jp.co.daifuku.wms.crossdock.listbox.plan.LstXDPlanMntParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class XDPlanRegistBusiness
        extends XDPlanRegist
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
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
        setFocus(txt_PlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        viewState.setObject(ViewStateKeys.MASTER, viewState.getObject(ViewStateKeys.MASTER));
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
        inparam.set(LstXDPlanMntParams.PLAN_DATE, txt_PlanDate.getValue());
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
        setFocus(txt_PlanDate);
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

        try
        {
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

            // forward.
            forward("/crossdock/planregist/XDPlanRegist2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
