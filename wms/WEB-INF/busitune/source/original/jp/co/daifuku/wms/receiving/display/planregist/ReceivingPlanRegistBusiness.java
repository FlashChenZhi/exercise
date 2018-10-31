// $Id: ReceivingPlanRegistBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.receiving.display.planregist;

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
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.receiving.display.planregist.ReceivingPlanRegist;
import jp.co.daifuku.wms.receiving.display.planregist.ViewStateKeys;
import jp.co.daifuku.wms.receiving.listbox.plan.LstReceivingPlanMntParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class ReceivingPlanRegistBusiness
        extends ReceivingPlanRegist
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
    public ReceivingPlanRegistBusiness()
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
        if (eventSource.equals("btn_PSearchReceivingPlan_Click"))
        {
            // process call.
            btn_PSearchReceivingPlan_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchReceivingPlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchReceivingPlan_Click_Process();
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
        setFocus(txt_ReceivingPlanDate);
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

        // clear.
        txt_ReceivingPlanDate.setValue(null);
        txt_SupplierCode.setValue(null);
        txt_SupplierName.setValue(null);
        txt_SlipNumber.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchReceivingPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstReceivingPlanMntParams inparam = new LstReceivingPlanMntParams();
        inparam.set(LstReceivingPlanMntParams.RECEIVING_PLAN_DATE, txt_ReceivingPlanDate.getValue());
        inparam.set(LstReceivingPlanMntParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
        inparam.set(LstReceivingPlanMntParams.SUPPLIER_NAME, txt_SupplierName.getValue());
        inparam.set(LstReceivingPlanMntParams.SLIP_NUMBER, txt_SlipNumber.getValue());
        inparam.set(LstReceivingPlanMntParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchReceivingPlan_Click");
        redirect("/receiving/listbox/plan/LstReceivingPlanMnt.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchReceivingPlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstReceivingPlanMntParams outparam = new LstReceivingPlanMntParams(dialogParams);
        txt_ReceivingPlanDate.setValue(outparam.get(LstReceivingPlanMntParams.RECEIVING_PLAN_DATE));
        txt_SupplierCode.setValue(outparam.get(LstReceivingPlanMntParams.SUPPLIER_CODE));
        txt_SupplierName.setValue(outparam.get(LstReceivingPlanMntParams.SUPPLIER_NAME));
        txt_SlipNumber.setValue(outparam.get(LstReceivingPlanMntParams.SLIP_NUMBER));

        // set focus.
        setFocus(txt_ReceivingPlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ReceivingPlanDate.validate(this, true);
        txt_SupplierCode.validate(this, true);
        txt_SupplierName.validate(this, true);
        txt_SlipNumber.validate(this, true);

        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.RECEIVING_PLAN_DATE, txt_ReceivingPlanDate.getValue());
            viewState.setObject(ViewStateKeys.SUPPLIER_CODE, txt_SupplierCode.getValue());
            viewState.setObject(ViewStateKeys.SUPPLIER_NAME, txt_SupplierName.getValue());
            viewState.setObject(ViewStateKeys.SLIP_NUMBER, txt_SlipNumber.getValue());

            // forward.
            forward("/receiving/planregist/ReceivingPlanRegist2.do");
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
        txt_ReceivingPlanDate.setValue(null);
        txt_SupplierCode.setValue(null);
        txt_SupplierName.setValue(null);
        txt_SlipNumber.setValue(null);
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
