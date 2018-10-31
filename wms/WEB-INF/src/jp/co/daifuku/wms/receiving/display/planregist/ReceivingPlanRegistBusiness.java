// $Id: ReceivingPlanRegistBusiness.java 7164 2010-02-19 10:16:59Z fukuwa $
package jp.co.daifuku.wms.receiving.display.planregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.listbox.supp.LstSupplierParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.receiving.listbox.plan.LstReceivingPlanMntParams;
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCH;


/**
 * 入荷予定データ登録（基本情報設定）の画面処理を行います。
 *
 * @version $Revision: 7164 $, $Date: 2010-02-19 19:16:59 +0900 (金, 19 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
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
        if (eventSource.equals("btn_PSearchReceivingPlan_Click"))
        {
            // process call.
            btn_PSearchReceivingPlan_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchSupplier_Click"))
        {
            // process call.
            btn_SearchSupplier_Click_DlgBack(dialogParams);
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
        setFocus(txt_ReceivingPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK: DAC modification from here
        Connection conn = null;
        ReceivingPlanRegistSCH sch = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            // Need to check master package existence
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingPlanRegistSCH(conn, this.getClass(), locale, ui);
            ReceivingOutParameter outparam = (ReceivingOutParameter)sch.initFind(new Parameter());
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.isMasterFlag());
            if (viewState.getBoolean(ViewStateKeys.MASTER))
            {
                this.txt_SupplierName.setReadOnly(true);
            }

            // Receiving Plan Date
            if (StringUtil.isBlank(viewState.getDate(ViewStateKeys.RECEIVING_PLAN_DATE)))
            {
                this.txt_ReceivingPlanDate.setValue(null);
            }
            else
            {
                this.txt_ReceivingPlanDate.setValue(viewState.getDate((ViewStateKeys.RECEIVING_PLAN_DATE)));
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
            // Slip No.
            if (StringUtil.isBlank(viewState.getString(ViewStateKeys.SLIP_NUMBER)))
            {
                this.txt_SlipNumber.setValue(null);
            }
            else
            {
                this.txt_SlipNumber.setValue(viewState.getString(ViewStateKeys.SLIP_NUMBER));
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
        // DFKLOOK: DAC modification to here
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
    private void btn_PSearchReceivingPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstReceivingPlanMntParams inparam = new LstReceivingPlanMntParams();
        // DFKLOOK: modified from here
        inparam.set(LstReceivingPlanMntParams.PLAN_DATE, WmsFormatter.toParamDate(txt_ReceivingPlanDate.getDate()));
        // DFKLOOK: modified to here
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
        // DFKLOOK: DAC modified from here
        txt_ReceivingPlanDate.setValue(outparam.get(LstReceivingPlanMntParams.PLAN_DATE));
        // DFKLOOK: DAC modified from here
        txt_SupplierCode.setValue(outparam.get(LstReceivingPlanMntParams.SUPPLIER_CODE));
        txt_SupplierName.setValue(outparam.get(LstReceivingPlanMntParams.SUPPLIER_NAME));
        // DFKLOOK: DAC modified from here
        txt_SlipNumber.setValue(outparam.get(LstReceivingPlanMntParams.TICKET));
        // DFKLOOK: DAC modified to here

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

        // DFKLOOK: DAC added from here
        if (!existSupplierCode(txt_SupplierCode.getText()))
        {
            return;
        }
        // DFKLOOK: DAC added to here

        // DFKLOOK: DAC modified from here
        txt_SupplierName.validate(this, false);
        // DFKLOOK: DAC modified to here
        txt_SlipNumber.validate(this, true);

        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.RECEIVING_PLAN_DATE, txt_ReceivingPlanDate.getValue());
        viewState.setObject(ViewStateKeys.SUPPLIER_CODE, txt_SupplierCode.getValue());
        viewState.setObject(ViewStateKeys.SUPPLIER_NAME, txt_SupplierName.getValue());
        viewState.setObject(ViewStateKeys.SLIP_NUMBER, txt_SlipNumber.getValue());

        // forward.
        forward("/receiving/planregist/ReceivingPlanRegist2.do");
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

    // DFKLOOK: DAC added from here
    /**
     * @param code 仕入先コード
     * @return boolean 存在ならtrue、非存在ならfalse
     * @throws Exception
     */
    private boolean existSupplierCode(String code)
            throws Exception
    {

        Connection conn = null;
        ReceivingPlanRegistSCH sch = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
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
}
//end of class
