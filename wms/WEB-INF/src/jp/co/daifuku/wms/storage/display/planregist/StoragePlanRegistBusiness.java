// $Id: StoragePlanRegistBusiness.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.storage.display.planregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.storage.listbox.plan.LstStoragePlanMntParams;


/**
 * 入庫予定データ登録（基本情報設定）の画面処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class StoragePlanRegistBusiness
        extends StoragePlanRegist
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
    public StoragePlanRegistBusiness()
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
        if (eventSource.equals("btn_PSearchStoragePlan_Click"))
        {
            // process call.
            btn_PSearchStoragePlan_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchStoragePlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchStoragePlan_Click_Process();
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
     * Menu button click event handling.
     * @param e ActionEvent, event information
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // Delete Connection From Session
        SessionUtil.deleteSession(getSession());
        // Forward to Submenu screen
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
        setFocus(txt_StoragePlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {

        //DFKLOOK: DAC modified from here
        // Storage plan date
        if (StringUtil.isBlank(viewState.getDate(ViewStateKeys.STORAGE_PLAN_DATE)))
        {
            this.txt_StoragePlanDate.setValue(null);
        }
        else
        {
            this.txt_StoragePlanDate.setValue(viewState.getDate((ViewStateKeys.STORAGE_PLAN_DATE)));
        }
        // Slip No.
        if (StringUtil.isBlank(viewState.getString(ViewStateKeys.SLIP_NUMBER)))
        {
            this.txt_SlipNumber.setValue(null);
        }
        else
        {
            this.txt_SlipNumber.setValue(viewState.getString(ViewStateKeys.SLIP_NUMBER));
        }
        // DFKLOOK: DAC modified to here
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchStoragePlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstStoragePlanMntParams inparam = new LstStoragePlanMntParams();
        // DFKLOOK: modified from here
        inparam.set(LstStoragePlanMntParams.PLAN_DATE, WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
        inparam.set(LstStoragePlanMntParams.STORAGE_PLAN_DATE, WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
        // DFKLOOK: modified to here
        inparam.set(LstStoragePlanMntParams.SLIP_NUMBER, txt_SlipNumber.getValue());
        inparam.set(LstStoragePlanMntParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchStoragePlan_Click");
        redirect("/storage/listbox/plan/LstStoragePlanMnt.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchStoragePlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstStoragePlanMntParams outparam = new LstStoragePlanMntParams(dialogParams);

        ////////////////////////////////////////////////////////////////////////
        // DFKLOOK: DAC modified from here
        ////////////////////////////////////////////////////////////////////////
        txt_StoragePlanDate.setValue(outparam.get(LstStoragePlanMntParams.PLAN_DATE));
        txt_SlipNumber.setValue(outparam.get(LstStoragePlanMntParams.TICKET));
        ////////////////////////////////////////////////////////////////////////
        // DFKLOOK: DAC modified to here
        ////////////////////////////////////////////////////////////////////////

        // set focus.
        setFocus(txt_StoragePlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, true);
        txt_SlipNumber.validate(this, true);

        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.STORAGE_PLAN_DATE, txt_StoragePlanDate.getValue());
        viewState.setObject(ViewStateKeys.SLIP_NUMBER, txt_SlipNumber.getValue());

        // forward.
        forward("/storage/planregist/StoragePlanRegist2.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_SlipNumber.setValue(null);

    }

    /**
     * Displays Screen title.
     *
     * @throws Exception
     */
    private void setTitle()
            throws Exception
    {
        // get Menu parameters from httpRequest
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // update Title information to view state to display when screen is refreshed
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // If there is no title , get the title key from ResourceKey (page.xml)
            // this is used for POPUP tilte keys
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // Set screen title
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
