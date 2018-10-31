// $Id: SystemStateMaintenanceBusiness.java 7423 2010-03-06 08:36:58Z shibamoto $
package jp.co.daifuku.wms.system.display.systemstate;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.wms.system.display.systemstate.SystemStateMaintenance;
import jp.co.daifuku.wms.system.display.systemstate.ViewStateKeys;
import jp.co.daifuku.wms.system.schedule.SystemStateMaintenanceSCH;
import jp.co.daifuku.wms.system.schedule.SystemStateMaintenanceSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7423 $, $Date:: 2010-03-06 17:36:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SystemStateMaintenanceBusiness
        extends SystemStateMaintenance
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
    /** RadioButtonGroupModel HostCommunication */
    private RadioButtonGroup _grp_HostCommunication;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SystemStateMaintenanceBusiness()
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
        if (eventSource.equals("btn_DailyProcess_Click"))
        {
            // process call.
            btn_DailyProcess_Click_Process(false);
        }
        else if (eventSource.equals("btn_LoadPlanData_Click"))
        {
            // process call.
            btn_LoadPlanData_Click_Process(false);
        }
        else if (eventSource.equals("btn_CreateReportData_Click"))
        {
            // process call.
            btn_CreateReportData_Click_Process(false);
        }
        else if (eventSource.equals("btn_RetrievalAllocate_Click"))
        {
            // process call.
            btn_RetrievalAllocate_Click_Process(false);
        }
        else if (eventSource.equals("btn_AllocationClear_Click"))
        {
            // process call.
            btn_AllocationClear_Click_Process(false);
        }
        else if (eventSource.equals("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_DailyProcess_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DailyProcess_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_LoadPlanData_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_LoadPlanData_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_CreateReportData_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_CreateReportData_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RetrievalAllocate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalAllocate_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllocationClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllocationClear_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize HostCommunication.
        _grp_HostCommunication = new RadioButtonGroup(new RadioButton[]{rdo_HostCommunication_Effectiv, rdo_HostCommunication_Invalidi}, locale);

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
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                txt_InAllocationClear.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
                _grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                viewState.setObject(ViewStateKeys.VS_HOST_COMMUNICATION, outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                viewState.setObject(ViewStateKeys.VS_IN_DAILY_PROCESS, outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                viewState.setObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                viewState.setObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                viewState.setObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE, outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                viewState.setObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR, outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
            }

            // clear.
            txt_InDailyProcess.setReadOnly(true);
            txt_InLoadPlanData.setReadOnly(true);
            txt_InCreateReportData.setReadOnly(true);
            txt_InRetrievalAllocate.setReadOnly(true);
            btn_DailyProcess.setEnabled(true);
            btn_LoadPlanData.setEnabled(true);
            btn_CreateReportData.setEnabled(true);
            btn_RetrievalAllocate.setEnabled(true);

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
     * @param confirm
     * @throws Exception
     */
    private void btn_DailyProcess_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS, viewState.getObject(ViewStateKeys.IN_DAILY_PROCESS));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_DailyProcess_Click");
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
            part11List.add("1", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();
            inparam.set(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS, viewState.getObject(ViewStateKeys.IN_DAILY_PROCESS));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
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
    private void btn_LoadPlanData_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA, viewState.getObject(ViewStateKeys.IN_LOAD_PLAN_DATA));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_LoadPlanData_Click");
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
            part11List.add("2", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();
            inparam.set(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA, viewState.getObject(ViewStateKeys.IN_LOAD_PLAN_DATA));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
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
    private void btn_CreateReportData_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA, viewState.getObject(ViewStateKeys.IN_CREATE_REPORT_DATA));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_CreateReportData_Click");
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
            part11List.add("3", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();
            inparam.set(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA, viewState.getObject(ViewStateKeys.IN_CREATE_REPORT_DATA));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
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
    private void btn_RetrievalAllocate_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE, viewState.getObject(ViewStateKeys.IN_RETRIEVAL_ALLOCATE));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalAllocate_Click");
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
            part11List.add("4", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();
            inparam.set(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE, viewState.getObject(ViewStateKeys.IN_RETRIEVAL_ALLOCATE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
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
    private void btn_AllocationClear_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_AllocationClear_Click");
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
            part11List.add("5", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
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
    private void btn_Set_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION, viewState.getObject(ViewStateKeys.IN_HOST_COMMUNICATION));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
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
            part11List.add("6", "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_DAILY_PROCESS), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_LOAD_PLAN_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_CREATE_REPORT_DATA), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_IN_ALLOCATION_CLEAR), "");
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add(viewState.getString(ViewStateKeys.VS_HOST_COMMUNICATION), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new SystemStateMaintenanceSCHParams();
            inparam.set(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION, viewState.getObject(ViewStateKeys.IN_HOST_COMMUNICATION));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                _grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
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
     * @throws Exception
     */
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateMaintenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateMaintenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateMaintenanceSCHParams inparam = new SystemStateMaintenanceSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                txt_InAllocationClear.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
                _grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                viewState.setObject(ViewStateKeys.VS_HOST_COMMUNICATION, outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                viewState.setObject(ViewStateKeys.VS_IN_DAILY_PROCESS, outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                viewState.setObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                viewState.setObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                viewState.setObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE, outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                viewState.setObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR, outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
            }

            // clear.
            txt_InDailyProcess.setReadOnly(true);
            txt_InLoadPlanData.setReadOnly(true);
            txt_InCreateReportData.setReadOnly(true);
            txt_InRetrievalAllocate.setReadOnly(true);
            btn_DailyProcess.setEnabled(true);
            btn_LoadPlanData.setEnabled(true);
            btn_CreateReportData.setEnabled(true);
            btn_RetrievalAllocate.setEnabled(true);

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
    private void btn_Close_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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
