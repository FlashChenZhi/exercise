// $Id: CustomerMasterRegistBusiness.java 7365 2010-03-05 00:43:55Z okayama $
package jp.co.daifuku.wms.master.display.custregist;

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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.master.display.custregist.CustomerMasterRegist;
import jp.co.daifuku.wms.master.schedule.CustomerMasterRegistSCH;
import jp.co.daifuku.wms.master.schedule.CustomerMasterRegistSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7365 $, $Date:: 2010-03-05 09:43:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class CustomerMasterRegistBusiness
        extends CustomerMasterRegist
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
    public CustomerMasterRegistBusiness()
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
        if (eventSource.equals("btn_Search_Click"))
        {
            // process call.
            btn_Search_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Search_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Submit_Click_Process();
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
        setFocus(txt_CustomerCode);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_Route.setValue(null);
        txt_ZipCode.setValue(null);
        txt_AdministrativeDivisions.setValue(null);
        txt_Address.setValue(null);
        txt_BuildingName.setValue(null);
        txt_TelephoneNumber.setValue(null);
        txt_Contact1.setValue(null);
        txt_Contact2.setValue(null);
        txt_SortPlace.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstCustomerParams.SORT_PLACE_DISP, LstCustomerBusiness.LST_SORT_PLACE_DISP);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Search_Click");
        redirect("/base/listbox/cust/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstCustomerParams.CUSTOMER_NAME));
        txt_Route.setValue(outparam.get(LstCustomerParams.ROUTE));
        txt_ZipCode.setValue(outparam.get(LstCustomerParams.POSTAL_CODE));
        txt_AdministrativeDivisions.setValue(outparam.get(LstCustomerParams.PREFECTURE));
        txt_Address.setValue(outparam.get(LstCustomerParams.ADDRESS1));
        txt_BuildingName.setValue(outparam.get(LstCustomerParams.ADDRESS2));
        txt_TelephoneNumber.setValue(outparam.get(LstCustomerParams.TELEPHONE));
        txt_Contact1.setValue(outparam.get(LstCustomerParams.CONTACT1));
        txt_Contact2.setValue(outparam.get(LstCustomerParams.CONTACT2));
        txt_SortPlace.setValue(outparam.get(LstCustomerParams.SORT_PLACE));

        // set focus.
        setFocus(txt_CustomerCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Submit_Click_Process()
            throws Exception
    {
        // input validation.
        txt_CustomerCode.validate(this, true);
        txt_CustomerName.validate(this, false);
        txt_Route.validate(this, false);
        txt_ZipCode.validate(this, false);
        txt_AdministrativeDivisions.validate(this, false);
        txt_Address.validate(this, false);
        txt_BuildingName.validate(this, false);
        txt_TelephoneNumber.validate(this, false);
        txt_Contact1.validate(this, false);
        txt_Contact2.validate(this, false);
        txt_SortPlace.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        CustomerMasterRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new CustomerMasterRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            CustomerMasterRegistSCHParams inparam = new CustomerMasterRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(CustomerMasterRegistSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(CustomerMasterRegistSCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(CustomerMasterRegistSCHParams.ROUTE, txt_Route.getValue());
            inparam.set(CustomerMasterRegistSCHParams.POSTAL_CODE, txt_ZipCode.getValue());
            inparam.set(CustomerMasterRegistSCHParams.PREFECTURE, txt_AdministrativeDivisions.getValue());
            inparam.set(CustomerMasterRegistSCHParams.ADDRESS1, txt_Address.getValue());
            inparam.set(CustomerMasterRegistSCHParams.ADDRESS2, txt_BuildingName.getValue());
            inparam.set(CustomerMasterRegistSCHParams.TELEPHONE, txt_TelephoneNumber.getValue());
            inparam.set(CustomerMasterRegistSCHParams.CONTACT1, txt_Contact1.getValue());
            inparam.set(CustomerMasterRegistSCHParams.CONTACT2, txt_Contact2.getValue());
            inparam.set(CustomerMasterRegistSCHParams.SORT_PLACE, txt_SortPlace.getValue());
            inparam.set(CustomerMasterRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_CustomerCode.getStringValue(), "");
            part11List.add(txt_CustomerName.getStringValue(), "");
            part11List.add(txt_Route.getStringValue(), "");
            part11List.add(txt_ZipCode.getStringValue(), "");
            part11List.add(txt_AdministrativeDivisions.getStringValue(), "");
            part11List.add(txt_Address.getStringValue(), "");
            part11List.add(txt_BuildingName.getStringValue(), "");
            part11List.add(txt_TelephoneNumber.getStringValue(), "");
            part11List.add(txt_Contact1.getStringValue(), "");
            part11List.add(txt_Contact2.getStringValue(), "");
            part11List.add(txt_SortPlace.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_Route.setValue(null);
        txt_ZipCode.setValue(null);
        txt_AdministrativeDivisions.setValue(null);
        txt_Address.setValue(null);
        txt_BuildingName.setValue(null);
        txt_TelephoneNumber.setValue(null);
        txt_Contact1.setValue(null);
        txt_Contact2.setValue(null);
        txt_SortPlace.setValue(null);
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
