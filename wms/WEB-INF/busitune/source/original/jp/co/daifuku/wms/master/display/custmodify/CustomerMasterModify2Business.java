// $Id: CustomerMasterModify2Business.java 7575 2010-03-15 09:46:22Z okayama $
package jp.co.daifuku.wms.master.display.custmodify;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.master.display.custmodify.CustomerMasterModify2;
import jp.co.daifuku.wms.master.display.custmodify.ViewStateKeys;
import jp.co.daifuku.wms.master.schedule.CustomerMasterModifySCH;
import jp.co.daifuku.wms.master.schedule.CustomerMasterModifySCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7575 $, $Date:: 2010-03-15 18:46:22 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class CustomerMasterModify2Business
        extends CustomerMasterModify2
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
    public CustomerMasterModify2Business()
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
        if (eventSource.equals("btn_Modify_Click"))
        {
            // process call.
            btn_Modify_Click_Process(false);
        }
        else if (eventSource.equals("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Modify_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process(true);
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
        setFocus(txt_CustomerName);

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
        CustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new CustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            CustomerMasterModifySCHParams inparam = new CustomerMasterModifySCHParams();
            inparam.set(CustomerMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(CustomerMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_CustomerName.setValue(outparam.get(CustomerMasterModifySCHParams.CUSTOMER_NAME));
                txt_Route.setValue(outparam.get(CustomerMasterModifySCHParams.ROUTE));
                txt_ZipCode.setValue(outparam.get(CustomerMasterModifySCHParams.POSTAL_CODE));
                txt_AdministrativeDivisions.setValue(outparam.get(CustomerMasterModifySCHParams.PREFECTURE));
                txt_Address.setValue(outparam.get(CustomerMasterModifySCHParams.ADDRESS1));
                txt_BuildingName.setValue(outparam.get(CustomerMasterModifySCHParams.ADDRESS2));
                txt_TelephoneNumber.setValue(outparam.get(CustomerMasterModifySCHParams.TELEPHONE));
                txt_Contact1.setValue(outparam.get(CustomerMasterModifySCHParams.CONTACT1));
                txt_Contact2.setValue(outparam.get(CustomerMasterModifySCHParams.CONTACT2));
                txt_SortPlace.setValue(outparam.get(CustomerMasterModifySCHParams.SORT_PLACE));
                txt_LastUpdateDate.setValue(outparam.get(CustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                txt_LastUsedDate.setValue(outparam.get(CustomerMasterModifySCHParams.LAST_USED_DATE));
                lbl_JavaSetCustomerCode.setValue(outparam.get(CustomerMasterModifySCHParams.CUSTOMER_CODE));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(CustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_CUSTOMER_CODE, outparam.get(CustomerMasterModifySCHParams.JAVA_SET_CUSTOMER_CODE));
                viewState.setObject(ViewStateKeys.VS_CUSTOMER_NAME, outparam.get(CustomerMasterModifySCHParams.CUSTOMER_NAME));
                viewState.setObject(ViewStateKeys.VS_ROUTE, outparam.get(CustomerMasterModifySCHParams.ROUTE));
                viewState.setObject(ViewStateKeys.VS_ZIP_CODE, outparam.get(CustomerMasterModifySCHParams.POSTAL_CODE));
                viewState.setObject(ViewStateKeys.VS_ADMINISTRATIVE_DIVISIONS, outparam.get(CustomerMasterModifySCHParams.PREFECTURE));
                viewState.setObject(ViewStateKeys.VS_ADDRESS, outparam.get(CustomerMasterModifySCHParams.ADDRESS1));
                viewState.setObject(ViewStateKeys.VS_BUILDING_NAME, outparam.get(CustomerMasterModifySCHParams.ADDRESS2));
                viewState.setObject(ViewStateKeys.VS_TELEPHONE_NUMBER, outparam.get(CustomerMasterModifySCHParams.TELEPHONE));
                viewState.setObject(ViewStateKeys.VS_CONTACT1, outparam.get(CustomerMasterModifySCHParams.CONTACT1));
                viewState.setObject(ViewStateKeys.VS_CONTACT2, outparam.get(CustomerMasterModifySCHParams.CONTACT2));
                viewState.setObject(ViewStateKeys.VS_SORT_PLACE, outparam.get(CustomerMasterModifySCHParams.SORT_PLACE));
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
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.CUSTOMER_CODE, lbl_JavaSetCustomerCode.getValue());

            // forward.
            forward("/master/custmodify/CustomerMasterModify.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Modify_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
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
        CustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new CustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            CustomerMasterModifySCHParams inparam = new CustomerMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(CustomerMasterModifySCHParams.ROUTE, txt_Route.getValue());
            inparam.set(CustomerMasterModifySCHParams.POSTAL_CODE, txt_ZipCode.getValue());
            inparam.set(CustomerMasterModifySCHParams.PREFECTURE, txt_AdministrativeDivisions.getValue());
            inparam.set(CustomerMasterModifySCHParams.ADDRESS1, txt_Address.getValue());
            inparam.set(CustomerMasterModifySCHParams.ADDRESS2, txt_BuildingName.getValue());
            inparam.set(CustomerMasterModifySCHParams.TELEPHONE, txt_TelephoneNumber.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONTACT1, txt_Contact1.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONTACT2, txt_Contact2.getValue());
            inparam.set(CustomerMasterModifySCHParams.SORT_PLACE, txt_SortPlace.getValue());
            inparam.set(CustomerMasterModifySCHParams.LAST_USED_DATE, txt_LastUsedDate.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(CustomerMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(CustomerMasterModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            if (confirm && !sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
                    return;
                }
            }

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
            part11List.add(lbl_JavaSetCustomerCode.getStringValue(), "");
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
            part11List.add(txt_LastUpdateDate.getStringValue(), "");
            part11List.add(txt_LastUsedDate.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CUSTOMER_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ROUTE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ZIP_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ADMINISTRATIVE_DIVISIONS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ADDRESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_BUILDING_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TELEPHONE_NUMBER), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CONTACT1), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CONTACT2), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SORT_PLACE), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new CustomerMasterModifySCHParams();
            inparam.set(CustomerMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(CustomerMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(CustomerMasterModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_CustomerName.setValue(outparam.get(CustomerMasterModifySCHParams.CUSTOMER_NAME));
                txt_Route.setValue(outparam.get(CustomerMasterModifySCHParams.ROUTE));
                txt_ZipCode.setValue(outparam.get(CustomerMasterModifySCHParams.POSTAL_CODE));
                txt_AdministrativeDivisions.setValue(outparam.get(CustomerMasterModifySCHParams.PREFECTURE));
                txt_Address.setValue(outparam.get(CustomerMasterModifySCHParams.ADDRESS1));
                txt_BuildingName.setValue(outparam.get(CustomerMasterModifySCHParams.ADDRESS2));
                txt_TelephoneNumber.setValue(outparam.get(CustomerMasterModifySCHParams.TELEPHONE));
                txt_Contact1.setValue(outparam.get(CustomerMasterModifySCHParams.CONTACT1));
                txt_Contact2.setValue(outparam.get(CustomerMasterModifySCHParams.CONTACT2));
                txt_SortPlace.setValue(outparam.get(CustomerMasterModifySCHParams.SORT_PLACE));
                txt_LastUpdateDate.setValue(outparam.get(CustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                txt_LastUsedDate.setValue(outparam.get(CustomerMasterModifySCHParams.LAST_USED_DATE));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(CustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_CUSTOMER_NAME, outparam.get(CustomerMasterModifySCHParams.CUSTOMER_NAME));
                viewState.setObject(ViewStateKeys.VS_ROUTE, outparam.get(CustomerMasterModifySCHParams.ROUTE));
                viewState.setObject(ViewStateKeys.VS_ZIP_CODE, outparam.get(CustomerMasterModifySCHParams.POSTAL_CODE));
                viewState.setObject(ViewStateKeys.VS_ADMINISTRATIVE_DIVISIONS, outparam.get(CustomerMasterModifySCHParams.PREFECTURE));
                viewState.setObject(ViewStateKeys.VS_ADDRESS, outparam.get(CustomerMasterModifySCHParams.ADDRESS1));
                viewState.setObject(ViewStateKeys.VS_BUILDING_NAME, outparam.get(CustomerMasterModifySCHParams.ADDRESS2));
                viewState.setObject(ViewStateKeys.VS_TELEPHONE_NUMBER, outparam.get(CustomerMasterModifySCHParams.TELEPHONE));
                viewState.setObject(ViewStateKeys.VS_CONTACT1, outparam.get(CustomerMasterModifySCHParams.CONTACT1));
                viewState.setObject(ViewStateKeys.VS_CONTACT2, outparam.get(CustomerMasterModifySCHParams.CONTACT2));
                viewState.setObject(ViewStateKeys.VS_SORT_PLACE, outparam.get(CustomerMasterModifySCHParams.SORT_PLACE));
            }

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
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        CustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new CustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            CustomerMasterModifySCHParams inparam = new CustomerMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(CustomerMasterModifySCHParams.ROUTE, txt_Route.getValue());
            inparam.set(CustomerMasterModifySCHParams.POSTAL_CODE, txt_ZipCode.getValue());
            inparam.set(CustomerMasterModifySCHParams.PREFECTURE, txt_AdministrativeDivisions.getValue());
            inparam.set(CustomerMasterModifySCHParams.ADDRESS1, txt_Address.getValue());
            inparam.set(CustomerMasterModifySCHParams.ADDRESS2, txt_BuildingName.getValue());
            inparam.set(CustomerMasterModifySCHParams.TELEPHONE, txt_TelephoneNumber.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONTACT1, txt_Contact1.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONTACT2, txt_Contact2.getValue());
            inparam.set(CustomerMasterModifySCHParams.SORT_PLACE, txt_SortPlace.getValue());
            inparam.set(CustomerMasterModifySCHParams.LAST_USED_DATE, txt_LastUsedDate.getValue());
            inparam.set(CustomerMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(CustomerMasterModifySCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(CustomerMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(CustomerMasterModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);

            // SCH call.
            if (confirm && !sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Delete_Click");
                    return;
                }
            }

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
            part11List.add(lbl_JavaSetCustomerCode.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CUSTOMER_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ROUTE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ZIP_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ADMINISTRATIVE_DIVISIONS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ADDRESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_BUILDING_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TELEPHONE_NUMBER), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CONTACT1), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CONTACT2), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SORT_PLACE), "");
            part11List.add(txt_LastUpdateDate.getStringValue(), "");
            part11List.add(txt_LastUsedDate.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_CustomerName.setValue(null);
            txt_Route.setValue(null);
            txt_ZipCode.setValue(null);
            txt_Address.setValue(null);
            txt_AdministrativeDivisions.setValue(null);
            txt_BuildingName.setValue(null);
            txt_TelephoneNumber.setValue(null);
            txt_Contact1.setValue(null);
            txt_Contact2.setValue(null);
            txt_SortPlace.setValue(null);
            txt_LastUpdateDate.setValue(null);
            txt_LastUsedDate.setValue(null);
            btn_Modify.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Clear.setEnabled(false);

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
